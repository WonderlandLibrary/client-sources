package dev.vertic;

import dev.vertic.command.api.CommandManager;
import dev.vertic.config.ConfigManager;
import dev.vertic.event.api.EventBus;
import dev.vertic.module.Module;
import dev.vertic.module.api.ModuleManager;
import dev.vertic.module.impl.render.ClientTheme;
import dev.vertic.ui.click.dropdown.DropUI;
import dev.vertic.ui.mainmenu.MainMenu;
import lombok.Getter;
import org.lwjgl.opengl.Display;

import java.awt.*;

@Getter
public enum Client {

    instance;

    public static final String name = "Vertic", version = "1.0";

    private EventBus eventBus;
    private ModuleManager moduleManager;
    private CommandManager commandManager;

    private DropUI dropDownUI;
    private ConfigManager configManager;
    private final MainMenu mainMenu = new MainMenu();

    public void start() {
        // pre init
        eventBus = new EventBus();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();

        // post init
        dropDownUI = new DropUI();
        configManager = new ConfigManager();

        //postStartup
        Display.setTitle(name + " v" + version);
        moduleManager.modules.forEach(Module::onInit);
    }

    public void stop() {
        configManager.saveClient();
    }

    public Color getTheme(final int offset) {
        return moduleManager.getModule(ClientTheme.class).getColor(offset);
    }

}
