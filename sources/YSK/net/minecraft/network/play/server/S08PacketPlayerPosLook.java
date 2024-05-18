package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;
import java.util.*;

public class S08PacketPlayerPosLook implements Packet<INetHandlerPlayClient>
{
    private float yaw;
    private double y;
    private double x;
    private double z;
    private Set<EnumFlags> field_179835_f;
    private float pitch;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.yaw = packetBuffer.readFloat();
        this.pitch = packetBuffer.readFloat();
        this.field_179835_f = EnumFlags.func_180053_a(packetBuffer.readUnsignedByte());
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeFloat(this.yaw);
        packetBuffer.writeFloat(this.pitch);
        packetBuffer.writeByte(EnumFlags.func_180056_a(this.field_179835_f));
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public double getY() {
        return this.y;
    }
    
    public float getYaw() {
        return this.yaw;
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Set<EnumFlags> func_179834_f() {
        return this.field_179835_f;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handlePlayerPosLook(this);
    }
    
    public S08PacketPlayerPosLook() {
    }
    
    public S08PacketPlayerPosLook(final double x, final double y, final double z, final float yaw, final float pitch, final Set<EnumFlags> field_179835_f) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.field_179835_f = field_179835_f;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public double getX() {
        return this.x;
    }
    
    public enum EnumFlags
    {
        private static final String[] I;
        
        Z(EnumFlags.I["  ".length()], "  ".length(), "  ".length()), 
        X_ROT(EnumFlags.I[0xC6 ^ 0xC2], 0xD ^ 0x9, 0x6D ^ 0x69), 
        Y_ROT(EnumFlags.I["   ".length()], "   ".length(), "   ".length()), 
        Y(EnumFlags.I[" ".length()], " ".length(), " ".length());
        
        private static final EnumFlags[] ENUM$VALUES;
        
        X(EnumFlags.I["".length()], "".length(), "".length());
        
        private int field_180058_f;
        
        private static void I() {
            (I = new String[0x27 ^ 0x22])["".length()] = I("!", "yMeYE");
            EnumFlags.I[" ".length()] = I("+", "rsTQd");
            EnumFlags.I["  ".length()] = I("\u0015", "OWQrE");
            EnumFlags.I["   ".length()] = I("\u001d>\b\u001a$", "DaZUp");
            EnumFlags.I[0x39 ^ 0x3D] = I("56\u0000\u0005-", "miRJy");
        }
        
        private boolean func_180054_b(final int n) {
            if ((n & this.func_180055_a()) == this.func_180055_a()) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        private EnumFlags(final String s, final int n, final int field_180058_f) {
            this.field_180058_f = field_180058_f;
        }
        
        public static Set<EnumFlags> func_180053_a(final int n) {
            final EnumSet<EnumFlags> none = EnumSet.noneOf(EnumFlags.class);
            final EnumFlags[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
            while (i < length) {
                final EnumFlags enumFlags = values[i];
                if (enumFlags.func_180054_b(n)) {
                    none.add(enumFlags);
                }
                ++i;
            }
            return none;
        }
        
        private int func_180055_a() {
            return " ".length() << this.field_180058_f;
        }
        
        static {
            I();
            final EnumFlags[] enum$VALUES = new EnumFlags[0x29 ^ 0x2C];
            enum$VALUES["".length()] = EnumFlags.X;
            enum$VALUES[" ".length()] = EnumFlags.Y;
            enum$VALUES["  ".length()] = EnumFlags.Z;
            enum$VALUES["   ".length()] = EnumFlags.Y_ROT;
            enum$VALUES[0x28 ^ 0x2C] = EnumFlags.X_ROT;
            ENUM$VALUES = enum$VALUES;
        }
        
        public static int func_180056_a(final Set<EnumFlags> set) {
            int length = "".length();
            final Iterator<EnumFlags> iterator = set.iterator();
            "".length();
            if (3 < 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                length |= iterator.next().func_180055_a();
            }
            return length;
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
                if (4 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
