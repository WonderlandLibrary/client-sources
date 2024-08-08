package com.example.editme.commands;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.command.syntax.SyntaxChunk;
import com.example.editme.util.module.ModuleManager;
import java.util.Iterator;

public class ToggleVisibleCommand extends Command {
   public void call(String[] var1) {
      Iterator var2 = ModuleManager.getModules().iterator();

      while(true) {
         Module var3;
         do {
            if (!var2.hasNext()) {
               return;
            }

            var3 = (Module)var2.next();
         } while(var3.getCategory().isHidden());

         Iterator var4 = var3.settingList.iterator();

         while(var4.hasNext()) {
            Setting var5 = (Setting)var4.next();
            if (var5.getName().equals("Visible")) {
               var5.setValue(!Boolean.getBoolean(var5.getValueAsString()));
            }
         }
      }
   }

   public ToggleVisibleCommand() {
      super("togglevisible", SyntaxChunk.EMPTY);
   }
}
