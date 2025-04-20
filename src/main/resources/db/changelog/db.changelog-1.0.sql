--liquibase formatted sql

--changeset makson:1
CREATE TABLE IF NOT EXISTS Users
(
    ID       SERIAL PRIMARY KEY,
    Login    VARCHAR(16) UNIQUE NOT NULL,
    Password VARCHAR(512)       NOT NULL
);
--rollback DROP TABLE Users;

--changeset makson:2
CREATE TABLE IF NOT EXISTS Locations
(
    ID        SERIAL PRIMARY KEY,
    Name      VARCHAR(128)              NOT NULL,
    User_id   INT REFERENCES Users (ID) NOT NULL,
    Latitude  DECIMAL                   NOT NULL,
    Longitude DECIMAL                   NOT NULL
);
--rollback DROP TABLE Locations ;

--changeset makson:3
CREATE TABLE IF NOT EXISTS Sessions
(
    ID         UUID PRIMARY KEY,
    User_id    INT REFERENCES Users (ID) NOT NULL,
    Expires_at TIMESTAMP                 NOT NULL
);
--rollback DROP TABLE Users;