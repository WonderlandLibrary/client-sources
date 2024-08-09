package xyz.mathax.mathaxclient.utils.render.color;

public class RainbowColor extends Color {
    private static final float[] hsb = new float[3];

    private double speed;
    private double saturation;
    private double brightness;

    public RainbowColor() {
        super();
    }

    public double getSpeed() {
        return speed;
    }

    public RainbowColor setSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    public double getSaturation() {
        return saturation;
    }

    public RainbowColor setSaturation(double saturation) {
        this.saturation = saturation;
        return this;
    }

    public double getBrightness() {
        return brightness;
    }

    public RainbowColor setBrightness(double brightness) {
        this.brightness = brightness;
        return this;
    }

    public RainbowColor getNext() {
        return getNext(1);
    }

    public RainbowColor getNext(double delta) {
        if (speed > 0) {
            java.awt.Color.RGBtoHSB(r, g, b, hsb);
            int color = java.awt.Color.HSBtoRGB(hsb[0] + (float) (speed * delta), (float) saturation, (float) brightness);

            r = toRGBAR(color);
            g = toRGBAG(color);
            b = toRGBAB(color);
        }

        return this;
    }

    public RainbowColor set(RainbowColor color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
        this.speed = color.speed;

        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!super.equals(object) || object == null || getClass() != object.getClass()) {
            return false;
        }

        return Double.compare(((RainbowColor) object).speed, speed) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(speed);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
