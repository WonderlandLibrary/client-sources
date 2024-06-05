package net.minecraft.network.play.server;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

public class S37PacketStatistics implements Packet {
   private static final String __OBFID = "CL_00001283";
   private Map field_148976_a;

   public S37PacketStatistics(Map var1) {
      this.field_148976_a = var1;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleStatistics(this);
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public Map func_148974_c() {
      return this.field_148976_a;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_148976_a.size());
      Iterator var2 = this.field_148976_a.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         var1.writeString(((StatBase)var3.getKey()).statId);
         var1.writeVarIntToBuffer((Integer)var3.getValue());
      }

   }

   public S37PacketStatistics() {
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      int var2 = var1.readVarIntFromBuffer();
      this.field_148976_a = Maps.newHashMap();

      for(int var3 = 0; var3 < var2; ++var3) {
         StatBase var4 = StatList.getOneShotStat(var1.readStringFromBuffer(32767));
         int var5 = var1.readVarIntFromBuffer();
         if (var4 != null) {
            this.field_148976_a.put(var4, var5);
         }
      }

   }
}
