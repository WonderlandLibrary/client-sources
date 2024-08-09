package dev.excellent.api.interfaces.game;

import dev.excellent.impl.util.math.ScaleMath;
import org.joml.Vector2d;

public interface IWindow extends IMinecraft {
    default Vector2d scaled() {
        return ScaleMath.getMouse(mc.getMainWindow().getScaledWidth(), mc.getMainWindow().getScaledHeight());
    }

}