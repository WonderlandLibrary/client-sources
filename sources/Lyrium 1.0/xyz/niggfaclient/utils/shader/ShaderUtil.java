// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.shader;

import xyz.niggfaclient.utils.other.FileUtils;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import java.io.IOException;
import net.minecraft.util.ResourceLocation;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import org.lwjgl.opengl.GL20;
import xyz.niggfaclient.utils.Utils;

public class ShaderUtil extends Utils
{
    private final int programID;
    private final String roundedRectGradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    \n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius))) * color1.a;\n    gl_FragColor = vec4(createGradient(st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);\n}";
    private String roundedRect;
    
    public ShaderUtil(final String fragmentShaderLoc, final String vertexShaderLoc) {
        this.roundedRect = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color;\nuniform float radius;\nuniform bool blur;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n    vec2 rectHalf = rectSize * .5;\n    // Smooth the result (free antialiasing).\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n\n}";
        final int program = GL20.glCreateProgram();
        try {
            int fragmentShaderID = 0;
            switch (fragmentShaderLoc) {
                case "roundedRect": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.roundedRect.getBytes()), 35632);
                    break;
                }
                case "roundedRectGradient": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    \n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius))) * color1.a;\n    gl_FragColor = vec4(createGradient(st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);\n}".getBytes()), 35632);
                    break;
                }
                default: {
                    fragmentShaderID = this.createShader(ShaderUtil.mc.getResourceManager().getResource(new ResourceLocation(fragmentShaderLoc)).getInputStream(), 35632);
                    break;
                }
            }
            GL20.glAttachShader(program, fragmentShaderID);
            final int vertexShaderID = this.createShader(ShaderUtil.mc.getResourceManager().getResource(new ResourceLocation(vertexShaderLoc)).getInputStream(), 35633);
            GL20.glAttachShader(program, vertexShaderID);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        GL20.glLinkProgram(program);
        final int status = GL20.glGetProgrami(program, 35714);
        if (status == 0) {
            throw new IllegalStateException("Shader failed to link!");
        }
        this.programID = program;
    }
    
    public ShaderUtil(final String fragmentShaderLoc) {
        this(fragmentShaderLoc, "client/shaders/vertex.vsh");
    }
    
    public void init() {
        GL20.glUseProgram(this.programID);
    }
    
    public void unload() {
        GL20.glUseProgram(0);
    }
    
    public int getUniform(final String name) {
        return GL20.glGetUniformLocation(this.programID, name);
    }
    
    public void setUniformf(final String name, final float... args) {
        final int loc = GL20.glGetUniformLocation(this.programID, name);
        switch (args.length) {
            case 1: {
                GL20.glUniform1f(loc, args[0]);
                break;
            }
            case 2: {
                GL20.glUniform2f(loc, args[0], args[1]);
                break;
            }
            case 3: {
                GL20.glUniform3f(loc, args[0], args[1], args[2]);
                break;
            }
            case 4: {
                GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
                break;
            }
        }
    }
    
    public void setUniformi(final String name, final int... args) {
        final int loc = GL20.glGetUniformLocation(this.programID, name);
        if (args.length > 1) {
            GL20.glUniform2i(loc, args[0], args[1]);
        }
        else {
            GL20.glUniform1i(loc, args[0]);
        }
    }
    
    public static void drawQuads(final float x, final float y, final float width, final float height) {
        if (ShaderUtil.mc.gameSettings.ofFastRender) {
            return;
        }
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();
    }
    
    public static void drawQuads() {
        if (ShaderUtil.mc.gameSettings.ofFastRender) {
            return;
        }
        final ScaledResolution sr = new ScaledResolution(ShaderUtil.mc);
        final float width = (float)sr.getScaledWidth_double();
        final float height = (float)sr.getScaledHeight_double();
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(0.0f, height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(width, height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(width, 0.0f);
        GL11.glEnd();
    }
    
    private int createShader(final InputStream inputStream, final int shaderType) {
        final int shader = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shader, FileUtils.readInputStream(inputStream));
        GL20.glCompileShader(shader);
        if (GL20.glGetShaderi(shader, 35713) == 0) {
            System.out.println(GL20.glGetShaderInfoLog(shader, 4096));
            throw new IllegalStateException(String.format("Shader (%s) failed to compile!", shaderType));
        }
        return shader;
    }
}
