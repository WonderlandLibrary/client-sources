package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.modulebase.Values.SwiftMode;
import me.hexxed.mercury.util.TimeHelper;
import me.hexxed.mercury.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInput;
import org.lwjgl.input.Keyboard;



public class Swift
  extends Module
{
  private int timerDelay;
  private int packetDelay;
  private int groundTimer;
  private TimeHelper time = new TimeHelper();
  private int motionDelay;
  private boolean faster = false;
  private int tick = 0;
  
  public Swift()
  {
    super("Speed", 50, true, ModuleCategory.MOVEMENT);
  }
  
  public void onEnable()
  {
    time.setLastMS();
  }
  
  public void onPreMotionUpdate()
  {
    iceslipperiness = 0.4F;
    packed_iceslipperiness = 0.4F;
    if (mc.thePlayer.onGround) {
      groundTimer += 1;
    } else {
      groundTimer = 0;
    }
    timerDelay += 1;
    mc.timer.timerSpeed = 1.0F;
    mc.thePlayer.getEntityBoundingBox().offset(0.0D, 0.01D, 0.0D);
    if (getValuesswiftmode != Values.SwiftMode.TIMER) {
      return;
    }
    if (shouldSpeedUp()) {
      motionDelay += 1;
      switch (motionDelay) {
      case 1: 
        mc.timer.timerSpeed = 1.0F;
        mc.thePlayer.motionX *= 1.5D;
        mc.thePlayer.motionZ *= 1.5D;
        break;
      case 2: 
        mc.timer.timerSpeed = 1.2F;
        mc.thePlayer.motionX /= 1.3D;
        mc.thePlayer.motionZ /= 1.3D;
        break;
      case 3: 
        mc.timer.timerSpeed = 3.0F;
        mc.thePlayer.motionX *= 1.75D;
        mc.thePlayer.motionZ *= 1.75D;
        break;
      case 4: 
        mc.timer.timerSpeed = 1.2F;
        packetDelay = 1;
        mc.thePlayer.motionX /= 1.8D;
        mc.thePlayer.motionZ /= 1.8D;
        break;
      case 5: 
        mc.timer.timerSpeed = 1.0F;
        motionDelay = 0;
        break;
      default: 
        mc.timer.timerSpeed = 1.0F;
        
        break; }
    } else { mc.thePlayer.motionX /= 1.04D;
      mc.thePlayer.motionZ /= 1.04D;
      mc.timer.timerSpeed = 1.0F;
    }
  }
  




  public void onPostMotionUpdate()
  {
    boolean jesusCheck = !(getBlock(1) instanceof BlockLiquid);
    switch (getValuesswiftmode)
    {
    case COMBAT: 
      
    



















    case SANIC: 
      if ((Keyboard.isKeyDown(17)) || (Keyboard.isKeyDown(31)) || (Keyboard.isKeyDown(30)) || (Keyboard.isKeyDown(32))) {
        if ((mc.thePlayer.onGround) && (!(getBlock(1) instanceof BlockIce)) && (!(getBlock(1) instanceof BlockPackedIce)) && (!Jesus.isOnLiquid()) && (!mc.thePlayer.movementInput.jump) && (groundTimer > 5)) {
          motionDelay += 1;
          motionDelay %= 2;
          if (motionDelay == 1) {
            if (faster) {
              if (mc.thePlayer.movementInput.moveStrafe == 0.0F) {
                mc.thePlayer.motionX *= 5.0D;
                mc.thePlayer.motionZ *= 5.0D;
              } else {
                mc.thePlayer.motionX *= 4.9D;
                mc.thePlayer.motionZ *= 4.9D;
              }
              mc.timer.timerSpeed = 1.3F;
            } else {
              mc.thePlayer.motionX *= 0.01D;
              mc.thePlayer.motionZ *= 0.01D;
              mc.timer.timerSpeed = 1.1F;
            }
            faster = (!faster);
          } else {
            mc.thePlayer.motionX *= 1.0D;
            mc.thePlayer.motionZ *= 1.0D;
            mc.timer.timerSpeed = 0.98F;
          }
        }
        resetTimer();
      }
      

      break;
    
    case TIMER: 
      if ((Keyboard.isKeyDown(17)) || (Keyboard.isKeyDown(31)) || (Keyboard.isKeyDown(30)) || (Keyboard.isKeyDown(32))) {
        if ((mc.thePlayer.onGround) && (!(getBlock(1) instanceof BlockIce)) && (!(getBlock(1) instanceof BlockPackedIce)) && (!Jesus.isOnLiquid()) && (!mc.thePlayer.movementInput.jump)) {
          motionDelay += 1;
          motionDelay %= 2;
          if (motionDelay == 1) {
            if (faster) {
              mc.thePlayer.motionX *= 2.7D;
              mc.thePlayer.motionZ *= 2.7D;
            } else {
              mc.thePlayer.motionX *= 2.5D;
              mc.thePlayer.motionZ *= 2.5D;
            }
            faster = (!faster);
          } else {
            mc.thePlayer.motionX /= 1.5D;
            mc.thePlayer.motionZ /= 1.5D;
          }
          mc.thePlayer.moveStrafing *= 0.0F;
          
          mc.thePlayer.motionY = 1.0E-6D;
        }
        mc.thePlayer.isAirBorne = false;
      }
      if ((timerDelay >= 4) && (!isStandingStill()) && (jesusCheck) && (mc.thePlayer.onGround)) {
        mc.timer.timerSpeed = 1.4F;
        timerDelay = 0;
      } else {
        mc.timer.timerSpeed = 1.0F;
      }
      

      resetTimer();
      break;
    }
    
  }
  


  public void onPacketSend(Packet packet)
  {
    if (getValuesswiftmode == Values.SwiftMode.COMBAT) {
      return;
    }
    if (getValuesswiftmode == Values.SwiftMode.TIMER) {
      if (((packet instanceof C03PacketPlayer)) && 
        (packetDelay >= 1)) {
        setOutboundPacketCancelled(true);
        packetDelay = 0;
      }
      
      return;
    }
    boolean jesusCheck = !(getBlock(1) instanceof BlockLiquid);
    if ((packet instanceof C03PacketPlayer)) {
      packetDelay += 1;
      if (packetDelay < 5) {
        return;
      }
      if ((jesusCheck) && (mc.thePlayer.onGround) && (groundTimer > 4))
        setOutboundPacketCancelled(true);
      packetDelay = 0;
    }
  }
  

  public void onDisable()
  {
    mc.timer.timerSpeed = 1.0F;
    iceslipperiness = 0.89F;
    packed_iceslipperiness = 0.89F;
  }
  
  private Block getBlock(int offset) {
    int x = (int)Math.round(mc.thePlayer.posX - 0.5D);
    int y = (int)Math.round(mc.thePlayer.posY - 0.5D);
    int z = (int)Math.round(mc.thePlayer.posZ - 0.5D);
    
    Block block = Util.getBlock(x, y - offset, z);
    return block;
  }
  
  private boolean isStandingStill() {
    return (Math.abs(mc.thePlayer.motionX) <= 0.01D) && (Math.abs(mc.thePlayer.motionZ) <= 0.01D);
  }
  
  private void resetTimer() {
    if (getValuesonlymotion) { mc.timer.timerSpeed = 1.0F;
    }
  }
  


























  private boolean shouldSpeedUp()
  {
    boolean moving = (mc.thePlayer.movementInput.moveForward != 0.0F) || 
      (mc.thePlayer.movementInput.moveStrafe != 0.0F);
    
    boolean walkable = true;
    











    return (!mc.thePlayer.isInWater()) && 
      (!Jesus.isOnLiquid()) && 
      (!mc.thePlayer.isSneaking()) && (mc.thePlayer.onGround) && 
      (moving) && (walkable);
  }
}
