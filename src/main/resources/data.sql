-- This file can be used to insert initial data.
-- Spring Boot will run this automatically if spring.jpa.hibernate.ddl-auto is not 'create' or 'create-drop'.
-- For this project, we recommend manual insertion or a dedicated data seeding service.

-- Sample Admin User (Password: admin123)
-- The password hash is for 'admin123' generated using a BCrypt generator.
INSERT INTO users (email, password, first_name, last_name, role)
VALUES ('admin@example.com', '$2a$10$3g5V3Bs4C4n3C2.d3Q.O4uAqfC6B.l2s9j.T4J.z.e1G0j.8m.L8S', 'Admin', 'User', 'ADMIN')
ON DUPLICATE KEY UPDATE email=email;

-- Sample Categories
INSERT INTO categories (name, description) VALUES ('Electronics', 'Find the latest gadgets and electronic devices.')
ON DUPLICATE KEY UPDATE name=name;
INSERT INTO categories (name, description) VALUES ('Books', 'Discover new worlds and stories.')
ON DUPLICATE KEY UPDATE name=name;

-- Sample Products
INSERT INTO products (name, description, price, stock, category_id, image_url)
VALUES ('Laptop Pro', 'A powerful laptop for professionals.', 1200.00, 50, 1, '/images/laptop.jpg')
ON DUPLICATE KEY UPDATE name=name;
INSERT INTO products (name, description, price, stock, category_id, image_url)
VALUES ('Smartphone X', 'The latest smartphone with amazing features.', 800.00, 150, 1, '/images/smartphone.jpg')
ON DUPLICATE KEY UPDATE name=name;
INSERT INTO products (name, description, price, stock, category_id, image_url)
VALUES ('The Great Novel', 'A captivating story of adventure.', 25.50, 200, 2, '/images/book.jpg')
ON DUPLICATE KEY UPDATE name=name;