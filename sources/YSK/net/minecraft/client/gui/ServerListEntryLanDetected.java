package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.client.resources.*;

public class ServerListEntryLanDetected implements GuiListExtended.IGuiListEntry
{
    private final GuiMultiplayer field_148292_c;
    protected final Minecraft mc;
    protected final LanServerDetector.LanServer field_148291_b;
    private static final String[] I;
    private long field_148290_d;
    
    static {
        I();
    }
    
    public LanServerDetector.LanServer getLanServer() {
        return this.field_148291_b;
    }
    
    @Override
    public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.field_148292_c.selectServer(n);
        if (Minecraft.getSystemTime() - this.field_148290_d < 250L) {
            this.field_148292_c.connectToSelected();
        }
        this.field_148290_d = Minecraft.getSystemTime();
        return "".length() != 0;
    }
    
    protected ServerListEntryLanDetected(final GuiMultiplayer field_148292_c, final LanServerDetector.LanServer field_148291_b) {
        this.field_148290_d = 0L;
        this.field_148292_c = field_148292_c;
        this.field_148291_b = field_148291_b;
        this.mc = Minecraft.getMinecraft();
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    @Override
    public void setSelected(final int n, final int n2, final int n3) {
    }
    
    @Override
    public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        this.mc.fontRendererObj.drawString(I18n.format(ServerListEntryLanDetected.I["".length()], new Object["".length()]), n2 + (0x92 ^ 0xB2) + "   ".length(), n3 + " ".length(), 6628159 + 1100297 + 5854705 + 3194054);
        this.mc.fontRendererObj.drawString(this.field_148291_b.getServerMotd(), n2 + (0xA3 ^ 0x83) + "   ".length(), n3 + (0xAF ^ 0xA3), 3781482 + 6914890 - 9335369 + 7060501);
        if (this.mc.gameSettings.hideServerAddress) {
            this.mc.fontRendererObj.drawString(I18n.format(ServerListEntryLanDetected.I[" ".length()], new Object["".length()]), n2 + (0x4 ^ 0x24) + "   ".length(), n3 + (0xB5 ^ 0xB9) + (0x3B ^ 0x30), 1920458 + 229801 - 2140817 + 3148622);
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            this.mc.fontRendererObj.drawString(this.field_148291_b.getServerIpPort(), n2 + (0x27 ^ 0x7) + "   ".length(), n3 + (0xB8 ^ 0xB4) + (0x65 ^ 0x6E), 391652 + 2593773 - 1093126 + 1265765);
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(";\f7\u0018\"%\u001b<9i#\u0004-'\"", "WmYKG");
        ServerListEntryLanDetected.I[" ".length()] = I("0\u0011\u00015&7'\b\"3&\u0006C8,'\u0010\b>\u0004'\u0010\u001f560", "CtmPE");
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
