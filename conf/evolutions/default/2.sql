# --- !Ups

CREATE TABLE schools
(
    id      INTEGER PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(100) NOT NULL UNIQUE,
    phone   VARCHAR(15) UNIQUE,
    email   VARCHAR(255) UNIQUE,
    website VARCHAR(255)
);

ALTER TABLE students
    ADD COLUMN school_id INTEGER;
ALTER TABLE students
    ADD CONSTRAINT fk_school_id FOREIGN KEY (school_id) REFERENCES schools (id);

INSERT INTO schools (name, phone, email, website)
VALUES ('School of Science', '555-555-5555', 'school.of.sciences@edu.com', 'www.school-of-sciences.edu'),
       ('School of Engineering', '555-555-4444', 'school.of.engineering@edu.com', 'www.school-of-engineering.edu'),
       ('EPFL', '555-555-3333', 'epfl@edu.ch', 'www.epfl.ch');

INSERT INTO schools (name)
VALUES ('CPNV');

UPDATE students
SET school_id = (SELECT id FROM schools WHERE name = 'EPFL')
WHERE id % 2 <> 0;

UPDATE students
SET school_id = (SELECT id FROM schools WHERE name = 'CPNV')
WHERE id % 2 = 0;

# --- !Downs
ALTER TABLE students
    DROP CONSTRAINT fk_school_id;
ALTER TABLE students
    DROP COLUMN school_id;

DROP TABLE schools;
