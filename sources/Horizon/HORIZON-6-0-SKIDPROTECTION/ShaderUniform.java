package HORIZON-6-0-SKIDPROTECTION;

import javax.vecmath.Matrix4f;
import org.lwjgl.BufferUtils;
import org.apache.logging.log4j.LogManager;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.apache.logging.log4j.Logger;

public class ShaderUniform
{
    private static final Logger HorizonCode_Horizon_È;
    private int Â;
    private final int Ý;
    private final int Ø­áŒŠá;
    private final IntBuffer Âµá€;
    private final FloatBuffer Ó;
    private final String à;
    private boolean Ø;
    private final ShaderManager áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001046";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public ShaderUniform(final String name, final int type, final int count, final ShaderManager manager) {
        this.à = name;
        this.Ý = count;
        this.Ø­áŒŠá = type;
        this.áŒŠÆ = manager;
        if (type <= 3) {
            this.Âµá€ = BufferUtils.createIntBuffer(count);
            this.Ó = null;
        }
        else {
            this.Âµá€ = null;
            this.Ó = BufferUtils.createFloatBuffer(count);
        }
        this.Â = -1;
        this.Ý();
    }
    
    private void Ý() {
        this.Ø = true;
        if (this.áŒŠÆ != null) {
            this.áŒŠÆ.Ø­áŒŠá();
        }
    }
    
    public static int HorizonCode_Horizon_È(final String p_148085_0_) {
        byte var1 = -1;
        if (p_148085_0_.equals("int")) {
            var1 = 0;
        }
        else if (p_148085_0_.equals("float")) {
            var1 = 4;
        }
        else if (p_148085_0_.startsWith("matrix")) {
            if (p_148085_0_.endsWith("2x2")) {
                var1 = 8;
            }
            else if (p_148085_0_.endsWith("3x3")) {
                var1 = 9;
            }
            else if (p_148085_0_.endsWith("4x4")) {
                var1 = 10;
            }
        }
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final int p_148084_1_) {
        this.Â = p_148084_1_;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.à;
    }
    
    public void HorizonCode_Horizon_È(final float p_148090_1_) {
        this.Ó.position(0);
        this.Ó.put(0, p_148090_1_);
        this.Ý();
    }
    
    public void HorizonCode_Horizon_È(final float p_148087_1_, final float p_148087_2_) {
        this.Ó.position(0);
        this.Ó.put(0, p_148087_1_);
        this.Ó.put(1, p_148087_2_);
        this.Ý();
    }
    
    public void HorizonCode_Horizon_È(final float p_148095_1_, final float p_148095_2_, final float p_148095_3_) {
        this.Ó.position(0);
        this.Ó.put(0, p_148095_1_);
        this.Ó.put(1, p_148095_2_);
        this.Ó.put(2, p_148095_3_);
        this.Ý();
    }
    
    public void HorizonCode_Horizon_È(final float p_148081_1_, final float p_148081_2_, final float p_148081_3_, final float p_148081_4_) {
        this.Ó.position(0);
        this.Ó.put(p_148081_1_);
        this.Ó.put(p_148081_2_);
        this.Ó.put(p_148081_3_);
        this.Ó.put(p_148081_4_);
        this.Ó.flip();
        this.Ý();
    }
    
    public void Â(final float p_148092_1_, final float p_148092_2_, final float p_148092_3_, final float p_148092_4_) {
        this.Ó.position(0);
        if (this.Ø­áŒŠá >= 4) {
            this.Ó.put(0, p_148092_1_);
        }
        if (this.Ø­áŒŠá >= 5) {
            this.Ó.put(1, p_148092_2_);
        }
        if (this.Ø­áŒŠá >= 6) {
            this.Ó.put(2, p_148092_3_);
        }
        if (this.Ø­áŒŠá >= 7) {
            this.Ó.put(3, p_148092_4_);
        }
        this.Ý();
    }
    
    public void HorizonCode_Horizon_È(final int p_148083_1_, final int p_148083_2_, final int p_148083_3_, final int p_148083_4_) {
        this.Âµá€.position(0);
        if (this.Ø­áŒŠá >= 0) {
            this.Âµá€.put(0, p_148083_1_);
        }
        if (this.Ø­áŒŠá >= 1) {
            this.Âµá€.put(1, p_148083_2_);
        }
        if (this.Ø­áŒŠá >= 2) {
            this.Âµá€.put(2, p_148083_3_);
        }
        if (this.Ø­áŒŠá >= 3) {
            this.Âµá€.put(3, p_148083_4_);
        }
        this.Ý();
    }
    
    public void HorizonCode_Horizon_È(final float[] p_148097_1_) {
        if (p_148097_1_.length < this.Ý) {
            ShaderUniform.HorizonCode_Horizon_È.warn("Uniform.set called with a too-small value array (expected " + this.Ý + ", got " + p_148097_1_.length + "). Ignoring.");
        }
        else {
            this.Ó.position(0);
            this.Ó.put(p_148097_1_);
            this.Ó.position(0);
            this.Ý();
        }
    }
    
    public void HorizonCode_Horizon_È(final float p_148094_1_, final float p_148094_2_, final float p_148094_3_, final float p_148094_4_, final float p_148094_5_, final float p_148094_6_, final float p_148094_7_, final float p_148094_8_, final float p_148094_9_, final float p_148094_10_, final float p_148094_11_, final float p_148094_12_, final float p_148094_13_, final float p_148094_14_, final float p_148094_15_, final float p_148094_16_) {
        this.Ó.position(0);
        this.Ó.put(0, p_148094_1_);
        this.Ó.put(1, p_148094_2_);
        this.Ó.put(2, p_148094_3_);
        this.Ó.put(3, p_148094_4_);
        this.Ó.put(4, p_148094_5_);
        this.Ó.put(5, p_148094_6_);
        this.Ó.put(6, p_148094_7_);
        this.Ó.put(7, p_148094_8_);
        this.Ó.put(8, p_148094_9_);
        this.Ó.put(9, p_148094_10_);
        this.Ó.put(10, p_148094_11_);
        this.Ó.put(11, p_148094_12_);
        this.Ó.put(12, p_148094_13_);
        this.Ó.put(13, p_148094_14_);
        this.Ó.put(14, p_148094_15_);
        this.Ó.put(15, p_148094_16_);
        this.Ý();
    }
    
    public void HorizonCode_Horizon_È(final Matrix4f p_148088_1_) {
        this.HorizonCode_Horizon_È(p_148088_1_.m00, p_148088_1_.m01, p_148088_1_.m02, p_148088_1_.m03, p_148088_1_.m10, p_148088_1_.m11, p_148088_1_.m12, p_148088_1_.m13, p_148088_1_.m20, p_148088_1_.m21, p_148088_1_.m22, p_148088_1_.m23, p_148088_1_.m30, p_148088_1_.m31, p_148088_1_.m32, p_148088_1_.m33);
    }
    
    public void Â() {
        if (!this.Ø) {}
        this.Ø = false;
        if (this.Ø­áŒŠá <= 3) {
            this.Ø­áŒŠá();
        }
        else if (this.Ø­áŒŠá <= 7) {
            this.Âµá€();
        }
        else {
            if (this.Ø­áŒŠá > 10) {
                ShaderUniform.HorizonCode_Horizon_È.warn("Uniform.upload called, but type value (" + this.Ø­áŒŠá + ") is not " + "a valid type. Ignoring.");
                return;
            }
            this.Ó();
        }
    }
    
    private void Ø­áŒŠá() {
        switch (this.Ø­áŒŠá) {
            case 0: {
                OpenGlHelper.HorizonCode_Horizon_È(this.Â, this.Âµá€);
                break;
            }
            case 1: {
                OpenGlHelper.Â(this.Â, this.Âµá€);
                break;
            }
            case 2: {
                OpenGlHelper.Ý(this.Â, this.Âµá€);
                break;
            }
            case 3: {
                OpenGlHelper.Ø­áŒŠá(this.Â, this.Âµá€);
                break;
            }
            default: {
                ShaderUniform.HorizonCode_Horizon_È.warn("Uniform.upload called, but count value (" + this.Ý + ") is " + " not in the range of 1 to 4. Ignoring.");
                break;
            }
        }
    }
    
    private void Âµá€() {
        switch (this.Ø­áŒŠá) {
            case 4: {
                OpenGlHelper.HorizonCode_Horizon_È(this.Â, this.Ó);
                break;
            }
            case 5: {
                OpenGlHelper.Â(this.Â, this.Ó);
                break;
            }
            case 6: {
                OpenGlHelper.Ý(this.Â, this.Ó);
                break;
            }
            case 7: {
                OpenGlHelper.Ø­áŒŠá(this.Â, this.Ó);
                break;
            }
            default: {
                ShaderUniform.HorizonCode_Horizon_È.warn("Uniform.upload called, but count value (" + this.Ý + ") is " + "not in the range of 1 to 4. Ignoring.");
                break;
            }
        }
    }
    
    private void Ó() {
        switch (this.Ø­áŒŠá) {
            case 8: {
                OpenGlHelper.HorizonCode_Horizon_È(this.Â, true, this.Ó);
                break;
            }
            case 9: {
                OpenGlHelper.Â(this.Â, true, this.Ó);
                break;
            }
            case 10: {
                OpenGlHelper.Ý(this.Â, true, this.Ó);
                break;
            }
        }
    }
}
