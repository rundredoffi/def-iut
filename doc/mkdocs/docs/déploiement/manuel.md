# Déploiement Manuel
 
* Installation des dépendances
 
```bash
sudo apt install npm maven mariadb-server mariadb-client openjdk-17-jdk
```
 
## Frontend
 
* Installation des modules npm
 
```bash
cd frontend
npm install
```

* Installation des défis

```bash
mkdir -p ./public/challenges_doc/
sudo cp ../doc/mkdocs/docs/challenges/* ./public/challenges_doc/
```
 
* Lancement du frontend en local

```bash
npm run serve
```

## Backend
 
### Création de la base
 
Les scripts SQL sont disponibles dans `doc/database/scripts`
 
* Connexion en Shell
 
```bash
sudo mariadb
```
 
* Configuration initiale de la base de données
 
Utilisez le script suivant pour configurer la base de donnée :
 
```sql
CREATE USER 'defiut'@'localhost' IDENTIFIED BY 'password'; -- Mot de passe modifiable
CREATE DATABASE defiut;
GRANT ALL PRIVILEGES ON defiut.* TO 'defiut'@'localhost';
SET GLOBAL max_connections = 1000000;
USE defiut;
```

Configurez le mot de passe de l'utilisateur `defiut` dans le fichier `backend/src/main/resources/application.properties`
 
Utilisez ensuite le script `init.sql` situé dans `doc/database/scripts` pour initialiser la base de données.
 
À des fins de test de l'application, le script `init.sql` contient des données factices.

### Compilation et exécution du backend Java
 
* Compilation
 
```bash
cd backend
mvn clean install
```
 
* Exécution
 
```bash
mvn spring-boot:run
```
