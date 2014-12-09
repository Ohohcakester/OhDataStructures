import java.util.LinkedList;
import java.util.Iterator;

public class Dijkstra extends ShortestPath {
    
    IndirectHeap<Double> pq; 
    
    public Dijkstra(Graph graph, int s) {
        super(graph);
        initialise(s);
        boolean[] visited = new boolean[graph.getNumV()];
        
        pq = new IndirectHeap<Double>(distance, true);
        pq.heapify();
        
        while (!pq.isEmpty()) {
            int current = pq.popMinIndex();
            visited[current] = true;
            
            Iterator<Edge> itr = graph.edgeIterator(current);
            while (itr.hasNext()) {
                Edge edge = itr.next();
                if (!visited[edge.getDest()] && relax(edge)) {
                    // If relaxation is done.
                    pq.decreaseKey(edge.getDest(), distance[edge.getDest()]);
                }
            }
        }
        
    }
}
