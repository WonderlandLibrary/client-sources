package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MovementInputEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.mixin.ClientPlayerInteractionManagerAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.player.NoFall;
import com.shroomclient.shroomclientnextgen.protection.JnicInclude;
import com.shroomclient.shroomclientnextgen.util.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
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
    name = "Scaffold",
    uniqueId = "scaffold",
    description = "Auto Bridges",
    category = ModuleCategory.Movement
)
public class Scaffold extends Module {

    @ConfigMode
    @ConfigParentId("scaffoldMode")
    @ConfigOption(
        name = "Scaffold Mode",
        description = "Presets For Different Anticheats",
        order = 0.1
    )
    public static ModeStink scaffoldMode = ModeStink.Simple;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 0, 1, 4 })
    @ConfigOption(
        name = "Keep Y",
        description = "Doesn't Bridge Above Or Below The Y Value Scaffold Was Set On",
        order = 1
    )
    public static Boolean keepY = false;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigParentId("rotmod")
    @ConfigOption(name = "Rotations", description = "", order = 2)
    public static RotationMode rotationMode = RotationMode.Backwards;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigParentId(value = "dontclosest")
    @ConfigOption(
        name = "Don't Use Closest Yaw",
        description = "Rotates To Blocks Center Instead Of Closest To Previous Yaw",
        order = 2.1
    )
    public static Boolean DontClosest = false;

    //@ConfigChild(value = "dontclosest")
    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigOption(
        name = "Constant Yaw",
        description = "Slightly Ignores Raytracing, Dangerous",
        order = 2.2
    )
    public static Boolean constantYaw = false;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigOption(
        name = "Fix Head Snap",
        description = "Makes The Rotations Less Snappy, Sacrifices Accuracy.",
        order = 2.3
    )
    public static Boolean headSnapFix = true;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 0, 1 })
    @ConfigOption(name = "Sprint Mode", description = "", order = 3)
    public static MovementMode sprintMode = MovementMode.Hypixel;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigParentId("lowestPithch")
    @ConfigOption(
        name = "Use Lowest Pitch",
        description = "Helps Bypass, But Bridges Down",
        order = 3.9
    )
    public static Boolean bridgeDown = false;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigOption(
        name = "Better Y Check",
        description = "Stops Bridging Down",
        order = 4
    )
    public static Boolean besterYcheck = false;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1, 2 })
    @ConfigOption(
        name = "Alan Industries",
        description = "Hypixel Keep Y LONG LIVE THE WOOD",
        order = 6.1
    )
    public static Boolean alanIndustries = false;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 3 })
    @ConfigOption(name = "Movement Mode", description = "", order = 6.2)
    public static MoveModeHypisel sprintingModeHypixel =
        MoveModeHypisel.Hypixel_Safe;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 3 })
    @ConfigOption(
        name = "Smooth Rotations",
        description = "Helps Bypass, My Cause You To Fall Off",
        order = 6.3
    )
    public static Boolean smoothRots = true;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigParentId("constantpitch")
    @ConfigOption(
        name = "Constant Pitch",
        description = "Keeps A Constant Pitch However It Will No Longer Have Strict Raytracing",
        order = 6.2
    )
    public static Boolean constantPitch = false;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigOption(
        name = "Tiny Pitch Correction",
        description = "Adds Tiny Pitch Correction To Constant Pitch To Help Bypass",
        order = 6.3
    )
    public static Boolean tinyPitchCorrect = false;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 3 })
    @ConfigOption(
        name = "Tower Mode",
        description = "How To Tower Up",
        order = 6.89
    )
    public static TowerModeHypisel towerMode = TowerModeHypisel.Fast;

    @ConfigOption(
        name = "Safewalk",
        description = "Never Fall In Void",
        order = 6.9
    )
    public static Boolean safeWalk = false;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigOption(
        name = "Min Place Delay, In Ticks",
        description = "Min Delay Between Block Placing",
        min = 0,
        max = 5,
        order = 7
    )
    public static Integer placemindelay = 0;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigOption(
        name = "Max Place Delay, In Ticks",
        description = "Max Delay Between Block Placing",
        min = 0,
        max = 5,
        order = 8,
        precision = 0
    )
    public static Integer placemaxdelay = 0;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 0, 1 })
    @ConfigOption(
        name = "Min Rotation Pitch",
        description = "",
        min = 0,
        max = 90,
        order = 9
    )
    public static Integer minYaw = 60;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 0, 1 })
    @ConfigOption(
        name = "Max Rotation Pitch",
        description = "",
        min = 0,
        max = 90,
        order = 10
    )
    public static Integer maxYaw = 85;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigOption(
        name = "No Self Place",
        description = "Stops Placing Blocks Inside Of Self",
        order = 6.9
    )
    public static Boolean noSelfPlaceNormal = false;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigOption(
        name = "Only Necessary Place",
        description = "Only Places Blocks If You Will Fall Multiple Blocks",
        order = 6.91
    )
    public static Boolean onlyNecPlace = false;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigOption(
        name = "Max Y Velocity",
        description = "Only Places Blocks Below A Certain Y Velocity",
        order = 11.9
    )
    public static Boolean maxYVelocity = false;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigOption(
        name = "Better Pitch",
        description = "Uses A Better Pitch For Closest Rots",
        order = 8.9
    )
    public static Boolean betterPitchClosest = true;

    @ConfigOption(
        name = "Outline",
        description = "Outlines The Last Placed Block",
        order = 13
    )
    public static Boolean outline = false;

    @ConfigParentId("visualmode")
    @ConfigOption(
        name = "Blocks info",
        description = "Shows Block Info",
        order = 13.1
    )
    // bro who named this :skull:
    public static Boolean idkimdrunkandtiredaddscoliosissyondiscordhaiiiiwhycantiputexclamationmarksorcolonsinthis =
        true;

    @ConfigChild("visualmode")
    @ConfigOption(name = "Visual Mode", description = "", order = 13.2)
    // bro who named this :skull:
    public static VisualMode visualMode = VisualMode.Mushroom;

    public static boolean noSprint = false;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigOption(
        name = "Max Y Velocity Number",
        description = "Max Y Velocity Before Placing Blocks",
        min = 0f,
        max = 0.5f,
        precision = 2,
        order = 12
    )
    public static Float maxYvelo = 0.12f;

    static int ticks = 0;
    static BlockHitResult rayrace = null;
    static int block = -1;
    static float rotationYaw = 0;
    static float rotationPitch = 81;
    static int blocks = 0;
    static int TIGGA = 0;
    static BlockPos lastPlacedBlock = null;

    @ConfigChild(value = "scaffoldMode", parentEnumOrdinal = { 1 })
    @ConfigOption(
        name = "Max Block Reach",
        description = "",
        min = 0,
        max = 6,
        precision = 2,
        order = 11
    )
    public Float reachDistance = 5f;

    int stack = 0;
    Direction lastSide;
    Direction lastLastSide;
    Direction lastLastLastSide;
    Direction neededSide;
    boolean lastGround = false;
    boolean gogo = false;
    int tickTime = 0;
    int crouchTick = 0;
    Vec2f rot;
    int lastBlocks = 0;
    int pitchOffset = -3;
    boolean up = true;
    BlockPos closest = null;
    RotationUtil.Rotation prevRot = new RotationUtil.Rotation(0, 0);

    public long lastScaffoldCrouch = System.currentTimeMillis();

    RotationUtil.Rotation rot2 = null;

    public static boolean isOverAir() {
        double x = C.p().getX();
        double y = C.p().getY() - 1D;
        double z = C.p().getZ();
        BlockPos p = new BlockPos(
            MathHelper.floor(x),
            MathHelper.floor(y),
            MathHelper.floor(z)
        );
        return C.w().getBlockState(p).getBlock() instanceof AirBlock;
    }

    public static boolean isOnEdge() {
        double x = C.p().getX();
        double z = C.p().getZ();

        BlockPos p = new BlockPos(
            MathHelper.floor(x),
            MathHelper.floor(0),
            MathHelper.floor(z)
        );

        return (
            (p.getX() > x || p.getX() < x || p.getZ() > z || p.getZ() < z) &&
            isOverAir()
        );
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
        BlockHitResult res = C.w()
            .raycast(
                new RaycastContext(
                    vec3,
                    vec5,
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    C.mc.cameraEntity
                )
            );

        if (res.getSide() == Direction.UP && keepY) return null;

        if (besterYcheck) return res;

        if (
            noSelfPlaceNormal &&
            (!alanIndustries || C.mc.options.jumpKey.isPressed())
        ) if (
            ((res.getBlockPos().getY() > (int) C.p().getY() - 1 &&
                    C.p().isOnGround()) ||
                ((res.getBlockPos().getY() > (int) C.p().getY() - 2 &&
                        !C.p().isOnGround())))
        ) return null;

        if (!keepY) {
            if (alanIndustries && !C.mc.options.jumpKey.isPressed()) {
                if (lastPlacedBlock == null) return res;

                if (res.getSide() == Direction.UP) {
                    if (
                        MovementUtil.airTicks >= 3 &&
                        res.getBlockPos().getX() ==
                            C.p().getBlockPos().getX() &&
                        res.getBlockPos().getZ() ==
                            C.p().getBlockPos().getZ() &&
                        (res.getBlockPos().getY() == lastPlacedBlock.getY())
                    ) {
                        return res;
                    }
                }
                if (
                    res.getSide() != Direction.DOWN &&
                    res.getSide() != Direction.UP
                ) {
                    if (lastPlacedBlock.getY() == res.getBlockPos().getY()) {
                        return res;
                    }
                }

                return null;
            }
        }

        return res;
    }

    public static BlockHitResult realRayTrace(
        float yaw,
        float pitch,
        float reach
    ) {
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

    public static boolean shouldSafeWalk() {
        return (
            (ModuleManager.isEnabled(Scaffold.class) && safeWalk) ||
            ModuleManager.isEnabled(HypixelScaffold.class) ||
            Eagle.shouldSafewalk()
        );
    }

    public static boolean scaffolding() {
        return (
            ModuleManager.isEnabled(HypixelScaffold.class) ||
            ModuleManager.isEnabled(Scaffold.class)
        );
    }

    @Override
    protected void onEnable() {
        lastPlacedBlock = null;
        if (C.p() != null) stack = C.p().getInventory().selectedSlot;
    }

    @Override
    protected void onDisable() {
        if (C.p() == null) return;

        C.p().getInventory().selectedSlot = stack;
        blocks = 0;
        lastLastLastSide = null;
        lastBlockPos = null;
    }

    @JnicInclude
    @SubscribeEvent
    public void onRenderTick(RenderTickEvent e) {
        if (
            idkimdrunkandtiredaddscoliosissyondiscordhaiiiiwhycantiputexclamationmarksorcolonsinthis
        ) {
            RenderUtil.setContext(e.drawContext);
            RenderUtil.setMatrix(e.matrixStack);

            switch (visualMode) {
                case Mushroom -> {
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

                    RenderUtil.drawItem(
                        C.p().getInventory().getMainHandStack(),
                        C.mc.getWindow().getScaledWidth() / 2 -
                        (boxWidth / 2) +
                        3,
                        C.mc.getWindow().getScaledHeight() -
                        (C.mc.getWindow().getScaledHeight() / 4) +
                        boxHeight / 4 -
                        RenderUtil.fontSize -
                        1
                    );

                    RenderUtil.drawTextShadow(
                        C.p().getInventory().getMainHandStack().getCount() +
                        " blocks left",
                        C.mc.getWindow().getScaledWidth() / 2 -
                        (boxWidth / 2) +
                        5 +
                        16,
                        C.mc.getWindow().getScaledHeight() -
                        (C.mc.getWindow().getScaledHeight() / 4) +
                        boxHeight / 4 -
                        RenderUtil.fontSize -
                        3,
                        ThemeUtil.themeColors()[0]
                    );
                    RenderUtil.drawTextShadow(
                        blocks + " blocks placed",
                        C.mc.getWindow().getScaledWidth() / 2 -
                        (boxWidth / 2) +
                        5 +
                        16,
                        C.mc.getWindow().getScaledHeight() -
                        (C.mc.getWindow().getScaledHeight() / 4) +
                        boxHeight / 4 -
                        1,
                        ThemeUtil.themeColors()[0]
                    );
                }
                case Simple -> {
                    if (
                        C.p() == null ||
                        C.p().getInventory().getMainHandStack() == null
                    ) return;

                    float boxWidth = (int) Math.max(
                        (RenderUtil.getFontWidth(blocks + " placed")),
                        (RenderUtil.getFontWidth(
                                C.p()
                                    .getInventory()
                                    .getMainHandStack()
                                    .getCount() +
                                " left"
                            ))
                    );
                    int boxHeight = 50;

                    float textScale = 1.5f;

                    boxWidth *= textScale;
                    boxWidth += 50;

                    float xCoord =
                        C.mc.getWindow().getScaledWidth() / 2f - boxWidth / 2f;
                    float yCoord =
                        C.mc.getWindow().getScaledHeight() -
                        (C.mc.getWindow().getScaledHeight() / 4f) +
                        3 -
                        boxHeight / 2f;

                    RenderUtil.drawRoundedRect2(
                        xCoord,
                        yCoord,
                        boxWidth,
                        boxHeight,
                        5,
                        new Color(20, 20, 20, 100),
                        false,
                        false,
                        false,
                        false
                    );
                    RenderUtil.drawRoundedGlow(
                        xCoord,
                        yCoord,
                        boxWidth,
                        boxHeight,
                        5,
                        5,
                        new Color(20, 20, 20, 100),
                        100,
                        false,
                        false,
                        false,
                        false
                    );

                    e.matrixStack.push();
                    e.matrixStack.translate(xCoord, yCoord + 5, 0);
                    e.matrixStack.scale(2.5f, 2.5f, 0);
                    RenderUtil.drawItem(
                        C.p().getInventory().getMainHandStack(),
                        0,
                        0
                    );
                    e.matrixStack.pop();

                    RenderUtil.drawText(
                        blocks + " placed",
                        (int) (xCoord + 45),
                        (int) (yCoord + 10),
                        new Color(230, 230, 230),
                        textScale
                    );
                    RenderUtil.drawText(
                        C.p().getInventory().getMainHandStack().getCount() +
                        " left",
                        (int) (xCoord + 45),
                        (int) (yCoord + 30),
                        new Color(230, 230, 230),
                        textScale
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public void onMovementEvent(MovementInputEvent event) {
        if (sprintMode == MovementMode.Legit && C.p().isOnGround()) {
            if (tickTime - crouchTick < 3) {
                event.sneaking = true;
            }
        }

        if (isOverAir() && sprintMode == MovementMode.Eagle) {
            event.sneaking = true;
        }
    }

    @JnicInclude
    @SubscribeEvent(EventBus.Priority.HIGH)
    public void onMotionPre(MotionEvent.Pre event) {
        tickTime++;
        if (scaffoldMode == ModeStink.Hypixel_New) return;
        if (MovementUtil.OverrideHeadRotations()) return;

        if (noSprint) {
            TIGGA++;

            if (TIGGA > 2) {
                TIGGA = 0;
                noSprint = false;
            }
        }

        block = getBlock();
        if (block > -1) {
            C.p().getInventory().selectedSlot = block;

            rotationPitch = 81;

            rotationYaw = MovementUtil.getYaw() - 180;
            if (rotationMode == RotationMode.Slanted) rotationYaw =
                MovementUtil.getYaw() - 160;
            if (rotationMode == RotationMode.Closest) {
                if (isOnEdge()) {
                    closest = getClosestBlock();

                    if (closest != null) {
                        float xOffset = 0.5f;
                        float zOffset = 0.5f;

                        if (
                            C.p().getBlockPos().getX() > closest.getX()
                        ) xOffset = 1;
                        else if (
                            C.p().getBlockPos().getX() < closest.getX()
                        ) xOffset = 0;

                        if (
                            C.p().getBlockPos().getZ() > closest.getZ()
                        ) zOffset = 1;
                        else if (
                            C.p().getBlockPos().getZ() < closest.getZ()
                        ) zOffset = 0;

                        rot = RotationUtil.getRotation(
                            closest.getX() + xOffset,
                            closest.getY() + 0.9f,
                            closest.getZ() + zOffset
                        );

                        rotationYaw = rot.y;

                        float newPitch = rot.x;

                        float min = Math.min(rotationYaw, prevRot.yaw);
                        float max = Math.max(prevRot.yaw, rotationYaw);

                        if (betterPitchClosest) {
                            rotationPitch = newPitch;
                        }

                        if (!DontClosest) {
                            for (float j = min; j < max; j++) {
                                BlockHitResult trace = rayTrace(
                                    j,
                                    newPitch,
                                    reachDistance
                                );
                                if (trace != null) {
                                    if (
                                        trace.getType() ==
                                            HitResult.Type.BLOCK &&
                                        trace.getBlockPos().equals(closest)
                                    ) {
                                        rotationYaw = j;

                                        if (rotationYaw > prevRot.yaw) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if (betterPitchClosest) {
                            for (float j = newPitch; j < 90; j++) {
                                BlockHitResult trace = rayTrace(
                                    rotationYaw,
                                    j,
                                    reachDistance
                                );
                                if (trace != null) {
                                    if (
                                        trace.getType() == HitResult.Type.BLOCK
                                    ) {
                                        rotationPitch = j;
                                    }
                                }
                            }
                        }
                    }

                    if (rot != null) {
                        rotationYaw = rot.y;
                    }
                } else {
                    rotationYaw = MovementUtil.getYaw() - 180;
                }
            }

            event.setYaw(rotationYaw);

            int startInt = maxYaw;
            int endInt = minYaw;

            if (
                alanIndustries &&
                !C.mc.options.jumpKey.isPressed() &&
                C.p().isOnGround()
            ) {
                MovementUtil.jump();
            }

            if (alanIndustries && !C.mc.options.jumpKey.isPressed()) {
                startInt = 85;
                endInt = 65;
            }

            int highest = -1;

            BlockPos closestBlack = null;

            if (!betterPitchClosest || rotationMode != RotationMode.Closest) {
                for (int j = endInt; j < startInt; j++) {
                    BlockHitResult trace = rayTrace(
                        rotationYaw,
                        (float) j,
                        reachDistance
                    );
                    if (trace != null && !trace.isInsideBlock()) {
                        if (trace.getType() == HitResult.Type.BLOCK) {
                            if (
                                trace.getBlockPos().getY() >= highest ||
                                (!besterYcheck && bridgeDown) ||
                                (!bridgeDown &&
                                    (trace.getBlockPos().getY() >= highest ||
                                        closestBlack == null ||
                                        MovementUtil.distanceTo(
                                                C.p().getPos(),
                                                new Vec3d(
                                                    closestBlack.getX(),
                                                    closestBlack.getY(),
                                                    closestBlack.getZ()
                                                )
                                            ) <
                                            MovementUtil.distanceTo(
                                                C.p().getPos(),
                                                new Vec3d(
                                                    trace.getBlockPos().getX(),
                                                    trace.getBlockPos().getY(),
                                                    trace.getBlockPos().getZ()
                                                )
                                            )))
                            ) {
                                if (
                                    trace.getSide() == neededSide ||
                                    neededSide == null ||
                                    (!besterYcheck && bridgeDown) ||
                                    !bridgeDown
                                ) {
                                    highest = trace.getBlockPos().getY();
                                    rotationPitch = j;

                                    closestBlack = trace.getBlockPos();
                                }
                            }
                        }
                    }
                }
            }

            event.setPitch(rotationPitch);

            //  insane bypas $$$
            if (constantPitch && !alanIndustries) {
                if (tinyPitchCorrect) {
                    if (blocks % 24 == 0 && blocks != lastBlocks) {
                        lastBlocks = blocks;
                        if (up) pitchOffset++;
                        else pitchOffset--;
                    }

                    if (pitchOffset >= 3) up = false;
                    else if (pitchOffset <= 0) up = true;
                } else pitchOffset = 0;

                event.setPitch(81 + pitchOffset);
            }

            // nesting
            if (rotationMode == RotationMode.Closest && headSnapFix) {
                if (constantYaw) {
                    event.setYaw(MovementUtil.getYaw() - 180);
                }
            }

            if (
                ((sprintMode == MovementMode.Legit ||
                        sprintMode == MovementMode.Eagle ||
                        sprintMode == MovementMode.Walk) &&
                    !C.p().isSneaking())
            ) {
                MovementUtil.setMotion(0.08);
            }

            rayrace = rayTrace(rotationYaw, rotationPitch, reachDistance);

            // $$$INSERT$$$___LICENSE-CHECK-1___$$$INSERT$$$

            if (rayrace != null && rayrace.getType() == HitResult.Type.BLOCK) {
                closest = rayrace.getBlockPos();

                if (
                    ticks <= 0 ||
                    (placemaxdelay == 0 &&
                        (NoFall.countAir(C.p().getPos()) > 2 || !onlyNecPlace))
                ) {
                    if (!rayrace.isInsideBlock()) {
                        if (
                            sprintMode == MovementMode.Hypixel &&
                            (!alanIndustries ||
                                C.mc.options.jumpKey.isPressed())
                        ) {
                            //if (C.p().isOnGround() && !C.mc.options.jumpKey.isPressed()) MovementUtil.setMotion(0.06);

                            // sets air diag speed
                            if (
                                rayrace.getSide() != lastSide &&
                                !C.p().isOnGround() &&
                                rayrace.getSide() != Direction.UP &&
                                lastSide != Direction.UP
                            ) {
                                MovementUtil.setMotion(0.04);
                            }
                        }

                        // rise bypass 2024 :skull: (if u cant find a bypass just stop people doing the flag!!!!)
                        if (
                            (!alanIndustries ||
                                C.mc.options.jumpKey.isPressed()) &&
                            rayrace.getSide() != lastSide &&
                            sprintMode == MovementMode.Hypixel &&
                            !C.p().isOnGround() &&
                            rayrace.getSide() != Direction.UP &&
                            lastSide != Direction.UP
                        ) MovementUtil.setMotion(0.13);

                        if (
                            (((C.p().getVelocity().y < maxYvelo ||
                                        !maxYVelocity) ||
                                    (alanIndustries &&
                                        !C.mc.options.jumpKey.isPressed())))
                        ) {
                            ActionResult i = interactBlock(
                                C.p(),
                                Hand.MAIN_HAND,
                                rayrace,
                                false
                            );

                            if (i == ActionResult.SUCCESS) {
                                interactBlock(
                                    C.p(),
                                    Hand.MAIN_HAND,
                                    rayrace,
                                    true
                                );

                                if (i.shouldSwingHand()) {
                                    if (
                                        (sprintMode == MovementMode.Legit) &&
                                        !C.p().isSneaking() &&
                                        C.p().isOnGround()
                                    ) {
                                        crouchTick = tickTime;
                                    }

                                    C.p().swingHand(Hand.MAIN_HAND);
                                    C.mc.gameRenderer.firstPersonRenderer.resetEquipProgress(
                                        Hand.MAIN_HAND
                                    );

                                    // INSANE hypixel sprint bypass 2024 no virus
                                    if (
                                        C.p().groundCollision &&
                                        sprintMode == MovementMode.Hypixel
                                    ) {
                                        noSprint = true;
                                        TIGGA = 0;
                                    }

                                    blocks++;
                                    lastSide = rayrace.getSide();
                                    neededSide = null;

                                    // need this, or else u get FUCKING DROPPED.
                                    if (
                                        C.p().getInventory().getStack(block) !=
                                            null &&
                                        C.p()
                                                .getInventory()
                                                .getStack(block)
                                                .getCount() <=
                                            0
                                    ) {
                                        C.p()
                                            .getInventory()
                                            .removeOne(
                                                C.p()
                                                    .getInventory()
                                                    .getStack(block)
                                            );
                                    }
                                }
                            }

                            if (placemaxdelay != 0) {
                                ticks = ((placemindelay) +
                                    new Random()
                                        .nextInt(
                                            (int) ((placemaxdelay) -
                                                (placemindelay) +
                                                1.0)
                                        ));
                                if (!C.p().isOnGround()) ticks++;
                            }
                        }
                    }
                }
            }
        }

        prevRot = new RotationUtil.Rotation(event.getYaw(), event.getPitch());

        if (besterYcheck && bridgeDown) {
            if (C.p().isOnGround() != lastGround) neededSide = Direction.UP;
            if (C.p().isOnGround() || C.p().getVelocity().y < 0.2) neededSide =
                null;
        }

        if (closest != null) {
            if (
                lastPlacedBlock == null ||
                lastPlacedBlock.getY() == closest.getY() ||
                C.mc.options.jumpKey.isPressed()
            ) {
                lastPlacedBlock = closest;
            }
        }

        //lastBlocks = blocks;
        lastGround = C.p().isOnGround();
        --ticks;
    }

    BlockPos lastBlockPos = null;

    @JnicInclude
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

                // this smells wtf
                //if (!packet)
                //    C.w().setBlockState(hitResult.getBlockPos().add(hitResult.getSide().getVector()), oldState);

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

    boolean tower = false;

    @JnicInclude
    @SubscribeEvent
    public void onMotion(MotionEvent.Pre e) {
        if (scaffoldMode != ModeStink.Hypixel_New) return;

        boolean snapOnce = false;
        boolean keepY = false;

        block = getBlock();
        if (block > -1) {
            C.p().getInventory().selectedSlot = getBlock();

            ArrayList<BlockPos> tempClose = getClosestBlockArray();
            if (tempClose != null) closest = tempClose.get(0);
            else closest = null;

            if (closest == null) {
                if (rot2 != null) {
                    e.setPitch(rot2.pitch);
                    e.setYaw(rot2.yaw);
                }
            } else {
                int towerTickPlace = 5;

                if (
                    (C.p().isOnGround() || MovementUtil.airTicks == 1) &&
                    towerMode != TowerModeHypisel.Legit
                ) {
                    tower = (C.mc.options.jumpKey.isPressed());
                } else {
                    if (
                        !C.mc.options.jumpKey.isPressed() ||
                        towerMode == TowerModeHypisel.Legit
                    ) {
                        tower = false;
                    }
                }

                if (
                    towerMode == TowerModeHypisel.Fast &&
                    MovementUtil.isMoving()
                ) {
                    tower = false;
                }

                boolean dowierdtowerstuff = false;

                if (tower) {
                    /* // crazy vulcan tower
                    if (C.p().isOnGround() && MovementUtil.jumps % 2 != 0 && MovementUtil.isMoving()) MovementUtil.jump();
                    if (MovementUtil.airTicks % 5 == 1) MovementUtil.setYmotion(0.33);
                    else if (MovementUtil.airTicks % 5 == 2) MovementUtil.setYmotion(1 - C.p().getY() % 1);
                    else if (MovementUtil.airTicks % 5 == 3) MovementUtil.setYmotion(0);
                    else if (MovementUtil.airTicks % 5 == 4) MovementUtil.setYmotion(0);
                    else if (MovementUtil.airTicks % 5 == 0) {
                        if (!MovementUtil.isMoving()) MovementUtil.jump();
                        else MovementUtil.setYmotion(0);
                    }
                     */

                    // crazy vanilla motion
                    /*
                    if (MovementUtil.airTicks % 4 == 0) {
                        MovementUtil.jump();
                    }
                     */

                    switch (towerMode) {
                        case Fast -> {
                            towerTickPlace = 0;

                            if (
                                MovementUtil.airTicks % 3 == 0
                            ) MovementUtil.setYmotion(0.42F);
                            else if (
                                MovementUtil.airTicks % 3 == 1
                            ) MovementUtil.setYmotion(0.33);
                            else if (MovementUtil.airTicks % 3 == 2) {
                                MovementUtil.setYmotion(0.25000001311302356);
                            }
                        }
                        case Moving -> {
                            towerTickPlace = 5;

                            if (
                                C.p().isOnGround() && !Speed.overrideJumping()
                            ) MovementUtil.jump();
                            if (
                                MovementUtil.airTicks == 1
                            ) MovementUtil.setYmotion(0.33);
                            else if (
                                MovementUtil.airTicks == 2
                            ) MovementUtil.setYmotion(1 - (C.p().getY() % 1));
                            else if (
                                MovementUtil.airTicks == 3
                            ) MovementUtil.setYmotion(0);
                            else if (
                                MovementUtil.airTicks == 4
                            ) MovementUtil.setYmotion(0);
                            else if (MovementUtil.airTicks == 5) {
                                if (
                                    !MovementUtil.isMoving()
                                ) MovementUtil.jump();
                                else MovementUtil.setYmotion(0);
                            } else if (MovementUtil.airTicks == 6) {
                                if (
                                    C.p().getVelocity().y <= 0
                                ) MovementUtil.setYmotion(0);
                            }
                        }
                    }
                    /* // motion tower use % 3 for vanilla tower $$$
                    if (MovementUtil.airTicks % 4 == 0) MovementUtil.setYmotion(0.41965f);
                    else if (MovementUtil.airTicks % 4 == 1) MovementUtil.setYmotion(0.33);
                    else if (MovementUtil.airTicks % 4 == 2) MovementUtil.setYmotion(1 - C.p().getY() % 1);
                     */
                }

                // urm
                BlockPos playerBlockPos = new BlockPos(
                    C.p().getBlockX(),
                    C.p().getBlockY() - 1,
                    C.p().getBlockZ()
                );

                if (rot2 != null && !snapOnce) {
                    e.setPitch(rot2.pitch);
                    e.setYaw(rot2.yaw);
                }

                float xOffset = 0.5f;
                float zOffset = 0.5f;
                float yOffset = 0.01f;

                if (
                    sprintingModeHypixel == MoveModeHypisel.Alan_Industries &&
                    !C.mc.options.jumpKey.isPressed()
                ) yOffset = 0.98f;

                float maxOffset = 1f;

                if (C.p().getBlockPos().getX() > closest.getX()) xOffset =
                    maxOffset;
                else if (C.p().getBlockPos().getX() < closest.getX()) xOffset =
                    1 - maxOffset;

                if (C.p().getBlockPos().getZ() > closest.getZ()) zOffset =
                    maxOffset;
                else if (C.p().getBlockPos().getZ() < closest.getZ()) zOffset =
                    1 - maxOffset;

                RotationUtil.Rotation neededRot = RotationUtil.getRotation(
                    new Vec3d(
                        closest.getX() + xOffset,
                        closest.getY() + yOffset,
                        closest.getZ() + zOffset
                    )
                );
                BlockHitResult realTrace = WorldUtil.rayTrace(
                    neededRot.pitch,
                    neededRot.yaw,
                    reachDistance
                );

                if (keepY) {
                    tempClose.removeIf(
                        block -> raytraceBlock(block).getSide() == Direction.UP
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

                if (sprintingModeHypixel == MoveModeHypisel.Alan_Industries) {
                    if (
                        C.p().isOnGround() && !Speed.overrideJumping()
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
                                raytraceBlock(block).getSide() == Direction.UP
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

                // SNAP ROTS:
                if (snapOnce) {
                    if (NoFall.countAir(C.p().getPos()) > 1) {
                        rot2 = RotationUtil.getRotation(
                            new Vec3d(
                                closest.getX() + xOffset,
                                closest.getY() + yOffset,
                                closest.getZ() + zOffset
                            )
                        );

                        e.setPitch(rot2.pitch);
                        e.setYaw(rot2.yaw);
                    }
                }

                // no head snap
                if (true) {
                    // rough values, usually snappy
                    //e.setPitch(rot.pitch);
                    //e.setYaw(rot.yaw);

                    float min = Math.min(e.getYaw(), neededRot.yaw);
                    float max = Math.max(neededRot.yaw, e.getYaw());

                    float rotationYaw = neededRot.yaw;

                    e.setPitch(neededRot.pitch);

                    for (float j = min; j < max; j++) {
                        BlockHitResult trace = WorldUtil.rayTrace(
                            e.getPitch(),
                            j,
                            reachDistance
                        );
                        if (trace != null) {
                            if (trace.getType() == HitResult.Type.BLOCK) {
                                if (
                                    trace.getSide() != Direction.UP ||
                                    C.p().getVelocity().y < -0.1 ||
                                    sprintingModeHypixel !=
                                        MoveModeHypisel.Alan_Industries
                                ) {
                                    if (
                                        MovementUtil.airTicks <= 0 ||
                                        MovementUtil.airTicks >=
                                            towerTickPlace ||
                                        trace.getSide() != Direction.UP ||
                                        !dowierdtowerstuff
                                    ) {
                                        if (
                                            (MovementUtil.airTicks >=
                                                    towerTickPlace &&
                                                trace.getSide() ==
                                                    Direction.UP) ||
                                            !dowierdtowerstuff
                                        ) {
                                            if (
                                                trace.getSide() !=
                                                    Direction.UP ||
                                                !keepY
                                            ) {
                                                rotationYaw = j;

                                                realTrace = WorldUtil.rayTrace(
                                                    e.getPitch(),
                                                    rotationYaw,
                                                    reachDistance
                                                );

                                                if (rotationYaw >= e.getYaw()) {
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    float rotationPitch = neededRot.pitch;

                    if (realTrace != null) {
                        float highest = realTrace.getBlockPos().getY();

                        for (float j = 40; j < 90; j++) {
                            BlockHitResult trace = WorldUtil.rayTrace(
                                j,
                                rotationYaw,
                                reachDistance
                            );
                            if (trace != null) {
                                if (trace.getType() == HitResult.Type.BLOCK) {
                                    if (
                                        trace.getSide() != Direction.UP ||
                                        C.p().getVelocity().y < -0.1 ||
                                        sprintingModeHypixel !=
                                            MoveModeHypisel.Alan_Industries
                                    ) {
                                        if (
                                            MovementUtil.airTicks <= 0 ||
                                            MovementUtil.airTicks >=
                                                towerTickPlace ||
                                            trace.getSide() != Direction.UP ||
                                            !dowierdtowerstuff
                                        ) {
                                            if (
                                                (MovementUtil.airTicks >=
                                                        towerTickPlace &&
                                                    trace.getSide() ==
                                                        Direction.UP) ||
                                                !dowierdtowerstuff
                                            ) {
                                                if (
                                                    trace
                                                            .getBlockPos()
                                                            .getX() ==
                                                        realTrace
                                                            .getBlockPos()
                                                            .getX() &&
                                                    trace
                                                            .getBlockPos()
                                                            .getZ() ==
                                                        realTrace
                                                            .getBlockPos()
                                                            .getZ() &&
                                                    trace.getSide() ==
                                                        realTrace.getSide() &&
                                                    trace
                                                            .getBlockPos()
                                                            .getY() >=
                                                        highest
                                                ) {
                                                    if (
                                                        trace.getSide() !=
                                                            Direction.UP ||
                                                        !keepY
                                                    ) {
                                                        rotationPitch = j;
                                                        highest = trace
                                                            .getBlockPos()
                                                            .getY();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    RotationUtil.Rotation rot = RotationUtil.getLimitedRotation(
                        new RotationUtil.Rotation(e.getPitch(), e.getYaw()),
                        new RotationUtil.Rotation(rotationPitch, rotationYaw),
                        30
                    );
                    if (!smoothRots) rot = RotationUtil.getLimitedRotation(
                        new RotationUtil.Rotation(e.getPitch(), e.getYaw()),
                        new RotationUtil.Rotation(rotationPitch, rotationYaw),
                        360
                    );

                    e.setYaw(rot.yaw);
                    e.setPitch(rot.pitch);
                }

                if (goingDiagonal()) {
                    //e.setYaw(MovementUtil.getYaw() - 180);
                }

                // stores rots
                if (!snapOnce) rot2 = new RotationUtil.Rotation(
                    e.getPitch(),
                    e.getYaw()
                );

                // movement fix shit
                if (sprintingModeHypixel != MoveModeHypisel.Vanilla) {
                    if (
                        sprintingModeHypixel !=
                            MoveModeHypisel.Hypixel_Autism ||
                        C.mc.options.jumpKey.isPressed() ||
                        !C.p().isOnGround() ||
                        goingDiagonal()
                    ) {
                        if (
                            sprintingModeHypixel !=
                                MoveModeHypisel.Alan_Industries ||
                            C.mc.options.jumpKey.isPressed()
                        ) {
                            if (C.p().isOnGround()) {
                                if (C.mc.options.jumpKey.isPressed()) {
                                    MovementUtil.setMotion(0);
                                } else MovementUtil.setMotion(0.06);
                            } else {
                                //MovementUtil.setMotion(0.13);
                            }
                        }
                    }

                    // sets air diag speed
                    if (
                        realTrace.getSide() != lastSide &&
                        !C.p().isOnGround() &&
                        realTrace.getSide() != Direction.UP &&
                        lastSide != Direction.UP
                    ) {
                        MovementUtil.setMotion(0.14);
                    }
                }

                if (
                    C.w().getBlockState(playerBlockPos).getBlock() instanceof
                    AirBlock
                ) {
                    BlockHitResult raytrace = WorldUtil.rayTrace(
                        e.getPitch(),
                        e.getYaw(),
                        5
                    );

                    if (
                        raytrace.getType() == HitResult.Type.BLOCK &&
                        !raytrace.isInsideBlock()
                    ) {
                        if (
                            raytrace.getBlockPos().getY() >
                                C.p().getY() - 2.1 &&
                            !C.p().isOnGround() &&
                            C.p().fallDistance <= 0 &&
                            C.p().getVelocity().y > 0
                        ) return;
                        if (
                            raytrace.getBlockPos().getY() > C.p().getY() - 1 &&
                            C.p().isOnGround()
                        ) return;
                        if (
                            MovementUtil.airTicks != 0 &&
                            MovementUtil.airTicks < towerTickPlace &&
                            raytrace.getSide() == Direction.UP &&
                            tower
                        ) return;

                        if (keepY && raytrace.getSide() == Direction.UP) return;

                        if (
                            sprintingModeHypixel ==
                                MoveModeHypisel.Alan_Industries &&
                            !C.mc.options.jumpKey.isPressed()
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

                                rot2 = RotationUtil.getRotation(
                                    new Vec3d(
                                        raytrace.getBlockPos().getX(),
                                        raytrace.getBlockPos().getY() - 1,
                                        raytrace.getBlockPos().getZ()
                                    )
                                );

                                e.setPitch(rot2.pitch);
                            }

                            if (
                                raytrace.getSide() == Direction.UP &&
                                C.p().getVelocity().y > -0.1
                            ) return;
                        }

                        // autism extra block sprint method
                        if (
                            C.p().isOnGround() &&
                            sprintingModeHypixel ==
                                MoveModeHypisel.Hypixel_Autism &&
                            !C.mc.options.jumpKey.isPressed() &&
                            !goingDiagonal()
                        ) {
                            if (blocks % 4 == 0) {
                                lastSide = raytrace.getSide();
                                raytrace = new BlockHitResult(
                                    raytrace.getPos(),
                                    C.p()
                                        .getMovementDirection()
                                        .rotateYClockwise(),
                                    raytrace.getBlockPos(),
                                    false
                                );

                                BlockPos newRotsPos = raytrace
                                    .getBlockPos()
                                    .offset(
                                        C.p()
                                            .getMovementDirection()
                                            .rotateYClockwise()
                                            .rotateYClockwise()
                                            .rotateYClockwise()
                                    );

                                rot2 = RotationUtil.getRotation(
                                    new Vec3d(
                                        newRotsPos.getX(),
                                        newRotsPos.getY() - 1,
                                        newRotsPos.getZ()
                                    )
                                );

                                e.setYaw(rot2.yaw);
                                e.setPitch(rot2.pitch);
                            }
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

                            lastLastLastSide = lastLastSide;
                            lastLastSide = lastSide;
                            if (
                                sprintingModeHypixel !=
                                    MoveModeHypisel.Hypixel_Autism ||
                                blocks % 4 != 0
                            ) {
                                lastSide = raytrace.getSide();
                            }

                            if (
                                lastBlockPos == null ||
                                sprintingModeHypixel !=
                                    MoveModeHypisel.Alan_Industries ||
                                C.mc.options.jumpKey.isPressed()
                            ) lastBlockPos = raytrace.getBlockPos();

                            blocks++;
                        } else if (
                            sprintingModeHypixel ==
                                MoveModeHypisel.Hypixel_Autism &&
                            C.p().isOnGround() &&
                            !C.mc.options.jumpKey.isPressed() &&
                            blocks % 4 == 0
                        ) blocks++;
                    }
                }
                /*
                if (autismSprint) {
                    // get new block pos:
                    Direction moveDir = C.p().getMovementDirection().rotateYClockwise();
                    BlockPos realTraceOffset = realTrace.getBlockPos().offset(moveDir);
                    Vec3d targetPos = new Vec3d(realTraceOffset.getX(),realTraceOffset.getY(),realTraceOffset.getZ());


                    if (MovementUtil.distanceTo(targetPos, C.p().getPos()) > 0.1) {
                        double rotationYawNeed = RotationUtil.getRotation(targetPos).yaw;

                        double cos = Math.cos(Math.toRadians(rotationYawNeed + 90.0f));
                        double sin = Math.sin(Math.toRadians(rotationYawNeed + 90.0f));

                        double x = C.p().getPos().x;
                        double z = C.p().getPos().z;

                        x += 0.05 * cos;
                        z += 0.05 * sin;

                        if (C.p().getBlockPos().getX() == realTraceOffset.getX())
                            C.p().setPos(C.p().getX(),C.p().getY(),z);
                        if (C.p().getBlockPos().getZ() == realTraceOffset.getZ())
                            C.p().setPos(x,C.p().getY(),C.p().getZ());
                    }
                }
                 */
            }
        }
    }

    BlockPos realTracePos = new BlockPos(0, 0, 0);

    float minPitch = 0;
    float maxPitch = 90;

    public boolean goingDiagonal() {
        return (
            ((lastLastSide != lastSide || lastLastLastSide != lastLastSide) &&
                lastLastSide != Direction.UP &&
                lastSide != Direction.UP &&
                lastLastLastSide != Direction.UP) ||
            lastLastLastSide == null
        );
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
        //neededRot = new RotationUtil.Rotation(Math.min(Math.max(minPitch, neededRot.pitch), maxPitch), neededRot.yaw);

        return WorldUtil.rayTrace(
            neededRot.pitch,
            neededRot.yaw,
            reachDistance
        );
    }

    @JnicInclude
    private ArrayList<BlockPos> getClosestBlockArray() {
        ArrayList<BlockPos> posList = new ArrayList<>();
        BlockPos baseBp = C.p().getBlockPos();
        int range = ((int) Math.ceil(reachDistance)) + 4; // Buffer just in case :tm:
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= -1; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos2 = baseBp.add(new Vec3i(x, y, z));
                    BlockState bs = C.w().getBlockState(pos2);
                    if (!bs.isAir() && !bs.isLiquid()) {
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

    @JnicInclude
    private BlockPos getClosestBlock() {
        ArrayList<BlockPos> posList = new ArrayList<>();
        BlockPos baseBp = C.p().getBlockPos().add(new Vec3i(0, 2, 0));
        int range = ((int) Math.ceil(reachDistance)) + 4; // Buffer just in case :tm:
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= -2; y++) {
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
        return posList
            .stream()
            //.filter(bp -> MovementUtil.distanceTo(bp.toCenterPos(), C.p().getEyePos()) <= reachDistance)
            .min(
                Comparator.comparingDouble(
                    bp ->
                        MovementUtil.distanceTo(
                            bp.toCenterPos(),
                            C.p().getPos()
                        )
                )
            )
            .orElse(null);
    }

    @SubscribeEvent
    public void render3d(Render3dEvent e) {
        if (closest != null && outline) {
            RenderUtil.drawOutlinedBox(
                closest.getX(),
                closest.getY(),
                closest.getZ(),
                1,
                1,
                1,
                e.partialTicks,
                e.matrixStack,
                ThemeUtil.themeColors()[0]
            );
        }
    }

    public enum ModeStink {
        Simple,
        Advanced,
        Hypixel,
        Hypixel_New,
        Vulcan,
    }

    public enum MovementMode {
        Vanilla,
        Hypixel,
        Legit,
        Eagle,
        Walk,
    }

    public enum MoveModeHypisel {
        Vanilla,
        Hypixel_Safe,
        Hypixel_Autism,
        //Hypixel,
        Alan_Industries,
    }

    public enum TowerModeHypisel {
        Legit,
        Fast,
        Moving,
    }

    public enum VisualMode {
        Mushroom,
        Simple,
    }

    public enum RotationMode {
        Backwards,
        Slanted,
        Closest,
    }
}
