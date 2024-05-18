package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.resources.*;

public class ServerListEntryLanScan implements GuiListExtended.IGuiListEntry
{
    private final Minecraft mc;
    private static final String[] I;
    
    @Override
    public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return "".length() != 0;
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
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    @Override
    public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        final int n8 = n3 + n5 / "  ".length() - this.mc.fontRendererObj.FONT_HEIGHT / "  ".length();
        this.mc.fontRendererObj.drawString(I18n.format(ServerListEntryLanScan.I["".length()], new Object["".length()]), this.mc.currentScreen.width / "  ".length() - this.mc.fontRendererObj.getStringWidth(I18n.format(ServerListEntryLanScan.I[" ".length()], new Object["".length()])) / "  ".length(), n8, 4468829 + 13905988 - 16344698 + 14747096);
        String s = null;
        switch ((int)(Minecraft.getSystemTime() / 300L % 4L)) {
            default: {
                s = ServerListEntryLanScan.I["  ".length()];
                "".length();
                if (4 <= -1) {
                    throw null;
                }
                break;
            }
            case 1:
            case 3: {
                s = ServerListEntryLanScan.I["   ".length()];
                "".length();
                if (2 != 2) {
                    throw null;
                }
                break;
            }
            case 2: {
                s = ServerListEntryLanScan.I[0x9 ^ 0xD];
                break;
            }
        }
        this.mc.fontRendererObj.drawString(s, this.mc.currentScreen.width / "  ".length() - this.mc.fontRendererObj.getStringWidth(s) / "  ".length(), n8 + this.mc.fontRendererObj.FONT_HEIGHT, 7315613 + 3565918 - 5790710 + 3330683);
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x24 ^ 0x21])["".length()] = I("9\u0019\u0003\n<'\u000e\b+w&\u001b\f77<\u0016\n", "UxmYY");
        ServerListEntryLanScan.I[" ".length()] = I("/\u0019\u0003!-1\u000e\b\u0000f0\u001b\f\u001c&*\u0016\n", "CxmrH");
        ServerListEntryLanScan.I["  ".length()] = I("$z>k<", "kZQKS");
        ServerListEntryLanScan.I["   ".length()] = I("6h\u001dn-", "YHRNB");
        ServerListEntryLanScan.I[0x97 ^ 0x93] = I("#U=h5", "LuRHz");
    }
    
    @Override
    public void setSelected(final int n, final int n2, final int n3) {
    }
    
    public ServerListEntryLanScan() {
        this.mc = Minecraft.getMinecraft();
    }
}
