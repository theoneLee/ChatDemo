package client.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.response.LogoutResponsePacket;
import util.SessionUtil;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        Channel channel = ctx.channel();
        String userId = SessionUtil.getSession(channel).getUserId();
        String userName = SessionUtil.getSession(channel).getUserName();
        if (msg.isSuccess()) {
            System.out.println("[" + userName + "]注销成功，userId 为: " + userId);
            SessionUtil.unBindSession(ctx.channel());
        } else {
            System.out.println("[" + userName + "]注销失败，原因：" + msg.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
