package fr.dog.module.impl.player;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.NumberProperty;
import fr.dog.util.math.TimeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.BlockPos;

public class ChestStealer extends Module {
    public ChestStealer() {
        super("ChestStealer", ModuleCategory.PLAYER);
        this.registerProperties(preTakeDelay, takeDelay);
    }

    private final NumberProperty preTakeDelay = NumberProperty.newInstance("Pre Steal Delay", 0f, 100f, 1000f, 50f);
    private final NumberProperty takeDelay = NumberProperty.newInstance("Take Delay", 0f, 100f, 1000f, 50f);


    private final TimeUtil timeUtil = new TimeUtil();
    private final TimeUtil timeUtil2 = new TimeUtil();

    @SubscribeEvent
    private void onTickEvent(PlayerTickEvent event){
        if ((mc.thePlayer.openContainer instanceof ContainerChest chest)) {
            if (isGui()) {
                return;
            }

            if (!timeUtil.finished(preTakeDelay.getValue().intValue())) {
                return;
            }

            for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                if ((chest.getLowerChestInventory().getStackInSlot(i) != null)) {
                    if (timeUtil2.finished(takeDelay.getValue().intValue())) {
                        mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
                        timeUtil2.reset();
                    }
                }
            }

            if (isChestEmpty(chest)) {
                mc.thePlayer.closeScreen();
            }

        } else {
            timeUtil.reset();
        }
    }


    private boolean isChestEmpty(Container container) {
        boolean isEmpty = true;
        int maxSlot = (container.inventorySlots.size() == 90) ? 54 : 27;
        for (int i = 0; i < maxSlot; ++i) {
            if (container.getSlot(i).getHasStack()) {
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    private boolean isGui() {
        final int range = 5;

        for (int i = -range; i < range; ++i) {
            for (int j = range; j > -range; --j) {
                for (int k = -range; k < range; ++k) {
                    int n2 = (int) mc.thePlayer.posX + i;
                    int n3 = (int) mc.thePlayer.posY + j;
                    int n4 = (int) mc.thePlayer.posZ + k;
                    BlockPos blockPos = new BlockPos(n2, n3, n4);
                    Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                    if (block instanceof BlockChest || block instanceof BlockEnderChest) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
