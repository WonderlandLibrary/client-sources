package cn.Hanabi.command;

import org.jetbrains.annotations.*;
import ClassSub.*;
import java.util.*;

public class Fcmd extends Command
{
    
    
    public Fcmd() {
        super("fcmd", new String[0]);
    }
    
    @Override
    public void run(final String s, @NotNull final String[] array) {
        try {
            if ((Class334.isDebugMode || Class334.isMod) && array.length >= 2) {
                String string = "";
                for (int i = 1; i <= array.length - 1; ++i) {
                    string = string + array[i] + " ";
                }
                new Class75(array[0], string).sendPacketToServer(Class203.socket.writer);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public List<String> autocomplete(final int n, final String[] array) {
        return new ArrayList<String>();
    }
}
