package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.init.Managers;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class SelfBowModule extends ToggleModule
{
    /**
     *
     */
    public SelfBowModule()
    {
        super("SelfBow", "Shoots player with beneficial tipped arrows",
                ModuleCategory.COMBAT);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event)
    {
        if (event.getStage() == EventStage.PRE)
        {
            int arrowSlot = -1;
            for (int i = 9; i < 36; i++)
            {
                ItemStack stack = mc.player.getInventory().getStack(i);
                if (stack.isEmpty() || stack.getItem() != Items.TIPPED_ARROW)
                {
                    continue;
                }
                Potion p = PotionUtil.getPotion(stack);
                for (StatusEffectInstance effect : p.getEffects())
                {
                    StatusEffect type = effect.getEffectType();
                    if (type.isBeneficial()
                            && !mc.player.getStatusEffects().contains(effect))
                    {
                        arrowSlot = i;
                        break;
                    }
                }
                if (arrowSlot != -1)
                {
                    break;
                }
            }
            int bowSlot = -1;
            for (int i = 36; i < 45; i++)
            {
                ItemStack stack = mc.player.getInventory().getStack(i);
                if (!stack.isEmpty() && stack.getItem() == Items.BOW)
                {
                    bowSlot = i;
                    break;
                }
            }
            if (bowSlot == -1)
            {
                disable();
                return;
            }
            int prevSlot = mc.player.getInventory().selectedSlot;
            mc.player.getInventory().selectedSlot = bowSlot;
            Managers.NETWORK.sendPacket(new UpdateSelectedSlotC2SPacket(bowSlot));
            if (arrowSlot != -1)
            {
                prioritizeArrow(arrowSlot);
            }
            mc.player.getInventory().selectedSlot = prevSlot;
            Managers.NETWORK.sendPacket(new UpdateSelectedSlotC2SPacket(prevSlot));
        }
    }

    /**
     *
     * @param slot
     */
    private void prioritizeArrow(int slot)
    {
        ItemStack stack = mc.player.getInventory().getStack(9);
        Managers.INVENTORY.pickupSlot(slot);
        boolean rt = !stack.isEmpty();
        Managers.INVENTORY.pickupSlot(9);
        if (rt)
        {
            Managers.INVENTORY.pickupSlot(slot);
        }
    }
}
