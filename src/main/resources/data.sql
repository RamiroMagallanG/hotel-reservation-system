INSERT INTO users (first_name, last_name, date_of_birth, email, password, role, registration_date) VALUES
  ('Admin',  'Sistema',  '1990-01-15', 'admin@test.com',      '$2a$12$oLePXbyCabbCxEuabRJBZORP7.ztMTO1OFkSx0.xwiUxUhwsn3jLy', 'ADMIN',  '2026-10-08'),
  ('Marta',  'Gómez',    '1985-03-22', 'manager.a@test.com',  '$2a$12$EVvWBA2PziiXRvFaOr77H.DdQJ9jx0tFPO61f7XrF8kFS44MMnG/.', 'STAFF',  '2026-10-08'),
  ('Julián', 'Pérez',    '1988-07-09', 'manager.b@test.com',  '$2a$12$HQEIgbLIY.SN0jx.tZnDmOVsj6s7dkmTv21j4qlaNjW0Oop69CGzq', 'STAFF',  '2026-10-08'),
  ('Lucía',  'Fernández','1995-11-30', 'employee.a@test.com', '$2a$12$GHvrP1uMyb/5kcBUM/V6q.vtmjjkH7GvmxpDtMKdE11sI2cctbRqa', 'STAFF',  '2026-10-08'),
  ('Carlos', 'Díaz',     '1992-05-18', 'client@test.com',     '$2a$12$3a51EqbN2G5YFLz.npv9kOfZgPF3D3Ge3RtaamVERAvNVsTO6oe0G', 'CLIENT', '2026-10-08');

INSERT INTO hotels (name, address) VALUES
  ('Hotel A', 'Calle Falsa 123'),
  ('Hotel B', 'Avenida Siempreviva 742');

INSERT INTO hotel_staff (hotel_id, user_id, staff_role)
SELECT h.id, u.id, v.staff_role
FROM (VALUES
  ('Hotel A', 'manager.a@test.com',  'MANAGER'),
  ('Hotel A', 'employee.a@test.com', 'EMPLOYEE'),
  ('Hotel B', 'manager.b@test.com',  'MANAGER')
) AS v (hotel_name, user_email, staff_role)
JOIN hotels h ON h.name  = v.hotel_name
JOIN users  u ON u.email = v.user_email;