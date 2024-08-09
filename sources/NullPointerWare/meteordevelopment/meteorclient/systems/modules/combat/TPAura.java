/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.entity.player.HandSwingEvent;
import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.function.Predicate;

public final class TPAura extends Module {
     private final SettingGroup sgGeneral = settings.getDefaultGroup();

  public final Setting<Integer> range = sgGeneral.add(new IntSetting.Builder()
        .name("max-range")
        .description("The maximum range you can TP!")
        .defaultValue(100)
        .range(0, 200)
        .sliderMax(200)
        .build()
    );;

    private int ticks;
    public TPAura() {
        super(Categories.Combat, "TPAura", "Perdorms more KB on hit! doesn't bipass Grim!");
    }

    @EventHandler
    private void onUpdate(final SendMovementPacketsEvent e) {
        assert mc.player != null;
        if (!(mc.player.getAttackCooldownProgress(0.5f) > 0.9))
            return;

        if (mc.options.useKey.wasPressed()) {

            BlockHitResult hit = getTargetBlock(range.get());
            assert hit != null;
            BlockPos block = hit.getBlockPos();

            assert mc.world != null;
            Block target = mc.world.getBlockState(block).getBlock();

            if (!(target instanceof ChestBlock || target instanceof EnderChestBlock || target instanceof BarrelBlock || target instanceof ShulkerBoxBlock)) {
                return;
            }

            // setDisplayName(target.getName().getString());

            Vec3d block_pos = new Vec3d(block.getX(), block.getY(), block.getZ());
            Vec3d playerPos = mc.player.getPos();
            double targetDist = targetDistance(playerPos, block_pos);

            if (targetDist > 5) {
                tpTo(playerPos, block_pos);
                PlayerInteractBlockC2SPacket packet = new PlayerInteractBlockC2SPacket(mc.player.preferredHand, hit, 1);
                sendNoEvent(packet);

                mc.player.setPosition(playerPos);
                mc.player.swingHand(mc.player.preferredHand);
            }
        }
    }

    @EventHandler
    public void onSwing(final HandSwingEvent e) {
        if (mc.options.attackKey.isPressed()) {
            Entity target = getTarget((int) range.get());

            if (target == null || target.getType().equals(EntityType.ITEM) || target.getType().equals(EntityType.EXPERIENCE_ORB)) {
                return;
            }

            Vec3d playerPos = mc.player.getPos();
            Vec3d entityPos = target.getPos();

            tpTo(playerPos, entityPos);

            PlayerInteractEntityC2SPacket attackPacket = PlayerInteractEntityC2SPacket.attack(target, false);
            sendNoEvent(attackPacket);

            tpTo(entityPos, playerPos);

            mc.player.setPosition(playerPos);
        }
    }

    private void tpTo(Vec3d from, Vec3d to) {
        double distancePerBlink = 8.0;
        double targetDistance = Math.ceil(from.distanceTo(to) / distancePerBlink);
        for (int i = 1; i <= targetDistance; i++) {
            Vec3d tempPos = from.lerp(to, i / targetDistance);
            assert mc.player != null;
            sendNoEvent(new PlayerMoveC2SPacket.PositionAndOnGround(tempPos.getX(), tempPos.getY(), tempPos.getZ(), mc.player.isOnGround()));
        }
    }

    private double targetDistance(Vec3d from, Vec3d to) {
        return Math.ceil(from.distanceTo(to));
    }

    public Entity getTarget(int max) {
        Entity entity2 = mc.getCameraEntity();
        Predicate<Entity> predicate = entity -> true; // do entity checking here!!!
        Vec3d eyePos = entity2.getEyePos();
        Vec3d vec3d2 = entity2.getRotationVec(mc.getTickDelta()).multiply(max);
        Vec3d vec3d3 = eyePos.add(vec3d2);
        Box box = entity2.getBoundingBox().stretch(vec3d2).expand(1.0);
        EntityHitResult entityHitResult = ProjectileUtil.raycast(entity2, eyePos, vec3d3, box, predicate, max * max);

        if (entityHitResult == null) return null;

        Entity res = entityHitResult.getEntity();

        return entityHitResult.getEntity();
    }

    public BlockHitResult getTargetBlock(int max) {
        HitResult hitResult = mc.cameraEntity.raycast(max, mc.getTickDelta(), false);
        if (hitResult instanceof BlockHitResult hit) {
            return hit;
        }

        return null;
    }
}
