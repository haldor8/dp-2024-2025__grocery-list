# Gestion du projet GroceryManager

## Difficultés rencontrées
- Format de sauvegarde incorrect (JSON enregistré comme une liste au lieu d'un dictionnaire).
- Gestion du nom de fichier pour garantir l’extension correcte en sortie.
- Difficultés à structurer la classe pour séparer le parsing CLI et la gestion des commandes et l'IO.

## Succès
- Implémentation correcte de la lecture et écriture en JSON et CSV.
- Génération automatique du fichier de sortie avec l’extension correspondant au format passé en argument.
- Séparation claire des responsabilités entre le parsing CLI dans le main et la gestion des commandes dans le GroceryManager.

## Échecs et ajustements
- Mauvaise manipulation initiale des structures de données lors du parsing JSON.
- Besoin d’adapter le code pour éviter d’écraser le fichier d’entrée avec un format incorrect.