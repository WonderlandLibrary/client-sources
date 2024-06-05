package zamorozka.ui;

public class myAngle {
    private float yaw;
    private float pitch;

    public myAngle(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public myAngle() {
        this(0.0f, 0.0f);
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public myAngle constrantAngle() {
        this.setYaw(this.getYaw() % 360F);
        this.setPitch(this.getPitch() % 360F);

        while (this.getYaw() <= -180F) {
            this.setYaw(this.getYaw() + 360F);
        }

        while (this.getPitch() <= -180F) {
            this.setPitch(this.getPitch() + 360F);
        }

        while (this.getYaw() > 180F) {
            this.setYaw(this.getYaw() - 360F);
        }

        while (this.getPitch() > 180F) {
            this.setPitch(this.getPitch() - 360F);
        }

        return this;
    }

}
