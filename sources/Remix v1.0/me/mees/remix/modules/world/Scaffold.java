package me.mees.remix.modules.world;

import net.minecraft.network.*;
import me.satisfactory.base.module.*;
import me.satisfactory.base.setting.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import pw.stamina.causam.scan.method.model.*;
import net.minecraft.potion.*;
import net.minecraft.block.*;
import me.satisfactory.base.utils.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import me.satisfactory.base.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;

public class Scaffold extends Module
{
    private boolean placing;
    private EnumFacing sideHit;
    private BlockPos targetBlockPos;
    private Vec3 hitVec;
    private MovingObjectPosition movingObjectPosition;
    private Vec3 vec1;
    private Vec3 vec2;
    private boolean shouldMotionDown;
    private boolean tower;
    private boolean down;
    private int towerStage;
    private BlockPos lastPlacedBlockPos;
    private ItemStack serverSideItemStack;
    private boolean isBlockBeingHeldServerSide;
    private boolean attackPacketSent;
    private ArrayList<Packet> packetQueue;
    private int lastInInventory;
    
    public Scaffold() {
        super("Scaffold", 0, Category.WORLD);
        this.towerStage = 0;
        this.packetQueue = new ArrayList<Packet>();
        this.addSetting(new Setting("AAC", this, true));
    }
    
    public float[] getFacePos(final Vec3 vec) {
        final double diffX = vec.xCoord + 0.5 - Scaffold.mc.thePlayer.posX;
        final double diffY = vec.yCoord - (Scaffold.mc.thePlayer.posY + Scaffold.mc.thePlayer.height);
        final double diffZ = vec.zCoord + 0.5 - Scaffold.mc.thePlayer.posZ;
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { Scaffold.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Scaffold.mc.thePlayer.rotationYaw), Scaffold.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Scaffold.mc.thePlayer.rotationPitch) };
    }
    
    public Block getBlock(final BlockPos pos) {
        return Scaffold.mc.theWorld.getBlockState(pos).getBlock();
    }
    
    public Block getBlockRelativeToEntity(final Entity en, final double d) {
        return this.getBlock(new BlockPos(en.posX, en.posY + d, en.posZ));
    }
    
    public static Vec3 getVec3(final BlockPos blockPos) {
        return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    
    @Override
    public void onDisable() {
        if (this.isBlockBeingHeldServerSide) {
            this.resetServerSideHotbarIndex();
        }
        super.onDisable();
    }
    
    @Override
    public void onToggle() {
        this.lastPlacedBlockPos = null;
        this.lastInInventory = -1;
        this.serverSideItemStack = null;
        this.attackPacketSent = false;
        this.placing = false;
        this.sideHit = null;
        this.targetBlockPos = null;
        this.hitVec = null;
        this.movingObjectPosition = null;
        this.vec1 = null;
        this.vec2 = null;
        this.shouldMotionDown = false;
        this.tower = false;
        this.down = false;
        this.towerStage = 0;
        final Timer timer = Scaffold.mc.timer;
        Timer.timerSpeed = 1.0f;
        super.onToggle();
    }
    
    private static float[] getAngles(final Entity e) {
        return new float[] { getYawChangeToEntity(e) + Minecraft.getMinecraft().thePlayer.rotationYaw, getPitchChangeToEntity(e) + Minecraft.getMinecraft().thePlayer.rotationPitch };
    }
    
    private static float getYawChangeToEntity(final Entity entity) {
        final double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double yawToEntity;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.wrapAngleTo180_float(-(Minecraft.getMinecraft().thePlayer.rotationYaw - (float)yawToEntity));
    }
    
    private static float getPitchChangeToEntity(final Entity entity) {
        final double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        final double deltaY = entity.posY - 1.6 + entity.getEyeHeight() - 0.4 - Minecraft.getMinecraft().thePlayer.posY;
        final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationPitch - (float)pitchToEntity);
    }
    
    @Subscriber
    public void onMove(final EventMove event) {
    }
    
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Scaffold.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Scaffold.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    @Subscriber
    public void onRender(final Event3DRender event) {
    }
    
    @Subscriber
    public void eventMotion(final EventMotion event) {
        if (this.findSettingByName("AAC").booleanValue()) {}
        final Vec3 under = new Vec3(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY - 1.0, Scaffold.mc.thePlayer.posZ);
        if (Scaffold.mc.gameSettings.keyBindJump.pressed && !Scaffold.mc.thePlayer.isMoving() && !(this.getBlock(new BlockPos(under)) instanceof BlockAir)) {
            Scaffold.mc.thePlayer.motionY = -0.3685;
            Scaffold.mc.thePlayer.motionY = 0.3685;
        }
        if (this.targetBlockPos != null) {
            if (!Scaffold.mc.gameSettings.keyBindJump.pressed) {
                this.targetBlockPos = new BlockPos(this.getBlockSide(this.targetBlockPos, this.sideHit));
            }
            final float[] rots = getBlockRotations(this.targetBlockPos.getX(), this.targetBlockPos.getY(), this.targetBlockPos.getZ(), EnumFacing.DOWN);
            event.yaw = rots[0];
            event.pitch = rots[1];
            MiscellaneousUtil.sendInfo("SEND 1");
        }
        for (final Packet packet : this.packetQueue) {
            Scaffold.mc.thePlayer.sendQueue.sendPacketNoEvent(packet);
        }
        this.packetQueue.clear();
        this.serverSideItemStack = null;
        this.placing = false;
        this.sideHit = null;
        this.targetBlockPos = null;
        this.hitVec = null;
        this.movingObjectPosition = null;
        this.tower = false;
        int blockInInventory = this.getBlockInHotbar();
        if (blockInInventory == -1) {
            if (this.isBlockBeingHeldServerSide) {
                this.resetServerSideHotbarIndex();
            }
            return;
        }
        if (Scaffold.mc.thePlayer.inventory.getStackInSlot(blockInInventory).stackSize <= 0) {
            blockInInventory = this.getBlockInHotbar(blockInInventory);
            if (blockInInventory == -1) {
                if (this.isBlockBeingHeldServerSide) {
                    this.resetServerSideHotbarIndex();
                }
                return;
            }
        }
        if (this.lastInInventory != blockInInventory) {
            this.isBlockBeingHeldServerSide = false;
        }
        this.lastInInventory = blockInInventory;
        this.serverSideItemStack = Scaffold.mc.thePlayer.inventory.getStackInSlot(blockInInventory);
        final MovementInput movementInput = Scaffold.mc.thePlayer.movementInput;
        final float forward = MovementInput.moveForward;
        final float strafe = MovementInput.moveStrafe;
        this.down = movementInput.sneak;
        if (this.getBlockRelativeToEntity(Scaffold.mc.thePlayer, -1.0).getMaterial() != Material.air) {
            return;
        }
        final double rayTraceRange = 4.2;
        final Vec3 rayTraceTargetRaw = new Vec3(Scaffold.mc.thePlayer.posX - Scaffold.mc.thePlayer.lastTickPosX, Scaffold.mc.thePlayer.posY - Scaffold.mc.thePlayer.lastTickPosY, Scaffold.mc.thePlayer.posZ - Scaffold.mc.thePlayer.lastTickPosZ).normalize();
        Vec3 rayTraceTarget = new Vec3(rayTraceTargetRaw.xCoord * -rayTraceRange, rayTraceTargetRaw.yCoord * -rayTraceRange, rayTraceTargetRaw.zCoord * -rayTraceRange);
        rayTraceTarget = rayTraceTarget.add(under);
        this.vec1 = under;
        this.vec2 = rayTraceTarget;
        MovingObjectPosition rayTrace = this.rayTrace(this.vec1, this.vec2);
        if (this.didRayTraceFindValidBlock(rayTrace)) {
            this.movingObjectPosition = rayTrace;
            this.hitVec = rayTrace.hitVec;
            this.sideHit = rayTrace.sideHit;
            this.targetBlockPos = rayTrace.getBlockPos();
            this.lastPlacedBlockPos = this.targetBlockPos;
            this.placing = true;
            final Vec3 oof = new Vec3(this.targetBlockPos);
            final float[] rots2 = getBlockRotations(oof.xCoord, oof.yCoord, oof.zCoord, EnumFacing.DOWN);
            event.yaw = rots2[0];
            event.pitch = rots2[1];
            MiscellaneousUtil.sendInfo("SEND 3");
            if (!this.isBlockBeingHeldServerSide) {
                Scaffold.mc.thePlayer.sendQueue.sendPacketNoEvent(new C09PacketHeldItemChange(blockInInventory));
                this.isBlockBeingHeldServerSide = true;
            }
        }
        else if (this.lastPlacedBlockPos != null && Scaffold.mc.thePlayer.getDistance(this.lastPlacedBlockPos.getX() + 0.5, this.lastPlacedBlockPos.getY() + 0.5, this.lastPlacedBlockPos.getZ() + 0.5) <= 4.2) {
            rayTrace = this.rayTrace(this.vec1, getVec3(this.lastPlacedBlockPos).addVector(0.5, 0.5, 0.5));
            if (this.didRayTraceFindValidBlock(rayTrace)) {
                this.movingObjectPosition = rayTrace;
                this.hitVec = rayTrace.hitVec;
                this.sideHit = rayTrace.sideHit;
                this.targetBlockPos = rayTrace.getBlockPos();
                this.placing = true;
                final Vec3 oof = new Vec3(this.targetBlockPos);
                final float[] rots2 = getBlockRotations(oof.xCoord, oof.yCoord, oof.zCoord, EnumFacing.DOWN);
                event.yaw = rots2[0];
                event.pitch = rots2[1];
                MiscellaneousUtil.sendInfo("SEND 1");
                if (!this.isBlockBeingHeldServerSide) {
                    Scaffold.mc.thePlayer.sendQueue.sendPacketNoEvent(new C09PacketHeldItemChange(blockInInventory));
                    this.isBlockBeingHeldServerSide = true;
                }
            }
        }
        if (!this.placing) {
            return;
        }
        this.placing = false;
        Scaffold.mc.thePlayer.swingItem();
        if (this.onPlayerRightClick(Scaffold.mc.thePlayer, Scaffold.mc.theWorld, this.serverSideItemStack, this.targetBlockPos, this.sideHit, this.hitVec)) {
            this.attackPacketSent = false;
            this.lastPlacedBlockPos = this.targetBlockPos;
            this.shouldMotionDown = true;
        }
    }
    
    private static float[] getBlockRotations(final double x, final double y, final double z, final EnumFacing facing) {
        final Entity temp = new EntitySnowball(Minecraft.getMinecraft().theWorld);
        temp.posX = x + 0.5;
        temp.posY = y + 0.5;
        temp.posZ = z + 0.5;
        return getAngles(temp);
    }
    
    private boolean didRayTraceFindValidBlock(final MovingObjectPosition rayTrace) {
        if (rayTrace != null && rayTrace.getBlockPos() != null) {
            final BlockPos hitBlockPos = rayTrace.getBlockPos();
            final Block hitBlock = this.getBlock(hitBlockPos);
            if (hitBlock.getMaterial() != Material.air) {
                return true;
            }
        }
        return false;
    }
    
    private MovingObjectPosition rayTrace(final Vec3 vec1, final Vec3 vec2) {
        return Scaffold.mc.theWorld.rayTraceBlocks(vec1, vec2, false, true, true);
    }
    
    private boolean onPlayerRightClick(final EntityPlayer player, final WorldClient worldIn, final ItemStack heldStack, final BlockPos hitPos, final EnumFacing side, final Vec3 hitVec) {
        Scaffold.mc.playerController.syncCurrentPlayItem();
        final float f = (float)(hitVec.xCoord - hitPos.getX());
        final float f2 = (float)(hitVec.yCoord - hitPos.getY());
        final float f3 = (float)(hitVec.zCoord - hitPos.getZ());
        boolean flag = false;
        if (!Scaffold.mc.theWorld.getWorldBorder().contains(hitPos)) {
            return false;
        }
        if (Scaffold.mc.playerController.currentGameType != WorldSettings.GameType.SPECTATOR) {
            final IBlockState iblockstate = worldIn.getBlockState(hitPos);
            if ((!player.isSneaking() || player.getHeldItem() == null) && iblockstate.getBlock().onBlockActivated(worldIn, hitPos, iblockstate, player, side, f, f2, f3)) {
                flag = true;
            }
            if (!flag && heldStack != null && heldStack.getItem() instanceof ItemBlock) {
                final ItemBlock itemblock = (ItemBlock)heldStack.getItem();
                if (!itemblock.canPlaceBlockOnSide(worldIn, hitPos, side, player, heldStack)) {
                    return false;
                }
            }
        }
        Scaffold.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(hitPos, side.getIndex(), heldStack, f, f2, f3));
        if (flag || Scaffold.mc.playerController.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return true;
        }
        if (heldStack == null) {
            return false;
        }
        if (Scaffold.mc.playerController.currentGameType.isCreative()) {
            final int i = heldStack.getMetadata();
            final int j = heldStack.stackSize;
            final boolean flag2 = heldStack.onItemUse(player, worldIn, hitPos, side, f, f2, f3);
            heldStack.setItemDamage(i);
            heldStack.stackSize = j;
            return flag2;
        }
        return heldStack.onItemUse(player, worldIn, hitPos, side, f, f2, f3);
    }
    
    private void resetServerSideHotbarIndex() {
        this.isBlockBeingHeldServerSide = false;
        Scaffold.mc.thePlayer.sendQueue.sendPacketNoEvent(new C09PacketHeldItemChange(Scaffold.mc.thePlayer.inventory.currentItem));
    }
    
    @Subscriber
    public void PacketSendEvent(final EventSendPacket packet) {
        if (!this.isBlockBeingHeldServerSide) {
            return;
        }
        if (packet.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity)packet.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK) {
            this.resetServerSideHotbarIndex();
            this.attackPacketSent = true;
        }
        if (packet.getPacket() instanceof C09PacketHeldItemChange) {
            this.packetQueue.add(new C09PacketHeldItemChange(((C09PacketHeldItemChange)packet.getPacket()).getSlotId()));
            packet.setCancelled(true);
            this.isBlockBeingHeldServerSide = false;
        }
        packet.setPacket(packet.getPacket());
    }
    
    private int getBlockInHotbar() {
        return this.getBlockInHotbar(-1);
    }
    
    private int getBlockInHotbar(final int ignore) {
        for (int i = 0; i < 9; ++i) {
            if (i != ignore) {
                final ItemStack stack = Scaffold.mc.thePlayer.inventory.getStackInSlot(i);
                if (stack != null) {
                    final Item item = stack.getItem();
                    if (item != null) {
                        if (Block.getBlockFromItem(item) != null) {
                            if (Block.getBlockFromItem(item).isFullBlock()) {
                                return i;
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    public Vec3 getBlockSide(final BlockPos pos, final EnumFacing face) {
        return (face == EnumFacing.NORTH) ? new Vec3(pos.getX(), pos.getY(), pos.getZ() - 0.5) : ((face == EnumFacing.EAST) ? new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ()) : ((face == EnumFacing.SOUTH) ? new Vec3(pos.getX(), pos.getY(), pos.getZ() + 0.5) : ((face == EnumFacing.WEST) ? new Vec3(pos.getX() - 0.5, pos.getY(), pos.getZ()) : new Vec3(pos.getX(), pos.getY(), pos.getZ()))));
    }
}
