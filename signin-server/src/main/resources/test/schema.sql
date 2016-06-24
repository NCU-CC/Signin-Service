CREATE TABLE IF NOT EXISTS activity
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  date_created DATETIME,
  date_updated DATETIME,
  date_started DATETIME,
  date_ended DATETIME,
  serial_id VARCHAR(255),
  creator_id VARCHAR(255),
  name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS signin
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  date_created DATETIME,
  user_id VARCHAR(255),
  activity_id INT NOT NULL
);