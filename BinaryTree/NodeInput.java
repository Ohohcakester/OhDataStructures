import java.util.LinkedList;

public class NodeInput {
 
    private LinkedList<Boolean> directions;
    public String label;
    public boolean valid;
    
    public NodeInput(String pairString) {
        // precondition: pairString is in the format (label,directions)
        // example: (A,LLR)
        
        int commaPosition = pairString.indexOf(',');
        
        if (commaPosition == -1) {
            valid = false;
            return;
        }
        
        label = pairString.substring(1,commaPosition);
        
        directions = new LinkedList<>();
        valid = readIntoDirection(pairString.substring(commaPosition+1,pairString.length()-1));
    }
    
    public boolean readIntoDirection(String input) {
        // Reads a series of L's and R's into a linkedlist of booleans.
        // E.g. LLR would translate into 0,0,1.
        
        for (int i=0; i<input.length(); i++) {
            char c = input.charAt(i);
            
            if (c == 'L')
                directions.offer(false);
            else if (c == 'R')
                directions.offer(true);
            else
                return false;
            
        }
        return true;
    }
    
    public int nextDirection() {
        // Returns 0 if reached end of path.
        // Returns -1 if next direction is left.
        // Returns 1 if next direction is right.
        
        if (directions.isEmpty())
            return 0;
        
        if (directions.poll() == false)
            return -1;
        else
            return 1;
    }
    
}