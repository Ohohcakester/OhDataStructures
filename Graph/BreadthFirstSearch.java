import java.util.LinkedList;
import java.util.Iterator;

public class BreadthFirstSearch {
    
    private Graph graph;
    private boolean[] visited;
    
    private int discoveryIndex;
    public int[] discoveryOrder;
    public int[] parent;
    
    public BreadthFirstSearch(Graph graph) {
        this.graph = graph;
        
        discoveryOrder = new int[graph.getNumV()];
        parent = new int[graph.getNumV()];
        visited = new boolean[graph.getNumV()];
        
        discoveryIndex = 0;
        
        int order = graph.getNumV();
        
        for (int i=0; i<order; i++) {
            if (!visited[i])
                bfs(i);
        }
    }
    
    private void bfs(int start) {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        parent[start] = -1;
        queue.offer(start);
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            visited[current] = true;
            discoveryOrder[discoveryIndex] = current;
            discoveryIndex++;
            
            Iterator<Edge> itr = graph.edgeIterator(current);
            while (itr.hasNext()) {
                Edge edge = itr.next();
                int dest = edge.getDest();
                if (!visited[dest]) {
                    parent[dest] = current;
                    queue.offer(dest);
                }
            }
        }
    }
    
}