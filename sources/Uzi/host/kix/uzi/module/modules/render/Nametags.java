package host.kix.uzi.module.modules.render;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.RenderStringEvent;
import host.kix.uzi.events.RenderWorldEvent;

import host.kix.uzi.Uzi;
import host.kix.uzi.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * Created by myche on 3/2/2017.
 */
public class Nametags extends Module {

    private final DecimalFormat decimalFormat = new DecimalFormat("#.#");

    public Nametags() {
        super("Nametags", 0, Category.RENDER);
    }

    @SubscribeEvent
    public void renderTag(RenderWorldEvent e) {
        if (!Minecraft.isGuiEnabled())
            return;
        for (Object obj : mc.theWorld.playerEntities) {
            if (obj instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) obj;
                if (!isRenderingPossible(player))
                    continue;
                float partialTicks = e.getPartialTicks();
                double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks - mc.getRenderManager().renderPosX;
                double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks - mc.getRenderManager().renderPosY;
                double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks - mc.getRenderManager().renderPosZ;
                drawNametags(player, x, y, z);
            }
        }
    }

    public void drawNametags(EntityLivingBase entity, double x, double y, double z) {
        String entityName = entity.getDisplayName().getFormattedText();
        if (Uzi.getInstance().getFriendManager().get(entity.getName()).isPresent()) {
            RenderStringEvent event = new RenderStringEvent(entityName);
            EventManager.call(event);
            entityName = event.getString();
        }
        if (getNametagColor(entity) != 0xFFFFFFFF) entityName = StringUtils.stripControlCodes(entityName);
        double health = entity.getHealth() / 2;
        double maxHealth = entity.getMaxHealth() / 2;
        double percentage = 100 * (health / maxHealth);
        String healthColor;
        if (percentage > 75) healthColor = "2";
        else if (percentage > 50) healthColor = "e";
        else if (percentage > 25) healthColor = "6";
        else healthColor = "4";
        String healthDisplay = decimalFormat.format(Math.floor((health + (double) 0.5F / 2) / 0.5F) * 0.5F);
        String maxHealthDisplay = decimalFormat.format(Math.floor((entity.getMaxHealth() + (double) 0.5F / 2) / 0.5F) * 0.5F);
        entityName = String.format("%s \247%s%s", entityName, healthColor, healthDisplay);
        float distance = mc.thePlayer.getDistanceToEntity(entity);
        float var13 = (distance / 5 <= 2 ? 2.0F : distance / 5) * 0.8F;
        float var14 = 0.016666668F * var13;
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(x + 0.0F, y + entity.height + 0.5F, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        if (mc.gameSettings.thirdPersonView == 2) {
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, -1.0F, 0.0F, 0.0F);
        } else {
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        }
        GlStateManager.scale(-var14, -var14, var14);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        int var17 = 0;
        if (entity.isSneaking()) {
            var17 += 4;
        }
        var17 -= distance / 5;
        if (var17 < -8) {
            var17 = -8;
        }
        GlStateManager.func_179090_x();
        worldRenderer.startDrawingQuads();
        float var18 = mc.fontRendererObj.getStringWidth(entityName) / 2;
        worldRenderer.func_178960_a(0.0F, 0.0F, 0.0F, 0.3F);
        worldRenderer.addVertex(-var18 - 1, -2 + var17, 0.0D);
        worldRenderer.addVertex(-var18 - 1, 7 + var17, 0.0D);
        worldRenderer.addVertex(var18 + 1, 7 + var17, 0.0D);
        worldRenderer.addVertex(var18 + 1, -2 + var17, 0.0D);
        tessellator.draw();
        GlStateManager.func_179098_w();
        mc.fontRendererObj.drawStringWithShadow(entityName, -var18, var17 - 1, getNametagColor(entity));
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public double interpolate(double start, double end, double percent) {
        return start + (end - start) * percent;
    }

    public int getNametagColor(EntityLivingBase entity) {
        int color = 0xFFFFFFFF;
        if (entity instanceof EntityPlayer && Uzi.getInstance().getFriendManager().get(entity.getName()).isPresent()) {
            color = 0xFF4DB3FF;
        } else if (entity.isInvisibleToPlayer(mc.thePlayer)) {
            color = 0xFFFFE600;
        } else if (entity.isSneaking()) {
            color = 0xFFFF0000;
        } else if (entity.hurtTime > 5) {
            color = new Color(0xFF4143).getRGB();
        }
        return color;
    }

    private boolean isRenderingPossible(Entity entity) {
        return entity != null && entity != mc.thePlayer && entity.isEntityAlive();
    }

}
