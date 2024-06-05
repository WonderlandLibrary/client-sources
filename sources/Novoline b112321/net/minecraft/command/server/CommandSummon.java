package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CommandSummon extends CommandBase {
   public String getCommandName() {
      return "summon";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.summon.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 1) {
         throw new WrongUsageException("commands.summon.usage", new Object[0]);
      } else {
         String var3 = var2[0];
         BlockPos var4 = var1.getPosition();
         Vec3 var5 = var1.getPositionVector();
         double var6 = var5.xCoord;
         double var8 = var5.yCoord;
         double var10 = var5.zCoord;
         if(var2.length >= 4) {
            var6 = parseDouble(var6, var2[1], true);
            var8 = parseDouble(var8, var2[2], false);
            var10 = parseDouble(var10, var2[3], true);
            var4 = new BlockPos(var6, var8, var10);
         }

         World var12 = var1.getEntityWorld();
         if(!var12.isBlockLoaded(var4)) {
            throw new CommandException("commands.summon.outOfWorld", new Object[0]);
         } else if("LightningBolt".equals(var3)) {
            var12.addWeatherEffect(new EntityLightningBolt(var12, var6, var8, var10));
            notifyOperators(var1, this, "commands.summon.success", new Object[0]);
         } else {
            NBTTagCompound var13 = new NBTTagCompound();
            boolean var14 = false;
            if(var2.length >= 5) {
               IChatComponent var15 = getChatComponentFromNthArg(var1, var2, 4);
               IChatComponent var10000 = var15;

               try {
                  var13 = JsonToNBT.getTagFromJson(var10000.getUnformattedText());
                  var14 = true;
               } catch (NBTException var18) {
                  throw new CommandException("commands.summon.tagError", new Object[]{var18.getMessage()});
               }
            }

            var13.setString("id", var3);
            NBTTagCompound var21 = var13;
            World var10001 = var12;

            try {
               Entity var20 = EntityList.createEntityFromNBT(var21, var10001);
            } catch (RuntimeException var17) {
               throw new CommandException("commands.summon.failed", new Object[0]);
            }

            throw new CommandException("commands.summon.failed", new Object[0]);
         }
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, EntityList.getEntityNameList()):(var2.length > 1 && var2.length <= 4?b(var2, 1, var3):null);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
