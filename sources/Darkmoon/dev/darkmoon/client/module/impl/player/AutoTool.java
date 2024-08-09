package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.player.EventInteractBlock;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@ModuleAnnotation(name = "AutoTool", category = Category.PLAYER)
public class AutoTool extends Module {
    public int itemIndex;
    private boolean swap;
    private long swapDelay;
    private final List<Integer> lastItem = new ArrayList<>();

    @EventTarget
    public void onInteractBlock(EventInteractBlock event) {
        if (mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null) {
            List<Integer> bestItem = new ArrayList<>();
            float bestSpeed = 1;
            Block block = mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
            for (int i = 0; i < 9; i++) {
                ItemStack item = mc.player.inventory.getStackInSlot(i);
                float speed = item.getDestroySpeed(block.getDefaultState());
                if (!(mc.player.inventory.getStackInSlot(i).getMaxDamage() - mc.player.inventory.getStackInSlot(i).getItemDamage() > 1))
                    continue;
                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestItem.add(i);
                }
            }

            if (!bestItem.isEmpty() && mc.gameSettings.keyBindAttack.pressed) {
                lastItem.add(mc.player.inventory.currentItem);
                mc.player.inventory.currentItem = bestItem.get(0);
                itemIndex = bestItem.get(0);
                swap = true;
                swapDelay = System.currentTimeMillis();
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (swap && !lastItem.isEmpty() && System.currentTimeMillis() >= swapDelay + 300) {
            if (!mc.gameSettings.keyBindAttack.pressed) {
                mc.player.inventory.currentItem = lastItem.get(0);
                itemIndex = lastItem.get(0);
                lastItem.clear();
                swap = false;
            }
        }
    }
}
