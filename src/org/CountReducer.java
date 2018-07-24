package org;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CountReducer extends Reducer<Text, IntWritable, NullWritable, IntWritable> {
IntWritable outval=new IntWritable();
	@Override
	protected void reduce(Text arg0, Iterable<IntWritable> arg1,
			Reducer<Text, IntWritable, NullWritable, IntWritable>.Context arg2) throws IOException, InterruptedException {
		int sum=0;
		for (IntWritable intWritable : arg1) {
			sum+=intWritable.get();
		}
		outval.set(sum);
		arg2.write(NullWritable.get(),outval );
	}
}
