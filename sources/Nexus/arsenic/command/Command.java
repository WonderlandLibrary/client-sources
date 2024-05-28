package arsenic.command;

import java.util.ArrayList;
import java.util.List;

import arsenic.utils.java.JavaUtils;

public abstract class Command {

    protected String name;
    protected String help;
    private final int minArgs;
    protected String[] aliases;
    protected String[] args;
    protected String usage;

    public Command() {
        if (!this.getClass().isAnnotationPresent(CommandInfo.class))
            throw new IllegalArgumentException("No @CommandInfo on class " + this.getClass().getCanonicalName());

        final CommandInfo info = this.getClass().getDeclaredAnnotation(CommandInfo.class);

        minArgs = info.minArgs();
        name = info.name();
        help = info.help();
        aliases = info.aliases();
        args = info.args();

        StringBuilder bobTheBuilder = new StringBuilder("." + getName());
        for (String arg : getArgs())
            bobTheBuilder.append(" <" + arg + ">");
        usage = bobTheBuilder.toString();
    }

    public abstract void execute(String[] args);

    public final List<String> getAutoComplete(String str, int arg) {
        return getAutoComplete(str, arg, new ArrayList<>());
    }

    //str and arg are used when this method is @Overridden
    protected List<String> getAutoComplete(String str, int arg, List<String> completions) {
        return completions;
    }

    public String getName() { return name; }

    public String getHelp() { return help; }

    public String[] getAliases() { return aliases; }

    public String[] getArgs() { return args; }

    public boolean isName(String name) {
        for (final String alias : JavaUtils.concat(aliases, new String[] { this.name }))
            if (alias.equalsIgnoreCase(name))
                return true;
        return false;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public String getUsage() {
        return usage;
    }
}
