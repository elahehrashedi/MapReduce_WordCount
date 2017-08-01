// Elaheh Rashedi

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, IntWritable>{

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens()) //reading all strings of the input text
	  {
        String str = itr.nextToken(); 	
        word.set(Integer.toString(str.length()));
		// here we set the input string length of each word as a key value		
        context.write(word, one);
      }
    }
  }

  public static class IntSumReducer
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values)  //reading all values which have the same key 
	  {
        sum += val.get(); // calculate the sum of the words with the same the lenght
      }
      result.set(sum);
      context.write(key, result); //write the output in <text, IntWritable> format
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "wordcount");
	//set the Jar by finding where a given class came from.
    job.setJarByClass(WordCount.class); 
    //set the mapper class
	job.setMapperClass(TokenizerMapper.class); 
	//set the combiner class
    job.setCombinerClass(IntSumReducer.class); 
	//set the reduce class
    job.setReducerClass(IntSumReducer.class); 
	//set the output key format
    job.setOutputKeyClass(Text.class); 
	//set the output value format
    job.setOutputValueClass(IntWritable.class); 
	//set the parameter of input directory
    FileInputFormat.addInputPath(job, new Path(args[0])); 
	//set the parameter of ouput directory
    FileOutputFormat.setOutputPath(job, new Path(args[1])); 
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}