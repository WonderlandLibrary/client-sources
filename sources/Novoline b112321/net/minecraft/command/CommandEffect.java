package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

public class CommandEffect extends CommandBase {
   public String getCommandName() {
      return "effect";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.effect.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 2) {
         throw new WrongUsageException("commands.effect.usage", new Object[0]);
      } else {
         EntityLivingBase var3 = (EntityLivingBase)getEntity(var1, var2[0], EntityLivingBase.class);
         if(var2[1].equals("clear")) {
            if(var3.getActivePotionEffects().isEmpty()) {
               throw new CommandException("commands.effect.failure.notActive.all", new Object[]{var3.getName()});
            }

            var3.clearActivePotions();
            notifyOperators(var1, this, "commands.effect.success.removed.all", new Object[]{var3.getName()});
         } else {
            int var4;
            try {
               var4 = parseInt(var2[1], 1);
            } catch (NumberInvalidException var11) {
               Potion var6 = Potion.getPotionFromResourceLocation(var2[1]);
               throw var11;
            }

            int var5 = 600;
            int var12 = 30;
            int var7 = 0;
            if(var4 >= Potion.potionTypes.length || Potion.potionTypes[var4] == null) {
               throw new NumberInvalidException("commands.effect.notFound", new Object[]{Integer.valueOf(var4)});
            }

            Potion var8 = Potion.potionTypes[var4];
            if(var2.length >= 3) {
               var12 = parseInt(var2[2], 0, 1000000);
               if(var8.isInstant()) {
                  var5 = var12;
               } else {
                  var5 = var12 * 20;
               }
            } else if(var8.isInstant()) {
               var5 = 1;
            }

            if(var2.length >= 4) {
               var7 = parseInt(var2[3], 0, 255);
            }

            boolean var9 = true;
            if(var2.length >= 5 && "true".equalsIgnoreCase(var2[4])) {
               var9 = false;
            }

            PotionEffect var10 = new PotionEffect(var4, var5, var7, false, var9);
            var3.addPotionEffect(var10);
            notifyOperators(var1, this, "commands.effect.success", new Object[]{new ChatComponentTranslation(var10.getEffectName(), new Object[0]), Integer.valueOf(var4), Integer.valueOf(var7), var3.getName(), Integer.valueOf(var12)});
         }

      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, this.getAllUsernames()):(var2.length == 2?getListOfStringsMatchingLastWord(var2, Potion.func_181168_c()):(var2.length == 5?getListOfStringsMatchingLastWord(var2, new String[]{"true", "false"}):null));
   }

   protected String[] getAllUsernames() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return true;
   }

   private static NumberInvalidException a(NumberInvalidException var0) {
      return var0;
   }
}
