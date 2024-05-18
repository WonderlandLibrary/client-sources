package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;

public class S20PacketEntityProperties implements Packet<INetHandlerPlayClient>
{
    private int entityId;
    private static final String[] I;
    private final List<Snapshot> field_149444_b;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeInt(this.field_149444_b.size());
        final Iterator<Snapshot> iterator = this.field_149444_b.iterator();
        "".length();
        if (4 == 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Snapshot snapshot = iterator.next();
            packetBuffer.writeString(snapshot.func_151409_a());
            packetBuffer.writeDouble(snapshot.func_151410_b());
            packetBuffer.writeVarIntToBuffer(snapshot.func_151408_c().size());
            final Iterator<AttributeModifier> iterator2 = snapshot.func_151408_c().iterator();
            "".length();
            if (1 >= 4) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final AttributeModifier attributeModifier = iterator2.next();
                packetBuffer.writeUuid(attributeModifier.getID());
                packetBuffer.writeDouble(attributeModifier.getAmount());
                packetBuffer.writeByte(attributeModifier.getOperation());
            }
        }
    }
    
    public S20PacketEntityProperties() {
        this.field_149444_b = (List<Snapshot>)Lists.newArrayList();
    }
    
    static {
        I();
    }
    
    public List<Snapshot> func_149441_d() {
        return this.field_149444_b;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        final int int1 = packetBuffer.readInt();
        int i = "".length();
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (i < int1) {
            final String stringFromBuffer = packetBuffer.readStringFromBuffer(0x5 ^ 0x45);
            final double double1 = packetBuffer.readDouble();
            final ArrayList arrayList = Lists.newArrayList();
            final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
            int j = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (j < varIntFromBuffer) {
                arrayList.add(new AttributeModifier(packetBuffer.readUuid(), S20PacketEntityProperties.I["".length()], packetBuffer.readDouble(), packetBuffer.readByte()));
                ++j;
            }
            this.field_149444_b.add(new Snapshot(stringFromBuffer, double1, arrayList));
            ++i;
        }
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("4?\u001f\u001d!\u0016?T\u00007\u000f2\u0011\u0017n\u0000%\u0000\u0001'\u0003$\u0000\u0016n\f>\u0010\u001a(\b4\u0006", "aQtsN");
    }
    
    public S20PacketEntityProperties(final int entityId, final Collection<IAttributeInstance> collection) {
        this.field_149444_b = (List<Snapshot>)Lists.newArrayList();
        this.entityId = entityId;
        final Iterator<IAttributeInstance> iterator = collection.iterator();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final IAttributeInstance attributeInstance = iterator.next();
            this.field_149444_b.add(new Snapshot(attributeInstance.getAttribute().getAttributeUnlocalizedName(), attributeInstance.getBaseValue(), attributeInstance.func_111122_c()));
        }
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityProperties(this);
    }
    
    public class Snapshot
    {
        private final Collection<AttributeModifier> field_151411_d;
        private final double field_151413_c;
        private final String field_151412_b;
        final S20PacketEntityProperties this$0;
        
        public Collection<AttributeModifier> func_151408_c() {
            return this.field_151411_d;
        }
        
        public Snapshot(final S20PacketEntityProperties this$0, final String field_151412_b, final double field_151413_c, final Collection<AttributeModifier> field_151411_d) {
            this.this$0 = this$0;
            this.field_151412_b = field_151412_b;
            this.field_151413_c = field_151413_c;
            this.field_151411_d = field_151411_d;
        }
        
        public double func_151410_b() {
            return this.field_151413_c;
        }
        
        public String func_151409_a() {
            return this.field_151412_b;
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
                if (true != true) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
