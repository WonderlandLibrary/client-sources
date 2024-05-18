package net.minecraft.client.shader;

import org.lwjgl.util.vector.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import java.io.*;
import net.minecraft.client.util.*;

public class Shader
{
    public final Framebuffer framebufferIn;
    public final Framebuffer framebufferOut;
    private final List<Integer> listAuxHeights;
    private static final String[] I;
    private Matrix4f projectionMatrix;
    private final List<Integer> listAuxWidths;
    private final ShaderManager manager;
    private final List<Object> listAuxFramebuffers;
    private final List<String> listAuxNames;
    
    private void preLoadShader() {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableAlpha();
        GlStateManager.disableFog();
        GlStateManager.disableLighting();
        GlStateManager.disableColorMaterial();
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture("".length());
    }
    
    public void addAuxFramebuffer(final String s, final Object o, final int n, final int n2) {
        this.listAuxNames.add(this.listAuxNames.size(), s);
        this.listAuxFramebuffers.add(this.listAuxFramebuffers.size(), o);
        this.listAuxWidths.add(this.listAuxWidths.size(), n);
        this.listAuxHeights.add(this.listAuxHeights.size(), n2);
    }
    
    public void deleteShader() {
        this.manager.deleteShader();
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x68 ^ 0x6F])["".length()] = I("/;\u000e\b\r\u00187;\u000f\u0015\u001b>\r\u001c", "kRhnx");
        Shader.I[" ".length()] = I("#6\u0012\u0014\u0018\u0018&", "bCjGq");
        Shader.I["  ".length()] = I("\n+\b\u001f';-", "ZYguj");
        Shader.I["   ".length()] = I(";>\u001f\u0007?\u0017", "rPLnE");
        Shader.I[0x39 ^ 0x3D] = I("\u0001<98\u000e4,", "NIMkg");
        Shader.I[0x15 ^ 0x10] = I("\u0006> \u0014", "RWMqt");
        Shader.I[0xB2 ^ 0xB4] = I("\u0010\u0014=\u0011?-$&\u000e?", "CwOtZ");
    }
    
    public ShaderManager getShaderManager() {
        return this.manager;
    }
    
    public void setProjectionMatrix(final Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
    
    public void loadShader(final float n) {
        this.preLoadShader();
        this.framebufferIn.unbindFramebuffer();
        final float n2 = this.framebufferOut.framebufferTextureWidth;
        final float n3 = this.framebufferOut.framebufferTextureHeight;
        GlStateManager.viewport("".length(), "".length(), (int)n2, (int)n3);
        this.manager.addSamplerTexture(Shader.I["".length()], this.framebufferIn);
        int i = "".length();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (i < this.listAuxFramebuffers.size()) {
            this.manager.addSamplerTexture(this.listAuxNames.get(i), this.listAuxFramebuffers.get(i));
            this.manager.getShaderUniformOrDefault(Shader.I[" ".length()] + i).set(this.listAuxWidths.get(i), this.listAuxHeights.get(i));
            ++i;
        }
        this.manager.getShaderUniformOrDefault(Shader.I["  ".length()]).set(this.projectionMatrix);
        this.manager.getShaderUniformOrDefault(Shader.I["   ".length()]).set(this.framebufferIn.framebufferTextureWidth, this.framebufferIn.framebufferTextureHeight);
        this.manager.getShaderUniformOrDefault(Shader.I[0x96 ^ 0x92]).set(n2, n3);
        this.manager.getShaderUniformOrDefault(Shader.I[0x64 ^ 0x61]).set(n);
        final Minecraft minecraft = Minecraft.getMinecraft();
        this.manager.getShaderUniformOrDefault(Shader.I[0xA7 ^ 0xA1]).set(minecraft.displayWidth, minecraft.displayHeight);
        this.manager.useShader();
        this.framebufferOut.framebufferClear();
        this.framebufferOut.bindFramebuffer("".length() != 0);
        GlStateManager.depthMask("".length() != 0);
        GlStateManager.colorMask(" ".length() != 0, " ".length() != 0, " ".length() != 0, " ".length() != 0);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0xA6 ^ 0xA1, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(0.0, n3, 500.0).color(131 + 5 + 98 + 21, 163 + 150 - 79 + 21, 167 + 210 - 180 + 58, 78 + 139 - 193 + 231).endVertex();
        worldRenderer.pos(n2, n3, 500.0).color(111 + 2 - 55 + 197, 120 + 85 - 161 + 211, 107 + 19 + 100 + 29, 184 + 248 - 218 + 41).endVertex();
        worldRenderer.pos(n2, 0.0, 500.0).color(163 + 36 - 158 + 214, 241 + 207 - 397 + 204, 182 + 153 - 333 + 253, 164 + 135 - 96 + 52).endVertex();
        worldRenderer.pos(0.0, 0.0, 500.0).color(175 + 196 - 320 + 204, 224 + 58 - 199 + 172, 142 + 14 + 24 + 75, 39 + 10 + 130 + 76).endVertex();
        instance.draw();
        GlStateManager.depthMask(" ".length() != 0);
        GlStateManager.colorMask(" ".length() != 0, " ".length() != 0, " ".length() != 0, " ".length() != 0);
        this.manager.endShader();
        this.framebufferOut.unbindFramebuffer();
        this.framebufferIn.unbindFramebufferTexture();
        final Iterator<Framebuffer> iterator = this.listAuxFramebuffers.iterator();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Framebuffer next = iterator.next();
            if (next instanceof Framebuffer) {
                next.unbindFramebufferTexture();
            }
        }
    }
    
    public Shader(final IResourceManager resourceManager, final String s, final Framebuffer framebufferIn, final Framebuffer framebufferOut) throws IOException, JsonException {
        this.listAuxFramebuffers = (List<Object>)Lists.newArrayList();
        this.listAuxNames = (List<String>)Lists.newArrayList();
        this.listAuxWidths = (List<Integer>)Lists.newArrayList();
        this.listAuxHeights = (List<Integer>)Lists.newArrayList();
        this.manager = new ShaderManager(resourceManager, s);
        this.framebufferIn = framebufferIn;
        this.framebufferOut = framebufferOut;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
