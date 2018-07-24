user_input_dir=$1
user_output_directory=$2
user_jar=$3
if [ "$user_jar" = "" ]
then
    user_jar="productprob.jar"
else
    echo "your selected jar file : $3"
fi
if [ "$user_output_directory" = "" ]
then
    user_output_directory="OutputFolder"
else
    echo "your selected output folder will be : $2"
fi
if [ "$user_input_dir" = "" ]
then
    user_input_dir="MBA.txt"
else
    echo "your selected inputFile is : $1"
fi
echo "files given: \n1)$user_input_dir \n 2)$user_output_directory \n 3)$user_jar"

hadoop fs -rm -r /user/indir
hadoop fs -mkdir /user/indir
hadoop fs -put $user_input_dir /user/indir
hadoop fs -rm -r /user/outdir*
hadoop jar $user_jar org.ProductDriver /user/indir /user/outdir /user/outdir1 /user/outdir2
rm -r $user_output_directory
mkdir $user_output_directory
hadoop fs -get /user/outdir2/part-r-00000 $user_output_directory
mv $user_output_directory/part-r-00000 $user_output_directory/output.txt
echo "Output File created at :$user_output_directory/output.txt"
