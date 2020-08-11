

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
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
			else {
				//System.out.println("SpolMask tab size: "+ spolMaskLines.size());
				for (String spolMask2 : spolMaskLines) 
				 {
				//Process process2 = runtime.exec(dossierInstallation+"Mask2 " +dossierInstallation+"bestIndiv "+ spolMask2);
					ProcessBuilder processBuilder = new ProcessBuilder();

					// -- Linux --

					// Run a shell command
					processBuilder.command("bash", "-c", " "+dossierInstallation+"Mask2 " +" " +dossierInstallation+"bestIndiv "+" " + spolMask2);
					//processBuilder.command("bash", "-c", "Mask2 " +dossierInstallation+"bestIndiv "+ spolMask2);
					
					
					// Run a shell script
					//processBuilder.command("path/to/hello.sh");

					// -- Windows --

					// Run a command
					//processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");

					// Run a bat file
					//processBuilder.command("C:\\Users\\mkyong\\hello.bat");
					//Process p;
					try {

						Process process = processBuilder.start();
						//p = Runtime.getRuntime().exec("ls -aF");

						StringBuilder output = new StringBuilder();

						BufferedReader reader = new BufferedReader(
								new InputStreamReader(process.getInputStream()));

						String line;
						while ((line = reader.readLine()) != null) {
							output.append(line);
						}

						int exitVal = process.waitFor();
						System.out.println("Exit: "+exitVal);
						if (exitVal == 0) {
							//System.out.println("Success!");
							//System.out.println(output);
							
							
							//String maskLineage2 = outp;  
				            System.out.println("MaskLineage: "+output);
				            mySpolMask.put(DecisionTree.transformDTEAmaskSpolToBasic(spolMask2), output.toString());
				            //System.exit(0);
							
						} else {
							//abnormal...
						}

					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}     
	            
	            
	            
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
