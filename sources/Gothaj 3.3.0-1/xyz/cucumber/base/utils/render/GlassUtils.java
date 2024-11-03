package xyz.cucumber.base.utils.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import xyz.cucumber.base.utils.render.shaders.Shaders;

public class GlassUtils {
   private Minecraft mc = Minecraft.getMinecraft();
   private Framebuffer framebuffer = new Framebuffer(1, 1, true);
   private ScaledResolution sr = new ScaledResolution(this.mc);
   private int programId = Shaders.glassEffect.getProgramID();
   private float[] dir = new float[]{1.0F, 0.0F};
   private float offset = 3.0F;

   public void pre() {
   }

   public void post() {
   }

   public void reset() {
   }
}
