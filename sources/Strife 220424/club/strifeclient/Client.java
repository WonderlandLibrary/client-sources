package club.strifeclient;

import best.azura.eventbus.core.EventBus;
import club.strifeclient.command.CommandManager;
import club.strifeclient.config.ConfigManager;
import club.strifeclient.font.FontManager;
import club.strifeclient.module.ModuleManager;
import club.strifeclient.target.TargetManager;
import club.strifeclient.ui.implementations.imgui.ImGuiRenderer;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import org.lwjglx.opengl.Display;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public enum Client { INSTANCE;

    public static final String NAME = "Strife";
    public static String CUSTOM_NAME = NAME;
    public static final String BRANCH = "Development";
    public static final int BUILD = 220424;
    public static Path DIRECTORY;

    private final Minecraft mc = Minecraft.getMinecraft();
    private final EventBus eventBus = new EventBus();
    private final FontManager fontManager = new FontManager();
    private final ModuleManager moduleManager = new ModuleManager();
    private final CommandManager commandManager = new CommandManager();
    private final ConfigManager configManager = new ConfigManager();
    private final TargetManager targetManager = new TargetManager();
    private final ImGuiRenderer imGuiRenderer = new ImGuiRenderer();

    public void startup() {
        DIRECTORY = Paths.get(mc.mcDataDir.getAbsolutePath(), "Strife");
        fontManager.init();
        moduleManager.init();
        commandManager.init();
        configManager.init();
        mc.ingameGUI.init();
        imGuiRenderer.init(Display.Window.handle);
    }

    public void reload() {
        fontManager.deInit();
        moduleManager.deInit();
        commandManager.deInit();
        configManager.deInit();
        mc.ingameGUI.deInit();
    }

    public void shutdown() {

    }
}

