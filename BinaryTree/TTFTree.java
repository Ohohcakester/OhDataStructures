import java.io.*;
import java.util.*;


public class TTFTree<E extends Comparable<? super E>> extends BTree<E> {
    
    public TTFTree() {
        super(4);
    }
    
    protected class TTFSplitter<E> extends BTSplitter<E> {
        //Note: order == 4.
        public TTFSplitter(BTNode<E> current) {
            // Note: used on a node of size 3. (a 4-node)
            if (current.size != 3)
                throw new IllegalArgumentException("You can only TTFSplit 4-nodes.");
            
            newRightChild = new BTNode<E>();
            excessData = current.data[1];
            
            newRightChild.data[0] = current.data[2];
            newRightChild.child[0] = current.child[2];
            newRightChild.child[1] = current.child[3];
            
            current.data[1] = null;
            current.data[2] = null;
            current.child[2] = null;
            current.child[3] = null;
            
            //System.out.println("CURR: " + Arrays.toString(current.data));
            //System.out.println("NEWRC: " + Arrays.toString(newRightChild.data));
            
            current.size = 1;
            newRightChild.size = 1;
        }
    }
    
    @Override    
    public void insert(E data) {
        if (root == null) {
            root = new BTNode<E>(data);
            return;
        }
        
        if (root.size == 3) { //System.out.println("Split Root");
            TTFSplitter<E> splitter = new TTFSplitter<E>(root);
            BTNode<E> newRoot = new BTNode<E>();
            newRoot.data[0] = splitter.excessData;
            newRoot.child[0] = root;
            newRoot.child[1] = splitter.newRightChild;
            newRoot.size = 1;
            root = newRoot;
        }
        insert(root, data);
    }
    
    private boolean splitChildIf4Node(BTNode<E> current, int position) {
        if (current.child[position].size == 3) {
            //split.
            TTFSplitter<E> splitter = new TTFSplitter<E>(current.child[position]);
            for (int i=1; i>=position; i--) {
                current.data[i+1] = current.data[i];
                current.child[i+2] = current.child[i+1];
            }
            current.data[position] = splitter.excessData;
            current.child[position+1] = splitter.newRightChild;
            current.size++;
            return true;
        }
        return false;
    }
    
    private void insert(BTNode<E> current, E data) {
        // Current node will be a 2-Node or 3-Node.
        // Because a split has been done before entering.
        if (current.size == 3)
            throw new IllegalArgumentException("Inserting into a 4-Node. Should have been split beforehand.");
        
        int position = linearSearch(current.data, data, current.size);
        if (position == -1) return;
        
        if (current.isLeaf()) {
            insertNode(current, position, data);
            return;
        }
        
        if (splitChildIf4Node(current, position)) {
            // Splitting occurred in child.
            // Compare with new parent data to see which side to go to.
            int compare = current.data[position].compareTo(data);
            if (compare == 0) return;
            if (compare > 0) // Go left
                insert(current.child[position], data);
            else // compare < 9 // Go right
                insert(current.child[position+1], data);
        }
        else {
            // No splitting occurred in child.
            insert(current.child[position], data);
        }
           
    }
    
    private int linearSearch(E[] array, E data, int size) {
        // Note: size of array is either 1 or 2.
        int compare = array[0].compareTo(data);
        if (compare == 0) return -1;
        if (compare > 0) return 0; // Go left
        
        // else compare < 0 // Go right
        if (size != 2) return 1;
        
        compare = array[1].compareTo(data);
        if (compare == 0) return -1;
        if (compare > 0) return 1; // Go left
        return 2; // Go right
    }
    
    private void insertNode(BTNode<E> current, int position, E data) {
        // Note: only for leaf nodes
        for (int i=current.size-1; i>=position; i--) {
            current.data[i+1] = current.data[i];
        }
        current.data[position] = data;
        current.size++;
    }
    
}