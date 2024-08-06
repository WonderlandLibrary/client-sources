package club.strifeclient.module.implementations.visual;

import club.strifeclient.Client;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.ColorSetting;
import club.strifeclient.setting.implementations.ModeSetting;
import club.strifeclient.ui.implementations.hud.clickgui.Theme;
import club.strifeclient.ui.implementations.hud.clickgui.themes.StrifeTheme;
import club.strifeclient.ui.implementations.imgui.GuiImGui;
import club.strifeclient.ui.implementations.imgui.implementations.DefaultImGuiInterface;
import club.strifeclient.ui.implementations.imgui.implementations.DevImGuiInterface;
import club.strifeclient.ui.implementations.imgui.implementations.IImGuiInterface;
import club.strifeclient.ui.implementations.imgui.theme.IImGuiTheme;
import club.strifeclient.ui.implementations.imgui.theme.implementations.DefaultImGuiTheme;
import club.strifeclient.ui.implementations.imgui.theme.implementations.ExternaliaImGuiTheme;
import lombok.Getter;
import org.lwjglx.input.Keyboard;
import org.lwjglx.opengl.Display;

import java.awt.*;

@ModuleInfo(
        name = "ClickGUI",
        description = "Customize ClickGUI settings.",
        keybind = Keyboard.KEY_RSHIFT,
        category = Category.VISUAL
)
public final class ClickGUI extends Module {

    private final ColorSetting colorSetting = new ColorSetting("Color", new Color(209, 50, 50));
    private final ModeSetting<ClickGuiMode> modeSetting = new ModeSetting<>("Mode", ClickGuiMode.IMGUI);
    private final ModeSetting<ImGuiTheme> imGuiThemeSetting = new ModeSetting<>("GUI Style", ImGuiTheme.EXTERNALIA,
            () -> modeSetting.getValue().equals(ClickGuiMode.IMGUI));
    private final ModeSetting<ImGuiMode> imGuiModeSetting = new ModeSetting<>("GUI Mode", ImGuiMode.DEV,
            () -> modeSetting.getValue().equals(ClickGuiMode.IMGUI));

    public ClickGUI() {
        colorSetting.addChangeCallback((old, change) -> {
            final Theme theme = mc.ingameGUI.getGuiClickGUI().getTheme();
            if (theme instanceof StrifeTheme)
                ((StrifeTheme) theme).setAccentColor(change.getValue());
        });
        imGuiThemeSetting.addChangeCallback((old, change) -> {
            Client.INSTANCE.getImGuiRenderer().destroy();
            mc.ingameGUI.getGuiImGui().getGuiInterface().setTheme(change.getValue().getTheme());
            Client.INSTANCE.getImGuiRenderer().init(Display.Window.handle);
        });
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        if (modeSetting.getValue().equals(ClickGuiMode.IMGUI)) {
            final GuiImGui guiImGui = mc.ingameGUI.getGuiImGui();
            if (!(mc.currentScreen instanceof GuiImGui)) guiImGui.setParent(mc.currentScreen);
            mc.displayGuiScreen(guiImGui);
        } else mc.displayGuiScreen(mc.ingameGUI.getActiveClickGUI());

        System.out.println("hi");
        setEnabled(false);
    }

    public enum ClickGuiMode implements SerializableEnum {
        STRIFE("Strife"),
        IMGUI("ImGui");
        final String name;

        ClickGuiMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    @Getter
    public enum ImGuiTheme implements SerializableEnum {
        STRIFE("Strife", new DefaultImGuiTheme()),
        EXTERNALIA("Externalia", new ExternaliaImGuiTheme());

        final String name;
        final IImGuiTheme theme;

        ImGuiTheme(final String name, final IImGuiTheme theme) {
            this.name = name;
            this.theme = theme;
        }
    }

    @Getter
    public enum ImGuiMode implements SerializableEnum {
        DEFAULT("Strife", new DefaultImGuiInterface()),
        DEV("Dev", new DevImGuiInterface());

        final String name;
        final IImGuiInterface guiInterface;

        ImGuiMode(final String name, final IImGuiInterface theme) {
            this.name = name;
            this.guiInterface = theme;
        }
    }
}
