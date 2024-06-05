/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class Autotool
extends Module {
    public Autotool() {
        super("AutoTool", new String[]{"tool", "teel", "auto"}, Type.MISC, "Switches to the best tool");
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        if (!Autotool.mc.gameSettings.keyBindAttack.pressed) {
            return;
        }
        if (Autotool.mc.objectMouseOver == null) {
            return;
        }
        BlockPos pos = Autotool.mc.objectMouseOver.getBlockPos();
        if (pos == null) {
            return;
        }
        this.updateTool(pos);
    }

    public void updateTool(BlockPos pos) {
        Block block = Autotool.mc.theWorld.getBlockState(pos).getBlock();
        float strength = 1.0f;
        int bestItemIndex = -1;
        int i = 0;
        while (true) {
            if (i >= 9) {
                if (bestItemIndex == -1) return;
                Minecraft.thePlayer.inventory.currentItem = bestItemIndex;
                return;
            }
            ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && !(itemStack.getStrVsBlock(block) <= strength)) {
                strength = itemStack.getStrVsBlock(block);
                bestItemIndex = i;
            }
            ++i;
        }
    }
}

