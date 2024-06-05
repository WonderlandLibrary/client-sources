package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.DoubleTag;
import java.util.concurrent.ThreadLocalRandom;
import net.aWT;
import net.cN;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;

class sf implements PacketHandler {
   final aWT a;

   sf(aWT var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      PacketWrapper var2 = var1.create(8);
      var2.write(Type.UNSIGNED_BYTE, Short.valueOf((short)((cN)var1.user().b(cN.class)).c()));
      var2.write(Type.SHORT, Short.valueOf((short)-999));
      var2.write(Type.BYTE, Byte.valueOf((byte)2));
      var2.write(Type.SHORT, Short.valueOf((short)ThreadLocalRandom.current().nextInt()));
      var2.write(Type.VAR_INT, Integer.valueOf(5));
      CompoundTag var3 = new CompoundTag("");
      var3.put(new DoubleTag("force_resync", Double.NaN));
      var2.write(Type.FLAT_VAR_INT_ITEM, new Item(1, (byte)1, (short)0, var3));
      var2.sendToServer(Protocol1_14To1_13_2.class, true, false);
   }
}
