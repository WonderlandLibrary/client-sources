package zamorozka.ui;

public class Color1 {

    public static java.awt.Color rainbow(float speed, float off) {

        double time = (double) System.currentTimeMillis() / speed;
        time += off;
        time %= 255.0f;
        return java.awt.Color.getHSBColor((float) (time / 255.0f), 1.0f, 1.0f);

    }

}