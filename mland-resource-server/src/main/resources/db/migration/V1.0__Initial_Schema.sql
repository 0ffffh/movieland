CREATE SEQUENCE IF NOT EXISTS country_id_sequence START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS genre_id_sequence START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS movie_id_sequence START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS review_id_sequence START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS user_id_sequence START WITH 1 INCREMENT BY 50;

CREATE TABLE country
(
    id   INTEGER     NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_country PRIMARY KEY (id)
);

CREATE TABLE genre
(
    id   INTEGER     NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_genre PRIMARY KEY (id)
);

CREATE TABLE movie
(
    id           INTEGER          NOT NULL,
    name_native  VARCHAR(100)     NOT NULL,
    name_ru      VARCHAR(100)     NOT NULL,
    release_date DATE             NOT NULL,
    description  VARCHAR(1000),
    rating        DECIMAL(5, 2),
    price         DECIMAL(5, 2),
    picture_path VARCHAR(2000),
    votes        INTEGER,
    CONSTRAINT pk_movie PRIMARY KEY (id)
);

CREATE TABLE movies_countries
(
    movie_id   INTEGER NOT NULL,
    country_id INTEGER NOT NULL,
    CONSTRAINT pk_movies_countries PRIMARY KEY (movie_id, country_id)
);

CREATE TABLE movies_genres
(
    movie_id INTEGER NOT NULL,
    genre_id INTEGER NOT NULL,
    CONSTRAINT pk_movies_genres PRIMARY KEY (movie_id, genre_id)
);

CREATE TABLE review
(
    id       INTEGER      NOT NULL,
    movie_id INTEGER      NOT NULL,
    user_id  INTEGER      NOT NULL,
    text     VARCHAR(500) NOT NULL,
    CONSTRAINT pk_review PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       INTEGER      NOT NULL,
    nickname VARCHAR(50)  NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE movies_countries
    ADD CONSTRAINT FK_MOVIES_COUNTRIES_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);

ALTER TABLE movies_countries
    ADD CONSTRAINT FK_MOVIES_COUNTRIES_ON_MOVIE FOREIGN KEY (movie_id) REFERENCES movie (id);

ALTER TABLE movies_genres
    ADD CONSTRAINT FK_MOVIES_GENRES_ON_GENRE FOREIGN KEY (genre_id) REFERENCES genre (id);

ALTER TABLE movies_genres
    ADD CONSTRAINT FK_MOVIES_GENRES_ON_MOVIE FOREIGN KEY (movie_id) REFERENCES movie (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_MOVIE FOREIGN KEY (movie_id) REFERENCES movie (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);