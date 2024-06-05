package net;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.world.World;
import net.optifine.ClearWater;

public class ib {
   public static void a(GameSettings var0, World var1) {
      ClearWater.updateWaterOpacity(var0, var1);
   }
}
