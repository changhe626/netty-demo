package nio_ser.xyz.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件读取
 */
public class Demo1 {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("E:\\nio\\1.txt");
        InputStream stream = Files.newInputStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line = bufferedReader.readLine();
        System.out.println(line);
        if(line!=null){
            //读取剩下的所有行
            System.out.println(bufferedReader.readLine());
        }
    }
}
