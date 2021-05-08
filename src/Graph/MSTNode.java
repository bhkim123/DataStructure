package Graph;

import java.util.ArrayList;

public class MSTNode implements Comparable<Object>{
    String name;
    boolean checked;
    double distance;
    String pointing; // node that this node is pointing in MST
    ArrayList<String> pointed; // node list that is pointing this node in MST
    public 	MSTNode(String name){
        this.name = name;
        checked = false;
        distance = Double.MAX_VALUE;
        pointing = null;
        pointed = new ArrayList<>();
    }
    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        if(this.distance - ((MSTNode) o).distance < 0) {
            return -1;
        }
        else if(this.distance - ((MSTNode) o).distance > 0) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
