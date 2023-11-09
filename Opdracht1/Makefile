# Makefile for simple Java program
JC = javac
JVM = java
JARCMD = jar
SOURCE = src/MainClass.java Parser.java Token.java
MAIN = MainClass
JAR = program.jar
.PHONY: clean run
default: build
build: $(SOURCE)
$(JC) $(SOURCE)
jar: build
$(JARCMD) cvfe $(JAR) $(MAIN) *.class
run: build
$(JVM) $(MAIN) positives.txt
# alternatively:
# $(JVM) $(MAIN) < positives.txt
# if your program reads from the standard input (stdin)
clean:
$(RM) *.class $(JAR)