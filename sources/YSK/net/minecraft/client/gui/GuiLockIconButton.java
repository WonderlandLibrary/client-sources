package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class GuiLockIconButton extends GuiButton
{
    private boolean field_175231_o;
    private static final String[] I;
    
    public void func_175229_b(final boolean field_175231_o) {
        this.field_175231_o = field_175231_o;
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
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            final int n4 = n3;
            Icon icon;
            if (this.field_175231_o) {
                if (!this.enabled) {
                    icon = Icon.LOCKED_DISABLED;
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                }
                else if (n4 != 0) {
                    icon = Icon.LOCKED_HOVER;
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                }
                else {
                    icon = Icon.LOCKED;
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
            }
            else if (!this.enabled) {
                icon = Icon.UNLOCKED_DISABLED;
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            else if (n4 != 0) {
                icon = Icon.UNLOCKED_HOVER;
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                icon = Icon.UNLOCKED;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, icon.func_178910_a(), icon.func_178912_b(), this.width, this.height);
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("", "vAYcK");
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
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public GuiLockIconButton(final int n, final int n2, final int n3) {
        super(n, n2, n3, 0x41 ^ 0x55, 0x1D ^ 0x9, GuiLockIconButton.I["".length()]);
        this.field_175231_o = ("".length() != 0);
    }
    
    public boolean func_175230_c() {
        return this.field_175231_o;
    }
    
    enum Icon
    {
        LOCKED_DISABLED(Icon.I["  ".length()], "  ".length(), "".length(), 38 + 125 - 66 + 89);
        
        private static final Icon[] ENUM$VALUES;
        
        LOCKED(Icon.I["".length()], "".length(), "".length(), 125 + 61 - 126 + 86);
        
        private static final String[] I;
        private final int field_178914_g;
        
        UNLOCKED(Icon.I["   ".length()], "   ".length(), 0x34 ^ 0x20, 100 + 51 - 19 + 14), 
        UNLOCKED_HOVER(Icon.I[0x1 ^ 0x5], 0xA3 ^ 0xA7, 0xA0 ^ 0xB4, 127 + 101 - 219 + 157);
        
        private final int field_178920_h;
        
        UNLOCKED_DISABLED(Icon.I[0x51 ^ 0x54], 0xAE ^ 0xAB, 0x34 ^ 0x20, 31 + 161 - 71 + 65), 
        LOCKED_HOVER(Icon.I[" ".length()], " ".length(), "".length(), 53 + 72 + 16 + 25);
        
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
                if (-1 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public int func_178910_a() {
            return this.field_178914_g;
        }
        
        private Icon(final String s, final int n, final int field_178914_g, final int field_178920_h) {
            this.field_178914_g = field_178914_g;
            this.field_178920_h = field_178920_h;
        }
        
        private static void I() {
            (I = new String[0x39 ^ 0x3F])["".length()] = I("+\u0002\r,\u0014#", "gMNgQ");
            Icon.I[" ".length()] = I("\b\u0019\u000f\"+\u0000\t\u0004&8\u0001\u0004", "DVLin");
            Icon.I["  ".length()] = I("\u0014(;2\u0001\u001c8<0\u0017\u0019%4<\u0000", "XgxyD");
            Icon.I["   ".length()] = I("\u0001\u0016\u001f\u00062\u001f\u001d\u0017", "TXSIq");
            Icon.I[0x7A ^ 0x7E] = I("\u0019\u001c\u0003 \u0017\u0007\u0017\u000b0\u001c\u0003\u0004\n=", "LROoT");
            Icon.I[0x6 ^ 0x3] = I("8/\u0003,5&$\u000b<2$2\u000e!:(%", "maOcv");
        }
        
        static {
            I();
            final Icon[] enum$VALUES = new Icon[0x9 ^ 0xF];
            enum$VALUES["".length()] = Icon.LOCKED;
            enum$VALUES[" ".length()] = Icon.LOCKED_HOVER;
            enum$VALUES["  ".length()] = Icon.LOCKED_DISABLED;
            enum$VALUES["   ".length()] = Icon.UNLOCKED;
            enum$VALUES[0x5F ^ 0x5B] = Icon.UNLOCKED_HOVER;
            enum$VALUES[0x10 ^ 0x15] = Icon.UNLOCKED_DISABLED;
            ENUM$VALUES = enum$VALUES;
        }
        
        public int func_178912_b() {
            return this.field_178920_h;
        }
    }
}
