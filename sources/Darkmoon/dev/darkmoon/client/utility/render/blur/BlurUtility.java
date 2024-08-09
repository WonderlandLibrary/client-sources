package dev.darkmoon.client.utility.render.blur;

import dev.darkmoon.client.utility.Utility;
import dev.darkmoon.client.utility.render.StencilUtility;

public class BlurUtility implements Utility {
    public static void drawBlur(float radius, Runnable data) {
        StencilUtility.initStencilToWrite();
        data.run();
        StencilUtility.readStencilBuffer(1);
        GaussianBlur.renderBlur(radius);
        StencilUtility.uninitStencilBuffer();
    }

    public static void drawBlurredScreen(float radius) {
        GaussianBlur.renderBlur(radius);
    }
}
