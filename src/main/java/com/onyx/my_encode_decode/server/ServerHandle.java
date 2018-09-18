package com.onyx.my_encode_decode.server;


import com.onyx.my_encode_decode.common.ConstantValue;
import com.onyx.my_encode_decode.common.Request;
import com.onyx.my_encode_decode.common.Response;
import com.onyx.my_encode_decode.common.fuben.request.FightRequest;
import com.onyx.my_encode_decode.common.fuben.response.FightResponse;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ServerHandle extends SimpleChannelHandler {


    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Request message = (Request)e.getMessage();

        //进行分类处理,不同的模块啊,不同的命令都是不同的处理
        if(message.getModule() == 1){

            if(message.getCmd() == 1){

                FightRequest fightRequest = new FightRequest();
                fightRequest.readFromBytes(message.getData());

                System.out.println("fubenId:" +fightRequest.getFubenId() + "   " + "count:" + fightRequest.getCount());

                //回写数据
                FightResponse fightResponse = new FightResponse();
                //int gold = fightRequest.getFubenId() * fightRequest.getCount() * 5;
                fightResponse.setGold(66);

                Response response = new Response();
                response.setModule((short) 1);
                response.setCmd((short) 1);
                response.setStateCode(ConstantValue.SUCCESS);
                response.setData(fightResponse.getBytes());
                ctx.getChannel().write(response);
            }else if(message.getCmd() == 2){
            }
        }else if (message.getModule() == 1){
        }
    }

    /**
     * 捕获异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("exceptionCaught");
        super.exceptionCaught(ctx, e);
    }

    /**
     * 新连接
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelConnected");
        super.channelConnected(ctx, e);
    }

    /**
     * 必须是链接已经建立，关闭通道的时候才会触发
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelDisconnected");
        super.channelDisconnected(ctx, e);
    }

    /**
     * channel关闭的时候触发
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelClosed");
        super.channelClosed(ctx, e);
    }
}
