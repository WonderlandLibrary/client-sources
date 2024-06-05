package koks.utilities;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 15:50
 */
public class DeltaTime {

    private static float deltaTime;
    private static long lastMS = System.currentTimeMillis();

    public static void setDeltaTime() {
        long systemTime = System.currentTimeMillis();
        float delta = systemTime - lastMS;
        lastMS = systemTime;
        if (delta > 1000)
            delta = 16;
        if (delta < 1)
            delta = 1;
        setDeltaTime(delta);
    }

    private static void setDeltaTime(float deltaTime) {
        DeltaTime.deltaTime = deltaTime;
    }

    public static float getDeltaTime() {
        return deltaTime;
    }
}
