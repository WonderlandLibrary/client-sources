package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.network.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import java.io.*;
import net.minecraft.util.*;

public class GuiEditSign extends GuiScreen
{
    private int editLine;
    private GuiButton doneBtn;
    private int updateCounter;
    private static final String[] I;
    private TileEntitySign tileSign;
    
    public GuiEditSign(final TileEntitySign tileSign) {
        this.tileSign = tileSign;
    }
    
    @Override
    public void updateScreen() {
        this.updateCounter += " ".length();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)("".length() != 0));
        final NetHandlerPlayClient netHandler = this.mc.getNetHandler();
        if (netHandler != null) {
            netHandler.addToSendQueue(new C12PacketUpdateSign(this.tileSign.getPos(), this.tileSign.signText));
        }
        this.tileSign.setEditable(" ".length() != 0);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiEditSign.I[" ".length()], new Object["".length()]), this.width / "  ".length(), 0x72 ^ 0x5A, 373352 + 15938512 - 2087196 + 2552547);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.width / "  ".length(), 0.0f, 50.0f);
        final float n4 = 93.75f;
        GlStateManager.scale(-n4, -n4, -n4);
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        if (this.tileSign.getBlockType() == Blocks.standing_sign) {
            GlStateManager.rotate(this.tileSign.getBlockMetadata() * (298 + 176 - 208 + 94) / 16.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -1.0625f, 0.0f);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            final int blockMetadata = this.tileSign.getBlockMetadata();
            float n5 = 0.0f;
            if (blockMetadata == "  ".length()) {
                n5 = 180.0f;
            }
            if (blockMetadata == (0x66 ^ 0x62)) {
                n5 = 90.0f;
            }
            if (blockMetadata == (0x60 ^ 0x65)) {
                n5 = -90.0f;
            }
            GlStateManager.rotate(n5, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -1.0625f, 0.0f);
        }
        if (this.updateCounter / (0x3 ^ 0x5) % "  ".length() == 0) {
            this.tileSign.lineBeingEdited = this.editLine;
        }
        TileEntityRendererDispatcher.instance.renderTileEntityAt(this.tileSign, -0.5, -0.75, -0.5, 0.0f);
        this.tileSign.lineBeingEdited = -" ".length();
        GlStateManager.popMatrix();
        super.drawScreen(n, n2, n3);
    }
    
    static {
        I();
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents((boolean)(" ".length() != 0));
        this.buttonList.add(this.doneBtn = new GuiButton("".length(), this.width / "  ".length() - (0x62 ^ 0x6), this.height / (0x2E ^ 0x2A) + (0xF8 ^ 0x80), I18n.format(GuiEditSign.I["".length()], new Object["".length()])));
        this.tileSign.setEditable("".length() != 0);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled && guiButton.id == 0) {
            this.tileSign.markDirty();
            this.mc.displayGuiScreen(null);
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("!\u001b0}\u001c)\u0000<", "FnYSx");
        GuiEditSign.I[" ".length()] = I("\".\u0005\u001el4#\u000b\u0004", "QGbpB");
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 85 + 0 + 69 + 46) {
            this.editLine = (this.editLine - " ".length() & "   ".length());
        }
        if (n == 78 + 98 - 173 + 205 || n == (0x12 ^ 0xE) || n == 11 + 85 - 8 + 68) {
            this.editLine = (this.editLine + " ".length() & "   ".length());
        }
        String s = this.tileSign.signText[this.editLine].getUnformattedText();
        if (n == (0x6D ^ 0x63) && s.length() > 0) {
            s = s.substring("".length(), s.length() - " ".length());
        }
        if (ChatAllowedCharacters.isAllowedCharacter(c) && this.fontRendererObj.getStringWidth(String.valueOf(s) + c) <= (0xF9 ^ 0xA3)) {
            s = String.valueOf(s) + c;
        }
        this.tileSign.signText[this.editLine] = new ChatComponentText(s);
        if (n == " ".length()) {
            this.actionPerformed(this.doneBtn);
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
