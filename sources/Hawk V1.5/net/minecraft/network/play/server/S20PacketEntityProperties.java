package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S20PacketEntityProperties implements Packet {
   private static final String __OBFID = "CL_00001341";
   private final List field_149444_b = Lists.newArrayList();
   private int field_149445_a;

   public int func_149442_c() {
      return this.field_149445_a;
   }

   public List func_149441_d() {
      return this.field_149444_b;
   }

   public void func_180754_a(INetHandlerPlayClient var1) {
      var1.handleEntityProperties(this);
   }

   public S20PacketEntityProperties(int var1, Collection var2) {
      this.field_149445_a = var1;
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         IAttributeInstance var4 = (IAttributeInstance)var3.next();
         this.field_149444_b.add(new S20PacketEntityProperties.Snapshot(this, var4.getAttribute().getAttributeUnlocalizedName(), var4.getBaseValue(), var4.func_111122_c()));
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149445_a = var1.readVarIntFromBuffer();
      int var2 = var1.readInt();

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = var1.readStringFromBuffer(64);
         double var5 = var1.readDouble();
         ArrayList var7 = Lists.newArrayList();
         int var8 = var1.readVarIntFromBuffer();

         for(int var9 = 0; var9 < var8; ++var9) {
            UUID var10 = var1.readUuid();
            var7.add(new AttributeModifier(var10, "Unknown synced attribute modifier", var1.readDouble(), var1.readByte()));
         }

         this.field_149444_b.add(new S20PacketEntityProperties.Snapshot(this, var4, var5, var7));
      }

   }

   public void processPacket(INetHandler var1) {
      this.func_180754_a((INetHandlerPlayClient)var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149445_a);
      var1.writeInt(this.field_149444_b.size());
      Iterator var2 = this.field_149444_b.iterator();

      while(var2.hasNext()) {
         S20PacketEntityProperties.Snapshot var3 = (S20PacketEntityProperties.Snapshot)var2.next();
         var1.writeString(var3.func_151409_a());
         var1.writeDouble(var3.func_151410_b());
         var1.writeVarIntToBuffer(var3.func_151408_c().size());
         Iterator var4 = var3.func_151408_c().iterator();

         while(var4.hasNext()) {
            AttributeModifier var5 = (AttributeModifier)var4.next();
            var1.writeUuid(var5.getID());
            var1.writeDouble(var5.getAmount());
            var1.writeByte(var5.getOperation());
         }
      }

   }

   public S20PacketEntityProperties() {
   }

   public class Snapshot {
      private final String field_151412_b;
      final S20PacketEntityProperties this$0;
      private final double field_151413_c;
      private static final String __OBFID = "CL_00001342";
      private final Collection field_151411_d;

      public Snapshot(S20PacketEntityProperties var1, String var2, double var3, Collection var5) {
         this.this$0 = var1;
         this.field_151412_b = var2;
         this.field_151413_c = var3;
         this.field_151411_d = var5;
      }

      public String func_151409_a() {
         return this.field_151412_b;
      }

      public double func_151410_b() {
         return this.field_151413_c;
      }

      public Collection func_151408_c() {
         return this.field_151411_d;
      }
   }
}
