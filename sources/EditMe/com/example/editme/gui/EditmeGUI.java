package com.example.editme.gui;

import com.example.editme.gui.frames.Frame;
import com.example.editme.gui.frames.SettingsFrame;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.module.ModuleManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class EditmeGUI extends GuiScreen {
   private float tempX;
   private int x;
   private final String vibes = "https://www.youtube.com/watch?v=QHDRRxKlimY";
   private final ArrayList frames = new ArrayList();
   private int y;
   private float tempY;

   public void func_146281_b() {
      if (this.field_146297_k.field_71460_t.func_147702_a()) {
         this.field_146297_k.field_71460_t.func_181022_b();
      }

      if (ModuleManager.isModuleEnabled("INeedGlasses")) {
         this.field_146297_k.field_71460_t.func_175069_a(new ResourceLocation("shaders/post/blur.json"));
      } else if (ModuleManager.isModuleEnabled("Australia")) {
         this.field_146297_k.field_71460_t.func_175069_a(new ResourceLocation("shaders/post/flip.json"));
      } else if (ModuleManager.isModuleEnabled("Vodka")) {
         this.field_146297_k.field_71460_t.func_175069_a(new ResourceLocation("shaders/post/wobble.json"));
      }

   }

   private static void lambda$mouseReleased$4(int var0, int var1, int var2, Frame var3) {
      var3.onMouseReleased(var0, var1, var2);
   }

   public void initialize() {
      Module.Category[] var1 = Module.Category.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Module.Category var4 = var1[var3];
         if (!var4.isHidden()) {
            Iterator var5 = ModuleManager.getModuleByName("ClickGUI").settingList.iterator();

            while(var5.hasNext()) {
               Setting var6 = (Setting)var5.next();
               if (var6.getName().equals(String.valueOf((new StringBuilder()).append(var4.getName().toUpperCase()).append("x")))) {
                  this.x = (Integer)var6.getValue();
               }

               if (var6.getName().equals(String.valueOf((new StringBuilder()).append(var4.getName().toUpperCase()).append("y")))) {
                  this.y = (Integer)var6.getValue();
               }
            }

            this.getFrames().add(new SettingsFrame(var4, (float)this.x, (float)this.y, 120.0F, 15.0F));
         }
      }

      this.getFrames().forEach(Frame::initialize);
      this.getFrames().forEach(EditmeGUI::lambda$initialize$0);
   }

   protected void func_73864_a(int var1, int var2, int var3) throws IOException {
      super.func_73864_a(var1, var2, var3);
      this.getFrames().forEach(EditmeGUI::lambda$mouseClicked$3);
   }

   private static void lambda$drawScreen$1(int var0, int var1, float var2, Frame var3) {
      var3.drawScreen(var0, var1, var2);
   }

   public void func_73863_a(int var1, int var2, float var3) {
      super.func_73863_a(var1, var2, var3);
      this.getFrames().forEach(EditmeGUI::lambda$drawScreen$1);
   }

   protected void func_146286_b(int var1, int var2, int var3) {
      super.func_146286_b(var1, var2, var3);
      this.getFrames().forEach(EditmeGUI::lambda$mouseReleased$4);
   }

   public void func_73866_w_() {
      if (ModuleManager.isModuleEnabled("GuiBlur")) {
         if (this.field_146297_k.field_71460_t.func_147702_a()) {
            this.field_146297_k.field_71460_t.func_181022_b();
         }

         this.field_146297_k.field_71460_t.func_175069_a(new ResourceLocation("shaders/post/blur.json"));
      }

   }

   private static void lambda$initialize$0(Frame var0) {
      Iterator var1 = ModuleManager.getModuleByName("ClickGUI").settingList.iterator();

      while(var1.hasNext()) {
         Setting var2 = (Setting)var1.next();
         if (var2.getName().equals(String.valueOf((new StringBuilder()).append(var0.getName().toUpperCase()).append("e")))) {
            var0.setExtended((Boolean)var2.getValue());
         }
      }

   }

   public ArrayList getFrames() {
      return this.frames;
   }

   private static void lambda$mouseClicked$3(int var0, int var1, int var2, Frame var3) {
      var3.onMouseClicked(var0, var1, var2);
   }

   protected void func_73869_a(char var1, int var2) throws IOException {
      super.func_73869_a(var1, var2);
      this.getFrames().forEach(EditmeGUI::lambda$keyTyped$2);
   }

   private static void lambda$keyTyped$2(char var0, int var1, Frame var2) {
      var2.keyTyped(var0, var1);
   }

   public boolean func_73868_f() {
      return false;
   }
}
