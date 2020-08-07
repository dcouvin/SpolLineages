import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DecisionTree {
	/**
	 * Decision Tree lineage prediction
	 * @author Erick Stattner
	 * 
	 */
	
	public static Evaluation classify(Classifier model, Instances trainingSet, Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);
 
		model.buildClassifier(trainingSet);
		evaluation.evaluateModel(model, testingSet);
 
		return evaluation;
	}
	
	public static Evaluation classifyWithModel(Classifier model, Instances trainingSet, Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);
		evaluation.evaluateModel(model, testingSet);
		return evaluation;
	}
	
	public static String transformSpolToDTEAmask (String spol){
		String outSpol = spol;
		outSpol = spol.replaceAll("1", "o");
		outSpol = outSpol.replaceAll("0", "n");
		outSpol = outSpol.replaceAll("o", "0");
		outSpol = outSpol.replaceAll("n", "1");
		return outSpol;
	}
	
	public static String transformDTEAmaskSpolToBasic (String spol){
		String outSpol = spol;
		outSpol = spol.replaceAll("1", "o");
		outSpol = outSpol.replaceAll("0", "n");
		outSpol = outSpol.replaceAll("o", "0");
		outSpol = outSpol.replaceAll("n", "1");
		return outSpol;
	}
	
	public static HashMap<String,String> getLineageDTEAMask(ArrayList<String> inSpoligos, String pathToDTfiles) throws Exception{
		//Creation du modele
		//String racine = "C:/Users/dcouvin/workspace/";
		String racine = pathToDTfiles;
		//String spolForMask = "";
		Instances dataApp = new DataSource(racine+"A_DATASET_APPRENTISSAGE.csv").getDataSet();
		
		//HashMap storing the results
		HashMap<String,String> myHDTEA = new HashMap<String,String>();
		//HashMap<String,String> mySpolMask = new HashMap<String,String>();
		//ArrayList<String> predictions = new ArrayList<String>();
		
		//Definition de la classe
		if (dataApp.classIndex() == -1){
			dataApp.setClassIndex(dataApp.numAttributes() - 1);
		}
		
		J48 tree = new J48();
		tree.setOptions(weka.core.Utils.splitOptions("-C 0.25 -M 2"));
		tree.buildClassifier(dataApp);
		
		//older for loop : for(int i = 1; i <= 10; i++){
		//for(int i = 1; i < 2; i++){
			//int nb = i*50000;
			//int nb = 1;
			//long 		begin 	= System.currentTimeMillis();
			
			
			//Get data from example.csv
			//BufferedReader fIn = new BufferedReader(new FileReader(racine+"example2.csv"));
			
			//ArrayList<String> inSpoligos = new ArrayList<String>();
			
			//inSpoligos.add("1111111111111111111111111111111111000000000");
			//inSpoligos.add("1110000000001111101111111111111100001110000");
			
			ArrayList<String> basicSpol = new ArrayList<String>();
			ArrayList<String> newLines = new ArrayList<String>();
			//ArrayList<String> spolMaskLines = new ArrayList<String>();
			
			int nbTest = inSpoligos.size();
			//Lecture de toutes les lignes
			//String ligne = fIn.readLine();
			//while(ligne != null  && !ligne.equals(null) && !ligne.isEmpty()){
			
			for (int i=0; i<nbTest; i++) 
			{
				//l.add(ligne);
				//StringTokenizer tok = new StringTokenizer(ligne, ";");
				//split string
				
				//String[] parts = ligne.split(";");
				//String part1 = parts[0]; // 004
				//String part2 = parts[1]; // Spoligo
				
				String part2 = inSpoligos.get(i); // Spoligo
				
				//Suppression de la premiere colonne dans le fichier test
				//tok.nextToken();
				//Reformatage du fichier test et sauvegarde
				//String sep = tok.nextToken(); //.replace("1", "0");
				
				basicSpol.add(part2);  // storing basic spoligotypes
				
				String currentSpol = transformSpolToDTEAmask(part2);
				//currentSpol = currentSpol.replaceAll("0", "n");
				//currentSpol = currentSpol.replaceAll("o", "0");
				//currentSpol = currentSpol.replaceAll("n", "1");
				//spolForMask = currentSpol;
				
				//spolMaskLines.add(spolForMask);
				
				
				//System.out.println("PART2= "+part2);
				//sep = sep.replace("0","1");
				//for(int k = 0; k < part2.length(); k++){
					//sep.charAt(k)
					currentSpol = currentSpol.replace("0", "\"0\",").replaceAll("1", "\"1\",");                 //("\""+part2.charAt(k)+"\""+(k!=42?",":",\"?\""));
					currentSpol = currentSpol+"\"?\"\n";
					//System.out.println(sep.charAt(k));
				//}
				newLines.add(currentSpol);
				//Ligne suivante
				//ligne = fIn.readLine();
				//System.out.println("ligne= "+ligne );
				//nbTotal++;
				//nbTest++;
			}
			
			//System.out.println("first newline is: "+newLines.get(0));
			//System.out.println("Nb test is: "+nbTest);
			
			//System.out.println("MyHashMask: "+mySpolMask);
			
			//get nbTotal
			BufferedReader fSet = new BufferedReader(new FileReader(racine+"A_DATASET_TEST_1.csv"));
			int nbTotal = 0;
			String ligne = fSet.readLine();
			while(ligne != null && !ligne.equals(null) && !ligne.isEmpty()){
				
				ligne = fSet.readLine();
				nbTotal++;
			}
			fSet.close();
			
			
			//System.out.println("Total strains dataset1 = "+nbTotal);
			
			
			//Write in dataset_test (after line number 3074)
			try
			{
			 PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(racine+"A_DATASET_TEST_1.csv", true)));
			 
			 for (String dtLineage : newLines) 
			 {
			     // only changes num, not the array element
				 out.print(dtLineage);
			 }
			 out.close();
			}
			catch (IOException e)
			{
			 //Gestion des exceptions en cas de problème d'accès au fichier
			}
			//Data test
			Instances dataTest = new DataSource(racine+"A_DATASET_TEST_1.csv").getDataSet();
			//Definition de la classe
			if (dataTest.classIndex() == -1){
				dataTest.setClassIndex(dataTest.numAttributes() - 1);
			}
			//Evaluation eval = classifyWithModel(tree, dataApp, dataTest);
			//System.out.println("Nb file is:"+nb);
			//TimeUnit.SECONDS.sleep(2);
			for(int j = (nbTotal-1); j < dataTest.numInstances(); j++){ // replace dataTest.numInstances() by 10
				//int nb = 0;
				double pred = tree.classifyInstance(dataTest.instance(j));
				
				//System.out.print(j+1+" ("+dataTest.instance(j)+") "+" - ");
				//System.out.print("Reel: "+dataTest.classAttribute().value((int)dataTest.instance(j).classValue())+" - ");
				//System.out.println("Predit: "+dataTest.classAttribute().value((int)pred));
				myHDTEA.put(basicSpol.get(j-(nbTotal-1)), dataTest.classAttribute().value((int)pred).toString());
				//predictions.add(dataTest.classAttribute().value((int)pred).toString());
				//nb++;
			}
			
			//System.out.println("NB TOTAL = "+nbTotal+" , NB TEST = "+nbTest);
			// Delete lines in file
			RemoveLines.delete(racine+"A_DATASET_TEST_1.csv", (nbTotal+1), nbTest);
			
			//Temps
			//long end = System.currentTimeMillis();
		    //float time = ((float) (end-begin)) / 1000f;
		    //System.out.println("Time is:"+time);
		    
		    //Correctness
		    //System.out.println("Correct is:"+myDouble);
		    
		    //Print MyH
		    //System.out.println("MyH :"+myHDTEA);
		    //System.out.println("Basic spol size = "+basicSpol.size());
		    //System.out.println("Predictions size = "+predictions.size());
		
		
		
		
		return myHDTEA;
	}
	
	
	
}
