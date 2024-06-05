package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class ServerCommandManager extends CommandHandler implements IAdminCommand
{
    public ServerCommandManager() {
        this.registerCommand(new CommandTime());
        this.registerCommand(new CommandGameMode());
        this.registerCommand(new CommandDifficulty());
        this.registerCommand(new CommandDefaultGameMode());
        this.registerCommand(new CommandKill());
        this.registerCommand(new CommandToggleDownfall());
        this.registerCommand(new CommandWeather());
        this.registerCommand(new CommandXP());
        this.registerCommand(new CommandServerTp());
        this.registerCommand(new CommandGive());
        this.registerCommand(new CommandEffect());
        this.registerCommand(new CommandEnchant());
        this.registerCommand(new CommandServerEmote());
        this.registerCommand(new CommandShowSeed());
        this.registerCommand(new CommandHelp());
        this.registerCommand(new CommandDebug());
        this.registerCommand(new CommandServerMessage());
        this.registerCommand(new CommandServerSay());
        this.registerCommand(new CommandSetSpawnpoint());
        this.registerCommand(new CommandGameRule());
        this.registerCommand(new CommandClearInventory());
        this.registerCommand(new ServerCommandTestFor());
        this.registerCommand(new ServerCommandScoreboard());
        if (MinecraftServer.getServer().isDedicatedServer()) {
            this.registerCommand(new CommandServerOp());
            this.registerCommand(new CommandServerDeop());
            this.registerCommand(new CommandServerStop());
            this.registerCommand(new CommandServerSaveAll());
            this.registerCommand(new CommandServerSaveOff());
            this.registerCommand(new CommandServerSaveOn());
            this.registerCommand(new CommandServerBanIp());
            this.registerCommand(new CommandServerPardonIp());
            this.registerCommand(new CommandServerBan());
            this.registerCommand(new CommandServerBanlist());
            this.registerCommand(new CommandServerPardon());
            this.registerCommand(new CommandServerKick());
            this.registerCommand(new CommandServerList());
            this.registerCommand(new CommandServerWhitelist());
        }
        else {
            this.registerCommand(new CommandServerPublishLocal());
        }
        CommandBase.setAdminCommander(this);
    }
    
    @Override
    public void notifyAdmins(final ICommandSender par1ICommandSender, final int par2, final String par3Str, final Object... par4ArrayOfObj) {
        boolean var5 = true;
        if (par1ICommandSender instanceof TileEntityCommandBlock && !MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput")) {
            var5 = false;
        }
        if (var5) {
            for (final EntityPlayerMP var7 : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                if (var7 != par1ICommandSender && MinecraftServer.getServer().getConfigurationManager().areCommandsAllowed(var7.username)) {
                    var7.sendChatToPlayer(new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.ITALIC).append("[").append(par1ICommandSender.getCommandSenderName()).append(": ").append(var7.translateString(par3Str, par4ArrayOfObj)).append("]").toString());
                }
            }
        }
        if (par1ICommandSender != MinecraftServer.getServer()) {
            MinecraftServer.getServer().getLogAgent().logInfo("[" + par1ICommandSender.getCommandSenderName() + ": " + MinecraftServer.getServer().translateString(par3Str, par4ArrayOfObj) + "]");
        }
        if ((par2 & 0x1) != 0x1) {
            par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString(par3Str, par4ArrayOfObj));
        }
    }
}
