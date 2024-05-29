package dev.client.tenacity.module.impl.misc;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.MultipleBoolSetting;
import dev.settings.impl.NumberSetting;
import dev.settings.impl.StringSetting;
import dev.utils.misc.MathUtils;
import dev.utils.time.TimerUtil;

public final class Spammer extends Module {
    private final StringSetting text = new StringSetting("Text");
    private final NumberSetting delay = new NumberSetting("Delay", 100, 1000, 10, 1);
    private final MultipleBoolSetting settings = new MultipleBoolSetting("Settings",
            new BooleanSetting("AntiSpam", false),
            new BooleanSetting("Bypass", false));
    private final TimerUtil delayTimer = new TimerUtil();
    private final EventListener<MotionEvent> motionEventEventListener = event -> {
        String spammerText = text.getString();

        if (spammerText != null && delayTimer.hasTimeElapsed(settings.getSetting("Bypass").isEnabled() ? 2000 : delay.getValue().longValue())) {

            if (settings.getSetting("AntiSpam").isEnabled()) {
                spammerText += " " + MathUtils.getRandomInRange(10, 100000);
            }

            mc.thePlayer.sendChatMessage(spammerText);
            delayTimer.reset();
        }
    };

    public Spammer() {
        super("Spammer", Category.MISC, "Spam the chat with a custom message");
        this.addSettings(text, delay, settings);
    }
}
