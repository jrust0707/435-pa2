package com.jrust;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by Jonathan Rust on 3/23/16.
 */
public class MysteryTFMap extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = value.toString().split("<===>");
        if(split.length == 1) return; /* Line doesn't contain key denoting author, no work to do */

        StringTokenizer tokenizer = new StringTokenizer(split[1].toLowerCase());
        String token;
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken().replaceAll("[^A-Za-z0-9]", "");
            if (!token.equals("")) {
                context.write(new Text(Main.MYSTERY_AUTHOR), new Text(token));
            }
        }
    }
}
