package com.demo.ch8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author zhoupeng
 */
public class IOUtil {

    public static String fileAsString(String path) {
        StringBuilder builder = new StringBuilder();
        File file = new File(path);
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
