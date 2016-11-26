/*
 *  (C)2016 Panos Iliopoulos - All Rights Reserved
 */

package aipathsolver;

public class GenomePerformance {

    double fitness=0;
    int validSteps=0;
    int totalSteps=0;
    int finalX=0;
    int finalY=0;
       
    GenomePerformance makeClone(){
        GenomePerformance result=new GenomePerformance();
        result.fitness=fitness;
        result.validSteps=validSteps;
        result.totalSteps=totalSteps;
        result.finalX=finalX;
        result.finalY=finalY;        
        return(result);
    }

}
