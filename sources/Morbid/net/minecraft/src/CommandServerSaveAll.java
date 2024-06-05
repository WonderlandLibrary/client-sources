package net.minecraft.src;

import net.minecraft.server.*;

public class CommandServerSaveAll extends CommandBase
{
    @Override
    public String getCommandName() {
        return "save-all";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        final MinecraftServer var3 = MinecraftServer.getServer();
        par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString("commands.save.start", new Object[0]));
        if (var3.getConfigurationManager() != null) {
            var3.getConfigurationManager().saveAllPlayerData();
        }
        try {
            for (int var4 = 0; var4 < var3.worldServers.length; ++var4) {
                if (var3.worldServers[var4] != null) {
                    final WorldServer var5 = var3.worldServers[var4];
                    final boolean var6 = var5.canNotSave;
                    var5.canNotSave = false;
                    var5.saveAllChunks(true, null);
                    var5.canNotSave = var6;
                }
            }
            if (par2ArrayOfStr.length > 0 && "flush".equals(par2ArrayOfStr[0])) {
                par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString("commands.save.flushStart", new Object[0]));
                for (int var4 = 0; var4 < var3.worldServers.length; ++var4) {
                    if (var3.worldServers[var4] != null) {
                        final WorldServer var5 = var3.worldServers[var4];
                        final boolean var6 = var5.canNotSave;
                        var5.canNotSave = false;
                        var5.func_104140_m();
                        var5.canNotSave = var6;
                    }
                }
                par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString("commands.save.flushEnd", new Object[0]));
            }
        }
        catch (MinecraftException var7) {
            CommandBase.notifyAdmins(par1ICommandSender, "commands.save.failed", var7.getMessage());
            return;
        }
        CommandBase.notifyAdmins(par1ICommandSender, "commands.save.success", new Object[0]);
    }
}
