package net.augustus.modules.render;

import java.awt.Color;
import net.augustus.events.EventChangeGuiScale;
import net.augustus.events.EventClickSetting;
import net.augustus.font.CustomFontUtil;
import net.augustus.font.testfontbase.FontUtil;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.RenderUtil;
import net.augustus.utils.skid.lorious.BlurUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public class ArrayList extends Module {
   public ColorSetting staticColor = new ColorSetting(1453543, "CustomColor", this, Color.blue);
   public StringValue mode = new StringValue(23638, "Mode", this, "Default", new String[]{"Default", "Vega", "New"});
   public StringValue sideOption = new StringValue(3, "Side", this, "Right", new String[]{"Left", "Right"});
   public StringValue sortOption = new StringValue(2, "Sorting", this, "Length", new String[]{"Alphabet", "Length"});
   public BooleanValue randomColor = new BooleanValue(4, "RandomColor", this, true);
   public BooleanValue rainbow = new BooleanValue(11, "Rainbow", this, false);
   public DoubleValue rainbowSpeed = new DoubleValue(12, "RainbowSpeed", this, 55.0, 0.0, 1000.0, 0);
   public BooleanValue toggleSound = new BooleanValue(9, "ToggleSound", this, true);
   public BooleanValue suffix = new BooleanValue(13, "Suffix", this, true);
   public BooleanValue blur = new BooleanValue(13, "Blur", this, false);
   public BooleanValue backGround = new BooleanValue(6, "Backgound", this, true);
   public ColorSetting vegaColor1 = new ColorSetting(7, "VegaColor1", this, Color.WHITE);
   public ColorSetting vegaColor2 = new ColorSetting(7, "VegaColor2", this, Color.BLACK);
   public ColorSetting backGroundColor = new ColorSetting(7, "BackGroundColor", this, new Color(0, 0, 0, 100));
   public StringValue font = new StringValue(23, "Font", this, "Minecraft", new String[]{"Minecraft", "Verdana", "Arial", "Roboto", "Comfortaa", "Esp"});
   public DoubleValue x1 = new DoubleValue(11, "PosX", this, 0.0, 0.0, 2000.0, 2);
   public DoubleValue y1 = new DoubleValue(111, "PosY", this, 0.0, 0.0, 2000.0, 2);
   public DoubleValue x2 = new DoubleValue(1111, "PosX2", this, 0.0, 0.0, 2000.0, 2);
   public DoubleValue y2 = new DoubleValue(11111, "PosY2", this, 0.0, 0.0, 2000.0, 2);
   private CustomFontUtil customFont = new CustomFontUtil("Verdana", 16);
   private FontRenderer fontRenderer = mc.fontRendererObj;

   public ArrayList() {
      super("ArrayList", Color.CYAN, Categorys.RENDER);
      this.x1.setVisible(false);
      this.y1.setVisible(false);
      this.x2.setVisible(false);
      this.y2.setVisible(false);
   }

   @Override
   public void onEnable() {
   }

   public void renderArrayList() {
      ScaledResolution sr = new ScaledResolution(mc);
      boolean leftSide = this.sideOption.getSelected().equals("Left");
      float x = (float)this.x1.getValue();
      if (leftSide) {
         x = (float)sr.getScaledWidth();
      }
      float y = (float) this.y1.getValue() + 2.0F;
      if(sideOption.getSelected().equalsIgnoreCase(mm.hud.side.getSelected())) {
         switch (mm.hud.mode.getSelected()) {
            case "Ryu": {
               y = mc.fontRendererObj.FONT_HEIGHT + 5F;
               break;
            }
            case "Old": {
               y = 48F;
               break;
            }
            case "Other": {
               y = (float)((int)((double)((float)mc.fontRendererObj.FONT_HEIGHT * 1.75F + 4.0F + 1.0F) * 1.5));
               break;
            }
            case "Basic": {
               y = (float)(mc.fontRendererObj.FONT_HEIGHT + 1);
               break;
            }
         }
      }
      int i = 0;
      double yMargin = 1.0;
      int backGroundColor = this.backGroundColor.getColor().getRGB();
      boolean minecraftFont = this.font.getSelected().equals("Minecraft");
      int height = (int)((double)(minecraftFont ? this.fontRenderer.FONT_HEIGHT : Math.round(this.customFont.getHeight())) + yMargin * 2.0);
      int color = this.staticColor.getColor().getRGB();

      for(Module module : mm.getActiveModules()) {
         String moduleName = module.getDisplayName();
         x = leftSide ? (float)this.x1.getValue() + 1.0F : (float)sr.getScaledWidth();
         module.setModuleWidth(
            this.font.getSelected().equals("Minecraft")
               ? (float)this.fontRenderer.getStringWidth(moduleName)
               : this.customFont.getStringWidth(moduleName) - this.customFont.getStringWidth(FontRenderer.getFormatFromString(moduleName))
         );
         if (!leftSide) {
            x -= module.getModuleWidth() + 3.0F;
         }
         
         if(this.blur.getBoolean()) {
             if (this.backGround.getBoolean()) {
                 RenderUtil.drawRect(
                    (double)(x + (float)(leftSide ? -1 : 0)),
                    (double)y - yMargin,
                    (double)(x + module.getModuleWidth() + (float)(leftSide ? 1 : 3)),
                    (double)(y + (float)height) - yMargin,
                    backGroundColor
                 );
              }
        	 BlurUtil.blur(
                     (float)(x + (float)(leftSide ? -1 : 0)),
                     (float)(y - yMargin + 0.25),
                     (float)(x + module.getModuleWidth() + (float)(leftSide ? 1 : 3)),
                     (float)((y + (float)height) - yMargin + 0.25)
                    );
         }
         
         if (this.backGround.getBoolean()) {
            RenderUtil.drawRect(
               (double)(x + (float)(leftSide ? -1 : 0)),
               (double)y - yMargin,
               (double)(x + module.getModuleWidth() + (float)(leftSide ? 1 : 3)),
               (double)(y + (float)height) - yMargin,
               backGroundColor
            );
         }

         
         
         if (this.randomColor.getBoolean()) {
            color = module.getColor().getRGB();
         }

         if (minecraftFont) {
        	 this.fontRenderer.drawStringWithShadow(moduleName, x + (float)(leftSide ? 0 : 1), y + 1.0F, color);
         } else {
            String string = FontRenderer.getFormatFromString(moduleName);
            int ii = 0;
            if (string.length() >= 2) {
               ii = this.fontRenderer.getColorCode(string.charAt(1));
            }

            float red = (float)(ii >> 16 & 0xFF) / 255.0F;
            float green = (float)(ii >> 8 & 0xFF) / 255.0F;
            float blue = (float)(ii & 0xFF) / 255.0F;
            if (!string.equals("") && moduleName.contains(string)) {
               String[] s = moduleName.split(string);
               this.customFont.drawString(s[0], (double)(x + (float)(leftSide ? 0 : 1)), (double)y, color);
               this.customFont
                  .drawString(
                     s[1], (double)(x + (float)(leftSide ? 0 : 1) + this.customFont.getStringWidth(s[0])), (double)y, new Color(red, green, blue).getRGB()
                  );
            } else {
               this.customFont.drawString(moduleName, (double)(x + (float)(leftSide ? 0 : 1)), (double)y, color);
            }
         }

         ++i;
         y += (float)height;
      }
   }

   public void updateSorting() {
      mm.getActiveModules().sort(Module::compareTo);
   }

   @EventTarget
   public void onEventClickSetting(EventClickSetting eventClickSetting) {
      if (!mm.arrayList.font.getSelected().equals("Minecraft") && !this.customFont.getFontName().equalsIgnoreCase(mm.arrayList.font.getSelected())) {
         System.out.println("Selecting " + mm.arrayList.font.getSelected());
         String var2 = mm.arrayList.font.getSelected();
         switch(var2) {
            case "Verdana":
               this.customFont = FontUtil.verdana;
               break;
            case "Roboto":
               this.customFont = FontUtil.roboto;
               break;
            case "Arial":
               this.customFont = FontUtil.arial;
               break;
            case "Comfortaa":
               this.customFont = FontUtil.comfortaa;
               break;
            case "Esp":
               this.customFont = FontUtil.esp;
         }
      }

      this.updateSorting();
   }

   @EventTarget
   public void onEventChangeGuiScale(EventChangeGuiScale eventChangeGuiScale) {
      this.updateSorting();
   }

   public CustomFontUtil getCustomFont() {
      return this.customFont;
   }
}
