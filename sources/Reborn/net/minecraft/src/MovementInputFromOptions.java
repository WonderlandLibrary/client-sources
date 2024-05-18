package net.minecraft.src;

public class MovementInputFromOptions extends MovementInput
{
    private GameSettings gameSettings;
    
    public MovementInputFromOptions(final GameSettings par1GameSettings) {
        this.gameSettings = par1GameSettings;
    }
    
    @Override
    public void updatePlayerMoveState() {
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        if (this.gameSettings.keyBindForward.pressed) {
            ++this.moveForward;
        }
        if (this.gameSettings.keyBindBack.pressed) {
            --this.moveForward;
        }
        if (this.gameSettings.keyBindLeft.pressed) {
            ++this.moveStrafe;
        }
        if (this.gameSettings.keyBindRight.pressed) {
            --this.moveStrafe;
        }
        this.jump = this.gameSettings.keyBindJump.pressed;
        this.sneak = this.gameSettings.keyBindSneak.pressed;
        if (this.sneak) {
            this.moveStrafe *= 0.3;
            this.moveForward *= 0.3;
        }
    }
}
