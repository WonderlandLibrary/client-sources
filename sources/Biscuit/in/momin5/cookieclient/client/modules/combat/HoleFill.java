package in.momin5.cookieclient.client.modules.combat;

import in.momin5.cookieclient.api.event.events.EventRender;
import in.momin5.cookieclient.api.event.events.PacketEvent;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingColor;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import in.momin5.cookieclient.api.util.utils.misc.EntityUtil;
import in.momin5.cookieclient.api.util.utils.player.BlockUtils;
import in.momin5.cookieclient.api.util.utils.player.PlayerUtil;
import in.momin5.cookieclient.api.util.utils.render.CustomColor;
import in.momin5.cookieclient.api.util.utils.render.DRenderUtils;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HoleFill extends Module {

    public SettingBoolean smart = register(new SettingBoolean("Smart",this,false));
    public SettingNumber range = register(new SettingNumber("Range",this,4.5,1.0,7.0,0.1));
    public SettingNumber smartRange = register(new SettingNumber("SmartRange",this,4,1,6,1));
    SettingBoolean rotate = register(new SettingBoolean("Rotate",this,false));
    SettingColor color = register(new SettingColor("Color",this,new CustomColor(52,68,255,29)));

    private BlockPos render;
    private Entity renderEnt;
    private EntityPlayer closestTarget;
    private final long systemTime = -1L;
    private static boolean togglePitch;
    private boolean switchCooldown = false;
    private boolean isAttacking = false;
    private boolean caOn;
    private int newSlot;
    double d;
    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;
    private Packet packet;

    public HoleFill(){
        super("HoleFill", Category.COMBAT);
    }

    @EventHandler
    private final Listener<PacketEvent.Send> meAndYourMomFlushed = new Listener<>(event -> {
        packet = event.getPacket();
        if (packet instanceof CPacketPlayer && HoleFill.isSpoofingAngles) {
            ((CPacketPlayer)packet).yaw = (float)HoleFill.yaw;
            ((CPacketPlayer)packet).pitch = (float)HoleFill.pitch;
        }
    }, (Predicate<PacketEvent.Send>[])new Predicate[0]);

    @Override
    public void onEnable() {
        if (ModuleManager.isModuleEnabled("AutoCrystal")) {
            this.caOn = true;
        }
    }

    @Override
    public void onDisable(){
    }

    public void onUpdate() {
        if (HoleFill.mc.world == null) {
            return;
        }
        if (this.smart.isEnabled()) {
            this.findClosestTarget();
        }
        final List<BlockPos> blocks = this.findCrystalBlocks();
        BlockPos q = null;
        int obsidianSlot = findItemInHotbar();

        for (final BlockPos blockPos : blocks) {
            if (mc.world.getEntitiesWithinAABB((Entity.class), new AxisAlignedBB(blockPos)).isEmpty()) {
                if (this.smart.isEnabled() && this.isInRange(blockPos)) {
                    q = blockPos;
                }
                else {
                    q = blockPos;
                }
            }
        }
        this.render = q;
        if (q != null && mc.player.onGround) {
            final int oldSlot = mc.player.inventory.currentItem;

            if(obsidianSlot != -1) {

                if(rotate.isEnabled()){
                    this.lookAtPacket(q.x + 0.5, q.y - 0.5, q.z + 0.5, mc.player);
                }

                if(mc.player.inventory.currentItem != obsidianSlot){
                    mc.player.inventory.currentItem = obsidianSlot;
                }
                if(mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)) {
                    BlockUtils.placeBlock(render, EnumHand.MAIN_HAND, false, true, false);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.inventory.currentItem = oldSlot;
                }
                if(rotate.isEnabled()) {
                    resetRotation();
                }
            }
        }
    }

    private void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = PlayerUtil.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float)v[0], (float)v[1]);
    }

    private boolean IsHole(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 0, 0);
        final BlockPos boost3 = blockPos.add(0, 0, -1);
        final BlockPos boost4 = blockPos.add(1, 0, 0);
        final BlockPos boost5 = blockPos.add(-1, 0, 0);
        final BlockPos boost6 = blockPos.add(0, 0, 1);
        final BlockPos boost7 = blockPos.add(0, 2, 0);
        final BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        final BlockPos boost9 = blockPos.add(0, -1, 0);
        return mc.world.getBlockState(boost).getBlock() == Blocks.AIR && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && HoleFill.mc.world.getBlockState(boost7).getBlock() == Blocks.AIR && (HoleFill.mc.world.getBlockState(boost3).getBlock() == Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(boost3).getBlock() == Blocks.BEDROCK) && (HoleFill.mc.world.getBlockState(boost4).getBlock() == Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(boost4).getBlock() == Blocks.BEDROCK) && (HoleFill.mc.world.getBlockState(boost5).getBlock() == Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(boost5).getBlock() == Blocks.BEDROCK) && (HoleFill.mc.world.getBlockState(boost6).getBlock() == Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(boost6).getBlock() == Blocks.BEDROCK) && HoleFill.mc.world.getBlockState(boost8).getBlock() == Blocks.AIR && (HoleFill.mc.world.getBlockState(boost9).getBlock() == Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(boost9).getBlock() == Blocks.BEDROCK);
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    public BlockPos getClosestTargetPos() {
        if (this.closestTarget != null) {
            return new BlockPos(Math.floor(this.closestTarget.posX), Math.floor(this.closestTarget.posY), Math.floor(this.closestTarget.posZ));
        }
        return null;
    }

    private void findClosestTarget() {
        final List<EntityPlayer> playerList = mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target == mc.player) {
                continue;
            }

            if (!EntityUtil.isLiving(target)) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            if (this.closestTarget == null) {
                this.closestTarget = target;
            }
            else {
                if (HoleFill.mc.player.getDistance(target) >= HoleFill.mc.player.getDistance(this.closestTarget)) {
                    continue;
                }
                this.closestTarget = target;
            }
        }
    }

    private boolean isInRange(final BlockPos blockPos) {
        NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(getSphere(getPlayerPos(), ((Double)this.range.getValue()).floatValue(), ((Double)this.range.getValue()).intValue(), false, true, 0).stream().filter(this::IsHole).collect(Collectors.toList()));
        if (positions.contains(blockPos))
            return true;
        return false;
    }

    private List<BlockPos> findCrystalBlocks() {
        float smartFloat = (float) this.smartRange.getValue();

        NonNullList<BlockPos> positions = NonNullList.create();
        if (this.smart.isEnabled() && this.closestTarget != null) {
            positions.addAll(getSphere(getClosestTargetPos(), smartFloat, ((Double)this.range.getValue()).intValue(), false, true, 0).stream().filter(this::IsHole).filter(this::isInRange).collect(Collectors.toList()));
        } else if (!this.smart.isEnabled()) {
            positions.addAll(getSphere(getPlayerPos(), ((Double)this.range.getValue()).floatValue(), ((Double)this.range.getValue()).intValue(), false, true, 0).stream().filter(this::IsHole).collect(Collectors.toList()));
        }
        return positions;
    }

    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    private static void setYawAndPitch(final float yaw1, final float pitch1) {
        HoleFill.yaw = yaw1;
        HoleFill.pitch = pitch1;
        HoleFill.isSpoofingAngles = true;
    }

    private static void resetRotation() {
        if (HoleFill.isSpoofingAngles) {
            HoleFill.yaw = HoleFill.mc.player.rotationYaw;
            HoleFill.pitch = HoleFill.mc.player.rotationPitch;
            HoleFill.isSpoofingAngles = false;
        }
    }

    private int findItemInHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock) stack.getItem()).getBlock();
                if (block instanceof BlockEnderChest)
                    return i;
                else if (block instanceof BlockObsidian)
                    return i;
            }
        }
        return -1;
    }

    @Override
    public void onWorldRender(EventRender event) {
        if(this.render != null){
            DRenderUtils.drawBox(this.render,1,new CustomColor(color.getValue()),255);
            DRenderUtils.drawBoundingBox(this.render,1,1.00f,new CustomColor(color.getValue(),255));
        }
    }
}
