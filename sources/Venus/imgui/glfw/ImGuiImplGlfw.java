/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.glfw;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiPlatformIO;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.callback.ImPlatformFuncViewport;
import imgui.callback.ImPlatformFuncViewportFloat;
import imgui.callback.ImPlatformFuncViewportImVec2;
import imgui.callback.ImPlatformFuncViewportString;
import imgui.callback.ImPlatformFuncViewportSuppBoolean;
import imgui.callback.ImPlatformFuncViewportSuppImVec2;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.lwjgl3.glfw.ImGuiImplGlfwNative;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowFocusCallback;

public class ImGuiImplGlfw {
    private static final String OS = System.getProperty("os.name", "generic").toLowerCase();
    protected static final boolean IS_WINDOWS = OS.contains("win");
    protected static final boolean IS_APPLE = OS.contains("mac") || OS.contains("darwin");
    private long windowPtr;
    private boolean glfwHawWindowTopmost;
    private boolean glfwHasWindowAlpha;
    private boolean glfwHasPerMonitorDpi;
    private boolean glfwHasFocusWindow;
    private boolean glfwHasFocusOnShow;
    private boolean glfwHasMonitorWorkArea;
    private boolean glfwHasOsxWindowPosFix;
    private final int[] winWidth = new int[1];
    private final int[] winHeight = new int[1];
    private final int[] fbWidth = new int[1];
    private final int[] fbHeight = new int[1];
    private final long[] mouseCursors = new long[9];
    private final long[] keyOwnerWindows = new long[512];
    private final float[] emptyNavInputs = new float[21];
    private final boolean[] mouseJustPressed = new boolean[5];
    private final ImVec2 mousePosBackup = new ImVec2();
    private final double[] mouseX = new double[1];
    private final double[] mouseY = new double[1];
    private final int[] windowX = new int[1];
    private final int[] windowY = new int[1];
    private final int[] monitorX = new int[1];
    private final int[] monitorY = new int[1];
    private final int[] monitorWorkAreaX = new int[1];
    private final int[] monitorWorkAreaY = new int[1];
    private final int[] monitorWorkAreaWidth = new int[1];
    private final int[] monitorWorkAreaHeight = new int[1];
    private final float[] monitorContentScaleX = new float[1];
    private final float[] monitorContentScaleY = new float[1];
    private GLFWWindowFocusCallback prevUserCallbackWindowFocus = null;
    private GLFWMouseButtonCallback prevUserCallbackMouseButton = null;
    private GLFWScrollCallback prevUserCallbackScroll = null;
    private GLFWKeyCallback prevUserCallbackKey = null;
    private GLFWCharCallback prevUserCallbackChar = null;
    private GLFWMonitorCallback prevUserCallbackMonitor = null;
    private GLFWCursorEnterCallback prevUserCallbackCursorEnter = null;
    private boolean callbacksInstalled = false;
    private boolean wantUpdateMonitors = true;
    private double time = 0.0;
    private long mouseWindowPtr;

    public void mouseButtonCallback(long l, int n, int n2, int n3) {
        if (this.prevUserCallbackMouseButton != null && l == this.windowPtr) {
            this.prevUserCallbackMouseButton.invoke(l, n, n2, n3);
        }
        if (n2 == 1 && n >= 0 && n < this.mouseJustPressed.length) {
            this.mouseJustPressed[n] = true;
        }
    }

    public void scrollCallback(long l, double d, double d2) {
        if (this.prevUserCallbackScroll != null && l == this.windowPtr) {
            this.prevUserCallbackScroll.invoke(l, d, d2);
        }
        ImGuiIO imGuiIO = ImGui.getIO();
        imGuiIO.setMouseWheelH(imGuiIO.getMouseWheelH() + (float)d);
        imGuiIO.setMouseWheel(imGuiIO.getMouseWheel() + (float)d2);
    }

    public void keyCallback(long l, int n, int n2, int n3, int n4) {
        if (this.prevUserCallbackKey != null && l == this.windowPtr) {
            this.prevUserCallbackKey.invoke(l, n, n2, n3, n4);
        }
        ImGuiIO imGuiIO = ImGui.getIO();
        if (n >= 0 && n < this.keyOwnerWindows.length) {
            if (n3 == 1) {
                imGuiIO.setKeysDown(n, false);
                this.keyOwnerWindows[n] = l;
            } else if (n3 == 0) {
                imGuiIO.setKeysDown(n, true);
                this.keyOwnerWindows[n] = 0L;
            }
        }
        imGuiIO.setKeyCtrl(imGuiIO.getKeysDown(0) || imGuiIO.getKeysDown(0));
        imGuiIO.setKeyShift(imGuiIO.getKeysDown(1) || imGuiIO.getKeysDown(1));
        imGuiIO.setKeyAlt(imGuiIO.getKeysDown(1) || imGuiIO.getKeysDown(1));
        imGuiIO.setKeySuper(imGuiIO.getKeysDown(0) || imGuiIO.getKeysDown(0));
    }

    public void windowFocusCallback(long l, boolean bl) {
        if (this.prevUserCallbackWindowFocus != null && l == this.windowPtr) {
            this.prevUserCallbackWindowFocus.invoke(l, bl);
        }
        ImGui.getIO().addFocusEvent(bl);
    }

    public void cursorEnterCallback(long l, boolean bl) {
        if (this.prevUserCallbackCursorEnter != null && l == this.windowPtr) {
            this.prevUserCallbackCursorEnter.invoke(l, bl);
        }
        if (bl) {
            this.mouseWindowPtr = l;
        }
        if (!bl && this.mouseWindowPtr == l) {
            this.mouseWindowPtr = 0L;
        }
    }

    public void charCallback(long l, int n) {
        if (this.prevUserCallbackChar != null && l == this.windowPtr) {
            this.prevUserCallbackChar.invoke(l, n);
        }
        ImGuiIO imGuiIO = ImGui.getIO();
        imGuiIO.addInputCharacter(n);
    }

    public void monitorCallback(long l, int n) {
        this.wantUpdateMonitors = true;
    }

    public boolean init(long l, boolean bl) {
        this.windowPtr = l;
        this.detectGlfwVersionAndEnabledFeatures();
        ImGuiIO imGuiIO = ImGui.getIO();
        imGuiIO.addBackendFlags(1030);
        imGuiIO.setBackendPlatformName("imgui_java_impl_glfw");
        int[] nArray = new int[]{258, 263, 262, 265, 264, 266, 267, 268, 269, 260, 261, 259, 32, 257, 256, 335, 65, 67, 86, 88, 89, 90};
        imGuiIO.setKeyMap(nArray);
        imGuiIO.setGetClipboardTextFn(new ImStrSupplier(this, l){
            final long val$windowId;
            final ImGuiImplGlfw this$0;
            {
                this.this$0 = imGuiImplGlfw;
                this.val$windowId = l;
            }

            @Override
            public String get() {
                String string = GLFW.glfwGetClipboardString(this.val$windowId);
                return string != null ? string : "";
            }
        });
        imGuiIO.setSetClipboardTextFn(new ImStrConsumer(this, l){
            final long val$windowId;
            final ImGuiImplGlfw this$0;
            {
                this.this$0 = imGuiImplGlfw;
                this.val$windowId = l;
            }

            @Override
            public void accept(String string) {
                GLFW.glfwSetClipboardString(this.val$windowId, string);
            }
        });
        GLFWErrorCallback gLFWErrorCallback = GLFW.glfwSetErrorCallback(null);
        this.mouseCursors[0] = GLFW.glfwCreateStandardCursor(221185);
        this.mouseCursors[1] = GLFW.glfwCreateStandardCursor(221186);
        this.mouseCursors[2] = GLFW.glfwCreateStandardCursor(221185);
        this.mouseCursors[3] = GLFW.glfwCreateStandardCursor(221190);
        this.mouseCursors[4] = GLFW.glfwCreateStandardCursor(221189);
        this.mouseCursors[5] = GLFW.glfwCreateStandardCursor(221185);
        this.mouseCursors[6] = GLFW.glfwCreateStandardCursor(221185);
        this.mouseCursors[7] = GLFW.glfwCreateStandardCursor(221188);
        this.mouseCursors[8] = GLFW.glfwCreateStandardCursor(221185);
        GLFW.glfwSetErrorCallback(gLFWErrorCallback);
        if (bl) {
            this.callbacksInstalled = true;
            this.prevUserCallbackWindowFocus = GLFW.glfwSetWindowFocusCallback(l, this::windowFocusCallback);
            this.prevUserCallbackCursorEnter = GLFW.glfwSetCursorEnterCallback(l, this::cursorEnterCallback);
            this.prevUserCallbackMouseButton = GLFW.glfwSetMouseButtonCallback(l, this::mouseButtonCallback);
            this.prevUserCallbackScroll = GLFW.glfwSetScrollCallback(l, this::scrollCallback);
            this.prevUserCallbackKey = GLFW.glfwSetKeyCallback(l, this::keyCallback);
            this.prevUserCallbackChar = GLFW.glfwSetCharCallback(l, this::charCallback);
        }
        this.updateMonitors();
        this.prevUserCallbackMonitor = GLFW.glfwSetMonitorCallback(this::monitorCallback);
        ImGuiViewport imGuiViewport = ImGui.getMainViewport();
        imGuiViewport.setPlatformHandle(this.windowPtr);
        if (IS_WINDOWS) {
            imGuiViewport.setPlatformHandleRaw(GLFWNativeWin32.glfwGetWin32Window(l));
        }
        if (imGuiIO.hasConfigFlags(1)) {
            this.initPlatformInterface();
        }
        return false;
    }

    public void newFrame() {
        ImGuiIO imGuiIO = ImGui.getIO();
        GLFW.glfwGetWindowSize(this.windowPtr, this.winWidth, this.winHeight);
        GLFW.glfwGetFramebufferSize(this.windowPtr, this.fbWidth, this.fbHeight);
        imGuiIO.setDisplaySize(this.winWidth[0], this.winHeight[0]);
        if (this.winWidth[0] > 0 && this.winHeight[0] > 0) {
            float f = (float)this.fbWidth[0] / (float)this.winWidth[0];
            float f2 = (float)this.fbHeight[0] / (float)this.winHeight[0];
            imGuiIO.setDisplayFramebufferScale(f, f2);
        }
        if (this.wantUpdateMonitors) {
            this.updateMonitors();
        }
        double d = GLFW.glfwGetTime();
        imGuiIO.setDeltaTime(this.time > 0.0 ? (float)(d - this.time) : 0.016666668f);
        this.time = d;
        this.updateMousePosAndButtons();
        this.updateMouseCursor();
        this.updateGamepads();
    }

    public void dispose() {
        this.shutdownPlatformInterface();
        try {
            if (this.callbacksInstalled) {
                GLFW.glfwSetWindowFocusCallback(this.windowPtr, this.prevUserCallbackWindowFocus).free();
                GLFW.glfwSetCursorEnterCallback(this.windowPtr, this.prevUserCallbackCursorEnter).free();
                GLFW.glfwSetMouseButtonCallback(this.windowPtr, this.prevUserCallbackMouseButton).free();
                GLFW.glfwSetScrollCallback(this.windowPtr, this.prevUserCallbackScroll).free();
                GLFW.glfwSetKeyCallback(this.windowPtr, this.prevUserCallbackKey).free();
                GLFW.glfwSetCharCallback(this.windowPtr, this.prevUserCallbackChar).free();
                this.callbacksInstalled = false;
            }
            GLFW.glfwSetMonitorCallback(this.prevUserCallbackMonitor).free();
        } catch (NullPointerException nullPointerException) {
            // empty catch block
        }
        for (int i = 0; i < 9; ++i) {
            GLFW.glfwDestroyCursor(this.mouseCursors[i]);
        }
    }

    private void detectGlfwVersionAndEnabledFeatures() {
        int[] nArray = new int[1];
        int[] nArray2 = new int[1];
        int[] nArray3 = new int[1];
        GLFW.glfwGetVersion(nArray, nArray2, nArray3);
        int n = nArray[0] * 1000 + nArray2[0] * 100 + nArray3[0] * 10;
        this.glfwHawWindowTopmost = n >= 3200;
        this.glfwHasWindowAlpha = n >= 3300;
        this.glfwHasPerMonitorDpi = n >= 3300;
        this.glfwHasFocusWindow = n >= 3200;
        this.glfwHasFocusOnShow = n >= 3300;
        this.glfwHasMonitorWorkArea = n >= 3300;
    }

    private void updateMousePosAndButtons() {
        ImGuiIO imGuiIO = ImGui.getIO();
        for (int i = 0; i < 5; ++i) {
            imGuiIO.setMouseDown(i, this.mouseJustPressed[i] || GLFW.glfwGetMouseButton(this.windowPtr, i) != 0);
            this.mouseJustPressed[i] = false;
        }
        imGuiIO.getMousePos(this.mousePosBackup);
        imGuiIO.setMousePos(-3.4028235E38f, -3.4028235E38f);
        imGuiIO.setMouseHoveredViewport(0);
        ImGuiPlatformIO imGuiPlatformIO = ImGui.getPlatformIO();
        for (int i = 0; i < imGuiPlatformIO.getViewportsSize(); ++i) {
            long l;
            ImGuiViewport imGuiViewport = imGuiPlatformIO.getViewports(i);
            long l2 = imGuiViewport.getPlatformHandle();
            boolean bl = GLFW.glfwGetWindowAttrib(l2, 131073) != 0;
            long l3 = l = this.mouseWindowPtr == l2 || bl ? l2 : 0L;
            if (bl) {
                for (int j = 0; j < 5; ++j) {
                    imGuiIO.setMouseDown(j, GLFW.glfwGetMouseButton(l2, j) != 0);
                }
            }
            if (imGuiIO.getWantSetMousePos() && bl) {
                GLFW.glfwSetCursorPos(l2, this.mousePosBackup.x - imGuiViewport.getPosX(), this.mousePosBackup.y - imGuiViewport.getPosY());
            }
            if (l == 0L) continue;
            GLFW.glfwGetCursorPos(l, this.mouseX, this.mouseY);
            if (imGuiIO.hasConfigFlags(1)) {
                GLFW.glfwGetWindowPos(l2, this.windowX, this.windowY);
                imGuiIO.setMousePos((float)this.mouseX[0] + (float)this.windowX[0], (float)this.mouseY[0] + (float)this.windowY[0]);
                continue;
            }
            imGuiIO.setMousePos((float)this.mouseX[0], (float)this.mouseY[0]);
        }
    }

    private void updateMouseCursor() {
        boolean bl;
        ImGuiIO imGuiIO = ImGui.getIO();
        boolean bl2 = imGuiIO.hasConfigFlags(1);
        boolean bl3 = bl = GLFW.glfwGetInputMode(this.windowPtr, 208897) == 212995;
        if (bl2 || bl) {
            return;
        }
        int n = ImGui.getMouseCursor();
        ImGuiPlatformIO imGuiPlatformIO = ImGui.getPlatformIO();
        for (int i = 0; i < imGuiPlatformIO.getViewportsSize(); ++i) {
            long l = imGuiPlatformIO.getViewports(i).getPlatformHandle();
            if (n == -1 || imGuiIO.getMouseDrawCursor()) {
                GLFW.glfwSetInputMode(l, 208897, 212994);
                continue;
            }
            GLFW.glfwSetCursor(l, this.mouseCursors[n] != 0L ? this.mouseCursors[n] : this.mouseCursors[0]);
            GLFW.glfwSetInputMode(l, 208897, 212993);
        }
    }

    private void updateGamepads() {
        ImGuiIO imGuiIO = ImGui.getIO();
        if (!imGuiIO.hasConfigFlags(1)) {
            return;
        }
        imGuiIO.setNavInputs(this.emptyNavInputs);
        ByteBuffer byteBuffer = GLFW.glfwGetJoystickButtons(0);
        int n = byteBuffer.limit();
        FloatBuffer floatBuffer = GLFW.glfwGetJoystickAxes(0);
        int n2 = floatBuffer.limit();
        this.mapButton(0, 0, byteBuffer, n, imGuiIO);
        this.mapButton(1, 1, byteBuffer, n, imGuiIO);
        this.mapButton(3, 2, byteBuffer, n, imGuiIO);
        this.mapButton(2, 3, byteBuffer, n, imGuiIO);
        this.mapButton(4, 13, byteBuffer, n, imGuiIO);
        this.mapButton(5, 11, byteBuffer, n, imGuiIO);
        this.mapButton(6, 10, byteBuffer, n, imGuiIO);
        this.mapButton(7, 12, byteBuffer, n, imGuiIO);
        this.mapButton(12, 4, byteBuffer, n, imGuiIO);
        this.mapButton(13, 5, byteBuffer, n, imGuiIO);
        this.mapButton(14, 4, byteBuffer, n, imGuiIO);
        this.mapButton(15, 5, byteBuffer, n, imGuiIO);
        this.mapAnalog(8, 0, -0.3f, -0.9f, floatBuffer, n2, imGuiIO);
        this.mapAnalog(9, 0, 0.3f, 0.9f, floatBuffer, n2, imGuiIO);
        this.mapAnalog(10, 1, 0.3f, 0.9f, floatBuffer, n2, imGuiIO);
        this.mapAnalog(11, 1, -0.3f, -0.9f, floatBuffer, n2, imGuiIO);
        if (n2 > 0 && n > 0) {
            imGuiIO.addBackendFlags(1);
        } else {
            imGuiIO.removeBackendFlags(1);
        }
    }

    private void mapButton(int n, int n2, ByteBuffer byteBuffer, int n3, ImGuiIO imGuiIO) {
        if (n3 > n2 && byteBuffer.get(n2) == 1) {
            imGuiIO.setNavInputs(n, 1.0f);
        }
    }

    private void mapAnalog(int n, int n2, float f, float f2, FloatBuffer floatBuffer, int n3, ImGuiIO imGuiIO) {
        float f3 = n3 > n2 ? floatBuffer.get(n2) : f;
        if ((f3 = (f3 - f) / (f2 - f)) > 1.0f) {
            f3 = 1.0f;
        }
        if (imGuiIO.getNavInputs(n) < f3) {
            imGuiIO.setNavInputs(n, f3);
        }
    }

    private void updateMonitors() {
        ImGuiPlatformIO imGuiPlatformIO = ImGui.getPlatformIO();
        PointerBuffer pointerBuffer = GLFW.glfwGetMonitors();
        imGuiPlatformIO.resizeMonitors(0);
        for (int i = 0; i < pointerBuffer.limit(); ++i) {
            long l = pointerBuffer.get(i);
            GLFW.glfwGetMonitorPos(l, this.monitorX, this.monitorY);
            GLFWVidMode gLFWVidMode = GLFW.glfwGetVideoMode(l);
            float f = this.monitorX[0];
            float f2 = this.monitorY[0];
            float f3 = gLFWVidMode.width();
            float f4 = gLFWVidMode.height();
            if (this.glfwHasMonitorWorkArea) {
                GLFW.glfwGetMonitorWorkarea(l, this.monitorWorkAreaX, this.monitorWorkAreaY, this.monitorWorkAreaWidth, this.monitorWorkAreaHeight);
            }
            float f5 = 0.0f;
            float f6 = 0.0f;
            float f7 = 0.0f;
            float f8 = 0.0f;
            if (this.glfwHasMonitorWorkArea && this.monitorWorkAreaWidth[0] > 0 && this.monitorWorkAreaHeight[0] > 0) {
                f5 = this.monitorWorkAreaX[0];
                f6 = this.monitorWorkAreaY[0];
                f7 = this.monitorWorkAreaWidth[0];
                f8 = this.monitorWorkAreaHeight[0];
            }
            if (this.glfwHasPerMonitorDpi) {
                GLFW.glfwGetMonitorContentScale(l, this.monitorContentScaleX, this.monitorContentScaleY);
            }
            float f9 = this.monitorContentScaleX[0];
            imGuiPlatformIO.pushMonitors(f, f2, f3, f4, f5, f6, f7, f8, f9);
        }
        this.wantUpdateMonitors = false;
    }

    private void windowCloseCallback(long l) {
        ImGuiViewport imGuiViewport = ImGui.findViewportByPlatformHandle(l);
        imGuiViewport.setPlatformRequestClose(false);
    }

    private void windowPosCallback(long l, int n, int n2) {
        boolean bl;
        ImGuiViewport imGuiViewport = ImGui.findViewportByPlatformHandle(l);
        ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
        boolean bl2 = bl = ImGui.getFrameCount() <= imGuiViewportDataGlfw.ignoreWindowPosEventFrame + 1;
        if (bl) {
            return;
        }
        imGuiViewport.setPlatformRequestMove(false);
    }

    private void windowSizeCallback(long l, int n, int n2) {
        boolean bl;
        ImGuiViewport imGuiViewport = ImGui.findViewportByPlatformHandle(l);
        ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
        boolean bl2 = bl = ImGui.getFrameCount() <= imGuiViewportDataGlfw.ignoreWindowSizeEventFrame + 1;
        if (bl) {
            return;
        }
        imGuiViewport.setPlatformRequestResize(false);
    }

    private void initPlatformInterface() {
        ImGuiPlatformIO imGuiPlatformIO = ImGui.getPlatformIO();
        imGuiPlatformIO.setPlatformCreateWindow(new CreateWindowFunction(this, null));
        imGuiPlatformIO.setPlatformDestroyWindow(new DestroyWindowFunction(this, null));
        imGuiPlatformIO.setPlatformShowWindow(new ShowWindowFunction(null));
        imGuiPlatformIO.setPlatformGetWindowPos(new GetWindowPosFunction(null));
        imGuiPlatformIO.setPlatformSetWindowPos(new SetWindowPosFunction(null));
        imGuiPlatformIO.setPlatformGetWindowSize(new GetWindowSizeFunction(null));
        imGuiPlatformIO.setPlatformSetWindowSize(new SetWindowSizeFunction(this, null));
        imGuiPlatformIO.setPlatformSetWindowTitle(new SetWindowTitleFunction(null));
        imGuiPlatformIO.setPlatformSetWindowFocus(new SetWindowFocusFunction(this, null));
        imGuiPlatformIO.setPlatformGetWindowFocus(new GetWindowFocusFunction(null));
        imGuiPlatformIO.setPlatformGetWindowMinimized(new GetWindowMinimizedFunction(null));
        imGuiPlatformIO.setPlatformSetWindowAlpha(new SetWindowAlphaFunction(this, null));
        imGuiPlatformIO.setPlatformRenderWindow(new RenderWindowFunction(null));
        imGuiPlatformIO.setPlatformSwapBuffers(new SwapBuffersFunction(null));
        ImGuiViewport imGuiViewport = ImGui.getMainViewport();
        ImGuiViewportDataGlfw imGuiViewportDataGlfw = new ImGuiViewportDataGlfw(null);
        imGuiViewportDataGlfw.window = this.windowPtr;
        imGuiViewportDataGlfw.windowOwned = false;
        imGuiViewport.setPlatformUserData(imGuiViewportDataGlfw);
    }

    private void shutdownPlatformInterface() {
    }

    static boolean access$100(ImGuiImplGlfw imGuiImplGlfw) {
        return imGuiImplGlfw.glfwHasFocusOnShow;
    }

    static boolean access$200(ImGuiImplGlfw imGuiImplGlfw) {
        return imGuiImplGlfw.glfwHawWindowTopmost;
    }

    static long access$300(ImGuiImplGlfw imGuiImplGlfw) {
        return imGuiImplGlfw.windowPtr;
    }

    static void access$400(ImGuiImplGlfw imGuiImplGlfw, long l, int n, int n2) {
        imGuiImplGlfw.windowSizeCallback(l, n, n2);
    }

    static void access$500(ImGuiImplGlfw imGuiImplGlfw, long l, int n, int n2) {
        imGuiImplGlfw.windowPosCallback(l, n, n2);
    }

    static void access$600(ImGuiImplGlfw imGuiImplGlfw, long l) {
        imGuiImplGlfw.windowCloseCallback(l);
    }

    static long[] access$700(ImGuiImplGlfw imGuiImplGlfw) {
        return imGuiImplGlfw.keyOwnerWindows;
    }

    static boolean access$800(ImGuiImplGlfw imGuiImplGlfw) {
        return imGuiImplGlfw.glfwHasOsxWindowPosFix;
    }

    static boolean access$900(ImGuiImplGlfw imGuiImplGlfw) {
        return imGuiImplGlfw.glfwHasFocusWindow;
    }

    static boolean access$1000(ImGuiImplGlfw imGuiImplGlfw) {
        return imGuiImplGlfw.glfwHasWindowAlpha;
    }

    private static final class ImGuiViewportDataGlfw {
        long window;
        boolean windowOwned = false;
        int ignoreWindowPosEventFrame = -1;
        int ignoreWindowSizeEventFrame = -1;

        private ImGuiViewportDataGlfw() {
        }

        ImGuiViewportDataGlfw(1 var1_1) {
            this();
        }
    }

    private static final class SwapBuffersFunction
    extends ImPlatformFuncViewport {
        private SwapBuffersFunction() {
        }

        @Override
        public void accept(ImGuiViewport imGuiViewport) {
            ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
            GLFW.glfwMakeContextCurrent(imGuiViewportDataGlfw.window);
            GLFW.glfwSwapBuffers(imGuiViewportDataGlfw.window);
        }

        SwapBuffersFunction(1 var1_1) {
            this();
        }
    }

    private static final class RenderWindowFunction
    extends ImPlatformFuncViewport {
        private RenderWindowFunction() {
        }

        @Override
        public void accept(ImGuiViewport imGuiViewport) {
            ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
            GLFW.glfwMakeContextCurrent(imGuiViewportDataGlfw.window);
        }

        RenderWindowFunction(1 var1_1) {
            this();
        }
    }

    private final class SetWindowAlphaFunction
    extends ImPlatformFuncViewportFloat {
        final ImGuiImplGlfw this$0;

        private SetWindowAlphaFunction(ImGuiImplGlfw imGuiImplGlfw) {
            this.this$0 = imGuiImplGlfw;
        }

        @Override
        public void accept(ImGuiViewport imGuiViewport, float f) {
            if (ImGuiImplGlfw.access$1000(this.this$0)) {
                ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
                GLFW.glfwSetWindowOpacity(imGuiViewportDataGlfw.window, f);
            }
        }

        SetWindowAlphaFunction(ImGuiImplGlfw imGuiImplGlfw, 1 var2_2) {
            this(imGuiImplGlfw);
        }
    }

    private static final class GetWindowMinimizedFunction
    extends ImPlatformFuncViewportSuppBoolean {
        private GetWindowMinimizedFunction() {
        }

        @Override
        public boolean get(ImGuiViewport imGuiViewport) {
            ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
            return GLFW.glfwGetWindowAttrib(imGuiViewportDataGlfw.window, 131074) != 0;
        }

        GetWindowMinimizedFunction(1 var1_1) {
            this();
        }
    }

    private static final class GetWindowFocusFunction
    extends ImPlatformFuncViewportSuppBoolean {
        private GetWindowFocusFunction() {
        }

        @Override
        public boolean get(ImGuiViewport imGuiViewport) {
            ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
            return GLFW.glfwGetWindowAttrib(imGuiViewportDataGlfw.window, 131073) != 0;
        }

        GetWindowFocusFunction(1 var1_1) {
            this();
        }
    }

    private final class SetWindowFocusFunction
    extends ImPlatformFuncViewport {
        final ImGuiImplGlfw this$0;

        private SetWindowFocusFunction(ImGuiImplGlfw imGuiImplGlfw) {
            this.this$0 = imGuiImplGlfw;
        }

        @Override
        public void accept(ImGuiViewport imGuiViewport) {
            if (ImGuiImplGlfw.access$900(this.this$0)) {
                ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
                GLFW.glfwFocusWindow(imGuiViewportDataGlfw.window);
            }
        }

        SetWindowFocusFunction(ImGuiImplGlfw imGuiImplGlfw, 1 var2_2) {
            this(imGuiImplGlfw);
        }
    }

    private static final class SetWindowTitleFunction
    extends ImPlatformFuncViewportString {
        private SetWindowTitleFunction() {
        }

        @Override
        public void accept(ImGuiViewport imGuiViewport, String string) {
            ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
            GLFW.glfwSetWindowTitle(imGuiViewportDataGlfw.window, string);
        }

        SetWindowTitleFunction(1 var1_1) {
            this();
        }
    }

    private final class SetWindowSizeFunction
    extends ImPlatformFuncViewportImVec2 {
        private final int[] x;
        private final int[] y;
        private final int[] width;
        private final int[] height;
        final ImGuiImplGlfw this$0;

        private SetWindowSizeFunction(ImGuiImplGlfw imGuiImplGlfw) {
            this.this$0 = imGuiImplGlfw;
            this.x = new int[1];
            this.y = new int[1];
            this.width = new int[1];
            this.height = new int[1];
        }

        @Override
        public void accept(ImGuiViewport imGuiViewport, ImVec2 imVec2) {
            ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
            if (IS_APPLE && !ImGuiImplGlfw.access$800(this.this$0)) {
                GLFW.glfwGetWindowPos(imGuiViewportDataGlfw.window, this.x, this.y);
                GLFW.glfwGetWindowSize(imGuiViewportDataGlfw.window, this.width, this.height);
                GLFW.glfwSetWindowPos(imGuiViewportDataGlfw.window, this.x[0], this.y[0] - this.height[0] + (int)imVec2.y);
            }
            imGuiViewportDataGlfw.ignoreWindowSizeEventFrame = ImGui.getFrameCount();
            GLFW.glfwSetWindowSize(imGuiViewportDataGlfw.window, (int)imVec2.x, (int)imVec2.y);
        }

        SetWindowSizeFunction(ImGuiImplGlfw imGuiImplGlfw, 1 var2_2) {
            this(imGuiImplGlfw);
        }
    }

    private static final class GetWindowSizeFunction
    extends ImPlatformFuncViewportSuppImVec2 {
        private final int[] width = new int[1];
        private final int[] height = new int[1];

        private GetWindowSizeFunction() {
        }

        @Override
        public void get(ImGuiViewport imGuiViewport, ImVec2 imVec2) {
            ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
            GLFW.glfwGetWindowSize(imGuiViewportDataGlfw.window, this.width, this.height);
            imVec2.x = this.width[0];
            imVec2.y = this.height[0];
        }

        GetWindowSizeFunction(1 var1_1) {
            this();
        }
    }

    private static final class SetWindowPosFunction
    extends ImPlatformFuncViewportImVec2 {
        private SetWindowPosFunction() {
        }

        @Override
        public void accept(ImGuiViewport imGuiViewport, ImVec2 imVec2) {
            ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
            imGuiViewportDataGlfw.ignoreWindowPosEventFrame = ImGui.getFrameCount();
            GLFW.glfwSetWindowPos(imGuiViewportDataGlfw.window, (int)imVec2.x, (int)imVec2.y);
        }

        SetWindowPosFunction(1 var1_1) {
            this();
        }
    }

    private static final class GetWindowPosFunction
    extends ImPlatformFuncViewportSuppImVec2 {
        private final int[] posX = new int[1];
        private final int[] posY = new int[1];

        private GetWindowPosFunction() {
        }

        @Override
        public void get(ImGuiViewport imGuiViewport, ImVec2 imVec2) {
            ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
            GLFW.glfwGetWindowPos(imGuiViewportDataGlfw.window, this.posX, this.posY);
            imVec2.x = this.posX[0];
            imVec2.y = this.posY[0];
        }

        GetWindowPosFunction(1 var1_1) {
            this();
        }
    }

    private static final class ShowWindowFunction
    extends ImPlatformFuncViewport {
        private ShowWindowFunction() {
        }

        @Override
        public void accept(ImGuiViewport imGuiViewport) {
            ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
            if (IS_WINDOWS && imGuiViewport.hasFlags(1)) {
                ImGuiImplGlfwNative.win32hideFromTaskBar(imGuiViewport.getPlatformHandleRaw());
            }
            GLFW.glfwShowWindow(imGuiViewportDataGlfw.window);
        }

        ShowWindowFunction(1 var1_1) {
            this();
        }
    }

    private final class DestroyWindowFunction
    extends ImPlatformFuncViewport {
        final ImGuiImplGlfw this$0;

        private DestroyWindowFunction(ImGuiImplGlfw imGuiImplGlfw) {
            this.this$0 = imGuiImplGlfw;
        }

        @Override
        public void accept(ImGuiViewport imGuiViewport) {
            ImGuiViewportDataGlfw imGuiViewportDataGlfw = (ImGuiViewportDataGlfw)imGuiViewport.getPlatformUserData();
            if (imGuiViewportDataGlfw != null && imGuiViewportDataGlfw.windowOwned) {
                for (int i = 0; i < ImGuiImplGlfw.access$700(this.this$0).length; ++i) {
                    if (ImGuiImplGlfw.access$700(this.this$0)[i] != imGuiViewportDataGlfw.window) continue;
                    this.this$0.keyCallback(imGuiViewportDataGlfw.window, i, 0, 0, 0);
                }
                Callbacks.glfwFreeCallbacks(imGuiViewportDataGlfw.window);
                GLFW.glfwDestroyWindow(imGuiViewportDataGlfw.window);
            }
            imGuiViewport.setPlatformUserData(null);
            imGuiViewport.setPlatformHandle(0L);
        }

        DestroyWindowFunction(ImGuiImplGlfw imGuiImplGlfw, 1 var2_2) {
            this(imGuiImplGlfw);
        }
    }

    private final class CreateWindowFunction
    extends ImPlatformFuncViewport {
        final ImGuiImplGlfw this$0;

        private CreateWindowFunction(ImGuiImplGlfw imGuiImplGlfw) {
            this.this$0 = imGuiImplGlfw;
        }

        @Override
        public void accept(ImGuiViewport imGuiViewport) {
            ImGuiViewportDataGlfw imGuiViewportDataGlfw = new ImGuiViewportDataGlfw(null);
            imGuiViewport.setPlatformUserData(imGuiViewportDataGlfw);
            GLFW.glfwWindowHint(131076, 0);
            GLFW.glfwWindowHint(131073, 0);
            if (ImGuiImplGlfw.access$100(this.this$0)) {
                GLFW.glfwWindowHint(131084, 0);
            }
            GLFW.glfwWindowHint(131077, imGuiViewport.hasFlags(1) ? 0 : 1);
            if (ImGuiImplGlfw.access$200(this.this$0)) {
                GLFW.glfwWindowHint(131079, imGuiViewport.hasFlags(1) ? 1 : 0);
            }
            imGuiViewportDataGlfw.window = GLFW.glfwCreateWindow((int)imGuiViewport.getSizeX(), (int)imGuiViewport.getSizeY(), "No Title Yet", 0L, ImGuiImplGlfw.access$300(this.this$0));
            imGuiViewportDataGlfw.windowOwned = true;
            imGuiViewport.setPlatformHandle(imGuiViewportDataGlfw.window);
            if (IS_WINDOWS) {
                imGuiViewport.setPlatformHandleRaw(GLFWNativeWin32.glfwGetWin32Window(imGuiViewportDataGlfw.window));
            }
            GLFW.glfwSetWindowPos(imGuiViewportDataGlfw.window, (int)imGuiViewport.getPosX(), (int)imGuiViewport.getPosY());
            GLFW.glfwSetMouseButtonCallback(imGuiViewportDataGlfw.window, this.this$0::mouseButtonCallback);
            GLFW.glfwSetScrollCallback(imGuiViewportDataGlfw.window, this.this$0::scrollCallback);
            GLFW.glfwSetKeyCallback(imGuiViewportDataGlfw.window, this.this$0::keyCallback);
            GLFW.glfwSetCharCallback(imGuiViewportDataGlfw.window, this.this$0::charCallback);
            GLFW.glfwSetWindowCloseCallback(imGuiViewportDataGlfw.window, arg_0 -> CreateWindowFunction.lambda$accept$0(this.this$0, arg_0));
            GLFW.glfwSetWindowPosCallback(imGuiViewportDataGlfw.window, (arg_0, arg_1, arg_2) -> CreateWindowFunction.lambda$accept$1(this.this$0, arg_0, arg_1, arg_2));
            GLFW.glfwSetWindowSizeCallback(imGuiViewportDataGlfw.window, (arg_0, arg_1, arg_2) -> CreateWindowFunction.lambda$accept$2(this.this$0, arg_0, arg_1, arg_2));
            GLFW.glfwMakeContextCurrent(imGuiViewportDataGlfw.window);
            GLFW.glfwSwapInterval(0);
        }

        private static void lambda$accept$2(ImGuiImplGlfw imGuiImplGlfw, long l, int n, int n2) {
            ImGuiImplGlfw.access$400(imGuiImplGlfw, l, n, n2);
        }

        private static void lambda$accept$1(ImGuiImplGlfw imGuiImplGlfw, long l, int n, int n2) {
            ImGuiImplGlfw.access$500(imGuiImplGlfw, l, n, n2);
        }

        private static void lambda$accept$0(ImGuiImplGlfw imGuiImplGlfw, long l) {
            ImGuiImplGlfw.access$600(imGuiImplGlfw, l);
        }

        CreateWindowFunction(ImGuiImplGlfw imGuiImplGlfw, 1 var2_2) {
            this(imGuiImplGlfw);
        }
    }
}

