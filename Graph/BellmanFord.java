import java.util.LinkedList;
import java.util.Iterator;

public class BellmanFord extends ShortestPath {
    
    private LinkedList<Edge> createEdgeList() {
        LinkedList<Edge> edgeList = new LinkedList<Edge>();
        for (int i=0; i<graph.getNumV(); i++) {
            Iterator<Edge> itr = graph.edgeIterator(i);
            while (itr.hasNext())
                edgeList.offer(itr.next());
        }
        return edgeList;
    }
 
    public BellmanFord(Graph graph, int s) {
        super(graph);
        initialise(s);
        
        LinkedList<Edge> edgeList = createEdgeList();
        
        int vMinus1 = graph.getNumV()-1;
        for (int blah=0; blah<vMinus1; blah++) {
            boolean didSomething = false;
            for (Edge edge : edgeList) {
                if (relax(edge))
                    didSomething = true;
            }
            if (!didSomething) //System.out.println("early break: " + blah + " iterations");
                break;
        }
        
        for (Edge edge : edgeList) {
            if (relax(edge)) {
                invalid();
                return;
            }
        }
    }
    
    private void invalid() {
        // Has negative weight cycle.
        parent = null;
        distance = null;
    }
    
}