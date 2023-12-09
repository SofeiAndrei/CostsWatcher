# CostsWatcher

Database:
1. Download PostgreSQL server
2. Add bin path to PATH
3. Open CMD
	psql -U userName
	*ENTER PASSWORD WHEN PROMPTED*
	CREATE DATABASE costsWatcher WITH ENCODING 'UTF8';
	\c costswatcher 
	if *You are now connected to database "costswatcher" as user "postgres".*
		\set ON_ERROR_STOP on
		\i 'path/postgres.sql'
