package cn.Hanabi.command;

import java.util.*;
import org.jetbrains.annotations.*;

public abstract class Command
{
    private String name;
    private String[] aliases;
    
    protected Command(final String name, final String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }
    
    public abstract void run(final String p0, final String[] p1);
    
    public abstract List<String> autocomplete(final int p0, final String[] p1);
    
    boolean match(final String name) {
        for (final String alias : this.aliases) {
            if (alias.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return this.name.equalsIgnoreCase(name);
    }
    
    @NotNull
    List<String> getNameAndAliases() {
        final List<String> l = new ArrayList<String>();
        l.add(this.name);
        l.addAll(Arrays.asList(this.aliases));
        return l;
    }
}
