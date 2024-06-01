package io.github.liticane.clients.feature.event.impl.input;

import com.ibm.icu.impl.duration.impl.Utils;
import io.github.liticane.clients.feature.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiScreen;

public class MoveInputEvent extends Event {
    private float forward, strafe;
    private boolean jump, sneak;
    private double sneakSlowDownMultiplier;

    public float getForward() {
        return forward;
    }

    public float getStrafe() {
        return strafe;
    }

    public boolean isJump() {
        return jump;
    }

    public void setForward(float forward) {
        this.forward = forward;
    }

    public void setStrafe(float strafe) {
        this.strafe = strafe;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setSneak(boolean sneak) {
        this.sneak = sneak;
    }

    public void setSneakSlowDownMultiplier(double sneakSlowDownMultiplier) {
        this.sneakSlowDownMultiplier = sneakSlowDownMultiplier;
    }

    public boolean isSneak() {
        return sneak;
    }

    public double getSneakSlowDownMultiplier() {
        return sneakSlowDownMultiplier;
    }

    public MoveInputEvent(float forward, float strafe, boolean jump, boolean sneak, double sneakSlowDownMultiplier) {
        this.forward = forward;
        this.strafe = strafe;
        this.jump = jump;
        this.sneak = sneak;
        this.sneakSlowDownMultiplier = sneakSlowDownMultiplier;
    }
}
