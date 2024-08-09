package fun.ellant.functions.impl.combat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Potions {
    STRENGTH(Effects.STRENGTH, 5),
    SPEED(Effects.SPEED, 1),
    FIRE_RESISTANCE(Effects.FIRE_RESISTANCE, 12),
    REGEN(Effects.REGENERATION,10),
    INSTANT_HEALTH(Effects.INSTANT_HEALTH, 3);

    public final Effect potion;
    public final int id;
    public boolean state;

    Potions(Effect potion, int potionId) {
        this.potion = potion;
        this.id = potionId;
    }
}
