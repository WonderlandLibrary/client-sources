package net.minecraft.client.gui.spectator;

import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.client.gui.spectator.categories.*;

public class BaseSpectatorGroup implements ISpectatorMenuView
{
    private static final String[] I;
    private final List<ISpectatorMenuObject> field_178671_a;
    
    @Override
    public IChatComponent func_178670_b() {
        return new ChatComponentText(BaseSpectatorGroup.I["".length()]);
    }
    
    @Override
    public List<ISpectatorMenuObject> func_178669_a() {
        return this.field_178671_a;
    }
    
    public BaseSpectatorGroup() {
        (this.field_178671_a = (List<ISpectatorMenuObject>)Lists.newArrayList()).add(new TeleportToPlayer());
        this.field_178671_a.add(new TeleportToTeam());
    }
    
    static {
        I();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001b\u0016\u0003\u001d\u0005k\u0005F\u0005\u00132D\u0012\u0001V8\u0001\n\u000b\u0015?D\u0007N\u0015$\t\u000b\u000f\u0018/HF\u000f\u0018/D\u0007\t\u0017\"\nF\u001a\u0019k\u0011\u0015\u000bV\"\u0010H", "Kdfnv");
    }
}
