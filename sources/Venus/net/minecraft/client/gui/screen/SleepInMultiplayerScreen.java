/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.util.text.TranslationTextComponent;

public class SleepInMultiplayerScreen
extends ChatScreen {
    public SleepInMultiplayerScreen() {
        super("");
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new Button(this.width / 2 - 100, this.height - 40, 200, 20, new TranslationTextComponent("multiplayer.stopSleeping"), this::lambda$init$0));
    }

    @Override
    public void closeScreen() {
        this.wakeFromSleep();
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.wakeFromSleep();
        } else if (n == 257 || n == 335) {
            String string = this.inputField.getText().trim();
            if (!string.isEmpty()) {
                this.sendMessage(string);
            }
            this.inputField.setText("");
            this.minecraft.ingameGUI.getChatGUI().resetScroll();
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private void wakeFromSleep() {
        ClientPlayNetHandler clientPlayNetHandler = this.minecraft.player.connection;
        clientPlayNetHandler.sendPacket(new CEntityActionPacket(this.minecraft.player, CEntityActionPacket.Action.STOP_SLEEPING));
    }

    private void lambda$init$0(Button button) {
        this.wakeFromSleep();
    }
}

