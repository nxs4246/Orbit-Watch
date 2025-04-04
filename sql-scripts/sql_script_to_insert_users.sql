-- Run this script to insert users into the asteroidalert database.
-- Ensure you are connected to the `asteroidalert` database before running this script.
SELECT * FROM asteroidalert.user;

-- This SQL script inserts multiple users into the `asteroidalert.user` table.
INSERT INTO asteroidalert.user(full_name, email, notification_enabled)
VALUES("Timo Werner", "tw@gmail.com", 1);

INSERT INTO asteroidalert.user(full_name, email, notification_enabled)
VALUES("James Werner", "jamesw@gmail.com", 1);

INSERT INTO asteroidalert.user(full_name, email, notification_enabled)
VALUES("John Werner", "johnw@gmail.com", 0);