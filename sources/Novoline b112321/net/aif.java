package net;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.SettingEvent;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.Setting$ColorPickerMode;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_17;
import java.awt.Color;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.Ci;
import net.J3;
import net.aHW;
import net.aIq;
import net.acE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

public class aif {
   public Setting e;
   public aIq c;
   private boolean f;
   private boolean h;
   public float g = 0.0F;
   private Timer d = new Timer();
   private static String[] b;

   public aif(Setting var1, aIq var2) {
      this.e = var1;
      this.c = var2;
   }

   public void a(int var1, int var2) {
      float var7;
      float var9;
      label294: {
         int var10;
         label294: {
            label294: {
               b();
               Minecraft var4 = Minecraft.getInstance();
               ScaledResolution var5 = new ScaledResolution(Minecraft.getInstance());
               float var6 = (float)EnumModuleType.values().length;
               var7 = (float)(var5.getScaledWidthStatic(var4) / 2);
               float var8 = (float)(var5.getScaledHeightStatic(var4) / 2);
               var9 = 5.0F + var6 * (float)(aHW.y + aHW.B) / 2.0F;
               Gui.drawRect((double)(var7 - var9), this.c(), (double)(var7 + var9), this.c() + 17.0D, ((List)this.c.i().stream().filter(aif::lambda$drawScreen$0).collect(Collectors.toList())).indexOf(this) % 2 != 0?(new Color(40, 40, 40)).getRGB():(new Color(56, 56, 56)).getRGB());
               Fonts$SF$SF_17.SF_17.drawString(this.e.getDisplayName(), (double)(var7 - var9 + 3.0F), this.c() + 5.0D, -1, true);
               var10 = ((List)this.c.i().stream().filter(aif::lambda$drawScreen$1).collect(Collectors.toList())).indexOf(this) % 2 == 0?(new Color(40, 40, 40)).getRGB():(new Color(56, 56, 56)).getRGB();
               switch(Ci.b[this.e.getSettingType().ordinal()]) {
               case 1:
                  Fonts$SF$SF_17.SF_17.drawString("CHECKBOX", (double)(var7 - var9 + 160.0F - (float)Fonts$SF$SF_17.SF_17.stringWidth("CHECKBOX")), this.c() + 5.0D, (new Color(220, 220, 220)).darker().darker().getRGB(), true);
                  String var11 = this.e.getCheckBoxValue().booleanValue()?"Enabled":"Disabled";
                  if((float)var1 >= var7 + var9 - 85.0F && (float)var1 <= var7 + var9 - 5.0F && (double)var2 >= this.c() + 2.0D && (double)var2 <= this.c() + 15.0D) {
                     Fonts$SF$SF_17.SF_17.drawString(var11, (double)(var7 + var9 - (float)Fonts$SF$SF_17.SF_17.stringWidth(var11) - 5.0F), this.c() + 5.0D, (new Color(255, 255, 255)).darker().darker().getRGB(), true);
                  }

                  Fonts$SF$SF_17.SF_17.drawString(var11, (double)(var7 + var9 - (float)Fonts$SF$SF_17.SF_17.stringWidth(var11) - 5.0F), this.c() + 5.0D, (new Color(220, 220, 220)).darker().darker().getRGB(), true);
               case 2:
                  double var12 = (double)((int)(this.e.getDouble() * 100.0D)) / 100.0D;
                  double var14 = (this.e.getDouble() - this.e.getSliderNumber().getMinimum().doubleValue()) / (this.e.getSliderNumber().getMaximum().doubleValue() - this.e.getSliderNumber().getMinimum().doubleValue());
                  double var16 = MathHelper.clamp_double((double)(Minecraft.getInstance().getDebugFPS() / 30), 1.0D, 9999.0D);
                  this.g = Math.max(0.0F, Math.min(1.0F, (float)((double)this.g + (Math.max(0.0D, Math.min(var14, 1.0D)) - (double)this.g) * (0.2D / var16))));
                  Fonts$SF$SF_17.SF_17.drawString("SLIDER", (double)(var7 - var9 + 160.0F - (float)Fonts$SF$SF_17.SF_17.stringWidth("SLIDER")), this.c() + 5.0D, (new Color(220, 220, 220)).darker().darker().getRGB(), true);
                  Gui.drawRect((double)(var7 + var9 - 85.0F), this.c() + 2.0D, (double)(var7 + var9 - 5.0F), this.c() + 15.0D, var10);
                  Gui.drawRect((double)(var7 + var9 - 85.0F), this.c() + 2.0D, (double)(var7 + var9 - 85.0F + 80.0F * this.g), this.c() + 15.0D, (new Color(65, 160, 114)).getRGB());
                  double var18 = (double)((int)(this.e.getDouble() * 100.0D)) / 100.0D;
                  Fonts$SF$SF_17.SF_17.drawString(var18 + "", (double)(var7 + var9 - 44.0F - (float)(Fonts$SF$SF_17.SF_17.stringWidth(var18 + "") / 2)), this.c() + 6.0D, -1, true);
                  if(this.h) {
                     double var20 = this.e.getSliderNumber().getMaximum().doubleValue() - this.e.getSliderNumber().getMinimum().doubleValue();
                     double var22 = this.e.getSliderNumber().getMinimum().doubleValue() + MathHelper.clamp_double((double)(((float)var1 - (var7 + var9 - 85.0F + 1.0F)) / 80.0F), 0.0D, 1.0D) * var20;
                     double var24 = MathHelper.incValue(var22, this.e.getIncrement());
                     this.e.setSlider(var24);
                     EventManager.call(new SettingEvent(this.c.f(), this.e.getName(), this.e.getSliderNumber()));
                  }

                  if(((float)var1 < var7 + var9 - 85.0F || (float)var1 > var7 + var9 - 5.0F || (double)var2 < this.c() + 2.0D || (double)var2 > this.c() + 15.0D) && !this.h) {
                     return;
                  }

                  J3.a((double)(var7 + var9 - 85.0F), this.c() + 2.0D, (double)(var7 + var9 - 5.0F), this.c() + 15.0D, 1.0F, (new Color(100, 100, 100)).getRGB(), 268435455);
               case 3:
                  Fonts$SF$SF_17.SF_17.drawString("CUSTOM", (double)(var7 - var9 + 160.0F - (float)Fonts$SF$SF_17.SF_17.stringWidth("CUSTOM")), this.c() + 5.0D, (new Color(220, 220, 220)).darker().darker().getRGB(), true);
                  Gui.drawRect((double)(var7 + var9 - 85.0F), this.c() + 2.0D, (double)(var7 + var9 - 5.0F), this.c() + 15.0D, var10);
                  Fonts$SF$SF_17.SF_17.drawCenteredString("Configure", var7 + var9 - 44.0F, (float)(this.c() + 5.5D), -1, true);
                  if((float)var1 < var7 + var9 - 85.0F || (float)var1 > var7 + var9 - 5.0F || (double)var2 < this.c() + 2.0D || (double)var2 > this.c() + 15.0D) {
                     return;
                  }

                  J3.a((double)(var7 + var9 - 85.0F), this.c() + 2.0D, (double)(var7 + var9 - 5.0F), this.c() + 15.0D, 1.0F, (new Color(100, 100, 100)).getRGB(), 268435455);
               case 4:
                  break;
               case 5:
                  break label294;
               case 6:
                  break label294;
               case 7:
                  break label294;
               default:
                  return;
               }

               Fonts$SF$SF_17.SF_17.drawString("TEXTBOX", (double)(var7 - var9 + 160.0F - (float)Fonts$SF$SF_17.SF_17.stringWidth("TEXTBOX")), this.c() + 5.0D, (new Color(220, 220, 220)).darker().darker().getRGB(), true);
               Gui.drawRect((double)(var7 + var9 - 85.0F), this.c() + 2.0D, (double)(var7 + var9 - 5.0F), this.c() + 15.0D, var10);
               Fonts$SF$SF_17.SF_17.drawString(Fonts$SF$SF_17.SF_17.trimStringToWidth(this.e.getTextBoxValue() + (this.e.isTextHovered()?" |":""), 65, false), (double)(var7 + var9 - 83.0F), this.c() + 5.5D, -1, true);
               String var31 = this.e.getTextBoxValue();
               if(!this.e.isTextHovered() || !Keyboard.isKeyDown(14) || !this.d.delay(100.0D) || var31.length() < 1) {
                  return;
               }

               this.e.getTextBoxValue2().set(var31.substring(0, var31.length() - 1));
               this.d.reset();
            }

            Fonts$SF$SF_17.SF_17.drawString("COMBOBOX", (double)(var7 - var9 + 160.0F - (float)Fonts$SF$SF_17.SF_17.stringWidth("COMBOBOX")), this.c() + 5.0D, (new Color(220, 220, 220)).darker().darker().getRGB(), true);
            Gui.drawRect((double)(var7 + var9 - 85.0F), this.c() + 2.0D, (double)(var7 + var9 - 5.0F), this.c() + 15.0D, var10);
            Fonts$SF$SF_17.SF_17.drawString(Fonts$SF$SF_17.SF_17.trimStringToWidth(this.e.getComboBoxValue(), 65, false), (double)(var7 + var9 - 83.0F), this.c() + 5.5D, -1, true);
            RenderUtils.drawArrow((double)(var7 + var9 - 15.0F), this.c() + (double)(this.f?10:7), 1, (new Color(100, 100, 100)).getRGB(), this.f?-3.0D:3.0D);
            if(this.f) {
               double var21 = this.c() + 17.0D;
               List var23 = (List)this.e.getComboBoxProperty().getAcceptableValues().stream().filter(this::lambda$drawScreen$2).collect(Collectors.toList());
               Iterator var40 = var23.iterator();
               if(var40.hasNext()) {
                  String var25 = (String)var40.next();
                  Gui.drawRect((double)(var7 + var9 - 85.0F), var21, (double)(var7 + var9 - 5.0F), var21 + 17.0D, ((List)this.c.i().stream().filter(aif::lambda$drawScreen$3).collect(Collectors.toList())).indexOf(this) % 2 == 0?(new Color(40, 40, 40)).getRGB():(new Color(56, 56, 56)).getRGB());
                  if((float)var1 >= var7 + var9 - 85.0F && (float)var1 <= var7 + var9 - 5.0F && (double)var2 >= var21 && (double)var2 <= var21 + 17.0D) {
                     Fonts$SF$SF_17.SF_17.drawString(var25, (double)(var7 + var9 - 83.0F), var21 + 5.5D, (new Color(255, 255, 255)).darker().getRGB(), true);
                  }

                  Fonts$SF$SF_17.SF_17.drawString(var25, (double)(var7 + var9 - 83.0F), var21 + 5.5D, (new Color(255, 255, 255)).darker().darker().getRGB(), true);
                  var21 = var21 + 17.0D;
               }

               J3.a((double)(var7 + var9 - 85.0F), this.c() + 17.0D, (double)(var7 + var9 - 5.0F), this.c() + 17.0D + (double)(var23.size() * 17), 1.2F, (new Color(100, 100, 100)).getRGB(), 268435455);
            }

            if((float)var1 < var7 + var9 - 85.0F || (float)var1 > var7 + var9 - 5.0F || (double)var2 < this.c() + 2.0D || (double)var2 > this.c() + 15.0D) {
               return;
            }

            J3.a((double)(var7 + var9 - 85.0F), this.c() + 2.0D, (double)(var7 + var9 - 5.0F), this.c() + 15.0D, 1.0F, (new Color(100, 100, 100)).getRGB(), 268435455);
         }

         Fonts$SF$SF_17.SF_17.drawString("SELECTBOX", (double)(var7 - var9 + 160.0F - (float)Fonts$SF$SF_17.SF_17.stringWidth("SELECTBOX")), this.c() + 5.0D, (new Color(220, 220, 220)).darker().darker().getRGB(), true);
         Gui.drawRect((double)(var7 + var9 - 85.0F), this.c() + 2.0D, (double)(var7 + var9 - 5.0F), this.c() + 15.0D, var10);
         StringBuilder var33 = new StringBuilder();
         Iterator var34 = ((List)this.e.getSelectBoxProperty().get()).iterator();
         if(var34.hasNext()) {
            String var38 = (String)var34.next();
            var33.append(var38).append(((List)this.e.getSelectBoxProperty().get()).indexOf(var38) == this.e.getSelectBoxProperty().size() - 1?"":",");
         }

         Fonts$SF$SF_17.SF_17.drawString(Fonts$SF$SF_17.SF_17.trimStringToWidth(((List)this.e.getSelectBoxProperty().get()).isEmpty()?"...":var33.toString(), 60, false), (double)(var7 + var9 - 83.0F), this.c() + 5.5D, -1, true);
         RenderUtils.drawArrow((double)(var7 + var9 - 15.0F), this.c() + (double)(this.f?10:7), 1, (new Color(100, 100, 100)).getRGB(), this.f?-3.0D:3.0D);
         if(this.f) {
            double var36 = this.c() + 17.0D;
            Iterator var42 = this.e.getSelectBoxProperty().getAcceptableValues().iterator();
            if(var42.hasNext()) {
               String var44 = (String)var42.next();
               Gui.drawRect((double)(var7 + var9 - 85.0F), var36, (double)(var7 + var9 - 5.0F), var36 + 17.0D, ((List)this.c.i().stream().filter(aif::lambda$drawScreen$4).collect(Collectors.toList())).indexOf(this) % 2 == 0?(new Color(40, 40, 40)).getRGB():(new Color(56, 56, 56)).getRGB());
               if((float)var1 >= var7 + var9 - 85.0F && (float)var1 <= var7 + var9 - 5.0F && (double)var2 >= var36 && (double)var2 <= var36 + 17.0D || ((List)this.e.getSelectBoxProperty().get()).contains(var44)) {
                  Fonts$SF$SF_17.SF_17.drawString(var44, (double)(var7 + var9 - 83.0F), var36 + 5.5D, (new Color(250, 250, 250)).getRGB(), true);
               }

               Fonts$SF$SF_17.SF_17.drawString(var44, (double)(var7 + var9 - 83.0F), var36 + 5.5D, (new Color(255, 255, 255)).darker().darker().getRGB(), true);
               var36 = var36 + 17.0D;
            }

            J3.a((double)(var7 + var9 - 85.0F), this.c() + 17.0D, (double)(var7 + var9 - 5.0F), this.c() + 17.0D + (double)(this.e.getSelectBoxProperty().getAcceptableValues().size() * 17), 1.2F, (new Color(100, 100, 100)).getRGB(), 268435455);
            return;
         }

         if((float)var1 < var7 + var9 - 85.0F || (float)var1 > var7 + var9 - 5.0F || (double)var2 < this.c() + 2.0D || (double)var2 > this.c() + 15.0D) {
            return;
         }

         J3.a((double)(var7 + var9 - 85.0F), this.c() + 2.0D, (double)(var7 + var9 - 5.0F), this.c() + 15.0D, 1.0F, (new Color(100, 100, 100)).getRGB(), 268435455);
      }

      Fonts$SF$SF_17.SF_17.drawString("COLOR_PICKER", (double)(var7 - var9 + 160.0F - (float)Fonts$SF$SF_17.SF_17.stringWidth("COLOR_PICKER")), this.c() + 5.0D, (new Color(220, 220, 220)).darker().darker().getRGB(), true);
      int var35 = ((ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class)).getGUIColor();
      Integer var39 = this.e.getColor().get();
      int var41 = var39.intValue() >> 16 & 255;
      int var43 = var39.intValue() >> 8 & 255;
      int var26 = var39.intValue() & 255;
      float[] var27 = Color.RGBtoHSB(var41, var43, var26, new float[3]);
      if(this.e.isColorPickerRainbow()) {
         this.e.setSeparatorHue((this.e.getSeparatorHue() + 0.35F) % 80.0F);
      }

      if(this.h) {
         double var28 = MathHelper.clamp_double((double)((float)var1 - (var7 + var9 - 86.0F)), 0.35D, 80.0D);
         float var30 = (float)(var28 / 80.0D);
         switch(Ci.a[this.e.getColorPickerMode().ordinal()]) {
         case 1:
            this.e.setSeparatorHue((float)((int)var28));
            this.e.getColor().set(Integer.valueOf(Color.getHSBColor(var30, var27[1], var27[2]).getRGB()));
         case 2:
            this.e.setSeparatorSaturation((float)((int)var28));
            this.e.getColor().set(Integer.valueOf(Color.getHSBColor(var27[0], var30, var27[2]).getRGB()));
         case 3:
            this.e.setSeparatorBrightness((float)((int)var28));
            this.e.getColor().set(Integer.valueOf(Color.getHSBColor(var27[0], var27[1], var30).getRGB()));
         }
      }

      switch(Ci.a[this.e.getColorPickerMode().ordinal()]) {
      case 1:
         byte var45 = 80;
         int var29 = 0;
         if(var29 < var45) {
            Gui.drawRect((double)(var7 + var9 - 85.0F + 1.0F + (float)var29), this.c() + 2.0D, (double)(var7 + var9 - 85.0F + 2.0F + (float)var29), this.c() + 15.0D, Color.getHSBColor((float)var29 / (float)var45, var27[1], var27[2]).getRGB());
            ++var29;
         }

         Gui.drawRect((double)(var7 + var9 - 85.0F + 1.0F + this.e.getSeparatorHue()), this.c() + 2.0D, (double)(var7 + var9 - 85.0F + 2.0F + this.e.getSeparatorHue()), this.c() + 15.0D, Color.WHITE.getRGB());
      case 2:
         byte var46 = 80;
         int var49 = 0;
         if(var49 < var46) {
            Gui.drawRect((double)(var7 + var9 - 85.0F + 1.0F + (float)var49), this.c() + 2.0D, (double)(var7 + var9 - 85.0F + 2.0F + (float)var49), this.c() + 15.0D, Color.getHSBColor(var27[0], (float)var49 / (float)var46, var27[2]).getRGB());
            ++var49;
         }

         Gui.drawRect((double)(var7 + var9 - 85.0F + 1.0F + this.e.getSeparatorSaturation()), this.c() + 2.0D, (double)(var7 + var9 - 85.0F + 2.0F + this.e.getSeparatorSaturation()), this.c() + 15.0D, Color.WHITE.getRGB());
      case 3:
         byte var47 = 80;
         int var51 = 0;
         if(var51 < var47) {
            Gui.drawRect((double)(var7 + var9 - 85.0F + 1.0F + (float)var51), this.c() + 2.0D, (double)(var7 + var9 - 85.0F + 2.0F + (float)var51), this.c() + 15.0D, Color.getHSBColor(var27[0], var27[1], (float)var51 / (float)var47).getRGB());
            ++var51;
         }

         Gui.drawRect((double)(var7 + var9 - 85.0F + 1.0F + this.e.getSeparatorBrightness()), this.c() + 2.0D, (double)(var7 + var9 - 85.0F + 2.0F + this.e.getSeparatorBrightness()), this.c() + 15.0D, Color.WHITE.getRGB());
      }

   }

   private double c() {
      b();
      ScaledResolution var2 = new ScaledResolution(Minecraft.getInstance());
      float var3 = (float)EnumModuleType.values().length;
      float var4 = (float)(var2.getScaledWidthStatic(Minecraft.getInstance()) / 2);
      float var5 = (float)(var2.getScaledHeightStatic(Minecraft.getInstance()) / 2);
      float var6 = 5.0F + var3 * (float)(aHW.y + aHW.B) / 2.0F;
      double var7 = (double)(var5 + 22.0F - this.c.g().d().b() + 17.0F);
      Iterator var9 = ((List)this.c.i().stream().filter(aif::lambda$getVerticalPosition$5).collect(Collectors.toList())).iterator();
      if(var9.hasNext()) {
         aif var10 = (aif)var9.next();
         if(var10 == this) {
            ;
         }

         var7 += 17.0D;
      }

      return var7;
   }

   public void b(int var1, int var2, int var3) {
      this.h = false;
   }

   public void a(int var1, int var2, int var3) {
      ScaledResolution var5 = new ScaledResolution(Minecraft.getInstance());
      b();
      float var6 = (float)EnumModuleType.values().length;
      float var7 = (float)(var5.getScaledWidthStatic(Minecraft.getInstance()) / 2);
      float var8 = (float)(var5.getScaledHeightStatic(Minecraft.getInstance()) / 2);
      float var9 = 5.0F + var6 * (float)(aHW.y + aHW.B) / 2.0F;
      switch(Ci.b[this.e.getSettingType().ordinal()]) {
      case 1:
         if((float)var1 < var7 + var9 - 85.0F || (float)var1 > var7 + var9 || (double)var2 < this.c() || (double)var2 > this.c() + 17.0D) {
            break;
         }

         this.e.getCheckBoxProperty().invert();
         EventManager.call(new SettingEvent(this.c.f(), this.e.getName(), this.e.getDisplayName(), this.e.getCheckBoxProperty()));
      case 2:
      case 7:
         if((float)var1 < var7 + var9 - 85.0F || (float)var1 > var7 + var9 - 5.0F || (double)var2 < this.c() + 2.0D || (double)var2 > this.c() + 15.0D) {
            break;
         }

         if(var3 == 0) {
            this.h = true;
         }

         if(var3 != 1 || this.e.getSettingType() != SettingType.COLOR_PICKER) {
            break;
         }

         Setting$ColorPickerMode[] var10 = Setting$ColorPickerMode.values();
         int var11 = (Arrays.binarySearch(var10, this.e.getColorPickerMode()) + 1) % var10.length;
         if(this.e.getColorPickedDisabledModes() == null) {
            this.e.setColorPickerMode(var10[var11]);
         }

         if(!this.e.getColorPickedDisabledModes().isEmpty()) {
            int var13 = 0;
            if(var13 < var10.length - 1) {
               Setting$ColorPickerMode var12 = var10[(var11 + var13) % var10.length];
               if(!this.e.getColorPickedDisabledModes().contains(var12)) {
                  this.e.setColorPickerMode(var10[(var13 + var11) % var10.length]);
               }

               ++var13;
            }
         }
      case 3:
         if((float)var1 < var7 + var9 - 85.0F || (float)var1 > var7 + var9 - 5.0F || (double)var2 < this.c() + 2.0D || (double)var2 > this.c() + 15.0D || var3 != 0) {
            break;
         }

         Minecraft.getInstance().displayGuiScreen(this.e.N());
      case 5:
         if((float)var1 >= var7 + var9 - 85.0F && (float)var1 <= var7 + var9 - 5.0F && (double)var2 >= this.c() + 2.0D && (double)var2 <= this.c() + 15.0D) {
            this.f = !this.f;
         }

         if(!this.f) {
            break;
         }

         double var15 = this.c() + 17.0D;
         List var19 = (List)this.e.getComboBoxProperty().getAcceptableValues().stream().filter(this::lambda$mouseClicked$6).collect(Collectors.toList());
         if((float)var1 >= var7 + var9 - 85.0F && (float)var1 <= var7 + var9 - 5.0F && (double)var2 >= var15 && (double)var2 <= var15 + (double)(var19.size() * 17)) {
            Iterator var22 = var19.iterator();
            if(var22.hasNext()) {
               String var14 = (String)var22.next();
               if((float)var1 >= var7 + var9 - 85.0F && (float)var1 <= var7 + var9 - 5.0F && (double)var2 >= var15 && (double)var2 <= var15 + 17.0D) {
                  this.e.setComboBoxValue(var14);
                  EventManager.call(new SettingEvent(this.c.f(), this.e.getName(), this.e.getComboBoxProperty()));
                  this.f = false;
               }

               var15 = var15 + 17.0D;
            }
         }

         this.f = false;
      case 6:
         if((float)var1 >= var7 + var9 - 85.0F && (float)var1 <= var7 + var9 - 5.0F && (double)var2 >= this.c() + 2.0D && (double)var2 <= this.c() + 15.0D) {
            this.f = !this.f;
         }

         if(!this.f) {
            break;
         }

         double var17 = this.c() + 17.0D;
         if((float)var1 >= var7 + var9 - 85.0F && (float)var1 <= var7 + var9 - 5.0F && (double)var2 >= var17 && (double)var2 <= var17 + (double)(this.e.getSelectBoxProperty().getAcceptableValues().size() * 17)) {
            Iterator var20 = this.e.getSelectBoxProperty().getAcceptableValues().iterator();
            if(var20.hasNext()) {
               String var23 = (String)var20.next();
               if((float)var1 >= var7 + var9 - 85.0F && (float)var1 <= var7 + var9 - 5.0F && (double)var2 >= var17 && (double)var2 <= var17 + 17.0D) {
                  if(((List)this.e.getSelectBoxProperty().get()).contains(var23)) {
                     ((List)this.e.getSelectBoxProperty().get()).remove(var23);
                  }

                  ((List)this.e.getSelectBoxProperty().get()).add(var23);
                  EventManager.call(new SettingEvent(this.c.f(), this.e.getName(), this.e.getSelectBoxProperty()));
               }

               var17 = var17 + 17.0D;
            }
         }

         this.f = false;
      case 4:
         if((float)var1 >= var7 + var9 - 85.0F && (float)var1 <= var7 + var9 - 5.0F && (double)var2 >= this.c() + 2.0D && (double)var2 <= this.c() + 15.0D) {
            this.e.setTextHovered(!this.e.isTextHovered());
         }

         if(this.e.isTextHovered()) {
            this.e.setTextHovered(false);
         }
      }

      if(acE.b() == null) {
         b(new String[4]);
      }

   }

   public void a(char var1, int var2) {
      String[] var3 = b();
      if(this.e.getSettingType() == SettingType.TEXTBOX && this.e.isTextHovered()) {
         if(var2 == 1 || var2 == 28) {
            this.e.setTextHovered(false);
         }

         if(var2 != 14 && var2 != 157 && var2 != 29 && var2 != 54 && var2 != 42 && var2 != 15 && var2 != 58 && var2 != 211 && var2 != 199 && var2 != 210 && var2 != 200 && var2 != 208 && var2 != 205 && var2 != 203 && var2 != 56 && var2 != 184 && var2 != 197 && var2 != 70 && var2 != 207 && var2 != 201 && var2 != 209 && var2 != 221 && var2 != 59 && var2 != 60 && var2 != 61 && var2 != 62 && var2 != 63 && var2 != 64 && var2 != 65 && var2 != 66 && var2 != 67 && var2 != 68 && var2 != 87 && var2 != 88) {
            this.e.getTextBoxValue2().append(Character.valueOf(var1));
         }
      }

   }

   public boolean d() {
      return this.f;
   }

   public void a(float var1) {
      this.g = var1;
   }

   private boolean lambda$mouseClicked$6(String var1) {
      String[] var2 = b();
      return !var1.equals(this.e.getComboBoxProperty().get());
   }

   private static boolean lambda$getVerticalPosition$5(aif var0) {
      String[] var1 = b();
      return var0.e.getSupplier() != null?((Boolean)var0.e.getSupplier().get()).booleanValue():true;
   }

   private static boolean lambda$drawScreen$4(aif var0) {
      String[] var1 = b();
      return var0.e.getSupplier() != null?((Boolean)var0.e.getSupplier().get()).booleanValue():true;
   }

   private static boolean lambda$drawScreen$3(aif var0) {
      String[] var1 = b();
      return var0.e.getSupplier() != null?((Boolean)var0.e.getSupplier().get()).booleanValue():true;
   }

   private boolean lambda$drawScreen$2(String var1) {
      String[] var2 = b();
      return !var1.equals(this.e.getComboBoxProperty().get());
   }

   private static boolean lambda$drawScreen$1(aif var0) {
      String[] var1 = b();
      return var0.e.getSupplier() != null?((Boolean)var0.e.getSupplier().get()).booleanValue():true;
   }

   private static boolean lambda$drawScreen$0(aif var0) {
      String[] var1 = b();
      return var0.e.getSupplier() != null?((Boolean)var0.e.getSupplier().get()).booleanValue():true;
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      b((String[])null);
   }
}
