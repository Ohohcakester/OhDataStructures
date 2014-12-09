
class BPNode<T extends Comparable<? super T>> {
    public T data;
    public BPNode<T> next;
    public BPNode<T> prev;
    
    public BPNode(T data) {
        this.data = data;
    }
    
    public BPNode(T data, BPNode<T> prev, BPNode<T> next) {
        // Inserts a node in between two nodes.
        // Works with null arguments for the nodes too.
        this.data = data;
        this.prev = prev;
        this.next = next;
        
        if (prev != null)
            prev.next = this;
        if (next != null)
            next.prev = this;
    }
    
    public String toString() {
        return data.toString();
    }
    
    public void remove() {
        if (next != null)
            next.prev = prev;
        if (prev != null)
            prev.next = next;
    }
    
    @Override
    public int hashCode() {
        return data.hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof BPNode)
            return data.equals(((BPNode)other).data);
        return false;
    }
}


public class BPlusTree<E extends Comparable<? super E>> {
    private final int order;
    private final int maxLeafSize;
    
    private BTree<BPData<E>> tree;
    private BPPointer<E> headPointer; // Not pointed to by any node in the tree. Represents all nodes smaller than the minimum node in the tree.
    
    public BPlusTree(int order) {
        this.order = order;
        this.maxLeafSize = order;
        tree = new BTree<BPData<E>>(order);
        headPointer = null;
    }
    
    protected interface BPData<T extends Comparable<? super T>> extends Comparable<BPData<T>>{
        public T getHead();
        
        public int compareTo(BPData<T> other);
        
        public int hashCode();
        
        public boolean equals(Object other);
    }
    
    protected class BPTempData<T extends Comparable<? super T>> implements BPData<T> {
        private final T data;
        
        public BPTempData(T data) {
            this.data = data;
        }
        
        public T getHead() {return data;}
        
        @Override
        public int compareTo(BPData<T> other) {
            return data.compareTo(other.getHead());
        }
        
        @Override
        public int hashCode() {
            return getHead().hashCode();
        }
        
        @Override
        public boolean equals(Object other) {
            if (other instanceof BPData)
                return getHead().equals(((BPData)other).getHead());
            return false;
        }
    }
    
    protected class BPPointer<T extends Comparable<? super T>> implements BPData<T>{
        public BPNode<T> head;
        public int size;
        
        public BPPointer(BPNode<T> head) {
            this.head = head;
        }
        
        public BPPointer(T data) {
            this.head = new BPNode<T>(data);
            size = 1;
        }
        
        public BPPointer(BPNode<T> head, int size) {
            this.head = head;
            this.size = size;
        }
        
        public BPPointer<T> add(T data) {
            // returns null if no split has occurred.
            // returns a the split off new node if a split has occurred.
            BPNode<T> current = head;
            boolean tail = false;
            int index = 0;
            
            int compare = current.data.compareTo(data);
            while (compare < 0) { // current < data
                if (current.next == null) {
                    tail = true;
                    index++;
                    break;
                }
                else {
                    current = current.next;
                    index++;
                    if (index > size) throw new IllegalArgumentException("Trying to insert into the wrong node: " + this);
                    compare = current.data.compareTo(data);
                }
            }
            
            if (compare == 0)
                return null; // don't insert.
            
            // Now current > data
            // Thus insert data such that current.prev < data < current.
            if (tail)
                current = new BPNode<T>(data, current, null); // InsertAfter. Index already set properly earlier.
            else {
                // InsertBefore.
                boolean moveHead = false;
                
                if (current == head)
                    moveHead = true;
                current = new BPNode<T>(data, current.prev, current); // Index will remain the same.
                if (moveHead)
                    head = current;
            }
            
            size++;
            
            if (size > maxLeafSize) {
                // Split node.
                int newHead = size/2;
                // size = 5 -> 0 1 2 3 4  -> new Head = 2. Sizes split into 2, 3
                // size = 4 -> 0 1 2 3    -> new Head = 2. Sizes split into 2, 2
                
                while (index < newHead) {
                    current = current.next;
                    index++;
                }
                while (index > newHead) {
                    current = current.prev;
                    index--;
                }
                // Now current index == newHead.
                
                // Set sizes accordingly.
                int newSize = this.size-newHead;
                this.size = newHead;
                return new BPPointer<T>(current, newSize); // Return the split-off node.
            }
            else 
                return null; // No split occurred.
        }
        
        public boolean remove(T data) {
            // Returns true iff the node goes under the minimum size after the removal.
            // Note: returns false if no removal is done.
            
            BPNode<T> current = head;
            int index = 0; // For debugging
            
            int compare = current.data.compareTo(data);
            while (compare < 0) { // current < data
                current = current.next;
                index++;
                if (current == null)
                    return false; // no deletion.
                if (index > size) throw new IllegalArgumentException("Trying to insert into the wrong node: " + this);
                compare = current.data.compareTo(data);
            }
            if (compare > 0) {
                return false; // no deleteion.
            }
            
            if (index == size) throw new IllegalArgumentException("Trying to insert into the wrong node: " + this);
                
            // compare == 0.
            if (current == head)
                head = current.next;
            current.remove();
            size--;
            return (size < minLeafSize());
        }
        
        public void merge(BPPointer<T> sibling) {
            // Assumption: sibling is the right sibling of the current node.
            this.size += sibling.size; // lol that's easy.
        }
        
        public void rotateLeftFrom(BPPointer<T> sibling) {
            // Assumption: sibling is the right sibling of the current node.
            this.size += 1;
            sibling.size -= 1;
            sibling.head = sibling.head.next;
        }
        
        public void rotateRightFrom(BPPointer<T> sibling) {
            // Assumption: sibling is the left sibling of the current node.
            this.size += 1;
            sibling.size -= 1;
            this.head = this.head.prev;
        }
        
        public T getHead() {
            return head.data;
        }
        
        @Override
        public int compareTo(BPData<T> other) {
            return getHead().compareTo(other.getHead());
        }
        
        @Override
        public int hashCode() {
            return getHead().hashCode();
        }
        
        @Override
        public boolean equals(Object other) {
            if (other instanceof BPData)
                return getHead().equals(((BPData)other).getHead());
            return false;
        }
        
        public boolean canMerge() {
            return (size == minLeafSize());
        }
        
        private int minLeafSize() {
            // If maxLeafSize = 5, minLeafSize = 3
            // If maxLeafSize = 4, minLeafSize = 2
            return (maxLeafSize+1)/2;
        }
        
        public String toString() {
            return "<" + toStringFull() + ">";
            //return head.toString();
        }
        
        public String toStringFull() {
            StringBuilder sb = new StringBuilder();
            BPNode<T> current = head;
            for (int i=0; i<size; i++) {
                sb.append(current.toString());
                current = current.next;
                if (i+1 < size)
                    sb.append("~");
            }
            return sb.toString();
        }
    }
    
    public void insert(E data) {
        BPTempData<E> tempData = new BPTempData<E>(data);
        BPPointer<E> targetNode = (BPPointer<E>)tree.floor(tempData);
        
        if (targetNode == null)
            targetNode = headPointer;
        
        if (headPointer == null) {
            headPointer = new BPPointer<E>(data);
            return;
        }
        
        if (data.equals(targetNode.getHead()))
            return; // Don't add.
        
        // Else add. newPointer will be set to null if no split has ocurred.
        BPPointer<E> newPointer = targetNode.add(data);
        
        if (newPointer != null) {
            // If split occurred, add new pointer to tree.
            tree.insert(newPointer);
        }
    }
    
    public void delete(E data) {
        BPTempData<E> tempData = new BPTempData<E>(data);
        BPPointer<E> targetNode = (BPPointer<E>)tree.floor(tempData);
        
        if (targetNode == null) {
            // head pointer. possibly merge with the right sibling.
            targetNode = headPointer;
            
            if (headPointer == null)
                return; // empty
            
            if (targetNode.remove(data)) {
                // Node becomes too small. May need to merge.
                BPPointer<E> sibling = (BPPointer<E>)tree.findMin();
                
                if (sibling == null) {
                    if (headPointer.size == 0)
                        headPointer = null;
                    return; // do nothing
                }
                
                if (sibling.canMerge()) {
                    targetNode.merge(sibling); // merge sibling into targetNode (headPointer).
                    tree.delete(sibling);
                }
                else {
                    // Rotate left
                    targetNode.rotateLeftFrom(sibling);
                }
            }
        }
        else {
            // Not head pointer. possibly merge with the left sibling.
            
            if (targetNode.remove(data)) {
                // Node becomes too small. May need to merge with the left sibling.
                BPTempData<E> temp = new BPTempData<E>(targetNode.head.prev.data);
                BPPointer<E> sibling = (BPPointer<E>)tree.floor(temp);
                if (sibling == null)
                    sibling = headPointer;
                
                if (sibling.canMerge()) {
                    sibling.merge(targetNode); // merge targetNode into sibling.
                    tree.delete(targetNode);
                }
                else {
                    // Rotate right
                    targetNode.rotateRightFrom(sibling);
                }
            }
        }
    }
    
    public String toString() {
        if (headPointer == null) return null;
        return headPointer.toString() + "\n" + tree.toString();
    }
    
    public BPNode<E> getHead() {
        return headPointer.head;
    }
}