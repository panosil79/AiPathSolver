/*
 *  (C)2016 Panos Iliopoulos - All Rights Reserved
 */

package aipathsolver;

import java.util.ArrayList;


public class Map {
   
public int map[][];
public int mapWidth=0;
public int mapHeight=0;
public int entryX=0;
public int entryY=0;
public int exitX=0;
public int exitY=0;


Map(){}

Map(int aMap[],int width,int height){
    
    map=new int[height][width];
    mapWidth=width;
    mapHeight=height;    
    
    //Find entry point
    for (int x=0;x<aMap.length;x++)
        if (aMap[x]==5){
            entryX=x%width;
            entryY=x/width;
    }

    //Find exit point
    for (int x=0;x<aMap.length;x++)
        if (aMap[x]==8){
            exitX=x%width;
            exitY=x/width;
    }
    
    //Copy map data
    for (int y=0;y<height;y++)
        for (int x=0;x<width;x++)
            map[y][x]=aMap[x+(y*width)];
    
}

GenomePerformance TestRoute(ArrayList<Integer> vecPath){
    
    GenomePerformance result=new GenomePerformance();
    
    result.finalX=entryX;
    result.finalY=entryY;
    
    //Create a clone map to test our route;
    int testMap[][]=new int[mapHeight][mapWidth];    
    for (int y=0;y<mapHeight;y++)
        for (int x=0;x<mapWidth;x++)
            testMap[y][x]=map[y][x];
    
    result.validSteps=0;
    result.totalSteps=0;
    for (int x=0;x<vecPath.size();x++){        
        int action=vecPath.get(x);        
        testMap[result.finalY][result.finalX]=1;
        switch (action){
            case 3: // left
                if ( result.finalX > 0 && testMap[result.finalY][result.finalX-1]!=1 ){
                    result.finalX--;
                    result.validSteps++;
                }
                break;
            case 2: // right
                if ( result.finalX < mapWidth-1 && testMap[result.finalY][result.finalX+1]!=1 ){
                    result.finalX++;
                    result.validSteps++;
                }
                break;
            case 1: // up
                if ( result.finalY>0 && testMap[result.finalY-1][result.finalX]!=1 ){
                    result.finalY--;
                    result.validSteps++;
                }
                break;
            case 0: // down
                if ( result.finalY<mapHeight-1 && testMap[result.finalY+1][result.finalX]!=1 ){
                    result.finalY++;
                    result.validSteps++;
                }
                break;                                
        }
        
        result.totalSteps++;
        
        //Return if exit is found
        if (result.finalX==exitX && result.finalY==exitY){
            result.fitness=1-(result.validSteps*0.001);
            return(result);       
        }
    }
    
    double totalDistance=Math.sqrt(Math.pow(entryX-exitX,2)+Math.pow(entryY-exitY,2));
    double agentDistance=Math.sqrt(Math.pow(result.finalX-exitX,2)+Math.pow(result.finalY-exitY,2));
    result.fitness=1-(agentDistance/totalDistance);
    
    return(result);
}


  

}
