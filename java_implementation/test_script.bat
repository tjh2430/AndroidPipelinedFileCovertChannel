@ECHO OFF

REM Test Case 1: Hello World (full)
REM Pipes: 1, 2, 3, 4, 5
java Sender hello_world.txt 1 -w 1
java Sender hello_world.txt 1 -w 2
java Sender hello_world.txt 1 -w 3
java Sender hello_world.txt 1 -w 4
java Sender hello_world.txt 1 -w 5
java Sender hello_world.txt 1 -w 6
java Sender hello_world.txt 1 -w 7
java Sender hello_world.txt 1 -w 8
java Sender hello_world.txt 1 -w 9
java Sender hello_world.txt 1 -w 10

java Sender hello_world.txt 2 -w 1
java Sender hello_world.txt 2 -w 2
java Sender hello_world.txt 2 -w 3
java Sender hello_world.txt 2 -w 4
java Sender hello_world.txt 2 -w 5
java Sender hello_world.txt 2 -w 6
java Sender hello_world.txt 2 -w 7
java Sender hello_world.txt 2 -w 8
java Sender hello_world.txt 2 -w 9
java Sender hello_world.txt 2 -w 10

java Sender hello_world.txt 3 -w 1
java Sender hello_world.txt 3 -w 2
java Sender hello_world.txt 3 -w 3
java Sender hello_world.txt 3 -w 4
java Sender hello_world.txt 3 -w 5
java Sender hello_world.txt 3 -w 6
java Sender hello_world.txt 3 -w 7
java Sender hello_world.txt 3 -w 8
java Sender hello_world.txt 3 -w 9
java Sender hello_world.txt 3 -w 10

java Sender hello_world.txt 4 -w 1
java Sender hello_world.txt 4 -w 2
java Sender hello_world.txt 4 -w 3
java Sender hello_world.txt 4 -w 4
java Sender hello_world.txt 4 -w 5
java Sender hello_world.txt 4 -w 6
java Sender hello_world.txt 4 -w 7
java Sender hello_world.txt 4 -w 8
java Sender hello_world.txt 4 -w 9
java Sender hello_world.txt 4 -w 10

java Sender hello_world.txt 5 -w 1
java Sender hello_world.txt 5 -w 2
java Sender hello_world.txt 5 -w 3
java Sender hello_world.txt 5 -w 4
java Sender hello_world.txt 5 -w 5
java Sender hello_world.txt 5 -w 6
java Sender hello_world.txt 5 -w 7
java Sender hello_world.txt 5 -w 8
java Sender hello_world.txt 5 -w 9
java Sender hello_world.txt 5 -w 10

REM Test Case 2: Shakespeare (100 bytes)
REM Pipes: 1, 2, 3, 4, 5, 10 ,16
java Sender complete_works_of_shakespeare.txt 1 -b 100 -w 1
java Sender complete_works_of_shakespeare.txt 1 -b 100 -w 2
java Sender complete_works_of_shakespeare.txt 1 -b 100 -w 3
java Sender complete_works_of_shakespeare.txt 1 -b 100 -w 4
java Sender complete_works_of_shakespeare.txt 1 -b 100 -w 5
java Sender complete_works_of_shakespeare.txt 1 -b 100 -w 6
java Sender complete_works_of_shakespeare.txt 1 -b 100 -w 7
java Sender complete_works_of_shakespeare.txt 1 -b 100 -w 8
java Sender complete_works_of_shakespeare.txt 1 -b 100 -w 9
java Sender complete_works_of_shakespeare.txt 1 -b 100 -w 10

java Sender complete_works_of_shakespeare.txt 2 -b 100 -w 1
java Sender complete_works_of_shakespeare.txt 2 -b 100 -w 2
java Sender complete_works_of_shakespeare.txt 2 -b 100 -w 3
java Sender complete_works_of_shakespeare.txt 2 -b 100 -w 4
java Sender complete_works_of_shakespeare.txt 2 -b 100 -w 5
java Sender complete_works_of_shakespeare.txt 2 -b 100 -w 6
java Sender complete_works_of_shakespeare.txt 2 -b 100 -w 7
java Sender complete_works_of_shakespeare.txt 2 -b 100 -w 8
java Sender complete_works_of_shakespeare.txt 2 -b 100 -w 9
java Sender complete_works_of_shakespeare.txt 2 -b 100 -w 10

java Sender complete_works_of_shakespeare.txt 3 -b 100 -w 1
java Sender complete_works_of_shakespeare.txt 3 -b 100 -w 2
java Sender complete_works_of_shakespeare.txt 3 -b 100 -w 3
java Sender complete_works_of_shakespeare.txt 3 -b 100 -w 4
java Sender complete_works_of_shakespeare.txt 3 -b 100 -w 5
java Sender complete_works_of_shakespeare.txt 3 -b 100 -w 6
java Sender complete_works_of_shakespeare.txt 3 -b 100 -w 7
java Sender complete_works_of_shakespeare.txt 3 -b 100 -w 8
java Sender complete_works_of_shakespeare.txt 3 -b 100 -w 9
java Sender complete_works_of_shakespeare.txt 3 -b 100 -w 10

java Sender complete_works_of_shakespeare.txt 4 -b 100 -w 1
java Sender complete_works_of_shakespeare.txt 4 -b 100 -w 2
java Sender complete_works_of_shakespeare.txt 4 -b 100 -w 3
java Sender complete_works_of_shakespeare.txt 4 -b 100 -w 4
java Sender complete_works_of_shakespeare.txt 4 -b 100 -w 5
java Sender complete_works_of_shakespeare.txt 4 -b 100 -w 6
java Sender complete_works_of_shakespeare.txt 4 -b 100 -w 7
java Sender complete_works_of_shakespeare.txt 4 -b 100 -w 8
java Sender complete_works_of_shakespeare.txt 4 -b 100 -w 9
java Sender complete_works_of_shakespeare.txt 4 -b 100 -w 10

java Sender complete_works_of_shakespeare.txt 5 -b 100 -w 1
java Sender complete_works_of_shakespeare.txt 5 -b 100 -w 2
java Sender complete_works_of_shakespeare.txt 5 -b 100 -w 3
java Sender complete_works_of_shakespeare.txt 5 -b 100 -w 4
java Sender complete_works_of_shakespeare.txt 5 -b 100 -w 5
java Sender complete_works_of_shakespeare.txt 5 -b 100 -w 6
java Sender complete_works_of_shakespeare.txt 5 -b 100 -w 7
java Sender complete_works_of_shakespeare.txt 5 -b 100 -w 8
java Sender complete_works_of_shakespeare.txt 5 -b 100 -w 9
java Sender complete_works_of_shakespeare.txt 5 -b 100 -w 10

java Sender complete_works_of_shakespeare.txt 10 -b 100 -w 1
java Sender complete_works_of_shakespeare.txt 10 -b 100 -w 2
java Sender complete_works_of_shakespeare.txt 10 -b 100 -w 3
java Sender complete_works_of_shakespeare.txt 10 -b 100 -w 4
java Sender complete_works_of_shakespeare.txt 10 -b 100 -w 5
java Sender complete_works_of_shakespeare.txt 10 -b 100 -w 6
java Sender complete_works_of_shakespeare.txt 10 -b 100 -w 7
java Sender complete_works_of_shakespeare.txt 10 -b 100 -w 8
java Sender complete_works_of_shakespeare.txt 10 -b 100 -w 9
java Sender complete_works_of_shakespeare.txt 10 -b 100 -w 10

java Sender complete_works_of_shakespeare.txt 16 -b 100 -w 1
java Sender complete_works_of_shakespeare.txt 16 -b 100 -w 2
java Sender complete_works_of_shakespeare.txt 16 -b 100 -w 3
java Sender complete_works_of_shakespeare.txt 16 -b 100 -w 4
java Sender complete_works_of_shakespeare.txt 16 -b 100 -w 5
java Sender complete_works_of_shakespeare.txt 16 -b 100 -w 6
java Sender complete_works_of_shakespeare.txt 16 -b 100 -w 7
java Sender complete_works_of_shakespeare.txt 16 -b 100 -w 8
java Sender complete_works_of_shakespeare.txt 16 -b 100 -w 9
java Sender complete_works_of_shakespeare.txt 16 -b 100 -w 10

REM Test Case 3: Shakespeare (500 bytes)
REM Pipes: 1, 2, 3, 4, 5, 10 ,16
java Sender complete_works_of_shakespeare.txt 1 -b 500 -w 1
java Sender complete_works_of_shakespeare.txt 1 -b 500 -w 2
java Sender complete_works_of_shakespeare.txt 1 -b 500 -w 3
java Sender complete_works_of_shakespeare.txt 1 -b 500 -w 4
java Sender complete_works_of_shakespeare.txt 1 -b 500 -w 5
java Sender complete_works_of_shakespeare.txt 1 -b 500 -w 6
java Sender complete_works_of_shakespeare.txt 1 -b 500 -w 7
java Sender complete_works_of_shakespeare.txt 1 -b 500 -w 8
java Sender complete_works_of_shakespeare.txt 1 -b 500 -w 9
java Sender complete_works_of_shakespeare.txt 1 -b 500 -w 10

java Sender complete_works_of_shakespeare.txt 2 -b 500 -w 1
java Sender complete_works_of_shakespeare.txt 2 -b 500 -w 2
java Sender complete_works_of_shakespeare.txt 2 -b 500 -w 3
java Sender complete_works_of_shakespeare.txt 2 -b 500 -w 4
java Sender complete_works_of_shakespeare.txt 2 -b 500 -w 5
java Sender complete_works_of_shakespeare.txt 2 -b 500 -w 6
java Sender complete_works_of_shakespeare.txt 2 -b 500 -w 7
java Sender complete_works_of_shakespeare.txt 2 -b 500 -w 8
java Sender complete_works_of_shakespeare.txt 2 -b 500 -w 9
java Sender complete_works_of_shakespeare.txt 2 -b 500 -w 10

java Sender complete_works_of_shakespeare.txt 3 -b 500 -w 1
java Sender complete_works_of_shakespeare.txt 3 -b 500 -w 2
java Sender complete_works_of_shakespeare.txt 3 -b 500 -w 3
java Sender complete_works_of_shakespeare.txt 3 -b 500 -w 4
java Sender complete_works_of_shakespeare.txt 3 -b 500 -w 5
java Sender complete_works_of_shakespeare.txt 3 -b 500 -w 6
java Sender complete_works_of_shakespeare.txt 3 -b 500 -w 7
java Sender complete_works_of_shakespeare.txt 3 -b 500 -w 8
java Sender complete_works_of_shakespeare.txt 3 -b 500 -w 9
java Sender complete_works_of_shakespeare.txt 3 -b 500 -w 10

java Sender complete_works_of_shakespeare.txt 4 -b 500 -w 1
java Sender complete_works_of_shakespeare.txt 4 -b 500 -w 2
java Sender complete_works_of_shakespeare.txt 4 -b 500 -w 3
java Sender complete_works_of_shakespeare.txt 4 -b 500 -w 4
java Sender complete_works_of_shakespeare.txt 4 -b 500 -w 5
java Sender complete_works_of_shakespeare.txt 4 -b 500 -w 6
java Sender complete_works_of_shakespeare.txt 4 -b 500 -w 7
java Sender complete_works_of_shakespeare.txt 4 -b 500 -w 8
java Sender complete_works_of_shakespeare.txt 4 -b 500 -w 9
java Sender complete_works_of_shakespeare.txt 4 -b 500 -w 10

java Sender complete_works_of_shakespeare.txt 5 -b 500 -w 1
java Sender complete_works_of_shakespeare.txt 5 -b 500 -w 2
java Sender complete_works_of_shakespeare.txt 5 -b 500 -w 3
java Sender complete_works_of_shakespeare.txt 5 -b 500 -w 4
java Sender complete_works_of_shakespeare.txt 5 -b 500 -w 5
java Sender complete_works_of_shakespeare.txt 5 -b 500 -w 6
java Sender complete_works_of_shakespeare.txt 5 -b 500 -w 7
java Sender complete_works_of_shakespeare.txt 5 -b 500 -w 8
java Sender complete_works_of_shakespeare.txt 5 -b 500 -w 9
java Sender complete_works_of_shakespeare.txt 5 -b 500 -w 10

java Sender complete_works_of_shakespeare.txt 10 -b 500 -w 1
java Sender complete_works_of_shakespeare.txt 10 -b 500 -w 2
java Sender complete_works_of_shakespeare.txt 10 -b 500 -w 3
java Sender complete_works_of_shakespeare.txt 10 -b 500 -w 4
java Sender complete_works_of_shakespeare.txt 10 -b 500 -w 5
java Sender complete_works_of_shakespeare.txt 10 -b 500 -w 6
java Sender complete_works_of_shakespeare.txt 10 -b 500 -w 7
java Sender complete_works_of_shakespeare.txt 10 -b 500 -w 8
java Sender complete_works_of_shakespeare.txt 10 -b 500 -w 9
java Sender complete_works_of_shakespeare.txt 10 -b 500 -w 10

java Sender complete_works_of_shakespeare.txt 16 -b 500 -w 1
java Sender complete_works_of_shakespeare.txt 16 -b 500 -w 2
java Sender complete_works_of_shakespeare.txt 16 -b 500 -w 3
java Sender complete_works_of_shakespeare.txt 16 -b 500 -w 4
java Sender complete_works_of_shakespeare.txt 16 -b 500 -w 5
java Sender complete_works_of_shakespeare.txt 16 -b 500 -w 6
java Sender complete_works_of_shakespeare.txt 16 -b 500 -w 7
java Sender complete_works_of_shakespeare.txt 16 -b 500 -w 8
java Sender complete_works_of_shakespeare.txt 16 -b 500 -w 9
java Sender complete_works_of_shakespeare.txt 16 -b 500 -w 10

REM Test Case $: Shakespeare (1000 bytes)
REM Pipes: 1, 2, 3, 4, 5, 10 ,16
java Sender complete_works_of_shakespeare.txt 1 -b 1000 -w 1
java Sender complete_works_of_shakespeare.txt 1 -b 1000 -w 2
java Sender complete_works_of_shakespeare.txt 1 -b 1000 -w 3
java Sender complete_works_of_shakespeare.txt 1 -b 1000 -w 4
java Sender complete_works_of_shakespeare.txt 1 -b 1000 -w 5
java Sender complete_works_of_shakespeare.txt 1 -b 1000 -w 6
java Sender complete_works_of_shakespeare.txt 1 -b 1000 -w 7
java Sender complete_works_of_shakespeare.txt 1 -b 1000 -w 8
java Sender complete_works_of_shakespeare.txt 1 -b 1000 -w 9
java Sender complete_works_of_shakespeare.txt 1 -b 1000 -w 10

java Sender complete_works_of_shakespeare.txt 2 -b 1000 -w 1
java Sender complete_works_of_shakespeare.txt 2 -b 1000 -w 2
java Sender complete_works_of_shakespeare.txt 2 -b 1000 -w 3
java Sender complete_works_of_shakespeare.txt 2 -b 1000 -w 4
java Sender complete_works_of_shakespeare.txt 2 -b 1000 -w 5
java Sender complete_works_of_shakespeare.txt 2 -b 1000 -w 6
java Sender complete_works_of_shakespeare.txt 2 -b 1000 -w 7
java Sender complete_works_of_shakespeare.txt 2 -b 1000 -w 8
java Sender complete_works_of_shakespeare.txt 2 -b 1000 -w 9
java Sender complete_works_of_shakespeare.txt 2 -b 1000 -w 10

java Sender complete_works_of_shakespeare.txt 3 -b 1000 -w 1
java Sender complete_works_of_shakespeare.txt 3 -b 1000 -w 2
java Sender complete_works_of_shakespeare.txt 3 -b 1000 -w 3
java Sender complete_works_of_shakespeare.txt 3 -b 1000 -w 4
java Sender complete_works_of_shakespeare.txt 3 -b 1000 -w 5
java Sender complete_works_of_shakespeare.txt 3 -b 1000 -w 6
java Sender complete_works_of_shakespeare.txt 3 -b 1000 -w 7
java Sender complete_works_of_shakespeare.txt 3 -b 1000 -w 8
java Sender complete_works_of_shakespeare.txt 3 -b 1000 -w 9
java Sender complete_works_of_shakespeare.txt 3 -b 1000 -w 10

java Sender complete_works_of_shakespeare.txt 4 -b 1000 -w 1
java Sender complete_works_of_shakespeare.txt 4 -b 1000 -w 2
java Sender complete_works_of_shakespeare.txt 4 -b 1000 -w 3
java Sender complete_works_of_shakespeare.txt 4 -b 1000 -w 4
java Sender complete_works_of_shakespeare.txt 4 -b 1000 -w 5
java Sender complete_works_of_shakespeare.txt 4 -b 1000 -w 6
java Sender complete_works_of_shakespeare.txt 4 -b 1000 -w 7
java Sender complete_works_of_shakespeare.txt 4 -b 1000 -w 8
java Sender complete_works_of_shakespeare.txt 4 -b 1000 -w 9
java Sender complete_works_of_shakespeare.txt 4 -b 1000 -w 10

java Sender complete_works_of_shakespeare.txt 5 -b 1000 -w 1
java Sender complete_works_of_shakespeare.txt 5 -b 1000 -w 2
java Sender complete_works_of_shakespeare.txt 5 -b 1000 -w 3
java Sender complete_works_of_shakespeare.txt 5 -b 1000 -w 4
java Sender complete_works_of_shakespeare.txt 5 -b 1000 -w 5
java Sender complete_works_of_shakespeare.txt 5 -b 1000 -w 6
java Sender complete_works_of_shakespeare.txt 5 -b 1000 -w 7
java Sender complete_works_of_shakespeare.txt 5 -b 1000 -w 8
java Sender complete_works_of_shakespeare.txt 5 -b 1000 -w 9
java Sender complete_works_of_shakespeare.txt 5 -b 1000 -w 10

java Sender complete_works_of_shakespeare.txt 10 -b 1000 -w 1
java Sender complete_works_of_shakespeare.txt 10 -b 1000 -w 2
java Sender complete_works_of_shakespeare.txt 10 -b 1000 -w 3
java Sender complete_works_of_shakespeare.txt 10 -b 1000 -w 4
java Sender complete_works_of_shakespeare.txt 10 -b 1000 -w 5
java Sender complete_works_of_shakespeare.txt 10 -b 1000 -w 6
java Sender complete_works_of_shakespeare.txt 10 -b 1000 -w 7
java Sender complete_works_of_shakespeare.txt 10 -b 1000 -w 8
java Sender complete_works_of_shakespeare.txt 10 -b 1000 -w 9
java Sender complete_works_of_shakespeare.txt 10 -b 1000 -w 10

java Sender complete_works_of_shakespeare.txt 16 -b 1000 -w 1
java Sender complete_works_of_shakespeare.txt 16 -b 1000 -w 2
java Sender complete_works_of_shakespeare.txt 16 -b 1000 -w 3
java Sender complete_works_of_shakespeare.txt 16 -b 1000 -w 4
java Sender complete_works_of_shakespeare.txt 16 -b 1000 -w 5
java Sender complete_works_of_shakespeare.txt 16 -b 1000 -w 6
java Sender complete_works_of_shakespeare.txt 16 -b 1000 -w 7
java Sender complete_works_of_shakespeare.txt 16 -b 1000 -w 8
java Sender complete_works_of_shakespeare.txt 16 -b 1000 -w 9
java Sender complete_works_of_shakespeare.txt 16 -b 1000 -w 10

REM Test Case $: Shakespeare (5000 bytes)
REM Pipes: 1, 2, 3, 4, 5, 10 ,16
java Sender complete_works_of_shakespeare.txt 1 -b 5000 -w 1
java Sender complete_works_of_shakespeare.txt 1 -b 5000 -w 2
java Sender complete_works_of_shakespeare.txt 1 -b 5000 -w 3
java Sender complete_works_of_shakespeare.txt 1 -b 5000 -w 4
java Sender complete_works_of_shakespeare.txt 1 -b 5000 -w 5
java Sender complete_works_of_shakespeare.txt 1 -b 5000 -w 6
java Sender complete_works_of_shakespeare.txt 1 -b 5000 -w 7
java Sender complete_works_of_shakespeare.txt 1 -b 5000 -w 8
java Sender complete_works_of_shakespeare.txt 1 -b 5000 -w 9
java Sender complete_works_of_shakespeare.txt 1 -b 5000 -w 10

java Sender complete_works_of_shakespeare.txt 2 -b 5000 -w 1
java Sender complete_works_of_shakespeare.txt 2 -b 5000 -w 2
java Sender complete_works_of_shakespeare.txt 2 -b 5000 -w 3
java Sender complete_works_of_shakespeare.txt 2 -b 5000 -w 4
java Sender complete_works_of_shakespeare.txt 2 -b 5000 -w 5
java Sender complete_works_of_shakespeare.txt 2 -b 5000 -w 6
java Sender complete_works_of_shakespeare.txt 2 -b 5000 -w 7
java Sender complete_works_of_shakespeare.txt 2 -b 5000 -w 8
java Sender complete_works_of_shakespeare.txt 2 -b 5000 -w 9
java Sender complete_works_of_shakespeare.txt 2 -b 5000 -w 10

java Sender complete_works_of_shakespeare.txt 3 -b 5000 -w 1
java Sender complete_works_of_shakespeare.txt 3 -b 5000 -w 2
java Sender complete_works_of_shakespeare.txt 3 -b 5000 -w 3
java Sender complete_works_of_shakespeare.txt 3 -b 5000 -w 4
java Sender complete_works_of_shakespeare.txt 3 -b 5000 -w 5
java Sender complete_works_of_shakespeare.txt 3 -b 5000 -w 6
java Sender complete_works_of_shakespeare.txt 3 -b 5000 -w 7
java Sender complete_works_of_shakespeare.txt 3 -b 5000 -w 8
java Sender complete_works_of_shakespeare.txt 3 -b 5000 -w 9
java Sender complete_works_of_shakespeare.txt 3 -b 5000 -w 10

java Sender complete_works_of_shakespeare.txt 4 -b 5000 -w 1
java Sender complete_works_of_shakespeare.txt 4 -b 5000 -w 2
java Sender complete_works_of_shakespeare.txt 4 -b 5000 -w 3
java Sender complete_works_of_shakespeare.txt 4 -b 5000 -w 4
java Sender complete_works_of_shakespeare.txt 4 -b 5000 -w 5
java Sender complete_works_of_shakespeare.txt 4 -b 5000 -w 6
java Sender complete_works_of_shakespeare.txt 4 -b 5000 -w 7
java Sender complete_works_of_shakespeare.txt 4 -b 5000 -w 8
java Sender complete_works_of_shakespeare.txt 4 -b 5000 -w 9
java Sender complete_works_of_shakespeare.txt 4 -b 5000 -w 10

java Sender complete_works_of_shakespeare.txt 5 -b 5000 -w 1
java Sender complete_works_of_shakespeare.txt 5 -b 5000 -w 2
java Sender complete_works_of_shakespeare.txt 5 -b 5000 -w 3
java Sender complete_works_of_shakespeare.txt 5 -b 5000 -w 4
java Sender complete_works_of_shakespeare.txt 5 -b 5000 -w 5
java Sender complete_works_of_shakespeare.txt 5 -b 5000 -w 6
java Sender complete_works_of_shakespeare.txt 5 -b 5000 -w 7
java Sender complete_works_of_shakespeare.txt 5 -b 5000 -w 8
java Sender complete_works_of_shakespeare.txt 5 -b 5000 -w 9
java Sender complete_works_of_shakespeare.txt 5 -b 5000 -w 10

java Sender complete_works_of_shakespeare.txt 10 -b 5000 -w 1
java Sender complete_works_of_shakespeare.txt 10 -b 5000 -w 2
java Sender complete_works_of_shakespeare.txt 10 -b 5000 -w 3
java Sender complete_works_of_shakespeare.txt 10 -b 5000 -w 4
java Sender complete_works_of_shakespeare.txt 10 -b 5000 -w 5
java Sender complete_works_of_shakespeare.txt 10 -b 5000 -w 6
java Sender complete_works_of_shakespeare.txt 10 -b 5000 -w 7
java Sender complete_works_of_shakespeare.txt 10 -b 5000 -w 8
java Sender complete_works_of_shakespeare.txt 10 -b 5000 -w 9
java Sender complete_works_of_shakespeare.txt 10 -b 5000 -w 10

java Sender complete_works_of_shakespeare.txt 16 -b 5000 -w 1
java Sender complete_works_of_shakespeare.txt 16 -b 5000 -w 2
java Sender complete_works_of_shakespeare.txt 16 -b 5000 -w 3
java Sender complete_works_of_shakespeare.txt 16 -b 5000 -w 4
java Sender complete_works_of_shakespeare.txt 16 -b 5000 -w 5
java Sender complete_works_of_shakespeare.txt 16 -b 5000 -w 6
java Sender complete_works_of_shakespeare.txt 16 -b 5000 -w 7
java Sender complete_works_of_shakespeare.txt 16 -b 5000 -w 8
java Sender complete_works_of_shakespeare.txt 16 -b 5000 -w 9
java Sender complete_works_of_shakespeare.txt 16 -b 5000 -w 10

REM Test Case $: Shakespeare (10000 bytes)
REM Pipes: 1, 2, 3, 4, 5, 10 ,16
java Sender complete_works_of_shakespeare.txt 1 -b 10000 -w 1
java Sender complete_works_of_shakespeare.txt 1 -b 10000 -w 2
java Sender complete_works_of_shakespeare.txt 1 -b 10000 -w 3
java Sender complete_works_of_shakespeare.txt 1 -b 10000 -w 4
java Sender complete_works_of_shakespeare.txt 1 -b 10000 -w 5
java Sender complete_works_of_shakespeare.txt 1 -b 10000 -w 6
java Sender complete_works_of_shakespeare.txt 1 -b 10000 -w 7
java Sender complete_works_of_shakespeare.txt 1 -b 10000 -w 8
java Sender complete_works_of_shakespeare.txt 1 -b 10000 -w 9
java Sender complete_works_of_shakespeare.txt 1 -b 10000 -w 10

java Sender complete_works_of_shakespeare.txt 2 -b 10000 -w 1
java Sender complete_works_of_shakespeare.txt 2 -b 10000 -w 2
java Sender complete_works_of_shakespeare.txt 2 -b 10000 -w 3
java Sender complete_works_of_shakespeare.txt 2 -b 10000 -w 4
java Sender complete_works_of_shakespeare.txt 2 -b 10000 -w 5
java Sender complete_works_of_shakespeare.txt 2 -b 10000 -w 6
java Sender complete_works_of_shakespeare.txt 2 -b 10000 -w 7
java Sender complete_works_of_shakespeare.txt 2 -b 10000 -w 8
java Sender complete_works_of_shakespeare.txt 2 -b 10000 -w 9
java Sender complete_works_of_shakespeare.txt 2 -b 10000 -w 10

java Sender complete_works_of_shakespeare.txt 3 -b 10000 -w 1
java Sender complete_works_of_shakespeare.txt 3 -b 10000 -w 2
java Sender complete_works_of_shakespeare.txt 3 -b 10000 -w 3
java Sender complete_works_of_shakespeare.txt 3 -b 10000 -w 4
java Sender complete_works_of_shakespeare.txt 3 -b 10000 -w 5
java Sender complete_works_of_shakespeare.txt 3 -b 10000 -w 6
java Sender complete_works_of_shakespeare.txt 3 -b 10000 -w 7
java Sender complete_works_of_shakespeare.txt 3 -b 10000 -w 8
java Sender complete_works_of_shakespeare.txt 3 -b 10000 -w 9
java Sender complete_works_of_shakespeare.txt 3 -b 10000 -w 10

java Sender complete_works_of_shakespeare.txt 4 -b 10000 -w 1
java Sender complete_works_of_shakespeare.txt 4 -b 10000 -w 2
java Sender complete_works_of_shakespeare.txt 4 -b 10000 -w 3
java Sender complete_works_of_shakespeare.txt 4 -b 10000 -w 4
java Sender complete_works_of_shakespeare.txt 4 -b 10000 -w 5
java Sender complete_works_of_shakespeare.txt 4 -b 10000 -w 6
java Sender complete_works_of_shakespeare.txt 4 -b 10000 -w 7
java Sender complete_works_of_shakespeare.txt 4 -b 10000 -w 8
java Sender complete_works_of_shakespeare.txt 4 -b 10000 -w 9
java Sender complete_works_of_shakespeare.txt 4 -b 10000 -w 10

java Sender complete_works_of_shakespeare.txt 5 -b 10000 -w 1
java Sender complete_works_of_shakespeare.txt 5 -b 10000 -w 2
java Sender complete_works_of_shakespeare.txt 5 -b 10000 -w 3
java Sender complete_works_of_shakespeare.txt 5 -b 10000 -w 4
java Sender complete_works_of_shakespeare.txt 5 -b 10000 -w 5
java Sender complete_works_of_shakespeare.txt 5 -b 10000 -w 6
java Sender complete_works_of_shakespeare.txt 5 -b 10000 -w 7
java Sender complete_works_of_shakespeare.txt 5 -b 10000 -w 8
java Sender complete_works_of_shakespeare.txt 5 -b 10000 -w 9
java Sender complete_works_of_shakespeare.txt 5 -b 10000 -w 10

java Sender complete_works_of_shakespeare.txt 10 -b 10000 -w 1
java Sender complete_works_of_shakespeare.txt 10 -b 10000 -w 2
java Sender complete_works_of_shakespeare.txt 10 -b 10000 -w 3
java Sender complete_works_of_shakespeare.txt 10 -b 10000 -w 4
java Sender complete_works_of_shakespeare.txt 10 -b 10000 -w 5
java Sender complete_works_of_shakespeare.txt 10 -b 10000 -w 6
java Sender complete_works_of_shakespeare.txt 10 -b 10000 -w 7
java Sender complete_works_of_shakespeare.txt 10 -b 10000 -w 8
java Sender complete_works_of_shakespeare.txt 10 -b 10000 -w 9
java Sender complete_works_of_shakespeare.txt 10 -b 10000 -w 10

java Sender complete_works_of_shakespeare.txt 16 -b 10000 -w 1
java Sender complete_works_of_shakespeare.txt 16 -b 10000 -w 2
java Sender complete_works_of_shakespeare.txt 16 -b 10000 -w 3
java Sender complete_works_of_shakespeare.txt 16 -b 10000 -w 4
java Sender complete_works_of_shakespeare.txt 16 -b 10000 -w 5
java Sender complete_works_of_shakespeare.txt 16 -b 10000 -w 6
java Sender complete_works_of_shakespeare.txt 16 -b 10000 -w 7
java Sender complete_works_of_shakespeare.txt 16 -b 10000 -w 8
java Sender complete_works_of_shakespeare.txt 16 -b 10000 -w 9
java Sender complete_works_of_shakespeare.txt 16 -b 10000 -w 10

REM Test Case $: Shakespeare (50000 bytes)
REM Pipes: 1, 2, 3, 4, 5, 10 ,16
java Sender complete_works_of_shakespeare.txt 1 -b 50000 -w 1
java Sender complete_works_of_shakespeare.txt 1 -b 50000 -w 2
java Sender complete_works_of_shakespeare.txt 1 -b 50000 -w 3
java Sender complete_works_of_shakespeare.txt 1 -b 50000 -w 4
java Sender complete_works_of_shakespeare.txt 1 -b 50000 -w 5
java Sender complete_works_of_shakespeare.txt 1 -b 50000 -w 6
java Sender complete_works_of_shakespeare.txt 1 -b 50000 -w 7
java Sender complete_works_of_shakespeare.txt 1 -b 50000 -w 8
java Sender complete_works_of_shakespeare.txt 1 -b 50000 -w 9
java Sender complete_works_of_shakespeare.txt 1 -b 50000 -w 10

java Sender complete_works_of_shakespeare.txt 2 -b 50000 -w 1
java Sender complete_works_of_shakespeare.txt 2 -b 50000 -w 2
java Sender complete_works_of_shakespeare.txt 2 -b 50000 -w 3
java Sender complete_works_of_shakespeare.txt 2 -b 50000 -w 4
java Sender complete_works_of_shakespeare.txt 2 -b 50000 -w 5
java Sender complete_works_of_shakespeare.txt 2 -b 50000 -w 6
java Sender complete_works_of_shakespeare.txt 2 -b 50000 -w 7
java Sender complete_works_of_shakespeare.txt 2 -b 50000 -w 8
java Sender complete_works_of_shakespeare.txt 2 -b 50000 -w 9
java Sender complete_works_of_shakespeare.txt 2 -b 50000 -w 10

java Sender complete_works_of_shakespeare.txt 3 -b 50000 -w 1
java Sender complete_works_of_shakespeare.txt 3 -b 50000 -w 2
java Sender complete_works_of_shakespeare.txt 3 -b 50000 -w 3
java Sender complete_works_of_shakespeare.txt 3 -b 50000 -w 4
java Sender complete_works_of_shakespeare.txt 3 -b 50000 -w 5
java Sender complete_works_of_shakespeare.txt 3 -b 50000 -w 6
java Sender complete_works_of_shakespeare.txt 3 -b 50000 -w 7
java Sender complete_works_of_shakespeare.txt 3 -b 50000 -w 8
java Sender complete_works_of_shakespeare.txt 3 -b 50000 -w 9
java Sender complete_works_of_shakespeare.txt 3 -b 50000 -w 10

java Sender complete_works_of_shakespeare.txt 4 -b 50000 -w 1
java Sender complete_works_of_shakespeare.txt 4 -b 50000 -w 2
java Sender complete_works_of_shakespeare.txt 4 -b 50000 -w 3
java Sender complete_works_of_shakespeare.txt 4 -b 50000 -w 4
java Sender complete_works_of_shakespeare.txt 4 -b 50000 -w 5
java Sender complete_works_of_shakespeare.txt 4 -b 50000 -w 6
java Sender complete_works_of_shakespeare.txt 4 -b 50000 -w 7
java Sender complete_works_of_shakespeare.txt 4 -b 50000 -w 8
java Sender complete_works_of_shakespeare.txt 4 -b 50000 -w 9
java Sender complete_works_of_shakespeare.txt 4 -b 50000 -w 10

java Sender complete_works_of_shakespeare.txt 5 -b 50000 -w 1
java Sender complete_works_of_shakespeare.txt 5 -b 50000 -w 2
java Sender complete_works_of_shakespeare.txt 5 -b 50000 -w 3
java Sender complete_works_of_shakespeare.txt 5 -b 50000 -w 4
java Sender complete_works_of_shakespeare.txt 5 -b 50000 -w 5
java Sender complete_works_of_shakespeare.txt 5 -b 50000 -w 6
java Sender complete_works_of_shakespeare.txt 5 -b 50000 -w 7
java Sender complete_works_of_shakespeare.txt 5 -b 50000 -w 8
java Sender complete_works_of_shakespeare.txt 5 -b 50000 -w 9
java Sender complete_works_of_shakespeare.txt 5 -b 50000 -w 10

java Sender complete_works_of_shakespeare.txt 10 -b 50000 -w 1
java Sender complete_works_of_shakespeare.txt 10 -b 50000 -w 2
java Sender complete_works_of_shakespeare.txt 10 -b 50000 -w 3
java Sender complete_works_of_shakespeare.txt 10 -b 50000 -w 4
java Sender complete_works_of_shakespeare.txt 10 -b 50000 -w 5
java Sender complete_works_of_shakespeare.txt 10 -b 50000 -w 6
java Sender complete_works_of_shakespeare.txt 10 -b 50000 -w 7
java Sender complete_works_of_shakespeare.txt 10 -b 50000 -w 8
java Sender complete_works_of_shakespeare.txt 10 -b 50000 -w 9
java Sender complete_works_of_shakespeare.txt 10 -b 50000 -w 10

java Sender complete_works_of_shakespeare.txt 16 -b 50000 -w 1
java Sender complete_works_of_shakespeare.txt 16 -b 50000 -w 2
java Sender complete_works_of_shakespeare.txt 16 -b 50000 -w 3
java Sender complete_works_of_shakespeare.txt 16 -b 50000 -w 4
java Sender complete_works_of_shakespeare.txt 16 -b 50000 -w 5
java Sender complete_works_of_shakespeare.txt 16 -b 50000 -w 6
java Sender complete_works_of_shakespeare.txt 16 -b 50000 -w 7
java Sender complete_works_of_shakespeare.txt 16 -b 50000 -w 8
java Sender complete_works_of_shakespeare.txt 16 -b 50000 -w 9
java Sender complete_works_of_shakespeare.txt 16 -b 50000 -w 10
