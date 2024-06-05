package net.minecraft.util;

import me.dinozoid.strife.module.implementations.player.InventoryMoveModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.input.Keyboard;

public class MovementInputFromOptions extends MovementInput
{
    private final GameSettings gameSettings;
    private final Minecraft mc = Minecraft.getMinecraft();

    public MovementInputFromOptions(GameSettings gameSettingsIn)
    {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState()
    {
        moveStrafe = 0.0F;
        moveForward = 0.0F;
        if(InventoryMoveModule.getInstance().toggled() && !(mc.currentScreen instanceof GuiChat)) {
            if (GameSettings.isKeyDown(gameSettings.keyBindForward)) ++moveForward;
            if (GameSettings.isKeyDown(gameSettings.keyBindBack)) --moveForward;
            if (GameSettings.isKeyDown(gameSettings.keyBindLeft)) ++moveStrafe;
            if (GameSettings.isKeyDown(gameSettings.keyBindRight)) --moveStrafe;
            jump = GameSettings.isKeyDown(gameSettings.keyBindJump);
            sneak = gameSettings.keyBindSneak.isKeyDown();
            if (sneak) {
                moveStrafe = (float)((double)moveStrafe * 0.3D);
                moveForward = (float)((double)moveForward * 0.3D);
            }
        } else {
            if (gameSettings.keyBindForward.isKeyDown()) ++moveForward;
            if (gameSettings.keyBindBack.isKeyDown()) --moveForward;
            if (gameSettings.keyBindLeft.isKeyDown()) ++moveStrafe;
            if (gameSettings.keyBindRight.isKeyDown()) --moveStrafe;
            jump = gameSettings.keyBindJump.isKeyDown();
            sneak = gameSettings.keyBindSneak.isKeyDown();
            if (sneak) {
                moveStrafe = (float)((double)moveStrafe * 0.3D);
                moveForward = (float)((double)moveForward * 0.3D);
            }
        }
        super.updatePlayerMoveState();
    }
}
