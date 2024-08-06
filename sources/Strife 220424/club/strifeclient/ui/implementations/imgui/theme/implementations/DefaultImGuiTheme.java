package club.strifeclient.ui.implementations.imgui.theme.implementations;

import club.strifeclient.ui.implementations.FontAwesomeIcons;
import club.strifeclient.ui.implementations.imgui.theme.IImGuiTheme;
import imgui.*;
import imgui.flag.ImGuiCol;

public class DefaultImGuiTheme implements IImGuiTheme {

    @Override
    public void preRender() {

    }

    @Override
    public void postRender() {

    }

    @Override
    public ImGuiStyle applyStyle() {
        final ImGuiStyle guiStyle = ImGui.getStyle();
        guiStyle.setTabRounding(0.0f);
        guiStyle.setWindowTitleAlign(0.5f, 0.5f);
        guiStyle.setAntiAliasedLines(true);

        // window/title
        guiStyle.setColor(ImGuiCol.TitleBgActive, ImGui.getColorU32(0.15f, 0.15f, 0.16f, 1.0f));
        guiStyle.setColor(ImGuiCol.TitleBgCollapsed, ImGui.getColorU32(0.09f, 0.09f, 0.09f, 1.0f));

        // resize grip (bottom right corner) & resize top
        guiStyle.setColor(ImGuiCol.ResizeGripActive, ImGui.getColorU32(0.86f, 0.15f, 0.15f, 1.0f));
        guiStyle.setColor(ImGuiCol.ResizeGripHovered, ImGui.getColorU32(0.73f, 0.11f, 0.11f, 1.0f));
        guiStyle.setColor(ImGuiCol.ResizeGrip, ImGui.getColorU32(0.5f, 0.11f, 0.11f, 1.0f));
        guiStyle.setColor(ImGuiCol.SeparatorActive, ImGui.getColorU32(0.86f, 0.15f, 0.15f, 1.0f));

        // buttons (dropdowns, collapse, etc.)
        guiStyle.setColor(ImGuiCol.Button, ImGui.getColorU32(0.86f, 0.15f, 0.15f, 1.0f));
        guiStyle.setColor(ImGuiCol.ButtonHovered, ImGui.getColorU32(0.94f, 0.27f, 0.27f, 1.0f));
        guiStyle.setColor(ImGuiCol.ButtonActive, ImGui.getColorU32(0.97f, 0.44f, 0.44f, 1.0f));

        // module dropdowns
        guiStyle.setColor(ImGuiCol.Header, ImGui.getColorU32(0.73f, 0.11f, 0.11f, 1.0f));
        guiStyle.setColor(ImGuiCol.HeaderHovered, ImGui.getColorU32(0.6f, 0.11f, 0.11f, 1.0f));
        guiStyle.setColor(ImGuiCol.HeaderActive, ImGui.getColorU32(0.5f, 0.11f, 0.11f, 1.0f));

        // settings
        guiStyle.setColor(ImGuiCol.FrameBg, ImGui.getColorU32(0.5f, 0.11f, 0.11f, 1.0f));
        guiStyle.setColor(ImGuiCol.FrameBgHovered, ImGui.getColorU32(0.6f, 0.11f, 0.11f, 1.0f));
        guiStyle.setColor(ImGuiCol.FrameBgActive, ImGui.getColorU32(0.73f, 0.11f, 0.11f, 1.0f));
        guiStyle.setColor(ImGuiCol.CheckMark, ImGui.getColorU32(0.09f, 0.09f, 0.09f, 1.0f));
        guiStyle.setColor(ImGuiCol.Separator, ImGui.getColorU32(0.09f, 0.09f, 0.09f, 1.0f));
        guiStyle.setColor(ImGuiCol.SliderGrab, ImGui.getColorU32(0.09f, 0.09f, 0.09f, 1.0f));
        guiStyle.setColor(ImGuiCol.SliderGrabActive, ImGui.getColorU32(0.15f, 0.15f, 0.15f, 1.0f));

        // tabs
        guiStyle.setColor(ImGuiCol.Tab, ImGui.getColorU32(0.5f, 0.11f, 0.11f, 1.0f));
        guiStyle.setColor(ImGuiCol.TabActive, ImGui.getColorU32(0.6f, 0.11f, 0.11f, 1.0f));
        guiStyle.setColor(ImGuiCol.TabHovered, ImGui.getColorU32(0.73f, 0.11f, 0.11f, 1.0f));

        return guiStyle;
    }

    @Override
    public ImGuiIO applyConfig(final ImGuiIO io) {
        return io;
    }

    @Override
    public ImFontConfig applyFontConfig(final ImGuiIO io) {
        final ImFontConfig fontConfig = new ImFontConfig();

        final ImFontAtlas fontAtlas = io.getFonts();

        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder(); // Glyphs ranges provide

        rangesBuilder.addRanges(fontAtlas.getGlyphRangesCyrillic());
        rangesBuilder.addRanges(fontAtlas.getGlyphRangesDefault());
        rangesBuilder.addRanges(fontAtlas.getGlyphRangesJapanese());
        rangesBuilder.addRanges(FontAwesomeIcons._IconRange);

//        final CustomFont defaultFont = Client.INSTANCE.getFontManager().getDefaultFont();

        try {
            final short[] glyphRanges = rangesBuilder.buildRanges();
//            fontAtlas.addFontFromMemoryTTF(loadFromResources(defaultFont.getPath()), 19, fontConfig, glyphRanges);
            fontAtlas.addFontDefault();
            fontConfig.setMergeMode(true); // When enabled, all fonts added with this config would be merged with the previously added font
            fontAtlas.addFontFromMemoryTTF(loadFromResources("assets/minecraft/strife/fonts/fa-regular-400.ttf"), 14, fontConfig, glyphRanges);
            fontAtlas.addFontFromMemoryTTF(loadFromResources("assets/minecraft/strife/fonts/fa-solid-900.ttf"), 14, fontConfig, glyphRanges);
        } catch (Exception e) {
            e.printStackTrace();
            fontAtlas.addFontDefault();
        }

        return fontConfig;
    }
}
