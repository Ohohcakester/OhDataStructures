
public class TestSplayTree {
 
    public static void main(String[] args) {
        
        SplayTree<Integer> tree = new SplayTree<>();
        /*for (int i=1; i<=30; i++) {
            insertAndPrint(i,tree);
            System.out.println("Inserted: " + (i*7%9+1));
        }
        for (int i=1; i<=9; i++)
            deleteAndPrint(tree.root.data,tree);*/
        
        insertAndPrint(5, tree);
        insertAndPrint(3, tree);
        insertAndPrint(6, tree);
        insertAndPrint(7, tree);
        insertAndPrint(4, tree);
        insertAndPrint(11, tree);
        insertAndPrint(8, tree);
        insertAndPrint(10, tree);
        
        //searchAndPrint(7, tree);
        //deleteAndPrint(7, tree);
    }
    
    public static void insertAndPrint(int num,  SplayTree<Integer> tree) {
        tree.insertTopDown(num);
        System.out.println(tree.inorderToString());
    }
    
    public static void searchAndPrint(int num,  SplayTree<Integer> tree) {
        System.out.println("Search for: " + tree.searchBottomUp(num));
        System.out.println(tree.inorderToString());
    }
    
    public static void deleteAndPrint(int num,  SplayTree<Integer> tree) {
        System.out.println("Deleted: " + tree.deleteBottomUp(num));
        System.out.println(tree.inorderToString());
    }
    
}