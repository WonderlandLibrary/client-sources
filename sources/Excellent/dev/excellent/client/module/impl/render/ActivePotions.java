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
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.value.impl.DragValue;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.StringUtils;
import org.joml.Vector2d;

import java.util.Collection;

@ModuleInfo(name = "Active Potions", description = "Получает список активных эффектов зелий.", category = Category.RENDER)
public class ActivePotions extends Module {


    public final DragValue position = new DragValue("Position", this, new Vector2d(200, 200));
    private final Animation expandAnimation = new Animation(Easing.EASE_OUT_QUART, 100);
    private final Font interbold14 = Fonts.INTER_BOLD.get(14);
    private final Font interbold12 = Fonts.INTER_BOLD.get(12);
    private final Font interbold10 = Fonts.INTER_BOLD.get(10);


    public final Listener<Render2DEvent> onRender2D = event -> {
        boolean expand = !mc.player.getActivePotionEffects().stream().toList().isEmpty();

        String name = "Active potions";

        double width = position.size.x = mc.player.getActivePotionEffects().stream()
                .mapToDouble(potion -> Math.max(40 + interbold14.getWidth(name), 5 + 40 + interbold12.getWidth(potion.getPotion().getDisplayName().getString()) + interbold12.getWidth(String.valueOf(potion.getAmplifier() + 1)) + 3 + interbold10.getWidth(getDuration(potion)) + 5))
                .max()
                .orElse(40 + interbold14.getWidth(name));

        expandAnimation.run(position.size.y);

        double height = expandAnimation.getValue();

        double x = position.position.x;
        double y = position.position.y;
        if (mc.gameSettings.showDebugInfo) return;

        double expaned = 14;
        position.size.y = expaned;

        RenderUtil.renderClientRect(event.getMatrix(), (float) x, (float) y, (float) width, (float) height, expand, expaned);

        interbold14.drawCenter(event.getMatrix(), name, x + width / 2F, y + (expaned / 2F) - (interbold14.getHeight() / 2F), -1);

        if (!expand) return;

        Collection<EffectInstance> potionsCollection = mc.player.getActivePotionEffects();

        int i = 0;
        for (EffectInstance potion : potionsCollection) {
            interbold12.draw(event.getMatrix(), potion.getPotion().getDisplayName().getString(), x + 5, y + 10 + interbold14.getHeight() + i, -1);
            interbold12.draw(event.getMatrix(), String.valueOf(potion.getAmplifier() + 1), x + interbold12.getWidth(potion.getPotion().getDisplayName().getString()) + 5 + 3, y + 10 + interbold14.getHeight() + i, -1);
            interbold10.drawRight(event.getMatrix(), getDuration(potion), x + width - 5, y + 10.5 + interbold14.getHeight() + i, -1);
            i += 10;
            position.size.y = 10 + interbold14.getHeight() + i + 2;
        }

    };

    public static String getDuration(EffectInstance potion) {
        if (potion.getIsPotionDurationMax()) {
            return "**:**";
        } else {
            return StringUtils.ticksToElapsedTime(potion.getDuration());
        }
    }

}