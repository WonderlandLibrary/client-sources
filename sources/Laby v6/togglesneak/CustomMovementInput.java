package togglesneak;

import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInputFromOptions;

public class CustomMovementInput
{
    public boolean isDisabled;
    public boolean canDoubleTap;
    public boolean sprint = false;
    public boolean sprintHeldAndReleased = false;
    public boolean sprintDoubleTapped = false;
    private long lastPressed;
    private long lastSprintPressed;
    private boolean handledSneakPress;
    private boolean handledSprintPress;
    private boolean wasRiding;

    public void update(Minecraft mc, MovementInputFromOptions options, EntityPlayerSP thisPlayer)
    {
        options.moveStrafe = 0.0F;
        options.moveForward = 0.0F;
        GameSettings gamesettings = mc.gameSettings;

        if (gamesettings.keyBindForward.isKeyDown())
        {
            ++options.moveForward;
        }

        if (gamesettings.keyBindBack.isKeyDown())
        {
            --options.moveForward;
        }

        if (gamesettings.keyBindLeft.isKeyDown())
        {
            ++options.moveStrafe;
        }

        if (gamesettings.keyBindRight.isKeyDown())
        {
            --options.moveStrafe;
        }

        options.jump = gamesettings.keyBindJump.isKeyDown();

        if (ToggleSneakMod.optionToggleSneak)
        {
            if (gamesettings.keyBindSneak.isKeyDown() && !this.handledSneakPress)
            {
                if (!thisPlayer.isRiding() && !thisPlayer.capabilities.isFlying)
                {
                    options.sneak = !options.sneak;
                }
                else
                {
                    options.sneak = true;
                    this.wasRiding = thisPlayer.isRiding();
                }

                this.lastPressed = System.currentTimeMillis();
                this.handledSneakPress = true;
            }

            if (!gamesettings.keyBindSneak.isKeyDown() && this.handledSneakPress)
            {
                if (!thisPlayer.capabilities.isFlying && !this.wasRiding)
                {
                    if (System.currentTimeMillis() - this.lastPressed > 300L)
                    {
                        options.sneak = false;
                    }
                }
                else
                {
                    options.sneak = false;
                    this.wasRiding = false;
                }

                this.handledSneakPress = false;
            }
        }
        else
        {
            options.sneak = gamesettings.keyBindSneak.isKeyDown();
        }

        if (options.sneak && mc.currentScreen != null)
        {
            options.sneak = false;
        }

        if (options.sneak)
        {
            options.moveStrafe = (float)((double)options.moveStrafe * 0.3D);
            options.moveForward = (float)((double)options.moveForward * 0.3D);
        }

        boolean flag = (float)thisPlayer.getFoodStats().getFoodLevel() > 6.0F || thisPlayer.capabilities.isFlying;
        boolean flag1 = !options.sneak && !thisPlayer.isRiding() && !thisPlayer.capabilities.isFlying && flag;
        this.isDisabled = !ToggleSneakMod.optionToggleSprint;
        this.canDoubleTap = ToggleSneakMod.optionDoubleTap;

        if ((flag1 || this.isDisabled) && gamesettings.keyBindSprint.isKeyDown() && !this.handledSprintPress && !this.isDisabled)
        {
            this.sprint = !this.sprint;
            this.lastSprintPressed = System.currentTimeMillis();
            this.handledSprintPress = true;
            this.sprintHeldAndReleased = false;
        }

        if ((flag1 || this.isDisabled) && !gamesettings.keyBindSprint.isKeyDown() && this.handledSprintPress)
        {
            if (System.currentTimeMillis() - this.lastSprintPressed > 300L)
            {
                this.sprintHeldAndReleased = true;
            }

            this.handledSprintPress = false;
        }

        this.UpdateStatus(options, thisPlayer, gamesettings);
    }

    public void UpdateSprint(boolean newValue, boolean doubleTapped)
    {
        this.sprint = newValue;
        this.sprintDoubleTapped = doubleTapped;
    }

    private void UpdateStatus(MovementInputFromOptions options, EntityPlayerSP thisPlayer, GameSettings settings)
    {
        if (ToggleSneakMod.optionShowHUDText)
        {
            String s = "";
            boolean flag = thisPlayer.capabilities.isFlying;
            boolean flag1 = thisPlayer.isRiding();
            boolean flag2 = settings.keyBindSneak.isKeyDown();
            boolean flag3 = settings.keyBindSprint.isKeyDown();

            if (flag)
            {
                DecimalFormat decimalformat = new DecimalFormat("#.00");

                if (ToggleSneakMod.optionEnableFlyBoost && flag3)
                {
                    s = s + "[Flying (" + decimalformat.format(ToggleSneakMod.optionFlyBoostAmount) + "x boost)]  ";
                }
                else
                {
                    s = s + "[Flying]  ";
                }
            }

            if (flag1)
            {
                s = s + "[Riding]  ";
            }

            if (options.sneak)
            {
                if (flag)
                {
                    s = s + "[Descending]  ";
                }
                else if (flag1)
                {
                    s = s + "[Dismounting]  ";
                }
                else if (flag2)
                {
                    s = s + "[Sneaking (Key Held)]  ";
                }
                else
                {
                    s = s + "[Sneaking (Toggled)]  ";
                }
            }
            else if (this.sprint && !flag && !flag1)
            {
                boolean flag4 = this.sprintHeldAndReleased || this.isDisabled || this.sprintDoubleTapped;

                if (flag3)
                {
                    s = s + "[Sprinting (Key Held)]";
                }
                else if (flag4)
                {
                    s = s + "[Sprinting (Vanilla)]";
                }
                else
                {
                    s = s + "[Sprinting (Toggled)]";
                }
            }

            ToggleSneakModEvents.SetHUDText(s);
        }
    }
}
