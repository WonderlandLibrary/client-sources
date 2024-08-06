package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.mixin.ClientPlayerInteractionManagerAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.player.NoFall;
import com.shroomclient.shroomclientnextgen.util.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.EmptyBlockView;
import net.minecraft.world.RaycastContext;
import org.apache.commons.lang3.mutable.MutableObject;

@RegisterModule(
    name = "Hypixel Scaffold",
    description = "Scaffold for Hypixel, probably only works on Hypixel",
    uniqueId = "scafold2dollardollar",
    category = ModuleCategory.Movement
)
public class HypixelScaffold extends Module {

    @ConfigOption(
        name = "KeepY",
        description = "Bridges On The Same Y Level",
        order = 1
    )
    public static Boolean alanIndustries = true;

    @ConfigOption(
        name = "Blocks Info",
        description = "Shows Block Info",
        order = 2
    )
    // bro who named this :skull:
    public static Boolean blocksInfo = true;

    public static int blocks = 0;
    public static boolean justStarted = false;
    public static int startedCounter = 0;
    static int block = -1;
    private final int minPitch = 0;
    private final int maxPitch = 90;
    RotationUtil.Rotation rot;
    BlockPos lastBlockPos = null;
    Direction lastSide = null;
    float reachDistance = 5;
    BlockPos closest = null;
    boolean towering = false;
    int towerTicks = 0;

    public static ActionResult interactBlock(
        ClientPlayerEntity player,
        Hand hand,
        BlockHitResult hitResult,
        boolean packet
    ) {
        if (C.isInGame() && C.p() != null && C.w() != null) {
            try {
                if (
                    packet
                ) ((ClientPlayerInteractionManagerAccessor) C.mc.interactionManager).syncSelectedSlot_();
                if (!C.w().getWorldBorder().contains(hitResult.getBlockPos())) {
                    return ActionResult.FAIL;
                }

                BlockState oldState = C.w()
                    .getBlockState(
                        hitResult
                            .getBlockPos()
                            .add(hitResult.getSide().getVector())
                    );

                MutableObject mutableObject = new MutableObject();
                if (packet) {
                    PacketUtil.sendSequencedPacket(sequence -> {
                        mutableObject.setValue(
                            ((ClientPlayerInteractionManagerAccessor) C.mc.interactionManager).interactBlockInternal_(
                                    player,
                                    hand,
                                    hitResult
                                )
                        );
                        return new PlayerInteractBlockC2SPacket(
                            hand,
                            hitResult,
                            sequence
                        );
                    });
                } else {
                    mutableObject.setValue(
                        ((ClientPlayerInteractionManagerAccessor) C.mc.interactionManager).interactBlockInternal_(
                                player,
                                hand,
                                hitResult
                            )
                    );
                }

                if (!packet) C.w()
                    .setBlockState(
                        hitResult
                            .getBlockPos()
                            .add(hitResult.getSide().getVector()),
                        oldState
                    );

                return (ActionResult) mutableObject.getValue();
            } catch (Exception e) {
                ChatUtil.chat(
                    "scaffold cant swing, change via version to 1.20.4 :sob:"
                );
                return ActionResult.FAIL;
            }
        }

        System.out.println(
            "yo calm down bud, we loading the world stop tryna scaffold :skull:"
        );
        return ActionResult.FAIL;
    }

    public static int getBlock() {
        if (
            C.p() != null &&
            C.p()
                    .getInventory()
                    .getStack(C.p().getInventory().selectedSlot)
                    .getItem() instanceof
                BlockItem h &&
            h
                .getBlock()
                .getDefaultState()
                .isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN) &&
            !(h.getBlock() instanceof FallingBlock)
        ) return C.p().getInventory().selectedSlot;
        int current = -1;
        int stackSize = 0;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = C.p().getInventory().getStack(i);
            if (
                stack != null &&
                stackSize < stack.getCount() &&
                stack.getItem() instanceof BlockItem h &&
                h
                    .getBlock()
                    .getDefaultState()
                    .isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN) &&
                !(h.getBlock() instanceof FallingBlock)
            ) {
                stackSize = stack.getCount();
                current = i;
            }
        }
        return current;
    }

    public static BlockHitResult rayTrace(float yaw, float pitch, float reach) {
        final Vec3d vec3 = C.mc.cameraEntity.getCameraPosVec(1.0f);
        final Vec3d vec4 = getVectorForRotation(yaw, pitch);
        final Vec3d vec5 = vec3.add(
            vec4.x * reach,
            vec4.y * reach,
            vec4.z * reach
        );

        return C.w()
            .raycast(
                new RaycastContext(
                    vec3,
                    vec5,
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    C.mc.cameraEntity
                )
            );
    }

    public static Vec3d getVectorForRotation(float yaw, float pitch) {
        final float f = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final float f2 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float f3 = -MathHelper.cos(-pitch * 0.017453292f);
        final float f4 = MathHelper.sin(-pitch * 0.017453292f);
        return new Vec3d((f2 * f3), f4, (f * f3));
    }

    public static boolean negativeExpand(double negativeExpandValue) {
        return (
            C.w()
                    .getBlockState(
                        new BlockPos(
                            (int) (C.p().getPos().getX() + negativeExpandValue),
                            (int) (C.p().getPos().getY() - 1.0),
                            (int) (C.p().getPos().getZ() + negativeExpandValue)
                        )
                    )
                    .getBlock() instanceof
                AirBlock &&
            C.w()
                    .getBlockState(
                        new BlockPos(
                            (int) (C.p().getPos().getX() - negativeExpandValue),
                            (int) (C.p().getPos().getZ() - 1.0),
                            (int) (C.p().getPos().getZ() - negativeExpandValue)
                        )
                    )
                    .getBlock() instanceof
                AirBlock &&
            C.w()
                    .getBlockState(
                        new BlockPos(
                            (int) (C.p().getX() - negativeExpandValue),
                            (int) (C.p().getPos().getY() - 1.0),
                            (int) C.p().getPos().getZ()
                        )
                    )
                    .getBlock() instanceof
                AirBlock &&
            C.w()
                    .getBlockState(
                        new BlockPos(
                            (int) (C.p().getPos().getX() + negativeExpandValue),
                            (int) (C.p().getPos().getY() - 1.0),
                            (int) C.p().getPos().getZ()
                        )
                    )
                    .getBlock() instanceof
                AirBlock &&
            C.w()
                    .getBlockState(
                        new BlockPos(
                            (int) C.p().getPos().getX(),
                            (int) (C.p().getPos().getY() - 1.0),
                            (int) (C.p().getPos().getZ() + negativeExpandValue)
                        )
                    )
                    .getBlock() instanceof
                AirBlock &&
            C.w()
                    .getBlockState(
                        new BlockPos(
                            (int) C.p().getPos().getX(),
                            (int) (C.p().getPos().getY() - 1.0),
                            (int) (C.p().getPos().getZ() - negativeExpandValue)
                        )
                    )
                    .getBlock() instanceof
                AirBlock
        );
    }

    @Override
    protected void onEnable() {
        lastBlockPos = null;
        blocks = 0;
        justStarted = true;
        towerTicks = 0;
        towering = false;
    }

    @Override
    protected void onDisable() {
        justStarted = false;
        startedCounter = 0;
    }

    private double randomAmount() {
        return 1.0E-4 + Math.random() * 0.001;
    }

    @SubscribeEvent
    public void onMotion(MotionEvent.Pre e) {
        if (justStarted) {
            if (C.p().isOnGround()) {
                justStarted = false;
                C.p().setSprinting(false);
                C.p().jump();
                startedCounter = 10;
            } else {
                return;
            }
        }

        if (startedCounter > 0) {
            if (C.p().isOnGround()) startedCounter--;
            return;
        }

        boolean rotateOncePerBlock = true;

        block = getBlock();
        if (block > -1) {
            C.p().getInventory().selectedSlot = getBlock();

            ArrayList<BlockPos> tempClose = getClosestBlock();
            if (tempClose != null) closest = tempClose.get(0);
            else closest = null;

            if (closest == null) {
                if (rot != null) {
                    e.setPitch(rot.pitch);
                    e.setYaw(rot.yaw);
                }
            } else {
                // urm
                BlockPos playerBlockPos = new BlockPos(
                    C.p().getBlockX(),
                    C.p().getBlockY() - 1,
                    C.p().getBlockZ()
                );

                if (rot != null) {
                    e.setPitch(rot.pitch);
                    e.setYaw(rot.yaw);
                }

                float xOffset = 0.5f;
                float zOffset = 0.5f;
                float yOffset = 0.99f;

                float maxOffset = 1;

                if (C.p().getBlockPos().getX() > closest.getX()) xOffset =
                    maxOffset;
                else if (C.p().getBlockPos().getX() < closest.getX()) xOffset =
                    1 - maxOffset;

                if (C.p().getBlockPos().getZ() > closest.getZ()) zOffset =
                    maxOffset;
                else if (C.p().getBlockPos().getZ() < closest.getZ()) zOffset =
                    1 - maxOffset;

                BlockHitResult raytrace1 = rayTrace(
                    e.getYaw(),
                    e.getPitch(),
                    reachDistance
                );

                RotationUtil.Rotation neededRot = RotationUtil.getRotation(
                    new Vec3d(
                        closest.getX() + xOffset,
                        closest.getY() + yOffset,
                        closest.getZ() + zOffset
                    )
                );

                BlockHitResult realTrace = rayTrace(
                    neededRot.yaw,
                    neededRot.pitch,
                    reachDistance
                );

                if (alanIndustries) {
                    C.p().setSprinting(true);
                    if (
                        C.p().isOnGround() && !C.mc.options.jumpKey.isPressed()
                    ) MovementUtil.jump();

                    ArrayList<BlockPos> tempCloseClone = (ArrayList<
                            BlockPos
                        >) tempClose.clone();

                    tempCloseClone.removeIf(
                        block ->
                            raytraceBlock(block).getSide() != Direction.UP ||
                            (lastBlockPos != null &&
                                block.getY() != lastBlockPos.getY()) ||
                            !playerBlockPos.equals(
                                new BlockPos(
                                    block.getX(),
                                    block.getY() + 1,
                                    block.getZ()
                                )
                            )
                    );
                    if (!tempCloseClone.isEmpty()) {
                        tempCloseClone.sort(
                            Comparator.comparingDouble(
                                bp ->
                                    MovementUtil.distanceTo(
                                        bp.toCenterPos(),
                                        C.p().getPos()
                                    )
                            )
                        );
                        closest = tempCloseClone.get(0);
                        realTrace = raytraceBlock(closest);
                    } else {
                        tempClose.removeIf(
                            block ->
                                raytraceBlock(block).getSide() ==
                                    Direction.UP ||
                                (lastBlockPos != null &&
                                    raytraceBlock(block).getBlockPos().getY() !=
                                        lastBlockPos.getY())
                        );

                        if (!tempClose.isEmpty()) {
                            tempClose.sort(
                                Comparator.comparingDouble(
                                    bp ->
                                        MovementUtil.distanceTo(
                                            bp.toCenterPos(),
                                            C.p().getPos()
                                        )
                                )
                            );
                            closest = tempClose.get(0);
                            realTrace = raytraceBlock(closest);
                        } else {
                            return;
                        }
                    }
                }

                // no head snap
                if (
                    (raytrace1.getType() != HitResult.Type.BLOCK ||
                        raytrace1.isInsideBlock() ||
                        raytrace1.getBlockPos() != closest ||
                        raytrace1.getSide() != realTrace.getSide()) &&
                    (!rotateOncePerBlock || NoFall.countAir(C.p().getPos()) > 1)
                ) {
                    float min = Math.min(e.getYaw() + 180, neededRot.yaw + 180);
                    float max = Math.max(neededRot.yaw + 180, e.getYaw() + 180);

                    float rotationYaw = neededRot.yaw + 180;

                    for (float j = min; j < max; j++) {
                        BlockHitResult trace = rayTrace(
                            j - 180,
                            neededRot.pitch,
                            reachDistance
                        );
                        if (trace != null) {
                            if (
                                trace.getType() == HitResult.Type.BLOCK &&
                                trace.getBlockPos().equals(closest) &&
                                trace.getSide() == realTrace.getSide()
                            ) {
                                rotationYaw = j;

                                if (rotationYaw >= e.getYaw()) {
                                    break;
                                }
                            }
                        }
                    }

                    e.setYaw(rotationYaw - 180);
                }

                e.setPitch(neededRot.pitch);

                rot = new RotationUtil.Rotation(e.getPitch(), e.getYaw());

                if (
                    C.w().getBlockState(playerBlockPos).getBlock() instanceof
                    AirBlock
                ) {
                    BlockHitResult raytrace = rayTrace(
                        e.getYaw(),
                        e.getPitch(),
                        5
                    );
                    raytrace = rayTrace(neededRot.yaw, neededRot.pitch, 5);

                    if (
                        raytrace.getType() == HitResult.Type.BLOCK &&
                        !raytrace.isInsideBlock()
                    ) {
                        if (
                            raytrace.getBlockPos().getY() >
                                C.p().getY() - 1.8 &&
                            !C.p().isOnGround() &&
                            C.p().fallDistance <= 0 &&
                            C.p().getVelocity().y != 0
                        ) return;
                        if (
                            raytrace.getBlockPos().getY() > C.p().getY() - 1 &&
                            C.p().isOnGround()
                        ) return;
                        if (
                            e.getPitch() > maxPitch || e.getPitch() < minPitch
                        ) return;
                        if (
                            alanIndustries && !C.mc.options.jumpKey.isPressed()
                        ) {
                            if (
                                lastBlockPos != null &&
                                raytrace.getBlockPos().getY() !=
                                    lastBlockPos.getY()
                            ) {
                                raytrace = raytraceBlock(
                                    new BlockPos(
                                        raytrace.getBlockPos().getX(),
                                        raytrace.getBlockPos().getY() - 1,
                                        raytrace.getBlockPos().getZ()
                                    )
                                );

                                if (
                                    raytrace.getType() !=
                                        HitResult.Type.BLOCK ||
                                    raytrace.isInsideBlock() ||
                                    raytrace.getBlockPos().getY() !=
                                        lastBlockPos.getY()
                                ) return;

                                rot = RotationUtil.getRotation(
                                    new Vec3d(
                                        raytrace.getBlockPos().getX(),
                                        raytrace.getBlockPos().getY() - 1,
                                        raytrace.getBlockPos().getZ()
                                    )
                                );

                                e.setPitch(rot.pitch);
                            }

                            if (
                                raytrace.getSide() == Direction.UP &&
                                C.p().getVelocity().y > -0.1
                            ) return;
                        }

                        ActionResult interactBlock = interactBlock(
                            C.p(),
                            Hand.MAIN_HAND,
                            raytrace,
                            false
                        );

                        if (
                            interactBlock.isAccepted() &&
                            interactBlock.shouldSwingHand()
                        ) {
                            interactBlock(
                                C.p(),
                                Hand.MAIN_HAND,
                                raytrace,
                                true
                            );
                            C.p().swingHand(Hand.MAIN_HAND);

                            blocks++;

                            lastSide = raytrace.getSide();

                            if (
                                lastBlockPos == null ||
                                !alanIndustries ||
                                C.mc.options.jumpKey.isPressed()
                            ) lastBlockPos = raytrace.getBlockPos();
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent e) {
        if (blocksInfo) {
            RenderUtil.setContext(e.drawContext);

            int boxWidth = (int) (27 +
                RenderUtil.getFontWidth(blocks + " blocks placed"));
            int boxHeight = 25;

            RenderUtil.drawCenteredRoundedRect(
                C.mc.getWindow().getScaledWidth() / 2f,
                C.mc.getWindow().getScaledHeight() -
                (C.mc.getWindow().getScaledHeight() / 4f) +
                3,
                boxWidth,
                boxHeight,
                5,
                new Color(20, 20, 20, 100),
                false,
                false,
                false,
                false
            );
            RenderUtil.drawCenteredRoundedGlow(
                C.mc.getWindow().getScaledWidth() / 2f,
                C.mc.getWindow().getScaledHeight() -
                (C.mc.getWindow().getScaledHeight() / 4f) +
                3,
                boxWidth,
                boxHeight,
                5,
                5,
                ThemeUtil.themeColors()[0],
                false,
                false,
                false,
                false
            );

            e.drawContext.drawItem(
                C.p().getInventory().getMainHandStack(),
                C.mc.getWindow().getScaledWidth() / 2 - (boxWidth / 2) + 3,
                C.mc.getWindow().getScaledHeight() -
                (C.mc.getWindow().getScaledHeight() / 4) +
                boxHeight / 4 -
                RenderUtil.fontSize -
                1,
                0
            );

            RenderUtil.drawTextShadow(
                C.p().getInventory().getMainHandStack().getCount() +
                " blocks left",
                C.mc.getWindow().getScaledWidth() / 2 - (boxWidth / 2) + 5 + 16,
                C.mc.getWindow().getScaledHeight() -
                (C.mc.getWindow().getScaledHeight() / 4) +
                boxHeight / 4 -
                RenderUtil.fontSize -
                3,
                ThemeUtil.themeColors()[0]
            );
            RenderUtil.drawTextShadow(
                blocks + " blocks placed",
                C.mc.getWindow().getScaledWidth() / 2 - (boxWidth / 2) + 5 + 16,
                C.mc.getWindow().getScaledHeight() -
                (C.mc.getWindow().getScaledHeight() / 4) +
                boxHeight / 4 -
                1,
                ThemeUtil.themeColors()[0]
            );
        }
    }

    public BlockHitResult raytraceBlock(BlockPos closest) {
        float xOffset = 0.5f;
        float zOffset = 0.5f;
        float yOffset = 0.99f;

        float maxOffset = 1;

        if (C.p().getBlockPos().getX() > closest.getX()) xOffset = maxOffset;
        else if (C.p().getBlockPos().getX() < closest.getX()) xOffset = 1 -
        maxOffset;

        if (C.p().getBlockPos().getZ() > closest.getZ()) zOffset = maxOffset;
        else if (C.p().getBlockPos().getZ() < closest.getZ()) zOffset = 1 -
        maxOffset;

        RotationUtil.Rotation neededRot = RotationUtil.getRotation(
            new Vec3d(
                closest.getX() + xOffset,
                closest.getY() + yOffset,
                closest.getZ() + zOffset
            )
        );

        return rayTrace(neededRot.yaw, neededRot.pitch, reachDistance);
    }

    private ArrayList<BlockPos> getClosestBlock() {
        ArrayList<BlockPos> posList = new ArrayList<>();
        BlockPos baseBp = C.p().getBlockPos().add(new Vec3i(0, 2, 0));
        int range = ((int) Math.ceil(reachDistance)) + 4; // Buffer just in case :tm:
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= -3; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos2 = baseBp.add(new Vec3i(x, y, z));
                    BlockState bs = C.w().getBlockState(pos2);
                    if (
                        !bs.isAir() &&
                        bs.isSolidBlock(C.w(), pos2) &&
                        bs.isFullCube(C.w(), pos2)
                    ) {
                        posList.add(pos2);
                    }
                }
            }
        }

        if (posList.isEmpty()) return null;
        posList.sort(
            Comparator.comparingDouble(
                bp -> MovementUtil.distanceTo(bp.toCenterPos(), C.p().getPos())
            )
        );
        return posList;
    }
}
