# App-ADE

### Fonctionnalités

* Visialisation d'une page web ADE affichant les cours renseignés.
* Page de paramètres permettant d'entrer les codes de cours avec enregistrement automatique
* Toolbar permettant de rafraichir la page et d'acceder au paramètres.
* Parametre permettant de changer le code de l'année + lien vers explication
* Credit et licence
* Version anglaise et francaise avec choix enregistré

### Implémentation

* Activité de pure visualisation qui recupere une Url
* Objet singleton qui mémorise les cours et peux renvoyer soit une url soit une liste de codes
* Activité settings qui affiche les cours enregistrés en les recupérant dans le singleton et qui envoie une liste au singleton pour sauvegarder
