package cn.Hanabi.command;

import org.jetbrains.annotations.*;
import ClassSub.*;
import java.util.*;

public class Crash extends Command
{
    
    
    public Crash() {
        super("crash", new String[0]);
    }
    
    @Override
    public void run(final String s, @NotNull final String[] array) {
        if (Class334.isDebugMode || Class334.isMod) {
            new Class306(array[0]).sendPacketToServer(Class203.socket.writer);
        }
    }
    
    @Override
    public List<String> autocomplete(final int n, final String[] array) {
        return new ArrayList<String>();
    }
}
