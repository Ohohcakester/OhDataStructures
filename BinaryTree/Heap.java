import java.io.*;
import java.util.*;
/**
 * Class for a binary tree that stores type E objects.
 * Node is a public class.
 **/

public class Heap<E extends Comparable<? super E>> extends BinaryTree<E> implements Serializable {

    // Data Field
    /** The root of the binary tree */
    private ArrayList<Node<E>> nodeList;
    private boolean minHeap;
    
    private Comparator<E> comparator;
    
    private int compare(E a, E b) {
        if (comparator == null) {
            return a.compareTo(b);
        }
        return comparator.compare(a,b);
    }
    
    /** Construct an empty BinaryTree */
    public Heap(boolean minHeap) {
        root = null;
        nIncompleteNodes = 0;
        this.minHeap = minHeap;
        nodeList = new ArrayList<>();
    }
    
    public Heap(E[] array, boolean minHeap) {
        this(minHeap);
        nodeList.ensureCapacity(array.length);
        for (E e : array)
            nodeList.add(new Node<E>(e));
        
        for (int i=0; i<array.length; i++)
            linkNode(i);
    }
    
    public void setComparator(Comparator<E> comparator) {
        this.comparator = comparator;
    }
    
    public void heapify() {
        for (int i=nodeList.size()/2-1; i>=0; i--) {
            bubbleDown(i);
        }
    }

    public void insert(E item) {
        int index = nodeList.size();
        
        Node<E> newNode = new Node<E>(item);
        nodeList.add(newNode);
        linkNode(index);
        bubbleUp(index);
    }
    
    private void linkNode(int index) {
        // Links the the current node's parent to the current node.
        
        if (index == 0)
            root = nodeList.get(0);
        else {
            // Even number = right child.
            // Odd number = left child.
            int parent = parent(index);
            if (index%2 == 0) {
                // Right child
                nodeList.get(parent).right = nodeList.get(index);
            }
            else {
                // Left child
                nodeList.get(parent).left = nodeList.get(index);
            }
        }
    }
    
    private void bubbleUp(int index) {
        if (index == 0) // Reached root
            return;
        
        int parent = (index-1)/2;
        if ((compare(nodeList.get(index).data, nodeList.get(parent).data) < 0) == minHeap) {
            // If meets the conditions to bubble up,
            swapData(index,parent);
            bubbleUp(parent);
        }
    }
    
    private void swapData(int a, int b) {
        E temp = nodeList.get(a).data;
        nodeList.get(a).data = nodeList.get(b).data;
        nodeList.get(b).data = temp;
    } 
    
    
    private int smallerNodeIffMin(int index1, int index2) {
        if (index1 >= nodeList.size()) {
            if (index2 >= nodeList.size())
                return -1;
            
            return index2;
        }
        if (index2 >= nodeList.size())
            return index1;
        
        return ((compare(nodeList.get(index1).data, nodeList.get(index2).data) < 0) == minHeap) ? index1 : index2;
    }
    
    private void bubbleDown(int index) {
        int leftChild = leftChild(index);
        int rightChild = rightChild(index);
        
        int smallerChild = smallerNodeIffMin(leftChild, rightChild);
        if (smallerChild == -1) return;
        
        if ((compare(nodeList.get(index).data, nodeList.get(smallerChild).data) > 0) == minHeap) {
            // If meets the conditions to bubble down,
            swapData(index,smallerChild);
            bubbleDown(smallerChild);
        }
    }
    
    public void decreaseKey(E key) {
        
    }
    
    public E pop() {
        if (nodeList.size() == 0)
            return null;
        else if (nodeList.size() == 1) {
            root = null;
            return nodeList.remove(0).data;
        }
        // nodeList.size() > 1
        E temp = root.data;
        int lastIndex = nodeList.size()-1;
        root.data = nodeList.get(lastIndex).data;
        nodeList.remove(lastIndex);
        
        // Unlink the parent.
        int parent = parent(lastIndex);
        if (lastIndex%2 == 0) {
            // Right child
            nodeList.get(parent).right = null;
        }
        else {
            // Left child
            nodeList.get(parent).left = null;
        }
        
        bubbleDown(0);
        
        return temp;
    }
    
    public String arrayToString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<nodeList.size(); i++) {
            sb.append(i);
            sb.append(" ");
            sb.append(nodeList.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private static int parent(int index) {
        return (index-1)/2;
    }
    
    private static int leftChild(int index) {
        return 2*index+1;
    }
    
    private static int rightChild(int index) {
        return 2*index+2;
    }
}