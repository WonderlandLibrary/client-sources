package xyz.mathax.mathaxclient.systems.modules.player;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.mixin.StatusEffectInstanceAccessor;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.settings.StatusEffectAmplifierMapSetting;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;

public class PotionSpoof extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    private final Setting<Object2IntMap<StatusEffect>> potionsSetting = generalSettings.add(new StatusEffectAmplifierMapSetting.Builder()
            .name("Potions")
            .description("Potions to add.")
            .defaultValue(Utils.createStatusEffectMap())
            .build()
    );

    private final Setting<Boolean> clearEffectsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Clear effects")
            .description("Clear effects on module disable.")
            .defaultValue(true)
            .build()
    );

    public PotionSpoof(Category category) {
        super(category, "Potion Spoof", "Spoofs specified potion effects for you. SOME effects DO NOT work.");
    }

    @Override
    public void onDisable() {
        if (!clearEffectsSetting.get() || !Utils.canUpdate()) {
            return;
        }

        for (StatusEffect effect : potionsSetting.get().keySet()) {
            if (potionsSetting.get().getInt(effect) <= 0) {
                continue;
            }

            if (mc.player.hasStatusEffect(effect)) {
                mc.player.removeStatusEffect(effect);
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        for (StatusEffect statusEffect : potionsSetting.get().keySet()) {
            int level = potionsSetting.get().getInt(statusEffect);
            if (level <= 0) {
                continue;
            }

            if (mc.player.hasStatusEffect(statusEffect)) {
                StatusEffectInstance instance = mc.player.getStatusEffect(statusEffect);
                ((StatusEffectInstanceAccessor) instance).setAmplifier(level - 1);
                if (instance.getDuration() < 20) {
                    ((StatusEffectInstanceAccessor) instance).setDuration(20);
                }
            } else {
                mc.player.addStatusEffect(new StatusEffectInstance(statusEffect, 20, level - 1));
            }
        }
    }
}