package net.shoreline.client.api.command.arg.arguments;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.util.chat.ChatUtil;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author linus
 * @since 1.0
 */
public class BooleanArgument extends Argument<Boolean> {

    /**
     * Initializes the config with a default value. This constructor should
     * not be used to initialize a configuration, instead use the explicit
     * definitions of the configs in {@link com.caspian.client.api.config.setting}.
     *
     * @param name The unique config identifier
     * @param desc The config description
     * @throws NullPointerException if value is <tt>null</tt>
     */
    public BooleanArgument(String name, String desc) {
        super(name, desc);
    }

    /**
     * @see Command#onCommandInput()
     */
    @Override
    public Boolean getValue() {
        // TODO: Make cleaner
        String literal = getLiteral();
        if (literal == null) {
            return null;
        }
        if (literal.equalsIgnoreCase("t")
                || literal.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        } else if (literal.equalsIgnoreCase("f")
                || literal.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        ChatUtil.error("Failed to parse Boolean argument!");
        return null;
    }

    /**
     * @return
     */
    @Override
    public Collection<String> getSuggestions() {
        return Arrays.asList("true", "false");
    }
}
