BefungeSimulator
================

A simple, cross-platform, Befunge debugger written in Java.

WTF is Befunge?
---------------
Befunge is an awesome for-fun 2-dimensional programming language. See
[overview here](http://esolangs.org/wiki/Befunge).

Usage
-----
A runnable jar file is included in the `bin` directory. Execute the following
command to run it:

```
$ java -jar bin/BefungeSimulator.jar
```

This will open the debugger/editor. Here you can type in Befunge code, as well
as move around the code by using the arrow keys. Before you start debugging,
you can add some input that your program can read (using Befunge's . and ,
commands) in the Input textbox. To start debugging, click the Start button. Now
you can step through the code using the Step button (or by pressing/holding the
Enter key). The contents of the stack are displayed, and any output will be
put in the Output textbox. Clicking Reset will take you back to edit mode, as
well as reset all changes that the program may have made. While in edit mode,
you can Open/Save Befunge code through the File menu.

Examples
--------
Many cool Befunge programs were included in the befunge_code directory. I also
included solutions to most of the problems of
[Unknown Language Round #4](http://codeforces.com/contest/130) on Codeforces. I
encourage you to try solving the problems yourself before looking at my
solutions, it's a lot of fun!

