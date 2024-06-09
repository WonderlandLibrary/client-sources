package me.travis.wurstplus.module.modules.combat;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.BlockInteractionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

// made by travis :D

@Module.Info(name = "Auto32k", category = Module.Category.COMBAT)
public class Auto32k extends Module
{
    private static final DecimalFormat df;
    private static final List<Block> shulkerList;
    private Setting<Boolean> moveToHotbar = this.register(Settings.b("Move 32k to Hotbar", true));
    private Setting<Double> placeRange = this.register(Settings.d("Place Range", 5.0));
    private Setting<Boolean> spoofRotation = this.register(Settings.b("Spoof Rotation", true));
    private Setting<Boolean> raytraceCheck = this.register(Settings.b("Raytrace Check", true));
    private Setting<Boolean> debugMessages = this.register(Settings.b("Debug Messages", false));
    private Setting<Integer> wait = this.register(Settings.integerBuilder("Hopper Wait").withMinimum(1).withValue(10).withMaximum(20).build());
    private boolean dispenserPlace;
    private boolean flag;
    private boolean lastBlock;
    private int hopperSlot;
    private int delay;
    private int swordSlot;
    private int shulkerSlot;
    private int obiSlot;
    private int redSlot;
    private int disSlot;

    private Float yaw;
    public int xPlus;
    public int zPlus;
    public double xDis;
    public double zDis;
    public EnumFacing face;
    private BlockPos placeTarget;
    
    @Override
    protected void onEnable() {
        if (Auto32k.mc.player == null) {
            this.disable();
            return;
        }
        this.delay = 0;
        this.lastBlock = false;
        this.dispenserPlace = false;
        this.flag = true;
        Auto32k.df.setRoundingMode(RoundingMode.CEILING); // sets the rounding mode to always round up
        this.hopperSlot = -1;
        this.obiSlot = -1;
        this.redSlot = -1;
        this.disSlot = -1;
        this.swordSlot = -1;
        this.shulkerSlot = -1;
        boolean isNegative = false;
        this.yaw = mc.player.rotationYaw;
        if (this.yaw < 0) { // if yaw is negative flip the x
            isNegative = true;
        }
        int dir = Math.round(Math.abs(this.yaw)) % 360; // yet the magnatude of the player's direction
        if (135 < dir && dir < 225) { // if looking south
            xPlus = 0;
            zPlus = 1;
            xDis = 0.0;
            zDis = -0.1;
            face = EnumFacing.SOUTH;
        } else if (225 < dir && dir < 315) { // if looking west
            if (isNegative) {
                xPlus = 1;
                zPlus = 0;
                xDis = 0.1;
                zDis = 0.0;
                face = EnumFacing.EAST;
            } else {
                xPlus = -1;
                zPlus = 0;
                xDis = -0.1;
                zDis = 0.0;
                face = EnumFacing.WEST;
            }            
        } else if (45 < dir && dir < 135) { // if looking east
            if (isNegative) {
                xPlus = -1;
                zPlus = 0;
                xDis = -0.1;
                zDis = 0.0;
                face = EnumFacing.WEST;
            } else {
                xPlus = 1;
                zPlus = 0;
                xDis = 0.1;
                zDis = 0.0;
                face = EnumFacing.EAST;
            }  
        } else { // if looking north
            xPlus = 0;
            zPlus = -1;
            xDis = 0.0;
            zDis = 0.1;
            face = EnumFacing.NORTH;
        }
        for (int i = 0; i < 9 && (this.hopperSlot == -1 || this.shulkerSlot == -1 || this.obiSlot == -1 || this.redSlot == -1 || this.disSlot == -1); ++i) { // finds all the blocks u need
            final ItemStack stack = Auto32k.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block == Blocks.HOPPER) {
                        this.hopperSlot = i;
                    }
                    else if (Auto32k.shulkerList.contains(block)) {
                        this.shulkerSlot = i;
                    }
                    else if (block == Blocks.OBSIDIAN) {
                        this.obiSlot = i;
                    }
                    else if (block == Blocks.REDSTONE_BLOCK) {
                        this.redSlot = i;
                    }
                    else if (block == Blocks.DISPENSER) {
                        this.disSlot = i;
                    }
                }
            }
        }
        if (this.shulkerSlot == -1 || this.hopperSlot == -1 || this.obiSlot == -1 || this.redSlot == -1 || this.disSlot == -1) {
            if (this.debugMessages.getValue()) {
                Command.sendChatMessage("Ensure all blocks needed are in your hotbar x");
            }
            this.disable();
            return;
        }
        try {
            this.placeTarget = new BlockPos((double)mc.objectMouseOver.getBlockPos().getX(), (double)mc.objectMouseOver.getBlockPos().getY()+1, (double)mc.objectMouseOver.getBlockPos().getZ());
        } catch (Exception e) {
            Command.sendChatMessage("right mate, ur fucking retarded if you think that's a block");
            this.disable();
            return;
        }
        if (this.isAreaPlaceable(this.placeTarget)) {
            Command.sendChatMessage("its most certainly games time");
        }
        else {
            Command.sendChatMessage("right pal, I cant quite place here rn, so imma try and find somewhere else");
            this.disable();
            return;
        }
        Auto32k.mc.player.inventory.currentItem = this.obiSlot;
        placeBlock(new BlockPos((Vec3i)this.placeTarget), this.spoofRotation.getValue()); // place obi
        Auto32k.mc.player.inventory.currentItem = this.disSlot;
        placeBlock(new BlockPos((Vec3i)this.placeTarget.add(0, 1, 0)), this.spoofRotation.getValue()); // place dispenser
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(new BlockPos((Vec3i)this.placeTarget.add(0, 1, 0)), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));  
    }
    
    @Override
    public void onUpdate() {
        if (this.isDisabled() || Auto32k.mc.player == null) {
            return;
        }
        if (this.lastBlock && this.delay >= this.wait.getValue()) {
            Auto32k.mc.player.inventory.currentItem = this.hopperSlot;
            final BlockPos hopperPos = new BlockPos((Vec3i)this.placeTarget.add(xPlus, 0, zPlus));

            Auto32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Auto32k.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            placeBlock(hopperPos, this.spoofRotation.getValue()); // place hopper
            Auto32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Auto32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            Auto32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(new BlockPos((Vec3i)this.placeTarget.add(xPlus, 0, zPlus)), face, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            Auto32k.mc.player.inventory.currentItem = this.shulkerSlot;
            this.swordSlot = this.shulkerSlot + 32;
            this.lastBlock = false;
            this.delay = 0;
        }
        if (!this.dispenserPlace) { // if you need to place the dispenser
            placeItemsInDispenser();
            return;
        }
        if (this.flag) { // if the rest of the blocks need to be placed
            placeRestOfBlocks();
        }
        if (!(Auto32k.mc.currentScreen instanceof GuiContainer)) {
            this.delay++;
            return;
        }
        if (!this.moveToHotbar.getValue()) {
            this.disable();
            return;
        }
        if (this.swordSlot == -1) {
            this.delay++;
            return;
        }
        else {
            boolean swapReady = true;
            if (((GuiContainer)Auto32k.mc.currentScreen).inventorySlots.getSlot(0).getStack().isEmpty) {
                this.delay++;
                swapReady = false;
            }
            if (!((GuiContainer)Auto32k.mc.currentScreen).inventorySlots.getSlot(this.swordSlot).getStack().isEmpty) {
                this.delay++;
                swapReady = false;
            }
            if (swapReady) {
                Auto32k.mc.playerController.windowClick(((GuiContainer)Auto32k.mc.currentScreen).inventorySlots.windowId, 0, this.swordSlot - 32, ClickType.SWAP, (EntityPlayer)Auto32k.mc.player);
                this.disable();
            }
        }
    }
    
    private boolean isAreaPlaceable(final BlockPos blockPos) {
        for (final Entity entity : Auto32k.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos))) {
            if (entity instanceof EntityLivingBase) {
                return false;
            }
        }
        if (!Auto32k.mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) { // obi
            return false;
        }
        if (!Auto32k.mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial().isReplaceable()) { // dispenser
            return false;
        }
        if (!Auto32k.mc.world.getBlockState(blockPos.add(xPlus, 0, zPlus)).getMaterial().isReplaceable()) { // hopper
            return false;
        }
        if (!Auto32k.mc.world.getBlockState(blockPos.add(xPlus, 1, zPlus)).getMaterial().isReplaceable()) { // shulker
            return false;
        }
        if (!Auto32k.mc.world.getBlockState(blockPos.add(0, 2, 0)).getMaterial().isReplaceable()) { // redstone block
            return false;
        }
        if (Auto32k.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() instanceof BlockAir) {
            return false;
        }
        if (Auto32k.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() instanceof BlockLiquid) {
            return false;
        }
        if (Auto32k.mc.player.getPositionVector().distanceTo(new Vec3d((Vec3i)blockPos)) > this.placeRange.getValue()) {
            return false;
        }
        if (this.raytraceCheck.getValue()) {
            final RayTraceResult result = Auto32k.mc.world.rayTraceBlocks(new Vec3d(Auto32k.mc.player.posX, Auto32k.mc.player.posY + Auto32k.mc.player.getEyeHeight(), Auto32k.mc.player.posZ), new Vec3d((Vec3i)blockPos), false, true, false);
            return result == null || result.getBlockPos().equals((Object)blockPos);
        }
        return true;
    }
    
    private void placeBlock(final BlockPos pos, final boolean spoofRotation) {
        if (!mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            Command.sendChatMessage("BLOCK WAS UNABLE TO BE PLACED :(");
            return;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (mc.world.getBlockState(neighbor).getBlock().canCollideCheck(mc.world.getBlockState(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5+xDis, 0.5, 0.5+zDis).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (spoofRotation) {
                    final Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
                    final double diffX = hitVec.x - eyesPos.x;
                    final double diffY = hitVec.y - eyesPos.y;
                    final double diffZ = hitVec.z - eyesPos.z;
                    final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
                    final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
                    final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
                    mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch), mc.player.onGround));
                }
                mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.rightClickDelayTimer = 4;
                return;
            }
        }
    }
    
    static {
        df = new DecimalFormat("#.#");
        shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(CrystalAura.mc.player.posX), Math.floor(CrystalAura.mc.player.posY),
                Math.floor(CrystalAura.mc.player.posZ));
    }

    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow,
        final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; ++x) {
            for (int z = cz - (int) r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int) r) : cy; y < (sphere ? (cy + r) : ((float) (cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z)
                            + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public void placeRestOfBlocks() {
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Auto32k.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        Auto32k.mc.player.inventory.currentItem = this.redSlot;
        placeBlock(new BlockPos((Vec3i)this.placeTarget.add(0, 2, 0)), this.spoofRotation.getValue()); // place redstone
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Auto32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        this.flag = false;
        this.lastBlock = true;
    }

    public void placeItemsInDispenser() {
        try {
            Auto32k.mc.playerController.windowClick(((GuiContainer)Auto32k.mc.currentScreen).inventorySlots.windowId, 0, this.shulkerSlot, ClickType.SWAP, (EntityPlayer)Auto32k.mc.player);
            this.dispenserPlace = true;
            return;
        } catch (Exception e) {
        }
    }

    public void faceBlock(final BlockPos pos, final EnumFacing face) {
        final Vec3d hitVec = new Vec3d((Vec3i)pos.offset(face)).add(0.5, 0.5, 0.5).add(new Vec3d(face.getDirectionVec()).scale(0.5));
        BlockInteractionHelper.faceVectorPacketInstant(hitVec);
    }
}