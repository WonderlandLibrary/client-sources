package net.SliceClient.modules;

import java.util.ArrayList;
import net.SliceClient.module.Module;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class Tower extends Module
{
  public Tower()
  {
    super("Tower", net.SliceClient.module.Category.PLAYER, 16376546);
  }
  
  private boolean performJump = false;
  private int delay = 0;
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    EntityPlayer p = Minecraft.thePlayer;
    if (delay > 2) {
      delay -= 1;
    }
    if ((onGround) && (delay <= 2)) {
      performJump = true;
    }
    if ((performJump) && (delay <= 80))
    {
      BlockPos above = new BlockPos(thePlayerposX, thePlayerposY + 2.0D, thePlayerposZ);
      IBlockState aboveState = worldObj.getBlockState(above);
      if ((thePlayeronGround) && (
        ((aboveState.getBlock() instanceof BlockAir)) || ((aboveState.getBlock() instanceof BlockLeavesBase)) || 
        ((aboveState.getBlock() instanceof net.minecraft.block.BlockGlass))))
      {
        thePlayermotionY = 0.2D;
        
        p.setPositionAndUpdate(thePlayerposX, thePlayerposY + 1.0D, thePlayerposZ);
      }
      ArrayList<BlockPos> collisionBlocks = getCollisionBlocks();
      for (BlockPos pos : collisionBlocks)
      {
        IBlockState state = worldObj.getBlockState(pos);
        if ((state.getBlock() instanceof BlockAir))
        {
          thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, 
            thePlayerposY, thePlayerposZ, false));
          thePlayersendQueue.netManager
            .sendPacket(new net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook(0.0F, 90.0F, true));
          Minecraft.playerController.onPlayerRightClick(Minecraft.thePlayer, Minecraft.theWorld, 
            thePlayerinventory.getCurrentItem(), pos.add(0, -1, 0), net.minecraft.util.EnumFacing.UP, 
            new net.minecraft.util.Vec3(pos.getX(), pos.getY(), pos.getZ()));
          Minecraft.thePlayer.swingItem();
          thePlayersendQueue.addToSendQueue(new net.minecraft.network.play.client.C0APacketAnimation());
          
          delay = 1;
        }
      }
    }
  }
  


  public void onEnable()
  {
    Minecraft.rightClickDelayTimer = 0;
    BlockPos pos = new BlockPos(thePlayerposX, thePlayerposY - 0.1D, thePlayerposZ);
    IBlockState state = thePlayerworldObj.getBlockState(pos);
    if (!thePlayeronGround) {
      return;
    }
    if ((state.getBlock() instanceof BlockAir)) {
      gameSettingskeyBindSneak.pressed = true;
    } else {
      gameSettingskeyBindSneak.pressed = false;
      super.onEnable();
    }
  }
  

  public void onDisable()
  {
    Minecraft.rightClickDelayTimer = 6;
    net.minecraft.util.Timer.timerSpeed = 1.0F;
    gameSettingskeyBindSneak.pressed = false;
    super.onDisable();
  }
  
  private ArrayList<BlockPos> getCollisionBlocks()
  {
    ArrayList<BlockPos> collidedBlocks = new ArrayList();
    EntityPlayer p = Minecraft.thePlayer;
    for (int var3 = (int)Math.floor(posX); var3 <= (int)Math.floor(posX); var3++) {
      for (int var4 = (int)posY - 1; var4 <= (int)posY; var4++) {
        for (int var5 = (int)Math.floor(posZ); var5 <= (int)Math.floor(posZ); var5++)
        {
          BlockPos blockPos = new BlockPos(var3, var4, var5);
          try
          {
            if ((var4 > posY - 1.5D) && (var4 <= posY - 1.0D)) {
              collidedBlocks.add(blockPos);
            }
          }
          catch (Throwable localThrowable) {}
        }
      }
    }
    return collidedBlocks;
  }
}
