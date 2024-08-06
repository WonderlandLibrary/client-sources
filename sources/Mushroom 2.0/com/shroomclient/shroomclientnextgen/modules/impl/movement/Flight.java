package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.player.NoFall;
import com.shroomclient.shroomclientnextgen.util.BlinkUtil;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.PacketUtil;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EmptyBlockView;

@RegisterModule(
    name = "Flight",
    uniqueId = "fly",
    description = "Become An Airplane",
    category = ModuleCategory.Movement
)
public class Flight extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "Flight Mode", order = 1)
    public static Mode mode = Mode.Vanilla;

    @ConfigChild("bedfly")
    @ConfigOption(
        name = "Multiplier",
        description = "Multiplier For Bed/Slime Fly",
        min = 0.1,
        max = 100,
        precision = 1,
        order = 5
    )
    public static Double veloMultiplier = 2d;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 0, 1, 2, 3, 4, 5 })
    @ConfigParentId("bedfly")
    @ConfigOption(
        name = "Bed/Slime Fly",
        description = "Increases Bed & Slime Bounce Height (vulcan)",
        order = 4.9
    )
    public static Boolean bedBounceModifier = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 0, 1, 2 })
    @ConfigOption(
        name = "Speed",
        description = "",
        min = 0.5,
        max = 10,
        precision = 1,
        order = 2
    )
    public Float FlySpeed = 1F;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 0, 2 })
    @ConfigOption(
        name = "Vertical Speed",
        description = "Only For Jetpack & Vanilla",
        min = 0.5,
        max = 10,
        precision = 1,
        order = 3
    )
    public Float FlySpeedVert = 1F;

    @ConfigChild(value = "mode", parentEnumOrdinal = 3)
    @ConfigOption(
        name = "Blink While Flying",
        description = "Blinks While Using Block Fly",
        order = 4
    )
    public Boolean Blink = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 5)
    @ConfigOption(
        name = "Auto Damage",
        description = "Self Damages",
        order = 4.1
    )
    public Boolean SelfDamage = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 5)
    @ConfigOption(
        name = "Auto Disable",
        description = "Automatically Disables",
        order = 4.2
    )
    public Boolean AutoDisable = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 5)
    @ConfigOption(
        name = "Keep Y",
        description = "Sets Y Velocity To 0",
        order = 4.3
    )
    public Boolean keepY = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 3)
    @ConfigOption(
        name = "Switch Block",
        description = "Auto Pulls Out A Block",
        order = 4.3
    )
    public Boolean switchblock = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 5)
    @ConfigOption(
        name = "Speed",
        description = "Damage Boost Speed",
        order = 4.4,
        min = 0.1,
        max = 5
    )
    public Float damageBoosting = 1f;

    int ticks = 0;
    boolean vulcanevil = false;
    float fallDistSince = 0;
    float lastFallDist = 0;
    boolean GONE = false;
    boolean boosted = false;

    public static double getVeloMultiplier() {
        return (
                (bedBounceModifier || mode == Mode.Bed_And_Slime) &&
                ModuleManager.isEnabled(Flight.class)
            )
            ? veloMultiplier
            : 0.66d;
    }

    static int getBlock() {
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

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {
        if (Blink || vulcanevil) {
            BlinkUtil.setIncomingBlink(false);
            BlinkUtil.setOutgoingBlink(false);
        }
    }

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        ticks++;
        switch (mode) {
            case JetPack -> {
                if (C.mc.options.jumpKey.isPressed()) {
                    C.p().setVelocity(0, FlySpeedVert, 0);
                }
                if (!C.p().isOnGround()) {
                    MovementUtil.setMotion(FlySpeed * 0.1);
                }
            }
            case KeepY -> {
                C.p().setVelocity(0, 0, 0);
                MovementUtil.setMotion(FlySpeed * 0.1);
            }
            case Vanilla -> {
                float ySpeed =
                    (C.mc.options.jumpKey.isPressed() ? FlySpeedVert : 0) +
                    (C.mc.options.sneakKey.isPressed() ? -FlySpeedVert : 0);
                C.p().setVelocity(0, ySpeed, 0);
                MovementUtil.setMotion(FlySpeed);
            }
            case Block_Fly -> {
                int block = getBlock();
                if (switchblock) {
                    if (block > -1) {
                        C.p().getInventory().selectedSlot = getBlock();
                    }
                }
                if (
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
                ) {
                    Vec3d playerCoords = C.p().getPos();
                    e.setYaw(MovementUtil.getYaw() - 180);
                    e.setPitch(89);

                    BlockHitResult hitResult = new BlockHitResult(
                        new Vec3d(
                            playerCoords.x,
                            playerCoords.y - 1,
                            playerCoords.z
                        ),
                        Direction.DOWN,
                        new BlockPos(
                            (int) playerCoords.x,
                            (int) (playerCoords.y - 1),
                            (int) playerCoords.z
                        ),
                        false
                    );

                    Scaffold.interactBlock(
                        C.p(),
                        Hand.MAIN_HAND,
                        hitResult,
                        false
                    );
                    Scaffold.interactBlock(
                        C.p(),
                        Hand.MAIN_HAND,
                        hitResult,
                        true
                    );

                    C.p().swingHand(Hand.MAIN_HAND);
                    C.mc.gameRenderer.firstPersonRenderer.resetEquipProgress(
                        Hand.MAIN_HAND
                    );

                    if (Blink) {
                        if (ticks % 10 == 0) {
                            BlinkUtil.setIncomingBlink(false);
                            BlinkUtil.setOutgoingBlink(false);
                        } else {
                            BlinkUtil.setIncomingBlink(true);
                            BlinkUtil.setOutgoingBlink(true);
                        }
                    }
                }
            }
            case Vulcan_Glide -> {
                //if (ticks % 20 == 0) MovementUtil.jump();
                if (ticks % 2 == 0 && C.p().fallDistance > 0) {
                    if (!bedBounceModifier || C.p().fallDistance > 1) C.p()
                        .setVelocity(
                            C.p().getVelocity().x,
                            C.p().getVelocity().y / 2.5f,
                            C.p().getVelocity().z
                        );
                }

                if (
                    NoFall.countAir(new Vec3d(e.getX(), e.getY(), e.getZ())) >
                        1 &&
                    !e.isOnGround() &&
                    (!bedBounceModifier || C.p().fallDistance > 3)
                ) {
                    BlinkUtil.setIncomingBlink(true);
                    vulcanevil = true;

                    fallDistSince = C.p().fallDistance - lastFallDist;
                    if (fallDistSince >= 3 && !e.isOnGround()) {
                        fallDistSince = 0;
                        lastFallDist = C.p().fallDistance;
                        e.setOnGround(true);
                    }
                } else if (vulcanevil) {
                    BlinkUtil.setIncomingBlink(false);
                    vulcanevil = false;
                    fallDistSince = 0;
                    lastFallDist = 0;
                }

                if (C.p().isDead()) {
                    BlinkUtil.setIncomingBlink(false);
                }
            }
            case Damage -> {
                if (SelfDamage && C.p().hurtTime <= 2) {
                    PacketUtil.sendPacket(
                        new PlayerMoveC2SPacket.PositionAndOnGround(
                            C.p().getX(),
                            C.p().getY(),
                            C.p().getZ(),
                            false
                        ),
                        false
                    );
                    PacketUtil.sendPacket(
                        new PlayerMoveC2SPacket.PositionAndOnGround(
                            C.p().getX(),
                            C.p().getY() + 3.25,
                            C.p().getZ(),
                            false
                        ),
                        false
                    );
                    PacketUtil.sendPacket(
                        new PlayerMoveC2SPacket.PositionAndOnGround(
                            C.p().getX(),
                            C.p().getY(),
                            C.p().getZ(),
                            false
                        ),
                        false
                    );
                    PacketUtil.sendPacket(
                        new PlayerMoveC2SPacket.PositionAndOnGround(
                            C.p().getX(),
                            C.p().getY(),
                            C.p().getZ(),
                            true
                        ),
                        false
                    );
                }

                if (C.p().hurtTime >= 2) {
                    if (keepY) C.p().setVelocity(0, 0, 0);
                    MovementUtil.setMotion(damageBoosting);
                    boosted = true;
                } else if (boosted && AutoDisable) {
                    boosted = false;
                    ModuleManager.toggle(Flight.class, false);
                }
            }
        }
    }

    public enum Mode {
        JetPack,
        KeepY,
        Vanilla,
        Block_Fly,
        Vulcan_Glide,
        Damage,
        Bed_And_Slime,
    }
}
