package org;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AssociationMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	Text outkey=new Text();
	IntWritable outval= new IntWritable(1);
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String record=value.toString();
		String recordval[]=record.split("\\t");
		String fields[]=recordval[0].split(",");
		int l=fields.length;
		for(int i=0;i<l;i++)
		{
			String ifstr="";
			for(int j=0;j<l;j++)
			{
				if(i!=j)
				{
					ifstr+=fields[j]+",";
				}
			}
			if(ifstr!="")
			{
			ifstr+="->"+fields[i]+",";
			outkey.set(ifstr);
			context.write(outkey, outval);
			}
		}
	}
}
