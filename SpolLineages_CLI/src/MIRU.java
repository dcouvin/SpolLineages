/**
 * #################################################################################################
 * #  SpolLineages: simple algorithms for the identification of Mycobacterium tuberculosis complex
 * #   lineages using classical genotyping methods and updated rules
 * # 
 * #  Copyright (C) 2019- SpolLineages team
 * #  (Institut Pasteur de la Guadeloupe, Université des Antilles)
 * #
 * #  See the COPYRIGHT file for details.
 * #
 * #  SpolLineages is distributed under the terms of the GNU General Public License
 * #  (GPLv3). See the COPYING file for details.  
 * #################################################################################################
 */

public class MIRU {

	//MIRU12 order: MIRU 2-4-10-16-20-23-24-26-27-31-39- and 40.
	//MIRU15 order: MIRU 4, 10, 16, 26, 31, and 40, ETR-A, ETR-C, QUB-11b, QUB-26, QUB-4156, Mtub04, Mtub21, Mtub30, and Mtub39.
	//MIRU24 order: classical 12-loci pattern followed by ETR-A, ETR-B, ETR-C, QUB-11b, QUB-26, QUB-4156, Mtub04, Mtub21, Mtub29, Mtub30, Mtub34, and Mtub39.
	
	//Constructor
	//public MIRU(){
	//}
	
	public static String getMIRULineageRuleTB (String miru, int miruFormat){ // obtained using StackTB publication and a mix between broad lineage and refined rules of RuleTB
		String lineage = "Unknown";
		char[] miruvntrs = miru.toCharArray();
		
		int[] mirus = new int[miruvntrs.length];     
		for (int i=0; i < miruvntrs.length; i++) {
			 if ((Character.isLetter(miruvntrs[i]) == true)) {
				 mirus[i] = 10;
	         }
			 else if ((Character.isDigit(miruvntrs[i]) == true)){
				 mirus[i] = Integer.parseInt(String.valueOf(miruvntrs[i]));
			 }
			 else{
				 mirus[i] = 0;
			 }
		}
		//System.out.println("MIRU pattern: "+miru+"  BOV rule loci: "+mirus[2]+" [2]<=2, "+mirus[20]+" [20]<=3, "+mirus[21]+" [21]>=3, "+mirus[17]+" [17]<=1");
		
		if (miruFormat==24){
			if ( (mirus[2] <= 2) && (mirus[20] <= 3) && (mirus[21] >= 3) && (mirus[17] <= 1) )  {
				lineage = "M. bovis";
			}
			else if ( mirus[6] >= 2 && mirus[7] >= 3 && mirus[7] <= 4 && mirus[12] <= 7 && mirus[19] <= 6 && mirus[16] <= 7 )  {
				lineage = "M. africanum";
			}
			else if ( mirus[7] <= 4 && mirus[20] <= 3 && mirus[21] <= 3 )  {
				lineage = "Indo-Oceanic";
			}
			else if ( mirus[3] >= 2 && mirus[9] >= 4 && mirus[12] >= 2 && mirus[14] <= 2 )  {
				lineage = "East-African-Indian";
			}
			else if ( mirus[2] <= 4 && mirus[9] >= 4 && mirus[11] <= 5 && mirus[15] >= 2 && mirus[21] >= 2 )  {
				lineage = "East-Asian"; 
			}
			else if ( mirus[9] <= 4 && mirus[10] <= 2 )  {
				lineage = "Euro-American";
			}
			
		}
		
		return lineage;
	}
	
	public String getMIRULineageRuleTB2 (String miru, int miruFormat){ // representing only refined rules of RuleTB (StackTB)
		String lineage = "Unknown";
		char[] mirus = miru.toCharArray();
		     
		if (miruFormat==24){
			if ( mirus[6] >= 2 && mirus[22] <= 2 && mirus[23] <= 1 ){
				lineage = "M. caprae";
			}
			else if ( mirus[2] <= 2 && mirus[20] <= 3 && mirus[21] >= 3 && mirus[17] <= 1 )  {
				lineage = "M. bovis";
			}
			else if ( mirus[3] >= 3 && mirus[12] >= 6 && mirus[21] >= 4 && mirus[23] >= 4 && mirus[16] >= 6 )  {
				lineage = "M. africanum 2";
			}
			else if ( mirus[5] <= 5 && mirus[6] >= 2 && mirus[11] <= 2 && mirus[15] <= 6 && mirus[16] <= 6 )  {
				lineage = "M. africanum 1";
			}
			else if ( mirus[7] <= 4 && mirus[20] <= 3 && mirus[21] <= 3 )  {
				lineage = "Indo-Oceanic";
			}
			else if ( mirus[2] >= 4 && mirus[3] >= 2 && mirus[9] >= 4 && mirus[12] >= 2 && mirus[18] >= 3 && mirus[19] >= 2 && mirus[20] >= 3 )  {
				lineage = "East-African-Indian";
			}
			else if ( mirus[2] <= 3 && mirus[9] >= 4 && mirus[11] <= 5 && mirus[13] >= 2 && mirus[18] >= 2 && mirus[19] >= 3 && mirus[21] >= 2 )  {
				lineage = "East-Asian";
			}
			else if ( mirus[9] <= 4 && mirus[10] <= 2 )  {
				lineage = "Euro-American";
			}
			
		}
		
		return lineage;
	}
}