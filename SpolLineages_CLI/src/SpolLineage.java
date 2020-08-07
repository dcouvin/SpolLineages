/**
 * #################################################################################################
 * #  SpolLineages: simple algorithms for the identification of Mycobacterium tuberculosis complex
 * #   lineages using classical genotyping methods and updated rules
 * # 
 * #  Copyright (C) 2019 - SpolLineages team
 * #  (Institut Pasteur de la Guadeloupe, Université des Antilles)
 * #
 * #  See the COPYRIGHT file for details.
 * #
 * #  SpolLineages is distributed under the terms of the GNU General Public License
 * #  (GPLv3). See the COPYING file for details.  
 * #################################################################################################
 */

/**
 * @authors dcouvin
 * SpolLineage version 1.0.1
 * Date: 08/11/2019
 */


import java.io.*;

public class SpolLineage {

	/**
	 * @param args
	 */
	
	public static final String VERSION = "1.0.1";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("################################################################");
		System.out.println("# --> Welcome to SpolLineages (version " + VERSION + ")");
		System.out.println("################################################################");
		System.out.println();
		
		// The time before the run
	    long start = System.currentTimeMillis();
		//Default values
		//boolean useStackTB = false;
		//boolean country = false;
		//int numberoptions = 0;
		String delimiter = ";";
		 // Input file and optional output file
	    String inputFileName = "";
	    String outputFileName = "resultFile.csv";
	    int numberLines = 0;
	    //Boolean variables for options (then stored in table)
	    boolean [] tabBool = new boolean[10];
	    boolean binaryRules = true;
	    boolean decisionTree = false;
	    boolean geneticAlgo = false;
	    boolean curatedSITVIT2 = false;
	    boolean snpBarcodeMatching = false;
	    boolean ruleTB = false;
	    boolean getSIT = false;
	    boolean countryDistrib = false;
	    boolean quiet = false;
	    String pathToDTfiles = "";
	    String pathToEAfiles = "";
	    
		if (args.length == 0)
	    {
	         //printHelp();
	         System.out.println("No arguments specified.");
	         System.out.println("Try 'java -jar spollineages.jar -h' for more information.");
	         System.exit(1);
	    }
		else if (args[0].equals("--help") || args[0].equals("-h"))
	    {
	         printHelp();
	         System.exit(0);
	    }
		else if (args[0].equals("--version") || args[0].equals("-v"))
	    {
	         printVersion();
	         System.exit(0);
	    }
		else if(args.length ==1 && !args[0].equals("--version") && !args[0].equals("-v") && !args[0].equals("--help") && !args[0].equals("-h")){
			inputFileName = args[0];
			if(inputFileName.equals(null) || inputFileName.equals("") || inputFileName.isEmpty()){
				System.out.println("The input file must be specified.");
				System.out.println("Try 'java -jar spollineages.jar -h' for more information.");
				System.exit(1);
			}
		}
		else if(args.length>1){
			
				for(int i=0; i<args.length; i++){
					if(args[i].equals("--input") || args[i].equals("-i")){
						inputFileName = args[i+1];
						if(inputFileName.equals(null) || inputFileName.equals("") || inputFileName.isEmpty()){
							System.out.println("The input file must be specified.");
							System.out.println("Try 'java -jar spollineages.jar -h' for more information.");
							System.exit(1);
						}
						else {
							System.out.println("Input file name is:" +inputFileName);
						}
					}
					else if (args[i].equals("--output") || args[i].equals("-o")){
						outputFileName = args[i+1];
						if(outputFileName.equals(null) || outputFileName.equals("") || outputFileName.isEmpty()){
							outputFileName = "resultFile.csv";
						}
						//sSystem.out.println("Output file name is:" +outputFileName);
					}
					else if (args[i].equals("--delimiter") || args[i].equals("-delim")){
						delimiter = args[i+1];
						if(delimiter.equals(null) || delimiter.equals("") || delimiter.isEmpty()){
							delimiter = ";";
						}
						else if(delimiter.equals("tab") || delimiter.equals("t")){
							delimiter = "\t";
						}
					}
					else if (args[i].equals("--BinaryRules") || args[i].equals("-B")){
						//--BinaryRules or -B
						binaryRules = true;
					}
					else if (args[i].equals("--DecisionTree") || args[i].equals("-D")){
						//--DecisionTree or -D
						decisionTree = true;
					}
					else if (args[i].equals("--pathToDTfiles") || args[i].equals("-pDT")){
						//--DecisionTree or -D
						pathToDTfiles = args[i+1];
					}
					//--pathToDTfiles or -pDT
					else if (args[i].equals("--GeneticAlgo") || args[i].equals("-G") || args[i].equals("--EvolutionAlgo")  || args[i].equals("-E")){
						//--GeneticAlgo or -G
						geneticAlgo = true;
					}
					else if (args[i].equals("--pathToEAfiles") || args[i].equals("-pEA")){
						//--DecisionTree or -D
						pathToEAfiles = args[i+1];
					}
					else if (args[i].equals("--curatedClade") || args[i].equals("-cur")){
						//--curatedClade or -cur
						curatedSITVIT2 = true;
					}
					else if (args[i].equals("--SNPlineage") || args[i].equals("-S")){
						//--SNPlineage or -S
						snpBarcodeMatching = true;
					}
					else if (args[i].equals("--useRuleTB") || args[i].equals("-uR")){
						//--useRuleTB or -uR
						ruleTB = true;
					}
					else if (args[i].equals("--SIT") || args[i].equals("-sit")){
						//--SIT or -sit
						getSIT = true;
					}
					else if (args[i].equals("--country") || args[i].equals("-c")){
						//--country or -c
						countryDistrib = true;
					}
					else if (args[i].equals("--all") || args[i].equals("-a")){
						//--all or -a
						binaryRules = true;
						//decisionTree = true;
						//geneticAlgo = true;
						curatedSITVIT2 = true;
						snpBarcodeMatching = true;
						ruleTB = true;
						getSIT = true;
						countryDistrib = true;
					}
					else if (args[i].equals("--quiet") || args[i].equals("-q")){
						//--country or -c
						quiet = true;
					}
			    	
			    }
			
		}
	   
		if(decisionTree == true && pathToDTfiles.equals("") ){
			System.out.println("The path to Decision Tree files must be specified.");
			System.out.println("Try 'java -jar spollineages.jar -h' for more information.");
			System.exit(1);
		}
		
		if(geneticAlgo == true && pathToEAfiles.equals("") ){
			System.out.println("The path to Evolutionary Algorithm's Binary Mask files must be specified.");
			System.out.println("Try 'java -jar spollineages.jar -h' for more information.");
			System.exit(1);
		}
		
	    System.out.println("Output file name is:" +outputFileName);
	    System.out.println("-----------------------------------------");
	    
	    //fill boolean table
	    tabBool[0] = binaryRules;
	    tabBool[1] = decisionTree;
	    tabBool[2] = geneticAlgo;
	    tabBool[3] = curatedSITVIT2;
	    tabBool[4] = snpBarcodeMatching;
	    tabBool[5] = ruleTB;
	    tabBool[6] = getSIT;
	    tabBool[7] = countryDistrib;
	    tabBool[8] = quiet;
		
	    
	    File inputFile = new File(inputFileName);
	    CSVReader csvReader = new CSVReader(inputFile.getPath(), outputFileName, delimiter, tabBool, pathToDTfiles, pathToEAfiles);
	    
	    
	      if (!inputFile.exists())
	      {
	         System.out.println("Input file " + inputFile.getPath() + " does not exist.");
	         System.out.println("Try 'java -jar spollineages.jar -h' for more information.");
	         System.exit(1);
	      }
	      else{
	    	  //System.out.println("InputFile Path = "+ inputFile.getPath());
	    	  try {
	    		  numberLines = csvReader.readwriteCSV();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      
	      //Finishing time
	      long end = System.currentTimeMillis();
	      //finding the time difference and converting it into seconds
	      float sec = (end - start) / 1000F; System.out.println();
	    
	      System.out.println("-----------------------------------------");
	      System.out.println("The total number of analyzed lines is: "+numberLines);
	      System.out.println("Thank you for using SpolLineages!");
	      System.out.println("The script lasted "+sec +" second(s)");
	}
	
	public static void printHelp()
	   {
	      System.out.println("SpolLineages: simple algorithms for the identification of MTBC lineages from classical genotyping data (Spoligotypes and MIRU-VNTRs)");
	      System.out.println();
	      System.out.println("Usage:    java -jar spollineages.jar [options] -i inputfile.txt [-o outputfile.txt]");
	      System.out.println();
	      
	      System.out.println("IN/OUT:   --input or -i         Option allowing to select user input file. This option must be satisfied.");
	      //System.out.println();
	      System.out.println("          --output or -o        Option allowing to select the name of the output result file. (Default: resultFile.csv)");
	      //System.out.println();
	      System.out.println("          --delimiter or -delim     Option allowing to set a delimiter for analyzed files. (Default: ';')");
	      System.out.println();
	      System.out.println("Other options:  --BinaryRules or -B              Option allowing to use SITVIT binary rules to predict lineages from spoligotypes. Default: true");
	      System.out.println("                --DecisionTree or -D              Option allowing to use a Decision Tree to predict lineages from spoligotypes. Default: false");
	      System.out.println("                --EvolutionAlgo or -E              Option allowing to use an Evolutionary Algorithm (EA) to predict lineages from spoligotypes. Default: false");
	      System.out.println("                --pathToDTfiles or -pDT              Option allowing to indicate the path to Decision Tree files (e.g. C:/Users/dcouvin/workspace/)");
	      System.out.println("                --pathToEAfiles or -pEA              Option allowing to indicate the path to Evolutionary Algorithm's Binary Mask folder (e.g. C:/Users/dcouvin/Documents/IPG_WORK/Bioinformatics_tools/C/Binary_Mask2/)");
	      System.out.println("                --curatedClade or -cur             Option allowing to print curated Clade information obtained from SITVIT2 database. Default: false");
	      System.out.println("                --SNPlineage or -S              Option allowing to print corresponding lineage according to Single Nucleotide Polymorphism (SNP) barcode. Default: false");
	      System.out.println("                --useRuleTB or -uR          Option allowing to use refined rules of RuleTB when MIRU-VNTR field is filled. Default: false");
	      System.out.println("                --SIT or -sit             Option allowing to print corresponding Spoligotype International Type (SIT) according to SITVIT2 in the output file. Default: false");  
	      System.out.println("                --country or -c             Option allowing to print country distribution according to SITVIT2 in the output file. Default: false");
	      System.out.println("                --all or -a              Option allowing to activate most boolean options (i.e. -cur, -c, -sit, -uR, -S, -B. Default: false");
	      System.out.println("                --quiet or -q              Option allowing to run the program silently");
	      System.out.println();
	      System.out.println("Help/Version:");
	      System.out.println("          -h or --help          Display this help message");
	      System.out.println("          -v or --version       Display version information");
	      System.out.println();
	      System.out.println("Examples: java -jar spollineages.jar -i inputFile.csv");
	      System.out.println("          java -jar spollineages.jar -delim tab -i inputFile.tsv -o outputFile.tsv");
	      System.out.println("          java -jar spollineages.jar -delim tab -i inputFile.tsv -o outputFile.xls");
	      System.out.println("          java -jar spollineages.jar -delim , -i inputFile.csv -o outputFile.csv -a");
	      System.out.println("          java -jar spollineages.jar -delim ; -D -pDT C:/Users/dcouvin/workspace/ -uR -sit -i inputFile.csv -o outputFile.csv");
	      System.out.println("          java -jar spollineages.jar -delim ; -E -pEA C:/Users/dcouvin/Documents/IPG_WORK/Bioinformatics_tools/C/Binary_Mask2/ -i inputFile.csv -o outputFile.csv");
	      System.out.println("          java -jar spollineages.jar -h");
	      System.out.println("          java -jar spollineages.jar -v");
	      System.out.println();
	      System.out.println();
	   }

	public static void printVersion()
	   {
	      System.out.println("SpolLineages version " + VERSION);
		  System.out.println("SpolLineages: simple algorithms for the identification of MTBC lineages from classical genotyping data (Spoligotypes and MIRU-VNTRs)");
	      System.out.println("Copyright (C) 2020 SpolLineages team");
	      System.out.println("Institut Pasteur de la Guadeloupe - WHO Supranational TB Reference Laboratory, Tuberculosis and Mycobacteria Unit");
	      System.out.println("Universite des Antilles - Laboratoire de Mathematiques Informatique et Applications (LAMIA)");
	      System.out.println();
	      //System.out.println("XXXXXXXXX et al., SpolLineages: simple algorithms for the identification of Mycobacterium tuberculosis complex");
	      //System.out.println("lineages using classical genotyping methods and updated rules");
	      //System.out.println("XXX journal.");
	      //System.out.println("Distributed under the GNU General Public License version 3");
	      System.out.println();
	      System.out.println();
	   }

}