package cc.novoline.gui.screen.click;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.click.DiscordGUI;
import cc.novoline.gui.screen.click.Module;
import cc.novoline.gui.screen.click.Scroll;
import cc.novoline.gui.screen.click.Tab$1;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.fonts.impl.Fonts$ICONFONT$ICONFONT_24;
import cc.novoline.utils.fonts.impl.Fonts$ICONFONT$ICONFONT_35;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_16;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_20;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.acE;
import net.minecraft.client.gui.Gui;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

public class Tab {
   private final Novoline novoline;
   private final EnumModuleType category;
   private final List moduleList = new CopyOnWriteArrayList();
   private final int y;
   private boolean selected;
   private int block = 5;

   public Tab(@NotNull Novoline var1, @NotNull EnumModuleType var2, int var3) {
      this.novoline = var1;
      this.category = var2;
      Scroll.b();
      this.y = var3;
      int var5 = 30;
      ObjectIterator var6 = this.novoline.getModuleManager().getModuleManager().values().iterator();
      if(var6.hasNext()) {
         ModuleHolder var7 = (ModuleHolder)var6.next();
         AbstractModule var8 = var7.getModule();
         if(var8.getType().equals(this.category)) {
            this.moduleList.add(new Module(var8, var5));
            var5 = var5 + 18;
         }
      }

   }

   public void drawScreen(int var1, int var2) {
      Scroll.b();
      DiscordGUI var4 = Novoline.getInstance().getDiscordGUI();
      if(this.selected && this.areButtonsHovered(var1, var2)) {
         Scroll var5 = DiscordGUI.scroll();
         switch(Tab$1.$SwitchMap$cc$novoline$gui$screen$click$Scroll[var5.ordinal()]) {
         case 1:
            if(this.moduleList.isEmpty() || ((Module)this.moduleList.get(this.moduleList.size() - 1)).getY() <= var4.getHeight() - 14) {
               break;
            }

            Iterator var6 = this.moduleList.iterator();
            if(var6.hasNext()) {
               Module var7 = (Module)var6.next();
               var7.setY(var7.getY() - 7);
            }
         case 2:
            if(((Module)this.moduleList.get(0)).getY() < 30) {
               Iterator var11 = this.moduleList.iterator();
               if(var11.hasNext()) {
                  Module var13 = (Module)var11.next();
                  var13.setY(var13.getY() + 7);
               }
            }
         }
      }

      int var10 = var4.getXCoordinate();
      int var12 = var4.getYCoordinate();
      int var14 = ((ClickGUI)this.novoline.getModuleManager().getModule(ClickGUI.class)).getGUIColor();
      if(this.isHovered(var1, var2)) {
         if(this.selected) {
            Gui.drawRect(var10, var12 + this.y - this.block, var10 + 2, var12 + this.y + this.block, -1);
         }

         Gui.drawRect(var10, var12 + this.y - 5, var10 + 2, var12 + this.y + 5, -1);
         RenderUtils.drawRoundedRect((float)(var10 - Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.stringWidth(this.category.name().substring(0, 1).toUpperCase() + this.category.name().substring(1).toLowerCase()) - 12), (float)(var12 + this.y - 6), (float)(Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.stringWidth(this.category.name().substring(0, 1).toUpperCase() + this.category.name().substring(1).toLowerCase()) + 7), 11.0F, 5.0F, -13684945);
         Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawString(this.category.name().substring(0, 1).toUpperCase() + this.category.name().substring(1).toLowerCase(), (float)(var10 - Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.stringWidth(this.category.name().substring(0, 1).toUpperCase() + this.category.name().substring(1).toLowerCase()) - 9), (float)(var12 + this.y - 3), -1);
      }

      if(this.selected) {
         Gui.drawRect(var10, var12 + this.y - this.block, var10 + 2, var12 + this.y + this.block, -1);
      }

      if(this.selected && this.block <= 10) {
         ++this.block;
      }

      if(!this.selected) {
         this.block = 5;
      }

      RenderUtils.drawFilledCircle((float)(var10 + 22), (float)(var12 + this.y), 15.0F, -13223617);
      Fonts$ICONFONT$ICONFONT_35.ICONFONT_35.drawCenteredString(this.getLetterForTab(), (float)this.getCoords()[0], (float)this.getCoords()[1], this.isHovered(var1, var2)?var14:-1);
      if(this.selected) {
         Fonts$ICONFONT$ICONFONT_24.ICONFONT_24.drawString(this.getLetterForTab(), (float)(var10 + (this.getLetterForTab().equals("D")?49:50)), (float)(var12 + 7), -1025);
         Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.drawString(this.category.name().substring(0, 1).toUpperCase() + this.category.name().substring(1).toLowerCase(), (float)(var10 + 63), (float)(var12 + 7), -1025);
         GL11.glPushMatrix();
         GL11.glScissor(0, var4.sHeight() - var4.getYCoordinate() * 2 - var4.getHeight() * 2, 1920, var4.getHeight() * 2 - 42);
         GL11.glEnable(3089);
         this.moduleList.stream().sorted(Tab::lambda$drawScreen$0).forEach(Tab::lambda$drawScreen$1);
         Iterator var8 = this.moduleList.iterator();
         if(var8.hasNext()) {
            Module var9 = (Module)var8.next();
            var9.drawScreen2(var1, var2);
         }

         GL11.glDisable(3089);
         GL11.glPopMatrix();
      }

      if(acE.b() == null) {
         Scroll.b(new int[5]);
      }

   }

   protected void mouseClicked(int var1, int var2, int var3) {
      Scroll.b();
      DiscordGUI var5 = Novoline.getInstance().getDiscordGUI();
      Iterator var6 = this.moduleList.iterator();
      if(var6.hasNext()) {
         Module var7 = (Module)var6.next();
         var7.mouseClicked(var1, var2, var3);
      }

      switch(var3) {
      case 0:
         var6 = this.moduleList.iterator();
         if(var6.hasNext()) {
            Module var12 = (Module)var6.next();
            if(var12.isHovered(var1, var2) && var2 >= var5.getYCoordinate() + 20 && var2 <= var5.getYCoordinate() + var5.getHeight()) {
               var12.getData().toggle();
            }
         }
      case 1:
         var6 = this.moduleList.iterator();
         if(var6.hasNext()) {
            Module var13 = (Module)var6.next();
            if(var13.isHovered(var1, var2) && var2 >= var5.getYCoordinate() + 20 && var2 <= var5.getYCoordinate() + var5.getHeight()) {
               Iterator var8 = this.moduleList.iterator();
               if(var8.hasNext()) {
                  Module var9 = (Module)var8.next();
                  if(!var9.equals(var13)) {
                     var9.setSelected(false);
                  }
               }

               var13.setSelected(!var13.isSelected());
            }
         }
      default:
      }
   }

   public void mouseReleased(int var1, int var2, int var3) {
      this.moduleList.forEach(Tab::lambda$mouseReleased$2);
   }

   protected void keyTyped(char var1, int var2) {
      Scroll.b();
      Iterator var4 = this.moduleList.iterator();
      if(var4.hasNext()) {
         Module var5 = (Module)var4.next();
         var5.keyTyped(var1, var2);
      }

   }

   private boolean areButtonsHovered(int var1, int var2) {
      Scroll.b();
      DiscordGUI var4 = Novoline.getInstance().getDiscordGUI();
      return var1 >= var4.getXCoordinate() + 45 && var1 <= var4.getXCoordinate() + 45 + 110 && var2 >= var4.getYCoordinate() + 22 && var2 <= var4.getYCoordinate() + 300;
   }

   public boolean isHovered(int var1, int var2) {
      Scroll.b();
      DiscordGUI var4 = Novoline.getInstance().getDiscordGUI();
      return var1 >= var4.getXCoordinate() + 8 && var1 <= var4.getXCoordinate() + 35 && var2 >= var4.getYCoordinate() + this.y - 15 && var2 <= var4.getYCoordinate() + this.y + 15;
   }

   private boolean anyModsExtended() {
      return this.moduleList.stream().anyMatch(Module::isSelected);
   }

   @NotNull
   private String getLetterForTab() {
      String var2 = this.category.name();
      Scroll.b();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 1993470708:
         if(!var2.equals("COMBAT")) {
            break;
         }

         var3 = 0;
      case 678949039:
         if(!var2.equals("MOVEMENT")) {
            break;
         }

         var3 = 1;
      case -1932423455:
         if(!var2.equals("PLAYER")) {
            break;
         }

         var3 = 2;
      case 1185082643:
         if(!var2.equals("VISUALS")) {
            break;
         }

         var3 = 3;
      case 2366700:
         if(!var2.equals("MISC")) {
            break;
         }

         var3 = 4;
      case -1146279864:
         if(var2.equals("EXPLOITS")) {
            var3 = 5;
         }
      }

      switch(var3) {
      case 0:
         return "D";
      case 1:
         return "A";
      case 2:
         return "B";
      case 3:
         return "C";
      case 4:
         return "F";
      case 5:
         return "G";
      default:
         return "";
      }
   }

   private double[] getCoords() {
      String var2 = this.category.name();
      Scroll.b();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 1993470708:
         if(!var2.equals("COMBAT")) {
            break;
         }

         var3 = 0;
      case 678949039:
         if(!var2.equals("MOVEMENT")) {
            break;
         }

         var3 = 1;
      case -1932423455:
         if(!var2.equals("PLAYER")) {
            break;
         }

         var3 = 2;
      case 1185082643:
         if(!var2.equals("VISUALS")) {
            break;
         }

         var3 = 3;
      case -1146279864:
         if(!var2.equals("EXPLOITS")) {
            break;
         }

         var3 = 4;
      case 2366700:
         if(var2.equals("MISC")) {
            var3 = 5;
         }
      }

      switch(var3) {
      case 0:
         return new double[]{(double)Novoline.getInstance().getDiscordGUI().getXCoordinate() + 21.5D, (double)(Novoline.getInstance().getDiscordGUI().getYCoordinate() + this.y) - 4.5D};
      case 1:
         return new double[]{(double)Novoline.getInstance().getDiscordGUI().getXCoordinate() + 22.5D, (double)(Novoline.getInstance().getDiscordGUI().getYCoordinate() + this.y - 5)};
      case 2:
         return new double[]{(double)Novoline.getInstance().getDiscordGUI().getXCoordinate() + 22.2D, (double)(Novoline.getInstance().getDiscordGUI().getYCoordinate() + this.y - 6)};
      case 3:
         return new double[]{(double)Novoline.getInstance().getDiscordGUI().getXCoordinate() + 22.0D, (double)(Novoline.getInstance().getDiscordGUI().getYCoordinate() + this.y - 5)};
      case 4:
         return new double[]{(double)Novoline.getInstance().getDiscordGUI().getXCoordinate() + 22.3D, (double)(Novoline.getInstance().getDiscordGUI().getYCoordinate() + this.y) - 5.6D};
      case 5:
         return new double[]{(double)Novoline.getInstance().getDiscordGUI().getXCoordinate() + 22.5D, (double)(Novoline.getInstance().getDiscordGUI().getYCoordinate() + this.y - 5)};
      default:
         return new double[]{(double)Novoline.getInstance().getDiscordGUI().getXCoordinate() + 22.0D, (double)(Novoline.getInstance().getDiscordGUI().getYCoordinate() + this.y - 5)};
      }
   }

   public List getModuleList() {
      return this.moduleList;
   }

   public boolean isSelected() {
      return this.selected;
   }

   public void setSelected(boolean var1) {
      int[] var2 = Scroll.b();
      if(!(this.selected = var1)) {
         Iterator var3 = Manager.getSettingList().iterator();
         if(var3.hasNext()) {
            Setting var4 = (Setting)var3.next();
            switch(Tab$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[var4.getSettingType().ordinal()]) {
            case 1:
            case 2:
               var4.setOpened(false);
            case 3:
               var4.setTextHovered(false);
            }
         }
      }

   }

   private static void lambda$mouseReleased$2(int var0, int var1, int var2, Module var3) {
      var3.mouseReleased(var0, var1, var2);
   }

   private static void lambda$drawScreen$1(int var0, int var1, Module var2) {
      var2.drawScreen(var0, var1);
   }

   private static int lambda$drawScreen$0(Module var0, Module var1) {
      return Boolean.compare(var0.isSelected(), var1.isSelected());
   }
}
