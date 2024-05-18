package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class S42PacketCombatEvent implements Packet<INetHandlerPlayClient>
{
    public Event eventType;
    public int field_179775_c;
    private static int[] $SWITCH_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event;
    public int field_179772_d;
    public int field_179774_b;
    public String deathMessage;
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleCombatEvent(this);
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.eventType = packetBuffer.readEnumValue(Event.class);
        if (this.eventType == Event.END_COMBAT) {
            this.field_179772_d = packetBuffer.readVarIntFromBuffer();
            this.field_179775_c = packetBuffer.readInt();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else if (this.eventType == Event.ENTITY_DIED) {
            this.field_179774_b = packetBuffer.readVarIntFromBuffer();
            this.field_179775_c = packetBuffer.readInt();
            this.deathMessage = packetBuffer.readStringFromBuffer(22199 + 24600 - 33887 + 19855);
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.eventType);
        if (this.eventType == Event.END_COMBAT) {
            packetBuffer.writeVarIntToBuffer(this.field_179772_d);
            packetBuffer.writeInt(this.field_179775_c);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else if (this.eventType == Event.ENTITY_DIED) {
            packetBuffer.writeVarIntToBuffer(this.field_179774_b);
            packetBuffer.writeInt(this.field_179775_c);
            packetBuffer.writeString(this.deathMessage);
        }
    }
    
    public S42PacketCombatEvent() {
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event() {
        final int[] $switch_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event = S42PacketCombatEvent.$SWITCH_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event;
        if ($switch_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event != null) {
            return $switch_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event;
        }
        final int[] $switch_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event2 = new int[Event.values().length];
        try {
            $switch_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event2[Event.END_COMBAT.ordinal()] = "  ".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event2[Event.ENTER_COMBAT.ordinal()] = " ".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event2[Event.ENTITY_DIED.ordinal()] = "   ".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        return S42PacketCombatEvent.$SWITCH_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event = $switch_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event2;
    }
    
    public S42PacketCombatEvent(final CombatTracker combatTracker, final Event eventType) {
        this.eventType = eventType;
        final EntityLivingBase func_94550_c = combatTracker.func_94550_c();
        switch ($SWITCH_TABLE$net$minecraft$network$play$server$S42PacketCombatEvent$Event()[eventType.ordinal()]) {
            case 2: {
                this.field_179772_d = combatTracker.func_180134_f();
                int entityId;
                if (func_94550_c == null) {
                    entityId = -" ".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    entityId = func_94550_c.getEntityId();
                }
                this.field_179775_c = entityId;
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.field_179774_b = combatTracker.getFighter().getEntityId();
                int entityId2;
                if (func_94550_c == null) {
                    entityId2 = -" ".length();
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else {
                    entityId2 = func_94550_c.getEntityId();
                }
                this.field_179775_c = entityId2;
                this.deathMessage = combatTracker.getDeathMessage().getUnformattedText();
                break;
            }
        }
    }
    
    public enum Event
    {
        private static final String[] I;
        
        ENTITY_DIED(Event.I["  ".length()], "  ".length()), 
        END_COMBAT(Event.I[" ".length()], " ".length());
        
        private static final Event[] ENUM$VALUES;
        
        ENTER_COMBAT(Event.I["".length()], "".length());
        
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
                if (3 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private Event(final String s, final int n) {
        }
        
        static {
            I();
            final Event[] enum$VALUES = new Event["   ".length()];
            enum$VALUES["".length()] = Event.ENTER_COMBAT;
            enum$VALUES[" ".length()] = Event.END_COMBAT;
            enum$VALUES["  ".length()] = Event.ENTITY_DIED;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("\u001d\u0014,\u0010$\u0007\u00197\u00184\u0019\u000e", "XZxUv");
            Event.I[" ".length()] = I(",\u001a\u001e\r\u000e&\u0019\u0018\u0013\u0019", "iTZRM");
            Event.I["  ".length()] = I("\u001c\u001e:\u0002\u0019\u0000\u000f*\u0002\b\u001d", "YPnKM");
        }
    }
}
