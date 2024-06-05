package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider$BackwardsBlockEntityHandler;
import viaversion.viaversion.api.data.UserConnection;

public class bgT {
   public static CompoundTag a(BackwardsBlockEntityProvider$BackwardsBlockEntityHandler var0, UserConnection var1, int var2, CompoundTag var3) {
      return var0.transform(var1, var2, var3);
   }
}
