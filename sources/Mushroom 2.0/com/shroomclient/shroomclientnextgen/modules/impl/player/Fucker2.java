package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.mixin.ClientPlayerInteractionManagerAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.client.Notifications;
import com.shroomclient.shroomclientnextgen.modules.impl.combat.Velocity;
import com.shroomclient.shroomclientnextgen.util.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@RegisterModule(
    name = "Bed Nuker",
    description = "Breaks Nearby Beds",
    uniqueId = "fucker2",
    category = ModuleCategory.Player
)
public class Fucker2 extends Module {

    @ConfigOption(
        name = "Auto Disable",
        description = "Disables On Break Bed",
        order = 1
    )
    public static Boolean autoDisable = true;

    @ConfigOption(
        name = "Set velo to 0/0",
        description = "Sets Velo To 0/0 When Breaking Bed"
    )
    public Boolean zeroZeroVelo = false;

    @ConfigOption(
        name = "Ground Ticks",
        description = "Amount Of Ticks To Be On Ground Before Breaking The Bed",
        min = 0,
        max = 40
    )
    public Integer groundTicks = 3;

    @ConfigOption(
        name = "Max distance",
        description = "",
        min = 0,
        max = 10,
        precision = 1
    )
    public Double maxDistance = 5d;

    @ConfigOption(
        name = "Show Notifications",
        description = "Shows Extra Notifications"
    )
    public Boolean sendNoti = false;

    boolean first = true;
    boolean isDigging = false;

    @Nullable
    Long breakStartMs = null;

    @Nullable
    Long expectedBreakEndMs = null;

    private float oldVeloX = 0;
    private float oldVeloY = 0;
    private boolean wasDigging = false;
    private BlockPos lastTarget;
    private boolean didBreak = false;
    private int groundedTicks = 0;

    private static ArrayList<Bed> findBedsWithinDistance(
        Vec3d source,
        double maxDistance
    ) {
        ArrayList<Bed> o = new ArrayList<>();
        for (BlockPos bp : VectorUtil.getPosWithinDistance(
            source,
            maxDistance
        )) {
            BlockState bs = C.w().getBlockState(bp);
            if (bs.getBlock() instanceof BedBlock) {
                boolean wasFound = false;
                for (Bed bed : o) {
                    if (bed.isSame(bp)) {
                        wasFound = true;
                        break;
                    }
                }
                if (!wasFound) {
                    if (
                        BedBlock.getBedPart(bs) ==
                        DoubleBlockProperties.Type.FIRST
                    ) { // Part is head
                        o.add(
                            new Bed(
                                bp,
                                bp.add(
                                    BedBlock.getOppositePartDirection(
                                        bs
                                    ).getVector()
                                )
                            )
                        );
                    } else { // Part is tail
                        o.add(
                            new Bed(
                                bp.add(
                                    BedBlock.getOppositePartDirection(
                                        bs
                                    ).getVector()
                                ),
                                bp
                            )
                        );
                    }
                }
            }
        }
        return o;
    }

    // Finds the closest bed
    public static @Nullable Bed findBed(double maxDist) {
        Vec3d src = C.p().getPos();
        return findBedsWithinDistance(src, maxDist)
            .stream()
            .min(
                Comparator.comparingDouble(
                    el -> el.closestSqDistanceToCenter(src)
                )
            )
            .orElse(null);
    }

    //  ondisable is called on game load
    @Override
    public void onDisable() {
        if (zeroZeroVelo && !first) {
            ModuleManager.getModule(Velocity.class).Horizontal = oldVeloX;
            ModuleManager.getModule(Velocity.class).Vertical = oldVeloY;
        }
    }

    @Override
    public void onEnable() {
        first = false;

        wasDigging = false;
        lastTarget = null;
        didBreak = false;
        isDigging = false;

        if (zeroZeroVelo) {
            oldVeloX = ModuleManager.getModule(Velocity.class).Horizontal;
            oldVeloY = ModuleManager.getModule(Velocity.class).Vertical;
            ModuleManager.getModule(Velocity.class).Horizontal = 0f;
            ModuleManager.getModule(Velocity.class).Vertical = 0f;
        }
    }

    @SubscribeEvent(EventBus.Priority.HIGHEST)
    public void preMotion(MotionEvent.Pre e) {
        if (isDigging) {
            //RotationUtil.Rotation rot2 = RotationUtil.getRotation(lastTarget);
            //e.setYaw(rot2.yaw);
            //e.setPitch(rot2.pitch);
            C.p().swingHand(Hand.MAIN_HAND);
        }

        Bed bedPos = findBed(maxDistance);
        if (bedPos == null) {
            // Disable when we break the bed
            if (didBreak && autoDisable) ModuleManager.toggle(
                Fucker2.class,
                true
            );
            return;
        }

        ArrayList<BlockPos> adj = VectorUtil.getAdjacent(
            bedPos.getHead(),
            false,
            true
        );
        for (BlockPos a : VectorUtil.getAdjacent(
            bedPos.getFeet(),
            false,
            true
        )) {
            if (!adj.contains(a)) adj.add(a);
        }

        // TODO Stop fetching blockstate thrice

        @Nullable
        BlockPos airedBedPos = bedPos
                    .getHead()
                    .getSquaredDistanceFromCenter(
                        C.p().getX(),
                        C.p().getY() + C.p().getEyeHeight(C.p().getPose()),
                        C.p().getZ()
                    ) <=
                Math.pow(maxDistance, 2) &&
            VectorUtil.getAdjacent(bedPos.getHead(), false, true)
                .stream()
                .map(b -> C.w().getBlockState(b).getBlock() instanceof AirBlock)
                .anyMatch(f -> f)
            ? bedPos.getHead()
            : (bedPos
                            .getFeet()
                            .getSquaredDistanceFromCenter(
                                C.p().getX(),
                                C.p().getY() +
                                C.p().getEyeHeight(C.p().getPose()),
                                C.p().getZ()
                            ) <=
                        Math.pow(maxDistance, 2) &&
                    VectorUtil.getAdjacent(bedPos.getFeet(), false, true)
                        .stream()
                        .map(
                            b ->
                                C.w().getBlockState(b).getBlock() instanceof
                                AirBlock
                        )
                        .anyMatch(f -> f)
                    ? bedPos.getFeet()
                    : null);

        // We can target the bed if there's at least 1 air adjacent
        BlockPos target = airedBedPos != null
            ? airedBedPos
            : adj
                .stream()
                .filter(
                    bp ->
                        !(C.w().getBlockState(bp).getBlock() instanceof
                            BedBlock) &&
                        bp.getSquaredDistanceFromCenter(
                                C.p().getX(),
                                C.p().getY() +
                                C.p().getEyeHeight(C.p().getPose()),
                                C.p().getZ()
                            ) <=
                            Math.pow(maxDistance, 2)
                )
                .min(
                    Comparator.comparingInt(
                        bp ->
                            getBreakTicks(
                                bp,
                                C.p()
                                    .getInventory()
                                    .getStack(getBestToolSlot(bp))
                            )
                    )
                )
                .get();
        if (lastTarget != null && lastTarget.equals(target)) return;
        if (wasDigging) {
            wasDigging = false;
            PacketUtil.sendPacket(
                new PlayerActionC2SPacket(
                    PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK,
                    lastTarget,
                    Direction.fromRotation(C.p().getYaw())
                ),
                false
            );
        }

        if (groundTicks > 0 && groundedTicks < groundTicks) return;

        lastTarget = target;

        startBreaking(e);

        if (
            lastTarget.equals(bedPos.getHead()) ||
            lastTarget.equals(bedPos.getFeet())
        ) didBreak = true;
    }

    @SubscribeEvent(EventBus.Priority.LOWEST)
    public void onPreMotionGrounded(MotionEvent.Pre e) {
        if (e.isOnGround()) {
            groundedTicks++;
        } else {
            groundedTicks = 0;
        }
    }

    private int getBestToolSlot(BlockPos bp) {
        PlayerInventory inv = C.p().getInventory();
        int best = inv.selectedSlot;
        int bestTime = Integer.MAX_VALUE;

        for (int i = 0; i < 9; i++) {
            ItemStack s = inv.getStack(i);
            if (s == null) continue;
            int ticks = getBreakTicks(bp, s);
            if (ticks < bestTime) {
                best = i;
                bestTime = ticks;
            }
        }

        return best;
    }

    private int getBreakTicks(BlockPos bp, ItemStack tool) {
        ItemStack oldHeld = C.p().getMainHandStack();
        C.p().getInventory().main.set(C.p().getInventory().selectedSlot, tool);
        BlockState bs = C.w().getBlockState(bp);
        int ticks = (int) Math.ceil(
            1f / bs.calcBlockBreakingDelta(C.p(), C.w(), bp)
        );
        C.p()
            .getInventory()
            .main.set(C.p().getInventory().selectedSlot, oldHeld);
        return ticks;
    }

    private void endBreaking(MotionEvent.Pre e, int toolSlot, int oldSlot) {
        if (groundTicks > 0 && groundedTicks < groundTicks) {
            DelayUtil.queue(
                ev -> {
                    endBreaking(ev, toolSlot, oldSlot);
                },
                1
            );
        } else {
            C.p().getInventory().selectedSlot = toolSlot;
            ((ClientPlayerInteractionManagerAccessor) C.mc.interactionManager).syncSelectedSlot_();

            DelayUtil.queue(
                evv -> {
                    C.p().getInventory().selectedSlot = oldSlot;
                    ((ClientPlayerInteractionManagerAccessor) C.mc.interactionManager).syncSelectedSlot_();
                },
                1
            );

            RotationUtil.Rotation rot2 = RotationUtil.getRotation(lastTarget);
            e.setYaw(rot2.yaw);
            e.setPitch(rot2.pitch);
            MovementUtil.headSnapped = true;

            isDigging = false;
            PacketUtil.sendPacket(
                new PlayerActionC2SPacket(
                    PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK,
                    lastTarget,
                    Direction.DOWN
                ),
                false
            );
            if (sendNoti) Notifications.notify(
                "Broke block",
                ThemeUtil.themeColors()[0],
                1
            );
        }
    }

    // Snap look at the block, send start digging
    private void startBreaking(MotionEvent.Pre e) {
        int oldSlot = C.p().getInventory().selectedSlot;
        int toolSlot = getBestToolSlot(lastTarget);

        C.p().getInventory().selectedSlot = toolSlot;
        ((ClientPlayerInteractionManagerAccessor) C.mc.interactionManager).syncSelectedSlot_();

        /*DelayUtil.queue((ev) -> {
            C.p().getInventory().selectedSlot = oldSlot;
            ((ClientPlayerInteractionManagerAccessor) C.mc.interactionManager).syncSelectedSlot_();
        }, 1);*/

        RotationUtil.Rotation rot1 = RotationUtil.getRotation(lastTarget);
        e.setYaw(rot1.yaw);
        e.setPitch(rot1.pitch);
        MovementUtil.headSnapped = true;
        wasDigging = true;

        if (!isDigging) {
            isDigging = true;
            C.p().swingHand(Hand.MAIN_HAND);
        }
        PacketUtil.sendPacket(
            new PlayerActionC2SPacket(
                PlayerActionC2SPacket.Action.START_DESTROY_BLOCK,
                lastTarget,
                Direction.DOWN
            ),
            false
        );
        if (sendNoti) Notifications.notify(
            "Started breaking block",
            ThemeUtil.themeColors()[0],
            1
        );

        int breakTicks = getBreakTicks(
            lastTarget,
            C.p().getInventory().getStack(toolSlot)
        );
        DelayUtil.queue(
            ev -> {
                endBreaking(ev, toolSlot, oldSlot);
            },
            breakTicks + 1
        );

        breakStartMs = Instant.now().toEpochMilli();
        expectedBreakEndMs = Instant.now().toEpochMilli() +
        (breakTicks + 1) * 40L;
    }

    @SubscribeEvent
    public void onRender3d(Render3dEvent e) {
        if (wasDigging && lastTarget != null) {
            long expectedTime = expectedBreakEndMs - breakStartMs;
            long passedTime = System.currentTimeMillis() - breakStartMs;

            if (((double) passedTime) / ((double) expectedTime) > 1) return;

            RenderUtil.drawBox2(
                lastTarget.getX(),
                lastTarget.getY(),
                lastTarget.getZ(),
                1,
                MathHelper.clamp(
                    ((double) passedTime) / ((double) expectedTime),
                    0d,
                    1d
                ),
                1,
                e.partialTicks,
                e.matrixStack,
                ThemeUtil.getGradient()[1],
                200
            );
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class Bed {

        public final BlockPos head;
        public final BlockPos feet;

        public boolean isSame(BlockPos pos) {
            return (
                (head.getX() == pos.getX() &&
                    head.getY() == pos.getY() &&
                    head.getZ() == pos.getZ()) ||
                (feet.getX() == pos.getX() &&
                    feet.getY() == pos.getY() &&
                    feet.getZ() == pos.getZ())
            );
        }

        public double closestSqDistanceToCenter(Vec3d src) {
            return Math.min(
                head.getSquaredDistanceFromCenter(
                    src.getX(),
                    src.getY(),
                    src.getZ()
                ),
                feet.getSquaredDistanceFromCenter(
                    src.getX(),
                    src.getY(),
                    src.getZ()
                )
            );
        }
    }
}
