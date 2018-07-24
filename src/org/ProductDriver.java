package org;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ProductDriver {
public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
	Configuration configuration=new Configuration();
	
	//job2 counts the total number of records
	Job job= Job.getInstance(configuration);
	job.setJarByClass(ProductDriver.class);
	job.setMapperClass(CountMapper.class);
	job.setReducerClass(CountReducer.class);
	job.setMapOutputKeyClass(Text.class);
	job.setMapOutputValueClass(IntWritable.class);
	job.setOutputKeyClass(NullWritable.class);
	job.setOutputValueClass(IntWritable.class);
	FileInputFormat.addInputPath(job, new Path(args[0]));//projectin//args[0]
	FileOutputFormat.setOutputPath(job, new Path(args[1]));//combiout2//args[1]
	job.waitForCompletion(true);
	//.................first job completes
	//job generates the total number of times particular combinations exist 
	//and calculates the support
	Job job2=Job.getInstance(configuration);
	Path path= new Path(args[1]+"/part-r-00000");//combiout2//args[1]
	URI uri=path.toUri();
	job2.addCacheFile(uri);
	job2.setJarByClass(ProductDriver.class);
	job2.setReducerClass(ProductReducer.class);
	//job2.setNumReduceTasks(0);
	job2.setMapperClass(ProductMapper.class);
	job2.setOutputKeyClass(Text.class);
	job2.setOutputValueClass(IntWritable.class);
	FileInputFormat.addInputPath(job2, new Path(args[0]));//projectin//args[0]
	FileOutputFormat.setOutputPath(job2, new Path(args[2]));//combicount//sample2out1
	job2.waitForCompletion(true);
	//second job completes
	

	
	//generates all associations
	//calculates confidence and lift
	Job job3= Job.getInstance(configuration);
	Path path4= new Path(args[2]+"/part-r-00000");//outdir3(support)//sample2out1
	URI uri4=path4.toUri();
	job3.addCacheFile(uri4);
	job3.setMapperClass(AssociationMapper.class);
	job3.setReducerClass(AssociationReducer.class);
	job3.setJarByClass(ProductDriver.class);
	job3.setMapOutputKeyClass(Text.class);
	job3.setMapOutputValueClass(IntWritable.class);
	job3.setOutputKeyClass(Text.class);
	job3.setOutputValueClass(Text.class);
	FileInputFormat.addInputPath(job3, new Path(args[2]));//combicount//sample2out1
	FileOutputFormat.setOutputPath(job3, new Path(args[3]));//association//sample2association
	job3.waitForCompletion(true);
	
	
}
}
