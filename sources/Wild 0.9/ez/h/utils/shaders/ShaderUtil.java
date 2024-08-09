package ez.h.utils.shaders;

import java.io.*;
import org.lwjgl.opengl.*;
import ez.h.utils.*;

public class ShaderUtil
{
    private final int programID;
    private final String roundedRectGradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    \n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius))) * color1.a;\n    gl_FragColor = vec4(createGradient(st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);\n}";
    private String roundedRect;
    public static bib mc;
    
    public void setUniformf(final String s, final float... array) {
        final int glGetUniformLocation = GL20.glGetUniformLocation(this.programID, (CharSequence)s);
        switch (array.length) {
            case 1: {
                GL20.glUniform1f(glGetUniformLocation, array[0]);
                break;
            }
            case 2: {
                GL20.glUniform2f(glGetUniformLocation, array[0], array[1]);
                break;
            }
            case 3: {
                GL20.glUniform3f(glGetUniformLocation, array[0], array[1], array[2]);
                break;
            }
            case 4: {
                GL20.glUniform4f(glGetUniformLocation, array[0], array[1], array[2], array[3]);
                break;
            }
        }
    }
    
    public void init() {
        GL20.glUseProgram(this.programID);
    }
    
    public ShaderUtil(final String s, final String s2) {
        this.roundedRect = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color;\nuniform float radius;\nuniform bool blur;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n    vec2 rectHalf = rectSize * .5;\n    // Smooth the result (free antialiasing).\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n\n}";
        final int glCreateProgram = GL20.glCreateProgram();
        try {
            int n2 = 0;
            switch (s) {
                case "roundedRect": {
                    n2 = this.createShader(new ByteArrayInputStream(this.roundedRect.getBytes()), 24983 + 32714 - 37694 + 15629);
                    break;
                }
                case "roundedRectGradient": {
                    n2 = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    \n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius))) * color1.a;\n    gl_FragColor = vec4(createGradient(st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);\n}".getBytes()), 34364 + 17139 - 23158 + 7287);
                    break;
                }
                default: {
                    n2 = this.createShader(ShaderUtil.mc.O().a(new nf(s)).b(), 17178 + 8226 - 11657 + 21885);
                    break;
                }
            }
            GL20.glAttachShader(glCreateProgram, n2);
            GL20.glAttachShader(glCreateProgram, this.createShader(ShaderUtil.mc.O().a(new nf(s2)).b(), 25452 + 7870 - 33118 + 35429));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        GL20.glLinkProgram(glCreateProgram);
        if (GL20.glGetProgrami(glCreateProgram, 2750 + 13676 + 10821 + 8467) == 0) {
            throw new IllegalStateException("Shader failed to link!");
        }
        this.programID = glCreateProgram;
    }
    
    static {
        ShaderUtil.mc = bib.z();
    }
    
    public void unload() {
        GL20.glUseProgram(0);
    }
    
    public ShaderUtil(final String s) {
        this(s, "vertex.vsh");
    }
    
    public int getUniform(final String s) {
        return GL20.glGetUniformLocation(this.programID, (CharSequence)s);
    }
    
    public static void drawQuads(final float n, final float n2, final float n3, final float n4) {
        if (ShaderUtil.mc.t.ofFastRender) {
            return;
        }
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(n, n2);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(n, n2 + n4);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(n + n3, n2 + n4);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(n + n3, n2);
        GL11.glEnd();
    }
    
    public static void drawQuads() {
        if (ShaderUtil.mc.t.ofFastRender) {
            return;
        }
        final bit bit = new bit(ShaderUtil.mc);
        final float n = (float)bit.c();
        final float n2 = (float)bit.d();
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(0.0f, n2);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(n, n2);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(n, 0.0f);
        GL11.glEnd();
    }
    
    public void setUniformi(final String s, final int... array) {
        final int glGetUniformLocation = GL20.glGetUniformLocation(this.programID, (CharSequence)s);
        if (array.length > 1) {
            GL20.glUniform2i(glGetUniformLocation, array[0], array[1]);
        }
        else {
            GL20.glUniform1i(glGetUniformLocation, array[0]);
        }
    }
    
    private int createShader(final InputStream inputStream, final int n) {
        final int glCreateShader = GL20.glCreateShader(n);
        GL20.glShaderSource(glCreateShader, (CharSequence)Utils.readInputStream(inputStream));
        GL20.glCompileShader(glCreateShader);
        if (GL20.glGetShaderi(glCreateShader, 28397 + 13308 - 24831 + 18839) == 0) {
            System.out.println(GL20.glGetShaderInfoLog(glCreateShader, 1753 + 305 - 1609 + 3647));
            throw new IllegalStateException(String.format("Shader (%s) failed to compile!", n));
        }
        return glCreateShader;
    }
}
