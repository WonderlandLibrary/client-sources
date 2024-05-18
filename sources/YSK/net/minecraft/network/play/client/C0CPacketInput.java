package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C0CPacketInput implements Packet<INetHandlerPlayServer>
{
    private boolean sneaking;
    private float strafeSpeed;
    private boolean jumping;
    private float forwardSpeed;
    
    public boolean isJumping() {
        return this.jumping;
    }
    
    public float getStrafeSpeed() {
        return this.strafeSpeed;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.strafeSpeed);
        packetBuffer.writeFloat(this.forwardSpeed);
        int length = "".length();
        if (this.jumping) {
            length = (byte)(length | " ".length());
        }
        if (this.sneaking) {
            length = (byte)(length | "  ".length());
        }
        packetBuffer.writeByte(length);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.strafeSpeed = packetBuffer.readFloat();
        this.forwardSpeed = packetBuffer.readFloat();
        final byte byte1 = packetBuffer.readByte();
        int jumping;
        if ((byte1 & " ".length()) > 0) {
            jumping = " ".length();
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        else {
            jumping = "".length();
        }
        this.jumping = (jumping != 0);
        int sneaking;
        if ((byte1 & "  ".length()) > 0) {
            sneaking = " ".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            sneaking = "".length();
        }
        this.sneaking = (sneaking != 0);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
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
            if (2 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public C0CPacketInput(final float strafeSpeed, final float forwardSpeed, final boolean jumping, final boolean sneaking) {
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.jumping = jumping;
        this.sneaking = sneaking;
    }
    
    public C0CPacketInput() {
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processInput(this);
    }
    
    public float getForwardSpeed() {
        return this.forwardSpeed;
    }
    
    public boolean isSneaking() {
        return this.sneaking;
    }
}
