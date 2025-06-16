
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description VARCHAR(255),
                          price INTEGER NOT NULL
);

CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        user_id INTEGER NOT NULL,
                        CONSTRAINT fk_orders_user FOREIGN KEY (user_id)
                            REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE order_items (
                             id SERIAL PRIMARY KEY,
                             order_id INTEGER NOT NULL,
                             product_id INTEGER NOT NULL,
                             quantity INTEGER NOT NULL DEFAULT 1,
                             CONSTRAINT fk_order_items_order FOREIGN KEY (order_id)
                                 REFERENCES orders(id) ON DELETE CASCADE,
                             CONSTRAINT fk_order_items_product FOREIGN KEY (product_id)
                                 REFERENCES products(id) ON DELETE RESTRICT,
                             CONSTRAINT unq_order_product UNIQUE (order_id, product_id)
);

-- Примеры запросов:
SELECT *
FROM users
WHERE id = 254;

SELECT *
FROM products
WHERE id IN (6, 7);

SELECT *
FROM orders;

SELECT *
FROM order_items;
