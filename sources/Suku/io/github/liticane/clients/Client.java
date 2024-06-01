package io.github.liticane.clients;

import io.github.liticane.clients.ui.imgui.renderer.ImImpl;
import io.github.liticane.clients.config.ConfigManager;
import io.github.liticane.clients.feature.event.api.EventManager;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.theme.ThemeManager;
import io.github.liticane.clients.util.interfaces.IMethods;
import io.github.liticane.clients.util.misc.ReflectionUtil;
import lombok.Getter;
import lombok.Setter;
import me.Godwhitelight.FontUtil;
import org.lwjglx.Sys;
import org.lwjglx.opengl.Display;
import io.github.liticane.clients.feature.command.Command;
import io.github.liticane.clients.feature.command.CommandManager;
import io.github.liticane.clients.feature.module.ModuleManager;

@Getter
public enum Client implements IMethods {
    INSTANCE;

    public static final String NAME = "Suku";
    public static final String VERSION = "0.1";

    public static final boolean DEVELOPMENT_SWITCH = true;

    private EventManager eventManager;
    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private ConfigManager configManager;

    private ThemeManager themeManager;

    public void initClient() {
        Display.setTitle(NAME + " " + VERSION + " - Minecraft 1.8.9" + " - LWJGL " + Sys.getVersion());

        mc.settings.guiScale = 2;
        mc.settings.ofFastRender = false;
        mc.settings.ofShowGlErrors = DEVELOPMENT_SWITCH;

        mc.settings.ofSmartAnimations = true;
        mc.settings.ofSmoothFps = false;
        mc.settings.ofFastMath = false;

        FontUtil.init();

        this.eventManager = new EventManager();
        this.moduleManager = new ModuleManager();
        this.commandManager = new CommandManager();
        this.configManager = new ConfigManager();
        this.themeManager = new ThemeManager();

        String[] paths = {
                "io.github.liticane.clients"
        };

        for (String path : paths) {
            if (!ReflectionUtil.exist(path))
                continue;

            Class<?>[] classes = ReflectionUtil.getClassesInPackage(path);

            for (Class<?> clazz : classes) {
                try {
                    if (Module.class.isAssignableFrom(clazz) && clazz != Module.class) {
                        this.moduleManager.add((Module) clazz.getConstructor().newInstance());
                    } else if (Command.class.isAssignableFrom(clazz) && clazz != Command.class) {
                        this.commandManager.add((Command) clazz.getConstructor().newInstance());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.configManager.stop();
        }));


        this.moduleManager.init();
        this.commandManager.init();
        this.configManager.init();

        ImImpl.initialize(Display.getHandle());

        this.eventManager.register(this);
    }

}
