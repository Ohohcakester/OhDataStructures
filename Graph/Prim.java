import java.util.Iterator;
import java.util.Comparator;

public class Prim extends MinSpanningTree {
    
    private IndirectHeap<Double> pq;
    protected Double distance[];
    
    private void initialise(int start) {
        initialise();
        int nNodes = graph.getNumV();
        for (int i=0; i<nNodes; i++) {
            distance[i] = Double.POSITIVE_INFINITY;
        }
        distance[start] = 0d;
    }
    
    private boolean relax(int src, int dest, double weight) {
        if (distance[dest] > weight) {
            parent[dest] = src;
            distance[dest] = weight;
            return true;
        }
        return false;
    }
    
    private boolean relax(Edge edge) {
        return relax(edge.getSource(), edge.getDest(), edge.getWeight());  
    }
    
    public Prim(Graph g, int start) {
        // Note: assume connected
        super(g);
        distance = new Double[g.getNumV()];
        initialise(start);
        
        pq = new IndirectHeap<Double>(distance, true);
        pq.heapify();
        
        while (!pq.isEmpty()) {
            int current = pq.popMinIndex();
            visited[current] = true;
            
            Iterator<Edge> itr = graph.edgeIterator(current);
            while(itr.hasNext()) {
                Edge edge = itr.next();
                if (!visited[edge.getDest()] && relax(edge)) {
                    // If relaxation is done.
                    pq.decreaseKey(edge.getDest(), distance[edge.getDest()]);
                }
            }
        }
    }
}
