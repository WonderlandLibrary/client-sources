package xyz.cucumber.base.module.feat.visuals;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventRenderItem;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Changes swing animation",
   name = "Animations",
   priority = ArrayPriority.LOW
)
public class AnimationsModule extends Mod {
   public ModeSettings mode = new ModeSettings(
      "Mode", new String[]{"Vanilla", "Sigma", "Stab", "Fan", "Sigma 2", "Old", "Exhibition", "Gothaj", "Swong", "Chill", "Basic", "Fast", "Fast 2"}
   );
   public static NumberSettings swingSpeed = new NumberSettings("Swing Speed", 4.0, 6.0, 25.0, 1.0);
   public BooleanSettings skidSwing = new BooleanSettings("Skid Swing", false);
   public BooleanSettings onlySword = new BooleanSettings("Only Sword", true);
   public NumberSettings x = new NumberSettings("X", 0.0, -2.0, 2.0, 0.1F);
   public NumberSettings y = new NumberSettings("Y", 0.0, -2.0, 2.0, 0.1F);
   public NumberSettings z = new NumberSettings("Z", 0.0, -2.0, 2.0, 0.1F);

   public AnimationsModule() {
      this.addSettings(new ModuleSettings[]{this.mode, swingSpeed, this.onlySword, this.skidSwing, this.x, this.y, this.z});
   }

   @EventListener
   public void onTick(EventTick e) {
      this.setInfo(this.mode.getMode());
   }

   @EventListener
   public void onRender(EventRenderItem e) {
      if (e.getType() == EventType.PRE) {
         ItemRenderer itemRenderer = this.mc.getItemRenderer();
         float animationProgression = e.getF();
         float swingProgress = e.getF1();
         float convertedProgress = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
         GlStateManager.translate(this.x.getValue(), this.y.getValue(), this.z.getValue());
         String var6;
         switch ((var6 = this.mode.getMode().toLowerCase()).hashCode()) {
            case -1281673874:
               if (var6.equals("fast 2")) {
                  GlStateManager.translate(0.41F, -0.25F, -0.5555557F);
                  GlStateManager.translate(0.0F, 0.0F, 0.0F);
                  GlStateManager.rotate(35.0F, 0.0F, 1.5F, 0.0F);
                  float racism = MathHelper.sin(swingProgress * swingProgress / 64.0F * (float) Math.PI);
                  GlStateManager.rotate(racism * -5.0F, 0.0F, 0.0F, 0.0F);
                  GlStateManager.rotate(convertedProgress * -12.0F, 0.0F, 0.0F, 1.0F);
                  GlStateManager.rotate(convertedProgress * -65.0F, 1.0F, 0.0F, 0.0F);
                  GlStateManager.scale(0.3F, 0.3F, 0.3F);
                  itemRenderer.doBlockTransformations();
               }
               break;
            case -1240095099:
               if (var6.equals("gothaj")) {
                  itemRenderer.transformFirstPersonItem(animationProgression / 2.0F, 0.0F);
                  GlStateManager.translate(0.0F, 0.3F, -0.0F);
                  GlStateManager.rotate(-convertedProgress * 30.0F, 1.0F, 0.0F, 2.0F);
                  GlStateManager.rotate(-convertedProgress * 44.0F, 1.5F, convertedProgress / 1.2F, 0.0F);
                  itemRenderer.doBlockTransformations();
               }
               break;
            case 101139:
               if (var6.equals("fan")) {
                  itemRenderer.transformFirstPersonItem(animationProgression, 0.0F);
                  GlStateManager.translate(0.0F, 0.2F, -1.0F);
                  GlStateManager.rotate(-59.0F, -1.0F, 0.0F, 3.0F);
                  GlStateManager.rotate((float)(-(System.currentTimeMillis() / 2L % 360L)), 1.0F, 0.0F, 0.0F);
                  GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
               }
               break;
            case 110119:
               if (var6.equals("old")) {
                  GlStateManager.translate(0.0F, 0.18F, 0.0F);
                  itemRenderer.transformFirstPersonItem(animationProgression / 2.0F, swingProgress);
                  itemRenderer.doBlockTransformations();
               }
               break;
            case 3135580:
               if (var6.equals("fast")) {
                  itemRenderer.transformFirstPersonItem(animationProgression, swingProgress);
                  itemRenderer.doBlockTransformations();
                  GlStateManager.translate(-0.3F, -0.1F, -0.0F);
               }
               break;
            case 3540546:
               if (var6.equals("stab")) {
                  float spin = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
                  GlStateManager.translate(0.6F, 0.3F, -0.6F + (double)(-spin) * 0.7);
                  GlStateManager.rotate(6090.0F, 0.0F, 0.0F, 0.1F);
                  GlStateManager.rotate(6085.0F, 0.0F, 0.1F, 0.0F);
                  GlStateManager.rotate(6110.0F, 0.1F, 0.0F, 0.0F);
                  itemRenderer.transformFirstPersonItem(0.0F, 0.0F);
                  itemRenderer.doBlockTransformations();
               }
               break;
            case 93508654:
               if (var6.equals("basic")) {
                  itemRenderer.transformFirstPersonItem(-0.25F, 1.0F + convertedProgress / 10.0F);
                  GL11.glRotated((double)(-convertedProgress * 25.0F), 1.0, 0.0, 0.0);
                  itemRenderer.doBlockTransformations();
               }
               break;
            case 94631204:
               if (var6.equals("chill")) {
                  itemRenderer.transformFirstPersonItem(animationProgression / 1.5F, 0.0F);
                  itemRenderer.doBlockTransformations();
                  GlStateManager.translate(-0.05F, 0.3F, 0.3F);
                  GlStateManager.rotate(-convertedProgress * 140.0F, 8.0F, 0.0F, 8.0F);
                  GlStateManager.rotate(convertedProgress * 90.0F, 8.0F, 0.0F, 8.0F);
               }
               break;
            case 109435429:
               if (var6.equals("sigma")) {
                  itemRenderer.transformFirstPersonItem(animationProgression, 0.0F);
                  float y = -convertedProgress * 2.0F;
                  GlStateManager.translate(0.0F, y / 10.0F + 0.1F, 0.0F);
                  GlStateManager.rotate(y * 10.0F, 0.0F, 1.0F, 0.0F);
                  GlStateManager.rotate(250.0F, 0.2F, 1.0F, -0.6F);
                  GlStateManager.rotate(-10.0F, 1.0F, 0.5F, 1.0F);
                  GlStateManager.rotate(-y * 20.0F, 1.0F, 0.5F, 1.0F);
               }
               break;
            case 109860228:
               if (var6.equals("swong")) {
                  itemRenderer.transformFirstPersonItem(animationProgression / 2.0F, swingProgress);
                  GlStateManager.rotate(convertedProgress * 30.0F / 2.0F, -convertedProgress, -0.0F, 9.0F);
                  GlStateManager.rotate(convertedProgress * 40.0F, 1.0F, -convertedProgress / 2.0F, -0.0F);
                  GlStateManager.translate(0.0F, 0.2F, 0.0F);
                  itemRenderer.doBlockTransformations();
               }
               break;
            case 233102203:
               if (var6.equals("vanilla")) {
                  itemRenderer.transformFirstPersonItem(animationProgression, swingProgress);
                  itemRenderer.doBlockTransformations();
               }
               break;
            case 1949242831:
               if (var6.equals("exhibition")) {
                  itemRenderer.transformFirstPersonItem(animationProgression / 2.0F, 0.0F);
                  GlStateManager.translate(0.0F, 0.3F, -0.0F);
                  GlStateManager.rotate(-convertedProgress * 31.0F, 1.0F, 0.0F, 2.0F);
                  GlStateManager.rotate(-convertedProgress * 33.0F, 1.5F, convertedProgress / 1.1F, 0.0F);
                  itemRenderer.doBlockTransformations();
               }
               break;
            case 2088233207:
               if (var6.equals("sigma 2")) {
                  itemRenderer.transformFirstPersonItem(animationProgression, 0.0F);
                  GlStateManager.scale(0.8F, 0.8F, 0.8F);
                  GlStateManager.translate(0.0F, 0.1F, 0.0F);
                  itemRenderer.doBlockTransformations();
                  GlStateManager.rotate(convertedProgress * 35.0F / 2.0F, 0.0F, 1.0F, 1.5F);
                  GlStateManager.rotate(-convertedProgress * 135.0F / 4.0F, 1.0F, 1.0F, 0.0F);
               }
         }
      }
   }
}
