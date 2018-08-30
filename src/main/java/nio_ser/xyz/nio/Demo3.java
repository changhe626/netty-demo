package nio_ser.xyz.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class Demo3 {
    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            list.add("E:\\nio\\"+i+".txt");
        }

        FileOutputStream stream = new FileOutputStream("E:\\nio\\5.txt");
        FileChannel channel = stream.getChannel();
        for (String s : list) {
            FileInputStream inputStream = new FileInputStream(s);
            FileChannel inputStreamChannel = inputStream.getChannel();
            inputStreamChannel.transferTo(0, inputStreamChannel.size(),channel);
            inputStream.close();
            inputStream.close();
        }

        channel.close();
        stream.close();

    }
}
