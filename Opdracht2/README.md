# CoPL

This assignment is made by Hanna Straathof (s3001202), Oliver ten Hoor (s2984113) and Domen van Soest (s2962632)

## To do uitleg over hoe te compilen en uitzoeken hoe dat via de command line kan

For ubuntu on how to download java:
sudo apt-get update.
sudo apt install default-jdk.
java -version.
update-alternatives --config java.
sudo nano /etc/environment.
source /etc/environment.

Als het nog niet werkt, voeg dan dit toe:
sudo apt-get update -y
sudo apt-get install -y openjdk-11-jdk-headless


Compilen: javac MainClass.javac
Runnen: java Mainclass

Met de makefile
Compile: make
Runnen: make run

## To do uitleg over of het programma helemaal werkt

The assignment submission must include a README file that documents:

Operating system: Linux
Compiler version: (Ubuntu 9.4.0-1ubuntu1~20.04.2) 9.4.0


## Whether there are any deviations from the assignment, and reasons why.

// Op dit moment --> beta reduction nog niet toegevoegd
--Als gedaan --> klaar opdracht 2

## Dingen die we nog kunnen implementeren


//Should's die te doen zijn:
--The α-conversions should only be performed if a β-reduction would otherwise lead to a captured variable
--Should only accept one expression in the input file
--Should be grammar insensitive

## TO DO reduction



## Werking programma

Samenvatting opdracht 1:
We hebben de code van opdracht 1 herbruikt voor deze opdracht. Dit betekent dat we de expressie eerst tokenizen en vervolgens checken we de expressie recursief om te kijken of hij aan alle eisen voldoet.

Wat we hierbij hebben toegevoegd is dat er een abstract syntax tree wordt gemaakt tijdens de recursie. Dit gebeurt als volgt:

Alle functies hebben we ten eerste veranderd van void functies naar Binary tree functies. Dit hebben we gedaan zodat we de boom stapje voor stapje kunnen opbouwen. Dit doen we omdat we de verschillende functies recursief meegeven en deze maken steeds kleine subbomen. De subbomen worden dan zo aan de boom toegevoegd dat het uiteindelijke resultaat left associative is. Herbij is de membervariabele gapnode een belangrijke toevoeging omdat we ervoor zorgen dat alle ouderknopen altijd gevuld een linker en rechterkind krijgen. 
Als we een fout tegenkomen bij het maken van de boom dan wordt de root van de boom verwijderd en door de garbage collector van java worden alle andere kinderen ook automatisch verwijderd. Als de expressie een valide expressie is, wordt er gekeken of we alpha conversion, beta reduction of beide moeten toepassen. Bij het checken of alpha conversion nodig is wordt er eerst gekeken of variabelen vrij zijn. Als dit niet zo is moet de naam van de variabele veranderd worden zodat hij niet clasht met de bound variabelen. Als we ervoor hebben gezorgd dat niks meer met elkaar clasht wordt er gekeken of we beta reduction moeten doen op de expressie. 

----------Schrijf nog iets over beta reduction---------------------


## Finally, the assignment submission may include the following two files:
• An archive (positive.tar.gz) of the positive examples used for testing.
• An archive (negative.tar.gz) of the negative examples used for testing.
