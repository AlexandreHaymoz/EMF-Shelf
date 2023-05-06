
## Diagramme global

![](/assets/images/architecture_globale.png)

## Description
Ce repository contient le projet du module 133 qui porte sur une librarie. Il y a 5 projets :
##### clientADMIN :
Contient un client HTML & Javascript qui visiter la librarie et la gérer.
##### clientUSER :
Contient un client HTML & Javascript qui visiter la librarie et gérer son propre compte et réservations.
##### compteREST :
Contient un projet REST en Java qui permet de traiter les requêtes relatif aux comptes des utilisateurs. 
##### gatewayAPI :
Contient un projet WEBAPP en Java qui permet de faire office de douane entre les clients et les serveurs REST.
##### livreREST :
Contient un projet REST en Java qui permet de traiter les requêtes relatif aux livres et aux réservations des utilisateurs. 

Un dossier est dédié aux documents externes tel que le planning ou le fichier UML se nomme **assets** et se trouve à la racine de ce repository. 

## Live demo

Livres REST : https://clapassonn.emf-informatique.ch/javaLivreREST/bibliotheque/livres
Reservation REST : https://clapassonn.emf-informatique.ch/javaLivreREST/bibliotheque/reservations
Comptes REST : https://haymozn.emf-informatique.ch/java_compteREST/bibliotheque/utilisateurs

Certains des clients ne peuvent pas fonctionner à 100% en raison de CORS qui bloquent les requêtes PUT et DELETE. La seule solution de contournement que nous avons trouvée consiste à désactiver les CORS en utilisant la commande suivante :
```
google --disable-web-security --user-data-dir="[some directory here]"
```
Client ADMIN : http://haymozn.emf-informatique.ch/clientADMIN/

Client USER :  http://haymozn.emf-informatique.ch/clientUSER/

Une gateway spéciale a été mise en place sous un VPS via Docker (Certifié avec Certbot) sous l'URL suivante : https://quoinkk.dedyn.io/gatewayAPI/