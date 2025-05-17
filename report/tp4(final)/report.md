# Rapport Final

## Ce que nous n'avons pas eu le temps de finaliser

Nous n’avons pas pu utiliser notre fonction de listing des objets dans l’interface web, car une structure de données différente a été utilisée (`WebGroceryItem` au lieu de notre structure `Item`). Cette différence a empêché une intégration directe, et par manque de temps, nous n’avons pas pu adapter les structures.

---
## Ce qui était difficile

La commande `info` a bien été implémentée, mais d’une manière un peu maladroite, sans utiliser un design pattern adapté.

---
## Quels design patterns avons-nous utilisés et pourquoi ?
- **Command** : Utilisé pour toutes les commandes (`add`, `remove`, `list`, `info`, `web`). Chaque commande est une classe séparée, ce qui rend le code plus organisé et facile à étendre.
- **Factory** : Utilisé dans `DAOFactory` pour créer automatiquement le bon type de DAO en fonction du format de fichier (JSON, CSV, etc.).
- **DAO** : Permet de séparer la logique métier de l’accès aux fichiers. C’est plus clair et plus facile à maintenir.
---
## Réponses aux questions
### Comment ajouter une nouvelle commande (en théorie, sans code) ?
1. Créer une nouvelle classe qui représente la commande.
2. Ajouter un cas dans la méthode `handleCommand` pour gérer cette commande.
3. Implémenter ce que la commande fait dans la méthode `execute`.
4. Si la commande prend des arguments, les ajouter dans l’analyseur CLI.
---
### Comment ajouter une nouvelle source de données (en théorie, sans code) ?
1. Créer une classe qui implémente l’interface `GroceryDAO`.
2. Ajouter le code pour charger et sauvegarder les données avec ce nouveau format.
3. Mettre à jour `DAOFactory` pour reconnaître ce nouveau type de source.
4. Adapter l’outil `FileNameFormatter` si besoin.
---
### Que changer pour pouvoir ajouter un magasin quand on ajoute une course (en théorie, sans code) ?
1. Ajouter un champ `magasin` dans la classe `Item`.
2. Mettre à jour les DAO pour gérer ce nouveau champ.
3. Modifier `AddItemCommand` pour inclure ce champ.
4. Adapter le CLI pour pouvoir saisir le nom du magasin.
5. Si une interface web est utilisée, l’adapter aussi pour afficher et gérer le magasin.
---
