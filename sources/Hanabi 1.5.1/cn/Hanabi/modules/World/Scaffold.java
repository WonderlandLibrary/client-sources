package cn.Hanabi.modules.World;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import cn.Hanabi.injection.interfaces.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import cn.Hanabi.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import ClassSub.*;
import net.minecraft.block.*;

public class Scaffold extends Mod
{
    public Value<Boolean> tower;
    public Value<Boolean> noswing;
    public static Value<String> mode;
    public static float yaw;
    public static float pitch;
    public static List<Block> blacklistedBlocks;
    private Class205 timer;
    public static ItemStack currentlyHolding;
    Class205 tDelay;
    Scaffold.Class289 blockdata;
    double y;
    boolean sneaking;
    int count;
    boolean isSneaking;
    int theSlot;
    int slot;
    
    
    public static List<Block> getBlacklistedBlocks() {
        return Scaffold.blacklistedBlocks;
    }
    
    public Scaffold() {
        super("Scaffold", Category.WORLD);
        this.tower = new Value<Boolean>("Scaffold_Tower", true);
        this.noswing = new Value<Boolean>("Scaffold_NoSwing", true);
        this.timer = new Class205();
        this.tDelay = new Class205();
        this.theSlot = -1;
        Scaffold.mode.addValue("HypixelCN");
        Scaffold.mode.addValue("HypixelGlobal");
        Scaffold.mode.addValue("AAC");
        this.setState(true);
        Scaffold.blacklistedBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.trapped_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.web, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence);
    }
    
    public void onEnable() {
        this.tDelay.reset();
        Scaffold.yaw = 999.0f;
        Scaffold.pitch = 999.0f;
        this.count = 1;
        this.blockdata = null;
        if (Scaffold.mc.thePlayer != null) {
            this.y = Scaffold.mc.thePlayer.posY;
        }
        this.sneaking = true;
        super.onEnable();
    }
    
    public void onDisable() {
        Scaffold.yaw = 999.0f;
        Scaffold.pitch = 999.0f;
        this.blockdata = null;
        Class211.getTimer().timerSpeed = 1.0f;
        if (this.sneaking && !Scaffold.mc.thePlayer.isSneaking()) {
            Scaffold.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Scaffold.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
        if (Scaffold.mc.thePlayer.isSwingInProgress) {
            Scaffold.mc.thePlayer.swingProgress = 0.0f;
            Scaffold.mc.thePlayer.swingProgressInt = 0;
            Scaffold.mc.thePlayer.isSwingInProgress = false;
        }
    }
    
    @EventTarget
    public void onPre(final EventPreMotion eventPreMotion) {
        this.getBestBlocks();
        if (this.sneaking && !Scaffold.mc.thePlayer.isSneaking()) {
            Scaffold.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Scaffold.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
            this.sneaking = !this.sneaking;
        }
        if (!this.hotbarContainBlock()) {
            this.blockdata = null;
            return;
        }
        if (((IKeyBinding)Scaffold.mc.gameSettings.keyBindSneak).getPress() && Class200.MovementInput()) {
            Class200.setSpeed(Class200.getBaseMoveSpeed() * 0.6);
            this.isSneaking = true;
        }
        else {
            this.isSneaking = false;
        }
        double n = Scaffold.mc.thePlayer.posX;
        double n2 = Scaffold.mc.thePlayer.posZ;
        final double n3 = Scaffold.mc.thePlayer.movementInput.moveForward;
        final double n4 = Scaffold.mc.thePlayer.movementInput.moveStrafe;
        final float rotationYaw = Scaffold.mc.thePlayer.rotationYaw;
        if (!Scaffold.mc.thePlayer.isCollidedHorizontally) {
            final double[] array = this.getExpandCoords(n, n2, n3, n4, rotationYaw);
            n = array[0];
            n2 = array[1];
        }
        if (this.isAirBlock(Scaffold.mc.theWorld.getBlockState(new BlockPos(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY - 1.0 - (this.isSneaking ? 0.01 : 0.0), Scaffold.mc.thePlayer.posZ)).getBlock())) {
            n = Scaffold.mc.thePlayer.posX;
            n2 = Scaffold.mc.thePlayer.posZ;
        }
        this.y = Scaffold.mc.thePlayer.posY;
        final BlockPos blockPos = new BlockPos(n, this.y - 1.0 - (this.isSneaking ? 0.01 : 0.0), n2);
        final Block getBlock = Scaffold.mc.theWorld.getBlockState(blockPos).getBlock();
        final Scaffold.Class289 blockData = this.getBlockData(blockPos);
        this.setSpeed("Hypixel", eventPreMotion);
        if (this.getBlockCount() > 0 && this.tower.getValueState() && !Class200.isMoving2() && !Class200.isMoving2()) {
            this.tower("Hypixel", eventPreMotion);
        }
        if (blockData != null && this.isAirBlock(getBlock)) {
            this.blockdata = blockData;
            final float[] array2 = getRotations(blockData.position, blockData.face);
            eventPreMotion.setYaw(array2[0] + (float)randomNumber(1.0, -1.0));
            eventPreMotion.setPitch(array2[1]);
            Scaffold.yaw = array2[0];
            Scaffold.pitch = array2[1];
            if (!((IKeyBinding)Scaffold.mc.gameSettings.keyBindJump).getPress() && Scaffold.mc.thePlayer.onGround && Class180.isOnGround(0.001) && Scaffold.mc.thePlayer.isCollidedVertically) {
                eventPreMotion.setOnGround(false);
            }
        }
        if (Class200.MovementInput() && !Scaffold.mode.isCurrentMode("HypixelCN") && Scaffold.mode.isCurrentMode("AAC")) {
            Class200.setSpeed(0.11);
        }
    }
    
    @EventTarget
    public void onPost(final EventPostMotion eventPostMotion) {
        if (!this.tDelay.isDelayComplete(80L) && Class180.isOnGround(0.01) && !((IKeyBinding)Scaffold.mc.gameSettings.keyBindJump).getPress()) {
            return;
        }
        final BlockPos blockPos = new BlockPos(Scaffold.mc.thePlayer.posX, this.y - 1.0 - (this.isSneaking ? 0.01 : 0.0), Scaffold.mc.thePlayer.posZ);
        final Block getBlock = Scaffold.mc.theWorld.getBlockState(blockPos).getBlock();
        final Scaffold.Class289 blockData = this.getBlockData(blockPos);
        this.tDelay.reset();
        final int currentItem = Scaffold.mc.thePlayer.inventory.currentItem;
        final ItemStack itemStack = new ItemStack(Item.getItemById(261));
        this.theSlot = -1;
        try {
            for (int i = 36; i < 45; ++i) {
                this.theSlot = i - 36;
                final Container inventoryContainer = Scaffold.mc.thePlayer.inventoryContainer;
                if (!Container.canAddItemToSlot(Scaffold.mc.thePlayer.inventoryContainer.getSlot(i), itemStack, true) && Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock && Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null && this.isValid(Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()) && Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize != 0) {
                    break;
                }
            }
        }
        catch (Exception ex) {}
        try {
            if (!(Scaffold.mc.thePlayer.inventory.getStackInSlot(this.theSlot).getItem() instanceof ItemBlock)) {
                Class200.tellPlayer("§b[Hanabi]没方块了好兄�?");
                this.set(false);
                return;
            }
        }
        catch (Exception ex2) {
            Class200.tellPlayer("§b[Hanabi]没方块了好兄�?");
            this.set(false);
            return;
        }
        ++this.count;
        if (blockData != null) {
            if (this.isAirBlock(getBlock)) {
                final boolean b = Scaffold.mc.thePlayer.inventory.currentItem != this.theSlot;
                if (b) {
                    Scaffold.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(this.theSlot));
                }
                Scaffold.currentlyHolding = Scaffold.mc.thePlayer.inventory.getStackInSlot(this.theSlot);
                Scaffold.mc.playerController.onPlayerRightClick(Scaffold.mc.thePlayer, Scaffold.mc.theWorld, Scaffold.mc.thePlayer.inventory.getStackInSlot(this.theSlot), blockData.position, blockData.face, getVec3(blockData.position, blockData.face));
                if (b) {
                    Scaffold.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(Scaffold.mc.thePlayer.inventory.currentItem));
                }
                if (this.timer.isDelayComplete(250L)) {
                    this.timer.reset();
                }
                if (this.noswing.getValueState()) {
                    Scaffold.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0APacketAnimation());
                }
                else {
                    Scaffold.mc.thePlayer.swingItem();
                }
            }
        }
        else {
            Scaffold.currentlyHolding = null;
        }
    }
    
    @EventTarget
    public void onPacket(final EventPacket eventPacket) {
        final Packet packet = eventPacket.getPacket();
        if (packet instanceof C09PacketHeldItemChange) {
            final C09PacketHeldItemChange c09PacketHeldItemChange = (C09PacketHeldItemChange)packet;
        }
    }
    
    @EventTarget
    public void onSafe(final EventSafeWalk eventSafeWalk) {
        if (!this.isSneaking) {
            eventSafeWalk.setSafe(true);
        }
    }
    
    @EventTarget
    public void onMove(final EventMove eventMove) {
        if (Scaffold.mode.isCurrentMode("HypixelGlobal")) {
            eventMove.x *= 0.75;
            eventMove.z *= 0.75;
        }
    }
    
    protected void swap(final int n, final int n2) {
        Scaffold.mc.playerController.windowClick(Scaffold.mc.thePlayer.inventoryContainer.windowId, n, n2, 2, (EntityPlayer)Scaffold.mc.thePlayer);
    }
    
    private boolean invCheck() {
        for (int i = 36; i < 45; ++i) {
            if (Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final Item getItem = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
                if (getItem instanceof ItemBlock && this.isValid(getItem)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int getBlockCount() {
        int n = 0;
        for (int i = 0; i < 45; ++i) {
            if (Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack getStack = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item getItem = getStack.getItem();
                if (getStack.getItem() instanceof ItemBlock && this.isValid(getItem)) {
                    n += getStack.stackSize;
                }
            }
        }
        return n;
    }
    
    public boolean isAirBlock(final Block block) {
        return block.getMaterial().isReplaceable() && (!(block instanceof BlockSnow) || block.getBlockBoundsMaxY() <= 0.125);
    }
    
    public int getBiggestBlockSlotInv() {
        int n = -1;
        int stackSize = 0;
        if (this.getBlockCount() == 0) {
            return -1;
        }
        for (int i = 9; i < 36; ++i) {
            if (Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final Item getItem = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
                final ItemStack getStack = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getItem instanceof ItemBlock && this.isValid(getItem) && getStack.stackSize > stackSize) {
                    stackSize = getStack.stackSize;
                    n = i;
                }
            }
        }
        return n;
    }
    
    public int getBiggestBlockSlotHotbar() {
        int n = -1;
        int stackSize = 0;
        if (this.getBlockCount() == 0) {
            return -1;
        }
        for (int i = 36; i < 45; ++i) {
            if (Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final Item getItem = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
                final ItemStack getStack = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getItem instanceof ItemBlock && this.isValid(getItem) && getStack.stackSize > stackSize) {
                    stackSize = getStack.stackSize;
                    n = i;
                }
            }
        }
        return n;
    }
    
    public void getBestBlocks() {
        if (this.getBlockCount() == 0) {
            return;
        }
        final ItemStack itemStack = new ItemStack(Item.getItemById(261));
        final int biggestBlockSlotInv = this.getBiggestBlockSlotInv();
        final int biggestBlockSlotHotbar = this.getBiggestBlockSlotHotbar();
        int n = (this.getBiggestBlockSlotHotbar() > 0) ? this.getBiggestBlockSlotHotbar() : this.getBiggestBlockSlotInv();
        int n2 = 42;
        if (biggestBlockSlotHotbar > 0 && biggestBlockSlotInv > 0 && Scaffold.mc.thePlayer.inventoryContainer.getSlot(biggestBlockSlotInv).getHasStack() && Scaffold.mc.thePlayer.inventoryContainer.getSlot(biggestBlockSlotHotbar).getHasStack() && Scaffold.mc.thePlayer.inventoryContainer.getSlot(biggestBlockSlotHotbar).getStack().stackSize < Scaffold.mc.thePlayer.inventoryContainer.getSlot(biggestBlockSlotInv).getStack().stackSize) {
            n = biggestBlockSlotInv;
        }
        if (this.hotbarContainBlock()) {
            for (int i = 36; i < 45; ++i) {
                if (Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    final Item getItem = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
                    if (getItem instanceof ItemBlock && this.isValid(getItem)) {
                        n2 = i;
                        break;
                    }
                }
            }
        }
        else {
            for (int j = 36; j < 45; ++j) {
                if (!Scaffold.mc.thePlayer.inventoryContainer.getSlot(j).getHasStack()) {
                    n2 = j;
                    break;
                }
            }
        }
        if (Scaffold.mc.thePlayer.inventoryContainer.getSlot(n2).slotNumber != n) {
            this.swap(n, n2 - 36);
            Scaffold.mc.playerController.updateController();
        }
    }
    
    public static Vec3 getVec3(final BlockPos blockPos, final EnumFacing enumFacing) {
        final double n = blockPos.getX() + 0.5;
        final double n2 = blockPos.getY() + 0.5;
        final double n3 = blockPos.getZ() + 0.5;
        double n4 = n + enumFacing.getFrontOffsetX() / 2.0;
        double n5 = n3 + enumFacing.getFrontOffsetZ() / 2.0;
        double n6 = n2 + enumFacing.getFrontOffsetY() / 2.0;
        if (enumFacing == EnumFacing.UP || enumFacing == EnumFacing.DOWN) {
            n4 += randomNumber(0.3, -0.3);
            n5 += randomNumber(0.3, -0.3);
        }
        else {
            n6 += randomNumber(0.3, -0.3);
        }
        if (enumFacing == EnumFacing.WEST || enumFacing == EnumFacing.EAST) {
            n5 += randomNumber(0.3, -0.3);
        }
        if (enumFacing == EnumFacing.SOUTH || enumFacing == EnumFacing.NORTH) {
            n4 += randomNumber(0.3, -0.3);
        }
        return new Vec3(n4, n6, n5);
    }
    
    private boolean isPosSolid(final BlockPos blockPos) {
        final Block getBlock = Scaffold.mc.theWorld.getBlockState(blockPos).getBlock();
        return (getBlock.getMaterial().isSolid() || !getBlock.isTranslucent() || getBlock.isFullCube() || getBlock instanceof BlockLadder || getBlock instanceof BlockCarpet || getBlock instanceof BlockSnow || getBlock instanceof BlockSkull) && !getBlock.getMaterial().isLiquid() && !(getBlock instanceof BlockContainer);
    }
    
    private void ItemSpoof() {
        final ItemStack itemStack = new ItemStack(Item.getItemById(261));
        try {
            int i = 36;
            while (i < 45) {
                final int currentItem = i - 36;
                final Container inventoryContainer = Scaffold.mc.thePlayer.inventoryContainer;
                if (!Container.canAddItemToSlot(Scaffold.mc.thePlayer.inventoryContainer.getSlot(i), itemStack, true) && Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock && Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null && this.isValid(Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()) && Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize != 0) {
                    if (Scaffold.mc.thePlayer.inventory.currentItem != currentItem) {
                        Scaffold.mc.thePlayer.inventory.currentItem = currentItem;
                        Scaffold.mc.playerController.updateController();
                        break;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static float[] getRotations(final BlockPos blockPos, final EnumFacing enumFacing) {
        final double n = blockPos.getX() + 0.5 - Scaffold.mc.thePlayer.posX + enumFacing.getFrontOffsetX() / 2.0;
        final double n2 = blockPos.getZ() + 0.5 - Scaffold.mc.thePlayer.posZ + enumFacing.getFrontOffsetZ() / 2.0;
        final double n3 = Scaffold.mc.thePlayer.posY + Scaffold.mc.thePlayer.getEyeHeight() - (blockPos.getY() + 0.5);
        final double n4 = MathHelper.sqrt_double(n * n + n2 * n2);
        float n5 = (float)(Math.atan2(n2, n) * 180.0 / 3.141592653589793) - 90.0f;
        final float n6 = (float)(Math.atan2(n3, n4) * 180.0 / 3.141592653589793);
        if (n5 < 0.0f) {
            n5 += 360.0f;
        }
        return new float[] { n5, n6 };
    }
    
    private boolean hotbarContainBlock() {
        int i = 36;
        while (i < 45) {
            try {
                final ItemStack getStack = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getStack == null || getStack.getItem() == null || !(getStack.getItem() instanceof ItemBlock) || !this.isValid(getStack.getItem())) {
                    ++i;
                    continue;
                }
                return true;
            }
            catch (Exception ex) {
                continue;
            }
            break;
        }
        return false;
    }
    
    private boolean isValid(final Item item) {
        return item instanceof ItemBlock && !Scaffold.blacklistedBlocks.contains(((ItemBlock)item).getBlock());
    }
    
    public void tower(final String s, final EventPreMotion eventPreMotion) {
        final BlockPos blockPos = new BlockPos(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY - 1.0, Scaffold.mc.thePlayer.posZ);
        final Block getBlock = Scaffold.mc.theWorld.getBlockState(blockPos).getBlock();
        final Scaffold.Class289 blockData = this.getBlockData(blockPos);
        if (!((IKeyBinding)Scaffold.mc.gameSettings.keyBindJump).getPress()) {
            if (Class200.isMoving2() && s.equalsIgnoreCase("Hypixel")) {
                if (Class180.isOnGround(0.76) && !Class180.isOnGround(0.75) && Scaffold.mc.thePlayer.motionY > 0.23 && Scaffold.mc.thePlayer.motionY < 0.25) {
                    Scaffold.mc.thePlayer.motionY = Math.round(Scaffold.mc.thePlayer.posY) - Scaffold.mc.thePlayer.posY;
                }
                if (!Class180.isOnGround(1.0E-4)) {
                    if (Scaffold.mc.thePlayer.motionY > 0.1 && Scaffold.mc.thePlayer.posY >= Math.round(Scaffold.mc.thePlayer.posY) - 1.0E-4 && Scaffold.mc.thePlayer.posY <= Math.round(Scaffold.mc.thePlayer.posY) + 1.0E-4) {
                        Scaffold.mc.thePlayer.motionY = 0.0;
                    }
                }
            }
            return;
        }
        if (s.equalsIgnoreCase("Hypixel")) {
            if (Class200.isMoving2()) {
                if (Class180.isOnGround(0.76) && !Class180.isOnGround(0.75) && Scaffold.mc.thePlayer.motionY > 0.23 && Scaffold.mc.thePlayer.motionY < 0.25) {
                    Scaffold.mc.thePlayer.motionY = Math.round(Scaffold.mc.thePlayer.posY) - Scaffold.mc.thePlayer.posY;
                }
                if (Class180.isOnGround(1.0E-4)) {
                    Scaffold.mc.thePlayer.motionY = 0.41993956416514;
                    final EntityPlayerSP thePlayer = Scaffold.mc.thePlayer;
                    thePlayer.motionX *= 0.9;
                    final EntityPlayerSP thePlayer2 = Scaffold.mc.thePlayer;
                    thePlayer2.motionZ *= 0.9;
                }
                else if (Scaffold.mc.thePlayer.posY >= Math.round(Scaffold.mc.thePlayer.posY) - 1.0E-4 && Scaffold.mc.thePlayer.posY <= Math.round(Scaffold.mc.thePlayer.posY) + 1.0E-4) {
                    Scaffold.mc.thePlayer.motionY = 0.0;
                }
            }
            else {
                Scaffold.mc.thePlayer.motionX = 0.0;
                Scaffold.mc.thePlayer.motionZ = 0.0;
                Scaffold.mc.thePlayer.jumpMovementFactor = 0.0f;
                if (this.isAirBlock(getBlock) && blockData != null) {
                    Scaffold.mc.thePlayer.motionY = 0.4195751556457;
                    final EntityPlayerSP thePlayer3 = Scaffold.mc.thePlayer;
                    thePlayer3.motionX *= 0.75;
                    final EntityPlayerSP thePlayer4 = Scaffold.mc.thePlayer;
                    thePlayer4.motionZ *= 0.75;
                }
            }
        }
    }
    
    public void setSpeed(final String s, final EventPreMotion eventPreMotion) {
        final double motionX = Scaffold.mc.thePlayer.motionX;
        final double motionZ = Scaffold.mc.thePlayer.motionZ;
    }
    
    public double[] getExpandCoords(final double n, final double n2, final double n3, final double n4, final float n5) {
        Block block = Scaffold.mc.theWorld.getBlockState(new BlockPos(n, Scaffold.mc.thePlayer.posY - 1.0, n2)).getBlock();
        double n6 = -999.0;
        double n7 = -999.0;
        double n8 = 0.0;
        final double n9 = 0.8;
        while (!this.isAirBlock(block)) {
            ++n8;
            if (n8 > n9) {
                n8 = n9;
            }
            n6 = n + (n3 * 0.45 * Math.cos(Math.toRadians(n5 + 90.0f)) + n4 * 0.45 * Math.sin(Math.toRadians(n5 + 90.0f))) * n8;
            n7 = n2 + (n3 * 0.45 * Math.sin(Math.toRadians(n5 + 90.0f)) - n4 * 0.45 * Math.cos(Math.toRadians(n5 + 90.0f))) * n8;
            if (n8 == n9) {
                break;
            }
            block = Scaffold.mc.theWorld.getBlockState(new BlockPos(n6, Scaffold.mc.thePlayer.posY - 1.0, n7)).getBlock();
        }
        return new double[] { n6, n7 };
    }
    
    public static double randomNumber(final double n, final double n2) {
        return Math.random() * (n - n2) + n2;
    }
    
    public Scaffold.Class289 getBlockData(BlockPos down) {
        Scaffold.Class289 class289 = null;
        int n = 0;
        while (class289 == null) {
            if (n >= 2) {
                break;
            }
            if (!this.isBlockPosAir(down.add(0, 0, 1))) {
                class289 = new Scaffold.Class289(this, down.add(0, 0, 1), EnumFacing.NORTH, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(0, 0, -1))) {
                class289 = new Scaffold.Class289(this, down.add(0, 0, -1), EnumFacing.SOUTH, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(1, 0, 0))) {
                class289 = new Scaffold.Class289(this, down.add(1, 0, 0), EnumFacing.WEST, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(-1, 0, 0))) {
                class289 = new Scaffold.Class289(this, down.add(-1, 0, 0), EnumFacing.EAST, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(0, -1, 0))) {
                class289 = new Scaffold.Class289(this, down.add(0, -1, 0), EnumFacing.UP, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(0, 1, 0)) && this.isSneaking) {
                class289 = new Scaffold.Class289(this, down.add(0, 1, 0), EnumFacing.DOWN, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(0, 1, 1)) && this.isSneaking) {
                class289 = new Scaffold.Class289(this, down.add(0, 1, 1), EnumFacing.DOWN, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(0, 1, -1)) && this.isSneaking) {
                class289 = new Scaffold.Class289(this, down.add(0, 1, -1), EnumFacing.DOWN, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(1, 1, 0)) && this.isSneaking) {
                class289 = new Scaffold.Class289(this, down.add(1, 1, 0), EnumFacing.DOWN, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(-1, 1, 0)) && this.isSneaking) {
                class289 = new Scaffold.Class289(this, down.add(-1, 1, 0), EnumFacing.DOWN, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(1, 0, 1))) {
                class289 = new Scaffold.Class289(this, down.add(1, 0, 1), EnumFacing.NORTH, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(-1, 0, -1))) {
                class289 = new Scaffold.Class289(this, down.add(-1, 0, -1), EnumFacing.SOUTH, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(1, 0, 1))) {
                class289 = new Scaffold.Class289(this, down.add(1, 0, 1), EnumFacing.WEST, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(-1, 0, -1))) {
                class289 = new Scaffold.Class289(this, down.add(-1, 0, -1), EnumFacing.EAST, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(-1, 0, 1))) {
                class289 = new Scaffold.Class289(this, down.add(-1, 0, 1), EnumFacing.NORTH, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(1, 0, -1))) {
                class289 = new Scaffold.Class289(this, down.add(1, 0, -1), EnumFacing.SOUTH, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(1, 0, -1))) {
                class289 = new Scaffold.Class289(this, down.add(1, 0, -1), EnumFacing.WEST, (Class233)null);
                break;
            }
            if (!this.isBlockPosAir(down.add(-1, 0, 1))) {
                class289 = new Scaffold.Class289(this, down.add(-1, 0, 1), EnumFacing.EAST, (Class233)null);
                break;
            }
            down = down.down();
            ++n;
        }
        return class289;
    }
    
    public boolean isBlockPosAir(final BlockPos blockPos) {
        return this.getBlockByPos(blockPos) == Blocks.air || this.getBlockByPos(blockPos) instanceof BlockLiquid;
    }
    
    public Block getBlockByPos(final BlockPos blockPos) {
        return Scaffold.mc.theWorld.getBlockState(blockPos).getBlock();
    }
    
    public Vec3 getBlockSide(final BlockPos blockPos, final EnumFacing enumFacing) {
        if (enumFacing == EnumFacing.NORTH) {
            return new Vec3((double)blockPos.getX(), (double)blockPos.getY(), blockPos.getZ() - 0.5);
        }
        if (enumFacing == EnumFacing.EAST) {
            return new Vec3(blockPos.getX() + 0.5, (double)blockPos.getY(), (double)blockPos.getZ());
        }
        if (enumFacing == EnumFacing.SOUTH) {
            return new Vec3((double)blockPos.getX(), (double)blockPos.getY(), blockPos.getZ() + 0.5);
        }
        if (enumFacing == EnumFacing.WEST) {
            return new Vec3(blockPos.getX() - 0.5, (double)blockPos.getY(), (double)blockPos.getZ());
        }
        return new Vec3((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
    }
    
    static {
        Scaffold.mode = new Value<String>("Scaffold", "Mode", 0);
        Scaffold.yaw = 999.0f;
        Scaffold.pitch = 999.0f;
        Scaffold.currentlyHolding = null;
    }
}
