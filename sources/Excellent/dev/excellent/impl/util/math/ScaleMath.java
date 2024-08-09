package dev.excellent.impl.util.math;

import dev.excellent.api.interfaces.game.IWindow;
import lombok.experimental.UtilityClass;
import org.joml.Vector2d;

@UtilityClass
public class ScaleMath implements IWindow {
    private final int SCALE = 2;

    public void scalePre() {
        mc.gameRenderer.setupOverlayRendering(SCALE);
    }

    public void scalePost() {
        mc.gameRenderer.setupOverlayRendering();
    }

    public Vector2d getMouse(double mouseX, double mouseY) {
        return new Vector2d(mouseX * mc.getMainWindow().getScaleFactor() / SCALE, mouseY * mc.getMainWindow().getScaleFactor() / SCALE);
    }

}
