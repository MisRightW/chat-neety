package com.fengling.chatserver.service;

import com.fengling.chatserver.request.SendMsgReqVO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class NettyServer {

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup group = new NioEventLoopGroup();

    @Value("${server.port}")
    private int port;

    @PostConstruct
    public void start() throws Exception {

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap
                .group(group, bossGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(this.port).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                log.info("{} : is online ! " + socketChannel.localAddress());
                socketChannel.pipeline().addLast(new NettyServerHandler());
            }
        });
        ChannelFuture channelFuture = bootstrap.bind(port).sync();
        channelFuture.channel().closeFuture().sync();
        if (channelFuture.isSuccess()) {
            log.info("start server success");
        }

    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        group.shutdownGracefully().sync();
        bossGroup.shutdownGracefully().sync();
        log.info("close server success");
    }

    /**
     * Push msg to client.
     * @param sendMsgReqVO ??????
     */
    public void sendMsg(SendMsgReqVO sendMsgReqVO){
        // ????????????

        // 1??? ?????????????????????channel

        // 2???????????????????????????

        // 3?????????|??????

        // ?????????????????????????????????channel??????????????????   ???????????????????????????????????????id???channel????????????

    }
}
