package cc.novoline.modules.visual.tabgui;

import cc.novoline.Novoline;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.visual.HUD;
import cc.novoline.modules.visual.TabGUI;
import cc.novoline.modules.visual.tabgui.TabModule;
import cc.novoline.modules.visual.tabgui.TabValue;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_18;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import net.acE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public final class TabType {
   private final List modules;
   private final EnumModuleType type;
   private final TabGUI tabGUI;
   private boolean opened;
   private float i;

   public TabType(TabGUI var1, EnumModuleType var2) {
      TabValue.f();
      super();
      this.modules = new ObjectArrayList();
      this.i = 0.0F;
      this.type = var2;
      this.tabGUI = var1;
      this.setOpened(false);
      Iterator var4 = var1.getNovoline().getModuleManager().getModuleListByCategory(var2).iterator();
      if(var4.hasNext()) {
         AbstractModule var5 = (AbstractModule)var4.next();
         this.modules.add(new TabModule(var5, this));
      }

   }

   public int g() {
      String[] var1 = TabValue.f();
      if(((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).isEnabled()) {
         HUD var2 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
         return var2.getHudElements().contains("Name")?(var2.C().equals("Normal")?15:20):5;
      } else {
         return 5;
      }
   }

   public void e() {
      double var2;
      double var4;
      label0: {
         var2 = (double)(this.g() + this.tabGUI.getTypes().indexOf(this) * 12);
         TabValue.f();
         var4 = var2 + 12.0D;
         double var6 = (double)(Minecraft.getInstance().getDebugFPS() / 13);
         if(this.isSelected()) {
            if(this.i >= 3.0F) {
               break label0;
            }

            this.i = (float)MathHelper.clamp_double((double)this.i + 3.0D / var6, 0.0D, 3.0D);
         }

         if(this.i > 0.0F) {
            this.i = (float)MathHelper.clamp_double((double)this.i - 3.0D / var6, 0.0D, 3.0D);
         }
      }

      String var8 = this.type.name().charAt(0) + this.type.name().substring(1).toLowerCase();
      Gui.drawRect(0.0D, var2, 65.0D, var4, (new Color(20, 20, 20, 170)).getRGB());
      if(this.isSelected()) {
         float var9 = (float)Minecraft.getSystemTime();
         int var10 = 0;
         int var11 = (int)var2 + 1;
         if((double)var11 < var4 - 1.0D) {
            Gui.drawRect(1, var11, 2, var11 + 1, ((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).a(var10, var9, 3));
            var10 = var10 + 20;
            var9 = var9 - 200.0F;
            ++var11;
         }
      }

      Fonts$SF$SF_18.SF_18.drawString(var8, (double)(3.0F + this.i), (double)((float)(var2 + 3.0D)), -1, true);
      if(this.isOpened()) {
         this.modules.forEach(TabModule::render);
      }

      if(acE.b() == null) {
         TabValue.b(new String[4]);
      }

   }

   private double[] getCoords() {
      TabValue.f();
      double var2 = (double)(15 + this.tabGUI.getTypes().indexOf(this) * 12);
      String var4 = this.type.name().toUpperCase();
      byte var5 = -1;
      switch(var4.hashCode()) {
      case 1993470708:
         if(!var4.equals("COMBAT")) {
            break;
         }

         var5 = 0;
      case 678949039:
         if(!var4.equals("MOVEMENT")) {
            break;
         }

         var5 = 1;
      case -1932423455:
         if(!var4.equals("PLAYER")) {
            break;
         }

         var5 = 2;
      case -1146279864:
         if(!var4.equals("EXPLOITS")) {
            break;
         }

         var5 = 3;
      case 1185082643:
         if(!var4.equals("VISUALS")) {
            break;
         }

         var5 = 4;
      case 2366700:
         if(var4.equals("MISC")) {
            var5 = 5;
         }
      }

      switch(var5) {
      case 0:
         return new double[]{61.0D, (double)((float)(var2 + 5.0D))};
      case 1:
      case 2:
      case 3:
         return new double[]{60.5D, (double)((float)(var2 + 5.0D))};
      case 4:
      case 5:
      default:
         return new double[]{60.0D, (double)((float)(var2 + 5.0D))};
      }
   }

   public TabModule getSelectedModule() {
      TabValue.f();
      Iterator var2 = this.modules.iterator();
      if(var2.hasNext()) {
         TabModule var3 = (TabModule)var2.next();
         if(var3.isSelected()) {
            return var3;
         }
      }

      return null;
   }

   public boolean isSelected() {
      String[] var1 = TabValue.f();
      return this.tabGUI.getTypes().indexOf(this) == this.tabGUI.getTypeN();
   }

   public boolean isOpened() {
      return this.opened;
   }

   public void setOpened(boolean var1) {
      this.opened = var1;
   }

   public TabGUI getTabGUI() {
      return this.tabGUI;
   }

   public List getModules() {
      return this.modules;
   }

   public EnumModuleType getType() {
      return this.type;
   }
}
