CREATE TABLE user_data (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  account_non_expired BOOLEAN NOT NULL,
  account_non_locked BOOLEAN NOT NULL,
  credentials_non_expired BOOLEAN NOT NULL,
  enabled BOOLEAN NOT NULL
);

CREATE TABLE movie_data (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  poster VARCHAR(255) NOT NULL,
  rating DECIMAL(5, 2)
);

CREATE TABLE match_data (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_value VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    finished BOOLEAN
);


CREATE TABLE round_data (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_movie_id BIGINT NOT NULL,
    second_movie_id BIGINT NOT NULL,
    match_id BIGINT NOT NULL,
    user_value VARCHAR(255),
    hash_value INT NOT NULL UNIQUE,
    right_response BOOLEAN,
    FOREIGN KEY (first_movie_id) REFERENCES movie_data(id),
    FOREIGN KEY (second_movie_id) REFERENCES movie_data(id),
    FOREIGN KEY (match_id) REFERENCES match_data(id)
);