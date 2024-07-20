/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.Event3D;
import ru.govno.client.event.events.EventMove2;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class Surround
extends Module {
    public static Surround get;
    public Settings Rotations;
    public Settings AutoCenter;
    public Settings PlaceHand;
    public Settings Mode;
    public Settings Switch;
    public Settings PlaceMS;
    public Settings PostPlacing;
    private final TimerHelper delayTimer = new TimerHelper();
    private boolean centered = true;
    private boolean postPlaceSet;
    private EnumHand tempHand;
    private BlockPos tempPlacePos;
    public static List<BlockPos> toPlacePoses;

    public Surround() {
        super("Surround", 0, Module.Category.MISC);
        this.Rotations = new Settings("Rotations", false, (Module)this);
        this.settings.add(this.Rotations);
        this.AutoCenter = new Settings("AutoCenter", true, (Module)this);
        this.settings.add(this.AutoCenter);
        this.PlaceHand = new Settings("PlaceHand", "Auto", (Module)this, new String[]{"OffHand", "MainHand", "Auto"});
        this.settings.add(this.PlaceHand);
        this.Mode = new Settings("Mode", "Fast", (Module)this, new String[]{"Fast", "Queue"});
        this.settings.add(this.Mode);
        this.Switch = new Settings("Switch", "Client", (Module)this, new String[]{"Client", "Silent"});
        this.settings.add(this.Switch);
        this.PlaceMS = new Settings("PlaceMS", 100.0f, 250.0f, 0.0f, this);
        this.settings.add(this.PlaceMS);
        this.PostPlacing = new Settings("PostPlacing", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Queue"));
        this.settings.add(this.PostPlacing);
        get = this;
    }

    private boolean switchIsSilent() {
        return this.Switch.currentMode.equalsIgnoreCase("Silent");
    }

    private void drawPosESP(BlockPos pos, int color) {
        AxisAlignedBB aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), (double)pos.getX() + 1.0, (double)pos.getY() + 1.0, (double)pos.getZ() + 1.0);
        int col1 = color;
        int col2 = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) / 2.0f);
        int col3 = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) / 5.0f);
        RenderUtils.drawCanisterBox(aabb, true, true, true, col1, col2, col3);
        GlStateManager.resetColor();
    }

    private BlockPos getEntityBlockPos(Entity entity) {
        return new BlockPos(entity.posX, entity.posY + (double)0.3f, entity.posZ);
    }

    private Vec3d getEntityVec3dPos(Entity entity) {
        return new Vec3d(entity.posX, entity.posY, entity.posZ);
    }

    private EntityPlayer getMe() {
        return FreeCam.fakePlayer != null && FreeCam.get.actived ? FreeCam.fakePlayer : Minecraft.player;
    }

    private boolean meIsCentered() {
        Vec3d myVec = this.getEntityVec3dPos(this.getMe());
        float w = this.getMe().width - 1.0E-5f;
        float xzDS = 0.5f - w / 2.0f;
        double xGrate = Math.floor(myVec.xCoord) + 0.5;
        double zGrate = Math.floor(myVec.zCoord) + 0.5;
        double xMe = myVec.xCoord;
        double zMe = myVec.zCoord;
        boolean cX = xMe > xGrate - (double)xzDS && xMe < xGrate + (double)xzDS;
        boolean cZ = zMe > zGrate - (double)xzDS && zMe < zGrate + (double)xzDS;
        return cX && cZ;
    }

    private Vec3d getCentereVec() {
        return new Vec3d((double)this.getEntityBlockPos(this.getMe()).getX() + 0.5, this.getEntityBlockPos(this.getMe()).getY(), (double)this.getEntityBlockPos(this.getMe()).getZ() + 0.5);
    }

    private boolean posNotCollide(BlockPos pos) {
        CopyOnWriteArrayList e = new CopyOnWriteArrayList();
        if (Surround.mc.world == null) {
            return true;
        }
        Surround.mc.world.getLoadedEntityList().forEach(ents -> {
            if (ents != null && ents instanceof EntityItem) {
                EntityItem item = (EntityItem)ents;
                e.add(item);
            }
            if (ents != null && ents instanceof EntityEnderCrystal) {
                EntityEnderCrystal crystal = (EntityEnderCrystal)ents;
                e.add(crystal);
            }
        });
        return pos != null;
    }

    public boolean getBlockWithExpand(float expand, double x, double y, double z, Block block) {
        return Surround.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() == block || Surround.mc.world.getBlockState(new BlockPos(x + (double)expand, y, z + (double)expand)).getBlock() == block || Surround.mc.world.getBlockState(new BlockPos(x - (double)expand, y, z - (double)expand)).getBlock() == block || Surround.mc.world.getBlockState(new BlockPos(x + (double)expand, y, z - (double)expand)).getBlock() == block || Surround.mc.world.getBlockState(new BlockPos(x - (double)expand, y, z + (double)expand)).getBlock() == block || Surround.mc.world.getBlockState(new BlockPos(x + (double)expand, y, z)).getBlock() == block || Surround.mc.world.getBlockState(new BlockPos(x - (double)expand, y, z)).getBlock() == block || Surround.mc.world.getBlockState(new BlockPos(x, y, z + (double)expand)).getBlock() == block || Surround.mc.world.getBlockState(new BlockPos(x, y, z - (double)expand)).getBlock() == block;
    }

    public boolean getBlockWithExpand(float expand, BlockPos pos, Block block) {
        return this.getBlockWithExpand(expand, pos.getX(), pos.getY(), pos.getZ(), block);
    }

    private Material getBlockMaterial(BlockPos pos) {
        return Surround.mc.world.getBlockState(pos).getBlock().getMaterial(Surround.mc.world.getBlockState(pos));
    }

    private boolean blockMaterialIsCurrent(BlockPos pos) {
        return !this.getBlockMaterial(pos).isReplaceable() && !this.getBlockMaterial(pos).isLiquid() && this.getBlockMaterial(pos).blocksMovement();
    }

    private boolean blockMaterialIsCurrentWithSideSets(BlockPos pos) {
        return this.blockMaterialIsCurrent(pos.west()) || this.blockMaterialIsCurrent(pos.east()) || this.blockMaterialIsCurrent(pos.south()) || this.blockMaterialIsCurrent(pos.north()) || this.blockMaterialIsCurrent(pos.down());
    }

    private boolean canPlaceObsidian(BlockPos pos) {
        boolean aired = this.getBlockMaterial(pos).isReplaceable();
        boolean neared = this.blockMaterialIsCurrentWithSideSets(pos);
        return aired && neared && this.posNotCollide(pos) && BlockUtils.getPlaceableSideSeen(pos, this.getMe()) != null;
    }

    private void moveTo(Vec3d vec, EventMove2 move, float speed) {
        float yawToVec = RotationUtil.getLookAngles(vec)[0].floatValue();
        double sin = -Math.sin(Math.toRadians(yawToVec)) * (double)speed;
        double cos = Math.cos(Math.toRadians(yawToVec)) * (double)speed;
        move.motion().xCoord = sin;
        move.motion().zCoord = cos;
        MoveMeHelp.multiplySpeed(0.01f);
    }

    private double getSpeedMove() {
        return MathUtils.clamp(MoveMeHelp.getCuttingSpeed(), 0.23, 0.5);
    }

    @EventTarget
    public void onMove(EventMove2 move) {
        if (!this.centered && this.AutoCenter.bValue) {
            this.moveTo(this.getCentereVec(), move, (float)this.getSpeedMove());
        }
    }

    private List<BlockPos> getCurPoses() {
        BlockPos myPos = this.basePosAtMe();
        ArrayList<BlockPos> cur = new ArrayList<BlockPos>();
        if (this.getBlockMaterial(myPos.west()).isReplaceable()) {
            cur.add(myPos.west());
        }
        if (this.getBlockMaterial(myPos.east()).isReplaceable()) {
            cur.add(myPos.east());
        }
        if (this.getBlockMaterial(myPos.south()).isReplaceable()) {
            cur.add(myPos.south());
        }
        if (this.getBlockMaterial(myPos.north()).isReplaceable()) {
            cur.add(myPos.north());
        }
        return cur;
    }

    private void updatePoses() {
        toPlacePoses = this.getCurPoses().stream().filter(temp -> this.canPlaceObsidian((BlockPos)temp)).collect(Collectors.toList());
    }

    private EnumFacing getPlaceableSide(BlockPos pos) {
        for (EnumFacing facing : EnumFacing.values()) {
            IBlockState state;
            BlockPos offset = pos.offset(facing);
            if (!Surround.mc.world.getBlockState(offset).getBlock().canCollideCheck(Surround.mc.world.getBlockState(offset), false) || (state = Surround.mc.world.getBlockState(offset)).getMaterial().isReplaceable()) continue;
            return facing;
        }
        return null;
    }

    private boolean itemInOffHand() {
        return Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemBlock;
    }

    private boolean haveItemInMainHand() {
        return Surround.getItemInHotbar() != -1;
    }

    private boolean haveItem() {
        return (this.haveItemInMainHand() || Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemBlock) && this.getUsedHand() != null;
    }

    private EnumHand getUsedHand() {
        boolean off = this.PlaceHand.currentMode.equalsIgnoreCase("OffHand");
        boolean main = this.PlaceHand.currentMode.equalsIgnoreCase("MainHand");
        boolean auto = this.PlaceHand.currentMode.equalsIgnoreCase("Auto");
        return off && this.itemInOffHand() ? EnumHand.OFF_HAND : (main && this.haveItemInMainHand() ? EnumHand.MAIN_HAND : (auto ? (this.itemInOffHand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND) : null));
    }

    public static int getItemInHotbar() {
        for (int i = 0; i < 9; ++i) {
            if (!(Minecraft.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock)) continue;
            return i;
        }
        return -1;
    }

    private int getSlotForItem() {
        return Surround.getItemInHotbar();
    }

    private void placeBlock(BlockPos pos, EnumHand placeHand) {
        EnumFacing v4 = BlockUtils.getPlaceableSide(pos);
        if (v4 == null) {
            return;
        }
        EnumFacing v2 = v4.getOpposite();
        BlockPos v1 = pos.offset(v4);
        Vec3d v3 = new Vec3d(v1).addVector(0.5, 0.5, 0.5).add(new Vec3d(v2.getDirectionVec()).scale(0.5));
        Surround.mc.playerController.processRightClickBlock(Minecraft.player, Surround.mc.world, v1, v2, v3, placeHand);
        Minecraft.player.connection.sendPacket(new CPacketAnimation(placeHand));
    }

    private void placeBlockPost(BlockPos pos, EnumHand placeHand) {
        if (this.postPlaceSet) {
            return;
        }
        this.tempPlacePos = pos;
        this.tempHand = placeHand;
        this.postPlaceSet = true;
    }

    private void updatePlaceBlockPost() {
        if (!this.postPlaceSet) {
            return;
        }
        this.placeBlockPost(this.tempPlacePos, this.tempHand);
        this.tempPlacePos = null;
        this.tempHand = null;
        this.postPlaceSet = false;
    }

    private void handSlotSnapFor(EnumHand placeHand, boolean packetSwap, Runnable code) {
        int startSlot = Minecraft.player.inventory.currentItem;
        int currentSlot = this.getSlotForItem();
        if (placeHand == EnumHand.MAIN_HAND && startSlot != this.getSlotForItem()) {
            Minecraft.player.inventory.currentItem = currentSlot;
            if (packetSwap) {
                Surround.mc.playerController.syncCurrentPlayItem();
            }
        }
        code.run();
        if (placeHand == EnumHand.MAIN_HAND && packetSwap) {
            Minecraft.player.inventory.currentItem = startSlot;
        }
    }

    private BlockPos basePosAtMe() {
        BlockPos pos = this.getEntityBlockPos(this.getMe());
        if (!this.getMe().onGround) {
            int x = pos.getX();
            int z = pos.getZ();
            for (int y = pos.getY() - 5; y < pos.getY(); ++y) {
                if (!Speed.posBlock(x, y, z) || Speed.posBlock(x, y + 1, z)) continue;
                pos = new BlockPos(x, y, z).up();
            }
        }
        return pos;
    }

    private boolean canPlace() {
        return this.haveItem() && (!this.getMe().isJumping() || !MoveMeHelp.isMoving());
    }

    @EventTarget
    public void onEventPlayerUpdate(EventPlayerMotionUpdate event) {
        BlockPos bpos;
        BlockPos blockPos = bpos = toPlacePoses.isEmpty() ? this.tempPlacePos : toPlacePoses.get(0);
        if (bpos != null && this.Rotations.bValue) {
            EnumFacing face = BlockUtils.getPlaceableSide(bpos);
            Vec3d toRot = new Vec3d(bpos).addVector(0.5, 0.5, 0.5).addVector((double)face.getFrontOffsetX() * 0.5, (double)face.getFrontOffsetY() * 0.5, (double)face.getFrontOffsetZ() * 0.5);
            if (toRot == null) {
                return;
            }
            float[] rotate = RotationUtil.getNeededFacing(new Vec3d(toRot.xCoord, toRot.yCoord, toRot.zCoord), false, Minecraft.player, false);
            if (rotate == null) {
                return;
            }
            event.setYaw(rotate[0]);
            event.setPitch(rotate[1]);
            this.getMe().rotationYawHead = rotate[0];
            this.getMe().renderYawOffset = rotate[0];
            this.getMe().rotationPitchHead = rotate[1];
            HitAura.get.rotationsCombat = rotate;
            HitAura.get.rotationsVisual = rotate;
        }
    }

    @Override
    public void onUpdate() {
        if (this.canPlace()) {
            this.centered = this.meIsCentered();
            this.updatePoses();
            boolean isFast = this.Mode.currentMode.equalsIgnoreCase("Fast");
            this.updatePlaceBlockPost();
            if (toPlacePoses != null && toPlacePoses.size() > 0 && this.delayTimer.hasReached(this.PlaceMS.fValue)) {
                this.handSlotSnapFor(this.getUsedHand(), this.switchIsSilent(), () -> {
                    boolean sneak = Minecraft.player.isSneaking();
                    if (!sneak) {
                        Minecraft.player.connection.preSendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    if (isFast) {
                        toPlacePoses.forEach(pos -> {
                            if (this.PostPlacing.bValue) {
                                this.placeBlockPost((BlockPos)pos, this.getUsedHand());
                            } else {
                                this.placeBlock((BlockPos)pos, this.getUsedHand());
                            }
                        });
                    } else if (toPlacePoses.get(0) != null) {
                        this.placeBlock(toPlacePoses.get(0), this.getUsedHand());
                    }
                    if (!sneak) {
                        Minecraft.player.connection.preSendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                });
                this.delayTimer.reset();
            }
            return;
        }
        this.centered = true;
        toPlacePoses.clear();
    }

    @EventTarget
    public void onRender3D(Event3D event) {
        if (this.actived) {
            BlockPos myPos = this.basePosAtMe();
            if (toPlacePoses != null && toPlacePoses.size() > 0) {
                RenderUtils.setup3dForBlockPos(() -> {
                    if (!this.centered) {
                        this.drawPosESP(myPos, ColorUtils.getColor(255, 40, 40, 180));
                    }
                    if (!this.centered) {
                        this.drawPosESP(myPos.up(), ColorUtils.getColor(255, 40, 40, 180));
                    }
                    toPlacePoses.forEach(pos -> this.drawPosESP((BlockPos)pos, ColorUtils.getColor(100, 255, 100, 100)));
                }, true);
            }
        }
    }

    static {
        toPlacePoses = new ArrayList<BlockPos>();
    }
}

