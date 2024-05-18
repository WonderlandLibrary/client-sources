package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.util.ChatColor;
import me.hexxed.mercury.util.Friends;
import me.hexxed.mercury.util.GLUtil;
import net.minecraft.client.Minecraft;
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
  private String nameCol;
  
  public Nametags()
  {
    super("Nametags", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.RENDER);
  }
  
  public void onRender()
  {
    for (Object o : mc.theWorld.playerEntities) {
      EntityPlayer p = (EntityPlayer)o;
      if ((p != mc.func_175606_aa()) && (p.isEntityAlive())) {
        mc.getRenderManager();double pX = lastTickPosX + (posX - lastTickPosX) * mc.timer.renderPartialTicks - RenderManager.renderPosX;
        mc.getRenderManager();double pY = lastTickPosY + (posY - lastTickPosY) * mc.timer.renderPartialTicks - RenderManager.renderPosY;
        mc.getRenderManager();double pZ = lastTickPosZ + (posZ - lastTickPosZ) * mc.timer.renderPartialTicks - RenderManager.renderPosZ;
        
        renderNameTag(p, p.getDisplayName().getUnformattedTextForChat(), pX, pY, pZ);
      }
    }
  }
  
  public void renderNameTag(EntityPlayer entity, String tag, double pX, double pY, double pZ) {
    if ((me.hexxed.mercury.modulebase.ModuleManager.getModByName("Stream").isEnabled()) && (!Friends.isFriend(entity.getName()))) return;
    FontRenderer var12 = mc.fontRendererObj;
    pY += 0.9D;
    float var13 = mc.thePlayer.getDistanceToEntity(entity) / 4.0F;
    if (var13 < 1.6F) {
      var13 = 1.6F;
    }
    if ((entity instanceof net.minecraft.entity.EntityLivingBase)) {
      if ((entity instanceof EntityPlayer)) {
        ChatColor nameCol = ChatColor.RESET;
        if (entity.isInvisible()) {
          nameCol = ChatColor.GOLD;
        } else if (entity.isSneaking()) {
          nameCol = ChatColor.DARK_RED;
        }
        if (Friends.isFriend(entity.getName())) {
          String name = entity.getName();
          nameCol = ChatColor.AQUA;
          tag = tag.replaceAll(name, Friends.getAlias(name));
        }
        tag = nameCol + tag;
      }
      
      double health = Math.ceil(entity.getHealth()) / 2.0D;
      ChatColor healthCol; ChatColor healthCol; if (health < 5.0D) {
        healthCol = ChatColor.RED; } else { ChatColor healthCol;
        if ((health > 5.0D) && (health < 6.5D)) {
          healthCol = ChatColor.YELLOW;
        } else
          healthCol = ChatColor.GREEN;
      }
      tag = tag + " " + healthCol + health;
    }
    

    RenderManager renderManager = mc.getRenderManager();
    int color = 16776960;
    float scale = var13 * 2.0F;
    scale /= 100.0F;
    GL11.glPushMatrix();
    GL11.glTranslatef((float)pX, (float)pY + 1.5F, (float)pZ);
    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
    GL11.glRotatef(-playerViewY, 0.0F, 1.0F, 0.0F);
    GL11.glRotatef(playerViewX, 1.0F, 0.0F, 0.0F);
    GL11.glScalef(-scale, -scale, scale);
    GLUtil.setGLCap(2896, false);
    GLUtil.setGLCap(2929, false);
    Tessellator var14 = Tessellator.getInstance();
    WorldRenderer var15 = var14.getWorldRenderer();
    int width = mc.fontRendererObj.getStringWidth(tag) / 2;
    GLUtil.setGLCap(3042, true);
    GL11.glBlendFunc(770, 771);
    Gui.drawRect(-width - 1, -1, width + 1, mc.fontRendererObj.FONT_HEIGHT, 1275068416);
    var12.func_175065_a(tag, -width, 0.0F, 16777215, true);
    GLUtil.revertAllCaps();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glPopMatrix();
  }
}
