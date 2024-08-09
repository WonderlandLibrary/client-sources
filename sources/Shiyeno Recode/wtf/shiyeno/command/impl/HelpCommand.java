package wtf.shiyeno.command.impl;

import wtf.shiyeno.command.Command;
import wtf.shiyeno.command.CommandInfo;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.util.ClientUtil;

@CommandInfo(name = "help", description = "Телепортирует вас вперед.")
public class HelpCommand extends Command {
    @Override
    public void run(String[] args) throws Exception {
        for (Command cmd : Managment.COMMAND_MANAGER.getCommands()) {
            if (cmd instanceof HelpCommand) continue;
            ClientUtil.sendMesage(cmd.command + " | " + cmd.description);
        }
    }

    @Override
    public void error() {

    }
}
