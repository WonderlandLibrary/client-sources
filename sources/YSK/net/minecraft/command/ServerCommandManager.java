package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.rcon.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.command.server.*;

public class ServerCommandManager extends CommandHandler implements IAdminCommand
{
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public void notifyOperators(final ICommandSender commandSender, final ICommand command, final int n, final String s, final Object... array) {
        int n2 = " ".length();
        final MinecraftServer server = MinecraftServer.getServer();
        if (!commandSender.sendCommandFeedback()) {
            n2 = "".length();
        }
        final String s2 = ServerCommandManager.I["".length()];
        final Object[] array2 = new Object["  ".length()];
        array2["".length()] = commandSender.getName();
        array2[" ".length()] = new ChatComponentTranslation(s, array);
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(s2, array2);
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GRAY);
        chatComponentTranslation.getChatStyle().setItalic(" ".length() != 0);
        if (n2 != 0) {
            final Iterator<EntityPlayerMP> iterator = server.getConfigurationManager().func_181057_v().iterator();
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityPlayerMP entityPlayerMP = iterator.next();
                if (entityPlayerMP != commandSender && server.getConfigurationManager().canSendCommands(entityPlayerMP.getGameProfile()) && command.canCommandSenderUseCommand(commandSender)) {
                    int n3;
                    if (commandSender instanceof MinecraftServer && MinecraftServer.getServer().func_183002_r()) {
                        n3 = " ".length();
                        "".length();
                        if (3 < 1) {
                            throw null;
                        }
                    }
                    else {
                        n3 = "".length();
                    }
                    final int n4 = n3;
                    int n5;
                    if (commandSender instanceof RConConsoleSource && MinecraftServer.getServer().func_181034_q()) {
                        n5 = " ".length();
                        "".length();
                        if (0 >= 3) {
                            throw null;
                        }
                    }
                    else {
                        n5 = "".length();
                    }
                    final int n6 = n5;
                    if (n4 == 0 && n6 == 0 && (commandSender instanceof RConConsoleSource || commandSender instanceof MinecraftServer)) {
                        continue;
                    }
                    entityPlayerMP.addChatMessage(chatComponentTranslation);
                }
            }
        }
        if (commandSender != server && server.worldServers["".length()].getGameRules().getBoolean(ServerCommandManager.I[" ".length()])) {
            server.addChatMessage(chatComponentTranslation);
        }
        boolean b = server.worldServers["".length()].getGameRules().getBoolean(ServerCommandManager.I["  ".length()]);
        if (commandSender instanceof CommandBlockLogic) {
            b = ((CommandBlockLogic)commandSender).shouldTrackOutput();
        }
        if (((n & " ".length()) != " ".length() && b) || commandSender instanceof MinecraftServer) {
            commandSender.addChatMessage(new ChatComponentTranslation(s, array));
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ServerCommandManager() {
        this.registerCommand(new CommandTime());
        this.registerCommand(new CommandGameMode());
        this.registerCommand(new CommandDifficulty());
        this.registerCommand(new CommandDefaultGameMode());
        this.registerCommand(new CommandKill());
        this.registerCommand(new CommandToggleDownfall());
        this.registerCommand(new CommandWeather());
        this.registerCommand(new CommandXP());
        this.registerCommand(new CommandTeleport());
        this.registerCommand(new CommandGive());
        this.registerCommand(new CommandReplaceItem());
        this.registerCommand(new CommandStats());
        this.registerCommand(new CommandEffect());
        this.registerCommand(new CommandEnchant());
        this.registerCommand(new CommandParticle());
        this.registerCommand(new CommandEmote());
        this.registerCommand(new CommandShowSeed());
        this.registerCommand(new CommandHelp());
        this.registerCommand(new CommandDebug());
        this.registerCommand(new CommandMessage());
        this.registerCommand(new CommandBroadcast());
        this.registerCommand(new CommandSetSpawnpoint());
        this.registerCommand(new CommandSetDefaultSpawnpoint());
        this.registerCommand(new CommandGameRule());
        this.registerCommand(new CommandClearInventory());
        this.registerCommand(new CommandTestFor());
        this.registerCommand(new CommandSpreadPlayers());
        this.registerCommand(new CommandPlaySound());
        this.registerCommand(new CommandScoreboard());
        this.registerCommand(new CommandExecuteAt());
        this.registerCommand(new CommandTrigger());
        this.registerCommand(new CommandAchievement());
        this.registerCommand(new CommandSummon());
        this.registerCommand(new CommandSetBlock());
        this.registerCommand(new CommandFill());
        this.registerCommand(new CommandClone());
        this.registerCommand(new CommandCompare());
        this.registerCommand(new CommandBlockData());
        this.registerCommand(new CommandTestForBlock());
        this.registerCommand(new CommandMessageRaw());
        this.registerCommand(new CommandWorldBorder());
        this.registerCommand(new CommandTitle());
        this.registerCommand(new CommandEntityData());
        if (MinecraftServer.getServer().isDedicatedServer()) {
            this.registerCommand(new CommandOp());
            this.registerCommand(new CommandDeOp());
            this.registerCommand(new CommandStop());
            this.registerCommand(new CommandSaveAll());
            this.registerCommand(new CommandSaveOff());
            this.registerCommand(new CommandSaveOn());
            this.registerCommand(new CommandBanIp());
            this.registerCommand(new CommandPardonIp());
            this.registerCommand(new CommandBanPlayer());
            this.registerCommand(new CommandListBans());
            this.registerCommand(new CommandPardonPlayer());
            this.registerCommand(new CommandServerKick());
            this.registerCommand(new CommandListPlayers());
            this.registerCommand(new CommandWhitelist());
            this.registerCommand(new CommandSetPlayerTimeout());
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            this.registerCommand(new CommandPublishLocalServer());
        }
        CommandBase.setAdminCommander(this);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("3\u000f\u0000&F$\u001e\u00117F1\u0003\f;\u0006", "PgaRh");
        ServerCommandManager.I[" ".length()] = I(")  \u000f\u0012(&)\r\u0019(\"& \u00126", "EOGNv");
        ServerCommandManager.I["  ".length()] = I("\u0017-\n+0\u000b%\t.\u001d\u0000\u000e\u0001*\u0017\u0006)\u0007$", "dHdOs");
    }
}
