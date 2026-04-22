CREATE TABLE contausuario (

    id BIGINT auto_increment NOT NULL,
    nomeusuario VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,

    PRIMARY KEY(id)
);