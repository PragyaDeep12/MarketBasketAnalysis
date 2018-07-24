package org;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	IntWritable outval=new IntWritable(1);
	Text outkey= new Text("s");
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		String record= value.toString();
		
			context.write(outkey, outval);
		
		
	}
}
