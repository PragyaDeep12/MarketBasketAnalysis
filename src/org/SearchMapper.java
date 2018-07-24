package org;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class SearchMapper extends Mapper<LongWritable, Text, Text, Text>{
	Double ConfidenceFilter=0.0;
	Double LiftFilter=0.0;
	Text outKey=new Text();
	Text outVal=new Text();
	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//super.setup(context);
		Path [] path=context.getLocalCacheFiles();
		for (Path path2 : path) {
			String strp=path2.toString();
			Scanner sc= new Scanner(new File(strp));
			while(sc.hasNextLine())
			{
				String record = sc.nextLine();
				String f[]=record.split(",");
				ConfidenceFilter=Double.parseDouble(f[0]);
				LiftFilter=Double.parseDouble(f[1]);
			}
				
				//supportKeys.add(hashMap);
				//total=Integer.parseInt(sc.nextLine().trim());
			
		}
	}
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//super.map(key, value, context);
		String record =value.toString();
		String sets[] =record.split("\\t");
		String valueSet[]=sets[1].split(",");
		try{
		Double lift=Double.parseDouble(valueSet[4]);
		Double confidence=Double.parseDouble(valueSet[3]);
		
		if(confidence>=ConfidenceFilter && lift>=LiftFilter){
			outKey.set(sets[0]);
			outVal.set(confidence+","+lift);
			context.write(outKey,outVal);
		}
		}catch(Exception e){
			
		}
		
	}

}
