/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.blaze3d.systems;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.IRenderCall;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallbackI;

public class RenderSystem {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ConcurrentLinkedQueue<IRenderCall> recordingQueue = Queues.newConcurrentLinkedQueue();
    private static final Tessellator RENDER_THREAD_TESSELATOR = new Tessellator();
    public static final float DEFAULTALPHACUTOFF = 0.1f;
    private static final int MINIMUM_ATLAS_TEXTURE_SIZE = 1024;
    private static boolean isReplayingQueue;
    private static Thread gameThread;
    private static Thread renderThread;
    private static int MAX_SUPPORTED_TEXTURE_SIZE;
    private static boolean isInInit;
    private static double lastDrawTime;

    public static void initRenderThread() {
        if (renderThread != null || gameThread == Thread.currentThread()) {
            throw new IllegalStateException("Could not initialize render thread");
        }
        renderThread = Thread.currentThread();
    }

    public static boolean isOnRenderThread() {
        return Thread.currentThread() == renderThread;
    }

    public static boolean isOnRenderThreadOrInit() {
        return isInInit || RenderSystem.isOnRenderThread();
    }

    public static void initGameThread(boolean bl) {
        boolean bl2;
        boolean bl3 = bl2 = renderThread == Thread.currentThread();
        if (gameThread != null || renderThread == null || bl2 == bl) {
            throw new IllegalStateException("Could not initialize tick thread");
        }
        gameThread = Thread.currentThread();
    }

    public static boolean isOnGameThread() {
        return false;
    }

    public static boolean isOnGameThreadOrInit() {
        return isInInit || RenderSystem.isOnGameThread();
    }

    public static void assertThread(Supplier<Boolean> supplier) {
        if (!supplier.get().booleanValue()) {
            throw new IllegalStateException("Rendersystem called from wrong thread");
        }
    }

    public static boolean isInInitPhase() {
        return false;
    }

    public static void recordRenderCall(IRenderCall iRenderCall) {
        recordingQueue.add(iRenderCall);
    }

    public static void flipFrame(long l) {
        GLFW.glfwPollEvents();
        RenderSystem.replayQueue();
        Tessellator.getInstance().getBuffer().reset();
        GLFW.glfwSwapBuffers(l);
        GLFW.glfwPollEvents();
    }

    public static void replayQueue() {
        isReplayingQueue = true;
        while (!recordingQueue.isEmpty()) {
            IRenderCall iRenderCall = recordingQueue.poll();
            iRenderCall.execute();
        }
        isReplayingQueue = false;
    }

    public static void limitDisplayFPS(int n) {
        double d = lastDrawTime + 1.0 / (double)n;
        double d2 = GLFW.glfwGetTime();
        while (d2 < d) {
            GLFW.glfwWaitEventsTimeout(d - d2);
            d2 = GLFW.glfwGetTime();
        }
        lastDrawTime = d2;
    }

    @Deprecated
    public static void pushLightingAttributes() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.pushLightingAttributes();
    }

    @Deprecated
    public static void pushTextureAttributes() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.pushTextureAttributes();
    }

    @Deprecated
    public static void popAttributes() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.popAttributes();
    }

    @Deprecated
    public static void disableAlphaTest() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableAlphaTest();
    }

    @Deprecated
    public static void enableAlphaTest() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableAlphaTest();
    }

    @Deprecated
    public static void alphaFunc(int n, float f) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.alphaFunc(n, f);
    }

    @Deprecated
    public static void enableLighting() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableLighting();
    }

    @Deprecated
    public static void disableLighting() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableLighting();
    }

    @Deprecated
    public static void enableColorMaterial() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableColorMaterial();
    }

    @Deprecated
    public static void disableColorMaterial() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableColorMaterial();
    }

    @Deprecated
    public static void colorMaterial(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.colorMaterial(n, n2);
    }

    @Deprecated
    public static void normal3f(float f, float f2, float f3) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.normal3f(f, f2, f3);
    }

    public static void disableDepthTest() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableDepthTest();
    }

    public static void enableDepthTest() {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.enableDepthTest();
    }

    public static void enableScissor(int n, int n2, int n3, int n4) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.func_244594_k();
        GlStateManager.func_244592_a(n, n2, n3, n4);
    }

    public static void disableScissor() {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.func_244593_j();
    }

    public static void depthFunc(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.depthFunc(n);
    }

    public static void depthMask(boolean bl) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.depthMask(bl);
    }

    public static void enableBlend() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableBlend();
    }

    public static void disableBlend() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableBlend();
    }

    public static void blendFunc(GlStateManager.SourceFactor sourceFactor, GlStateManager.DestFactor destFactor) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.blendFunc(sourceFactor.param, destFactor.param);
    }

    public static void blendFunc(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.blendFunc(n, n2);
    }

    public static void blendFuncSeparate(GlStateManager.SourceFactor sourceFactor, GlStateManager.DestFactor destFactor, GlStateManager.SourceFactor sourceFactor2, GlStateManager.DestFactor destFactor2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.blendFuncSeparate(sourceFactor.param, destFactor.param, sourceFactor2.param, destFactor2.param);
    }

    public static void blendFuncSeparate(int n, int n2, int n3, int n4) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.blendFuncSeparate(n, n2, n3, n4);
    }

    public static void blendEquation(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.blendEquation(n);
    }

    public static void blendColor(float f, float f2, float f3, float f4) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.blendColor(f, f2, f3, f4);
    }

    @Deprecated
    public static void enableFog() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableFog();
    }

    @Deprecated
    public static void disableFog() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableFog();
    }

    @Deprecated
    public static void fogMode(GlStateManager.FogMode fogMode) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fogMode(fogMode.param);
    }

    @Deprecated
    public static void fogMode(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fogMode(n);
    }

    @Deprecated
    public static void fogDensity(float f) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fogDensity(f);
    }

    @Deprecated
    public static void fogStart(float f) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fogStart(f);
    }

    @Deprecated
    public static void fogEnd(float f) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fogEnd(f);
    }

    @Deprecated
    public static void fog(int n, float f, float f2, float f3, float f4) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fog(n, new float[]{f, f2, f3, f4});
    }

    @Deprecated
    public static void fogi(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fogi(n, n2);
    }

    public static void enableCull() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableCull();
    }

    public static void disableCull() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableCull();
    }

    public static void polygonMode(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.polygonMode(n, n2);
    }

    public static void enablePolygonOffset() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enablePolygonOffset();
    }

    public static void disablePolygonOffset() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disablePolygonOffset();
    }

    public static void enableLineOffset() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableLineOffset();
    }

    public static void disableLineOffset() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableLineOffset();
    }

    public static void polygonOffset(float f, float f2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.polygonOffset(f, f2);
    }

    public static void enableColorLogicOp() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableColorLogicOp();
    }

    public static void disableColorLogicOp() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableColorLogicOp();
    }

    public static void logicOp(GlStateManager.LogicOp logicOp) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.logicOp(logicOp.opcode);
    }

    public static void activeTexture(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.activeTexture(n);
    }

    public static void enableTexture() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableTexture();
    }

    public static void disableTexture() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableTexture();
    }

    public static void texParameter(int n, int n2, int n3) {
        GlStateManager.texParameter(n, n2, n3);
    }

    public static void deleteTexture(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.deleteTexture(n);
    }

    public static void bindTexture(int n) {
        GlStateManager.bindTexture(n);
    }

    @Deprecated
    public static void shadeModel(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.shadeModel(n);
    }

    @Deprecated
    public static void enableRescaleNormal() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableRescaleNormal();
    }

    @Deprecated
    public static void disableRescaleNormal() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableRescaleNormal();
    }

    public static void viewport(int n, int n2, int n3, int n4) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.viewport(n, n2, n3, n4);
    }

    public static void colorMask(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.colorMask(bl, bl2, bl3, bl4);
    }

    public static void stencilFunc(int n, int n2, int n3) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.stencilFunc(n, n2, n3);
    }

    public static void stencilMask(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.stencilMask(n);
    }

    public static void stencilOp(int n, int n2, int n3) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.stencilOp(n, n2, n3);
    }

    public static void clearDepth(double d) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.clearDepth(d);
    }

    public static void clearColor(float f, float f2, float f3, float f4) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.clearColor(f, f2, f3, f4);
    }

    public static void clearStencil(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.clearStencil(n);
    }

    public static void clear(int n, boolean bl) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.clear(n, bl);
    }

    @Deprecated
    public static void matrixMode(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.matrixMode(n);
    }

    @Deprecated
    public static void loadIdentity() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.loadIdentity();
    }

    @Deprecated
    public static void pushMatrix() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.pushMatrix();
    }

    @Deprecated
    public static void popMatrix() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.popMatrix();
    }

    @Deprecated
    public static void ortho(double d, double d2, double d3, double d4, double d5, double d6) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.ortho(d, d2, d3, d4, d5, d6);
    }

    @Deprecated
    public static void rotatef(float f, float f2, float f3, float f4) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.rotatef(f, f2, f3, f4);
    }

    @Deprecated
    public static void scalef(float f, float f2, float f3) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.scalef(f, f2, f3);
    }

    @Deprecated
    public static void scaled(double d, double d2, double d3) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.scaled(d, d2, d3);
    }

    @Deprecated
    public static void translatef(float f, float f2, float f3) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.translatef(f, f2, f3);
    }

    @Deprecated
    public static void translated(double d, double d2, double d3) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.translated(d, d2, d3);
    }

    @Deprecated
    public static void multMatrix(Matrix4f matrix4f) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.multMatrix(matrix4f);
    }

    @Deprecated
    public static void color4f(float f, float f2, float f3, float f4) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.color4f(f, f2, f3, f4);
    }

    @Deprecated
    public static void color3f(float f, float f2, float f3) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.color4f(f, f2, f3, 1.0f);
    }

    @Deprecated
    public static void clearCurrentColor() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.clearCurrentColor();
    }

    public static void drawArrays(int n, int n2, int n3) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.drawArrays(n, n2, n3);
    }

    public static void lineWidth(float f) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.lineWidth(f);
    }

    public static void pixelStore(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.pixelStore(n, n2);
    }

    public static void pixelTransfer(int n, float f) {
        GlStateManager.pixelTransfer(n, f);
    }

    public static void readPixels(int n, int n2, int n3, int n4, int n5, int n6, ByteBuffer byteBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.readPixels(n, n2, n3, n4, n5, n6, byteBuffer);
    }

    public static void getString(int n, Consumer<String> consumer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        consumer.accept(GlStateManager.getString(n));
    }

    public static String getBackendDescription() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        return String.format("LWJGL version %s", GLX._getLWJGLVersion());
    }

    public static String getApiDescription() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        return GLX.getOpenGLVersionString();
    }

    public static LongSupplier initBackendSystem() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        return GLX._initGlfw();
    }

    public static void initRenderer(int n, boolean bl) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        GLX._init(n, bl);
    }

    public static void setErrorCallback(GLFWErrorCallbackI gLFWErrorCallbackI) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        GLX._setGlfwErrorCallback(gLFWErrorCallbackI);
    }

    public static void renderCrosshair(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GLX._renderCrosshair(n, true, true, true);
    }

    public static void setupNvFogDistance() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GLX._setupNvFogDistance();
    }

    @Deprecated
    public static void glMultiTexCoord2f(int n, float f, float f2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.multiTexCoord2f(n, f, f2);
    }

    public static String getCapsString() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        return GLX._getCapsString();
    }

    public static void setupDefaultState(int n, int n2, int n3, int n4) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        GlStateManager.enableTexture();
        GlStateManager.shadeModel(7425);
        GlStateManager.clearDepth(1.0);
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(515);
        GlStateManager.enableAlphaTest();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.viewport(n, n2, n3, n4);
    }

    public static int maxSupportedTextureSize() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        if (MAX_SUPPORTED_TEXTURE_SIZE == -1) {
            int n = GlStateManager.getInteger(3379);
            for (int i = Math.max(32768, n); i >= 1024; i >>= 1) {
                GlStateManager.texImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, null);
                int n2 = GlStateManager.getTexLevelParameter(32868, 0, 4096);
                if (n2 == 0) continue;
                MAX_SUPPORTED_TEXTURE_SIZE = i;
                return i;
            }
            MAX_SUPPORTED_TEXTURE_SIZE = Math.max(n, 1024);
            LOGGER.info("Failed to determine maximum texture size by probing, trying GL_MAX_TEXTURE_SIZE = {}", (Object)MAX_SUPPORTED_TEXTURE_SIZE);
        }
        return MAX_SUPPORTED_TEXTURE_SIZE;
    }

    public static void glBindBuffer(int n, Supplier<Integer> supplier) {
        GlStateManager.bindBuffer(n, supplier.get());
    }

    public static void glBufferData(int n, ByteBuffer byteBuffer, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GlStateManager.bufferData(n, byteBuffer, n2);
    }

    public static void glDeleteBuffers(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.deleteBuffers(n);
    }

    public static void glUniform1i(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform1i(n, n2);
    }

    public static void glUniform1(int n, IntBuffer intBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform1i(n, intBuffer);
    }

    public static void glUniform2(int n, IntBuffer intBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform2i(n, intBuffer);
    }

    public static void glUniform3(int n, IntBuffer intBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform3i(n, intBuffer);
    }

    public static void glUniform4(int n, IntBuffer intBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform4i(n, intBuffer);
    }

    public static void glUniform1(int n, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform1f(n, floatBuffer);
    }

    public static void glUniform2(int n, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform2f(n, floatBuffer);
    }

    public static void glUniform3(int n, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform3f(n, floatBuffer);
    }

    public static void glUniform4(int n, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform4f(n, floatBuffer);
    }

    public static void glUniformMatrix2(int n, boolean bl, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniformMatrix2f(n, bl, floatBuffer);
    }

    public static void glUniformMatrix3(int n, boolean bl, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniformMatrix3f(n, bl, floatBuffer);
    }

    public static void glUniformMatrix4(int n, boolean bl, FloatBuffer floatBuffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniformMatrix4f(n, bl, floatBuffer);
    }

    public static void setupOutline() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.setupOutline();
    }

    public static void teardownOutline() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.teardownOutline();
    }

    public static void setupOverlayColor(IntSupplier intSupplier, int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.setupOverlayColor(intSupplier.getAsInt(), n);
    }

    public static void teardownOverlayColor() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.teardownOverlayColor();
    }

    public static void setupLevelDiffuseLighting(Vector3f vector3f, Vector3f vector3f2, Matrix4f matrix4f) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.setupLighting(vector3f, vector3f2, matrix4f);
    }

    public static void setupGuiFlatDiffuseLighting(Vector3f vector3f, Vector3f vector3f2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.setupScaledLighting(vector3f, vector3f2);
    }

    public static void setupGui3DDiffuseLighting(Vector3f vector3f, Vector3f vector3f2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.setupGui3DMatrix(vector3f, vector3f2);
    }

    public static void mulTextureByProjModelView() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.mulTextureByProjModelView();
    }

    public static void setupEndPortalTexGen() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.setupEndPortalTexGen();
    }

    public static void clearTexGen() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.clearTexGen();
    }

    public static void beginInitialization() {
        isInInit = true;
    }

    public static void finishInitialization() {
        isInInit = false;
        if (!recordingQueue.isEmpty()) {
            RenderSystem.replayQueue();
        }
        if (!recordingQueue.isEmpty()) {
            throw new IllegalStateException("Recorded to render queue during initialization");
        }
    }

    public static void glGenBuffers(Consumer<Integer> consumer) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> RenderSystem.lambda$glGenBuffers$0(consumer));
        } else {
            consumer.accept(GlStateManager.genBuffers());
        }
    }

    public static Tessellator renderThreadTesselator() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return RENDER_THREAD_TESSELATOR;
    }

    public static void defaultBlendFunc() {
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    }

    public static void defaultAlphaFunc() {
        RenderSystem.alphaFunc(516, 0.1f);
    }

    @Deprecated
    public static void runAsFancy(Runnable runnable) {
        boolean bl = Minecraft.isFabulousGraphicsEnabled();
        if (!bl) {
            runnable.run();
        } else {
            GameSettings gameSettings = Minecraft.getInstance().gameSettings;
            GraphicsFanciness graphicsFanciness = gameSettings.graphicFanciness;
            gameSettings.graphicFanciness = GraphicsFanciness.FANCY;
            runnable.run();
            gameSettings.graphicFanciness = graphicsFanciness;
        }
    }

    private static void lambda$glGenBuffers$0(Consumer consumer) {
        consumer.accept(GlStateManager.genBuffers());
    }

    static {
        MAX_SUPPORTED_TEXTURE_SIZE = -1;
        lastDrawTime = Double.MIN_VALUE;
    }
}

