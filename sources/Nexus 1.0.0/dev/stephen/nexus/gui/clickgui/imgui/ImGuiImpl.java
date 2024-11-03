package dev.stephen.nexus.gui.clickgui.imgui;

import dev.stephen.nexus.utils.render.ColorUtils;
import dev.stephen.nexus.utils.render.ThemeUtils;
import imgui.*;
import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImGuiImpl {

    private final static ImGuiImplGlfw glfw = new ImGuiImplGlfw();
    private final static ImGuiImplGl3 gl3 = new ImGuiImplGl3();

    private static ImGuiStyle imGuiStyle;

    public static void initialize(final long windowId) {
        ImGui.createContext();
        ImPlot.createContext();

        final ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);

        ImFontAtlas fontAtlas = io.getFonts();
        ImFontConfig fontConfig = new ImFontConfig();
        fontConfig.setMergeMode(false);
        fontConfig.setPixelSnapH(true);

        String fontPath = "assets/nexus/fonts/product_sans_medium.ttf";
        float fontSize = 18.0f;

        File tempFontFile = null;
        try (InputStream is = ImGuiImpl.class.getClassLoader().getResourceAsStream(fontPath)) {
            if (is == null) {
                throw new IOException("Failed to find font: " + fontPath);
            }

            tempFontFile = File.createTempFile("tempFont", ".ttf");
            try (FileOutputStream os = new FileOutputStream(tempFontFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }

            ImFont customFont = fontAtlas.addFontFromFileTTF(tempFontFile.getAbsolutePath(), fontSize, fontConfig);
            if (customFont == null) {
                System.err.println("Failed to load custom font from extracted file.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load custom font: " + e.getMessage());
        } finally {
            if (tempFontFile != null) {
                tempFontFile.deleteOnExit();
            }
        }

        io.setFontGlobalScale(1f);

        io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.setConfigFlags(ImGuiConfigFlags.ViewportsEnable);

        glfw.init(windowId, true);
        gl3.init();

        imGuiStyle = ImGui.getStyle();
        imGuiStyle.setWindowRounding(10.0f);
        imGuiStyle.setChildRounding(10.0f);
        imGuiStyle.setFrameRounding(10.0f);
        imGuiStyle.setPopupRounding(10.0f);
        imGuiStyle.setScrollbarRounding(10.0f);
        imGuiStyle.setGrabRounding(10.0f);
        setDarkTheme();
    }

    public static void render(final ImGuiRenderer renderer) {
        setDarkTheme();
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

    private static void setDarkTheme() {
        setColColor(ImGuiCol.Text, new Color(219, 237, 226, 224));
        setColColor(ImGuiCol.TextDisabled, new Color(219, 237, 226, 71));
        setColColor(ImGuiCol.WindowBg, new Color(22, 22, 22, 255));
        setColColor(ImGuiCol.ChildBg, new Color(0, 0, 0, 0));
        setColColor(ImGuiCol.PopupBg, new Color(69, 69, 70, 229));
        setColColor(ImGuiCol.Border, new Color(137, 122, 65, 41));
        setColColor(ImGuiCol.BorderShadow, new Color(0, 0, 0, 0));
        setColColor(ImGuiCol.FrameBg, new Color(39, 39, 39, 255));
        setColColor(ImGuiCol.TitleBgCollapsed, new Color(51, 56, 68, 191));
        setColColor(ImGuiCol.MenuBarBg, new Color(51, 56, 68, 120));
        setColColor(ImGuiCol.ScrollbarBg, new Color(48, 48, 48, 255));
        setColColor(ImGuiCol.ScrollbarGrab, new Color(23, 23, 23, 255));
        setColColor(ImGuiCol.Button, new Color(119, 196, 211, 36));
        setColColor(ImGuiCol.Separator, new Color(109, 109, 127, 127));
        setColColor(ImGuiCol.ResizeGrip, new Color(211, 119, 209, 10));
        setColColor(ImGuiCol.PlotLines, new Color(219, 237, 226, 161));
        setColColor(ImGuiCol.PlotHistogram, new Color(219, 237, 226, 161));
        setColColor(ImGuiCol.TableHeaderBg, new Color(47, 47, 47, 255));
        setColColor(ImGuiCol.TableBorderStrong, new Color(90, 90, 90, 255));
        setColColor(ImGuiCol.TableBorderLight, new Color(65, 65, 65, 255));
        setColColor(ImGuiCol.TableRowBg, new Color(0, 0, 0, 0));
        setColColor(ImGuiCol.TableRowBgAlt, new Color(255, 255, 255, 15));
        setColColor(ImGuiCol.DragDropTarget, new Color(255, 255, 0, 229));
        setColColor(ImGuiCol.NavWindowingHighlight, new Color(255, 255, 255, 178));
        setColColor(ImGuiCol.NavWindowingDimBg, new Color(204, 204, 204, 51));
        setColColor(ImGuiCol.ModalWindowDimBg, new Color(204, 204, 204, 89));

        Color mainColor = new Color(142, 197, 252);
        Color mainColorDarker = mainColor.darker();
        Color mainColorDarkerDarker = mainColor.darker().darker();
        Color mainColorDarkerDarkerDarker = mainColor.darker().darker().darker();
        Color mainColorDarkerDarkerDarkerDarkerDarker = mainColor.darker().darker().darker().darker().darker();

        setColColor(ImGuiCol.FrameBgHovered, ColorUtils.changeOpacity(mainColorDarkerDarker, 199));
        setColColor(ImGuiCol.FrameBgActive, mainColorDarker);
        setColColor(ImGuiCol.TitleBg, mainColorDarkerDarkerDarker);
        setColColor(ImGuiCol.TitleBgActive, mainColorDarkerDarker);// needs to be a bit lighter
        setColColor(ImGuiCol.ScrollbarGrabHovered, ColorUtils.changeOpacity(mainColorDarkerDarker, 199));
        setColColor(ImGuiCol.ScrollbarGrabActive, mainColorDarker);
        setColColor(ImGuiCol.CheckMark, mainColor);
        setColColor(ImGuiCol.SliderGrab, mainColor);
        setColColor(ImGuiCol.SliderGrabActive, mainColor);
        setColColor(ImGuiCol.ButtonHovered, ColorUtils.changeOpacity(mainColorDarker, 219));
        setColColor(ImGuiCol.ButtonActive, mainColorDarkerDarker);
        setColColor(ImGuiCol.Header, ColorUtils.changeOpacity(mainColorDarkerDarkerDarker, 194));
        setColColor(ImGuiCol.HeaderHovered, ColorUtils.changeOpacity(mainColorDarkerDarker, 219));
        setColColor(ImGuiCol.HeaderActive, mainColorDarker);
        setColColor(ImGuiCol.SeparatorHovered, ColorUtils.changeOpacity(mainColorDarker, 199));
        setColColor(ImGuiCol.SeparatorActive, mainColor);
        setColColor(ImGuiCol.ResizeGripHovered, ColorUtils.changeOpacity(mainColorDarker, 199));
        setColColor(ImGuiCol.ResizeGripActive, mainColorDarker);
        setColColor(ImGuiCol.Tab, ColorUtils.changeOpacity(mainColorDarkerDarker, 220));
        setColColor(ImGuiCol.TabHovered, mainColor);
        setColColor(ImGuiCol.TabActive, mainColor);
        setColColor(ImGuiCol.TabUnfocused, ColorUtils.changeOpacity(mainColorDarkerDarkerDarkerDarkerDarker, 248));
        setColColor(ImGuiCol.TabUnfocusedActive, mainColorDarker);
        setColColor(ImGuiCol.PlotLinesHovered, mainColorDarkerDarkerDarker);
        setColColor(ImGuiCol.PlotHistogramHovered, mainColorDarkerDarkerDarker);
        setColColor(ImGuiCol.TextSelectedBg, ColorUtils.changeOpacity(mainColorDarker, 199));
        setColColor(ImGuiCol.NavHighlight, mainColor);
    }

    private static void setColColor(int imGuiCol, Color color) {
        imGuiStyle.setColor(imGuiCol, (float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) color.getAlpha() / 255);
    }
}
