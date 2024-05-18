package net.minecraft.client.gui;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.resources.*;

public class GuiResourcePackSelected extends GuiResourcePackList
{
    private static final String[] I;
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("!\u0001&\u0004\u0011!\u00070;\u00050\u000f{\u0018\u0001?\u00016\u001f\u00017J!\u0002\u0010?\u0001", "SdUkd");
    }
    
    @Override
    protected String getListHeader() {
        return I18n.format(GuiResourcePackSelected.I["".length()], new Object["".length()]);
    }
    
    public GuiResourcePackSelected(final Minecraft minecraft, final int n, final int n2, final List<ResourcePackListEntry> list) {
        super(minecraft, n, n2, list);
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
            if (-1 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
