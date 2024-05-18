package net.minecraft.src;

import net.minecraft.client.*;

public class GuiSleepMP extends GuiChat
{
    @Override
    public void initGui() {
        super.initGui();
        final StringTranslate var1 = StringTranslate.getInstance();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 40, var1.translateKey("multiplayer.stopSleeping")));
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (par2 == 1) {
            this.wakeEntity();
        }
        else if (par2 == 28) {
            final String var3 = this.inputField.getText().trim();
            if (var3.length() > 0) {
                Minecraft.thePlayer.sendChatMessage(var3);
            }
            this.inputField.setText("");
            this.mc.ingameGUI.getChatGUI().resetScroll();
        }
        else {
            super.keyTyped(par1, par2);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.id == 1) {
            this.wakeEntity();
        }
        else {
            super.actionPerformed(par1GuiButton);
        }
    }
    
    private void wakeEntity() {
        final NetClientHandler var1 = Minecraft.thePlayer.sendQueue;
        var1.addToSendQueue(new Packet19EntityAction(Minecraft.thePlayer, 3));
    }
}
