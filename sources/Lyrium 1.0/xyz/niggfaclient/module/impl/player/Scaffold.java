// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.player;

import xyz.niggfaclient.utils.player.InventoryUtils;
import net.minecraft.util.Vec3i;
import net.minecraft.util.Vec3;
import net.minecraft.item.Item;
import net.minecraft.util.MovementInput;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import xyz.niggfaclient.utils.render.RenderUtils;
import org.lwjgl.opengl.GL11;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import xyz.niggfaclient.utils.player.BlockUtils;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import xyz.niggfaclient.utils.other.ProtocolUtils;
import net.minecraft.init.Blocks;
import xyz.niggfaclient.utils.other.ServerUtils;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.block.BlockAir;
import net.minecraft.potion.Potion;
import xyz.niggfaclient.events.impl.MoveEvent;
import xyz.niggfaclient.utils.player.MoveUtils;
import xyz.niggfaclient.notifications.Notification;
import xyz.niggfaclient.notifications.NotificationType;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.module.impl.movement.Speed;
import xyz.niggfaclient.events.impl.Render2DEvent;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.SafeEvent;
import xyz.niggfaclient.eventbus.Listener;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Scaffold", description = "Places blocks under you", cat = Category.PLAYER)
public class Scaffold extends Module
{
    private final EnumProperty<RotationsMode> rotationsMode;
    private final EnumProperty<SprintMode> sprintMode;
    private final EnumProperty<TowerMode> towerMode;
    private final DoubleProperty expandDistance;
    private final Property<Boolean> silentSneak;
    private final Property<Boolean> safeWalk;
    private final Property<Boolean> keepY;
    public final Property<Boolean> autoJump;
    private final EnumProperty<JumpMode> jumpMode;
    private final Property<Boolean> showSpoofCat;
    private final EnumProperty<SpoofMode> spoofMode;
    private final EnumProperty<SwingMode> swingMode;
    private final BlockPos[] blockPositions;
    private final EnumFacing[] facings;
    private int slot;
    private BlockData data;
    private boolean sneaking;
    private double startPosY;
    @EventLink
    private final Listener<SafeEvent> safeEventListener;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    @EventLink
    private final Listener<Render2DEvent> render2DEventListener;
    
    public Scaffold() {
        this.rotationsMode = new EnumProperty<RotationsMode>("Rotations Mode", RotationsMode.None);
        this.sprintMode = new EnumProperty<SprintMode>("Sprint Mode", SprintMode.Normal);
        this.towerMode = new EnumProperty<TowerMode>("Tower Mode", TowerMode.Vanilla);
        this.expandDistance = new DoubleProperty("Expand", 0.0, 0.0, 10.0, 0.01);
        this.silentSneak = new Property<Boolean>("Silent Sneak", false);
        this.safeWalk = new Property<Boolean>("Safe Walk", true);
        this.keepY = new Property<Boolean>("Keep Y", false);
        this.autoJump = new Property<Boolean>("Auto Jump", false);
        this.jumpMode = new EnumProperty<JumpMode>("Jump Mode", JumpMode.Vanilla, this.autoJump::getValue);
        this.showSpoofCat = new Property<Boolean>("Show Spoof Cat..", false);
        this.spoofMode = new EnumProperty<SpoofMode>("Spoof Mode", SpoofMode.Silent, this.showSpoofCat::getValue);
        this.swingMode = new EnumProperty<SwingMode>("Swing Mode", SwingMode.Client, this.showSpoofCat::getValue);
        this.blockPositions = new BlockPos[] { new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };
        this.facings = new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH };
        this.safeEventListener = (e -> {
            if (this.safeWalk.getValue()) {
                e.setCancelled(!ModuleManager.getModule(Speed.class).isEnabled() && !this.keepY.getValue());
            }
            return;
        });
        final boolean canTower;
        BlockPos blockUnder;
        EntityPlayerSP thePlayer;
        EntityPlayerSP thePlayer2;
        EntityPlayerSP thePlayer3;
        EntityPlayerSP thePlayer4;
        EntityPlayerSP thePlayer5;
        EntityPlayerSP thePlayer6;
        float[] calcRots;
        int oldItem;
        int i;
        InventoryPlayer inventory;
        final Object packet;
        final int currentItem;
        int j;
        int k;
        final C08PacketPlayerBlockPlacement packet2;
        int oldItem2;
        int l;
        InventoryPlayer inventory2;
        final Object packet3;
        final int currentItem2;
        InventoryPlayer inventory3;
        final Object packet4;
        final int currentItem3;
        this.motionEventListener = (e -> {
            canTower = (!ModuleManager.getModule(Speed.class).isEnabled() && this.mc.gameSettings.keyBindJump.isKeyDown());
            this.setDisplayName(this.getName() + " §7" + this.rotationsMode.getValue());
            if (this.getBlockCount() <= 0) {
                Client.getInstance().getNotificationManager().add(new Notification("Scaffold", "Not enough blocks", 3500L, NotificationType.ERROR));
                this.toggle();
                return;
            }
            else {
                this.data = null;
                blockUnder = this.getBlockUnder();
                this.data = this.getBlockData(blockUnder);
                if (this.data == null) {
                    this.data = this.getBlockData(blockUnder.offset(EnumFacing.DOWN));
                }
                if (e.isPre()) {
                    if (this.autoJump.getValue()) {
                        switch (this.jumpMode.getValue()) {
                            case Vanilla: {
                                if (MoveUtils.isMovingOnGround()) {
                                    this.mc.thePlayer.motionY = 0.41999998688697815;
                                    break;
                                }
                                else {
                                    break;
                                }
                                break;
                            }
                            case Strafe: {
                                if (MoveUtils.isMoving()) {
                                    if (MoveUtils.isOnGround()) {
                                        this.mc.thePlayer.motionY = 0.41999998688697815;
                                    }
                                    MoveUtils.setSpeed(null, MoveUtils.getSpeed());
                                    break;
                                }
                                else {
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    if (!this.mc.thePlayer.isCollidedHorizontally && MoveUtils.isMoving()) {
                        switch (this.sprintMode.getValue()) {
                            case None: {
                                this.mc.thePlayer.setSprinting(false);
                                thePlayer = this.mc.thePlayer;
                                thePlayer.motionX *= (this.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.6 : 0.7505);
                                thePlayer2 = this.mc.thePlayer;
                                thePlayer2.motionZ *= (this.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.6 : 0.7505);
                                break;
                            }
                            case Slow: {
                                this.mc.thePlayer.setSprinting(false);
                                thePlayer3 = this.mc.thePlayer;
                                thePlayer3.motionX *= 0.6;
                                thePlayer4 = this.mc.thePlayer;
                                thePlayer4.motionZ *= 0.6;
                                break;
                            }
                            case Normal: {
                                this.mc.thePlayer.setSprinting(true);
                                break;
                            }
                            case WatchdogSlow: {
                                this.mc.thePlayer.setSprinting(true);
                                thePlayer5 = this.mc.thePlayer;
                                thePlayer5.motionX *= 0.75;
                                thePlayer6 = this.mc.thePlayer;
                                thePlayer6.motionZ *= 0.75;
                                break;
                            }
                        }
                    }
                    if (this.data != null) {
                        calcRots = this.getCalculatedRotations(this.data);
                        switch (this.rotationsMode.getValue()) {
                            case None: {
                                e.setYaw(this.mc.thePlayer.rotationYaw);
                                e.setPitch(this.mc.thePlayer.rotationPitch);
                                break;
                            }
                            case Static: {
                                switch (this.data.getFacing()) {
                                    case NORTH: {
                                        e.setYaw(0.0f);
                                        break;
                                    }
                                    case SOUTH: {
                                        e.setYaw(180.0f);
                                        break;
                                    }
                                    case WEST: {
                                        e.setYaw(-90.0f);
                                        break;
                                    }
                                    case EAST: {
                                        e.setYaw(90.0f);
                                        break;
                                    }
                                }
                                e.setPitch(canTower ? 90.0f : 80.5f);
                                break;
                            }
                            case Calculated: {
                                e.setYaw(calcRots[0]);
                                e.setPitch(calcRots[1]);
                                break;
                            }
                            case UniversoCraft: {
                                e.setYaw(this.mc.thePlayer.rotationYaw - 180.0f);
                                e.setPitch(canTower ? 90.0f : 75.0f);
                                break;
                            }
                            case Vulcan: {
                                e.setYaw(this.mc.thePlayer.rotationYaw - 180.0f);
                                e.setPitch(canTower ? 90.0f : 86.93f);
                                break;
                            }
                            case Watchdog: {
                                e.setYaw(MoveUtils.getDirection() - 168.0f);
                                e.setPitch(canTower ? 90.0f : ((this.mc.thePlayer.getDiagonalTicks() > 0) ? 83.8f : 81.5f));
                                break;
                            }
                            case BlocksMC: {
                                e.setYaw(calcRots[0]);
                                e.setPitch((canTower && !MoveUtils.isMoving()) ? 90.0f : calcRots[1]);
                                break;
                            }
                        }
                        this.mc.thePlayer.renderYawOffset = e.getYaw();
                        this.mc.thePlayer.rotationYawHead = e.getYaw();
                        this.mc.thePlayer.rotationPitchHead = e.getPitch();
                        if (this.silentSneak.getValue()) {
                            if (this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ)).getBlock() instanceof BlockAir) {
                                if (!this.sneaking) {
                                    this.sneaking = true;
                                    PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                                }
                            }
                            else if (this.sneaking) {
                                this.sneaking = false;
                                PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                            }
                        }
                        oldItem = this.mc.thePlayer.inventory.currentItem;
                        i = 0;
                        while (i < 9) {
                            if (this.mc.thePlayer.inventory.getStackInSlot(i) != null && this.mc.thePlayer.inventory.getStackInSlot(i).stackSize != 0 && this.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                                if (this.spoofMode.getValue() == SpoofMode.Silent) {
                                    // new(net.minecraft.network.play.client.C09PacketHeldItemChange.class)
                                    inventory = this.mc.thePlayer.inventory;
                                    new C09PacketHeldItemChange(inventory.currentItem = currentItem);
                                    PacketUtil.sendPacketNoEvent((Packet)packet);
                                    break;
                                }
                                else {
                                    this.mc.thePlayer.inventory.currentItem = i;
                                    break;
                                }
                            }
                            else {
                                ++i;
                            }
                        }
                        if (!ServerUtils.isOnHypixel()) {
                            if (this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ)).getBlock() == Blocks.air) {
                                if (ProtocolUtils.isOneDotEight()) {
                                    if (this.swingMode.getValue() == SwingMode.Client) {
                                        this.mc.thePlayer.swingItem();
                                    }
                                    else {
                                        PacketUtil.sendPacketNoEvent(new C0APacketAnimation());
                                    }
                                }
                                this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), this.data.getPosition(), this.data.getFacing(), this.data.getHitVec());
                                if (!ProtocolUtils.isOneDotEight()) {
                                    if (this.swingMode.getValue() == SwingMode.Client) {
                                        this.mc.thePlayer.swingItem();
                                    }
                                    else {
                                        PacketUtil.sendPacketNoEvent(new C0APacketAnimation());
                                    }
                                }
                            }
                        }
                    }
                }
                else if (this.data != null) {
                    if (canTower && !ModuleManager.getModule(Speed.class).isEnabled()) {
                        switch (this.towerMode.getValue()) {
                            case Vanilla: {
                                this.mc.thePlayer.motionY = MoveUtils.getJumpHeight();
                                break;
                            }
                            case Vulcan: {
                                if (MoveUtils.isOnGround(0.0625)) {
                                    this.mc.thePlayer.motionY = MoveUtils.getJumpHeight();
                                    for (j = 0; j < 2; ++j) {
                                        PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, this.mc.thePlayer.getCurrentEquippedItem(), 0.0f, 0.0f, 0.0f));
                                    }
                                    break;
                                }
                                else {
                                    break;
                                }
                                break;
                            }
                            case Verus: {
                                this.mc.thePlayer.motionY = MoveUtils.getJumpHeight();
                                for (k = 0; k < 4; ++k) {
                                    new C08PacketPlayerBlockPlacement(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ), 1, this.mc.thePlayer.getCurrentEquippedItem(), 0.0f, 0.94f, 0.0f);
                                    PacketUtil.sendPacketNoEvent(packet2);
                                }
                                break;
                            }
                            case NCP: {
                                if (MoveUtils.isOnGround(0.0626)) {
                                    this.mc.thePlayer.motionY = MoveUtils.getJumpHeight();
                                    break;
                                }
                                else {
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    oldItem2 = this.mc.thePlayer.inventory.currentItem;
                    l = 0;
                    while (l < 9) {
                        if (this.mc.thePlayer.inventory.getStackInSlot(l) != null && this.mc.thePlayer.inventory.getStackInSlot(l).stackSize != 0 && this.mc.thePlayer.inventory.getStackInSlot(l).getItem() instanceof ItemBlock && !BlockUtils.BLACKLISTED_BLOCKS.contains(((ItemBlock)this.mc.thePlayer.inventory.getStackInSlot(l).getItem()).getBlock())) {
                            if (this.spoofMode.getValue() == SpoofMode.Silent) {
                                // new(net.minecraft.network.play.client.C09PacketHeldItemChange.class)
                                inventory2 = this.mc.thePlayer.inventory;
                                new C09PacketHeldItemChange(inventory2.currentItem = currentItem2);
                                PacketUtil.sendPacketNoEvent((Packet)packet3);
                                break;
                            }
                            else {
                                this.mc.thePlayer.inventory.currentItem = l;
                                break;
                            }
                        }
                        else {
                            ++l;
                        }
                    }
                    if (this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ)).getBlock() == Blocks.air) {
                        if (ProtocolUtils.isOneDotEight()) {
                            if (this.swingMode.getValue() == SwingMode.Client) {
                                this.mc.thePlayer.swingItem();
                            }
                            else {
                                PacketUtil.sendPacketNoEvent(new C0APacketAnimation());
                            }
                        }
                        this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), this.data.getPosition(), this.data.getFacing(), this.data.getHitVec());
                        if (!ProtocolUtils.isOneDotEight()) {
                            if (this.swingMode.getValue() == SwingMode.Client) {
                                this.mc.thePlayer.swingItem();
                            }
                            else {
                                PacketUtil.sendPacketNoEvent(new C0APacketAnimation());
                            }
                        }
                    }
                    if (this.spoofMode.getValue() == SpoofMode.Silent) {
                        // new(net.minecraft.network.play.client.C09PacketHeldItemChange.class)
                        inventory3 = this.mc.thePlayer.inventory;
                        new C09PacketHeldItemChange(inventory3.currentItem = currentItem3);
                        PacketUtil.sendPacketNoEvent((Packet)packet4);
                    }
                }
                return;
            }
        });
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.RECEIVE && e.getPacket() instanceof S08PacketPlayerPosLook) {
                Client.getInstance().getNotificationManager().add(new Notification("Lag back", "Disabling scaffold due to a teleport.", 5000L, NotificationType.INFO));
                this.toggle();
            }
            return;
        });
        final ItemStack stack;
        ScaledResolution sr;
        FontRenderer bit;
        String text;
        float width;
        float x;
        float y;
        FontRenderer bit2;
        String text2;
        this.render2DEventListener = (e -> {
            GL11.glPushMatrix();
            RenderUtils.scale();
            stack = this.mc.thePlayer.inventory.getStackInSlot(this.getNeededSlot());
            if (stack != null && stack.getItem() instanceof ItemBlock && this.isValid(stack.getItem())) {
                sr = new ScaledResolution(this.mc);
                bit = this.mc.bit;
                if (this.getBlockCount() == 1) {
                    text = this.getBlockCount() + " block";
                }
                else {
                    text = this.getBlockCount() + " blocks";
                }
                width = (float)bit.getStringWidth(text);
                x = sr.getScaledWidthStatic() / 2.0f - width / 2.0f;
                y = sr.getScaledHeightStatic() / 2.0f;
                bit2 = this.mc.bit;
                if (this.getBlockCount() == 1) {
                    text2 = this.getBlockCount() + " block";
                }
                else {
                    text2 = this.getBlockCount() + " blocks";
                }
                bit2.drawStringWithShadow(text2, x + 5.0f, y + 16.0f, -1);
            }
            GL11.glPopMatrix();
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.slot = this.mc.thePlayer.inventory.currentItem;
        this.data = null;
        this.startPosY = this.mc.thePlayer.posY;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.timer.setTimerSpeed(1.0f);
        if (this.spoofMode.getValue() == SpoofMode.Client) {
            this.mc.thePlayer.inventory.currentItem = this.slot;
        }
        else if (this.slot != this.mc.thePlayer.inventory.currentItem) {
            PacketUtil.sendPacketNoEvent(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
        }
        if (this.silentSneak.getValue() && this.sneaking) {
            this.sneaking = false;
            PacketUtil.sendPacket(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    private BlockPos getBlockUnder() {
        if (this.keepY.getValue() && !Keyboard.isKeyDown(57)) {
            final double[] expand = this.getExpandCoords(Math.min(this.startPosY, this.mc.thePlayer.posY) - 1.0);
            final boolean air = BlockUtils.isAirBlock(BlockUtils.getBlock(this.mc.thePlayer.posX, Math.min(this.startPosY, this.mc.thePlayer.posY) - 1.0, this.mc.thePlayer.posZ));
            return new BlockPos(air ? this.mc.thePlayer.posX : expand[0], Math.min(this.startPosY, this.mc.thePlayer.posY) - 1.0, air ? this.mc.thePlayer.posZ : expand[1]);
        }
        this.startPosY = this.mc.thePlayer.posY;
        final double[] expand2 = this.getExpandCoords(this.mc.thePlayer.posY - 1.0);
        final boolean air2 = BlockUtils.isAirBlock(BlockUtils.getBlock(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ));
        return new BlockPos(air2 ? this.mc.thePlayer.posX : expand2[0], this.mc.thePlayer.posY - 1.0, air2 ? this.mc.thePlayer.posZ : expand2[1]);
    }
    
    private float[] getCalculatedRotations(final BlockData data) {
        final double x = data.getPosition().getX() + 0.5 - this.mc.thePlayer.posX + data.getFacing().getFrontOffsetX() / 2.0;
        final double y = data.getPosition().getY() + 0.45;
        final double z = data.getPosition().getZ() + 0.5 - this.mc.thePlayer.posZ + data.getFacing().getFrontOffsetZ() / 2.0;
        float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(Math.atan2(this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight() - y, Math.sqrt(x * x + z * z)) * 180.0 / 3.141592653589793);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        final float mouseSensitivity = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float totalSens = mouseSensitivity * mouseSensitivity * mouseSensitivity * 1.2f;
        yaw -= yaw % totalSens;
        pitch -= pitch % totalSens * mouseSensitivity;
        return new float[] { yaw, MathHelper.clamp_float(pitch, 76.0f, 81.0f) };
    }
    
    public double[] getExpandCoords(final double y) {
        BlockPos underPos = new BlockPos(this.mc.thePlayer.posX, y, this.mc.thePlayer.posZ);
        Block underBlock = this.mc.theWorld.getBlockState(underPos).getBlock();
        final MovementInput movementInput = this.mc.thePlayer.movementInput;
        final float forward = movementInput.moveForward;
        final float strafe = movementInput.moveStrafe;
        final float yaw = this.mc.thePlayer.rotationYaw;
        double xCalc = -999.0;
        double zCalc = -999.0;
        double dist = 0.0;
        final double expandDist = this.expandDistance.getValue().floatValue();
        while (!BlockUtils.isAirBlock(underBlock)) {
            xCalc = this.mc.thePlayer.posX;
            zCalc = this.mc.thePlayer.posZ;
            ++dist;
            if (dist > expandDist) {
                dist = expandDist;
            }
            xCalc += (forward * 0.45 * MathHelper.cos((float)Math.toRadians(yaw + 90.0f)) + strafe * 0.45 * MathHelper.sin((float)Math.toRadians(yaw + 90.0f))) * dist;
            zCalc += (forward * 0.45 * MathHelper.sin((float)Math.toRadians(yaw + 90.0f)) - strafe * 0.45 * MathHelper.cos((float)Math.toRadians(yaw + 90.0f))) * dist;
            if (dist == expandDist) {
                break;
            }
            underPos = new BlockPos(xCalc, y, zCalc);
            underBlock = this.mc.theWorld.getBlockState(underPos).getBlock();
        }
        return new double[] { xCalc, zCalc };
    }
    
    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock) {
                    if (!BlockUtils.BLACKLISTED_BLOCKS.contains(((ItemBlock)item).getBlock())) {
                        blockCount += is.stackSize;
                    }
                }
            }
        }
        return blockCount;
    }
    
    private int getNeededSlot() {
        for (int i = 0; i < 9; ++i) {
            if (this.mc.thePlayer.inventory.getStackInSlot(i) != null && this.mc.thePlayer.inventory.getStackInSlot(i).stackSize != 0) {
                final Item item = this.mc.thePlayer.inventory.getStackInSlot(i).getItem();
                if (this.isValid(item)) {
                    return i;
                }
            }
        }
        return this.mc.thePlayer.inventory.currentItem;
    }
    
    private boolean isValid(final Item item) {
        if (!(item instanceof ItemBlock)) {
            return false;
        }
        final ItemBlock itemBlock = (ItemBlock)item;
        final Block block = itemBlock.getBlock();
        return !BlockUtils.BLACKLISTED_BLOCKS.contains(block);
    }
    
    private boolean validateDataRange(final BlockData data) {
        final Vec3 hitVec = data.getHitVec();
        final double x = hitVec.xCoord - this.mc.thePlayer.posX;
        final double y = hitVec.yCoord - (this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight());
        final double z = hitVec.zCoord - this.mc.thePlayer.posZ;
        return Math.sqrt(x * x + y * y + z * z) <= 4.0;
    }
    
    private BlockData getBlockData(final BlockPos pos) {
        final BlockPos[] blockPositions = this.blockPositions;
        final EnumFacing[] facings = this.facings;
        for (int i = 0; i < blockPositions.length; ++i) {
            final BlockPos blockPos = pos.add(blockPositions[i]);
            if (InventoryUtils.isValidBlock(this.mc.theWorld.getBlockState(blockPos).getBlock(), false)) {
                final BlockData data = new BlockData(blockPos, facings[i]);
                if (this.validateDataRange(data)) {
                    return data;
                }
            }
        }
        final BlockPos posBelow = new BlockPos(0, -1, 0);
        if (InventoryUtils.isValidBlock(this.mc.theWorld.getBlockState(pos.add(posBelow)).getBlock(), false)) {
            final BlockData data2 = new BlockData(pos.add(posBelow), EnumFacing.UP);
            if (this.validateDataRange(data2)) {
                return data2;
            }
        }
        for (final BlockPos blockPosition : blockPositions) {
            final BlockPos blockPos2 = pos.add(blockPosition);
            for (int j = 0; j < blockPositions.length; ++j) {
                final BlockPos blockPos3 = blockPos2.add(blockPositions[j]);
                if (InventoryUtils.isValidBlock(this.mc.theWorld.getBlockState(blockPos3).getBlock(), false)) {
                    final BlockData data3 = new BlockData(blockPos3, facings[j]);
                    if (this.validateDataRange(data3)) {
                        return data3;
                    }
                }
            }
        }
        for (final BlockPos blockPosition : blockPositions) {
            final BlockPos blockPos2 = pos.add(blockPosition);
            for (final BlockPos position : blockPositions) {
                final BlockPos blockPos4 = blockPos2.add(position);
                for (int k = 0; k < blockPositions.length; ++k) {
                    final BlockPos blockPos5 = blockPos4.add(blockPositions[k]);
                    if (InventoryUtils.isValidBlock(this.mc.theWorld.getBlockState(blockPos5).getBlock(), false)) {
                        final BlockData data4 = new BlockData(blockPos5, facings[k]);
                        if (this.validateDataRange(data4)) {
                            return data4;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    private static class BlockData
    {
        private final BlockPos position;
        private final EnumFacing facing;
        
        public BlockData(final BlockPos pos, final EnumFacing face) {
            this.position = pos;
            this.facing = face;
        }
        
        public BlockPos getPosition() {
            return this.position;
        }
        
        public EnumFacing getFacing() {
            return this.facing;
        }
        
        private Vec3 getHitVec() {
            return new Vec3(this.position).addVector(0.5, 0.5, 0.5).addVector(this.facing.getDirectionVec().getX() * 0.5, this.facing.getDirectionVec().getY() * 0.5, this.facing.getDirectionVec().getZ() * 0.5);
        }
    }
    
    public enum JumpMode
    {
        Vanilla, 
        Strafe;
    }
    
    public enum RotationsMode
    {
        None, 
        Static, 
        Calculated, 
        UniversoCraft, 
        Vulcan, 
        Watchdog, 
        BlocksMC;
    }
    
    public enum SprintMode
    {
        None, 
        Slow, 
        Normal, 
        WatchdogSlow;
    }
    
    public enum TowerMode
    {
        None, 
        Vanilla, 
        Vulcan, 
        Verus, 
        NCP;
    }
    
    public enum SpoofMode
    {
        Client, 
        Silent;
    }
    
    public enum SwingMode
    {
        Client, 
        Silent;
    }
}
