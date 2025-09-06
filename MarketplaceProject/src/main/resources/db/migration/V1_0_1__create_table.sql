-- создаём/выбираем схему
CREATE SCHEMA IF NOT EXISTS public;
SET search_path TO public;

-- USERS
CREATE TABLE IF NOT EXISTS users (
                                     id        BIGSERIAL PRIMARY KEY,
                                     name      VARCHAR(255)        NOT NULL,
    email     VARCHAR(255)        NOT NULL UNIQUE,
    password  VARCHAR(255)        NOT NULL,
    role      VARCHAR(20)         NOT NULL DEFAULT 'USER',
    CONSTRAINT chk_user_role CHECK (role IN ('USER','ADMIN'))
    );

-- PRODUCTS
CREATE TABLE IF NOT EXISTS products (
                                        id          BIGSERIAL PRIMARY KEY,
                                        name        VARCHAR(255)  NOT NULL,
    description TEXT,
    price       INTEGER       NOT NULL CHECK (price >= 0)
    );

-- ORDERS
CREATE TABLE IF NOT EXISTS orders (
                                      id         BIGSERIAL PRIMARY KEY,
                                      user_id    BIGINT       NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    status     VARCHAR(20)  NOT NULL DEFAULT 'CREATED',
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
    );

-- ORDER ITEMS
CREATE TABLE IF NOT EXISTS order_items (
                                           id         BIGSERIAL PRIMARY KEY,
                                           order_id   BIGINT NOT NULL REFERENCES orders(id)   ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE RESTRICT,
    quantity   INTEGER NOT NULL CHECK (quantity > 0),
    -- цена на момент покупки (если надо)
    price_at_purchase INTEGER
    );

-- Индексы
CREATE INDEX IF NOT EXISTS idx_orders_user_id      ON orders(user_id);
CREATE INDEX IF NOT EXISTS idx_order_items_order   ON order_items(order_id);
CREATE INDEX IF NOT EXISTS idx_order_items_product ON order_items(product_id);
