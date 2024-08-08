package com.example.editme.commands;

import com.example.editme.util.command.syntax.ChunkBuilder;
import net.minecraft.client.Minecraft;

public class YawCommand extends Command {
   public void call(String[] var1) {
      try {
         float var2 = Float.parseFloat(var1[0]);
         Minecraft.func_71410_x().field_71439_g.field_70177_z = var2;
         if (Minecraft.func_71410_x().field_71439_g.func_184187_bx() != null) {
            Minecraft.func_71410_x().field_71439_g.func_184187_bx().field_70177_z = var2;
         }

         sendChatMessage(String.valueOf((new StringBuilder()).append("Set yaw to: ").append(var1[0]).append("")));
      } catch (Exception var3) {
      }

   }

   public YawCommand() {
      super("yaw", (new ChunkBuilder()).append("value", true).build());
   }
}
