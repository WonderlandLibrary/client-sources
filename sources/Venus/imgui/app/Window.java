/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.app;

import imgui.ImGui;
import imgui.app.Color;
import imgui.app.Configuration;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import java.nio.IntBuffer;
import java.util.Objects;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL32;
import org.lwjgl.system.MemoryStack;

public abstract class Window {
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private String glslVersion = null;
    protected long handle;
    protected final Color colorBg = new Color(0.5f, 0.5f, 0.5f, 1.0f);

    protected void init(Configuration configuration) {
        this.initWindow(configuration);
        this.initImGui(configuration);
        this.imGuiGlfw.init(this.handle, false);
        this.imGuiGl3.init(this.glslVersion);
    }

    protected void dispose() {
        this.imGuiGl3.dispose();
        this.imGuiGlfw.dispose();
        this.disposeImGui();
        this.disposeWindow();
    }

    protected void initWindow(Configuration configuration) {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        this.decideGlGlslVersions();
        GLFW.glfwWindowHint(131076, 0);
        this.handle = GLFW.glfwCreateWindow(configuration.getWidth(), configuration.getHeight(), configuration.getTitle(), 0L, 0L);
        if (this.handle == 0L) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            IntBuffer intBuffer2 = memoryStack.mallocInt(1);
            GLFW.glfwGetWindowSize(this.handle, intBuffer, intBuffer2);
            GLFWVidMode gLFWVidMode = Objects.requireNonNull(GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()));
            GLFW.glfwSetWindowPos(this.handle, (gLFWVidMode.width() - intBuffer.get(0)) / 2, (gLFWVidMode.height() - intBuffer2.get(0)) / 2);
        }
        GLFW.glfwMakeContextCurrent(this.handle);
        GL.createCapabilities();
        GLFW.glfwSwapInterval(1);
        if (configuration.isFullScreen()) {
            GLFW.glfwMaximizeWindow(this.handle);
        } else {
            GLFW.glfwShowWindow(this.handle);
        }
        this.clearBuffer();
        this.renderBuffer();
        GLFW.glfwSetWindowSizeCallback(this.handle, new GLFWWindowSizeCallback(this){
            final Window this$0;
            {
                this.this$0 = window;
            }

            @Override
            public void invoke(long l, int n, int n2) {
                this.this$0.runFrame();
            }
        });
    }

    private void decideGlGlslVersions() {
        boolean bl = System.getProperty("os.name").toLowerCase().contains("mac");
        if (bl) {
            this.glslVersion = "#version 150";
            GLFW.glfwWindowHint(139266, 3);
            GLFW.glfwWindowHint(139267, 2);
            GLFW.glfwWindowHint(139272, 204801);
            GLFW.glfwWindowHint(139270, 1);
        } else {
            this.glslVersion = "#version 130";
            GLFW.glfwWindowHint(139266, 3);
            GLFW.glfwWindowHint(139267, 0);
        }
    }

    protected void initImGui(Configuration configuration) {
        ImGui.createContext();
    }

    protected void preProcess() {
    }

    protected void postProcess() {
    }

    protected void run() {
        while (!GLFW.glfwWindowShouldClose(this.handle)) {
            this.runFrame();
        }
    }

    protected void runFrame() {
        this.startFrame();
        this.preProcess();
        this.process();
        this.postProcess();
        this.endFrame();
    }

    public abstract void process();

    private void clearBuffer() {
        GL32.glClearColor(this.colorBg.getRed(), this.colorBg.getGreen(), this.colorBg.getBlue(), this.colorBg.getAlpha());
        GL32.glClear(16640);
    }

    protected void startFrame() {
        this.clearBuffer();
        this.imGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    protected void endFrame() {
        ImGui.render();
        this.imGuiGl3.renderDrawData(ImGui.getDrawData());
        if (ImGui.getIO().hasConfigFlags(1)) {
            long l = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(l);
        }
        this.renderBuffer();
    }

    private void renderBuffer() {
        GLFW.glfwSwapBuffers(this.handle);
        GLFW.glfwPollEvents();
    }

    protected void disposeImGui() {
        ImGui.destroyContext();
    }

    protected void disposeWindow() {
        Callbacks.glfwFreeCallbacks(this.handle);
        GLFW.glfwDestroyWindow(this.handle);
        GLFW.glfwTerminate();
        Objects.requireNonNull(GLFW.glfwSetErrorCallback(null)).free();
    }

    public final long getHandle() {
        return this.handle;
    }

    public final Color getColorBg() {
        return this.colorBg;
    }
}

