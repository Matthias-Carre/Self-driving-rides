## Projet d'optimisation d'attribution de courses

Ce projet implémente et compare différentes heuristiques pour résoudre un problème d'attribution de courses à des véhicules (similaire au problème "Self-Driving Rides" de Google Hash Code).
L'objectif est d'assigner une série de courses (rides) à une flotte de véhicules de manière à maximiser le score total, en tenant compte des points pour la course, des bonus de départ anticipé et des contraintes de temps.

## Tester les différents algorithmes

La classe `Instancemc.java` contient plusieurs méthodes, chacune représentant un algorithme ou une heuristique différente pour résoudre le problème.
Pour tester une nouvelle stratégie, il vous suffit de modifier **une seule ligne** dans le fichier `Main.java`.

### Comment faire :
1.  Ouvrez le fichier `Main.java`.
2.  Naviguez jusqu'à la méthode `runForFolder(String folderName, String outFile)`.
3.  Trouvez la ligne commentée `//fonction utiliser`.
4.  Modifiez l'appel de méthode sur la ligne suivante :

    ```java
    //fonction utiliser
    int res = inst.earlyStartGoalLS(); // <-- MODIFIEZ CETTE LIGNE
    ```

### 📜 Liste des algorithmes disponibles

Vous pouvez remplacer `inst.earlyStartGoalLS()` par l'un des appels de méthode suivants de la classe `Instancemc` :

* `inst.testGoalDy()`
* `inst.earlyStartGoalLS()`
* `inst.longestRidesLocalSearch()`
