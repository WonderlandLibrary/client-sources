package lol.point.returnclient.command.impl;

import lol.point.Return;
import lol.point.returnclient.command.Command;
import lol.point.returnclient.command.CommandInfo;
import lol.point.returnclient.util.exception.CommandException;
import lol.point.returnclient.util.minecraft.ChatUtil;
import net.minecraft.util.EnumChatFormatting;

import java.util.Locale;

@CommandInfo(
        name = "Help",
        usage = ".help <command>",
        description = "helps you with commands",
        aliases = {"help"}
)
public class Help extends Command {

    @Override
    public void execute(String... args) throws CommandException {
        if (args.length > 0) {
            ChatUtil.addChatMessage("\n");
            return;
        }

        ChatUtil.addChatMessage("§eVersion: §a" + Return.INSTANCE.version);
        ChatUtil.addChatMessage("§eAuthors: §a" + Return.INSTANCE.authors + "\n");


        Return.INSTANCE.commandManager
                .getCommands()
                .values()
                .forEach(command -> ChatUtil.addChatMessage(String.format("§e%s%s " + EnumChatFormatting.WHITE + "- " + EnumChatFormatting.GRAY + "%s", Return.INSTANCE.commandPrefix, command.getName().toLowerCase(Locale.ROOT), command.getDescription())));
    }
}
