# String Search and Match
The user must input the path to a directory, a search phrase, and a similarity threshold. The directory consists of frequently downloaded eBooks. The resulting output of phrases from these books is matched to the given search phrase by first cleaning each series of words, then splitting them into bigrams, and finally calculating the Jaccard coefficient.

## Repository Structure
```
├── CS1003P4.java
├── README.md
└── data
    ├── 1080-0.txt
    ├── 11-0.txt
    ├── 1342-0.txt
    ├── 2542-0.txt
    ├── 2701-0.txt
    ├── 5200-0.txt
    ├── 64317-0.txt
    ├── 84-0.txt
    ├── 844-0.txt
    └── 98-0.txt
```

## Usage
To export classpath
```bash
  $ export CLASSPATH=${CLASSPATH}:spark/*:apache-maven-3.8.5/libs/*:.
```

To compile 
```bash
  $ javac *.java
```

To run
```bash
  $  java CS1003P4 data/ "term/ phrase to be searched" double 2> /dev/null
```
 &emsp;           **double** = desired similarity threshold