package fr.dog.util.input;

import lombok.Setter;
import org.lwjglx.input.Mouse;

public class ScrollUtil {


    private final float frequency, size;
    private float scrollTemp = 0;
    @Setter
    private boolean isActive = true;

    public ScrollUtil(float frequency, float size) {
        this.frequency = frequency;
        this.size = size;

    }

    public float getScroll() {
        float yWheel = !isActive ? 0 : (float) Mouse.getDWheel() / size;

        scrollTemp += yWheel;
        scrollTemp *= frequency;
        return scrollTemp;
    }

}
