package net.minecraft.src;

import java.util.*;
import net.minecraft.server.*;

public class CommandServerTp extends CommandBase
{
    @Override
    public String getCommandName() {
        return "tp";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.tp.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 1) {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }
        EntityPlayerMP var3;
        if (par2ArrayOfStr.length != 2 && par2ArrayOfStr.length != 4) {
            var3 = CommandBase.getCommandSenderAsPlayer(par1ICommandSender);
        }
        else {
            var3 = CommandBase.func_82359_c(par1ICommandSender, par2ArrayOfStr[0]);
            if (var3 == null) {
                throw new PlayerNotFoundException();
            }
        }
        if (par2ArrayOfStr.length != 3 && par2ArrayOfStr.length != 4) {
            if (par2ArrayOfStr.length == 1 || par2ArrayOfStr.length == 2) {
                final EntityPlayerMP var4 = CommandBase.func_82359_c(par1ICommandSender, par2ArrayOfStr[par2ArrayOfStr.length - 1]);
                if (var4 == null) {
                    throw new PlayerNotFoundException();
                }
                if (var4.worldObj != var3.worldObj) {
                    CommandBase.notifyAdmins(par1ICommandSender, "commands.tp.notSameDimension", new Object[0]);
                    return;
                }
                var3.mountEntity(null);
                var3.playerNetServerHandler.setPlayerLocation(var4.posX, var4.posY, var4.posZ, var4.rotationYaw, var4.rotationPitch);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.tp.success", var3.getEntityName(), var4.getEntityName());
            }
        }
        else if (var3.worldObj != null) {
            int var5 = par2ArrayOfStr.length - 3;
            final double var6 = this.func_82368_a(par1ICommandSender, var3.posX, par2ArrayOfStr[var5++]);
            final double var7 = this.func_82367_a(par1ICommandSender, var3.posY, par2ArrayOfStr[var5++], 0, 0);
            final double var8 = this.func_82368_a(par1ICommandSender, var3.posZ, par2ArrayOfStr[var5++]);
            var3.mountEntity(null);
            var3.setPositionAndUpdate(var6, var7, var8);
            CommandBase.notifyAdmins(par1ICommandSender, "commands.tp.success.coordinates", var3.getEntityName(), var6, var7, var8);
        }
    }
    
    private double func_82368_a(final ICommandSender par1ICommandSender, final double par2, final String par4Str) {
        return this.func_82367_a(par1ICommandSender, par2, par4Str, -30000000, 30000000);
    }
    
    private double func_82367_a(final ICommandSender par1ICommandSender, final double par2, String par4Str, final int par5, final int par6) {
        final boolean var7 = par4Str.startsWith("~");
        double var8 = var7 ? par2 : 0.0;
        if (!var7 || par4Str.length() > 1) {
            final boolean var9 = par4Str.contains(".");
            if (var7) {
                par4Str = par4Str.substring(1);
            }
            var8 += CommandBase.parseDouble(par1ICommandSender, par4Str);
            if (!var9 && !var7) {
                var8 += 0.5;
            }
        }
        if (par5 != 0 || par6 != 0) {
            if (var8 < par5) {
                throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { var8, par5 });
            }
            if (var8 > par6) {
                throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { var8, par6 });
            }
        }
        return var8;
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
