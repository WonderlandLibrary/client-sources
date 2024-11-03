package dev.star.module.impl.render;

import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.NumberSetting;

public class Camera extends Module {
   // I don't know what to call it
   public static final BooleanSetting fov = new BooleanSetting("fov", true);
   public static final BooleanSetting clip = new BooleanSetting("Clip", false);
   public static final BooleanSetting animation = new BooleanSetting("Animation", true);
   public static final BooleanSetting motion = new BooleanSetting("Motion", false);
   public static final NumberSetting interpolation = new NumberSetting("MotionInterpolation", 0.15f, 0.5f, 0.05f, 0.05f);
   public static final BooleanSetting transform = new BooleanSetting("Transform", false);
   public static final NumberSetting x = new NumberSetting("TransformX", 0f, 5f, -5f, 0.1f);
   public static final NumberSetting y = new NumberSetting("TransformY", 0f, 5f, -5f, 0.1f);
   public static final NumberSetting z = new NumberSetting("TransformZ", 0f, 5f, -5f, 0.1f);

   public Camera(){
      super("Camera", Category.RENDER,"Removes camera view collision.");
      this.addSettings(fov, clip, animation, motion, interpolation, transform, x, y);
      motion.toggleAction = () -> {
         if (motion.isEnabled()) {
            mc.entityRenderer.prevRenderX = mc.getRenderViewEntity().posX;
            mc.entityRenderer.prevRenderY = mc.getRenderViewEntity().posY + mc.getRenderViewEntity().getEyeHeight();
            mc.entityRenderer.prevRenderZ = mc.getRenderViewEntity().posZ;
         }
      };
   }
}
