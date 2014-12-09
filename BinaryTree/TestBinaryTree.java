import java.io.*;
    import java.util.List;
    import java.util.Arrays;
    import java.util.Collections;

/**
 *  Class to test the BinaryTree class
 */
public class TestBinaryTree {

    public static void main(String[] args) throws Exception {
        /*
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BinaryTree<String> tree = BinaryTree.readBinaryTree1(br);
        
        if (tree == null)
            System.out.println("Invalid input");
        else {
            System.out.println(tree);
            
            if (!tree.isComplete())
                System.out.println("not complete");
            System.out.println("levelorder: " + tree.levelorderToString()); 
            
        }*/
        /*
        BinarySearchTree<String> bt = new BinarySearchTree<>();
        
        //String sentence = "happy depressed manic sad ecstatic";
        String sentence = "ecstatic depressed manic sad happy";
        String[] words = sentence.split(" ");
        for (String word : words)
            bt.insertBST(word);
        
        System.out.println(bt.preorderToString());
        System.out.println(bt.postorderToString());
        System.out.println(bt.levelorderToString());
        System.out.println(bt.inorderToString());
        */
        
        
        BinarySearchTree<Integer> bt = new BinarySearchTree<>();
        
        Integer[] words = new Integer[] {90,30,15,50,60,20,25,45};
        for (Integer word : words)
            bt.insertBST(word);
        
        System.out.println(bt.preorderToString());
        System.out.println(bt.postorderToString());
        System.out.println(bt.levelorderToString());
        System.out.println(bt.inorderToString());
        
        /*
        BinaryTree<Integer> bt = new BinaryTree<>();
        insertMiddle(bt, 0, 256);
        bt.insertBST(256);
        
        String output = (bt.levelorderToString());
        String[] outputs = output.split(" ");
        List<String> stringList = Arrays.asList(outputs);
        Collections.shuffle(stringList);
        
        for (String s : stringList) {
            System.out.print(s + " ");
        }*/
    }
    
    /*
    public static void insertMiddle(BinaryTree<Integer> bt, int start, int end) {
        if (end - start <= 1)
            return;
        
        int mid = start + (end-start)/2;
        bt.insertBST(mid);
        insertMiddle(bt, start,mid);
        insertMiddle(bt, mid,end);
    }*/
}

