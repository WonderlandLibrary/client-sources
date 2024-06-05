package cc.novoline.gui.screen.alt.repository.hypixel;

import cc.novoline.gui.screen.alt.repository.hypixel.HypixelProfile;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class HypixelProfileFactory {
   private HypixelProfileFactory() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   @NotNull
   public static HypixelProfile hypixelProfile(@NotNull String var0, int var1) {
      return HypixelProfile.of(var0, var1, (String)null, (Integer)null, (Integer)null, (Integer)null, (Long)null, (Long)null, Long.valueOf(System.currentTimeMillis()));
   }

   @NotNull
   public static HypixelProfile hypixelProfile(@Nullable String var0, int var1, @Nullable String var2, @Nullable Integer var3, @Nullable Integer var4, @Nullable Integer var5, @Nullable Long var6, @Nullable Long var7, @Nullable Long var8) {
      return HypixelProfile.of(var0, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Nullable
   public static HypixelProfile fromNBT(@Nullable NBTTagCompound var0) {
      String var1 = HypixelProfile.i();
      if(var0 == null) {
         return null;
      } else {
         String var2 = var0.getString("rank", "Default");
         int var3 = var0.getInteger("level", Integer.valueOf(1)).intValue();
         String var4 = var0.getString("rank", (String)null);
         int var5 = var0.getInteger("ap", Integer.valueOf(0)).intValue();
         int var6 = var0.getInteger("quests", Integer.valueOf(0)).intValue();
         int var7 = var0.getInteger("friends", Integer.valueOf(0)).intValue();
         Long var8 = var0.getLong("first_login", (Long)null);
         Long var9 = var0.getLong("last_login", (Long)null);
         Long var10 = var0.getLong("cached", (Long)null);
         return HypixelProfile.of(var2, var3, var4, Integer.valueOf(var5), Integer.valueOf(var6), Integer.valueOf(var7), var8, var9, var10);
      }
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
