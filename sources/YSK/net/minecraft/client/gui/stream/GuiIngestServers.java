package net.minecraft.client.gui.stream;

import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import tv.twitch.broadcast.*;
import net.minecraft.client.stream.*;
import net.minecraft.client.*;

public class GuiIngestServers extends GuiScreen
{
    private ServerList field_152311_g;
    private final GuiScreen field_152309_a;
    private static final String[] I;
    private String field_152310_f;
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_152311_g.handleMouseInput();
    }
    
    @Override
    public void onGuiClosed() {
        if (this.mc.getTwitchStream().func_152908_z()) {
            this.mc.getTwitchStream().func_152932_y().func_153039_l();
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == " ".length()) {
                this.mc.displayGuiScreen(this.field_152309_a);
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else {
                this.mc.gameSettings.streamPreferredServer = GuiIngestServers.I["   ".length()];
                this.mc.gameSettings.saveOptions();
            }
        }
    }
    
    static {
        I();
    }
    
    @Override
    public void initGui() {
        this.field_152310_f = I18n.format(GuiIngestServers.I["".length()], new Object["".length()]);
        this.field_152311_g = new ServerList(this.mc);
        if (!this.mc.getTwitchStream().func_152908_z()) {
            this.mc.getTwitchStream().func_152909_x();
        }
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (91 + 142 - 141 + 63), this.height - (0x23 ^ 0x3B) - (0x8E ^ 0x88), 128 + 25 - 86 + 83, 0x1A ^ 0xE, I18n.format(GuiIngestServers.I[" ".length()], new Object["".length()])));
        this.buttonList.add(new GuiButton("  ".length(), this.width / "  ".length() + (0x6B ^ 0x6E), this.height - (0x8D ^ 0x95) - (0x13 ^ 0x15), 87 + 18 + 12 + 33, 0x62 ^ 0x76, I18n.format(GuiIngestServers.I["  ".length()], new Object["".length()])));
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
            if (3 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_152311_g.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.field_152310_f, this.width / "  ".length(), 0x8D ^ 0x99, 183989 + 15762568 - 10213678 + 11044336);
        super.drawScreen(n, n2, n3);
    }
    
    private static void I() {
        (I = new String[0x7 ^ 0x3])["".length()] = I(".<-'\u0017/?w=\f3)8#V(\">+\u000b5b-'\f-)", "ALYNx");
        GuiIngestServers.I[" ".length()] = I("\u0005?\u0007x#\r$\u000b", "bJnVG");
        GuiIngestServers.I["  ".length()] = I("\u00054\u0016*\u0006\u00047L0\u001d\u0018!\u0003.G\u0003*\u0005&\u001a\u001ej\u0010&\u001a\u000f0", "jDbCi");
        GuiIngestServers.I["   ".length()] = I("", "qjchQ");
    }
    
    public GuiIngestServers(final GuiScreen field_152309_a) {
        this.field_152309_a = field_152309_a;
    }
    
    static FontRenderer access$0(final GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }
    
    class ServerList extends GuiSlot
    {
        private static final String[] I;
        final GuiIngestServers this$0;
        
        @Override
        protected void drawSlot(final int n, int n2, final int n3, final int n4, final int n5, final int n6) {
            final IngestServer ingestServer = this.mc.getTwitchStream().func_152925_v()[n];
            String s = ingestServer.serverUrl.replaceAll(ServerList.I["".length()], ServerList.I[" ".length()]);
            String s2 = String.valueOf((int)ingestServer.bitrateKbps) + ServerList.I["  ".length()];
            String s3 = null;
            final IngestServerTester func_152932_y = this.mc.getTwitchStream().func_152932_y();
            if (func_152932_y != null) {
                if (ingestServer == func_152932_y.func_153040_c()) {
                    s = EnumChatFormatting.GREEN + s;
                    s2 = String.valueOf((int)(func_152932_y.func_153030_h() * 100.0f)) + ServerList.I["   ".length()];
                    "".length();
                    if (3 == 2) {
                        throw null;
                    }
                }
                else if (n < func_152932_y.func_153028_p()) {
                    if (ingestServer.bitrateKbps == 0.0f) {
                        s2 = EnumChatFormatting.RED + ServerList.I[0x8D ^ 0x89];
                        "".length();
                        if (3 < 3) {
                            throw null;
                        }
                    }
                }
                else {
                    s2 = EnumChatFormatting.OBFUSCATED + ServerList.I[0xB ^ 0xE] + EnumChatFormatting.RESET + ServerList.I[0x16 ^ 0x10];
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
            }
            else if (ingestServer.bitrateKbps == 0.0f) {
                s2 = EnumChatFormatting.RED + ServerList.I[0x1C ^ 0x1B];
            }
            n2 -= 15;
            if (this.isSelected(n)) {
                s3 = EnumChatFormatting.BLUE + ServerList.I[0x9B ^ 0x93];
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else if (ingestServer.defaultServer) {
                s3 = EnumChatFormatting.GREEN + ServerList.I[0xAA ^ 0xA3];
            }
            this.this$0.drawString(GuiIngestServers.access$0(this.this$0), ingestServer.serverName, n2 + "  ".length(), n3 + (0x2F ^ 0x2A), 14292844 + 12276459 - 11259680 + 1467592);
            this.this$0.drawString(GuiIngestServers.access$0(this.this$0), s, n2 + "  ".length(), n3 + GuiIngestServers.access$0(this.this$0).FONT_HEIGHT + (0x5E ^ 0x5B) + "   ".length(), 1098691 + 256441 - 771474 + 2574406);
            this.this$0.drawString(GuiIngestServers.access$0(this.this$0), s2, this.getScrollBarX() - (0x2E ^ 0x2B) - GuiIngestServers.access$0(this.this$0).getStringWidth(s2), n3 + (0x99 ^ 0x9C), 1521391 + 7560915 - 3580878 + 2920076);
            if (s3 != null) {
                this.this$0.drawString(GuiIngestServers.access$0(this.this$0), s3, this.getScrollBarX() - (0x24 ^ 0x21) - GuiIngestServers.access$0(this.this$0).getStringWidth(s3), n3 + (0x6 ^ 0x3) + "   ".length() + GuiIngestServers.access$0(this.this$0).FONT_HEIGHT, 1398009 + 7706888 - 5786346 + 5102953);
            }
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
            this.mc.gameSettings.streamPreferredServer = this.mc.getTwitchStream().func_152925_v()[n].serverUrl;
            this.mc.gameSettings.saveOptions();
        }
        
        static {
            I();
        }
        
        private static void I() {
            (I = new String[0x82 ^ 0x88])["".length()] = I("$\b&\u001a'\u001d\u001281>\u001d\n\t\u0013", "xsUnU");
            ServerList.I[" ".length()] = I("", "NYPLc");
            ServerList.I["  ".length()] = I("b\f\u0006\u0003'", "BgdsT");
            ServerList.I["   ".length()] = I("I", "lRuZb");
            ServerList.I[0x1B ^ 0x1F] = I("+,59R", "oCBWs");
            ServerList.I[0x1E ^ 0x1B] = I("hdYb", "YVjVQ");
            ServerList.I[0xA3 ^ 0xA5] = I("o<\u000b\t\u001c", "OWiyo");
            ServerList.I[0x2 ^ 0x5] = I("\u0016<8\u001bR", "RSOus");
            ServerList.I[0x25 ^ 0x2D] = I("z\u0002'\u0001\u00107 '\u0001\u0012{", "RRUdv");
            ServerList.I[0x75 ^ 0x7C] = I("r*\u000e\u0014\u0014/\u0002\u001f[", "Znkru");
        }
        
        @Override
        protected void drawBackground() {
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
                if (2 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return this.mc.getTwitchStream().func_152925_v()[n].serverUrl.equals(this.mc.gameSettings.streamPreferredServer);
        }
        
        @Override
        protected int getSize() {
            return this.mc.getTwitchStream().func_152925_v().length;
        }
        
        public ServerList(final GuiIngestServers this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, this$0.width, this$0.height, 0x4B ^ 0x6B, this$0.height - (0xE7 ^ 0xC4), (int)(minecraft.fontRendererObj.FONT_HEIGHT * 3.5));
            this.setShowSelectionBox("".length() != 0);
        }
        
        @Override
        protected int getScrollBarX() {
            return super.getScrollBarX() + (0xAE ^ 0xA1);
        }
    }
}
