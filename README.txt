HOW TO RUN THE PROJECT

A live and running version of the project can be viewed at http://semanticmusic.xyz

1. Add Apache-jena and jena-fuseki libraries to /resources/libs/apache.
2. pip install spotipy in /resources.
3. If you want to run everything localy you must set up a sql-database named "spotify" with the tables as specified in /resources/database.sql
3.1 Then you must change user credentials and host in resources/spotifyData.py and resources/Semantics/src/database/DbCredentials.java to what you set them to. (The folder /resources/Semantics is an IntelliJ project folder so it can be opened there or imported to Eclipse, or you can edit and compile the files manualy)
3.2 If you don't want to set up or change anything you can just leave things as they are and it will run on our servers. 
4. Run /resources/spotifyData.py in Python 3.4 or newer. 
5. Run the createTriples main class in resources/Semantics
6. Start a fuseki server with the command "./fuseki-server --update --loc=/../../../MYTDB /ds &" from /resources/libs/apache/jena-fuseki../ And populate it with the tripleStore.nt with the command "s-put http://localhost:3030/ds/data default ../../../tripleStore.nt" from the same place. 
7. Now you can test the project by opening /web/index.html in your browser.
