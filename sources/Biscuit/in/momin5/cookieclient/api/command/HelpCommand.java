package in.momin5.cookieclient.api.command;

import in.momin5.cookieclient.api.util.utils.player.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;
import me.yagel15637.venture.command.ICommand;
import me.yagel15637.venture.exceptions.VentureException;
import me.yagel15637.venture.manager.CommandManager;

import java.util.Arrays;

public class HelpCommand extends AbstractCommand {
    public HelpCommand(){
        super("i have a really big penis, i hope that helps you ;)","help","?","help","commands","cmdlist");
    }

    @Override
    public void execute(String[] args) throws VentureException {
        for(ICommand command: CommandManager.getCommands()){
            MessageUtil.sendClientPrefixMessage(Arrays.toString(command.getAliases()) + " : " + command.getUsage());
        }
    }
}
