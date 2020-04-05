# <img src="http://www.pasteur-guadeloupe.fr:8081/SpolLineages/images/spollineages2.png" title="SpolLineages"></img>
Rapid and accurate prediction of *Mycobacterium tuberculosis* [complex](https://en.wikipedia.org/wiki/Mycobacterium_tuberculosis_complex) (MTBC) lineages
## Synopsis
[SpolLineages](http://www.pasteur-guadeloupe.fr:8081/SpolLineages) is a software tool mainly written in Java allowing to predict Mycobacterium tuberculosis complex lineages from spoligotyping or MIRU-VNTR typing patterns using various methods ([SITVIT2](http://www.pasteur-guadeloupe.fr:8081/SITVIT2) binary rules, [RuleTB](https://doi.org/10.1016/j.meegid.2018.06.029) refined rules, [Decision tree](https://en.wikipedia.org/wiki/Decision_tree) and/or [Evolutionary algorithm](https://en.wikipedia.org/wiki/Evolutionary_algorithm)).
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
-run a simple analysis with the provided example (CSV file separated by semicolons)
```bash
java -jar spollineages.jar -i example.csv -o result_example.csv
```
