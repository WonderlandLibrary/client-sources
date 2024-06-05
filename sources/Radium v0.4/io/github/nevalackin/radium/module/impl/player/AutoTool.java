package io.github.nevalackin.radium.module.impl.player;

import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.impl.combat.KillAura;
import io.github.nevalackin.radium.utils.InventoryUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo(label = "Auto Tool", category = ModuleCategory.SELF)
public final class AutoTool extends Module {

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre()) {
            MovingObjectPosition objectMouseOver;
            if ((objectMouseOver = Wrapper.getMinecraft().objectMouseOver) != null &&
                    Wrapper.getGameSettings().keyBindAttack.isKeyDown()) {
                BlockPos blockPos;
                if (objectMouseOver.entityHit != null)
                    doSwordSwap();
                else if ((blockPos = objectMouseOver.getBlockPos()) != null) {
                    Block block = Wrapper.getWorld().getBlockState(blockPos).getBlock();
                    float strongestToolStr = 1.0F;
                    int strongestToolSlot = -1;
                    for (int i = 36; i < 45; i++) {
                        ItemStack stack = Wrapper.getStackInSlot(i);

                        if (stack != null && stack.getItem() instanceof ItemTool) {
                            float strVsBlock = stack.getStrVsBlock(block);
                            if (strVsBlock > strongestToolStr) {
                                strongestToolStr = strVsBlock;
                                strongestToolSlot = i;
                            }
                        }
                    }

                    if (strongestToolSlot != -1)
                        Wrapper.getPlayer().inventory.currentItem = strongestToolSlot - 36;
                }
            } else if (KillAura.getInstance().getTarget() != null)
                doSwordSwap();
        }
    }

    private void doSwordSwap() {
        double damage = 1.0;
        int slot = -1;
        for (int i = 36; i < 45; i++) {
            ItemStack stack = Wrapper.getStackInSlot(i);

            if (stack != null && stack.getItem() instanceof ItemSword) {
                double damageVs = InventoryUtils.getItemDamage(stack);
                if (damageVs > damage) {
                    damage = damageVs;
                    slot = i;
                }
            }
        }

        if (slot != -1)
            Wrapper.getPlayer().inventory.currentItem = slot - 36;
    }
}
