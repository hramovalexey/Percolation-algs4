# Percolation-algs4
My solution for programming assignment "Percolation" at Algorithms, Part I by Princeton University.
Solution uses other classes provided by Princeton: StdOut.java, StdRandom.java, StdStats.java, WeightedQuickUnionUF.java  
Programming assignment itself can be found here: https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php

Note:
I've got 95% at autograder due to using of double instance WeightedQuickUnionUF object and failed last memory test. Exists another solution without double instance and without memory test failing. But in my opinion that solution is likely suitable for graph chapter but not union (brief: you should track "full"/"not full" state for every new opened cell using method similar to depth first search).
