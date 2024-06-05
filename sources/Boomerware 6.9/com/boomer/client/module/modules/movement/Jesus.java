package com.boomer.client.module.modules.movement;

import java.awt.Color;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.Client;
import com.boomer.client.event.events.player.BoundingBoxEvent;
import com.boomer.client.event.events.player.MotionEvent;
import com.boomer.client.event.events.player.UpdateEvent;
import com.boomer.client.event.events.world.PacketEvent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.TimerUtil;

import com.boomer.client.utils.value.impl.EnumValue;
import com.sun.xml.internal.ws.util.StringUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Jesus extends Module {
    private boolean wasWater;
    private TimerUtil timer = new TimerUtil();

    private EnumValue<Mode> mode = new EnumValue<Mode>("Mode",Mode.SOLID);

    public Jesus() {
        super("Jesus", Category.MOVEMENT, new Color(142, 248, 255).getRGB());
        setDescription("Walk on water.");
        addValues(mode);
    }

    private enum Mode {
        SOLID,BOB,BOUNCE
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer != null) {
            setSuffix(StringUtils.capitalize(mode.getValue().name().toLowerCase()));

            if (event.isPre() && !Client.INSTANCE.getModuleManager().getModuleClass(PepperSpray.class).isEnabled()) {
                if (isInLiquid() && !mc.thePlayer.isSneaking()) {
                    mc.thePlayer.motionY = 0.1;
                }
                if (mode.getValue().equals(Mode.BOUNCE)) {
                    if (mc.thePlayer.isSneaking() || (mc.thePlayer.isBurning() && isOnWater())) {
                        return;
                    }
                    if (mc.thePlayer.onGround || mc.thePlayer.isOnLadder() || isInLiquid()) {
                        wasWater = false;
                    }
                    if (wasWater && mc.thePlayer.motionY > 0.0) {
                        if (mc.thePlayer.motionY < 0.03) {
                            mc.thePlayer.motionY *= 1.2;
                            mc.thePlayer.motionY += 0.067;
                        } else if (mc.thePlayer.motionY < 0.05) {
                            mc.thePlayer.motionY *= 1.2;
                            mc.thePlayer.motionY += 0.06;
                        } else if (mc.thePlayer.motionY < 0.07) {
                            mc.thePlayer.motionY *= 1.2;
                            mc.thePlayer.motionY += 0.057;
                        } else if (mc.thePlayer.motionY < 0.11) {
                            mc.thePlayer.motionY *= 1.2;
                            mc.thePlayer.motionY += 0.0534;
                        } else {
                            mc.thePlayer.motionY += 0.0517;
                        }
                    }
                    if (wasWater && mc.thePlayer.motionY < 0.0 && mc.thePlayer.motionY > -0.3) {
                        mc.thePlayer.motionY += 0.04;
                    }
                    mc.thePlayer.fallDistance = 0.0f;
                    if (!isOnLiquid()) {
                        return;
                    }
                    if (MathHelper.ceiling_double_int(mc.thePlayer.posY) != mc.thePlayer.posY + 1.0E-6) {
                        return;
                    }
                    mc.thePlayer.motionY = 0.5;
                    setSpeed(getSpeed() * 1.35);
                    event.setOnGround(true);
                    wasWater = true;
                }
            }
        }
    }

    @Handler
    public void onBoundingBox(BoundingBoxEvent event) {
        if (mc.thePlayer != null) {
            switch (mode.getValue()) {
                case SOLID:
                    if (mc.theWorld == null || mc.thePlayer.fallDistance > 3 || (mc.thePlayer.isBurning() && isOnWater()))
                        return;
                    Block block = mc.theWorld.getBlockState(event.getBlockPos()).getBlock();
                    if (!(block instanceof BlockLiquid) || isInLiquid() || mc.thePlayer.isSneaking())
                        return;
                    event.setBoundingBox(new AxisAlignedBB(0, 0, 0, 1, 1, 1).contract(0, 0.000000000002000111, 0).offset(event.getBlockPos().getX(), event.getBlockPos().getY(), event.getBlockPos().getZ()));
                    break;
                case BOUNCE:
                    if (!(event.getBlock() instanceof BlockLiquid) || isInLiquid() || mc.thePlayer.isSneaking()|| (mc.thePlayer.isBurning() && isOnWater())) {
                        return;
                    }
                    event.setBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).contract(0.0, 1.0E-6, 0.0).offset(event.getBlockPos().getX(), event.getBlockPos().getY(), event.getBlockPos().getZ()));
                    break;
            }
        }
    }

    @Handler
    public void onPacketSent(PacketEvent event) {
        if (mc.thePlayer != null) {
            switch (mode.getValue()) {
                case SOLID:
                    if (!(event.getPacket() instanceof C03PacketPlayer) || !event.isSending() || isInLiquid() || !isOnLiquid() || mc.thePlayer.isSneaking() || mc.thePlayer.fallDistance > 3 || (mc.thePlayer.isBurning() && isOnWater()))
                        return;
                    C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
                    if (event.getPacket() instanceof C03PacketPlayer && !mc.thePlayer.isMoving()) event.setCanceled(true);
                    if (mc.thePlayer.isSprinting() && mc.thePlayer.isInLava() && isOnLiquid())
                        mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                    mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    packet.setY(packet.getPositionY() + (mc.thePlayer.ticksExisted % 2 == 0 ? 0.000000000002000111 : 0));
                    packet.setOnGround(mc.thePlayer.ticksExisted % 2 != 0);
                    break;
                case BOB:
                    if (event.getPacket() instanceof C03PacketPlayer) {
                        C03PacketPlayer packetPlayer = (C03PacketPlayer) event.getPacket();

                        if (isOnLiquid() && !isInLiquid() && mc.thePlayer.fallDistance <= 3 && !mc.thePlayer.isSneaking()) {
                            packetPlayer.setY(mc.thePlayer.posY + (mc.thePlayer.ticksExisted % 2 == 0 ? 0.01 : -0.01));
                        }
                    }
                    break;
            }
        }
    }


    private boolean isOnLiquid() {
        final double y = mc.thePlayer.posY - 0.03;
        for (int x = MathHelper.floor_double(mc.thePlayer.posX); x < MathHelper.ceiling_double_int(mc.thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(mc.thePlayer.posZ); z < MathHelper.ceiling_double_int(mc.thePlayer.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor_double(y), z);
                if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOnWater() {
        final double y = mc.thePlayer.posY - 0.03;
        for (int x = MathHelper.floor_double(mc.thePlayer.posX); x < MathHelper.ceiling_double_int(mc.thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(mc.thePlayer.posZ); z < MathHelper.ceiling_double_int(mc.thePlayer.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor_double(y), z);
                if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid && mc.theWorld.getBlockState(pos).getBlock().getMaterial() == Material.water) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInLiquid() {
        final double y = mc.thePlayer.posY + 0.01;
        for (int x = MathHelper.floor_double(mc.thePlayer.posX); x < MathHelper.ceiling_double_int(mc.thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(mc.thePlayer.posZ); z < MathHelper.ceiling_double_int(mc.thePlayer.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, (int) y, z);
                if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }

    private double getSpeed() {
        return Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    }

    private void setSpeed(final double speed) {
        mc.thePlayer.motionX = -(Math.sin(getDirection()) * speed);
        mc.thePlayer.motionZ = Math.cos(getDirection()) * speed;
    }

    private float getDirection() {
        float direction = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0f) {
            direction += 180.0f;
        }
        float forward = 1.0f;
        if (mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        } else {
            forward = 1.0f;
        }
        if (mc.thePlayer.moveStrafing > 0.0f) {
            direction -= 90.0f * forward;
        } else if (mc.thePlayer.moveStrafing < 0.0f) {
            direction += 90.0f * forward;
        }
        direction *= 0.017453292f;
        return direction;
    }

    private void setMoveSpeed(MotionEvent EventMove, double speed) {
        double forward = mc.thePlayer.movementInput.moveForward, strafe = mc.thePlayer.movementInput.moveStrafe, yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            EventMove.setX(0.0);
            EventMove.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -40 : 40);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 40 : -40);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            EventMove.setX(forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw)));
            EventMove.setZ(forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw)));
        }
    }

    private Block getBlockRelativeToEntity(Entity en, double d) {
        return getBlock(new BlockPos(en.posX, en.posY + d, en.posZ));
    }

    private Block getBlock(BlockPos pos) {
        return mc.theWorld.getBlockState(pos).getBlock();
    }

    @Override
    public void onEnable() {
        timer.reset();
    }

    @Override
    public void onDisable() {
        wasWater = false;
    }
}
