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





-- ______________________________

INSERT INTO "products" ("product_id","name","description","price") VALUES (1, 'Air Jordans', 'Air Jordan – iconic Nike sneakers blending style, comfort, and Michael Jordan’s legacy.', 200);

select * from users


SELECT * FROM users WHERE user_id = 254;
SELECT * FROM products WHERE product_id IN (6, 7);

-- 1) создаём секвенс для order_items.id
CREATE SEQUENCE IF NOT EXISTS order_items_id_seq;

-- 2) “выравниваем” его на текущее max(id)+1
SELECT setval(
               'order_items_id_seq',
               (SELECT COALESCE(MAX(id),0) FROM order_items) + 1,
               false
       );

-- 3) привязываем секвенс к колонке id
ALTER TABLE order_items
    ALTER COLUMN id
        SET DEFAULT nextval('order_items_id_seq');

-- 4) убеждаемся, что id по-прежнему NOT NULL
ALTER TABLE order_items
    ALTER COLUMN id
        SET NOT NULL;



Select * from order_items
