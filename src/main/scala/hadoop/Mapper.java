package hadoop;

import org.apache.hadoop.hdfs.client.HdfsAdmin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.FileOutputCommitter;

import java.io.IOException;
import java.net.URI;
public class Mapper {
    public static void main(String []args) throws IOException {
        Configuration conf = new Configuration();
        HdfsAdmin hdfsAdmin = new HdfsAdmin(URI.create("http://"),conf);
        FileOutputCommitter fileOutputCommitter = new FileOutputCommitter();

    }
}
