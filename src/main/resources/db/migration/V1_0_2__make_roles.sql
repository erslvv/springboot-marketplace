ALTER TABLE users ADD COLUMN IF NOT EXISTS role VARCHAR(20);
UPDATE users SET role = 'USER' WHERE role IS NULL;
ALTER TABLE users ALTER COLUMN role SET NOT NULL;

DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1 FROM pg_constraint
            WHERE conname = 'chk_user_role'
        ) THEN
            ALTER TABLE users
                ADD CONSTRAINT chk_user_role CHECK (role IN ('USER','ADMIN'));
        END IF;
    END$$;

-- пример инициализации админа (по желанию)
-- UPDATE users SET role = 'ADMIN' WHERE email = '12345678@gmail.com';
