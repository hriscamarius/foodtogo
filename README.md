# foodtogo
FoodToGO Application / Spring Boot + Data JPA + Hibernate + Security + Web + MySQL + Thymeleaf

Utilizatori/parola: admin/admin, vendor1/123, vendor2/123, customer1/123, customer1/123

MySQL: CREATE SCHEMA \`foodtogo\`;
	connection user/pass: root/admin

Construiți o aplicație pentru plasare de comenzi de produse din restaurante.

Funcționalități:

Register
	Un utilizator se poate înregistra ca vendor sau cumpărător.
	Există un utilizator ADMIN cu drepturi depline. De exemplu poate să șteargă un 
restaurant, un user, etc.

	Informațiile de bază pentru înregistrarea oricărui tip de utilizator sunt:
Adresa de e-mail
Nume de utilizator
Parolă
	În plus, pentru înregistrare vendorului i se vor mai cere și informații precum:
Numele restaurantului
Specificul restaurantului
Adresa la care se află restaurantul
Numar de telefon
	În plus, pentru înregistrare cumpărătorului i se vor mai cere și informații precum:
Numar de telefon
Nume
Prenume
Data nașterii
Adresa cumpărătorului

Login
	Un utilizator se poate autentifica în aplicație cu numele de utilizator/ e-mail și         
Parola.

Afișarea profilului unui utilizator
	Profilul unui utilizator poate fi văzut doar dacă utilizatorul este autentificat, iar profilul 	unui utilizator poate fi văzut doar de acel utilizator.

	Profilul unui vendor va conține informații despre acel restaurant, iar profilul unui 
cumpărător va conține informații despre cumpărător.

Produse
Vendorul poate crea produse, poate șterge, edita și vedea produsele pe care le 
deține.
	Produsele au următoarele caracteristici:
Numele produsului
Tipul produsului
Prețul produsului
Ingrediente
	Cumpărătorul poate vedea toate produsele de la toți vendorii.



Comenzi
Un cumpărător poate plasa comenzi. O comandă poate conține mai multe produse de la același vendor.
Comenzile au un status care poate fi:
Pending - după ce a fost plasată o comanda
In progress - după ce vendorul a acceptat comanda
Delivered - după ce vendorul a confirmat livrarea comenzii

Funcționalități pentru bonus:

Logout

Statistici pentru utilizatori

