package src.Wiksi.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.Hand;


@FunctionRegister(name = "AirPlace", type = Category.Player)
public class AirPlace extends Function {

    private final Minecraft mc = Minecraft.getInstance();

    private long lastPlacementTime = 0;
    private final long placementDelay = 312;

    @Subscribe
    private void onUpdate(EventUpdate update) {
        if (System.currentTimeMillis() - lastPlacementTime < placementDelay) {
            return;
        }
        if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
            BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult) mc.objectMouseOver;
            BlockPos blockPos = blockRayTraceResult.getPos();
            Direction face = blockRayTraceResult.getFace();
            mc.player.swingArm(Hand.MAIN_HAND);
            mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, blockRayTraceResult);
            lastPlacementTime = System.currentTimeMillis();
        }
    }
}