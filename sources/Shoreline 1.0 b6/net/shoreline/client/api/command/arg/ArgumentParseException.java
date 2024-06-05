package net.shoreline.client.api.command.arg;

import net.shoreline.client.util.chat.ChatUtil;

/**
 * @author linus
 * @see Argument
 * @see RuntimeException
 * @since 1.0
 */
public class ArgumentParseException extends RuntimeException {
    /**
     * @param message
     */
    public ArgumentParseException(String message) {
        super(message);
        ChatUtil.error(message);
    }
}
