package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.client.network.*;

public class GuiSleepMP extends GuiChat
{
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, GuiSleepMP.width / 2 - 100, GuiSleepMP.height - 40, I18n.format("multiplayer.stopSleeping", new Object[0])));
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        if (keyCode == 1) {
            this.wakeFromSleep();
        }
        else if (keyCode != 28 && keyCode != 156) {
            super.keyTyped(typedChar, keyCode);
        }
        else {
            final String var3 = this.inputField.getText().trim();
            if (!var3.isEmpty()) {
                GuiSleepMP.mc.thePlayer.sendChatMessage(var3);
            }
            this.inputField.setText("");
            GuiSleepMP.mc.ingameGUI.getChatGUI().resetScroll();
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 1) {
            this.wakeFromSleep();
        }
        else {
            super.actionPerformed(button);
        }
    }
    
    private void wakeFromSleep() {
        final NetHandlerPlayClient var1 = GuiSleepMP.mc.thePlayer.sendQueue;
        var1.addToSendQueue(new C0BPacketEntityAction(GuiSleepMP.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SLEEPING));
    }
}
