# Déploiement avec Docker

Installez les dépendances suivantes et ajoutez votre utilisateur au groupe docker :
```bash
sudo apt install docker docker-compose
sudo usermod -aG docker $USER
```

**Note** : Vous devrez vous déconnecter et vous reconnecter pour que les modifications prennent effet.

Tous les fichiers sont déjà créés, vous n'avez qu'à exécuter la commande pour construire le projet à la racine :

```bash
docker-compose build
```

Pour allumer et éteindre l'application, utilisez les commandes suivantes :

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
