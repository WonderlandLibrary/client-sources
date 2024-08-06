package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.player.NoFall;
import com.shroomclient.shroomclientnextgen.protection.JnicInclude;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.WorldUtil;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EmptyBlockView;

@RegisterModule(
    name = "Speed",
    uniqueId = "spd",
    description = "Move Faster",
    category = ModuleCategory.Movement
)
public class Speed extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "The Type Of Bypass", order = 1)
    public static Mode mode = Mode.Legit;

    @ConfigOption(
        name = "Auto Jump",
        description = "Automatically Jumps For You",
        order = 1.1
    )
    public static Boolean autoJump = true;

    @ConfigOption(
        name = "Auto Sprint",
        description = "Automatically Sprints For You",
        order = 1.2
    )
    public static Boolean autoSprint = true;

    @ConfigOption(
        name = "No Jump Delay",
        description = "Removes Jump Delay",
        order = 5
    )
    public static Boolean noJumpDelay = true;

    @ConfigOption(
        name = "Disable While Scaffold",
        description = "Needed for INSANE Hypixel speeds",
        order = 6
    )
    public static Boolean disableWhileScaffold = true;

    @ConfigOption(
        name = "Disable In Inventory",
        description = "Needed for INSANE Hypixel Speeds",
        order = 6.1
    )
    public static Boolean disableWhileInventory = true;

    @ConfigOption(
        name = "Pause On Hurt",
        description = "Stops Speed On Hurt",
        order = 7
    )
    public static Boolean stopOnHurt = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 8, 11 })
    @ConfigOption(
        name = "Boost On Jump",
        description = "Adds Extra Jump Force",
        order = 1.01
    )
    public static Boolean INSANEBOOST = true;

    public static boolean headSnapped = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = 12)
    @ConfigOption(
        name = "Jump Velo",
        description = "Jump Velocity For Low Hop, 0.42 Is Default",
        precision = 2,
        min = 0,
        max = 2,
        order = 2
    )
    public Double jumpVelocity = 0.42d;

    @ConfigChild(value = "mode", parentEnumOrdinal = 3)
    @ConfigOption(
        name = "Turn Strafe Delay",
        description = "Delay For Turn Strafe",
        min = 0,
        max = 20,
        order = 3
    )
    public Integer turnStrafeDelay = 3;

    @ConfigChild(value = "mode", parentEnumOrdinal = 13)
    @ConfigOption(
        name = "Vanilla Speed",
        description = "",
        min = 0.01,
        max = 10,
        precision = 2,
        order = 3
    )
    public static Double vanspeed = 2d;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 2, 3 })
    @ConfigOption(
        name = "Strafe Speed",
        description = "Speed That You Strafe At",
        min = 0.05f,
        max = 0.2f,
        precision = 2,
        order = 4
    )
    public Float strafeSpeed = 0.18f;

    @ConfigParentId("stairSpeed")
    @ConfigOption(
        name = "Stair Speed",
        description = "Lets You Move Faster On Stairs",
        order = 11
    )
    public static Boolean stairSpeed = true;

    @ConfigChild("stairSpeed")
    @ConfigOption(
        name = "Stair Speed Speed",
        description = "Speed When On Stairs",
        order = 11,
        max = 1,
        precision = 2
    )
    public static Float stairSpeedSpeed = 0.3f;

    Boolean jump = false;
    double jumporg = 0;
    double jumporgahh = 0;
    double prevYaw = 0;
    int ticks = 0;
    double prevStrafeYaw = 0;
    Vec3d veloPrev;
    int spoofTicks = 0;

    public static float jumpVelo() {
        float jumpHeight = (ModuleManager.isEnabled(Speed.class) &&
                Speed.mode == Speed.Mode.Low_Hop)
            ? ((float) (double) ModuleManager.getModule(
                    Speed.class
                ).jumpVelocity)
            : 0.42F;

        // fun fact! velocity in minecraft is always multiplied by 0.98 before being applied to the player!
        return jumpHeight + (jumpHeight * MovementUtil.jumpVeloMulti());
    }

    @Override
    protected void onEnable() {
        lowhopping = false;
    }

    @Override
    protected void onDisable() {}

    boolean lowhopping = false;

    double motionY = 0;

    @JnicInclude
    @SubscribeEvent(EventBus.Priority.HIGH)
    public void onMotionPre(MotionEvent.Pre e) {
        headSnapped = false;

        Block block = C.w()
            .getBlockState(
                new BlockPos(
                    (int) C.p().getX(),
                    (int) (C.p().getY() - 1),
                    (int) C.p().getZ()
                )
            )
            .getBlock();
        Block block2 = C.w()
            .getBlockState(
                new BlockPos(
                    (int) C.p().getX(),
                    (int) (C.p().getY()),
                    (int) C.p().getZ()
                )
            )
            .getBlock();

        if (shouldSpeed()) {
            if (autoJumping() && C.p().isOnGround()) {
                if (mode == Mode.Ground_Strafe) MovementUtil.setMotion(0.2);
                MovementUtil.jump();
            }

            if (C.p().isOnGround()) {
                if (
                    (C.mc.options.jumpKey.isPressed() || autoJump) &&
                    !MovementUtil.lastOnGround
                ) lowhopping = true;
                if (
                    !C.mc.options.jumpKey.isPressed() &&
                    !autoJump &&
                    C.p().isOnGround()
                ) lowhopping = false;
            }

            switch (mode) {
                case Legit -> {
                    if (
                        C.p().isOnGround() &&
                        C.mc.options.forwardKey.isPressed() &&
                        !e.isSprinting()
                    ) {
                        if (autoSprint) C.p().setSprinting(true);
                    }
                }
                case Strafe -> {
                    if (
                        C.mc.options.jumpKey.isPressed() &&
                        !C.p().isOnGround() &&
                        MovementUtil.getYaw() != prevYaw
                    ) {
                        MovementUtil.setMotion(strafeSpeed);
                    }
                }
                case Turn_Strafe -> {
                    if (
                        C.mc.options.jumpKey.isPressed() &&
                        !C.p().isOnGround() &&
                        !MovementUtil.OverrideHeadRotations() &&
                        MovementUtil.getYaw() != prevYaw &&
                        ticks <= 0
                    ) {
                        e.setYaw(MovementUtil.getYaw());
                        headSnapped = true;

                        MovementUtil.setMotion(strafeSpeed);

                        ticks = turnStrafeDelay;
                    }
                }
                case Vulcan -> {
                    if (C.p().isOnGround()) {
                        jumporg = C.p().getPos().getY();
                        if (
                            C.mc.options.forwardKey.isPressed() ||
                            C.mc.options.backKey.isPressed() ||
                            C.mc.options.leftKey.isPressed() ||
                            C.mc.options.rightKey.isPressed()
                        ) {
                            MovementUtil.setMotion(0.20);
                            MovementUtil.jump();
                            jump = true;
                            jumporgahh = jumporg + 1.25;
                        }
                    }

                    if (jump) {
                        if (C.p().getPos().getY() >= jumporgahh) {
                            if (
                                !(C.mc.options.backKey.isPressed() &&
                                    (C.mc.options.leftKey.isPressed() ||
                                        C.mc.options.rightKey.isPressed()))
                            ) {
                                if ((!C.p().isOnGround() && ticks <= 0)) {
                                    C.p().setVelocity(0, -0.3, 0);
                                }
                                MovementUtil.setMotion(0.15);
                            }
                        }
                    }
                }
                case Vulcan_Glide -> {
                    if (
                        !(C.p().isOnGround() &&
                            !C.mc.options.jumpKey.isPressed())
                    ) {
                        jump = false;
                    }
                    if (MovementUtil.isUserMoving(false)) {
                        if (!C.p().isOnGround() && C.p().getVelocity().y < 0) {
                            jump = true;
                            double motionX = C.p().getVelocity().x;
                            double motionZ = C.p().getVelocity().z;
                            if (NoFall.countAir(C.p().getPos()) < 1) {
                                if (
                                    !(C.mc.options.backKey.isPressed() &&
                                        (C.mc.options.leftKey.isPressed() ||
                                            C.mc.options.rightKey.isPressed()))
                                ) {
                                    if ((!C.p().isOnGround() && ticks <= 0)) {
                                        C.p()
                                            .setVelocity(
                                                motionX,
                                                -0.095,
                                                motionZ
                                            );
                                    }
                                    MovementUtil.setMotion(0.15);
                                }
                            }
                        }
                    }
                }
                case Vulcan_YPort -> {
                    if (
                        !(C.p().isOnGround() &&
                            !C.mc.options.jumpKey.isPressed())
                    ) {
                        jump = false;
                    }
                    if (MovementUtil.isUserMoving(false)) {
                        if (!C.p().isOnGround() && C.p().getVelocity().y < 0) {
                            jump = true;
                            double motionX = C.p().getVelocity().x;
                            double motionZ = C.p().getVelocity().z;
                            if (NoFall.countAir(C.p().getPos()) < 1) {
                                if (
                                    !(C.mc.options.backKey.isPressed() &&
                                        (C.mc.options.leftKey.isPressed() ||
                                            C.mc.options.rightKey.isPressed()))
                                ) {
                                    if ((!C.p().isOnGround() && ticks <= 0)) {
                                        if (!isOverVoid()) C.p()
                                            .setVelocity(
                                                motionX,
                                                -100,
                                                motionZ
                                            );
                                        MovementUtil.setMotion(0.2);
                                    } else {
                                        MovementUtil.setMotion(0.15);
                                    }
                                }
                            }
                        }
                    }
                }
                case BMC -> {
                    if (C.p().isOnGround()) {
                        jump();
                        MovementUtil.setMotion(0.5);
                    } else {
                        MovementUtil.setMotion(0.17);
                    }
                }
                case Hypixel -> {
                    if (
                        ((C.p().getY() - (int) (C.p().getY()) < 1.4)) &&
                        (block
                                .getDefaultState()
                                .isFullCube(
                                    EmptyBlockView.INSTANCE,
                                    BlockPos.ORIGIN
                                ) ||
                            block instanceof AirBlock) &&
                        MovementUtil.lastJumpPos ==
                            MovementUtil.lastLeaveGroundPos &&
                        NoFall.countAir(C.p().getPos()) < 2 &&
                        MovementUtil.isMoving()
                    ) {
                        if (
                            C.p().fallDistance < 2 &&
                            !C.p().isSubmergedInWater() &&
                            !C.p().isInLava()
                        ) {
                            switch (MovementUtil.airTicks) {
                                case 1:
                                    if (INSANEBOOST) MovementUtil.setMotion(
                                        0.24
                                    );
                                    break;
                                case 5:
                                    C.p()
                                        .setVelocity(
                                            new Vec3d(
                                                C.p().getVelocity().x,
                                                0,
                                                C.p().getVelocity().z
                                            )
                                        );
                                    break;
                                case 6:
                                    C.p()
                                        .setVelocity(
                                            new Vec3d(
                                                C.p().getVelocity().x,
                                                -0.08,
                                                C.p().getVelocity().z
                                            )
                                        );
                                    break;
                                case 7:
                                    C.p()
                                        .setVelocity(
                                            new Vec3d(
                                                C.p().getVelocity().x,
                                                -0.16,
                                                C.p().getVelocity().z
                                            )
                                        );
                                    break;
                                case 8:
                                    C.p()
                                        .setVelocity(
                                            new Vec3d(
                                                C.p().getVelocity().x,
                                                -0.24,
                                                C.p().getVelocity().z
                                            )
                                        );
                                    break;
                                case 9:
                                    if (
                                        C.p().getY() - (int) (C.p().getY()) <
                                            0.9 &&
                                        !(block instanceof AirBlock) &&
                                        NoFall.countAir(C.p().getPos()) < 1
                                    ) {
                                        C.p()
                                            .setVelocity(
                                                new Vec3d(
                                                    C.p().getVelocity().x,
                                                    -0.24f,
                                                    C.p().getVelocity().z
                                                )
                                            );
                                        MovementUtil.setMotion(0.15);
                                    }
                                    break;
                                case 10, 11:
                                    if (
                                        C.p().getY() - (int) (C.p().getY()) <
                                            0.6 &&
                                        NoFall.countAir(C.p().getPos()) < 1
                                    ) {
                                        MovementUtil.setMotion(0.17);
                                    }
                                    break;
                            }
                        }
                    }
                }
                case Hypixel_Spoof -> {
                    if (C.p().isOnGround()) {
                        spoofTicks = 0;
                    }

                    if (
                        block2 instanceof AirBlock &&
                        block
                            .getDefaultState()
                            .isFullCube(
                                EmptyBlockView.INSTANCE,
                                BlockPos.ORIGIN
                            ) &&
                        C.p().getY() - (int) (C.p().getY()) < 0.5 &&
                        C.p().hurtTime < 9 &&
                        NoFall.countAir(C.p().getPos()) < 1 &&
                        C.p().getVelocity().y < 0 &&
                        !C.p().isOnGround() &&
                        MovementUtil.lastJumpPos ==
                            MovementUtil.lastLeaveGroundPos &&
                        (int) C.p().getY() == MovementUtil.lastJumpPos.y &&
                        C.p().fallDistance < 5 &&
                        C.p().fallDistance > 0.5
                    ) {
                        MovementUtil.setMotion(0.2);

                        switch (spoofTicks) {
                            case 0, 1:
                                if (C.p().getY() - (int) (C.p().getY()) < 0.6) {
                                    C.p()
                                        .setVelocity(
                                            new Vec3d(
                                                C.p().getVelocity().x,
                                                0f,
                                                C.p().getVelocity().z
                                            )
                                        );
                                    MovementUtil.setMotion(0.23);
                                }
                                break;
                            case 2:
                                break;
                            default:
                                C.p()
                                    .setVelocity(
                                        new Vec3d(
                                            C.p().getVelocity().x,
                                            -0.3f,
                                            C.p().getVelocity().z
                                        )
                                    );
                                break;
                        }

                        spoofTicks++;
                    }
                }
                case Hypixel_Glide -> {
                    if (
                        block
                            .getDefaultState()
                            .isFullCube(
                                EmptyBlockView.INSTANCE,
                                BlockPos.ORIGIN
                            ) &&
                        C.p().getY() - (int) (C.p().getY()) < 0.5 &&
                        C.p().hurtTime < 9 &&
                        NoFall.countAir(C.p().getPos()) < 1 &&
                        C.p().getVelocity().y < 0 &&
                        C.p().fallDistance > 0.5
                    ) {
                        if (C.mc.options.jumpKey.isPressed()) {
                            C.p()
                                .setVelocity(
                                    new Vec3d(
                                        C.p().getVelocity().x,
                                        C.p().getVelocity().y / 3.5,
                                        C.p().getVelocity().z
                                    )
                                );
                            MovementUtil.setMotion(0.2);
                        }
                    }
                }
                case Hypixel_Low_Hop -> {
                    if (lowhopping) {
                        if (C.p().isOnGround()) e.setY(e.getY() + 1e-10);

                        if (
                            ((C.p().getY() - (int) (C.p().getY()) < 1.4)) &&
                            (block
                                    .getDefaultState()
                                    .isFullCube(
                                        EmptyBlockView.INSTANCE,
                                        BlockPos.ORIGIN
                                    ) ||
                                block instanceof AirBlock) &&
                            MovementUtil.lastJumpPos ==
                                MovementUtil.lastLeaveGroundPos &&
                            NoFall.countAir(C.p().getPos()) < 2
                        ) {
                            if (
                                C.p().fallDistance < 2 &&
                                !C.p().isSubmergedInWater() &&
                                !C.p().isInLava()
                            ) {
                                if (
                                    C.mc.options.jumpKey.isPressed() &&
                                    !autoJump &&
                                    C.p().isOnGround()
                                ) {
                                    if (INSANEBOOST) MovementUtil.setMotion(
                                        0.3
                                    );
                                }

                                switch (MovementUtil.airTicks) {
                                    case 1, 8:
                                        if (INSANEBOOST) MovementUtil.setMotion(
                                            0.24
                                        );
                                        break;
                                    case 2:
                                        //if (INSANEBOOST) MovementUtil.setMotion(0.2);
                                        break;
                                    case 4:
                                        //MovementUtil.setMotion(0.1);
                                        if (
                                            C.p().getVelocity().y >= 0.038f
                                        ) MovementUtil.setYmotion(0.038f);
                                        break;
                                    case 6:
                                        MovementUtil.setYmotion(
                                            C.p().getVelocity().y - 0.2d
                                        );
                                        break;
                                }
                            } else lowhopping = false;
                        }
                    }
                }
                case Vanilla -> MovementUtil.setMotion(vanspeed);
            }

            if (
                stairSpeed &&
                C.w()
                        .getBlockState(
                            new BlockPos(
                                C.p().getBlockPos().getX(),
                                C.p().getBlockPos().getY() - 1,
                                C.p().getBlockPos().getZ()
                            )
                        )
                        .getBlock() instanceof
                    StairsBlock &&
                C.p().getY() > C.p().prevY &&
                C.p().isOnGround()
            ) {
                MovementUtil.setMotion(stairSpeedSpeed);
            }
        }

        ticks--;
        prevYaw = MovementUtil.getYaw();
    }

    public void jump() {
        if (
            (!ModuleManager.isEnabled(Timer.class) ||
                Timer.mode == Timer.Mode.Vanilla) &&
            !autoJumping() &&
            !C.mc.player.isInFluid() &&
            !C.mc.player.isInLava()
        ) {
            MovementUtil.jump();
        }
    }

    public boolean autoJumping() {
        return (
            (C.mc.options.jumpKey.isPressed() && noJumpDelay) ||
            (autoJump && MovementUtil.isMoving())
        );
    }

    public static boolean shouldSpeed() {
        return (
            (!disableWhileScaffold || !Scaffold.scaffolding()) &&
            (!stopOnHurt || MovementUtil.ticksSinceLastHurt >= 30) &&
            (C.mc.currentScreen == null || !disableWhileInventory) &&
            (!C.p().isUsingItem() || NoSlow.doNoSlow) &&
            !WorldUtil.overWater()
        );
    }

    public static boolean overrideJumping() {
        return (
            (ModuleManager.isEnabled(Speed.class) &&
                Speed.noJumpDelay &&
                Speed.shouldSpeed()) ||
            (WorldUtil.overWater() &&
                ModuleManager.isEnabled(Jesus.class) &&
                !Jesus.canJump())
        );
    }

    public static boolean isOverVoid() {
        final BlockPos block = new BlockPos(
            (int) C.p().getX(),
            (int) C.p().getY(),
            (int) C.p().getZ()
        );
        if (Blocks.VOID_AIR.equals(C.w().getBlockState(block).getBlock())) {
            return false;
        }

        final Box player = C.p().getBoundingBox();
        return !C.w()
            .getCollisions(
                C.p(),
                new Box(
                    player.minX,
                    0.0,
                    player.minZ,
                    player.maxX,
                    player.maxY,
                    player.maxZ
                )
            )
            .iterator()
            .hasNext();
    }

    public enum Mode {
        Legit,
        Ground_Strafe,
        Strafe,
        Turn_Strafe,
        Vulcan,
        Vulcan_Glide,
        Vulcan_YPort,
        BMC,
        Hypixel,
        Hypixel_Spoof,
        Hypixel_Glide,
        Hypixel_Low_Hop,
        Low_Hop,
        Vanilla,
    }
}
