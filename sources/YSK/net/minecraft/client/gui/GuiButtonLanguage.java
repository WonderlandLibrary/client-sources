package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class GuiButtonLanguage extends GuiButton
{
    private static final String[] I;
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("", "qjOUm");
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
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuiButtonLanguage(final int n, final int n2, final int n3) {
        super(n, n2, n3, 0x92 ^ 0x86, 0x31 ^ 0x25, GuiButtonLanguage.I["".length()]);
    }
    
    @Override
    public void drawButton(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            minecraft.getTextureManager().bindTexture(GuiButton.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            int n3;
            if (n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height) {
                n3 = " ".length();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            final int n4 = n3;
            int n5 = 0x36 ^ 0x5C;
            if (n4 != 0) {
                n5 += this.height;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, "".length(), n5, this.width, this.height);
        }
    }
}
