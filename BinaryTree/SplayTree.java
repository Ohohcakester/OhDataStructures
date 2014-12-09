public class SplayTree<E extends Comparable<? super E>> extends BinarySearchTree<E> {
    
    public SplayTree() {
        root = null;
    }
    
    private SplayTree(Node<E> newRoot) {
        root = newRoot;
    }
    
    private Node<E> leftZigZig(Node<E> current) {
        current = rotateRight(current);
        return rotateRight(current);
    }
    
    private Node<E> rightZigZig(Node<E> current) {
        current = rotateLeft(current);
        return rotateLeft(current);
    }
    
    private Node<E> leftZigZag(Node<E> current) {
        current.left = rotateLeft(current.left);
        return rotateRight(current);
    }
    
    private Node<E> rightZigZag(Node<E> current) {
        current.right = rotateRight(current.right);
        return rotateLeft(current);
    }
    
    public void insertBottomUp(E data) {
        if (root == null) {
            root = new Node<E>(data);
            return;
        }
        
        int compare = root.data.compareTo(data);
        if (compare == 0)
            return;
        
        byte[] skip = new byte[] {0};
        // skip == 0 -> do nothing
        // skip == -1 or 1 -> rotate. -1 means came from left. +1 means came from right.
        
        if (compare < 0) {
            // root < data. Go Right.
            root.right = insertChild(data, root.right, skip);
            if (skip[0] == 0)
                root = rotateLeft(root); // ZIG step.
            else if (skip[0] == 1) {
                root = rightZigZig(root);
            }
            else { // skip[0] == -1
                root = rightZigZag(root);
            }
        }
        else { // (compare > 0)
            // root > data. Go Left.
            root.left = insertChild(data, root.left, skip);
            if (skip[0] == 0)
                root = rotateRight(root); // ZIG step.
            else if (skip[0] == -1) {
                root = leftZigZig(root);
            }
            else { // skip[0] == +1
                root = leftZigZag(root);
            }
        }
    }
    
    private Node<E> insertChild(E data, Node<E> current, byte[] skip) {
        if (current == null)
            return new Node<E>(data);
        
        int compare = current.data.compareTo(data);
        
        if (compare == 0)
            return current; // do nothing.
        
        if (compare < 0) {
            // root < data. Go Right.
            current.right = insertChild(data, current.right, skip);
            if (skip[0] == 0) {
                skip[0] = 1;
                return current;
            }
            else if (skip[0] == 1) {
                skip[0] = 0;
                return rightZigZig(current);
            }
            else { // (skip[0] == -1)
                skip[0] = 0;
                return rightZigZag(current);
            }
        }
        else { // (compare > 0)
            // root > data. Go Left.
            current.left = insertChild(data, current.left, skip);
            if (skip[0] == 0) {
                skip[0] = -1;
                return current;
            }
            else if (skip[0] == -1) {
                skip[0] = 0;
                return leftZigZig(current);
            }
            else { // (skip[0] == 1)
                skip[0] = 0;
                return leftZigZag(current);
            }
        }
    }
    
    public E searchBottomUp(E data) {
        bottomUpSplayToRoot(data);
        return root.data.equals(data) ? root.data : null;
    }
    
    public E deleteBottomUp(E data) {
        bottomUpSplayToRoot(data);
        if(!root.data.equals(data))
            return null; // Not found.
       
        E temp = root.data;
        if (root.left == null)
            root = root.right;
        else {        
            SplayTree<E> leftTree = new SplayTree<E>(root.left);
            leftTree.bottomUpSplayToRoot(data); // This will splay the inorder predecessor to root.
            leftTree.root.right = root.right;
            root = leftTree.root;
        }
        return temp;
    }
    
    private void bottomUpSplayToRoot(E data) {
        if (root == null)
            return;
        
        int compare = root.data.compareTo(data);
        if (compare == 0)
            return;
        
        byte[] skip = new byte[] {0};
        // skip == 0 -> do nothing
        // skip == -1 or 1 -> rotate. -1 means came from left. +1 means came from right.
        
        if (compare < 0) {
            // root < data. Go Right.
            root.right = bottomUpSplayChild(data, root.right, skip);
            if (skip[0] == 0)
                root = rotateLeft(root); // ZIG step.
            else if (skip[0] == 1) {
                root = rightZigZig(root);
            }
            else if (skip[0] == -1) {
                root = rightZigZag(root);
            }
            else if (skip[0] == 2) {
                // do nothing.
            }
            else
                throw new UnsupportedOperationException("Unknown skip value");
        }
        else { // (compare > 0)
            // root > data. Go Left.
            root.left = bottomUpSplayChild(data, root.left, skip);
            if (skip[0] == 0)
                root = rotateRight(root); // ZIG step.
            else if (skip[0] == -1) {
                root = leftZigZig(root);
            }
            else if (skip[0] == +1) {
                root = leftZigZag(root);
            }
            else if (skip[0] == 2) {
                // do nothing.
            }
            else
                throw new UnsupportedOperationException("Unknown skip value");
        }
    }
    
    private Node<E> bottomUpSplayChild(E data, Node<E> current, byte[] skip) {
        if (current == null) {
            skip[0] = 2;
            return current; // Not found.
        }
        
        int compare = current.data.compareTo(data);
        
        if (compare == 0)
            return current; // do nothing. It will splay to root.
        
        if (compare < 0) {
            // root < data. Go Right.
            current.right = bottomUpSplayChild(data, current.right, skip);
            if (skip[0] == 0) {
                skip[0] = 1;
                return current;
            }
            else if (skip[0] == 1) {
                skip[0] = 0;
                return rightZigZig(current);
            }
            else if (skip[0] == -1) {
                skip[0] = 0;
                return rightZigZag(current);
            }
            else if (skip[0] == 2) {
                skip[0] = 0;
                return current;
            }
            else
                throw new UnsupportedOperationException("Unknown skip value");
        }
        else { // (compare > 0)
            // root > data. Go Left.
            current.left = bottomUpSplayChild(data, current.left, skip);
            if (skip[0] == 0) {
                skip[0] = -1;
                return current;
            }
            else if (skip[0] == -1) {
                skip[0] = 0;
                return leftZigZig(current);
            }
            else if (skip[0] == 1) {
                skip[0] = 0;
                return leftZigZag(current);
            }
            else if (skip[0] == 2) {
                skip[0] = 0;
                return current;
            }
            else
                throw new UnsupportedOperationException("Unknown skip value");
        }
    }
    
    
    
    
    
    private class ThreeTrees {
        public Node<E> leftTree;
        public Node<E> leftTreeRightMost;
        public Node<E> rightTree;
        public Node<E> rightTreeLeftMost;
        public Node<E> current;
        
        public ThreeTrees(Node<E> root){
            leftTree = new Node<E>(null);
            leftTreeRightMost = leftTree;
            rightTree = new Node<E>(null);
            rightTreeLeftMost = rightTree;
            current = root;
        }
        
        public void attachCurrentToLeft() {
            leftTreeRightMost.right = current;
            leftTreeRightMost = current;
            current = current.right;
        }
        
        public void attachCurrentToRight() {
            rightTreeLeftMost.left = current;
            rightTreeLeftMost = current;
            current = current.left;
        }
        
        public void reassemble() {
            leftTreeRightMost.right = current.left;
            rightTreeLeftMost.left = current.right;
            current.left = leftTree.right;
            current.right = rightTree.left;
        }
        
    }
    
    private Node<E> topDownSplayToRoot(E data) {
        ThreeTrees trees = new ThreeTrees(root);
        
        boolean keepLooping = true;
        while (keepLooping) {
            int compare = trees.current.data.compareTo(data);
            if (compare == 0)
                break; // End loop, combine.
            
            if (compare < 0) {
                // Right side.
                if (trees.current.right == null)
                    break; // End loop, combine.
                
                compare = trees.current.right.data.compareTo(data);
                if (trees.current.right.right == null || compare == 0) {
                    // Equals. - ZIG
                    trees.attachCurrentToLeft();
                }
                else if (compare < 0) {
                    // Right side - ZIG-ZIG
                    trees.current = rotateLeft(trees.current);
                    trees.attachCurrentToLeft();
                }
                else { // compare > 0
                    // Left side - ZIG
                    trees.attachCurrentToLeft();
                }
            }
            else { // compare > 0
                if (trees.current.left == null)
                    break; // End loop, combine.
                
                compare = trees.current.left.data.compareTo(data);
                if (trees.current.left.left == null || compare == 0) {
                    // Equals. - ZIG
                    trees.attachCurrentToRight();
                }
                else if (compare > 0) {
                    // Left side - ZIG-ZIG
                    trees.current = rotateRight(trees.current);
                    trees.attachCurrentToRight();
                }
                else { // compare < 0
                    // Right side - ZIG
                    trees.attachCurrentToRight();
                }
            }
        }
        // Reassemble
        trees.reassemble();
        
        return trees.current;
    }
    
    public void insertTopDown(E data) {
        if (root == null) {
            root = new Node<E>(data);
            return;
        }
        
        root = topDownSplayToRoot(data);
        System.out.println(inorderToString());
        int compare = root.data.compareTo(data);
        if (compare == 0)
            return; // Do nothing.
        
        Node<E> newNode = new Node<E>(data);
        if (compare < 0) {
            // Insert to the right.
            newNode.right = root.right;
            newNode.left = root;
            root.right = null;
            root = newNode;
        }
        else { // compare > 0
            // Insert to the left.
            newNode.left = root.left;
            newNode.right = root;
            root.left = null;
            root = newNode;
        }
    }
    
    public E searchTopDown(E data) {
        root = topDownSplayToRoot(data);
        return root.data.equals(data) ? root.data : null;
    }
    
    public E deleteTopDown(E data) {
        root = topDownSplayToRoot(data);
        if(!root.data.equals(data))
            return null; // Not found.
       
        E temp = root.data;
        if (root.left == null)
            root = root.right;
        else {        
            SplayTree<E> leftTree = new SplayTree<E>(root.left);
            leftTree.topDownSplayToRoot(data); // This will splay the inorder predecessor to root.
            leftTree.root.right = root.right;
            root = leftTree.root;
        }
        return temp;
    }
}