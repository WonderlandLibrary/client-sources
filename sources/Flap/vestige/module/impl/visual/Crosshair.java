package vestige.module.impl.visual;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import vestige.Flap;
import vestige.module.AlignType;
import vestige.module.Category;
import vestige.module.HUDModule;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.render.RenderUtils2;

public class Crosshair extends HUDModule {
   public final ModeSetting color = new ModeSetting("Color", "White", new String[]{"White", "Black", "Red", "Green", "Blue", "Yellow", "Purple", "Theme"});
   private final DoubleSetting offset = new DoubleSetting("Offset", 0.0D, 0.0D, 30.0D, 0.25D);
   private final DoubleSetting height = new DoubleSetting("Height", 3.75D, 0.0D, 50.0D, 0.25D);
   private final DoubleSetting width = new DoubleSetting("Width", 3.75D, 0.0D, 50.0D, 0.25D);
   private final DoubleSetting thickness = new DoubleSetting("Thickness", 0.5D, 0.0D, 10.0D, 0.25D);
   private final BooleanSetting outline = new BooleanSetting("Outline", true);
   private final DoubleSetting outlineThickness = new DoubleSetting("Outline Thickness", 0.5D, 0.0D, 5.0D, 0.25D);
   private boolean initialised;
   private final Minecraft mc = Minecraft.getMinecraft();
   private ClientTheme theme;

   public Crosshair() {
      super("Crosshair", Category.VISUAL, 0.0D, 0.0D, 0, 0, AlignType.LEFT);
      this.addSettings(new AbstractSetting[]{this.color, this.offset, this.height, this.width, this.thickness, this.outline, this.outlineThickness});
   }

   private void initialise() {
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
   }

   protected void renderModule(boolean inChat) {
      if (!this.initialised) {
         this.initialise();
         this.initialised = true;
      }

      if (!this.mc.gameSettings.showDebugInfo) {
         this.renderCrosshair();
      }
   }

   private void renderCrosshair() {
      float red = 1.0F;
      float green = 1.0F;
      float blue = 1.0F;
      String var4 = this.color.getMode();
      byte var5 = -1;
      switch(var4.hashCode()) {
      case -1893076004:
         if (var4.equals("Purple")) {
            var5 = 5;
         }
         break;
      case -1650372460:
         if (var4.equals("Yellow")) {
            var5 = 4;
         }
         break;
      case 82033:
         if (var4.equals("Red")) {
            var5 = 1;
         }
         break;
      case 2073722:
         if (var4.equals("Blue")) {
            var5 = 3;
         }
         break;
      case 64266207:
         if (var4.equals("Black")) {
            var5 = 0;
         }
         break;
      case 69066467:
         if (var4.equals("Green")) {
            var5 = 2;
         }
         break;
      case 80774569:
         if (var4.equals("Theme")) {
            var5 = 6;
         }
      }

      switch(var5) {
      case 0:
         blue = 0.0F;
         green = 0.0F;
         red = 0.0F;
         break;
      case 1:
         red = 1.0F;
         blue = 0.0F;
         green = 0.0F;
         break;
      case 2:
         green = 1.0F;
         blue = 0.0F;
         red = 0.0F;
         break;
      case 3:
         blue = 1.0F;
         green = 0.0F;
         red = 0.0F;
         break;
      case 4:
         green = 1.0F;
         red = 1.0F;
         blue = 0.0F;
         break;
      case 5:
         red = 150.0F;
         blue = 1.0F;
         green = 0.0F;
      case 6:
      }

      int colorInt = (int)(red * 255.0F) << 16 | (int)(green * 255.0F) << 8 | (int)(blue * 255.0F) | -16777216;
      int outlineColor = Color.BLACK.getRGB();
      float xOffset = (float)this.offset.getValue();
      float yOffset = (float)this.offset.getValue();
      float lineHeight = (float)this.height.getValue();
      float lineWidth = (float)this.width.getValue();
      float lineThickness = (float)this.thickness.getValue();
      float outlineThicknessValue = (float)this.outlineThickness.getValue();
      ScaledResolution scaledRes = new ScaledResolution(this.mc);
      int screenWidth = scaledRes.getScaledWidth();
      int screenHeight = scaledRes.getScaledHeight();
      float centerX = (float)screenWidth / 2.0F;
      float centerY = (float)screenHeight / 2.0F;
      if (this.color.getMode() == "Theme") {
         this.drawRectWithOutline(centerX - lineThickness / 2.0F, centerY - yOffset - lineHeight, centerX + lineThickness / 2.0F, centerY - yOffset, this.theme.getColor(1), this.outline.isEnabled(), outlineThicknessValue, outlineColor);
         this.drawRectWithOutline(centerX - lineThickness / 2.0F, centerY + yOffset, centerX + lineThickness / 2.0F, centerY + yOffset + lineHeight, this.theme.getColor(1), this.outline.isEnabled(), outlineThicknessValue, outlineColor);
         this.drawRectWithOutline(centerX - xOffset - lineWidth, centerY - lineThickness / 2.0F, centerX - xOffset, centerY + lineThickness / 2.0F, this.theme.getColor(1), this.outline.isEnabled(), outlineThicknessValue, outlineColor);
         this.drawRectWithOutline(centerX + xOffset, centerY - lineThickness / 2.0F, centerX + xOffset + lineWidth, centerY + lineThickness / 2.0F, this.theme.getColor(1), this.outline.isEnabled(), outlineThicknessValue, outlineColor);
      } else {
         this.drawRectWithOutline(centerX - lineThickness / 2.0F, centerY - yOffset - lineHeight, centerX + lineThickness / 2.0F, centerY - yOffset, colorInt, this.outline.isEnabled(), outlineThicknessValue, outlineColor);
         this.drawRectWithOutline(centerX - lineThickness / 2.0F, centerY + yOffset, centerX + lineThickness / 2.0F, centerY + yOffset + lineHeight, colorInt, this.outline.isEnabled(), outlineThicknessValue, outlineColor);
         this.drawRectWithOutline(centerX - xOffset - lineWidth, centerY - lineThickness / 2.0F, centerX - xOffset, centerY + lineThickness / 2.0F, colorInt, this.outline.isEnabled(), outlineThicknessValue, outlineColor);
         this.drawRectWithOutline(centerX + xOffset, centerY - lineThickness / 2.0F, centerX + xOffset + lineWidth, centerY + lineThickness / 2.0F, colorInt, this.outline.isEnabled(), outlineThicknessValue, outlineColor);
      }

   }

   private void drawRectWithOutline(float left, float top, float right, float bottom, int color, boolean hasOutline, float outlineThicknessValue, int outlineColor) {
      if (hasOutline) {
         RenderUtils2.drawRect((double)(left - outlineThicknessValue), (double)(top - outlineThicknessValue), (double)(right + outlineThicknessValue), (double)(bottom + outlineThicknessValue), outlineColor);
      }

      RenderUtils2.drawRect((double)left, (double)top, (double)right, (double)bottom, color);
   }
}
