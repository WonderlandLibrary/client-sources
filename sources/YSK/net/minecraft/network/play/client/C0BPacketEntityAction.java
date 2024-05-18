package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import java.io.*;

public class C0BPacketEntityAction implements Packet<INetHandlerPlayServer>
{
    private Action action;
    private int auxData;
    private int entityID;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C0BPacketEntityAction(final Entity entity, final Action action) {
        this(entity, action, "".length());
    }
    
    public C0BPacketEntityAction(final Entity entity, final Action action, final int auxData) {
        this.entityID = entity.getEntityId();
        this.action = action;
        this.auxData = auxData;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.action = packetBuffer.readEnumValue(Action.class);
        this.auxData = packetBuffer.readVarIntFromBuffer();
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public int getAuxData() {
        return this.auxData;
    }
    
    public C0BPacketEntityAction() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeVarIntToBuffer(this.auxData);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processEntityAction(this);
    }
    
    public enum Action
    {
        private static final Action[] ENUM$VALUES;
        
        START_SNEAKING(Action.I["".length()], "".length()), 
        STOP_SPRINTING(Action.I[0x65 ^ 0x61], 0x5E ^ 0x5A), 
        STOP_SLEEPING(Action.I["  ".length()], "  ".length()), 
        RIDING_JUMP(Action.I[0x4E ^ 0x4B], 0x9D ^ 0x98);
        
        private static final String[] I;
        
        STOP_SNEAKING(Action.I[" ".length()], " ".length()), 
        OPEN_INVENTORY(Action.I[0xC6 ^ 0xC0], 0x49 ^ 0x4F), 
        START_SPRINTING(Action.I["   ".length()], "   ".length());
        
        private Action(final String s, final int n) {
        }
        
        private static void I() {
            (I = new String[0x1F ^ 0x18])["".length()] = I(" >(\u001f\u001b,9'\b\u000e8#'\n", "sjiMO");
            Action.I[" ".length()] = I("\u0003\u0004*\u0003\u0015\u0003\u001e \u0012\u0001\u0019\u001e\"", "PPeSJ");
            Action.I["  ".length()] = I("\u0006\u001b% 6\u0006\u0003/59\u001c\u0001-", "UOjpi");
            Action.I["   ".length()] = I(":.1\u0010\u001a6) \u0010\u0007'.9\f\t", "izpBN");
            Action.I[0x51 ^ 0x55] = I("\u0015\u001b\r=8\u0015\u001f\u0010$)\u0012\u0006\f*", "FOBmg");
            Action.I[0x8 ^ 0xD] = I("6\u001c6\u001b<#\n8\u0007?4", "dUrRr");
            Action.I[0x5F ^ 0x59] = I(":2\u0016\u0019\u001c<,\u0005\u0012\r!-\u0001\u000e", "ubSWC");
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
                if (1 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final Action[] enum$VALUES = new Action[0x26 ^ 0x21];
            enum$VALUES["".length()] = Action.START_SNEAKING;
            enum$VALUES[" ".length()] = Action.STOP_SNEAKING;
            enum$VALUES["  ".length()] = Action.STOP_SLEEPING;
            enum$VALUES["   ".length()] = Action.START_SPRINTING;
            enum$VALUES[0xBA ^ 0xBE] = Action.STOP_SPRINTING;
            enum$VALUES[0x85 ^ 0x80] = Action.RIDING_JUMP;
            enum$VALUES[0x7D ^ 0x7B] = Action.OPEN_INVENTORY;
            ENUM$VALUES = enum$VALUES;
        }
    }
}
