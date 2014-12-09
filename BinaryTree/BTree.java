import java.io.*;
import java.util.*;

public class BTree<E extends Comparable<? super E>> {

    // Data Field
    /** The root of the binary tree */
    protected BTNode<E> root;
    public final int order;
    
    protected class BTSplitter<E>{
        public E excessData;
        public BTNode<E> newRightChild;
        
        public BTSplitter() {}
        
        public BTSplitter(E data) {
            excessData = data;
            newRightChild = null;
        }
        
        public BTSplitter(BTNode<E> current, int position, E data, BTNode<E> rightChild) {
            // sets excessData to the new data to be sent upwards
            // sets excessData to null if no splitting was actually done.
            
            if (current.size+1 < order) {
                // No need to split
                for (int i=current.size-1; i >= position; i--) {
                    // Shift data
                    current.data[i+1] = current.data[i];
                    current.child[i+2] = current.child[i+1];
                }
                current.data[position] = data;
                current.child[position+1] = rightChild;
                current.size++;
                excessData = null;
                return;
            }
            
            // current.size+1 == order)
            // Need to split
            newRightChild = new BTNode<E>();
            int midIndex = (order-1)/2;
            int currIndex = midIndex;    // curr index represents the index of the next item to transfer
            if (position == midIndex) {
                excessData = data;
                newRightChild.child[0] = rightChild;
                data = null;
            }
            // Set sizes
            newRightChild.size = current.size-midIndex;  // current.size+1 - currIndex
            current.size = midIndex;
            
            //System.out.println("pos: " + position);
            //System.out.println("curr: " + currIndex);
            //System.out.println("mid: " + midIndex);
            
            if (position > midIndex) {
                // new data in right side
                excessData = current.data[midIndex];
                newRightChild.child[0] = current.child[midIndex+1];
                current.data[midIndex] = null;
                
                // Insert the new data first
                newRightChild.data[position-midIndex-1] = data;
                newRightChild.child[position-midIndex] = rightChild;
                data = null;
                
                currIndex++;
            }
            int newIndex = 0;
            
            while(currIndex < order-1) {
                if (currIndex == position && position != midIndex) // Skip if it's the new data.
                    newIndex++;
                
                newRightChild.data[newIndex] = current.data[currIndex];
                newRightChild.child[newIndex+1] = current.child[currIndex+1];
                current.data[currIndex] = null;
                current.child[currIndex+1] = null;
                currIndex++;
                newIndex++;
            }
            
            if (data != null) {
                // new data in left side
                excessData = current.data[midIndex-1];
                newRightChild.child[0] = current.child[midIndex];
                current.data[midIndex-1] = null;
                
                for (int i=midIndex-1; i>=position; i--) {
                    current.data[i+1] = current.data[i];
                    current.child[i+2] = current.child[i+1];
                }
                current.data[position] = data;
                current.child[position+1] = rightChild;
            }
            
            //System.out.println(Arrays.toString(current.data));
            //System.out.println(Arrays.toString(newRightChild.data));
            //System.out.println(excessData);
        }
    }
    
    protected class BTNode<E> {
        public int size;
        public E[] data;
        public BTNode<E>[] child;
        
        public BTNode() {
            data = (E[])new Comparable[order-1];
            child = (BTNode<E>[])new BTNode[order];
            size = 0;
        }
        
        public BTNode(E data) {
            this();
            this.data[0] = data;
            size = 1;
        }
        
        public BTNode(E data, BTNode<E> left, BTNode<E> right) {
            this(data);
            this.child[0] = left;
            this.child[1] = right;
        }
        
        public String toString() {
            return toString(0);
        }
        
        public String spaces(int indentation) {
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<indentation; i++) {
                sb.append("   ");
            }
            return sb.toString();
        }
        
        public int totalSize() {
            int sum = size;
            for (int i=0; i<=size; i++) {
                if (child[i] != null)
                    sum += child[i].totalSize();
            }
            return sum;
        }
        
        public boolean isLeaf() {
            return (child[0] == null);
        }
        
        public String toString(int indentation) {
            StringBuilder sb = new StringBuilder();
            sb.append(spaces(indentation));
            sb.append(Arrays.toString(data));
            sb.append("\n");
            
            indentation++;
            
            //if (child[0] != null) {
                for (int i=0; i<=size; i++) {
                    if (child[i] != null)
                        sb.append(child[i].toString(indentation));
                }
            //}
            return sb.toString();
        }
    }
    
    public BTree() {
        this(4);
    }
    
    public BTree(int order) {
        this.order = order;
        root = null;
    }
    
    public void insert(E data) {
        BTSplitter<E> splitter = insert(root, data);
        if (splitter == null || splitter.excessData == null)
            return;
        else
            root = new BTNode<E>(splitter.excessData, root, splitter.newRightChild);
    }
    
    private BTSplitter<E> insert(BTNode<E> current, E data) {
        if (current == null)
            return new BTSplitter<E>(data);
        
        int position = binarySearch(current.data, data, current.size); // position = The largest value in the array less than data.
        if (position < current.size && current.data[position].compareTo(data) == 0)
            return null; // don't insert
        BTSplitter<E> splitter = insert(current.child[position], data);
        if (splitter == null || splitter.excessData == null)
            return null;
        return insertNode(current, splitter.excessData, splitter.newRightChild);
    }
    
    private BTSplitter<E> insertNode(BTNode<E> current, E data, BTNode<E> rightChild) {
        int position = binarySearch(current.data, data, current.size);
        return new BTSplitter<E>(current, position, data, rightChild);
    }
    
    private int binarySearch(E[] array, E data, int size) {
        // Finds the position of the smallest value in the array larger than data.
        int position = binarySearch(array, data, 0, size-1);
        return position;
    }
    
    private int binarySearch(E[] array, E data, int start, int end) {
        if (start > end) {
            return start;
        }
        int mid = (end+1-start)/2+start;
        int compare = data.compareTo(array[mid]);
        if (compare == 0)
            return mid;
        if (compare < 0) // data < array[mid]
            return binarySearch(array, data, start, mid-1);
        else
            return binarySearch(array, data, mid+1, end);
    }
    
    private void addToCurrent(BTNode<E> current, E data) {
        if (root == null) {
            root = new BTNode<E>(data);
            return;
        }
    }
    
    private int minChildren() {
        return (order+1)/2;
    }
    
    private int minData() {
        return (order-1)/2;
    }
    
    public void delete(E data) {
        
        BTNode<E> current = root;
        
        E[] replaceArray = null;
        int replacePosition = 0;
        boolean stillSearching = true;
        
        Stack<BTNode<E>> parentStack = new Stack<BTNode<E>>();
        Stack<Integer> positionStack = new Stack<Integer>();
        
        while(current != null) {
            // Search until you reach a leaf.
            int position;
            
            if (stillSearching) {
                // Searching for data
                position = binarySearch(current.data, data, current.size);
                if (position < current.size && current.data[position].equals(data)) {
                    replaceArray = current.data;
                    replacePosition = position;
                    stillSearching = false;
                }
            }
            else {
                // finding the inorder predecessor of data.
                position = current.size;
            }
            parentStack.push(current);
            positionStack.push(position);
            current = current.child[position];
        }
        if (stillSearching == true) {
            return; // Unable to find data to be deleted.
        }
        
        // stillSearching == true. Means data has been found.
        if (positionStack.peek() == parentStack.peek().size) {
            // If was finding inorder predecessor, the final position will be == size. Adjust it to size-1.
            decrementHead(positionStack);
        }
        
        replaceArray[replacePosition] = parentStack.peek().data[positionStack.peek()];
        
        // Now to delete.
        deleteData(parentStack, positionStack);
    }
    
    
    private void deleteData(Stack<BTNode<E>> parentStack, Stack<Integer> positionStack) {
        // Looks at the top of the stack, and deletes the data specified.
        // Then does a recursive deletion as necessary.
        BTNode<E> current = parentStack.pop();
        int position = positionStack.pop();
        
        if (parentStack.isEmpty() || current.size > minData()) {
            // No need to rebalance
            //System.out.println("no need rebalance " + current.size + " " + minData());
            leftShiftFillUp(current, position);
            current.size--;
            if (current == root && root.size == 0) {
                if (root.child[0] == null)
                    root = null;
                else
                    root = root.child[0];
            }
            return;
        }
        
        BTNode<E> parent = parentStack.peek();
        int parentPos = positionStack.peek();
        
        // Else: Need Reblancing as current.size < minData.
        
        // Case 1: If there is no left sibling,
        if (parentPos == 0) {
            // Case 1a: Right sibling has more than minimum elements.
            if (parent.child[parentPos+1].size > minData()) {
                // Action: Rotate left.
                leftShiftFillUp(current, position);
                current.size--;
                rotateLeft(parent, parentPos);
            }
            // Case 1b: Right sibling has exactly minimum elements.
            else { //parent.child[parentPos+1].size == minData()
                // Action: Merge with right sibling
                leftShiftFillUp(current, position);
                current.size--;
                mergeChildren(parent, parentPos);
                deleteData(parentStack, positionStack);
            }
        }
        // Case 2: There is a left sibling.
        else {
            // Case 2a: Left sibling has more than minimmum elements.
            if (parent.child[parentPos-1].size > minData()) {
                // Action: Rotate right
                rightShiftFillUp(current, position);
                rotateRightAfterShift(parent, parentPos-1);
            }
            // Case 2b: Left sibling has exactly minimum elements.
            else { // parent.child[parentPos-1].size == minData()
                // Action: Merge with left sibling
                //System.out.println("Me: " + current.size + "LeftSib: " + parent.child[parentPos-1].size);
                leftShiftFillUp(current, position);
                current.size--;
                mergeChildren(parent, parentPos-1);
                decrementHead(positionStack);
                deleteData(parentStack, positionStack);
            }
        
        }
    }
    
    private void decrementHead(Stack<Integer> positionStack) {
        positionStack.push(positionStack.pop()-1);
    }
    
    private void rotateLeft(BTNode<E> parent, int position) {
        // assumes sizes do not exceed array. Throws exception otherwise.
        BTNode<E> left = parent.child[position];
        BTNode<E> right = parent.child[position+1];
        
        left.data[left.size] = parent.data[position];
        left.child[left.size+1] = right.child[0]; // transfer child
        left.size++;
        
        parent.data[position] = right.data[0];
        
        right.child[0] = right.child[1];
        leftShiftFillUp(right, 0);
        
        right.size--;
    }
    
    public void leftShiftFillUp(BTNode<E> current, int emptySlot) {
        // Note: deletes the right child of emptySlot.
        // Does not change size
        for (int i=emptySlot; i<current.size-1; i++) {
            current.data[i] = current.data[i+1];
            current.child[i+1] = current.child[i+2];
        }
        
        current.data[current.size-1] = null;
        current.child[current.size] = null;
    }
    
    private void rightShiftFillUp(BTNode<E> current, int emptySlot) {
        // Note: deletes the right child of emptySlot.
        // Does not change size
        
        current.child[emptySlot+1] = current.child[emptySlot];
        for (int i=emptySlot-1; i>=0; i--) {
            current.data[i+1] = current.data[i];
            current.child[i+1] = current.child[i];
        }
        current.data[0] = null;
        current.child[0] = null;
    }
    
    private void rotateRightAfterShift(BTNode<E> parent, int position) {
        // assumes sizes do not exceed array. Throws exception otherwise.
        BTNode<E> left = parent.child[position];
        BTNode<E> right = parent.child[position+1];
        
        if (right.child[0] != null || right.data[0] != null)
            throw new IllegalArgumentException("Haven't shifted elements right before rotate");
        
        right.data[0] = parent.data[position];
        right.child[0] = left.child[left.size]; // transfer the child.
        //right.size++;
        
        parent.data[position] = left.data[left.size-1];
        
        left.data[left.size-1] = null;
        left.child[left.size] = null;
        left.size--;
    }
    
    private void mergeChildren(BTNode<E> parent, int position) {
        // Assumes that the left child has enough space to fit all the elements from the merge.
        // Merges the right child and parent into the left child.
        BTNode<E> left = parent.child[position];
        BTNode<E> right = parent.child[position+1];
        
        //int leftIndex = left.size;
        //int rightIndex = 0;
        
        left.data[left.size] = parent.data[position];
        left.child[left.size+1] = right.child[0];
        
        for (int i = 0; i < right.size; i++) {
            left.data[left.size+1+i] = right.data[i];
            left.child[left.size+2+i] = right.child[i+1];
        }
        left.size = left.size+right.size+1;
        
        // Not necessary.
        parent.data[position] = null;
        parent.child[position+1] = null; // Remove right child
    }
    
    public boolean contains(E target) {
        return search(root, target);
    }
    
    private boolean search(BTNode<E> current, E target) {
        if (current == null)
            return false;
        
        int position = binarySearch(current.data, target, current.size);
        if (position < current.size && current.data[position].equals(target))
            return true;
        
        return search(current.child[position], target);
    }
    
    public E floor(E target) {
        // Returns the largest value smaller than target.
        // Returns null if all target is smaller than all values in the set.
        return searchFloor(root, target);
    }
    
    private E searchFloor(BTNode<E> current, E target) {
        if (current == null)
            return null;
        
        int position = binarySearch(current.data, target, current.size);
        if (position < current.size && current.data[position].compareTo(target) == 0)
            return current.data[position];
        
        E result = searchFloor(current.child[position], target);
        if (result == null) {
            position -= 1; // Floor is ceiling-1 (case of equality is taken care of above)
            
            if (position < 0)
                return null; // Search parent.
            return current.data[position]; // Return floor.
        }
        return result;
    }
    
    public E findMin() {
        if (root == null) return null;
        return findMin(root);
    }
    
    private E findMin(BTNode<E> current) {
        BTNode<E> target = current.child[0];
        if (target == null)
            return current.data[0];
        return findMin(current.child[0]);
    }
    
    
    public String toString() {
        if (root == null)
            return null;
        return root.toString();
    }
    
    public int size() {
        if (root == null)
            return 0;
        return root.totalSize();
    }
}