# Note — Couche Repository (Atelier 3)

## Choix d'interface

Toutes les interfaces étendent `JpaRepository<Entité, Long>` : CRUD complet, `findAll` renvoie une `List` (et non un `Iterable`), tri et pagination, et `saveAndFlush` / `flush` disponibles. `Repository` et `CrudRepository` seraient trop limités pour AutoLoc, et `PagingAndSortingRepository` seul n'a plus le CRUD depuis Spring Data 3.

| Interface | Étend | Justification |
|---|---|---|
| `IAgenceRepository` | `JpaRepository<Agence, Long>` | CRUD complet, listes des agences, tri par ville possible. |
| `IEmployeRepository` | `JpaRepository<Employe, Long>` | CRUD complet ; `findAll` renvoie une `List` facile à manipuler. |
| `IVehiculeRepository` | `JpaRepository<Vehicule, Long>` | CRUD, tri par tarif journalier et pagination du catalogue de véhicules. |
| `IEquipementRepository` | `JpaRepository<Equipement, Long>` | CRUD complet sur une entité simple. |
| `IClientRepository` | `JpaRepository<Client, Long>` | CRUD, pagination de la liste des clients. |
| `IReservationRepository` | `JpaRepository<Reservation, Long>` | CRUD, tri par date de début. |
| `IContratRepository` | `JpaRepository<Contrat, Long>` | CRUD complet, `findAll` renvoie une `List`, `saveAndFlush` disponible. Les suppressions via `deleteById` passent par le contexte de persistance : la cascade et l'`orphanRemoval` s'appliquent. |
| `IPaiementRepository` | `JpaRepository<Paiement, Long>` | Créé pour pouvoir **lire** les paiements. Le code métier passe par le Contrat pour créer ou retirer un paiement. |
| `IMaintenanceRepository` | `JpaRepository<Maintenance, Long>` | CRUD, tri par date de début. |

**Limite à retenir :** `deleteAllInBatch` et `deleteAllByIdInBatch` contournent le contexte de persistance. La cascade et l'`orphanRemoval` ne s'appliquent pas, donc supprimer ainsi des contrats laisserait des paiements orphelins ou ferait échouer la requête sur la clé étrangère. On utilise `deleteById` / `delete`.

Aucune classe d'implémentation n'est écrite : Spring Data génère un proxy à l'exécution, et `@Repository` n'est pas nécessaire sur une interface.

## Anomalies SonarQube for IDE

> À confirmer avec **ton** panneau SonarQube (numéro de règle et texte exact) après « Analyze with SonarQube for IDE ». Ajoute aussi celles que tu relèves toi-même.

| Anomalie SonarQube for IDE | Règle / explication | Correction apportée |
|---|---|---|
| Import générique `import jakarta.persistence.*;` dans les entités | Règle S2208 : les imports avec `*` cachent d'où viennent les classes et peuvent créer des conflits de noms. | Remplacés par des imports explicites (`Entity`, `Table`, `Id`, `GeneratedValue`, `GenerationType`, `Column`, `Enumerated`, `EnumType`, `ManyToOne`, `OneToMany`, `ManyToMany`, `OneToOne`, `JoinColumn`, `JoinTable`, `FetchType`, `CascadeType` selon l'entité). |
| *(à compléter chez toi)* | | |
| *(à compléter chez toi)* | | |
