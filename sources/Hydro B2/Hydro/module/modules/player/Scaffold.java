package Hydro.module.modules.player;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.event.Event;
import Hydro.event.events.EventMotion;
import Hydro.event.events.EventPacket;
import Hydro.event.events.EventRenderGUI;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.MoveUtils;
import Hydro.util.ScaffoldUtils;
import Hydro.util.Timer;
import Hydro.util.font.FontUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Scaffold extends Module {
    public static boolean swing;
  //  public static boolean downWards;
    public static boolean tower = false;

    int lastItem = -1;
    boolean down = false;
    Timer downwardsTimer, towerTimer, slowDownTimer, timer;
    public static BlockCache blockCache, lastBlockCache;
    static Minecraft mc = Minecraft.getMinecraft();
    private ArrayList<Packet> packets = new ArrayList<Packet>();
    private double yPos;

    public Scaffold() {
        super("Scaffold", Keyboard.KEY_LMENU, true, Category.PLAYER, "Places blocks under you while you walk");

        this.downwardsTimer = new Timer();
        this.towerTimer = new Timer();
        this.slowDownTimer = new Timer();
        this.timer = new Timer();
        Client.instance.settingsManager.rSetting(new Setting("ScaffoldTower", "Tower", this, false));
        Client.instance.settingsManager.rSetting(new Setting("ScaffoldTimer", "Timer Boost", this, 1, 1, 5, false));
    }

    @Override
    public void onEnable() {
    	down = false;
    	tower = Client.instance.settingsManager.getSettingByName("ScaffoldTower").getValBoolean();
        mc.timer.timerSpeed = 1f;
        this.downwardsTimer.reset();
        this.towerTimer.reset();
        this.slowDownTimer.reset();
        this.timer.reset();
        this.packets.clear();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1f;
        this.downwardsTimer.reset();
        this.towerTimer.reset();
        this.slowDownTimer.reset();
        this.timer.reset();
        blockCache = null;
        lastBlockCache = null;
        down = false;
    }

    private boolean placeBlock(final BlockPos pos, final EnumFacing facing) {
        final Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
        		mc.thePlayer.posZ);

        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, facing,
                new Vec3(blockCache.position).addVector(0.5, 0.5, 0.5)
                        .add(new Vec3(blockCache.facing.getDirectionVec()).scale(0.5)))) {
            if (swing) {
            	mc.thePlayer.swingItem();
            } else {
            	mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            }
            return true;
        }
        return false;
    }

    private BlockCache grab() {
        final EnumFacing[] invert = {EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH,
                EnumFacing.EAST, EnumFacing.WEST};
        BlockPos position = new BlockPos(0, 0, 0);
        if (mc.thePlayer.isMoving() && !mc.gameSettings.keyBindJump.isKeyDown()) {
            for (double n2 = 0.08D, n3 = 0.0; n3 <= n2; n3 += n2 / (Math.floor(n2) + 1.0)) {
                final BlockPos blockPos2 = new BlockPos(
                		mc.thePlayer.posX - MathHelper.sin(ScaffoldUtils.clampRotation()) * n3,
                		mc.thePlayer.posY - 1.0,
                		mc.thePlayer.posZ + MathHelper.cos(ScaffoldUtils.clampRotation()) * n3);
                final IBlockState blockState = mc.theWorld.getBlockState(blockPos2);
                if (blockState != null && blockState.getBlock() == Blocks.air) {
                    position = blockPos2;
                    break;
                }
            }
            // position = new BlockPos(new
            // BlockPos(this.mc.thePlayer.getPositionVector().xCoord,
            // this.mc.thePlayer.getPositionVector().yCoord,
            // this.mc.thePlayer.getPositionVector().zCoord)).offset(EnumFacing.DOWN);
        } else {
            position = new BlockPos(new BlockPos(mc.thePlayer.getPositionVector().xCoord,
            		mc.thePlayer.getPositionVector().yCoord, mc.thePlayer.getPositionVector().zCoord))
                    .offset(EnumFacing.DOWN);
        }

        if (!(mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)
                && !(mc.theWorld.getBlockState(position).getBlock() instanceof BlockLiquid)) {
            return null;
        }
        EnumFacing[] values;
        for (int length = (values = EnumFacing.values()).length, i = 0; i < length; ++i) {
            final EnumFacing facing = values[i];
            final BlockPos offset = position.offset(facing);
            final IBlockState blockState = mc.theWorld.getBlockState(offset);
            if (!(mc.theWorld.getBlockState(offset).getBlock() instanceof BlockAir)
                    && !(mc.theWorld.getBlockState(position).getBlock() instanceof BlockLiquid)) {
                return new BlockCache(offset, invert[facing.ordinal()], (BlockCache) null);
            }
        }
        final BlockPos[] offsets = {new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1),
                new BlockPos(0, 0, 1)};
        BlockPos[] array;
        for (int length2 = (array = offsets).length, j = 0; j < length2; ++j) {
            final BlockPos offset2 = array[j];
            final BlockPos offsetPos = position.add(offset2.getX(), 0, offset2.getZ());
            final IBlockState blockState2 = mc.theWorld.getBlockState(offsetPos);
            if (mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                EnumFacing[] values2;
                for (int length3 = (values2 = EnumFacing.values()).length, k = 0; k < length3; ++k) {
                    final EnumFacing facing2 = values2[k];
                    final BlockPos offset3 = offsetPos.offset(facing2);
                    final IBlockState blockState3 = mc.theWorld.getBlockState(offset3);
                    if (!(mc.theWorld.getBlockState(offset3).getBlock() instanceof BlockAir)) {
                        return new BlockCache(offset3, invert[facing2.ordinal()], (BlockCache) null);
                    }
                }
            }

        }
        return null;
    }
    
    @Override
    public void onEvent(Event event) {
    	if(event instanceof EventPacket) {
    		if(((EventPacket) event).getPacket() instanceof C03PacketPlayer) {
    			if(((EventPacket) event).isSending()) {
    				Packet<?> packet = ((EventPacket) event).getPacket();
    				
                    C03PacketPlayer C03 = (C03PacketPlayer) packet;

                    if(lastBlockCache != null) {
                        float[] rotations = ScaffoldUtils.getRotations(lastBlockCache.position, lastBlockCache.facing);

                        C03.setPitch(rotations[1]);
                        C03.setYaw(rotations[0]);
                    }else{
                        C03.setPitch(85f);
                    }
    			}
    		}
    	}
    	
    	if(event instanceof EventMotion) {
    		if(lastBlockCache != null) {
    			float[] rotations = ScaffoldUtils.getRotations(lastBlockCache.position, lastBlockCache.facing);
                mc.thePlayer.rotationYawHead = rotations[0];
                mc.thePlayer.rotationPitchHead = rotations[1];
            }
            
            //mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.getRotationYaw(), 85.0f, mc.thePlayer.onGround));

    		mc.thePlayer.setSprinting(false);
    		
            if (lastBlockCache != null) {
                //mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, rotations[0], mc.thePlayer.getRotationPitch(), mc.thePlayer.onGround));
            }

            if (event.isPre()) {
                if (mc.gameSettings.keyBindSneak.isKeyDown() && !down) {
                	mc.thePlayer.setSneaking(false);
                    //mc.gameSettings.keyBindSneak.pressed = false;
                    down = true;
                }

                if (mc.gameSettings.keyBindSneak.isKeyDown() && down) {
                	mc.thePlayer.setSneaking(false);
                    //mc.gameSettings.keyBindSneak.pressed = false;
                    down = false;
                }

                if (ScaffoldUtils.grabBlockSlot() == -1) {
                    return;
                }
                blockCache = this.grab();
                if (blockCache != null) {
                    lastBlockCache = this.grab();
                }
                if (blockCache == null) {
                    return;
                }
            } else {
                if (blockCache == null)
                    return;

                if (mc.gameSettings.keyBindJump.isKeyDown() && !mc.thePlayer.isMoving()
                        && tower) {
                    if (MoveUtils.getJumpEffect() == 0) {
                    	mc.thePlayer.motionY = 0;
                    }

                    mc.thePlayer.motionX = 0.0;
                    mc.thePlayer.motionZ = 0.0;
                    mc.thePlayer.isJumping = false;
                    mc.thePlayer.setJumping(false);

                    if (towerTimer.hasReached(100)) {
                        if (MoveUtils.getJumpEffect() == 0) {
                        	mc.thePlayer.jump();

                            if (slowDownTimer.delay(1500)) {
                            	mc.thePlayer.motionY = -0.28;
                                slowDownTimer.reset();
                            }
                        }
                        towerTimer.reset();
                    }
                } else {
                    towerTimer.reset();
                }

                final int currentSlot = mc.thePlayer.inventory.currentItem;
                final int slot = ScaffoldUtils.grabBlockSlot();
                int time = 30;

                if (MoveUtils.getSpeedEffect() > 0) {
                    time = time / (MoveUtils.getSpeedEffect() * 8);
                }

                if (timer.hasReached(time)) {
                	mc.thePlayer.inventory.currentItem = slot;
                    if (this.placeBlock(blockCache.position, blockCache.facing)) {
                        boolean exists = false;
                        for (int i = 0; i < 9; i++) {
                            if (mc.thePlayer.inventory.mainInventory[i] == mc.thePlayer.inventory.mainInventory[currentSlot]) {
                                exists = true;
                            }
                        }
                        if (exists) {
                        	mc.thePlayer.inventory.currentItem = currentSlot;
                            mc.playerController.updateController();
                            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(currentSlot));
                        }

                        blockCache = null;
                    }
                    timer.reset();
                }
            }
    	}
    	
    	if(event instanceof EventRenderGUI) {
    		ScaledResolution sr = new ScaledResolution(mc);
            int blockCount = getBlockCount();
            Color color = new Color(0, 255, 0);
            if (this.getBlockCount() > 100) {
                color = new Color(0, 255, 0);
            }
            if (this.getBlockCount() < 64) {
                color = new Color(255, 255, 0);
            }
            if (this.getBlockCount() < 32) {
                color = new Color(255, 0, 0);
            }
            
            FontUtil.arrayList.drawStringWithShadow(blockCount + "", (sr.getScaledWidth() / 2 - -10) - mc.fontRendererObj.getStringWidth(blockCount + "") / 2, (sr.getScaledHeight() / 2 + 30) + -21, color.getRGB());
    	}
    }

    private class BlockCache {
        private BlockPos position;
        private EnumFacing facing;

        private BlockCache(final BlockPos position, final EnumFacing facing, BlockCache blockCache) {
            this.position = position;
            this.facing = facing;
        }

        private BlockPos getPosition() {
            return this.position;
        }

        private EnumFacing getFacing() {
            return this.facing;
        }
    }

    public int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && canIItemBePlaced(item)) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }

    private boolean canIItemBePlaced(Item item) {
        if (Item.getIdFromItem(item) == 116)
            return false;
        if (Item.getIdFromItem(item) == 30)
            return false;
        if (Item.getIdFromItem(item) == 31)
            return false;
        if (Item.getIdFromItem(item) == 175)
            return false;
        if (Item.getIdFromItem(item) == 28)
            return false;
        if (Item.getIdFromItem(item) == 27)
            return false;
        if (Item.getIdFromItem(item) == 66)
            return false;
        if (Item.getIdFromItem(item) == 157)
            return false;
        if (Item.getIdFromItem(item) == 31)
            return false;
        if (Item.getIdFromItem(item) == 6)
            return false;
        if (Item.getIdFromItem(item) == 31)
            return false;
        if (Item.getIdFromItem(item) == 32)
            return false;
        if (Item.getIdFromItem(item) == 140)
            return false;
        if (Item.getIdFromItem(item) == 390)
            return false;
        if (Item.getIdFromItem(item) == 37)
            return false;
        if (Item.getIdFromItem(item) == 38)
            return false;
        if (Item.getIdFromItem(item) == 39)
            return false;
        if (Item.getIdFromItem(item) == 40)
            return false;
        if (Item.getIdFromItem(item) == 69)
            return false;
        if (Item.getIdFromItem(item) == 50)
            return false;
        if (Item.getIdFromItem(item) == 75)
            return false;
        if (Item.getIdFromItem(item) == 76)
            return false;
        if (Item.getIdFromItem(item) == 54)
            return false;
        if (Item.getIdFromItem(item) == 130)
            return false;
        if (Item.getIdFromItem(item) == 146)
            return false;
        if (Item.getIdFromItem(item) == 342)
            return false;
        if (Item.getIdFromItem(item) == 12)
            return false;
        if (Item.getIdFromItem(item) == 77)
            return false;
        if (Item.getIdFromItem(item) == 143)
            return false;
        return true;
    }

}
