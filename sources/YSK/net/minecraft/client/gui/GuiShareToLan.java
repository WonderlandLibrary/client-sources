package net.minecraft.client.gui;

import net.minecraft.world.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.resources.*;

public class GuiShareToLan extends GuiScreen
{
    private static final String[] I;
    private String field_146599_h;
    private GuiButton field_146596_f;
    private boolean field_146600_i;
    private GuiButton field_146597_g;
    private final GuiScreen field_146598_a;
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == (0x1F ^ 0x79)) {
            this.mc.displayGuiScreen(this.field_146598_a);
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else if (guiButton.id == (0x1C ^ 0x74)) {
            if (this.field_146599_h.equals(GuiShareToLan.I[0x8 ^ 0x4])) {
                this.field_146599_h = GuiShareToLan.I[0x2E ^ 0x23];
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else if (this.field_146599_h.equals(GuiShareToLan.I[0x13 ^ 0x1D])) {
                this.field_146599_h = GuiShareToLan.I[0x81 ^ 0x8E];
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else if (this.field_146599_h.equals(GuiShareToLan.I[0x5 ^ 0x15])) {
                this.field_146599_h = GuiShareToLan.I[0xB ^ 0x1A];
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                this.field_146599_h = GuiShareToLan.I[0xA9 ^ 0xBB];
            }
            this.func_146595_g();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (guiButton.id == (0x28 ^ 0x4F)) {
            int field_146600_i;
            if (this.field_146600_i) {
                field_146600_i = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                field_146600_i = " ".length();
            }
            this.field_146600_i = (field_146600_i != 0);
            this.func_146595_g();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if (guiButton.id == (0xC8 ^ 0xAD)) {
            this.mc.displayGuiScreen(null);
            final String shareToLAN = this.mc.getIntegratedServer().shareToLAN(WorldSettings.GameType.getByName(this.field_146599_h), this.field_146600_i);
            ChatComponentStyle chatComponentStyle;
            if (shareToLAN != null) {
                final String s = GuiShareToLan.I[0x8C ^ 0x9F];
                final Object[] array = new Object[" ".length()];
                array["".length()] = shareToLAN;
                chatComponentStyle = new ChatComponentTranslation(s, array);
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else {
                chatComponentStyle = new ChatComponentText(GuiShareToLan.I[0x9C ^ 0x88]);
            }
            this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponentStyle);
        }
    }
    
    private void func_146595_g() {
        this.field_146597_g.displayString = String.valueOf(I18n.format(GuiShareToLan.I[0x32 ^ 0x37], new Object["".length()])) + GuiShareToLan.I[0x4 ^ 0x2] + I18n.format(GuiShareToLan.I[0x23 ^ 0x24] + this.field_146599_h, new Object["".length()]);
        this.field_146596_f.displayString = String.valueOf(I18n.format(GuiShareToLan.I[0x5D ^ 0x55], new Object["".length()])) + GuiShareToLan.I[0x30 ^ 0x39];
        if (this.field_146600_i) {
            this.field_146596_f.displayString = String.valueOf(this.field_146596_f.displayString) + I18n.format(GuiShareToLan.I[0x5F ^ 0x55], new Object["".length()]);
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else {
            this.field_146596_f.displayString = String.valueOf(this.field_146596_f.displayString) + I18n.format(GuiShareToLan.I[0x15 ^ 0x1E], new Object["".length()]);
        }
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0xD3 ^ 0xB6, this.width / "  ".length() - (139 + 6 + 7 + 3), this.height - (0x8A ^ 0x96), 18 + 146 - 73 + 59, 0xA5 ^ 0xB1, I18n.format(GuiShareToLan.I[" ".length()], new Object["".length()])));
        this.buttonList.add(new GuiButton(0xFB ^ 0x9D, this.width / "  ".length() + (0xAF ^ 0xAA), this.height - (0x15 ^ 0x9), 95 + 65 - 68 + 58, 0x52 ^ 0x46, I18n.format(GuiShareToLan.I["  ".length()], new Object["".length()])));
        this.buttonList.add(this.field_146597_g = new GuiButton(0x1F ^ 0x77, this.width / "  ".length() - (115 + 85 - 50 + 5), 0xD9 ^ 0xBD, 38 + 5 - 11 + 118, 0xA8 ^ 0xBC, I18n.format(GuiShareToLan.I["   ".length()], new Object["".length()])));
        this.buttonList.add(this.field_146596_f = new GuiButton(0xEA ^ 0x8D, this.width / "  ".length() + (0x31 ^ 0x34), 0x4B ^ 0x2F, 56 + 57 - 24 + 61, 0x5C ^ 0x48, I18n.format(GuiShareToLan.I[0x6B ^ 0x6F], new Object["".length()])));
        this.func_146595_g();
    }
    
    public GuiShareToLan(final GuiScreen field_146598_a) {
        this.field_146599_h = GuiShareToLan.I["".length()];
        this.field_146598_a = field_146598_a;
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
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiShareToLan.I[0x9A ^ 0x8F], new Object["".length()]), this.width / "  ".length(), 0x76 ^ 0x44, 14829377 + 5441974 - 15775749 + 12281613);
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiShareToLan.I[0x3F ^ 0x29], new Object["".length()]), this.width / "  ".length(), 0xD9 ^ 0x8B, 4880598 + 6002093 - 8649495 + 14544019);
        super.drawScreen(n, n2, n3);
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x2F ^ 0x38])["".length()] = I("=! \u0019\u000f85>", "NTRof");
        GuiShareToLan.I[" ".length()] = I("\u0019\u000e6:+\u0007\u0019=\u001b`\u0006\u001b9\u001b:", "uoXiN");
        GuiShareToLan.I["  ".length()] = I("\u0013'+c5\u0015<!(:", "tRBMV");
        GuiShareToLan.I["   ".length()] = I("0\u001c\u00183\u00167.\u001b$\u0019'W\u00137\u0018&4\u001b2\u0010", "CytVu");
        GuiShareToLan.I[0xB0 ^ 0xB4] = I("$\u00026&\u0007#051\b3I;/\b8\u0010\u0019,\t:\u00064'\u0017", "WgZCd");
        GuiShareToLan.I[0x7B ^ 0x7E] = I("7*\"&\f0\u0018!1\u0003 a)\"\u0002!\u0002!'\n", "DONCo");
        GuiShareToLan.I[0x76 ^ 0x70] = I("P", "paqES");
        GuiShareToLan.I[0x98 ^ 0x9F] = I("\n#\"4(\r\u0011!#'\u001dh)0&\u001c\u000b!5.W", "yFNQK");
        GuiShareToLan.I[0x7 ^ 0xF] = I("\u000b$%0\u0019\f\u0016&'\u0016\u001co(9\u0016\u00176\n:\u0017\u0015 '1\t", "xAIUz");
        GuiShareToLan.I[0x52 ^ 0x5B] = I("u", "UGvGj");
        GuiShareToLan.I[0x6F ^ 0x65] = I("91 $(82z\")", "VATMG");
        GuiShareToLan.I[0x5 ^ 0xE] = I("\u00154\u001d17\u00147G7>\u001c", "zDiXX");
        GuiShareToLan.I[0x8E ^ 0x82] = I("\u0001\u001b!\t6\u0013\u001f+\u0018", "rkDjB");
        GuiShareToLan.I[0x79 ^ 0x74] = I("\b!\b\r\u000e\u0002%\b", "kSmlz");
        GuiShareToLan.I[0xB7 ^ 0xB9] = I("\r\u0007!\n\u001c\u0007\u0003!", "nuDkh");
        GuiShareToLan.I[0x84 ^ 0x8B] = I(";\u0000<\f\u0016.\u00118\f", "ZdJix");
        GuiShareToLan.I[0x95 ^ 0x85] = I("\u0000\u0016<\u00174\u0015\u00078\u0017", "arJrZ");
        GuiShareToLan.I[0x58 ^ 0x49] = I("\u00180\u001c2\u000e\u001d$\u0002", "kEnDg");
        GuiShareToLan.I[0x6D ^ 0x7F] = I("8#\u000f1\u001d*'\u0005 ", "KSjRi");
        GuiShareToLan.I[0x5 ^ 0x16] = I("\u0010\u0015+\u000f,\u001d\u001e5L=\u0006\u0018*\u000b>\u001bT5\u0016,\u0001\u000e#\u0006", "szFbM");
        GuiShareToLan.I[0x3A ^ 0x2E] = I("!'\u001d\u0007.,,\u0003D?7*\u001c\u0003<*f\u0016\u000b&.-\u0014", "BHpjO");
        GuiShareToLan.I[0xA9 ^ 0xBC] = I("\"'%0,<0.\u0011g:/?\u000f,", "NFKcI");
        GuiShareToLan.I[0xBF ^ 0xA9] = I("+\u0005\u0007\u0005<5\u0012\f$w(\u0010\u00013+\u0017\b\b/<5\u0017", "GdiVY");
    }
}
