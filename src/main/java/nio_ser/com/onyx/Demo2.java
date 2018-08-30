package nio_ser.com.onyx;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zk
 * @Description:
 * @date 2018-07-23 15:25
 */
public class Demo2 {


    /**
     * 注意 buf.flip() 的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据
     * @throws Exception
     */
    @Test
    public void test1() throws Exception{
        RandomAccessFile file = new RandomAccessFile("F:\\API2\\app.txt", "rw");
        FileChannel channel = file.getChannel();
        //要想获得一个Buffer对象首先要进行分配。 每一个Buffer类都有一个allocate方法。下面是一个分配48字节capacity的ByteBuffer的例子。
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        //1.写入数据到Buffer  读取数据，放入buffer
        int read = channel.read(byteBuffer);
        while(read!=-1){
            System.out.println("Read:" + read);
            //2.调用flip()方法   设置buffer切换模式为读模式
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.println((char)byteBuffer.get());   //每次读取1byte，也就是一个字节
            }
            byteBuffer.clear();  //清空buffer，准备再次写入
            //3.从Buffer中读取数据
            read = channel.read(byteBuffer);
        }
        //4.调用clear()方法或者compact()方法
        file.close();
    }


}
