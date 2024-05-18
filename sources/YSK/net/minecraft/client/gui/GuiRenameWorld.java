package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import java.io.*;
import org.lwjgl.input.*;

public class GuiRenameWorld extends GuiScreen
{
    private static final String[] I;
    private GuiScreen parentScreen;
    private final String saveName;
    private GuiTextField field_146583_f;
    
    public GuiRenameWorld(final GuiScreen parentScreen, final String saveName) {
        this.parentScreen = parentScreen;
        this.saveName = saveName;
    }
    
    @Override
    public void updateScreen() {
        this.field_146583_f.updateCursorCounter();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiRenameWorld.I["  ".length()], new Object["".length()]), this.width / "  ".length(), 0x28 ^ 0x3C, 14380227 + 5793749 - 11010363 + 7613602);
        this.drawString(this.fontRendererObj, I18n.format(GuiRenameWorld.I["   ".length()], new Object["".length()]), this.width / "  ".length() - (0xC4 ^ 0xA0), 0x55 ^ 0x7A, 1731088 + 4069000 - 3548256 + 8275048);
        this.field_146583_f.drawTextBox();
        super.drawScreen(n, n2, n3);
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        this.field_146583_f.textboxKeyTyped(c, n);
        final GuiButton guiButton = this.buttonList.get("".length());
        int enabled;
        if (this.field_146583_f.getText().trim().length() > 0) {
            enabled = " ".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            enabled = "".length();
        }
        guiButton.enabled = (enabled != 0);
        if (n == (0x11 ^ 0xD) || n == 55 + 119 - 165 + 147) {
            this.actionPerformed(this.buttonList.get("".length()));
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.field_146583_f.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)("".length() != 0));
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x38 ^ 0x3C])["".length()] = I("\u0006-&\b5\u0001\u001f%\u001f:\u0011f8\b8\u0014%//#\u0001<%\u0003", "uHJmV");
        GuiRenameWorld.I[" ".length()] = I(">\u0005?_\"8\u001e5\u0014-", "YpVqA");
        GuiRenameWorld.I["  ".length()] = I("\u0010-\u0003\n\u001b\u0017\u001f\u0000\u001d\u0014\u0007f\u001d\n\u0016\u0002%\n;\u0011\u0017$\n", "cHoox");
        GuiRenameWorld.I["   ".length()] = I("0#=?.7\u0011>(!'h449&4\u001f; &", "CFQZM");
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)(" ".length() != 0));
        this.buttonList.clear();
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (0xF1 ^ 0x95), this.height / (0x1E ^ 0x1A) + (0x6E ^ 0xE) + (0x93 ^ 0x9F), I18n.format(GuiRenameWorld.I["".length()], new Object["".length()])));
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (0x75 ^ 0x11), this.height / (0x1D ^ 0x19) + (0x62 ^ 0x1A) + (0x37 ^ 0x3B), I18n.format(GuiRenameWorld.I[" ".length()], new Object["".length()])));
        final String worldName = this.mc.getSaveLoader().getWorldInfo(this.saveName).getWorldName();
        (this.field_146583_f = new GuiTextField("  ".length(), this.fontRendererObj, this.width / "  ".length() - (0xE2 ^ 0x86), 0x42 ^ 0x7E, 66 + 24 - 84 + 194, 0xB3 ^ 0xA7)).setFocused(" ".length() != 0);
        this.field_146583_f.setText(worldName);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == " ".length()) {
                this.mc.displayGuiScreen(this.parentScreen);
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else if (guiButton.id == 0) {
                this.mc.getSaveLoader().renameWorld(this.saveName, this.field_146583_f.getText().trim());
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }
}
