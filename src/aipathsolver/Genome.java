/*
 *  (C)2016 Panos Iliopoulos - All Rights Reserved
 */

package aipathsolver;

import java.util.ArrayList;

public class Genome {
    
    ArrayList<Integer> geneBits;
    GenomePerformance   performance;
    
    Genome(){
        performance=new GenomePerformance();
        geneBits=new ArrayList<>();
    }

    Genome(int length){
        performance=new GenomePerformance();        
        geneBits=new ArrayList<>();
        for (int x=0;x<length*2;x++) // 2bits/action
            geneBits.add((int)(Math.round((Math.random())%2)));
        
    }  
   
   Genome makeClone(){
       Genome result=new Genome();
       result.geneBits=(ArrayList<Integer>)geneBits.clone();
       result.performance=performance.makeClone();
       return(result);
   }
   
   
   @Override
   public String toString(){
       String result=new String();
       for (int x=0;x<geneBits.size();x++)
           if (geneBits.get(x)==0)
               result+='.';
            else
               result+='|';
       return(result);
   }
    
}
