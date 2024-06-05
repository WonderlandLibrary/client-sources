package cc.novoline.gui.screen.dropdown;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.dropdown.Module;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.fonts.impl.Fonts$ICONFONT$ICONFONT_24;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_20;
import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.acE;
import net.minecraft.client.gui.Gui;

public class Tab {
   private final EnumModuleType enumModuleType;
   private float posX;
   private float posY;
   public boolean dragging;
   public boolean opened;
   public List modules = new CopyOnWriteArrayList();
   private static boolean f;

   public Tab(EnumModuleType var1, float var2, float var3) {
      e();
      this.enumModuleType = var1;
      this.posX = var2;
      this.posY = var3;
      Iterator var5 = Novoline.getInstance().getModuleManager().getModuleListByCategory(var1).iterator();
      if(var5.hasNext()) {
         AbstractModule var6 = (AbstractModule)var5.next();
         if(!(var6 instanceof WindowedFullscreen)) {
            this.modules.add(new Module(var6, this));
         }
      }

   }

   public void drawScreen(int var1, int var2) {
      e();
      HUD var4 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      String var5 = "";
      if(this.enumModuleType.name().equalsIgnoreCase("Combat")) {
         var5 = "D";
      }

      if(this.enumModuleType.name().equalsIgnoreCase("Movement")) {
         var5 = "A";
      }

      if(this.enumModuleType.name().equalsIgnoreCase("Player")) {
         var5 = "B";
      }

      if(this.enumModuleType.name().equalsIgnoreCase("Visuals")) {
         var5 = "C";
      }

      if(this.enumModuleType.name().equalsIgnoreCase("Exploits")) {
         var5 = "G";
      }

      if(this.enumModuleType.name().equalsIgnoreCase("Misc")) {
         var5 = "F";
      }

      Gui.drawRect((double)(this.posX - 1.0F), (double)this.posY, (double)(this.posX + 101.0F), (double)(this.posY + 15.0F), (new Color(29, 29, 29, 255)).getRGB());
      Fonts$ICONFONT$ICONFONT_24.ICONFONT_24.drawString(var5, this.posX + 88.0F, this.posY + 5.0F, -1);
      Fonts$SF$SF_20.SF_20.drawString(this.enumModuleType.name().charAt(0) + this.enumModuleType.name().substring(1).toLowerCase(), (double)(this.posX + 4.0F), (double)(this.posY + 4.0F), -1, true);
      if(this.opened) {
         Gui.drawRect((double)(this.posX - 1.0F), (double)(this.posY + 15.0F), (double)(this.posX + 101.0F), (double)(this.posY + 15.0F + (float)this.getTabHeight() + 1.0F), (new Color(29, 29, 29, 255)).getRGB());
         this.modules.forEach(Tab::lambda$drawScreen$0);
      }

      this.modules.forEach(Tab::lambda$drawScreen$1);
      if(acE.b() == null) {
         b(false);
      }

   }

   public int getTabHeight() {
      f();
      int var2 = 0;
      Iterator var3 = this.modules.iterator();
      if(var3.hasNext()) {
         Module var4 = (Module)var3.next();
         var2 += var4.yPerModule;
      }

      return var2;
   }

   public boolean b(int var1, int var2) {
      boolean var3 = f();
      return (float)var1 > this.posX && (float)var2 > this.posY && (float)var1 < this.posX + 101.0F && (float)var2 < this.posY + 15.0F;
   }

   public void mouseReleased(int var1, int var2, int var3) {
      boolean var4 = e();
      if(this.opened) {
         this.modules.forEach(Tab::lambda$mouseReleased$2);
      }

   }

   public void keyTyped(char var1, int var2) {
      this.modules.forEach(Tab::lambda$keyTyped$3);
   }

   public void mouseClicked(int var1, int var2, int var3) throws IOException {
      boolean var4 = e();
      if(this.b(var1, var2) && var3 == 1) {
         this.opened = !this.opened;
         if(this.opened) {
            Iterator var5 = this.modules.iterator();
            if(var5.hasNext()) {
               Module var6 = (Module)var5.next();
               var6.fraction = 0.0F;
            }
         }
      }

      if(this.opened) {
         this.modules.forEach(Tab::lambda$mouseClicked$4);
      }

   }

   public boolean a(AbstractModule var1) {
      f();
      Iterator var3 = this.modules.iterator();
      if(var3.hasNext()) {
         Module var4 = (Module)var3.next();
         if(var1 == var4.getModule()) {
            return true;
         }
      }

      return false;
   }

   public EnumModuleType a() {
      return this.enumModuleType;
   }

   public void setPosX(float var1) {
      this.posX = var1;
   }

   public void setPosY(float var1) {
      this.posY = var1;
   }

   public float getPosY() {
      return this.posY;
   }

   public float getPosX() {
      return this.posX;
   }

   public List getModules() {
      return this.modules;
   }

   private static void lambda$mouseClicked$4(int var0, int var1, int var2, Module var3) {
      Module var10000 = var3;
      int var10001 = var0;
      int var10002 = var1;
      int var10003 = var2;

      try {
         var10000.mouseClicked(var10001, var10002, var10003);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   private static void lambda$keyTyped$3(char var0, int var1, Module var2) {
      var2.keyTyped(var0, var1);
   }

   private static void lambda$mouseReleased$2(int var0, int var1, int var2, Module var3) {
      var3.mouseReleased(var0, var1, var2);
   }

   private static void lambda$drawScreen$1(Module var0) {
      var0.yPerModule = 0;
   }

   private static void lambda$drawScreen$0(int var0, int var1, Module var2) {
      var2.drawScreen(var0, var1);
   }

   public static void b(boolean var0) {
      f = var0;
   }

   public static boolean e() {
      return f;
   }

   public static boolean f() {
      boolean var0 = e();
      return true;
   }

   private static IOException a(IOException var0) {
      return var0;
   }

   static {
      b(false);
   }
}
