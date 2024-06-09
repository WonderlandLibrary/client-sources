// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL20;
import net.minecraft.client.Minecraft;

public class ShaderBackgroundMM
{
    public static final String VERTEX_SHADER = "#version 130\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
    private final Minecraft mc;
    private final int program;
    private final long startTime;
    private final String fragment;
    
    public ShaderBackgroundMM() {
        this("/*\n * Original shader from: https://www.shadertoy.com/view/ld3czS\n */\n\n#ifdef GL_ES\nprecision mediump float;\n#endif\n\n// glslsandbox uniforms\nuniform float time;\nuniform vec2 resolution;\n\n// shadertoy emulation\n#define iTime time\n#define iResolution resolution\n\n\n#define NS 100.\n#define CI 0.3\n\nfloat N21(vec2 p) {\n    return fract(sin(p.x*100.+p.y*7446.)*8345.);\n}\n\nfloat SS(vec2 uv) {\n    vec2 lv = fract(uv);\n    lv = lv*lv*(3.-2.*lv);\n    vec2 id = floor(uv);\n    \n    float bl = N21(id);\n    float br = N21(id+vec2(1., 0.));\n    float b = mix(bl, br, lv.x);\n\n    float tl = N21(id+vec2(0., 1.));\n    float tr = N21(id+vec2(1., 1.));\n    float t = mix(tl, tr, lv.x);\n\n    return mix(b, t, lv.y);\n}\n\nfloat L(vec2 uv, vec2 ofs, float b, float l) {\n    return smoothstep(0., 1000., b*max(0.1, l)/pow(max(0.0000000000001, length(uv-ofs)), 1./max(0.1, l)));\n}\n\nfloat rand(vec2 co, float s){\n    float PHI = 1.61803398874989484820459;\n    return fract(tan(distance(co*PHI, co)*s)*co.x);\n}\n\nvec2 H12(float s) {\n    float x = rand(vec2(243.234,63.834), s)-.5;\n    float y = rand(vec2(53.1434,13.1234), s)-.5;\n    return vec2(x, y);\n}\n\nvoid mainImage( out vec4 fragColor, in vec2 fragCoord )\n{\n    vec2 uv = fragCoord/iResolution.xy;\n\n    uv -= .5;\n    uv.x *= iResolution.x/iResolution.y;\n    \n   \n    \n    vec4 col = vec4(.0);\n    \n    vec4 b = vec4(0.01176470588, 0.05098039215, 0.14117647058, 1.);\n    vec4 p = vec4(0.13333333333, 0.07843137254, 0.13725490196, 1.);\n    vec4 lb = vec4(0.10196078431, 0.21568627451, 0.33333333333, 1.);\n    \n    vec4 blb = mix(b, lb, -uv.x*.2-(uv.y*.5));\n    \n    col += mix(blb, p, uv.x-(uv.y*1.5));\n    \n    for(float i=0.; i < NS; i++) {\n    \n        vec2 ofs = H12(i+1.);\n        ofs *= vec2(1.8, 1.1);\n        float r = (mod(i, 20.) == 0.)? 0.5+abs(sin(i/50.)): 0.25;\n        col += vec4(L(uv, ofs, r+(sin(fract(iTime)*.5*i)+1.)*0.02, 1.));\n    }\n    \n    uv.x += iTime*.03;\n    \n    \n    float c = 0.;\n    \n    for(float i = 1.; i < 8.; i+=1.) {\n        c += SS(uv*pow(2.,i))*pow(0.5, i);\n    }\n    \n    col = col + c * CI;\n\n    fragColor = col;\n}\n\nvoid main(void)\n{\n    mainImage(gl_FragColor, gl_FragCoord.xy);\n}");
    }
    
    public ShaderBackgroundMM(final String fragment) {
        this.mc = Minecraft.getMinecraft();
        this.program = GL20.glCreateProgram();
        this.startTime = System.currentTimeMillis();
        this.initShader(fragment);
        this.fragment = fragment;
    }
    
    public void initShader(final String frag) {
        final int vertex = GL20.glCreateShader(35633);
        final int fragment = GL20.glCreateShader(35632);
        GL20.glShaderSource(vertex, "#version 130\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}");
        GL20.glShaderSource(fragment, frag);
        GL20.glValidateProgram(this.program);
        GL20.glCompileShader(vertex);
        GL20.glCompileShader(fragment);
        GL20.glAttachShader(this.program, vertex);
        GL20.glAttachShader(this.program, fragment);
        GL20.glLinkProgram(this.program);
    }
    
    public void renderFirst() {
        GlStateManager.enableAlpha();
        this.bindShader();
    }
    
    public void renderSecond(final int scaledWidth, final int scaledHeight) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(0.0, scaledHeight);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d(scaledWidth, scaledHeight);
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d(scaledWidth, 0.0);
        GL11.glEnd();
        GL20.glUseProgram(0);
    }
    
    public void renderShader() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.renderFirst();
        this.addDefaultUniforms();
        this.renderSecond(sr.getScaledWidth(), sr.getScaledHeight());
    }
    
    public void renderShader(final int width, final int height) {
        this.renderFirst();
        this.addDefaultUniforms();
        this.renderSecond(width, height);
    }
    
    public void bindShader() {
        GL20.glUseProgram(this.program);
    }
    
    public void addDefaultUniforms() {
        GL20.glUniform2f(GL20.glGetUniformLocation(this.program, "resolution"), (float)this.mc.displayWidth, (float)this.mc.displayHeight);
        final float time = (System.currentTimeMillis() - this.startTime) / 1000.0f;
        GL20.glUniform1f(GL20.glGetUniformLocation(this.program, "time"), time);
    }
}
