package cc.novoline.gui.screen.dropdown;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.dropdown.Module;
import cc.novoline.gui.screen.dropdown.Setting;
import cc.novoline.gui.screen.dropdown.Tab;
import cc.novoline.gui.screen.dropdown.config.ConfigTab;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.utils.ScaleUtils;
import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.a1I;
import net.arW;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class DropdownGUI extends GuiScreen {
   private final List tabs = new CopyOnWriteArrayList();
   private boolean dragging;
   private int dragX;
   private int dragY;
   private int alpha;
   int alphaBG = 0;

   public void initGui() {
      Tab.f();
      float var2 = 35.0F;
      this.alphaBG = 0;
      if(this.tabs.isEmpty()) {
         EnumModuleType[] var3 = EnumModuleType.values();
         int var4 = var3.length;
         int var5 = 0;
         if(var5 < var4) {
            EnumModuleType var6 = var3[var5];
            this.tabs.add(new Tab(var6, var2, 10.0F));
            var2 += 110.0F;
            ++var5;
         }

         this.tabs.add(new ConfigTab(var2, 10.0F));
         var2 = var2 + 110.0F;
         this.tabs.add(new a1I(var2, 10.0F));
      }

      Iterator var10 = this.tabs.iterator();
      if(var10.hasNext()) {
         Tab var13 = (Tab)var10.next();
         Iterator var17 = Novoline.getInstance().u().b().iterator();
         if(var17.hasNext()) {
            AbstractModule var20 = (AbstractModule)var17.next();
            if(var20.getType() == var13.a() && !var13.a(var20) && !(var20 instanceof WindowedFullscreen)) {
               var13.modules.add(new Module(var20, var13));
            }
         }
      }

      var10 = this.tabs.iterator();
      if(var10.hasNext()) {
         Tab var14 = (Tab)var10.next();
         Iterator var18 = var14.modules.iterator();
         if(var18.hasNext()) {
            Module var21 = (Module)var18.next();
            if(var21.getModule() instanceof arW && !Novoline.getInstance().u().b().contains(var21.getModule())) {
               var14.modules.remove(var21);
            }
         }
      }

      if(!(this.mc.previousScreen instanceof GuiChest) && this.mc.previousScreen != this) {
         var10 = this.tabs.iterator();
         if(var10.hasNext()) {
            Tab var15 = (Tab)var10.next();
            Iterator var19 = var15.modules.iterator();
            if(var19.hasNext()) {
               Module var22 = (Module)var19.next();
               var22.fraction = 0.0F;
               Iterator var7 = var22.settings.iterator();
               if(var7.hasNext()) {
                  Setting var8 = (Setting)var7.next();
                  var8.setPercent(0.0F);
               }
            }

            if(var15 instanceof ConfigTab) {
               ((ConfigTab)var15).refreshConfigs();
            }

            if(var15 instanceof a1I) {
               ((a1I)var15).b();
            }
         }
      }

      if(((Boolean)((ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class)).getBlur().get()).booleanValue()) {
         this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
      }

      super.initGui();
   }

   public void onGuiClosed() {
      boolean var1 = Tab.f();
      if(this.mc.entityRenderer.theShaderGroup != null) {
         this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
         this.mc.entityRenderer.theShaderGroup = null;
      }

      super.onGuiClosed();
   }

   public void drawScreen(int var1, int var2, float var3) {
      GL11.glPushMatrix();
      ScaledResolution var5 = new ScaledResolution(this.mc);
      int var6 = ScaleUtils.getScaledMouseCoordinates(this.mc, var1, var2)[0];
      Tab.f();
      int var7 = ScaleUtils.getScaledMouseCoordinates(this.mc, var1, var2)[1];
      ScaleUtils.scale(this.mc);
      Gui.drawRect(0, 0, var5.getScaledWidthStatic(this.mc), var5.getScaledHeightStatic(this.mc), (new Color(0, 0, 0, 120)).getRGB());
      Iterator var8 = this.tabs.iterator();
      if(var8.hasNext()) {
         Tab var9 = (Tab)var8.next();
         var9.drawScreen(var6, var7);
         if(var9.dragging) {
            var9.setPosX((float)(this.dragX + var6));
            var9.setPosY((float)(this.dragY + var7));
         }
      }

      super.drawScreen(var1, var2, var3);
      GL11.glPopMatrix();
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      Tab.e();
      int var5 = ScaleUtils.getScaledMouseCoordinates(this.mc, var1, var2)[0];
      int var6 = ScaleUtils.getScaledMouseCoordinates(this.mc, var1, var2)[1];
      Iterator var7 = this.tabs.iterator();
      if(var7.hasNext()) {
         Tab var8 = (Tab)var7.next();
         if(var8.b(var5, var6) && var3 == 0 && !this.anyDragging()) {
            var8.dragging = true;
            this.dragX = (int)(var8.getPosX() - (float)var5);
            this.dragY = (int)(var8.getPosY() - (float)var6);
         }

         Tab var10000 = var8;
         int var10001 = var5;
         int var10002 = var6;
         int var10003 = var3;

         try {
            var10000.mouseClicked(var10001, var10002, var10003);
         } catch (IOException var10) {
            var10.printStackTrace();
         }
      }

      super.mouseClicked(var1, var2, var3);
   }

   private boolean anyDragging() {
      Tab.e();
      Iterator var2 = this.tabs.iterator();
      if(var2.hasNext()) {
         Tab var3 = (Tab)var2.next();
         if(var3.dragging) {
            return true;
         }
      }

      return false;
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      boolean var3 = Tab.e();
      if(var2 == 1 && !this.areAnyHovered()) {
         this.mc.displayGuiScreen((GuiScreen)null);
         if(this.mc.currentScreen != null) {
            return;
         }

         this.mc.setIngameFocus();
      }

      this.tabs.forEach(DropdownGUI::lambda$keyTyped$0);
   }

   private boolean areAnyHovered() {
      Tab.f();
      Iterator var2 = Manager.getSettingList().iterator();
      if(var2.hasNext()) {
         cc.novoline.gui.screen.setting.Setting var3 = (cc.novoline.gui.screen.setting.Setting)var2.next();
         if(var3.isTextHovered()) {
            return true;
         }
      }

      return false;
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      boolean var4 = Tab.f();
      this.tabs.forEach(DropdownGUI::lambda$mouseReleased$1);
      this.tabs.forEach(DropdownGUI::lambda$mouseReleased$2);
      super.mouseReleased(var1, var2, var3);
   }

   public List getTabs() {
      return this.tabs;
   }

   private static void lambda$mouseReleased$2(int var0, int var1, int var2, Tab var3) {
      var3.mouseReleased(var0, var1, var2);
   }

   private static void lambda$mouseReleased$1(Tab var0) {
      var0.dragging = false;
   }

   private static void lambda$keyTyped$0(char var0, int var1, Tab var2) {
      var2.keyTyped(var0, var1);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
