package xyz.vergoclient.modules.impl.visual;

import com.mojang.realmsclient.gui.ChatFormatting;
import org.lwjgl.opengl.GL11;

import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventRender3D;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.modules.impl.miscellaneous.AntiBot;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.ui.fonts.FontUtil;
import xyz.vergoclient.ui.fonts.JelloFontRenderer;
import xyz.vergoclient.util.main.MiscellaneousUtils;
import xyz.vergoclient.util.main.RenderUtils2;
import xyz.vergoclient.util.main.RotationUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

import java.awt.*;
import java.util.Arrays;

public class Nametags extends Module implements OnEventInterface {

	public Nametags() {
		super("Nametags", Category.VISUAL);
	}

	public ModeSetting mode = new ModeSetting("Design", "SkidFlux", "SkidFlux");

	@Override
	public void loadSettings() {

		mode.modes.addAll(Arrays.asList("SkidFlux"));

		addSettings(mode);
	}

	@Override
	public void onEvent(Event e) {
		
		if (e instanceof EventRender3D && e.isPre()) {
			
			Vec3 playerPos = MiscellaneousUtils.getRenderEntityPos(mc.thePlayer);
			
			for (EntityPlayer player : mc.theWorld.playerEntities) {
				if (player.isUser() || player.isDead || (Vergo.config.modAntibot.isEnabled() && AntiBot.isBot(player))) {
					continue;
				}
				Vec3 targetPos = MiscellaneousUtils.getRenderEntityPos(player);
				double x = targetPos.xCoord, y = targetPos.yCoord + player.getEyeHeight() + 1, z = targetPos.zCoord;
				String text = ChatFormatting.WHITE + player.getName();
				String scoreTitle = text;
				String textNoColorFormatting = "";
				boolean removeNext = false;
				for (char c : scoreTitle.toCharArray()) {
					if (c == "\247".toCharArray()[0]) {
						removeNext = true;
					}
					else if (!removeNext) {
						textNoColorFormatting += c;
					}else {
						removeNext = false;
					}
				}
				int color = -1;
				double opacity = 1;
				float[] rots = RotationUtils.getRotationFromPosition(playerPos.xCoord, playerPos.zCoord, playerPos.yCoord + mc.thePlayer.getEyeHeight(), x, z, y);
				
				GlStateManager.pushMatrix();
				GlStateManager.pushAttrib();
				RenderHelper.enableStandardItemLighting();
				GlStateManager.enablePolygonOffset();
				GL11.glPolygonOffset(1.0f, -1100000.0f);
				GlStateManager.disableLighting();
				GlStateManager.disableDepth();
				GlStateManager.enableBlend();
				double scale = 35;
				GlStateManager.scale(1/scale, 1/scale, 1/scale);
				GlStateManager.rotate(180, 1, 0, 0);
				GlStateManager.enableTexture2D();
				GlStateManager.translate((x - mc.getRenderManager().renderPosX) * scale,
						(y - mc.getRenderManager().renderPosY) * -scale, (z - mc.getRenderManager().renderPosZ) * -scale);
				GlStateManager.rotate(rots[0], 0, 1, 0);
				GlStateManager.rotate(rots[1], 1, 0, 0);
				JelloFontRenderer fr = FontUtil.juraNormal;
				GlStateManager.rotate(180, 0, 1, 0);
				GlStateManager.color(1, 1, 1, 1);
				GlStateManager.color(1, 1, 1, (float) (opacity));
				GlStateManager.translate(-fr.getStringWidth(text) / 2, 0, 0);
				
				// Render stuff
				// Tinted background
				RenderUtils2.drawBorderedRect(-4, 0, (float) fr.getStringWidth(text) + 28, 15, 0.3f, new Color(0xD2212121, true), new Color(0xCD0A0A0A, true));

				
				// Render name
				GlStateManager.translate(0, 1, 0);
				GlStateManager.colorState.alpha = 1;
				fr.drawString(text, 0, 3, color);

				String healthStr = Math.round(player.getHealth() * 20) / 20d + "";

				Color healthColor = new Color(95, 255, 67);

				if(player.getHealth() >= 11) {
					healthColor = new Color(95, 255, 67);
				} else if(player.getHealth() <= 10 && player.getHealth() > 5){
					healthColor = new Color(250, 112, 57);
				} else if(player.getHealth() < 5) {
					healthColor = new Color(252, 37, 37);
				}

				FontUtil.comfortaaSmall.drawString(healthStr, fr.getStringWidth(text) + 7, 5.5f, healthColor.getRGB());

				GlStateManager.color(1, 1, 1, 1);
				GlStateManager.disableTexture2D();
				GlStateManager.disableBlend();
				GlStateManager.enableDepth();
				GlStateManager.enableLighting();
				GlStateManager.disablePolygonOffset();
				RenderHelper.disableStandardItemLighting();
				GlStateManager.popAttrib();
				GlStateManager.popMatrix();
				
			}
			
		}
		
	}

}
