package de.labystudio.chat;

import de.labystudio.handling.PacketDecoder;
import de.labystudio.handling.PacketEncoder;
import de.labystudio.handling.PacketPrepender;
import de.labystudio.handling.PacketSplitter;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.util.concurrent.TimeUnit;

public class ClientChannelInitializer extends ChannelInitializer<NioSocketChannel>
{
    private ClientConnection clientConnection;

    public ClientChannelInitializer(ClientConnection clientConnection)
    {
        this.clientConnection = clientConnection;
    }

    protected void initChannel(NioSocketChannel channel) throws Exception
    {
        this.getClientConnection().ch = channel;
        channel.pipeline().addLast((String)"timeout", (ChannelHandler)(new ReadTimeoutHandler(120L, TimeUnit.SECONDS))).addLast((String)"splitter", (ChannelHandler)(new PacketPrepender())).addLast((String)"decoder", (ChannelHandler)(new PacketDecoder())).addLast((String)"prepender", (ChannelHandler)(new PacketSplitter())).addLast((String)"encoder", (ChannelHandler)(new PacketEncoder())).addLast(new ChannelHandler[] {this.getClientConnection()});
    }

    public ClientConnection getClientConnection()
    {
        return this.clientConnection;
    }
}
