package wtf.diablo.client.util.render.gl;

import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public final class ScissorUtils {

    private ScissorUtils() {}

    public static void scissorArea(final ScaledResolution sr, final float x, final double y, final float width, final float height)
    {
        final float x2 = x + width;
        final float y2 = (float) (y + height);
        final int factor = sr.getScaleFactor();
        GL11.glScissor((int) (x * factor), (int) ((sr.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }

    public static void scissorArea(final ScaledResolution sr, final double x, final double y, final double width, final double height) {
        ScissorUtils.scissorArea(sr, (float) x, y, (float) width, (float) height);
    }

}
