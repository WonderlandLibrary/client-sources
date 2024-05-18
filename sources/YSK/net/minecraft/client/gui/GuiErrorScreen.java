package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import java.io.*;

public class GuiErrorScreen extends GuiScreen
{
    private String field_146313_a;
    private static final String[] I;
    private String field_146312_f;
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (0x70 ^ 0x14), 27 + 120 - 54 + 47, I18n.format(GuiErrorScreen.I["".length()], new Object["".length()])));
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0002&\u0005t\u0010\u0004=\u000f?\u001f", "eSlZs");
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
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        this.mc.displayGuiScreen(null);
    }
    
    public GuiErrorScreen(final String field_146313_a, final String field_146312_f) {
        this.field_146313_a = field_146313_a;
        this.field_146312_f = field_146312_f;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawGradientRect("".length(), "".length(), this.width, this.height, -(9825646 + 6523863 - 9199317 + 5424496), -(5017010 + 2683151 - 51031 + 3881094));
        this.drawCenteredString(this.fontRendererObj, this.field_146313_a, this.width / "  ".length(), 0x68 ^ 0x32, 9376464 + 1632985 + 1196392 + 4571374);
        this.drawCenteredString(this.fontRendererObj, this.field_146312_f, this.width / "  ".length(), 0x7E ^ 0x10, 9260238 + 7184942 - 13641813 + 13973848);
        super.drawScreen(n, n2, n3);
    }
}
