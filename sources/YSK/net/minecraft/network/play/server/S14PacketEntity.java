package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;

public class S14PacketEntity implements Packet<INetHandlerPlayClient>
{
    private static final String[] I;
    protected byte yaw;
    protected byte pitch;
    protected int entityId;
    protected byte posZ;
    protected byte posY;
    protected boolean onGround;
    protected byte posX;
    protected boolean field_149069_g;
    
    public byte func_149061_d() {
        return this.posY;
    }
    
    static {
        I();
    }
    
    public boolean func_149060_h() {
        return this.field_149069_g;
    }
    
    public byte func_149062_c() {
        return this.posX;
    }
    
    public S14PacketEntity(final int entityId) {
        this.entityId = entityId;
    }
    
    public byte func_149064_e() {
        return this.posZ;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
    }
    
    public byte func_149066_f() {
        return this.yaw;
    }
    
    public byte func_149063_g() {
        return this.pitch;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityMovement(this);
    }
    
    public S14PacketEntity() {
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Entity getEntity(final World world) {
        return world.getEntityByID(this.entityId);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001c+\u001e$\u0005 \u001a", "YEjMq");
    }
    
    @Override
    public String toString() {
        return S14PacketEntity.I["".length()] + super.toString();
    }
    
    public boolean getOnGround() {
        return this.onGround;
    }
    
    public static class S17PacketEntityLookMove extends S14PacketEntity
    {
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            super.writePacketData(packetBuffer);
            packetBuffer.writeByte(this.posX);
            packetBuffer.writeByte(this.posY);
            packetBuffer.writeByte(this.posZ);
            packetBuffer.writeByte(this.yaw);
            packetBuffer.writeByte(this.pitch);
            packetBuffer.writeBoolean(this.onGround);
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            super.readPacketData(packetBuffer);
            this.posX = packetBuffer.readByte();
            this.posY = packetBuffer.readByte();
            this.posZ = packetBuffer.readByte();
            this.yaw = packetBuffer.readByte();
            this.pitch = packetBuffer.readByte();
            this.onGround = packetBuffer.readBoolean();
        }
        
        public S17PacketEntityLookMove(final int n, final byte posX, final byte posY, final byte posZ, final byte yaw, final byte pitch, final boolean onGround) {
            super(n);
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.yaw = yaw;
            this.pitch = pitch;
            this.onGround = onGround;
            this.field_149069_g = (" ".length() != 0);
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
                if (3 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public S17PacketEntityLookMove() {
            this.field_149069_g = (" ".length() != 0);
        }
    }
    
    public static class S16PacketEntityLook extends S14PacketEntity
    {
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
                if (false) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public S16PacketEntityLook() {
            this.field_149069_g = (" ".length() != 0);
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            super.writePacketData(packetBuffer);
            packetBuffer.writeByte(this.yaw);
            packetBuffer.writeByte(this.pitch);
            packetBuffer.writeBoolean(this.onGround);
        }
        
        public S16PacketEntityLook(final int n, final byte yaw, final byte pitch, final boolean onGround) {
            super(n);
            this.yaw = yaw;
            this.pitch = pitch;
            this.field_149069_g = (" ".length() != 0);
            this.onGround = onGround;
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            super.readPacketData(packetBuffer);
            this.yaw = packetBuffer.readByte();
            this.pitch = packetBuffer.readByte();
            this.onGround = packetBuffer.readBoolean();
        }
    }
    
    public static class S15PacketEntityRelMove extends S14PacketEntity
    {
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
                if (1 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public S15PacketEntityRelMove(final int n, final byte posX, final byte posY, final byte posZ, final boolean onGround) {
            super(n);
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.onGround = onGround;
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            super.writePacketData(packetBuffer);
            packetBuffer.writeByte(this.posX);
            packetBuffer.writeByte(this.posY);
            packetBuffer.writeByte(this.posZ);
            packetBuffer.writeBoolean(this.onGround);
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            super.readPacketData(packetBuffer);
            this.posX = packetBuffer.readByte();
            this.posY = packetBuffer.readByte();
            this.posZ = packetBuffer.readByte();
            this.onGround = packetBuffer.readBoolean();
        }
        
        public S15PacketEntityRelMove() {
        }
    }
}
