package algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import datastructure.Sync;

class Node
{
    int id;
    LinkedList<Node> adjacent = new LinkedList<>();
    
    Node(int id)
    {
        this.id = id;
    }
}

public class Graph {

    private HashMap<Integer, Node> nodeLookup = new HashMap<>();
    private Sync sync;

    public Graph(Sync sync)
    {
        this.sync = sync;
    }

    private Node getNode(int id)
    {
        if(nodeLookup.containsKey(id) == false)
            nodeLookup.put(id, new Node(id));
        return nodeLookup.get(id);
    }
    
    public void addEdge(int source, int destination)
    {
        if(source == destination)
            return;
        
        Node s = getNode(source);
        Node d = getNode(destination);
        
        if(s.adjacent.contains(d) == false)
        {
            s.adjacent.add(d);
            System.out.println(s.id + " " + d.id);
        }
    }

    public void BFS(Integer startId)
    {
        if(nodeLookup.containsKey(startId) == false)
            return;
        
        Node root = getNode(startId);

        LinkedList<Node> nextToVisit = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();
        nextToVisit.add(root);

        while(!nextToVisit.isEmpty())
        {
            Node node = nextToVisit.remove();

            if(visited.contains(node.id))
                continue;
            
            System.out.println(node.id + ", ");

            sync.send(node.id, id -> visited.add(id) );
            //visited.add(node.id);

            for(Node child : node.adjacent)
                nextToVisit.add(child);

        }

        sync.isCompleted = true;
    }

    public int[][] createRandomGraph()
    {
        int[][] edges = new int[20][2];
        for(int i = 0; i < 20; ++i)
        {
            int id1 = (int)(Math.random() * 7);
            int id2 = (int)(Math.random() * 7);
            addEdge(id1, id2);
            addEdge(id2, id1);
            edges[i][0] = id1;
            edges[i][1] = id2;
        }
        return edges;
    }
    
    // public static void main(String[] args)
    // {
    //     Graph g = new Graph();
    //     g.createRandomGraph();

    //     g.BFS(1);

    // }

}
