package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.player.MoveUtil;
import dev.excellent.impl.util.time.TimerUtil;
import org.apache.commons.lang3.RandomStringUtils;

@ModuleInfo(name = "Anti AFK", description = "Предотвращает кик за афк со стороны сервера.", category = Category.MISC)
public class AntiAFK extends Module {
    private final TimerUtil time = TimerUtil.create();

    private final Listener<UpdateEvent> onUpdate = event -> {
        if (!MoveUtil.isMoving()) {
            if (time.hasReached(15000)) {
                ChatUtil.sendText("/" + RandomStringUtils.randomAlphabetic(5));
                time.reset();
            }
        } else {
            time.reset();
        }
    };
}
