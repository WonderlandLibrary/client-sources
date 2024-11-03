package xyz.cucumber.base.utils.render.shaders;

public class Kernel {
   public static float calculateGaussianValue(float x, float sigma) {
      double output = 1.0 / Math.sqrt((Math.PI * 2) * (double)(sigma * sigma));
      return (float)(output * Math.exp((double)(-(x * x)) / (2.0 * (double)(sigma * sigma))));
   }
}
