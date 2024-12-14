# CostsWatcher

Documentation:
https://github.com/SofeiAndrei/CostsWatcher/blob/main/CostsWatcherDocumentation.pdf

Diagrams:
https://github.com/SofeiAndrei/CostsWatcher/tree/main/diagrams

Database:
1. Download PostgreSQL server
2. Add bin path to PATH
3. Open CMD
	- psql -U userName
	- *ENTER PASSWORD WHEN PROMPTED*
	- CREATE DATABASE costsWatcher WITH ENCODING 'UTF8';
	- \c costswatcher 
	- if *You are now connected to database "costswatcher" as user "postgres".*
		- \set ON_ERROR_STOP on
		- \i 'path/postgres.sql'

Code Coverage (JaCoCo)
1. Run maven test
2. Go to target/site/jacoco/index.html
3. Preview the file in the IDE or on the Web to access a very detailed view of the test coverage using JaCoCo
