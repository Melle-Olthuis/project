CREATE TABLE note (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255),
  content TEXT,
  date DATE,
  deadline DATE,
  completed BOOLEAN,
  urgent BOOLEAN
);

CREATE TABLE note_tags (
  note_id BIGINT,
  tag VARCHAR(255),
  FOREIGN KEY (note_id) REFERENCES note(id) ON DELETE CASCADE
);
