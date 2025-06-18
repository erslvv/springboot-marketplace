-- 1. добаляем колонку (пока NULLable)
ALTER TABLE users ADD COLUMN role VARCHAR(20);

-- 2. заполняем существующих пользователей значением USER
UPDATE users SET role = 'USER' WHERE role IS NULL;

-- 3. делаем NOT NULL + ограничиваем список значений
ALTER TABLE users ALTER COLUMN role SET NOT NULL;
ALTER TABLE users
    ADD CONSTRAINT chk_user_role
        CHECK (role IN ('USER','ADMIN'));
UPDATE users SET role = 'ADMIN'
WHERE email = '12345678@gmail.com';

UPDATE users
SET    role = 'USER'
WHERE  role IS NULL
  AND  email <> 'era@gmail.com';

Select * from users where role = 'ADMIN';

Select * from users where role = 'USER';