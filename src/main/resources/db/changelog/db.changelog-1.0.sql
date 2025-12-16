--liquibase formatted sql

--changeset kane:1
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(64) NOT NULL UNIQUE,
    firstname VARCHAR(64) NOT NULL,
    lastname VARCHAR(64) NOT NULL,
    email VARCHAR(64) UNIQUE,
    bio TEXT,
    avatar VARCHAR(128),
    password VARCHAR(3000),
    created_date DATE
);

--changeset kane:2
CREATE TABLE IF NOT EXISTS comment
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users,
    message TEXT NOT NULL,
    created_date DATE,
    post_id BIGINT NOT NULL REFERENCES post ON DELETE CASCADE
);

--changeset kane:3
CREATE TABLE IF NOT EXISTS post
(
    id BIGSERIAL PRIMARY KEY ,
    title VARCHAR(64),
    caption VARCHAR(64),
    location VARCHAR(64),
    user_id BIGINT REFERENCES users ON DELETE CASCADE,
    created_date DATE
);

--changeset kane:4
CREATE TABLE IF NOT EXISTS image_model
(
    id BIGSERIAL PRIMARY KEY ,
    imageURL VARCHAR(128),
    post_id BIGINT
);

----changeset kane:5
CREATE TABLE IF NOT EXISTS likes_post
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users ON DELETE CASCADE,
    post_id BIGINT REFERENCES post ON DELETE CASCADE,
    UNIQUE (user_id, post_id)
)
