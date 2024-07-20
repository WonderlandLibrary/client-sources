/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.Event3D;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventRotationJump;
import ru.govno.client.event.events.EventRotationStrafe;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.PotionThrower;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class Nuker
extends Module {
    Settings MaxBlocksShare;
    Settings Rotations;
    Settings ClientLook;
    Settings SilentMoveRot;
    Settings CheckAsACube;
    Settings Target;
    Settings BreakFermBlocks;
    private double dzX = 0.0;
    private double dzY = 0.0;
    private double dzZ = 0.0;
    private double dzX2 = 0.0;
    private double dzY2 = 0.0;
    private double dzZ2 = 0.0;
    private double smoothProgress = 0.0;
    private float alphaHPG = 0.0f;
    private float hpgX = 0.0f;
    private float hpgY = 0.0f;
    private float hpgZ = 0.0f;
    private Vec3d animatedHPG = null;
    final List<BlockPos> positions = new ArrayList<BlockPos>();
    List<BlockPos> targetedPoses = new ArrayList<BlockPos>();
    private static BlockPos targetedPosition = null;
    public static BlockPos renderPosition = null;
    float yaw;
    float lastRYaw;
    float lastRPitch;

    public Nuker() {
        super("Nuker", 0, Module.Category.MISC);
        this.MaxBlocksShare = new Settings("MaxBlocksShare", 1.0f, 4.0f, 1.0f, this);
        this.settings.add(this.MaxBlocksShare);
        this.Rotations = new Settings("Rotations", true, (Module)this);
        this.settings.add(this.Rotations);
        this.ClientLook = new Settings("ClientLook", false, (Module)this, () -> this.Rotations.bValue);
        this.settings.add(this.ClientLook);
        this.SilentMoveRot = new Settings("SilentMoveRot", true, (Module)this, () -> this.Rotations.bValue);
        this.settings.add(this.SilentMoveRot);
        this.CheckAsACube = new Settings("CheckAsACube", false, (Module)this);
        this.settings.add(this.CheckAsACube);
        this.Target = new Settings("Target", "All", (Module)this, new String[]{"All", "Ores", "Wooden", "Stones", "Ferma"});
        this.settings.add(this.Target);
        this.BreakFermBlocks = new Settings("BreakFermBlocks", false, (Module)this, () -> this.Target.currentMode.equalsIgnoreCase("Ferma"));
        this.settings.add(this.BreakFermBlocks);
    }

    private Vec3d playerVec3dPos() {
        return new Vec3d(mc.getRenderManager().getRenderPosX(), mc.getRenderManager().getRenderPosY() + 0.51, mc.getRenderManager().getRenderPosZ());
    }

    private ArrayList<BlockPos> positionsZone(Vec3d playerPos, float[] ranges) {
        ArrayList<BlockPos> poses = new ArrayList<BlockPos>();
        int x = (int)(-ranges[0]);
        while ((float)x < ranges[0]) {
            int z = (int)(-ranges[0]);
            while ((float)z < ranges[0]) {
                int y = (int)(-ranges[2]);
                while ((float)y < ranges[1]) {
                    BlockPos pos2 = new BlockPos(playerPos.xCoord + (double)x, playerPos.yCoord + (double)y, playerPos.zCoord + (double)z);
                    if (pos2 != null) {
                        poses.add(pos2);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        poses.sort(Comparator.comparingInt(pos -> (int)pos.getDistanceToBlockPos(BlockUtils.getEntityBlockPos(Minecraft.player))));
        return poses;
    }

    private boolean isUnbreakebleBlock(Block block) {
        return block == Blocks.AIR || (block == Blocks.BEDROCK || block == Blocks.BARRIER) && !Minecraft.player.isCreative() || block instanceof BlockLiquid;
    }

    private Vec3d[] getPositionsZone01(Vec3d playerPos, float[] ranges) {
        return new Vec3d[]{new Vec3d(playerPos.xCoord - (double)this.getRanges()[0], playerPos.yCoord - (double)this.getRanges()[2], playerPos.zCoord - (double)this.getRanges()[0]), new Vec3d(playerPos.xCoord + (double)this.getRanges()[0], playerPos.yCoord + (double)this.getRanges()[1], playerPos.zCoord + (double)this.getRanges()[0])};
    }

    private void drawZone(float[] ranges) {
        float ext = 0.01f;
        Vec3d vecMin = this.getPositionsZone01(this.playerVec3dPos(), ranges)[0].addVector(ext, ext, ext);
        Vec3d vecMax = this.getPositionsZone01(this.playerVec3dPos(), ranges)[1].addVector(-ext, -ext, -ext);
        float animationsSpeed = (float)((double)0.0125f * Minecraft.frameTime);
        double toX = vecMin.xCoord;
        double toY = vecMin.yCoord;
        double toZ = vecMin.zCoord;
        double toX2 = vecMax.xCoord;
        double toY2 = vecMax.yCoord;
        double toZ2 = vecMax.zCoord;
        this.dzX = MathUtils.harpD(this.dzX, toX, animationsSpeed);
        this.dzY = MathUtils.harpD(this.dzY, toY, animationsSpeed);
        this.dzZ = MathUtils.harpD(this.dzZ, toZ, animationsSpeed);
        this.dzX2 = MathUtils.harpD(this.dzX2, toX2, animationsSpeed);
        this.dzY2 = MathUtils.harpD(this.dzY2, toY2, animationsSpeed);
        this.dzZ2 = MathUtils.harpD(this.dzZ2, toZ2, animationsSpeed);
        AxisAlignedBB axisBox = new AxisAlignedBB(this.dzX, this.dzY, this.dzZ, this.dzX2, this.dzY2, this.dzZ2);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.33333f);
        int color = ColorUtils.getColor(255, 255, 255, 50);
        int color2 = ColorUtils.getColor(255, 255, 255, 5);
        RenderUtils.drawCanisterBox(axisBox, true, false, true, color, 0, color2);
    }

    private void drawHittingProgress() {
        float animationsSpeed = (float)((double)0.02f * Minecraft.frameTime);
        BlockPos pos = this.getRenderPosition();
        if (pos != null) {
            float toX = pos.getX();
            float toY = pos.getY();
            float toZ = pos.getZ();
            this.hpgX = MathUtils.harp(this.hpgX, toX, animationsSpeed);
            this.hpgY = MathUtils.harp(this.hpgY, toY, animationsSpeed);
            this.hpgZ = MathUtils.harp(this.hpgZ, toZ, animationsSpeed);
            this.alphaHPG = MathUtils.harp(this.alphaHPG, 255.0f, animationsSpeed * 3.0f);
        } else if (MathUtils.getDifferenceOf(this.alphaHPG, 0.0f) > 0.0) {
            this.alphaHPG = MathUtils.harp(this.alphaHPG, 0.0f, animationsSpeed);
        }
        this.animatedHPG = new Vec3d((double)this.hpgX + 0.5, (double)this.hpgY + 0.5, (double)this.hpgZ + 0.5);
        float progress = Nuker.mc.playerController.curBlockDamageMP;
        this.smoothProgress = MathUtils.lerp((float)this.smoothProgress, progress, animationsSpeed * 3.0f);
        Vec3d firstPoint = new Vec3d(this.animatedHPG.xCoord - 0.5 * this.smoothProgress, this.animatedHPG.yCoord - 0.5 * this.smoothProgress, this.animatedHPG.zCoord - 0.5 * this.smoothProgress);
        Vec3d lastPoint = new Vec3d(this.animatedHPG.xCoord + 0.5 * this.smoothProgress, this.animatedHPG.yCoord + 0.5 * this.smoothProgress, this.animatedHPG.zCoord + 0.5 * this.smoothProgress);
        AxisAlignedBB axisBox = new AxisAlignedBB(firstPoint.xCoord, firstPoint.yCoord, firstPoint.zCoord, lastPoint.xCoord, lastPoint.yCoord, lastPoint.zCoord);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, this.alphaHPG / 255.0f);
        int color = ColorUtils.getColor(255, 255, 255, this.alphaHPG / 3.0f);
        int color2 = ColorUtils.getColor(255, 255, 255, this.alphaHPG / 25.5f);
        RenderUtils.drawCanisterBox(axisBox, true, true, true, color, color, color2);
    }

    private boolean seenBlockPos(BlockPos pos) {
        return Minecraft.player.canEntityBeSeenCoords(pos.getX(), pos.getY(), pos.getZ());
    }

    private boolean canSeenBlock(BlockPos pos, boolean ignoreNoSeen) {
        if (this.seenBlockPos(pos) || this.seenBlockPos(pos.add(1, 1, 1)) || this.seenBlockPos(pos.add(-1, 1, -1)) || this.seenBlockPos(pos.add(1, 1, -1)) || this.seenBlockPos(pos.add(-1, 1, 1)) || this.seenBlockPos(pos.add(1, 1, 0)) || this.seenBlockPos(pos.add(-1, 1, 0)) || this.seenBlockPos(pos.add(0, 1, 1)) || this.seenBlockPos(pos.add(0, 1, -1)) || this.seenBlockPos(pos.add(1, 0, 1)) || this.seenBlockPos(pos.add(-1, 0, -1)) || this.seenBlockPos(pos.add(1, 0, -1)) || this.seenBlockPos(pos.add(-1, 0, 1)) || this.seenBlockPos(pos.add(1, 0, 0)) || this.seenBlockPos(pos.add(-1, 0, 0)) || this.seenBlockPos(pos.add(0, 0, 1)) || this.seenBlockPos(pos.add(0, 0, -1)) || pos == Minecraft.player.getPosition().add(0, 1, 0)) {
            return true;
        }
        return ignoreNoSeen;
    }

    private boolean blockIsInRange(BlockPos pos, float[] ranges, boolean returnTrue) {
        int pointX = pos.getX() + ((double)pos.getX() < this.playerVec3dPos().xCoord ? 1 : 0);
        int pointY = pos.getY() + ((double)pos.getY() < this.playerVec3dPos().yCoord + 1.0 ? 1 : 0);
        int pointZ = pos.getZ() + ((double)pos.getZ() < this.playerVec3dPos().zCoord ? 1 : 0);
        float yRange = (double)pos.getY() < this.playerVec3dPos().xCoord + 1.0 ? ranges[2] : ranges[1];
        double sqrtRangeToPos = Math.abs(Math.sqrt(ranges[0] * ranges[0] + yRange * yRange));
        double xDifference = (double)pointX - this.playerVec3dPos().xCoord;
        double yDifference = (double)pointY - (this.playerVec3dPos().yCoord + 1.0);
        double zDifference = (double)pointZ - this.playerVec3dPos().zCoord;
        return returnTrue || Math.abs(Math.sqrt(xDifference * xDifference + zDifference * zDifference)) + Math.abs(Math.sqrt(yDifference * yDifference)) <= sqrtRangeToPos;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean canBreakBlock(BlockPos pos, String mode) {
        IBlockState state = Nuker.mc.world.getBlockState(pos);
        Block block = state.getBlock();
        Material mat = state.getMaterial();
        switch (mode) {
            case "All": {
                return true;
            }
            case "Ores": {
                if (block instanceof BlockOre) return true;
                if (block == Blocks.LIT_REDSTONE_ORE) return true;
                if (block != Blocks.REDSTONE_ORE) return false;
                return true;
            }
            case "Wooden": {
                if (mat != Material.WOOD) return false;
                return true;
            }
            case "Stones": {
                if (mat != Material.ROCK) return false;
                if (block instanceof BlockOre) return false;
                if (block == Blocks.LIT_REDSTONE_ORE) return false;
                if (block == Blocks.REDSTONE_ORE) return false;
                return true;
            }
            case "Ferma": {
                if (block instanceof BlockCrops) {
                    BlockCrops crop = (BlockCrops)block;
                    if (crop.isMaxAge(state)) return true;
                }
                if (block != Blocks.MELON_BLOCK && block != Blocks.PUMPKIN) {
                    if (!(block instanceof BlockCocoa)) return false;
                    BlockCocoa cocoa = (BlockCocoa)block;
                    if (state.getValue(BlockCocoa.AGE) != 2) return false;
                }
                if (!this.BreakFermBlocks.bValue) return false;
                return true;
            }
        }
        return false;
    }

    private List<BlockPos> getTargetBlocks(int maxCount, Vec3d playerPos, float[] ranges, boolean ignoreWalls, boolean checkDistance, String mode) {
        this.positions.clear();
        for (BlockPos position : this.positionsZone(playerPos, ranges)) {
            IBlockState state = Nuker.mc.world.getBlockState(position);
            Block block = state.getBlock();
            if (!this.blockIsInRange(position, ranges, checkDistance) || this.isUnbreakebleBlock(block) || !this.canSeenBlock(position, ignoreWalls) || !this.canBreakBlock(position, mode) || this.positions.size() >= maxCount) continue;
            this.positions.add(position);
        }
        return this.positions;
    }

    private float[] getRanges() {
        return new float[]{3.5f, 5.0f, 0.0f};
    }

    private void setTargetPositions(Vec3d playerPos, float[] ranges, boolean ignoreWalls, boolean checkDistance, int maxPosesCount) {
        this.targetedPoses = this.getTargetBlocks(maxPosesCount, playerPos, ranges, ignoreWalls, checkDistance, this.Target.currentMode);
        targetedPosition = this.targetedPoses == null || this.targetedPoses.isEmpty() || this.targetedPoses.get(0) == null ? null : this.targetedPoses.get(0);
    }

    private BlockPos getTargetedPosition() {
        return targetedPosition;
    }

    private BlockPos getRenderPosition() {
        return renderPosition;
    }

    private void processBreakBlock(List<BlockPos> poses) {
        if (poses.isEmpty()) {
            return;
        }
        poses.forEach(pos -> {
            if (pos != null) {
                EnumFacing face = BlockUtils.getPlaceableSide(pos);
                if (face != null) {
                    face = face.getOpposite();
                }
                if (this.Rotations.bValue && Nuker.mc.objectMouseOver != null) {
                    float prevYaw = Minecraft.player.rotationYaw;
                    float prevPitch = Minecraft.player.rotationPitch;
                    Minecraft.player.rotationYaw = this.lastRYaw;
                    Minecraft.player.rotationPitch = this.lastRPitch;
                    Nuker.mc.entityRenderer.getMouseOver(1.0f);
                    face = Nuker.mc.objectMouseOver.sideHit;
                    pos = Nuker.mc.objectMouseOver.getBlockPos();
                    Nuker.mc.entityRenderer.getMouseOver(1.0f);
                    Minecraft.player.rotationYaw = prevYaw;
                    Minecraft.player.rotationPitch = prevPitch;
                }
                if (face != null && pos != null && Nuker.mc.playerController.onPlayerDamageBlock((BlockPos)pos, face)) {
                    Minecraft.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        });
    }

    private void rotations(EventPlayerMotionUpdate e, BlockPos pos) {
        if (pos == null) {
            return;
        }
        e.setYaw(RotationUtil.getMatrixRotations4BlockPos(pos.add(0.5, 0.5, 0.5))[0]);
        e.setPitch(RotationUtil.getMatrixRotations4BlockPos(pos.add(0.5, 0.3, 0.5))[1]);
        Minecraft.player.rotationYawHead = e.getYaw();
        Minecraft.player.renderYawOffset = e.getYaw();
        Minecraft.player.rotationPitchHead = e.getPitch();
        if (this.ClientLook.bValue) {
            Minecraft.player.rotationYaw = e.getYaw();
            Minecraft.player.rotationPitch = e.getPitch();
        }
        this.lastRYaw = e.getYaw();
        this.lastRPitch = e.getPitch();
    }

    @Override
    public void onUpdate() {
        boolean ignoreWalls = false;
        boolean checkDistance = this.CheckAsACube.bValue;
        int maxBlocksSame = (int)this.MaxBlocksShare.fValue;
        this.setTargetPositions(this.playerVec3dPos(), this.getRanges(), false, checkDistance, maxBlocksSame);
        this.processBreakBlock(this.targetedPoses);
    }

    @EventTarget
    public void onUpdate(EventPlayerMotionUpdate e) {
        if (this.Rotations.bValue && !PotionThrower.get.forceThrow && !PotionThrower.get.callThrowPotions) {
            this.rotations(e, this.getTargetedPosition());
            this.yaw = e.getYaw();
        } else {
            this.yaw = -10001.0f;
        }
    }

    @EventTarget
    public void onStrafeSide(EventRotationStrafe e) {
        if (this.SilentMoveRot.bValue && this.Rotations.bValue && this.getTargetedPosition() != null && this.yaw != -10001.0f) {
            e.setYaw(this.yaw);
        }
    }

    @EventTarget
    public void onJumpSide(EventRotationJump e) {
        if (this.SilentMoveRot.bValue && this.Rotations.bValue && this.getTargetedPosition() != null && this.yaw != -10001.0f) {
            e.setYaw(this.yaw);
        }
    }

    @EventTarget
    public void onRender3D(Event3D e) {
        RenderUtils.setup3dForBlockPos(() -> {
            this.drawHittingProgress();
            GL11.glEnable(2929);
            this.drawZone(this.getRanges());
        }, true);
    }
}

