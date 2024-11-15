package exhibition;

import java.lang.reflect.Field;

import exhibition.event.EventSystem;
import exhibition.event.impl.EventPacket;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
public class CustomChannelHandler extends ChannelDuplexHandler
{
    NetworkManager networkManager;
    
    public CustomChannelHandler(final NetworkManager zhuzhux) {
        this.networkManager = zhuzhux;
    }

    @Override
    public void channelRead(final ChannelHandlerContext a, final Object PacketObject) throws Exception {
        if (PacketObject instanceof Packet) {
        	EventPacket ep = (EventPacket)EventSystem.getInstance(EventPacket.class);
            ep.fire((Packet) PacketObject, false);
            if (ep.isCancelled()) {
                return;
            }
        }
        super.channelRead(a, PacketObject);
    }

    @Override
    public void write(final ChannelHandlerContext CHC, final Object PacketObject, final ChannelPromise CP) throws Exception {
        if (PacketObject instanceof Packet) {
            EventPacket ep = (EventPacket)EventSystem.getInstance(EventPacket.class);
            ep.fire((Packet) PacketObject, true);
            if (ep.isCancelled()) {
                 return;
             }
        }
        super.write(CHC, PacketObject, CP);
    }
}
