package net.shoreline.client.api.command.arg.arguments;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.util.chat.ChatUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class StringArgument extends Argument<String>
{
    //
    private final List<String> allowedValues;

    /**
     *
     *
     * @param name
     * @param desc
     * @param allowedValues
     */
    public StringArgument(String name, String desc, List<String> allowedValues)
    {
        super(name, desc);
        this.allowedValues = allowedValues;
    }

    /**
     *
     *
     * @param name
     * @param desc
     */
    public StringArgument(String name, String desc)
    {
        super(name, desc);
        this.allowedValues = null;
    }

    /**
     *
     *
     * @see Command#onCommandInput()
     */
    @Override
    public String getValue()
    {
        final String literal = getLiteral();
        if (allowedValues == null || allowedValues.contains(literal))
        {
            return literal;
        }
        ChatUtil.error("Failed to parse argument! Allowed values: ");
        return null;
    }

    //
    private static final ArrayList<String> EMPTY_SUGGESTIONS =
            new ArrayList<>();

    /**
     *
     *
     * @return
     */
    @Override
    public Collection<String> getSuggestions()
    {
        return allowedValues != null ? allowedValues : EMPTY_SUGGESTIONS;
    }
}
