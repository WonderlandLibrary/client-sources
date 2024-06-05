package cc.novoline.gui.screen.dropdown;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.BindEvent;
import cc.novoline.events.events.SettingEvent;
import cc.novoline.gui.screen.dropdown.Module;
import cc.novoline.gui.screen.dropdown.Setting$1;
import cc.novoline.gui.screen.dropdown.Tab;
import cc.novoline.gui.screen.setting.Setting$ColorPickerMode;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.binds.KeyboardKeybind;
import cc.novoline.modules.binds.ModuleKeybind;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_16;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_17;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_18;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_26;
import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Setting {
   public cc.novoline.gui.screen.setting.Setting setting;
   private Module module;
   public boolean opened;
   private final Timer backSpace = new Timer();
   private final Timer caretTimer = new Timer();
   public int height;
   public float percent = 0.0F;
   private boolean dragging;

   public Setting(cc.novoline.gui.screen.setting.Setting var1, Module var2) {
      this.setting = var1;
      this.module = var2;
   }

   public void setPercent(float var1) {
      this.percent = var1;
   }

   public void drawScreen(int var1, int var2) {
      int var4 = this.getY();
      Tab.f();
      HUD var5 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      ScaledResolution var6 = new ScaledResolution(Minecraft.getInstance());
      boolean var7 = var6.getScaleFactor() != 1;
      double var8 = MathHelper.clamp_double((double)(Minecraft.getInstance().getDebugFPS() / 30), 1.0D, 9999.0D);
      switch(Setting$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[this.setting.getSettingType().ordinal()]) {
      case 1:
         if(this.module.yPerModule == this.module.getY() && var7) {
            GL11.glPushMatrix();
            GL11.glScissor((int)(this.module.tab.getPosX() * 2.0F + 1.0F), 0, 197, 999999999);
            GL11.glEnable(3089);
         }

         double var10 = (double)((int)(this.setting.getDouble() * 100.0D)) / 100.0D;
         double var12 = (this.setting.getDouble() - this.setting.getSliderNumber().getMinimum().doubleValue()) / (this.setting.getSliderNumber().getMaximum().doubleValue() - this.setting.getSliderNumber().getMinimum().doubleValue());
         this.percent = Math.max(0.0F, Math.min(1.0F, (float)((double)this.percent + (Math.max(0.0D, Math.min(var12, 1.0D)) - (double)this.percent) * (0.2D / var8))));
         Gui.drawRect((double)(this.module.tab.getPosX() + 1.0F), (double)(var4 + 3), (double)(this.module.tab.getPosX() + 99.0F), (double)(var4 + 14), (new Color(0, 0, 0, 50)).getRGB());
         Gui.drawRect((double)(this.module.tab.getPosX() + 1.0F), (double)(var4 + 3), (double)(this.module.tab.getPosX() + 1.0F + 98.0F * this.percent), (double)(var4 + 14), var5.getHUDColor());
         Fonts$SF$SF_18.SF_18.drawString(this.setting.getDisplayName() + " " + var10, (double)(this.module.tab.getPosX() + 4.0F), (double)((float)var4 + 5.5F), -1, true);
         if(this.dragging) {
            double var14 = this.setting.getSliderNumber().getMaximum().doubleValue() - this.setting.getSliderNumber().getMinimum().doubleValue();
            double var16 = this.setting.getSliderNumber().getMinimum().doubleValue() + MathHelper.clamp_double((double)(((float)var1 - (this.module.tab.getPosX() + 1.0F)) / 99.0F), 0.0D, 1.0D) * var14;
            double var18 = MathHelper.incValue(var16, this.setting.getIncrement());
            this.setting.setSlider(var18);
            EventManager.call(new SettingEvent(this.module.getModule(), this.setting.getName(), this.setting.getSliderNumber()));
         }

         if(this.module.yPerModule != this.module.getY() || !var7) {
            break;
         }

         GL11.glDisable(3089);
         GL11.glPopMatrix();
      case 2:
         Gui.drawRect((double)(this.module.tab.getPosX() + 1.0F), (double)(var4 + 3), (double)(this.module.tab.getPosX() + 99.0F), (double)(var4 + 14), (new Color(0, 0, 0, 50)).getRGB());
         String var20 = this.setting.getDisplayName() + (this.opened?"":"");
         Fonts$SF$SF_18.SF_18.drawString(var20, (double)this.module.tab.getPosX() + 49.5D - (double)(Fonts$SF$SF_18.SF_18.stringWidth(var20) / 2), (double)((float)var4 + 5.5F), Color.WHITE.getRGB(), true);
      case 3:
         Gui.drawRect((double)(this.module.tab.getPosX() + 89.0F), (double)(var4 + 4), (double)(this.module.tab.getPosX() + 99.0F), (double)(var4 + 14), (new Color(0, 0, 0, 50)).getRGB());
         if(this.setting.getCheckBoxValue().booleanValue()) {
            RenderUtils.drawCheck((double)(this.module.tab.getPosX() + 91.0F), (double)((float)var4 + 8.5F), 2, (new Color(var5.getHUDColor())).brighter().getRGB());
         }

         Fonts$SF$SF_18.SF_18.drawString(this.setting.getDisplayName(), (double)(this.module.tab.getPosX() + 4.0F), (double)((float)var4 + 5.5F), (new Color(227, 227, 227, 255)).getRGB(), true);
      case 4:
         Fonts$SF$SF_17.SF_17.drawString(this.setting.getDisplayName(), (double)(this.module.tab.getPosX() + 3.0F), (double)((float)(var4 + 6)), -1, true);
         Fonts$SF$SF_17.SF_17.drawString(this.setting.getComboBoxValue().toUpperCase(), (double)(this.module.tab.getPosX() + 97.0F - (float)Fonts$SF$SF_17.SF_17.stringWidth(this.setting.getComboBoxValue().toUpperCase())), (double)((float)var4 + 7.0F), (new Color(255, 255, 255, 255)).getRGB(), true);
      case 5:
         int var21 = 0;
         Iterator var11 = this.setting.getSelectBoxProperty().getAcceptableValues().iterator();
         if(var11.hasNext()) {
            String var28 = (String)var11.next();
            var21 += 17;
         }

         var21 = var21 + 2;
         Gui.drawRect((double)(this.module.tab.getPosX() + 1.0F), (double)(var4 + 3), (double)(this.module.tab.getPosX() + 99.0F), (double)(var4 + 14 + (this.opened?var21:0)), (new Color(0, 0, 0, 50)).getRGB());
         String var26 = this.setting.getDisplayName() + (this.opened?"":"");
         Fonts$SF$SF_18.SF_18.drawString(var26, (double)this.module.tab.getPosX() + 49.5D - (double)(Fonts$SF$SF_18.SF_18.stringWidth(var26) / 2), (double)((float)var4 + 5.5F), Color.WHITE.getRGB(), true);
         if(!this.opened) {
            break;
         }

         int var29 = var4 + 17;
         Iterator var13 = this.setting.getSelectBoxProperty().getAcceptableValues().iterator();
         if(var13.hasNext()) {
            String var33 = (String)var13.next();
            int var15 = this.setting.getSelectBox().contains(var33)?-1:(new Color(174, 174, 174, 150)).getRGB();
            Fonts$SF$SF_18.SF_18.drawString(var33, (double)(this.module.tab.getPosX() + 15.0F), (double)((float)(var29 + 6)), var15, true);
            Fonts$SF$SF_26.SF_26.drawString(".", this.module.tab.getPosX() + 9.0F, (float)var29, this.setting.getSelectBox().contains(var33)?-1:(new Color(174, 174, 174, 150)).getRGB());
            var29 = var29 + 17;
         }
      case 6:
         String var23 = this.setting.getTextBoxValue();
         if(this.setting.isTextHovered() && Keyboard.isKeyDown(14) && this.backSpace.delay(100.0D) && var23.length() >= 1) {
            this.setting.getTextBoxValue2().set(var23.substring(0, var23.length() - 1));
            this.backSpace.reset();
         }

         Gui.drawRect((double)(this.module.tab.getPosX() + 6.0F), (double)(var4 + 16), (double)(this.module.tab.getPosX() + 84.0F), (double)var4 + 16.5D, (new Color(195, 195, 195, 220)).getRGB());
         Fonts$SF$SF_16.SF_16.drawString(this.setting.getDisplayName(), this.module.tab.getPosX() + 5.5F, (float)var4 + 1.5F, (new Color(227, 227, 227, 255)).getRGB());
         if(Fonts$SF$SF_16.SF_16.stringWidth(var23) > 65) {
            Fonts$SF$SF_16.SF_16.drawString(Fonts$SF$SF_16.SF_16.trimStringToWidth(var23, 78, true), this.module.tab.getPosX() + 6.0F, (float)(var4 + 10), -1);
         }

         Fonts$SF$SF_16.SF_16.drawString(var23, this.module.tab.getPosX() + 6.0F, (float)(var4 + 10), -1);
      case 7:
         int var24 = ((ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class)).getGUIColor();
         Integer var27 = this.setting.getColor().get();
         int var31 = var27.intValue() >> 16 & 255;
         int var32 = var27.intValue() >> 8 & 255;
         int var34 = var27.intValue() & 255;
         float[] var35 = Color.RGBtoHSB(var31, var32, var34, new float[3]);
         if(this.setting.isColorPickerRainbow()) {
            this.setting.setSeparatorHue((this.setting.getSeparatorHue() + 0.35F) % 99.0F);
         }

         if(this.dragging) {
            double var36 = MathHelper.clamp_double((double)((float)var1 - this.module.tab.getPosX() - 1.0F), 0.35D, 97.0D);
            float var45 = (float)(var36 / 99.0D);
            switch(Setting$1.$SwitchMap$cc$novoline$gui$screen$setting$Setting$ColorPickerMode[this.setting.getColorPickerMode().ordinal()]) {
            case 1:
               this.setting.setSeparatorHue((float)((int)var36));
               this.setting.getColor().set(Integer.valueOf(Color.getHSBColor(var45, var35[1], var35[2]).getRGB()));
            case 2:
               this.setting.setSeparatorSaturation((float)((int)var36));
               this.setting.getColor().set(Integer.valueOf(Color.getHSBColor(var35[0], var45, var35[2]).getRGB()));
            case 3:
               this.setting.setSeparatorBrightness((float)((int)var36));
               this.setting.getColor().set(Integer.valueOf(Color.getHSBColor(var35[0], var35[1], var45).getRGB()));
            }
         }

         switch(Setting$1.$SwitchMap$cc$novoline$gui$screen$setting$Setting$ColorPickerMode[this.setting.getColorPickerMode().ordinal()]) {
         case 1:
            byte var37 = 98;
            int var17 = 0;
            if(var17 < var37) {
               Gui.drawRect((double)(this.module.tab.getPosX() + 1.0F + (float)var17), (double)(var4 + 3), (double)(this.module.tab.getPosX() + 2.0F + (float)var17), (double)(var4 + 14), Color.getHSBColor((float)var17 / (float)var37, var35[1], var35[2]).getRGB());
               ++var17;
            }

            Fonts$SF$SF_18.SF_18.drawString(this.setting.getDisplayName(), (double)(this.module.tab.getPosX() + 4.0F), (double)((float)var4 + 5.5F), -1, true);
            Gui.drawRect((double)(this.module.tab.getPosX() + 1.0F + this.setting.getSeparatorHue()), (double)(var4 + 3), (double)(this.module.tab.getPosX() + 2.0F + this.setting.getSeparatorHue()), (double)(var4 + 14), Color.WHITE.getRGB());
         case 2:
            byte var38 = 98;
            int var41 = 0;
            if(var41 < var38) {
               Gui.drawRect((double)(this.module.tab.getPosX() + 1.0F + (float)var41), (double)(var4 + 3), (double)(this.module.tab.getPosX() + 2.0F + (float)var41), (double)(var4 + 14), Color.getHSBColor(var35[0], (float)var41 / (float)var38, var35[2]).getRGB());
               ++var41;
            }

            Fonts$SF$SF_18.SF_18.drawString(this.setting.getDisplayName(), (double)(this.module.tab.getPosX() + 4.0F), (double)((float)var4 + 5.5F), -1, true);
            Gui.drawRect((double)(this.module.tab.getPosX() + 1.0F + this.setting.getSeparatorSaturation()), (double)(var4 + 3), (double)(this.module.tab.getPosX() + 2.0F + this.setting.getSeparatorSaturation()), (double)(var4 + 14), Color.WHITE.getRGB());
         case 3:
            byte var39 = 98;
            int var43 = 0;
            if(var43 < var39) {
               Gui.drawRect((double)(this.module.tab.getPosX() + 1.0F + (float)var43), (double)(var4 + 3), (double)(this.module.tab.getPosX() + 2.0F + (float)var43), (double)(var4 + 14), Color.getHSBColor(var35[0], var35[1], (float)var43 / (float)var39).getRGB());
               ++var43;
            }

            Fonts$SF$SF_18.SF_18.drawString(this.setting.getDisplayName(), (double)(this.module.tab.getPosX() + 4.0F), (double)((float)var4 + 5.5F), -1, true);
            Gui.drawRect((double)(this.module.tab.getPosX() + 1.0F + this.setting.getSeparatorBrightness()), (double)(var4 + 3), (double)(this.module.tab.getPosX() + 2.0F + this.setting.getSeparatorBrightness()), (double)(var4 + 14), Color.WHITE.getRGB());
         }
      case 8:
         Gui.drawRect((double)(this.module.tab.getPosX() + 1.0F), (double)(var4 + 3), (double)(this.module.tab.getPosX() + 99.0F), (double)(var4 + 14), (new Color(0, 0, 0, 50)).getRGB());
         Fonts$SF$SF_18.SF_18.drawString(this.setting.getDisplayName(), this.module.tab.getPosX() + 3.0F, (float)(var4 + 6), -1);
         String var25 = "[" + (this.setting.isListening()?"..":Keyboard.getKeyName(((ModuleKeybind)this.setting.getKeyBindValue().get()).getKey())) + "]";
         Fonts$SF$SF_18.SF_18.drawString(var25, this.module.tab.getPosX() + 97.5F - (float)Fonts$SF$SF_18.SF_18.stringWidth(var25), (float)var4 + 5.5F, (new Color(170, 170, 170)).getRGB());
      }

   }

   private int getY() {
      Tab.e();
      int var2 = this.module.y + 14;
      Iterator var3 = ((List)this.module.settings.stream().filter(Setting::lambda$getY$0).collect(Collectors.toList())).iterator();
      if(var3.hasNext()) {
         Setting var4 = (Setting)var3.next();
         if(var4 == this) {
            ;
         }

         var2 += var4.getHeight();
      }

      return var2;
   }

   public int getHeight() {
      boolean var1 = Tab.e();
      return this.setting.getSettingType() == SettingType.SELECTBOX?(this.opened?17 + this.setting.getSelectBoxProperty().getAcceptableValues().size() * 17:15):15;
   }

   public void mouseClicked(int var1, int var2, int var3) throws IOException {
      boolean var4 = Tab.e();
      if(this.isHovered(var1, var2)) {
         switch(Setting$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[this.setting.getSettingType().ordinal()]) {
         case 6:
         default:
            break;
         case 8:
            if(!this.isHovered(var1, var2)) {
               break;
            }

            if(this.setting.isListening()) {
               if(((ModuleKeybind)this.setting.getKeyBindValue().get()).getKey() != 0) {
                  this.setting.getKeyBindValue().set(KeyboardKeybind.of(0));
                  EventManager.call(new BindEvent(this.setting.getParentModule(), 0, Keyboard.getKeyName(((ModuleKeybind)this.setting.getKeyBindValue().get()).getKey())));
               }

               this.setting.setListening(false);
            }

            this.setting.setListening(true);
         case 3:
            this.setting.getCheckBoxProperty().invert();
            EventManager.call(new SettingEvent(this.module.getModule(), this.setting.getName(), this.setting.getDisplayName(), this.setting.getCheckBoxProperty()));
         case 4:
            this.setting.setComboBoxValue((String)this.setting.getComboBox().getAcceptableValues().get(this.setting.getComboBox().getAcceptableValues().indexOf(this.setting.getComboBoxValue()) + 1 > this.setting.getComboBox().getAcceptableValues().size() - 1?0:this.setting.getComboBox().getAcceptableValues().indexOf(this.setting.getComboBoxValue()) + 1));
            EventManager.call(new SettingEvent(this.module.getModule(), this.setting.getName(), this.setting.getComboBoxProperty()));
         case 5:
            if(var3 != 1 && var3 != 0) {
               break;
            }

            this.opened = !this.opened;
         case 1:
            this.dragging = true;
         case 7:
            this.dragging = true;
         case 2:
            if(this.isHovered(var1, var2)) {
               Minecraft.getInstance().displayGuiScreen(this.setting.N());
            }
         }
      }

      if(this.setting.getSettingType() == SettingType.TEXTBOX) {
         if(this.isHovered(var1, var2)) {
            this.setting.setTextHovered(!this.setting.isTextHovered());
         }

         if(this.setting.isTextHovered()) {
            this.setting.setTextHovered(false);
         }
      }

      if(this.setting.getSettingType() == SettingType.SELECTBOX && this.opened && (float)var1 >= this.module.tab.getPosX() && (float)var1 <= this.module.tab.getPosX() + 90.0F) {
         List var5 = this.setting.getSelectBoxProperty().getAcceptableValues();
         int var6 = 0;
         if(var6 < var5.size()) {
            int var7 = this.getY() + 17 + var6 * 17;
            if(var2 >= var7 && var2 <= var7 + 17) {
               String var8 = (String)var5.get(var6);
               if(this.setting.getSelectBoxProperty().contains(var8)) {
                  this.setting.getSelectBoxProperty().remove(var8);
               }

               this.setting.getSelectBoxProperty().add(var8);
               EventManager.call(new SettingEvent(this.module.getModule(), this.setting.getName(), this.setting.getSelectBoxProperty()));
            }

            ++var6;
         }
      }

      if(var3 == 1 && this.setting.getSettingType() == SettingType.COLOR_PICKER && this.isHovered(var1, var2)) {
         Setting$ColorPickerMode[] var9 = Setting$ColorPickerMode.values();
         int var11 = (Arrays.binarySearch(var9, this.setting.getColorPickerMode()) + 1) % var9.length;
         if(this.setting.getColorPickedDisabledModes() == null) {
            this.setting.setColorPickerMode(var9[var11]);
         }

         if(!this.setting.getColorPickedDisabledModes().isEmpty()) {
            int var13 = 0;
            if(var13 < var9.length - 1) {
               Setting$ColorPickerMode var12 = var9[(var11 + var13) % var9.length];
               if(!this.setting.getColorPickedDisabledModes().contains(var12)) {
                  this.setting.setColorPickerMode(var9[(var13 + var11) % var9.length]);
               }

               ++var13;
            }
         }
      }

   }

   public void keyTyped(char var1, int var2) {
      boolean var3 = Tab.f();
      if(this.setting.getSettingType() == SettingType.TEXTBOX) {
         if(!this.setting.isTextHovered()) {
            return;
         }

         if(var2 == 1 || var2 == 28) {
            this.setting.setTextHovered(false);
         }

         if(var2 == 14 || var2 == 157 || var2 == 29 || var2 == 54 || var2 == 42 || var2 == 15 || var2 == 58 || var2 == 211 || var2 == 199 || var2 == 210 || var2 == 200 || var2 == 208 || var2 == 205 || var2 == 203 || var2 == 56 || var2 == 184 || var2 == 197 || var2 == 70 || var2 == 207 || var2 == 201 || var2 == 209 || var2 == 221 || var2 == 59 || var2 == 60 || var2 == 61 || var2 == 62 || var2 == 63 || var2 == 64 || var2 == 65 || var2 == 66 || var2 == 67 || var2 == 68 || var2 == 87 || var2 == 88) {
            return;
         }

         this.setting.getTextBoxValue2().append(Character.valueOf(var1));
      }

      if(this.setting.getSettingType() == SettingType.BINDABLE && this.setting.isListening()) {
         this.setting.getKeyBindValue().set(KeyboardKeybind.of(var2));
         EventManager.call(new BindEvent(this.setting.getParentModule(), ((ModuleKeybind)this.setting.getKeyBindValue().get()).getKey(), Keyboard.getKeyName(((ModuleKeybind)this.setting.getKeyBindValue().get()).getKey())));
         this.setting.setListening(false);
      }

   }

   public void mouseReleased(int var1, int var2, int var3) {
      this.dragging = false;
   }

   private boolean areHovered(int var1, int var2) {
      Tab.f();
      int var4 = this.getY() + 17;
      if(this.opened) {
         Iterator var5 = this.setting.getSelectBoxProperty().getAcceptableValues().iterator();
         if(var5.hasNext()) {
            String var6 = (String)var5.next();
            var4 += 17;
         }
      }

      return (float)var1 <= this.module.tab.getPosX() && var2 <= var4 && (float)var1 >= this.module.tab.getPosX() + 90.0F && var2 <= var4 + 17;
   }

   public boolean isHovered(int var1, int var2) {
      Tab.e();
      int var4 = this.getY();
      switch(Setting$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[this.setting.getSettingType().ordinal()]) {
      case 1:
      case 2:
      case 7:
         return (float)var1 >= this.module.tab.getPosX() + 1.0F && var2 >= var4 + 3 && (float)var1 <= this.module.tab.getPosX() + 99.0F && var2 <= var4 + 14;
      case 3:
         return (float)var1 >= this.module.tab.getPosX() + 89.0F && var2 >= var4 + 4 && (float)var1 <= this.module.tab.getPosX() + 99.0F && var2 <= var4 + 14;
      case 4:
      case 5:
      case 6:
      default:
         return (float)var1 >= this.module.tab.getPosX() && var2 >= var4 && (float)var1 <= this.module.tab.getPosX() + 90.0F && var2 <= var4 + 17;
      case 8:
         String var5 = "[" + Keyboard.getKeyName(((ModuleKeybind)this.setting.getKeyBindValue().get()).getKey()) + "]";
         return (float)var1 >= this.module.tab.getPosX() + 97.5F - (float)Fonts$SF$SF_18.SF_18.stringWidth(var5) && (float)var1 <= this.module.tab.getPosX() + 97.5F && var2 >= var4 + 4 && var2 <= var4 + 14;
      }
   }

   private static boolean lambda$getY$0(Setting var0) {
      boolean var1 = Tab.e();
      return var0.setting.getSupplier() != null?((Boolean)var0.setting.getSupplier().get()).booleanValue():true;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
