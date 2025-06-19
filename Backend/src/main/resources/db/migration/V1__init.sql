-- Maak de gebruikerstabel aan
CREATE TABLE user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  email VARCHAR(255),
  password VARCHAR(255) NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Maak de notitietabel aan
CREATE TABLE note (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255),
  content TEXT,
  date DATE,
  deadline DATE,
  completed BOOLEAN,
  urgent BOOLEAN,
  user_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES user(id)
);

-- Optioneel: tabel voor tags aan notities
CREATE TABLE note_tags (
  note_id BIGINT,
  tag VARCHAR(255),
  FOREIGN KEY (note_id) REFERENCES note(id)
);
