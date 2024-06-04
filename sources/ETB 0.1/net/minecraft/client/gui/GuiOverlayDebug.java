package net.minecraft.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.chunk.Chunk;
import optifine.Reflector;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class GuiOverlayDebug extends Gui
{
  private final Minecraft mc;
  private final FontRenderer fontRenderer;
  private static final String __OBFID = "CL_00001956";
  
  public GuiOverlayDebug(Minecraft mc)
  {
    this.mc = mc;
    fontRenderer = fontRendererObj;
  }
  
  public void func_175237_a(ScaledResolution scaledResolutionIn)
  {
    mc.mcProfiler.startSection("debug");
    GlStateManager.pushMatrix();
    func_180798_a();
    func_175239_b(scaledResolutionIn);
    GlStateManager.popMatrix();
    mc.mcProfiler.endSection();
  }
  
  private boolean func_175236_d()
  {
    return (mc.thePlayer.func_175140_cp()) || (mc.gameSettings.field_178879_v);
  }
  
  protected void func_180798_a()
  {
    List var1 = call();
    
    for (int var2 = 0; var2 < var1.size(); var2++)
    {
      String var3 = (String)var1.get(var2);
      
      if (!Strings.isNullOrEmpty(var3))
      {
        int var4 = fontRenderer.FONT_HEIGHT;
        int var5 = fontRenderer.getStringWidth(var3);
        boolean var6 = true;
        int var7 = 2 + var4 * var2;
        drawRect(1.0D, var7 - 1, 2 + var5 + 1, var7 + var4 - 1, -1873784752);
        fontRenderer.drawString(var3, 2, var7, 14737632);
      }
    }
  }
  
  protected void func_175239_b(ScaledResolution p_175239_1_)
  {
    List var2 = func_175238_c();
    
    for (int var3 = 0; var3 < var2.size(); var3++)
    {
      String var4 = (String)var2.get(var3);
      
      if (!Strings.isNullOrEmpty(var4))
      {
        int var5 = fontRenderer.FONT_HEIGHT;
        int var6 = fontRenderer.getStringWidth(var4);
        int var7 = p_175239_1_.getScaledWidth() - 2 - var6;
        int var8 = 2 + var5 * var3;
        drawRect(var7 - 1, var8 - 1, var7 + var6 + 1, var8 + var5 - 1, -1873784752);
        fontRenderer.drawString(var4, var7, var8, 14737632);
      }
    }
  }
  
  protected List call()
  {
    BlockPos var1 = new BlockPos(mc.func_175606_aa().posX, mc.func_175606_aa().getEntityBoundingBox().minY, mc.func_175606_aa().posZ);
    
    if (func_175236_d())
    {
      return Lists.newArrayList(new String[] { "Minecraft 1.8 (" + mc.func_175600_c() + "/" + net.minecraft.client.ClientBrandRetriever.getClientModName() + ")", mc.debug, mc.renderGlobal.getDebugInfoRenders(), mc.renderGlobal.getDebugInfoEntities(), "P: " + mc.effectRenderer.getStatistics() + ". T: " + mc.theWorld.getDebugLoadedEntities(), mc.theWorld.getProviderName(), "", String.format("Chunk-relative: %d %d %d", new Object[] { Integer.valueOf(var1.getX() & 0xF), Integer.valueOf(var1.getY() & 0xF), Integer.valueOf(var1.getZ() & 0xF) }) });
    }
    

    Entity var2 = mc.func_175606_aa();
    EnumFacing var3 = var2.func_174811_aO();
    String var4 = "Invalid";
    
    switch (SwitchEnumFacing.field_178907_a[var3.ordinal()])
    {
    case 1: 
      var4 = "Towards negative Z";
      break;
    
    case 2: 
      var4 = "Towards positive Z";
      break;
    
    case 3: 
      var4 = "Towards negative X";
      break;
    
    case 4: 
      var4 = "Towards positive X";
    }
    
    ArrayList var5 = Lists.newArrayList(new String[] { "Minecraft 1.8 (" + mc.func_175600_c() + "/" + net.minecraft.client.ClientBrandRetriever.getClientModName() + ")", mc.debug, mc.renderGlobal.getDebugInfoRenders(), mc.renderGlobal.getDebugInfoEntities(), "P: " + mc.effectRenderer.getStatistics() + ". T: " + mc.theWorld.getDebugLoadedEntities(), mc.theWorld.getProviderName(), "", String.format("XYZ: %.3f / %.5f / %.3f", new Object[] { Double.valueOf(mc.func_175606_aa().posX), Double.valueOf(mc.func_175606_aa().getEntityBoundingBox().minY), Double.valueOf(mc.func_175606_aa().posZ) }), String.format("Block: %d %d %d", new Object[] { Integer.valueOf(var1.getX()), Integer.valueOf(var1.getY()), Integer.valueOf(var1.getZ()) }), String.format("Chunk: %d %d %d in %d %d %d", new Object[] { Integer.valueOf(var1.getX() & 0xF), Integer.valueOf(var1.getY() & 0xF), Integer.valueOf(var1.getZ() & 0xF), Integer.valueOf(var1.getX() >> 4), Integer.valueOf(var1.getY() >> 4), Integer.valueOf(var1.getZ() >> 4) }), String.format("Facing: %s (%s) (%.1f / %.1f)", new Object[] { var3, var4, Float.valueOf(MathHelper.wrapAngleTo180_float(rotationYaw)), Float.valueOf(MathHelper.wrapAngleTo180_float(rotationPitch)) }) });
    
    if ((mc.theWorld != null) && (mc.theWorld.isBlockLoaded(var1)))
    {
      Chunk var9 = mc.theWorld.getChunkFromBlockCoords(var1);
      var5.add("Biome: " + getBiomemc.theWorld.getWorldChunkManager()).biomeName);
      var5.add("Light: " + var9.setLight(var1, 0) + " (" + var9.getLightFor(net.minecraft.world.EnumSkyBlock.SKY, var1) + " sky, " + var9.getLightFor(net.minecraft.world.EnumSkyBlock.BLOCK, var1) + " block)");
      DifficultyInstance var7 = mc.theWorld.getDifficultyForLocation(var1);
      
      if ((mc.isIntegratedServerRunning()) && (mc.getIntegratedServer() != null))
      {
        net.minecraft.entity.player.EntityPlayerMP var8 = mc.getIntegratedServer().getConfigurationManager().func_177451_a(mc.thePlayer.getUniqueID());
        
        if (var8 != null)
        {
          var7 = worldObj.getDifficultyForLocation(new BlockPos(var8));
        }
      }
      
      var5.add(String.format("Local Difficulty: %.2f (Day %d)", new Object[] { Float.valueOf(var7.func_180168_b()), Long.valueOf(mc.theWorld.getWorldTime() / 24000L) }));
    }
    
    if ((mc.entityRenderer != null) && (mc.entityRenderer.isShaderActive()))
    {
      var5.add("Shader: " + mc.entityRenderer.getShaderGroup().getShaderGroupName());
    }
    
    if ((mc.objectMouseOver != null) && (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) && (mc.objectMouseOver.func_178782_a() != null))
    {
      BlockPos var91 = mc.objectMouseOver.func_178782_a();
      var5.add(String.format("Looking at: %d %d %d", new Object[] { Integer.valueOf(var91.getX()), Integer.valueOf(var91.getY()), Integer.valueOf(var91.getZ()) }));
    }
    
    return var5;
  }
  

  protected List func_175238_c()
  {
    long var1 = Runtime.getRuntime().maxMemory();
    long var3 = Runtime.getRuntime().totalMemory();
    long var5 = Runtime.getRuntime().freeMemory();
    long var7 = var3 - var5;
    ArrayList var9 = Lists.newArrayList(new String[] { String.format("Java: %s %dbit", new Object[] { System.getProperty("java.version"), Integer.valueOf(mc.isJava64bit() ? 64 : 32) }), String.format("Mem: % 2d%% %03d/%03dMB", new Object[] { Long.valueOf(var7 * 100L / var1), Long.valueOf(func_175240_a(var7)), Long.valueOf(func_175240_a(var1)) }), String.format("Allocated: % 2d%% %03dMB", new Object[] { Long.valueOf(var3 * 100L / var1), Long.valueOf(func_175240_a(var3)) }), "", String.format("Display: %dx%d (%s)", new Object[] { Integer.valueOf(Display.getWidth()), Integer.valueOf(Display.getHeight()), GL11.glGetString(7936) }), GL11.glGetString(7937), GL11.glGetString(7938) });
    
    if (Reflector.FMLCommonHandler_getBrandings.exists())
    {
      Object var10 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
      var9.add("");
      var9.addAll((java.util.Collection)Reflector.call(var10, Reflector.FMLCommonHandler_getBrandings, new Object[] { Boolean.valueOf(false) }));
    }
    
    if (func_175236_d())
    {
      return var9;
    }
    

    if ((mc.objectMouseOver != null) && (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) && (mc.objectMouseOver.func_178782_a() != null))
    {
      BlockPos var101 = mc.objectMouseOver.func_178782_a();
      IBlockState var11 = mc.theWorld.getBlockState(var101);
      
      if (mc.theWorld.getWorldType() != net.minecraft.world.WorldType.DEBUG_WORLD)
      {
        var11 = var11.getBlock().getActualState(var11, mc.theWorld, var101);
      }
      
      var9.add("");
      var9.add(String.valueOf(Block.blockRegistry.getNameForObject(var11.getBlock())));
      
      Map.Entry var13;
      String var14;
      for (UnmodifiableIterator var12 = var11.getProperties().entrySet().iterator(); var12.hasNext(); var9.add(((IProperty)var13.getKey()).getName() + ": " + var14))
      {
        var13 = (Map.Entry)var12.next();
        var14 = ((Comparable)var13.getValue()).toString();
        
        if (var13.getValue() == Boolean.TRUE)
        {
          var14 = EnumChatFormatting.GREEN + var14;
        }
        else if (var13.getValue() == Boolean.FALSE)
        {
          var14 = EnumChatFormatting.RED + var14;
        }
      }
    }
    
    return var9;
  }
  

  private static long func_175240_a(long p_175240_0_)
  {
    return p_175240_0_ / 1024L / 1024L;
  }
  
  static final class SwitchEnumFacing
  {
    static final int[] field_178907_a = new int[EnumFacing.values().length];
    private static final String __OBFID = "CL_00001955";
    
    static
    {
      try
      {
        field_178907_a[EnumFacing.NORTH.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError1) {}
      



      try
      {
        field_178907_a[EnumFacing.SOUTH.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError2) {}
      



      try
      {
        field_178907_a[EnumFacing.WEST.ordinal()] = 3;
      }
      catch (NoSuchFieldError localNoSuchFieldError3) {}
      



      try
      {
        field_178907_a[EnumFacing.EAST.ordinal()] = 4;
      }
      catch (NoSuchFieldError localNoSuchFieldError4) {}
    }
    
    SwitchEnumFacing() {}
  }
}
