/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.shader.FramebufferConstants;
import net.minecraft.client.util.LWJGLMemoryUntracker;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.render.GlCullState;
import net.optifine.shaders.Shaders;
import net.optifine.util.LockCounter;
import org.lwjgl.opengl.ARBCopyBuffer;
import org.lwjgl.opengl.ARBDrawBuffersBlend;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.EXTFramebufferBlit;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryUtil;

public class GlStateManager {
    private static final FloatBuffer MATRIX_BUFFER = GLX.make(MemoryUtil.memAllocFloat(16), GlStateManager::lambda$static$0);
    private static final AlphaState ALPHA_TEST = new AlphaState();
    private static final BooleanState LIGHTING = new BooleanState(2896);
    private static final BooleanState[] LIGHT_ENABLE = (BooleanState[])IntStream.range(0, 8).mapToObj(GlStateManager::lambda$static$1).toArray(GlStateManager::lambda$static$2);
    private static final ColorMaterialState COLOR_MATERIAL = new ColorMaterialState();
    private static final BlendState BLEND = new BlendState();
    private static final DepthState DEPTH = new DepthState();
    private static final FogState FOG = new FogState();
    private static final CullState CULL = new CullState();
    private static final PolygonOffsetState POLY_OFFSET = new PolygonOffsetState();
    private static final ColorLogicState COLOR_LOGIC = new ColorLogicState();
    private static final TexGenState TEX_GEN = new TexGenState();
    private static final StencilState STENCIL = new StencilState();
    private static final ScissorState field_244591_n = new ScissorState();
    private static final FloatBuffer FLOAT_4_BUFFER = GLAllocation.createDirectFloatBuffer(4);
    private static int activeTexture;
    private static final TextureState[] TEXTURES;
    private static int shadeModel;
    private static final BooleanState RESCALE_NORMAL;
    private static final ColorMask COLOR_MASK;
    private static final Color COLOR;
    private static FramebufferExtension fboMode;
    private static SupportType supportType;
    private static LockCounter alphaLock;
    private static GlAlphaState alphaLockState;
    private static LockCounter blendLock;
    private static GlBlendState blendLockState;
    private static LockCounter cullLock;
    private static GlCullState cullLockState;
    private static boolean clientStateLocked;
    private static int clientActiveTexture;
    private static boolean creatingDisplayList;
    public static float lastBrightnessX;
    public static float lastBrightnessY;
    public static boolean openGL31;
    public static boolean vboRegions;
    public static int GL_COPY_READ_BUFFER;
    public static int GL_COPY_WRITE_BUFFER;
    public static int GL_ARRAY_BUFFER;
    public static int GL_STATIC_DRAW;
    private static boolean fogAllowed;
    public static final int GL_QUADS = 7;
    public static final int GL_TRIANGLES = 4;
    public static final int GL_TEXTURE0 = 33984;
    public static final int GL_TEXTURE1 = 33985;
    public static final int GL_TEXTURE2 = 33986;
    private static int framebufferRead;
    private static int framebufferDraw;
    private static final int[] IMAGE_TEXTURES;

    @Deprecated
    public static void pushLightingAttributes() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glPushAttrib(8256);
    }

    @Deprecated
    public static void pushTextureAttributes() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glPushAttrib(270336);
    }

    @Deprecated
    public static void popAttributes() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glPopAttrib();
    }

    @Deprecated
    public static void disableAlphaTest() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (alphaLock.isLocked()) {
            alphaLockState.setDisabled();
        } else {
            GlStateManager.ALPHA_TEST.test.disable();
        }
    }

    @Deprecated
    public static void enableAlphaTest() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (alphaLock.isLocked()) {
            alphaLockState.setEnabled();
        } else {
            GlStateManager.ALPHA_TEST.test.enable();
        }
    }

    @Deprecated
    public static void alphaFunc(int n, float f) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (alphaLock.isLocked()) {
            alphaLockState.setFuncRef(n, f);
        } else if (n != GlStateManager.ALPHA_TEST.func || f != GlStateManager.ALPHA_TEST.ref) {
            GlStateManager.ALPHA_TEST.func = n;
            GlStateManager.ALPHA_TEST.ref = f;
            GL11.glAlphaFunc(n, f);
        }
    }

    @Deprecated
    public static void enableLighting() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        LIGHTING.enable();
    }

    @Deprecated
    public static void disableLighting() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        LIGHTING.disable();
    }

    @Deprecated
    public static void enableLight(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        LIGHT_ENABLE[n].enable();
    }

    @Deprecated
    public static void enableColorMaterial() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.COLOR_MATERIAL.colorMaterial.enable();
    }

    @Deprecated
    public static void disableColorMaterial() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.COLOR_MATERIAL.colorMaterial.disable();
    }

    @Deprecated
    public static void colorMaterial(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (n != GlStateManager.COLOR_MATERIAL.face || n2 != GlStateManager.COLOR_MATERIAL.mode) {
            GlStateManager.COLOR_MATERIAL.face = n;
            GlStateManager.COLOR_MATERIAL.mode = n2;
            GL11.glColorMaterial(n, n2);
        }
    }

    @Deprecated
    public static void light(int n, int n2, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glLightfv(n, n2, floatBuffer);
    }

    @Deprecated
    public static void lightModel(int n, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glLightModelfv(n, floatBuffer);
    }

    @Deprecated
    public static void normal3f(float f, float f2, float f3) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glNormal3f(f, f2, f3);
    }

    public static void func_244593_j() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GlStateManager.field_244591_n.field_244595_a.disable();
    }

    public static void func_244594_k() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GlStateManager.field_244591_n.field_244595_a.enable();
    }

    public static void func_244592_a(int n, int n2, int n3, int n4) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL20.glScissor(n, n2, n3, n4);
    }

    public static void disableDepthTest() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GlStateManager.DEPTH.test.disable();
    }

    public static void enableDepthTest() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GlStateManager.DEPTH.test.enable();
    }

    public static void depthFunc(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (n != GlStateManager.DEPTH.func) {
            GlStateManager.DEPTH.func = n;
            GL11.glDepthFunc(n);
        }
    }

    public static void depthMask(boolean bl) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (bl != GlStateManager.DEPTH.mask) {
            GlStateManager.DEPTH.mask = bl;
            GL11.glDepthMask(bl);
        }
    }

    public static void disableBlend() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (blendLock.isLocked()) {
            blendLockState.setDisabled();
        } else {
            GlStateManager.BLEND.blend.disable();
        }
    }

    public static void enableBlend() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (blendLock.isLocked()) {
            blendLockState.setEnabled();
        } else {
            GlStateManager.BLEND.blend.enable();
        }
    }

    public static void blendFunc(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (blendLock.isLocked()) {
            blendLockState.setFactors(n, n2);
        } else if (n != GlStateManager.BLEND.srcFactorRgb || n2 != GlStateManager.BLEND.dstFactorRgb || n != GlStateManager.BLEND.srcFactorAlpha || n2 != GlStateManager.BLEND.dstFactorAlpha) {
            GlStateManager.BLEND.srcFactorRgb = n;
            GlStateManager.BLEND.dstFactorRgb = n2;
            GlStateManager.BLEND.srcFactorAlpha = n;
            GlStateManager.BLEND.dstFactorAlpha = n2;
            if (Config.isShaders()) {
                Shaders.uniform_blendFunc.setValue(n, n2, n, n2);
            }
            GL11.glBlendFunc(n, n2);
        }
    }

    public static void blendFuncSeparate(int n, int n2, int n3, int n4) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (blendLock.isLocked()) {
            blendLockState.setFactors(n, n2, n3, n4);
        } else if (n != GlStateManager.BLEND.srcFactorRgb || n2 != GlStateManager.BLEND.dstFactorRgb || n3 != GlStateManager.BLEND.srcFactorAlpha || n4 != GlStateManager.BLEND.dstFactorAlpha) {
            GlStateManager.BLEND.srcFactorRgb = n;
            GlStateManager.BLEND.dstFactorRgb = n2;
            GlStateManager.BLEND.srcFactorAlpha = n3;
            GlStateManager.BLEND.dstFactorAlpha = n4;
            if (Config.isShaders()) {
                Shaders.uniform_blendFunc.setValue(n, n2, n3, n4);
            }
            GlStateManager.glBlendFuncSeparate(n, n2, n3, n4);
        }
    }

    public static void blendColor(float f, float f2, float f3, float f4) {
        GL14.glBlendColor(f, f2, f3, f4);
    }

    public static void blendEquation(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL14.glBlendEquation(n);
    }

    public static String init(GLCapabilities gLCapabilities) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        Config.initDisplay();
        openGL31 = gLCapabilities.OpenGL31;
        if (openGL31) {
            GL_COPY_READ_BUFFER = 36662;
            GL_COPY_WRITE_BUFFER = 36663;
        } else {
            GL_COPY_READ_BUFFER = 36662;
            GL_COPY_WRITE_BUFFER = 36663;
        }
        if (gLCapabilities.OpenGL15) {
            GL_ARRAY_BUFFER = 34962;
            GL_STATIC_DRAW = 35044;
        } else {
            GL_ARRAY_BUFFER = 34962;
            GL_STATIC_DRAW = 35044;
        }
        boolean bl = openGL31 || gLCapabilities.GL_ARB_copy_buffer;
        boolean bl2 = gLCapabilities.OpenGL14;
        boolean bl3 = vboRegions = bl && bl2;
        if (!vboRegions) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            if (!bl) {
                arrayList.add("OpenGL 1.3, ARB_copy_buffer");
            }
            if (!bl2) {
                arrayList.add("OpenGL 1.4");
            }
            String string = "VboRegions not supported, missing: " + Config.listToString(arrayList);
            Config.dbg(string);
            arrayList.add(string);
        }
        supportType = gLCapabilities.OpenGL30 ? SupportType.BASE : (gLCapabilities.GL_EXT_framebuffer_blit ? SupportType.EXT : SupportType.NONE);
        if (gLCapabilities.OpenGL30) {
            fboMode = FramebufferExtension.BASE;
            FramebufferConstants.GL_FRAMEBUFFER = 36160;
            FramebufferConstants.GL_RENDERBUFFER = 36161;
            FramebufferConstants.GL_COLOR_ATTACHMENT0 = 36064;
            FramebufferConstants.GL_DEPTH_ATTACHMENT = 36096;
            FramebufferConstants.GL_FRAMEBUFFER_COMPLETE = 36053;
            FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 36054;
            FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 36055;
            FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 36059;
            FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 36060;
            return "OpenGL 3.0";
        }
        if (gLCapabilities.GL_ARB_framebuffer_object) {
            fboMode = FramebufferExtension.ARB;
            FramebufferConstants.GL_FRAMEBUFFER = 36160;
            FramebufferConstants.GL_RENDERBUFFER = 36161;
            FramebufferConstants.GL_COLOR_ATTACHMENT0 = 36064;
            FramebufferConstants.GL_DEPTH_ATTACHMENT = 36096;
            FramebufferConstants.GL_FRAMEBUFFER_COMPLETE = 36053;
            FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 36055;
            FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 36054;
            FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 36059;
            FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 36060;
            return "ARB_framebuffer_object extension";
        }
        if (gLCapabilities.GL_EXT_framebuffer_object) {
            fboMode = FramebufferExtension.EXT;
            FramebufferConstants.GL_FRAMEBUFFER = 36160;
            FramebufferConstants.GL_RENDERBUFFER = 36161;
            FramebufferConstants.GL_COLOR_ATTACHMENT0 = 36064;
            FramebufferConstants.GL_DEPTH_ATTACHMENT = 36096;
            FramebufferConstants.GL_FRAMEBUFFER_COMPLETE = 36053;
            FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 36055;
            FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 36054;
            FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 36059;
            FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 36060;
            return "EXT_framebuffer_object extension";
        }
        throw new IllegalStateException("Could not initialize framebuffer support.");
    }

    public static int getProgram(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GL20.glGetProgrami(n, n2);
    }

    public static void attachShader(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glAttachShader(n, n2);
    }

    public static void deleteShader(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glDeleteShader(n);
    }

    public static int createShader(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GL20.glCreateShader(n);
    }

    public static void shaderSource(int n, CharSequence charSequence) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glShaderSource(n, charSequence);
    }

    public static void compileShader(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glCompileShader(n);
    }

    public static int getShader(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GL20.glGetShaderi(n, n2);
    }

    public static void useProgram(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUseProgram(n);
    }

    public static int createProgram() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GL20.glCreateProgram();
    }

    public static void deleteProgram(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glDeleteProgram(n);
    }

    public static void linkProgram(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glLinkProgram(n);
    }

    public static int getUniformLocation(int n, CharSequence charSequence) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GL20.glGetUniformLocation(n, charSequence);
    }

    public static void uniform1i(int n, IntBuffer intBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUniform1iv(n, intBuffer);
    }

    public static void uniform1i(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUniform1i(n, n2);
    }

    public static void uniform1f(int n, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUniform1fv(n, floatBuffer);
    }

    public static void uniform2i(int n, IntBuffer intBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUniform2iv(n, intBuffer);
    }

    public static void uniform2f(int n, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUniform2fv(n, floatBuffer);
    }

    public static void uniform3i(int n, IntBuffer intBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUniform3iv(n, intBuffer);
    }

    public static void uniform3f(int n, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUniform3fv(n, floatBuffer);
    }

    public static void uniform4i(int n, IntBuffer intBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUniform4iv(n, intBuffer);
    }

    public static void uniform4f(int n, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUniform4fv(n, floatBuffer);
    }

    public static void uniformMatrix2f(int n, boolean bl, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUniformMatrix2fv(n, bl, floatBuffer);
    }

    public static void uniformMatrix3f(int n, boolean bl, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUniformMatrix3fv(n, bl, floatBuffer);
    }

    public static void uniformMatrix4f(int n, boolean bl, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glUniformMatrix4fv(n, bl, floatBuffer);
    }

    public static int getAttribLocation(int n, CharSequence charSequence) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GL20.glGetAttribLocation(n, charSequence);
    }

    public static int genBuffers() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        return GL15.glGenBuffers();
    }

    public static void bindBuffer(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL15.glBindBuffer(n, n2);
    }

    public static void bufferData(int n, ByteBuffer byteBuffer, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL15.glBufferData(n, byteBuffer, n2);
    }

    public static void deleteBuffers(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL15.glDeleteBuffers(n);
    }

    public static void copySubImage(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL20.glCopyTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8);
    }

    public static void bindFramebuffer(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (n == 36160) {
            if (framebufferRead == n2 && framebufferDraw == n2) {
                return;
            }
            framebufferRead = n2;
            framebufferDraw = n2;
        } else if (n == 36008) {
            if (framebufferRead == n2) {
                return;
            }
            framebufferRead = n2;
        }
        if (n == 36009) {
            if (framebufferDraw == n2) {
                return;
            }
            framebufferDraw = n2;
        }
        switch (fboMode) {
            case BASE: {
                GL30.glBindFramebuffer(n, n2);
                break;
            }
            case ARB: {
                ARBFramebufferObject.glBindFramebuffer(n, n2);
                break;
            }
            case EXT: {
                EXTFramebufferObject.glBindFramebufferEXT(n, n2);
            }
        }
    }

    public static int getFrameBufferAttachmentParam() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        switch (fboMode) {
            case BASE: {
                if (GL30.glGetFramebufferAttachmentParameteri(36160, 36096, 36048) != 5890) break;
                return GL30.glGetFramebufferAttachmentParameteri(36160, 36096, 36049);
            }
            case ARB: {
                if (ARBFramebufferObject.glGetFramebufferAttachmentParameteri(36160, 36096, 36048) != 5890) break;
                return ARBFramebufferObject.glGetFramebufferAttachmentParameteri(36160, 36096, 36049);
            }
            case EXT: {
                if (EXTFramebufferObject.glGetFramebufferAttachmentParameteriEXT(36160, 36096, 36048) != 5890) break;
                return EXTFramebufferObject.glGetFramebufferAttachmentParameteriEXT(36160, 36096, 36049);
            }
        }
        return 1;
    }

    public static void blitFramebuffer(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        switch (supportType) {
            case BASE: {
                GL30.glBlitFramebuffer(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
                break;
            }
            case EXT: {
                EXTFramebufferBlit.glBlitFramebufferEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
            }
        }
    }

    public static void deleteFramebuffers(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        switch (fboMode) {
            case BASE: {
                GL30.glDeleteFramebuffers(n);
                break;
            }
            case ARB: {
                ARBFramebufferObject.glDeleteFramebuffers(n);
                break;
            }
            case EXT: {
                EXTFramebufferObject.glDeleteFramebuffersEXT(n);
            }
        }
    }

    public static int genFramebuffers() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        switch (fboMode) {
            case BASE: {
                return GL30.glGenFramebuffers();
            }
            case ARB: {
                return ARBFramebufferObject.glGenFramebuffers();
            }
            case EXT: {
                return EXTFramebufferObject.glGenFramebuffersEXT();
            }
        }
        return 1;
    }

    public static int checkFramebufferStatus(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        switch (fboMode) {
            case BASE: {
                return GL30.glCheckFramebufferStatus(n);
            }
            case ARB: {
                return ARBFramebufferObject.glCheckFramebufferStatus(n);
            }
            case EXT: {
                return EXTFramebufferObject.glCheckFramebufferStatusEXT(n);
            }
        }
        return 1;
    }

    public static void framebufferTexture2D(int n, int n2, int n3, int n4, int n5) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        switch (fboMode) {
            case BASE: {
                GL30.glFramebufferTexture2D(n, n2, n3, n4, n5);
                break;
            }
            case ARB: {
                ARBFramebufferObject.glFramebufferTexture2D(n, n2, n3, n4, n5);
                break;
            }
            case EXT: {
                EXTFramebufferObject.glFramebufferTexture2DEXT(n, n2, n3, n4, n5);
            }
        }
    }

    @Deprecated
    public static int getActiveTextureId() {
        return GlStateManager.TEXTURES[GlStateManager.activeTexture].textureName;
    }

    public static void glActiveTexture(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL13.glActiveTexture(n);
    }

    @Deprecated
    public static void clientActiveTexture(int n) {
        if (n != clientActiveTexture) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThread);
            GL13.glClientActiveTexture(n);
            clientActiveTexture = n;
        }
    }

    @Deprecated
    public static void multiTexCoord2f(int n, float f, float f2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL13.glMultiTexCoord2f(n, f, f2);
        if (n == 33986) {
            lastBrightnessX = f;
            lastBrightnessY = f2;
        }
    }

    public static void glBlendFuncSeparate(int n, int n2, int n3, int n4) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL14.glBlendFuncSeparate(n, n2, n3, n4);
    }

    public static String getShaderInfoLog(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GL20.glGetShaderInfoLog(n, n2);
    }

    public static String getProgramInfoLog(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GL20.glGetProgramInfoLog(n, n2);
    }

    public static void setupOutline() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.texEnv(8960, 8704, 34160);
        GlStateManager.color(7681, 34168);
    }

    public static void teardownOutline() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.texEnv(8960, 8704, 8448);
        GlStateManager.color(8448, 5890, 34168, 34166);
    }

    public static void setupOverlayColor(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.activeTexture(33985);
        GlStateManager.enableTexture();
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        float f = 1.0f / (float)(n2 - 1);
        GlStateManager.scalef(f, f, f);
        GlStateManager.matrixMode(5888);
        GlStateManager.bindTexture(n);
        GlStateManager.texParameter(3553, 10241, 9728);
        GlStateManager.texParameter(3553, 10240, 9728);
        GlStateManager.texParameter(3553, 10242, 10496);
        GlStateManager.texParameter(3553, 10243, 10496);
        GlStateManager.texEnv(8960, 8704, 34160);
        GlStateManager.color(34165, 34168, 5890, 5890);
        GlStateManager.alpha(7681, 34168);
        GlStateManager.activeTexture(33984);
    }

    public static void teardownOverlayColor() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.activeTexture(33985);
        GlStateManager.disableTexture();
        GlStateManager.activeTexture(33984);
    }

    private static void color(int n, int n2) {
        GlStateManager.texEnv(8960, 34161, n);
        GlStateManager.texEnv(8960, 34176, n2);
        GlStateManager.texEnv(8960, 34192, 768);
    }

    private static void color(int n, int n2, int n3, int n4) {
        GlStateManager.texEnv(8960, 34161, n);
        GlStateManager.texEnv(8960, 34176, n2);
        GlStateManager.texEnv(8960, 34192, 768);
        GlStateManager.texEnv(8960, 34177, n3);
        GlStateManager.texEnv(8960, 34193, 768);
        GlStateManager.texEnv(8960, 34178, n4);
        GlStateManager.texEnv(8960, 34194, 770);
    }

    private static void alpha(int n, int n2) {
        GlStateManager.texEnv(8960, 34162, n);
        GlStateManager.texEnv(8960, 34184, n2);
        GlStateManager.texEnv(8960, 34200, 770);
    }

    public static void setupLighting(Vector3f vector3f, Vector3f vector3f2, Matrix4f matrix4f) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.enableLight(0);
        GlStateManager.enableLight(1);
        Vector4f vector4f = new Vector4f(vector3f);
        vector4f.transform(matrix4f);
        GlStateManager.light(16384, 4611, GlStateManager.getBuffer(vector4f.getX(), vector4f.getY(), vector4f.getZ(), 0.0f));
        float f = 0.6f;
        GlStateManager.light(16384, 4609, GlStateManager.getBuffer(0.6f, 0.6f, 0.6f, 1.0f));
        GlStateManager.light(16384, 4608, GlStateManager.getBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GlStateManager.light(16384, 4610, GlStateManager.getBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        Vector4f vector4f2 = new Vector4f(vector3f2);
        vector4f2.transform(matrix4f);
        GlStateManager.light(16385, 4611, GlStateManager.getBuffer(vector4f2.getX(), vector4f2.getY(), vector4f2.getZ(), 0.0f));
        GlStateManager.light(16385, 4609, GlStateManager.getBuffer(0.6f, 0.6f, 0.6f, 1.0f));
        GlStateManager.light(16385, 4608, GlStateManager.getBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GlStateManager.light(16385, 4610, GlStateManager.getBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GlStateManager.shadeModel(7424);
        float f2 = 0.4f;
        GlStateManager.lightModel(2899, GlStateManager.getBuffer(0.4f, 0.4f, 0.4f, 1.0f));
        GlStateManager.popMatrix();
    }

    public static void setupScaledLighting(Vector3f vector3f, Vector3f vector3f2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        matrix4f.mul(Matrix4f.makeScale(1.0f, -1.0f, 1.0f));
        matrix4f.mul(Vector3f.YP.rotationDegrees(-22.5f));
        matrix4f.mul(Vector3f.XP.rotationDegrees(135.0f));
        GlStateManager.setupLighting(vector3f, vector3f2, matrix4f);
    }

    public static void setupGui3DMatrix(Vector3f vector3f, Vector3f vector3f2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        matrix4f.mul(Vector3f.YP.rotationDegrees(62.0f));
        matrix4f.mul(Vector3f.XP.rotationDegrees(185.5f));
        matrix4f.mul(Matrix4f.makeScale(1.0f, -1.0f, 1.0f));
        matrix4f.mul(Vector3f.YP.rotationDegrees(-22.5f));
        matrix4f.mul(Vector3f.XP.rotationDegrees(135.0f));
        GlStateManager.setupLighting(vector3f, vector3f2, matrix4f);
    }

    private static FloatBuffer getBuffer(float f, float f2, float f3, float f4) {
        FLOAT_4_BUFFER.clear();
        FLOAT_4_BUFFER.put(f).put(f2).put(f3).put(f4);
        FLOAT_4_BUFFER.flip();
        return FLOAT_4_BUFFER;
    }

    public static void setupEndPortalTexGen() {
        GlStateManager.texGenMode(TexGen.S, 9216);
        GlStateManager.texGenMode(TexGen.T, 9216);
        GlStateManager.texGenMode(TexGen.R, 9216);
        GlStateManager.texGenParam(TexGen.S, 9474, GlStateManager.getBuffer(1.0f, 0.0f, 0.0f, 0.0f));
        GlStateManager.texGenParam(TexGen.T, 9474, GlStateManager.getBuffer(0.0f, 1.0f, 0.0f, 0.0f));
        GlStateManager.texGenParam(TexGen.R, 9474, GlStateManager.getBuffer(0.0f, 0.0f, 1.0f, 0.0f));
        GlStateManager.enableTexGen(TexGen.S);
        GlStateManager.enableTexGen(TexGen.T);
        GlStateManager.enableTexGen(TexGen.R);
    }

    public static void clearTexGen() {
        GlStateManager.disableTexGen(TexGen.S);
        GlStateManager.disableTexGen(TexGen.T);
        GlStateManager.disableTexGen(TexGen.R);
    }

    public static void mulTextureByProjModelView() {
        GlStateManager.getMatrix(2983, MATRIX_BUFFER);
        GlStateManager.multMatrix(MATRIX_BUFFER);
        GlStateManager.getMatrix(2982, MATRIX_BUFFER);
        GlStateManager.multMatrix(MATRIX_BUFFER);
    }

    @Deprecated
    public static void enableFog() {
        if (fogAllowed) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThread);
            GlStateManager.FOG.fog.enable();
        }
    }

    @Deprecated
    public static void disableFog() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.FOG.fog.disable();
    }

    @Deprecated
    public static void fogMode(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (n != GlStateManager.FOG.mode) {
            GlStateManager.FOG.mode = n;
            GlStateManager.fogi(2917, n);
            if (Config.isShaders()) {
                Shaders.setFogMode(n);
            }
        }
    }

    @Deprecated
    public static void fogDensity(float f) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f != GlStateManager.FOG.density) {
            GlStateManager.FOG.density = f;
            GL11.glFogf(2914, f);
            if (Config.isShaders()) {
                Shaders.setFogDensity(f);
            }
        }
    }

    @Deprecated
    public static void fogStart(float f) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (f != GlStateManager.FOG.start) {
            GlStateManager.FOG.start = f;
            GL11.glFogf(2915, f);
        }
    }

    @Deprecated
    public static void fogEnd(float f) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (f != GlStateManager.FOG.end) {
            GlStateManager.FOG.end = f;
            GL11.glFogf(2916, f);
        }
    }

    @Deprecated
    public static void fog(int n, float[] fArray) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glFogfv(n, fArray);
    }

    @Deprecated
    public static void fogi(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glFogi(n, n2);
    }

    public static void enableCull() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (cullLock.isLocked()) {
            cullLockState.setEnabled();
        } else {
            GlStateManager.CULL.cullFace.enable();
        }
    }

    public static void disableCull() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (cullLock.isLocked()) {
            cullLockState.setDisabled();
        } else {
            GlStateManager.CULL.cullFace.disable();
        }
    }

    public static void polygonMode(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glPolygonMode(n, n2);
    }

    public static void enablePolygonOffset() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.POLY_OFFSET.polyOffset.enable();
    }

    public static void disablePolygonOffset() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.POLY_OFFSET.polyOffset.disable();
    }

    public static void enableLineOffset() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.POLY_OFFSET.lineOffset.enable();
    }

    public static void disableLineOffset() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.POLY_OFFSET.lineOffset.disable();
    }

    public static void polygonOffset(float f, float f2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (f != GlStateManager.POLY_OFFSET.factor || f2 != GlStateManager.POLY_OFFSET.units) {
            GlStateManager.POLY_OFFSET.factor = f;
            GlStateManager.POLY_OFFSET.units = f2;
            GL11.glPolygonOffset(f, f2);
        }
    }

    public static void enableColorLogicOp() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.COLOR_LOGIC.colorLogicOp.enable();
    }

    public static void disableColorLogicOp() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.COLOR_LOGIC.colorLogicOp.disable();
    }

    public static void logicOp(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (n != GlStateManager.COLOR_LOGIC.logicOpcode) {
            GlStateManager.COLOR_LOGIC.logicOpcode = n;
            GL11.glLogicOp(n);
        }
    }

    @Deprecated
    public static void enableTexGen(TexGen texGen) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.getTexGen((TexGen)texGen).textureGen.enable();
    }

    @Deprecated
    public static void disableTexGen(TexGen texGen) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.getTexGen((TexGen)texGen).textureGen.disable();
    }

    @Deprecated
    public static void texGenMode(TexGen texGen, int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        TexGenCoord texGenCoord = GlStateManager.getTexGen(texGen);
        if (n != texGenCoord.mode) {
            texGenCoord.mode = n;
            GL11.glTexGeni(texGenCoord.coord, 9472, n);
        }
    }

    @Deprecated
    public static void texGenParam(TexGen texGen, int n, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glTexGenfv(GlStateManager.getTexGen((TexGen)texGen).coord, n, floatBuffer);
    }

    @Deprecated
    private static TexGenCoord getTexGen(TexGen texGen) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        switch (texGen) {
            case S: {
                return GlStateManager.TEX_GEN.s;
            }
            case T: {
                return GlStateManager.TEX_GEN.t;
            }
            case R: {
                return GlStateManager.TEX_GEN.r;
            }
            case Q: {
                return GlStateManager.TEX_GEN.q;
            }
        }
        return GlStateManager.TEX_GEN.s;
    }

    public static void activeTexture(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (activeTexture != n - 33984) {
            activeTexture = n - 33984;
            GlStateManager.glActiveTexture(n);
        }
    }

    public static void enableTexture() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GlStateManager.TEXTURES[GlStateManager.activeTexture].texture2DState.enable();
    }

    public static void disableTexture() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.TEXTURES[GlStateManager.activeTexture].texture2DState.disable();
    }

    @Deprecated
    public static void texEnv(int n, int n2, int n3) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glTexEnvi(n, n2, n3);
    }

    public static void texParameter(int n, int n2, float f) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL11.glTexParameterf(n, n2, f);
    }

    public static void texParameter(int n, int n2, int n3) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL11.glTexParameteri(n, n2, n3);
    }

    public static int getTexLevelParameter(int n, int n2, int n3) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        return GL11.glGetTexLevelParameteri(n, n2, n3);
    }

    public static int genTexture() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        return GL11.glGenTextures();
    }

    public static void genTextures(int[] nArray) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL11.glGenTextures(nArray);
    }

    public static void deleteTexture(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (n != 0) {
            for (int i = 0; i < IMAGE_TEXTURES.length; ++i) {
                if (IMAGE_TEXTURES[i] != n) continue;
                GlStateManager.IMAGE_TEXTURES[i] = 0;
            }
            GL11.glDeleteTextures(n);
            for (TextureState textureState : TEXTURES) {
                if (textureState.textureName != n) continue;
                textureState.textureName = 0;
            }
        }
    }

    public static void deleteTextures(int[] nArray) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        for (TextureState textureState : TEXTURES) {
            for (int n : nArray) {
                if (textureState.textureName != n) continue;
                textureState.textureName = -1;
            }
        }
        GL11.glDeleteTextures(nArray);
    }

    public static void bindTexture(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (n != GlStateManager.TEXTURES[GlStateManager.activeTexture].textureName) {
            GlStateManager.TEXTURES[GlStateManager.activeTexture].textureName = n;
            GL11.glBindTexture(3553, n);
            if (SmartAnimations.isActive()) {
                SmartAnimations.textureRendered(n);
            }
        }
    }

    public static void texImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, @Nullable IntBuffer intBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL11.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, intBuffer);
    }

    public static void texSubImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, long l) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL11.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void getTexImage(int n, int n2, int n3, int n4, long l) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glGetTexImage(n, n2, n3, n4, l);
    }

    @Deprecated
    public static void shadeModel(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (n != shadeModel) {
            shadeModel = n;
            GL11.glShadeModel(n);
        }
    }

    @Deprecated
    public static void enableRescaleNormal() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        RESCALE_NORMAL.enable();
    }

    @Deprecated
    public static void disableRescaleNormal() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        RESCALE_NORMAL.disable();
    }

    public static void viewport(int n, int n2, int n3, int n4) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        Viewport.INSTANCE.x = n;
        Viewport.INSTANCE.y = n2;
        Viewport.INSTANCE.w = n3;
        Viewport.INSTANCE.h = n4;
        GL11.glViewport(n, n2, n3, n4);
    }

    public static void colorMask(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (bl != GlStateManager.COLOR_MASK.red || bl2 != GlStateManager.COLOR_MASK.green || bl3 != GlStateManager.COLOR_MASK.blue || bl4 != GlStateManager.COLOR_MASK.alpha) {
            GlStateManager.COLOR_MASK.red = bl;
            GlStateManager.COLOR_MASK.green = bl2;
            GlStateManager.COLOR_MASK.blue = bl3;
            GlStateManager.COLOR_MASK.alpha = bl4;
            GL11.glColorMask(bl, bl2, bl3, bl4);
        }
    }

    public static void stencilFunc(int n, int n2, int n3) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (n != GlStateManager.STENCIL.func.func || n != GlStateManager.STENCIL.func.ref || n != GlStateManager.STENCIL.func.mask) {
            GlStateManager.STENCIL.func.func = n;
            GlStateManager.STENCIL.func.ref = n2;
            GlStateManager.STENCIL.func.mask = n3;
            GL11.glStencilFunc(n, n2, n3);
        }
    }

    public static void stencilMask(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (n != GlStateManager.STENCIL.mask) {
            GlStateManager.STENCIL.mask = n;
            GL11.glStencilMask(n);
        }
    }

    public static void stencilOp(int n, int n2, int n3) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (n != GlStateManager.STENCIL.sfail || n2 != GlStateManager.STENCIL.dpfail || n3 != GlStateManager.STENCIL.dppass) {
            GlStateManager.STENCIL.sfail = n;
            GlStateManager.STENCIL.dpfail = n2;
            GlStateManager.STENCIL.dppass = n3;
            GL11.glStencilOp(n, n2, n3);
        }
    }

    public static void clearDepth(double d) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL11.glClearDepth(d);
    }

    public static void clearColor(float f, float f2, float f3, float f4) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL11.glClearColor(f, f2, f3, f4);
    }

    public static void clearStencil(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glClearStencil(n);
    }

    public static void clear(int n, boolean bl) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL11.glClear(n);
        if (bl) {
            GlStateManager.getError();
        }
    }

    @Deprecated
    public static void matrixMode(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL11.glMatrixMode(n);
    }

    @Deprecated
    public static void loadIdentity() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL11.glLoadIdentity();
    }

    @Deprecated
    public static void pushMatrix() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glPushMatrix();
    }

    @Deprecated
    public static void popMatrix() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glPopMatrix();
    }

    @Deprecated
    public static void getMatrix(int n, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glGetFloatv(n, floatBuffer);
    }

    @Deprecated
    public static void ortho(double d, double d2, double d3, double d4, double d5, double d6) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glOrtho(d, d2, d3, d4, d5, d6);
    }

    @Deprecated
    public static void rotatef(float f, float f2, float f3, float f4) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glRotatef(f, f2, f3, f4);
    }

    @Deprecated
    public static void scalef(float f, float f2, float f3) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glScalef(f, f2, f3);
    }

    @Deprecated
    public static void scaled(double d, double d2, double d3) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glScaled(d, d2, d3);
    }

    @Deprecated
    public static void translatef(float f, float f2, float f3) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glTranslatef(f, f2, f3);
    }

    @Deprecated
    public static void translated(double d, double d2, double d3) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glTranslated(d, d2, d3);
    }

    @Deprecated
    public static void multMatrix(FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glMultMatrixf(floatBuffer);
    }

    @Deprecated
    public static void multMatrix(Matrix4f matrix4f) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        matrix4f.write(MATRIX_BUFFER);
        MATRIX_BUFFER.rewind();
        GlStateManager.multMatrix(MATRIX_BUFFER);
    }

    @Deprecated
    public static void color4f(float f, float f2, float f3, float f4) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (f != GlStateManager.COLOR.red || f2 != GlStateManager.COLOR.green || f3 != GlStateManager.COLOR.blue || f4 != GlStateManager.COLOR.alpha) {
            GlStateManager.COLOR.red = f;
            GlStateManager.COLOR.green = f2;
            GlStateManager.COLOR.blue = f3;
            GlStateManager.COLOR.alpha = f4;
            GL11.glColor4f(f, f2, f3, f4);
        }
    }

    @Deprecated
    public static void clearCurrentColor() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.COLOR.red = -1.0f;
        GlStateManager.COLOR.green = -1.0f;
        GlStateManager.COLOR.blue = -1.0f;
        GlStateManager.COLOR.alpha = -1.0f;
    }

    @Deprecated
    public static void normalPointer(int n, int n2, long l) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glNormalPointer(n, n2, l);
    }

    @Deprecated
    public static void texCoordPointer(int n, int n2, int n3, long l) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glTexCoordPointer(n, n2, n3, l);
    }

    @Deprecated
    public static void vertexPointer(int n, int n2, int n3, long l) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glVertexPointer(n, n2, n3, l);
    }

    @Deprecated
    public static void colorPointer(int n, int n2, int n3, long l) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glColorPointer(n, n2, n3, l);
    }

    public static void vertexAttribPointer(int n, int n2, int n3, boolean bl, int n4, long l) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glVertexAttribPointer(n, n2, n3, bl, n4, l);
    }

    @Deprecated
    public static void enableClientState(int n) {
        if (!clientStateLocked) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThread);
            GL11.glEnableClientState(n);
        }
    }

    @Deprecated
    public static void disableClientState(int n) {
        if (!clientStateLocked) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThread);
            GL11.glDisableClientState(n);
        }
    }

    public static void enableVertexAttribArray(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glEnableVertexAttribArray(n);
    }

    public static void glEnableVertexAttribArray(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL20.glEnableVertexAttribArray(n);
    }

    public static void drawArrays(int n, int n2, int n3) {
        int n4;
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glDrawArrays(n, n2, n3);
        if (Config.isShaders() && !creatingDisplayList && (n4 = Shaders.activeProgram.getCountInstances()) > 1) {
            for (int i = 1; i < n4; ++i) {
                Shaders.uniform_instanceId.setValue(i);
                GL11.glDrawArrays(n, n2, n3);
            }
            Shaders.uniform_instanceId.setValue(0);
        }
    }

    public static void lineWidth(float f) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glLineWidth(f);
    }

    public static void pixelStore(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GL11.glPixelStorei(n, n2);
    }

    public static void pixelTransfer(int n, float f) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glPixelTransferf(n, f);
    }

    public static void readPixels(int n, int n2, int n3, int n4, int n5, int n6, ByteBuffer byteBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glReadPixels(n, n2, n3, n4, n5, n6, byteBuffer);
    }

    public static int getError() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GL11.glGetError();
    }

    public static String getString(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GL11.glGetString(n);
    }

    public static int getInteger(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        return GL11.glGetInteger(n);
    }

    public static boolean isFabulous() {
        return supportType != SupportType.NONE;
    }

    public static int getActiveTextureUnit() {
        return 33984 + activeTexture;
    }

    public static void bindCurrentTexture() {
        GL11.glBindTexture(3553, GlStateManager.TEXTURES[GlStateManager.activeTexture].textureName);
    }

    public static int getBoundTexture() {
        return GlStateManager.TEXTURES[GlStateManager.activeTexture].textureName;
    }

    public static void checkBoundTexture() {
        if (Config.isMinecraftThread()) {
            int n = GL11.glGetInteger(34016);
            int n2 = GL11.glGetInteger(32873);
            int n3 = GlStateManager.getActiveTextureUnit();
            int n4 = GlStateManager.getBoundTexture();
            if (n4 > 0 && (n != n3 || n2 != n4)) {
                Config.dbg("checkTexture: act: " + n3 + ", glAct: " + n + ", tex: " + n4 + ", glTex: " + n2);
            }
        }
    }

    public static void genTextures(IntBuffer intBuffer) {
        GL11.glGenTextures(intBuffer);
    }

    public static void deleteTextures(IntBuffer intBuffer) {
        intBuffer.rewind();
        while (intBuffer.position() < intBuffer.limit()) {
            int n = intBuffer.get();
            GlStateManager.deleteTexture(n);
        }
        intBuffer.rewind();
    }

    public static boolean isFogEnabled() {
        return GlStateManager.FOG.fog.currentState;
    }

    public static void setFogEnabled(boolean bl) {
        GlStateManager.FOG.fog.setEnabled(bl);
    }

    public static void lockAlpha(GlAlphaState glAlphaState) {
        if (!alphaLock.isLocked()) {
            GlStateManager.getAlphaState(alphaLockState);
            GlStateManager.setAlphaState(glAlphaState);
            alphaLock.lock();
        }
    }

    public static void unlockAlpha() {
        if (alphaLock.unlock()) {
            GlStateManager.setAlphaState(alphaLockState);
        }
    }

    public static void getAlphaState(GlAlphaState glAlphaState) {
        if (alphaLock.isLocked()) {
            glAlphaState.setState(alphaLockState);
        } else {
            glAlphaState.setState(GlStateManager.ALPHA_TEST.test.currentState, GlStateManager.ALPHA_TEST.func, GlStateManager.ALPHA_TEST.ref);
        }
    }

    public static void setAlphaState(GlAlphaState glAlphaState) {
        if (alphaLock.isLocked()) {
            alphaLockState.setState(glAlphaState);
        } else {
            GlStateManager.ALPHA_TEST.test.setEnabled(glAlphaState.isEnabled());
            GlStateManager.alphaFunc(glAlphaState.getFunc(), glAlphaState.getRef());
        }
    }

    public static void lockBlend(GlBlendState glBlendState) {
        if (!blendLock.isLocked()) {
            GlStateManager.getBlendState(blendLockState);
            GlStateManager.setBlendState(glBlendState);
            blendLock.lock();
        }
    }

    public static void unlockBlend() {
        if (blendLock.unlock()) {
            GlStateManager.setBlendState(blendLockState);
        }
    }

    public static void getBlendState(GlBlendState glBlendState) {
        if (blendLock.isLocked()) {
            glBlendState.setState(blendLockState);
        } else {
            glBlendState.setState(GlStateManager.BLEND.blend.currentState, GlStateManager.BLEND.srcFactorRgb, GlStateManager.BLEND.dstFactorRgb, GlStateManager.BLEND.srcFactorAlpha, GlStateManager.BLEND.dstFactorAlpha);
        }
    }

    public static void setBlendState(GlBlendState glBlendState) {
        if (blendLock.isLocked()) {
            blendLockState.setState(glBlendState);
        } else {
            GlStateManager.BLEND.blend.setEnabled(glBlendState.isEnabled());
            if (!glBlendState.isSeparate()) {
                GlStateManager.blendFunc(glBlendState.getSrcFactor(), glBlendState.getDstFactor());
            } else {
                GlStateManager.blendFuncSeparate(glBlendState.getSrcFactor(), glBlendState.getDstFactor(), glBlendState.getSrcFactorAlpha(), glBlendState.getDstFactorAlpha());
            }
        }
    }

    public static void lockCull(GlCullState glCullState) {
        if (!cullLock.isLocked()) {
            GlStateManager.getCullState(cullLockState);
            GlStateManager.setCullState(glCullState);
            cullLock.lock();
        }
    }

    public static void unlockCull() {
        if (cullLock.unlock()) {
            GlStateManager.setCullState(cullLockState);
        }
    }

    public static void getCullState(GlCullState glCullState) {
        if (cullLock.isLocked()) {
            glCullState.setState(cullLockState);
        } else {
            glCullState.setState(GlStateManager.CULL.cullFace.currentState, GlStateManager.CULL.mode);
        }
    }

    public static void setCullState(GlCullState glCullState) {
        if (cullLock.isLocked()) {
            cullLockState.setState(glCullState);
        } else {
            GlStateManager.CULL.cullFace.setEnabled(glCullState.isEnabled());
            GlStateManager.CULL.mode = glCullState.getMode();
        }
    }

    public static void glMultiDrawArrays(int n, IntBuffer intBuffer, IntBuffer intBuffer2) {
        int n2;
        GL14.glMultiDrawArrays(n, intBuffer, intBuffer2);
        if (Config.isShaders() && !creatingDisplayList && (n2 = Shaders.activeProgram.getCountInstances()) > 1) {
            for (int i = 1; i < n2; ++i) {
                Shaders.uniform_instanceId.setValue(i);
                GL14.glMultiDrawArrays(n, intBuffer, intBuffer2);
            }
            Shaders.uniform_instanceId.setValue(0);
        }
    }

    public static void clear(int n) {
        GlStateManager.clear(n, false);
    }

    public static void callLists(IntBuffer intBuffer) {
        int n;
        GL11.glCallLists(intBuffer);
        if (Config.isShaders() && !creatingDisplayList && (n = Shaders.activeProgram.getCountInstances()) > 1) {
            for (int i = 1; i < n; ++i) {
                Shaders.uniform_instanceId.setValue(i);
                GL11.glCallLists(intBuffer);
            }
            Shaders.uniform_instanceId.setValue(0);
        }
    }

    public static void bufferData(int n, long l, int n2) {
        GL15.glBufferData(n, l, n2);
    }

    public static void bufferSubData(int n, long l, ByteBuffer byteBuffer) {
        GL15.glBufferSubData(n, l, byteBuffer);
    }

    public static void copyBufferSubData(int n, int n2, long l, long l2, long l3) {
        if (openGL31) {
            GL31.glCopyBufferSubData(n, n2, l, l2, l3);
        } else {
            ARBCopyBuffer.glCopyBufferSubData(n, n2, l, l2, l3);
        }
    }

    public static boolean isFogAllowed() {
        return fogAllowed;
    }

    public static void setFogAllowed(boolean bl) {
        fogAllowed = bl;
    }

    public static void lockClientState() {
        clientStateLocked = true;
    }

    public static void unlockClientState() {
        clientStateLocked = false;
    }

    public static void readPixels(int n, int n2, int n3, int n4, int n5, int n6, long l) {
        GL11.glReadPixels(n, n2, n3, n4, n5, n6, l);
    }

    public static int getFramebufferRead() {
        return framebufferRead;
    }

    public static int getFramebufferDraw() {
        return framebufferDraw;
    }

    public static void applyCurrentBlend() {
        if (GlStateManager.BLEND.blend.currentState) {
            GL11.glEnable(3042);
        } else {
            GL11.glDisable(3042);
        }
        GL14.glBlendFuncSeparate(GlStateManager.BLEND.srcFactorRgb, GlStateManager.BLEND.dstFactorRgb, GlStateManager.BLEND.srcFactorAlpha, GlStateManager.BLEND.dstFactorAlpha);
    }

    public static void setBlendsIndexed(GlBlendState[] glBlendStateArray) {
        if (glBlendStateArray != null) {
            for (int i = 0; i < glBlendStateArray.length; ++i) {
                GlBlendState glBlendState = glBlendStateArray[i];
                if (glBlendState == null) continue;
                if (glBlendState.isEnabled()) {
                    GL30.glEnablei(3042, i);
                } else {
                    GL30.glDisablei(3042, i);
                }
                ARBDrawBuffersBlend.glBlendFuncSeparateiARB(i, glBlendState.getSrcFactor(), glBlendState.getDstFactor(), glBlendState.getSrcFactorAlpha(), glBlendState.getDstFactorAlpha());
            }
        }
    }

    public static void bindImageTexture(int n, int n2, int n3, boolean bl, int n4, int n5, int n6) {
        if (n >= 0 && n < IMAGE_TEXTURES.length) {
            if (IMAGE_TEXTURES[n] == n2) {
                return;
            }
            GlStateManager.IMAGE_TEXTURES[n] = n2;
        }
        GL42.glBindImageTexture(n, n2, n3, bl, n4, n5, n6);
    }

    private static TextureState[] lambda$static$4(int n) {
        return new TextureState[n];
    }

    private static TextureState lambda$static$3(int n) {
        return new TextureState();
    }

    private static BooleanState[] lambda$static$2(int n) {
        return new BooleanState[n];
    }

    private static BooleanState lambda$static$1(int n) {
        return new BooleanState(16384 + n);
    }

    private static void lambda$static$0(FloatBuffer floatBuffer) {
        LWJGLMemoryUntracker.untrack(MemoryUtil.memAddress(floatBuffer));
    }

    static {
        TEXTURES = (TextureState[])IntStream.range(0, 32).mapToObj(GlStateManager::lambda$static$3).toArray(GlStateManager::lambda$static$4);
        shadeModel = 7425;
        RESCALE_NORMAL = new BooleanState(32826);
        COLOR_MASK = new ColorMask();
        COLOR = new Color();
        alphaLock = new LockCounter();
        alphaLockState = new GlAlphaState();
        blendLock = new LockCounter();
        blendLockState = new GlBlendState();
        cullLock = new LockCounter();
        cullLockState = new GlCullState();
        clientStateLocked = false;
        clientActiveTexture = 0;
        creatingDisplayList = false;
        lastBrightnessX = 0.0f;
        lastBrightnessY = 0.0f;
        fogAllowed = true;
        IMAGE_TEXTURES = new int[8];
    }

    @Deprecated
    static class AlphaState {
        public final BooleanState test = new BooleanState(3008);
        public int func = 519;
        public float ref = -1.0f;

        private AlphaState() {
        }
    }

    static class BooleanState {
        private final int capability;
        private boolean currentState;

        public BooleanState(int n) {
            this.capability = n;
        }

        public void disable() {
            this.setEnabled(true);
        }

        public void enable() {
            this.setEnabled(false);
        }

        public void setEnabled(boolean bl) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
            if (bl != this.currentState) {
                this.currentState = bl;
                if (bl) {
                    GL11.glEnable(this.capability);
                } else {
                    GL11.glDisable(this.capability);
                }
            }
        }
    }

    @Deprecated
    static class ColorMaterialState {
        public final BooleanState colorMaterial = new BooleanState(2903);
        public int face = 1032;
        public int mode = 5634;

        private ColorMaterialState() {
        }
    }

    static class ScissorState {
        public final BooleanState field_244595_a = new BooleanState(3089);

        private ScissorState() {
        }
    }

    static class DepthState {
        public final BooleanState test = new BooleanState(2929);
        public boolean mask = true;
        public int func = 513;

        private DepthState() {
        }
    }

    static class BlendState {
        public final BooleanState blend = new BooleanState(3042);
        public int srcFactorRgb = 1;
        public int dstFactorRgb = 0;
        public int srcFactorAlpha = 1;
        public int dstFactorAlpha = 0;

        private BlendState() {
        }
    }

    public static enum SupportType {
        BASE,
        EXT,
        NONE;

    }

    public static enum FramebufferExtension {
        BASE,
        ARB,
        EXT;

    }

    static class TextureState {
        public final BooleanState texture2DState = new BooleanState(3553);
        public int textureName;

        private TextureState() {
        }
    }

    @Deprecated
    public static enum TexGen {
        S,
        T,
        R,
        Q;

    }

    @Deprecated
    static class FogState {
        public final BooleanState fog = new BooleanState(2912);
        public int mode = 2048;
        public float density = 1.0f;
        public float start;
        public float end = 1.0f;

        private FogState() {
        }
    }

    static class CullState {
        public final BooleanState cullFace = new BooleanState(2884);
        public int mode = 1029;

        private CullState() {
        }
    }

    static class PolygonOffsetState {
        public final BooleanState polyOffset = new BooleanState(32823);
        public final BooleanState lineOffset = new BooleanState(10754);
        public float factor;
        public float units;

        private PolygonOffsetState() {
        }
    }

    static class ColorLogicState {
        public final BooleanState colorLogicOp = new BooleanState(3058);
        public int logicOpcode = 5379;

        private ColorLogicState() {
        }
    }

    @Deprecated
    static class TexGenCoord {
        public final BooleanState textureGen;
        public final int coord;
        public int mode = -1;

        public TexGenCoord(int n, int n2) {
            this.coord = n;
            this.textureGen = new BooleanState(n2);
        }
    }

    @Deprecated
    static class TexGenState {
        public final TexGenCoord s = new TexGenCoord(8192, 3168);
        public final TexGenCoord t = new TexGenCoord(8193, 3169);
        public final TexGenCoord r = new TexGenCoord(8194, 3170);
        public final TexGenCoord q = new TexGenCoord(8195, 3171);

        private TexGenState() {
        }
    }

    public static enum Viewport {
        INSTANCE;

        protected int x;
        protected int y;
        protected int w;
        protected int h;
    }

    static class ColorMask {
        public boolean red = true;
        public boolean green = true;
        public boolean blue = true;
        public boolean alpha = true;

        private ColorMask() {
        }
    }

    static class StencilState {
        public final StencilFunc func = new StencilFunc();
        public int mask = -1;
        public int sfail = 7680;
        public int dpfail = 7680;
        public int dppass = 7680;

        private StencilState() {
        }
    }

    static class StencilFunc {
        public int func = 519;
        public int ref;
        public int mask = -1;

        private StencilFunc() {
        }
    }

    @Deprecated
    static class Color {
        public float red = 1.0f;
        public float green = 1.0f;
        public float blue = 1.0f;
        public float alpha = 1.0f;

        public Color() {
            this(1.0f, 1.0f, 1.0f, 1.0f);
        }

        public Color(float f, float f2, float f3, float f4) {
            this.red = f;
            this.green = f2;
            this.blue = f3;
            this.alpha = f4;
        }
    }

    public static enum SourceFactor {
        CONSTANT_ALPHA(32771),
        CONSTANT_COLOR(32769),
        DST_ALPHA(772),
        DST_COLOR(774),
        ONE(1),
        ONE_MINUS_CONSTANT_ALPHA(32772),
        ONE_MINUS_CONSTANT_COLOR(32770),
        ONE_MINUS_DST_ALPHA(773),
        ONE_MINUS_DST_COLOR(775),
        ONE_MINUS_SRC_ALPHA(771),
        ONE_MINUS_SRC_COLOR(769),
        SRC_ALPHA(770),
        SRC_ALPHA_SATURATE(776),
        SRC_COLOR(768),
        ZERO(0);

        public final int param;

        private SourceFactor(int n2) {
            this.param = n2;
        }
    }

    public static enum LogicOp {
        AND(5377),
        AND_INVERTED(5380),
        AND_REVERSE(5378),
        CLEAR(5376),
        COPY(5379),
        COPY_INVERTED(5388),
        EQUIV(5385),
        INVERT(5386),
        NAND(5390),
        NOOP(5381),
        NOR(5384),
        OR(5383),
        OR_INVERTED(5389),
        OR_REVERSE(5387),
        SET(5391),
        XOR(5382);

        public final int opcode;

        private LogicOp(int n2) {
            this.opcode = n2;
        }
    }

    @Deprecated
    public static enum FogMode {
        LINEAR(9729),
        EXP(2048),
        EXP2(2049);

        public final int param;

        private FogMode(int n2) {
            this.param = n2;
        }
    }

    public static enum DestFactor {
        CONSTANT_ALPHA(32771),
        CONSTANT_COLOR(32769),
        DST_ALPHA(772),
        DST_COLOR(774),
        ONE(1),
        ONE_MINUS_CONSTANT_ALPHA(32772),
        ONE_MINUS_CONSTANT_COLOR(32770),
        ONE_MINUS_DST_ALPHA(773),
        ONE_MINUS_DST_COLOR(775),
        ONE_MINUS_SRC_ALPHA(771),
        ONE_MINUS_SRC_COLOR(769),
        SRC_ALPHA(770),
        SRC_COLOR(768),
        ZERO(0);

        public final int param;

        private DestFactor(int n2) {
            this.param = n2;
        }
    }
}

