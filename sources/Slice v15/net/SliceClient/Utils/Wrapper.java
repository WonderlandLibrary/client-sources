package net.SliceClient.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.BlockPos;

public class Wrapper
{
  public static Minecraft mc = ;
  public static FontRenderer fr = Minecraft.fontRendererObj;
  
  public Wrapper() {}
  
  public static EntityPlayerSP getPlayer()
  {
    return Minecraft.thePlayer;
  }
  
  public static String getDirection() {
    return getMinecraft().func_175606_aa().func_174811_aO().name();
  }
  
  public static boolean isMoving(Entity e)
  {
    return (motionX != 0.0D) && (motionZ != 0.0D) && ((motionY != 0.0D) || (motionY > 0.0D));
  }
  

  public static Minecraft getMinecraft()
  {
    return Minecraft.getMinecraft();
  }
  
  public EntityRenderer getEntityRenderer()
  {
    return mcentityRenderer;
  }
  
  public net.minecraft.client.particle.EffectRenderer getEffectRenderer()
  {
    return mceffectRenderer;
  }
  
  public static GameSettings getGameSettings()
  {
    return Minecraft.gameSettings;
  }
  
  public static Block getBlockAtPos(BlockPos inBlockPos) {
    BlockPos currentPos = inBlockPos;
    IBlockState s = Minecraft.theWorld.getBlockState(currentPos);
    return s.getBlock();
  }
  
  public static void sendPacket(Packet p) { Minecraft.getNetHandler().getNetworkManager().sendPacket(p); }
  

  public static void blinkToPos(double[] startPos, BlockPos endPos, double slack)
  {
    double curX = startPos[0];
    double curY = startPos[1];
    double curZ = startPos[2];
    double endX = endPos.getX() + 0.5D;
    double endY = endPos.getY() + 1.0D;
    double endZ = endPos.getZ() + 0.5D;
    
    double distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
    int count = 0;
    while (distance > slack) {
      distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
      
      if (count > 120) {
        break;
      }
      boolean next = false;
      double diffX = curX - endX;
      double diffY = curY - endY;
      double diffZ = curZ - endZ;
      
      double offset = (count & 0x1) == 0 ? 0.4D : 0.1D;
      if (diffX < 0.0D) {
        if (Math.abs(diffX) > offset) {
          curX += offset;
        } else {
          curX += Math.abs(diffX);
        }
      }
      if (diffX > 0.0D) {
        if (Math.abs(diffX) > offset) {
          curX -= offset;
        } else {
          curX -= Math.abs(diffX);
        }
      }
      if (diffY < 0.0D) {
        if (Math.abs(diffY) > 0.25D) {
          curY += 0.25D;
        } else {
          curY += Math.abs(diffY);
        }
      }
      if (diffY > 0.0D) {
        if (Math.abs(diffY) > 0.25D) {
          curY -= 0.25D;
        } else {
          curY -= Math.abs(diffY);
        }
      }
      if (diffZ < 0.0D) {
        if (Math.abs(diffZ) > offset) {
          curZ += offset;
        } else {
          curZ += Math.abs(diffZ);
        }
      }
      if (diffZ > 0.0D) {
        if (Math.abs(diffZ) > offset) {
          curZ -= offset;
        } else {
          curZ -= Math.abs(diffZ);
        }
      }
      Minecraft.getMinecraft();Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(curX, curY, curZ, true));
      count++;
    }
  }
  
  public static net.minecraft.entity.player.InventoryPlayer getInventory()
  {
    return getPlayerinventory;
  }
  
  public static PlayerControllerMP getPlayerController()
  {
    getMinecraft();return Minecraft.playerController;
  }
  
  public static WorldClient getWorld()
  {
    getMinecraft();return Minecraft.theWorld;
  }
  


  public static Block getBlock(BlockPos pos)
  {
    Minecraft.getMinecraft();return Minecraft.theWorld.getBlockState(pos).getBlock();
  }
  
  public static Block getBlockAtPos(EntityPlayer inPlayer, double x, double y, double z) {
    return getBlock(new BlockPos(posX - x, posY - y, posZ - z));
  }
  
  public static boolean isOnLiquid() {
    boolean onLiquid = false;
    if ((getBlockAtPos(Minecraft.thePlayer, 0.30000001192092896D, 0.10000000149011612D, 0.30000001192092896D).getMaterial().isLiquid()) && (getBlockAtPos(Minecraft.thePlayer, -0.30000001192092896D, 0.10000000149011612D, -0.30000001192092896D).getMaterial().isLiquid())) {
      onLiquid = true;
    }
    return onLiquid;
  }
}
