package Graph;

import java.util.*;

public class GraphAlgorithm {
    public static ArrayList<Edge> shortestPath(Graph graph, String from, String to){
        final ArrayList<Edge> path = new ArrayList<>();

        // DUMMY CODE (STUB) TODO

        HashMap<String, Double> distance = new HashMap<>();

        for(String str : graph.getNodes()) {
            distance.put(str, Double.MAX_VALUE);
        }
        distance.replace(from, 0.0);
        // Set start node to 0.0 and other nodes to max value.

        LinkedList<String> checkList = new LinkedList<>();
        checkList.add(from);
        // Add start node to check nodes list

        HashMap<String, ArrayList<Edge>> shortestPath = new HashMap<>();
        for(String str : graph.getNodes()) {
            shortestPath.put(str, new ArrayList<Edge>());
        }
        // Each node has shortest path. Each node doesn't have any path at the first.

        while(!checkList.isEmpty()) {
            String currentNode = checkList.poll();
            // Node to check. Since it will be checked in the next steps,
            // remove from check list.

            ArrayList<Edge> paths = (ArrayList<Edge>) shortestPath.get(currentNode);
            // Shortest path of current node that we are checking.

            double totalWeight = 0;
            for(int i = 0; i < paths.size(); i++) {
                totalWeight += paths.get(i).getWeight();
            }// Calculate total weight of shortest path.


            // Check every adjacency node of current node
            // if its shortest path's weight is larger than current node's weight + edge's weight
            // that connects current node and adjacency node.
            for(int i = 0; i < graph.getEdgeList(currentNode).size(); i++) {
                Edge e = graph.getEdgeList(currentNode).get(i);
                String opposite = e.getOpposite(currentNode);

                if(totalWeight + e.getWeight() < distance.get(opposite)) {
                    distance.replace(opposite, totalWeight + e.getWeight());
                    ArrayList<Edge> newEdges = new ArrayList<>();
                    for(Edge ed : paths) {
                        newEdges.add(ed);
                    }
                    newEdges.add(e);
                    shortestPath.replace(opposite, newEdges);
                    // If the current node's weight + edge's weight
                    // that connects current node and adjacency node is smaller
                    // than adjacency node's shortest path's weight,
                    // update with smaller path.

                    checkList.add(opposite);
                    // If the shortest path was updated,
                    // add node to check list to check again later.
                }
            }
        }


        for(Edge e : shortestPath.get(to)) {
            path.add(e);
        } // path = shortest path of destination node

        return path;
    }

    public static Graph MST(Graph graph){
        Set<String> nds = graph.getNodes();
        // Node set

        if(nds.size() <= 2)
            return graph;
        //If node is not greater than 2, mst = graph. So, return graph itself.

        HashMap<String, MSTNode> mstNodes = new HashMap<>(); // Hash map to get information of nodes
        PriorityQueue<MSTNode> nodes = new PriorityQueue<>(); // PQ to organize nodes. Node that has lower distance is lower node.

        for(String n : graph.getNodes()) {
            MSTNode v = new MSTNode(n);
            mstNodes.put(n, v);
            nodes.add(v);
        } // Set MStNode classes.

        MSTNode startNode = nodes.peek();
        startNode.distance = 0; // Set the first node's distance = 0 to start from here.

        while(!nodes.isEmpty()) {

            startNode = nodes.poll(); // First node from the list and remove it.
            List<Edge> edges = graph.getEdgeList(startNode.name); // Edge list of each node.
            String nodeName = startNode.name; // Each node name.

            for(int i = 0; i < edges.size(); i++) {
                String other = edges.get(i).getOpposite(nodeName);
                double weight = edges.get(i).getWeight();

                if(!mstNodes.get(other).checked
                        && mstNodes.get(other).distance > weight) {
                    mstNodes.get(other).distance = weight;

                    if(mstNodes.get(other).pointing != null) {
                        String p = mstNodes.get(other).pointing;
                        mstNodes.get(p).pointed.remove(other);
                    } // If pointing node of opposite node of checking node is not null,
                    // remove of pointing node from pointed node list of pointing node.

                    mstNodes.get(other).pointing = nodeName;
                    mstNodes.get(nodeName).pointed.add(other);
                    // Update pointing and pointed node.

                } // If each distance of opposite node of checking node is greater than
                // checking node distance + weight between them,
                // update distance to smaller value.

            }
            mstNodes.get(startNode.name).checked = true; // After checking node, mark that it is checked.
            if(nodes.size() > 0) {
                PriorityQueue<MSTNode> temp = new PriorityQueue<>();
                for(MSTNode v : nodes) {
                    temp.add(v);
                }
                nodes = temp;
            } // Re-organize nodes to check nodes from lower distance node.
        }// Do it until checking every node.

        HashMap<String, ArrayList<Edge>> mst = new HashMap<>(); // MST graph

        for(String n : nds) {

            ArrayList<String> oppositeNodes = new ArrayList<>(); // nodes of 'n' node that should have as opposite node for mst.
            ArrayList<Edge> mstEdges = new ArrayList<>(); // Edges of 'n' node that should have.

            if(mstNodes.get(n).pointing != null) {
                String other = mstNodes.get(n).pointing;
                oppositeNodes.add(other);
            } // Add pointing node.

            for(int i = 0; i < mstNodes.get(n).pointed.size(); i++) {
                String other = mstNodes.get(n).pointed.get(i);
                oppositeNodes.add(other);
            } // Add pointed nodes.

            for(int i = 0; i < graph.getEdgeList(n).size(); i++){
                String other = graph.getEdgeList(n).get(i).getOpposite(n);
                for(String str : oppositeNodes){
                    if(other.equals(str)){
                        Edge mstEdge = graph.getEdgeList(n).get(i);
                        mstEdges.add(mstEdge);
                        break;
                    }
                }
            } // Make edges connecting 'n' and nodes that 'n' node should have as opposite node
            // among total edges that 'n' has in original graph.

            mst.put(n, mstEdges); // Add edges to mst graph.
        }
        return new Graph(mst);
    }
}
