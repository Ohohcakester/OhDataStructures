public class TestSkipList {
    
    public static void main(String[] args) {
        SkipList<Integer> sList = new SkipList<>();
        
        for (int i=5; i<60; i++) {
            int a = i*17%37;
            
            System.out.println("Insert " + a);
            sList.insert(a);
            System.out.println(sList);
        }
        
        for (int i=5; i<60; i++) {
            int a = i*17%37;
            
            System.out.println("Delete " + a);
            sList.delete(a);
            System.out.println(sList);
            
        }
        
        for (int i=5; i<60; i++) {
            int a = i*17%37;
            
            System.out.println("Insert " + a);
            sList.insert(a);
            System.out.println(sList);
        }
        
        
        for (int i=5; i<10; i++) {
            int a = i*17%37;
            
            System.out.println("Delete " + a);
            sList.delete(a);
            System.out.println(sList);
            
        }
        
        
        for (int i=5; i<60; i++) {
            int a = i*17%37;
            
            System.out.println("Insert " + a);
            sList.insert(a);
            System.out.println(sList);
        }
        
        
        
        System.out.println(sList);
        
    }
    
}