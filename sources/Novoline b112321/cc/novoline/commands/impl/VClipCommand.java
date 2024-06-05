package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import net.a_E;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandException;

public final class VClipCommand extends NovoCommand {
   public VClipCommand(Novoline var1) {
      super(var1, "vc", "vclip");
   }

   public void process(String[] var1) throws CommandException {
      int[] var2 = a_E.b();
      if(var1.length != 1) {
         this.notifyError("Use .vclip (height)");
      } else {
         EntityPlayerSP var3 = Minecraft.getInstance().player;
         double var4 = this.getDouble(var1[0]);
         var3.setPositionAndUpdate(var3.posX, var3.posY + var4, var3.posZ);
      }
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
