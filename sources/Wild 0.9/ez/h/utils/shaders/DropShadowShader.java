package ez.h.utils.shaders;

import ez.h.utils.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import java.nio.*;

public class DropShadowShader
{
    public static bvd framebuffer;
    static bib mc;
    public static ShaderUtil blurShader;
    
    public static void renderBlur(final float n) {
        bus.m();
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        cii.c(330 + 146 - 297 + 591, 146 + 44 + 209 + 372, 1, 0);
        (DropShadowShader.framebuffer = RenderUtils.createFrameBuffer(DropShadowShader.framebuffer)).f();
        DropShadowShader.framebuffer.a(true);
        DropShadowShader.blurShader.init();
        setupUniforms(1.0f, 0.0f, n);
        RenderUtils.bindTexture(DropShadowShader.mc.b().g);
        ShaderUtil.drawQuads();
        DropShadowShader.framebuffer.e();
        DropShadowShader.blurShader.unload();
        DropShadowShader.mc.b().a(true);
        DropShadowShader.blurShader.init();
        setupUniforms(0.0f, 1.0f, n);
        RenderUtils.bindTexture(DropShadowShader.framebuffer.g);
        ShaderUtil.drawQuads();
        DropShadowShader.blurShader.unload();
        bus.I();
        bus.i(0);
    }
    
    public static float calculateGaussianValue(final float n, final float n2) {
        return (float)(1.0 / Math.sqrt(2.0 * 3.141592653 * (n2 * n2)) * Math.exp(-(n * n) / (2.0 * (n2 * n2))));
    }
    
    static {
        DropShadowShader.blurShader = new ShaderUtil("gaussian.frag");
        DropShadowShader.framebuffer = new bvd(1, 1, false);
        DropShadowShader.mc = bib.z();
    }
    
    public static void setupUniforms(final float n, final float n2, final float n3) {
        DropShadowShader.blurShader.setUniformi("textureIn", 0);
        DropShadowShader.blurShader.setUniformf("texelSize", 1.0f / DropShadowShader.mc.d, 1.0f / DropShadowShader.mc.e);
        DropShadowShader.blurShader.setUniformf("direction", n, n2);
        DropShadowShader.blurShader.setUniformf("radius", n3);
        final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(192 + 21 - 184 + 227);
        for (int n4 = 0; n4 <= n3; ++n4) {
            floatBuffer.put(calculateGaussianValue((float)n4, n3 / 2.0f));
        }
        floatBuffer.rewind();
        GL20.glUniform1(DropShadowShader.blurShader.getUniform("weights"), floatBuffer);
    }
}
