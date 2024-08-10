package cc.slack.features.modules.impl.other;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.potion.Potion;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@ModuleInfo(
        name = "RemoveEffect",
        category = Category.OTHER
)

public class RemoveEffect extends Module {

    private enum Effect {
        SLOWNESS(Potion.moveSlowdown.id, "Slowness"),
        MINING_FATIGUE(Potion.digSlowdown.id, "MiningFatigue"),
        BLINDNESS(Potion.blindness.id, "Blindness"),
        WEAKNESS(Potion.weakness.id, "Weakness"),
        WITHER(Potion.wither.id, "Wither"),
        POISON(Potion.poison.id, "Poison"),
        WATER_BREATHING(Potion.waterBreathing.id, "WaterBreathing");

        private final int potionId;
        private final String name;

        Effect(int potionId, String name) {
            this.potionId = potionId;
            this.name = name;
        }

        public int getPotionId() {
            return potionId;
        }

        public String getName() {
            return name;
        }
    }

    private final Map<Effect, BooleanValue> effectSettings = new EnumMap<>(Effect.class);

    public RemoveEffect() {
        for (Effect effect : Effect.values()) {
            effectSettings.put(effect, new BooleanValue(effect.getName(), false));
        }
        addSettings(effectSettings.values().toArray(new BooleanValue[0]));
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer != null) {
            for (Map.Entry<Effect, BooleanValue> entry : effectSettings.entrySet()) {
                if (entry.getValue().getValue()) {
                    mc.thePlayer.removePotionEffectClient(entry.getKey().getPotionId());
                }
            }
        }
    }
}
