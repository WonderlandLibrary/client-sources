package net.shadersmod.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.optifine.Config;
import net.optifine.GuiScreenOF;
import net.optifine.Lang;
import net.optifine.StrUtils;
import net.shadersmod.client.GuiButtonShaderOption;
import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.ShaderOptionProfile;
import net.shadersmod.client.ShaderOptionScreen;
import net.shadersmod.client.Shaders;

public class GuiShaderOptions extends GuiScreenOF {
   private GuiScreen prevScreen;
   protected String title;
   private GameSettings settings;
   private int lastMouseX;
   private int lastMouseY;
   private long mouseStillTime;
   private String screenName;
   private String screenText;
   private boolean changed;
   public static final String A = "<profile>";
   public static final String F = "<empty>";
   public static final String OPTION_REST = "*";

   public GuiShaderOptions(GuiScreen var1, GameSettings var2) {
      this.lastMouseX = 0;
      this.lastMouseY = 0;
      this.mouseStillTime = 0L;
      this.screenName = null;
      this.screenText = null;
      this.changed = false;
      this.title = "Shader Options";
      this.prevScreen = var1;
      this.settings = var2;
   }

   public GuiShaderOptions(GuiScreen var1, GameSettings var2, String var3) {
      ShaderOption.p();
      this(var1, var2);
      this.screenName = var3;
      this.screenText = Shaders.translate("screen." + var3, var3);
   }

   public void initGui() {
      ShaderOption.p();
      this.title = I18n.format("of.options.shaderOptionsTitle", new Object[0]);
      byte var2 = 100;
      int var3 = 0;
      byte var4 = 30;
      byte var5 = 20;
      int var6 = this.width - 130;
      byte var7 = 120;
      byte var8 = 20;
      int var9 = 2;
      ShaderOption[] var10 = Shaders.getShaderPackOptions(this.screenName);
      if(var10.length > 18) {
         var9 = var10.length / 9 + 1;
      }

      int var11 = 0;
      if(var11 < var10.length) {
         ShaderOption var12 = var10[var11];
         if(var12.isVisible()) {
            int var13 = var11 % var9;
            int var14 = var11 / var9;
            int var15 = Math.min(this.width / var9, 200);
            var3 = (this.width - var15 * var9) / 2;
            int var16 = var13 * var15 + 5 + var3;
            int var17 = var4 + var14 * var5;
            int var18 = var15 - 10;
            String var19 = this.getButtonText(var12, var18);
            GuiButtonShaderOption var20 = new GuiButtonShaderOption(var2 + var11, var16, var17, var18, var8, var12, var19);
            var20.enabled = var12.isEnabled();
            this.buttonList.add(var20);
         }

         ++var11;
      }

      this.buttonList.add(new GuiButton(201, this.width / 2 - var7 - 20, this.height / 6 + 168 + 11, var7, var8, I18n.format("controls.reset", new Object[0])));
      this.buttonList.add(new GuiButton(200, this.width / 2 + 20, this.height / 6 + 168 + 11, var7, var8, I18n.format("gui.done", new Object[0])));
   }

   private String getButtonText(ShaderOption var1, int var2) {
      ShaderOption.p();
      String var4 = var1.getNameText();
      if(var1 instanceof ShaderOptionScreen) {
         ShaderOptionScreen var8 = (ShaderOptionScreen)var1;
         return var4 + "...";
      } else {
         FontRenderer var5 = Config.getMinecraft().fontRendererObj;
         int var6 = var5.d(": " + Lang.getOff()) + 5;
         if(var5.d(var4) + var6 >= var2 && !var4.isEmpty()) {
            var4 = var4.substring(0, var4.length() - 1);
         }

         String var9 = var1.isChanged()?var1.getValueColor(var1.getValue()):"";
         String var7 = var1.getValueText(var1.getValue());
         return var4 + ": " + var9 + var7;
      }
   }

   protected void actionPerformed(GuiButton var1) {
      String[] var2 = ShaderOption.p();
      if(var1.enabled) {
         if(var1.id < 200 && var1 instanceof GuiButtonShaderOption) {
            GuiButtonShaderOption var3 = (GuiButtonShaderOption)var1;
            ShaderOption var4 = var3.getShaderOption();
            if(var4 instanceof ShaderOptionScreen) {
               String var9 = var4.getName();
               GuiShaderOptions var11 = new GuiShaderOptions(this, this.settings, var9);
               this.mc.displayGuiScreen(var11);
               return;
            }

            if(isShiftKeyDown()) {
               var4.resetValue();
            }

            var4.nextValue();
            this.updateAllButtons();
            this.changed = true;
         }

         if(var1.id == 201) {
            ShaderOption[] var8 = Shaders.getChangedOptions(Shaders.getShaderPackOptions());
            int var5 = var8.length;
            int var6 = 0;
            if(var6 < var5) {
               ShaderOption var7 = var8[var6];
               var7.resetValue();
               this.changed = true;
               ++var6;
            }

            this.updateAllButtons();
         }

         if(var1.id == 200) {
            if(this.changed) {
               Shaders.saveShaderPackOptions();
               this.changed = false;
               Shaders.uninit();
            }

            this.mc.displayGuiScreen(this.prevScreen);
         }
      }

   }

   protected void actionPerformedRightClick(GuiButton var1) {
      String[] var2 = ShaderOption.p();
      if(var1 instanceof GuiButtonShaderOption) {
         GuiButtonShaderOption var3 = (GuiButtonShaderOption)var1;
         ShaderOption var4 = var3.getShaderOption();
         if(isShiftKeyDown()) {
            var4.resetValue();
         }

         var4.prevValue();
         this.updateAllButtons();
         this.changed = true;
      }

   }

   public void onGuiClosed() {
      ShaderOption.p();
      super.onGuiClosed();
      if(this.changed) {
         Shaders.saveShaderPackOptions();
         this.changed = false;
         Shaders.uninit();
      }

   }

   private void updateAllButtons() {
      ShaderOption.p();
      Iterator var2 = this.buttonList.iterator();
      if(var2.hasNext()) {
         GuiButton var3 = (GuiButton)var2.next();
         if(var3 instanceof GuiButtonShaderOption) {
            GuiButtonShaderOption var4 = (GuiButtonShaderOption)var3;
            ShaderOption var5 = var4.getShaderOption();
            if(var5 instanceof ShaderOptionProfile) {
               ShaderOptionProfile var6 = (ShaderOptionProfile)var5;
               var6.updateProfile();
            }

            var4.displayString = this.getButtonText(var5, var4.getButtonWidth());
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      ShaderOption.p();
      this.drawDefaultBackground();
      if(this.screenText != null) {
         this.drawCenteredString(this.fontRendererObj, this.screenText, this.width / 2, 15, 16777215);
      }

      this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
      super.drawScreen(var1, var2, var3);
      if(Math.abs(var1 - this.lastMouseX) <= 5 && Math.abs(var2 - this.lastMouseY) <= 5) {
         this.a(var1, var2, this.buttonList);
      }

      this.lastMouseX = var1;
      this.lastMouseY = var2;
      this.mouseStillTime = System.currentTimeMillis();
   }

   private void a(int var1, int var2, List var3) {
      ShaderOption.p();
      short var5 = 700;
      if(System.currentTimeMillis() >= this.mouseStillTime + (long)var5) {
         int var6 = this.width / 2 - 150;
         int var7 = this.height / 6 - 7;
         if(var2 <= var7 + 98) {
            var7 += 105;
         }

         int var8 = var6 + 150 + 150;
         int var9 = var7 + 84 + 10;
         GuiButton var10 = getSelectedButton(var3, var1, var2);
         if(var10 instanceof GuiButtonShaderOption) {
            GuiButtonShaderOption var11 = (GuiButtonShaderOption)var10;
            ShaderOption var12 = var11.getShaderOption();
            String[] var13 = this.makeTooltipLines(var12, var8 - var6);
            return;
         }
      }

   }

   private String[] makeTooltipLines(ShaderOption var1, int var2) {
      String[] var3 = ShaderOption.p();
      if(var1 instanceof ShaderOptionProfile) {
         return null;
      } else {
         String var4 = var1.getNameText();
         String var5 = Config.c(var1.getDescriptionText()).trim();
         String[] var6 = this.splitDescription(var5);
         String var7 = null;
         if(!var4.equals(var1.getName()) && this.settings.advancedItemTooltips) {
            var7 = "§8" + Lang.get("of.general.id") + ": " + var1.getName();
         }

         String var8 = null;
         if(var1.getPaths() != null && this.settings.advancedItemTooltips) {
            var8 = "§8" + Lang.get("of.general.from") + ": " + Config.a((Object[])var1.getPaths());
         }

         String var9 = null;
         if(var1.getValueDefault() != null && this.settings.advancedItemTooltips) {
            String var10 = var1.isEnabled()?var1.getValueText(var1.getValueDefault()):Lang.get("of.general.ambiguous");
            var9 = "§8" + Lang.getDefault() + ": " + var10;
         }

         ArrayList var12 = new ArrayList();
         var12.add(var4);
         var12.addAll(Arrays.asList(var6));
         if(var7 != null) {
            var12.add(var7);
         }

         if(var8 != null) {
            var12.add(var8);
         }

         if(var9 != null) {
            var12.add(var9);
         }

         String[] var11 = this.makeTooltipLines(var2, var12);
         return var11;
      }
   }

   private String[] splitDescription(String var1) {
      String[] var2 = ShaderOption.p();
      if(var1.length() <= 0) {
         return new String[0];
      } else {
         var1 = StrUtils.removePrefix(var1, "//");
         String[] var3 = var1.split("\\. ");
         int var4 = 0;
         if(var4 < var3.length) {
            var3[var4] = "- " + var3[var4].trim();
            var3[var4] = StrUtils.removeSuffix(var3[var4], ".");
            ++var4;
         }

         return var3;
      }
   }

   private String[] makeTooltipLines(int var1, List var2) {
      FontRenderer var4 = Config.getMinecraft().fontRendererObj;
      ArrayList var5 = new ArrayList();
      ShaderOption.p();
      Iterator var6 = var2.iterator();
      if(var6.hasNext()) {
         String var7 = (String)var6.next();
         if(var7 != null && !var7.isEmpty()) {
            Iterator var9 = var4.listFormattedStringToWidth(var7, var1).iterator();
            if(var9.hasNext()) {
               Object var10 = var9.next();
               var5.add((String)var10);
            }
         }
      }

      String[] var11 = (String[])var5.toArray(new String[var5.size()]);
      return var11;
   }
}
