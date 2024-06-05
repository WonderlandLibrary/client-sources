package cn.Hanabi.command;

import org.jetbrains.annotations.*;
import cn.Hanabi.modules.*;
import org.lwjgl.input.*;
import ClassSub.*;
import java.util.*;

public class Binds extends Command
{
    
    
    public Binds() {
        super("binds", new String[0]);
    }
    
    @Override
    public void run(final String s, @NotNull final String[] array) {
        try {
            for (final Mod mod : ModManager.modules) {
                if (mod.getKeybind() != 0) {
                    Class200.tellPlayer("§b[Hanabi]§a" + mod.getName() + " - " + Keyboard.getKeyName(mod.getKeybind()));
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @Override
    public List<String> autocomplete(final int n, final String[] array) {
        return new ArrayList<String>();
    }
}
