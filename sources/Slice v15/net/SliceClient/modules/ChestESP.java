package net.SliceClient.modules;

import com.darkmagician6.eventapi.EventManager;
import net.SliceClient.Utils.GuiUtils;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class ChestESP
  extends Module
{
  public ChestESP()
  {
    super("StorageESP", Category.RENDER, 16376546);
  }
  
  public void onEnable()
  {
    EventManager.register(this);
    super.onEnable();
  }
  





  public void onDisable()
  {
    EventManager.unregister(this);
    super.onDisable();
  }
  





  public void onRender()
  {
    if (getState())
    {
      for (Object o : theWorldloadedTileEntityList)
      {
        TileEntity tileEntity = (TileEntity)o;
        mc.getRenderManager();double x = tileEntity.getPos().getX() - RenderManager.renderPosX;
        mc.getRenderManager();double y = tileEntity.getPos().getY() - RenderManager.renderPosY;
        mc.getRenderManager();double z = tileEntity.getPos().getZ() - RenderManager.renderPosZ;
        if ((tileEntity instanceof TileEntityFurnace))
        {
          GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1.5F, 1717987071);
          GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1717986864);
        }
        if ((tileEntity instanceof TileEntityHopper))
        {
          GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1.5F, -2004317953);
          GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), -2004318160);
        }
        if ((tileEntity instanceof TileEntityDropper))
        {
          GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1.5F, -2004317953);
          GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), -2004318160);
        }
        if ((tileEntity instanceof TileEntityDispenser))
        {
          GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1.5F, -2004317953);
          GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), -2004318160);
        }
        if ((tileEntity instanceof TileEntityEnderChest))
        {
          GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1.5F, 294134527);
          GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 294134320);
        }
        if ((tileEntity instanceof TileEntityBrewingStand))
        {
          GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1.5F, 288585727);
          GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 288585520);
        }
        if ((tileEntity instanceof TileEntityChest))
        {
          TileEntityChest tileChest = (TileEntityChest)tileEntity;
          Block chest = Minecraft.theWorld.getBlockState(tileChest.getPos()).getBlock();
          Block border = Minecraft.theWorld.getBlockState(new BlockPos(tileChest.getPos().getX(), tileChest.getPos().getY(), tileChest.getPos().getZ() - 1)).getBlock();
          Block border2 = Minecraft.theWorld.getBlockState(new BlockPos(tileChest.getPos().getX(), tileChest.getPos().getY(), tileChest.getPos().getZ() + 1)).getBlock();
          Block border3 = Minecraft.theWorld.getBlockState(new BlockPos(tileChest.getPos().getX() - 1, tileChest.getPos().getY(), tileChest.getPos().getZ())).getBlock();
          Block border4 = Minecraft.theWorld.getBlockState(new BlockPos(tileChest.getPos().getX() + 1, tileChest.getPos().getY(), tileChest.getPos().getZ())).getBlock();
          if ((chest == Blocks.chest) && 
            (border != Blocks.chest)) {
            if (border2 == Blocks.chest)
            {
              GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 2.0D), 1.5F, -2006576641);
              GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 2.0D), -2006576848);
            }
            else if (border4 == Blocks.chest)
            {
              GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 2.0D, y + 1.0D, z + 1.0D), 1.5F, -2006576641);
              GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 2.0D, y + 1.0D, z + 1.0D), -2006576848);
            }
            else if (border4 == Blocks.chest)
            {
              GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1.5F, -2006576641);
              GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), -2006576848);
            }
            else if ((border != Blocks.chest) && (border2 != Blocks.chest) && (border3 != Blocks.chest) && (border4 != Blocks.chest))
            {
              GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1.5F, -2006576641);
              GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), -2006576848);
            }
          }
          if ((chest == Blocks.trapped_chest) && 
            (border != Blocks.trapped_chest)) {
            if (border2 == Blocks.trapped_chest)
            {
              GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 2.0D), 1.5F, 1997603071);
              GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 2.0D), 1997602864);
            }
            else if (border4 == Blocks.trapped_chest)
            {
              GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 2.0D, y + 1.0D, z + 1.0D), 1.5F, 1997603071);
              GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 2.0D, y + 1.0D, z + 1.0D), 1997602864);
            }
            else if (border4 == Blocks.trapped_chest)
            {
              GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1.5F, 1997603071);
              GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1997602864);
            }
            else if ((border != Blocks.trapped_chest) && (border2 != Blocks.trapped_chest) && (border3 != Blocks.trapped_chest) && (border4 != Blocks.trapped_chest))
            {
              GuiUtils.drawBoundingBoxESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1.5F, 1997603071);
              GuiUtils.drawFilledBBESP(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1997602864);
            }
          }
        }
      }
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
  }
}
