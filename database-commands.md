# PostgreSQL Database Commands

## Method 1: Using Docker Exec (Recommended)

### Connect to PostgreSQL container:
```bash
docker exec -it postgres5332 psql -U demo -d demo
```

### Once connected, you can run SQL commands:

#### View all employees:
```sql
SELECT * FROM employees;
```

#### Insert a new employee:
```sql
INSERT INTO employees (name, email, department) 
VALUES ('John Smith', 'john.smith@example.com', 'Engineering');
```

#### Insert multiple employees:
```sql
INSERT INTO employees (name, email, department) VALUES
('Sarah Johnson', 'sarah.j@example.com', 'Marketing'),
('Mike Davis', 'mike.davis@example.com', 'Sales'),
('Emily Wilson', 'emily.w@example.com', 'HR');
```

#### Update an employee:
```sql
UPDATE employees 
SET name = 'John Updated', email = 'john.updated@example.com' 
WHERE id = 1;
```

#### Delete an employee:
```sql
DELETE FROM employees WHERE id = 1;
```

#### Exit psql:
```sql
\q
```

---

## Method 2: Using psql directly (if PostgreSQL client is installed)

### Connect from host machine:
```bash
psql -h localhost -p 5433 -U demo -d demo
```

When prompted, enter password: `demo123`

### Then use the same SQL commands as above.

---

## Quick One-Liner Commands

### Insert data without entering interactive mode:
```bash
docker exec -i postgres5332 psql -U demo -d demo -c "INSERT INTO employees (name, email, department) VALUES ('Test User', 'test@example.com', 'IT');"
```

### View all employees:
```bash
docker exec -it postgres5332 psql -U demo -d demo -c "SELECT * FROM employees;"
```

### Clear all employees:
```bash
docker exec -i postgres5332 psql -U demo -d demo -c "DELETE FROM employees;"
```

