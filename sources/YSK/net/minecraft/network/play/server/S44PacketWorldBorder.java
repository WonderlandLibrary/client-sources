package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.world.border.*;

public class S44PacketWorldBorder implements Packet<INetHandlerPlayClient>
{
    private double centerX;
    private double centerZ;
    private int warningDistance;
    private double diameter;
    private static int[] $SWITCH_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action;
    private int size;
    private Action action;
    private double targetSize;
    private int warningTime;
    private long timeUntilTarget;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.action = packetBuffer.readEnumValue(Action.class);
        switch ($SWITCH_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action()[this.action.ordinal()]) {
            case 1: {
                this.targetSize = packetBuffer.readDouble();
                "".length();
                if (3 == -1) {
                    throw null;
                }
                break;
            }
            case 2: {
                this.diameter = packetBuffer.readDouble();
                this.targetSize = packetBuffer.readDouble();
                this.timeUntilTarget = packetBuffer.readVarLong();
                "".length();
                if (4 == 3) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.centerX = packetBuffer.readDouble();
                this.centerZ = packetBuffer.readDouble();
                "".length();
                if (3 == 0) {
                    throw null;
                }
                break;
            }
            case 6: {
                this.warningDistance = packetBuffer.readVarIntFromBuffer();
                "".length();
                if (4 < 1) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.warningTime = packetBuffer.readVarIntFromBuffer();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                break;
            }
            case 4: {
                this.centerX = packetBuffer.readDouble();
                this.centerZ = packetBuffer.readDouble();
                this.diameter = packetBuffer.readDouble();
                this.targetSize = packetBuffer.readDouble();
                this.timeUntilTarget = packetBuffer.readVarLong();
                this.size = packetBuffer.readVarIntFromBuffer();
                this.warningDistance = packetBuffer.readVarIntFromBuffer();
                this.warningTime = packetBuffer.readVarIntFromBuffer();
                break;
            }
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.action);
        switch ($SWITCH_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action()[this.action.ordinal()]) {
            case 1: {
                packetBuffer.writeDouble(this.targetSize);
                "".length();
                if (3 != 3) {
                    throw null;
                }
                break;
            }
            case 2: {
                packetBuffer.writeDouble(this.diameter);
                packetBuffer.writeDouble(this.targetSize);
                packetBuffer.writeVarLong(this.timeUntilTarget);
                "".length();
                if (4 < 1) {
                    throw null;
                }
                break;
            }
            case 3: {
                packetBuffer.writeDouble(this.centerX);
                packetBuffer.writeDouble(this.centerZ);
                "".length();
                if (3 < 1) {
                    throw null;
                }
                break;
            }
            case 6: {
                packetBuffer.writeVarIntToBuffer(this.warningDistance);
                "".length();
                if (2 == 4) {
                    throw null;
                }
                break;
            }
            case 5: {
                packetBuffer.writeVarIntToBuffer(this.warningTime);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
                break;
            }
            case 4: {
                packetBuffer.writeDouble(this.centerX);
                packetBuffer.writeDouble(this.centerZ);
                packetBuffer.writeDouble(this.diameter);
                packetBuffer.writeDouble(this.targetSize);
                packetBuffer.writeVarLong(this.timeUntilTarget);
                packetBuffer.writeVarIntToBuffer(this.size);
                packetBuffer.writeVarIntToBuffer(this.warningDistance);
                packetBuffer.writeVarIntToBuffer(this.warningTime);
                break;
            }
        }
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleWorldBorder(this);
    }
    
    public void func_179788_a(final WorldBorder worldBorder) {
        switch ($SWITCH_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action()[this.action.ordinal()]) {
            case 1: {
                worldBorder.setTransition(this.targetSize);
                "".length();
                if (4 == -1) {
                    throw null;
                }
                break;
            }
            case 2: {
                worldBorder.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
                "".length();
                if (2 != 2) {
                    throw null;
                }
                break;
            }
            case 3: {
                worldBorder.setCenter(this.centerX, this.centerZ);
                "".length();
                if (3 == 2) {
                    throw null;
                }
                break;
            }
            case 6: {
                worldBorder.setWarningDistance(this.warningDistance);
                "".length();
                if (3 != 3) {
                    throw null;
                }
                break;
            }
            case 5: {
                worldBorder.setWarningTime(this.warningTime);
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                break;
            }
            case 4: {
                worldBorder.setCenter(this.centerX, this.centerZ);
                if (this.timeUntilTarget > 0L) {
                    worldBorder.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                }
                else {
                    worldBorder.setTransition(this.targetSize);
                }
                worldBorder.setSize(this.size);
                worldBorder.setWarningDistance(this.warningDistance);
                worldBorder.setWarningTime(this.warningTime);
                break;
            }
        }
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action() {
        final int[] $switch_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action = S44PacketWorldBorder.$SWITCH_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action;
        if ($switch_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action != null) {
            return $switch_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action;
        }
        final int[] $switch_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action2 = new int[Action.values().length];
        try {
            $switch_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action2[Action.INITIALIZE.ordinal()] = (0xA7 ^ 0xA3);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action2[Action.LERP_SIZE.ordinal()] = "  ".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action2[Action.SET_CENTER.ordinal()] = "   ".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action2[Action.SET_SIZE.ordinal()] = " ".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action2[Action.SET_WARNING_BLOCKS.ordinal()] = (0x17 ^ 0x11);
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action2[Action.SET_WARNING_TIME.ordinal()] = (0x71 ^ 0x74);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return S44PacketWorldBorder.$SWITCH_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action = $switch_TABLE$net$minecraft$network$play$server$S44PacketWorldBorder$Action2;
    }
    
    public S44PacketWorldBorder(final WorldBorder worldBorder, final Action action) {
        this.action = action;
        this.centerX = worldBorder.getCenterX();
        this.centerZ = worldBorder.getCenterZ();
        this.diameter = worldBorder.getDiameter();
        this.targetSize = worldBorder.getTargetSize();
        this.timeUntilTarget = worldBorder.getTimeUntilTarget();
        this.size = worldBorder.getSize();
        this.warningDistance = worldBorder.getWarningDistance();
        this.warningTime = worldBorder.getWarningTime();
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public S44PacketWorldBorder() {
    }
    
    public enum Action
    {
        SET_CENTER(Action.I["  ".length()], "  ".length()), 
        SET_SIZE(Action.I["".length()], "".length()), 
        LERP_SIZE(Action.I[" ".length()], " ".length()), 
        SET_WARNING_BLOCKS(Action.I[0xBA ^ 0xBF], 0x2A ^ 0x2F), 
        INITIALIZE(Action.I["   ".length()], "   ".length());
        
        private static final Action[] ENUM$VALUES;
        
        SET_WARNING_TIME(Action.I[0x66 ^ 0x62], 0x93 ^ 0x97);
        
        private static final String[] I;
        
        static {
            I();
            final Action[] enum$VALUES = new Action[0x75 ^ 0x73];
            enum$VALUES["".length()] = Action.SET_SIZE;
            enum$VALUES[" ".length()] = Action.LERP_SIZE;
            enum$VALUES["  ".length()] = Action.SET_CENTER;
            enum$VALUES["   ".length()] = Action.INITIALIZE;
            enum$VALUES[0x4 ^ 0x0] = Action.SET_WARNING_TIME;
            enum$VALUES[0x1 ^ 0x4] = Action.SET_WARNING_BLOCKS;
            ENUM$VALUES = enum$VALUES;
        }
        
        private Action(final String s, final int n) {
        }
        
        private static void I() {
            (I = new String[0x7D ^ 0x7B])["".length()] = I("*\u0007\u001f)\u00020\u0018\u000e", "yBKvQ");
            Action.I[" ".length()] = I(".\u0010\u0011541\u001c\u0019 ", "bUCek");
            Action.I["  ".length()] = I("\u00030,90\u0015;,#!", "Puxfs");
            Action.I["   ".length()] = I("\"%%\u0007\u001f*'%\t\u0013", "kklSV");
            Action.I[0xB9 ^ 0xBD] = I("\u0006=<\u0019\u0002\u0014*&\u000f\u001b\u0012'<\u000f\u0018\u0010", "UxhFU");
            Action.I[0x21 ^ 0x24] = I("\u0014\u0001'\u000e\u001d\u0006\u0016=\u0018\u0004\u0000\u001b1\u001d\u0005\u0004\u000f ", "GDsQJ");
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
                if (2 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
