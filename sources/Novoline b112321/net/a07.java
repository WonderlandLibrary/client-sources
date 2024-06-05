package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;
import net.D4;
import net.bgR;
import net.p9;

public class a07 implements p9 {
   public CompoundTag a(bgR var1, int var2, CompoundTag var3) {
      FlowerPotHandler.b();
      Tag var5 = var3.get("SpawnData");
      if(var5 instanceof CompoundTag) {
         CompoundTag var6 = (CompoundTag)var5;
         Tag var7 = var6.get("id");
         if(var7 instanceof StringTag) {
            StringTag var8 = (StringTag)var7;
            var8.setValue(D4.a(var8.getValue()));
         }
      }

      return var3;
   }
}
