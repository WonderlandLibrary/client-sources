package me.vaziak.sensation.client.api.gui.ingame.HUD.element.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.event.EventSystem;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Element;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Quadrant;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.impl.combat.KillAura;
import me.vaziak.sensation.client.impl.visual.Overlay;
import me.vaziak.sensation.utils.anthony.ColorCreator;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import me.vaziak.sensation.utils.math.MathUtils;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.StringUtils;


public class TargetHUD extends Element {
	private BooleanProperty enabled = new BooleanProperty("Enabled", "After multiple reports from some people that the TargetHUD crashed the game for some unknown reason, we added an option to disable it since we're lazy as fuck", false);
	private DoubleProperty opactiy = new DoubleProperty("Opacity", "The darkness of the background", 160, 1, 250, 1);
	private int compar;
	private long lastPacket;
	private TimerUtil displayTimer;
	private List<Integer> delays = new ArrayList();
	private int currentIndex;
    public TargetHUD() {
        super("TargetHud", Quadrant.TOP_LEFT, 415, 320); 
        registerValue(enabled);
        registerValue(opactiy);
        currentIndex = 0;
        displayTimer = new TimerUtil();
		EventSystem.hook(this);
    } 

	@Collect
	public void onEvent(SendPacketEvent event) {
    	if (event.getPacket() instanceof C0APacketAnimation) { 
    		compar = (int) Math.abs(System.currentTimeMillis() - lastPacket);
    		lastPacket = System.currentTimeMillis();
    	}
	}
	
    @Override
    public void drawElement(boolean editor) {
		this.editX = positionX;
		this.editY = positionY;
		this.height = 55;
		this.width = 90;
		if (!enabled.getValue()) return;
		Overlay hud = (Overlay) Sensation.instance.cheatManager.getCheatRegistry().get("Overlay");
        KillAura aura = (KillAura) Sensation.instance.cheatManager.getCheatRegistry().get("Kill Aura");

		if (!hud.getState() || !aura.getState() || aura.targetList.size() == 0 || aura.targetIndex == -1 || aura.targetList.get(aura.targetIndex) == null)
			return;

		EntityLivingBase target = (EntityLivingBase) aura.target;

		if (target == null || !(target instanceof EntityPlayer)) {
			delays.clear();
			currentIndex = 0; 
			return;
		}

		final String playerName = "" + StringUtils.stripControlCodes(target.getName());
		
		width = (target instanceof EntityPlayer ? 60 : 50) + Fonts.bf18.getStringWidth(target.getName()) + Fonts.bf18.getStringWidth("Health:" + (target.isDead ? 0 : !Double.isNaN(target.getHealth()) ? MathUtils.round(target.getHealth() / 2 , 1) : 0));
 
		drawBorderedRect(positionX, positionY, width, height, 0.5, new Color(0, 0, 0, opactiy.getValue().intValue()).getRGB(),new Color(0, 0, 0, opactiy.getValue().intValue()).getRGB());
	
		Fonts.bf18.drawStringWithShadow(playerName, positionX + (width / 2) - 20, positionY + Fonts.bf18.getStringHeight(playerName), -1);

		drawBorderedRect(positionX + 35, positionY + Fonts.bf18.getStringHeight(playerName) + 7, width - 35, 1, 0.5, new Color(255, 255, 255).getRGB(), new Color(255, 255, 255).getRGB());

        Draw.drawRectangle(positionX, positionY + height, positionX + ((target.getHealth() <= 20 ? target.getHealth() : 20) * (3.3 * 2)), positionY + height + 1.1, getHealthColor(target));

		if (!Float.isNaN(target.getHealth())) {
			int healf = (int)target.getHealth();
			double theHealth = (target.getHealth() - (target.getHealth() - healf));
            Fonts.bf18.drawStringWithShadow("Health: " + theHealth, positionX + 35, positionY + 20, -1);
        }

		String hasBetterArmor = "";

		if (target.getTotalArmorValue() > Minecraft.getMinecraft().thePlayer.getTotalArmorValue()) {
		    hasBetterArmor = "Them";
        } else if (target.getTotalArmorValue() == Minecraft.getMinecraft().thePlayer.getTotalArmorValue()) {
		    hasBetterArmor = "Equal";
        } else {
		    hasBetterArmor = "You";
        }

		Fonts.bf18.drawStringWithShadow("Better Armor: " + hasBetterArmor, positionX + 35, positionY + 30, -1);

		Fonts.bf18.drawStringWithShadow("Distance: " + MathUtils.newRound(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(target), "#.#"), positionX + 35, positionY + 40, -1);
		/*
		Fonts.bf18.drawStringWithShadow("Health: ", positionX + 30, positionY + 30, -1);
		
		if (!String.valueOf(target.getHealth()).toLowerCase().contains("nan") ) {
			drawBorderedRect(positionX + 30, positionY + 40, target.getHealth() * 3.8, 10, 0.5, new Color(0, 0, 0).getRGB(), getHealthColor(target));
			
			Fonts.bf18.drawStringWithShadow("" + MathUtils.round(target.getHealth() / 2, 1), positionX + 60, positionY + 30, getHealthColor(target));
		} else {
			Fonts.bf18.drawStringWithShadow(EnumChatFormatting.RED + " Has no health", positionX + 60, positionY + 30, getHealthColor(target));
			
		}

		if (compar != 0) {
			Fonts.bf18.drawStringWithShadow("CPS: " + (int)(1000 / compar ) * 2, positionX + Fonts.bf18.getStringWidth(playerName) + (target instanceof EntityPlayer ? 39 : 15), positionY + 17, -1); 
		}*/


		if (target instanceof EntityPlayer) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1, 1, 1, 1);
			drawEntityOnScreen(positionX + 15, positionY + 50, 25, 20, 20, target); 
            GL11.glDisable(GL11.GL_BLEND);
		}
    }
    
    public static void drawEntityOnScreen(double d, double e, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) d, (float) e, 50.0F);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float) Math.atan((double) (mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4; 
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    
    
    public static void drawBorderedRect(double x, double y, double width, double height, double lineSize, int borderColor, int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
        Gui.drawRect(x, y, x + width, y + lineSize, borderColor);
        Gui.drawRect(x, y, x + lineSize, y + height, borderColor);
        Gui.drawRect(x + width, y, x + width - lineSize, y + height, borderColor);
        Gui.drawRect(x, y + height, x + width, y + height - lineSize, borderColor);
    }

    private int getHealthColor(EntityLivingBase player) {
        double hpPercentage = player.isDead ? 0 : player.getHealth() / 20;
        if (hpPercentage > 1)
            hpPercentage = 1;
        else if (hpPercentage < 0)
            hpPercentage = 0;


        int r = (int) (230 + (50 - 230) * hpPercentage);
        int g = (int) (50 + (230 - 50) * hpPercentage);
        int b = 50;
        
        return ColorCreator.create(r, g, b, 255);
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double) (x + 0), (double) (y + height), (double) -90).tex((double) ((float) (textureX + 0) * f), (double) ((float) (textureY + height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) (y + height), (double) -90).tex((double) ((float) (textureX + width) * f), (double) ((float) (textureY + height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) (y + 0), (double) -90).tex((double) ((float) (textureX + width) * f), (double) ((float) (textureY + 0) * f1)).endVertex();
        worldrenderer.pos((double) (x + 0), (double) (y + 0), (double) -90).tex((double) ((float) (textureX + 0) * f), (double) ((float) (textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }
}
