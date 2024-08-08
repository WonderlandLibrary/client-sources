package com.example.editme.util.module;

import com.example.editme.EditmeMod;
import com.example.editme.events.RenderEvent;
import com.example.editme.modules.ClickGUI;
import com.example.editme.modules.Module;
import com.example.editme.util.client.EntityUtil;
import com.example.editme.util.client.Wrapper;
import com.example.editme.util.render.EditmeTessellator;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

public class ModuleManager {
   static HashMap lookup = new HashMap();
   public static ArrayList modules = new ArrayList();

   public static void updateLookup() {
      lookup.clear();
      Iterator var0 = modules.iterator();

      while(var0.hasNext()) {
         Module var1 = (Module)var0.next();
         lookup.put(var1.getName().toLowerCase(), var1);
      }

   }

   public static void initialize() {
      Set var0 = findClasses(ClickGUI.class.getPackage().getName(), Module.class);
      var0.forEach(ModuleManager::lambda$initialize$0);
      EditmeMod.log.info("Modules initialised");
      getModules().sort(Comparator.comparing(Module::getName));
   }

   private static void lambda$onWorldRender$6(RenderEvent var0, Module var1) {
      Minecraft.func_71410_x().field_71424_I.func_76320_a(var1.getName());
      var1.onWorldRender(var0);
      Minecraft.func_71410_x().field_71424_I.func_76319_b();
   }

   private static void lambda$onRender$4(Module var0) {
      var0.onRender();
   }

   public static void onRender() {
      modules.stream().filter(ModuleManager::lambda$onRender$3).forEach(ModuleManager::lambda$onRender$4);
   }

   public static void disableModule(String var0) {
      Module var1 = getModuleByName(var0);
      if (!var1.isDisabled()) {
         var1.disable();
      }

   }

   private static boolean lambda$onWorldRender$5(Module var0) {
      return var0.alwaysListening || var0.isEnabled();
   }

   public static Module getModuleByName(String var0) {
      return (Module)lookup.get(var0.toLowerCase());
   }

   private static void lambda$onUpdate$2(Module var0) {
      var0.onUpdate();
   }

   public static ArrayList getModules() {
      return modules;
   }

   private static void lambda$initialize$0(Class var0) {
      try {
         Module var1 = (Module)var0.getConstructor().newInstance();
         modules.add(var1);
      } catch (InvocationTargetException var2) {
         var2.getCause().printStackTrace();
         System.err.println(String.valueOf((new StringBuilder()).append("Couldn't initiate module ").append(var0.getSimpleName()).append("! Error: ").append(var2.getClass().getSimpleName()).append(", message: ").append(var2.getMessage())));
      } catch (Exception var3) {
         var3.printStackTrace();
         System.err.println(String.valueOf((new StringBuilder()).append("Couldn't initiate module ").append(var0.getSimpleName()).append("! Error: ").append(var3.getClass().getSimpleName()).append(", message: ").append(var3.getMessage())));
      }

   }

   private static Set findClasses(String var0, Class var1) {
      Reflections var2 = new Reflections(var0, new Scanner[0]);
      return var2.getSubTypesOf(var1);
   }

   public static void onWorldRender(RenderWorldLastEvent var0) {
      Minecraft.func_71410_x().field_71424_I.func_76320_a("editme");
      Minecraft.func_71410_x().field_71424_I.func_76320_a("setup");
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179103_j(7425);
      GlStateManager.func_179097_i();
      GlStateManager.func_187441_d(1.0F);
      Vec3d var1 = EntityUtil.getInterpolatedPos(Wrapper.getPlayer(), var0.getPartialTicks());
      RenderEvent var2 = new RenderEvent(EditmeTessellator.INSTANCE, var1);
      var2.resetTranslation();
      Minecraft.func_71410_x().field_71424_I.func_76319_b();
      modules.stream().filter(ModuleManager::lambda$onWorldRender$5).forEach(ModuleManager::lambda$onWorldRender$6);
      Minecraft.func_71410_x().field_71424_I.func_76320_a("release");
      GlStateManager.func_187441_d(1.0F);
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
      GlStateManager.func_179089_o();
      EditmeTessellator.releaseGL();
      Minecraft.func_71410_x().field_71424_I.func_76319_b();
      Minecraft.func_71410_x().field_71424_I.func_76319_b();
   }

   private static void lambda$onBind$7(int var0, Module var1) {
      if (var1.getBind().isDown(var0)) {
         var1.toggle();
      }

   }

   private static boolean lambda$onRender$3(Module var0) {
      return var0.alwaysListening || var0.isEnabled();
   }

   public static void onBind(int var0) {
      if (var0 != 0) {
         modules.forEach(ModuleManager::lambda$onBind$7);
      }
   }

   public static void onUpdate() {
      modules.stream().filter(ModuleManager::lambda$onUpdate$1).forEach(ModuleManager::lambda$onUpdate$2);
   }

   private static boolean lambda$onUpdate$1(Module var0) {
      return var0.alwaysListening || var0.isEnabled();
   }

   public static boolean isModuleEnabled(String var0) {
      Module var1 = getModuleByName(var0);
      return var1 == null ? false : var1.isEnabled();
   }

   public static void enableModule(String var0) {
      Module var1 = getModuleByName(var0);
      if (var1.isDisabled()) {
         var1.enable();
      }

   }
}
