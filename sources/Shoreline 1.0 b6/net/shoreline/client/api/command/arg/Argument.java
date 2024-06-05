package net.shoreline.client.api.command.arg;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.impl.manager.client.CommandManager;
import net.shoreline.client.impl.event.gui.chat.ChatInputEvent;

import java.util.Collection;

/**
 * {@link Command} Argument structure which builds the value from the literal
 * argument. The argument can be marked as an optional value by using the
 * {@link OptionalArgument} annotation.
 *
 * @param <T> The argument value type
 * @author linus
 * @see Command
 * @see CommandManager
 * @since 1.0
 */
public abstract class Argument<T> extends Config<T> {
    // The literal string input of the user which will be updated every key
    // press. If the input is null, the argument is left blank.
    private String literal;
    //
    private boolean optional;

    /**
     * Initializes the config with a default value. This constructor should
     * not be used to initialize a configuration, instead use the explicit
     * definitions of the configs in {@link com.caspian.client.api.config.setting}.
     *
     * @param name The unique config identifier
     * @param desc The config description
     * @throws NullPointerException if value is <tt>null</tt>
     */
    public Argument(String name, String desc) {
        super(name.toLowerCase(), desc);
    }

    /**
     * Automatically completes the argument from the {@link #getSuggestion()}
     * values.
     *
     * @see #getSuggestion()
     */
    public String completeLiteral() {
        if (literal != null && !literal.isBlank()) {
            final String suggestion = getSuggestion();
            if (suggestion != null) {
                setLiteral(suggestion);
                return suggestion;
            }
        }
        return "";
    }

    /**
     * @throws ArgumentParseException
     * @see Command#onCommandInput()
     */
    @Override
    public abstract T getValue();

    /**
     * @return
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * @param literal
     * @see CommandManager#onChatInput(ChatInputEvent)
     */
    public void setLiteral(String literal) {
        this.literal = literal;
    }

    /**
     * @return
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * @param optional
     */
    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    /**
     * @return
     * @see #getSuggestions()
     */
    public String getSuggestion() {
        for (String suggestion : getSuggestions()) {
            if (suggestion.startsWith(literal)) {
                return suggestion;
            }
        }
        return literal;
    }

    /**
     * @return
     * @see #getSuggestion()
     */
    public abstract Collection<String> getSuggestions();
}
