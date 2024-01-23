package frontEnd;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import backEnd.GraphThread;
import datastructure.Sync;

public class VisualizerPanel extends JPanel{

    JButton startBFS;
    Timer t1;
    Sync sync;
    Integer nodeId;
    boolean isGraphReady;
    boolean isTraversing;
    int[][] distXY;
    boolean[] isVisited;
    GraphThread graphThread;
    int[][] edges;
    JComboBox<Integer> startId;

    VisualizerPanel()
    {
        startBFS = new JButton("Start");
        startBFS.addActionListener(new MakeGraph());

        isVisited = new boolean[7];
        for(int i = 0; i < 7; ++i)
            isVisited[i] = false;

        distXY = new int[7][2];
        distXY[0][0] = 220;
        distXY[0][1] = 150;
        distXY[1][0] = 140;
        distXY[1][1] = 230;
        distXY[2][0] = 300;
        distXY[2][1] = 230;
        distXY[3][0] = 100;
        distXY[3][1] = 330;
        distXY[4][0] = 180;
        distXY[4][1] = 310;
        distXY[5][0] = 260;
        distXY[5][1] = 310;
        distXY[6][0] = 340;
        distXY[6][1] = 330;


        Integer[] arr = {0, 1, 2, 3, 4, 5, 6};
        startId = new JComboBox<>(arr);
        startId.addActionListener(new StartBFS());
        startId.setEnabled(false);

        t1 = new Timer(1500, new TraverseEachNode());
        isTraversing = false;
        isGraphReady = false;

        this.setBackground(new Color(0x041e29));
        this.setOpaque(true);
        this.add(startBFS);
        this.add(startId);

    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        if(isGraphReady)
        {
            g2d.setPaint(Color.white);
            for(int i = 0; i < 20; ++i)
                g2d.drawLine(distXY[edges[i][0]][0] + 20, distXY[edges[i][0]][1] + 30, distXY[edges[i][1]][0] + 20, distXY[edges[i][1]][1] + 30);
            
        }

        for(int i = 0; i < 7; ++i)
            new Circle(g2d, distXY[i][0], distXY[i][1], i, isVisited);

        if(isTraversing)
        {
            new Circle(g2d, distXY[nodeId][0], distXY[nodeId][1], nodeId, true);
            isVisited[nodeId] = true;
        }

    }

    private class MakeGraph implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {

            sync = new Sync();
            graphThread = new GraphThread(sync);
            edges = graphThread.getEdges();
            for(int i = 0; i < 7; ++i)
                isVisited[i] = false;
            isGraphReady = true;

            repaint();

            startId.setEnabled(true);

        }
        
    }


    private class StartBFS implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {

            startId.setEnabled(false);
            graphThread.setStartId(startId.getSelectedIndex());

            graphThread.start();
            t1.start();
            
        }

    }

    private class TraverseEachNode implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            sync.receive( id -> { 
                nodeId = id;
                isTraversing = true;
                // System.out.println("hehe" + id);
                repaint(); 
            });
            
            if(sync.isCompleted)
            {
                // System.out.println(graphThread.isAlive());
                t1.stop();
                Timer t2 = new Timer(1500, e1 -> {
                    isTraversing = false;
                    graphThread = null;
                    sync = null;
                    repaint();
                });
                t2.setRepeats(false);
                t2.start();
            }
        }

    }

}
