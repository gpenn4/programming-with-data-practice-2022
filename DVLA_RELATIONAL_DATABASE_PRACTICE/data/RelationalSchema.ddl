
DROP TABLE IF EXISTS 'Tax';

CREATE TABLE 'Tax'('taxClass' VARCHAR(4) PRIMARY KEY NOT NULL,
'taxRate6Months' REAL,
'taxRate12Months' REAL);


DROP TABLE IF EXISTS 'Cars';

CREATE TABLE 'Cars'( 'Make' VARCHAR(20) PRIMARY KEY NOT NULL,
'COIntake' REAL,
'Manufacturer' VARCHAR(50),
'taxClass' REFERENCES 'Tax' ('taxClass') ON DELETE CASCADE);


DROP TABLE IF EXISTS 'Owner';

CREATE TABLE 'Owner'( 'ownerID' REAL PRIMARY KEY NOT NULL,
'lastName' VARCHAR(30),
'firstName' VARCHAR(30),
'address' VARCHAR(100) );


DROP TABLE IF EXISTS 'Registration'; 

CREATE TABLE 'Registration'('referenceNum' REAL PRIMARY KEY NOT NULL,
'color' VARCHAR(20),
'purchaseDate' VARCHAR(10),
'MOTStatus' BOOLEAN,
'dateLastMOTed' VARCHAR(10),
'renewalDate' VARCHAR(10),
'make' REFERENCES 'Cars' ('make') ON DELETE CASCADE,
'ownerID' REFERENCES 'Owner' ('ownerID')  ON DELETE CASCADE);