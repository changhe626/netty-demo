package nio_ser.xyz.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * 字符集
 */
public class Demo8 {
    public static void main(String[] args) {

        Charset charset = Charset.forName("UTF-8");
        System.out.println(charset.displayName());
        System.out.println(charset.aliases());
        System.out.println(charset.canEncode());


        String st = "Welcome to yiibai.com, it is Charset test Example.";
        ByteBuffer buffer = ByteBuffer.wrap(st.getBytes());
        CharBuffer charBuffer = charset.decode(buffer);
        System.out.println(charBuffer.toString());
        ByteBuffer buffer1 = charset.encode(charBuffer);
        while (buffer1.hasRemaining()){
           //buffer1.flip();
            //这里加上会报错的....
            System.out.println((char)buffer1.get());
        }


    }
}
