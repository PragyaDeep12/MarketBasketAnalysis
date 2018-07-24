package org;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ProductMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	IntWritable intw=new IntWritable(1);
	Text mytext= new Text();
	IntWritable intwritable=new IntWritable(1);
	ArrayList<String> strings=new ArrayList<>();
	
@Override
protected void map(LongWritable key, Text value,
		Mapper<LongWritable, Text, Text, IntWritable>.Context context)
		throws IOException, InterruptedException {
	String record=value.toString();
	String fields[]=record.split(" ");
	int l=fields.length;
	int[] arr=new int[l];
	for(int j=0;j<l;j++) {
	arr[j]=j;
	}
		
	if(l>=2)
	{
		Permutation permutation=new Permutation();
	
		
	ArrayList<ArrayList<String>> lists=permutation.getItemSets(fields);
	
	for (ArrayList<String> arrayList : lists) {
		String answer="";
		if(strings.size()>0)
		{
			strings.clear();
		}
		for (String integer : arrayList) {
			strings.add(integer);
		}
		
		strings=bubbleSort(strings);
		for (String s : strings) {
			answer+=s+",";
		}
		mytext.set(answer);
		context.write(mytext, intwritable);
	}		
		
	
	}
	

}
public ArrayList<String> bubbleSort(ArrayList<String> strings2) {
	ArrayList<String> newsw=new ArrayList<String>();
	String stringArray[] = new String[strings.size()];
	int k=0;
	for (String string : strings2) {
		stringArray[k]=string;
		k++;
	}
    int n = stringArray.length;
    String temp;
    for (int i = 0; i < n; i++) {
        for (int j = 1; j < (n - i); j++) {
            if (stringArray[j - 1].compareTo( stringArray[j] ) > 0) {
                temp = stringArray[j - 1];
                stringArray[j - 1] = stringArray[j];
                stringArray[j] = temp;
            }

        }
    }
    for (String string : stringArray) {
		newsw.add(string);
	}
    return newsw;
}
}
