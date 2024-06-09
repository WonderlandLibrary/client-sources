package net.andrewsnetwork.icarus.module.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.command.Command;
import net.andrewsnetwork.icarus.command.CommandManager;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.event.events.BlockRender;
import net.andrewsnetwork.icarus.event.events.RenderIn3D;
import net.andrewsnetwork.icarus.module.Module;
import net.andrewsnetwork.icarus.module.Module.Category;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.andrewsnetwork.icarus.values.ConstrainedValue;
import net.andrewsnetwork.icarus.values.Value;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class BlockESP
  extends Module
{
  private final List blockList = new ArrayList();
  private final List blockCache = new CopyOnWriteArrayList();
  private final Value<Float> range = new ConstrainedValue("blockesp_Reach", "reach", Float.valueOf(128.0F), Float.valueOf(10.0F), Float.valueOf(512.0F), this);
  
  public BlockESP()
  {
    super("BlockESP", -6710887, Module.Category.RENDER);
    setTag("Block ESP");
    
    Icarus.getCommandManager().getCommands()
      .add(new Command("blockesp", "<block-name/id>")
      {
        public boolean isInteger(String string)
        {
          try
          {
            Integer.parseInt(string);
          }
          catch (Exception e)
          {
            return false;
          }
          return true;
        }
        
        public void run(String message)
        {
          String[] arguments = message.split(" ");
          if (arguments.length == 1)
          {
            BlockESP.this.blockList.clear();
            BlockESP.this.blockCache.clear();
            Logger.writeChat("Block list cleared.");
            return;
          }
          String input = message
            .substring((arguments[0] + " ").length());
          Block block = null;
          if (isInteger(input)) {
            block = Block.getBlockById(Integer.parseInt(input));
          } else {
            for (Object o : Block.blockRegistry)
            {
              Block blockk = (Block)o;
              String name = blockk.getLocalizedName()
                .replaceAll("tile.", "")
                .replaceAll(".name", "");
              if (name.toLowerCase().startsWith(input.toLowerCase()))
              {
                block = blockk;
                break;
              }
            }
          }
          if (block == null)
          {
            Logger.writeChat("Invalid block.");
            return;
          }
          if (BlockESP.this.blockList.contains(block))
          {
            Logger.writeChat("No longer searching for [" + block.getLocalizedName() + "]");
            BlockESP.this.blockList.remove(BlockESP.this.blockList.indexOf(block));
            BlockESP.this.blockCache.clear();
          }
          else
          {
            Logger.writeChat("Searching for [" + block.getLocalizedName() + "]");
            BlockESP.this.blockList.add(block);
            BlockESP.this.blockCache.clear();
          }
          mc.renderGlobal.loadRenderers();
        }
      });
  }
  
  public void onEvent(Event e)
  {
    if ((e instanceof RenderIn3D))
    {
      if (!Minecraft.isGuiEnabled()) {
        return;
      }
      RenderIn3D event = (RenderIn3D)e;
      GL11.glDisable(2896);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2929);
      GL11.glEnable(2848);
      GL11.glDepthMask(false);
      GL11.glLineWidth(1.5F);
      
      Iterator var2 = this.blockCache.iterator();
      while (var2.hasNext())
      {
        Vec3 vec3 = (Vec3)var2.next();
        if (mc.thePlayer.getDistance(vec3.xCoord, vec3.yCoord, vec3.zCoord) > ((Float)this.range.getValue()).floatValue())
        {
          this.blockCache.remove(vec3);
        }
        else
        {
          Block block = mc.theWorld.getBlockState(new BlockPos(vec3.xCoord, vec3.yCoord, vec3.zCoord)).getBlock();
          renderBox(block, vec3);
        }
      }
      GL11.glDepthMask(true);
      GL11.glDisable(2848);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(3553);
    }
    else if ((e instanceof BlockRender))
    {
      BlockRender event = (BlockRender)e;
      Vec3 blockPos = new Vec3(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
      if ((this.blockList.contains(event.getBlock())) && (!isCached(blockPos))) {
        this.blockCache.add(blockPos);
      }
    }
  }
  
  private boolean isCached(Vec3 blockPos)
  {
    Iterator var2 = this.blockCache.iterator();
    Vec3 vec3;
    do
    {
      if (!var2.hasNext()) {
        return false;
      }
      vec3 = (Vec3)var2.next();
    } while ((vec3.xCoord != blockPos.xCoord) || (vec3.yCoord != blockPos.yCoord) || (vec3.zCoord != blockPos.zCoord));
    return true;
  }
  
  private int getBlockColor(Block block)
  {
    int color = block.getMaterial().getMaterialMapColor().colorValue;
    switch (Block.getIdFromBlock(block))
    {
    case 14: 
    case 41: 
      color = 16576075;
      break;
    case 16: 
    case 173: 
      color = 3618615;
      break;
    case 21: 
    case 22: 
      color = 1525445;
      break;
    case 49: 
      color = 3944534;
      break;
    case 54: 
      color = 16760576;
      break;
    case 56: 
    case 57: 
    case 116: 
      color = 6155509;
      break;
    case 61: 
    case 62: 
      color = 16658167;
      break;
    case 73: 
    case 74: 
    case 152: 
      color = 16711680;
      break;
    case 129: 
    case 133: 
      color = 1564002;
      break;
    case 130: 
      color = 14614999;
      break;
    case 146: 
      color = 13474867;
    }
    return color == 0 ? 1216104 : color;
  }
  
  private void renderBox(Block block, Vec3 vec3)
  {
    if (this.blockList.contains(block))
    {
      mc.getRenderManager();double x = vec3.xCoord - RenderManager.renderPosX;
      mc.getRenderManager();double y = vec3.yCoord - RenderManager.renderPosY;
      mc.getRenderManager();double z = vec3.zCoord - RenderManager.renderPosZ;
      AxisAlignedBB box = AxisAlignedBB.fromBounds(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);
      int color = getBlockColor(block);
      float red = (color >> 16 & 0xFF) / 255.0F;
      float green = (color >> 8 & 0xFF) / 255.0F;
      float blue = (color & 0xFF) / 255.0F;
      GlStateManager.color(red, green, blue, 0.21F);
      RenderHelper.drawFilledBox(box);
      GlStateManager.color(red, green, blue, 1.0F);
      RenderHelper.drawOutlinedBoundingBox(box);
    }
  }
}
