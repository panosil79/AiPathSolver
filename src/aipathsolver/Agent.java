/*
 *  (C)2016 Panos Iliopoulos - All Rights Reserved
 */

package aipathsolver;

import java.util.ArrayList;

public class Agent {


ArrayList<Genome> genomesList;
int populationSize=0;
double crossoverRate=0;
double mutationRate=0;
int genesLength=0;
double bestFitnessScore=0;
Genome bestGenome;
double totalFitnessScore=0;
int generation=0;
Map worldMap;


Genome RouletteWheelSelection(){
    double fSlice = Math.random() * totalFitnessScore;
    double cfTotal = 0;
    int SelectedGenome = 0;
    for (int i=0; i<populationSize; ++i){
        cfTotal += genomesList.get(i).performance.fitness;
        if (cfTotal > fSlice){
            SelectedGenome = i;
            break;
            }
        }
    return genomesList.get(SelectedGenome);
}


void Mutate(ArrayList<Integer> geneBits){
    for (int curBit=0; curBit<geneBits.size(); curBit++)
        if (Math.random() < mutationRate)
            geneBits.set(curBit, geneBits.get(curBit).intValue() & 0x1 );
}


void Crossover(ArrayList<Integer> mum,ArrayList<Integer> dad,ArrayList<Integer> baby1,ArrayList<Integer> baby2){

    if ( (Math.random()> crossoverRate) || (mum == dad)){
        baby1 = mum;
        baby2 = dad;
        return;
        }

    int cutPoint = (int)(Math.random()*(genesLength-1) );

    baby1.clear();
    baby2.clear();

    for (int i=0; i<cutPoint; i++){
        baby1.add(mum.get(i));
        baby2.add(dad.get(i));
        }

    for (int i=cutPoint; i<mum.size(); i++){
        baby1.add(dad.get(i));
        baby2.add(mum.get(i));
        }
}


void UpdateFitnessScores(){
    totalFitnessScore=0;
    for (int x=0;x<genomesList.size();x++){
        Genome genome=genomesList.get(x); 
        GenomePerformance results=worldMap.TestRoute(Decode(genome.geneBits));
        genome.performance.fitness=results.fitness;
        totalFitnessScore+=genome.performance.fitness;
        if (genome.performance.fitness>bestFitnessScore){
            bestFitnessScore=genome.performance.fitness;
            bestGenome=genome.makeClone();
            //System.out.printf("Best fitnes found F=%f S=%d Pos=%dx%d in generation %d with genome %s \n",genome.dFitness,results.steps,results.x,results.y,generation,genome.geneBits.toString());
            }
        }
}


ArrayList<Integer> Decode(ArrayList<Integer> bits){
    ArrayList<Integer> result=new ArrayList<>();
    for (int x=0;x<bits.size();x+=2)
        result.add((bits.get(x)<<1)+bits.get(x+1));
    return(result);
}


void CreateStartPopulation(){
    genomesList=new ArrayList<>();
    for (int x=0;x<populationSize;x++)  
        genomesList.add(new Genome(genesLength));
}


Agent(double cross_rat,double mut_rat,int population_size,int genes_length){
    crossoverRate=cross_rat;
    mutationRate=mut_rat;
    populationSize=population_size;
    genesLength=genes_length;
    totalFitnessScore=0.0;
    generation=0;    
    CreateStartPopulation();    
}


void setMap(int oMap[],int width,int height){
    worldMap=new Map(oMap,width,height);
}


void Epoch(){
    //System.out.printf("New epoch with generation %d start ...\n",generation);
    UpdateFitnessScores();
        
    int NewBabies = 0;
    ArrayList<Genome> babyGenomes=new ArrayList<Genome>();
        
    while (NewBabies < populationSize){
        //select 2 parents
        Genome mum = RouletteWheelSelection();
        Genome dad = RouletteWheelSelection();
        //operator - crossover
        Genome baby1 = new Genome(genesLength);
        Genome baby2 = new Genome(genesLength);        
        Crossover(mum.geneBits, dad.geneBits, baby1.geneBits, baby2.geneBits);
        //mutate
        Mutate(baby1.geneBits);
        Mutate(baby2.geneBits);        
        //add to new population
        babyGenomes.add(baby1);
        babyGenomes.add(baby2);
        NewBabies += 2;       
        }
   
    //copy babies back into starter population
    genomesList = babyGenomes; 
    ++generation;
    //System.out.println("Epoch finish ...");    
    }


}
