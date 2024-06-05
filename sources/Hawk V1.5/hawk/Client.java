package hawk;

import hawk.access.DisplayOnScreen;
import hawk.events.Event;
import hawk.events.listeners.EventKey;
import hawk.modules.Module;
import hawk.modules.combat.Aimbot;
import hawk.modules.combat.Antibot;
import hawk.modules.combat.Antikb;
import hawk.modules.combat.Criticals;
import hawk.modules.combat.Killaura;
import hawk.modules.combat.TPAura;
import hawk.modules.movement.Airjump;
import hawk.modules.movement.Fly;
import hawk.modules.movement.Invmove;
import hawk.modules.movement.Longjump;
import hawk.modules.movement.Noslow;
import hawk.modules.movement.RedeFly;
import hawk.modules.movement.Safewalk;
import hawk.modules.movement.Speed;
import hawk.modules.movement.Sprint;
import hawk.modules.player.AutoSetting;
import hawk.modules.player.Autoarmor;
import hawk.modules.player.Bednuker;
import hawk.modules.player.Cheststealer;
import hawk.modules.player.Disabler;
import hawk.modules.player.FastEat;
import hawk.modules.player.Fastplace;
import hawk.modules.player.Nofall;
import hawk.modules.player.Phase;
import hawk.modules.player.ScaffoldHopeItWorks;
import hawk.modules.player.Spammer;
import hawk.modules.player.Timer;
import hawk.modules.render.BlockAnimation;
import hawk.modules.render.ESP;
import hawk.modules.render.Fullbright;
import hawk.modules.render.HeadRotations;
import hawk.modules.render.ModToggleGUI;
import hawk.modules.render.ModulesListOptions;
import hawk.modules.render.Nametags;
import hawk.modules.render.TabGUI;
import hawk.ui.HUD;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.lwjgl.opengl.Display;

public class Client {
   public static Killaura killAura;
   public static String NameInConsole = "[Hawk Client] ";
   public static boolean IsRegistered = false;
   public static String name = "Hawk";
   public static String mcversion = "Minecraft 1.8";
   public static HUD hud = new HUD();
   public static String version = "beta 1.5";
   public static ScaffoldHopeItWorks scaffold;
   public static BlockAnimation animations;
   public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList();
   public static ModulesListOptions arraylist;

   public static void startup() {
      System.out.println(String.valueOf((new StringBuilder(String.valueOf(NameInConsole))).append("Starting Hawk Client - Minecraft 1.8")));
      Display.setTitle(String.valueOf((new StringBuilder(String.valueOf(name))).append(" ").append(version).append(" - ").append(mcversion)));

      try {
         BufferedReader var1 = new BufferedReader(new FileReader("gie5hg8u4toihu45.hawkclient"));

         String var0;
         while((var0 = var1.readLine()) != null) {
            if (var0.equals(DisplayOnScreen.CorrectKey)) {
               IsRegistered = true;
               System.out.println(String.valueOf((new StringBuilder(String.valueOf(NameInConsole))).append("Private version loaded")));
               System.out.println(String.valueOf((new StringBuilder(String.valueOf(NameInConsole))).append("Key : ").append(DisplayOnScreen.CorrectKey)));
            }
         }

         var1.close();
      } catch (IOException var2) {
         System.out.println(String.valueOf((new StringBuilder(String.valueOf(NameInConsole))).append("Normal version loaded")));
      }

      modules.add(new Sprint());
      modules.add(new Fullbright());
      modules.add(new Fly());
      modules.add(new Nofall());
      modules.add(new Speed());
      modules.add(killAura = new Killaura());
      modules.add(new Fastplace());
      modules.add(new Airjump());
      modules.add(new Timer());
      modules.add(new Antikb());
      modules.add(new TabGUI());
      modules.add(new Aimbot());
      modules.add(new Cheststealer());
      modules.add(new FastEat());
      modules.add(new Noslow());
      modules.add(animations = new BlockAnimation());
      modules.add(arraylist = new ModulesListOptions());
      modules.add(new Autoarmor());
      modules.add(new Antibot());
      modules.add(new Invmove());
      modules.add(new AutoSetting());
      modules.add(new Longjump());
      modules.add(new Phase());
      modules.add(new ESP());
      modules.add(new Criticals());
      modules.add(scaffold = new ScaffoldHopeItWorks());
      modules.add(new Safewalk());
      modules.add(new ModToggleGUI());
      modules.add(new HeadRotations());
      modules.add(new Nametags());
      modules.add(new RedeFly());
      modules.add(new Bednuker());
      modules.add(new TPAura());
      modules.add(new Disabler());
      modules.add(new Spammer());
      Thread var3 = new Thread(Client::lambda$0);
      var3.setDaemon(true);
      var3.start();
   }

   public static void onEvent(Event var0) {
      Iterator var2 = modules.iterator();

      while(var2.hasNext()) {
         Module var1 = (Module)var2.next();
         if (var1.toggled) {
            var1.onEvent(var0);
         }
      }

   }

   public static void shutdown() {
   }

   public static Module getModule(String var0) {
      Iterator var2 = modules.iterator();

      while(var2.hasNext()) {
         Module var1 = (Module)var2.next();
         if (var1.name.equalsIgnoreCase(var0)) {
            return var1;
         }
      }

      return null;
   }

   public static void keyPress(int var0) {
      onEvent(new EventKey(var0));
      Iterator var2 = modules.iterator();

      while(var2.hasNext()) {
         Module var1 = (Module)var2.next();
         if (var1.getKey() == var0) {
            var1.toggle();
         }
      }

   }

   private static void lambda$0() {
      while(true) {
         while(true) {
            try {
               Thread.sleep(30000L);
            } catch (InterruptedException var1) {
               var1.printStackTrace();
            }
         }
      }
   }

   public static List<Module> getModulesByCategory(Module.Category var0) {
      ArrayList var1 = new ArrayList();
      Iterator var3 = modules.iterator();

      while(var3.hasNext()) {
         Module var2 = (Module)var3.next();
         if (var2.category == var0) {
            var1.add(var2);
         }
      }

      return var1;
   }
}
