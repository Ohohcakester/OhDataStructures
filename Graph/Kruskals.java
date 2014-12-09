import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Kruskals extends MinSpanningTree {
    
    private UnionSet sets;
    private PriorityQueue<Edge> pq;
    
    private class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge e1, Edge e2) {
            double result =  e1.getWeight() - e2.getWeight();
            if (result < 0) return -1;
            if (result > 0) return 1;
            return 0;
        }
    }
    
    public Kruskals(Graph g) {
        super(g);
        initialiseSpanningTree();
        
        int nNodes = graph.getNumV();
        int nEdges = 0;
        
        pq = new PriorityQueue<Edge>(nNodes, new EdgeComparator());
        sets = new UnionSet(nNodes);
        
        // Add everything to the priorityqueue.
        for (int i=0; i<nNodes; i++) {
            Iterator<Edge> itr = graph.edgeIterator(i);
            while (itr.hasNext())
                pq.offer(itr.next());
        }
        
        while(!pq.isEmpty()) {
            Edge current = pq.poll();
            int src = current.getSource();
            int dest = current.getDest();
            if (!sets.sameSet(src, dest)) {
                // Add edge to tree.
                //parent[dest] = src;
                insert(current);
                sets.union(src, dest);
                nEdges++;
            }
        }
        generateParentArray();
        //System.out.println(nEdges);
    }
    
}