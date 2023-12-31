## The class and group name, and the names of the student(s) who worked on the assignment. (Putting the names of the student(s) in each source file is good practice too.)
This assignment is made by Hanna Straathof (s3001202), Oliver ten Hoor (s2984113) and Domen van Soest (s2962632)

## The compiler version and operating system used by the student(s).

The assignment submission must include a README file that documents:

Operating system: Linux
Compiler version: (Ubuntu 9.4.0-1ubuntu1~20.04.2) 9.4.0

## Whether it is known that the program works correctly, or whether the program has known defects.
program works correctly


## Whether there are any deviations from the assignment, and reasons why.
No deviations from the assignment as of now



## How does the program work
The first two steps are almost the same as the previous two assignments. The only thing we had to change in the tokenizer and leesin function were the new symbols that came with the new grammar. When we succesfully tokenized all the symbols we started the parsing the expression. When parsing, we recursively start making a tree from the expression and while doing this, we look for errors which could be encountered during the process. The errors we give are specific and the user immediately knows what is wrong with their expression. During the process of building the tree, there are some differences in building it. Because the grammar also differs from the grammar we had in exercise one and two, a node could also have three children. 

We build the tree step by step. We do this because we pass the various functions recursively and these always create small subtrees. The subtrees are then added to the tree in such a way that the final result is left associative. 
If we encounter an error while creating the tree then the root of the tree is deleted and java's garbage collector automatically deletes all other children as well.

For checking whether or not the type is correct we first construct a new tree typetree which consists of all the types from the left side of the judgement. we build that tree from bottom to top. We then check if that tree is the same as the right side of the judgement. If it is then the types match and the expression is accepted, otherwise the type is wrong and returned false.

When the tree is completely build and the process is completed, the main loop return if the expression has been parsed succesfully or there has been some error during the parsing process.


## Remarks for impnroving the assignment
Errors with opening and closig parenthesis