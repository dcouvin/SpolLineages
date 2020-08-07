

//import java.util.*;

import java.io.*;

class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    
    StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }
    
    
    //public static String getLine(String myLine){
    	//return myLine;
    //}
    
    public void run()
    {
    	//String myline="";
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            String line=null;
            while ( (line = br.readLine()) != null)
                //System.out.println(type + ">" + line);  
                //lineTrue = line;
                //stdOut.append(line);
            	System.setProperty("MaskLineage", line.toString());
            	//setLineage(line);
            } catch (IOException ioe)
              {
                ioe.printStackTrace();  
              }
            //return line;
            
            
           
    }

    public String getLineage()
    {
      //driver2 = new ChromeDriver();
      return System.getProperty("MaskLineage" );          
    }
    
    
}