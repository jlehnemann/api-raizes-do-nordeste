INSERT INTO unit_tb (id, name, city, state, active)
VALUES
    (nextval('unit_seq'), 'Raízes do Nordeste Recife Matriz', 'Recife', 'PE', true);

INSERT INTO user_tb (id, email, password, role, active, created_at)
VALUES (
           nextval('user_seq'),
           'joaoadmin@raizesdonordeste.com.br',
           '$2a$10$N0r91zlgt3Ct14asBPajD.rGH2.t1rG8ADJTp6HnxgLVdTYMFzg/q',
           'ADMIN',
           true,
           now()
       );

INSERT INTO employee_tb (id, name, telephone, address, lgpd_consent, lgpd_consent_date, created_at, unit_id, user_id)
VALUES (
           nextval('employee_seq'),
           'João Administrador',
           '(81) 99999-9999',
           'Rua dos Administradores, 150 - Recife - PE',
           true,
           now(),
           now(),
           1,
           1
       );

INSERT INTO product_tb (id, name, unit_price, active)
VALUES
    (nextval('product_seq'), 'Bolo de Rolo', 24.90, true),
    (nextval('product_seq'), 'Rapadura', 15.90, true),
    (nextval('product_seq'), 'Queijo Coalho', 12.90, true),
    (nextval('product_seq'), 'Suco de Caju', 8.90, true),
    (nextval('product_seq'), 'Água Mineral', 4.90, true);


INSERT INTO stock_tb (id, unit_id)
VALUES
    (nextval('stock_seq'), 1);

INSERT INTO stock_item_tb (id, name, quantity, product_id, stock_id)
VALUES
    (nextval('stock_item_seq'), 'Bolo de Rolo', 50, 1, 1),
    (nextval('stock_item_seq'), 'Rapadura', 50, 2, 1),
    (nextval('stock_item_seq'), 'Queijo Coalho', 40, 3, 1),
    (nextval('stock_item_seq'), 'Suco de Caju', 50, 4, 1),
    (nextval('stock_item_seq'), 'Água Mineral', 100, 5, 1);



