package com.onyx.my_encode_decode.server;

import com.onyx.my_encode_decode.common.RequestDecoder;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.onyx.my_encode_decode.common.ResponseEncoder;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;


public class Server {
    public static void main(String[] args) throws InterruptedException {

        //服务类
        ServerBootstrap bootstrap = new ServerBootstrap();

        //boss线程监听端口，worker线程负责数据读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //设置niosocket工厂
        bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));

        //设置管道的工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            @Override
            public ChannelPipeline getPipeline() throws Exception {

                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new RequestDecoder());
                pipeline.addLast("encoder", new ResponseEncoder());
                pipeline.addLast("helloHandler", new ServerHandle());
                return pipeline;
            }
        });

        //4.进行绑定,服务端只需要绑定端口号
        bootstrap.bind(new InetSocketAddress(10101));

        System.out.println("start!!!");

    }
}
