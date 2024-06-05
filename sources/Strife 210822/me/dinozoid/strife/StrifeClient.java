package me.dinozoid.strife;

import me.dinozoid.strife.account.AccountRepository;
import me.dinozoid.strife.alpine.bus.EventBus;
import me.dinozoid.strife.alpine.bus.EventManager;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listenable;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.bind.BindRepository;
import me.dinozoid.strife.command.CommandRepository;
import me.dinozoid.strife.config.ConfigRepository;
import me.dinozoid.strife.config.settings.SettingsLoader;
import me.dinozoid.strife.event.implementations.system.KeyEvent;
import me.dinozoid.strife.font.FontRepository;
import me.dinozoid.strife.module.ModuleRepository;
import me.dinozoid.strife.target.TargetRepository;
import me.dinozoid.strife.util.MinecraftUtil;
import me.dinozoid.strife.util.player.AltService;
import me.dinozoid.strife.util.system.StringUtil;
import me.dinozoid.strife.util.ui.WindowedFullscreen;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum StrifeClient implements Listenable { INSTANCE;

    public static final String NAME = "Strife";
//    public static String CUSTOMNAME = NAME;
    public static final String BUILD = "210822-Development";
    public static final String CHAT_PREFIX = ".";
    public static final String COMMAND_PREFIX = "\u00A7cStrife \u00A77»\u00A7r ";
    public static final Path DIRECTORY = Paths.get(Minecraft.getMinecraft().mcDataDir.getAbsolutePath(), "Strife");
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final EventBus eventBus = new EventManager();
    private final SettingsLoader settingsLoader = new SettingsLoader();

    private final BindRepository bindRepository = new BindRepository();
    private final ConfigRepository configRepository = new ConfigRepository();
    private final ModuleRepository moduleRepository = new ModuleRepository();
    private final AccountRepository accountRepository = new AccountRepository();
    private final CommandRepository commandRepository = new CommandRepository();
    private final TargetRepository targetRepository = new TargetRepository();
    private final FontRepository fontRepository = new FontRepository();

    /**
        * Called when the client starts up.
    **/
    public void startup() {
        Display.setTitle(NAME + " " + BUILD);
        eventBus.subscribe(this);
        Minecraft.getMinecraft().strifeSplashScreen.progress(6, "Settings...");
        settingsLoader.load();
        Minecraft.getMinecraft().strifeSplashScreen.progress(7, "Configs...");
        configRepository.init();
        Minecraft.getMinecraft().strifeSplashScreen.progress(8, "Fonts...");
        fontRepository.init();
        bindRepository.init();
        Minecraft.getMinecraft().strifeSplashScreen.progress(9, "Modules...");
        moduleRepository.init();
        Minecraft.getMinecraft().strifeSplashScreen.progress(10, "Commands...");
        commandRepository.init();
        accountRepository.init();
    }

    /**
        * Called when the client shuts down.
     **/
    public void shutdown() {
        eventBus.unsubscribe(this);
        settingsLoader.save();
    }

    @EventHandler
    private final Listener<KeyEvent> keyListener = new Listener<>(event -> {
    });

    public EventBus eventBus() {
        return eventBus;
    }

    public ExecutorService executorService() {
        return executorService;
    }
    public BindRepository bindRepository() {
        return bindRepository;
    }
    public ModuleRepository moduleRepository() {
        return moduleRepository;
    }
    public AccountRepository accountRepository() {
        return accountRepository;
    }
    public CommandRepository commandRepository() {
        return commandRepository;
    }
    public TargetRepository targetRepository() {
        return targetRepository;
    }
    public ConfigRepository configRepository() {
        return configRepository;
    }
    public FontRepository fontRepository() {
        return fontRepository;
    }

}
