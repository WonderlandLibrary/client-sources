package net.minecraft.client.gui.spectator.categories;

import java.util.*;
import net.minecraft.client.gui.spectator.*;
import com.google.common.base.*;

public class SpectatorDetails
{
    private final List<ISpectatorMenuObject> field_178682_b;
    private final int field_178683_c;
    private final ISpectatorMenuView field_178684_a;
    
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int func_178681_b() {
        return this.field_178683_c;
    }
    
    public SpectatorDetails(final ISpectatorMenuView field_178684_a, final List<ISpectatorMenuObject> field_178682_b, final int field_178683_c) {
        this.field_178684_a = field_178684_a;
        this.field_178682_b = field_178682_b;
        this.field_178683_c = field_178683_c;
    }
    
    public ISpectatorMenuObject func_178680_a(final int n) {
        ISpectatorMenuObject field_178657_a;
        if (n >= 0 && n < this.field_178682_b.size()) {
            field_178657_a = (ISpectatorMenuObject)Objects.firstNonNull((Object)this.field_178682_b.get(n), (Object)SpectatorMenu.field_178657_a);
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            field_178657_a = SpectatorMenu.field_178657_a;
        }
        return field_178657_a;
    }
}
