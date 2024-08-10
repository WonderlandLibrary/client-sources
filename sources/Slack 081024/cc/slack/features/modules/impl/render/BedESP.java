package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@ModuleInfo(
        name = "BedESP",
        category = Category.RENDER
)
public class BedESP extends Module {

    private final NumberValue<Integer> range = new NumberValue<>("Range", 15, 2, 30, 1);
    private final NumberValue<Double> rate = new NumberValue<>("Rate", 0.4D, 0.1D, 3D, 0.1D);

    private BlockPos[] bed = null;
    private final List<BlockPos[]> beds = new ArrayList<>();
    private long lastCheck = 0L;

    public BedESP() {
        addSettings(range, rate);
    }


    @SuppressWarnings("unused")
    @Listen
    public void onUpdate(UpdateEvent event) {
        if (System.currentTimeMillis() - lastCheck >= rate.getValue() * 1000.0) {
            lastCheck = System.currentTimeMillis();

            int rangeValue = range.getValue();
            for (int i = -rangeValue; i <= rangeValue; ++i) {
                for (int j = -rangeValue; j <= rangeValue; ++j) {
                    for (int k = -rangeValue; k <= rangeValue; ++k) {
                        BlockPos blockPos = new BlockPos(mc.thePlayer.posX + j, mc.thePlayer.posY + i, mc.thePlayer.posZ + k);
                        IBlockState getBlockState = mc.theWorld.getBlockState(blockPos);
                        if (getBlockState.getBlock() == Blocks.bed && getBlockState.getValue(BlockBed.PART) == BlockBed.EnumPartType.FOOT) {
                            for (BlockPos[] bedPair : beds) {
                                if (BlockPos.isSamePos(blockPos, bedPair[0])) {
                                    continue;
                                }
                            }
                            beds.add(new BlockPos[]{blockPos, blockPos.offset(getBlockState.getValue(BlockBed.FACING))});
                        }
                    }
                }
            }
        }
    }

    @Listen
    public void onRender(RenderEvent event) {
        if (event.getState() == RenderEvent.State.RENDER_3D) {
            if (BlockPos.nullCheck() && !beds.isEmpty()) {
                Iterator<BlockPos[]> iterator = beds.iterator();
                while (iterator.hasNext()) {
                    BlockPos[] blockPos = iterator.next();
                    if (mc.theWorld.getBlockState(blockPos[0]).getBlock() instanceof BlockBed) {
                        RenderUtil.renderBed(blockPos);
                    } else {
                        iterator.remove();
                    }
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        bed = null;
        beds.clear();
    }
}
