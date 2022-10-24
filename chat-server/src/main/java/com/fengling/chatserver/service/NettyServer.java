package com.fengling.chatserver.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class NettyServer {

    @Value("${server.port}")
    private int port;

    @PostConstruct
    public void start() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap
                    .group(group, bossGroup)
                    .channel(NioSctpServerChannel.class)
                    .localAddress(this.port).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    log.info("accept new link : "+ socketChannel.localAddress());
                    socketChannel.pipeline().addLast(new HttpServerCodec());
                    socketChannel.pipeline().addLast(new ChunkedWriteHandler());
                    socketChannel.pipeline().addLast(new HttpObjectAggregator(8192));
                    socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", "WebSocket", true, 65536 * 10));
                    socketChannel.pipeline().addLast(null);
                }
            });
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }
    }
}
