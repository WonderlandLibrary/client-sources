package io.github.liticane.monoxide;

import de.florianmichael.viamcp.ViaMCP;
import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.files.storage.FileStorage;
import io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.altmanager.utils.AccountManager;
import org.lwjglx.opengl.Display;
import io.github.liticane.monoxide.anticheat.check.CheckStorage;
import io.github.liticane.monoxide.command.storage.CommandStorage;
import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.theme.storage.ThemeStorage;
import io.github.liticane.monoxide.value.ValueManager;
import io.github.liticane.monoxide.listener.handling.EventHandling;
import io.github.liticane.monoxide.component.ComponentManager;
import io.github.liticane.monoxide.util.discord.DiscordRP;
import io.github.liticane.monoxide.util.interfaces.ClientInformationAccess;

public enum Modification implements ClientInformationAccess {
    INSTANCE;

    public static final long initTime = System.currentTimeMillis();

    private boolean loaded = false;

    public void start() {
        Display.setTitle(CLIENT_NAME + " v" + CLIENT_VERSION);

        EventHandling.setInstance(new EventHandling());
        FontStorage.setInstance(new FontStorage());
        ComponentManager.setInstance(new ComponentManager());
        ValueManager.setInstance(new ValueManager());
        ThemeStorage.setInstance(new ThemeStorage());
        ModuleManager.setInstance(new ModuleManager());
        CommandStorage.setInstance(new CommandStorage());
        FileStorage.setInstance(new FileStorage());
        CheckStorage.setInstance(new CheckStorage());

        AccountManager.init();
        FontStorage.getInstance().init();
        ComponentManager.getInstance().init();
        ThemeStorage.getInstance().init();
        CommandStorage.getInstance().init();
        FileStorage.getInstance().init();
        CheckStorage.getInstance().init();

        try {
            ViaMCP.create();

            // In case you want a version slider like in the Minecraft options, you can use this code here, please choose one of those:

            ViaMCP.INSTANCE.initAsyncSlider(); // For top left aligned slider
        } catch (Exception e) {
            e.printStackTrace();
        }

        DiscordRP.startup();
        DiscordRP.create();

        Runtime.getRuntime().addShutdownHook(new Thread(this::end));

        loaded = true;
    }

    public void end() {
        FileStorage.getInstance().save();
        DiscordRP.end();
    }

    public boolean isLoaded() {
        return loaded;
    }
}
