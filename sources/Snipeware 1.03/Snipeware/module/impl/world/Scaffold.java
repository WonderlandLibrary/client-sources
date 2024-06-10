package Snipeware.module.impl.world;

import javafx.scene.transform.Scale;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.GL11;

import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.events.packet.EventPacketSend;
import Snipeware.events.player.EventCollide;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.events.render.EventModelUpdate;
import Snipeware.events.render.EventRender2D;
import Snipeware.module.Module;
import Snipeware.module.impl.movement.Speed.Redemode;
import Snipeware.module.impl.player.Safewalk;
import Snipeware.util.other.InventoryUtils;
import Snipeware.util.other.Logger;
import Snipeware.util.other.MathUtils;
import Snipeware.util.other.TimeHelper;
import Snipeware.util.player.MovementUtils;
import Snipeware.util.player.Rotation;
import Snipeware.util.player.RotationUtils;
import Snipeware.util.player.setBlockAndFacing;
import Snipeware.util.visual.RenderUtil;
import Snipeware.value.impl.BooleanValue;
import Snipeware.value.impl.ColorValue;
import Snipeware.value.impl.EnumValue;
import Snipeware.value.impl.NumberValue;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;



public class Scaffold extends Module {


private final float[] rotations = new float[2];
private static final Map<Integer, Boolean> glCapMap = new HashMap<>();
private final List<Block> badBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.trapped_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.web, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence);
private BlockData blockData;

private BooleanValue keepsprint = new BooleanValue("Sprint", false);
private BooleanValue Jump = new BooleanValue("Jump", false);
private BooleanValue silient = new BooleanValue("Silent", true);
private BooleanValue tower  = new BooleanValue("Tower", false);
private BooleanValue keeprots = new BooleanValue("Keep Rotations", true);
private BooleanValue Swing = new BooleanValue("Swing", true);
private BooleanValue keepy = new BooleanValue("KeepY", false);
private BooleanValue eagle = new BooleanValue("Eagle", false);
public  BooleanValue blockCountBarProperty = new BooleanValue("Block count", true);
private BooleanValue edge = new BooleanValue("Edge", false);
private BooleanValue raycast = new BooleanValue("Raycast", false);
public NumberValue<Float> TimerBoost = new NumberValue<>("Time Speed", 1.00f, 0.1f, 5f, 0.1f);
public NumberValue<Float> delay = new NumberValue<>("Delay", 50f, 0f, 1000f, 10f);
public NumberValue<Float> eageOffset = new NumberValue<>("Edge Offset", 0.13f, 0f, 5f, 0.1f);
int stage = 0;
public static boolean isPlaceTick = false;
public static boolean stopWalk = false;
private double startY;
public TimeHelper towerTimer = new TimeHelper();
private int count;
private static BlockPos currentPos;
private EnumFacing currentFacing;
private boolean rotated = false;
private final TimeHelper timer = new TimeHelper();
float oldPitch = 0;
private RotationUtils RayCastUtil;
private EnumValue<TowerMode> towerMode  = new EnumValue<>("TowerMode", TowerMode.Hypixel);
private EnumValue<Mode> mode  = new EnumValue<>("Mode", Mode.Normal);
private final TimeHelper Random = new TimeHelper();
private final TimeHelper RotationTimer = new TimeHelper();
private int smothYaw;



public Scaffold() {
    super("Scaffold", 0, ModuleCategory.WORLD);
    this.addValues(mode,towerMode, keepsprint, Jump, silient, blockCountBarProperty, tower, keeprots, Swing, keepy ,eagle, edge, raycast, TimerBoost , delay, eageOffset);
}


private enum TowerMode {
	Hypixel, Packet
	
	}
private enum Mode {
	Normal, Watchdog
	
	}


float yaw = 0;
float pitch = 0;

private boolean isBlockUnder() {
    for (int i = (int) (mc.thePlayer.posY - 1.0); i > 0; --i) {
        BlockPos pos = new BlockPos( mc.thePlayer.posX, i, mc.thePlayer.posZ);
        if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
        return true;
    }
    return false;
}

@Override
public void onEnable() {
    super.onEnable();
    timer.reset();
    slotTimer.reset();
    Random.reset();
    ticks = 0;
    if (mc.thePlayer !=null) {
        startY = mc.thePlayer.posY;
    }
    
}

float lastYaw = 0;

public void fakeJump() {
    mc.thePlayer.isAirBorne = true;
    mc.thePlayer.triggerAchievement(StatList.jumpStat);

}

int ticks = 0;


@Handler
public void aa(EventPacketSend event){
    switch (towerMode.getValueAsString ()) { 
    	case "Hypixel":
    		Packet<?> packet  = event.getPacket();
    		if(!towerTimer.reach(200) && mc.gameSettings.keyBindJump.pressed == true) {
				if(packet instanceof C03PacketPlayer) {
					C03PacketPlayer c04 = (C03PacketPlayer) event.getPacket();
					c04.onGround = false;
					c04.setY(mc.thePlayer.posY + MathUtils.getRandomInRange(0.002, 0.005));
				}
    		}
    	break;
    }

}

@Handler
public void onMotionUpdate(final EventMotionUpdate event) {
	if(mc.thePlayer.onGround && mc.thePlayer.isMoving2()) {
		mc.timer.timerSpeed = TimerBoost.getValue();
	}else {
		mc.timer.timerSpeed = 1f;
	}
	
    setSuffix ("Normal"); 
	 if(mode.getValueAsString() == "Watchdog"){
		   setSuffix ("Watchdog"); 
   	 }else if(mode.getValueAsString() == "Normal") {
   	   setSuffix ("Normal"); 
   	 }
    
   
            int slot = this.getSlot ();
     
            isPlaceTick = keeprots.getValue ().booleanValue () ? blockData != null && slot != -1 : blockData != null && slot != -1 && mc.theWorld.getBlockState ( new BlockPos ( mc.thePlayer ).add ( 0, -1, 0 ) ).getBlock () == Blocks.air;
        
            if(Jump.isEnabled()) {
            if(mc.thePlayer.isMoving2()) {
            	mc.gameSettings.keyBindJump.pressed = true;
 
            	}else {
            		mc.gameSettings.keyBindJump.pressed = false;
            	}
            }
            this.blockData = getBlockData ();
            if (this.blockData == null) {
                return;
            }

          
            if (mc.gameSettings.keyBindJump.isKeyDown () && tower.getValue ().booleanValue () && !mc.thePlayer.isMoving () && !mc.thePlayer.isPotionActive ( Potion.jump )) {
              
                switch (towerMode.getValueAsString ()) { 
                    case "Hypixel":
                        EntityPlayerSP player = mc.thePlayer;
                        if (!MovementUtils.isOnGround (0.8) || mc.thePlayer.onGround && !mc.thePlayer.isMoving()) {
                        	event.setOnGround(false);
                        	player.motionY = 0.42;
                            stage = 1;
                        }
                        if(mc.gameSettings.keyBindJump.pressed == false) {
                        	towerTimer.reset();
                        }
                        if(towerTimer.isDelayComplete(150)) {
                        	event.setOnGround(true);
                        	mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                        }

                    case "Packet":
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.setPosition ( mc.thePlayer.posX, mc.thePlayer.posY + 0.99, mc.thePlayer.posZ );
                            mc.thePlayer.sendQueue.addToSendQueue ( new C03PacketPlayer.C04PacketPlayerPosition ( mc.thePlayer.posX, mc.thePlayer.posY + 0.41999998688698, mc.thePlayer.posZ, false ) );
                            mc.thePlayer.sendQueue.addToSendQueue ( new C03PacketPlayer.C04PacketPlayerPosition ( mc.thePlayer.posX, mc.thePlayer.posY + 0.7531999805212, mc.thePlayer.posZ, false ) );
                        }
                }
            

            } else {
                towerTimer.reset ();
            }

            if (isPlaceTick) {
               Rotation targetRotation = new Rotation ( setBlockAndFacing.BlockUtil.getDirectionToBlock ( blockData.getPosition ().getX (), blockData.getPosition ().getY (), blockData.getPosition ().getZ (), blockData.getFacing () )[0], 79.44f );
                Rotation limitedRotation = setBlockAndFacing.BlockUtil.limitAngleChange ( new Rotation ( yaw, event.getPitch () ), targetRotation, (float) ThreadLocalRandom.current ().nextDouble ( 20, 30 ) );
                yaw = getRotations(blockData.getPosition(), blockData.getFacing())[0];
                pitch = limitedRotation.getPitch ();
                	 if(mode.getValueAsString() == "Watchdog"){
               

                	  		event.setYaw(yaw); 
                	  		
                    		 event.setPitch((float) (87 + MathUtils.getRandomInRange(0, 3)));
                       		 
                	 
                	 }else if(mode.getValueAsString() == "Normal") {
                		 event.setYaw(yaw);
                         event.setPitch(82); 
                	 }
            
            rotated = false;
            currentPos = null;
            currentFacing = null;

            BlockPos pos = new BlockPos ( mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ );
            if (mc.theWorld.getBlockState ( pos ).getBlock () instanceof BlockAir) {
                setBlockAndFacing ( pos );

                if (currentPos != null) {
                    float[] facing = setBlockAndFacing.BlockUtil.getDirectionToBlock ( currentPos.getX (), currentPos.getY (), currentPos.getZ (), currentFacing );

                    float yaw = facing[0] + randomNumber ( 3, -3 );
                    float pitch = Math.min ( 90, facing[1] + 9 + randomNumber ( 3, -3 ) );

                    rotations[0] = yaw;
                    rotations[1] = pitch;

                    rotated = !raycast.getValue ().booleanValue () || rayTrace ( yaw, pitch );


                }
            } else {
            
            
            	    yaw = getRotations(blockData.getPosition(), blockData.getFacing())[0];
                    pitch = limitedRotation.getPitch ();
            	
        
            		if (keeprots.getValue ().booleanValue ()) {

                   	 if(mode.getValueAsString() == "Watchdog"){
                   		 event.setYaw(yaw); 
                   		 if(mc.thePlayer.onGround) {
                   			 event.setPitch((float) (87 + MathUtils.getRandomInRange(0, 3)));
                   		 }
                   	 }else if(mode.getValueAsString() == "Normal") {
                   		 event.setYaw(yaw);
                         event.setPitch(82); 
                   	 }
                  
                    }
            	
            }
            
    }
    mc.thePlayer.rotationYawHead = event.getYaw ();
    mc.thePlayer.renderYawOffset = event.getYaw ();

}


@Handler
public void onMotionUpdate1(final EventMotionUpdate event) {

	if(event.isPre()) {
    if (!this.keepsprint.getValue ().booleanValue ()) {
            mc.thePlayer.setSprinting(false);
           

            mc.gameSettings.keyBindSprint.pressed = false;

    }

    int slot = this.getSlot ();
    double x = mc.thePlayer.posX;
    double z = mc.thePlayer.posZ;
    double forward = MovementInput.moveForward;
    double strafe = MovementInput.moveStrafe;
    float YAW = mc.thePlayer.rotationYaw;
 
    BlockPos pos = new BlockPos ( x, mc.thePlayer.posY - 1, z );
    if (slot != -1 && this.blockData != null) {
        final int currentSlot = mc.thePlayer.inventory.currentItem;
      
                mc.thePlayer.inventory.currentItem = slot;
            if (this.getPlaceBlock ( this.blockData.getPosition (), this.blockData.getFacing () )) {
                mc.thePlayer.sendQueue.addToSendQueue ( new C09PacketHeldItemChange ( currentSlot ) );
            }
      
        if (silient.getValue()) {
            mc.thePlayer.inventory.currentItem = currentSlot;
        }
        switch (towerMode.getValueAsString ()) {
            case "Packet":
                for (int i = 0; i < 9; i++) {
                    if (mc.thePlayer.inventory.getStackInSlot ( i ) == null)
                        continue;
                    if (mc.thePlayer.inventory.getStackInSlot ( i ).getItem () instanceof ItemBlock) {
                        mc.thePlayer.sendQueue.addToSendQueueNoEvent ( new C09PacketHeldItemChange ( mc.thePlayer.inventory.currentItem = i ) );
                    }
                }
                if (currentPos != null) {
                    if (timer.reach ( this.delay.getValue () ) && rotated) {
                        if (mc.thePlayer.getCurrentEquippedItem () != null && mc.thePlayer.getCurrentEquippedItem ().getItem () instanceof ItemBlock) {
                        		mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), this.blockData.blockPos, this.blockData.getFacing(), new Vec3(this.blockData.blockPos.getY(), this.blockData.blockPos.getY(), this.blockData.blockPos.getZ()));
                                timer.reset ();
                                if (Swing.getValue ().booleanValue ()) {
                                    mc.thePlayer.swingItem ();
                                } else {
                                    mc.getNetHandler ().addToSendQueueNoEvent ( new C0APacketAnimation () );
                                }

                            }
                        }
                    }
                }
        }
    }
}


private boolean getPlaceBlock(final BlockPos pos, final EnumFacing facing) {
    final Vec3 eyesPos = new Vec3( mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
    Vec3i data = this.blockData.getFacing().getDirectionVec();
    if (timer.reach( this.delay.getValue() )){
        if (edge.getValue() ? mc.playerController.onPlayerRightClick( mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, facing, getVec3(new BlockData(pos, facing))) && isOnEdgeWithOffset(eageOffset.getValue()) : mc.playerController.onPlayerRightClick( mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, facing, getVec3(new BlockData(pos, facing)))) {
            if (this.Swing.getValue ().booleanValue ()) {
                mc.thePlayer.swingItem();
            } else {
                mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            }

            timer.reset();
            return true;
        }


    }
    return false;
}

private Vec3 getVec3(BlockData data) {
    BlockPos pos = data.getPosition();
    EnumFacing face = data.getFacing();
    double x = (double) pos.getX() + 0.5D;
    double y = (double) pos.getY() + 0.5D;
    double z = (double) pos.getZ() + 0.5D;
    x += (double) face.getFrontOffsetX() / 2.0D;
    z += (double) face.getFrontOffsetZ() / 2.0D;
    y += (double) face.getFrontOffsetY() / 2.0D;
    if (face != EnumFacing.UP && face != EnumFacing.DOWN) {
        y += this.randomNumber(0.49D, 0.5D);
    } else {
        x += this.randomNumber(0.3D, -0.3D);
        z += this.randomNumber(0.3D, -0.3D);
    }

    if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
        z += this.randomNumber(0.3D, -0.3D);
    }

    if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
        x += this.randomNumber(0.3D, -0.3D);
    }

    return new Vec3(x, y, z);
}

private double randomNumber(double max, double min) {
    return Math.random() * (max - min) + min;
}

public static boolean rayTrace(float yaw, float pitch) {
    Vec3 vec3 = Minecraft.getMinecraft().thePlayer.getPositionEyes(1.0f);
    Vec3 vec31 = RotationUtils.getVectorForRotation (new float[]{yaw, pitch});
    Vec3 vec32 = vec3.addVector(vec31.xCoord * 5, vec31.yCoord * 5, vec31.zCoord * 5);


    MovingObjectPosition result = Minecraft.getMinecraft().theWorld.rayTraceBlocks(vec3, vec32, false);


    return result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && currentPos.equals(result.getBlockPos());
}

static Random rng = new Random();

public static int getRandom(final int floor, final int cap) {
    return floor + rng.nextInt(cap - floor + 1);
}

public static Color rainbow(int delay) {
    double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
    rainbowState %= 360;
    return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f);
}

public static float[] getRotations(BlockPos block, EnumFacing face) {
    double x = block.getX() + 0.5 -  Minecraft.getMinecraft().thePlayer.posX;
    double z = block.getZ() + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
    double y = (block.getY() + 0.2);
    double d1 = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() - y;
    double d3 = MathHelper.sqrt_double(x * x + z * z);
    float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
    float pitch = (float) (Math.atan2(d1, d3) * 180.0D / Math.PI);
    if (yaw < 0.0F) {
        yaw += 360f;
    }
    
    return new float[]{yaw, pitch};
}

public void setBlockAndFacing(BlockPos var1) {


    if (mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, -1, 0);
        currentFacing = EnumFacing.UP;
    } else if (mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, 0, 0);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, 0, 0);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {

        currentPos = var1.add(0, 0, -1);
        currentFacing = EnumFacing.SOUTH;

    } else if (mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {

        currentPos = var1.add(0, 0, 1);
        currentFacing = EnumFacing.NORTH;

    } else if (mc.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, 0, -1);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, 0, 1);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, 0, -1);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, 0, 1);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(-1, -1, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, -1, 0);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(1, -1, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, -1, 0);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(0, -1, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, -1, -1);
        currentFacing = EnumFacing.SOUTH;
    } else if (mc.theWorld.getBlockState(var1.add(0, -1, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, -1, 1);
        currentFacing = EnumFacing.NORTH;
    } else if (mc.theWorld.getBlockState(var1.add(-1, -1, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, -1, -1);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(-1, -1, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, -1, 1);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, -1, -1);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(1, -1, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, -1, 1);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(-2, 0, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(-2, 0, 0);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(2, 0, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(2, 0, 0);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(0, 0, -2)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, 0, -2);
        currentFacing = EnumFacing.SOUTH;
    } else if (mc.theWorld.getBlockState(var1.add(0, 0, 2)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, 0, 2);
        currentFacing = EnumFacing.NORTH;
    } else if (mc.theWorld.getBlockState(var1.add(-2, 0, -2)).getBlock() != Blocks.air) {
        currentPos = var1.add(-2, 0, -2);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(-2, 0, 2)).getBlock() != Blocks.air) {
        currentPos = var1.add(-2, 0, 2);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(2, 0, -2)).getBlock() != Blocks.air) {
        currentPos = var1.add(2, 0, -2);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(2, 0, 2)).getBlock() != Blocks.air) {
        currentPos = var1.add(2, 0, 2);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(0, 1, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, 1, 0);
        currentFacing = EnumFacing.DOWN;
    } else if (mc.theWorld.getBlockState(var1.add(-1, 1, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, 1, 0);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(1, 1, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, 1, 0);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(0, 1, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, 1, -1);
        currentFacing = EnumFacing.SOUTH;
    } else if (mc.theWorld.getBlockState(var1.add(0, 1, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, 1, 1);
        currentFacing = EnumFacing.NORTH;
    } else if (mc.theWorld.getBlockState(var1.add(-1, 1, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, 1, -1);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(-1, 1, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, 1, 1);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(1, 1, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, 1, -1);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(1, 1, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, 1, 1);
        currentFacing = EnumFacing.WEST;
    }
}



@Override
public void onDisable() {
    super.onDisable();
    mc.gameSettings.keyBindJump.pressed = false;
    this.setSneaking(false);
    mc.timer.timerSpeed = 1f;
}

private void setSneaking(boolean b) {
    KeyBinding sneakBinding = mc.gameSettings.keyBindSneak;
    mc.gameSettings.keyBindSneak.pressed = b;
}

public BlockData getBlockData() {
    final EnumFacing[] invert = {EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST};
    double yValue = 0;
    /*if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && !mc.gameSettings.keyBindJump.isKeyDown() && blockfly.getValue ().booleanValue ()) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
        yValue -= 0.6;
    }*/
    BlockPos aa = new BlockPos( mc.thePlayer.getPositionVector()).offset(EnumFacing.DOWN).add(0, yValue, 0);
    BlockPos playerpos = aa;	
 
 
    boolean tower = !this.tower.getValue ().booleanValue () && !mc.thePlayer.isMoving();
    if (/*!this.blockfly.getValue ().booleanValue () &&*/ this.keepy.getValue ().booleanValue () && !tower && mc.thePlayer.isMoving()) {
        playerpos = new BlockPos(new Vec3( mc.thePlayer.getPositionVector().xCoord, this.startY, mc.thePlayer.getPositionVector().zCoord)).offset(EnumFacing.DOWN);
    } else {
        this.startY = mc.thePlayer.posY;
    }

    for (EnumFacing facing : EnumFacing.values()) {
        if (playerpos.offset(facing).getBlock().getMaterial() != Material.air) {
            return new BlockData(playerpos.offset(facing), invert[facing.ordinal()]);
        }
    }
    final BlockPos[] addons = {
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, -1),
            new BlockPos(0, 0, 1)};

    for (int length2 = addons.length, j = 0; j < length2; ++j) {
        final BlockPos offsetPos = playerpos.add(addons[j].getX(), 0, addons[j].getZ());
        if (mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
            for (int k = 0; k < EnumFacing.values().length; ++k) {
                if (mc.theWorld.getBlockState(offsetPos.offset(EnumFacing.values()[k])).getBlock().getMaterial() != Material.air) {

                    return new BlockData(offsetPos.offset(EnumFacing.values()[k]), invert[EnumFacing.values()[k].ordinal()]);
                }
            }
        }
    }

    return null;
}

int slotIndex = 0;

private final TimeHelper slotTimer = new TimeHelper();

private int getSlot() {
    ArrayList<Integer> slots = new ArrayList<>();
    for (int k = 0; k < 9; ++k) {
        final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
        if (itemStack != null && this.isValid(itemStack) && itemStack.stackSize >= 2) {
            slots.add(k);
        }
    }
    if (slots.isEmpty()) {
        return -1;
    }

    return slots.get(slotIndex);
}
@Handler
public void a(EventRender2D event) {
    if (blockCountBarProperty.getValue()) {
        ScaledResolution sr = new ScaledResolution(mc);
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack())
                continue;
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            if (!(is.getItem() instanceof ItemBlock) || !InventoryUtils.isValidBlock(((ItemBlock) item).getBlock(), false))
                continue;
            blockCount += is.stackSize;
        }
        int color = new Color(255, 0, 0).getRGB();
        int bgcolor = new Color(1,1,1).getRGB();
        if (blockCount >= 64 && 128 > blockCount) {
            color = new Color(255, 255, 0).getRGB();
        } else if (blockCount >= 128) {
            color = new Color(0, 255, 0).getRGB();
        }

        GlStateManager.pushMatrix();
        mc.fontRendererObj.drawString(Integer.toString(blockCount), (sr.getScaledWidth() >> 1)  - mc.fontRendererObj.getStringWidth(Integer.toString(blockCount)) / 2 + 1, (sr.getScaledHeight() >> 1) - 15, bgcolor);
        mc.fontRendererObj.drawString(Integer.toString(blockCount), (sr.getScaledWidth() >> 1)  - mc.fontRendererObj.getStringWidth(Integer.toString(blockCount)) / 2 - 1, (sr.getScaledHeight() >> 1) - 15, bgcolor);
        mc.fontRendererObj.drawString(Integer.toString(blockCount), (sr.getScaledWidth() >> 1)  - mc.fontRendererObj.getStringWidth(Integer.toString(blockCount)) / 2, (sr.getScaledHeight() >> 1) - 15 + 1, bgcolor);
        mc.fontRendererObj.drawString(Integer.toString(blockCount), (sr.getScaledWidth() >> 1)  - mc.fontRendererObj.getStringWidth(Integer.toString(blockCount)) / 2, (sr.getScaledHeight() >> 1) - 15 - 1, bgcolor);
        mc.fontRendererObj.drawString(Integer.toString(blockCount), (sr.getScaledWidth() >> 1)  - mc.fontRendererObj.getStringWidth(Integer.toString(blockCount)) / 2, (sr.getScaledHeight() >> 1) - 15, color);
        GlStateManager.popMatrix();
    }
}


private boolean isValid(ItemStack itemStack) {
    if (itemStack.getItem() instanceof ItemBlock) {
        boolean isBad = false;

        ItemBlock block = (ItemBlock) itemStack.getItem();
        for (int i = 0; i < this.badBlocks.size(); i++) {
            if (block.getBlock().equals(this.badBlocks.get(i))) {
                isBad = true;
            }
        }

        return !isBad;
    }
    return false;
}

public int getBlockCount() {
    int count = -1;
    for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
        final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
        if (itemStack != null && this.isValid(itemStack) && itemStack.stackSize >= 1) {
            count += itemStack.stackSize;
        }
    }
    return count;
}

public class BlockData {
    private final BlockPos blockPos;
    private final EnumFacing enumFacing;

    public BlockData(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.blockPos = blockPos;
        this.enumFacing = enumFacing;
    }

    public EnumFacing getFacing() {
        return this.enumFacing;
    }

    public BlockPos getPosition() {
        return this.blockPos;
    }
}

private void moveBlocksToHotbar() {
    boolean added = false;
    if (!isHotbarFull()) {
        for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
            if (k > 8 && !added) {
                final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
                if (itemStack != null && this.isValid(itemStack)) {
                    shiftClick(k);
                    added = true;
                }
            }
        }
    }
}

public boolean isHotbarFull() {
    int count = 0;
    for (int k = 0; k < 9; ++k) {
        final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
        if (itemStack != null) {
            count++;
        }
    }
    return count == 8;
}

public float setSmooth(float current, float target, float speed) {
    if (target - current > 0) {
        current -= speed;
    } else {
        current += speed;
    }
    return current;
}

public static void shiftClick(int slot) {
   Minecraft.getMinecraft().playerController.windowClick( Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.getMinecraft().thePlayer );
   Minecraft.getMinecraft().playerController.windowClick( Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.getMinecraft().thePlayer );
}

public boolean isAirBlock(Block block) {
    if (block.getMaterial().isReplaceable()) {
        return !(block instanceof BlockSnow) || !(block.getBlockBoundsMaxY() > 0.125);
    }

    return false;
}

public static int randomNumber(int max, int min) {
    return Math.round(min + (float) Math.random() * ((max - min)));
}


private boolean isOnEdgeWithOffset(double paramDouble) {
    double d1 = mc.thePlayer.posX;
    double d2 = mc.thePlayer.posY;
    double d3 = mc.thePlayer.posZ;
    BlockPos blockPos1 = new BlockPos(d1 - paramDouble, d2 - 0.5D, d3 - paramDouble);
    BlockPos blockPos2 = new BlockPos(d1 - paramDouble, d2 - 0.5D, d3 + paramDouble);
    BlockPos blockPos3 = new BlockPos(d1 + paramDouble, d2 - 0.5D, d3 + paramDouble);
    BlockPos blockPos4 = new BlockPos(d1 + paramDouble, d2 - 0.5D, d3 - paramDouble);
    return (mc.thePlayer.worldObj.getBlockState(blockPos1).getBlock() == Blocks.air && mc.thePlayer.worldObj.getBlockState(blockPos2).getBlock() == Blocks.air && mc.thePlayer.worldObj.getBlockState(blockPos3).getBlock() == Blocks.air && mc.thePlayer.worldObj.getBlockState(blockPos4).getBlock() == Blocks.air);
}
}

