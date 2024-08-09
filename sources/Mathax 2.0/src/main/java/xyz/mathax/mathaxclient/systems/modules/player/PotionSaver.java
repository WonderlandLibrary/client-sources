package xyz.mathax.mathaxclient.systems.modules.player;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.settings.StatusEffectListSetting;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;

import java.util.List;

public class PotionSaver extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<List<StatusEffect>> effectsSetting = generalSettings.add(new StatusEffectListSetting.Builder()
            .name("Effects")
            .description("The effects to preserve.")
            .defaultValue(
                    StatusEffects.STRENGTH,
                    StatusEffects.ABSORPTION,
                    StatusEffects.RESISTANCE,
                    StatusEffects.FIRE_RESISTANCE,
                    StatusEffects.SPEED,
                    StatusEffects.HASTE,
                    StatusEffects.REGENERATION,
                    StatusEffects.WATER_BREATHING,
                    StatusEffects.SATURATION,
                    StatusEffects.LUCK,
                    StatusEffects.SLOW_FALLING,
                    StatusEffects.DOLPHINS_GRACE,
                    StatusEffects.CONDUIT_POWER,
                    StatusEffects.HERO_OF_THE_VILLAGE
            )
            .build()
    );

    public final Setting<Boolean> onlyWhenStationarySetting = generalSettings.add(new BoolSetting.Builder()
            .name("Only when stationary")
            .description("Only freeze effects when you aren't moving.")
            .defaultValue(false)
            .build()
    );

    public PotionSaver(Category category) {
        super(category, "Potion Saver", "Stops potion effects ticking when you stand still.");
    }

    public boolean shouldFreeze(StatusEffect effect) {
        return isEnabled() && (!onlyWhenStationarySetting.get() || !PlayerUtils.isMoving()) && !mc.player.getStatusEffects().isEmpty() && effectsSetting.get().contains(effect);
    }
}