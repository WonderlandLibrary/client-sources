package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C03PacketPlayer implements Packet<INetHandlerPlayServer>
{
    protected double z;
    protected boolean moving;
    protected float pitch;
    protected float yaw;
    protected boolean onGround;
    protected double y;
    protected double x;
    protected boolean rotating;
    
    public double getPositionZ() {
        return this.z;
    }
    
    public C03PacketPlayer(final boolean onGround) {
        this.onGround = onGround;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public void setMoving(final boolean moving) {
        this.moving = moving;
    }
    
    public boolean isMoving() {
        return this.moving;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        int onGround;
        if (packetBuffer.readUnsignedByte() != 0) {
            onGround = " ".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            onGround = "".length();
        }
        this.onGround = (onGround != 0);
    }
    
    public double getPositionX() {
        return this.x;
    }
    
    public double getPositionY() {
        return this.y;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processPlayer(this);
    }
    
    public C03PacketPlayer() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public boolean getRotating() {
        return this.rotating;
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
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        int n;
        if (this.onGround) {
            n = " ".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        packetBuffer.writeByte(n);
    }
    
    public static class C04PacketPlayerPosition extends C03PacketPlayer
    {
        public C04PacketPlayerPosition() {
            this.moving = (" ".length() != 0);
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            this.x = packetBuffer.readDouble();
            this.y = packetBuffer.readDouble();
            this.z = packetBuffer.readDouble();
            super.readPacketData(packetBuffer);
        }
        
        public C04PacketPlayerPosition(final double x, final double y, final double z, final boolean onGround) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.onGround = onGround;
            this.moving = (" ".length() != 0);
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            packetBuffer.writeDouble(this.x);
            packetBuffer.writeDouble(this.y);
            packetBuffer.writeDouble(this.z);
            super.writePacketData(packetBuffer);
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
                if (3 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class C05PacketPlayerLook extends C03PacketPlayer
    {
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            packetBuffer.writeFloat(this.yaw);
            packetBuffer.writeFloat(this.pitch);
            super.writePacketData(packetBuffer);
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            this.yaw = packetBuffer.readFloat();
            this.pitch = packetBuffer.readFloat();
            super.readPacketData(packetBuffer);
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
                if (4 < 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public C05PacketPlayerLook(final float yaw, final float pitch, final boolean onGround) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.onGround = onGround;
            this.rotating = (" ".length() != 0);
        }
        
        public C05PacketPlayerLook() {
            this.rotating = (" ".length() != 0);
        }
    }
    
    public static class C06PacketPlayerPosLook extends C03PacketPlayer
    {
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            packetBuffer.writeDouble(this.x);
            packetBuffer.writeDouble(this.y);
            packetBuffer.writeDouble(this.z);
            packetBuffer.writeFloat(this.yaw);
            packetBuffer.writeFloat(this.pitch);
            super.writePacketData(packetBuffer);
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
                if (1 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public C06PacketPlayerPosLook(final double x, final double y, final double z, final float yaw, final float pitch, final boolean onGround) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.onGround = onGround;
            this.rotating = (" ".length() != 0);
            this.moving = (" ".length() != 0);
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            this.x = packetBuffer.readDouble();
            this.y = packetBuffer.readDouble();
            this.z = packetBuffer.readDouble();
            this.yaw = packetBuffer.readFloat();
            this.pitch = packetBuffer.readFloat();
            super.readPacketData(packetBuffer);
        }
        
        public C06PacketPlayerPosLook() {
            this.moving = (" ".length() != 0);
            this.rotating = (" ".length() != 0);
        }
    }
}
