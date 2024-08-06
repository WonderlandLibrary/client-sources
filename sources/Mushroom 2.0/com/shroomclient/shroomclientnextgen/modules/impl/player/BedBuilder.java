package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.mixin.ClientPlayerInteractionManagerAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Scaffold;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RotationUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import net.minecraft.block.BedBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3i;

/*
@RegisterModule(
        name = "Bed Builder",
        description = "Spams wool (bad)",
        category = ModuleCategory.Player,
        uniqueId = "bedbuilder"
)
*/
public class BedBuilder extends Module {

    @ConfigOption(
        name = "Every x ticks",
        description = "Build every x ticks",
        min = 1,
        max = 10,
        order = 0
    )
    public Integer everyXTicks = 2;

    @ConfigOption(
        name = "Block place reach",
        description = "",
        min = 1,
        max = 6
    )
    public Integer blockPlaceReach = 5;

    @ConfigOption(
        name = "Max rotation range diff",
        description = "I don't even know",
        min = 1,
        max = 1000,
        precision = 0
    )
    public Float maxRotationRangeDiff = 65f;

    int ticks = 0;

    @Override
    protected void onEnable() {
        ticks = 0;
    }

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        ticks++;

        int blockSlot = Scaffold.getBlock();
        if (blockSlot != -1) {
            C.p().getInventory().selectedSlot = blockSlot;
            ((ClientPlayerInteractionManagerAccessor) C.mc.interactionManager).syncSelectedSlot_();
        }

        if (
            C.p().getStackInHand(Hand.MAIN_HAND).getItem() instanceof
            BlockItem blockItem
        ) {
            if (!blockItem.getBlock().getDefaultState().isSolid()) return;
        } else return;

        Fucker2.Bed bed = Fucker2.findBed(25);
        if (bed == null) return;
        ArrayList<BlockPos> pos = new ArrayList<>();
        for (int dX = -1 * blockPlaceReach; dX <= blockPlaceReach; dX++) {
            for (int dY = -1 * blockPlaceReach; dY <= blockPlaceReach; dY++) {
                for (
                    int dZ = -1 * blockPlaceReach;
                    dZ <= blockPlaceReach;
                    dZ++
                ) {
                    BlockPos bp = C.p().getBlockPos().add(new Vec3i(0, 2, 0));
                    pos.add(bp.add(new Vec3i(dX, dY, dZ)));
                }
            }
        }

        Optional<BlockPos> targetBpOpt = pos
            .stream()
            .filter(bp -> {
                Vec2f rot = RotationUtil.getRotation(
                    bp.getX() + 0.5,
                    bp.getY() + 0.5,
                    bp.getZ() + 0.5
                );
                // x pitch
                // y yaw
                BlockHitResult rtr = Scaffold.rayTrace(
                    rot.y,
                    rot.x,
                    blockPlaceReach
                );
                return (
                    rtr != null &&
                    rtr.getType() == HitResult.Type.BLOCK &&
                    !(C.w()
                            .getBlockState(rtr.getBlockPos())
                            .getBlock() instanceof
                        BedBlock) &&
                    !C.p()
                        .getBoundingBox()
                        .contains(
                            rtr
                                .getBlockPos()
                                .add(rtr.getSide().getVector())
                                .toCenterPos()
                        ) &&
                    C.w()
                        .getBlockState(
                            rtr.getBlockPos().add(rtr.getSide().getVector())
                        )
                        .isAir() &&
                    rtr.getBlockPos().add(rtr.getSide().getVector()).getY() <
                        C.p().getBlockY() &&
                    RotationUtil.getRotationDifference(
                            rot,
                            new Vec2f(C.p().getPitch(), C.p().getYaw())
                        ) <
                        maxRotationRangeDiff
                );
            })
            .min(
                Comparator.comparingDouble(bp -> {
                    Vec2f rot = RotationUtil.getRotation(
                        bp.getX() + 0.5,
                        bp.getY() + 0.5,
                        bp.getZ() + 0.5
                    );
                    // x pitch
                    // y yaw
                    BlockHitResult rtr = Scaffold.rayTrace(
                        rot.y,
                        rot.x,
                        blockPlaceReach
                    );
                    if (rtr == null) return Double.MAX_VALUE;
                    return Math.min(
                        rtr
                            .getPos()
                            .squaredDistanceTo(bed.getFeet().toCenterPos()),
                        rtr
                            .getPos()
                            .squaredDistanceTo(bed.getHead().toCenterPos())
                    );
                })
            );

        if (targetBpOpt.isEmpty()) return;
        BlockPos targetBp = targetBpOpt.get();

        Vec2f rot = RotationUtil.getRotation(
            targetBp.getX() + 0.5,
            targetBp.getY() + 0.5,
            targetBp.getZ() + 0.5
        );
        // x pitch
        // y yaw
        e.setPitch(rot.x);
        e.setYaw(rot.y);

        if (ticks % everyXTicks != 0) return;
        ticks = 0;

        BlockHitResult rtr = Scaffold.rayTrace(rot.y, rot.x, blockPlaceReach);
        ActionResult actResult = C.mc.interactionManager.interactBlock(
            C.p(),
            Hand.MAIN_HAND,
            rtr
        );
        if (actResult.shouldSwingHand()) C.p().swingHand(Hand.MAIN_HAND);
    }
}
