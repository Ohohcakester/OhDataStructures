public class TestAVLTree {
    
    public static void main(String[] args) {
        /*AVLTree<String> tree = new AVLTree<String>();
        //BinarySearchTree<String> tree = new BinarySearchTree<String>();
        
        //String sentence = "Now is the time for all good men to come to the aid of the party";
        String sentence = "jumps brown quick The fox over the dog lazy";
        String[] words = sentence.split(" ");
        for (String word : words)
            tree.insert(word);
        
        System.out.println(tree.inorderToString());
        System.out.println(tree.preorderToString());
        System.out.println(tree.height());
        tree.logAll();
        
        tree.insert("apple");
        
        System.out.println(tree.inorderToString());
        tree.logAll();
        
        tree.insert("cat");
        
        System.out.println(tree.inorderToString());
        tree.logAll();
        
        tree.insert("hat");
        
        System.out.println(tree.inorderToString());
        tree.logAll();*/
        
        
        
        AVLTree<Integer> tree = new AVLTree<Integer>();
        
        for (int i=1; i<10; i++) {
            tree.insert(i);
            System.out.println(tree.inorderToString());
        }
        
        //System.out.println(tree.inorderToString());
        System.out.println(tree.size() + ", " + tree.height());
    }
    
    public static void insert(int value, AVLTree<Integer> tree) {
        tree.insert(value);
        //System.out.println(tree.inorderToString());
        //System.out.println(tree.size() + ", " + tree.height());
    }
    
    public static void insert(String value, AVLTree<String> tree) {
        tree.insert(value);
        System.out.println(tree.size() + ", " + tree.height());
    }
}