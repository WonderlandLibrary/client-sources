package dev.client.tenacity.module.impl.misc;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.ui.notifications.NotificationManager;
import dev.client.tenacity.ui.notifications.NotificationType;
import dev.client.tenacity.utils.player.ChatUtils;
import dev.event.EventListener;
import dev.event.impl.player.ChatReceivedEvent;
import dev.settings.ParentAttribute;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.NumberSetting;
import dev.settings.impl.StringSetting;
import dev.utils.misc.Multithreading;
import net.minecraft.util.StringUtils;

import java.util.concurrent.TimeUnit;

public class AutoHypixel extends Module {

    private final BooleanSetting autoGG = new BooleanSetting("AutoGG", true);
    private final StringSetting autoGGMessage = new StringSetting("AutoGG Message", "gg");
    private final BooleanSetting autoPlay = new BooleanSetting("AutoPlay", true);
    private final NumberSetting autoPlayDelay = new NumberSetting("AutoPlay Delay", 2.5, 8, 2, 0.5);
    private final BooleanSetting autoHubOnBan = new BooleanSetting("Auto /l on ban", false);

    public AutoHypixel() {
        super("AutoHypixel", Category.MISC, "stuff for hypixel");
        autoGGMessage.addParent(autoGG, ParentAttribute.BOOLEAN_CONDITION);
        autoPlayDelay.addParent(autoPlay, ParentAttribute.BOOLEAN_CONDITION);
        this.addSettings(autoGG, autoGGMessage, autoPlay, autoPlayDelay, autoHubOnBan);
    }

    private final EventListener<ChatReceivedEvent> onChatReceived = e -> {
        String message = e.message.getUnformattedText(), strippedMessage = StringUtils.stripControlCodes(message);
        if (autoHubOnBan.isEnabled() && strippedMessage.equals("A player has been removed from your game.")) {
            ChatUtils.send("/lobby");
            NotificationManager.post(NotificationType.WARNING, "AutoHypixel", "A player in your lobby got banned.");
        }
        String m = e.message.toString();
        if (m.contains("ClickEvent{action=RUN_COMMAND, value='/play ")) {
            if (autoGG.isEnabled() && !strippedMessage.startsWith("You died!")) {
                ChatUtils.send("/ac " + autoGGMessage.getString());
            }
            if (autoPlay.isEnabled()) {
                sendToGame(m.split("action=RUN_COMMAND, value='")[1].split("'}")[0]);
            }
        }
    };

    private void sendToGame(String mode) {
        float delay = autoPlayDelay.getValue().floatValue();
        NotificationManager.post(NotificationType.INFO, "AutoPlay",
                "Sending you to a new game" + (delay > 0 ? " in " + delay + "s" : "") + "!", delay);
        Multithreading.schedule(() -> ChatUtils.send(mode), (long) (delay * 1000), TimeUnit.MILLISECONDS);
    }

}
