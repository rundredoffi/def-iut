#!/bin/bash
set -e
cd ./backend || { echo "Erreur : le dossier ./backend est introuvable."; exit 1; }
mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.projectKey=rundredoffi_def-iut_1183c05f-13f3-4e26-9257-38bf3bc396a6 \
  -Dsonar.projectName='def-iut' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=sqp_c611f15414fefb52d39b36de42ebc7433b2aa6ce -DskipTests