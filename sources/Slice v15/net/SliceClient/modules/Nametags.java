package net.SliceClient.modules;

import net.SliceClient.Utils.GLUtil;
import net.SliceClient.Utils.Wrapper;
import net.SliceClient.commands.FriendManager;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;

public class Nametags extends Module
{
  public Nametags()
  {
    super("Nametags", Category.RENDER, 16376546);
  }
  
  public void onRender()
  {
    if (getState())
    {
      for (Object o : theWorldplayerEntities)
      {
        EntityPlayer p = (EntityPlayer)o;
        if ((p != Wrapper.mc.func_175606_aa()) && (p.isEntityAlive()))
        {
          Wrapper.mc.getRenderManager();
          double pX = lastTickPosX + (posX - lastTickPosX) * Timer.renderPartialTicks - RenderManager.renderPosX;
          Wrapper.mc.getRenderManager();
          double pY = lastTickPosY + (posY - lastTickPosY) * Timer.renderPartialTicks - RenderManager.renderPosY;
          Wrapper.mc.getRenderManager();
          double pZ = lastTickPosZ + (posZ - lastTickPosZ) * Timer.renderPartialTicks - RenderManager.renderPosZ;
          renderNameTag(p, p.getDisplayName().getUnformattedTextForChat(), pX, pY, pZ);
        }
      }
    }
  }
  
  public void renderNameTag(EntityPlayer entity, String tag, double pX, double pY, double pZ)
  {
    FontRenderer var12 = Minecraft.fontRendererObj;
    pY += 0.9D;
    float var13 = Minecraft.thePlayer.getDistanceToEntity(entity) / 4.0F;
    if (var13 < 1.6F) {
      var13 = 1.6F;
    }
    if ((entity instanceof net.minecraft.entity.EntityLivingBase))
    {
      if ((entity instanceof EntityPlayer))
      {
        double health = Math.ceil(entity.getHealth()) / 2.0D;
        String tag1 = "§0";
        if (health > 0.0D) {
          tag1 = "§4";
        }
        if (health > 2.5D) {
          tag1 = "§c";
        }
        if (health > 4.5D) {
          tag1 = "§6";
        }
        if (health > 7.5D) {
          tag1 = "§a";
        }
        if (FriendManager.isFriend(tag)) {
          tag = tag1 + health + " §r" + tag + " §a[F]";
        } else {
          tag = "§8" + tag1 + "§8" + tag + " §7|§r " + health + " ❤";
        }
      }
      RenderManager renderManager = Wrapper.mc.getRenderManager();
      int color = 16776960;
      float scale = var13 * 2.0F;
      scale /= 100.0F;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)pX, (float)pY + 1.5F, (float)pZ);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-scale, -scale, scale);
      GLUtil.setGLCap(2896, false);
      GLUtil.setGLCap(2929, false);
      Tessellator var14 = Tessellator.getInstance();
      WorldRenderer var15 = var14.getWorldRenderer();
      int width = Minecraft.fontRendererObj.getStringWidth(tag) / 2;
      GLUtil.setGLCap(3042, true);
      GL11.glBlendFunc(770, 771);
      Gui.drawRect(-width - 1, -1, width + 1, fontRendererObjFONT_HEIGHT, 1275068416);
      var12.func_175065_a(tag, -width, 0.0F, 16777215, true);
      GLUtil.revertAllCaps();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
    }
  }
}
