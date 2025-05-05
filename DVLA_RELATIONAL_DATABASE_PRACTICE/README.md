# DVLA Relational Database
  The database models: \
   &emsp; - Owners of cars & their addresses s.t. letters can be sent to them \
    &emsp;- the tax price band for taxing a vehicle either for 6 or 12 months \
   &emsp; - the class of each model of car indicating the manufacturer along with the co2 produced by each model \
  &emsp;  - the taxed instances of cars along with their registration number, the color of the car, the model, when it was purchased &(year and month, and the anniversary), the owner \
 &emsp;   - a record of the MOT status of each car and the month and year the vehicle was last MOTed \
  The program has the following built-in queries: \
   &emsp; 1. List the registrations of cars owned by a specified person. \
   &emsp; 2. Print the details of the person who owns a car with a specified registration number. \
  &emsp;  3. List the MOT status and renewal dates of all cars owned by a specified person. \
  &emsp;  4. List the car registrations, renewal dates, and road tax due for 6 and 12 months for all cars owned by a specified person \
   &emsp; 5. List the registrations, owners name and address, co2 levels and the 6 and 12 months tax due for all owners of all vehicles for tax due in a given month. \
   &emsp; 6. List tax class, tax rate for 6 and 12 months, and owner first and last name given owner ID.

## Repository Structure
```
├── DVLAERDiagram.png
├── README.md
├── data
│   ├── Cars.csv
│   ├── DVLA.db
│   ├── Owners.csv
│   ├── Registration.csv
│   ├── RelationalSchema.ddl
│   └── Tax.csv
└── src
    ├── CSVReader.java
    ├── CheckSQLiteJAR.java
    ├── DBConfiguration.java
    ├── InitialiseDB.java
    ├── PopulateDB.java
    └── Query.java
```

## Usage
1. Add the sqlite jsbc jar to the classpath:
    ```bash
    $ export CLASSPATH=${CLASSPATH}:sqlite-jdbc-3.8.7.jar
    ```
2. To run class to check the jar is properly loaded:
    ```bash
    $ java CheckSQLiteJAR
    ```
3. To inititialise the database:
    ```bash
    $ java InitialiseDB
    ```
4. To populate the database:
    ```bash
    $ java populateDB
    ```
5. To run a query:
    ```bash
    $ java QueryDB arg1 arg2
        arg1 = a query number (0-5)
        arg2 = a parameter for the query (multiple word parameter should be between quotes)
                for queries 0, 2, and 3 the parameter should be an owner's first and last lastName
                for query 1 the parameter should be a reference number
                for query 4 the parameter should be the renewal date
                for query 5 the parameter should be the owner id 
    ```
