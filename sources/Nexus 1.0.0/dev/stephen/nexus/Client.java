package dev.stephen.nexus;

import dev.stephen.nexus.anticheat.AntiCheatManager;
import dev.stephen.nexus.commands.CommandManager;
import dev.stephen.nexus.config.ConfigManager;
import dev.stephen.nexus.event.EventManager;
import dev.stephen.nexus.module.ModuleManager;
import dev.stephen.nexus.utils.auth.fake.FakeAuthClass;
import dev.stephen.nexus.utils.font.FontManager;
import dev.stephen.nexus.utils.mc.DelayUtil;
import dev.stephen.nexus.utils.render.notifications.NotificationManager;
import dev.stephen.nexus.utils.rotation.manager.RotationManager;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;

@Getter
public final class Client {
    public static MinecraftClient mc;
    public static Client INSTANCE;

    private final EventManager eventManager;
    private final RotationManager rotationManager;
    private final ModuleManager moduleManager;

    private final NotificationManager notificationManager;
    private final AntiCheatManager antiCheatManager;

    private final FontManager fontManager;
    private final ConfigManager configManager;
    private final CommandManager commandManager;

    private final DelayUtil delayUtil;
    public static String verison = "1.0.0";

    public Client() {

        INSTANCE = this;
        mc = MinecraftClient.getInstance();

        eventManager = new EventManager();

        notificationManager = new NotificationManager();
        antiCheatManager = new AntiCheatManager();
        rotationManager = new RotationManager();
        commandManager = new CommandManager();
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();

        fontManager = new FontManager();
        delayUtil = new DelayUtil();

        eventManager.subscribe(notificationManager);
        eventManager.subscribe(rotationManager);
        eventManager.subscribe(delayUtil);
    }
}
