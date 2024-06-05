package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.VV;
import net.bgR;
import net.p9;

public class BannerHandler implements p9 {
   private static final int WALL_BANNER_START = 7110;
   private static final int WALL_BANNER_STOP = 7173;
   private static final int BANNER_START = 6854;
   private static final int BANNER_STOP = 7109;

   public CompoundTag a(bgR var1, int var2, CompoundTag var3) {
      int[] var4 = FlowerPotHandler.b();
      if(var2 >= 6854 && var2 <= 7109) {
         int var5 = var2 - 6854 >> 4;
         var3.put(new IntTag("Base", 15 - var5));
      }

      if(var2 >= 7110 && var2 <= 7173) {
         int var9 = var2 - 7110 >> 2;
         var3.put(new IntTag("Base", 15 - var9));
      }

      VV.d().a().warning("Why does this block have the banner block entity? :(" + var3);
      Tag var10 = var3.get("Patterns");
      if(var10 instanceof ListTag) {
         for(Tag var7 : (ListTag)var10) {
            if(var7 instanceof CompoundTag) {
               IntTag var8 = (IntTag)((CompoundTag)var7).get("Color");
               var8.setValue(15 - var8.getValue().intValue());
               break;
            }
         }
      }

      if(PacketRemapper.b() == null) {
         FlowerPotHandler.b(new int[5]);
      }

      return var3;
   }
}
