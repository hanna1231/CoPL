# CoPL

## The class and group number, and the names of the student(s) who worked on the assignment. (Putting the names of the student(s) in each source file is good practice too.)
This assignment is made by Hanna Straathof (s3001202), Oliver ten Hoor (s2984113) and Domen van Soest (s2962632)

## Compiling and running the program
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


Makefile compiling and running:

Compile: make
Runnen: make run postives.txt

## The compiler version and operating system used by the student(s) if applicable.
The assignment submission must include a README file that documents:

Operating system: Linux
Compiler version: (Ubuntu 9.4.0-1ubuntu1~20.04.2) 9.4.0

## Whether it is known that the program works correctly, or whether the program has known defects.
The must implementations have all been completed

## Whether there are any deviations from the assignment, and reasons why.
Not as of yet

## The README may include an explanation of how the program works, and remarks for improving the assignment

Summary assignment 1:
We have reused the code from assignment 1 for this assignment. This means that we first tokenize the expression and then we recursively check the expression to see if it meets all requirements.

What we have added is that an abstract syntax tree is created during the recursion. This happens as follows:

First, we changed all functions from void functions to Binary tree functions. We did this so that we can build the tree step by step. We do this because we pass the various functions recursively and these always create small subtrees. The subtrees are then added to the tree in such a way that the final result is left associative. Here again, the member variable gapnode is an important addition because we ensure that all parent nodes always have a left and right child filled.
If we encounter an error while creating the tree then the root of the tree is deleted and java's garbage collector automatically deletes all other children as well.

If the expression is a valid expression, we check whether we should only apply beta reduction. If we need to change another variable so that the variable names do not clash, we must first apply alpha conversion and then reduce the expression. When checking whether alpha conversion is necessary, we first check whether variables are free. If this is not the case, the name of the variable must be changed so that it does not clash with the bound variables. Once we have ensured that nothing clashes anymore, we check whether we need to do beta reduction on the expression.

The beta reduction is carried out anyway, we reduce the expression to a smaller expression. As an example we take this simple lambda expression: (lx xy) z, this expression is interpreted as follows (l parameter output) input. Our beta reduction then works as follows: find all occurrences of the parameter x in the output and substitute the input z for the parameter x in the output.

## Dingen die we nog kunnen implementeren


//Should's die te doen zijn:
--The α-conversions should only be performed if a β-reduction would otherwise lead to a captured variable
--Should only accept one expression in the input file
--Should be grammar insensitive
--But may also work for files which contain non-printable ASCII characters

## TO DO PROGRAMMA AFSLUITEN MET 0, 1 OF 2

File meegegeven vanuit command line
Inlezen meerdere expressies


