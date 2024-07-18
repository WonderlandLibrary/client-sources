package com.alan.clients.module.impl.player.nofall;

import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.module.impl.player.NoFall;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.tuples.Triple;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.value.Mode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClutchNoFall extends Mode<NoFall> {
    private Vec3 position;

    public ClutchNoFall(String name, NoFall parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (mc.thePlayer.fallDistance > 2 && PlayerUtil.isBlockUnder(15) && position == null) {
            final int slot = SlotUtil.findItem(Items.water_bucket);

            if (slot == -1) {
                return;
            }

            getComponent(Slot.class).setSlot(slot);
            List<Triple<Block, Double, Vec3>> blocks = new ArrayList<>();

            for (int x = -1; x <= 1; x++) {
                for (int y = -10; y <= 0; y++) {
                    for (int z = -1; z <= 1; z++) {
                        final Block block = PlayerUtil.blockRelativeToPlayer(x, y, z);

                        if (block instanceof BlockAir) continue;

                        Vec3 position = new Vec3(x + Math.floor(mc.thePlayer.posX) + 0.5,
                                y + Math.floor(mc.thePlayer.posY) + 1, z + Math.floor(mc.thePlayer.posZ) + 0.5);

                        blocks.add(new Triple<>(block, position.distanceTo(
                                new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + MoveUtil.predictedMotion(mc.thePlayer.motionY),
                                        mc.thePlayer.posZ)), position));
                    }
                }
            }

            if (blocks.size() == 0) return;

            blocks.sort(Comparator.comparingDouble(Triple::getSecond));

            Vector2f rotations = RotationUtil.calculate(blocks.get(0).getThird());

            RotationComponent.setRotations(rotations, 10, MovementFix.NORMAL);

            if (mc.objectMouseOver.getBlockPos().equals(new BlockPos(blocks.get(0).getThird()))) {
                mc.rightClickMouse();
                position = blocks.get(0).getThird();
                ChatUtil.display("Right Clicked");
            }

//            mc.rightClickMouse();
//            if (mc.objectMouseOver.getBlockPos().equals(new BlockPos(position))/* && getComponent(SlotComponent.class).getItemStack().getItem() == Items.water_bucket*/) {
//                MovingObjectPosition movingObjectPosition = RayCastUtil.rayCast(rotations, 4.5);
//
//                Vec3 hitVec = movingObjectPosition.hitVec;
//                BlockPos hitPos = movingObjectPosition.getBlockPos();
//
//                final float f = (float) (hitVec.xCoord - (double) hitPos.getX());
//                final float f1 = (float) (hitVec.yCoord - (double) hitPos.getY());
//                final float f2 = (float) (hitVec.zCoord - (double) hitPos.getZ());
//
//                PacketUtil.send(new C08PacketPlayerBlockPlacement(hitPos, EnumFacing.UP.getIndex(), getComponent(SlotComponent.class).getItemStack(), f, f1, f2));
//                PacketUtil.send(new C08PacketPlayerBlockPlacement(getComponent(SlotComponent.class).getItemStack()));
//                ChatUtil.display("Right Clicked");
//            } else {
//                position = null;
//            }
        } else if (mc.thePlayer.onGround) {
            position = null;
        }

//        if (mc.thePlayer.fallDistance > 3 && this.placePacket == null && PlayerUtil.isBlockUnder(15)) {
//            final int slot = SlotUtil.findItem(Items.water_bucket);
//
//            if (slot == -1) {
//                return;
//            }
//
//            getComponent(SlotComponent.class).setSlot(slot);
//
//            final double minRotationSpeed = 8;
//            final double maxRotationSpeed = 10;
//            final float rotationSpeed = (float) MathUtil.getRandom(minRotationSpeed, maxRotationSpeed);
//            RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw, 90), rotationSpeed, MovementFix.NORMAL);
//
//            if (RotationComponent.rotations.y > 85 && !BadPacketsComponent.bad()) {
//                for (int i = 0; i < 3; i++) {
//                    final Block block = PlayerUtil.blockRelativeToPlayer(0, -i, 0);
//
//                    if (block.getMaterial() == Material.water) {
//                        break;
//                    }
//
//                    if (block.isFullBlock()) {
//                        final BlockPos position = new BlockPos(mc.thePlayer).down(i);
//
//                        Vec3 hitVec = new Vec3(position.getX() + Math.random(), position.getY() + Math.random(), position.getZ() + Math.random());
//                        final MovingObjectPosition movingObjectPosition = RayCastUtil.rayCast(RotationComponent.rotations, mc.playerController.getBlockReachDistance());
//                        if (movingObjectPosition != null && movingObjectPosition.getBlockPos().equals(position)) {
//                            hitVec = movingObjectPosition.hitVec;
//                        }
//
//                        final float f = (float) (hitVec.xCoord - (double) position.getX());
//                        final float f1 = 1.0F;
//                        final float f2 = (float) (hitVec.zCoord - (double) position.getZ());
//
//                        PacketUtil.send(this.placePacket = new C08PacketPlayerBlockPlacement(position, EnumFacing.UP.getIndex(), getComponent(SlotComponent.class).getItemStack(), f, f1, f2));
//                        PacketUtil.send(new C08PacketPlayerBlockPlacement(getComponent(SlotComponent.class).getItemStack()));
//                        break;
//                    }
//                }
//            }
//        } else if (this.placePacket != null && mc.thePlayer.onGroundTicks > 1) {
//            int slot = SlotUtil.findItem(Items.bucket);
//
//            if (slot == -1) {
//                slot = SlotUtil.findItem(Items.water_bucket);
//            }
//
//            if (slot == -1) {
//                this.placePacket = null;
//                return;
//            }
//
//            getComponent(SlotComponent.class).setSlot(slot);
//
//            final double minRotationSpeed = 8;
//            final double maxRotationSpeed = 10;
//            final float rotationSpeed = (float) MathUtil.getRandom(minRotationSpeed, maxRotationSpeed);
//            RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw, 90), rotationSpeed, MovementFix.NORMAL);
//
//            if (RotationComponent.rotations.y > 85 && !BadPacketsComponent.bad()) {
//                PacketUtil.send(this.placePacket);
//                PacketUtil.send(new C08PacketPlayerBlockPlacement(getComponent(SlotComponent.class).getItemStack()));
//            }
//
//            this.placePacket = null;
//        }
    };
}