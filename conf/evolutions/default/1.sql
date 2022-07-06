# Tasks schema

# --- !Ups

CREATE TABLE students
(
    id    INTEGER PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO students (email)
VALUES ('johndoe@gmail.com'),
       ('johnatandotte@gmail.com'),
       ('janedowe@gmail.com'),
       ('junedos@hotmail.ch');

# --- !Downs

DROP TABLE students;
