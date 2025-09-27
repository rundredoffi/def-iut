# Improved MySQL<br><br>

## Introduction<br>

Bienvenue dans le défi "Improved MySQL" !<br><br>

Vous êtes administrateur système d'un magasin de cidre breton. Malheusement, quand nos 4 salariés essaient de se connecter simultanément à la base de données, ils reçoivent un message d'erreur. Vous devez donc résoudre ce problème.<br><br>

<strong>Attention !</strong> Vous devez respecter la politique de sécurité de l'entreprise : c'est-à-dire ne pas donner plus de droits que nécessaire aux utilisateurs.

Voici les élements dont vous disposez :<br><br>

- Un serveur MySQL que vous lancerez en cliquant sur le bouton "Lancer le défi"
- Utilisateur de la base de données : `root`
- L'adresse IP de la base de données : `localhost` ou `127.0.0.1`
- Port de la base de données : `3307`
- Mot de passe de l'utilisateur `root` : `QylWbngY2gm3OTO4BUBJtn8Ik1iTPeaCIhptoEXJRaouJH3rPs7kcNC3pjDA`
- Nom de la base de données : `challenge_db`<br><br>

Pour lancer le Docker du défi, vous pouvez suivre les instructions suivantes :<br><br>

```bash
cd ./challenges/ImprovedMySQL
docker-compose up --build
```

<br>

Pour arrêter et réinitialiser le Docker :<br><br>

```bash
docker-compose down
```

<br>

## Objectif<br>

Vous devez permettre à tous les salariés de se connecter simultanément à la base de données.<br><br>

## Informations complémentaires<br>

Si vous avez besoin de réinitialiser la base de données, vous pouvez arrêter et relancer le Docker comme indiqué ci-dessus.<br><br>

## Flag<br>

Le flag est une chaine de caractères qui ressemble à `FLAG-xxxxx` trouvable dans la table `flag_table`.<br><br>

## Encouragements<br>

Bon courage !