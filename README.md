# Kevin Bacon Game Implementation in Java
Uses the JGraphT Java graph implementation as well as an existing file full of movies and actors to create the Kevin Bacon Game in Java.

# Technologies Used
- [JGraphT](https://jgrapht.org/) for the Java Graph data structure implementation.
- [Commons IO](https://commons.apache.org/proper/commons-io/) to read the data file.
- [JSON in Java](https://mvnrepository.com/artifact/org.json/json) for the JSON library.

# Installation
Requirements
- Java - I am using Java 15, but I believe a minimum of Java 11 is required due to JGraphT.
- Maven - To download dependencies, compile, and run.

### 1. Clone this repo.
    git clone https://github.com/ChuckCaplan/KevinBaconGame

### Compile and run the application.
	cd KevinBaconGame
	mvn compile exec:java -Dexec.mainClass="com.chuckcaplan.game.kevinbacongame.KevinBaconGame"

# Notes
1. Edit KevinBaconGame.java to change the actors, i.e. if you don't want to use Kevin Bacon. Get the exact spelling of the actor from data.txt. For example, for Kevin Bacon, you would need to use [[Kevin Bacon]] as that is how he is in the file.
2. Thanks to The Oracle of Bacon for the idea and the data file - https://oracleofbacon.org/

# Author
[Chuck Caplan](https://www.linkedin.com/in/charlescaplan)