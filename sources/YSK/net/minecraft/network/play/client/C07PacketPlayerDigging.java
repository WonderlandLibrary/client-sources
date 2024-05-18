package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.io.*;

public class C07PacketPlayerDigging implements Packet<INetHandlerPlayServer>
{
    private BlockPos position;
    private Action status;
    private EnumFacing facing;
    
    public Action getStatus() {
        return this.status;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processPlayerDigging(this);
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C07PacketPlayerDigging() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.status);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.facing.getIndex());
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.status = packetBuffer.readEnumValue(Action.class);
        this.position = packetBuffer.readBlockPos();
        this.facing = EnumFacing.getFront(packetBuffer.readUnsignedByte());
    }
    
    public EnumFacing getFacing() {
        return this.facing;
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
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public C07PacketPlayerDigging(final Action status, final BlockPos position, final EnumFacing facing) {
        this.status = status;
        this.position = position;
        this.facing = facing;
    }
    
    public enum Action
    {
        RELEASE_USE_ITEM(Action.I[0x95 ^ 0x90], 0x23 ^ 0x26), 
        STOP_DESTROY_BLOCK(Action.I["  ".length()], "  ".length()), 
        DROP_ITEM(Action.I[0x36 ^ 0x32], 0xB1 ^ 0xB5);
        
        private static final String[] I;
        private static final Action[] ENUM$VALUES;
        
        START_DESTROY_BLOCK(Action.I["".length()], "".length()), 
        ABORT_DESTROY_BLOCK(Action.I[" ".length()], " ".length()), 
        DROP_ALL_ITEMS(Action.I["   ".length()], "   ".length());
        
        private static void I() {
            (I = new String[0x2F ^ 0x29])["".length()] = I("\u0012#\r\u0000.\u001e3\t\u0001.\u00138\u0015\r8\r8\u000f\u0019", "AwLRz");
            Action.I[" ".length()] = I("\r!\u001f\u001f7\u0013'\u0015\u001e7\u001e,\t\u0012!\u0000,\u0013\u0006", "LcPMc");
            Action.I["  ".length()] = I("\u001a\u00115%+\r\u0000)!&\u0006\u001c%78\u0006\u00061", "IEzut");
            Action.I["   ".length()] = I("\u001d\u0003\u001f'\n\u0018\u001d\u001c(\u001c\r\u0014\u001d$", "YQPwU");
            Action.I[0x55 ^ 0x51] = I("\u001e\n\u000e$&\u0013\f\u00049", "ZXAty");
            Action.I[0x31 ^ 0x34] = I("\u0014\u0016\u0019*7\u0015\u0016\n:%\u0003\f\u001c;3\u000b", "FSUov");
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
                if (2 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final Action[] enum$VALUES = new Action[0x27 ^ 0x21];
            enum$VALUES["".length()] = Action.START_DESTROY_BLOCK;
            enum$VALUES[" ".length()] = Action.ABORT_DESTROY_BLOCK;
            enum$VALUES["  ".length()] = Action.STOP_DESTROY_BLOCK;
            enum$VALUES["   ".length()] = Action.DROP_ALL_ITEMS;
            enum$VALUES[0x70 ^ 0x74] = Action.DROP_ITEM;
            enum$VALUES[0xC7 ^ 0xC2] = Action.RELEASE_USE_ITEM;
            ENUM$VALUES = enum$VALUES;
        }
        
        private Action(final String s, final int n) {
        }
    }
}
