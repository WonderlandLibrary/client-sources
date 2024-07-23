package io.github.liticane.monoxide.module.impl.combat;

import net.minecraft.util.EnumFacing;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.rotation.RayTraceRangeEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;

@ModuleData(name = "Reach", description = "Allows you to hit further", category = ModuleCategory.COMBAT)
public class ReachModule extends Module {

    public NumberValue<Float> attackRange = new NumberValue<>("Attack Range", this, 3f, 3f, 6f, 1);
    public BooleanValue fixServersSideMisplace = new BooleanValue("Fix Server-Side Misplace", this, true);

    double correctedRange;

    @Listen
    public void onRayTrace(RayTraceRangeEvent rayTraceRangeEvent) {
        correctedRange = this.attackRange.getValue() + 0.00256f;
        if (this.fixServersSideMisplace.getValue()) {
            final float n = 0.010625f;
            if (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH || mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST) {
                correctedRange += n * 2.0f;
            }
        }
        rayTraceRangeEvent.setRange((float) correctedRange);
        rayTraceRangeEvent.setBlockReachDistance((float) Math.max(mc.playerController.getBlockReachDistance(), correctedRange));
    }
}
