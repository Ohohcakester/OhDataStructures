import java.util.Arrays;

public class TestGraph {
    
    public static void main(String[] args) {
        
        AbstractGraph graph = setupGraph5();
        
        int startNode = 0;
        
        MinSpanningTree mst = new Prim(graph, startNode);
        System.out.println("Prim:    " + Arrays.toString(mst.getParent()));
        
        mst = new Kruskals(graph);
        System.out.println("Kruskal: " + Arrays.toString(mst.getParent()));
        
        System.out.println();
        
        ShortestPath sp = new BellmanFord(graph, startNode);
        System.out.println("Bellman-Ford:");
        System.out.println(Arrays.toString(sp.getDistance()));
        System.out.println(Arrays.toString(sp.getParent()));
        
        sp = new Dijkstra(graph, startNode);
        System.out.println("Dijkstra:");
        System.out.println(Arrays.toString(sp.getDistance()));
        System.out.println(Arrays.toString(sp.getParent()));
    }
    
    public static AbstractGraph setupGraph1() {
        
        AbstractGraph graph = new MatrixGraph(9, true);
        
        graph.addEdge(0,1,25);
        graph.addEdge(0,4,15);
        graph.addEdge(0,3,3);
        graph.addEdge(2,1,2);
        graph.addEdge(3,6,8);
        graph.addEdge(3,8,2);
        graph.addEdge(4,2,4);
        graph.addEdge(5,4,2);
        graph.addEdge(6,5,5);
        graph.addEdge(7,4,3);
        graph.addEdge(8,7,4);
        
        return graph;
    }
    
    public static AbstractGraph setupGraph2() {
        
        AbstractGraph graph = new ListGraph(9, false);
        
        graph.addEdge(0,1,25);
        graph.addEdge(0,4,15);
        graph.addEdge(0,3,3);
        graph.addEdge(2,1,2);
        graph.addEdge(3,6,8);
        graph.addEdge(3,8,2);
        graph.addEdge(4,2,4);
        graph.addEdge(5,4,2);
        graph.addEdge(6,5,5);
        graph.addEdge(7,4,3);
        graph.addEdge(8,7,4);
        
        return graph;
    }
    
    
    public static AbstractGraph setupGraph3() {
        
        AbstractGraph graph = new ListGraph(9, true);
        
        graph.addEdge(0,1,25);
        graph.addEdge(0,4,15);
        graph.addEdge(0,3,3);
        graph.addEdge(2,1,2);
        graph.addEdge(3,6,8);
        graph.addEdge(3,8,2);
        graph.addEdge(4,2,4);
        graph.addEdge(5,4,2);
        graph.addEdge(6,5,5);
        //graph.addEdge(7,4,-3);
        graph.addEdge(7,4,-2);
        graph.addEdge(4,6,-6);
        
        graph.addEdge(8,7,4);
        
        return graph;
    }
    
    public static AbstractGraph setupGraph4() {
        
        AbstractGraph graph = new ListGraph(9, true);
        
        graph.addEdge(0,1,25);
        graph.addEdge(0,4,15);
        graph.addEdge(0,3,3);
        graph.addEdge(2,1,2);
        graph.addEdge(3,6,8);
        graph.addEdge(3,8,2);
        graph.addEdge(4,2,4);
        graph.addEdge(5,4,2);
        graph.addEdge(6,5,5);
        //graph.addEdge(7,4,-3);
        graph.addEdge(7,4,-2);
        graph.addEdge(4,6,-9);
        
        graph.addEdge(8,7,4);
        
        return graph;
    }
    
    public static AbstractGraph setupGraph5() {
        AbstractGraph graph = new ListGraph(9,false);
        
        graph.addEdge(0,1,2);
        graph.addEdge(0,3,5);
        graph.addEdge(1,3,2);
        graph.addEdge(1,4,8);
        graph.addEdge(2,3,4);
        graph.addEdge(3,5,1);
        graph.addEdge(3,8,6);
        graph.addEdge(4,7,6);
        graph.addEdge(5,6,7);
        graph.addEdge(5,7,4);
        graph.addEdge(6,7,2);
        graph.addEdge(6,8,3);
        graph.addEdge(7,8,2);
        
        return graph;
    }
    
}