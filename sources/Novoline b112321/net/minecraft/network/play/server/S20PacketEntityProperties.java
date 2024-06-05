package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S20PacketEntityProperties$Snapshot;

public class S20PacketEntityProperties implements Packet {
   private int entityId;
   private final List field_149444_b = Lists.newArrayList();

   public S20PacketEntityProperties() {
   }

   public S20PacketEntityProperties(int var1, Collection var2) {
      this.entityId = var1;

      for(IAttributeInstance var4 : var2) {
         this.field_149444_b.add(new S20PacketEntityProperties$Snapshot(this, var4.getAttribute().getAttributeUnlocalizedName(), var4.getBaseValue(), var4.func_111122_c()));
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
      int var2 = var1.readInt();

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = var1.a(64);
         double var5 = var1.readDouble();
         ArrayList var7 = Lists.newArrayList();
         int var8 = var1.readVarIntFromBuffer();

         for(int var9 = 0; var9 < var8; ++var9) {
            UUID var10 = var1.readUuid();
            var7.add(new AttributeModifier(var10, "Unknown synced attribute modifier", var1.readDouble(), var1.readByte()));
         }

         this.field_149444_b.add(new S20PacketEntityProperties$Snapshot(this, var4, var5, var7));
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
      var1.writeInt(this.field_149444_b.size());

      for(S20PacketEntityProperties$Snapshot var3 : this.field_149444_b) {
         var1.writeString(var3.func_151409_a());
         var1.writeDouble(var3.func_151410_b());
         var1.writeVarIntToBuffer(var3.func_151408_c().size());

         for(AttributeModifier var5 : var3.func_151408_c()) {
            var1.writeUuid(var5.getID());
            var1.writeDouble(var5.getAmount());
            var1.writeByte(var5.getOperation());
         }
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityProperties(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public List func_149441_d() {
      return this.field_149444_b;
   }
}
