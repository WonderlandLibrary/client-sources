package cc.novoline.gui.screen.click;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.click.DiscordGUI;
import cc.novoline.gui.screen.click.Module$1;
import cc.novoline.gui.screen.click.Scroll;
import cc.novoline.gui.screen.click.Tab;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.binds.KeyboardKeybind;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_26;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_12;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_20;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

public class Module {
   private final AbstractModule data;
   private boolean selected;
   private boolean listening;
   private int y;

   public Module(AbstractModule var1, int var2) {
      this.data = var1;
      this.y = var2;
   }

   public void drawScreen(int var1, int var2) {
      Scroll.b();
      DiscordGUI var4 = Novoline.getInstance().getDiscordGUI();
      ClickGUI var5 = (ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class);
      boolean var6 = false;
      int var7 = var4.getXCoordinate();
      int var8 = var4.getYCoordinate();
      if(this.isHovered(var1, var2) || this.selected) {
         Gui.drawRect((double)(var7 + 45), (double)(var8 + this.y - 4), (double)(var7 + 45 + 110), var8 + this.y + Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.getHeight() + 4 > var8 + var4.getHeight()?(double)MathHelper.clamp_float((float)(var8 + this.y + Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.getHeight() + 4), (float)(var8 + this.y - 4), (float)(var8 + var4.getHeight())):(double)(var8 + this.y + Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.getHeight() + 4), -13223618);
      }

      Fonts$SFBOLD$SFBOLD_26.SFBOLD_26.drawString("#", (float)(var7 + 50), (float)(var8 + this.y - 2), -10461856);
      if(this.data.isEnabled()) {
         Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.drawString(this.data.getName(), (float)(var7 + 63), (float)(var8 + this.y), -1843229);
      }

      Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.drawString(this.data.getName(), (float)(var7 + 63), (float)(var8 + this.y), -7961722);

      try {
         Tab var9 = var4.getSelectedTab();
         if(this.isHovered(var1, var2) && (var9 == null || var9.getModuleList().stream().noneMatch(Module::isListening)) && StringUtils.isNotBlank(this.getData().getDescription())) {
            String var10 = this.getData().getDescription();
            Fonts$SFTHIN$SFTHIN_12.SFTHIN_12.drawString(var10, (double)(var7 + 147 + var4.getWidth() - Fonts$SFTHIN$SFTHIN_12.SFTHIN_12.stringWidth(var10)), (double)(var8 - 6), 16777215, true);
         }
      } catch (NullPointerException var11) {
         ;
      }

   }

   void drawScreen2(int var1, int var2) {
      Scroll.b();
      DiscordGUI var4 = Novoline.getInstance().getDiscordGUI();
      List var5 = Manager.getSettingsByMod(this.data);
      if(this.selected) {
         Scroll var6 = DiscordGUI.scroll();
         if(var5 != null && !var5.isEmpty()) {
            switch(Module$1.$SwitchMap$cc$novoline$gui$screen$click$Scroll[var6.ordinal()]) {
            case 1:
               if(((Setting)var5.get(var5.size() - 1)).getY() <= var4.getYCoordinate() + var4.getHeight() - 14) {
                  break;
               }

               Iterator var7 = var5.iterator();
               if(var7.hasNext()) {
                  Setting var8 = (Setting)var7.next();
                  var8.setOffset(var8.getOffset() - 5);
               }
            case 2:
               if(((Setting)var5.get(0)).getY() < var4.getYCoordinate() + 30) {
                  Iterator var10 = var5.iterator();
                  if(var10.hasNext()) {
                     Setting var12 = (Setting)var10.next();
                     var12.setOffset(var12.getOffset() + 5);
                  }
               }
            }
         }
      }

      if(this.selected) {
         Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.drawString(this.data.getName() + " Settings", (float)(var4.getXCoordinate() + 165), (float)(var4.getYCoordinate() + 6), -1);
         if(var5 == null || var5.isEmpty()) {
            String var9 = "NO SETTINGS ;(";
            FontRenderer var11 = Minecraft.getInstance().fontRendererObj;
            var11.drawStringWithShadow("NO SETTINGS ;(", (float)(var4.getXCoordinate() + 150) + (float)(var4.getWidth() - var11.d("NO SETTINGS ;(")) / 2.0F, (float)var4.getYCoordinate() + (float)(var4.getHeight() - var11.getHeight()) / 2.0F, -9801351);
         }
      }

      GL11.glPushMatrix();
      GL11.glScissor(0, var4.sHeight() - var4.getYCoordinate() * 2 - var4.getHeight() * 2, 1920, var4.getHeight() * 2 - 42);
      GL11.glEnable(3089);
      Manager.getSettingList().stream().filter(Module::lambda$drawScreen2$0).filter(this::lambda$drawScreen2$1).sorted(Module::lambda$drawScreen2$2).forEach(Module::lambda$drawScreen2$3);
      GL11.glDisable(3089);
      GL11.glPopMatrix();
   }

   protected void mouseClicked(int var1, int var2, int var3) {
      Scroll.b();
      DiscordGUI var5 = Novoline.getInstance().getDiscordGUI();
      if(var2 >= var5.getYCoordinate() + 20 && var2 <= var5.getYCoordinate() + var5.getHeight() && this.isHovered(var1, var2) && var3 == 2) {
         Tab var6 = Novoline.getInstance().getDiscordGUI().getSelectedTab();
         if(var6 == null) {
            return;
         }

         Iterator var7 = var6.getModuleList().iterator();
         if(var7.hasNext()) {
            Module var8 = (Module)var7.next();
            if(var8.listening) {
               var8.listening = false;
            }
         }

         this.listening = true;
      }

      if(this.selected) {
         Iterator var9 = Manager.getSettingList().iterator();
         if(var9.hasNext()) {
            Setting var10 = (Setting)var9.next();
            if(var2 >= var5.getYCoordinate() + 20 && var2 <= var5.getYCoordinate() + var5.getHeight() && this.data.equals(var10.getParentModule()) && var10.mouseClicked(var1, var2, var3)) {
               return;
            }
         }
      }

   }

   protected void keyTyped(char var1, int var2) {
      Scroll.b();
      DiscordGUI var4 = Novoline.getInstance().getDiscordGUI();
      if(this.listening) {
         if(var2 != 1) {
            this.data.setKeyBind(KeyboardKeybind.of(var2));
            this.data.getNovoline().getModuleManager().getBindManager().save();
         }

         this.listening = false;
      }

      if(this.selected) {
         Iterator var5 = Manager.getSettingList().iterator();
         if(var5.hasNext()) {
            Setting var6 = (Setting)var5.next();
            if(this.data.equals(var6.getParentModule())) {
               var6.keyTyped(var1, var2);
            }
         }
      }

   }

   public void mouseReleased(int var1, int var2, int var3) {
      if(this.selected) {
         Manager.getSettingList().stream().filter(this::lambda$mouseReleased$4).forEach(Module::lambda$mouseReleased$5);
      }

   }

   public boolean isHovered(int var1, int var2) {
      Scroll.b();
      DiscordGUI var4 = Novoline.getInstance().getDiscordGUI();
      int var5 = var4.getXCoordinate();
      int var6 = var4.getYCoordinate();
      return var1 >= var5 + 45 && var1 <= var5 + 45 + 110 && var2 >= var6 + this.y - 4 && var2 <= var6 + this.y + Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.getHeight() + 4;
   }

   public boolean isInsideMenu() {
      Scroll.b();
      DiscordGUI var2 = Novoline.getInstance().getDiscordGUI();
      int var3 = var2.getYCoordinate();
      return var3 + this.y <= var3 + var2.getHeight() - 9 && var3 + this.y >= var3 + 23;
   }

   public AbstractModule getData() {
      return this.data;
   }

   public int getY() {
      return this.y;
   }

   public void setY(int var1) {
      this.y = var1;
   }

   public boolean isListening() {
      return this.listening;
   }

   public boolean isSelected() {
      return this.selected;
   }

   public void setSelected(boolean var1) {
      this.selected = var1;
   }

   private static void lambda$mouseReleased$5(int var0, int var1, int var2, Setting var3) {
      var3.mouseReleased(var0, var1, var2);
   }

   private boolean lambda$mouseReleased$4(Setting var1) {
      int[] var2 = Scroll.b();
      return var1.getParentModule().equals(this.data) && var1.isInsideMenu();
   }

   private static void lambda$drawScreen2$3(int var0, int var1, Setting var2) {
      var2.update();
      var2.drawScreen(var0, var1);
   }

   private static int lambda$drawScreen2$2(Setting var0, Setting var1) {
      return Boolean.compare(var0.isOpened(), var1.isOpened());
   }

   private boolean lambda$drawScreen2$1(Setting var1) {
      int[] var2 = Scroll.b();
      return var1.getParentModule().equals(this.data) && this.selected;
   }

   private static boolean lambda$drawScreen2$0(Setting var0) {
      int[] var1 = Scroll.b();
      return var0.getSupplier() != null?((Boolean)var0.getSupplier().get()).booleanValue():true;
   }

   private static NullPointerException a(NullPointerException var0) {
      return var0;
   }
}
