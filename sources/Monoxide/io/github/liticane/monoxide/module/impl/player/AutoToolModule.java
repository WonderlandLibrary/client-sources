package io.github.liticane.monoxide.module.impl.player;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;

@ModuleData(name = "AutoTool", description = "Switches to best tools", category = ModuleCategory.PLAYER)
public class AutoToolModule extends Module {

    @Listen
    public void onMotionEvent(UpdateMotionEvent event) {
        if(Methods.mc.thePlayer == null || Methods.mc.theWorld == null)
            return;
        
        if(event.getType() == UpdateMotionEvent.Type.MID) {
            if (!mc.gameSettings.keyBindAttack.isKeyDown())
                return;

            if(mc.objectMouseOver == null)
                return;

            BlockPos position = mc.objectMouseOver.getBlockPos();
            if(position == null)
                return;

            Block block = mc.theWorld.getBlockState(position).getBlock();
            if(block == null)
                return;

            float best = 1F;
            int slot = 1000;

            for (int index = 0; index < 9; index += 1) {
                ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(index);

                if (itemStack == null)
                    continue;

                float speed = itemStack.getStrVsBlock(block);

                if (speed > best) {
                    best = speed;
                    slot = index;
                }
            }

            if (slot == 1000)
                return;

            mc.thePlayer.inventory.currentItem = slot;
        }
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

}