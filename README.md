# README
To build use:
`mvn package`

To run use:
`./run`

To start the server use:
`./run --gui [--port=<port>]`

# REPL 
The REPL is built to be easily modified and have commands added easily, without 
disrupting the structure of the REPL. Anyone using this code can add their own commands
and remove commands that they do not need. 

The REPL class utilizes a Map of String representing REPL commands to the Class object relevant to the command.
It utilizes Java's Reflection API in order to call the relevant methods for each type of command
and print the correct output.

