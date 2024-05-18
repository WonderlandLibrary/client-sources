package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.client.*;

public class GuiListButton extends GuiButton
{
    private boolean field_175216_o;
    private String localizationStr;
    private final GuiPageButtonList.GuiResponder guiResponder;
    private static final String[] I;
    
    private String buildDisplayString() {
        final StringBuilder append = new StringBuilder(String.valueOf(I18n.format(this.localizationStr, new Object["".length()]))).append(GuiListButton.I[" ".length()]);
        String s;
        if (this.field_175216_o) {
            s = I18n.format(GuiListButton.I["  ".length()], new Object["".length()]);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            s = I18n.format(GuiListButton.I["   ".length()], new Object["".length()]);
        }
        return append.append(s).toString();
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
            if (-1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void func_175212_b(final boolean field_175216_o) {
        this.field_175216_o = field_175216_o;
        this.displayString = this.buildDisplayString();
        this.guiResponder.func_175321_a(this.id, field_175216_o);
    }
    
    static {
        I();
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            int field_175216_o;
            if (this.field_175216_o) {
                field_175216_o = "".length();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                field_175216_o = " ".length();
            }
            this.field_175216_o = (field_175216_o != 0);
            this.displayString = this.buildDisplayString();
            this.guiResponder.func_175321_a(this.id, this.field_175216_o);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0xB1 ^ 0xB5])["".length()] = I("", "RKSNO");
        GuiListButton.I[" ".length()] = I("sp", "IPsWy");
        GuiListButton.I["  ".length()] = I(",\u001b\u0013i3.\u001d", "KnzGJ");
        GuiListButton.I["   ".length()] = I("\t\u0010%`\b\u0001", "neLNf");
    }
    
    public GuiListButton(final GuiPageButtonList.GuiResponder guiResponder, final int n, final int n2, final int n3, final String localizationStr, final boolean field_175216_o) {
        super(n, n2, n3, 109 + 63 - 78 + 56, 0x25 ^ 0x31, GuiListButton.I["".length()]);
        this.localizationStr = localizationStr;
        this.field_175216_o = field_175216_o;
        this.displayString = this.buildDisplayString();
        this.guiResponder = guiResponder;
    }
}
