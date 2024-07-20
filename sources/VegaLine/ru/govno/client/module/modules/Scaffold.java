/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventCanPlaceBlock;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventRotationStrafe;
import ru.govno.client.event.events.EventSafeWalk;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.Timer;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.RotationUtils;

public class Scaffold
extends Module {
    public static Scaffold get;
    private final List<Block> BLOCK_BLACKLIST = Arrays.asList(Blocks.ENCHANTING_TABLE, Blocks.CHEST, Blocks.ENDER_CHEST, Blocks.TRAPPED_CHEST, Blocks.ANVIL, Blocks.SAND, Blocks.WEB, Blocks.TORCH, Blocks.CRAFTING_TABLE, Blocks.FURNACE, Blocks.WATERLILY, Blocks.DISPENSER, Blocks.STONE_BRICK_STAIRS, Blocks.WOODEN_PRESSURE_PLATE, Blocks.NOTEBLOCK, Blocks.DROPPER, Blocks.TNT, Blocks.STANDING_BANNER, Blocks.WALL_BANNER, Blocks.REDSTONE_TORCH, Blocks.TRAPDOOR, Blocks.IRON_TRAPDOOR);
    Vec3d targetBlock;
    List<Vec3d> placePossibilities = new ArrayList<Vec3d>();
    TimerHelper delayUtils = new TimerHelper();
    EnumFacingOffset enumFacing;
    BlockPos blockFace;
    float yaw;
    float pitch;
    public static int ticksOnAir;
    public static int oldSlot;
    public static int offGroundTicks;
    public static int blocksPlaced;
    public static int blockCount;
    public static int slotIndex;
    boolean sneaking;
    public static float animationDelta;

    public Scaffold() {
        super("Scaffold", 0, Module.Category.PLAYER);
        this.settings.add(new Settings("Delay", 100.0f, 500.0f, 0.0f, this));
        this.settings.add(new Settings("BlockCount", true, (Module)this));
        this.settings.add(new Settings("Sprint", false, (Module)this));
        this.settings.add(new Settings("Sneak", "Packet", (Module)this, new String[]{"Packet", "Client", "None"}));
        this.settings.add(new Settings("Swing", false, (Module)this));
        this.settings.add(new Settings("TowerMotion", true, (Module)this));
        this.settings.add(new Settings("Tower", "Matrix", this, new String[]{"Matrix", "NCP", "Strict"}, () -> this.currentBooleanValue("TowerMotion")));
        this.settings.add(new Settings("Speed", 0.58f, 1.0f, 0.01f, this));
        this.settings.add(new Settings("TimerUse", true, (Module)this));
        this.settings.add(new Settings("TimerWalk", 1.0f, 2.0f, 0.5f, this, () -> this.currentBooleanValue("TimerUse")));
        this.settings.add(new Settings("TimerTower", 1.8f, 2.0f, 0.5f, this, () -> this.currentBooleanValue("TimerUse")));
        this.settings.add(new Settings("SilentSwitch", false, (Module)this));
        get = this;
    }

    @Override
    public void onToggled(boolean actived) {
        if (actived) {
            if (Minecraft.player == null || Scaffold.mc.world == null) {
                return;
            }
            oldSlot = Minecraft.player.inventory.currentItem;
            this.delayUtils.reset();
            this.blockFace = new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ);
            this.yaw = Minecraft.player.rotationYaw;
            this.pitch = Minecraft.player.rotationPitch;
            this.targetBlock = null;
            this.placePossibilities.clear();
        } else {
            blocksPlaced = 0;
            Scaffold.mc.timer.speed = 1.0;
            if (Minecraft.player == null || Scaffold.mc.world == null) {
                return;
            }
            Minecraft.player.inventory.currentItem = oldSlot;
            this.delayUtils.reset();
            this.placePossibilities.clear();
            Scaffold.mc.gameSettings.keyBindSneak.pressed = Keyboard.isKeyDown(Scaffold.mc.gameSettings.keyBindSneak.getKeyCode());
            if (this.sneaking) {
                this.sneaking = false;
                if (this.currentMode("Sneak").equalsIgnoreCase("Packet")) {
                    Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (this.currentMode("Sneak").equalsIgnoreCase("Client")) {
                    Scaffold.mc.gameSettings.keyBindSneak.pressed = Keyboard.isKeyDown(Scaffold.mc.gameSettings.keyBindSneak.getKeyCode());
                }
            }
        }
        super.onToggled(actived);
    }

    @EventTarget
    public void onStrafe(EventRotationStrafe event) {
        if (!this.actived) {
            return;
        }
        float yaw = Minecraft.player.rotationYaw % 360.0f;
        int ticks = 6;
        int yawing = 6;
        float prevDiff = yaw + (float)(Minecraft.player.ticksExisted % ticks < ticks / 2 ? yawing : -yawing);
        event.setYaw(prevDiff);
    }

    @EventTarget
    public void on(EventSafeWalk eventSafeWalk) {
        eventSafeWalk.setCancelled(true);
    }

    public static float getAnimationState(float animation, float finalState, float speed) {
        float add = (float)((double)animationDelta * (double)speed);
        animation = animation < finalState ? ((double)animation + (double)add < (double)finalState ? animation + add : finalState) : ((double)animation - (double)add > (double)finalState ? animation - add : finalState);
        return animation;
    }

    private final EnumFacing getPlaceableSide(BlockPos pos) {
        for (EnumFacing facing : EnumFacing.values()) {
            IBlockState state;
            BlockPos offset = pos.offset(facing);
            if (!Scaffold.mc.world.getBlockState(offset).getBlock().canCollideCheck(Scaffold.mc.world.getBlockState(offset), false) || (state = Scaffold.mc.world.getBlockState(offset)).getMaterial().isReplaceable()) continue;
            return facing;
        }
        return null;
    }

    private void placeAction() {
        BlockPos toPlacePos = new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 1.0, Minecraft.player.posZ);
        EnumFacing v4 = this.getPlaceableSide(toPlacePos);
        if (v4 == null) {
            v4 = EnumFacing.UP;
        }
        EnumFacing v2 = v4.getOpposite();
        BlockPos v1 = toPlacePos.offset(v4);
        Vec3d v3 = new Vec3d(v1).addVector(0.5, 0.5, 0.5).add(new Vec3d(v2.getDirectionVec()).scale(0.5));
        Scaffold.mc.playerController.processRightClickBlock(Minecraft.player, Scaffold.mc.world, v1, v2, v3, EnumHand.MAIN_HAND);
    }

    @Override
    public void onUpdate() {
        boolean isTowered;
        boolean bl = isTowered = this.currentBooleanValue("TowerMotion") && Minecraft.player.isJumping() && blockCount > 0;
        if (this.currentBooleanValue("TimerUse")) {
            Scaffold.mc.timer.speed = isTowered ? (double)this.currentFloatValue("TimerTower") : (double)this.currentFloatValue("TimerWalk");
            Timer.forceTimer(isTowered ? this.currentFloatValue("TimerTower") : this.currentFloatValue("TimerWalk"));
        }
        if (!isTowered && !this.sneaking) {
            Minecraft.player.multiplyMotionXZ(MathUtils.clamp(this.currentFloatValue("Speed"), 0.0f, 1.0f));
        }
        if (this.currentBooleanValue("TowerMotion") && blockCount > 0) {
            if (this.currentMode("Tower").equalsIgnoreCase("Matrix") && Minecraft.player.isJumping() && !this.delayUtils.hasReached(10.0)) {
                Minecraft.player.jumpTicks = 0;
                double x = Minecraft.player.posX;
                double y = Minecraft.player.posY - 1.0;
                double z = Minecraft.player.posZ;
                if (Scaffold.mc.world.getBlockState(new BlockPos(x, y - 0.01, z)).getBlock() != Blocks.AIR) {
                    Minecraft.player.onGround = true;
                }
            }
            if (this.currentMode("Tower").equalsIgnoreCase("Strict") && Minecraft.player.isJumping() && !MoveMeHelp.moveKeysPressed() && Minecraft.player.onGround) {
                Minecraft.player.motionX = 0.0;
                Minecraft.player.motionZ = 0.0;
                MoveMeHelp.setCuttingSpeed(0.0);
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 0.41999998688698, Minecraft.player.posZ, false));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 0.7531999805211997, Minecraft.player.posZ, false));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 1.00133597911214, Minecraft.player.posZ, false));
                Minecraft.player.setPosY(Minecraft.player.posY + 1.0);
                if (slotIndex != -1) {
                    int oldSwitch = Minecraft.player.inventory.currentItem;
                    Minecraft.player.inventory.currentItem = this.currentBooleanValue("SilentSwitch") ? slotIndex : slotIndex;
                    this.placeAction();
                    if (this.currentBooleanValue("SilentSwitch")) {
                        Minecraft.player.inventory.currentItem = oldSwitch;
                    }
                    if (this.currentBooleanValue("Swing")) {
                        Minecraft.player.swingArm(EnumHand.MAIN_HAND);
                    } else {
                        Minecraft.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                    }
                    GuiIngame.trottleScaff = (float)((double)GuiIngame.trottleScaff + 0.3);
                    ++blocksPlaced;
                }
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (this.currentMode("Tower").equalsIgnoreCase("NCP") && Scaffold.mc.gameSettings.keyBindJump.isKeyDown()) {
                Minecraft.player.motionY = Minecraft.player.onGround ? 0.48 : (Minecraft.player.motionY *= 1.016);
                if (Minecraft.player.motionY < 0.16) {
                    Minecraft.player.motionY -= 10.0;
                }
            }
        }
        if (this.enumFacing == null || this.blockFace == null) {
            return;
        }
        BlockPos pos = this.blockFace;
        double x = (double)pos.getX() + 0.5;
        double y = (double)pos.getY() - 0.08;
        double z = (double)pos.getZ() + 0.5;
        float[] rotate = RotationUtils.setRotationsToVec3d(new Vec3d(x, y -= 2.0, z));
        this.yaw = rotate[0] + (float)MathUtils.getRandomInRange(-2, -2);
        this.pitch = rotate[1] + (float)MathUtils.getRandomInRange(-1, -1);
        float mouseDelta = Scaffold.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float matrixFixed = mouseDelta * mouseDelta * mouseDelta * 1.2f;
        this.yaw -= this.yaw % matrixFixed;
        this.pitch -= this.pitch % matrixFixed;
    }

    @EventTarget
    public void onPlayerMotionUpdate(EventPlayerMotionUpdate e) {
        e.setYaw(this.yaw);
        e.setPitch(this.pitch);
        ticksOnAir = this.getBlockRelativeToPlayer(0.0, -1.0, 0.0) instanceof BlockAir ? ++ticksOnAir : 0;
        offGroundTicks = Minecraft.player.onGround ? 0 : ++offGroundTicks;
        int blocks = 0;
        for (int i = 36; i < 45; ++i) {
            Block block;
            ItemStack itemStack = Minecraft.player.inventoryContainer.getSlot(i).getStack();
            if (itemStack == null || !(itemStack.getItem() instanceof ItemBlock) || itemStack.stackSize <= 0 || this.BLOCK_BLACKLIST.contains(block = ((ItemBlock)itemStack.getItem()).getBlock())) continue;
            blocks += itemStack.stackSize;
        }
        blockCount = blocks;
        this.placePossibilities = this.getPlacePossibilities();
        if (this.placePossibilities.isEmpty()) {
            return;
        }
        this.placePossibilities.sort(Comparator.comparingDouble(vec3 -> Minecraft.player.getDistance(vec3.xCoord, vec3.yCoord + 1.0, vec3.zCoord)));
        this.targetBlock = this.placePossibilities.get(0);
        this.enumFacing = this.getEnumFacing();
        if (this.enumFacing == null) {
            return;
        }
        BlockPos position = new BlockPos(this.targetBlock.xCoord, this.targetBlock.yCoord, this.targetBlock.zCoord);
        this.blockFace = position.add(this.enumFacing.getOffset().xCoord, this.enumFacing.getOffset().yCoord, this.enumFacing.getOffset().zCoord);
        if (Scaffold.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 1.0, Minecraft.player.posZ)).getBlock() instanceof BlockAir || Scaffold.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 1.0, Minecraft.player.posZ)).getBlock() instanceof BlockLiquid) {
            if (!this.sneaking) {
                if (!this.currentMode("Sneak").equalsIgnoreCase("None")) {
                    this.sneaking = true;
                }
                if (this.currentMode("Sneak").equalsIgnoreCase("Packet")) {
                    Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SNEAKING));
                }
                if (this.currentMode("Sneak").equalsIgnoreCase("Client")) {
                    Scaffold.mc.gameSettings.keyBindSneak.pressed = true;
                }
            }
        } else if (this.sneaking) {
            if (!this.currentMode("Sneak").equalsIgnoreCase("None")) {
                this.sneaking = false;
            }
            if (this.currentMode("Sneak").equalsIgnoreCase("Packet")) {
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (this.currentMode("Sneak").equalsIgnoreCase("Client")) {
                Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
            }
        }
        if (!this.currentBooleanValue("Sprint")) {
            Scaffold.mc.gameSettings.keyBindSprint.pressed = false;
            Minecraft.player.setSprinting(false);
        }
        e.setYaw(this.yaw);
        e.setPitch(this.pitch);
        Minecraft.player.rotationYawHead = Minecraft.player.renderYawOffset = this.yaw;
        Minecraft.player.rotationPitchHead = this.pitch;
        if (this.placePossibilities.isEmpty() || this.targetBlock == null || this.enumFacing == null || this.blockFace == null) {
            return;
        }
        if (Scaffold.mc.gameSettings.keyBindJump.isKeyDown() && Minecraft.player.onGround && !(this.getBlockRelativeToPlayer(0.0, -1.0, 0.0) instanceof BlockAir) && !(this.getBlockRelativeToPlayer(0.0, -1.0, 0.0) instanceof BlockLiquid)) {
            Minecraft.player.jump();
        }
    }

    @EventTarget
    public void can(EventCanPlaceBlock event) {
        if (!this.currentMode("Tower").equalsIgnoreCase("Strict") || !Minecraft.player.isJumping() || MoveMeHelp.moveKeysPressed()) {
            this.placeBlock();
        }
    }

    private void placeBlock() {
        boolean isTowered;
        if (this.placePossibilities.isEmpty() || this.targetBlock == null || this.enumFacing == null || this.blockFace == null) {
            return;
        }
        RayTraceResult movingObjectPosition = Minecraft.player.rayTraceCustom(Scaffold.mc.playerController.getBlockReachDistance(), mc.getRenderPartialTicks(), this.yaw, this.pitch);
        Vec3d hitVec = movingObjectPosition.hitVec;
        slotIndex = -1;
        for (int i = 0; i < 9; ++i) {
            ItemBlock iBlock;
            Block block;
            ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
            Item item = itemStack.getItem();
            if (!(item instanceof ItemBlock) || this.BLOCK_BLACKLIST.contains(block = (iBlock = (ItemBlock)item).getBlock())) continue;
            slotIndex = i;
        }
        boolean bl = isTowered = this.currentBooleanValue("TowerMotion") && Minecraft.player.isJumping() && blockCount > 0;
        if ((offGroundTicks == 0 || (Minecraft.player.fallDistance > 0.0f || isTowered) && offGroundTicks <= 3 || offGroundTicks > 5) && ticksOnAir > 0 && this.lookingAtBlock()) {
            if (!this.lookingAtBlock()) {
                hitVec.yCoord = this.blockFace.getY();
                hitVec.zCoord = this.blockFace.getZ();
                hitVec.xCoord = this.blockFace.getX();
            }
            if (this.delayUtils.hasReached(isTowered ? 0.0 : (double)((int)this.currentFloatValue("Delay")))) {
                if (slotIndex != -1) {
                    int oldSwitch = Minecraft.player.inventory.currentItem;
                    Minecraft.player.inventory.currentItem = this.currentBooleanValue("SilentSwitch") ? slotIndex : slotIndex;
                    Scaffold.mc.playerController.processRightClickBlock(Minecraft.player, Scaffold.mc.world, this.blockFace, this.enumFacing.getEnumFacing(), hitVec, EnumHand.MAIN_HAND);
                    if (this.currentBooleanValue("SilentSwitch")) {
                        Minecraft.player.inventory.currentItem = oldSwitch;
                    }
                    if (this.currentBooleanValue("Swing")) {
                        Minecraft.player.swingArm(EnumHand.MAIN_HAND);
                    } else {
                        Minecraft.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                    }
                    GuiIngame.trottleScaff = (float)((double)GuiIngame.trottleScaff + 0.3);
                    ++blocksPlaced;
                }
                this.delayUtils.reset();
            }
        }
    }

    EnumFacingOffset getEnumFacing() {
        for (int x2 = -1; x2 <= 1; x2 += 2) {
            if (Scaffold.mc.world.getBlockState(new BlockPos(this.targetBlock.xCoord + (double)x2, this.targetBlock.yCoord, this.targetBlock.zCoord)).getBlock() instanceof BlockAir || Scaffold.mc.world.getBlockState(new BlockPos(this.targetBlock.xCoord + (double)x2, this.targetBlock.yCoord, this.targetBlock.zCoord)).getBlock() instanceof BlockLiquid) continue;
            if (x2 > 0) {
                return new EnumFacingOffset(EnumFacing.WEST, new Vec3d(x2, 0.0, 0.0));
            }
            return new EnumFacingOffset(EnumFacing.EAST, new Vec3d(x2, 0.0, 0.0));
        }
        for (int y2 = -1; y2 <= 1; y2 += 2) {
            if (Scaffold.mc.world.getBlockState(new BlockPos(this.targetBlock.xCoord, this.targetBlock.yCoord + (double)y2, this.targetBlock.zCoord)).getBlock() instanceof BlockAir || Scaffold.mc.world.getBlockState(new BlockPos(this.targetBlock.xCoord, this.targetBlock.yCoord + (double)y2, this.targetBlock.zCoord)).getBlock() instanceof BlockLiquid || y2 >= 0) continue;
            return new EnumFacingOffset(EnumFacing.UP, new Vec3d(0.0, y2, 0.0));
        }
        for (int z2 = -1; z2 <= 1; z2 += 2) {
            if (Scaffold.mc.world.getBlockState(new BlockPos(this.targetBlock.xCoord, this.targetBlock.yCoord, this.targetBlock.zCoord + (double)z2)).getBlock() instanceof BlockAir || Scaffold.mc.world.getBlockState(new BlockPos(this.targetBlock.xCoord, this.targetBlock.yCoord, this.targetBlock.zCoord + (double)z2)).getBlock() instanceof BlockLiquid) continue;
            if (z2 < 0) {
                return new EnumFacingOffset(EnumFacing.SOUTH, new Vec3d(0.0, 0.0, z2));
            }
            return new EnumFacingOffset(EnumFacing.NORTH, new Vec3d(0.0, 0.0, z2));
        }
        return null;
    }

    List<Vec3d> getPlacePossibilities() {
        ArrayList<Vec3d> possibilities = new ArrayList<Vec3d>();
        int range = (int)Math.ceil(6.0);
        for (int x = -range; x <= range; ++x) {
            for (int y = -range; y <= range; ++y) {
                for (int z = -range; z <= range; ++z) {
                    Block block = this.getBlockRelativeToPlayer(x, y, z);
                    if (block instanceof BlockAir) continue;
                    for (int x2 = -1; x2 <= 1; x2 += 2) {
                        possibilities.add(new Vec3d(Minecraft.player.posX + (double)x + (double)x2, Minecraft.player.posY + (double)y, Minecraft.player.posZ + (double)z));
                    }
                    for (int y2 = -1; y2 <= 1; y2 += 2) {
                        possibilities.add(new Vec3d(Minecraft.player.posX + (double)x, Minecraft.player.posY + (double)y + (double)y2, Minecraft.player.posZ + (double)z));
                    }
                    for (int z2 = -1; z2 <= 1; z2 += 2) {
                        possibilities.add(new Vec3d(Minecraft.player.posX + (double)x, Minecraft.player.posY + (double)y, Minecraft.player.posZ + (double)z + (double)z2));
                    }
                }
            }
        }
        return possibilities;
    }

    boolean lookingAtBlock() {
        RayTraceResult movingObjectPosition = Minecraft.player.rayTraceCustom(Scaffold.mc.playerController.getBlockReachDistance(), mc.getRenderPartialTicks(), this.yaw, this.pitch);
        Vec3d hitVec = movingObjectPosition.hitVec;
        if (hitVec == null) {
            return false;
        }
        if (hitVec.xCoord - (double)this.blockFace.getX() > 1.0 || hitVec.xCoord - (double)this.blockFace.getX() < 0.0) {
            return false;
        }
        if (hitVec.yCoord - (double)this.blockFace.getY() > 1.0 || hitVec.yCoord - (double)this.blockFace.getY() < 0.0) {
            return false;
        }
        return !(hitVec.zCoord - (double)this.blockFace.getZ() > 1.0) && !(hitVec.zCoord - (double)this.blockFace.getZ() < 0.0);
    }

    Block getBlockRelativeToPlayer(double offsetX, double offsetY, double offsetZ) {
        return Scaffold.mc.world.getBlockState(new BlockPos(Minecraft.player.posX + offsetX, Minecraft.player.posY + offsetY, Minecraft.player.posZ + offsetZ)).getBlock();
    }

    public class EnumFacingOffset {
        public EnumFacing enumFacing;
        private final Vec3d offset;

        public EnumFacingOffset(EnumFacing enumFacing, Vec3d offset) {
            this.enumFacing = enumFacing;
            this.offset = offset;
        }

        public EnumFacing getEnumFacing() {
            return this.enumFacing;
        }

        public Vec3d getOffset() {
            return this.offset;
        }
    }
}

