package net.minecraft.client.gui;

import net.minecraft.command.server.*;
import org.apache.logging.log4j.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import java.io.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class GuiCommandBlock extends GuiScreen
{
    private static final Logger field_146488_a;
    private GuiTextField commandTextField;
    private GuiButton cancelBtn;
    private GuiButton doneBtn;
    private boolean field_175389_t;
    private final CommandBlockLogic localCommandBlock;
    private GuiButton field_175390_s;
    private GuiTextField previousOutputTextField;
    private static final String[] I;
    
    static {
        I();
        field_146488_a = LogManager.getLogger();
    }
    
    @Override
    public void updateScreen() {
        this.commandTextField.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)(" ".length() != 0));
        this.buttonList.clear();
        this.buttonList.add(this.doneBtn = new GuiButton("".length(), this.width / "  ".length() - (0x2C ^ 0x28) - (46 + 88 - 16 + 32), this.height / (0x2D ^ 0x29) + (0xC5 ^ 0xBD) + (0x66 ^ 0x6A), 119 + 84 - 199 + 146, 0x83 ^ 0x97, I18n.format(GuiCommandBlock.I["".length()], new Object["".length()])));
        this.buttonList.add(this.cancelBtn = new GuiButton(" ".length(), this.width / "  ".length() + (0x84 ^ 0x80), this.height / (0x92 ^ 0x96) + (0xEF ^ 0x97) + (0x6 ^ 0xA), 99 + 136 - 94 + 9, 0x1F ^ 0xB, I18n.format(GuiCommandBlock.I[" ".length()], new Object["".length()])));
        this.buttonList.add(this.field_175390_s = new GuiButton(0x81 ^ 0x85, this.width / "  ".length() + (68 + 32 - 27 + 77) - (0xA6 ^ 0xB2), 36 + 53 + 3 + 58, 0xBD ^ 0xA9, 0xB3 ^ 0xA7, GuiCommandBlock.I["  ".length()]));
        (this.commandTextField = new GuiTextField("  ".length(), this.fontRendererObj, this.width / "  ".length() - (41 + 70 - 16 + 55), 0xBA ^ 0x88, 116 + 237 - 307 + 254, 0x83 ^ 0x97)).setMaxStringLength(28183 + 3402 - 25710 + 26892);
        this.commandTextField.setFocused(" ".length() != 0);
        this.commandTextField.setText(this.localCommandBlock.getCommand());
        (this.previousOutputTextField = new GuiTextField("   ".length(), this.fontRendererObj, this.width / "  ".length() - (34 + 9 + 66 + 41), 120 + 26 - 110 + 114, 204 + 188 - 229 + 113, 0x72 ^ 0x66)).setMaxStringLength(7925 + 8846 - 7999 + 23995);
        this.previousOutputTextField.setEnabled("".length() != 0);
        this.previousOutputTextField.setText(GuiCommandBlock.I["   ".length()]);
        this.field_175389_t = this.localCommandBlock.shouldTrackOutput();
        this.func_175388_a();
        final GuiButton doneBtn = this.doneBtn;
        int enabled;
        if (this.commandTextField.getText().trim().length() > 0) {
            enabled = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            enabled = "".length();
        }
        doneBtn.enabled = (enabled != 0);
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)("".length() != 0));
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        this.commandTextField.textboxKeyTyped(c, n);
        this.previousOutputTextField.textboxKeyTyped(c, n);
        final GuiButton doneBtn = this.doneBtn;
        int enabled;
        if (this.commandTextField.getText().trim().length() > 0) {
            enabled = " ".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            enabled = "".length();
        }
        doneBtn.enabled = (enabled != 0);
        if (n != (0xA2 ^ 0xBE) && n != 148 + 81 - 118 + 45) {
            if (n == " ".length()) {
                this.actionPerformed(this.cancelBtn);
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
        }
        else {
            this.actionPerformed(this.doneBtn);
        }
    }
    
    private static void I() {
        (I = new String[0x2B ^ 0x3B])["".length()] = I("&0&d'.+*", "AEOJC");
        GuiCommandBlock.I[" ".length()] = I("\u0011/8]\u0019\u001742\u0016\u0016", "vZQsz");
        GuiCommandBlock.I["  ".length()] = I(",", "cNuzg");
        GuiCommandBlock.I["   ".length()] = I("`", "MDiYL");
        GuiCommandBlock.I[0x94 ^ 0x90] = I("\u0015&7\u000e\t.&/\"", "XeKOm");
        GuiCommandBlock.I[0x9D ^ 0x98] = I("\n >\u0002<\u000f!f<6\u001f\u0007'\">\n*,", "kDHOS");
        GuiCommandBlock.I[0x68 ^ 0x6E] = I("$\u0006 %\b!\u0007x\u000b\b(\u000f7\u0006\u0003", "EbVhg");
        GuiCommandBlock.I[0x3A ^ 0x3D] = I("9\u000b;\u0000\u0015<\nc#\u001f9\u001d(>\u000e\b\u0003,4\u001f*", "XoMMz");
        GuiCommandBlock.I[0xCD ^ 0xC5] = I("\u000e.\u0000\u0018#\u000b/X'-\u0001.\u00198\u001c\u0003+\u000f0>", "oJvUL");
        GuiCommandBlock.I[0x88 ^ 0x81] = I("\u0003 \u0006\u0005\u001e\u0006!^)\u001d\u000e\u0014\u001c)\b\u00076\u0003", "bDpHq");
        GuiCommandBlock.I[0x8F ^ 0x85] = I("3\u00079\u001c\f6\u0006a0\u000f>&!%\n&\n*\"", "RcOQc");
        GuiCommandBlock.I[0xA0 ^ 0xAB] = I("", "fyQlw");
        GuiCommandBlock.I[0x78 ^ 0x74] = I("'2\u00189\u000b\"3@\u0004\u0016# \u0007\u001b\u00115\u0019\u001b\u0000\u00143\"", "FVntd");
        GuiCommandBlock.I[0x6D ^ 0x60] = I("\u0006", "ISApH");
        GuiCommandBlock.I[0x7 ^ 0x9] = I("\f", "TTYoS");
        GuiCommandBlock.I[0x10 ^ 0x1F] = I("l", "AQFQZ");
    }
    
    public GuiCommandBlock(final CommandBlockLogic localCommandBlock) {
        this.localCommandBlock = localCommandBlock;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == " ".length()) {
                this.localCommandBlock.setTrackOutput(this.field_175389_t);
                this.mc.displayGuiScreen(null);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else if (guiButton.id == 0) {
                final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                packetBuffer.writeByte(this.localCommandBlock.func_145751_f());
                this.localCommandBlock.func_145757_a(packetBuffer);
                packetBuffer.writeString(this.commandTextField.getText());
                packetBuffer.writeBoolean(this.localCommandBlock.shouldTrackOutput());
                this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(GuiCommandBlock.I[0x71 ^ 0x75], packetBuffer));
                if (!this.localCommandBlock.shouldTrackOutput()) {
                    this.localCommandBlock.setLastOutput(null);
                }
                this.mc.displayGuiScreen(null);
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (guiButton.id == (0x35 ^ 0x31)) {
                final CommandBlockLogic localCommandBlock = this.localCommandBlock;
                int trackOutput;
                if (this.localCommandBlock.shouldTrackOutput()) {
                    trackOutput = "".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    trackOutput = " ".length();
                }
                localCommandBlock.setTrackOutput(trackOutput != 0);
                this.func_175388_a();
            }
        }
    }
    
    private void func_175388_a() {
        if (this.localCommandBlock.shouldTrackOutput()) {
            this.field_175390_s.displayString = GuiCommandBlock.I[0xA1 ^ 0xAC];
            if (this.localCommandBlock.getLastOutput() != null) {
                this.previousOutputTextField.setText(this.localCommandBlock.getLastOutput().getUnformattedText());
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
        }
        else {
            this.field_175390_s.displayString = GuiCommandBlock.I[0x10 ^ 0x1E];
            this.previousOutputTextField.setText(GuiCommandBlock.I[0x75 ^ 0x7A]);
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.commandTextField.mouseClicked(n, n2, n3);
        this.previousOutputTextField.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiCommandBlock.I[0x8C ^ 0x89], new Object["".length()]), this.width / "  ".length(), 0x95 ^ 0x81, 16118973 + 10255394 - 15914816 + 6317664);
        this.drawString(this.fontRendererObj, I18n.format(GuiCommandBlock.I[0xA1 ^ 0xA7], new Object["".length()]), this.width / "  ".length() - (85 + 118 - 146 + 93), 0x9F ^ 0xBA, 4234581 + 9896638 - 12069653 + 8465314);
        this.commandTextField.drawTextBox();
        final int n4 = 0x41 ^ 0xA;
        int length = "".length();
        this.drawString(this.fontRendererObj, I18n.format(GuiCommandBlock.I[0x35 ^ 0x32], new Object["".length()]), this.width / "  ".length() - (81 + 41 - 1 + 29), n4 + length++ * this.fontRendererObj.FONT_HEIGHT, 8240452 + 3333247 - 2030030 + 983211);
        this.drawString(this.fontRendererObj, I18n.format(GuiCommandBlock.I[0x87 ^ 0x8F], new Object["".length()]), this.width / "  ".length() - (142 + 85 - 207 + 130), n4 + length++ * this.fontRendererObj.FONT_HEIGHT, 3434644 + 9755422 - 4941788 + 2278602);
        this.drawString(this.fontRendererObj, I18n.format(GuiCommandBlock.I[0x56 ^ 0x5F], new Object["".length()]), this.width / "  ".length() - (2 + 66 + 50 + 32), n4 + length++ * this.fontRendererObj.FONT_HEIGHT, 695145 + 8982485 - 8836142 + 9685392);
        this.drawString(this.fontRendererObj, I18n.format(GuiCommandBlock.I[0xB4 ^ 0xBE], new Object["".length()]), this.width / "  ".length() - (101 + 73 - 159 + 135), n4 + length++ * this.fontRendererObj.FONT_HEIGHT, 671415 + 951785 + 1197248 + 7706432);
        this.drawString(this.fontRendererObj, GuiCommandBlock.I[0x88 ^ 0x83], this.width / "  ".length() - (48 + 64 - 63 + 101), n4 + length++ * this.fontRendererObj.FONT_HEIGHT, 9117565 + 9894309 - 18197291 + 9712297);
        if (this.previousOutputTextField.getText().length() > 0) {
            this.drawString(this.fontRendererObj, I18n.format(GuiCommandBlock.I[0x9D ^ 0x91], new Object["".length()]), this.width / "  ".length() - (135 + 37 - 123 + 101), n4 + length * this.fontRendererObj.FONT_HEIGHT + (0x16 ^ 0x6), 4728171 + 2972112 - 2891858 + 5718455);
            this.previousOutputTextField.drawTextBox();
        }
        super.drawScreen(n, n2, n3);
    }
}
