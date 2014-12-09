import java.util.*;
//Oh's Implementation of a 2-3-4 Tree
// Deletion is inherited from the BTree class.

public class TestTTFTree {
 
    public static void main(String[] args) {
        q5();
    }
    
    public static void q5() {
        TTFTree<String> tree = new TTFTree<String>();
        
        String sentence = "Now is the time for all good men to come to the aid of the world party";
        String[] words = sentence.split(" ");
        for (String word : words) {
            System.out.println("Insert " + word);
            tree.insert(word);
            System.out.println(tree);
        }
    }

    
    public static void q4(){
        TTFTree<Integer> tree = new TTFTree<Integer>();
        
        tree.insert(62);
        tree.insert(14);
        tree.insert(21);
        tree.insert(4);
        tree.insert(15);
        tree.insert(56);
        tree.insert(90);
        tree.insert(79);
        tree.insert(68);
        tree.insert(28);
        tree.insert(38);
        tree.insert(71);
        tree.insert(55);
        tree.insert(15);
        
        System.out.println(tree);
        
        insertAndPrint(1,tree);
        insertAndPrint(5,tree);
        insertAndPrint(9,tree);
        insertAndPrint(13,tree);
        insertAndPrint(2,tree);
        insertAndPrint(3,tree);
        insertAndPrint(91,tree);
        insertAndPrint(92,tree);
        
    }
    
    public static void insertAndPrint(int value, TTFTree<Integer> tree) {
        System.out.println("Insert " + value);
        tree.insert(value);
        System.out.println(tree);
    }
    
    public static void deleteAndPrint(int a, TTFTree<Integer> tree) {
        System.out.println("Delete " + a);
        tree.delete(a);
        System.out.println(tree);
        System.out.println(tree.size());
    }
    
    public static void search(int value, TTFTree<Integer> tree) {
        System.out.println("Contains " + value + ": " + tree.contains(value));
    }
}