

import java.util.ArrayList;
import java.util.HashMap;

public class BinaryMask {
	
	/**
	 * Evolutionary Algorithm (Binary Mask) lineage prediction
	 * @author Wilfried Segretier
	 * 
	 */
	public static HashMap<String,String> getLineageEAMask(ArrayList<String> inSpoligos, String pathToEA) throws Exception{
		//String lineage="";
		
		ArrayList<String> spolMaskLines = new ArrayList<String>();
		HashMap<String,String> mySpolMask = new HashMap<String,String>();
		
		for (String spoligo : inSpoligos) 
		 {
			String tmpSpol = DecisionTree.transformSpolToDTEAmask(spoligo);
			spolMaskLines.add(tmpSpol);
		 }
		
		//BinaryMask calling
		/* Récupération de l'environnement d'exécution */
		Runtime runtime = Runtime.getRuntime();
		
		final String dossierInstallation = pathToEA.toString();  
		//final String dossierInstallation = "C:/Users/dcouvin/Documents/IPG_WORK/Bioinformatics_tools/C/Binary_Mask2";  
		//    C:\Users\dcouvin\Documents\IPG_WORK\Bioinformatics_tools\C\Binary_Mask2
		
		
		try {
			
			//int nbBasic = 0;
			/* Création et lancement de processus qui fait le ping */
			//  C:/Users/dcouvin/Documents/IPG_WORK/Bioinformatics_tools/C/
			//Process process = runtime.exec("cmd /c "+"dir ");
			
			//get OS name
			String oS = System.getProperty("os.name").toLowerCase();
			System.out.println("OS: "+oS);
			//Process process;
			
			if(oS.contains("windows")){
				for (String spolMask : spolMaskLines) 
				 {
				Process process = runtime.exec(dossierInstallation+"Mask2.exe" + " " +dossierInstallation+"bestIndiv "+ " " + spolMask);
			
			
				//System.out.println("SpolMask: "+spolMask);
				//System.out.println("transformedSpol: "+transformDTEAmaskSpolToBasic(spolMask));
				
				
				// any error message?
	            StreamGobbler errorGobbler = new 
	                StreamGobbler(process.getErrorStream(), "ERROR");            
	            
	            // any output?
	            StreamGobbler outputGobbler = new 
	                StreamGobbler(process.getInputStream(), "OUTPUT");
	                
	            // kick them off
	            errorGobbler.start();
	            outputGobbler.start();
	            // any error???
	            int exitVal = process.waitFor();
	            System.out.println("ExitValue: " + exitVal);     
	            
	            String maskLineage = outputGobbler.getLineage();  
	            //System.out.println("MaskLineage: "+maskLineage);
	            mySpolMask.put(DecisionTree.transformDTEAmaskSpolToBasic(spolMask), maskLineage.toString());
	            
	            //nbBasic++;
				 }//end for
			}
			else{
				for (String spolMask2 : spolMaskLines) 
				 {
				Process process2 = runtime.exec(dossierInstallation+"Mask2 " +dossierInstallation+"bestIndiv "+ spolMask2);
				System.out.println("SpolMask: "+spolMask2);
				//System.out.println("transformedSpol: "+transformDTEAmaskSpolToBasic(spolMask));
		
				// any error message?
	            StreamGobbler errorGobbler2 = new 
	                StreamGobbler(process2.getErrorStream(), "ERROR");            
	            
	            // any output?
	            StreamGobbler outputGobbler2 = new 
	                StreamGobbler(process2.getInputStream(), "OUTPUT");
	                
	            // kick them off
	            errorGobbler2.start();
	            outputGobbler2.start();
	            // any error???
	            int exitVal2 = process2.waitFor();
	            System.out.println("ExitValue: " + exitVal2);     
	            
	            String maskLineage2 = outputGobbler2.getLineage();  
	            System.out.println("MaskLineage: "+maskLineage2);
	            mySpolMask.put(DecisionTree.transformDTEAmaskSpolToBasic(spolMask2), maskLineage2.toString());
	            
	            //nbBasic++;
			}// end for
		}//end try
            
        } catch (Throwable t)
          {
            t.printStackTrace();
          }
		
		
		return mySpolMask;
	}
	

}
