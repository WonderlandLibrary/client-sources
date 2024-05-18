package net.minecraft.client.gui;

import java.io.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;

public class GuiYesNo extends GuiScreen
{
    private String messageLine2;
    private int ticksUntilEnable;
    private final List<String> field_175298_s;
    protected GuiYesNoCallback parentScreen;
    protected String cancelButtonText;
    protected String messageLine1;
    protected String confirmButtonText;
    protected int parentButtonClickedId;
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiOptionButton("".length(), this.width / "  ".length() - (117 + 89 - 151 + 100), this.height / (0xB8 ^ 0xBE) + (0x64 ^ 0x4), this.confirmButtonText));
        this.buttonList.add(new GuiOptionButton(" ".length(), this.width / "  ".length() - (19 + 62 - 69 + 143) + (15 + 143 - 133 + 135), this.height / (0x85 ^ 0x83) + (0xD2 ^ 0xB2), this.cancelButtonText));
        this.field_175298_s.clear();
        this.field_175298_s.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, this.width - (0x80 ^ 0xB2)));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        final GuiYesNoCallback parentScreen = this.parentScreen;
        int n;
        if (guiButton.id == 0) {
            n = " ".length();
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        parentScreen.confirmClicked(n != 0, this.parentButtonClickedId);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\n3\u001eX\u0017\b5", "mFwvn");
        GuiYesNo.I[" ".length()] = I("-\u001f\u0019y9%", "JjpWW");
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final int ticksUntilEnable = this.ticksUntilEnable - " ".length();
        this.ticksUntilEnable = ticksUntilEnable;
        if (ticksUntilEnable == 0) {
            final Iterator<GuiButton> iterator = this.buttonList.iterator();
            "".length();
            if (0 == 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                iterator.next().enabled = (" ".length() != 0);
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.messageLine1, this.width / "  ".length(), 0x4C ^ 0xA, 13939987 + 7664462 - 7021302 + 2194068);
        int n4 = 0x24 ^ 0x7E;
        final Iterator<String> iterator = this.field_175298_s.iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.drawCenteredString(this.fontRendererObj, iterator.next(), this.width / "  ".length(), n4, 9383563 + 15413987 - 12432916 + 4412581);
            n4 += this.fontRendererObj.FONT_HEIGHT;
        }
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuiYesNo(final GuiYesNoCallback parentScreen, final String messageLine1, final String messageLine2, final int parentButtonClickedId) {
        this.field_175298_s = (List<String>)Lists.newArrayList();
        this.parentScreen = parentScreen;
        this.messageLine1 = messageLine1;
        this.messageLine2 = messageLine2;
        this.parentButtonClickedId = parentButtonClickedId;
        this.confirmButtonText = I18n.format(GuiYesNo.I["".length()], new Object["".length()]);
        this.cancelButtonText = I18n.format(GuiYesNo.I[" ".length()], new Object["".length()]);
    }
    
    public void setButtonDelay(final int ticksUntilEnable) {
        this.ticksUntilEnable = ticksUntilEnable;
        final Iterator<GuiButton> iterator = this.buttonList.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().enabled = ("".length() != 0);
        }
    }
    
    public GuiYesNo(final GuiYesNoCallback parentScreen, final String messageLine1, final String messageLine2, final String confirmButtonText, final String cancelButtonText, final int parentButtonClickedId) {
        this.field_175298_s = (List<String>)Lists.newArrayList();
        this.parentScreen = parentScreen;
        this.messageLine1 = messageLine1;
        this.messageLine2 = messageLine2;
        this.confirmButtonText = confirmButtonText;
        this.cancelButtonText = cancelButtonText;
        this.parentButtonClickedId = parentButtonClickedId;
    }
}
