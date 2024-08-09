/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.shader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.shader.IShader;
import mpp.venusfr.utils.shader.Shaders;
import mpp.venusfr.utils.shader.exception.UndefinedShader;
import net.minecraft.client.MainWindow;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderUtil
implements IMinecraft {
    private final int programID = ARBShaderObjects.glCreateProgramObjectARB();
    public static ShaderUtil textShader = new ShaderUtil("textShader");
    public static ShaderUtil rounded = new ShaderUtil("rounded");
    public static ShaderUtil roundedout = new ShaderUtil("roundedout");
    public static ShaderUtil smooth = new ShaderUtil("smooth");
    public static ShaderUtil white = new ShaderUtil("white");
    public static ShaderUtil alpha = new ShaderUtil("alpha");
    public static ShaderUtil kawaseUp = new ShaderUtil("kawaseUp");
    public static ShaderUtil kawaseDown = new ShaderUtil("kawaseDown");
    public static ShaderUtil outline = new ShaderUtil("outline");
    public static ShaderUtil contrast = new ShaderUtil("contrast");
    public static ShaderUtil mask = new ShaderUtil("mask");

    public ShaderUtil(String string) {
        try {
            int n = switch (string) {
                case "textShader" -> this.createShader(Shaders.getInstance().getFont(), 35632);
                case "smooth" -> this.createShader(Shaders.getInstance().getSmooth(), 35632);
                case "white" -> this.createShader(Shaders.getInstance().getWhite(), 35632);
                case "rounded" -> this.createShader(Shaders.getInstance().getRounded(), 35632);
                case "roundedout" -> this.createShader(Shaders.getInstance().getRoundedout(), 35632);
                case "bloom" -> this.createShader(Shaders.getInstance().getGaussianbloom(), 35632);
                case "kawaseUp" -> this.createShader(Shaders.getInstance().getKawaseUp(), 35632);
                case "kawaseDown" -> this.createShader(Shaders.getInstance().getKawaseDown(), 35632);
                case "alpha" -> this.createShader(Shaders.getInstance().getAlpha(), 35632);
                case "outline" -> this.createShader(Shaders.getInstance().getOutline(), 35632);
                case "contrast" -> this.createShader(Shaders.getInstance().getContrast(), 35632);
                case "mask" -> this.createShader(Shaders.getInstance().getMask(), 35632);
                default -> throw new UndefinedShader(string);
            };
            ARBShaderObjects.glAttachObjectARB(this.programID, n);
            ARBShaderObjects.glAttachObjectARB(this.programID, this.createShader(Shaders.getInstance().getVertex(), 35633));
            ARBShaderObjects.glLinkProgramARB(this.programID);
        } catch (UndefinedShader undefinedShader) {
            undefinedShader.fillInStackTrace();
            System.out.println("\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u0440\u0438 \u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0435: " + string);
        }
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        return ShaderUtil.createFrameBuffer(framebuffer, false);
    }

    public static boolean needsNewFramebuffer(Framebuffer framebuffer) {
        return framebuffer == null || framebuffer.framebufferWidth != mc.getMainWindow().getWidth() || framebuffer.framebufferHeight != mc.getMainWindow().getHeight();
    }

    public int getUniform(String string) {
        return ARBShaderObjects.glGetUniformLocationARB(this.programID, string);
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer, boolean bl) {
        if (ShaderUtil.needsNewFramebuffer(framebuffer)) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(mc.getMainWindow().getWidth(), mc.getMainWindow().getHeight(), bl, false);
        }
        return framebuffer;
    }

    public static void drawQuads(float f, float f2, float f3, float f4) {
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(f, f2);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(f, f2 + f4);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(f + f3, f2 + f4);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(f + f3, f2);
        GL11.glEnd();
    }

    public static void drawQuads() {
        MainWindow mainWindow = mc.getMainWindow();
        float f = mainWindow.getScaledWidth();
        float f2 = mainWindow.getScaledHeight();
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(0.0f, f2);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(f, f2);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(f, 0.0f);
        GL11.glEnd();
    }

    public Framebuffer setupBuffer(Framebuffer framebuffer) {
        if (framebuffer.framebufferWidth != mc.getMainWindow().getWidth() || framebuffer.framebufferHeight != mc.getMainWindow().getHeight()) {
            framebuffer.resize(Math.max(1, mc.getMainWindow().getWidth()), Math.max(1, mc.getMainWindow().getHeight()), true);
        } else {
            framebuffer.framebufferClear(true);
        }
        framebuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        return framebuffer;
    }

    public void attach() {
        ARBShaderObjects.glUseProgramObjectARB(this.programID);
    }

    public void detach() {
        GL20.glUseProgram(0);
    }

    public void setUniform(String string, float ... fArray) {
        int n = ARBShaderObjects.glGetUniformLocationARB(this.programID, string);
        switch (fArray.length) {
            case 1: {
                ARBShaderObjects.glUniform1fARB(n, fArray[0]);
                break;
            }
            case 2: {
                ARBShaderObjects.glUniform2fARB(n, fArray[0], fArray[1]);
                break;
            }
            case 3: {
                ARBShaderObjects.glUniform3fARB(n, fArray[0], fArray[1], fArray[2]);
                break;
            }
            case 4: {
                ARBShaderObjects.glUniform4fARB(n, fArray[0], fArray[1], fArray[2], fArray[3]);
                break;
            }
            default: {
                throw new IllegalArgumentException("\u041d\u0435\u0434\u043e\u043f\u0443\u0441\u0442\u0438\u043c\u043e\u0435 \u043a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u0430\u0440\u0433\u0443\u043c\u0435\u043d\u0442\u043e\u0432 \u0434\u043b\u044f uniform '" + string + "'");
            }
        }
    }

    public void setUniform(String string, int ... nArray) {
        int n = ARBShaderObjects.glGetUniformLocationARB(this.programID, string);
        switch (nArray.length) {
            case 1: {
                ARBShaderObjects.glUniform1iARB(n, nArray[0]);
                break;
            }
            case 2: {
                ARBShaderObjects.glUniform2iARB(n, nArray[0], nArray[1]);
                break;
            }
            case 3: {
                ARBShaderObjects.glUniform3iARB(n, nArray[0], nArray[1], nArray[2]);
                break;
            }
            case 4: {
                ARBShaderObjects.glUniform4iARB(n, nArray[0], nArray[1], nArray[2], nArray[3]);
                break;
            }
            default: {
                throw new IllegalArgumentException("\u041d\u0435\u0434\u043e\u043f\u0443\u0441\u0442\u0438\u043c\u043e\u0435 \u043a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u0430\u0440\u0433\u0443\u043c\u0435\u043d\u0442\u043e\u0432 \u0434\u043b\u044f uniform '" + string + "'");
            }
        }
    }

    public void setUniformf(String string, float ... fArray) {
        int n = ARBShaderObjects.glGetUniformLocationARB(this.programID, string);
        switch (fArray.length) {
            case 1: {
                ARBShaderObjects.glUniform1fARB(n, fArray[0]);
                break;
            }
            case 2: {
                ARBShaderObjects.glUniform2fARB(n, fArray[0], fArray[1]);
                break;
            }
            case 3: {
                ARBShaderObjects.glUniform3fARB(n, fArray[0], fArray[1], fArray[2]);
                break;
            }
            case 4: {
                ARBShaderObjects.glUniform4fARB(n, fArray[0], fArray[1], fArray[2], fArray[3]);
            }
        }
    }

    public void setUniformf(String string, double ... dArray) {
        int n = ARBShaderObjects.glGetUniformLocationARB(this.programID, string);
        switch (dArray.length) {
            case 1: {
                ARBShaderObjects.glUniform1fARB(n, (float)dArray[0]);
                break;
            }
            case 2: {
                ARBShaderObjects.glUniform2fARB(n, (float)dArray[0], (float)dArray[1]);
                break;
            }
            case 3: {
                ARBShaderObjects.glUniform3fARB(n, (float)dArray[0], (float)dArray[1], (float)dArray[2]);
                break;
            }
            case 4: {
                ARBShaderObjects.glUniform4fARB(n, (float)dArray[0], (float)dArray[1], (float)dArray[2], (float)dArray[3]);
            }
        }
    }

    private int createShader(IShader iShader, int n) {
        int n2 = ARBShaderObjects.glCreateShaderObjectARB(n);
        ARBShaderObjects.glShaderSourceARB(n2, (CharSequence)this.readInputStream(new ByteArrayInputStream(iShader.glsl().getBytes())));
        ARBShaderObjects.glCompileShaderARB(n2);
        if (GL20.glGetShaderi(n2, 35713) == 0) {
            System.out.println(GL20.glGetShaderInfoLog(n2, 4096));
            throw new IllegalStateException(String.format("Shader (%s) failed to compile!", n));
        }
        return n2;
    }

    public String readInputStream(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream)).lines().map(ShaderUtil::lambda$readInputStream$0).collect(Collectors.joining());
    }

    public static void setupRoundedRectUniforms(float f, float f2, float f3, float f4, float f5, ShaderUtil shaderUtil) {
        shaderUtil.setUniform("location", f * 2.0f, (float)window.getHeight() - f4 * 2.0f - f2 * 2.0f);
        shaderUtil.setUniform("rectSize", f3 * 2.0f, f4 * 2.0f);
        shaderUtil.setUniform("radius", f5 * 2.0f);
    }

    private static String lambda$readInputStream$0(String string) {
        return string + "\n";
    }
}

