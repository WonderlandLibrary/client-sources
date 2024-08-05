package fr.dog.imgui;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;

public class ImGuiRendererImpl {
    private final static ImGuiImplGlfw glfw = new ImGuiImplGlfw();
    private final static ImGuiImplGl3 gl3 = new ImGuiImplGl3();

    public static void initialize(final long handler) {
        ImGui.createContext();
        ImPlot.createContext();

        final ImGuiIO data = ImGui.getIO();
        data.setIniFilename("imgui.ini");
        data.setFontGlobalScale(1f);
        data.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        data.setConfigFlags(ImGuiConfigFlags.ViewportsEnable);

        if (data.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final ImGuiStyle style = ImGui.getStyle();

            style.setFrameRounding(4f);
            style.setGrabRounding(4f);
            style.setWindowBorderSize(0f);
            style.setItemSpacing(8, 3);
            style.setGrabMinSize(12);
            style.setWindowRounding(6f);
        }

        glfw.init(handler, true);
        gl3.init();
    }

    public static void render(final ImGuiRenderer renderer) {
        glfw.newFrame();
        ImGui.newFrame();

        renderer.render(ImGui.getIO());

        ImGui.render();

        gl3.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long pointer = GLFW.glfwGetCurrentContext();

            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();

            GLFW.glfwMakeContextCurrent(pointer);
        }
    }
}