package cn.Hanabi.command;

import org.jetbrains.annotations.*;
import ClassSub.*;
import java.util.*;

public class Whois extends Command
{
    
    
    public Whois() {
        super("whois", new String[0]);
    }
    
    @Override
    public void run(final String s, @NotNull final String[] array) {
        if ((Class334.isDebugMode || Class334.isMod) && array.length == 1) {
            Class200.tellPlayer("§c[Whois]Trying to search §e" + array[0] + "§c...");
            new Class178(array[0]).sendPacketToServer(Class203.socket.writer);
        }
    }
    
    @Override
    public List<String> autocomplete(final int n, final String[] array) {
        return new ArrayList<String>();
    }
}
