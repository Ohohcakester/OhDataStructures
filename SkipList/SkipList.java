import java.util.Random;
import java.util.Arrays;

public class SkipList<E extends Comparable<? super E>> {
    
    // If maxLevel = 4, the levels are {0,1,2,3}.
    protected int maxLevel; // note: this can only increase. not decrease.
    protected SkipNode<E> head;
    protected Random rand;
    protected int size;
    
    protected class SkipNode<E extends Comparable<? super E>> {
        E data;
        SkipNode<E>[] next;
        
        public SkipNode(E data, int level) {
            this.data = data;
            next = (SkipNode<E>[])(new SkipNode[level]);
        }
        
        public String toString() {
            return data.toString()+ ":" + next.length;
        }
    }
    
    public SkipList() {
        maxLevel = 2;
        size = 0;
        rand = new Random();
    }
    
    public void insert(E data) {
        if (head == null) {
            size++;
            head = new SkipNode<E>(data, maxLevel);
            return;
        }   
        
        boolean insertNewHead = false;
        if (data.compareTo(head.data) < 0)
            insertNewHead = true;
        
        SkipNode<E>[] parents = searchParent(data);
        
        
        SkipNode<E> next = parents[0].next[0];
        if (next != null && data.equals(next.data))
            return; // don't insert.
        //System.out.println(Arrays.toString(parents));
        size++;
        
        if (size > calculateMaxCapacity(maxLevel))
            increaseMaxLevel(maxLevel+1);
        
        int level = generateLevel();
        SkipNode<E> newNode = new SkipNode<E>(data, level);
        
        int lastIndex = min(level,parents.length);
        for (int i=0; i<lastIndex; i++) {
            newNode.next[i] = parents[i].next[i];
            parents[i].next[i] = newNode;
        }
        for (int i=lastIndex; i<level; i++)
            head.next[i] = newNode;
        
        if (insertNewHead) {
            // Swap head with new node.
            E temp = head.data;
            head.data = newNode.data;
            newNode.data = temp;
        }
    }
    
    private int min(int a, int b) {
        return a<b?a:b;
    }
    
    public E delete(E data) {
        if (head == null) return null;
        
        boolean deleteHead = false;
        if (head.data.equals(data)) {
            if (head.next[0] == null) {
                // Delete head.
                E temp = head.data;
                head = null;
                return temp;
            }
            deleteHead = true;
        }
        
        SkipNode<E>[] parents = searchParent(data);
        
        SkipNode<E> target = parents[0].next[0];
        if (target == null || !data.equals(target.data)) {
            if (!deleteHead)
                return null; // Not present.
        }
        
        // Remove target.
        E temp = target.data;
        size--;
        for (int i=0; i<target.next.length; i++) {
            parents[i].next[i] = target.next[i];
        }
        
        if (deleteHead) {
            E temp2 = head.data;
            head.data = temp;
            return temp2;
        }
        
        return temp;
    }
    
    private void increaseMaxLevel(int newMaxLevel) {
        SkipNode<E> newHead = new SkipNode<E>(head.data, newMaxLevel);
        for (int i=0; i<head.next.length; i++)
            newHead.next[i] = head.next[i];
        head = newHead;
        maxLevel = newMaxLevel;
    }
    
    public boolean contains(E data) {
        if (data.equals(head.data))
            return true;
        
        SkipNode<E>[] parents = searchParent(data);
        SkipNode<E> next = parents[0].next[0];
        if (data.equals(next.data))
            return true;
        return false;
    }
    
    private SkipNode<E>[] searchParent(E data) {
        SkipNode<E>[] parents = (SkipNode<E>[])(new SkipNode[maxLevel]);
        int level = maxLevel-1;
        
        SkipNode<E> current = head;
        //StringBuilder sb = new StringBuilder(); sb.append(current).append("->");
        while(level>=0) {
            SkipNode<E> next = current.next[level];
            while(next != null && (next.data.compareTo(data) < 0)) {
                current = next;
                next = current.next[level]; //sb.append(current).append("->");
            }
            parents[level] = current;
            level--;
        }
        //System.out.println(sb.toString());
        return parents;
    }
    
    private int generateLevel() {
        int range = calculateMaxCapacity(maxLevel);
        int result = rand.nextInt(range);
        // e.g. maxLevel = 4
        // range = 1111
        int level = 0;
        while(level < maxLevel) {
            if (result%2 == 0)
                return level+1;
            result = (result >> 1);
            level++;
        }
        throw new IllegalArgumentException("Generate Level generated an invalid level");
    }
    
    private int calculateMaxCapacity(int capacity) {
        return ~(~0 << capacity); // this computes 2^maxLevel - 1
        // maxLevel = 3 -> returns 111
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        SkipNode<E> current = head;
        
        sb.append("[ ");
        while (current != null) {
            sb.append(current);
            sb.append(" ");
            current = current.next[0];
        }
        sb.append("]");
        return sb.toString();
    }
}