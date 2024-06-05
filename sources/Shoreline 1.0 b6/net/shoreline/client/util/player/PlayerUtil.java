package net.shoreline.client.util.player;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.util.math.MathHelper;
import net.shoreline.client.util.Globals;

/**
 * @author linus
 * @since 1.0
 */
public final class PlayerUtil implements Globals {

    public static float getLocalPlayerHealth() {
        return mc.player.getHealth() + mc.player.getAbsorptionAmount();
    }

    // from MC source
    public static int computeFallDamage(float fallDistance, float damageMultiplier) {
        if (mc.player.getType().isIn(EntityTypeTags.FALL_DAMAGE_IMMUNE)) {
            return 0;
        } else {
            StatusEffectInstance statusEffectInstance = mc.player.getStatusEffect(StatusEffects.JUMP_BOOST);
            float f = statusEffectInstance == null ? 0.0F : (float)(statusEffectInstance.getAmplifier() + 1);
            return MathHelper.ceil((fallDistance - 3.0F - f) * damageMultiplier);
        }
    }

    public static boolean isHolding(final Item item) {
        ItemStack itemStack = mc.player.getMainHandStack();
        if (!itemStack.isEmpty() && itemStack.getItem() == item) {
            return true;
        }
        itemStack = mc.player.getOffHandStack();
        return !itemStack.isEmpty() && itemStack.getItem() == item;
    }
}
