package nio_ser.xyz.nio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Demo10 {
    public static void main(String[] args) throws IOException {

        String input = "* end of the file.";
        ByteBuffer byteBuffer = ByteBuffer.wrap(input.getBytes());

        Path path = Paths.get("E:\\nio\\6.txt");
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        fileChannel.position(fileChannel.size()-1);
        FileLock lock = fileChannel.lock();
        System.out.println("The Lock is shared: " + lock.isShared());
        fileChannel.write(byteBuffer);
        //Releases the Lock
        fileChannel.close();

        print("E:\\nio\\6.txt");
    }

    public static void print(String path) throws IOException {
        FileReader filereader = new FileReader(path);
        BufferedReader bufferedreader = new BufferedReader(filereader);
        String tr = bufferedreader.readLine();
        System.out.println("The Content of testout-file.txt file is: ");
        while (tr != null) {
            System.out.println("    " + tr);
            tr = bufferedreader.readLine();
        }
        filereader.close();
        bufferedreader.close();
    }
}
