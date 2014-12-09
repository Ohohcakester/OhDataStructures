
public class TestArrayTree {
    
    public static void main(String[] args) {
        ArrayTree<String> tree = new ArrayTree<String>(16);
        for (int i=0; i<6; i++) {
        String sentence = "L Z B W H Y U Q R N M K J";
        String[] words = sentence.split(" ");
        for (String word : words) {
            inp(tree, word);
        }
        System.out.println(tree.inorderToString());
        sentence = "U Q R Z B W H Y N L M K J";
        words = sentence.split(" ");
        for (String word : words) {
            dnp(tree, word);
        }
        System.out.println(tree.inorderToString());
        }
    }
    
    public static void inp(ArrayTree<String> tree, String value) {
        tree.insert(value);
        System.out.println(tree);
    }
    
    public static void dnp(ArrayTree<String> tree, String value) {
        tree.delete(value);
        System.out.println(tree);
    }
}