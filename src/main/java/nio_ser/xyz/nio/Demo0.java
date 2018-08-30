package nio_ser.xyz.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * 文件复制
 */
public class Demo0 {

    public static void main(String[] args) throws IOException {

        FileInputStream inputStream = new FileInputStream("E:\\nio\\1.txt");
        FileChannel channel = inputStream.getChannel();
        FileOutputStream outputStream = new FileOutputStream("E:\\nio\\2.txt");
        FileChannel outputStreamChannel = outputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(50);
        int read = channel.read(byteBuffer);
        if(read!=-1){
            byteBuffer.flip();
            while(byteBuffer.hasRemaining()){
                outputStreamChannel.write(byteBuffer);
            }
        }
        byteBuffer.clear();
        inputStream.close();
        outputStream.close();


    }
}
