# Programming with Data Practice
This repository contains a couple of Java projects developed between February and April of 2022. \
\
Each project is self-contained and includes its own README with usage instructions.

## Project Descriptions
### [DVLA Relational Database](DVLA_RELATIONAL_DATABASE_PRACTICE/README.md)
A relational database based on the one used by the DVLA to keep track of cars, their owners, tax, and MOT status.

### [String Search and Match](StringSearchAndMatch/README.md)
A program that performs a fuzzy string search on several files and prints matching results to standard output. 

## Table of Contents
- [Repository](#repository-structure)
- [Usage](#usage)

## Repository Structure
```
├── DVLA_RELATIONAL_DATABASE_PRACTICE
│   ├── DVLAERDiagram.png
│   ├── README.md
│   ├── data
│   │   ├── Cars.csv
│   │   ├── DVLA.db
│   │   ├── Owners.csv
│   │   ├── Registration.csv
│   │   ├── RelationalSchema.ddl
│   │   └── Tax.csv
│   └── src
│       ├── CSVReader.java
│       ├── CheckSQLiteJAR.java
│       ├── DBConfiguration.java
│       ├── InitialiseDB.java
│       ├── PopulateDB.java
│       └── Query.java
├── README.md
└── StringSearchAndMatch
    ├── CS1003P4.java
    ├── README.md
    └── data
```

## Usage
Navigate to the individual project directories and follow the instructions in their respective README.md files.