package net.minecraft.util;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.ghost.ModEagle;
import cafe.kagu.kagu.mods.impl.move.ModNoSlow;
import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput
{
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn)
    {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState()
    {
    	ModNoSlow modNoSlow = Kagu.getModuleManager().getModule(ModNoSlow.class);
        this.moveStrafe = 0.0F;
        this.moveForward = 0.0F;

        if (this.gameSettings.keyBindForward.isKeyDown())
        {
            ++this.moveForward;
        }

        if (this.gameSettings.keyBindBack.isKeyDown())
        {
            --this.moveForward;
        }

        if (this.gameSettings.keyBindLeft.isKeyDown())
        {
            ++this.moveStrafe;
        }

        if (this.gameSettings.keyBindRight.isKeyDown())
        {
            --this.moveStrafe;
        }

        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown() || (Kagu.getModuleManager().getModule(ModEagle.class).isEnabled() 
        		&& Kagu.getModuleManager().getModule(ModEagle.class).isShouldSneak());

        if (this.sneak && (modNoSlow.isDisabled() || modNoSlow.getCancelSneakSlowdown().isDisabled()))
        {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
            this.moveForward = (float)((double)this.moveForward * 0.3D);
        }
    }
}
