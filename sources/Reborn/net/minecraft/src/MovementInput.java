package net.minecraft.src;

public class MovementInput
{
    public float moveStrafe;
    public float moveForward;
    public boolean jump;
    public boolean sneak;
    
    public MovementInput() {
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        this.jump = false;
        this.sneak = false;
    }
    
    public void updatePlayerMoveState() {
    }
}
