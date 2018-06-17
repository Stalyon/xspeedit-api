XspeedIt
========


Contexte
--------
XspeedIt est une société d'import / export ayant robotisé toute sa chaîne d'emballage de colis.
Elle souhaite trouver un algorithme permettant à ses robots d'optimiser le nombre de cartons d'emballage utilisés.

Les articles à emballer sont de taille variable, représentée par un entier compris entre 1 et 9.
Chaque carton a une capacité de contenance de 10.
Ainsi, un carton peut par exemple contenir un article de taille 3, un article de taille 1, et un article de taille 6.

La chaîne d'articles à emballer est représentée par une suite de chiffres, chacun représentant un article par sa taille.
Après traitement par le robot d'emballage, la chaîne est séparée par des "/" pour représenter les articles contenus dans un carton.

*Exemple*  
```python
Chaîne d'articles en entrée : 163841689525773  
Chaîne d'articles emballés  : 163/8/41/6/8/9/52/5/7/73
```

L'algorithme actuel du robot d'emballage est très basique.
Il prend les articles les uns après les autres, et les mets dans un carton.
Si la taille totale dépasse la contenance du carton, le robot met l'article dans le carton suivant.


Objectif
--------
Implémenter une application qui permettrait de maximiser le nombre d'articles par carton, en utilisant un langage pouvant être exécuté sur une JVM 1.7 minimum ou en node.js.
L'ordre des cartons et des articles n'a pas d'importance.

*Exemple*  
```python
Articles      : 163841689525773  
Robot actuel  : 163/8/41/6/8/9/52/5/7/73 => 10 cartons utilisés  
Robot optimisé: 163/82/46/19/8/55/73/7   => 8  cartons utilisés
```


Réalisation
-----------
Afin de répondre à la demande de la société XspeedIt, les sources contenant le projet Maven "xspeedit-api" ont été livrées.
Celles-ci permettent le déploiement d'une API répondant aux attentes de l'entreprise.

Après différents ateliers fonctionnels, il a été entendu avec XspeedIt le développement :
- du calculateur actuel (correspondant à leur existant)
- d'un calculateur optimisé
- d'un comparateur entre ces deux calculateurs

Lors de ces échanges, il a été mis en avant le fait que certaines conditions de traitement pouvaient être amenées à changer.
Ainsi, la possibilité de changer les options suivantes a été mise en place :
- le délimiteur entre les articles (initialement chaque article correspondant à un chiffre)
- la capacité des cartons (initialement 10)
- le délimiteur entre les cartons (initialement le caractère /)

Afin de pouvoir tester rapidement les différentes fonctionnalités avec les conditions exprimées initialement, la société a souhaité différencié les fonctionnalités sans condition des fonctionnalités génériques.
Les 6 endpoints suivants ont donc été mis en place :
- /api/calculator/actual/organizeTheBoxes
- /api/calculator/actual/organizeTheBoxesGeneric
- /api/calculator/optimized/organizeTheBoxes
- /api/calculator/optimized/organizeTheBoxesGeneric
- /api/calculator/comparator/compare
- /api/calculator/comparator/compareGeneric

Enfin, il a été convenu avec XspeedIt le déploiement d'une interface permettant la documentation et le test de ces différents endpoints.
Cette interface est disponible via l'URL suivante : http://localhost:8080/swagger-ui.html#/


Déploiement
-----------
Les commandes qui suivent explicitent les différentes façons de lancer l'application.

*Déploiement avec le plugin Maven Spring Boot :*
```python
mvn spring-boot:run
```

*Déploiement en tant qu'application packagée (après compilation) :*
```python
mvn clean install
java -jar target/xspeedit-api-0.0.1-SNAPSHOT.jar
```


Notes techniques
----------------
##### Choix techniques
Afin de réaliser une API facilement déployable, le framework Spring Boot a été choisi.
La dépendance ```spring-boot-starter-web```  met en place une stack technique permettant l'implémentation d'une API de façon rapide et simple.
Afin de générer une documentation de cette API, la librairie Swagger a été utilisée. Elle permet également de pouvoir tester chaque endpoint.
Enfin, les tests ont été écrits via l'utilisation de la dépendance ```spring-boot-starter-test```. Celle-ci permet notamment l'utilisation, sans configuration supplémentaire, des librairies JUnit et Mockito.

##### Architecture générale
Les différentes couches applicatives sont réparties dans les packages suivants :
- configuration : regroupe toutes les configurations liées à l'application (ici, il n'y a que la config liée à Swagger)
- controller : regroupe les différents controllers mis en place par l'API (tous les endpoints sont ici)
- entity : regroupe les entités de l'application (ici, il n'y a que la classe CalculatorComparator qui est utilisée pour le récapitulatif du comparateur)
- exception : regroupe les exceptions de l'application (ici, une seule exception globale : XspeedItException)
- service : regroupe les servies (interfaces et implémentations) de l'application
- utils : regroupe les constantes et autres méthodes statiques (sans état)

##### Architecture détaillée concernant le calculateur optimisé
Pour calculer la répartition idéale d'articles dans des cartons dans le but de les remplir au maximum, l'algorithme qui a été implémentée est le suivant :
1. tri des carticles suivant leur contenance (du plus gros article au plus petit)
2. ajout des articles dans un carton en commençant par le plus gros, jusqu'à :
- remplir ce carton
- ou qu'il n'y ait plus d'articles plus petit pouvant être ajouté
3. et ainsi de suite jusqu'à ce qu'il n'y ait plus d'articles

Afin d'optimiser le temps de traitement, les articles sont initialisés dans une Map avec pour clé leur contenance et pour valeur leur récurrence.
Par exemple, la liste d'article 58744818 sera triée et répartie de cette façon :
- clé : 8, valeur : 3
- clé : 7, valeur : 1
- clé : 5, valeur : 1
- clé : 4, valeur : 2
- clé : 1, valeur : 1

A chaque fois qu'un article est ajouté dans un carton, la récurrence est donc décrémentée. 
Lorsque la récurrence vaut 0, tous les articles d'une même contenance ont donc été ajoutés.


##### Tests mis en place
La politique de couverture de tests émise par la société XspeedIt était très rigoureuse.
Ainsi, 100% des lignes de code ont été couvertes (cf. dossier coverage : ouvrir le fichier index.html dans un navigateur).
Note : les packages entity et exception ne présentant aucun intérêt à être couverts, ils ne sont pas représentés dans cette couverture.

Chaque service possède sa classe de tests asssociée. Par exemple, les tests de CalculatorComparatorService sont définis dans la classe de tests CalculatorComparatorServiceTU.
Il en est de même pour les packages service et utils.

Tous les tests mis en place sont des tests de type unitaire. Ainsi, les appels vers d'autres services sont "mockés" (bouchonnés) via la librairie Mockito.
