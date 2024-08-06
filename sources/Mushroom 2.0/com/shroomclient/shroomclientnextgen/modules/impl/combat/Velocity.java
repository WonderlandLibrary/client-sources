package com.shroomclient.shroomclientnextgen.modules.impl.combat;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.mixin.EntityVelocityUpdateS2CPacketAccessor;
import com.shroomclient.shroomclientnextgen.mixin.ExplosionS2CPacketAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.LongJump;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;

@RegisterModule(
    name = "Velocity",
    uniqueId = "velo",
    description = "Allows You To Not Take Knockback",
    category = ModuleCategory.Combat
)
public class Velocity extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "", order = 1)
    public Mode mode = Mode.Vanilla;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Horizontal",
        description = "",
        min = 0,
        max = 1,
        precision = 2,
        order = 2
    )
    public Float Horizontal = 0f;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Vertical",
        description = "",
        min = 0,
        max = 1,
        precision = 2,
        order = 3
    )
    public Float Vertical = 0f;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Keep Motion",
        description = "Keeps Velocity Instead Of Pausing Motion",
        order = 4
    )
    public Boolean keepMotion = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigParentId("boost")
    @ConfigOption(
        name = "Damage Boost",
        description = "Adds Velocity On Hit",
        order = 5
    )
    public Boolean boost = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = 1)
    @ConfigParentId("hypixelBoost")
    @ConfigOption(
        name = "Boost",
        description = "Adds Velocity On Hit",
        order = 6
    )
    public Boolean hypixelBoost = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = 1)
    @ConfigOption(
        name = "0 0",
        description = "Take 0 Vertical Or Horizontal Velocity",
        order = 7
    )
    public Boolean zeroZero = false;

    @ConfigChild("boost")
    @ConfigOption(
        name = "Boost Speed",
        description = "",
        min = 0.2f,
        max = 1,
        precision = 3,
        order = 7
    )
    public Float BoostSpeed = 0.4f;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(name = "Only When Sprinting", description = "", order = 8)
    public Boolean OnlyWhenSprinting = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(name = "Only When Moving", description = "", order = 9)
    public Boolean OnlyWhenMoving = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(name = "Only On Ground", description = "", order = 10)
    public Boolean OnlyOnGround = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Only Vertical On Ground",
        description = "Only Take Vertical Knockback When On Ground",
        order = 10
    )
    public Boolean OnlyVerticleOnGround = false;

    boolean boosted = false;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void preMotionEvent(MotionEvent.Pre e) {
        if (
            hypixelBoost &&
            !boosted &&
            C.p().hurtTime == 9 &&
            mode == Mode.Hypixel &&
            MovementUtil.isUserMoving(false)
        ) {
            if (
                !C.p().isOnGround() && !C.p().isInFluid()
            ) MovementUtil.setMotion(0.25);
            else MovementUtil.setMotion(0.02);
            boosted = true;
        }
    }

    @SubscribeEvent
    public void PacketEvent(PacketEvent.Receive.Pre e) {
        if (LongJump.fireballed || C.p() == null) return;

        if (mode == Mode.Vanilla) {
            if (OnlyWhenSprinting && !C.p().isSprinting()) return;
            if (OnlyWhenMoving && !MovementUtil.isMoving()) return;
            if (OnlyOnGround && !C.p().isOnGround()) return;
        }

        if (
            boost &&
            e.getPacket() instanceof EntityVelocityUpdateS2CPacket p &&
            mode == Mode.Vanilla
        ) {
            double forward = C.p().forwardSpeed;
            double strafe = C.p().sidewaysSpeed;

            double yaw = C.p().getRotationClient().y;
            double speed = BoostSpeed;

            if (forward == 0.0 && strafe == 0.0) {
                C.p().setVelocity(0, C.p().getVelocity().y, 0);
            } else {
                if (forward != 0.0) {
                    if (strafe > 0.0) {
                        yaw += ((forward > 0.0) ? -45 : 45);
                    } else if (strafe < 0.0) {
                        yaw += ((forward > 0.0) ? 45 : -45);
                    }
                    strafe = 0.0;
                    if (forward > 0.0) {
                        forward = 1.0;
                    } else if (forward < 0.0) {
                        forward = -1.0;
                    }
                }
                double cos = Math.cos(Math.toRadians(yaw + 90.0f));
                double sin = Math.sin(Math.toRadians(yaw + 90.0f));

                ((EntityVelocityUpdateS2CPacketAccessor) p).setVelocityX(
                        (int) ((forward * speed * cos + strafe * speed * sin) *
                            8000)
                    );
                ((EntityVelocityUpdateS2CPacketAccessor) p).setVelocityZ(
                        (int) ((forward * speed * sin - strafe * speed * cos) *
                            8000)
                    );
            }
        }

        switch (mode) {
            case Vanilla -> {
                if (e.getPacket() instanceof EntityVelocityUpdateS2CPacket p) {
                    if (p.getId() == C.p().getId()) { // Velo is stored as x8000 in packets *shrugs*
                        if (Horizontal != 1f) {
                            ((EntityVelocityUpdateS2CPacketAccessor) p).setVelocityX(
                                    (int) (p.getVelocityX() * Horizontal) +
                                    ((int) (keepMotion
                                                ? C.p().getVelocity().getX() *
                                                8000
                                                : 0))
                                );
                            ((EntityVelocityUpdateS2CPacketAccessor) p).setVelocityZ(
                                    (int) (p.getVelocityZ() * Horizontal) +
                                    ((int) (keepMotion
                                                ? C.p().getVelocity().getZ() *
                                                8000
                                                : 0))
                                );
                        }

                        if (!OnlyVerticleOnGround || !C.p().isOnGround()) {
                            if (
                                Vertical != 1f
                            ) ((EntityVelocityUpdateS2CPacketAccessor) p).setVelocityY(
                                    (int) (p.getVelocityY() * Vertical) +
                                    ((int) (keepMotion
                                                ? C.p().getVelocity().getY() *
                                                8000
                                                : 0))
                                );
                        }
                    }
                } else if (e.getPacket() instanceof ExplosionS2CPacket p) { // Explosions always keep
                    if (Horizontal != 1f) {
                        ((ExplosionS2CPacketAccessor) p).setPlayerVelocityX(
                                p.getPlayerVelocityX() * Horizontal
                            );
                        ((ExplosionS2CPacketAccessor) p).setPlayerVelocityZ(
                                p.getPlayerVelocityZ() * Horizontal
                            );
                    }

                    if (!OnlyVerticleOnGround || !C.p().isOnGround()) {
                        if (
                            Vertical != 1f
                        ) ((ExplosionS2CPacketAccessor) p).setPlayerVelocityY(
                                p.getPlayerVelocityY() * Vertical
                            );
                    }
                }
            }
            case Hypixel -> {
                if (e.getPacket() instanceof EntityVelocityUpdateS2CPacket p) {
                    if (p.getId() == C.p().getId()) {
                        boosted = false;
                        if (
                            !hypixelBoost ||
                            C.p().isInFluid() ||
                            !MovementUtil.isUserMoving(false)
                        ) {
                            ((EntityVelocityUpdateS2CPacketAccessor) p).setVelocityX(
                                    (int) (C.p().getVelocity().getX() * 8000)
                                );
                            ((EntityVelocityUpdateS2CPacketAccessor) p).setVelocityZ(
                                    (int) (C.p().getVelocity().getZ() * 8000)
                                );
                        } else {
                            ((EntityVelocityUpdateS2CPacketAccessor) p).setVelocityX(
                                    p.getVelocityX() / 2
                                );
                            ((EntityVelocityUpdateS2CPacketAccessor) p).setVelocityZ(
                                    p.getVelocityZ() / 2
                                );
                        }

                        if (
                            !C.p().isOnGround() && !hypixelBoost && zeroZero
                        ) ((EntityVelocityUpdateS2CPacketAccessor) p).setVelocityY(
                                (int) (C.p().getVelocity().getY() * 8000)
                            );
                    }
                } else if (e.getPacket() instanceof ExplosionS2CPacket p) {
                    ((ExplosionS2CPacketAccessor) p).setPlayerVelocityX(0);
                    ((ExplosionS2CPacketAccessor) p).setPlayerVelocityZ(0);
                    ((ExplosionS2CPacketAccessor) p).setPlayerVelocityY(0);
                }
            }
            case Packet -> {
                if (e.getPacket() instanceof EntityVelocityUpdateS2CPacket p) {
                    if (p.getId() == C.p().getId()) e.cancel();
                } else if (e.getPacket() instanceof ExplosionS2CPacket p) {
                    e.cancel();
                }
            }
        }
    }

    public enum Mode {
        Vanilla,
        Hypixel,
        Packet,
    }
}
