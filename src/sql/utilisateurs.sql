
CREATE DATABASE utilisateurs;

CREATE USER utilisateurs@"%" identified BY "utilisateurs@2026";

GRANT ALL PRIVILEGES ON utilisateurs.* TO utilisateurs@"%";

USE utilisateurs;

CREATE TABLE utilisateur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(100),
    login VARCHAR(50),
    password VARCHAR(100)
);
