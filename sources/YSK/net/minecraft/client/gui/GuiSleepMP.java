package net.minecraft.client.gui;

import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;

public class GuiSleepMP extends GuiChat
{
    private static final String[] I;
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == " ".length()) {
            this.wakeFromSleep();
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else if (n != (0x74 ^ 0x68) && n != 97 + 21 - 40 + 78) {
            super.keyTyped(c, n);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            final String trim = this.inputField.getText().trim();
            if (!trim.isEmpty()) {
                this.mc.thePlayer.sendChatMessage(trim);
            }
            this.inputField.setText(GuiSleepMP.I[" ".length()]);
            this.mc.ingameGUI.getChatGUI().resetScroll();
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (0x56 ^ 0x32), this.height - (0x48 ^ 0x60), I18n.format(GuiSleepMP.I["".length()], new Object["".length()])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == " ".length()) {
            this.wakeFromSleep();
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else {
            super.actionPerformed(guiButton);
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0007\u0006\u0019\u0016$\u001a\u001f\u0014\u001b(\u0018]\u0006\u0016\"\u001a \u0019\u0007(\u001a\u001a\u001b\u0005", "jsubM");
        GuiSleepMP.I[" ".length()] = I("", "ARmOB");
    }
    
    static {
        I();
    }
    
    private void wakeFromSleep() {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SLEEPING));
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
