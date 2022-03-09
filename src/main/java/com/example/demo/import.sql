/* Populate tables */
INSERT INTO clients(id, create_at, email, first_name, last_name) VALUES (1, '2022-03-09', 'sylvicruza@gmail.com', 'Sylvester', 'Ofueu');

/* Populate table products */
INSERT INTO products (id, name, price, create_at) VALUES(1, 'Panasonic LCD', 259990, NOW());
INSERT INTO products (id, name, price, create_at) VALUES(2, 'Sony Camera DSC-W320B', 1234590, NOW());
INSERT INTO products (id, name, price, create_at) VALUES(3, 'Apple iPod', 1499990, NOW());
INSERT INTO products (id, name, price, create_at) VALUES(4, 'Sony Notebook Z110', 37990, NOW());
INSERT INTO products (id, name, price, create_at) VALUES(5, 'HP F2280 MultiF', 69990, NOW());
INSERT INTO products (id, name, price, create_at) VALUES(6, 'Bike 26 BMW', 69990, NOW());
INSERT INTO products (id, name, price, create_at) VALUES(7, 'Keyboard Razer CLGv', 299990, NOW());
INSERT INTO products (id, name, price, create_at) VALUES(8, 'Mouse Razer Deathadder', 299650, NOW());

/* Invoices */
INSERT INTO invoices(id, description, observation, client_id, create_at) VALUES(1, 'Invoice office team', null, 1, NOW());
INSERT INTO items_invoices(amount, invoice_id, product_id) VALUES(1, 1, 1); 
INSERT INTO items_invoices(amount, invoice_id, product_id) VALUES(2, 1, 4); 
INSERT INTO items_invoices(amount, invoice_id, product_id) VALUES(1, 1, 5); 
INSERT INTO items_invoices(amount, invoice_id, product_id) VALUES(1, 1, 7); 

INSERT INTO invoices(id, description, observation, client_id, create_at) VALUES(2, 'Bike Invoice', 'IMPORTANT STUFF', 1, NOW());
INSERT INTO items_invoices(amount, invoice_id, product_id) VALUES(3, 2, 6); 


