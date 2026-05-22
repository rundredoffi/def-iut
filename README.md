# Déf'IUT

Déf'IUT est une application web éducative permettant aux étudiants de relever différents défis interactifs autour du développement et des technologies informatiques.
Elle propose une interface intuitive pour consulter des défis, soumettre des solutions et suivre sa progression.

La documentation complète se trouve dans [http://localhost:8000](http://localhost:8000) lorsque l'application est lancée.

Une démonstration vidéo de l'application est disponible [ici](https://youtu.be/WvscyhMkrLY?si=UMKc40824oDTsjLY).

## Sommaire

1. [Prérequis](#prérequis-)
2. [Déploiement avec Docker (recommandé)](#déploiement-avec-docker-recommandé)
3. [Déploiement manuel](#déploiement-manuel)
    - [Frontend](#frontend)
    - [Backend](#backend)
4. [Accès à l'application](#Accès-à-lapplication)
5. [Développeurs](#développeurs)

## Prérequis :
Dans un premier temps, clonez le dépôt :

```bash
git clone https://github.com/rundredoffi/def-iut.git
cd defiut-app
```

Ensuite, vous pouvez la lancer soit :

- Avec Docker (recommandé)
- Manuellement

## Déploiement avec Docker (recommandé)

Installez les dépendances suivantes et ajoutez votre utilisateur au groupe Docker :
```bash
sudo apt install docker docker-compose
sudo usermod -aG docker $USER
```

**Note** : Vous devrez vous déconnecter et vous reconnecter pour que les modifications prennent effet.

Tous les fichiers sont déjà créés, vous n'avez qu'à exécuter la commande suivante à la racine du projet pour le construire :

```bash
docker-compose build
```

Pour démarrer et arrêter l'application, utilisez les commandes suivantes :

```bash
docker-compose up
docker-compose down
```

**Note** : Pensez à désactiver votre backend, frontend ainsi que la base de données MySQL en local pour éviter les conflits de port.

Pour réinitialiser la base de données, exécutez la commande suivante :

```bash
docker volume rm defiut-app_mysql-data
```

**Note** : Une nouvelle construction de l'application sera nécessaire pour démarrer l'application avec la base de données réinitialisée.

## Déploiement Manuel

* Installation des dépendances

```bash
sudo apt install npm maven mariadb-server mariadb-client openjdk-17-jdk
```

### Frontend

* Installation des modules npm dans le dossier Frontend

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

### Backend

#### Création de la base

Les scripts SQL sont disponibles dans `doc/database/scripts`

* Connexion en Shell

```bash
sudo mariadb
```

* Configuration initiale de la base de données avec ce script :


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

#### Compilation et exécution du backend Java

* Compilation :

```bash
cd backend
mvn clean install
```

* Exécution :

```bash
mvn spring-boot:run
```

## Accès à l'application

Pour accéder à l'application, ouvrez un navigateur et entrez l'adresse correspondant à votre besoin :

- Application utilisateur : [http://localhost:8080](http://localhost:8080)

L'administrateur par défaut est `admin@localhost` avec le mot de passe `admin`.

- Accès API : [http://localhost:8081/api](http://localhost:8080/api)
- Documentation : [http://localhost:8000](http://localhost:8000)

## Développeurs

- Thomas LE BRETON
- Gwenvaël CAOUISSIN
- Gwendal LE GUELLEC
- Guyaume MORICE

# Mainteneurs
- Melvyn BARIOU
- Nicolas JOUIN--DERRIEN