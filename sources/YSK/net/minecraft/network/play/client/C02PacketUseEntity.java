package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.*;

public class C02PacketUseEntity implements Packet<INetHandlerPlayServer>
{
    private int entityId;
    private Action action;
    private Vec3 hitVec;
    
    public Vec3 getHitVec() {
        return this.hitVec;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public Entity getEntityFromWorld(final World world) {
        return world.getEntityByID(this.entityId);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processUseEntity(this);
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
            if (2 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeEnumValue(this.action);
        if (this.action == Action.INTERACT_AT) {
            packetBuffer.writeFloat((float)this.hitVec.xCoord);
            packetBuffer.writeFloat((float)this.hitVec.yCoord);
            packetBuffer.writeFloat((float)this.hitVec.zCoord);
        }
    }
    
    public C02PacketUseEntity() {
    }
    
    public C02PacketUseEntity(final Entity entity, final Action action) {
        this.entityId = entity.getEntityId();
        this.action = action;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.action = packetBuffer.readEnumValue(Action.class);
        if (this.action == Action.INTERACT_AT) {
            this.hitVec = new Vec3(packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readFloat());
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C02PacketUseEntity(final Entity entity, final Vec3 hitVec) {
        this(entity, Action.INTERACT_AT);
        this.hitVec = hitVec;
    }
    
    public enum Action
    {
        INTERACT_AT(Action.I["  ".length()], "  ".length());
        
        private static final Action[] ENUM$VALUES;
        private static final String[] I;
        
        INTERACT(Action.I["".length()], "".length()), 
        ATTACK(Action.I[" ".length()], " ".length());
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("\u0001\u0014\f-\u0016\t\u0019\f", "HZXhD");
            Action.I[" ".length()] = I("9!\u0012 .3", "xuFam");
            Action.I["  ".length()] = I("\u0013 \u0017!0\u001b-\u0017;#\u000e", "ZnCdb");
        }
        
        private Action(final String s, final int n) {
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
                if (-1 == 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final Action[] enum$VALUES = new Action["   ".length()];
            enum$VALUES["".length()] = Action.INTERACT;
            enum$VALUES[" ".length()] = Action.ATTACK;
            enum$VALUES["  ".length()] = Action.INTERACT_AT;
            ENUM$VALUES = enum$VALUES;
        }
    }
}
