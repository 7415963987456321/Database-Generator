# Author:  Hrafnkell Sigurðarson <hrs70@hi.is>
all: Database.class

Database.class: Database.java
	javac -g Database.java

clean:
	rm -Rf *~ Database.class database.db

test: Database.class
	@echo ''
	java -cp .:sqlite-jdbc-3.30.1.jar Database

run: testNANOMORPHO.mexe testFIBO.mexe
	java -jar morpho.jar testNANOMORPHO
	java -jar morpho.jar testFIBO

