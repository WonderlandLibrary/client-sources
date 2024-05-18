package mods.togglesprint.me.jannik.module.modules.render;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.jannik.events.EventRender;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.utils.EntityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;

public class NameTags
  extends Module
{
  public NameTags()
  {
    super("NameTags", Category.RENDER);
  }
  
  @EventTarget
  public void onRender(EventRender event)
  {
    for (EntityPlayer e : mc.theWorld.playerEntities) {
      if (!EntityHelper.isBot(e))
      {
        double posX = e.lastTickPosX + (e.posX - e.lastTickPosX) * Timer.renderPartialTicks - mc.getRenderManager().renderPosX;
        double posY = e.lastTickPosY + (e.posY - e.lastTickPosY) * Timer.renderPartialTicks - mc.getRenderManager().renderPosY;
        double posZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * Timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
        renderLivingLabel(e, e.getDisplayName().getFormattedText(), posX, posY, posZ);
      }
    }
  }
  
  protected void renderLivingLabel(EntityPlayer entityIn, String str, double x, double y, double z)
  {
    int intping;
    try
    {
      intping = mc.getNetHandler().getPlayerInfo(entityIn.getUniqueID()).getResponseTime();
    }
    catch (NullPointerException e)
    {
      int ping;
      intping = 0;
    }
    String ping = "§e" + intping + " ms§r | ";
    String health = " | §c" + entityIn.getHealth() / 2.0F + " ❤§r";
    str = ping + str + health;
    
    double d0 = entityIn.getDistanceSqToEntity(mc.getRenderManager().livingPlayer);
    
    FontRenderer fontrenderer = mc.fontRendererObj;
    float f = (float)Math.sqrt(Math.sqrt(d0));
    float f1 = 0.016666668F * f;
    GlStateManager.pushMatrix();
    GlStateManager.translate((float)x + 0.0F, (float)y + entityIn.height + 0.5F, (float)z);
    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
    GlStateManager.scale(-f1, -f1, f1);
    GlStateManager.disableLighting();
    GlStateManager.depthMask(false);
    GlStateManager.disableDepth();
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    byte b0 = 0;
    if (str.equals("deadmau5")) {
      b0 = -10;
    }
    int i = fontrenderer.getStringWidth(str) / 2;
    GlStateManager.disableTexture2D();
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
    worldrenderer.pos(-i - 1, -1 + b0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
    worldrenderer.pos(-i - 1, 8 + b0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
    worldrenderer.pos(i + 1, 8 + b0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
    worldrenderer.pos(i + 1, -1 + b0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
    tessellator.draw();
    GlStateManager.enableTexture2D();
    fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, b0, -1);
    GlStateManager.enableDepth();
    GlStateManager.depthMask(true);
    GlStateManager.enableLighting();
    GlStateManager.disableBlend();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.popMatrix();
  }
}
