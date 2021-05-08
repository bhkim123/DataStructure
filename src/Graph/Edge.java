package Graph;

import java.util.NoSuchElementException;

public class Edge {
    String v1;
    String v2;
    double weight;
    public Edge(String v1, String v2, double weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public String getOpposite(String v) {
        if (v.equals(v1)) return v2;
        else if (v.equals(v2)) return v1;
        else throw new NoSuchElementException("Incorrect input");
    }

    public double getWeight(){
        return weight;
    }
}
