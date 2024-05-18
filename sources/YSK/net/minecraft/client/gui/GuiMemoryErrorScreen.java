package net.minecraft.client.gui;

import java.io.*;
import net.minecraft.client.resources.*;

public class GuiMemoryErrorScreen extends GuiScreen
{
    private static final String[] I;
    
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiMainMenu());
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else if (guiButton.id == " ".length()) {
            this.mc.shutdown();
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, GuiMemoryErrorScreen.I["  ".length()], this.width / "  ".length(), this.height / (0xB4 ^ 0xB0) - (0x77 ^ 0x4B) + (0x5D ^ 0x49), 9016627 + 4850568 - 7032757 + 9942777);
        this.drawString(this.fontRendererObj, GuiMemoryErrorScreen.I["   ".length()], this.width / "  ".length() - (50 + 57 - 74 + 107), this.height / (0xA0 ^ 0xA4) - (0x5A ^ 0x66) + (0x4B ^ 0x77) + "".length(), 2357137 + 4877146 - 1275998 + 4568595);
        this.drawString(this.fontRendererObj, GuiMemoryErrorScreen.I[0xAD ^ 0xA9], this.width / "  ".length() - (118 + 62 - 108 + 68), this.height / (0xC ^ 0x8) - (0x56 ^ 0x6A) + (0x92 ^ 0xAE) + (0xA4 ^ 0xB6), 10453446 + 9295710 - 16264785 + 7042509);
        this.drawString(this.fontRendererObj, GuiMemoryErrorScreen.I[0x35 ^ 0x30], this.width / "  ".length() - (65 + 30 + 23 + 22), this.height / (0x5 ^ 0x1) - (0xF ^ 0x33) + (0x83 ^ 0xBF) + (0xA7 ^ 0xBC), 2057849 + 6498078 - 8525996 + 10496949);
        this.drawString(this.fontRendererObj, GuiMemoryErrorScreen.I[0xAB ^ 0xAD], this.width / "  ".length() - (7 + 28 + 32 + 73), this.height / (0x89 ^ 0x8D) - (0x72 ^ 0x4E) + (0x90 ^ 0xAC) + (0x43 ^ 0x67), 8589456 + 6224550 - 13303759 + 9016633);
        this.drawString(this.fontRendererObj, GuiMemoryErrorScreen.I[0x1F ^ 0x18], this.width / "  ".length() - (1 + 110 - 35 + 64), this.height / (0xC ^ 0x8) - (0x72 ^ 0x4E) + (0xA1 ^ 0x9D) + (0x24 ^ 0x12), 7000109 + 1128219 + 1101726 + 1296826);
        this.drawString(this.fontRendererObj, GuiMemoryErrorScreen.I[0x99 ^ 0x91], this.width / "  ".length() - (78 + 126 - 157 + 93), this.height / (0x6F ^ 0x6B) - (0x44 ^ 0x78) + (0x95 ^ 0xA9) + (0xFC ^ 0xC3), 6931549 + 5989416 - 7365360 + 4971275);
        this.drawString(this.fontRendererObj, GuiMemoryErrorScreen.I[0x7C ^ 0x75], this.width / "  ".length() - (58 + 103 - 35 + 14), this.height / (0x72 ^ 0x76) - (0xAC ^ 0x90) + (0xA2 ^ 0x9E) + (0x8B ^ 0xC3), 6380095 + 8103380 - 8853144 + 4896549);
        this.drawString(this.fontRendererObj, GuiMemoryErrorScreen.I[0x27 ^ 0x2D], this.width / "  ".length() - (113 + 1 - 110 + 136), this.height / (0x57 ^ 0x53) - (0x92 ^ 0xAE) + (0x87 ^ 0xBB) + (0x97 ^ 0xC6), 10273872 + 932925 - 6112103 + 5432186);
        super.drawScreen(n, n2, n3);
    }
    
    private static void I() {
        (I = new String[0x49 ^ 0x42])["".length()] = I("3\f&X>;-&\u0002&1", "TyOvJ");
        GuiMemoryErrorScreen.I[" ".length()] = I("\u000b)\u0019\u0014K\u00179\u001e\u0015", "fLwae");
        GuiMemoryErrorScreen.I["  ".length()] = I("\u0016\u0010.U>?E7\u0010<6\u0017#T", "YeZuQ");
        GuiMemoryErrorScreen.I["   ".length()] = I("\u001c\u0013\u0019\n.#\u001b\u0011\u001bm9\u001b\u0004O?$\u0014W\u00008%Z\u0018\tm<\u001f\u001a\u0000?(T", "QzwoM");
        GuiMemoryErrorScreen.I[0x58 ^ 0x5C] = I("\u001d/\n d*(\u0016? i%\u0006s'(2\u00106 i%\u001as%i%\u00164d )C',,g\u00042),g\f!d+>C',,", "IGcSD");
        GuiMemoryErrorScreen.I[0xB5 ^ 0xB0] = I("+\u0010\u000f\u0002m7\u0018\u000b\u00178\u0000\u001dY.,\u0002\u0019\u0010\r(A\u001f\u0016\u0017m\u0003\u0014\u0010\r*A\u0010\u0015\u000f\"\u0002\u0010\r\u0006)A\u0014\u0017\f8\u0006\u0019", "aqycM");
        GuiMemoryErrorScreen.I[0xBC ^ 0xBA] = I("%\u0015\u0001%\u00021^", "HplJp");
        GuiMemoryErrorScreen.I[0x68 ^ 0x6F] = I("\u00196m\u00136(/(\r0m5(\u0015!!y.\f6?,=\u0017-\"7aC0%<m\u00001?+(\r0m>,\u000e!m1,\u0010d<,$\u0017j", "MYMcD");
        GuiMemoryErrorScreen.I[0x5 ^ 0xD] = I(";!F\u0012!L0\u0013\r!\bd\u0015\u000bd\n6\u0004\u0001d\u00194A\u0001*\u00031\u0006\fd\u0001!\f\u000b6\u0015d\u0015\u000bd\u0000!\u0015D=\u00031A\u0003+L&\u0000\u0007/L0\u000e", "lDadD");
        GuiMemoryErrorScreen.I[0xA ^ 0x3] = I("\u0002\u001b/c'\u0017\u001a$c'\u0013\u001d?c+\u0018\u0017j!+\u0015\u0018j7%V\u0003&\"3\u001f\u001d-oj\u0014\u0006>c>\u001e\u001a9c'\u0017\nj-%\u0002S\"\"<\u0013S=,8\u001d\u0016.m", "vsJCJ");
        GuiMemoryErrorScreen.I[0x3D ^ 0x37] = I("\u001b\u001d?/\u0019.Q(+\u0019?\u0010(:J?\u0019?n\r*\u001c?n\u0003-Q#!\u001fk\u0002?+J?\u00193=J&\u0014)=\u000b,\u0014z/\r*\u00184`", "KqZNj");
    }
    
    static {
        I();
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiOptionButton("".length(), this.width / "  ".length() - (88 + 21 + 18 + 28), this.height / (0x53 ^ 0x57) + (0xD8 ^ 0xA0) + (0xCA ^ 0xC6), I18n.format(GuiMemoryErrorScreen.I["".length()], new Object["".length()])));
        this.buttonList.add(new GuiOptionButton(" ".length(), this.width / "  ".length() - (68 + 38 + 49 + 0) + (4 + 119 + 12 + 25), this.height / (0x6D ^ 0x69) + (0x49 ^ 0x31) + (0xB8 ^ 0xB4), I18n.format(GuiMemoryErrorScreen.I[" ".length()], new Object["".length()])));
    }
}
