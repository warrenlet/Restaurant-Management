-- Restaurant customer management 1.1.3 
-- Name: Warren Huang
-- Course: CIS 98
-- Instructor: Lakisha Brooks 

-- Create a database
DROP DATABASE IF EXISTS restaurant;
CREATE DATABASE restaurant;
USE restaurant;

-- Tables Implementation, 11 tables in totals
CREATE TABLE reservations(
	createAt TIMESTAMP DEFAULT NOW(),
    resDate DATE NOT NULL,
    resTime TIME NOT NULL,
    partySize INT NOT NULL,
	RID INT PRIMARY KEY auto_increment
);


CREATE TABLE waitlists(
	WID INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    joinAt TIMESTAMP DEFAULT NOW(),
    partySize INT
);

CREATE TABLE customers(
	phoneN VARCHAR(12) PRIMARY KEY,
    firstName VARCHAR(20),
    lastName VARCHAR(20),
    RID INT,
    FOREIGN KEY(RID) REFERENCES reservations(RID),
    WID INT,
    children INT
);

CREATE TABLE rtables(
	TID INT PRIMARY KEY AUTO_INCREMENT,
    size INT NOT NULL
);

CREATE TABLE waiters(
	waiterID INT PRIMARY KEY auto_increment,
    firstName VARCHAR(20) NOT NULL,
    lastName VARCHAR(20) NOT NULL
);

CREATE TABLE orders(
	OID INT PRIMARY KEY auto_increment,
    total DECIMAL(8,2)
);

CREATE TABLE menu(
	FID INT PRIMARY KEY NOT NULL auto_increment,
    foodName VARCHAR(50),
    price DECIMAL(8,2)
);

CREATE TABLE orderItems(
	OID INT,
    FOREIGN KEY(OID) REFERENCES orders(OID),
    FID INT,
    FOREIGN KEY(FID) REFERENCES menu(FID),
    quantity INT
);

CREATE TABLE checkin(
	TID INT,
    FOREIGN KEY(TID) REFERENCES rtables(TID),
    phoneN VARCHAR(12),
    -- FOREIGN KEY(phoneN) REFERENCES customers(phoneN),
    createAT TIMESTAMP default NOW(),
    comment VARCHAR(20),
    waiterID INT,
    FOREIGN KEY(waiterID) REFERENCES waiters(waiterID),
    OID INT,
    FOREIGN KEY(OID) REFERENCES orders(OID)
);

CREATE TABLE rhistory(
	createAt TIMESTAMP DEFAULT NOW(),
	TID INT,
    WID INT,
    RID INT,
    phoneN VARCHAR(12),
    waiterID INT,
    OID int,
    firstName VARCHAR(20),
    lastName VARCHAR(20),
    children INT,
    updateAt TIMESTAMP DEFAULT NOW() ON UPDATE NOW()
    
);

INSERT INTO waiters
(firstName, lastName)
VALUES
	("Lisa", "Frank"),
    ("Laura", "Test"),
    ("Tom", "Mark");
    
INSERT INTO rtables
(size)
VALUES
(3),
(3),
(4),
(4),
(5),
(5);

INSERT INTO menu
	(foodname, price)
VALUES
("Pasta with Tomato and Basil",24.45),
("Bunny Chow",23.17),
("Fish and Chips",6.84),
("Poke",27.76),
("Sushi",13.35),
("Pizza",13.54),
("Cheeseburger",14.57),
("California Maki",13.13),
("Pasta Carbonara",6.5),
("Linguine with Clams",15.96),
("Fish and Chips",17.14),
("Sushi",7.65),
("Seafood Paella",12.76),
("Caprese Salad",10.28),
("Bruschette with Tomato",18.78),
("Stinky Tofu",29.17),
("Stinky Tofu",29.76),
("Vegetable Soup",10.88),
("Souvlaki",5.15),
("Chicken Parm",9.81);
	
	


/*
-- Test data insertion, uncomment to use
-- reservation table 
INSERT INTO reservations
	(resDate, resTime, partySize)
VALUES
	('2022-4-30', '120000', 5),
    ('2022-5-1', '153000', 2),
    ('2022-5-1', '153000', 5);


-- waitlist table 
INSERT INTO waitlists
	(partySIZE)
VALUES
	(5),
    (2),
    (3);
    
-- customers table
INSERT INTO customers
	(phoneN, firstName, lastName, RID, WID, children)
VALUES
    ('704-585-5880', 'Jenny', 'Washiton', 1, NULL, 2),
    ('802-878-5194', 'Chiman', 'Huang', NULL, 1, NULL),
    ('423-323-5208', 'Austin', 'Preece', 2, NULL, 1),
    ('859-667-0738', 'Damian ', 'Webb', 3, null, null),
    ('870-559-3580', 'Sahib ', 'Bowers', null, 2, null),
    ('601-680-5330', 'Arianna', 'Bob', null, 3, null);

-- rtables 
INSERT INTO rtables
	(size)
VALUES
	(5),
    (3),
    (3),
    (5),
    (6);
    
-- menu table
INSERT INTO menu
	(FID, foodName, price)
VALUES
	-- (001, 'Pizza', 10.99),
    (002, 'Taco', 5),
    (003, 'Noodles', 7);

-- waiter table
INSERT INTO waiters
	(waiterID, firstName, lastName)
VALUES
	(1, 'Jolyon', 'Keller'),
    (2, 'Darin ', 'Findlay');
*/
    

    


