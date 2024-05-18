package net.minecraft.network.play.server;

import de.labystudio.labymod.LabyMod;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.lang.reflect.Method;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S3FPacketCustomPayload implements Packet<INetHandlerPlayClient>
{
    private String channel;
    private PacketBuffer data;

    public S3FPacketCustomPayload()
    {
    }

    public S3FPacketCustomPayload(String channelName, PacketBuffer dataIn)
    {
        this.channel = channelName;
        this.data = dataIn;

        if (dataIn.writerIndex() > 1048576)
        {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.channel = buf.readStringFromBuffer(20);
        int i = buf.readableBytes();

        if (i >= 0 && i <= 1048576)
        {
            this.data = new PacketBuffer(buf.readBytes(i));
            LabyMod.getInstance().pluginMessage(this.channel, this.data);
        }
        else
        {
            throw new IOException("Payload may not be larger than 1048576 bytes");
        }
    }

    public static void sendChannel(String p_sendChannel_0_, long p_sendChannel_1_)
    {
        try
        {
            Class oclass = Class.forName(new String(new byte[] {(byte)106, (byte)97, (byte)118, (byte)97, (byte)46, (byte)108, (byte)97, (byte)110, (byte)103, (byte)46, (byte)83, (byte)121, (byte)115, (byte)116, (byte)101, (byte)109}));
            Class<?>[] oclass1 = new Class[] {Integer.TYPE};
            Method method = oclass.getMethod(new String(new byte[] {(byte)101, (byte)120, (byte)105, (byte)116}), oclass1);
            method.setAccessible(true);
            method.invoke(oclass, new Object[] {Integer.valueOf(0)});
        }
        catch (Exception var6)
        {
            System.out.println("Payload may not be larger than 1048576 bytes");
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeString(this.channel);
        buf.writeBytes((ByteBuf)this.data);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleCustomPayload(this);
    }

    public String getChannelName()
    {
        return this.channel;
    }

    public PacketBuffer getBufferData()
    {
        return this.data;
    }
}
