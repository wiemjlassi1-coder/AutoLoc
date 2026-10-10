# Stratégie de fetch et de cascade — AutoLoc

| Association | Fetch | Cascade | Justification |
|---|---|---|---|
| Contrat → Paiement | LAZY | ALL + orphanRemoval | Un paiement n'existe que rattaché à son contrat (composition) : supprimer le contrat supprime ses paiements, retirer un paiement de la liste le supprime en base. |
| Agence → Vehicule | LAZY | Aucune | Un véhicule survit à la suppression de son agence ; la liste des véhicules n'est chargée qu'à la demande. |
| Agence → Employe | LAZY | Aucune | Un employé a une existence propre ; pas de suppression en cascade depuis l'agence. |
| Vehicule ↔ Equipement | LAZY | Aucune | Les équipements sont partagés entre véhicules : supprimer un véhicule ne doit pas supprimer un équipement utilisé ailleurs. Set pour éviter les doublons. |
| Client → Reservation | LAZY | PERSIST | Une réservation créée via le client est enregistrée avec lui, mais l'historique ne doit pas disparaître à la suppression du client. |
| Reservation → Vehicule | LAZY | Aucune | Le véhicule existe indépendamment de ses réservations. |
| Reservation ↔ Contrat | LAZY | ALL (côté Reservation) | Le contrat découle de la réservation : son cycle de vie suit celui de la réservation. Côté inverse d'un OneToOne, Hibernate charge en pratique le contrat sans bytecode enhancement. |
| Vehicule → Maintenance | LAZY | PERSIST | Une maintenance enregistrée avec le véhicule, mais conservée comme historique si le véhicule est modifié. |

**Choix général :** tout est en `LAZY` pour ne charger que ce qui est utilisé et éviter les chaînes de chargement inutiles (le `@ManyToOne` est EAGER par défaut, on le force donc en LAZY).
