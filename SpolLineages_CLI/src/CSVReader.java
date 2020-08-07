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

import java.io.*;
import java.util.*;


public class CSVReader {
	
	String fileName = "";
	String outputFile = "";
	ArrayList<String> records = new ArrayList<String>();
	FileWriter csvWriter;
	String delimiter = ";";
	boolean [] tabBool = new boolean[10];
	ArrayList<String> spoligoList = new ArrayList<String>();
	String pathToDTfiles = "";
	String pathToEAfiles = "";
	
	//Constructor
	public CSVReader(String file, String output, String del, boolean [] tab, String pathToDT, String pathToEA)
	{
		fileName = file;
		outputFile = output;
		delimiter = del;
		tabBool = tab;
		pathToDTfiles = pathToDT;
		pathToEAfiles = pathToEA;
	}
	
	
	public int readwriteCSV() throws Exception{
		int lines = 0;
	try  {
	    String line = "";
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    
	    try {
	    csvWriter = new FileWriter(outputFile);
	    csvWriter.append("StrainID");
	    csvWriter.append(delimiter);
	    csvWriter.append("Spoligo Binary");
	    csvWriter.append(delimiter);
	    csvWriter.append("Spoligo Octal");
	    csvWriter.append(delimiter);
	    csvWriter.append("MIRU-VNTR");
	    csvWriter.append(delimiter);
	    csvWriter.append("Lineage (binary rules)");
	    csvWriter.append(delimiter);
	    csvWriter.append("SubLineage (binary rules)");
	    csvWriter.append(delimiter);
	    csvWriter.append("Other candidate lineages");
	    csvWriter.append(delimiter);
	    csvWriter.append("Lineage (decision tree)");
	    csvWriter.append(delimiter);
	    csvWriter.append("Lineage (evolutionary algorithm)");
	    csvWriter.append(delimiter);
	    csvWriter.append("Curated lineage (SITVIT2)");
	    csvWriter.append(delimiter);
	    csvWriter.append("SNP-based lineage"); // According to SNP barcode [Coll et al., 2014]
	    csvWriter.append(delimiter);
	    csvWriter.append("MIRU-VNTR based lineage (RuleTB)");
	    csvWriter.append(delimiter);
	    csvWriter.append("SIT");
	    csvWriter.append(delimiter);
	    //csvWriter.append("MIT");
	    //csvWriter.append(delimiter);
	    csvWriter.append("Country Distribution (SITVIT2)");
	    csvWriter.append("\n");
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    try {
			while ((line = br.readLine()) != null) {
				if(!line.isEmpty() || line.length() != 0){
				    records.add(line.toString());
					if(!line.toString().startsWith("#")){
						String[] valuesDTEA = line.toString().split(delimiter); 
						String tmpSpol = "";
						
						if(valuesDTEA[1].length() == 15){
							//Spoligo is octal
							tmpSpol = valuesDTEA[1];
							tmpSpol=Spoligotype43.octToBin(tmpSpol);
							tmpSpol = tmpSpol.replace("n", "1").replaceAll("o", "0");
						}
						else if(valuesDTEA[1].length() == 43){
							if(valuesDTEA[1].contains("n") || valuesDTEA[1].contains("o")){
								
								tmpSpol = valuesDTEA[1].replace("n", "1").replaceAll("o", "0");
							}
							else if(tabBool[1] == true && valuesDTEA[1].contains(".")){
								tmpSpol = valuesDTEA[1].replace("n", "1").replaceAll("o", "0");
								tmpSpol = tmpSpol.replace(".", "1");
							}
							else{
								tmpSpol = valuesDTEA[1];
							}
							
						}
						
						spoligoList.add(tmpSpol);
					
					}
				    //Quiet?
					//if(tabBool[8] == false){
						//System.out.println("Line: "+line.toString());
					//}
				}
			}
			
			//Decision tree and Evolutionary algo
			HashMap<String,String> myHDT = new HashMap<String,String>();
			HashMap<String,String> myHEA = new HashMap<String,String>();
			
			if(tabBool[1] == true){
				myHDT = DecisionTree.getLineageDTEAMask(spoligoList,pathToDTfiles);
			}
			
			if(tabBool[2] == true){
				myHEA = BinaryMask.getLineageEAMask(spoligoList,pathToEAfiles);
			}
			
			for (String str:records) {
				if(!str.startsWith("#")){
					String[] values = str.split(delimiter); // "\t" replaced by ";"
					String spoligoBinary = "";
					String spoligoOctal = "";
					String miru = "";
					//String strainID="";
					//increment lines
					lines++;
					
					//System.out.println("Values table size="+values.length); //Show spoligo value in input file
					
					/*if(values[1].length()==0){
						spoligoOctal = "ND";
						spoligoBinary = "ND";
					}*/
					if(values.length == 0){
						System.out.println("The input file must contain values.");
						System.out.println("Try 'java -jar spollineages.jar -h' for more information.");
						System.exit(1);
					}
					else if(values.length == 1){
						spoligoOctal = "ND";
						spoligoBinary = "ND";
						miru = "ND";
					}
					else{
						
						if(values.length <= 2){
							miru = "ND";
						}
						else{
							miru = values[2].replace("\"", "");
						}
						values[1] = values[1].replace("\"", "");
						if(values[1].length() == 15){
							//Spoligo is octal
							spoligoOctal = values[1];
							spoligoBinary=Spoligotype43.octToBin(spoligoOctal);
						}
						else if(values[1].length() == 43){
							if(values[1].contains("1") || values[1].contains("0")){
								spoligoBinary = values[1].replace("1", "n").replaceAll("0", "o");
								spoligoOctal = Spoligotype43.binToOct(spoligoBinary);
							}
							else{
								spoligoBinary = values[1];
								spoligoOctal = Spoligotype43.binToOct(spoligoBinary);
							}
						}
						
						
						
						//Get Lineage/SubLineage and SNP-based lineage
						String subLineage = "ND";
						String lineage = "ND";
						String lineageDT = "ND";
						String lineageEA = "ND";
						String snpLineage = "ND";
						String sit = "ND";
						String cladeExpert = "ND";
						String countries = "ND";
						String ruleTB = "ND";
						ArrayList<String> candidateLineages = new ArrayList<String>();
						
						//Begin binary rules
						if(tabBool[0] == true){
							HashMap<String,String> myH = new HashMap<String,String>();
							myH = Spoligotype43.getLineage();
							//Map Lineage
							//HashMap<String,String> myMapLineage = new HashMap<String,String>();
		
							//Récupération clé et calcul du score
							Set<String> setKeyRules = myH.keySet();
							Object[] obRules = setKeyRules.toArray();
							//String score="../..";
							
							String potentialLineage = "";
							
							//String cladetest = "";
							//System.out.print("matched lineages: ");
							for(int k=0;k<obRules.length;k++){
								if(spoligoBinary.matches(obRules[k].toString())){
									//System.out.print(myH.get(obRules[k].toString())+ " - ");
									
									//cladetest = cladetest.concat(myH.get(obRules[k].toString())+" - ");
									
									candidateLineages.add(myH.get(obRules[k].toString()));
									potentialLineage = myH.get(obRules[k].toString());
									//myMapLineage.put(spoligoBinary,myH.get(obRules[k].toString()));
									//TO DO SCORE CALCULATION
							
								}
							}
							
							//PriorityRules pr = new PriorityRules();
							potentialLineage = PriorityRules.choseLineage(candidateLineages);
							
							
							//Remove the potentialLineage in the list of condidateLineages
							candidateLineages.remove(potentialLineage);
							
							//System.out.println("END");
							//System.out.println();
							//System.out.println();
							
							subLineage = potentialLineage;//subLineage = myMapLineage.get(spoligoBinary);
							if(subLineage==null || subLineage.equals(null) || subLineage.isEmpty()){
								subLineage="Unknown";
							}
							
							lineage = Spoligotype43.getLineageFromSublineage(subLineage);
						}
						else{
							tabBool[4] = false; //deactivate SNP lineage (when no Binary search) 
						}
						//End binary rules
						
						String spolBinaryDTEA = spoligoBinary;
	        			
	        			spolBinaryDTEA=spolBinaryDTEA.replace("n","1");
	        			spolBinaryDTEA=spolBinaryDTEA.replace("o","0");
						
						//Begin Decision tree tabBool[1]
						if(tabBool[1] == true){
							//myHDT = DecisionTree.getLineageDTEAMask(spoligoList);
							if(spolBinaryDTEA.contains(".")){
								
								spolBinaryDTEA = spolBinaryDTEA.replace(".", "1");
								System.out.println("SpolDTEA= "+spolBinaryDTEA);
							}
							lineageDT = myHDT.get(spolBinaryDTEA);
						}
						//End Decision tree
						
	        			//Begin Genetic algorithm tabBool[2]
	        			if(tabBool[2] == true){
	        				//myHEA = BinaryMask.getLineageEAMask(spoligoList);
	        				lineageEA = myHEA.get(spolBinaryDTEA);
	        			}
						//End Genetic algorithm
						
						
						//Begin SNP
						if(tabBool[4] == true){
							snpLineage = Spoligotype43.getSNPbasedLFromLineage(lineage);
						}
						//End SNP
						
						//Begin SIT
						if(tabBool[6] == true){
							sit = Spoligotype43.getSITFromSpoligo(spoligoOctal);
						}
						//End SIT
						
						//Begin Curated
						if(tabBool[3] == true){
							if(!Spoligotype43.getCladeExpert(spoligoOctal).equals("ND")){
								cladeExpert = Spoligotype43.getCladeExpert(spoligoOctal);
							}
							else{
								cladeExpert = Spoligotype43.getCladeExpert2(spoligoOctal);
							}
						}
						//End Curated
						
						//Begin countries
						if(tabBool[7] == true){
							if(!Spoligotype43.getCountry(sit).equals("ND")){
								countries = Spoligotype43.getCountry(sit);
							}
							else{
								countries = Spoligotype43.getCountryOrphan(spoligoOctal);
							}
						}
						//End countries
						
						//Begin RuleTB with MIRU Object
						if(tabBool[5] == true){
							if(miru.length()==24){
								//System.out.println("MIRU length == 24");
								ruleTB = MIRU.getMIRULineageRuleTB(miru, 24);
							}
						}
						//End RuleTB
						
						/*if(values[2].equals("") || values[2] == null){
							miru = "ND";
						}*/
						
						//Begin quiet
						if( tabBool[0] == true && tabBool[8] == false ){
							System.out.println("Line: "+values[0]+delimiter+spoligoBinary+delimiter+subLineage);
						}
						//End Quiet
						
						
						csvWriter.append(values[0]);   // StrainID //csvWriter.append(String.join(",", str));
						csvWriter.append(delimiter);   
						csvWriter.append(spoligoBinary);   // SpoligoBinary
						csvWriter.append(delimiter);
						csvWriter.append("'"+spoligoOctal);   // SpoligoOctal
						csvWriter.append(delimiter);
						csvWriter.append("'"+miru);   // MIRU
						csvWriter.append(delimiter);
						csvWriter.append(lineage);   // Lineage (
						csvWriter.append(delimiter);
						csvWriter.append(subLineage);   // SubLineage
						csvWriter.append(delimiter);
						csvWriter.append(candidateLineages.toString().replace(",",""));   // candidate Lineages
						csvWriter.append(delimiter);
						csvWriter.append(lineageDT);   // Lineage based on decision tree algorithm
						csvWriter.append(delimiter);
						csvWriter.append(lineageEA);   // Lineage based on genetic algorithm
						csvWriter.append(delimiter);
						csvWriter.append(cladeExpert);   // Expert Clade/Curated (SITVIT2)
						csvWriter.append(delimiter);
						csvWriter.append(snpLineage);   // SNP-based Lineages
						csvWriter.append(delimiter);
						csvWriter.append(ruleTB);   // MIRU-VNTR based lineage (RuleTB/StackTB)
						csvWriter.append(delimiter);
						csvWriter.append(sit); //SIT 
						csvWriter.append(delimiter);
					    //csvWriter.append("ND"); //MIT
					    //csvWriter.append(delimiter);
					    csvWriter.append(countries.replace(",","")); //Country Distribution (SITVIT2)
						csvWriter.append("\n");
					}
				}
			}
			//csvWriter.append("\n");
			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	
	//Return integer corresponding to the number of lines
	return lines;
	}

}

