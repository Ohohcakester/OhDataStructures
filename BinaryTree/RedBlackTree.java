import java.util.Stack;

class RBNode<E> extends Node<E> {
    public boolean black;
    
    public RBNode(E data) {
        // Defaults to red.
        this.data = data;
        black = false;
    }
    
    public RBNode(E data, boolean black) {
        this.data = data;
        this.black = black;
    }
}

public class RedBlackTree<E extends Comparable<? super E>> extends BinarySearchTree<E> {
    
    public RedBlackTree() {
        root = null;
    }
    
    private boolean isBlack(Node<E> node) {
        if (node == null) // null nodes are black
            return true;
        return (((RBNode<E>)node).black);
    }
    
    private void setBlack(Node<E> node) {
        ((RBNode<E>)node).black = true;
    }
    
    private void setRed(Node<E> node) {
        ((RBNode<E>)node).black = false;
    }
    
    private class State {
        // Value stores node data on the way down,
        // and stores action data on the way up.
        private int value;
        
        // BLACK = 1, RED = 0
        // G U P S
        // 1 = grandparent
        // 2 = uncle
        // 4 = parent
        // 8 = sibling
        
        //private static final int GRANDPARENT = 1;
        private static final int UNCLE = 2;
        private static final int PARENT = 4;
        private static final int SIBLING = 8;
        
        private static final int PARENTISRC = 16;
        private static final int CURRENTISRC = 32;
        
        // Action variables
        private static final int DONOTHING = 0;
        
        private static final int C1_PARENT = 1;
        private static final int C1_GRANDPARENT = 2;
        
        private static final int C2_PARENT = 3;
        private static final int C2_GRANDPARENT = 4;
        
        private static final int C3_PARENT = 5;
        //private static final int C3_GRANDPARENT = 6;
        
        public State() {
            value = 0;
        }
        
        private State(int value) {
            this.value = value;
        }
        
        public State promoteFamily(Node<E> current, Node<E> otherChild, boolean goingRight) {
            // Does not change current state.
            State newState = new State();
            
            // Promote parent to grandparent.
            // Promote sibling to uncle.
            // Set parent to current.
            // Set sibling to the other child.
            //newState.setGrandparent(parentIsBlack());
            newState.setUncle(siblingIsBlack());
            newState.setParent(isBlack(current));
            newState.setSibling(isBlack(otherChild));
            
            newState.setParentIsRC(currentIsRC());
            newState.setCurrentIsRC(goingRight);
            
            return newState;
        }
        
        /*public void setGrandparent(boolean black) {
            if (black)
                value = value | GRANDPARENT;
            else
                value = value & ~GRANDPARENT;
        }*/
        
        public void setUncle(boolean black) {
            if (black)
                value = value | UNCLE;
            else
                value = value & ~UNCLE;
        }
        
        public void setParent(boolean black) {
            if (black)
                value = value | PARENT;
            else
                value = value & ~PARENT;
        }
        
        public void setSibling(boolean black) {
            if (black)
                value = value | SIBLING;
            else
                value = value & ~SIBLING;
        }
        
        public void setParentIsRC(boolean rc) {
            if (rc)
                value = value | PARENTISRC;
            else
                value = value & ~PARENTISRC;
        }
        
        public void setCurrentIsRC(boolean rc) {
            if (rc)
                value = value | CURRENTISRC;
            else
                value = value & ~CURRENTISRC;
        }
        
        //public boolean grandparentIsBlack() {return (value & GRANDPARENT) != 0;}
        public boolean uncleIsBlack() {return (value & UNCLE) != 0;}
        public boolean parentIsBlack() {return (value & PARENT) != 0;}
        public boolean siblingIsBlack() {return (value & SIBLING) != 0;}
        public boolean parentIsRC() {return (value & PARENTISRC) != 0;}
        public boolean currentIsRC() {return (value & CURRENTISRC) != 0;}
        
        public void setDoNothing() {
            value = DONOTHING;
        }
        
        public void decideNextAction(Node<E> current) {
            // See table of actions below.
            // Here lists the actions for CURRENT
            
            if (parentIsBlack())
                value = DONOTHING;
            else if (!uncleIsBlack())
                value = C1_PARENT;
            else if (parentIsRC() == currentIsRC())
                value = C2_PARENT;
            else { // parentIsRC() != currentIsRC()
                setBlack(current);
                value = C3_PARENT;
            }
            //System.out.println(value);
        }
        
        public Node<E> executeCurrentAction(Node<E> current, boolean goRight, State nextAction) {
            /*
             *     TABLE OF ACTIONS
             * -----|------------|------------|-----------------------------
             *      |  CURRENT   |   PARENT   |      GRANDPARENT
             * -----|------------|------------|-----------------------------
             *  C0: | Do nothing | ^          | ^
             * -----|------------|------------|-----------------------------
             *  C1: | skip       | skip       | Set both children black
             *      |            |            | If not root, set to Red, recurse (case recheck)
             * -----|------------|------------|-----------------------------
             *  C2: | skip       | Set black  | Set red, rotate Opposite -> Do nothing
             * -----|------------|------------|-----------------------------
             *  C3: | Set black  | rotate Opp | Set red, rotate Opp      -> Do nothing
             * -----|------------|------------|-----------------------------
             */
            
            switch(value) {
                case DONOTHING: {
                    nextAction.value = DONOTHING;
                    return current;
                }
                
                case C1_PARENT: {
                    nextAction.value = C1_GRANDPARENT;
                    return current;
                }
                
                case C1_GRANDPARENT: {
                    setBlack(current.left);
                    setBlack(current.right);
                    if (current != root) {
                        setRed(current);
                        nextAction.decideNextAction(current); // Recurse
                    }
                    return current;
                }
                
                case C2_PARENT: {
                    setBlack(current);
                    nextAction.value = C2_GRANDPARENT;
                    return current;
                }
                
                case C2_GRANDPARENT: {
                    setRed(current);
                    nextAction.value = DONOTHING;
                    if (goRight)
                        return rotateLeft(current);
                    else
                        return rotateRight(current);
                }
                
                case C3_PARENT: {
                    nextAction.value = C2_GRANDPARENT;
                    if (goRight)
                        return rotateLeft(current);
                    else
                        return rotateRight(current);
                }
                
                default:
                    throw new IllegalArgumentException("unknown action state");
            }
        }
    }
    
    public void insert(E data) {
        if (root == null) {
            root = new RBNode<E>(data);
            setBlack(root);
            return;
        }
        
        State currentState = new State();
        
        root = insert(data, root, currentState);
    }
    
    
    
    private Node<E> insert(E data, Node<E> current, State currentState) {
        
        if (current == null) {
            return insertHere(data, current, currentState);
        }
        
        int compare = current.data.compareTo(data);
        
        if (compare == 0) {
            currentState.setDoNothing();
            return current; // don't insert.
        }
        if (compare < 0) {
            // Insert right.
            State nextState = currentState.promoteFamily(current, current.left, true);
            current.right = insert(data, current.right, nextState);
            return nextState.executeCurrentAction(current, true, currentState);
        }
        else { // (compare > 0)
            // Insert left.
            State nextState = currentState.promoteFamily(current, current.right, false);
            current.left = insert(data, current.left, nextState);
            return nextState.executeCurrentAction(current, false, currentState);
        }
    }
    
    private Node<E> insertHere(E data, Node<E> current, State currentState) {
        
        Node<E> newNode = new RBNode<E>(data);
        currentState.decideNextAction(newNode);
        return newNode;
    }
    
    
    
    private void log(Node<E> node) {
        String colour = isBlack(node) ? "Black" : "Red";
        System.out.println(node.data + ": " + colour);
    }
    
    public void logAll() {
        logRecursive(root);
    }
    
    private void logRecursive(Node<E> node) {
        if (node == null) return;
        logRecursive(node.left);
        log(node);
        logRecursive(node.right);
    }
    
    public void forceColour(E data, boolean black) {
        forceColour(data,black,root);
    }
    
    private void forceColour(E data, boolean black, Node<E> node) {
        if (node == null)
            System.out.println(data + " not found");
        int compare = node.data.compareTo(data);
        if (compare == 0) if (black) setBlack(node); else setRed(node);
        else if (compare < 0) forceColour(data,black,node.right);
        else if (compare > 0) forceColour(data,black,node.left);
    }
    
    
    private void moveBlackDown(Node<E> current) {
        // Note: assume that current and children are not red at the same time.
        // If children are red, set both children black and current to red.
        
        if (current.left == null || current.right == null)
            return;
        if (!isBlack(current.left) && !isBlack(current.right)) {
            setBlack(current.left);
            setBlack(current.right);
            setRed(current);
        }   
    }

    public void insertAlt(E data) {
        if (root == null) {
            root = new RBNode<E>(data, true);
        }
        root = insertAlt(root, data);
        setBlack(root);
    }
    
    private Node<E> insertAlt(Node<E> current, E data) {
        int compare = current.data.compareTo(data);
        if (compare == 0)
            return current;
        
        if (compare < 0) {
            // Insert right.
            if (current.right == null) {
                current.right = new RBNode<E>(data);
                return current;
            }
            moveBlackDown(current);
            
            current.right = insertAlt(current.right, data);
            
            if (!isBlack(current.right)) {
                if (!isBlack(current.right.right)) {
                    setBlack(current.right);
                    setRed(current);
                    return rotateLeft(current);
                }
                else if (!isBlack(current.right.left)) {
                    current.right = rotateRight(current.right);
                    setBlack(current.right);
                    setRed(current);
                    return rotateLeft(current);
                }
            }
            return current;
        }
        
        else { // compare > 0
            // Insert left.
            if (current.left == null) {
                current.left = new RBNode<E>(data);
                return current;
            }
            moveBlackDown(current);
            
            current.left = insertAlt(current.left, data);
            
            if (!isBlack(current.left)) {
                if (!isBlack(current.left.left)) {
                    setBlack(current.left);
                    setRed(current);
                    return rotateRight(current);
                }
                else if (!isBlack(current.left.right)) {
                    current.left = rotateLeft(current.left);
                    setBlack(current.left);
                    setRed(current);
                    return rotateRight(current);
                }
            }
            return current;
        }
    }
}