package HORIZON-6-0-SKIDPROTECTION;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.opengl.GL11;

public class GLAllocation
{
    private static final String HorizonCode_Horizon_È = "CL_00000630";
    
    public static synchronized int HorizonCode_Horizon_È(final int p_74526_0_) {
        final int var1 = GL11.glGenLists(p_74526_0_);
        if (var1 == 0) {
            final int var2 = GL11.glGetError();
            String var3 = "No error code reported";
            if (var2 != 0) {
                var3 = GLU.gluErrorString(var2);
            }
            throw new IllegalStateException("glGenLists returned an ID of 0 for a count of " + p_74526_0_ + ", GL error (" + var2 + "): " + var3);
        }
        return var1;
    }
    
    public static synchronized void HorizonCode_Horizon_È(final int p_178874_0_, final int p_178874_1_) {
        GL11.glDeleteLists(p_178874_0_, p_178874_1_);
    }
    
    public static synchronized void Â(final int p_74523_0_) {
        GL11.glDeleteLists(p_74523_0_, 1);
    }
    
    public static synchronized ByteBuffer Ý(final int p_74524_0_) {
        return ByteBuffer.allocateDirect(p_74524_0_).order(ByteOrder.nativeOrder());
    }
    
    public static IntBuffer Ø­áŒŠá(final int p_74527_0_) {
        return Ý(p_74527_0_ << 2).asIntBuffer();
    }
    
    public static FloatBuffer Âµá€(final int p_74529_0_) {
        return Ý(p_74529_0_ << 2).asFloatBuffer();
    }
}
