package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.CommandSpreadPlayers$Position;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.PlayerSelector;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CommandSpreadPlayers extends CommandBase {
   public String getCommandName() {
      return "spreadplayers";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.spreadplayers.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 6) {
         throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
      } else {
         int var3 = 0;
         BlockPos var4 = var1.getPosition();
         double var5 = parseDouble((double)var4.getX(), var2[var3++], true);
         double var7 = parseDouble((double)var4.getZ(), var2[var3++], true);
         double var9 = parseDouble(var2[var3++], 0.0D);
         double var11 = parseDouble(var2[var3++], var9 + 1.0D);
         boolean var13 = parseBoolean(var2[var3++]);
         ArrayList var14 = Lists.newArrayList();

         while(var3 < var2.length) {
            String var15 = var2[var3++];
            if(!PlayerSelector.hasArguments(var15)) {
               EntityPlayerMP var22 = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(var15);
               throw new PlayerNotFoundException();
            }

            List var16 = PlayerSelector.matchEntities(var1, var15, Entity.class);
            if(var16.isEmpty()) {
               throw new EntityNotFoundException();
            }

            var14.addAll(var16);
         }

         var1.setCommandStat(CommandResultStats$Type.AFFECTED_ENTITIES, var14.size());
         if(var14.isEmpty()) {
            throw new EntityNotFoundException();
         } else {
            var1.addChatMessage(new ChatComponentTranslation("commands.spreadplayers.spreading." + "teams", new Object[]{Integer.valueOf(var14.size()), Double.valueOf(var11), Double.valueOf(var5), Double.valueOf(var7), Double.valueOf(var9)}));
            this.func_110669_a(var1, var14, new CommandSpreadPlayers$Position(var5, var7), var9, var11, ((Entity)var14.get(0)).worldObj, var13);
         }
      }
   }

   private void func_110669_a(ICommandSender var1, List var2, CommandSpreadPlayers$Position var3, double var4, double var6, World var8, boolean var9) throws CommandException {
      Random var10 = new Random();
      double var11 = var3.field_111101_a - var6;
      double var13 = var3.field_111100_b - var6;
      double var15 = var3.field_111101_a + var6;
      double var17 = var3.field_111100_b + var6;
      CommandSpreadPlayers$Position[] var19 = this.func_110670_a(var10, this.func_110667_a(var2), var11, var13, var15, var17);
      int var20 = this.func_110668_a(var3, var4, var8, var10, var11, var13, var15, var17, var19, var9);
      double var21 = this.func_110671_a(var2, var8, var19, var9);
      notifyOperators(var1, this, "commands.spreadplayers.success." + "teams", new Object[]{Integer.valueOf(var19.length), Double.valueOf(var3.field_111101_a), Double.valueOf(var3.field_111100_b)});
      if(var19.length > 1) {
         var1.addChatMessage(new ChatComponentTranslation("commands.spreadplayers.info." + "teams", new Object[]{String.format("%.2f", new Object[]{Double.valueOf(var21)}), Integer.valueOf(var20)}));
      }

   }

   private int func_110667_a(List var1) {
      HashSet var2 = Sets.newHashSet();

      for(Entity var4 : var1) {
         if(var4 instanceof EntityPlayer) {
            var2.add(((EntityPlayer)var4).getTeam());
         } else {
            var2.add((Team)null);
         }
      }

      return var2.size();
   }

   private int func_110668_a(CommandSpreadPlayers$Position var1, double var2, World var4, Random var5, double var6, double var8, double var10, double var12, CommandSpreadPlayers$Position[] var14, boolean var15) throws CommandException {
      boolean var16 = true;
      double var17 = 3.4028234663852886E38D;

      int var19;
      for(var19 = 0; var19 < 10000; ++var19) {
         var16 = false;
         var17 = 3.4028234663852886E38D;

         for(int var20 = 0; var20 < var14.length; ++var20) {
            CommandSpreadPlayers$Position var21 = var14[var20];
            int var22 = 0;
            CommandSpreadPlayers$Position var23 = new CommandSpreadPlayers$Position();

            for(int var24 = 0; var24 < var14.length; ++var24) {
               if(var20 != var24) {
                  CommandSpreadPlayers$Position var25 = var14[var24];
                  double var26 = var21.func_111099_a(var25);
                  var17 = Math.min(var26, var17);
                  if(var26 < var2) {
                     ++var22;
                     var23.field_111101_a += var25.field_111101_a - var21.field_111101_a;
                     var23.field_111100_b += var25.field_111100_b - var21.field_111100_b;
                  }
               }
            }

            var23.field_111101_a /= (double)var22;
            var23.field_111100_b /= (double)var22;
            double var36 = (double)var23.func_111096_b();
            if(var36 > 0.0D) {
               var23.func_111095_a();
               var21.func_111094_b(var23);
            } else {
               var21.func_111097_a(var5, var6, var8, var10, var12);
            }

            var16 = true;
            if(var21.func_111093_a(var6, var8, var10, var12)) {
               var16 = true;
            }
         }

         for(CommandSpreadPlayers$Position var35 : var14) {
            if(!var35.func_111098_b(var4)) {
               var35.func_111097_a(var5, var6, var8, var10, var12);
               var16 = true;
            }
         }
      }

      if(var19 >= 10000) {
         throw new CommandException("commands.spreadplayers.failure." + "teams", new Object[]{Integer.valueOf(var14.length), Double.valueOf(var1.field_111101_a), Double.valueOf(var1.field_111100_b), String.format("%.2f", new Object[]{Double.valueOf(var17)})});
      } else {
         return var19;
      }
   }

   private double func_110671_a(List var1, World var2, CommandSpreadPlayers$Position[] var3, boolean var4) {
      double var5 = 0.0D;
      int var7 = 0;
      HashMap var8 = Maps.newHashMap();

      for(Entity var10 : var1) {
         Team var13 = var10 instanceof EntityPlayer?((EntityPlayer)var10).getTeam():null;
         if(!var8.containsKey(var13)) {
            var8.put(var13, var3[var7++]);
         }

         CommandSpreadPlayers$Position var12 = (CommandSpreadPlayers$Position)var8.get(var13);
         var10.setPositionAndUpdate((double)((float)MathHelper.floor_double(var12.field_111101_a) + 0.5F), (double)var12.func_111092_a(var2), (double)MathHelper.floor_double(var12.field_111100_b) + 0.5D);
         double var22 = Double.MAX_VALUE;

         for(CommandSpreadPlayers$Position var18 : var3) {
            if(var12 != var18) {
               double var19 = var12.func_111099_a(var18);
               var22 = Math.min(var19, var22);
            }
         }

         var5 += var22;
      }

      var5 = var5 / (double)var1.size();
      return var5;
   }

   private CommandSpreadPlayers$Position[] func_110670_a(Random var1, int var2, double var3, double var5, double var7, double var9) {
      CommandSpreadPlayers$Position[] var11 = new CommandSpreadPlayers$Position[var2];

      for(int var12 = 0; var12 < var11.length; ++var12) {
         CommandSpreadPlayers$Position var13 = new CommandSpreadPlayers$Position();
         var13.func_111097_a(var1, var3, var5, var7, var9);
         var11[var12] = var13;
      }

      return var11;
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length >= 1 && var2.length <= 2?a(var2, 0, var3):null;
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
