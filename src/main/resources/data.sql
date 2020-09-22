DROP TABLE IF EXISTS person;

CREATE TABLE person (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  personality CHAR(1)
);

INSERT INTO person (first_name, last_name, personality) VALUES
  ('Peter', 'Parker', 'M'),
  ('Bruce', 'Wayne', 'D'),
  ('Selina', 'Kyle', 'C');
