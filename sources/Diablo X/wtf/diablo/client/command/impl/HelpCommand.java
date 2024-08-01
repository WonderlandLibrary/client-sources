package wtf.diablo.client.command.impl;

import wtf.diablo.client.command.api.AbstractCommand;
import wtf.diablo.client.command.api.data.Command;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;

@Command(name = "help", description = "Displays all commands")
public final class HelpCommand extends AbstractCommand {
    @Override
    public void execute(String[] args) {
        for (final AbstractCommand command : Diablo.getInstance().getCommandRepository().getCommands()) {
            ChatUtil.addChatMessage(command.getName() + " - " + command.getDescription());
        }
    }
}
