 package me.vene.skilled.modules.mods.render;
 
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.modules.mods.combat.AntiBot;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.renderer.GlStateManager;
 import net.minecraft.client.renderer.Tessellator;
 import net.minecraft.client.renderer.WorldRenderer;
 import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.util.EnumChatFormatting;
 import net.minecraftforge.client.event.RenderLivingEvent;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import org.lwjgl.opengl.GL11;
 
public class Nametags extends Module
{
    private Minecraft mc;
    private BooleanValue showHealth;
    private BooleanValue showDistance;
    private BooleanValue showInvis;
    private NumberValue scale;
    
    public Nametags() {
        super(StringRegistry.register("Nametags"), 0, Category.R);
        this.mc = Minecraft.getMinecraft();
        this.showHealth = new BooleanValue("Show Health", true);
        this.showDistance = new BooleanValue("Show Distance", false);
        this.showInvis = new BooleanValue("Show Invisible", true);
        this.scale = new NumberValue("Scale", 1.0, 0.1, 5.0);
        this.addOption(this.showHealth);
        this.addOption(this.showInvis);
        this.addOption(this.showDistance);
    }
    
    @SubscribeEvent
    public void onRenderEvent(final RenderLivingEvent.Specials.Pre event) {
        if (event.entity instanceof EntityPlayer && event.entity != this.mc.thePlayer && event.entity.deathTime == 0) {
            final EntityPlayer entity = (EntityPlayer)event.entity;
            if (AntiBot.bots.contains(entity)) {
                return;
            }
            if (!this.showInvis.getState() && entity.isInvisible()) {
                return;
            }
            event.setCanceled(true);
            String playerName = "";
            if (this.showDistance.getState()) {
                playerName = playerName + EnumChatFormatting.GREEN.toString() + "[" + EnumChatFormatting.WHITE.toString() + (int)this.mc.thePlayer.getDistanceToEntity((Entity)entity) + EnumChatFormatting.GREEN.toString() + "] ";
            }
            playerName += entity.getDisplayName().getFormattedText();
            if (this.showHealth.getState()) {
                final double health = entity.getHealth() / entity.getMaxHealth();
                final String healthString = ((health < 0.3) ? EnumChatFormatting.RED.toString() : ((health < 0.5) ? EnumChatFormatting.GOLD.toString() : ((health < 0.7) ? EnumChatFormatting.YELLOW.toString() : EnumChatFormatting.GREEN.toString()))) + rnd(entity.getHealth(), 1);
                playerName = playerName + " " + healthString;
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)event.x + 0.0f, (float)event.y + entity.height + 0.5f, (float)event.z);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(this.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
            final float f1 = 0.02666667f;
            GlStateManager.scale(-0.02666667f, -0.02666667f, 0.02666667f);
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0f, 9.374999f, 0.0f);
            }
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final Tessellator tessellator = Tessellator.getInstance();
            final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            final int i = (int)this.scale.getValue();
            final int j = (int)(this.mc.fontRendererObj.getStringWidth(playerName) / 2 * this.scale.getValue());
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos((double)(-j - 1), (double)(-1 + i), 0.0).color(entity.isSneaking() ? 100.0f : 0.0f, 0.0f, 0.0f, 1.0f).endVertex();
            worldrenderer.pos((double)(-j - 1), (double)(8 + i), 0.0).color(entity.isSneaking() ? 100.0f : 0.0f, 0.0f, 0.0f, 1.0f).endVertex();
            worldrenderer.pos((double)(j + 1), (double)(8 + i), 0.0).color(entity.isSneaking() ? 100.0f : 0.0f, 0.0f, 0.0f, 1.0f).endVertex();
            worldrenderer.pos((double)(j + 1), (double)(-1 + i), 0.0).color(entity.isSneaking() ? 100.0f : 0.0f, 0.0f, 0.0f, 1.0f).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            this.mc.fontRendererObj.drawString(playerName, (int)(-this.mc.fontRendererObj.getStringWidth(playerName) / 2 * this.scale.getValue()), i, -1);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }
    
    public static double rnd(final double n, final int d) {
        if (d == 0) {
            return (double)Math.round(n);
        }
        final double p = Math.pow(10.0, d);
        return Math.round(n * p) / p;
    }
}
