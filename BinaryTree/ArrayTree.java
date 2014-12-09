import java.util.Arrays;

public class ArrayTree<E extends Comparable<? super E>> {
 
    private E data[];
    private int left[];
    private int right[];
    private int root;
    private int free;
    
    public ArrayTree(int maxSize) {
        data = (E[])(new Comparable[maxSize]);
        left = new int[maxSize];
        right = new int[maxSize];
        root = -1;
        free = 0;
        for (int i=0; i<maxSize-1; i++) {
            left[i] = i+1;
        }
        left[maxSize-1] = -1;
    }
    
    public void insert(E value) {
        if (root == -1) {
            int current = free;
            free = left[free];
            
            data[current] = value;
            left[current] = -1;
            right[current] = -1;
            root = current;
            return;
        }
        
        root = insert(root, value);
    }
    
    private int insert(int node, E value) {
        if (node == -1) {
            int current = free;
            free = left[free];
            
            data[current] = value;
            left[current] = -1;
            right[current] = -1;
            return current;
        }
        
        int compare = data[node].compareTo(value);
        if (compare == 0) {
            // Don't insert.
            return node;
        }
        
        if (compare < 0) {
            // insert right
            right[node] = insert(right[node], value);
        }
        
        else { // compare > 0
            left[node] = insert(left[node], value);
        }
        return node;
    }
    
    public void delete(E value) {
        root = delete(root, value);
    }
    
    private int delete(int node, E value) {
        if (node == -1) // not found
            return -1;
        
        int compare = data[node].compareTo(value);
        if (compare < 0) {
            // go right.
            right[node] = delete(right[node], value);
            return node;
        }
        else if (compare > 0) {
            // go left.
            left[node] = delete(left[node], value);
            return node;
        }
        else {// (compare == 0) {
            // delete current.
            if (left[node] == -1) {
                // No left child.
                int replacement = right[node];
                data[node] = null;
                left[node] = free;
                right[node] = -1;
                free = node;
                return replacement;
            }
            if (right[node] == -1) {
                // No right child.
                int replacement = left[node];
                data[node] = null;
                left[node] = free;
                right[node] = -1;
                free = node;
                return replacement;
            }
            // has both left and right child.
            // find inorder predecessor and delete.
            left[node] = deletePredecessor(left[node], node);
            return node;
        }
    }
    
    private int deletePredecessor(int node, int toReplace) {
        if (right[node] == -1) {
            int replacement = left[node];
            data[toReplace] = data[node];
            data[node] = null;
            left[node] = free;
            right[node] = -1;
            free = node;
            return replacement;
        }
        right[node] = deletePredecessor(right[node], toReplace);
        return node;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Arrays.toString(data)).append("\n");
        sb.append(Arrays.toString(left)).append("\n");
        sb.append(Arrays.toString(right)).append("\n");
        sb.append("Free: ").append(free).append("\n");
        sb.append("Root: ").append(root);
        return sb.toString();
    }
    
    public String inorderToString() {
        return inorderToString(root);
    }
    
    private String inorderToString(int node) {
        if (node == -1)
            return "";
        return inorderToString(left[node]) + " " + data[node].toString() + " " + inorderToString(right[node]);
    }
    
}