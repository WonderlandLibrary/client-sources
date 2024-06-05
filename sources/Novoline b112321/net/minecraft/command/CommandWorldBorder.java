package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.border.WorldBorder;

public class CommandWorldBorder extends CommandBase {
   public String getCommandName() {
      return "worldborder";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.worldborder.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 1) {
         throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
      } else {
         WorldBorder var3 = this.getWorldBorder();
         if(var2[0].equals("set")) {
            if(var2.length != 2 && var2.length != 3) {
               throw new WrongUsageException("commands.worldborder.set.usage", new Object[0]);
            }

            double var4 = var3.getTargetSize();
            double var6 = parseDouble(var2[1], 1.0D, 6.0E7D);
            long var8 = var2.length > 2?parseLong(var2[2], 0L, 9223372036854775L) * 1000L:0L;
            if(var8 > 0L) {
               var3.setTransition(var4, var6, var8);
               if(var4 > var6) {
                  notifyOperators(var1, this, "commands.worldborder.setSlowly.shrink.success", new Object[]{String.format("%.1f", new Object[]{Double.valueOf(var6)}), String.format("%.1f", new Object[]{Double.valueOf(var4)}), Long.toString(var8 / 1000L)});
               } else {
                  notifyOperators(var1, this, "commands.worldborder.setSlowly.grow.success", new Object[]{String.format("%.1f", new Object[]{Double.valueOf(var6)}), String.format("%.1f", new Object[]{Double.valueOf(var4)}), Long.toString(var8 / 1000L)});
               }
            } else {
               var3.setTransition(var6);
               notifyOperators(var1, this, "commands.worldborder.set.success", new Object[]{String.format("%.1f", new Object[]{Double.valueOf(var6)}), String.format("%.1f", new Object[]{Double.valueOf(var4)})});
            }
         } else if(var2[0].equals("add")) {
            if(var2.length != 2 && var2.length != 3) {
               throw new WrongUsageException("commands.worldborder.add.usage", new Object[0]);
            }

            double var10 = var3.getDiameter();
            double var18 = var10 + parseDouble(var2[1], -var10, 6.0E7D - var10);
            long var21 = var3.getTimeUntilTarget() + (var2.length > 2?parseLong(var2[2], 0L, 9223372036854775L) * 1000L:0L);
            if(var21 > 0L) {
               var3.setTransition(var10, var18, var21);
               if(var10 > var18) {
                  notifyOperators(var1, this, "commands.worldborder.setSlowly.shrink.success", new Object[]{String.format("%.1f", new Object[]{Double.valueOf(var18)}), String.format("%.1f", new Object[]{Double.valueOf(var10)}), Long.toString(var21 / 1000L)});
               } else {
                  notifyOperators(var1, this, "commands.worldborder.setSlowly.grow.success", new Object[]{String.format("%.1f", new Object[]{Double.valueOf(var18)}), String.format("%.1f", new Object[]{Double.valueOf(var10)}), Long.toString(var21 / 1000L)});
               }
            } else {
               var3.setTransition(var18);
               notifyOperators(var1, this, "commands.worldborder.set.success", new Object[]{String.format("%.1f", new Object[]{Double.valueOf(var18)}), String.format("%.1f", new Object[]{Double.valueOf(var10)})});
            }
         } else if(var2[0].equals("center")) {
            if(var2.length != 3) {
               throw new WrongUsageException("commands.worldborder.center.usage", new Object[0]);
            }

            BlockPos var11 = var1.getPosition();
            double var5 = parseDouble((double)var11.getX() + 0.5D, var2[1], true);
            double var7 = parseDouble((double)var11.getZ() + 0.5D, var2[2], true);
            var3.setCenter(var5, var7);
            notifyOperators(var1, this, "commands.worldborder.center.success", new Object[]{Double.valueOf(var5), Double.valueOf(var7)});
         } else if(var2[0].equals("damage")) {
            if(var2.length < 2) {
               throw new WrongUsageException("commands.worldborder.damage.usage", new Object[0]);
            }

            if(var2[1].equals("buffer")) {
               if(var2.length != 3) {
                  throw new WrongUsageException("commands.worldborder.damage.buffer.usage", new Object[0]);
               }

               double var12 = parseDouble(var2[2], 0.0D);
               double var19 = var3.getDamageBuffer();
               var3.setDamageBuffer(var12);
               notifyOperators(var1, this, "commands.worldborder.damage.buffer.success", new Object[]{String.format("%.1f", new Object[]{Double.valueOf(var12)}), String.format("%.1f", new Object[]{Double.valueOf(var19)})});
            } else if(var2[1].equals("amount")) {
               if(var2.length != 3) {
                  throw new WrongUsageException("commands.worldborder.damage.amount.usage", new Object[0]);
               }

               double var13 = parseDouble(var2[2], 0.0D);
               double var20 = var3.getDamageAmount();
               var3.setDamageAmount(var13);
               notifyOperators(var1, this, "commands.worldborder.damage.amount.success", new Object[]{String.format("%.2f", new Object[]{Double.valueOf(var13)}), String.format("%.2f", new Object[]{Double.valueOf(var20)})});
            }
         } else if(var2[0].equals("warning")) {
            if(var2.length < 2) {
               throw new WrongUsageException("commands.worldborder.warning.usage", new Object[0]);
            }

            int var14 = parseInt(var2[2], 0);
            if(var2[1].equals("time")) {
               if(var2.length != 3) {
                  throw new WrongUsageException("commands.worldborder.warning.time.usage", new Object[0]);
               }

               int var16 = var3.getWarningTime();
               var3.setWarningTime(var14);
               notifyOperators(var1, this, "commands.worldborder.warning.time.success", new Object[]{Integer.valueOf(var14), Integer.valueOf(var16)});
            } else if(var2[1].equals("distance")) {
               if(var2.length != 3) {
                  throw new WrongUsageException("commands.worldborder.warning.distance.usage", new Object[0]);
               }

               int var17 = var3.getWarningDistance();
               var3.setWarningDistance(var14);
               notifyOperators(var1, this, "commands.worldborder.warning.distance.success", new Object[]{Integer.valueOf(var14), Integer.valueOf(var17)});
            }
         } else {
            if(!var2[0].equals("get")) {
               throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
            }

            double var15 = var3.getDiameter();
            var1.setCommandStat(CommandResultStats$Type.QUERY_RESULT, MathHelper.floor_double(var15 + 0.5D));
            var1.addChatMessage(new ChatComponentTranslation("commands.worldborder.get.success", new Object[]{String.format("%.0f", new Object[]{Double.valueOf(var15)})}));
         }

      }
   }

   protected WorldBorder getWorldBorder() {
      return MinecraftServer.getServer().worldServers[0].getWorldBorder();
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, new String[]{"set", "center", "damage", "warning", "add", "get"}):(var2.length == 2 && var2[0].equals("damage")?getListOfStringsMatchingLastWord(var2, new String[]{"buffer", "amount"}):(var2.length >= 2 && var2.length <= 3 && var2[0].equals("center")?a(var2, 1, var3):(var2.length == 2 && var2[0].equals("warning")?getListOfStringsMatchingLastWord(var2, new String[]{"time", "distance"}):null)));
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
