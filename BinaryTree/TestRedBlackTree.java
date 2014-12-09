import java.util.Random;

public class TestRedBlackTree {
    
    public static void main(String[] args) {
        
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        RedBlackTree<Integer> treeAlt = new RedBlackTree<>();
        Random rand = new Random();
        
        int[] values = new int[]{1,0,3,19,9,2,10,4,18,8,7,6,13,11,14,17,12,16};
        int errors = 0;
        boolean same = true;
        int testNo = 0;
        while (same && testNo<100000) {
            tree = new RedBlackTree<>();
            treeAlt = new RedBlackTree<>();
            
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<40; i++) {
                //int value = i*517%1907;
                //int value = rand.nextInt(21);
                int value = values[i];
                
                if (tree.contains(value)) {
                    continue;
                }
                
                sb.append(value);
                sb.append(" ");
                
                //System.out.println("Insert " + value);
                tree.insert(value);
                treeAlt.insertAlt(value);
                String r1 = tree.inorderToString();
                String r2 = treeAlt.inorderToString();
                
                    System.out.println(r1);
                    System.out.println(r2);
                    tree.logAll();
                    treeAlt.logAll();
                    
                if (!r1.equals(r2)) {
                    System.out.println("Test " + testNo + ". Not Same!");
                    System.out.println(r1);
                    System.out.println(r2);
                    System.out.println("Order: " + sb.toString());
                    System.out.println("Insertions: " + tree.size());
                    same = false;
                    errors++;
                    break;
                }
            }
            if (same) {
                //System.out.println("Order: " + sb.toString());
                //System.out.println("Test " + testNo + ". Same.");
            }
            testNo++;
        }
        if (same)
            System.out.println("Same.");
        
        
    }
    
    public static void tut() {
        RedBlackTree<String> tree = new RedBlackTree<String>();
        
        String sentence = "Now is the time for all good men to come to the aid of the party";
        String words[] = sentence.split(" ");
        for (String word : words) {
            tree.insert(word);
            System.out.println(tree.inorderToString());
        }
        
        tree.logAll();
        
        /*
        RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
       
        for (int i=1; i<16; i++) {
            insertAndPrint(i*19%47, tree);
            //tree.logAll();
        }
        
        tree.logAll();*/
        
    }
    
    public static void insertAndPrint(int value, RedBlackTree<Integer> tree) {
        System.out.println("Insert " + value);
        tree.insert(value);
        System.out.println(tree.inorderToString());
    }
}