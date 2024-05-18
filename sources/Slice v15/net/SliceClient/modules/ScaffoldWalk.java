package net.SliceClient.modules;

import java.util.ArrayList;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.SliceClient.module.ModuleManager;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;



public class ScaffoldWalk
  extends Module
{
  private transient int delay = 0;
  private boolean safewalkWasActive;
  
  public ScaffoldWalk() {
    super("ScaffoldWalk", Category.PLAYER, 16376546);
  }
  
  public void onToggle()
  {
    if (ModuleManager.getModule(ScaffoldWalk.class).isEnabled())
      ModuleManager.getModule(SafeWalk.class).isEnabled();
  }
  
  public void onUpdate() {
    if (!getState()) {
      return;
    }
    EntityPlayerSP p = Minecraft.thePlayer;
    
    if (delay > 0) {
      delay -= 1;
    }
    if (delay <= 0)
    {
      ArrayList<BlockPos> blockStandOn = getSurroundingBlocks();
      
      ArrayList<BlockPos> collisionBlocks = getCollisionBlocks();
      
      for (BlockPos posCollision : collisionBlocks) {
        IBlockState blockOnCollision = worldObj.getBlockState(posCollision);
        if ((blockOnCollision.getBlock() instanceof BlockAir))
        {
          for (BlockPos posStandOn : blockStandOn) {
            IBlockState blockInReach = worldObj.getBlockState(posStandOn);
            
            if (!(blockInReach.getBlock() instanceof BlockAir))
            {
              if (!posStandOn.equals(posCollision))
              {
                if (posStandOn.offset(p.func_174811_aO()).equals(posCollision))
                {
                  sendQueue.netManager.sendPacket(new C03PacketPlayer.C05PacketPlayerLook(thePlayerrotationYaw, 90.0F, true));
                  
                  if (Minecraft.playerController.func_178890_a(Minecraft.thePlayer, Minecraft.theWorld, inventory.getCurrentItem(), posStandOn.add(0, 0, 0), p.func_174811_aO(), new Vec3(posStandOn.getX(), posStandOn.getY(), posStandOn.getZ()))) {
                    p.swingItem();
                    break;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  

  private ArrayList<BlockPos> getCollisionBlocks()
  {
    ArrayList<BlockPos> collisionBlocks = new ArrayList();
    EntityPlayer p = Minecraft.thePlayer;
    
    BlockPos var1 = new BlockPos(getEntityBoundingBoxminX + 0.1D, getEntityBoundingBoxminY - 0.001D, getEntityBoundingBoxminZ + 0.1D);
    BlockPos var2 = new BlockPos(getEntityBoundingBoxmaxX - 0.1D, getEntityBoundingBoxmaxY + 0.001D, getEntityBoundingBoxmaxZ - 0.1D);
    
    for (int var3 = var1.getX(); var3 <= var2.getX(); var3++) {
      for (int var4 = var1.getY(); var4 <= var2.getY(); var4++) {
        for (int var5 = var1.getZ(); var5 <= var2.getZ(); var5++)
        {
          BlockPos blockPos = new BlockPos(var3, var4, var5);
          IBlockState var7 = worldObj.getBlockState(blockPos);
          try
          {
            if ((var4 > posY - 2.0D) && (var4 <= posY - 1.0D)) {
              collisionBlocks.add(blockPos);
            }
          }
          catch (Throwable localThrowable) {}
        }
      }
    }
    
    return collisionBlocks;
  }
  
  private ArrayList<BlockPos> getSurroundingBlocks() {
    ArrayList<BlockPos> blocksStandOn = new ArrayList();
    EntityPlayer p = Minecraft.thePlayer;
    
    for (int var3 = (int)posX - 1; var3 <= (int)posX + 1; var3++) {
      for (int var4 = (int)posY - 1; var4 <= (int)posY; var4++) {
        for (int var5 = (int)posZ - 1; var5 <= (int)posZ + 1; var5++)
        {
          if ((var3 == Math.floor(posX)) || (var5 == Math.floor(posZ)))
          {
            BlockPos blockPos = new BlockPos(var3, var4, var5);
            try
            {
              if ((var4 > posY - 2.0D) && (var4 <= posY - 1.0D)) {
                blocksStandOn.add(blockPos);
              }
            }
            catch (Throwable localThrowable) {}
          }
        }
      }
    }
    
    return blocksStandOn;
  }
}
