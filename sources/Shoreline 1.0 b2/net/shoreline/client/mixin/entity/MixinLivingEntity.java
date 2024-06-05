package net.shoreline.client.mixin.entity;

import net.minecraft.util.Hand;
import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.entity.ConsumeItemEvent;
import net.shoreline.client.impl.event.entity.LevitationEvent;
import net.shoreline.client.impl.event.entity.SwingEvent;
import net.shoreline.client.util.Globals;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity implements Globals
{
    /**
     *
     * @param effect
     * @return
     */
    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    //
    @Shadow
    protected ItemStack activeItemStack;

    /**
     *
     * @param instance
     * @param effect
     * @return
     */
    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/" +
            "minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/" +
            "entity/effect/StatusEffect;)Z"))
    private boolean hookHasStatusEffect(LivingEntity instance, StatusEffect effect)
    {
        if (instance.equals(mc.player))
        {
            LevitationEvent levitationEvent = new LevitationEvent();
            Shoreline.EVENT_HANDLER.dispatch(levitationEvent);
            return !levitationEvent.isCanceled() && hasStatusEffect(effect);
        }
        return hasStatusEffect(effect);
    }

    /**
     *
     * @param ci
     */
    @Inject(method = "consumeItem", at = @At(value = "INVOKE", target = "Lnet/" +
            "minecraft/item/ItemStack;finishUsing(Lnet/minecraft/world/World;" +
            "Lnet/minecraft/entity/LivingEntity;)" +
            "Lnet/minecraft/item/ItemStack;", shift = At.Shift.AFTER))
    private void hookConsumeItem(CallbackInfo ci)
    {
        if ((Object) this != mc.player)
        {
            return;
        }
        ConsumeItemEvent consumeItemEvent = new ConsumeItemEvent(activeItemStack);
        Shoreline.EVENT_HANDLER.dispatch(consumeItemEvent);
    }
}
