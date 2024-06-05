package ClassSub;

import cn.Hanabi.command.*;
import org.jetbrains.annotations.*;
import java.util.*;

public class Class267 extends Command
{
    
    
    public Class267() {
        super("wdr", new String[] { "w" });
    }
    
    @Override
    public void run(final String s, @NotNull final String[] array) {
        if (array.length < 1) {
            throw new Class226("Usage: ." + s + " player");
        }
        Class200.tellPlayer("§b[Hanabi]§a暂时停用�?");
    }
    
    @Override
    public List<String> autocomplete(final int n, final String[] array) {
        return new ArrayList<String>();
    }
}
