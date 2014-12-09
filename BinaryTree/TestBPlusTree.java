import java.util.Random;
import java.util.Scanner;

public class TestBPlusTree {
 
    public static void main(String[] args) {
        test();
    }
    
    public static void test() {
        BPlusTree<Integer> tree = new BPlusTree<Integer>(5);
        Scanner sc = new Scanner(System.in);
        
        while(true) {
        Random rand = new Random();
        for (int i=0; i<8000; i++) {
            switch(rand.nextInt(30)) {
                case 0:
                    tree.insert(i*53%200);
                
                case 1:
                    tree.delete(i*7%200);
                    
                default:
                    tree.delete(i*73%200);
            }
            if (i%1000 == 0)
                System.out.println(tree);
        }
        sc.nextLine();
        }
    }
    
    public static void q3() {
        BPlusTree<Integer> tree = new BPlusTree<Integer>(5);
        
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
        
    }
    
    public static void insertAndPrint(int value, BPlusTree<Integer> tree) {
        System.out.println("Insert " + value);
        tree.insert(value);
        System.out.println(tree);
    }
    
    public static void deleteAndPrint(int value, BPlusTree<Integer> tree) {
        System.out.println("Delete " + value);
        tree.delete(value);
        System.out.println(tree);
    }
}