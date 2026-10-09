CREATE TABLE IF NOT EXISTS users (
    id                BIGSERIAL      PRIMARY KEY,
    first_name        VARCHAR(50)  NOT NULL,
    last_name         VARCHAR(50)  NOT NULL,
    date_of_birth     DATE         NOT NULL,
    email             VARCHAR(100) UNIQUE NOT NULL,
    password          VARCHAR(255) NOT NULL,
    role              VARCHAR(15)  NOT NULL,
    registration_date DATE         NOT NULL
);

CREATE TABLE IF NOT EXISTS hotels (
    id      BIGSERIAL    PRIMARY KEY,
    name    VARCHAR(256) NOT NULL,
    address VARCHAR(512) NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel_staff (
    id         BIGSERIAL    PRIMARY KEY,
    hotel_id   BIGINT       NOT NULL,
    user_id    BIGINT       NOT NULL,
    staff_role VARCHAR(20)  NOT NULL,

    CONSTRAINT fk_hotel_staff_hotel FOREIGN KEY (hotel_id) REFERENCES hotels (id),
    CONSTRAINT fk_hotel_staff_user  FOREIGN KEY (user_id)  REFERENCES users (id),
    CONSTRAINT uq_hotel_staff_user_hotel UNIQUE (user_id, hotel_id),
    CONSTRAINT ck_hotel_staff_role CHECK (staff_role IN ('MANAGER', 'EMPLOYEE'))
);

CREATE INDEX IF NOT EXISTS idx_hotel_staff_hotel_role ON hotel_staff (hotel_id, staff_role);