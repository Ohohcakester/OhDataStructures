import java.util.Comparator;




public class TestHeap {
 
    public static void main(String[] args) {
        heapifyTest();
        /*Heap<String> lengthHeap = new Heap<String>(true);
        
        lengthHeap.insert("apple");
        lengthHeap.insert("orange");
        lengthHeap.insert("watermelon");
        lengthHeap.insert("grape");
        lengthHeap.insert("tomato");
        lengthHeap.insert("pineapple");
        lengthHeap.insert("rambutan");
        lengthHeap.insert("pear");
        lengthHeap.insert("mangosteen");
        lengthHeap.insert("lychee");
        lengthHeap.insert("kiwi");
        lengthHeap.insert("peach");
        lengthHeap.insert("durian");
        lengthHeap.insert("banana");
        lengthHeap.insert("blueberry");
        lengthHeap.insert("strawberry");
        
        System.out.println(lengthHeap.levelorderToString());
        
        lengthHeap.setComparator(new CompareLength());
        lengthHeap.heapify();
        
        System.out.println(lengthHeap.levelorderToString());
        
        lengthHeap.setComparator(null);
        lengthHeap.heapify();
        
        System.out.println(lengthHeap.levelorderToString());*/
    }
    
    
    public static void heapifyTest() {
        /*
        Heap<Integer> heap1 = new Heap<>(false);
        for (int i=0; i<10; i++)
            heap1.insert(i);
        
        Integer[] array = new Integer[] {0,1,2,3,4,5,6,7,8,9};
        Heap<Integer> heap2 = new Heap<>(array, false);
        heap2.heapify();
        
        System.out.println(heap1.levelorderToString());
        System.out.println(heap2.levelorderToString());
        */
    }
    
    
    
    
    public static void misc() {
        /*Integer[] onetwo = new Integer[] {1,2,3,4,5};
        Heap<Integer> heap = new Heap<Integer>(onetwo,false);
        
        heap.heapify();
        
        System.out.println(heap.levelorderToString());*/
        
        /*Heap<String> strHeap = new Heap<>(true);
        
        //String sentence = "This is the house that Jack built";
        String sentence = "This is the house that Jack built";
        String[] words = sentence.split(" ");
        for (String word : words)
            strHeap.insert(word);
    
        System.out.println(strHeap.preorderToString());
        System.out.println(strHeap.postorderToString());
        System.out.println(strHeap.inorderToString());
        System.out.println(strHeap.levelorderToString());*/
        
        /*Integer[] array = new Integer[] {8,18,29,20,28,39,66,37,26,76,32,74,89};
        Heap<Integer> arrayHeap = new Heap<Integer>(array,true);
    
        System.out.println(arrayHeap.preorderToString());
        System.out.println(arrayHeap.levelorderToString());
        
        arrayHeap.pop();
        
        System.out.println(arrayHeap.preorderToString());
        System.out.println(arrayHeap.levelorderToString());
        
        System.out.println(arrayHeap.arrayToString());
        
        System.out.println(arrayHeap.inorderToString());
        for (int a : arrayHeap) {
            System.out.print(a + " ");
        }
        System.out.println();*/
        
        /*Heap<Integer> heap = new Heap<>(false);
        
        heap.insert(95);
        heap.insert(25);
        heap.insert(10);
        heap.insert(33);
        heap.insert(55);
        heap.insert(47);
        heap.insert(82);
        heap.insert(90);
        heap.insert(18);
        
        System.out.println(heap.preorderToString());
        System.out.println(heap.levelorderToString());*/
        
        
    }
    
}



class CompareLength implements Comparator<String> {
    
    public int compare(String a, String b) {
        return a.length()-b.length();
    }
    
}