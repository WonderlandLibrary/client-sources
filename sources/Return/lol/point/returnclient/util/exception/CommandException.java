package lol.point.returnclient.util.exception;

import lol.point.returnclient.util.minecraft.ChatUtil;
import net.minecraft.util.EnumChatFormatting;

public class CommandException extends IllegalArgumentException {

    public CommandException(String message) {
        super(message);
        ChatUtil.addChatMessage(EnumChatFormatting.RED + message);
    }
}
