/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

public class EventInput {
    private float forward;
    private float strafe;
    private boolean jump;
    private boolean sneak;
    private double sneakSlowDownMultiplier;

    public float getForward() {
        return this.forward;
    }

    public float getStrafe() {
        return this.strafe;
    }

    public boolean isJump() {
        return this.jump;
    }

    public boolean isSneak() {
        return this.sneak;
    }

    public double getSneakSlowDownMultiplier() {
        return this.sneakSlowDownMultiplier;
    }

    public void setForward(float f) {
        this.forward = f;
    }

    public void setStrafe(float f) {
        this.strafe = f;
    }

    public void setJump(boolean bl) {
        this.jump = bl;
    }

    public void setSneak(boolean bl) {
        this.sneak = bl;
    }

    public void setSneakSlowDownMultiplier(double d) {
        this.sneakSlowDownMultiplier = d;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof EventInput)) {
            return true;
        }
        EventInput eventInput = (EventInput)object;
        if (!eventInput.canEqual(this)) {
            return true;
        }
        if (Float.compare(this.getForward(), eventInput.getForward()) != 0) {
            return true;
        }
        if (Float.compare(this.getStrafe(), eventInput.getStrafe()) != 0) {
            return true;
        }
        if (this.isJump() != eventInput.isJump()) {
            return true;
        }
        if (this.isSneak() != eventInput.isSneak()) {
            return true;
        }
        return Double.compare(this.getSneakSlowDownMultiplier(), eventInput.getSneakSlowDownMultiplier()) != 0;
    }

    protected boolean canEqual(Object object) {
        return object instanceof EventInput;
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        n2 = n2 * 59 + Float.floatToIntBits(this.getForward());
        n2 = n2 * 59 + Float.floatToIntBits(this.getStrafe());
        n2 = n2 * 59 + (this.isJump() ? 79 : 97);
        n2 = n2 * 59 + (this.isSneak() ? 79 : 97);
        long l = Double.doubleToLongBits(this.getSneakSlowDownMultiplier());
        n2 = n2 * 59 + (int)(l >>> 32 ^ l);
        return n2;
    }

    public String toString() {
        return "EventInput(forward=" + this.getForward() + ", strafe=" + this.getStrafe() + ", jump=" + this.isJump() + ", sneak=" + this.isSneak() + ", sneakSlowDownMultiplier=" + this.getSneakSlowDownMultiplier() + ")";
    }

    public EventInput(float f, float f2, boolean bl, boolean bl2, double d) {
        this.forward = f;
        this.strafe = f2;
        this.jump = bl;
        this.sneak = bl2;
        this.sneakSlowDownMultiplier = d;
    }
}

