//Clientes

INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (1, 'Javier', 'Moncada', 'javier@ufps.edu.co','2021-01-13', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (2, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (3, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (4, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (5, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (6, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (7, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (8, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (9, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (10, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (11, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (12, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (13, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (14, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (15, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (16, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (17, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (18, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (19, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (20, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (21, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (22, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (23, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (24, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (25, 'Camilo', 'Boada', 'Camilo@ufps.edu.co','2021-01-12', '');

//Productos

INSERT INTO productos (create_at, nombre, precio) VALUES(NOW(), 'Pantalla LCD', 12000);
INSERT INTO productos (create_at, nombre, precio) VALUES(NOW(), 'CPU RYZEN', 12000);
INSERT INTO productos (create_at, nombre, precio) VALUES(NOW(), 'Placa Aorus', 12000);
INSERT INTO productos (create_at, nombre, precio) VALUES(NOW(), 'Torre 2', 12000);
INSERT INTO productos (create_at, nombre, precio) VALUES(NOW(), 'Mouse IN.', 12000);
INSERT INTO productos (create_at, nombre, precio) VALUES(NOW(), 'Teclado', 12000);

//Facturas
INSERT INTO facturas (create_at, descripcion, observacion, cliente_id) VALUES (NOW(), 'Factura---1', 'Esta es una factura', 1);
INSERT INTO facturas_items (cantidad, producto_id, factura_id) VALUES (2, 1, 1);
INSERT INTO facturas_items (cantidad, producto_id, factura_id) VALUES (3, 2, 1);
INSERT INTO facturas_items (cantidad, producto_id, factura_id) VALUES (1, 3, 1);

//Usuarios
INSERT INTO users (username, password, enable) VALUES ('andres', '$2a$10$vvsE4xVYUD2i7B68IoJo/ur2lwY0K2bApYd/JbzoWO22pjYHAZ9QC', 1);
INSERT INTO users (username, password, enable) VALUES ('admin', '$2a$10$id9q9DdjdG3LMsJHL4LClORqz9P2J8a2GmX2n9c3qbBnJzsJehpjW', 1);


//ROLES
INSERT INTO authorities (user_id, authority) VALUES (1,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2,'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES (2,'ROLE_USER');
