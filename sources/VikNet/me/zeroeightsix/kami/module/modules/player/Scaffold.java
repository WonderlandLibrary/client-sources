package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFalling;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static me.zeroeightsix.kami.util.BlockInteractionHelper.*;

// TODO place block below player with listener for jumping event

@Module.Info(name = "Scaffold", category = Module.Category.HIDDEN)
public class Scaffold extends Module {

    //private Setting<Integer> future = register(Settings.integerBuilder("Ticks").withMinimum(0).withMaximum(60).withValue(2));

    @Override
    public void onUpdate() {

        if (isDisabled() || mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }

        // set slot
        int oldSlot = Wrapper.getPlayer().inventory.currentItem;

        // TODO add Settings<Float> when json corrupt bug is fixed
        Vec3d interpol1 = EntityUtil.getInterpolatedPos(mc.player, 2);
        Vec3d interpol2 = EntityUtil.getInterpolatedPos(mc.player, 4);

        doBlockScaffold(interpol1);
        doBlockScaffold(interpol2);

        // reset slot
        Wrapper.getPlayer().inventory.currentItem = oldSlot;
    }

    private void doBlockScaffold(Vec3d vec) {

        BlockPos blockPos = new BlockPos(vec).down();
        BlockPos belowBlockPos = blockPos.down();

        // check if block is already placed
        if (!Wrapper.getWorld().getBlockState(blockPos).getMaterial().isReplaceable()) {
            return;
        }

        // search blocks in hotbar
        int newSlot = -1;
        for (int i = 0; i < 9; i++) {
            // filter out non-block items
            ItemStack stack =
                    Wrapper.getPlayer().inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }
            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (blackList.contains(block) || block instanceof BlockContainer) {
                continue;
            }

            // filter out non-solid blocks
            if (!Block.getBlockFromItem(stack.getItem()).getDefaultState()
                    .isFullBlock()) {
                continue;
            }

            // don't use falling blocks if it'd fall
            if (((ItemBlock) stack.getItem()).getBlock() instanceof BlockFalling) {
                if (Wrapper.getWorld().getBlockState(belowBlockPos).getMaterial().isReplaceable()) {
                    continue;
                }
            }

            newSlot = i;
            break;

        }

        // check if any blocks were found
        if (newSlot == -1) {
            return;
        }

        Wrapper.getPlayer().inventory.currentItem = newSlot;

        // check if we don't have a block adjacent to blockpos
        if (!checkForNeighbours(blockPos)) {
            return;
        }

        // place block
        placeBlockScaffold(blockPos);
    }

}
