package net.shoreline.client.api.command;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.ArgumentFactory;
import net.shoreline.client.api.config.ConfigContainer;
import net.shoreline.client.util.Globals;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public abstract class Command extends ConfigContainer implements Globals
{
    //
    private final String desc;
    // The command arguments, For reference: If the command line (chat) is
    // split up by whitespaces then the command ".friend add {player_name}"
    // would be split into args -> "add" and "{player_name}"
    private final List<Argument<?>> arguments = new ArrayList<>();

    /**
     *
     *
     * @param name
     * @param desc
     */
    public Command(String name, String desc)
    {
        super(name.toLowerCase());
        this.desc = desc;
    }

    /**
     *
     * @param arg
     */
    protected void register(Argument<?> arg)
    {
        arguments.add(arg);
    }

    /**
     *
     * @param args
     */
    protected void register(Argument<?>... args)
    {
        arguments.addAll(Arrays.asList(args));
    }

    /**
     *
     */
    @Override
    public void reflectConfigs()
    {
        final ArgumentFactory factory = new ArgumentFactory(this);
        // populate container using reflection
        for (Field field : getClass().getDeclaredFields())
        {
            if (Argument.class.isAssignableFrom(field.getType()))
            {
                Argument<?> argument = factory.build(field);
                if (argument == null)
                {
                    // failsafe for debugging purposes
                    Shoreline.error("Value for field {} is null!", field);
                    continue;
                }
                register(argument);
            }
        }
    }

    /**
     *
     * @param length
     * @return
     */
    public boolean isValidArgLength(int length)
    {
        int min = 0;
        int max = 0;
        for (Argument<?> arg : arguments)
        {
            max++;
            // Count required args
            if (arg.isOptional())
            {
                continue;
            }
            min++;
        }
        length -= 1;
        return length >= min && length <= max;
    }

    /**
     * Runs when the command is inputted in chat
     */
    public abstract void onCommandInput();

    /**
     *
     * @param prefix
     * @return
     */
    public String getLiteral(String prefix)
    {
        final StringBuilder literal = new StringBuilder(prefix);
        literal.append(getName());
        literal.append(" ");
        for (Argument<?> arg : arguments)
        {
            String l = arg.getLiteral().trim();
            literal.append(l);
            if (!l.isBlank())
            {
                literal.append(" ");
            }
        }
        return literal.toString();
    }

    /**
     * Returns the unique command identifier, used to identify the command in
     * the chat. Ex: help, prefix, openfolder, etc.
     * 
     * @return
     */
    public String getUsage()
    {
        StringBuilder usage = new StringBuilder();
        for (Argument<?> arg : arguments)
        {
            usage.append("<");
            usage.append(arg.getName());
            usage.append("> ");
        }
        return usage.toString();
    }

    /**
     * 
     * 
     * @return
     */
    public String getDescription()
    {
        return desc;
    }

    /**
     * Returns the {@link Argument} at the param index in the command
     * argument structure.
     *
     * @param i The index of the arg
     * @return Returns the arg at the param index
     */
    public Argument<?> getArg(int i)
    {
        if (i >= 0 && i < arguments.size())
        {
            return arguments.get(i);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public List<Argument<?>> getArgs()
    {
        return arguments;
    }
}
