package net.minecraft.src;

import java.util.*;
import net.minecraft.server.*;

public class CommandSetSpawnpoint extends CommandBase
{
    @Override
    public String getCommandName() {
        return "spawnpoint";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.spawnpoint.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        final EntityPlayerMP var3 = (par2ArrayOfStr.length == 0) ? CommandBase.getCommandSenderAsPlayer(par1ICommandSender) : CommandBase.func_82359_c(par1ICommandSender, par2ArrayOfStr[0]);
        if (par2ArrayOfStr.length == 4) {
            if (var3.worldObj != null) {
                final byte var4 = 1;
                final int var5 = 30000000;
                int var6 = var4 + 1;
                final int var7 = CommandBase.parseIntBounded(par1ICommandSender, par2ArrayOfStr[var4], -var5, var5);
                final int var8 = CommandBase.parseIntBounded(par1ICommandSender, par2ArrayOfStr[var6++], 0, 256);
                final int var9 = CommandBase.parseIntBounded(par1ICommandSender, par2ArrayOfStr[var6++], -var5, var5);
                var3.setSpawnChunk(new ChunkCoordinates(var7, var8, var9), true);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.spawnpoint.success", var3.getEntityName(), var7, var8, var9);
            }
        }
        else {
            if (par2ArrayOfStr.length > 1) {
                throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
            }
            final ChunkCoordinates var10 = var3.getPlayerCoordinates();
            var3.setSpawnChunk(var10, true);
            CommandBase.notifyAdmins(par1ICommandSender, "commands.spawnpoint.success", var3.getEntityName(), var10.posX, var10.posY, var10.posZ);
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length != 1 && par2ArrayOfStr.length != 2) ? null : CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }
    
    @Override
    public boolean isUsernameIndex(final String[] par1ArrayOfStr, final int par2) {
        return par2 == 0;
    }
}
