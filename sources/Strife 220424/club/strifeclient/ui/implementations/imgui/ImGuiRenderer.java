package club.strifeclient.ui.implementations.imgui;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

import club.strifeclient.ui.implementations.imgui.implementations.IImGuiInterface;
import club.strifeclient.ui.implementations.imgui.theme.IImGuiTheme;
import club.strifeclient.ui.implementations.imgui.theme.implementations.DefaultImGuiTheme;
import club.strifeclient.ui.implementations.imgui.theme.implementations.ExternaliaImGuiTheme;
import club.strifeclient.util.misc.MinecraftUtil;
import imgui.ImFontConfig;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import lombok.Setter;

public class ImGuiRenderer extends MinecraftUtil {

	private long windowHandle;

	@Setter
	private boolean shouldRender;

	private final ImGuiImplGlfw imGuiGlfw;
	private final ImGuiImplGl3 imGuiGl3;

	private IImGuiTheme theme;
	private IImGuiInterface guiInterface;

	public ImGuiRenderer() {
		imGuiGlfw = new ImGuiImplGlfw();
		imGuiGl3 = new ImGuiImplGl3();
		// dont try to init stuff here that wont exist before client startup (init stuff in the init() method)
	}

	private void loadConfig() {
		final ImGuiIO io = ImGui.getIO();

		io.setIniFilename(null);                               // We don't want to save .ini file
		io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard); // Enable Keyboard Controls
		io.addConfigFlags(ImGuiConfigFlags.DockingEnable);     // Enable Docking
		io.addConfigFlags(
			ImGuiConfigFlags.ViewportsEnable);   // Enable Multi-Viewport / Platform Windows
//        io.setConfigViewportsNoTaskBarIcon(true);

		theme.applyConfig(io);

		final ImFontConfig fontConfig = theme.applyFontConfig(io);
		// apply additional config flags and destroy just in case someone forgot to implement within the theme
		fontConfig.setPixelSnapH(true);
		fontConfig.destroy();

		if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
			final ImGuiStyle style = ImGui.getStyle();
			style.setWindowRounding(0.0f);
			style.setColor(ImGuiCol.WindowBg, ImGui.getColorU32(ImGuiCol.WindowBg, 1));
		}
	}

	public void init(final long handle) {
		guiInterface = mc.ingameGUI.getGuiImGui().getGuiInterface();
		theme = new ExternaliaImGuiTheme();

		ImGui.createContext();
		loadConfig();

		imGuiGlfw.init(handle, true);
		imGuiGl3.init();
		windowHandle = handle;
	}

	public void destroy() {
		imGuiGlfw.dispose();
		imGuiGl3.dispose();
		windowHandle = 0;
	}

	public void render() {
		theme.applyStyle();
		imGuiGlfw.newFrame();
		ImGui.newFrame();

		theme.preRender();
		guiInterface.render();
		theme.postRender();

		ImGui.render();

		imGuiGl3.renderDrawData(ImGui.getDrawData());

		if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
			final long backupWindowPtr = glfwGetCurrentContext();
			ImGui.updatePlatformWindows();
			ImGui.renderPlatformWindowsDefault();
			glfwMakeContextCurrent(backupWindowPtr);

		}
	}
}
