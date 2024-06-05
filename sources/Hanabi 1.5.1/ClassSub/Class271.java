package ClassSub;

import cn.Hanabi.command.*;
import org.jetbrains.annotations.*;
import cn.Hanabi.*;
import cn.Hanabi.modules.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;

public class Class271 extends Command
{
    
    
    public Class271() {
        super("toggle", new String[] { "t" });
    }
    
    @Override
    public void run(final String s, @NotNull final String[] array) {
        if (array.length < 1) {
            throw new Class226("Usage: ." + s + " <module> [<on/off>]");
        }
        final ModManager moduleManager = Hanabi.INSTANCE.moduleManager;
        final Mod module = ModManager.getModule(array[0], false);
        if (module == null) {
            throw new Class226("The module '" + array[0] + "' does not exist");
        }
        boolean state = !module.getState();
        if (array.length >= 2) {
            if (array[1].equalsIgnoreCase("on")) {
                state = true;
            }
            else {
                if (!array[1].equalsIgnoreCase("off")) {
                    throw new Class226("Usage: ." + s + " <module> <on/off>");
                }
                state = false;
            }
        }
        module.setState(state);
        Class64.success(module.getName() + " was " + "§7" + (state ? "enabled" : "disabled"));
    }
    
    @Override
    public List<String> autocomplete(final int n, final String[] array) {
        String s = "";
        boolean b = false;
        try {
            if (n == 0) {
                b = true;
            }
            else if (n == 1) {
                b = true;
                s = array[0];
            }
        }
        catch (Exception ex) {}
        if (b) {
            return Hanabi.INSTANCE.moduleManager.getModules().stream().filter((Predicate<? super Object>)Class271::lambda$autocomplete$0).map((Function<? super Object, ?>)Mod::getName).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        }
        return new ArrayList<String>();
    }
    
    private static boolean lambda$autocomplete$0(final String s, final Mod mod) {
        return mod.getName().toLowerCase().startsWith(s);
    }
}
