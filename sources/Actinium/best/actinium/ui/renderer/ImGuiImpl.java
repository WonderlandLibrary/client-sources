package best.actinium.ui.renderer;

import imgui.*;
import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.compress.utils.IOUtils;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;

public class ImGuiImpl {

    private final static ImGuiImplGlfw glfw = new ImGuiImplGlfw();
    private final static ImGuiImplGl3 gl3 = new ImGuiImplGl3();

    private static ImGuiStyle imGuiStyle;

    public static void initialize(final long windowId) {
        ImGui.createContext();
        ImPlot.createContext();

        final ImGuiIO data = ImGui.getIO();
        data.setIniFilename(null);

        final ImFontConfig fontConfig = applyFont(data);

        fontConfig.setPixelSnapH(true);
        fontConfig.destroy();

        data.setFontGlobalScale(1f);
        data.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        data.setConfigFlags(ImGuiConfigFlags.ViewportsEnable);

        glfw.init(windowId, true);
        gl3.init();
    }

    public static void render(final ImGuiRenderer renderer) {

        if (imGuiStyle == null)
            imGuiStyle = ImGui.getStyle();

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

    public static ImFontConfig applyFont(ImGuiIO io){
        final ImFontConfig fontConfig = new ImFontConfig();
        final ImFontAtlas fontAtlas = io.getFonts();
        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder(); // Glyphs ranges provide

        rangesBuilder.addRanges(fontAtlas.getGlyphRangesCyrillic());
        rangesBuilder.addRanges(fontAtlas.getGlyphRangesDefault());
        rangesBuilder.addRanges(fontAtlas.getGlyphRangesJapanese());

        try {
            final short[] glyphRanges = rangesBuilder.buildRanges();
            fontAtlas.addFontFromMemoryTTF(loadFromResources("actinium/fonts/product-sans-regular.ttf"), 18, fontConfig, glyphRanges);
        } catch (Exception e) {
            fontAtlas.addFontDefault();
        }

        return fontConfig;
    }

    static byte[] loadFromResources(String path) {
        try {
            return IOUtils.toByteArray(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(path)).getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}