package org;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ProductReducer extends Reducer<Text, IntWritable, Text, DoubleWritable>{
	DoubleWritable outval=new DoubleWritable(1);
	private int total;
		@Override
		protected void setup(Reducer<Text, IntWritable, Text, DoubleWritable>.Context context)
				throws IOException, InterruptedException {
			Path [] path=context.getLocalCacheFiles();
			for (Path path2 : path) {
				String strp=path2.toString();
				Scanner sc= new Scanner(new File(strp));
				while(sc.hasNextLine())
				{
					total=Integer.parseInt(sc.nextLine().trim());
				}
			}
		}
@Override
protected void reduce(Text key, Iterable<IntWritable> value,Context context) throws IOException, InterruptedException {
	int sum=0;
	for (IntWritable intWritable : value) {
		sum++;
	}
	if(sum>0){
	double support=(double)sum/total;
	outval.set(support);
	context.write(key, outval);
	}
	
	
}

}
