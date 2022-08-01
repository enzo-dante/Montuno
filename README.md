# MusicManagerUI

The MusicManagerUI application is a proof-of-concept (POC) that demonstrates that a Java application can manage a SQL database (specifically a SQLite3 database) using Java Database Connectivity (JDBC).

The queries and changes made by the user-interface that is created with JavaFX can be validated by the DB Brower for SQLite gui application. 

__To Run MusicManagerUI:__
1. open MusicManagerUI in Intelli J
2. go to the Main class and select the play button on the left hand side


# POC scope

Please understand, this is a POC and not a fully functional architecture for managing data, but it does use some best practices like data protection against SQL Injection Attacks.

# POC limitations

Even though the application is functional, for the scope:
- I decided to not fully compartmentalize methods respective of the class it utalizes and decided to use the DataSource class as the primary class.
- For testing purposes, I deliberates slowed down the SQL query to get a list of artists to show the progress bar
- The update method only updates the artist AC/DC with a hard-coded String variable in the code and does not currently take in user input
- If no artist is selected when the show albums by artist button is pressed, the terminal will only report the issue, but a modal warning for the UI has not been implemented yet

# java database connectivity (jdbc)

This project already contains the JDBC driver required for JDBC to interface with SQLite, all you have to do is update the String path named JDBC_CONNECTION_STRING in the DataSource class.

JDBC acts like the middleman between a java application and the data source
- JDBC works with databases, flat files, and spreadsheets

to work with sqlite 3, JDBC needs the sqlite driver

switching between drivers like SQLite and mySQL drivers is relatively easy

# DB Browser for SQLite

it is a GUI for handling a SQLite DB which is compatible with JDBC

when you are using the GUI, the app will lock the DB file and wont be able to access the db from a java application
- solution: open file drown down and select 'close database' to release lock

# Intelli J

__steps to connect SQLite 3 driver to JDBC__

1. download sqlite-jdbc driver jar file from github and save to desired dir
https://github.com/xerial/sqlite-jdbc/releases

2. download DB Browser for SQLite from sqlite.org
3. create a new Intelli J java project with the command line template
4. open file drown down -> project structure -> Libraries -> plus symbol -> java
5. open sqlite-jdbc driver from saved location -> OK
6. define CONSTANTS for DB_NAME and CONNECTION_STRING outside of main method
6. define try and catch block inside main method
7. in try define and establish jdbc:sqlite connection

# Constants 

use Constants for static variables for scalability

hard coding static variables is a bad practice because it makes updating very error-prone

# JDBC Statement and Execute 

to execute SQL with JDBC, create Statement objects with the DriverManager connection

__you can reuse a statement object to execute multiple CRUD SQL commands BUT not SELECT commands__
- each SELECT having it's own statement ensures data integrity, especially if multiple SELECT are running concurrently

# review SQL commands with DB Browser with SQLite

1. open DB Browser with SQLite
2. select Open Database panel and select & open target db
2. select Browse Data view

# SQL Injection Attacks

if user input isn't scrubbed the db is vulnerable to the user enter SQL commands into their input and getting private db data

the below blindly concatenates to SQL command and since 1=1 would be true for every record all records would be returned

ex:

Enter a song title:
Heartless' OR 1=1 OR '

# SQL PreparedStatement

A PreparedStatement can protect against SQL injection attacks because they are only pre-compiled once and the userInput is being treated as literal values and not as SQL

A StringBuilder concatenates string to SQL command, while a PreparedStatement would substitute literal value as title

ex:

__vulnerable StringBuilder__
SELECT name FROM artist_list WHERE title = 'Heartless' OR 1=1 OR ';

__protected PreparedStatement__
SELECT name FROM artist_list WHERE title = 'Heartless OR 1=1 OR ';
