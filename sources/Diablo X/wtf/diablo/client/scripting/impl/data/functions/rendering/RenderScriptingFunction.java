package wtf.diablo.client.scripting.impl.data.functions.rendering;

import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;

public final class RenderScriptingFunction {

    public void drawRectangle(final double x, final double y, final double width, final double height, final int color) {
        RenderUtil.drawRect((int) x, (int) y, (int) width, (int) height, color);
    }

    public void drawRectangle(final double x, final double y, final double width, final double height, final Color color) {
        RenderUtil.drawRect((int) x, (int) y, (int) width, (int) height, color.getRGB());
    }

    public void drawRoundedRectangle(final double x, final double y, final double width, final double height, final int radius, final int color) {
        RenderUtil.drawRoundedRectangle(x, y, width, height, radius, color);
    }

    public void drawRoundedRectangle(final double x, final double y, final double width, final double height, final int radius, final Color color) {
        RenderUtil.drawRoundedRectangle(x, y, width, height, radius, color.getRGB());
    }
}
