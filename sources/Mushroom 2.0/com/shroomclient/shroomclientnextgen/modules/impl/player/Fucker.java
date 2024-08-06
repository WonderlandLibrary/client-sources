package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.listeners.BlockESP;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Scaffold;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Speed;
import com.shroomclient.shroomclientnextgen.util.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

//@RegisterModule(
//        name = "Fucker",
//        uniqueId = "fucker",
//        description = "Destroys Beds Around The Player",
//        category = ModuleCategory.Player
//)
public class Fucker extends Module {

    Vec3d closest;
    int prevSlot = -1;
    int swapSlot = -1;
    BlockPos breakingPos = null;
    String blockName = "";
    int lastBreak = 0;
    int u = 0;

    @Override
    protected void onEnable() {
        breakingPos = null;
    }

    @Override
    protected void onDisable() {
        Speed.headSnapped = false;
    }

    @SubscribeEvent(EventBus.Priority.HIGHEST)
    public void onMotion(MotionEvent.Pre n) {
        u++;
        if (!BlockESP.beds.isEmpty()) {
            // sort beds arraylist to get the nearest one!
            BlockESP.beds = (ArrayList<BlockPos>) BlockESP.beds
                .stream()
                .sorted(
                    Comparator.comparingDouble(
                        e ->
                            C.p()
                                .squaredDistanceTo(e.getX(), e.getY(), e.getZ())
                    )
                )
                .collect(Collectors.toList());

            int xCoord = 0;
            int zCoord = 0;

            BlockPos bed = BlockESP.beds.get(0);

            if (bed != null && BlockESP.blockAtPos(bed) instanceof BedBlock) {
                switch (
                    BedBlock.getOppositePartDirection(C.w().getBlockState(bed))
                ) {
                    case NORTH:
                        zCoord -= 1;
                        break;
                    case SOUTH:
                        zCoord += 1;
                        break;
                    case EAST:
                        xCoord += 1;
                        break;
                    case WEST:
                        xCoord -= 1;
                        break;
                }

                closest = new Vec3d(bed.getX(), bed.getY(), bed.getZ());
                if (
                    C.p()
                        .squaredDistanceTo(
                            bed.getX() + 0.5f,
                            bed.getY(),
                            bed.getZ() + 0.5f
                        ) >
                    C.p()
                        .squaredDistanceTo(
                            bed.getX() + 0.5f + xCoord,
                            bed.getY(),
                            bed.getZ() + 0.5f + zCoord
                        )
                ) closest = new Vec3d(
                    bed.getX() + xCoord,
                    bed.getY(),
                    bed.getZ() + zCoord
                );

                Speed.headSnapped = false;

                if (Math.sqrt(C.p().squaredDistanceTo(closest)) < 5 && u > 5) {
                    if (breakingPos == null) {
                        BlockPos bedBlockPos = new BlockPos(
                            (int) closest.x,
                            (int) closest.y,
                            (int) closest.z
                        );

                        // TODO: batman can make it get best block to mine
                        if (
                            !(C.w()
                                    .getBlockState(
                                        new BlockPos(
                                            (int) closest.x,
                                            (int) closest.y + 1,
                                            (int) closest.z
                                        )
                                    )
                                    .getBlock() instanceof
                                AirBlock)
                        ) bedBlockPos = new BlockPos(
                            (int) closest.x,
                            (int) closest.y + 1,
                            (int) closest.z
                        );

                        Vec2f rotation = RotationUtil.getRotation(
                            bedBlockPos.getX() + 0.5f,
                            bedBlockPos.getY(),
                            bedBlockPos.getZ() + 0.5f
                        );

                        n.setYaw(rotation.y);
                        n.setPitch(rotation.x);

                        Speed.headSnapped = true;

                        breakingPos = bedBlockPos;

                        blockName = C.w()
                            .getBlockState(breakingPos)
                            .getBlock()
                            .getName()
                            .getString();

                        if (
                            C.w()
                                .getBlockState(breakingPos)
                                .getBlock() instanceof
                            AirBlock
                        ) {
                            breakingPos = null;

                            u = 0;
                            blockName = "";
                            return;
                        }

                        //Notifications.notify("Breaking " + blockName, new Color(100, 250, 150), 0);

                        prevSlot = C.p().getInventory().selectedSlot;

                        // autotool
                        for (int i = 0; i < 9; i++) {
                            ItemStack stack = C.p().getInventory().getStack(i);
                            if (
                                stack != null &&
                                stack.getMiningSpeedMultiplier(
                                        C.w()
                                            .getBlockState(bedBlockPos)
                                            .getBlock()
                                            .getDefaultState()
                                    ) >
                                    C.p()
                                        .getInventory()
                                        .getMainHandStack()
                                        .getMiningSpeedMultiplier(
                                            C.w()
                                                .getBlockState(bedBlockPos)
                                                .getBlock()
                                                .getDefaultState()
                                        )
                            ) {
                                C.p().getInventory().selectedSlot = i;
                                swapSlot = i;
                            }
                        }

                        final Vec3d vec3 = C.p().getEyePos();
                        final Vec3d vec4 = Scaffold.getVectorForRotation(
                            rotation.y,
                            rotation.x
                        );
                        final Vec3d vec5 = vec3.add(
                            vec4.x * 5,
                            vec4.y * 5,
                            vec4.z * 5
                        );
                        BlockHitResult res = C.w()
                            .raycast(
                                new RaycastContext(
                                    vec3,
                                    vec5,
                                    RaycastContext.ShapeType.COLLIDER,
                                    RaycastContext.FluidHandling.NONE,
                                    C.p()
                                )
                            );

                        // start block break
                        if (
                            C.mc.interactionManager.updateBlockBreakingProgress(
                                breakingPos,
                                res.getSide()
                            )
                        ) {
                            PacketUtil.sendPacket(
                                new PlayerActionC2SPacket(
                                    PlayerActionC2SPacket.Action.START_DESTROY_BLOCK,
                                    breakingPos,
                                    res.getSide()
                                ),
                                false
                            );
                            C.p().swingHand(Hand.MAIN_HAND);
                            C.mc.particleManager.addBlockBreakingParticles(
                                breakingPos,
                                res.getSide()
                            );
                        }
                    } else {
                        if (
                            C.w()
                                .getBlockState(breakingPos)
                                .getBlock() instanceof
                            AirBlock
                        ) {
                            breakingPos = null;
                            blockName = "";
                            u = 0;
                            return;
                        }

                        Vec2f rotation = RotationUtil.getRotation(
                            breakingPos.getX() + 0.5f,
                            breakingPos.getY(),
                            breakingPos.getZ() + 0.5f
                        );

                        final Vec3d vec3 = C.p().getEyePos();
                        final Vec3d vec4 = Scaffold.getVectorForRotation(
                            rotation.y,
                            rotation.x
                        );
                        final Vec3d vec5 = vec3.add(
                            vec4.x * 5,
                            vec4.y * 5,
                            vec4.z * 5
                        );
                        BlockHitResult res = C.w()
                            .raycast(
                                new RaycastContext(
                                    vec3,
                                    vec5,
                                    RaycastContext.ShapeType.COLLIDER,
                                    RaycastContext.FluidHandling.NONE,
                                    C.mc.player
                                )
                            );

                        // update block breaking time
                        if (
                            C.mc.interactionManager.updateBlockBreakingProgress(
                                breakingPos,
                                res.getSide()
                            )
                        ) {
                            C.p().swingHand(Hand.MAIN_HAND);
                            C.mc.particleManager.addBlockBreakingParticles(
                                breakingPos,
                                res.getSide()
                            );
                        }

                        Speed.headSnapped = false;

                        if (
                            C.mc.interactionManager.getBlockBreakingProgress() ==
                                -1 &&
                            breakingPos != null
                        ) {
                            if (lastBreak != -1) {
                                // done breaking
                                n.setYaw(rotation.y);
                                n.setPitch(rotation.x);

                                Speed.headSnapped = true;

                                C.p().swingHand(Hand.MAIN_HAND);
                                PacketUtil.sendPacket(
                                    new PlayerActionC2SPacket(
                                        PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK,
                                        breakingPos,
                                        res.getSide()
                                    ),
                                    false
                                );
                            }

                            breakingPos = null;
                        }
                    }

                    lastBreak =
                        C.mc.interactionManager.getBlockBreakingProgress();
                }
            }
        } else {
            closest = null;
        }
    }

    @SubscribeEvent
    public void onRender3d(Render3dEvent e) {
        if (breakingPos != null) {
            RenderUtil.drawBox2(
                breakingPos.getX(),
                breakingPos.getY(),
                breakingPos.getZ(),
                1,
                C.mc.interactionManager.getBlockBreakingProgress() / 10f,
                1,
                e.partialTicks,
                e.matrixStack,
                ThemeUtil.getGradient()[1],
                200
            );
        }
    }
}
