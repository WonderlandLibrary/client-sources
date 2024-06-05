package koks.modules.impl.visuals;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventRender3D;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.BoxUtil;
import koks.utilities.value.values.NumberValue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 21:06
 */
public class BlockESP extends Module {

    public NumberValue<Integer> renderRange = new NumberValue<>("Render Range", 50, 100, 1, this);
    public ArrayList<BlockPos> blocks = new ArrayList<>();
    public BoxUtil boxUtil = new BoxUtil();

    public BlockESP() {
        super("BlockESP", "Its mark a block", Category.VISUALS);

        Koks.getKoks().valueManager.addValue(renderRange);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            int range = renderRange.getDefaultValue();
            for (int y = range * -1; y < range; y++) {
                for (int x = range * -1; x < range; x++) {
                    for (int z = range * -1; z < range; z++) {
                        BlockPos blockPos = mc.thePlayer.playerLocation.add(x, y, z);
                        if (mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.emerald_block || mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.redstone_block) {
                            blocks.add(blockPos);
                        }
                    }
                }
            }

            System.out.println(blocks);
        }

        if (event instanceof EventRender3D) {
            for (BlockPos block : blocks) {
                double x = (block.getX() - mc.getRenderManager().renderPosX);
                double y = (block.getY() - mc.getRenderManager().renderPosY);
                double z = (block.getZ() - mc.getRenderManager().renderPosZ);

                AxisAlignedBB axisalignedbb = new AxisAlignedBB(
                        x,
                        y,
                        z,
                        x + 1,
                        y + 1,
                        z + 1);

                boxUtil.renderOutline(axisalignedbb);
            }
        }
    }

    @Override
    public void onEnable() {
        blocks.clear();
    }

    @Override
    public void onDisable() {

    }

}