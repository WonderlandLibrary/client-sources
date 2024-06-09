package io.github.liticane.clients.ui.imgui.renderer;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;

public class ImImpl {
    private final static ImGuiImplGlfw glfw = new ImGuiImplGlfw();
    private final static ImGuiImplGl3 gl3 = new ImGuiImplGl3();

    public static void initialize(final long windowId) {
        ImGui.createContext();
        ImPlot.createContext();

        final ImGuiIO data = ImGui.getIO();
        data.setIniFilename("interface.ini");

        data.setFontGlobalScale(1.0F);
        data.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        data.setConfigFlags(ImGuiConfigFlags.ViewportsEnable);

        glfw.init(windowId, true);
        gl3.init();
    }

    public static void render(final ImRenderer renderer) {
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
