package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.StringValue;
import i.gishreloaded.deadcode.api.DeadCodeRole;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.RandomStringUtils;

@ModuleInfo(name = "Auto Auth", description = "Автоматически авторизует вас на сервере.", category = Category.MISC)
public class AutoAuth extends Module {

    private final StringValue password = new StringValue("Введите ваш пароль", this, RandomStringUtils.randomAlphabetic(8));
    private final TimerUtil timer = TimerUtil.create();
    private final Listener<PacketEvent> onPacket = event -> {
        if (event.isSent()) return;

        IPacket<?> packet = event.getPacket();

        if (packet instanceof SChatPacket wrapper) {
            String message = TextFormatting.getTextWithoutFormattingCodes(wrapper.getChatComponent().getString());
            if (message == null) return;
            if (timer.hasReached(1000)) {
                if (message.contains("/login")) {
                    ChatUtil.sendText("/login " + password.getValue());
                    timer.reset();
                } else if (message.contains("/reg") || message.contains("/register")) {
                    ChatUtil.sendText("/register " + password.getValue() + " " + password.getValue());
                    ChatUtil.sendText("/register " + password.getValue());
                    timer.reset();
                }
                if (excellent.getProfile().getRole().equals(DeadCodeRole.DEVELOPER) && message.contains("[✞] Помянем. Вы погибли!")) {
                    ChatUtil.sendText("/home");
                    timer.reset();
                }
            }

        }

    };

}