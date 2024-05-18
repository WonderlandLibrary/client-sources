package me.protocol_client.modules;

import org.lwjgl.opengl.GL11;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import me.protocol_client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.Render3DEvent;

public class Tracers extends Module {

	public Tracers() {
		super("Tracers", "tracers", 0, Category.RENDER, new String[] { "tracers", "tracer" });
	}

	private final ClampedValue<Float>	linewidth	= new ClampedValue<>("tracers_line_width", 1f, 1f, 5f);

	public void onEnable() {
		boolean t = Protocol.tags.isToggled();
		Protocol.tags.setToggled(false);
		EventManager.register(this);
		Protocol.tags.setToggled(t);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	@EventTarget
	public void eventRender(Render3DEvent event) {
		setDisplayName("Tracers [" + linewidth.getValue().longValue() + "]");

		for (Object o : mc.theWorld.loadedEntityList) {
			if (o instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase) o;
				if (entity instanceof EntityPlayer) {
					if (entity != mc.thePlayer && !entity.isPlayerSleeping()) {
						// player(entity);
						double x = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
						double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY;
						double z = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;
						boolean temp = Wrapper.mc().gameSettings.viewBobbing;
						Wrapper.mc().gameSettings.viewBobbing = false;
						Wrapper.mc().entityRenderer.setupCameraTransform(Wrapper.mc().timer.renderPartialTicks, 2);
						Wrapper.mc().gameSettings.viewBobbing = temp;
						GL11.glPushMatrix();
						GL11.glEnable(GL11.GL_BLEND);
						GL11.glEnable(GL11.GL_LINE_SMOOTH);
						GL11.glDisable(GL11.GL_DEPTH_TEST);
						GL11.glDisable(GL11.GL_TEXTURE_2D);
						GL11.glBlendFunc(770, 771);
						GL11.glEnable(GL11.GL_BLEND);
						GL11.glLineWidth(this.linewidth.getValue());
						getColors(entity);
						GL11.glBegin(GL11.GL_LINE_STRIP);
						GL11.glVertex3d(0.0D, 0.0D + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0D);
						GL11.glVertex3d(x, y, z);
						GL11.glEnd();
						GL11.glDisable(GL11.GL_BLEND);
						GL11.glEnable(GL11.GL_TEXTURE_2D);
						GL11.glEnable(GL11.GL_DEPTH_TEST);
						GL11.glDisable(GL11.GL_LINE_SMOOTH);
						GL11.glDisable(GL11.GL_BLEND);
						GL11.glPopMatrix();
					}
				}
			}
		}

	}

	public void getColors(EntityLivingBase entity) {
		if (Protocol.getFriendManager().isFriend(entity.getName())) {
			GL11.glColor3f(0, 1, 1);
			return;
		}
		if (entity.isInvisible()) {
			GL11.glColor3f(1, 0.5f, 0);
			return;
		}
		if (entity.isSneaking()) {
			GL11.glColor3f(1, 0.4f, 0.4f);
			return;
		}
		GL11.glColor3f(1f, 1f,1f);
	}
}