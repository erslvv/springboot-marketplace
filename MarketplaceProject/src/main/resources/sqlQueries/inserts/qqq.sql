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
