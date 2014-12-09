import java.util.*;

public class TestBTree {
 
    public static void main(String[] args) {
        q2();
    }
    
    public static void q2() {
        BTree<Integer> tree = new BTree<Integer>(5);
        
        tree.insert(5);
        tree.insert(7);
        tree.insert(10);
        tree.insert(17);
        tree.insert(13);
        tree.insert(15);
        tree.insert(18);
        tree.insert(20);
        tree.insert(22);
        tree.insert(30);
        tree.insert(26);
        tree.insert(27);
        tree.insert(40);
        tree.insert(32);
        tree.insert(35);
        tree.insert(42);
        tree.insert(46);
        tree.insert(8);
        tree.insert(38);
        tree.insert(39);
        
        System.out.println(tree);
        
        deleteAndPrint(30, tree);
        deleteAndPrint(26, tree);
        deleteAndPrint(15, tree);
        deleteAndPrint(17, tree);
        deleteAndPrint(42, tree);
    }
        
    public static void q1() {
        BTree<Integer> tree = new BTree<Integer>(5);
        
        insertAndPrint(20,tree);
        insertAndPrint(30,tree);
        insertAndPrint(8,tree);
        insertAndPrint(10,tree);
        insertAndPrint(15,tree);
        
        insertAndPrint(18,tree);
        insertAndPrint(44,tree);
        insertAndPrint(26,tree);
        insertAndPrint(28,tree);
        insertAndPrint(23,tree);
        
        insertAndPrint(25,tree);
        insertAndPrint(43,tree);
        insertAndPrint(55,tree);
        insertAndPrint(36,tree);
        insertAndPrint(39,tree);
        
        insertAndPrint(7,tree);
        insertAndPrint(9,tree);
        insertAndPrint(66,tree);
        insertAndPrint(77,tree);
        
        
        System.out.println(tree.size());
        System.out.println(tree);
    }
    
    public static void insertAndPrint(int value, BTree<Integer> tree) {
        tree.insert(value);
        System.out.println(tree);
    }
    
    public static void deleteAndPrint(int a, BTree<Integer> tree) {
        System.out.println("Delete " + a);
        tree.delete(a);
        System.out.println(tree);
        System.out.println("Size: " + tree.size());
    }
    
    
    public static void search(int value, BTree<Integer> tree) {
        System.out.println("Contains " + value + ": " + tree.contains(value));
    }
}