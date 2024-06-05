package net;

import net.FJ;
import net.a_E;
import net.gZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandException;

public final class FE extends FJ {
   public FE(gZ var1) {
      super(var1, "vc", "vclip");
   }

   public void b(String[] var1) throws CommandException {
      int[] var2 = a_E.b();
      if(var1.length != 1) {
         this.f("Use .vclip (height)");
      } else {
         EntityPlayerSP var3 = Minecraft.getMinecraft().thePlayer;
         double var4 = this.b(var1[0]);
         var3.setPositionAndUpdate(var3.posX, var3.posY + var4, var3.posZ);
      }
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
