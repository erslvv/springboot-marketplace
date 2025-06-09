CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       name VARCHAR,
                       email VARCHAR,
                       password VARCHAR
);
CREATE TABLE "products" (
    "product_id" SERIAL PRIMARY KEY,
    "name" varchar,
    "description" varchar,
    "price" int
);

CREATE TABLE "orders" (
    "order_id" SERIAL PRIMARY KEY,
    "user_id" int
);

CREATE TABLE order_items (
                             id SERIAL PRIMARY KEY,
                             order_id int,
                             product_id int,
                             quantity int DEFAULT 1
);



ALTER TABLE "orders" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");
ALTER TABLE "order_items" ADD FOREIGN KEY ("order_id") REFERENCES "orders" ("order_id");
ALTER TABLE "order_items" ADD FOREIGN KEY ("product_id") REFERENCES "products" ("product_id");


-- 1) Создаём секвенс, если ещё нет
CREATE SEQUENCE IF NOT EXISTS orders_order_id_seq;

-- 2) Синхронизируем значение секвенса с уже существующими данными
SELECT setval(
               'orders_order_id_seq',
               (SELECT COALESCE(MAX(order_id), 0) FROM orders) + 1,
               false
       );

-- 3) Привязываем секвенс к колонке order_id
ALTER TABLE orders
    ALTER COLUMN order_id
        SET DEFAULT nextval('orders_order_id_seq');

-- 4) Убедимся, что order_id не может быть NULL
ALTER TABLE orders
    ALTER COLUMN order_id
        SET NOT NULL;

