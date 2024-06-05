package net.minecraft.util;

import club.mega.Mega;
import club.mega.gui.click.ClickGUI;
import club.mega.interfaces.MinecraftInterface;
import club.mega.module.impl.movement.InvMove;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.input.Keyboard;

public class MovementInputFromOptions extends MovementInput implements MinecraftInterface
{
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn)
    {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState()
    {
        this.moveStrafe = 0.0F;
        this.moveForward = 0.0F;

        if (Mega.INSTANCE.getModuleManager().getModule(InvMove.class).isToggled() && !(MC.currentScreen instanceof GuiChat) && (MC.currentScreen instanceof ClickGUI || !Mega.INSTANCE.getModuleManager().getModule(InvMove.class).clickGuiOnly.get())) {
            if (Keyboard.isKeyDown(gameSettings.keyBindForward.getKeyCode())) ++moveForward;
            if (Keyboard.isKeyDown(gameSettings.keyBindBack.getKeyCode())) --moveForward;
            if (Keyboard.isKeyDown(gameSettings.keyBindLeft.getKeyCode())) ++moveStrafe;
            if (Keyboard.isKeyDown(gameSettings.keyBindRight.getKeyCode())) --moveStrafe;
            jump = Keyboard.isKeyDown(gameSettings.keyBindJump.getKeyCode());
        } else {
            if (gameSettings.keyBindForward.isKeyDown()) ++moveForward;
            if (gameSettings.keyBindBack.isKeyDown()) --moveForward;
            if (gameSettings.keyBindLeft.isKeyDown()) ++moveStrafe;
            if (gameSettings.keyBindRight.isKeyDown()) --moveStrafe;
            jump = gameSettings.keyBindJump.isKeyDown();
        }
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown();

        if (this.sneak)
        {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
            this.moveForward = (float)((double)this.moveForward * 0.3D);
        }
    }
}
