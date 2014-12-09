import java.util.Arrays;

class ListNode {
    public int data;
    public ListNode next;
    
    public ListNode(int data) {
        this.data = data;
    }
}

class MergableList {
    public ListNode head;
    public ListNode tail;
    
    public MergableList(int data) {
        head = new ListNode(data);
        tail = head;
    }
    
    public void insert(int data) {
        tail.next = new ListNode(data);
        tail = tail.next;
    }
    
    public void addAll(MergableList list2) {
        tail.next = list2.head;
        tail = list2.tail;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode current = head;
        sb.append("<");
        while(current != null) {
            sb.append(current.data);
            sb.append(" ");
            current = current.next;
        }
        sb.append(">");
        return sb.toString();
    }
}


public class UnionSet {
    
    private int[] group;
    private MergableList[] elementList;
    private int[] sizes;
    
    public UnionSet(int size) {
        group = new int[size];
        sizes = new int[size];
        elementList = new MergableList[size];
        
        for (int i=0; i<size; i++) {
            group[i] = i;
            sizes[i] = 1;
            elementList[i] = new MergableList(i);
        }
    }
    
    public int find(int element) {
        return group[element];
    }
    
    public boolean sameSet(int e1, int e2) {
        return group[e1] == group[e2];
    }
    
    public void union(int e1, int e2) {
        if (sameSet(e1,e2))
            throw new IllegalArgumentException("They already belong to the same set!");
        
        // Merge the smaller list into the larger one.
        if (sizes[group[e1]] < sizes[group[e2]])
            mergeInto(group[e1], group[e2]);
        else
            mergeInto(group[e2], group[e1]);
    }
    
    private void mergeInto(int g1, int g2) {
        //System.out.println(Arrays.toString(group));
        //System.out.println(Arrays.toString(elementList));
        //System.out.println("Merge " + g1 + " into " + g2);
        
        // Merges group g1 into group g2.
        ListNode current = elementList[g1].head;
        while(current != null) {
            group[current.data] = g2;
            current = current.next;
        }
        elementList[g2].addAll(elementList[g1]);
        sizes[g2] += sizes[g1];
        
        elementList[g1] = null;
    }
    
}