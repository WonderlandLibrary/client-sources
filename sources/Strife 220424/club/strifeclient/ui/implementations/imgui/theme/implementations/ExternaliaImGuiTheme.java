package club.strifeclient.ui.implementations.imgui.theme.implementations;

import club.strifeclient.ui.implementations.FontAwesomeIcons;
import club.strifeclient.ui.implementations.imgui.theme.IImGuiTheme;
import imgui.ImFontAtlas;
import imgui.ImFontConfig;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;


public class ExternaliaImGuiTheme implements IImGuiTheme {

	@Override
	public void preRender() {

	}

	@Override
	public void postRender() {

	}

	@Override
	public ImGuiStyle applyStyle() {
		final ImGuiStyle guiStyle = ImGui.getStyle();
		guiStyle.setWindowRounding(5);
		guiStyle.setChildRounding(5F);
		guiStyle.setFrameRounding(5);
		guiStyle.setItemSpacing(8, 8);
		guiStyle.setScrollbarSize(10);
        guiStyle.setScrollbarRounding(5);

		guiStyle.setColor(ImGuiCol.Text, ImGui.getColorU32(1.00f, 1.00f, 1.00f, 1.00f));
		guiStyle.setColor(ImGuiCol.TextDisabled, ImGui.getColorU32(0.50f, 0.50f, 0.50f, 1.00f));
		guiStyle.setColor(ImGuiCol.WindowBg, ImGui.getColorU32(0.06f, 0.06f, 0.06f, 0.96f));
		guiStyle.setColor(ImGuiCol.ChildBg, ImGui.getColorU32(0.11f, 0.11f, 0.14f, 1.00f));
		guiStyle.setColor(ImGuiCol.PopupBg, ImGui.getColorU32(0.09f, 0.09f, 0.09f, 1.00f));
		guiStyle.setColor(ImGuiCol.Border, ImGui.getColorU32(0.32f, 0.32f, 0.58f, 0.30f));
		guiStyle.setColor(ImGuiCol.BorderShadow, ImGui.getColorU32(0.17f, 0.00f, 0.52f, 0.26f));
		guiStyle.setColor(ImGuiCol.FrameBg, ImGui.getColorU32(0.24f, 0.27f, 0.38f, 0.54f));
		guiStyle.setColor(ImGuiCol.FrameBgHovered, ImGui.getColorU32(0.29f, 0.37f, 0.62f, 0.54f));
		guiStyle.setColor(ImGuiCol.FrameBgActive, ImGui.getColorU32(0.33f, 0.33f, 0.67f, 1.00f));
		guiStyle.setColor(ImGuiCol.TitleBg, ImGui.getColorU32(0.33f, 0.33f, 0.68f, 1.00f));
		guiStyle.setColor(ImGuiCol.TitleBgActive, ImGui.getColorU32(0.33f, 0.33f, 0.67f, 1.00f));
		guiStyle.setColor(ImGuiCol.TitleBgCollapsed, ImGui.getColorU32(0.00f, 0.00f, 0.00f, 0.51f));
		guiStyle.setColor(ImGuiCol.MenuBarBg, ImGui.getColorU32(0.14f, 0.14f, 0.14f, 1.00f));
		guiStyle.setColor(ImGuiCol.ScrollbarBg, ImGui.getColorU32(0.02f, 0.02f, 0.02f, 0.53f));
		guiStyle.setColor(ImGuiCol.ScrollbarGrab, ImGui.getColorU32(0.31f, 0.31f, 0.31f, 1.00f));
		guiStyle.setColor(ImGuiCol.ScrollbarGrabHovered, ImGui.getColorU32(0.41f, 0.41f, 0.41f, 1.00f));
		guiStyle.setColor(ImGuiCol.ScrollbarGrabActive, ImGui.getColorU32(0.51f, 0.51f, 0.51f, 1.00f));
		guiStyle.setColor(ImGuiCol.CheckMark, ImGui.getColorU32(0.81f, 0.66f, 1.00f, 1.00f));
		guiStyle.setColor(ImGuiCol.SliderGrab, ImGui.getColorU32(0.24f, 0.52f, 0.88f, 1.00f));
		guiStyle.setColor(ImGuiCol.SliderGrabActive, ImGui.getColorU32(0.26f, 0.59f, 0.98f, 1.00f));
		guiStyle.setColor(ImGuiCol.Button, ImGui.getColorU32(0.35f, 0.37f, 0.48f, 0.40f));
		guiStyle.setColor(ImGuiCol.ButtonHovered, ImGui.getColorU32(0.33f, 0.35f, 0.49f, 1.00f));
		guiStyle.setColor(ImGuiCol.ButtonActive, ImGui.getColorU32(0.33f, 0.33f, 0.67f, 1.00f));
		guiStyle.setColor(ImGuiCol.Header, ImGui.getColorU32(0.42f, 0.32f, 0.67f, 1.00f));
		guiStyle.setColor(ImGuiCol.HeaderHovered, ImGui.getColorU32(0.50f, 0.41f, 0.73f, 1.00f));
		guiStyle.setColor(ImGuiCol.HeaderActive, ImGui.getColorU32(0.33f, 0.33f, 0.67f, 1.00f));
		guiStyle.setColor(ImGuiCol.Separator, ImGui.getColorU32(0.43f, 0.43f, 0.50f, 0.50f));
		guiStyle.setColor(ImGuiCol.SeparatorHovered, ImGui.getColorU32(0.10f, 0.40f, 0.75f, 0.78f));
		guiStyle.setColor(ImGuiCol.SeparatorActive, ImGui.getColorU32(0.10f, 0.40f, 0.75f, 1.00f));
		guiStyle.setColor(ImGuiCol.ResizeGrip, ImGui.getColorU32(0.26f, 0.59f, 0.98f, 0.20f));
		guiStyle.setColor(ImGuiCol.ResizeGripHovered, ImGui.getColorU32(0.26f, 0.59f, 0.98f, 0.67f));
		guiStyle.setColor(ImGuiCol.ResizeGripActive, ImGui.getColorU32(0.26f, 0.59f, 0.98f, 0.95f));
		guiStyle.setColor(ImGuiCol.Tab, ImGui.getColorU32(0.58f, 0.50f, 1.00f, 0.35f));
		guiStyle.setColor(ImGuiCol.TabHovered, ImGui.getColorU32(0.38f, 0.29f, 0.84f, 1.00f));
		guiStyle.setColor(ImGuiCol.TabActive, ImGui.getColorU32(0.33f, 0.24f, 0.80f, 1.00f));
		guiStyle.setColor(ImGuiCol.TabUnfocused, ImGui.getColorU32(0.07f, 0.10f, 0.15f, 0.97f));
		guiStyle.setColor(ImGuiCol.TabUnfocusedActive, ImGui.getColorU32(0.14f, 0.26f, 0.42f, 1.00f));
		guiStyle.setColor(ImGuiCol.PlotLines, ImGui.getColorU32(0.61f, 0.61f, 0.61f, 1.00f));
		guiStyle.setColor(ImGuiCol.PlotLinesHovered, ImGui.getColorU32(1.00f, 0.43f, 0.35f, 1.00f));
		guiStyle.setColor(ImGuiCol.PlotHistogram, ImGui.getColorU32(0.90f, 0.70f, 0.00f, 1.00f));
		guiStyle.setColor(ImGuiCol.PlotHistogramHovered, ImGui.getColorU32(1.00f, 0.60f, 0.00f, 1.00f));
		guiStyle.setColor(ImGuiCol.TableHeaderBg, ImGui.getColorU32(0.19f, 0.19f, 0.20f, 1.00f));
		guiStyle.setColor(ImGuiCol.TableBorderStrong, ImGui.getColorU32(0.31f, 0.31f, 0.35f, 1.00f));
		guiStyle.setColor(ImGuiCol.TableBorderLight, ImGui.getColorU32(0.23f, 0.23f, 0.25f, 1.00f));
		guiStyle.setColor(ImGuiCol.TableRowBg, ImGui.getColorU32(0.00f, 0.00f, 0.00f, 0.00f));
		guiStyle.setColor(ImGuiCol.TableRowBgAlt, ImGui.getColorU32(1.00f, 1.00f, 1.00f, 0.06f));
		guiStyle.setColor(ImGuiCol.TextSelectedBg, ImGui.getColorU32(0.26f, 0.59f, 0.98f, 0.35f));
		guiStyle.setColor(ImGuiCol.DragDropTarget, ImGui.getColorU32(1.00f, 1.00f, 0.00f, 0.90f));
		guiStyle.setColor(ImGuiCol.NavHighlight, ImGui.getColorU32(0.26f, 0.59f, 0.98f, 1.00f));
		guiStyle.setColor(ImGuiCol.NavWindowingHighlight, ImGui.getColorU32(1.00f, 1.00f, 1.00f, 0.70f));
		guiStyle.setColor(ImGuiCol.NavWindowingDimBg, ImGui.getColorU32(0.80f, 0.80f, 0.80f, 0.20f));
		guiStyle.setColor(ImGuiCol.ModalWindowDimBg, ImGui.getColorU32(0.80f, 0.80f, 0.80f, 0.35f));

		return guiStyle;
	}

	@Override
	public ImGuiIO applyConfig(ImGuiIO io) {
		return io;
	}

	@Override
	public ImFontConfig applyFontConfig(ImGuiIO io) {
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
			fontAtlas.addFontFromMemoryTTF(
				loadFromResources("assets/minecraft/strife/fonts/CircularStd-Book.ttf"), 19,
				fontConfig, glyphRanges);
			fontConfig.setMergeMode(
				true); // When enabled, all fonts added with this config would be merged with the
			// previously added font
			fontAtlas.addFontFromMemoryTTF(loadFromResources(
				"assets/minecraft/strife/fonts/fa-regular-400.ttf"), 14, fontConfig, glyphRanges);
			fontAtlas.addFontFromMemoryTTF(loadFromResources(
				"assets/minecraft/strife/fonts/fa-solid-900.ttf"), 14, fontConfig, glyphRanges);
		} catch (Exception e) {
			e.printStackTrace();
			fontAtlas.addFontDefault();
		}

		fontConfig.setPixelSnapH(true);

		return fontConfig;
	}
}
