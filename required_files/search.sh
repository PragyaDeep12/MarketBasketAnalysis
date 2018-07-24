#"sh required_files/search.sh "+confidence+" "+lift+" "+outFolder+" required_files/productprob.jar"
confidence=$1
lift=$2
user_output_directory=$3
jar=$4
echo "confidence=$1"
echo "lift=$2"
echo "ouputdir=$3"
echo "jar=$4"
confidence_lift=/user/outdir2
filter_file="fil"
search_value= /user/filsearch
hadoop fs -rm -r $search_value
hadoop fs -mkdir $search_value
hadoop fs -rm -r /user/outdir3
echo "$confi,$lift" >> fil
hadoop fs -put fil /user/filsearch
rm fil
hadoop jar $4 org.SearchDriver /user/filsearch /user/outdir2 /user/outdir3
rm $user_output_directory/part-r-00000
hadoop fs -get /user/outdir3/part-r-00000 $user_output_directory
mv $user_output_directory/part-r-00000 $user_output_directory/search.txt
clear
echo "Search Filter Value"
hadoop fs -cat /user/filsearch/fil
cat $user_output_directory/search.txt


