CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    price NUMERIC(12, 2)
);


CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    age INTEGER CHECK (age > 0),
    has_driver_license BOOLEAN DEFAULT FALSE,
    car_id INTEGER REFERENCES car(id)
);