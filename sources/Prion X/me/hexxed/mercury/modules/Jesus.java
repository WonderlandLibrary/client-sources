package me.hexxed.mercury.modules;

import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Jesus extends Module
{
  public static boolean nextTick;
  private double fallDist;
  public static boolean active;
  
  public Jesus()
  {
    super("Jesus", 36, true, me.hexxed.mercury.modulebase.ModuleCategory.MOVEMENT);
  }
  





  public static boolean isInLiquid()
  {
    boolean inLiquid = false;
    int y = (int)getMinecraftthePlayer.boundingBox.minY;
    for (int x = MathHelper.floor_double(getMinecraftthePlayer.boundingBox.minX); x < 
          MathHelper.floor_double(getMinecraftthePlayer.boundingBox.maxX) + 1; x++) {
      for (int z = MathHelper.floor_double(getMinecraftthePlayer.boundingBox.minZ); z < 
            MathHelper.floor_double(getMinecraftthePlayer.boundingBox.maxZ) + 1; z++) {
        Block block = Util.getBlock(x, y, z);
        if ((block != null) && (!(block instanceof net.minecraft.block.BlockAir))) {
          if (!(block instanceof BlockLiquid))
            return false;
          inLiquid = true;
        }
      }
    }
    return inLiquid;
  }
  
  public static boolean isOnLiquid() {
    boolean onLiquid = false;
    int y = 
      (int)getMinecraftthePlayer.boundingBox.offset(0.0D, -0.01D, 0.0D).minY;
    for (int x = MathHelper.floor_double(getMinecraftthePlayer.boundingBox.minX); x < 
          MathHelper.floor_double(getMinecraftthePlayer.boundingBox.maxX) + 1; x++) {
      for (int z = MathHelper.floor_double(getMinecraftthePlayer.boundingBox.minZ); z < 
            MathHelper.floor_double(getMinecraftthePlayer.boundingBox.maxZ) + 1; z++) {
        Block block = Util.getBlock(x, y, z);
        if ((block != null) && (!(block instanceof net.minecraft.block.BlockAir))) {
          if (!(block instanceof BlockLiquid))
            return false;
          onLiquid = true;
        }
      }
    }
    return onLiquid;
  }
  
  public void onPreMotionUpdate()
  {
    if ((isInLiquid()) && 
      (mc.thePlayer.isInsideOfMaterial(net.minecraft.block.material.Material.air)) && 
      (!mc.thePlayer.isSneaking())) {
      mc.thePlayer.motionY = (0.085D * (mc.thePlayer.isCollidedHorizontally ? 1 : 1));
    }
  }
  
  public void onPacketSend(net.minecraft.network.Packet packet)
  {
    if ((packet instanceof C03PacketPlayer)) {
      C03PacketPlayer prepacket = (C03PacketPlayer)packet;
      if (isOnLiquid()) {
        nextTick = !nextTick;
        if (nextTick) {
          y -= 0.01D;
        }
      }
    }
    if (!ModuleManager.isAntiCheatOn()) return;
    if (((packet instanceof C03PacketPlayer)) && (!ModuleManager.getModByName("Fly").isEnabled()) && (!ModuleManager.getModByName("NoFall").isEnabled())) {
      C03PacketPlayer p = (C03PacketPlayer)packet;
      if (mc.thePlayer.fallDistance > 3.0F) {
        mc.thePlayer.fallDistance = 0.0F;
        field_149474_g = true;
      }
    }
  }
  
  public void onBoundingBox(Block block, BlockPos pos)
  {
    if (getMinecraftthePlayer == null)
      return;
    if (((block instanceof BlockLiquid)) && 
      (!isInLiquid()))
    {
      if (!getMinecraftthePlayer.isSneaking()) {
        if (mc.thePlayer.movementInput.jump) {
          getValuesboundingBox = AxisAlignedBB.fromBounds(pos.getX(), 
            pos.getY(), pos.getZ(), pos.getX() + 1, 
            pos.getY() + 1, pos.getZ() + 1);
        } else {
          getValuesboundingBox = AxisAlignedBB.fromBounds(pos.getX(), 
            pos.getY(), pos.getZ(), pos.getX() + 1, 
            pos.getY() + 1, pos.getZ() + 1);
        }
      }
    }
  }
  

  public boolean isSafe()
  {
    if ((!mc.thePlayer.isInWater()) && 
      (!mc.thePlayer.isInsideOfMaterial(net.minecraft.block.material.Material.lava)) && 
      (!mc.thePlayer.isOnLadder()))
    {
      if ((!mc.thePlayer.getActivePotionEffects().contains(net.minecraft.potion.Potion.blindness)) && (mc.thePlayer.ridingEntity == null))
        return false; } return true;
  }
  



  private Block getBlock(int offset)
  {
    int x = (int)Math.round(mc.thePlayer.posX - 0.5D);
    int y = (int)Math.round(mc.thePlayer.posY - 0.5D);
    int z = (int)Math.round(mc.thePlayer.posZ - 0.5D);
    
    Block block = Util.getBlock(x, y - offset, z);
    return block;
  }
}
