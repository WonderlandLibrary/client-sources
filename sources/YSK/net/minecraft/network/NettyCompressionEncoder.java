package net.minecraft.network;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import java.util.zip.*;
import io.netty.channel.*;

public class NettyCompressionEncoder extends MessageToByteEncoder<ByteBuf>
{
    private final Deflater deflater;
    private final byte[] buffer;
    private int treshold;
    
    public NettyCompressionEncoder(final int treshold) {
        this.buffer = new byte[3019 + 6081 - 7545 + 6637];
        this.treshold = treshold;
        this.deflater = new Deflater();
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws Exception {
        final int readableBytes = byteBuf.readableBytes();
        final PacketBuffer packetBuffer = new PacketBuffer(byteBuf2);
        if (readableBytes < this.treshold) {
            packetBuffer.writeVarIntToBuffer("".length());
            packetBuffer.writeBytes(byteBuf);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            final byte[] array = new byte[readableBytes];
            byteBuf.readBytes(array);
            packetBuffer.writeVarIntToBuffer(array.length);
            this.deflater.setInput(array, "".length(), readableBytes);
            this.deflater.finish();
            "".length();
            if (false) {
                throw null;
            }
            while (!this.deflater.finished()) {
                packetBuffer.writeBytes(this.buffer, "".length(), this.deflater.deflate(this.buffer));
            }
            this.deflater.reset();
        }
    }
    
    public void setCompressionTreshold(final int treshold) {
        this.treshold = treshold;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
}
