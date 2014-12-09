public abstract class MinSpanningTree {
 
    protected Graph graph;
    protected boolean visited[];
    protected int parent[];
    protected Graph spanningTree;
    
    public MinSpanningTree(Graph g) {
        graph = g;
        parent = new int[g.getNumV()];
        visited = new boolean[g.getNumV()];
    }
    
    protected void initialiseSpanningTree() {
        spanningTree = new ListGraph(graph.getNumV(), false);
    }
    
    protected void initialise() {
        int nNodes = graph.getNumV();
        for (int i=0; i<nNodes; i++) {
            parent[i] = -1;
            visited[i] = false;
        }
    }
    
    protected void insert(Edge edge) {
        spanningTree.insert(edge);
    }
    
    protected void generateParentArray() {
        DepthFirstSearch dfs = new DepthFirstSearch(spanningTree);
        parent = dfs.getParent();
    }
    
    
    public int[] getParent() {
        return parent;
    }
}