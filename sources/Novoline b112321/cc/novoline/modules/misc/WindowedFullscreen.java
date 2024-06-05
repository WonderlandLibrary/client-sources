package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class WindowedFullscreen extends AbstractModule {
   private boolean x = false;
   private static String[] y;

   public WindowedFullscreen(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.MISC, "WindowedFullscreen", "Windowed Fullscreen");
   }

   public void onEnable() {
      a();
      this.setHidden(true);
      if(this.mc.isFullScreen()) {
         this.mc.toggleFullscreen();
         this.mc.toggleFullscreen();
         this.d(true);
      }

   }

   public void onDisable() {
      String[] var1 = a();

      try {
         System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
         Display.setDisplayMode(new DisplayMode(this.mc.displayWidth, this.mc.displayHeight));
         Display.setFullscreen(true);
         Display.setResizable(true);
      } catch (Exception var3) {
         ;
      }

      if(this.mc.isFullScreen()) {
         this.mc.toggleFullscreen();
         this.mc.toggleFullscreen();
      }

   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      a();
      boolean var3 = this.mc.isFullScreen();
      if(this.x != var3) {
         this.d(var3);
         this.x = var3;
      }

   }

   public void d(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public static void b(String[] var0) {
      y = var0;
   }

   public static String[] a() {
      return y;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      b(new String[4]);
   }
}
