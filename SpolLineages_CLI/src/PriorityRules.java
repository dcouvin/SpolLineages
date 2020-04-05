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

import java.util.*;

public class PriorityRules {
	
	//ArrayList<String> candidateLineages = new ArrayList<String>();

	//Constructor
	//public PriorityRules(ArrayList<String> candidates){
		//candidateLineages = candidates;
	//}
	//public PriorityRules(){
		
	//}
	
	public static String choseLineage(ArrayList<String> candidateLineages){
		String potentialLineage = "";
		
		
		if(candidateLineages.contains("AFRI") && candidateLineages.contains("AFRI_1")){
			potentialLineage = "AFRI_1";
		}
		else if(candidateLineages.contains("AFRI_2") && candidateLineages.contains("AFRI_3")){
			potentialLineage = "AFRI_2";
		}
		else if(candidateLineages.contains("AFRI") && candidateLineages.contains("AFRI_3") && candidateLineages.size()==2){
			potentialLineage = "AFRI_3";
		}
		else if(candidateLineages.contains("AFRI") && candidateLineages.contains("Manu1") && candidateLineages.size()==2){
			potentialLineage = "AFRI";
		}
		else if(candidateLineages.contains("AFRI") && candidateLineages.contains("T1") && candidateLineages.size()==2){
			potentialLineage = "T1";
		}
		else if(candidateLineages.contains("Beijing") && !candidateLineages.contains("microti") && !candidateLineages.contains("ATYPICAL")){
			potentialLineage = "Beijing";
		}
		else if(candidateLineages.contains("BOV") && candidateLineages.contains("BOV_3") && candidateLineages.size()==2){
			potentialLineage = "BOV_3";
		}
		else if(candidateLineages.contains("BOV") && candidateLineages.contains("BOV_1") && candidateLineages.size()==2){
			potentialLineage = "BOV_1";
		}
		else if(candidateLineages.contains("BOV") && candidateLineages.contains("BOV_2") && candidateLineages.size()==2){
			potentialLineage = "BOV_2";
		}
		else if(candidateLineages.contains("BOV") && candidateLineages.contains("BOV_4-CAPRAE") && candidateLineages.size()==2){
			potentialLineage = "BOV_4-CAPRAE";
		}
		else if(candidateLineages.contains("BOV") && candidateLineages.contains("BOV_1")  && (candidateLineages.contains("Manu2") || candidateLineages.contains("Manu3") || candidateLineages.contains("Manu1")) && candidateLineages.size()==3){
			potentialLineage = "BOV_1";
		}
		else if(candidateLineages.contains("BOV") && candidateLineages.contains("BOV_2")  && (candidateLineages.contains("Manu2") || candidateLineages.contains("Manu1") || candidateLineages.contains("Manu3")) && candidateLineages.size()==3){
			potentialLineage = "BOV_2";
		}
		else if(candidateLineages.contains("BOV") && candidateLineages.contains("BOV_4-CAPRAE")  && (candidateLineages.contains("Manu2") || candidateLineages.contains("Manu1") || candidateLineages.contains("Manu3")) && candidateLineages.size()==3){
			potentialLineage = "BOV_4-CAPRAE";
		}
		else if(candidateLineages.contains("AFRI") && candidateLineages.contains("CAS") && candidateLineages.size()==2){
			potentialLineage = "CAS";
		}
		else if(candidateLineages.contains("CAS1-Delhi") && candidateLineages.contains("CAS")){
			potentialLineage = "CAS1-Delhi";
		}
		else if(candidateLineages.contains("CAS1-Kili") && candidateLineages.contains("CAS")){
			potentialLineage = "CAS1-Kili";
		}
		else if(candidateLineages.contains("CAS2") && candidateLineages.contains("CAS")){
			potentialLineage = "CAS2";
		}
		else if(candidateLineages.contains("EAI1-MYS") && !candidateLineages.contains("EAI6-BGD1")){
			potentialLineage = "EAI1-MYS";
		}
		else if(candidateLineages.contains("EAI1-SOM") && !candidateLineages.contains("EAI1-MYS") && !candidateLineages.contains("EAI2") && !candidateLineages.contains("EAI2-Manila") && !candidateLineages.contains("EAI2-Nonthaburi") && !candidateLineages.contains("EAI4-VNM") && !candidateLineages.contains("EAI6-BGD1") && !candidateLineages.contains("EAI8-MDG")){
			potentialLineage = "EAI1-SOM";
		}
		else if(candidateLineages.contains("EAI2-Manila")){
			potentialLineage = "EAI2-Manila";
		}
		else if(candidateLineages.contains("EAI2") && !candidateLineages.contains("EAI2-Manila") && !candidateLineages.contains("EAI2-Nonthaburi")){
			potentialLineage = "EAI2";
		}
		else if(candidateLineages.contains("EAI2") && 
				candidateLineages.contains("EAI2-Nonthaburi")){
			potentialLineage = "EAI2-Nonthaburi";
		}
		else if(candidateLineages.contains("EAI3-IND")){
			potentialLineage = "EAI3-IND";
		}
		else if(candidateLineages.contains("EAI4-VNM")){
			potentialLineage = "EAI4-VNM";
		}
		else if(candidateLineages.contains("EAI6-BGD1") && !candidateLineages.contains("EAI2-Manila") && !candidateLineages.contains("EAI3-IND") && !candidateLineages.contains("EAI8-MDG")){
			potentialLineage = "EAI6-BGD1";
		}
		else if(candidateLineages.contains("EAI8-MDG") && 
				!candidateLineages.contains("EAI3-IND")){
			potentialLineage = "EAI8-MDG";
		}
		else if(candidateLineages.contains("EAI5") && 
				candidateLineages.contains("Manu3") &&
				candidateLineages.size()==2){
			potentialLineage = "EAI5";
		}
		else if(candidateLineages.contains("EAI5") && 
				candidateLineages.contains("Manu1") &&
				candidateLineages.size()==2){
			potentialLineage = "EAI5";
		}
		else if(candidateLineages.contains("EAI5") && 
				candidateLineages.contains("Manu1") &&
				candidateLineages.contains("AFRI") &&
				candidateLineages.size()==3){
			potentialLineage = "EAI5";
		}
		else if(candidateLineages.contains("EAI5") && 
				candidateLineages.contains("Manu3") &&
				candidateLineages.contains("AFRI") &&
				candidateLineages.size()==3){
			potentialLineage = "EAI5";
		}
		else if(candidateLineages.contains("AFRI") && 
				candidateLineages.contains("H3") &&
				candidateLineages.size()==2){
			potentialLineage = "H3";
		}
		else if(candidateLineages.contains("H1") && 
				candidateLineages.contains("H2") &&
				candidateLineages.contains("H3") &&
				candidateLineages.size()==3){
			potentialLineage = "H2";
		}
		else if(candidateLineages.contains("H1") && 
				candidateLineages.contains("H3") &&
				candidateLineages.size()==2){
			potentialLineage = "H1";
		}
		else if(candidateLineages.contains("T1") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.size()==2){
			potentialLineage = "LAM9";
		}
		else if(candidateLineages.contains("T1") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM1") &&
				candidateLineages.size()==3){
			potentialLineage = "LAM1";
		}
		else if(candidateLineages.contains("T1") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM4") &&
				candidateLineages.size()==3){
			potentialLineage = "LAM4";
		}
		else if(candidateLineages.contains("T1") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM6") &&
				candidateLineages.size()==3){
			potentialLineage = "LAM6";
		}
		else if(candidateLineages.contains("LAM3") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM4") &&
				!candidateLineages.contains("LAM5") &&
				!candidateLineages.contains("LAM6") &&
				!candidateLineages.contains("LAM8") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("X2") &&
				!candidateLineages.contains("X3") &&
				!candidateLineages.contains("S")){
			potentialLineage = "LAM3";
		}
		else if(candidateLineages.contains("LAM6") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM4") &&
				!candidateLineages.contains("LAM5") &&
				!candidateLineages.contains("LAM6") &&
				!candidateLineages.contains("LAM8") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("X2") &&
				!candidateLineages.contains("X3") &&
				!candidateLineages.contains("S")){
			potentialLineage = "LAM";
		}
		else if(candidateLineages.contains("LAM1") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM4") &&
				!candidateLineages.contains("LAM5") &&
				!candidateLineages.contains("LAM6") &&
				!candidateLineages.contains("LAM8") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("X2") &&
				!candidateLineages.contains("X3") &&
				!candidateLineages.contains("S")){
			potentialLineage = "LAM";
		}
		else if(candidateLineages.contains("LAM3") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM1") &&
				!candidateLineages.contains("LAM5") &&
				!candidateLineages.contains("LAM6") &&
				!candidateLineages.contains("LAM8") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("X2") &&
				!candidateLineages.contains("X3") &&
				!candidateLineages.contains("S")){
			potentialLineage = "LAM3";
		}
		else if(candidateLineages.contains("T1") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM5") &&
				candidateLineages.size()==3){
			potentialLineage = "LAM5";
		}
		else if(candidateLineages.contains("T1") && 
				candidateLineages.contains("LAM5") &&
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("T3") &&
				candidateLineages.size()==4){
			potentialLineage = "LAM5";
		}
		else if(candidateLineages.contains("T1") && 
				candidateLineages.contains("LAM4") &&
				candidateLineages.contains("LAM5") &&
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("T3") &&
				candidateLineages.size()==5){
			potentialLineage = "LAM";
		}
		else if(candidateLineages.contains("T1") && 
				candidateLineages.contains("LAM8") &&
				candidateLineages.contains("LAM5") &&
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("T3") &&
				candidateLineages.size()==5){
			potentialLineage = "LAM";
		}
		// LAM4 T1 LAM5 LAM9 T3
		else if(candidateLineages.contains("LAM6") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM5") &&
				candidateLineages.contains("LAM8") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("X2") &&
				!candidateLineages.contains("X3") &&
				!candidateLineages.contains("S")){
			potentialLineage = "LAM";
		}
		else if(candidateLineages.contains("LAM2") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM5") &&
				candidateLineages.contains("LAM1") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("X2") &&
				!candidateLineages.contains("X3") &&
				!candidateLineages.contains("S")){
			potentialLineage = "LAM";
		}
		else if(candidateLineages.contains("LAM3") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM5") &&
				candidateLineages.contains("LAM1") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("X2") &&
				!candidateLineages.contains("X3") &&
				!candidateLineages.contains("S")){
			potentialLineage = "LAM";
		}
		else if(candidateLineages.contains("LAM3") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM5") &&
				candidateLineages.contains("LAM4") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("X2") &&
				!candidateLineages.contains("X3") &&
				!candidateLineages.contains("S")){
			potentialLineage = "LAM";
		}
		else if(candidateLineages.contains("LAM6") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM5") &&
				candidateLineages.contains("LAM4") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("X2") &&
				!candidateLineages.contains("X3") &&
				!candidateLineages.contains("S")){
			potentialLineage = "LAM";
		}
		else if(candidateLineages.contains("LAM6") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM1") &&
				candidateLineages.contains("LAM4") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("X2") &&
				!candidateLineages.contains("X3") &&
				!candidateLineages.contains("S")){
			potentialLineage = "LAM";
		}
		else if(candidateLineages.contains("LAM3") && 
				candidateLineages.contains("LAM9") &&
				candidateLineages.contains("LAM5") &&
				candidateLineages.contains("LAM6") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("X2") &&
				!candidateLineages.contains("X3") &&
				!candidateLineages.contains("S")){
			potentialLineage = "LAM";
		}
		else if(candidateLineages.contains("X1") && 
				candidateLineages.contains("X2") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("S")){
			potentialLineage = "X2";
		}
		else if(candidateLineages.contains("X1") && 
				candidateLineages.contains("X3") &&
				!candidateLineages.contains("Turkey") &&
				!candidateLineages.contains("S")){
			potentialLineage = "X3";
		}
		else if(candidateLineages.contains("X1") && 
				candidateLineages.contains("X2") &&
				candidateLineages.contains("X3") &&
				!candidateLineages.contains("S")){
			potentialLineage = "X3";
		}
		else if(candidateLineages.contains("T1") && 
				candidateLineages.contains("X1") &&
				candidateLineages.size()==2){
			potentialLineage = "X1";
		}
		else if(candidateLineages.contains("T1") && 
				candidateLineages.contains("X1") &&
				candidateLineages.contains("T3") &&
				candidateLineages.size()==3){
			potentialLineage = "X1";
		}
		else if(candidateLineages.contains("PINI2") && 
				candidateLineages.contains("PINI") &&
				candidateLineages.contains("BOV") &&
				candidateLineages.size()==3){
			potentialLineage = "PINI2";
		}
		else if(candidateLineages.contains("PINI1") && 
				candidateLineages.contains("PINI") &&
				candidateLineages.contains("BOV") &&
				candidateLineages.size()==3){
			potentialLineage = "PINI1";
		}
		else if(candidateLineages.contains("PINI2") && 
				candidateLineages.contains("BOV_4-CAPRAE") &&
				candidateLineages.contains("PINI") &&
				candidateLineages.contains("BOV") &&
				candidateLineages.size()==4){
			potentialLineage = "PINI2";
		}
		//PINI //PINI2, PINI, BOV // PINI2, BOV_4-CAPRAE, PINI, BOV // PINI, PINI1, BOV
		else if(candidateLineages.contains("NEW-1 (misnamed as Ural-2)")){
			potentialLineage = "NEW-1 (misnamed as Ural-2)";
		}
		else if(candidateLineages.contains("Ural-1")){
			potentialLineage = "Ural-1";
		}
		else if(candidateLineages.contains("microti")){
			potentialLineage = "microti";
		}
		else if(candidateLineages.contains("CANETTII")){
			potentialLineage = "CANETTII";
		}
		//
		else if(candidateLineages.contains("Turkey")){
			potentialLineage = "Turkey";
		}
		else if(candidateLineages.contains("LAM12-Madrid1")){
			potentialLineage = "LAM12-Madrid1";
		}
		else if(candidateLineages.contains("LAM11-ZWE")){
			potentialLineage = "LAM11-ZWE";
		}
		else if(candidateLineages.contains("LAM-RUS")){
			potentialLineage = "LAM-RUS";
		}
		else if(candidateLineages.contains("ATYPICAL")){
			potentialLineage = "ATYPICAL";
		}
		else if(candidateLineages.contains("T1-RUS2")){
			potentialLineage = "T1-RUS2";
		}
		else if(candidateLineages.contains("S") && !candidateLineages.contains("Cameroon")){
			potentialLineage = "S";
		}
		else if(candidateLineages.contains("S") && candidateLineages.contains("Cameroon") && !candidateLineages.contains("T1-RUS2")){
			potentialLineage = "Cameroon";
		}
		else if(candidateLineages.contains("T1-RUS2") && candidateLineages.contains("Cameroon")){
			potentialLineage = "T1-RUS2";
		}
		else if(!candidateLineages.contains("T1-RUS2") && candidateLineages.contains("Cameroon")){
			potentialLineage = "Cameroon";
		}
		else if(candidateLineages.contains("T2") && candidateLineages.contains("T2-Uganda")){
			potentialLineage = "T2-Uganda";
		}
		//T2
		else if(candidateLineages.size()==1){
			potentialLineage = candidateLineages.get(0);
		}
		else if( candidateLineages.size()-1 > 0 && (potentialLineage.isEmpty() || potentialLineage.equals(null) || potentialLineage.equals("")) ){
			potentialLineage = candidateLineages.get(candidateLineages.size()-1);
		}
		//ATYPICAL
		
		
		return potentialLineage;
	}
	
	
}
