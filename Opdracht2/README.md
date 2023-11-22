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


## To do uitleg over of het programma helemaal werkt

The assignment submission must include a README file that documents:
• The class and group number, and the names of the student(s) who worked on the assignment. (Putting the names of the student(s) in each source file is good practice too.)
• The compiler version and operating system used by the student(s) if applicable.
• Whether it is known that the program works correctly, or whether the program has known defects.
• Whether there are any deviations from the assignment, and reasons why.

The README may include an explanation of how the program works, and
remarks for improving the assignment. Finally, the assignment submission may
include the following two files:
• An archive (positive.tar.gz) of the positive examples used for testing.
• An archive (negative.tar.gz) of the negative examples used for testing.


Ons programma werkt als volgt:

Samenvatting opdracht 1:
We hebben de code van opdracht 1 herbruikt voor deze opdracht. Dit betekent dat we de expressie eerst tokenizen en vervolgens checken we de expressie recursief om te kijken of hij aan alle eisen voldoet.

Wat we hierbij hebben toegevoegd is dat er een abstract syntax tree wordt gemaakt tijdens de recursie. Dit gebeurt als volgt:

Alle functies hebben we ten eerste veranderd van void functies naar Binary tree functies. Dit hebben we gedaan zodat we de boom stapje voor stapje kunnen opbouwen. Dit doen we omdat we de verschillende functies recursief meegeven en deze maken steeds kleine subbomen. De subbomen worden dan zo aan de boom toegevoegd dat het uiteindelijke resultaat left associative is. Als we een fout tegenkomen bij het maken van de boom dan wordt de root van de boom verwijderd en door de garbage collector van java worden alle andere kinderen ook automatisch verwijderd. Als de expressie een valide expressie is, wordt er gekeken of we alpha conversion, beta reduction of beide moeten toepassen. Met alpha conversion wordt er gekeken of variabele niet onderling clashen in verschillende scopes.


## TO DO bedenken wat er gebeurd als addNode false returned

## gapnode een membervariabele van binary tree maken, UITLEGGEN WAAROM


## TO DO reduction



## To do uitleg over hoe het programma werkt
Ons programma werkt als volgt:
We beginnen met het maken van een object parser, deze moet ervoor gaan zorgen dat de expressie later correct wordt geparsed. Hierna wordt de expressie ingelezen, deze expressie wordt in de variabele string gezet en vervolgens wordt string, karakter per karakter, geanalyseerd in de functie leesIn. Als er zich een fout optreedt bij het tokenizen van alle karakters, wordt de leesIn functie false en blijft de exitstatus false. Als het tokenizen goed is gegaan, staan alle tokens in de vector TokenList in de class parser. Vanuit deze vector wordt de ingevoerde expressie parsen met de LL grammar die wij hebben gekregen. Om te checken of de expressie voldoet aan de LL grammar, wordt de expressie vervolgens recursief gecheckt. Als er een fout optreedt bij deze stap wordt er een specifieke error message verstuurd en stopt de recursie. Als alles is goedgegaan, wat inhoud geen errors en de haakjes die kloppen, wordt er bij de parse functie true gereturned, anders false. De waarde van parser.parse wordt dan de waarde van exitStatus, en met een kleine ifstatement checken we dan of de expressie klopt of dat er een fout in zit.

## To do exit status 1 bij syntax error en anders 0
In Java moet de main functie een void functie zijn omdat die anders niet de main functie herkent. Om toch het programma te eindigen met exit status 0 of 1 wordt `System.exit(int status)` gebruikt.

## To do standard output printen bij exit status 0 en anders een duidelijke error message

## To do variabelen mogen niet met een cijfer beginnen
Gefixt

## To do iets met preference - Hanna mee bezig
Nu wordt bij lambda gewoon nieuwe haakjes toegevoegd. Kijken hoe je kan voorkomen dat je niet teveel haakjes hebt. Ook wordt printen nu niet goed gedaan want ik wilde zoveel mogelijk spaties printen

Application associates to the left

## Code voor het inlezen van een file inplaats vanuit de terminal

        String input = "";               //The input read from the file        
        try {
            FileReader filereader 
                = new FileReader(string);     //initialize a new filereader


            int i;
            while((i=filereader.read()) != -1) {    //making of the input
                input = input + (char)i;
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        if(leesIn(input, parser)) {
            exitStatus = parser.parse();
        }


import java.io.*;

### Meerdere expressies kunnen inlezen

### Ook lambda anders kunnen inlezen

### Dot implementeren?