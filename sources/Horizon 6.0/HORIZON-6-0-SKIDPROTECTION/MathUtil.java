package HORIZON-6-0-SKIDPROTECTION;

import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.opengl.GL11;
import org.lwjgl.BufferUtils;

public class MathUtil
{
    public static Vec3 HorizonCode_Horizon_È(final double x, final double y, final double z) {
        final FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
        final IntBuffer viewport = BufferUtils.createIntBuffer(16);
        final FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
        final FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        final boolean result = GLU.gluProject((float)x, (float)y, (float)z, modelView, projection, viewport, screenCoords);
        if (result) {
            return new Vec3(screenCoords.get(0), Display.getHeight() - screenCoords.get(1), screenCoords.get(2));
        }
        return null;
    }
    
    public static Vec3 HorizonCode_Horizon_È(final Vec3 vec) {
        return HorizonCode_Horizon_È(vec.HorizonCode_Horizon_È, vec.Â, vec.Ý);
    }
    
    public static double HorizonCode_Horizon_È(final double x1, final double y1, final double x2, final double y2) {
        return Math.toDegrees(Math.atan2(y1 - y2, x1 - x2));
    }
    
    public static double Â(final double x1, final double y1, final double x2, final double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0));
    }
    
    public static double HorizonCode_Horizon_È(final double a, final double b) {
        return ((a - b) % 360.0 + 540.0) % 360.0 - 180.0;
    }
    
    public static double Â(final double val, final double inc) {
        return Math.round(val * (1.0 / inc)) / (1.0 / inc);
    }
}
