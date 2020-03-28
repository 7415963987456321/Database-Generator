# Author:  Hrafnkell Sigurðarson <hrs70@hi.is>
all: Database.class

Database.class: Database.java
	@echo 'Compiling...'
	javac -g Database.java

clean:
	@echo 'Cleaning...'
	rm -Rf *~ Database.class database.db

test: Database.class
	@echo 'Making database this might take some time...'
	java -cp .:sqlite-jdbc-3.30.1.jar Database

run: database.db
	sqlite3 database.db

