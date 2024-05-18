package net.minecraft.client.gui;

import net.minecraft.entity.player.*;
import net.minecraft.client.resources.*;
import java.io.*;

public class GuiCustomizeSkin extends GuiScreen
{
    private static final String[] I;
    private final GuiScreen parentScreen;
    private String title;
    
    private String func_175358_a(final EnumPlayerModelParts enumPlayerModelParts) {
        String s;
        if (this.mc.gameSettings.getModelParts().contains(enumPlayerModelParts)) {
            s = I18n.format(GuiCustomizeSkin.I["  ".length()], new Object["".length()]);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            s = I18n.format(GuiCustomizeSkin.I["   ".length()], new Object["".length()]);
        }
        return String.valueOf(enumPlayerModelParts.func_179326_d().getFormattedText()) + GuiCustomizeSkin.I[0x4F ^ 0x4B] + s;
    }
    
    static {
        I();
    }
    
    public GuiCustomizeSkin(final GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }
    
    static String access$0(final GuiCustomizeSkin guiCustomizeSkin, final EnumPlayerModelParts enumPlayerModelParts) {
        return guiCustomizeSkin.func_175358_a(enumPlayerModelParts);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / "  ".length(), 0x90 ^ 0x84, 5858589 + 10436708 - 14644154 + 15126072);
        super.drawScreen(n, n2, n3);
    }
    
    private static void I() {
        (I = new String[0x81 ^ 0x84])["".length()] = I("\b\u0006#,\u001e\t\u0005y6\u001a\u000e\u0018\u00140\u0002\u0013\u0019:,\u0002\u0006\u0002>*\u001fI\u0002>1\u001d\u0002", "gvWEq");
        GuiCustomizeSkin.I[" ".length()] = I(" -$f\u0013(6(", "GXMHw");
        GuiCustomizeSkin.I["  ".length()] = I("5=\u001c\u0007<4>F\u0001=", "ZMhnS");
        GuiCustomizeSkin.I["   ".length()] = I("6($\u0002\u000b7+~\u0004\u0002?", "YXPkd");
        GuiCustomizeSkin.I[0xC3 ^ 0xC7] = I("Xq", "bQYEE");
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 0 + 8 + 21 + 171) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else if (guiButton instanceof ButtonPart) {
                final EnumPlayerModelParts access$1 = ButtonPart.access$1((ButtonPart)guiButton);
                this.mc.gameSettings.switchModelPartEnabled(access$1);
                guiButton.displayString = this.func_175358_a(access$1);
            }
        }
    }
    
    @Override
    public void initGui() {
        int length = "".length();
        this.title = I18n.format(GuiCustomizeSkin.I["".length()], new Object["".length()]);
        final EnumPlayerModelParts[] values;
        final int length2 = (values = EnumPlayerModelParts.values()).length;
        int i = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (i < length2) {
            final EnumPlayerModelParts enumPlayerModelParts = values[i];
            this.buttonList.add(new ButtonPart(enumPlayerModelParts.getPartId(), this.width / "  ".length() - (16 + 56 + 17 + 66) + length % "  ".length() * (66 + 108 - 30 + 16), this.height / (0x6D ^ 0x6B) + (0x7D ^ 0x65) * (length >> " ".length()), 71 + 74 - 132 + 137, 0xB4 ^ 0xA0, enumPlayerModelParts, null));
            ++length;
            ++i;
        }
        if (length % "  ".length() == " ".length()) {
            ++length;
        }
        this.buttonList.add(new GuiButton(53 + 151 - 58 + 54, this.width / "  ".length() - (0x64 ^ 0x0), this.height / (0x5E ^ 0x58) + (0xBD ^ 0xA5) * (length >> " ".length()), I18n.format(GuiCustomizeSkin.I[" ".length()], new Object["".length()])));
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
    
    class ButtonPart extends GuiButton
    {
        private final EnumPlayerModelParts playerModelParts;
        final GuiCustomizeSkin this$0;
        
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
                if (3 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        ButtonPart(final GuiCustomizeSkin guiCustomizeSkin, final int n, final int n2, final int n3, final int n4, final int n5, final EnumPlayerModelParts enumPlayerModelParts, final ButtonPart buttonPart) {
            this(guiCustomizeSkin, n, n2, n3, n4, n5, enumPlayerModelParts);
        }
        
        static EnumPlayerModelParts access$1(final ButtonPart buttonPart) {
            return buttonPart.playerModelParts;
        }
        
        private ButtonPart(final GuiCustomizeSkin this$0, final int n, final int n2, final int n3, final int n4, final int n5, final EnumPlayerModelParts playerModelParts) {
            this.this$0 = this$0;
            super(n, n2, n3, n4, n5, GuiCustomizeSkin.access$0(this$0, playerModelParts));
            this.playerModelParts = playerModelParts;
        }
    }
}
