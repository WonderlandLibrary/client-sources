package cc.novoline.utils;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.click.DiscordGUI;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class ScaleUtils {
   public static int[] getScaledMouseCoordinates(Minecraft var0, int var1, int var2) {
      Timer.e();
      int var4 = var1;
      int var5 = var2;
      switch(var0.gameSettings.guiScale) {
      case 0:
         var4 = var1 * 2;
         var5 = var2 * 2;
      case 1:
         var4 = (int)((double)var4 * 0.5D);
         var5 = (int)((double)var5 * 0.5D);
      case 3:
         var4 = (int)((double)var4 * 1.5D);
         var5 = (int)((double)var5 * 1.5D);
      case 2:
      default:
         return new int[]{var4, var5};
      }
   }

   public static int[] b(Minecraft var0, int var1, int var2) {
      Timer.e();
      int var4 = var1;
      int var5 = var2;
      double var6 = 1.0D / ((ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class)).s();
      switch(var0.gameSettings.guiScale) {
      case 0:
         var4 = (int)((double)var1 * 2.0D * var6);
         var5 = (int)((double)var2 * 2.0D * var6);
      case 1:
         var4 = (int)((double)var4 * 0.5D * var6);
         var5 = (int)((double)var5 * 0.5D * var6);
      case 3:
         var4 = (int)((double)var4 * 1.5D * var6);
         var5 = (int)((double)var5 * 1.5D * var6);
      case 2:
      default:
         var4 = (int)((double)var4 * var6);
         var5 = (int)((double)var5 * var6);
         return new int[]{var4, var5};
      }
   }

   public static double[] a(Minecraft var0, double var1, double var3) {
      Timer.e();
      double var6 = var1;
      double var8 = var3;
      switch(var0.gameSettings.guiScale) {
      case 0:
         var6 = var1 * 2.0D;
         var8 = var3 * 2.0D;
      case 1:
         var6 *= 0.5D;
         var8 *= 0.5D;
      case 3:
         var6 *= 1.5D;
         var8 *= 1.5D;
      case 2:
      default:
         return new double[]{var6, var8};
      }
   }

   public static void scale(Minecraft var0) {
      String[] var1 = Timer.e();
      if(!(var0.currentScreen instanceof DiscordGUI)) {
         switch(var0.gameSettings.guiScale) {
         case 0:
            GlStateManager.scale(0.5D, 0.5D, 0.5D);
         case 1:
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
         case 3:
            GlStateManager.scale(0.6666666666666667D, 0.6666666666666667D, 0.6666666666666667D);
         case 2:
         }
      }

   }
}
