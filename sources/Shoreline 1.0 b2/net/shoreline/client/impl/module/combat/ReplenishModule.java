package net.shoreline.client.impl.module.combat;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;
import net.minecraft.item.ItemStack;
import net.shoreline.client.init.Managers;
import net.shoreline.client.init.Modules;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class ReplenishModule extends ToggleModule
{
    //
    Config<Integer> percentConfig = new NumberConfig<>("Percent",
            "The minimum percent of total stack before replenishing", 1, 25, 80);

    /**
     *
     */
    public ReplenishModule()
    {
        super("Replenish", "Automatically replaces items in your hotbar",
                ModuleCategory.COMBAT);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event)
    {
        if (event.getStage() != EventStage.PRE)
        {
            return;
        }
        for (int i = 0; i < 9; i++)
        {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty() || !stack.isStackable())
            {
                continue;
            }
            float total = ((float) stack.getCount() / stack.getMaxCount()) * 100.0f;
            if (total < percentConfig.getValue())
            {
                if (stack.getItem().equals(Items.END_CRYSTAL) && Modules.AUTO_CRYSTAL.isPlacing())
                {
                    continue;
                }
                replenishStack(stack, i);
                break;
            }
        }
    }

    /**
     *
     * @param item
     * @param slot
     */
    private void replenishStack(ItemStack item, int slot)
    {
        int replenishSlot = -1;
        for (int i = 9; i < 36; i++)
        {
            ItemStack stack = mc.player.getInventory().getStack(i);
            // We cannot merge stacks if they dont have the same name
            if (!stack.getName().equals(item.getName()))
            {
                continue;
            }
            if (stack.getItem() instanceof BlockItem blockItem)
            {
                if (!(item.getItem() instanceof BlockItem blockItem1))
                {
                    continue;
                }
                if (blockItem.getBlock() != blockItem1.getBlock())
                {
                    continue;
                }
            }
            else
            {
                if (stack.getItem() != item.getItem())
                {
                    continue;
                }
            }
            replenishSlot = i;
        }
        if (replenishSlot != -1)
        {
            int total = item.getCount() + mc.player.getInventory().getStack(replenishSlot).getCount();
            Managers.INVENTORY.pickupSlot(replenishSlot);
            Managers.INVENTORY.pickupSlot(slot < 9 ? slot + 36 : slot);
            if (total >= item.getMaxCount())
            {
                Managers.INVENTORY.pickupSlot(replenishSlot);
            }
        }
    }
}
