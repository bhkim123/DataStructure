package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Graph {
    private HashMap<String, ArrayList<Edge>> Graphs;
    //each node has its edges.

    public Graph(){
        this.Graphs = new HashMap<>();
    }

    public Graph(HashMap<String, ArrayList<Edge>> Graphs){
        this.Graphs = Graphs;
    }

    public Set<String> getNodes(){
        return Graphs.keySet();
    }

    public void addNodes(String node, ArrayList<Edge> e){
        Graphs.put(node, e);
    }

    public ArrayList<Edge> getEdgeList(String node){
        return Graphs.get(node);
    }
}
