# Utiliser l'image officielle MySQL
FROM mysql:latest

# Définir les variables d'environnement pour la configuration initiale de MySQL
ENV MYSQL_ROOT_PASSWORD=QylWbngY2gm3OTO4BUBJtn8Ik1iTPeaCIhptoEXJRaouJH3rPs7kcNC3pjDA
ENV MYSQL_DATABASE=challenge_db

# Exposer le port 3307
EXPOSE 3307

# Ajouter le fichier SQL d'initialisation
COPY init.sql /docker-entrypoint-initdb.d/

# Changer le port par défaut de MySQL
CMD ["mysqld", "--port=3307"]
