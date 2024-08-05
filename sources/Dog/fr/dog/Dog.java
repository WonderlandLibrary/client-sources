package fr.dog;

import fr.dog.anticheat.AnticheatManager;
import fr.dog.command.CommandManager;
import fr.dog.command.irc.IRC;
import fr.dog.component.ComponentManager;
import fr.dog.config.ConfigManager;
import fr.dog.event.EventBus;
import fr.dog.module.ModuleManager;
import fr.dog.theme.ThemeManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Dog {
    private static final Dog INSTANCE = new Dog();
    private String uid, username, token,key, vi;


    private final EventBus eventBus = new EventBus();
    private ModuleManager moduleManager;
    private AnticheatManager checkManager;
    private CommandManager commandManager;
    private ComponentManager componentManager;
    private ConfigManager configManager;
    private ThemeManager themeManager;
    private IRC irc;

    public static Dog getInstance() {
        return INSTANCE;
    }
}
