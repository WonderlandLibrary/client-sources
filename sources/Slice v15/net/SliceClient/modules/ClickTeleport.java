package net.SliceClient.modules;

import net.SliceClient.Utils.BlockUtils;
import net.SliceClient.Utils.Helper;
import net.SliceClient.Utils.PlayerUtils;
import net.SliceClient.Utils.RenderUtil.R3DUtils;
import net.SliceClient.event.EventPreMotionUpdates;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.opengl.GL11;

public class ClickTeleport extends Module
{
  private boolean canTP;
  private int delay;
  private BlockPos endPos;
  
  public ClickTeleport()
  {
    super("ClickTeleport", Category.EXPLOITS, 16376546);
  }
  
  @com.darkmagician6.eventapi.EventTarget
  public void onPreUpdate(EventPreMotionUpdates event)
  {
    if ((canTP) && (org.lwjgl.input.Mouse.isButtonDown(1)) && (!Minecraft.thePlayer.isSneaking()) && (delay == 0) && (mcinGameHasFocus))
    {
      event.setCancelled(true);
      endPos = mcobjectMouseOver.func_178782_a();
      double[] startPos = { thePlayerposX, thePlayerposY, thePlayerposZ };
      Helper.playerUtils();PlayerUtils.blinkToPos(startPos, endPos, 0.0D, new double[] { 0.3D, 0.2D });
      Helper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(endPos.getX() + 0.5D, endPos.getY() - 1.0D, endPos.getZ() + 0.5D, false));
      Minecraft.thePlayer.setPosition(endPos.getX() + 0.5D, endPos.getY() + 1, endPos.getZ() + 0.5D);
      delay = 5;
      event.setCancelled(false);
    }
    if (delay > 0) {
      delay -= 1;
    }
  }
  
  public void onRender()
  {
    if (getState())
    {
      int x = mcobjectMouseOver.func_178782_a().getX();
      int y = mcobjectMouseOver.func_178782_a().getY();
      int z = mcobjectMouseOver.func_178782_a().getZ();
      Helper.blockUtils();Block block1 = BlockUtils.getBlock(x, y, z);
      Helper.blockUtils();Block block2 = BlockUtils.getBlock(x, y + 2, z);
      Helper.blockUtils();Block block3 = BlockUtils.getBlock(x, y + 1, z);
      boolean blockBelow = (block1 instanceof BlockSign) ? false : block1.getMaterial().isSolid();
      boolean blockLevel = (block2 instanceof BlockSign) ? false : block1.getMaterial().isSolid();
      boolean blockAbove = (block3 instanceof BlockSign) ? false : block1.getMaterial().isSolid();
      Helper.blockUtils();
      if ((BlockUtils.getBlock(mcobjectMouseOver.func_178782_a()).getMaterial() != Material.air) && (blockBelow) && (blockLevel) && (blockAbove))
      {
        canTP = true;
        GL11.glPushMatrix();
        Helper.get3DUtils();RenderUtil.R3DUtils.startDrawing();
        mcentityRenderer.setupCameraTransform(net.minecraft.util.Timer.renderPartialTicks, 20);
        GL11.glColor4d(0.0D, 0.9D, 1.0D, 0.3D);
        Helper.get3DUtils();RenderUtil.R3DUtils.drawBoundingBox(new AxisAlignedBB(x - RenderManager.renderPosX, y + 1 - RenderManager.renderPosY, z - RenderManager.renderPosZ, x - RenderManager.renderPosX + 1.0D, y + 1.01D - RenderManager.renderPosY, z - RenderManager.renderPosZ + 1.0D));
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
        Helper.get3DUtils();RenderUtil.R3DUtils.drawOutlinedBox(new AxisAlignedBB(x - RenderManager.renderPosX, y + 1 - RenderManager.renderPosY, z - RenderManager.renderPosZ, x - RenderManager.renderPosX + 1.0D, y + 1.01D - RenderManager.renderPosY, z - RenderManager.renderPosZ + 1.0D));
        Helper.get3DUtils();RenderUtil.R3DUtils.stopDrawing();
        GL11.glPopMatrix();
      }
      else
      {
        canTP = false;
      }
    }
  }
}
