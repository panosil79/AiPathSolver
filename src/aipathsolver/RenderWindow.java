/*
 *  (C)2016 Panos Iliopoulos - All Rights Reserved
 */
package aipathsolver;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class RenderWindow extends javax.swing.JDialog {

    final int MAP_BLOCK_WIDTH=40;
    final int MAP_BLOCK_HEIGHT=40;
    int firstTime=0;
    
    Map map;
    Agent ourAgent;
    
    ArrayList<Integer> lastMoves;

    int generation;
        
    public void setMap(Map map) {
        this.map = map;
    }

    public void setOurAgent(Agent ourAgent) {
        this.ourAgent = ourAgent;
    }
    
    public void redrawBoard(Agent agent){        
        ourAgent=agent;
        if (ourAgent.bestGenome!=null)
            this.lastMoves = ourAgent.Decode(ourAgent.bestGenome.geneBits);        
        generation=ourAgent.generation;        
        repaint();        
    }
                
    
    public RenderWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();                
    }
    
    public RenderWindow(java.awt.Frame parent, boolean modal,Agent agent) {
        super(parent, modal);
        initComponents();
        setTitle("GA Path Solver by Panos Iliopoulos");
        setMap(agent.worldMap);
        setOurAgent(agent);
    }

    @Override
    public void paint(Graphics g) {

       super.paint(g);
       if (map!=null){
            
            setSize(400+20+map.mapWidth*MAP_BLOCK_WIDTH,100+map.mapHeight*MAP_BLOCK_HEIGHT);
            for (int y=0;y<map.mapHeight;y++)
                for (int x=0;x<map.mapWidth;x++){
                    int tile=map.map[y][x];
                    switch (tile){
                        case 8:
                            g.setColor(Color.green);
                            break;
                        case 5:
                            g.setColor(Color.blue);
                            break;
                        case 1:
                            g.setColor(Color.gray);
                            break;
                        case 0:
                            g.setColor(Color.white);
                            break;
                    }                
                                        
                    g.fillRect(11+x*MAP_BLOCK_WIDTH ,40+y*MAP_BLOCK_WIDTH,MAP_BLOCK_WIDTH-2,MAP_BLOCK_HEIGHT-2);
                    g.setColor(Color.black);
                    g.drawRect(11+x*MAP_BLOCK_WIDTH ,40+y*MAP_BLOCK_WIDTH,MAP_BLOCK_WIDTH-2,MAP_BLOCK_HEIGHT-2);                    
                    g.setColor(Color.white);
                    g.fillRect(0,40+map.mapHeight*MAP_BLOCK_HEIGHT,400+20+map.mapWidth*MAP_BLOCK_WIDTH, 50);
                    g.setColor(Color.black);
                    g.drawString("Generation "+String.valueOf(generation),20,60+map.mapHeight*MAP_BLOCK_HEIGHT);
                    g.drawString("Best genome "+ourAgent.bestGenome.toString(),20,80+map.mapHeight*MAP_BLOCK_HEIGHT);
                    if (lastMoves!=null)
                        drawPath(g);                                   
                }
        }
    }

    
    void drawPath(Graphics g){
        
    int agentX=map.entryX,agentY=map.entryY;   
    int testMap[][]=new int[map.mapHeight][map.mapWidth];
   
    for (int y=0;y<map.mapHeight;y++)
        for (int x=0;x<map.mapWidth;x++)
            testMap[y][x]=map.map[y][x];
    
    Integer steps=0;    
    for (int x=0;x<lastMoves.size();x++){        
        testMap[agentY][agentX]=1;
        int action=lastMoves.get(x);        
        switch (action){
            case 3: // left
                if ( agentX > 0 && testMap[agentY][agentX-1]!=1 ){
                    agentX--;
                    steps++;
                }
                break;
            case 2: // right
                if ( agentX < map.mapWidth-1 && testMap[agentY][agentX+1]!=1 ){
                    agentX++;
                    steps++;
                }
                break;
            case 1: // down
                if ( agentY>0 && testMap[agentY-1][agentX]!=1 ){
                    agentY--;
                    steps++;
                }
                break;
            case 0: // down
                if ( agentY<map.mapHeight-1 && testMap[agentY+1][agentX]!=1 ){
                    agentY++;
                    steps++;
                }
                break;                                
        }
        
        g.setColor(Color.black);
        g.fillRect(11+agentX*MAP_BLOCK_WIDTH,41+agentY*MAP_BLOCK_WIDTH ,MAP_BLOCK_WIDTH-2,MAP_BLOCK_HEIGHT-2);        
        g.setColor(Color.white);
        g.drawRect(11+agentX*MAP_BLOCK_WIDTH,41+agentY*MAP_BLOCK_WIDTH ,MAP_BLOCK_WIDTH-2,MAP_BLOCK_HEIGHT-2);
        g.drawString(steps.toString(),20+agentX*MAP_BLOCK_WIDTH ,60+agentY*MAP_BLOCK_WIDTH);        
        if (agentX==map.exitX && agentY==map.exitY)
            return;                
        }
    }
     
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(new java.awt.Point(200, 200));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
