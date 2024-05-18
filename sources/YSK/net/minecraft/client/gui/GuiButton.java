package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.audio.*;

public class GuiButton extends Gui
{
    public boolean visible;
    private static final String[] I;
    protected int width;
    protected int height;
    public boolean enabled;
    public int yPosition;
    protected boolean hovered;
    protected static final ResourceLocation buttonTextures;
    public int xPosition;
    public int id;
    public String displayString;
    
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (this.enabled && this.visible && n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getButtonWidth() {
        return this.width;
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean isMouseOver() {
        return this.hovered;
    }
    
    public void drawButton(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            final FontRenderer fontRendererObj = minecraft.fontRendererObj;
            minecraft.getTextureManager().bindTexture(GuiButton.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            int hovered;
            if (n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height) {
                hovered = " ".length();
                "".length();
                if (0 < -1) {
                    throw null;
                }
            }
            else {
                hovered = "".length();
            }
            this.hovered = (hovered != 0);
            final int hoverState = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(126 + 265 + 263 + 116, 380 + 286 - 138 + 243, " ".length(), "".length());
            GlStateManager.blendFunc(23 + 697 - 464 + 514, 619 + 613 - 737 + 276);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, "".length(), (0x6D ^ 0x43) + hoverState * (0x21 ^ 0x35), this.width / "  ".length(), this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / "  ".length(), this.yPosition, 141 + 154 - 195 + 100 - this.width / "  ".length(), (0x20 ^ 0xE) + hoverState * (0x1E ^ 0xA), this.width / "  ".length(), this.height);
            this.mouseDragged(minecraft, n, n2);
            int n3 = 6414336 + 8967779 - 2602141 + 1957658;
            if (!this.enabled) {
                n3 = 805488 + 8197248 - 993863 + 2518007;
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            else if (this.hovered) {
                n3 = 5250541 + 15732259 - 5212338 + 1006658;
            }
            this.drawCenteredString(fontRendererObj, this.displayString, this.xPosition + this.width / "  ".length(), this.yPosition + (this.height - (0x4D ^ 0x45)) / "  ".length(), n3);
        }
    }
    
    static {
        I();
        buttonTextures = new ResourceLocation(GuiButton.I["".length()]);
    }
    
    protected void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u00170\u0016\u0000\u001d\u00110\u001d[\u000f\u0016<A\u0003\u0001\u00072\u000b\u0000\u001bM%\u0000\u0013", "cUnth");
        GuiButton.I[" ".length()] = I("0\u0016\u001c|\r\"\u0017\u0001=\u0001y\u0013\u00077\u001c$", "WcuRo");
    }
    
    public GuiButton(final int n, final int n2, final int n3, final String s) {
        this(n, n2, n3, 192 + 161 - 262 + 109, 0x60 ^ 0x74, s);
    }
    
    public GuiButton(final int id, final int xPosition, final int yPosition, final int width, final int height, final String displayString) {
        this.width = 197 + 23 - 88 + 68;
        this.height = (0x79 ^ 0x6D);
        this.enabled = (" ".length() != 0);
        this.visible = (" ".length() != 0);
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.displayString = displayString;
    }
    
    protected int getHoverState(final boolean b) {
        int n = " ".length();
        if (!this.enabled) {
            n = "".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else if (b) {
            n = "  ".length();
        }
        return n;
    }
    
    public void playPressSound(final SoundHandler soundHandler) {
        soundHandler.playSound(PositionedSoundRecord.create(new ResourceLocation(GuiButton.I[" ".length()]), 1.0f));
    }
    
    public void mouseReleased(final int n, final int n2) {
    }
    
    public void drawButtonForegroundLayer(final int n, final int n2) {
    }
}
