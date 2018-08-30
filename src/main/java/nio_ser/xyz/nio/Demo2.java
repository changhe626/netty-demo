package nio_ser.xyz.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Demo2 {
    public static void main(String[] args) throws IOException {

        //gatherBytes();

        scatterBytes();
    }


    public static void gatherBytes() throws IOException {
        String s="Process finished with exit code ,ok";
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(10);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(100);
        byteBuffer1.asIntBuffer().put(120);
        byteBuffer2.asCharBuffer().put(s);
        FileOutputStream outputStream = new FileOutputStream("E:\\nio\\3.txt");
        FileChannel channel = outputStream.getChannel();
        channel.write(new ByteBuffer[]{byteBuffer1,byteBuffer2});
        channel.close();
        outputStream.close();
    }


    public static void scatterBytes() throws IOException {
        FileInputStream inputStream = new FileInputStream("E:\\nio\\3.txt");
        FileChannel channel = inputStream.getChannel();
        ByteBuffer buffer1 = ByteBuffer.allocate(10);
        ByteBuffer buffer2 = ByteBuffer.allocate(100);
        channel.read(new ByteBuffer[]{buffer1,buffer2});

        //Read the two buffers seperately
        buffer1.rewind();
        buffer2.rewind();
        int i = buffer1.asIntBuffer().get();
        System.out.println(i);
        String string = buffer2.asCharBuffer().toString();
        System.out.println(string);


    }


}
