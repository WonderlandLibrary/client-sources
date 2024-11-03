package xyz.cucumber.base.utils.math;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class PositionHandler {
   public static Minecraft mc = Minecraft.getMinecraft();

   public static double[] getScaledPosition(double positionX, double positionY) {
      ScaledResolution sr = new ScaledResolution(mc);
      double displayWidth = (double)sr.getScaledWidth();
      double displayHeight = sr.getScaledHeight_double();
      return new double[]{displayWidth / 1000.0 * positionX, displayHeight / 1000.0 * positionY};
   }

   public static double[] getPositionFromValue(double valueX, double valueY) {
      ScaledResolution sr = new ScaledResolution(mc);
      double displayWidth = (double)sr.getScaledWidth();
      double displayHeight = sr.getScaledHeight_double();
      return new double[]{valueX * 1000.0 / displayWidth, valueY * 1000.0 / displayHeight};
   }
}
