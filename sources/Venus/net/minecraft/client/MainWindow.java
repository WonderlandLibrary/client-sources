/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Optional;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.Monitor;
import net.minecraft.client.renderer.IWindowEventListener;
import net.minecraft.client.renderer.MonitorHandler;
import net.minecraft.client.renderer.ScreenSize;
import net.minecraft.client.renderer.VideoMode;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.util.InputMappings;
import net.minecraft.client.util.UndeclaredException;
import net.minecraftforge.fml.loading.progress.EarlyProgressVisualization;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.TextureUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public final class MainWindow
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final GLFWErrorCallback loggingErrorCallback = GLFWErrorCallback.create(this::logGlError);
    private final IWindowEventListener mc;
    private final MonitorHandler monitorHandler;
    private final long handle;
    private int prevWindowX;
    private int prevWindowY;
    private int prevWindowWidth;
    private int prevWindowHeight;
    private Optional<VideoMode> videoMode;
    private boolean fullscreen;
    private boolean lastFullscreen;
    private int windowX;
    private int windowY;
    private int width;
    private int height;
    private int framebufferWidth;
    private int framebufferHeight;
    private int scaledWidth;
    private int scaledHeight;
    private double guiScaleFactor;
    private String renderPhase = "";
    private boolean videoModeChanged;
    private int framerateLimit;
    private boolean vsync;
    private boolean closed;

    public MainWindow(IWindowEventListener iWindowEventListener, MonitorHandler monitorHandler, ScreenSize screenSize, @Nullable String string, String string2) {
        Object object;
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        this.monitorHandler = monitorHandler;
        this.setThrowExceptionOnGlError();
        this.setRenderPhase("Pre startup");
        this.mc = iWindowEventListener;
        Optional<VideoMode> optional = VideoMode.parseFromSettings(string);
        this.videoMode = optional.isPresent() ? optional : (screenSize.fullscreenWidth.isPresent() && screenSize.fullscreenHeight.isPresent() ? Optional.of(new VideoMode(screenSize.fullscreenWidth.getAsInt(), screenSize.fullscreenHeight.getAsInt(), 8, 8, 8, 60)) : Optional.empty());
        this.lastFullscreen = this.fullscreen = screenSize.fullscreen;
        Monitor monitor = monitorHandler.getMonitor(GLFW.glfwGetPrimaryMonitor());
        this.width = screenSize.width > 0 ? screenSize.width : 1;
        this.prevWindowWidth = this.width;
        this.height = screenSize.height > 0 ? screenSize.height : 1;
        this.prevWindowHeight = this.height;
        GLFW.glfwDefaultWindowHints();
        if (Config.isAntialiasing()) {
            GLFW.glfwWindowHint(135181, Config.getAntialiasingLevel());
        }
        GLFW.glfwWindowHint(139265, 196609);
        GLFW.glfwWindowHint(139275, 221185);
        GLFW.glfwWindowHint(139266, 2);
        GLFW.glfwWindowHint(139267, 0);
        GLFW.glfwWindowHint(139272, 0);
        long l = 0L;
        if (Reflector.EarlyProgressVisualization_handOffWindow.exists()) {
            object = Reflector.getFieldValue(Reflector.EarlyProgressVisualization_INSTANCE);
            l = Reflector.callLong(object, Reflector.EarlyProgressVisualization_handOffWindow, this::lambda$new$0, this::lambda$new$1, () -> MainWindow.lambda$new$2(string2), () -> this.lambda$new$3(monitor));
            if (Config.isAntialiasing()) {
                GLFW.glfwDestroyWindow(l);
                l = 0L;
            }
        }
        this.handle = l != 0L ? l : GLFW.glfwCreateWindow(this.width, this.height, string2, this.fullscreen && monitor != null ? monitor.getMonitorPointer() : 0L, 0L);
        if (monitor != null) {
            object = monitor.getVideoModeOrDefault(this.fullscreen ? this.videoMode : Optional.empty());
            this.prevWindowX = this.windowX = monitor.getVirtualPosX() + ((VideoMode)object).getWidth() / 2 - this.width / 2;
            this.prevWindowY = this.windowY = monitor.getVirtualPosY() + ((VideoMode)object).getHeight() / 2 - this.height / 2;
        } else {
            object = new int[1];
            int[] nArray = new int[1];
            GLFW.glfwGetWindowPos(this.handle, (int[])object, nArray);
            Object object2 = object[0];
            this.windowX = (int)object2;
            this.prevWindowX = (int)object2;
            this.prevWindowY = this.windowY = nArray[0];
        }
        GLFW.glfwMakeContextCurrent(this.handle);
        GL.createCapabilities();
        this.updateVideoMode();
        this.updateFramebufferSize();
        GLFW.glfwSetFramebufferSizeCallback(this.handle, this::onFramebufferSizeUpdate);
        GLFW.glfwSetWindowPosCallback(this.handle, this::onWindowPosUpdate);
        GLFW.glfwSetWindowSizeCallback(this.handle, this::onWindowSizeUpdate);
        GLFW.glfwSetWindowFocusCallback(this.handle, this::onWindowFocusUpdate);
        GLFW.glfwSetCursorEnterCallback(this.handle, this::onWindowEnterUpdate);
    }

    public int getRefreshRate() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GLX._getRefreshRate(this);
    }

    public boolean shouldClose() {
        return GLX._shouldClose(this);
    }

    public static void checkGlfwError(BiConsumer<Integer, String> biConsumer) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            PointerBuffer pointerBuffer = memoryStack.mallocPointer(1);
            int n = GLFW.glfwGetError(pointerBuffer);
            if (n != 0) {
                long l = pointerBuffer.get();
                String string = l == 0L ? "" : MemoryUtil.memUTF8(l);
                biConsumer.accept(n, string);
            }
        }
    }

    public void setWindowIcon(InputStream inputStream, InputStream inputStream2) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            if (inputStream == null) {
                throw new FileNotFoundException("icons/icon_16x16.png");
            }
            if (inputStream2 == null) {
                throw new FileNotFoundException("icons/icon_32x32.png");
            }
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            IntBuffer intBuffer2 = memoryStack.mallocInt(1);
            IntBuffer intBuffer3 = memoryStack.mallocInt(1);
            GLFWImage.Buffer buffer = GLFWImage.mallocStack(2, memoryStack);
            ByteBuffer byteBuffer = this.loadIcon(inputStream, intBuffer, intBuffer2, intBuffer3);
            if (byteBuffer == null) {
                throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }
            buffer.position(0);
            buffer.width(intBuffer.get(0));
            buffer.height(intBuffer2.get(0));
            buffer.pixels(byteBuffer);
            ByteBuffer byteBuffer2 = this.loadIcon(inputStream2, intBuffer, intBuffer2, intBuffer3);
            if (byteBuffer2 == null) {
                throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }
            buffer.position(1);
            buffer.width(intBuffer.get(0));
            buffer.height(intBuffer2.get(0));
            buffer.pixels(byteBuffer2);
            buffer.position(0);
            GLFW.glfwSetWindowIcon(this.handle, buffer);
            STBImage.stbi_image_free(byteBuffer);
            STBImage.stbi_image_free(byteBuffer2);
        } catch (IOException iOException) {
            LOGGER.error("Couldn't set icon", (Throwable)iOException);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    private ByteBuffer loadIcon(InputStream inputStream, IntBuffer intBuffer, IntBuffer intBuffer2, IntBuffer intBuffer3) throws IOException {
        ByteBuffer byteBuffer;
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        ByteBuffer byteBuffer2 = null;
        try {
            byteBuffer2 = TextureUtil.readToBuffer(inputStream);
            byteBuffer2.rewind();
            byteBuffer = STBImage.stbi_load_from_memory(byteBuffer2, intBuffer, intBuffer2, intBuffer3, 0);
        } finally {
            if (byteBuffer2 != null) {
                MemoryUtil.memFree(byteBuffer2);
            }
        }
        return byteBuffer;
    }

    public void setRenderPhase(String string) {
        this.renderPhase = string;
        if (string.equals("Startup")) {
            TextureUtils.registerTickableTextures();
        }
    }

    private void setThrowExceptionOnGlError() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        GLFW.glfwSetErrorCallback(MainWindow::throwExceptionForGlError);
    }

    private static void throwExceptionForGlError(int n, long l) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        String string = "GLFW error " + n + ": " + MemoryUtil.memUTF8(l);
        TinyFileDialogs.tinyfd_messageBox("Minecraft", string + ".\n\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).", "ok", "error", false);
        throw new GlException(string);
    }

    public void logGlError(int n, long l) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        String string = MemoryUtil.memUTF8(l);
        LOGGER.error("########## GL ERROR ##########");
        LOGGER.error("@ {}", (Object)this.renderPhase);
        LOGGER.error("{}: {}", (Object)n, (Object)string);
    }

    public void setLogOnGlError() {
        GLFWErrorCallback gLFWErrorCallback = GLFW.glfwSetErrorCallback(this.loggingErrorCallback);
        if (gLFWErrorCallback != null) {
            gLFWErrorCallback.free();
        }
        TextureUtils.registerResourceListener();
    }

    public void setVsync(boolean bl) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        this.vsync = bl;
        GLFW.glfwSwapInterval(bl ? 1 : 0);
    }

    @Override
    public void close() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        this.closed = true;
        Callbacks.glfwFreeCallbacks(this.handle);
        this.loggingErrorCallback.close();
        GLFW.glfwDestroyWindow(this.handle);
        GLFW.glfwTerminate();
    }

    private void onWindowPosUpdate(long l, int n, int n2) {
        this.windowX = n;
        this.windowY = n2;
    }

    private void onFramebufferSizeUpdate(long l, int n, int n2) {
        if (l == this.handle) {
            int n3 = this.getFramebufferWidth();
            int n4 = this.getFramebufferHeight();
            if (n != 0 && n2 != 0) {
                this.framebufferWidth = n;
                this.framebufferHeight = n2;
                if (this.getFramebufferWidth() != n3 || this.getFramebufferHeight() != n4) {
                    this.mc.updateWindowSize();
                }
            }
        }
    }

    private void updateFramebufferSize() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        int[] nArray = new int[1];
        int[] nArray2 = new int[1];
        GLFW.glfwGetFramebufferSize(this.handle, nArray, nArray2);
        this.framebufferWidth = nArray[0];
        this.framebufferHeight = nArray2[0];
        if (this.framebufferHeight == 0 || this.framebufferWidth == 0) {
            EarlyProgressVisualization.INSTANCE.updateFBSize(this::lambda$updateFramebufferSize$4, this::lambda$updateFramebufferSize$5);
        }
    }

    private void onWindowSizeUpdate(long l, int n, int n2) {
        this.width = n;
        this.height = n2;
    }

    private void onWindowFocusUpdate(long l, boolean bl) {
        if (l == this.handle) {
            this.mc.setGameFocused(bl);
        }
    }

    private void onWindowEnterUpdate(long l, boolean bl) {
        if (bl) {
            this.mc.ignoreFirstMove();
        }
    }

    public void setFramerateLimit(int n) {
        this.framerateLimit = n;
    }

    public int getLimitFramerate() {
        return this.framerateLimit;
    }

    public void flipFrame() {
        RenderSystem.flipFrame(this.handle);
        if (this.fullscreen != this.lastFullscreen) {
            this.lastFullscreen = this.fullscreen;
            this.toggleFullscreen(this.vsync);
        }
    }

    public Optional<VideoMode> getVideoMode() {
        return this.videoMode;
    }

    public void setVideoMode(Optional<VideoMode> optional) {
        boolean bl = !optional.equals(this.videoMode);
        this.videoMode = optional;
        if (bl) {
            this.videoModeChanged = true;
        }
    }

    public void update() {
        if (this.fullscreen && this.videoModeChanged) {
            this.videoModeChanged = false;
            this.updateVideoMode();
            this.mc.updateWindowSize();
        }
    }

    private void updateVideoMode() {
        boolean bl;
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        boolean bl2 = bl = GLFW.glfwGetWindowMonitor(this.handle) != 0L;
        if (this.fullscreen) {
            Monitor monitor = this.monitorHandler.getMonitor(this);
            if (monitor == null) {
                LOGGER.warn("Failed to find suitable monitor for fullscreen mode");
                this.fullscreen = false;
            } else {
                VideoMode videoMode = monitor.getVideoModeOrDefault(this.videoMode);
                if (!bl) {
                    this.prevWindowX = this.windowX;
                    this.prevWindowY = this.windowY;
                    this.prevWindowWidth = this.width;
                    this.prevWindowHeight = this.height;
                }
                this.windowX = 0;
                this.windowY = 0;
                this.width = videoMode.getWidth();
                this.height = videoMode.getHeight();
                GLFW.glfwSetWindowMonitor(this.handle, monitor.getMonitorPointer(), this.windowX, this.windowY, this.width, this.height, videoMode.getRefreshRate());
            }
        } else {
            this.windowX = this.prevWindowX;
            this.windowY = this.prevWindowY;
            this.width = this.prevWindowWidth;
            this.height = this.prevWindowHeight;
            GLFW.glfwSetWindowMonitor(this.handle, 0L, this.windowX, this.windowY, this.width, this.height, -1);
        }
    }

    public void toggleFullscreen() {
        this.fullscreen = !this.fullscreen;
    }

    private void toggleFullscreen(boolean bl) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        try {
            this.updateVideoMode();
            this.mc.updateWindowSize();
            this.setVsync(bl);
            this.flipFrame();
        } catch (Exception exception) {
            LOGGER.error("Couldn't toggle fullscreen", (Throwable)exception);
        }
    }

    public int calcGuiScale(int n, boolean bl) {
        int n2;
        for (n2 = 1; n2 != n && n2 < this.framebufferWidth && n2 < this.framebufferHeight && this.framebufferWidth / (n2 + 1) >= 320 && this.framebufferHeight / (n2 + 1) >= 240; ++n2) {
        }
        if (bl && n2 % 2 != 0) {
            ++n2;
        }
        return n2;
    }

    public void setGuiScale(double d) {
        this.guiScaleFactor = d;
        int n = (int)((double)this.framebufferWidth / d);
        this.scaledWidth = (double)this.framebufferWidth / d > (double)n ? n + 1 : n;
        int n2 = (int)((double)this.framebufferHeight / d);
        this.scaledHeight = (double)this.framebufferHeight / d > (double)n2 ? n2 + 1 : n2;
    }

    public void setWindowTitle(String string) {
        GLFW.glfwSetWindowTitle(this.handle, string);
    }

    public long getHandle() {
        return this.handle;
    }

    public boolean isFullscreen() {
        return this.fullscreen;
    }

    public int getFramebufferWidth() {
        return this.framebufferWidth;
    }

    public int getFramebufferHeight() {
        return this.framebufferHeight;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getScaledWidth() {
        return this.scaledWidth;
    }

    public int getScaledHeight() {
        return this.scaledHeight;
    }

    public int scaledWidth() {
        int n = 0;
        int n2 = (int)((double)this.framebufferWidth / 2.0);
        n = (double)this.framebufferWidth / 2.0 > (double)n2 ? n2 + 1 : n2;
        return n;
    }

    public int height() {
        int n = 0;
        int n2 = (int)((double)this.height / 2.0);
        n = (double)this.height / 2.0 > (double)n2 ? n2 + 1 : n2;
        return n;
    }

    public int width() {
        int n = 0;
        int n2 = (int)((double)this.width / 2.0);
        n = (double)this.width / 2.0 > (double)n2 ? n2 + 1 : n2;
        return n;
    }

    public int scaledHeight() {
        int n = 0;
        int n2 = (int)((double)this.framebufferHeight / 2.0);
        n = (double)this.framebufferHeight / 2.0 > (double)n2 ? n2 + 1 : n2;
        return n;
    }

    public int getWindowX() {
        return this.windowX;
    }

    public int getWindowY() {
        return this.windowY;
    }

    public double getGuiScaleFactor() {
        return this.guiScaleFactor;
    }

    @Nullable
    public Monitor getMonitor() {
        return this.monitorHandler.getMonitor(this);
    }

    public void setRawMouseInput(boolean bl) {
        InputMappings.setRawMouseInput(this.handle, bl);
    }

    public void resizeFramebuffer(int n, int n2) {
        this.onFramebufferSizeUpdate(this.handle, n, n2);
    }

    public boolean isClosed() {
        return this.closed;
    }

    private void lambda$updateFramebufferSize$5(int n) {
        this.framebufferHeight = n;
    }

    private void lambda$updateFramebufferSize$4(int n) {
        this.framebufferWidth = n;
    }

    private long lambda$new$3(Monitor monitor) {
        return this.fullscreen && monitor != null ? monitor.getMonitorPointer() : 0L;
    }

    private static String lambda$new$2(String string) {
        return string;
    }

    private int lambda$new$1() {
        return this.height;
    }

    private int lambda$new$0() {
        return this.width;
    }

    public static class GlException
    extends UndeclaredException {
        private GlException(String string) {
            super(string);
        }
    }
}

