package mods.togglesprint.me.jannik.module.modules.render;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.jannik.Jannik;
import mods.togglesprint.me.jannik.events.EventRender;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.module.ModuleManager;
import mods.togglesprint.me.jannik.value.Value;
import mods.togglesprint.me.jannik.value.values.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class ChestEsp
  extends Module
{
  public ChestEsp()
  {
    super("ChestEsp", Category.RENDER);
  }
  
  @EventTarget
  public void onRender(EventRender event)
  {
    boolean hi = Jannik.getModuleManager().getModuleByClass(NameTags.class).isEnabled();
    Jannik.getModuleManager().getModuleByClass(NameTags.class).setEnabled(!hi);
    Jannik.getModuleManager().getModuleByClass(NameTags.class).setEnabled(hi);
    for (TileEntity e : mc.theWorld.loadedTileEntityList)
    {
      if ((Values.chestesp_chest.getBooleanValue()) && ((e instanceof TileEntityChest))) {
        renderChest(e.getPos());
      }
      if ((Values.chestesp_enderchest.getBooleanValue()) && ((e instanceof TileEntityEnderChest))) {
        renderEnderChest(e.getPos());
      }
    }
  }
  
  public static void renderChest(BlockPos blockPos)
  {
    double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX;
    double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY;
    double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
    GL11.glPushMatrix();
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glLineWidth(1.0F);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(true);
    GL11.glColor4d(255.0D, 170.0D, 0.0D, 1.0D);
    RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
    GL11.glPopMatrix();
  }
  
  public static void renderEnderChest(BlockPos blockPos)
  {
    double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX;
    double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY;
    double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
    GL11.glPushMatrix();
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glLineWidth(1.0F);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(true);
    GL11.glColor4d(170.0D, 0.0D, 170.0D, 1.0D);
    RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
    GL11.glPopMatrix();
  }
}
