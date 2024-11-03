package dev.stephen.nexus.module.modules.render;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.stat.Stat;

public class NoRender extends Module {
    public static BooleanSetting hurtCam = new BooleanSetting("HurtCam", false);
    public static BooleanSetting blindness = new BooleanSetting("Blindness", false);
    public static BooleanSetting darkness = new BooleanSetting("Darkness", false);

    public NoRender() {
        super("NoRender", "Removes the hurt tilt and blindness", 0, ModuleCategory.RENDER);
        this.addSettings(hurtCam, blindness, darkness);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (blindness.getValue() && mc.player.hasStatusEffect(StatusEffects.BLINDNESS))
            mc.player.removeStatusEffect(StatusEffects.BLINDNESS);
        if (darkness.getValue() && mc.player.hasStatusEffect(StatusEffects.DARKNESS))
            mc.player.removeStatusEffect(StatusEffects.DARKNESS);
    };
}
