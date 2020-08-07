#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>

#define NBJEU 14
#define NBEXAMPLES 3074//50000//4594//3074
#define NBEXAMPLESREEL 3074//50000//4594//3074
//int classe[NBEXAMPLESREEL];
int nbexem=0;
int nbOther[NBJEU];
int nbFamille[NBJEU];
//unsigned long long mask[NBEXAMPLESREEL];



unsigned long long puiss2(int a)
{
 unsigned long long v=1;

 return(v<<a);
}

int pbin(unsigned long long b, int n)
{
 int i; unsigned long long a=puiss2(n-1);

 for(i=0;i<n;i++)
  {
     printf("%llu",(b&a)>>n-1-i);
     a=a>>1;
     //b=b>>1;
  }
 printf(" ");
 return(0);
}

double moyp(double * tab, int * coef, int n)
{
  double s=0;
  int i,sumco=0;

  for(i=0;i<n;i++) { s+=coef[i]*tab[i]; sumco+=coef[i];}

  return(s/sumco);

}

void main(int argc, char ** argv)
{
  srandom(time(NULL));
  int NBMASK,i,j,k,c,z;

  int * classe=(int *) malloc(NBEXAMPLESREEL*sizeof(int));
  unsigned long long * mask=(unsigned long long *) malloc(NBEXAMPLESREEL*sizeof(unsigned long long));
  int ** prediction = (int **) malloc(NBEXAMPLES*sizeof(int *)); //prediction[NBEXAMPLES][NBJEU]
  for(i=0;i<NBEXAMPLES;i++) prediction[i]=(int *) malloc(NBJEU*sizeof(int));

  char names[NBJEU][20]={"Beijing","BOVIS","Cameroon","CAS","EAI","H","LAM","Manu","PINI","S","T","Turkey","Ural","X"};
  double truepos[NBJEU],falsepos[NBJEU],trueneg[NBJEU],tpr,tnr,prec_fam,sprec_fam=0;
  int tabpred[NBJEU],tabtp[NBJEU],tabfp[NBJEU],nbpred,alea;
  int prio[NBJEU]={7,2,11,6,4,3,12,9,5,8,1,0,10,13};
  int ii,jj,stop;
  for(i=0;i<NBEXAMPLES;i++) for(j=0;j<NBJEU;j++) prediction[i][j]=0;

  char ** tabclasse = (char **) malloc(NBEXAMPLES*sizeof(char *));
  for(i=0;i<NBEXAMPLES;i++) tabclasse[i]=(char *) malloc(20*sizeof(char));
  
  unsigned long long tab[10],combi;

  unsigned long long a=1,d,b;

  int TP,TN,FP,FN,stp=0,sfp=0,stn=0;

  FILE * entree2 = fopen(argv[2],"r+");
  i=0;

  while(!feof(entree2))
    {
	fscanf(entree2,"%llu",mask+i);
	fscanf(entree2,"%s",tabclasse[i]); //printf("%s\n",tabclasse[i]);
	//if(strcmp(chaineclasse,"OTHER")!=0) { classe[i]=1; nbFamille++; }
	//else { classe[i]=0; nbOther++; }
	i++;
    }
  nbexem=i-1;
  fclose(entree2);

  FILE * entree = fopen(argv[1],"r+");

  printf("Famille\t\tTP rate\t\tTN rate\t\tPrecision\n\n");

  for(z=0;z<NBJEU;z++)
   {
    TP=0; TN=0; FP=0; FN=0; nbFamille[z]=0; nbOther[z]=0;
    fscanf(entree,"%d",&NBMASK);
 
    for(i=0;i<NBMASK;i++) { fscanf(entree,"%llu",tab+i); pbin(tab[i],43); printf("\n");}

    fscanf(entree,"%llu",&combi); pbin(combi,16);printf("\n");printf("\n");
    b=combi;

    for(i=0;i<nbexem;i++) 
     {
	if(strcmp(names[z],tabclasse[i])==0) { classe[i]=1; nbFamille[z]++; }
	else { classe[i]=0; nbOther[z]++; }
     }

  /****/

 

 for(i=0;i<nbexem;i++)
  {

 
    for(j=0;j<puiss2(NBMASK);j++)
    {
     c=0;
     for(k=0;k<NBMASK;k++)
      { 
	if((j&(int)puiss2(k))==0) 
	 { 
	   if((tab[k]&mask[i])==0) c++;
	 }
	else
	 { 
	   if((tab[k]&mask[i])!=0) c++;
	 }
      } 
     //if(j==puiss2(NBMASK)-1) d=1;
     //else d=0;
     d=(b>>j)&a;
     if(c==NBMASK) 
      { 
	if(d==1) prediction[i][z]=1;
	if(classe[i]==d)
 	 {
	  if(d==0) { TN++;}
	  else { TP++;}
	 }
	else
	 {
	  if(d==0) { FN++;}
	  else { FP++;}
	 }
	break; // else if
      }
    }//exit(0);

  }

  /****/

  //printf("%d %d %d %d\n",TP,nbFamille,FP,nbOther);
  truepos[z]=(double)TP/(double)nbFamille[z];
  //falsepos[z]=(double)FP/(double)nbOther;
  trueneg[z]=(double)TN/(double)nbOther[z];
  

  stp+=TP;
  //sfp+=FP;
  stn+=TN;
  prec_fam=(double)(TP+TN)/((double)nbFamille[z]+(double)nbOther[z]);
  sprec_fam+=prec_fam;
  if(strcmp(names[z],"Cameroon")==0) printf("%s\t%lf\t%lf\t%lf\n",names[z],truepos[z],trueneg[z],prec_fam);//falsepos[z]);
  else printf("%s\t\t%lf\t%lf\t%lf\n",names[z],truepos[z],trueneg[z],prec_fam);//falsepos[z]);

   }

  tpr=moyp(truepos,nbFamille,NBJEU); tnr=moyp(trueneg,nbOther,NBJEU);
  printf("\nGLOBAL\t\t%lf\t%lf\t%lf\n",tpr,tnr,sprec_fam/NBJEU);//(tpr+tnr)/2);

  //printf("TP rate: %lf\nTN rate: %lf\n",(double)stp/(double)NBEXAMPLES,(double)stn/(double)NBEXAMPLES);//moy(truepos,NBJEU),moy(falsepos,NBJEU));


 TP=0;
 FP=0;

  for(i=0;i<NBJEU;i++) {tabtp[i]=0;tabfp[i]=0;}

  for(i=0;i<nbexem;i++)
   {
    nbpred=0;
    for(j=0;j<NBJEU;j++)
     {
	if(prediction[i][j]==1)
	 {
	  tabpred[nbpred]=j; //printf("%s ",names[j]);
	  nbpred++;
	 }
/*	if(nbpred==1)
	 {
	  if(strcmp(tabclasse[i],names[j])==0) TP++;
	  else FP++;
	 }
	else
	 {
	  alea=random()%
	 }*/
     }
    if(nbpred==0) 
    {//printf("No dec\n"); 
     continue;}
    
    alea=0;

    stop=0;
    if(nbpred==2)
     {
	for(ii=0;ii<NBJEU;ii++)
         {  
          for(jj=0;jj<2;jj++) 
           { 
            if(tabpred[jj]==prio[ii]) 
             {
	      alea=jj;
              stop=1; 
              break;
             } 
           } 
          if(stop==1) break;
         }
     }

    //printf(" --> %s/%s",names[tabpred[alea]],tabclasse[i]);
    if(strcmp(tabclasse[i],names[tabpred[alea]])==0)
     {
      tabtp[tabpred[alea]]++;
     }
    else
     {
      tabfp[tabpred[alea]]++;
     }
 //printf("\n");
   }

 //printf("\n\n");
 stp=0; sfp=0;
 for(i=0;i<NBJEU;i++)
  {
   stp+=tabtp[i];
   sfp+=tabfp[i];
   if(strcmp(names[z],"Cameroon")==0) printf("%s\t%lf\t%lf\t%lf\n",names[z],(double)tabtp[i]/(double)nbFamille[i],(double)tabfp[i]/(double)nbOther[i],(double)tabtp[i]/((double)tabtp[i]+(double)tabfp[i]));//falsepos[z]);
   else printf("%s\t\t%lf\t%lf\t%lf\n",names[z],(double)tabtp[i]/(double)nbFamille[i],(double)tabfp[i]/(double)nbOther[i],(double)tabtp[i]/((double)tabtp[i]+(double)tabfp[i]));

  }

 printf("TP: %lf, FP: %lf, Prec: %lf\n",(double)stp/NBEXAMPLES,(double)sfp/NBEXAMPLES, (double)stp/(stp+sfp));

  fclose(entree);

}
