package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

public class ServerCommandManager extends CommandHandler implements IAdminCommand
{
    private static final String HorizonCode_Horizon_È = "CL_00000922";
    
    public ServerCommandManager() {
        this.HorizonCode_Horizon_È(new CommandTime());
        this.HorizonCode_Horizon_È(new CommandGameMode());
        this.HorizonCode_Horizon_È(new CommandDifficulty());
        this.HorizonCode_Horizon_È(new CommandDefaultGameMode());
        this.HorizonCode_Horizon_È(new CommandKill());
        this.HorizonCode_Horizon_È(new CommandToggleDownfall());
        this.HorizonCode_Horizon_È(new CommandWeather());
        this.HorizonCode_Horizon_È(new CommandXP());
        this.HorizonCode_Horizon_È(new CommandTeleport());
        this.HorizonCode_Horizon_È(new CommandGive());
        this.HorizonCode_Horizon_È(new CommandReplaceItem());
        this.HorizonCode_Horizon_È(new CommandStats());
        this.HorizonCode_Horizon_È(new CommandEffect());
        this.HorizonCode_Horizon_È(new CommandEnchant());
        this.HorizonCode_Horizon_È(new CommandParticle());
        this.HorizonCode_Horizon_È(new CommandEmote());
        this.HorizonCode_Horizon_È(new CommandShowSeed());
        this.HorizonCode_Horizon_È(new CommandHelp());
        this.HorizonCode_Horizon_È(new CommandDebug());
        this.HorizonCode_Horizon_È(new CommandMessage());
        this.HorizonCode_Horizon_È(new CommandBroadcast());
        this.HorizonCode_Horizon_È(new CommandSetSpawnpoint());
        this.HorizonCode_Horizon_È(new CommandSetDefaultSpawnpoint());
        this.HorizonCode_Horizon_È(new CommandGameRule());
        this.HorizonCode_Horizon_È(new CommandClearInventory());
        this.HorizonCode_Horizon_È(new CommandTestFor());
        this.HorizonCode_Horizon_È(new CommandSpreadPlayers());
        this.HorizonCode_Horizon_È(new CommandPlaySound());
        this.HorizonCode_Horizon_È(new CommandScoreboard());
        this.HorizonCode_Horizon_È(new CommandExecuteAt());
        this.HorizonCode_Horizon_È(new CommandTrigger());
        this.HorizonCode_Horizon_È(new CommandAchievement());
        this.HorizonCode_Horizon_È(new CommandSummon());
        this.HorizonCode_Horizon_È(new CommandSetBlock());
        this.HorizonCode_Horizon_È(new CommandFill());
        this.HorizonCode_Horizon_È(new CommandClone());
        this.HorizonCode_Horizon_È(new CommandCompare());
        this.HorizonCode_Horizon_È(new CommandBlockData());
        this.HorizonCode_Horizon_È(new CommandTestForBlock());
        this.HorizonCode_Horizon_È(new CommandMessageRaw());
        this.HorizonCode_Horizon_È(new CommandWorldBorder());
        this.HorizonCode_Horizon_È(new CommandTitle());
        this.HorizonCode_Horizon_È(new CommandEntityData());
        if (MinecraftServer.áƒ().Ä()) {
            this.HorizonCode_Horizon_È(new CommandOp());
            this.HorizonCode_Horizon_È(new CommandDeOp());
            this.HorizonCode_Horizon_È(new CommandStop());
            this.HorizonCode_Horizon_È(new CommandSaveAll());
            this.HorizonCode_Horizon_È(new CommandSaveOff());
            this.HorizonCode_Horizon_È(new CommandSaveOn());
            this.HorizonCode_Horizon_È(new CommandBanIp());
            this.HorizonCode_Horizon_È(new CommandPardonIp());
            this.HorizonCode_Horizon_È(new CommandBanPlayer());
            this.HorizonCode_Horizon_È(new CommandListBans());
            this.HorizonCode_Horizon_È(new CommandPardonPlayer());
            this.HorizonCode_Horizon_È(new CommandServerKick());
            this.HorizonCode_Horizon_È(new CommandListPlayers());
            this.HorizonCode_Horizon_È(new CommandWhitelist());
            this.HorizonCode_Horizon_È(new CommandSetPlayerTimeout());
        }
        else {
            this.HorizonCode_Horizon_È(new CommandPublishLocalServer());
        }
        CommandBase.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final ICommand command, final int p_152372_3_, final String msgFormat, final Object... msgParams) {
        boolean var6 = true;
        final MinecraftServer var7 = MinecraftServer.áƒ();
        if (!sender.g_()) {
            var6 = false;
        }
        final ChatComponentTranslation var8 = new ChatComponentTranslation("chat.type.admin", new Object[] { sender.v_(), new ChatComponentTranslation(msgFormat, msgParams) });
        var8.à().HorizonCode_Horizon_È(EnumChatFormatting.Ø);
        var8.à().Â(Boolean.valueOf(true));
        if (var6) {
            for (final EntityPlayer var10 : var7.Œ().Âµá€) {
                if (var10 != sender && var7.Œ().Âµá€(var10.áˆºà()) && command.HorizonCode_Horizon_È(sender)) {
                    var10.HorizonCode_Horizon_È(var8);
                }
            }
        }
        if (sender != var7 && var7.Ý[0].Çªà¢().Â("logAdminCommands")) {
            var7.HorizonCode_Horizon_È(var8);
        }
        boolean var11 = var7.Ý[0].Çªà¢().Â("sendCommandFeedback");
        if (sender instanceof CommandBlockLogic) {
            var11 = ((CommandBlockLogic)sender).á();
        }
        if ((p_152372_3_ & 0x1) != 0x1 && var11) {
            sender.HorizonCode_Horizon_È(new ChatComponentTranslation(msgFormat, msgParams));
        }
    }
}
