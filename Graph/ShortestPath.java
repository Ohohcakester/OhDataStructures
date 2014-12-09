import java.util.LinkedList;
import java.util.Iterator;

public abstract class ShortestPath {
 
    protected Graph graph;
    protected Double distance[];
    protected int parent[];
    
    protected ShortestPath(Graph graph) {
        this.graph = graph;
        distance = new Double[graph.getNumV()];
        parent = new int[graph.getNumV()];
    }
    
    public Double[] getDistance(){return distance;}
    public int[] getParent(){return parent;}
    
    protected final boolean relax(int u, int v, double weightUV) {
        // return true iff relaxation is done.
        
        double newWeight = distance[u] + weightUV;
        if (newWeight < distance[v]) {
            distance[v] = newWeight;
            parent[v] = u;
            return true;
        }
        return false;
    }
    
    protected final boolean relax(Edge edge) {
        // return true iff relaxation is done.
        return relax(edge.getSource(), edge.getDest(), edge.getWeight());
    }
    
    protected final void initialise(int s) {
        for (int i=0; i<distance.length; i++) {
            distance[i] = Double.POSITIVE_INFINITY;
            parent[i] = -1;
        }
        distance[s] = 0d;
    }
}