package dev.excellent.impl.util.rotation;

import dev.excellent.api.interfaces.client.IAccess;
import lombok.experimental.UtilityClass;

import static java.lang.Math.round;

@UtilityClass
public class GCDUtil implements IAccess {


    public float getSensitivity(float rot) {
        return getDeltaMouse(rot) * getGCDValue();
    }

    public float getGCDValue() {
        return (float) (getGCD() * 0.15);
    }

    public float getGCD() {
        float f1;
        return (f1 = (float) (mc.gameSettings.mouseSensitivity * 0.6 + 0.2)) * f1 * f1 * 8;
    }

    public float getDeltaMouse(float delta) {
        return round(delta / getGCDValue());
    }

}
