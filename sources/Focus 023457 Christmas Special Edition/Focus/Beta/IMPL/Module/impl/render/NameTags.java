package Focus.Beta.IMPL.Module.impl.render;

import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.render.EventRender3D;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.font.FontLoaders;
import Focus.Beta.IMPL.managers.ModuleManager;
import Focus.Beta.IMPL.set.Mode;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Timer;

import java.awt.Color;

public class NameTags extends Module{
	public Mode<Enum> modes = new Mode<Enum>("Modes", "Modes", Modes.values(), Modes.Jello);
	
	public NameTags() {
		super("NameTags", new String[0], Type.RENDER, "Allow's to see players nametags through walls");
	}
	
	@EventHandler
	public void E(EventRender3D e) {
		for(Entity en : mc.theWorld.loadedEntityList) {
			if(en instanceof EntityPlayer) {
				renderNameTag((EntityPlayer) en, en.getName());
			}
		}
	}
	
	private String getPlayerName(EntityPlayer entity) {
		String name = entity.getDisplayName().getFormattedText();
		String pre = "";
			pre = ChatFormatting.WHITE + "";
		return name + pre;
	}
	
	private void renderNameTag(EntityLivingBase entity, String tag) {

		GL11.glPushMatrix();
		RenderManager renderManager = mc.getRenderManager();
		Timer timer = mc.timer;
		
		GL11.glTranslated(
				entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks - renderManager.renderPosX,
				entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks - renderManager.renderPosY + entity.getEyeHeight() + 0.55,
				entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks - renderManager.renderPosZ
		);

		GL11.glRotatef(-mc.getRenderManager().playerViewY, 0F, 1F, 0F);
		GL11.glRotatef(-mc.getRenderManager().playerViewX, -1F, 0F, 0F);
		
		float distance = mc.thePlayer.getDistanceToEntity(entity) / 4F;
		
		if(distance < 1F) {
			distance = 1F;
		}
		
		float scale = (distance / 150F) * 1.5F;

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);

		
		switch(modes.getModeAsString()) {
			case "Jello":
				String name = entity.getDisplayName().getUnformattedText();
				int width = FontLoaders.arial18.getStringWidth(tag) / 2;
				float maxWidth = (width + 4F) - (-width - 4F);
				float healthPrecent = entity.getHealth() / entity.getMaxHealth();
				
				GL11.glScalef(-scale * 2, -scale * 2, scale * 2);
				Gui.drawRect(-width - 4F, -FontLoaders.arial18.getHeight() * 3F, width + 4F, -3F, new Color(50, 50, 50, 170).getRGB());

				if(healthPrecent > 1) {
					healthPrecent = 1F;
				}

				Gui.drawRect(-width - 4F, -3F, (-width - 4F) + (maxWidth * healthPrecent), 1F, HUD.color);

				FontLoaders.arial18.drawString(tag, -width, -FontLoaders.arial18.getHeight() * 2 - 4, Color.white.getRGB());
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				FontLoaders.arial18.drawString("HP: " + String.valueOf(entity.getHealth()), -width * 2, -FontLoaders.arial18.getHeight() * 2, Color.white.getRGB());
		break;
		}

		GL11.glColor4f(1F, 1F, 1F, 1F);
GL11.glPopMatrix();
GL11.glEnable(GL11.GL_LIGHTING);
GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	enum Modes{
		Jello
	}

}
