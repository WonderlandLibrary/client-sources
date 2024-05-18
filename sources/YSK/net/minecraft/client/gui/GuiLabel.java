package net.minecraft.client.gui;

import java.util.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class GuiLabel extends Gui
{
    public boolean visible;
    protected int field_146161_f;
    private boolean labelBgEnabled;
    private boolean centered;
    private FontRenderer fontRenderer;
    protected int field_146167_a;
    private int field_146168_n;
    private int field_146166_p;
    public int field_175204_i;
    private List<String> field_146173_k;
    public int field_146174_h;
    public int field_146162_g;
    private int field_146163_s;
    private int field_146169_o;
    private int field_146165_q;
    
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuiLabel setCentered() {
        this.centered = (" ".length() != 0);
        return this;
    }
    
    public void func_175202_a(final String s) {
        this.field_146173_k.add(I18n.format(s, new Object["".length()]));
    }
    
    public GuiLabel(final FontRenderer fontRenderer, final int field_175204_i, final int field_146162_g, final int field_146174_h, final int field_146167_a, final int field_146161_f, final int field_146168_n) {
        this.field_146167_a = 35 + 179 - 195 + 181;
        this.field_146161_f = (0x45 ^ 0x51);
        this.visible = (" ".length() != 0);
        this.fontRenderer = fontRenderer;
        this.field_175204_i = field_175204_i;
        this.field_146162_g = field_146162_g;
        this.field_146174_h = field_146174_h;
        this.field_146167_a = field_146167_a;
        this.field_146161_f = field_146161_f;
        this.field_146173_k = (List<String>)Lists.newArrayList();
        this.centered = ("".length() != 0);
        this.labelBgEnabled = ("".length() != 0);
        this.field_146168_n = field_146168_n;
        this.field_146169_o = -" ".length();
        this.field_146166_p = -" ".length();
        this.field_146165_q = -" ".length();
        this.field_146163_s = "".length();
    }
    
    public void drawLabel(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(763 + 57 - 587 + 537, 46 + 114 - 103 + 714, " ".length(), "".length());
            this.drawLabelBackground(minecraft, n, n2);
            final int n3 = this.field_146174_h + this.field_146161_f / "  ".length() + this.field_146163_s / "  ".length() - this.field_146173_k.size() * (0x46 ^ 0x4C) / "  ".length();
            int i = "".length();
            "".length();
            if (3 == 1) {
                throw null;
            }
            while (i < this.field_146173_k.size()) {
                if (this.centered) {
                    this.drawCenteredString(this.fontRenderer, this.field_146173_k.get(i), this.field_146162_g + this.field_146167_a / "  ".length(), n3 + i * (0x89 ^ 0x83), this.field_146168_n);
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else {
                    this.drawString(this.fontRenderer, this.field_146173_k.get(i), this.field_146162_g, n3 + i * (0x92 ^ 0x98), this.field_146168_n);
                }
                ++i;
            }
        }
    }
    
    protected void drawLabelBackground(final Minecraft minecraft, final int n, final int n2) {
        if (this.labelBgEnabled) {
            final int n3 = this.field_146167_a + this.field_146163_s * "  ".length();
            final int n4 = this.field_146161_f + this.field_146163_s * "  ".length();
            final int n5 = this.field_146162_g - this.field_146163_s;
            final int n6 = this.field_146174_h - this.field_146163_s;
            Gui.drawRect(n5, n6, n5 + n3, n6 + n4, this.field_146169_o);
            this.drawHorizontalLine(n5, n5 + n3, n6, this.field_146166_p);
            this.drawHorizontalLine(n5, n5 + n3, n6 + n4, this.field_146165_q);
            this.drawVerticalLine(n5, n6, n6 + n4, this.field_146166_p);
            this.drawVerticalLine(n5 + n3, n6, n6 + n4, this.field_146165_q);
        }
    }
}
