package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.request.LogoutRequestPacket;
import protocol.response.LogoutResponsePacket;
import util.SessionUtil;

public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();

        Channel channel = ctx.channel();

        if (SessionUtil.hasLogin(channel)){
            logoutResponsePacket.setSuccess(true);
            System.out.println("[" + SessionUtil.getSession(channel).getUserName() + "]注销登陆成功");
            SessionUtil.unBindSession(channel);
            ctx.channel().writeAndFlush(logoutResponsePacket);
        }else {
            logoutResponsePacket.setSuccess(false);
            logoutResponsePacket.setReason("尚未登陆");
            System.out.println("[" + SessionUtil.getSession(channel).getUserName() + "]注销登陆失败");
            ctx.channel().writeAndFlush(logoutResponsePacket);
            channel.close();
        }

    }
}
