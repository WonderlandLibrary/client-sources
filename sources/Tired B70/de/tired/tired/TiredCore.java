package de.tired.tired;

import de.tired.api.util.misc.DiscordRPC;
import de.tired.command.CommandManager;
import de.tired.notification.NotificationRenderer;
import de.tired.event.EventManager;
import de.tired.api.logger.impl.ErrorLog;
import lombok.SneakyThrows;


public enum TiredCore {

    CORE;

    public EventManager eventManager;

    public NotificationRenderer notificationRenderer;
    public CommandManager commandManager;

    public boolean onSendChatMessage(String s) {
        if (s.startsWith(".")) {
            commandManager.execute(s.substring(1));
            return false;
        }
        return true;
    }

    @SneakyThrows
    public void initCore() {
        if (eventManager == null) {
            ErrorLog.ERROR_LOG.doLog("Event manager is null!");
        }
        this.eventManager = EventManager.SINGLETON;
        this.commandManager = new CommandManager();
        this.notificationRenderer = new NotificationRenderer();
        Tired.INSTANCE.discordRPC = new DiscordRPC();
        Tired.INSTANCE.discordRPC.start();
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
