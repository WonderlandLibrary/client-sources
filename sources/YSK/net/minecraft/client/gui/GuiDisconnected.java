package net.minecraft.client.gui;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.resources.*;
import java.io.*;

public class GuiDisconnected extends GuiScreen
{
    private int field_175353_i;
    private IChatComponent message;
    private String reason;
    private final GuiScreen parentScreen;
    private static final String[] I;
    private List<String> multilineMessage;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0013\u001a-J\u0016\u001b\"!\n\u0017", "toDdb");
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.reason, this.width / "  ".length(), this.height / "  ".length() - this.field_175353_i / "  ".length() - this.fontRendererObj.FONT_HEIGHT * "  ".length(), 2146600 + 3743188 + 1556658 + 3738364);
        int n4 = this.height / "  ".length() - this.field_175353_i / "  ".length();
        if (this.multilineMessage != null) {
            final Iterator<String> iterator = this.multilineMessage.iterator();
            "".length();
            if (2 < -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                this.drawCenteredString(this.fontRendererObj, iterator.next(), this.width / "  ".length(), n4, 5621520 + 8896927 - 12735699 + 14994467);
                n4 += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(n, n2, n3);
    }
    
    public GuiDisconnected(final GuiScreen parentScreen, final String s, final IChatComponent message) {
        this.parentScreen = parentScreen;
        this.reason = I18n.format(s, new Object["".length()]);
        this.message = message;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.multilineMessage = (List<String>)this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - (0x79 ^ 0x4B));
        this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (0x1F ^ 0x7B), this.height / "  ".length() + this.field_175353_i / "  ".length() + this.fontRendererObj.FONT_HEIGHT, I18n.format(GuiDisconnected.I["".length()], new Object["".length()])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
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
            if (4 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
}
