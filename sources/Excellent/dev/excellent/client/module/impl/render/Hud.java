package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import lombok.Getter;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.text.TextFormatting;

@Getter
@ModuleInfo(name = "Hud", description = "Отображает информацию о клиенте/игроке", category = Category.RENDER)
public class Hud extends Module {
    private final Font inter12 = Fonts.INTER_BOLD.get(12);
    private final Animation animation = new Animation(Easing.EASE_OUT_QUAD, 100);
    private final MultiBooleanValue elements = new MultiBooleanValue("Элементы", this)
            .add(
                    new BooleanValue("Позиция", true),
                    new BooleanValue("Скорость", true),
                    new BooleanValue("Пользователь", true),
                    new BooleanValue("Пинг", true)
            );
    private final Listener<Render2DEvent> onRender2D = event -> {
        if (mc.gameSettings.showDebugInfo) return;
        boolean isChatScreen = (mc.currentScreen instanceof ChatScreen);
        int margin = 2;
        animation.run((isChatScreen ? 10 + margin : 0));

        StringBuilder leftSide = new StringBuilder();
        if (elements.isEnabled("Позиция")) {
            leftSide.append(TextFormatting.GRAY)
                    .append("Pos: ")
                    .append(TextFormatting.RESET)
                    .append(Mathf.round(mc.player.getPosX(), 1))
                    .append(", ")
                    .append(Mathf.round(mc.player.getPosY(), 1))
                    .append(", ")
                    .append(Mathf.round(mc.player.getPosZ(), 1))
                    .append(TextFormatting.GRAY);
        }

        if (elements.isEnabled("Позиция") && elements.isEnabled("Скорость")) {
            leftSide.append(", ");
        }
        if (elements.isEnabled("Скорость")) {
            leftSide.append(TextFormatting.GRAY)
                    .append("Bps: ").append(TextFormatting.RESET)
                    .append(PlayerUtil.getBps(mc.player, 1));
        }

        StringBuilder rightSide = new StringBuilder();
        if (elements.isEnabled("Пользователь")) {
            rightSide.append(TextFormatting.GRAY)
                    .append("User: ")
                    .append(TextFormatting.RESET)
                    .append(excellent.getProfile().getName())
                    .append(TextFormatting.GRAY);
        }
        if (elements.isEnabled("Пользователь") && elements.isEnabled("Пинг")) {
            rightSide.append(", ");
        }
        if (elements.isEnabled("Пинг")) {
            rightSide.append(TextFormatting.GRAY)
                    .append("Ping: ")
                    .append(TextFormatting.RESET)
                    .append(PlayerUtil.getPing());
        }
        float y = (float) (scaled().y - inter12.getHeight() - margin - animation.getValue());

        inter12.drawOutline(event.getMatrix(), leftSide.toString(), margin, (int) y, -1);
        inter12.drawRightOutline(event.getMatrix(), rightSide.toString(), scaled().x - margin, (int) y, -1);
    };
}

