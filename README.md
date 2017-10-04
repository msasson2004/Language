# Language
Fixing language hierarchy files for Yad Vashem 

This little bit of code reads tab delimited files containing information about language hierarchy in Yad Vashem records 
and checks the structures for errors. The code reads the records and verifies languages that appear in the lower levels,  
are contained in the higher levels.

The Java code requires only single parameter which is the name of input directory. In my code I conveniently called that 
directory 'input'. The directory can contain a number of files. The code creates an output directory which is the name 
of the input directory with the addition of '_result'. In my example the out will be called input_result. I create in that 
directory a file containing the fixed hierarchy with the same name.

The code uses Java HashMap to store the information it reads. The only drawback of hash tables, are size, but it would 
take a few billions of records to see failures. Hash tables allow for fast access to the data. The program runs very fast. 

The program assumes that each file stands by itself and data is not shared between files. However if this is the intended 
result, the code can easily be modified to share data across files.

