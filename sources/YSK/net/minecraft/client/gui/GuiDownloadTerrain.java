package net.minecraft.client.gui;

import net.minecraft.client.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.resources.*;
import java.io.*;

public class GuiDownloadTerrain extends GuiScreen
{
    private int progress;
    private static final String[] I;
    private NetHandlerPlayClient netHandlerPlayClient;
    
    @Override
    public boolean doesGuiPauseGame() {
        return "".length() != 0;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
    }
    
    public GuiDownloadTerrain(final NetHandlerPlayClient netHandlerPlayClient) {
        this.netHandlerPlayClient = netHandlerPlayClient;
    }
    
    static {
        I();
    }
    
    @Override
    public void updateScreen() {
        this.progress += " ".length();
        if (this.progress % (0xAF ^ 0xBB) == 0) {
            this.netHandlerPlayClient.addToSendQueue(new C00PacketKeepAlive());
        }
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
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(";\u0003.\u001c.&\u001a#\u0011\"$X&\u000708\u001a-\t#?\u0018%<\"$\u0004#\u0001)", "VvBhG");
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawBackground("".length());
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiDownloadTerrain.I["".length()], new Object["".length()]), this.width / "  ".length(), this.height / "  ".length() - (0x7D ^ 0x4F), 3064332 + 4500254 + 9006387 + 206242);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
}
