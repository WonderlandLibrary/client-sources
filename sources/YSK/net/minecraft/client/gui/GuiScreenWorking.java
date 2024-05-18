package net.minecraft.client.gui;

import net.minecraft.util.*;

public class GuiScreenWorking extends GuiScreen implements IProgressUpdate
{
    private int progress;
    private String field_146591_a;
    private boolean doneWorking;
    private static final String[] I;
    private String field_146589_f;
    
    @Override
    public void resetProgressAndMessage(final String field_146591_a) {
        this.field_146591_a = field_146591_a;
        this.displayLoadingString(GuiScreenWorking.I["  ".length()]);
    }
    
    private static void I() {
        (I = new String[0x32 ^ 0x37])["".length()] = I("", "TjKFU");
        GuiScreenWorking.I[" ".length()] = I("", "WEZUJ");
        GuiScreenWorking.I["  ".length()] = I("\u001e<\u001c8!'4@}f", "ISnSH");
        GuiScreenWorking.I["   ".length()] = I("W", "wALnB");
        GuiScreenWorking.I[0x65 ^ 0x61] = I("u", "PVCna");
    }
    
    @Override
    public void displaySavingString(final String s) {
        this.resetProgressAndMessage(s);
    }
    
    @Override
    public void setLoadingProgress(final int progress) {
        this.progress = progress;
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.doneWorking) {
            if (!this.mc.func_181540_al()) {
                this.mc.displayGuiScreen(null);
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
        }
        else {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, this.field_146591_a, this.width / "  ".length(), 0x0 ^ 0x46, 14569664 + 2692933 - 1489855 + 1004473);
            this.drawCenteredString(this.fontRendererObj, String.valueOf(this.field_146589_f) + GuiScreenWorking.I["   ".length()] + this.progress + GuiScreenWorking.I[0x35 ^ 0x31], this.width / "  ".length(), 0xDD ^ 0x87, 4952101 + 3270089 + 5312387 + 3242638);
            super.drawScreen(n, n2, n3);
        }
    }
    
    @Override
    public void displayLoadingString(final String field_146589_f) {
        this.field_146589_f = field_146589_f;
        this.setLoadingProgress("".length());
    }
    
    static {
        I();
    }
    
    public GuiScreenWorking() {
        this.field_146591_a = GuiScreenWorking.I["".length()];
        this.field_146589_f = GuiScreenWorking.I[" ".length()];
    }
    
    @Override
    public void setDoneWorking() {
        this.doneWorking = (" ".length() != 0);
    }
}
