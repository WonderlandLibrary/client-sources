package cc.novoline.modules.visual.tabgui;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.HUD;
import cc.novoline.modules.visual.tabgui.TabSetting;
import cc.novoline.modules.visual.tabgui.TabValue$1;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_18;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public final class TabValue {
   private final TabSetting setting;
   private String value;
   private float i = 0.0F;
   private static String[] b;

   public TabValue(TabSetting var1) {
      this.setting = var1;
   }

   public TabValue(TabSetting var1, String var2) {
      this.value = var2;
      this.setting = var1;
   }

   public int c() {
      String[] var1 = f();
      if(((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).isEnabled()) {
         HUD var2 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
         return var2.getHudElements().contains("Name")?(var2.C().equals("Normal")?15:20):5;
      } else {
         return 5;
      }
   }

   public void render() {
      float var2;
      float var3;
      label0: {
         var2 = (float)(this.c() + this.setting.getValues().indexOf(this) * 12);
         var3 = var2 + 12.0F;
         f();
         double var4 = (double)(Minecraft.getInstance().getDebugFPS() / 13);
         if(this.isSelected()) {
            if(this.i >= 3.0F) {
               break label0;
            }

            this.i = (float)MathHelper.clamp_double((double)this.i + 3.0D / var4, 0.0D, 3.0D);
         }

         if(this.i > 0.0F) {
            this.i = (float)MathHelper.clamp_double((double)this.i - 3.0D / var4, 0.0D, 3.0D);
         }
      }

      switch(TabValue$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[this.setting.getSetting().getSettingType().ordinal()]) {
      case 1:
         double var6 = this.setting.getSetting().getDouble();
         double var8 = (double)((int)(var6 * 100.0D)) / 100.0D;
         if(var8 % 1.0D == 0.0D) {
            String var10 = (new DecimalFormat("0.##")).format(var8);
         }

         String var11 = var8 + "";
         Gui.drawRect((double)(109 + this.setting.getModule().getLongest() + this.setting.getLongestS()), (double)var2, (double)(119 + this.setting.getModule().getLongest() + this.setting.getLongestS() + this.getLong()), (double)var3, (new Color(20, 20, 20, 170)).getRGB());
         Gui.drawRect((double)(109 + this.setting.getModule().getLongest() + this.setting.getLongestS()), (double)var2, (double)(109 + this.setting.getModule().getLongest() + this.setting.getLongestS()), (double)var3, this.setting.getModule().getType().getTabGUI().getColor());
         Fonts$SF$SF_18.SF_18.drawString(var11, (double)(114 + this.setting.getModule().getLongest() + this.setting.getLongestS()), (double)(var2 + 3.0F), -1, true);
      case 2:
         Gui.drawRect((double)(109 + this.setting.getModule().getLongest() + this.setting.getLongestS()), (double)var2, (double)(129 + this.setting.getModule().getLongest() + this.setting.getLongestS() + this.getLong()), (double)var3, (new Color(20, 20, 20, 170)).getRGB());
         if(this.isSelected()) {
            Gui.drawRect((double)(109 + this.setting.getModule().getLongest() + this.setting.getLongestS()), (double)var2, (double)(129 + this.setting.getModule().getLongest() + this.setting.getLongestS() + this.getLong()), (double)var3, this.setting.getModule().getType().getTabGUI().getColor());
         }

         Fonts$SF$SF_18.SF_18.drawString(this.value, (double)(114.0F + this.i + (float)this.setting.getModule().getLongest() + (float)this.setting.getLongestS()), (double)(var2 + 3.0F), this.setting.getSetting().getComboBoxValue().equalsIgnoreCase(this.value)?-1:(new Color(163, 163, 163, 255)).getRGB(), true);
      case 3:
         Gui.drawRect((double)(109 + this.setting.getModule().getLongest() + this.setting.getLongestS()), (double)var2, (double)(129 + this.setting.getModule().getLongest() + this.setting.getLongestS() + this.getLong()), (double)var3, (new Color(20, 20, 20, 170)).getRGB());
         if(this.isSelected()) {
            Gui.drawRect((double)(109 + this.setting.getModule().getLongest() + this.setting.getLongestS()), (double)var2, (double)(129 + this.setting.getModule().getLongest() + this.setting.getLongestS() + this.getLong()), (double)var3, this.setting.getModule().getType().getTabGUI().getColor());
         }

         Fonts$SF$SF_18.SF_18.drawString(this.value, (double)(114.0F + this.i + (float)this.setting.getModule().getLongest() + (float)this.setting.getLongestS()), (double)(var2 + 3.0F), this.setting.getSetting().getSelectBoxProperty().contains(this.value)?-1:(new Color(163, 163, 163, 255)).getRGB(), true);
      default:
      }
   }

   public int getLong() {
      f();
      int var2 = 0;
      switch(TabValue$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[this.setting.getSetting().getSettingType().ordinal()]) {
      case 1:
         double var9 = this.setting.getSetting().getDouble();
         double var5 = (double)((int)(var9 * 100.0D)) / 100.0D;
         if(var5 % 1.0D == 0.0D) {
            String var7 = (new DecimalFormat("0.##")).format(var5);
         }

         String var11 = var5 + "";
         return Fonts$SF$SF_18.SF_18.stringWidth(var11);
      case 2:
         Iterator var3 = this.setting.getSetting().getComboBox().getAcceptableValues().iterator();
         if(var3.hasNext()) {
            String var4 = (String)var3.next();
            if(Fonts$SF$SF_18.SF_18.stringWidth(var4) > var2) {
               var2 = Fonts$SF$SF_18.SF_18.stringWidth(var4);
            }
         }
      case 3:
         Iterator var8 = this.setting.getSetting().getSelectBoxProperty().getAcceptableValues().iterator();
         if(var8.hasNext()) {
            String var10 = (String)var8.next();
            if(Fonts$SF$SF_18.SF_18.stringWidth(var10) > var2) {
               var2 = Fonts$SF$SF_18.SF_18.stringWidth(var10);
            }
         }
      default:
         return var2;
      }
   }

   public String getValue() {
      return this.value;
   }

   public boolean isSelected() {
      String[] var1 = f();
      return this.setting.getValues().indexOf(this) == this.setting.getModule().getType().getTabGUI().getValueN();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] f() {
      return b;
   }

   static {
      b((String[])null);
   }
}
