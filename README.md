# MapReduce WordCount

Re-write the common WordCount.java program. Instead of counting occurrences for
each word in a given input file, your program counts the occurrences of each word
length.

For example, when the content of the input file is:

	Hello World	
	Hello Hadoop
	Hello Mapreduce

Then the output should be:

	5 4
	6 1
	9 1
Use the provided “input.txt” as input file for your testing.

[A clue: You only need to modify the map function. Instead of using word as key, you
need to use the word length as the key for mapping. Also note that any key has to be of
string type. You can google “how to get the length of a String in Java” and “how to
convert from int to String in Java” to look for functions for the purpose.]
