package me.xatzdevelopments.modules.movement;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventCanInteract;
import me.xatzdevelopments.events.listeners.EventModeChanged;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventMove;
import me.xatzdevelopments.events.listeners.EventReadPacket;
import me.xatzdevelopments.events.listeners.EventRenderGUI;
import me.xatzdevelopments.events.listeners.EventSendPacket;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.util.BlockUtil;
import me.xatzdevelopments.util.MoveUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Scaffold extends Module
{
    public static final List<Block> BLOCK_BLACKLIST;
    public ModeSetting Mode = new ModeSetting("Mode", "NCP", "NCP" );
    public ModeSetting Tower = new ModeSetting("Tower", "NPC", "NCP", "Constant");
    public BooleanSetting AllowSprint = new BooleanSetting("Allow Sprint", true);
    public BooleanSetting Sneak = new BooleanSetting("Sneak", false);
    public NumberSetting SneakLength = new NumberSetting("Sneak Length", 2.0, 1.0, 7.0, 1.0);
    public ModeSetting BlockPicker = new ModeSetting("BlockPicker", "Keep", "Switch", "Keep");
    public ModeSetting Rotations = new ModeSetting("Rotations", "KeepRot", "KeepRot", "Snap" );
    public BooleanSetting OppositeYaw = new BooleanSetting("Opposite Yaw", false);
    public BooleanSetting Jitter = new BooleanSetting("Jitter", false);
    public NumberSetting Timer = new NumberSetting("Timer", 1.0, 0.1, 10.0, 0.1);
    public BooleanSetting SameY = new BooleanSetting("SameY", false);
    public NumberSetting Placedelay = new NumberSetting("Placedelay", 1.0, 0.0, 5.0, 1.0);
    public BooleanSetting Diagonal = new BooleanSetting("Diagonal", true);
    public BooleanSetting Safewalk = new BooleanSetting("Safewalk", true);
    public BooleanSetting MoveDuringTower = new BooleanSetting("MoveDuringTower", true);
    private BlockPos currentPos;
    private EnumFacing currentFacing;
    private int sneakTimer = 0;
    boolean rotated = true;
    boolean wasonground;
    float yaw;
    float pitch;
    double OPosY;
    int ticks = 0;
    int JitterTicks = 0;
    int blockSlot = -1;
    ItemStack itemStack;
    int PlaceDelayInt = 0;
    
    static {
        BLOCK_BLACKLIST = Arrays.asList(Blocks.enchanting_table, Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.anvil, Blocks.sand, Blocks.web, Blocks.torch, Blocks.crafting_table, Blocks.furnace, Blocks.waterlily, Blocks.dispenser, Blocks.stone_pressure_plate, Blocks.wooden_pressure_plate, Blocks.noteblock, Blocks.dropper, Blocks.tnt, Blocks.standing_banner, Blocks.wall_banner, Blocks.redstone_torch);
    }
    
    public Scaffold() {
        super("Scaffold", Keyboard.KEY_N, Category.MOVEMENT, "Builds blocks under you");
        this.addSettings(this.Mode, this.Tower, this.SneakLength, this.Sneak, this.AllowSprint, this.BlockPicker, this.Rotations, this.OppositeYaw, this.Jitter, this.Timer, this.SameY, this.Placedelay, this.Diagonal, this.Safewalk, this.MoveDuringTower);
        this.addonText = this.Mode.getMode();
    }
    
    @Override
    public void onEnable() {
    	this.addonText = this.Mode.getMode();
        this.sneakTimer = 0;
        this.ticks = 0;
        this.JitterTicks = 0;
        this.blockSlot = -1;
        if (this.BlockPicker.getMode() == "Keep") {
            this.blockSlot = BlockUtil.findAutoBlockBlock();
            if (this.blockSlot == -1) {
                return;
            }
            this.itemStack = this.mc.thePlayer.getHeldItem();
            this.itemStack = this.mc.thePlayer.inventoryContainer.getSlot(this.blockSlot - 36).getStack();
            this.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(this.blockSlot - 36));
        }
        this.OPosY = this.mc.thePlayer.posY - 1.0;
    }
    
    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
        final KeyBinding keyBindSneak = this.mc.gameSettings.keyBindSneak;
        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), false);
        this.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
        final KeyBinding keyBindRight = this.mc.gameSettings.keyBindRight;
        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindRight.getKeyCode(), false);
        final KeyBinding keyBindLeft = this.mc.gameSettings.keyBindLeft;
        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindLeft.getKeyCode(), false);
    }
    
    @Override
    public void onEvent(final Event e) {
    	if(e instanceof EventModeChanged) {
    		this.addonText = this.Mode.getMode();
    	}
        if (e instanceof EventUpdate) {
            if (this.mc.thePlayer.ticksExisted == 1) {
                this.toggled = false;
            }
            ++this.ticks;
            ++this.JitterTicks;
            if (this.BlockPicker.getMode() == "Switch") {
                this.blockSlot = -1;
                this.blockSlot = BlockUtil.findAutoBlockBlock();
                if (this.blockSlot == -1) {
                    return;
                }
                this.itemStack = this.mc.thePlayer.getHeldItem();
                this.itemStack = this.mc.thePlayer.inventoryContainer.getSlot(this.blockSlot - 36).getStack();
                this.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(this.blockSlot - 36));
                this.ticks = 0;
            }
            if (this.BlockPicker.getMode() == "Keep") {
                if (this.blockSlot == -1) {
                    return;
                }
                if (this.mc.thePlayer.inventoryContainer.getSlot(this.blockSlot).getStack() == null) {
                    return;
                }
                if (this.mc.thePlayer.inventoryContainer.getSlot(this.blockSlot).getStack().stackSize == 0) {
                    this.onEnable();
                }
            }
            if (this.mc.thePlayer.onGround) {
                this.OPosY = this.mc.thePlayer.posY;
            }
            if ((!MoveUtils.isMoving() || (!this.MoveDuringTower.isEnabled() && this.mc.gameSettings.keyBindJump.getIsKeyPressed())) && this.mc.thePlayer.onGround && this.mc.gameSettings.keyBindJump.getIsKeyPressed() && this.Tower.getMode() == "NCP") {
                this.mc.thePlayer.jump(false);
            }
            
            if (this.Tower.getMode() == "Constant" && this.mc.gameSettings.keyBindJump.getIsKeyPressed() && !MoveUtils.isMoving()) {
                this.mc.thePlayer.motionY = 0.42;
            }
        }
        if (e instanceof EventRenderGUI) {
            int BlockCounter = 0;
            for (int i = 36; i < 45; ++i) {
                final ItemStack itemStack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > 0) {
                    final ItemBlock itemBlock = (ItemBlock)itemStack.getItem();
                    final Block block = itemBlock.getBlock();
                    if (block.isFullCube() && !Scaffold.BLOCK_BLACKLIST.contains(block)) {
                        BlockCounter += itemStack.getStackSize();
                    }
                }
            }
            this.mc.fontRendererObj.drawString(Integer.toString(BlockCounter), (float)(Minecraft.getMinecraft().displayWidth / 4 - this.mc.fontRendererObj.getStringWidth(Integer.toString(BlockCounter)) - 4), (float)(Minecraft.getMinecraft().displayHeight / 4 - 3), (BlockCounter < 15) ? -2873818 : -330690);
            this.mc.fontRendererObj.drawString("", -2.0f, -2.0f, -1);
        }
        if (e instanceof EventMotion) {
            this.mc.timer.timerSpeed = (float)this.Timer.getValue();
            if (this.Jitter.isEnabled()) {
                if (this.mc.thePlayer.moveForward != 0.0f) {
                    if (this.JitterTicks < 1) {
                        final KeyBinding keyBindRight = this.mc.gameSettings.keyBindRight;
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindRight.getKeyCode(), true);
                        final KeyBinding keyBindLeft = this.mc.gameSettings.keyBindLeft;
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindLeft.getKeyCode(), false);
                    }
                    else {
                        final KeyBinding keyBindRight2 = this.mc.gameSettings.keyBindRight;
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindRight.getKeyCode(), false);
                        final KeyBinding keyBindLeft2 = this.mc.gameSettings.keyBindLeft;
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindLeft.getKeyCode(), true);
                    }
                }
                else {
                    final KeyBinding keyBindRight3 = this.mc.gameSettings.keyBindRight;
                    KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindRight.getKeyCode(), false);
                    final KeyBinding keyBindLeft3 = this.mc.gameSettings.keyBindLeft;
                    KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindLeft.getKeyCode(), false);
                }
            }
            if (this.JitterTicks >= 2) {
                this.JitterTicks = 0;
            }
            if (!this.AllowSprint.isEnabled()) {
                this.mc.thePlayer.setSprinting(false);
            }
            final EventMotion event = (EventMotion)e;
            this.rotated = false;
            if (this.Rotations.getMode() == "Snap") {
                this.currentPos = null;
                this.currentFacing = null;
            }
            if (this.sneakTimer > this.SneakLength.getValue()) {
                final KeyBinding keyBindSneak = this.mc.gameSettings.keyBindSneak;
                KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), false);
            }
            else {
                ++this.sneakTimer;
            }
            final BlockPos pos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
            if (this.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
                this.setBlockAndFacing(pos);
                if (this.Sneak.isEnabled() && MoveUtils.isMoving()) {
                    this.sneakTimer = 0;
                    final KeyBinding keyBindSneak2 = this.mc.gameSettings.keyBindSneak;
                    KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), true);
                }
                if (this.currentPos != null && this.currentFacing != null) {
                    final float[] facing = BlockUtil.getDirectionToBlock(this.currentPos.getX(), this.currentPos.getY(), this.currentPos.getZ(), this.currentFacing);
                    this.yaw = facing[0];
                    this.pitch = Math.min(90.0f, facing[1] + 9.0f);
                }
            }
            if (this.currentPos != null && this.currentFacing != null) {
                this.rotated = true;
                event.setPitch(this.pitch);
                mc.thePlayer.rotationPitchHead = event.getPitch();
                if ((this.Rotations.getMode() == "KeepRot" && this.OppositeYaw.isEnabled()) || (this.Rotations.getMode() == "Snap" && this.OppositeYaw.isEnabled())) {
                    if (this.mc.gameSettings.keyBindForward.getIsKeyPressed()) {
                        event.setYaw(this.mc.thePlayer.rotationYaw - 180.0f);
                    }
                    else if (this.mc.gameSettings.keyBindLeft.getIsKeyPressed()) {
                        event.setYaw(this.mc.thePlayer.rotationYaw + 90.0f);
                    }
                    else if (this.mc.gameSettings.keyBindRight.getIsKeyPressed()) {
                        event.setYaw(this.mc.thePlayer.rotationYaw - 90.0f);
                    }
                }
                else {
                    event.setYaw(this.yaw);
                    mc.thePlayer.renderYawOffset = event.getYaw();
                    mc.thePlayer.rotationYawHead = event.getYaw();
                }
            }
        }
        if (e instanceof EventMove && !this.MoveDuringTower.isEnabled() && this.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
            e.setCancelled(true);
        }
        if (e instanceof EventCanInteract) {
            int blockSlot = -1;
            blockSlot = BlockUtil.findAutoBlockBlock();
            if (blockSlot == -1) {
                return;
            }
            final ItemStack itemStack2 = this.mc.thePlayer.inventoryContainer.getSlot(blockSlot).getStack();
            final BlockPos pos2 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.9, this.mc.thePlayer.posZ);
            if (this.mc.theWorld.getBlockState(pos2).getBlock() instanceof BlockAir) {
                ++this.PlaceDelayInt;
                if (this.currentPos != null && this.PlaceDelayInt >= this.Placedelay.getValue()) {
                    if (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, itemStack2, this.currentPos, this.currentFacing, new Vec3(this.currentPos.getX(), this.currentPos.getY(), this.currentPos.getZ()))) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                        this.PlaceDelayInt = 0;
                        if (!this.Sneak.isEnabled()) {
                            final KeyBinding keyBindSneak3 = this.mc.gameSettings.keyBindSneak;
                            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), false);
                        }
                        if ((!MoveUtils.isMoving() || (!this.MoveDuringTower.isEnabled() && this.mc.gameSettings.keyBindJump.getIsKeyPressed())) && this.Tower.getMode() == "NCP" && this.OPosY + 1.0 <= this.mc.thePlayer.posY) {
                            this.mc.thePlayer.motionY = -0.2;
                        }
                    }
                }
                else if (this.Sneak.isEnabled() && MoveUtils.isMoving()) {
                    final KeyBinding keyBindSneak4 = this.mc.gameSettings.keyBindSneak;
                    KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), true);
                }
            }
            else {
                this.PlaceDelayInt = 0;
            }
            if (this.Mode.getMode() == "Switch") {
                this.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
            }
        }
        if (e instanceof EventSendPacket && this.ticks > 2) {
            final Packet p = ((EventSendPacket)e).getPacket();
            if (p instanceof C09PacketHeldItemChange) {
                e.setCancelled(true);
            }
        }
        if (e instanceof EventReadPacket) {
            final Packet p = ((EventReadPacket)e).getPacket();
            if (p instanceof S2FPacketSetSlot) {
                e.setCancelled(true);
            }
        }
    }
    
    private void setBlockAndFacing(final BlockPos var1) {
        if (this.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air && !this.SameY.isEnabled()) {
            this.currentPos = var1.add(0, -1, 0);
            this.currentFacing = EnumFacing.UP;
            this.PlaceDelayInt = 999;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 0, 0);
            this.currentFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 0, 0);
            this.currentFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, -1);
            this.currentFacing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, 1);
            this.currentFacing = EnumFacing.NORTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, -1, 0);
            this.currentFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, -1, 0);
            this.currentFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, -1, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, -1);
            this.currentFacing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, -1, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, 1);
            this.currentFacing = EnumFacing.NORTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, 1)).getBlock() != Blocks.air && this.Diagonal.isEnabled()) {
            this.currentPos = var1.add(-1, -1, 1);
            this.currentFacing = EnumFacing.NORTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air && this.Diagonal.isEnabled()) {
            this.currentPos = var1.add(-1, 0, -1);
            this.currentFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air && this.Diagonal.isEnabled()) {
            this.currentPos = var1.add(1, 0, 1);
            this.currentFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air && this.Diagonal.isEnabled()) {
            this.currentPos = var1.add(1, 0, -1);
            this.currentFacing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air && this.Diagonal.isEnabled()) {
            this.currentPos = var1.add(1, 0, -1);
            this.currentFacing = EnumFacing.NORTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, -1)).getBlock() != Blocks.air && this.Diagonal.isEnabled()) {
            this.currentPos = var1.add(-1, -1, -1);
            this.currentFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, -1, 1)).getBlock() != Blocks.air && this.Diagonal.isEnabled()) {
            this.currentPos = var1.add(1, -1, 1);
            this.currentFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air && this.Diagonal.isEnabled()) {
            this.currentPos = var1.add(1, -1, -1);
            this.currentFacing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air && this.Diagonal.isEnabled()) {
            this.currentPos = var1.add(1, -1, -1);
            this.currentFacing = EnumFacing.NORTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air && this.Diagonal.isEnabled()) {
            this.currentPos = var1.add(-1, 0, 1);
            this.currentFacing = EnumFacing.NORTH;
        }
        else {
            this.currentPos = null;
            this.currentFacing = null;
        }
    }
}
