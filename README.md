# <img src="http://www.pasteur-guadeloupe.fr:8081/SpolLineages/images/spollineages2.png" title="SpolLineages"></img>
Rapid and precise prediction of *Mycobacterium tuberculosis* [complex](https://en.wikipedia.org/wiki/Mycobacterium_tuberculosis_complex) (MTBC) spoligotype families
## Synopsis
[SpolLineages](http://www.pasteur-guadeloupe.fr:8081/SpolLineages) is a software tool mainly written in Java allowing to predict Mycobacterium tuberculosis complex families from spoligotyping or MIRU-VNTR typing patterns using various methods ([SITVIT2](http://www.pasteur-guadeloupe.fr:8081/SITVIT2) binary rules, [RuleTB](https://doi.org/10.1016/j.meegid.2018.06.029) refined rules, [Decision tree](https://en.wikipedia.org/wiki/Decision_tree) and/or [Evolutionary algorithm](https://en.wikipedia.org/wiki/Evolutionary_algorithm)).
<img src="http://www.pasteur-guadeloupe.fr:8081/SpolLineages/images/workflow.png" title="Workflow"></img>
## Requirements
Java version 6 (or later) programming language must be available in your system to run SpolLineages. Otherwise, you can use the corresponding online tool. Further instructions on how to install Java are available [here](https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_Howto.html).
To check if Java is installed on your machine, you can run the following command:
```bash
java -version
```
## Instructions on how to use the program
Users can use the provided executable [JAR](https://en.wikipedia.org/wiki/JAR_(file_format)) file and run the following commands for:

-help on this software
```bash
java -jar spollineages.jar -h
```
-version
```bash
java -jar spollineages.jar -v
```
-before running the evolutionary algorithm (binary mask) prediction, the following commands must be typed (when using Windows, users can install [MinGW](http://www.mingw.org/) or [Cygwin](https://www.cygwin.com/) in order to use the gcc command):
```bash
cd Binary_Mask2
```
(when using Unix)
```bash
gcc Mask2.c -o Mask2
```
(when using Windows)
```bash
gcc Mask2.c -o Mask2.exe
```
or
```bash
cd C:\MINGW\bin
gcc.exe path\to\Binary_Mask2\Mask2.c -o path\to\Binary_Mask2\Mask2.exe
```

-Then the Binary Mask folder and Mask2 file should be exported to $PATH (for Unix users) as follows:
```bash
export PATH="/path/to/SpolLineages/Binary_Mask2/Mask2:$PATH"
export PATH="/path/to/SpolLineages/Binary_Mask2:$PATH"
```
-run a simple analysis with the provided example (CSV file separated by semicolons)
```bash
java -jar spollineages.jar -i example.csv -o result_example.csv
```
-run an example using decision tree or evolutionary algorithm (binary mask) predictions:
```bash
java -jar spollineages.jar -i example2.csv -o result_DT.csv -D -pDT C:/Users/dcouvin/workspace/SpolLineages/Decision_Tree/
java -jar spollineages.jar -i example2.csv -o result_EA.csv -E -pEA C:/Users/dcouvin/workspace/SpolLineages/Binary_Mask2/
```
-run an example using both decision tree and evolutionary algorithm predictions as well as all other options:
```bash
java -jar spollineages.jar -i example2.csv -o result_DT.csv -D -pDT C:/Users/dcouvin/workspace/SpolLineages/Decision_Tree/ -E -pEA C:/Users/dcouvin/workspace/SpolLineages/Binary_Mask2/ -a
```

## Citation
If you use SpolLineages in your work, please cite:

Couvin D, Segretier W, Stattner E, Rastogi N. Novel methods included in SpolLineages tool for fast and precise prediction of Mycobacterium tuberculosis complex spoligotype families. Database (Oxford). 2020 Dec 15;2020:baaa108. [https://doi.org/10.1093/database/baaa108](https://doi.org/10.1093/database/baaa108) PMID: 33320180
