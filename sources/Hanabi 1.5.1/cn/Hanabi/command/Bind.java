package cn.Hanabi.command;

import org.jetbrains.annotations.*;
import cn.Hanabi.*;
import cn.Hanabi.modules.*;
import ClassSub.*;
import org.lwjgl.input.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;

public class Bind extends Command
{
    private boolean active;
    @Nullable
    private Mod currentModule;
    
    
    public Bind() {
        super("bind", new String[0]);
        this.active = false;
        this.currentModule = null;
        EventManager.register(this);
    }
    
    @Override
    public void run(final String s, @NotNull final String[] array) {
        if (array.length == 0) {
            throw new Class226("Usage: ." + s + " <module> [<none/show>]");
        }
        final ModManager moduleManager = Hanabi.INSTANCE.moduleManager;
        final Mod module = ModManager.getModule(array[0], false);
        if (module == null) {
            throw new Class226("The module '" + array[0] + "' does not exist");
        }
        if (array.length > 1) {
            if (array[1].equalsIgnoreCase("none")) {
                module.setKeybind(0);
                Class64.success("§1" + module.getName() + "§7" + " was bound to " + "§1" + "NONE");
            }
            else if (array[1].equalsIgnoreCase("show")) {
                Class64.success("§1" + module.getName() + "§7" + " is bound to " + "§1" + Keyboard.getKeyName(module.getKeybind()));
            }
            else {
                final int keyIndex = Keyboard.getKeyIndex(array[1].toUpperCase());
                module.setKeybind(keyIndex);
                Class64.success("§1" + module.getName() + "§7" + " was bound to " + "§1" + Keyboard.getKeyName(keyIndex));
            }
            return;
        }
        this.active = true;
        this.currentModule = module;
        Class64.info("Please press a key");
    }
    
    @Override
    public List<String> autocomplete(final int n, final String[] array) {
        String s = "";
        boolean b = false;
        if (n == 0 || array.length == 0) {
            b = true;
        }
        else if (n == 1) {
            b = true;
            s = array[0];
        }
        if (b) {
            return Hanabi.INSTANCE.moduleManager.getModules().stream().filter((Predicate<? super Object>)Bind::lambda$autocomplete$0).map((Function<? super Object, ?>)Mod::getName).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        }
        if (n == 2) {
            final ArrayList<String> list = new ArrayList<String>();
            list.add("none");
            list.add("show");
            return list;
        }
        return new ArrayList<String>();
    }
    
    @EventTarget
    public void onKey(@NotNull final EventKey eventKey) {
        if (this.active) {
            this.currentModule.setKeybind(eventKey.getKey());
            Class64.success("§1" + this.currentModule.getName() + "§7" + " was bound to " + "§1" + Keyboard.getKeyName(eventKey.getKey()));
            this.active = false;
            this.currentModule = null;
        }
    }
    
    private static boolean lambda$autocomplete$0(final String s, final Mod mod) {
        return mod.getName().toLowerCase().startsWith(s);
    }
}
