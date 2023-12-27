## The class and group name, and the names of the student(s) who worked on the assignment. (Putting the names of the student(s) in each source file is good practice too.)
This assignment is made by Hanna Straathof (s3001202), Oliver ten Hoor (s2984113) and Domen van Soest (s2962632)

## The compiler version and operating system used by the student(s).

The assignment submission must include a README file that documents:

Operating system: Linux
Compiler version: (Ubuntu 9.4.0-1ubuntu1~20.04.2) 9.4.0

## Whether it is known that the program works correctly, or whether the program has known defects.
Can't print specific errors such as unknown type
Created type tree is wrong


## Whether there are any deviations from the assignment, and reasons why.
No deviations from the assignment as of now



## How does the program work
The first two steps are almost the same as the previous two assignments. The only thing we had to change in the tokenizer and leesin function were the new symbols that came with the new grammar. When we succesfully tokenized all the symbols we started the parsing the expression. When parsing, we recursively start making a tree from the expression and while doing this, we look for errors which could be encountered during the process. The errors we give are specific and the user immediately knows what is wrong with their expression. During the process of building the tree, there are some differences in building it. Because the grammar also differs from the grammar we had in exercise one and two, a node could also have three children. 

//Nog iets schrijven over boom

// nog iets over type tree schrijven

When the tree is completely build and the process is completed, the main loop return if the expression has been parsed succesfully or there has been some error during the parsing process.


## Wat we nog zouden kunnen doen
The program may support international variable names (i.e. Unicode), and also accept λ instead of \



## Remarks for improving the assignment
NO improving yet

## Add Makefile arguments assignment 3
## Add Makefile arguments assignment 2
