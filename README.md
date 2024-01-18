# programming-with-data-practice-2022
Projects completed between February and April 2022

DVLA RELATIONAL DATABASE PRACTICE
  I designed and created a relational database based on the one used by the DVLA to keep track of cars, their owners, tax, and MOT status. To start, I created an ER diagram, then a relational schema, followed by 3 Java classes to implement the relational schema, populate the database (with faux data), and perform queries using JDBC.
  The database models:
    - Owners of cars & their addresses s.t. letters can be sent to them
    - the tax price band for taxing a vehicle either for 6 or 12 months
    - the class of each model of car indicating the manufacturer along with the co2 produced by each model
    - the taxed instances of cars along with their registration number, the color of the car, the model, when it was purchased (year and month, and the anniversary), the owner
    - a record of the MOT status of each car and the month and year the vehicle was last MOTed
  The program has the following built-in queries:
    1. List the registrations of cars owned by a specified person.
    2. Print the details of the person who owns a car with a specified registration number.
    3. List the MOT status and renewal dates of all cars owned by a specified person.
    4. List the car registrations, renewal dates, and road tax due for 6 and 12 months for all cars owned by a specified person
    5. List the registrations, owners name and address, co2 levels and the 6 and 12 months tax due for all owners of all vehicles for tax due in a given month
    6. List tax class, tax rate for 6 and 12 months, and owner first and last name given owner ID.

    STRING SEARCH AND MATCH
      A program that performs a fuzzy string search on several files and prints matching results to standard output.  The user must input the path to a directory, a search phrase, and a similarity threshold. The directory consisted of the most frequently downloaded eBooks. The resulting output of phrases from these books was meant to be matched to the given search phrase by first cleaning each series of words, then splitting them into bigrams, and finally calculating the Jaccard coefficient.
