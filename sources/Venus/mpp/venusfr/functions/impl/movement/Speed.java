/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import java.util.Iterator;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.movement.TargetStrafe;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.client.TimerUtil;
import mpp.venusfr.utils.player.MoveUtils;
import net.minecraft.block.Blocks;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="Speed", type=Category.Movement)
public class Speed
extends Function {
    ItemStack currentStack = ItemStack.EMPTY;
    public ModeSetting mod = new ModeSetting("\u041c\u043e\u0434", "Grim", "Grim", "HolyWorld", "FunTime");
    private final SliderSetting speedGrim = new SliderSetting("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c", 1.15f, 1.0f, 1.3f, 0.01f).setVisible(this::lambda$new$0);
    private final SliderSetting distanceGrim = new SliderSetting("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f", 2.0f, 0.0f, 5.0f, 0.5f).setVisible(this::lambda$new$1);
    private RemoteClientPlayerEntity fakePlayer;
    private long lastPacketTime = -1L;
    public boolean boosting;
    public TimerUtil timerUtil = new TimerUtil();

    public Speed() {
        this.addSettings(this.mod, this.speedGrim, this.distanceGrim);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.timerUtil.reset();
        this.boosting = false;
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        int n;
        int n2;
        int n3;
        double d;
        double d2;
        double d3;
        double d4;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        double d5;
        double d6;
        float f;
        if (this.mod.is("Matrix Old")) {
            if (Speed.mc.player.isOnGround()) {
                Speed.mc.player.jump();
                f = 10.0f;
                d6 = Speed.mc.player.getForward().x * (double)(f /= 100.0f);
                d5 = Speed.mc.player.getForward().z * (double)f;
                if (MoveUtils.isMoving() && MoveUtils.getMotion() < (double)0.9f) {
                    n10 = 4;
                    Speed.mc.player.motion.x *= 2.25;
                    n9 = 4;
                    n8 = 2;
                    Speed.mc.player.motion.z *= 2.25;
                }
            }
            if (Speed.mc.player.fallDistance >= 1.0f) {
                f = 10.0f;
                d6 = Speed.mc.player.getForward().x * (double)(f /= 100.0f);
                d5 = Speed.mc.player.getForward().z * (double)f;
                if (MoveUtils.isMoving() && MoveUtils.getMotion() < (double)0.9f) {
                    n10 = 2;
                    Speed.mc.player.motion.x *= 2.25;
                    n9 = 4;
                    n8 = 4;
                    Speed.mc.player.motion.z *= 2.25;
                }
            }
        }
        if (this.mod.is("Matrix Old 2") && Speed.mc.player.isOnGround()) {
            Speed.mc.player.jump();
            f = 10.0f;
            d6 = Speed.mc.player.getForward().x * (double)(f /= 100.0f);
            d5 = Speed.mc.player.getForward().z * (double)f;
            if (MoveUtils.isMoving() && MoveUtils.getMotion() < (double)0.8f) {
                n10 = 1;
                n9 = 4;
                n8 = 1;
                n7 = 3;
                Speed.mc.player.motion.x *= 1.75;
                n6 = 2;
                n5 = 5;
                n4 = 2;
                Speed.mc.player.motion.z *= 1.75;
            }
        }
        if (this.mod.is("OnGround") && Speed.mc.player.isOnGround()) {
            if (MoveUtils.isMoving()) {
                Speed.mc.player.setSprinting(false);
                Speed.mc.player.jump(0.0f, 1.2f);
            } else {
                Speed.mc.player.setSprinting(true);
            }
        }
        if (this.mod.is("Timer 1")) {
            if (Speed.mc.player.isInWater() || Speed.mc.player.isInLava() || Speed.mc.player.isOnLadder()) {
                return;
            }
            f = 1.0f;
            if (Speed.mc.player.fallDistance <= 0.1f) {
                f = 1.34f;
            }
            if (Speed.mc.player.fallDistance > 1.0f) {
                f = 0.6f;
            }
            if (MoveUtils.isMoving()) {
                Speed.mc.timer.timerSpeed = 1.0f;
                if (Speed.mc.player.isOnGround()) {
                    if (!Speed.mc.gameSettings.keyBindJump.isKeyDown()) {
                        Speed.mc.player.jump();
                    }
                } else {
                    Speed.mc.timer.timerSpeed = f;
                }
            } else {
                Speed.mc.timer.timerSpeed = 1.0f;
            }
        }
        if (this.mod.is("Timer 2")) {
            if (Speed.mc.player.isInWater() || Speed.mc.player.isInLava() || Speed.mc.player.isOnLadder()) {
                return;
            }
            f = 1.0f;
            if (Speed.mc.player.fallDistance <= 0.1f) {
                f = 1.0f;
            }
            if (Speed.mc.player.fallDistance > 0.6f) {
                f = 1.7f;
            }
            if (MoveUtils.isMoving()) {
                Speed.mc.timer.timerSpeed = 1.0f;
                if (Speed.mc.player.isOnGround()) {
                    if (!Speed.mc.gameSettings.keyBindJump.isKeyDown()) {
                        Speed.mc.player.jump();
                    }
                } else {
                    Speed.mc.timer.timerSpeed = f;
                }
            } else {
                Speed.mc.timer.timerSpeed = 1.0f;
            }
        }
        if (this.mod.is("Vulcan")) {
            Speed.mc.player.jumpMovementFactor = 0.025f;
            if (Speed.mc.player.isOnGround() && MoveUtils.isMoving()) {
                if (Speed.mc.player.collidedHorizontally || Speed.mc.gameSettings.keyBindJump.pressed) {
                    if (!Speed.mc.gameSettings.keyBindJump.pressed) {
                        Speed.mc.player.jump();
                    }
                    return;
                }
                Speed.mc.player.jump();
                Speed.mc.player.motion.y = 0.1;
            }
        }
        if (this.mod.is("LongHop") && Speed.mc.player.fallDistance >= 0.04f && MoveUtils.isMoving()) {
            f = Speed.mc.player.rotationYawHead;
            float f2 = Speed.mc.player.rotationPitch;
            d4 = 1.0;
            d3 = -Math.sin((double)f / 180.0 * Math.PI) * Math.cos((double)f2 / 180.0 * Math.PI) * d4;
            d2 = Math.cos((double)f / 180.0 * Math.PI) * Math.cos((double)f2 / 180.0 * Math.PI) * d4;
            Speed.mc.player.setVelocity(d3, -0.6, d2);
        }
        if (this.mod.is("Elytra")) {
            this.currentStack = Speed.mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (this.currentStack.getItem() == Items.ELYTRA && Speed.mc.player.isOnGround()) {
                Speed.mc.player.jump();
                if (Speed.mc.player.isAirBorne) {
                    Speed.mc.player.startFallFlying();
                    int n11 = 5;
                    n9 = 5;
                    Speed.mc.player.connection.sendPacket(new CEntityActionPacket(Speed.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                    if ((double)Speed.mc.player.fallDistance == 0.1) {
                        // empty if block
                    }
                    Speed.mc.player.jump();
                    Speed.mc.player.jump();
                }
            }
        }
        if (this.mod.is("Bot") && MoveUtils.isMoving()) {
            this.spawnFakePlayer();
            Speed.mc.player.setSprinting(false);
            int n12 = 4;
            n9 = 5;
            n8 = 1;
            Speed.mc.player.connection.sendPacket(new CEntityActionPacket(Speed.mc.player, CEntityActionPacket.Action.START_SPRINTING));
            if (Speed.mc.player.isOnGround()) {
                Speed.mc.player.jump();
            }
        }
        if (this.mod.is("Elytra 2")) {
            this.currentStack = Speed.mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (this.currentStack.getItem() == Items.ELYTRA) {
                if (Speed.mc.player.isOnGround()) {
                    Speed.mc.player.jump();
                    Speed.mc.player.rotationPitchHead = -90.0f;
                } else if (ElytraItem.isUsable(this.currentStack) && !Speed.mc.player.isElytraFlying()) {
                    Speed.mc.player.startFallFlying();
                    int n13 = 4;
                    n9 = 3;
                    Speed.mc.player.connection.sendPacket(new CEntityActionPacket(Speed.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                    Speed.mc.player.rotationPitchHead = -90.0f;
                }
            }
        }
        if (this.mod.is("Grim")) {
            if (MoveUtils.isMoving() && Speed.mc.player.isOnGround()) {
                Speed.mc.player.jump();
            }
            float f3 = Speed.mc.player.isOnGround() ? 0.9f : (Speed.mc.timer.timerSpeed = Speed.mc.player.fallDistance >= 0.3f ? 2.0f : 0.8f);
        }
        if (this.mod.is("Legit")) {
            Speed.mc.timer.timerSpeed = 1.0f;
            int n14 = 5;
            n9 = 1;
            n8 = 4;
            n7 = 5;
            n6 = 3;
            Speed.mc.player.getMotion().y -= 0.00348;
            Speed.mc.player.jumpMovementFactor = 0.026f;
            Speed.mc.gameSettings.keyBindJump.setPressed(Speed.mc.gameSettings.keyBindJump.isKeyDown());
            if (MoveUtils.isMoving() && Speed.mc.player.isOnGround()) {
                Speed.mc.gameSettings.keyBindJump.setPressed(true);
                Speed.mc.timer.timerSpeed = 1.35f;
                Speed.mc.player.jump();
            }
        }
        if (this.mod.is("HolyWorld")) {
            Speed.mc.gameSettings.keyBindJump.setPressed(Speed.mc.gameSettings.keyBindJump.isKeyDown());
            if (MoveUtils.isMoving()) {
                if (Speed.mc.player.isOnGround()) {
                    Speed.mc.gameSettings.keyBindJump.setPressed(true);
                    Speed.mc.timer.timerSpeed = 1.0f;
                    Speed.mc.player.jump();
                }
                if (Speed.mc.player.getMotion().y > 0.003) {
                    int n15 = 5;
                    n9 = 5;
                    Speed.mc.player.getMotion().x *= 1.0015;
                    n8 = 4;
                    n7 = 3;
                    n6 = 1;
                    n5 = 3;
                    Speed.mc.player.getMotion().z *= 1.0015;
                    Speed.mc.timer.timerSpeed = 1.06f;
                }
            }
        }
        if (this.mod.is("FunSkyHVH") && MoveUtils.isMoving()) {
            this.currentStack = Speed.mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (this.currentStack.getItem() == Items.ELYTRA) {
                if (Speed.mc.player.isOnGround()) {
                    Speed.mc.player.jump();
                    Speed.mc.player.rotationPitchHead = -90.0f;
                } else if (ElytraItem.isUsable(this.currentStack) && !Speed.mc.player.isElytraFlying()) {
                    Speed.mc.player.startFallFlying();
                    int n16 = 2;
                    n9 = 3;
                    Speed.mc.player.connection.sendPacket(new CEntityActionPacket(Speed.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                    Speed.mc.player.rotationPitchHead = -90.0f;
                    if (Speed.mc.player.isElytraFlying()) {
                        Speed.mc.player.setSprinting(false);
                        Speed.mc.player.jump(0.0f, 1.2f);
                        f = Speed.mc.player.rotationYawHead;
                        float f4 = Speed.mc.player.rotationPitch;
                        d4 = 1.4;
                        d3 = -Math.sin((double)f / 180.0 * Math.PI) * Math.cos((double)f4 / 180.0 * Math.PI) * d4;
                        d2 = Math.cos((double)f / 180.0 * Math.PI) * Math.cos((double)f4 / 180.0 * Math.PI) * d4;
                        Speed.mc.player.setVelocity(d3, -0.65, d2);
                    }
                }
            }
        }
        if (this.mod.is("OnGround2") && MoveUtils.isMoving() && Speed.mc.player.isOnGround()) {
            f = Speed.mc.player.rotationYawHead;
            float f5 = Speed.mc.player.rotationPitch;
            d4 = 1.3;
            d3 = 15.0;
            d2 = d3 / 180.0 * 3.1415927410125732;
            d = (double)(-MathHelper.sin(f / 180.0f * (float)Math.PI)) * Math.cos(d2) * d4;
            double d7 = (double)MathHelper.cos(f / 180.0f * (float)Math.PI) * Math.cos(d2) * d4;
            Speed.mc.player.setVelocity(d, 0.0, d7);
        }
        if (this.mod.is("Old Sunrise")) {
            this.handleSunriseDamageMode();
        }
        if (this.mod.is("Matrix") && Speed.mc.player.isOnGround() && MoveUtils.isMoving()) {
            Speed.mc.gameSettings.keyBindJump.setPressed(false);
            this.applyMatrixSpeed();
        }
        if (this.mod.is("Simulations")) {
            this.handleRWMode();
        }
        if (this.mod.is("Elytra 3")) {
            this.elytranew();
        }
        if (this.mod.is("Old Intave")) {
            Speed.mc.gameSettings.keyBindJump.setPressed(true);
            if (MoveUtils.isMoving()) {
                if (Speed.mc.player.isOnGround()) {
                    Speed.mc.player.jump();
                    Speed.mc.timer.timerSpeed = 1.0f;
                }
                if (Speed.mc.player.getMotion().y > 0.003) {
                    int n17 = 2;
                    int n18 = 2;
                    int n19 = 2;
                    n7 = 1;
                    n6 = 1;
                    Speed.mc.player.getMotion().x *= 1.0015;
                    n5 = 1;
                    n4 = 1;
                    n3 = 5;
                    n2 = 3;
                    n = 4;
                    Speed.mc.player.getMotion().z *= 1.0015;
                    Speed.mc.timer.timerSpeed = 1.06f;
                }
            }
        }
        if (this.mod.is("Grim Entity")) {
            Iterator<AbstractClientPlayerEntity> iterator2 = Speed.mc.world.getPlayers().iterator();
            while (true) {
                float f6;
                if (!iterator2.hasNext()) {
                    return;
                }
                PlayerEntity playerEntity = iterator2.next();
                if (Speed.mc.player == playerEntity) continue;
                float f7 = ((Float)this.distanceGrim.get()).floatValue();
                if (!(Speed.mc.player.getDistance(playerEntity) <= f7) || !Speed.mc.gameSettings.keyBindForward.isKeyDown() && !Speed.mc.gameSettings.keyBindRight.isKeyDown() && !Speed.mc.gameSettings.keyBindLeft.isKeyDown() && !Speed.mc.gameSettings.keyBindBack.isKeyDown()) continue;
                float f8 = f6 = ((Float)this.speedGrim.get()).floatValue();
                Vector3d vector3d = Speed.mc.player.getMotion();
                n4 = 2;
                n3 = 4;
                vector3d.x *= (double)f8;
                vector3d = Speed.mc.player.getMotion();
                n2 = 4;
                n = 5;
                vector3d.z *= (double)f8;
            }
        }
        if (this.mod.is("Low")) {
            Iterator<AbstractClientPlayerEntity> iterator3 = Speed.mc.world.getPlayers().iterator();
            while (true) {
                if (!iterator3.hasNext()) {
                    return;
                }
                PlayerEntity playerEntity = iterator3.next();
                if (Speed.mc.player == playerEntity || !(Speed.mc.player.getDistance(playerEntity) <= 0.89f) || !Speed.mc.gameSettings.keyBindForward.isKeyDown() && !Speed.mc.gameSettings.keyBindRight.isKeyDown() && !Speed.mc.gameSettings.keyBindLeft.isKeyDown() && !Speed.mc.gameSettings.keyBindBack.isKeyDown()) continue;
                float f9 = 1.3f;
                Vector3d vector3d = Speed.mc.player.getMotion();
                n6 = 3;
                n5 = 2;
                vector3d.x *= (double)f9;
                vector3d = Speed.mc.player.getMotion();
                n4 = 2;
                n3 = 3;
                vector3d.z *= (double)f9;
            }
        }
        if (this.mod.is("Grim Old")) {
            if (Speed.mc.player.movementInput.moveForward == 0.0f) {
                MovementInput movementInput = Speed.mc.player.movementInput;
                int n20 = 4;
                int n21 = 5;
                n7 = 1;
                n6 = 2;
                if (MovementInput.moveStrafe == 0.0f) {
                    return;
                }
            }
            double d8 = 0.0027;
            float f10 = Speed.mc.player.rotationYaw;
            if (Speed.mc.player.movementInput.moveForward < 0.0f) {
                f10 += 0.4f;
            }
            MovementInput movementInput = Speed.mc.player.movementInput;
            n6 = 4;
            n5 = 3;
            n4 = 4;
            if (MovementInput.moveStrafe > 0.0f) {
                n3 = 3;
                f10 -= 0.4f * (Speed.mc.player.movementInput.moveForward < 0.0f ? -0.5f : (Speed.mc.player.movementInput.moveForward > 0.0f ? 0.5f : 1.0f));
            }
            MovementInput movementInput2 = Speed.mc.player.movementInput;
            n2 = 3;
            n = 3;
            int n22 = 5;
            if (MovementInput.moveStrafe < 0.0f) {
                f10 += 0.4f * (Speed.mc.player.movementInput.moveForward < 0.0f ? -0.5f : (Speed.mc.player.movementInput.moveForward > 0.0f ? 0.5f : 1.0f));
            }
            int n23 = 5;
            int n24 = 4;
            boolean bl = true;
            Speed.mc.player.setMotion(Speed.mc.player.getMotion().x - Math.sin(Math.toRadians(f10)) * d8, Speed.mc.player.getMotion().y, Speed.mc.player.getMotion().z + Math.cos(Math.toRadians(f10)) * d8);
        }
        if (this.mod.is("Boost")) {
            ClientPlayerEntity clientPlayerEntity = Speed.mc.player;
            if (clientPlayerEntity.movementInput.moveForward > 0.0f && clientPlayerEntity.isOnGround()) {
                long l = System.currentTimeMillis();
                if (this.lastPacketTime == -1L || l - this.lastPacketTime > 500L) {
                    d5 = 2.0;
                    float f11 = clientPlayerEntity.getYaw(0.0f);
                    d2 = -Math.sin(Math.toRadians(f11)) * d5;
                    d = Math.cos(Math.toRadians(f11)) * d5;
                    clientPlayerEntity.setMotion(d2, clientPlayerEntity.getMotion().y, d);
                    this.lastPacketTime = l;
                }
            }
        }
        if (this.mod.is("Elytra Abuse")) {
            this.currentStack = Speed.mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (this.currentStack.getItem() == Items.ELYTRA) {
                if (Speed.mc.player.isOnGround()) {
                    Speed.mc.player.jump();
                }
                if (Speed.mc.player.fallDistance >= 0.11f) {
                    Speed.mc.gameSettings.keyBindJump.setPressed(false);
                } else {
                    Speed.mc.gameSettings.keyBindJump.setPressed(true);
                }
                if (Speed.mc.player.isInWater()) {
                    Speed.mc.player.stopFallFlying();
                }
            }
        }
        if (this.mod.is("FunTime")) {
            this.ft();
        }
        if (this.mod.is("New")) {
            // empty if block
        }
        if (this.mod.is("IceSpoof")) {
            this.isespoof();
        }
    }

    private void ft() {
        boolean bl;
        AxisAlignedBB axisAlignedBB = TargetStrafe.mc.player.getBoundingBox().grow(0.1);
        int n = TargetStrafe.mc.world.getEntitiesWithinAABB(ArmorStandEntity.class, axisAlignedBB).size();
        boolean bl2 = bl = n > 1 || TargetStrafe.mc.world.getEntitiesWithinAABB(LivingEntity.class, axisAlignedBB).size() > 1;
        if (bl && !TargetStrafe.mc.player.isOnGround()) {
            TargetStrafe.mc.player.jumpMovementFactor = n > 1 ? 1.0f / (float)n : 0.16f;
            float f = TargetStrafe.mc.player.jumpMovementFactor;
        }
    }

    private void elytranew() {
        this.currentStack = Speed.mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
        if (this.currentStack.getItem() == Items.ELYTRA) {
            if (Speed.mc.player.isOnGround()) {
                Speed.mc.player.jump();
            }
            float f = 1.0f;
            if (Speed.mc.player.fallDistance <= 0.1f) {
                f = 1.0f;
            }
            if (Speed.mc.player.fallDistance > 0.6f) {
                Speed.mc.player.startFallFlying();
                int n = 2;
                boolean bl = true;
                boolean bl2 = true;
                Speed.mc.player.connection.sendPacket(new CEntityActionPacket(Speed.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                Speed.mc.player.stopFallFlying();
                float f2 = 10.0f;
                double d = Speed.mc.player.getForward().x * (double)(f2 /= 100.0f);
                double d2 = Speed.mc.player.getForward().z * (double)f2;
                if (MoveUtils.isMoving() && MoveUtils.getMotion() < (double)0.9f) {
                    int n2 = 2;
                    int n3 = 5;
                    int n4 = 3;
                    Speed.mc.player.motion.x *= 2.25;
                    int n5 = 5;
                    boolean bl3 = true;
                    Speed.mc.player.motion.z *= 2.25;
                    if (MoveUtils.isMoving()) {
                        Speed.mc.timer.timerSpeed = 1.0f;
                        if (Speed.mc.player.isOnGround()) {
                            if (!Speed.mc.gameSettings.keyBindJump.isKeyDown()) {
                                Speed.mc.player.jump();
                            }
                        } else {
                            Speed.mc.timer.timerSpeed = f;
                        }
                    } else {
                        Speed.mc.timer.timerSpeed = 1.0f;
                    }
                }
            }
        }
    }

    private void isespoof() {
        int n = 4;
        int n2 = 5;
        boolean bl = true;
        int n3 = 3;
        int n4 = 5;
        BlockPos blockPos = new BlockPos(Speed.mc.player.getPosX(), Speed.mc.player.getPosY() - 1.0, Speed.mc.player.getPosZ());
        if (Speed.mc.world.getBlockState(blockPos).isAir() || !Speed.mc.gameSettings.keyBindJump.isPressed()) {
            return;
        }
        int n5 = 5;
        int n6 = 4;
        Speed.mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
        Speed.mc.world.setBlockState(blockPos, Blocks.ICE.getDefaultState(), 0);
        boolean bl2 = true;
    }

    private void handleRWMode() {
        if (this.timerUtil.hasTimeElapsed(1150L)) {
            this.boosting = true;
        }
        if (this.timerUtil.hasTimeElapsed(7000L)) {
            this.boosting = false;
            this.timerUtil.reset();
        }
        if (this.boosting) {
            if (Speed.mc.player.isOnGround() && !Speed.mc.gameSettings.keyBindJump.pressed) {
                Speed.mc.player.jump();
            }
            Speed.mc.timer.timerSpeed = Speed.mc.player.ticksExisted % 2 == 0 ? 1.5f : 1.2f;
        } else {
            Speed.mc.timer.timerSpeed = 0.05f;
        }
    }

    private void spawnFakePlayer() {
        int n = 5;
        int n2 = 2;
        this.fakePlayer = new RemoteClientPlayerEntity(Speed.mc.world, Speed.mc.player.getGameProfile());
        this.fakePlayer.copyLocationAndAnglesFrom(Speed.mc.player);
        this.fakePlayer.rotationYawHead = Speed.mc.player.rotationYawHead;
        this.fakePlayer.renderYawOffset = Speed.mc.player.renderYawOffset;
        this.fakePlayer.rotationPitchHead = Speed.mc.player.rotationPitchHead;
        this.fakePlayer.container = Speed.mc.player.container;
        this.fakePlayer.inventory = Speed.mc.player.inventory;
        Speed.mc.world.addEntity(1337, this.fakePlayer);
        boolean bl = true;
        this.fakePlayer.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
        boolean bl2 = true;
        int n3 = 4;
        this.fakePlayer.setInvisible(false);
    }

    private void handleSunriseDamageMode() {
        double d = MoveUtils.getDirection(true);
        if (MoveUtils.isMoving()) {
            if (Speed.mc.player.isOnGround()) {
                this.applySunriseGroundMotion(d);
            } else if (Speed.mc.player.isInWater()) {
                this.applySunriseWaterMotion(d);
            } else if (!Speed.mc.player.isOnGround()) {
                this.applySunriseAirMotion(d);
            } else {
                this.applySunriseDefaultMotion(d);
            }
        }
    }

    private void applySunriseGroundMotion(double d) {
        Speed.mc.player.addVelocity((double)(-MathHelper.sin((float)d)) * 9.5 / 24.5, 0.0, (double)MathHelper.cos((float)d) * 9.5 / 24.5);
        MoveUtils.setMotion(MoveUtils.getMotion());
    }

    private void applySunriseWaterMotion(double d) {
        Speed.mc.player.addVelocity((double)(-MathHelper.sin((float)d)) * 9.5 / 24.5, 0.0, (double)MathHelper.cos((float)d) * 9.5 / 24.5);
        MoveUtils.setMotion(MoveUtils.getMotion());
    }

    private void applySunriseAirMotion(double d) {
        Speed.mc.player.addVelocity((double)(-MathHelper.sin((float)d)) * 0.11 / 24.5, 0.0, (double)MathHelper.cos((float)d) * 0.11 / 24.5);
        MoveUtils.setMotion(MoveUtils.getMotion());
    }

    private void applySunriseDefaultMotion(double d) {
        Speed.mc.player.addVelocity((double)(-MathHelper.sin((float)d)) * 0.005 * MoveUtils.getMotion(), 0.0, (double)MathHelper.cos((float)d) * 0.005 * MoveUtils.getMotion());
        MoveUtils.setMotion(MoveUtils.getMotion());
    }

    private void applyMatrixSpeed() {
        double d = 2.0;
        boolean bl = true;
        int n = 4;
        Speed.mc.player.motion.x *= d;
        int n2 = 4;
        int n3 = 5;
        Speed.mc.player.motion.z *= d;
        StrafeMovement.oldSpeed *= d;
    }

    private void applyFunTimeSpeed() {
        double d = 2.2;
        int n = 2;
        Speed.mc.player.motion.x *= d;
        int n2 = 4;
        Speed.mc.player.motion.z *= d;
        StrafeMovement.oldSpeed *= d;
    }

    private void applyNewSpeed() {
        if (Speed.mc.player.isOnGround()) {
            double d = 1.5;
            int n = 5;
            int n2 = 3;
            Speed.mc.player.motion.x *= d;
            boolean bl = true;
            int n3 = 2;
            int n4 = 4;
            int n5 = 3;
            Speed.mc.player.motion.z *= d;
            StrafeMovement.oldSpeed *= d;
        }
    }

    private void removeFakePlayer() {
        Speed.mc.world.removeEntityFromWorld(1337);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.removeFakePlayer();
        Speed.mc.timer.timerSpeed = 1.0f;
    }

    private Boolean lambda$new$1() {
        return !this.mod.is("Timer 1") && !this.mod.is("IceSpoof") && !this.mod.is("Grim") && !this.mod.is("Legit") && !this.mod.is("Legit 2") && !this.mod.is("Timer 2") && !this.mod.is("Vulcan") && !this.mod.is("LongHop") && !this.mod.is("Elytra") && !this.mod.is("Elytra 2") && !this.mod.is("Elytra 3") && !this.mod.is("FunSkyHVH") && !this.mod.is("Old Sunrise") && !this.mod.is("Matrix") && !this.mod.is("Simulations") && !this.mod.is("Old Intave") && !this.mod.is("Elytra Abuse") && !this.mod.is("Low") && !this.mod.is("Grim Old") && !this.mod.is("Boost") && !this.mod.is("FunTime") && !this.mod.is("New");
    }

    private Boolean lambda$new$0() {
        return !this.mod.is("Timer 1") && !this.mod.is("IceSpoof") && !this.mod.is("Grim") && !this.mod.is("Legit") && !this.mod.is("Legit 2") && !this.mod.is("Timer 2") && !this.mod.is("Vulcan") && !this.mod.is("LongHop") && !this.mod.is("Elytra") && !this.mod.is("Elytra 2") && !this.mod.is("Elytra 3") && !this.mod.is("FunSkyHVH") && !this.mod.is("Old Sunrise") && !this.mod.is("Matrix") && !this.mod.is("Simulations") && !this.mod.is("Old Intave") && !this.mod.is("Elytra Abuse") && !this.mod.is("Low") && !this.mod.is("Grim Old") && !this.mod.is("Boost") && !this.mod.is("FunTime") && !this.mod.is("New");
    }

    public static class StrafeMovement {
        public static double oldSpeed;
        public static double contextFriction;
        public static boolean needSwap;
        public static boolean needSprintState;
        public static int counter;
        public static int noSlowTicks;

        public static void postMove(double d) {
            oldSpeed = d * contextFriction;
        }

        public static float getAIMoveSpeed(ClientPlayerEntity clientPlayerEntity) {
            boolean bl = clientPlayerEntity.isSprinting();
            clientPlayerEntity.setSprinting(true);
            float f = clientPlayerEntity.getAIMoveSpeed() * 1.3f;
            clientPlayerEntity.setSprinting(bl);
            return f;
        }
    }
}

