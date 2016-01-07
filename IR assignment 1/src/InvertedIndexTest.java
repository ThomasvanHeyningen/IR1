import java.util.Arrays;
import java.util.ArrayList;

          
   public class InvertedIndexTest {
                     
    public static void main(String[] args) throws Exception {
       
    	String testinputMap = "1-experimental_investigation_of_the_aerodynamics_of_awing_in_a_slipstream_.";
    	map(testinputMap);
    	
    	String keyReduce = "investigation";
    	ArrayList<Integer> valuesReduce = new ArrayList<Integer>();
    	valuesReduce.add(1);
    	valuesReduce.add(2);
    	valuesReduce.add(5);
    	reduce(keyReduce, valuesReduce);
    	
    	}

    	// map: input is testinput; output is a key-value pair (word;doc.nr)
        public static void map(String line) {
        	            
            // Code to process a line of text; 
            // e.g. split the line in document number, title and text
            // use tokenizer for words.
            
            System.out.println("result map: " );
        }
        
        // reduce: input is a key and a list of integers, output is a key and a list of Text
        public static void reduce(String key, ArrayList<Integer> values) {
        	
           // Create a ArrayList<Text> from ArrayList<Integer> 

        	System.out.println("result reduce: ");
         }      
  }