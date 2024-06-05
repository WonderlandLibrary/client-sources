package net;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.nbt.NBTException;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class F8 extends CommandBase {
   public String getCommandName() {
      return "blockdata";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.blockdata.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 4) {
         throw new WrongUsageException("commands.blockdata.usage", new Object[0]);
      } else {
         var1.setCommandStat(CommandResultStats$Type.AFFECTED_BLOCKS, 0);
         BlockPos var3 = parseBlockPos(var1, var2, 0, false);
         World var4 = var1.getEntityWorld();
         if(!var4.isBlockLoaded(var3)) {
            throw new CommandException("commands.blockdata.outOfWorld", new Object[0]);
         } else {
            TileEntity var5 = var4.getTileEntity(var3);
            throw new CommandException("commands.blockdata.notValid", new Object[0]);
         }
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length > 0 && var2.length <= 3?b(var2, 0, var3):null;
   }

   private static NBTException a(NBTException var0) {
      return var0;
   }
}
