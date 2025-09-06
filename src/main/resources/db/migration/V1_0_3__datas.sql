INSERT INTO users (id, name, email, password, role) VALUES
    (1, 'Test User', 'user@example.com', 'pass', 'USER');

INSERT INTO products (id, name, description, price) VALUES
    (1, 'Sample Product', 'Demo product', 100);

INSERT INTO orders (id, user_id) VALUES
    (1, 1);

INSERT INTO order_items (id, order_id, product_id, quantity) VALUES
    (1, 1, 1, 2);
