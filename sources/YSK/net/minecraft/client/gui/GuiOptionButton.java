package net.minecraft.client.gui;

import net.minecraft.client.settings.*;

public class GuiOptionButton extends GuiButton
{
    private final GameSettings.Options enumOptions;
    
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GameSettings.Options returnEnumOptions() {
        return this.enumOptions;
    }
    
    public GuiOptionButton(final int n, final int n2, final int n3, final GameSettings.Options enumOptions, final String s) {
        super(n, n2, n3, 56 + 6 + 47 + 41, 0xBA ^ 0xAE, s);
        this.enumOptions = enumOptions;
    }
    
    public GuiOptionButton(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        super(n, n2, n3, n4, n5, s);
        this.enumOptions = null;
    }
    
    public GuiOptionButton(final int n, final int n2, final int n3, final String s) {
        this(n, n2, n3, null, s);
    }
}
