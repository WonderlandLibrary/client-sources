package net.minecraft.client.gui;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.resources.*;

public class GuiResourcePackAvailable extends GuiResourcePackList
{
    private static final String[] I;
    
    public GuiResourcePackAvailable(final Minecraft minecraft, final int n, final int n2, final List<ResourcePackListEntry> list) {
        super(minecraft, n, n2, list);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("&\b67\"&\u000e \b67\u0006k9!5\u0004)958\bk,> \u0001 ", "TmEXW");
    }
    
    @Override
    protected String getListHeader() {
        return I18n.format(GuiResourcePackAvailable.I["".length()], new Object["".length()]);
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
