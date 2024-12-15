package com.alan.clients.module.impl.player;

import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.RayCastUtil;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.EnumFacingOffset;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.util.vector.Vector3d;
import com.alan.clients.value.impl.BoundsNumberValue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

@ModuleInfo(aliases = {"module.player.clutch.name"}, description = "module.player.clutch.description", category = Category.PLAYER)
public class Clutch extends Module {
    private final BoundsNumberValue rotationSpeed = new BoundsNumberValue("Rotation Speed", this, 5, 10, 0, 10, 1);
    private final BoundsNumberValue placeDelay = new BoundsNumberValue("Place Delay", this, 0, 0, 0, 1, 1);

    private Vec3 targetBlock;
    private EnumFacingOffset enumFacing;
    private BlockPos blockFace;
    private float targetYaw, targetPitch;
    private int ticksOnAir;
    private int toggle;

    @Override
    public void onEnable() {
        targetYaw = mc.thePlayer.rotationYaw - 180;
        targetPitch = 90;

        targetBlock = null;
    }

    public void calculateRotations() {
        if (ticksOnAir > 0 && !RayCastUtil.overBlock(RotationComponent.rotations, enumFacing.getEnumFacing(), blockFace, true)) {
            getRotations(0);
        }

        /* Smoothing rotations */
        final double minRotationSpeed = this.rotationSpeed.getValue().doubleValue();
        final double maxRotationSpeed = this.rotationSpeed.getSecondValue().doubleValue();
        float rotationSpeed = (float) MathUtil.getRandom(minRotationSpeed, maxRotationSpeed);

        if (rotationSpeed != 0) {
            RotationComponent.setRotations(new Vector2f(targetYaw, targetPitch), rotationSpeed, MovementFix.NORMAL);
        }
    }

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (mc.thePlayer.ticksSinceTeleport <= 15 || mc.thePlayer.ticksExisted <= 50 ||
                BadPacketsComponent.bad() || getModule(Scaffold.class).isEnabled() ||
                (!mc.gameSettings.keyBindSneak.isKeyDown())) return;

        if (mc.thePlayer.offGroundTicks > 3 && !PlayerUtil.isBlockUnder(10)) {
            toggle = 10;
        }

        if (toggle-- < 0) return;

        // Getting ItemSlot
        getComponent(Slot.class).setSlot(SlotUtil.findBlock());

        final Vec3i offset = new Vec3i(0, 0, 0);

        //Used to detect when to place a block, if over air, allow placement of blocks
        if (PlayerUtil.blockRelativeToPlayer(offset.getX(), -1 + offset.getY(), offset.getZ()).isReplaceable(mc.theWorld, new BlockPos(mc.thePlayer).down())) {
            ticksOnAir++;
        } else {
            ticksOnAir = 0;
        }

        // Gets block to place
        targetBlock = PlayerUtil.getPlacePossibility(offset.getX(), offset.getY(), offset.getZ());

        if (targetBlock == null) {
            return;
        }

        //Gets EnumFacing
        enumFacing = PlayerUtil.getEnumFacing(targetBlock);

        if (enumFacing == null) {
            return;
        }

        final BlockPos position = new BlockPos(targetBlock.xCoord, targetBlock.yCoord, targetBlock.zCoord);

        blockFace = position.add(enumFacing.getOffset().xCoord, enumFacing.getOffset().yCoord, enumFacing.getOffset().zCoord);

        if (blockFace == null || enumFacing == null) {
            return;
        }

        this.calculateRotations();

        if (targetBlock == null || enumFacing == null || blockFace == null) {
            return;
        }

        if (mc.thePlayer.inventory.alternativeCurrentItem == getComponent(Slot.class).getItemIndex()) {
            if (!BadPacketsComponent.bad(false, true, false, false, true) &&
                    ticksOnAir > MathUtil.getRandom(placeDelay.getValue().intValue(), placeDelay.getSecondValue().intValue()) &&
                    (RayCastUtil.overBlock(enumFacing.getEnumFacing(), blockFace, true))) {

                Vec3 hitVec = RayCastUtil.rayCast(RotationComponent.rotations, mc.playerController.getBlockReachDistance()).hitVec;

                if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, getComponent(Slot.class).getItemStack(), blockFace, enumFacing.getEnumFacing(), hitVec)) {
                    PacketUtil.send(new C0APacketAnimation());
                }

                mc.rightClickDelayTimer = 0;
                ticksOnAir = 0;

                assert getComponent(Slot.class).getItemStack() != null;
                if (getComponent(Slot.class).getItemStack() != null && getComponent(Slot.class).getItemStack().stackSize == 0) {
                    mc.thePlayer.inventory.mainInventory[getComponent(Slot.class).getItemIndex()] = null;
                }
            } else if (Math.random() > 0.92 && mc.rightClickDelayTimer <= 0) {
                PacketUtil.send(new C08PacketPlayerBlockPlacement(getComponent(Slot.class).getItemStack()));
                mc.rightClickDelayTimer = 0;
            }
        }
    };

    public void getRotations(final int yawOffset) {

        EntityPlayer entityPlayer = mc.thePlayer;
        double difference = entityPlayer.posY + entityPlayer.getEyeHeight() - targetBlock.yCoord - 0.1 - Math.random() * 0.8;

        MovingObjectPosition movingObjectPosition;

        for (int offset = -180 + yawOffset; offset <= 180; offset += 45) {
            entityPlayer.setPosition(entityPlayer.posX, entityPlayer.posY - difference, entityPlayer.posZ);
            movingObjectPosition = RayCastUtil.rayCast(new Vector2f(entityPlayer.rotationYaw + offset, 0), 4.5);
            entityPlayer.setPosition(entityPlayer.posX, entityPlayer.posY + difference, entityPlayer.posZ);

            if (movingObjectPosition != null && new BlockPos(blockFace).equals(movingObjectPosition.getBlockPos()) &&
                    enumFacing.getEnumFacing() == movingObjectPosition.sideHit) {
                Vector2f rotations = RotationUtil.calculate(movingObjectPosition.hitVec);

                targetYaw = rotations.x;
                targetPitch = rotations.y;
                return;
            }
        }

        // Backup Rotations
        final Vector2f rotations = RotationUtil.calculate(
                new Vector3d(blockFace.getX(), blockFace.getY(), blockFace.getZ()), enumFacing.getEnumFacing());

        targetYaw = rotations.x;
        targetPitch = rotations.y;
    }
}