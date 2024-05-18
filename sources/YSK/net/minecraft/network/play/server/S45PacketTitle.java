package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S45PacketTitle implements Packet<INetHandlerPlayClient>
{
    private int fadeOutTime;
    private int displayTime;
    private IChatComponent message;
    private Type type;
    private int fadeInTime;
    
    public S45PacketTitle(final int n, final int n2, final int n3) {
        this(Type.TIMES, null, n, n2, n3);
    }
    
    public S45PacketTitle() {
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleTitle(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.type = packetBuffer.readEnumValue(Type.class);
        if (this.type == Type.TITLE || this.type == Type.SUBTITLE) {
            this.message = packetBuffer.readChatComponent();
        }
        if (this.type == Type.TIMES) {
            this.fadeInTime = packetBuffer.readInt();
            this.displayTime = packetBuffer.readInt();
            this.fadeOutTime = packetBuffer.readInt();
        }
    }
    
    public Type getType() {
        return this.type;
    }
    
    public int getFadeOutTime() {
        return this.fadeOutTime;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.type);
        if (this.type == Type.TITLE || this.type == Type.SUBTITLE) {
            packetBuffer.writeChatComponent(this.message);
        }
        if (this.type == Type.TIMES) {
            packetBuffer.writeInt(this.fadeInTime);
            packetBuffer.writeInt(this.displayTime);
            packetBuffer.writeInt(this.fadeOutTime);
        }
    }
    
    public IChatComponent getMessage() {
        return this.message;
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
    
    public S45PacketTitle(final Type type, final IChatComponent chatComponent) {
        this(type, chatComponent, -" ".length(), -" ".length(), -" ".length());
    }
    
    public int getFadeInTime() {
        return this.fadeInTime;
    }
    
    public S45PacketTitle(final Type type, final IChatComponent message, final int fadeInTime, final int displayTime, final int fadeOutTime) {
        this.type = type;
        this.message = message;
        this.fadeInTime = fadeInTime;
        this.displayTime = displayTime;
        this.fadeOutTime = fadeOutTime;
    }
    
    public int getDisplayTime() {
        return this.displayTime;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public enum Type
    {
        private static final Type[] ENUM$VALUES;
        
        SUBTITLE(Type.I[" ".length()], " ".length()), 
        CLEAR(Type.I["   ".length()], "   ".length());
        
        private static final String[] I;
        
        TIMES(Type.I["  ".length()], "  ".length()), 
        RESET(Type.I[0xBD ^ 0xB9], 0x62 ^ 0x66), 
        TITLE(Type.I["".length()], "".length());
        
        private Type(final String s, final int n) {
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
                if (1 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0x32 ^ 0x37])["".length()] = I("\u0011\u000f0\u000b\u0007", "EFdGB");
            Type.I[" ".length()] = I("\u001a1\u0015\f\u001b\u001d(\u0012", "IdWXR");
            Type.I["  ".length()] = I("\u0016!7(0", "Bhzmc");
            Type.I["   ".length()] = I("!&=\r1", "bjxLc");
            Type.I[0xB6 ^ 0xB2] = I("3,)\f1", "aizIe");
        }
        
        public static String[] getNames() {
            final String[] array = new String[values().length];
            int length = "".length();
            final Type[] values;
            final int length2 = (values = values()).length;
            int i = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (i < length2) {
                array[length++] = values[i].name().toLowerCase();
                ++i;
            }
            return array;
        }
        
        public static Type byName(final String s) {
            final Type[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
            while (i < length) {
                final Type type = values[i];
                if (type.name().equalsIgnoreCase(s)) {
                    return type;
                }
                ++i;
            }
            return Type.TITLE;
        }
        
        static {
            I();
            final Type[] enum$VALUES = new Type[0x6B ^ 0x6E];
            enum$VALUES["".length()] = Type.TITLE;
            enum$VALUES[" ".length()] = Type.SUBTITLE;
            enum$VALUES["  ".length()] = Type.TIMES;
            enum$VALUES["   ".length()] = Type.CLEAR;
            enum$VALUES[0x4A ^ 0x4E] = Type.RESET;
            ENUM$VALUES = enum$VALUES;
        }
    }
}
