package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.hud.HudElement;
import xyz.mathax.mathaxclient.utils.misc.Names;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;

public class PotionTimersHudElement extends HudElement {
    private final Color color = new Color();

    public PotionTimersHudElement(Hud hud) {
        super(hud, "Potion Timers", "Displays active potion effects with timers.");
    }

    @Override
    public void update(OverlayRenderer renderer) {
        if (isInEditor()) {
            box.setSize(renderer.textWidth(name + " 0:00"), renderer.textHeight());
            return;
        }

        double width = 0;
        double height = 0;

        int i = 0;
        for (StatusEffectInstance statusEffectInstance : mc.player.getStatusEffects()) {
            width = Math.max(width, renderer.textWidth(getString(statusEffectInstance)));
            height += renderer.textHeight();

            if (i > 0) {
                height += 2;
            }

            i++;
        }

        box.setSize(width, height);
    }

    @Override
    public void render(OverlayRenderer renderer) {
        double x = box.getX();
        double y = box.getY();

        if (isInEditor()) {
            renderer.text(name + " 0:00", x, y, color);
            return;
        }

        int i = 0;
        for (StatusEffectInstance statusEffectInstance : mc.player.getStatusEffects()) {
            StatusEffect statusEffect = statusEffectInstance.getEffectType();

            int intColor = statusEffect.getColor();
            color.r = Color.toRGBAR(intColor);
            color.g = Color.toRGBAG(intColor);
            color.b = Color.toRGBAB(intColor);

            String text = getString(statusEffectInstance);
            renderer.text(text, x + box.alignX(renderer.textWidth(text)), y, color);

            color.r = color.g = color.b = 255;
            y += renderer.textHeight();
            if (i > 0) {
                y += 2;
            }

            i++;
        }
    }

    private String getString(StatusEffectInstance statusEffectInstance) {
        return String.format("%s %d (%s)", Names.get(statusEffectInstance.getEffectType()), statusEffectInstance.getAmplifier() + 1, StatusEffectUtil.durationToString(statusEffectInstance, 1));
    }
}
