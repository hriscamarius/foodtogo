REPLACE INTO `users` VALUES (1,'admin@admin.ro', '$2a$10$w/gBPXUm3iAiV0Q0s2dbPeuZso72Pby8MTpUWPO7RI1p4aAg4bBLC', 'admin');
REPLACE INTO `users_roles` VALUES (1,1);
REPLACE INTO `roles` VALUES (1,'ROLE_ADMIN');
REPLACE INTO `roles` VALUES (2,'ROLE_VENDOR');
REPLACE INTO `roles` VALUES (3,'ROLE_CUSTOMER');

REPLACE INTO `users` VALUES ('2', 'customer1@customer1.ro', '$2a$10$8wtz7fXlKWkEDs4Zw/Te7eOZ.V1sjQSX0K097FdirfYs2I5zjkso.', 'customer1');
REPLACE INTO `customers` VALUES ('Customer1 Address', '1988-02-09 00:00:00', 'Customer1 Name', '0741234567', '2');
REPLACE INTO `users_roles` VALUES (2, 3);
REPLACE INTO `users` VALUES ('3', 'customer2@customer2.ro', '$2a$10$8wtz7fXlKWkEDs4Zw/Te7eOZ.V1sjQSX0K097FdirfYs2I5zjkso.', 'customer2');
REPLACE INTO `customers` VALUES ('Customer2 Address', '1988-02-09 00:00:00', 'Customer2 Name', '0741234567', '3');
REPLACE INTO `users_roles` VALUES (3, 3);

REPLACE INTO `users` VALUES ('4', 'vendor1@vendor1.ro', '$2a$10$8wtz7fXlKWkEDs4Zw/Te7eOZ.V1sjQSX0K097FdirfYs2I5zjkso.', 'vendor1');
REPLACE INTO `vendors` VALUES ('Vendor1 Address', '0741454545', 'Vendor1 Restaurant', 'Mixt', '4');
REPLACE INTO `users_roles` VALUES (4, 2);
REPLACE INTO `users` VALUES ('5', 'vendor2@vendor2.ro', '$2a$10$8wtz7fXlKWkEDs4Zw/Te7eOZ.V1sjQSX0K097FdirfYs2I5zjkso.', 'vendor2');
REPLACE INTO `vendors` VALUES ('Vendor2 Address', '0741454545', 'Vendor2 Restaurant', 'Mixt', '5');
REPLACE INTO `users_roles` VALUES (5, 2);

REPLACE INTO `products` VALUES ('1', 'Pizza', '2020-04-28 11:36:22', 'ardei gras, ciuperci, salam vara, sunca de porc, mix branzeturi, sos, aluat', 'Quattro Stagioni', '42');
REPLACE INTO `vendors_products` VALUES (4, 1);
REPLACE INTO `products` VALUES ('2', 'Salate', '2020-04-28 11:36:22', 'ardei gras, capere, castraveti verzi, ceapa rosie, branza feta, oregano, rosii cherry, ulei masline, masline verzi', 'Salata Greceasca', '15');
REPLACE INTO `vendors_products` VALUES (5, 2);
REPLACE INTO `products` VALUES ('3', 'Desert', '2020-04-28 11:36:22', 'clatite, crema de ciocolata, topping', 'Clatite Cu Ciocolata', '13');
REPLACE INTO `vendors_products` VALUES (4, 3);
REPLACE INTO `products` VALUES ('4', 'Pizza', '2020-04-28 11:36:22', 'piept de pui, cascaval + mozzarella, sos, rosii bruschette, aluat, masline kalamata', 'Polo', '45');
REPLACE INTO `vendors_products` VALUES (5, 4);
REPLACE INTO `products` VALUES ('5', 'Paste', '2020-04-28 11:36:22', 'spaghete, carne tocata amestec vita-porc, rosii pasate, 3 linguri ulei de mssline, cimbru, sare, suc de rosii, parmezan, 1 catel usturoi, 2 morcovi, piper, ', 'Bolognese', '43');
REPLACE INTO `vendors_products` VALUES (5, 5);
