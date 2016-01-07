     
   import java.io.IOException;
import java.util.*;
           
   import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
           
   public class InvertedIndex {
           
    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
        
       public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
           String line = value.toString();
           String[] first_and_last_part = line.split("_.");
           
           String[] number_and_title = first_and_last_part[0].split("-");
           String number = number_and_title[0];
           String title = number_and_title[1];
           
           String tokenString = first_and_last_part[1];
           int lastPartIndex = tokenString.indexOf("..I");
           tokenString = tokenString.substring(0,lastPartIndex);
           String[] tokens = tokenString.split(" ");
           
           for(String token : tokens) {
        	   context.write(new Text(token), new IntWritable(Integer.getInteger(number)));
       	}
           
           // Code to process a line of text; 
           // e.g. split the line in document number, title and text
           // use tokenizer for words.
           // and write docnumber-each word onto the context (use context.write(...))
       }
    } 
           
    public static class Reduce extends Reducer<Text, IntWritable, Text, Text> {
   
       public void reduce(Text key, Iterable<IntWritable> values, Context context) 
         throws IOException, InterruptedException {
    	  ArrayList<IntWritable> documentNumberList = new ArrayList<IntWritable>();
    	  
    	  Iterator itr = values.iterator();
    	  while(itr.hasNext()) {
    	         IntWritable documentNumber = (IntWritable) itr.next();
    	         if(!documentNumberList.contains(documentNumber)){
    	        	 documentNumberList.add(documentNumber);
    	         }
    	      }
    	  String DocumentNumbers = "";
    	  for(int i = 0; i < documentNumberList.size(); i++)
    	  {
    		  DocumentNumbers += documentNumberList.get(i);
    	  }
    	   context.write(key,new Text(DocumentNumbers));
       }
    }
           
    public static void main(String[] args) throws Exception {
       Configuration conf = new Configuration();
           
       // The job is called "wordcount", but we are doing an inverted index!
           Job job = new Job(conf, "wordcount");
           job.setJarByClass(InvertedIndex.class);
      
       job.setOutputKeyClass(Text.class);
       job.setOutputValueClass(IntWritable.class);
           
       job.setMapperClass(InvertedIndex.Map.class);
       job.setReducerClass(InvertedIndex.Reduce.class);
           
       job.setInputFormatClass(TextInputFormat.class);
       job.setOutputFormatClass(TextOutputFormat.class);
         
       FileInputFormat.addInputPath(job, new Path(args[0]));
       FileOutputFormat.setOutputPath(job, new Path(args[1]));
       
       job.setNumReduceTasks(7);
       job.waitForCompletion(true);
    }
    
   
           
  }