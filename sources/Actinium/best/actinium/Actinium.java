package best.actinium;

import best.actinium.component.Component;
import best.actinium.component.ComponentManager;
import best.actinium.event.Event;
import best.actinium.event.api.EventManager;
import best.actinium.module.Module;
import best.actinium.module.api.ModuleManager;
import best.actinium.module.api.command.Command;
import best.actinium.module.api.command.CommandManager;
import best.actinium.module.api.config.ConfigManager;
import best.actinium.ui.renderer.ImGuiImpl;
import best.actinium.util.io.ReflectionUtil;
import best.actinium.util.render.RenderEngine2D;
import best.actinium.util.render.drag.DragManager;
import best.actinium.util.render.font.FontUtil;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.viamcpFix.viamcp.ViaMCP;
import org.lwjglx.Sys;
import org.lwjglx.opengl.Display;

@Getter
public enum Actinium {

    INSTANCE;

    public static final String NAME = "Actinium", VERSION = "1.1";
    public static String USER = "";
    public static boolean hasLaunched = false;

    private EventManager<Event> eventManager;
    private ComponentManager componentManager;
    private ModuleManager moduleManager;
    private ConfigManager configManager;
    private CommandManager commandManager;
    /*todo:
    *  Recode list
    * Recode inv manager
    * Recode drag stuff
    * and some render stuff*/

    public void init() {
        Display.setTitle(NAME + " " + VERSION + " - Minecraft 1.8.9" + " - LWJGL " + Sys.getVersion());
        Minecraft.getMinecraft().setWindowIcon();

        eventManager = new EventManager<>();
        componentManager = new ComponentManager();
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();
        commandManager = new CommandManager();

        String[] pathListing = {
                "best.actinium",
                "actinium"
        };

        for (String path : pathListing) {
            if (!ReflectionUtil.exist(path))
                continue;

            Class<?>[] classes = ReflectionUtil.getClassesInPackage(path);

            for (Class<?> aClass : classes) {
                try {
                    if (Module.class.isAssignableFrom(aClass) && aClass != Module.class) {
                        this.moduleManager.add((Module) aClass.getConstructor().newInstance());
                    } else if (Component.class.isAssignableFrom(aClass) && aClass != Component.class) {
                        this.componentManager.add((Component) aClass.getConstructor().newInstance());
                    } else if (Command.class.isAssignableFrom(aClass) && aClass != Command.class) {
                        this.commandManager.add((Command) aClass.getConstructor().newInstance());
                    }
                } catch (Exception ignored) {
                    // nothing here my black ass nigger
                }
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DragManager.saveDragData();
            this.configManager.stop();
        }));

        RenderEngine2D.init();
        DragManager.loadDragData();
        configManager.init();
        commandManager.init();
        FontUtil.init();
        ViaMCP.create();

        ImGuiImpl.initialize(
                Display.Window.handle
        );
    }

}
