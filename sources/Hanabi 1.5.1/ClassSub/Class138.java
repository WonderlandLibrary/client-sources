package ClassSub;

import cn.Hanabi.command.*;
import org.jetbrains.annotations.*;
import cn.Hanabi.modules.*;
import java.util.*;

public class Class138 extends Command
{
    
    
    public Class138() {
        super("irc", new String[] { "i" });
    }
    
    @Override
    public void run(final String s, @NotNull final String[] array) {
        if (array.length < 1) {
            throw new Class226("Usage: ." + s + " message");
        }
        String string = "";
        for (int i = 0; i < array.length; ++i) {
            string = string + array[i] + " ";
        }
        if (ModManager.getModule("IRC").isEnabled()) {
            new Class199(Class203.type, Class194.getIRCUserByNameAndType(Class203.type, Class203.username), string).sendPacketToServer(Class203.socket.writer);
        }
        else {
            Class120.sendClientMessage("Open your IRC first then you can send message!", Class84.Class307.ERROR);
        }
    }
    
    @Override
    public List<String> autocomplete(final int n, final String[] array) {
        return new ArrayList<String>();
    }
}
