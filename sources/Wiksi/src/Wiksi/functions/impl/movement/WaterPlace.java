package src.Wiksi.functions.impl.movement;


import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

@FunctionRegister(name = "WaterPlace", type = Category.Movement)
public class WaterPlace extends Function {
    private final Minecraft mc = Minecraft.getInstance();
    private long lastPlacementTime = 0L;
    private final long placementDelay = 312L;

    public WaterPlace() {
    }

    @Subscribe
    private void onUpdate(EventUpdate update) {
        if (System.currentTimeMillis() - this.lastPlacementTime >= 312L) {
            if (this.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                double distance = Math.min(3.3, 3.299999952316284);
                BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult)this.mc.objectMouseOver;
                BlockPos blockPos = blockRayTraceResult.getPos();
                Direction face = blockRayTraceResult.getFace();
                if (this.mc.world.getBlockState(blockPos).getBlock() == Blocks.WATER) {
                    this.mc.player.swingArm(Hand.MAIN_HAND);
                    this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, Hand.MAIN_HAND, blockRayTraceResult);
                    this.lastPlacementTime = System.currentTimeMillis();
                }
            }

        }
    }
}
