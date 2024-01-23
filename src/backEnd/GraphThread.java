package backEnd;

import algorithm.Graph;
import datastructure.Sync;

public class GraphThread extends Thread{

    Sync sync;
    int[][] edges;
    Graph g;
    int startId;

    public GraphThread(Sync sync)
    {
        this.sync = sync;
        g = new Graph(sync);
        edges = g.createRandomGraph();
        startId = 0;
    }

    public int[][] getEdges()
    {
        return edges;
    }

    public void setStartId(int id)
    {
        startId = id;
    }

    @Override
    public void run() {

        g.BFS(startId);
        
    }


}
