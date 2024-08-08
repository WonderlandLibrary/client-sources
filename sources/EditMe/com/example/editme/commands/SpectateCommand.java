package com.example.editme.commands;

import com.example.editme.modules.exploits.Spectate;
import com.example.editme.util.command.syntax.ChunkBuilder;
import com.example.editme.util.module.ModuleManager;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class SpectateCommand extends Command {
   public void call(String[] var1) {
      Iterator var2 = Minecraft.func_71410_x().field_71441_e.field_72996_f.iterator();

      while(var2.hasNext()) {
         Entity var3 = (Entity)var2.next();
         if (var3.func_70005_c_().equalsIgnoreCase(var1[0])) {
            Spectate.entity = var3;
            ModuleManager.enableModule("Spectate");
         }
      }

   }

   public SpectateCommand() {
      super("spectate", (new ChunkBuilder()).append("name", true).build());
   }
}
