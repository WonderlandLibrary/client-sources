package net;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;

public class F1 extends CommandBase {
   public String getCommandName() {
      return "particle";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.particle.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 8) {
         throw new WrongUsageException("commands.particle.usage", new Object[0]);
      } else {
         boolean var3 = false;
         Object var4 = null;

         for(EnumParticleTypes var8 : EnumParticleTypes.values()) {
            if(var8.hasArguments()) {
               if(var2[0].startsWith(var8.getParticleName())) {
                  var3 = true;
                  break;
               }
            } else if(var2[0].equals(var8.getParticleName())) {
               var3 = true;
               break;
            }
         }

         throw new CommandException("commands.particle.notFound", new Object[]{var2[0]});
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, EnumParticleTypes.getParticleNames()):(var2.length > 1 && var2.length <= 4?b(var2, 1, var3):(var2.length == 10?getListOfStringsMatchingLastWord(var2, new String[]{"normal", "force"}):null));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
