## The student number(s) of the student(s) who worked on the assignment.

This assignment is made by Hanna Straathof (s3001202), Oliver ten Hoor (s2984113) and Domen van Soest (s2962632)

## Whether it is known that the program works correctly, or whether the program has known defects.


## Compiling and running the program

For ubuntu on how to download java:
sudo apt-get update.
sudo apt install default-jdk

Als het nog niet werkt, voeg dan dit toe:
sudo apt-get update -y
sudo apt-get install -y openjdk-11-jdk-headless

Zorg dat je in de src map zit
Compilen: javac MainClass.java
Runnen: java Mainclass


## Whether there are any deviations from the assignment, and reasons why.
Not that we know of

## The README may include an explanation of how the program works.
We start by making a parser object, this object is the key for parsing the expression. When the object has been made, the expression will be read in, this can be done multiple times. When reading the expression in, the tokenizen can begin. For each character/variable a new token will be made and analyzed in the leesin function. If something has gone wrong in this stage, an error will occur and the parsing will not be done. If the tokenizing has been done and no errors have occured, all the tokens are located in a vector called Tokenlist in the class parser. From this vector, the expression will be parsed with the LL grammar which we have gotten. To check if the expression meets the LL grammar, the expression will be checked recursively. When an error has occurred during this step, a specific errormessage will be sent and stops the recursion. If all the steps have been completed and no error has occurred, the parse function returns true, else false. The value of parser.parse becomes the value of exitstatus and with a small if statement we check if the expression is right. 
