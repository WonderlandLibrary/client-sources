package com.example.editme.commands;

import com.example.editme.EditmeMod;
import com.example.editme.gui.frames.Frame;
import com.example.editme.gui.frames.SettingsFrame;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.command.syntax.SyntaxChunk;
import com.example.editme.util.module.ModuleManager;
import java.util.Iterator;

public class ResetGuiCommand extends Command {
   public ResetGuiCommand() {
      super("resetgui", SyntaxChunk.EMPTY);
   }

   public void call(String[] var1) {
      int var2 = 2;
      Iterator var3 = ModuleManager.getModuleByName("ClickGUI").settingList.iterator();

      while(var3.hasNext()) {
         Setting var4 = (Setting)var3.next();
         if (var4.getName().endsWith("OLDFAGy")) {
            var4.setValue(102);
         }

         if (var4.getName().endsWith("OLDFAGx")) {
            var4.setValue(var2);
         }

         if (var4.getName().endsWith("y")) {
            var4.setValue(2);
         }

         if (var4.getName().endsWith("x")) {
            var4.setValue(var2);
            var2 += 120;
         }
      }

      EditmeMod.getInstance().getGuiManager().getFrames().forEach(ResetGuiCommand::lambda$call$0);
      var2 = 2;
      var3 = ModuleManager.getModules().iterator();

      while(true) {
         Module var7;
         do {
            if (!var3.hasNext()) {
               return;
            }

            var7 = (Module)var3.next();
         } while(var7.getCategory() != Module.Category.HUD);

         Iterator var5 = ModuleManager.getModuleByName("ClickGUI").settingList.iterator();

         while(var5.hasNext()) {
            Setting var6 = (Setting)var5.next();
            if (var6.getName().equals("x")) {
               var6.setValue(var2);
               var2 += 60;
            }

            if (var6.getName().equals("y")) {
               var6.setValue(400);
            }
         }
      }
   }

   private static void lambda$call$0(Frame var0) {
      if (var0 instanceof SettingsFrame) {
         SettingsFrame var1 = (SettingsFrame)var0;
         Iterator var2 = ModuleManager.getModuleByName("ClickGUI").settingList.iterator();

         while(var2.hasNext()) {
            Setting var3 = (Setting)var2.next();
            if (var3.getName().equals(String.valueOf((new StringBuilder()).append(var1.getModuleCategory().getName().toUpperCase()).append("x")))) {
               var1.moved((float)(Integer)var3.getValue(), 2.0F);
               break;
            }
         }
      }

   }
}
