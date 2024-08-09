package xyz.mathax.mathaxclient.systems.modules.player;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.mixin.StatusEffectInstanceAccessor;
import xyz.mathax.mathaxclient.settings.DoubleSetting;
import xyz.mathax.mathaxclient.settings.EnumSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class SpeedMine extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<Mode> modeSetting = generalSettings.add(new EnumSetting.Builder<Mode>()
            .name("mode")
            .defaultValue(Mode.Normal)
            .build()
    );
    public final Setting<Double> modifierSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("modifier")
            .description("Mining speed modifier. An additional value of 0.2 is equivalent to one haste level (1.2 = haste 1).")
            .defaultValue(1.4)
            .min(0)
            .build()
    );

    public SpeedMine(Category category) {
        super(category, "Speed Mine", "Allows you to quickly mine blocks.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (modeSetting.get() == Mode.Normal) {
            return;
        }

        int amplifier = modeSetting.get() == Mode.Haste_II ? 1 : 0;
        if (!mc.player.hasStatusEffect(StatusEffects.HASTE)) {
            mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 255, amplifier, false, false, false));
        }

        StatusEffectInstance effect = mc.player.getStatusEffect(StatusEffects.HASTE);
        ((StatusEffectInstanceAccessor) effect).setAmplifier(amplifier);
        if (effect.getDuration() < 20) {
            ((StatusEffectInstanceAccessor) effect).setDuration(20);
        }
    }

    public enum Mode {
        Normal("Normal"),
        Haste_I("Haste I"),
        Haste_II("Haste II");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}