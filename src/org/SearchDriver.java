package org;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SearchDriver {

	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		Configuration configuration=new Configuration();
		Job job3=Job.getInstance(configuration,"Search");
		Path path= new Path(args[0]+"/fil");//confidence search fiter values//args[0]
		URI uri=path.toUri();
		job3.addCacheFile(uri);
		job3.setJarByClass(SearchDriver.class);
		job3.setMapperClass(SearchMapper.class);
		job3.setOutputKeyClass(Text.class);
		job3.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job3, new Path(args[1]));//confidenceList//args[1]
		FileOutputFormat.setOutputPath(job3, new Path(args[2]));//searchResult//args[2]
		job3.waitForCompletion(true);

	}

}
