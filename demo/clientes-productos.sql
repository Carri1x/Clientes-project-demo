DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS producto;
CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(15),
    fecha_nacimiento DATE
    CHECK (fecha_nacimiento <= CURRENT_DATE)
);

CREATE TABLE producto (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(10, 2) NOT NULL,
    stock INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cliente_producto (
    --Si se elimina un cliente, se eliminan sus relaciones con productos
    cliente_id INT REFERENCES cliente(id) ON DELETE CASCADE,
    --Si se elimina un producto, se eliminan sus relaciones con clientes
    --Por eso se inserta ON DELETE CASCADE para que se eliminen las relaciones
    producto_id INT REFERENCES producto(id) ON DELETE CASCADE,
    PRIMARY KEY (cliente_id, producto_id)
);

select * from cliente;
select * from producto;
select * from cliente_producto;

-- Insertar productos
INSERT INTO producto (nombre, descripcion, precio, stock) VALUES
('Portátil Lenovo', 'Portátil de 15 pulgadas', 799.99, 10),
('Smartphone Samsung', 'Galaxy S21', 599.99, 20),
('Auriculares Sony', 'Auriculares inalámbricos', 129.99, 30),
('Monitor LG', 'Monitor 24 pulgadas', 179.99, 15),
('Teclado Logitech', 'Teclado mecánico', 89.99, 25),
('Ratón HP', 'Ratón óptico', 19.99, 40),
('Tablet Apple', 'iPad 10.2"', 399.99, 12),
('Impresora Epson', 'Impresora multifunción', 149.99, 8),
('Disco SSD Kingston', 'SSD 1TB', 99.99, 18),
('Altavoz JBL', 'Altavoz Bluetooth', 59.99, 22);

-- Insertar clientes
INSERT INTO cliente (nombre, apellido, email, telefono, fecha_nacimiento) VALUES
('Ana', 'García', 'ana.garcia@email.com', '600111222', '1990-03-15'),
('Luis', 'Martínez', 'luis.martinez@email.com', '600222333', '1985-07-22'),
('María', 'López', 'maria.lopez@email.com', '600333444', '1992-11-05'),
('Pedro', 'Sánchez', 'pedro.sanchez@email.com', '600444555', '1988-01-30'),
('Lucía', 'Fernández', 'lucia.fernandez@email.com', '600555666', '1995-09-12'),
('Javier', 'Ruiz', 'javier.ruiz@email.com', '600666777', '1983-06-18'),
('Carmen', 'Jiménez', 'carmen.jimenez@email.com', '600777888', '1991-12-25'),
('David', 'Moreno', 'david.moreno@email.com', '600888999', '1987-04-09'),
('Sara', 'Muñoz', 'sara.munoz@email.com', '600999000', '1993-08-14'),
('Miguel', 'Álvarez', 'miguel.alvarez@email.com', '601000111', '1989-02-27'),
('Elena', 'Romero', 'elena.romero@email.com', '601111222', '1996-10-03'),
('Raúl', 'Navarro', 'raul.navarro@email.com', '601222333', '1984-05-19'),
('Patricia', 'Torres', 'patricia.torres@email.com', '601333444', '1990-12-11'),
('Alberto', 'Domínguez', 'alberto.dominguez@email.com', '601444555', '1986-03-07'),
('Marta', 'Vázquez', 'marta.vazquez@email.com', '601555666', '1994-07-28'),
('Sergio', 'Ramos', 'sergio.ramos@email.com', '601666777', '1982-09-16'),
('Paula', 'Castro', 'paula.castro@email.com', '601777888', '1997-01-21'),
('Diego', 'Ortega', 'diego.ortega@email.com', '601888999', '1991-05-02'),
('Natalia', 'Rubio', 'natalia.rubio@email.com', '601999000', '1993-11-13'),
('Jorge', 'Molina', 'jorge.molina@email.com', '602000111', '1985-08-24');

-- Relacionar clientes con productos (cliente_producto)
-- (Clientes 1-15 tienen productos, 16-20 no tienen productos)
INSERT INTO cliente_producto (cliente_id, producto_id) VALUES
(1, 1), (1, 2),
(2, 3),
(3, 1), (3, 4), (3, 5),
(4, 2),
(5, 6), (5, 7),
(6, 8),
(7, 9), (7, 10),
(8, 1), (8, 3),
(9, 2), (9, 4), (9, 6),
(10, 5),
(11, 7), (11, 8),
(12, 9),
(13, 10), (13, 1),
(14, 2), (14, 3),
(15, 4), (15, 5);

-- Clientes 16 a 20 no tienen productos asociados