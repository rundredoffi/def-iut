# Utiliser l'image officielle Python comme base
FROM python:3.9-slim

# Créer un utilisateur non-root
RUN adduser --disabled-password --gecos "" mkdocs

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier de configuration mkdocs.yml et le contenu du site dans le conteneur
COPY mkdocs.yml mkdocs.yml
COPY docs/ docs/

# Installer mkdocs et le thème mkdocs-material en tant qu'utilisateur mkdocs
RUN pip install --no-cache-dir mkdocs mkdocs-material

# Changer les permissions du dossier de travail pour l'utilisateur mkdocs
RUN chown -R mkdocs:mkdocs /app

# Passer à l'utilisateur mkdocs
USER mkdocs

# Exposer le port 8000
EXPOSE 8000

# Commande pour lancer le serveur MkDocs
CMD ["mkdocs", "serve", "-a", "0.0.0.0:8000"]
