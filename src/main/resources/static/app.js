const API_BASE_URL = '/employees';

// Load employees on page load
document.addEventListener('DOMContentLoaded', () => {
    loadEmployees();
    
    // Handle form submission
    document.getElementById('employee-form').addEventListener('submit', handleFormSubmit);
    
    // Smooth scroll for navigation links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                const offset = 100; // Account for fixed navbar
                const targetPosition = target.getBoundingClientRect().top + window.pageYOffset - offset;
                window.scrollTo({ top: targetPosition, behavior: 'smooth' });
            }
        });
    });
});

// Load all employees
async function loadEmployees() {
    const loading = document.getElementById('loading');
    const employeesList = document.getElementById('employees-list');
    
    try {
        loading.style.display = 'block';
        employeesList.innerHTML = '';
        
        const response = await fetch(API_BASE_URL);
        if (!response.ok) {
            throw new Error('Failed to fetch employees');
        }
        
        const employees = await response.json();
        loading.style.display = 'none';
        
        if (employees.length === 0) {
            employeesList.innerHTML = '<div class="empty-state"><p>No employees found. Add your first employee above!</p></div>';
            return;
        }
        
        employeesList.innerHTML = employees.map(employee => createEmployeeCard(employee)).join('');
        
        // Add event listeners to delete buttons
        document.querySelectorAll('.btn-delete').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const id = e.target.getAttribute('data-id');
                deleteEmployee(id);
            });
        });
        
        // Add event listeners to edit buttons
        document.querySelectorAll('.btn-edit').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const id = e.target.getAttribute('data-id');
                editEmployee(id);
            });
        });
        
    } catch (error) {
        loading.style.display = 'none';
        employeesList.innerHTML = `<div class="error-message">Error loading employees: ${error.message}</div>`;
    }
}

// Create employee card HTML
function createEmployeeCard(employee) {
    return `
        <div class="employee-card">
            <div class="employee-info">
                <h3>${escapeHtml(employee.name)}</h3>
                <p class="email">${escapeHtml(employee.email)}</p>
                <span class="department">${escapeHtml(employee.department)}</span>
            </div>
            <div class="employee-actions">
                <button class="btn-edit" data-id="${employee.id}">Edit</button>
                <button class="btn-delete" data-id="${employee.id}">Delete</button>
            </div>
        </div>
    `;
}

// Handle form submission
async function handleFormSubmit(e) {
    e.preventDefault();
    
    const form = e.target;
    const employeeId = document.getElementById('employee-id').value;
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const department = document.getElementById('department').value;
    
    const employeeData = {
        name: name,
        email: email,
        department: department
    };
    
    try {
        let response;
        if (employeeId) {
            // Update existing employee
            response = await fetch(`${API_BASE_URL}/${employeeId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(employeeData)
            });
        } else {
            // Create new employee
            response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(employeeData)
            });
        }
        
        if (!response.ok) {
            throw new Error('Failed to save employee');
        }
        
        // Reset form and reload employees
        form.reset();
        document.getElementById('employee-id').value = '';
        document.getElementById('form-title').textContent = 'Add New Employee';
        document.getElementById('submit-btn').textContent = 'Add Employee';
        document.getElementById('cancel-btn').style.display = 'none';
        
        showMessage('Employee saved successfully!', 'success');
        loadEmployees();
        
    } catch (error) {
        showMessage(`Error saving employee: ${error.message}`, 'error');
    }
}

// Edit employee
async function editEmployee(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`);
        if (!response.ok) {
            throw new Error('Failed to fetch employee');
        }
        
        const employee = await response.json();
        
        // Populate form with employee data
        document.getElementById('employee-id').value = employee.id;
        document.getElementById('name').value = employee.name;
        document.getElementById('email').value = employee.email;
        document.getElementById('department').value = employee.department;
        
        // Update form title and button
        document.getElementById('form-title').textContent = 'Edit Employee';
        document.getElementById('submit-btn').textContent = 'Update Employee';
        document.getElementById('cancel-btn').style.display = 'inline-block';
        
        // Scroll to form
        setTimeout(() => {
            const formSection = document.querySelector('.form-section');
            if (formSection) {
                const offset = 100; // Account for fixed navbar
                const targetPosition = formSection.getBoundingClientRect().top + window.pageYOffset - offset;
                window.scrollTo({ top: targetPosition, behavior: 'smooth' });
            }
        }, 100);
        
    } catch (error) {
        showMessage(`Error loading employee: ${error.message}`, 'error');
    }
}

// Delete employee
async function deleteEmployee(id) {
    if (!confirm('Are you sure you want to delete this employee?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            throw new Error('Failed to delete employee');
        }
        
        showMessage('Employee deleted successfully!', 'success');
        loadEmployees();
        
    } catch (error) {
        showMessage(`Error deleting employee: ${error.message}`, 'error');
    }
}

// Cancel edit
function cancelEdit() {
    document.getElementById('employee-form').reset();
    document.getElementById('employee-id').value = '';
    document.getElementById('form-title').textContent = 'Add New Employee';
    document.getElementById('submit-btn').textContent = 'Add Employee';
    document.getElementById('cancel-btn').style.display = 'none';
}

// Show message
function showMessage(message, type) {
    const messageDiv = document.createElement('div');
    messageDiv.className = type === 'success' ? 'success-message' : 'error-message';
    messageDiv.textContent = message;
    
    const mainContent = document.querySelector('.main-content');
    if (mainContent) {
        mainContent.insertBefore(messageDiv, mainContent.firstChild);
        
        setTimeout(() => {
            messageDiv.remove();
        }, 3000);
    }
}

// Escape HTML to prevent XSS
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

