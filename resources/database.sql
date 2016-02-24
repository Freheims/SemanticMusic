CREATE TABLE IF NOT EXISTS artist (
    id char(22) NOT NULL,
    name varchar(60) NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS album (
    id char(22) NOT NULL,
    name varchar(120) NOT NULL,
    type varchar(20),
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS track (
    id char(22) NOT NULL,
    name varchar(60) NOT NULL,
    duration integer NOT NULL,
    image char(70),
    annotated boolean DEFAULT FALSE,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS genre (
    name varchar(30) NOT NULL,
    PRIMARY KEY(name)
    );

CREATE TABLE IF NOT EXISTS trackGenre (
    id int(10) NOT NULL AUTO_INCREMENT,
    track_id char(22) NOT NULL,
    genre char(30) NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS trackArtist (
    id int(10) NOT NULL AUTO_INCREMENT,
    track_id char(22) NOT NULL,
    artist_id char(22) NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS trackAlbum (
    id int(10) NOT NULL AUTO_INCREMENT,
    track_id char(22) NOT NULL,
    album_id char(22) NOT NULL,
    PRIMARY KEY(id)
    );

