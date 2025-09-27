# Utiliser une image Python
FROM python:3.8-slim

# Installer les dépendances
RUN pip install mysql-connector-python

# Copier le script Python dans l'image
COPY check_max_connections.py /usr/src/app/check_max_connections.py

# Définir le point d'entrée pour exécuter le script
CMD ["python", "/usr/src/app/check_max_connections.py"]
