# CardGame #
## How to compile and run cardgame ##
0. First of all, make sure that   
• the name of the root directory is cardgame, and  
• you create a folder called txt under the root directory, if it doesn't exist.

1. In the cardgame directory run 
```
 javac -d bin *.java
```

2. Go to the bin directory
```
 cd bin
```

3. run
```
java cardgame.CardGame
```
and input the number of players.  

## How to compile and run tests ##
before running this, make sure junit-4.12.jar and hamcrest-core-1.3.jar are installed and the classpath is pointing at the correct path to them 

1. In the cardgame directory run 
  ```
 javac -d bin *.java
```
2. Go to the bin directory
  ```
 cd bin
```
3. run
```
java  org.junit.runner.JUnitCore cardgame.test.TestSuite 
```

## *How to generate a pack file ##
1. In the cardgame directory run 
```
 javac -d bin *.java
```

2. Go to the bin directory
```
 cd bin
```

3. run
```
java cardgame.GeneratePack
```