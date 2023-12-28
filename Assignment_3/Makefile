# Makefile for simple Java program

JC = javac
JVM = java
JARCMD = jar

SOURCE_DIR = src
SOURCE = $(wildcard $(SOURCE_DIR)/*.java)
MAIN = MainClass
JAR = program.jar

.PHONY: clean run

default: build

build: $(SOURCE)
			$(JC) $(SOURCE)

jar: build
			$(JARCMD) cvfe $(JAR) $(MAIN) *.class

run: build
			$(JVM) -cp $(SOURCE_DIR) $(MAIN) test.txt

clean:
			$(RM) *.class $(JAR)

