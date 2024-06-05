package cc.novoline.gui.screen.dropdown;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.dropdown.Module$1;
import cc.novoline.gui.screen.dropdown.Setting;
import cc.novoline.gui.screen.dropdown.Tab;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_18;
import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class Module {
   private final AbstractModule module;
   public int yPerModule;
   public int y;
   public Tab tab;
   public boolean opened;
   public List settings;
   public Timer hoverTimer;
   private double length;
   private double anim;
   private int alph;
   float fraction;
   float fractionBackground;
   private float alpha;

   public Module(AbstractModule var1, Tab var2) {
      Tab.e();
      this.settings = new CopyOnWriteArrayList();
      this.hoverTimer = new Timer();
      this.length = 3.0D;
      this.anim = 5.0D;
      this.alph = 0;
      this.fraction = 0.0F;
      this.fractionBackground = 0.0F;
      this.alpha = 0.0F;
      this.module = var1;
      this.tab = var2;
      Iterator var4 = Manager.getSettingsByMod(var1).iterator();
      if(var4.hasNext()) {
         cc.novoline.gui.screen.setting.Setting var5 = (cc.novoline.gui.screen.setting.Setting)var4.next();
         this.settings.add(new Setting(var5, this));
      }

   }

   public void drawScreen(int var1, int var2) {
      Tab.f();
      Minecraft var4 = Minecraft.getInstance();
      int var5 = var4.getDebugFPS();
      if(this.module.isEnabled() && this.fraction < 1.0F) {
         this.fraction = (float)((double)this.fraction + 0.0025D * (double)(2000 / var5));
      }

      if(!this.module.isEnabled() && this.fraction > 0.0F) {
         this.fraction = (float)((double)this.fraction - 0.0025D * (double)(2000 / var5));
      }

      if(!this.module.isEnabled()) {
         if(this.isHovered(var1, var2) && this.fractionBackground < 1.0F) {
            this.fractionBackground = (float)((double)this.fractionBackground + 0.0025D * (double)(2000 / var5));
         }

         if(!this.isHovered(var1, var2) && this.fractionBackground > 0.0F) {
            this.fractionBackground = (float)((double)this.fractionBackground - 0.0025D * (double)(2000 / var5));
         }
      }

      this.fraction = MathHelper.clamp_float(this.fraction, 0.0F, 1.0F);
      this.fractionBackground = MathHelper.clamp_float(this.fractionBackground, 0.0F, 1.0F);
      if(this.yPerModule < this.getY()) {
         this.yPerModule = (int)((double)this.yPerModule + (double)(this.getY() + 9 - this.yPerModule) * 0.1D);
      }

      if(this.yPerModule > this.getY()) {
         this.yPerModule = (int)((double)this.yPerModule + (double)(this.getY() - this.yPerModule) * 0.1D);
      }

      this.y = (int)(this.tab.getPosY() + 15.0F);
      Iterator var6 = this.tab.getModules().iterator();
      if(var6.hasNext()) {
         Module var7 = (Module)var6.next();
         if(var7 == this) {
            ;
         }

         this.y += var7.yPerModule;
      }

      HUD var11 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      Color var12 = var11.getColor();
      new Color(16777215);
      if(var12.getRed() > 220 && var12.getBlue() > 220 && var12.getGreen() > 220) {
         Color var8 = var12.darker().darker();
      }

      Gui.drawRect((double)this.tab.getPosX(), (double)this.y, (double)(this.tab.getPosX() + 100.0F), (double)(this.y + this.yPerModule), (new Color(40, 40, 40, 255)).getRGB());
      if(!this.module.isEnabled() && this.fraction == 0.0F) {
         Gui.drawRect((double)this.tab.getPosX(), (double)this.y, (double)(this.tab.getPosX() + 100.0F), (double)(this.y + 14), this.interpolateColor(new Color(40, 40, 40, 255), new Color(29, 29, 29, 255), MathHelper.clamp_float(this.fractionBackground, 0.0F, 1.0F)));
      }

      Gui.drawRect((double)this.tab.getPosX(), (double)this.y, (double)(this.tab.getPosX() + 100.0F), (double)(this.y + 14), this.interpolateColor(new Color(40, 40, 40, 255), var12, MathHelper.clamp_float(this.fraction, 0.0F, 1.0F)));
      Fonts$SF$SF_18.SF_18.drawString(this.module.getName(), (double)(this.tab.getPosX() + 2.0F), (double)((float)(this.y + 4)), -1, true);
      if(!this.settings.isEmpty()) {
         double var9 = (double)var5 / 8.3D;
         if(this.opened && this.length > -3.0D) {
            this.length -= 3.0D / var9;
         }

         if(!this.opened && this.length < 3.0D) {
            this.length += 3.0D / var9;
         }

         if(this.opened && this.anim < 8.0D) {
            this.anim += 3.0D / var9;
         }

         if(!this.opened && this.anim > 5.0D) {
            this.anim -= 3.0D / var9;
         }

         RenderUtils.drawArrow((double)(this.tab.getPosX() + 92.0F), (double)this.y + this.anim, 2, -1, this.length);
      }

      if(this.opened || this.yPerModule != 14) {
         ScaledResolution var13 = new ScaledResolution(Minecraft.getInstance());
         if(this.yPerModule != this.getY() && var13.getScaleFactor() != 1) {
            GL11.glScissor(0, var13.getScaledHeightStatic(Minecraft.getInstance()) * 2 - this.y * 2 - this.yPerModule * 2, var13.getScaledWidthStatic(Minecraft.getInstance()) * 2, this.yPerModule * 2);
            GL11.glEnable(3089);
            this.settings.stream().filter(Module::lambda$drawScreen$0).forEach(Module::lambda$drawScreen$1);
            GL11.glDisable(3089);
            this.settings.stream().filter(Module::lambda$drawScreen$2).forEach(Module::lambda$drawScreen$3);
         }

         this.settings.stream().filter(Module::lambda$drawScreen$4).forEach(Module::lambda$drawScreen$5);
      }

      this.settings.forEach(Module::lambda$drawScreen$6);
   }

   private int interpolateColor(Color var1, Color var2, float var3) {
      int var4 = (int)((float)var1.getRed() + (float)(var2.getRed() - var1.getRed()) * var3);
      int var5 = (int)((float)var1.getGreen() + (float)(var2.getGreen() - var1.getGreen()) * var3);
      int var6 = (int)((float)var1.getBlue() + (float)(var2.getBlue() - var1.getBlue()) * var3);
      int var7 = (int)((float)var1.getAlpha() + (float)(var2.getAlpha() - var1.getAlpha()) * var3);

      try {
         return (new Color(var4, var5, var6, var7)).getRGB();
      } catch (Exception var9) {
         return -1;
      }
   }

   public void keyTyped(char var1, int var2) {
      boolean var3 = Tab.e();
      if(this.opened) {
         this.settings.stream().filter(Module::lambda$keyTyped$7).forEach(Module::lambda$keyTyped$8);
      }

   }

   public int getY() {
      boolean var1 = Tab.e();
      if(this.opened) {
         int var2 = 17;
         Iterator var3 = ((List)this.settings.stream().filter(Module::lambda$getY$9).collect(Collectors.toList())).iterator();
         if(var3.hasNext()) {
            Setting var4 = (Setting)var3.next();
            switch(Module$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[var4.setting.getSettingType().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
               var2 += 15;
            case 8:
               if(var4.opened) {
                  var2 += 15 + var4.setting.getSelectBoxProperty().getAcceptableValues().size() * 17;
               }

               var2 += 15;
            }
         }

         return this.tab.modules.indexOf(this) == this.tab.modules.size() - 1?var2:var2;
      } else {
         return 14;
      }
   }

   public void mouseClicked(int var1, int var2, int var3) throws IOException {
      boolean var4 = Tab.f();
      if(this.isHovered(var1, var2)) {
         switch(var3) {
         case 0:
            this.module.toggle();
         case 1:
            if(!Manager.getSettingsByMod(this.module).isEmpty()) {
               if(!this.opened && ((Boolean)((ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class)).getClosePrevious().get()).booleanValue()) {
                  this.tab.modules.forEach(Module::lambda$mouseClicked$10);
               }

               this.opened = !this.opened;
               Iterator var5 = Manager.getSettingsByMod(this.module).iterator();
               if(var5.hasNext()) {
                  cc.novoline.gui.screen.setting.Setting var6 = (cc.novoline.gui.screen.setting.Setting)var5.next();
                  if(var6.getSettingType() == SettingType.TEXTBOX) {
                     var6.setTextHovered(false);
                  }
               }
            }
         }
      }

      if(this.opened) {
         this.settings.stream().filter(Module::lambda$mouseClicked$11).forEach(Module::lambda$mouseClicked$12);
      }

   }

   public void mouseReleased(int var1, int var2, int var3) {
      boolean var4 = Tab.e();
      if(this.opened) {
         this.settings.stream().filter(Module::lambda$mouseReleased$13).forEach(Module::lambda$mouseReleased$14);
      }

   }

   public boolean isHovered(int var1, int var2) {
      Tab.e();
      this.y = (int)(this.tab.getPosY() + 15.0F);
      Iterator var4 = this.tab.getModules().iterator();
      if(var4.hasNext()) {
         Module var5 = (Module)var4.next();
         if(var5 == this) {
            ;
         }

         this.y += var5.yPerModule;
      }

      return this.opened?(float)var1 >= this.tab.getPosX() && var2 >= this.y && (float)var1 <= this.tab.getPosX() + 101.0F && var2 <= this.y + 14:(float)var1 >= this.tab.getPosX() && var2 >= this.y && (float)var1 <= this.tab.getPosX() + 101.0F && var2 <= this.y + this.yPerModule;
   }

   private void update() {
   }

   public AbstractModule getModule() {
      return this.module;
   }

   private static void lambda$mouseReleased$14(int var0, int var1, int var2, Setting var3) {
      var3.mouseReleased(var0, var1, var2);
   }

   private static boolean lambda$mouseReleased$13(Setting var0) {
      boolean var1 = Tab.f();
      return var0.setting.getSupplier() != null?((Boolean)var0.setting.getSupplier().get()).booleanValue():true;
   }

   private static void lambda$mouseClicked$12(int var0, int var1, int var2, Setting var3) {
      Setting var10000 = var3;
      int var10001 = var0;
      int var10002 = var1;
      int var10003 = var2;

      try {
         var10000.mouseClicked(var10001, var10002, var10003);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   private static boolean lambda$mouseClicked$11(Setting var0) {
      boolean var1 = Tab.f();
      return var0.setting.getSupplier() != null?((Boolean)var0.setting.getSupplier().get()).booleanValue():true;
   }

   private static void lambda$mouseClicked$10(Module var0) {
      boolean var1 = Tab.f();
      if(var0.opened) {
         var0.opened = false;
      }

   }

   private static boolean lambda$getY$9(Setting var0) {
      boolean var1 = Tab.e();
      return var0.setting.getSupplier() != null?((Boolean)var0.setting.getSupplier().get()).booleanValue():true;
   }

   private static void lambda$keyTyped$8(char var0, int var1, Setting var2) {
      var2.keyTyped(var0, var1);
   }

   private static boolean lambda$keyTyped$7(Setting var0) {
      boolean var1 = Tab.e();
      return var0.setting.getSupplier() != null?((Boolean)var0.setting.getSupplier().get()).booleanValue():true;
   }

   private static void lambda$drawScreen$6(Setting var0) {
      var0.setPercent(0.0F);
   }

   private static void lambda$drawScreen$5(int var0, int var1, Setting var2) {
      var2.drawScreen(var0, var1);
   }

   private static boolean lambda$drawScreen$4(Setting var0) {
      boolean var1 = Tab.f();
      return var0.setting.getSupplier() != null?((Boolean)var0.setting.getSupplier().get()).booleanValue():true;
   }

   private static void lambda$drawScreen$3(Setting var0) {
      var0.setPercent(0.0F);
   }

   private static boolean lambda$drawScreen$2(Setting var0) {
      boolean var1 = Tab.f();
      return var0.setting.getSupplier() != null && !((Boolean)var0.setting.getSupplier().get()).booleanValue();
   }

   private static void lambda$drawScreen$1(int var0, int var1, Setting var2) {
      var2.drawScreen(var0, var1);
   }

   private static boolean lambda$drawScreen$0(Setting var0) {
      boolean var1 = Tab.e();
      return var0.setting.getSupplier() != null?((Boolean)var0.setting.getSupplier().get()).booleanValue():true;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
