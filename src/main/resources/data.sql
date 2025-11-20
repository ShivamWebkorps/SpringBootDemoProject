-- Clear tables
TRUNCATE TABLE employees RESTART IDENTITY CASCADE;
TRUNCATE TABLE addresses RESTART IDENTITY CASCADE;
TRUNCATE TABLE countries RESTART IDENTITY CASCADE;

-- Countries (only unique ones)
INSERT INTO countries (iso_code, country_name, telephone_code) VALUES
  ('IN', 'INDIA', '+91'),
  ('US', 'USA', '+1'),
  ('CN', 'CHINA', '+86');

-- Addresses
INSERT INTO addresses (building, street, city) VALUES
  ('A-101', 'Vijay Nagar', 'Indore'),
  ('B-204', 'MG Road', 'Bangalore'),
  ('C-307', 'Marine Drive', 'Mumbai'),
  ('D-410', 'Park Street', 'Kolkata'),
  ('A-515', 'Connaught Place', 'New Delhi'),
  ('F-620', 'Viman Nagar', 'Pune');

-- Employees (country_id = 1,2,3 ONLY)
INSERT INTO employees (
    name, email, department, badge_number, phone_number, telephone_code,
    deductions, in_hand_salary, badge_prefix, address_id, country_id
) VALUES
  ('Shivam', 'shivam@gmail.com', 'Engineering', 'SHIV7890', '+91-9876543210', '+91',
   5000.00, 45000.00, 'SHVM', 1, 1),

  ('Amit', 'amit@gmail.com', 'Engineering', 'AMIT4567', '+91-9823456789', '+91',
   4500.00, 42000.00, 'AMIT', 2, 1),

  ('Aryan', 'aryan@gmail.com', 'Marketing', 'ARYN1234', '+1-4155557890', '+1',
   3000.00, 40000.00, 'ARYN', 3, 2),

  ('Arnav', 'arnav@gmail.com', 'Operations', 'ARNV2345', '+1-6175551234', '+1',
   3200.00, 38000.00, 'ARNV', 4, 2),

  ('Srijan', 'srijan@gmail.com', 'Product', 'SRJN5678', '+86-2155559876', '+86',
   2800.00, 36000.00, 'SRJN', 5, 3),

  ('Saharsh', 'saharsh@gmail.com', 'Support', 'SHRS6789', '+86-1055554321', '+86',
   2600.00, 34000.00, 'SHRS', 6, 3);
