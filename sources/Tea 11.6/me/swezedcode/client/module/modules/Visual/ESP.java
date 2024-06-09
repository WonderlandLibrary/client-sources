package me.swezedcode.client.module.modules.Visual;

import java.text.DecimalFormat;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventListener;
import com.mojang.authlib.GameProfile;

import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.more.Friend;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.MemeNames;
import me.swezedcode.client.module.modules.Options.Rainbow;
import me.swezedcode.client.module.modules.World.FriendsPlus;
import me.swezedcode.client.utils.events.EventRender;
import me.swezedcode.client.utils.render.ColorUtils;
import me.swezedcode.client.utils.render.SpecialCircle;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ESP extends Module {

	public ESP() {
		super("ESP", Keyboard.KEY_NONE, 0xFF1CFFD5, ModCategory.Visual);
		if (!MemeNames.enabled) {
			setDisplayName("ESP");
		} else {
			setDisplayName("ESPWithoutOpenGLErrors");
		}
	}

	public static float alpha;

	private BooleanValue rainbow = new BooleanValue(this, "Rainbow", "rainbow", Boolean.valueOf(false));

	@EventListener
	public void onRender(EventRender e) {
		for (Object theEntity : mc.theWorld.loadedEntityList) {
			if ((((Entity) theEntity).isInvisible()) && (theEntity != Minecraft.thePlayer)) {
				Minecraft.theWorld.removeEntity((Entity) theEntity);
			}
		}
		for (Object i : mc.theWorld.loadedEntityList) {
			EntityPlayer player = (EntityPlayer) i;
			if ((player != mc.thePlayer) && player instanceof EntityPlayer && (player != null)
					&& (player.getName() != mc.thePlayer.getName()) && !player.isPlayerFullyAsleep()) {
				float posX = (float) ((float) player.lastTickPosX
						+ (player.posX - player.lastTickPosX) * mc.timer.renderPartialTicks);
				float posY = (float) ((float) player.lastTickPosY
						+ (player.posY - player.lastTickPosY) * mc.timer.renderPartialTicks);
				float posZ = (float) ((float) player.lastTickPosZ
						+ (player.posZ - player.lastTickPosZ) * mc.timer.renderPartialTicks);
				float distance = mc.thePlayer.getDistanceToEntity(player);
				float health = ((EntityLivingBase) player).getHealth();
				if (mc.thePlayer.isOnSameTeam((EntityPlayer) player)) {
					if (!player.isPlayerSleeping()) {
						draw2D(player, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY,
								posZ - RenderManager.renderPosZ, 0.0F, 1.0F, 0.0F, alpha);
					}
				} else {
					if (!player.isPlayerSleeping()) {
						draw2D(player, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY,
								posZ - RenderManager.renderPosZ, 1.0F, 0.0F, 0.0F, alpha);
					}
				}
				if (!player.isPlayerSleeping()) {
					draw2D(player, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY,
							posZ - RenderManager.renderPosZ, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF);
					if (distance <= 5.0F) {
						draw2D(player, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY,
								posZ - RenderManager.renderPosZ, 1.0F, 0.0F, 0.0F, alpha);
					} else if (distance <= 40.0F) {
						draw2D(player, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY,
								posZ - RenderManager.renderPosZ, 1.0F, distance / 100.0F, 0.0F, alpha);
					} else if (distance > 40.0F) {
						draw2D(player, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY,
								posZ - RenderManager.renderPosZ, 0.0F, 255.0F, 0.0F, alpha);
					}
				}
				DecimalFormat decimal = new DecimalFormat("#.#");
				float percent = Float.valueOf(decimal.format(health / 2.0F)).floatValue();
				if (!player.isPlayerSleeping()) {
					if (percent >= 6.0F) {
						draw2D(player, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY,
								posZ - RenderManager.renderPosZ, 0.1F, 1.0F, 0.1F, alpha);
					}
					if (percent < 6.0F) {
						draw2D(player, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY,
								posZ - RenderManager.renderPosZ, 1.0F, 0.5F, 0.0F, alpha);
					}
					if (percent < 3.0F) {
						draw2D(player, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY,
								posZ - RenderManager.renderPosZ, 1.0F, 0.0F, 0.0F, alpha);
					}
				}
			}
		}
	}

	GameProfile gameProfile;

	public void draw2D(Entity e, double posX, double posY, double posZ, float alpha, float red, float green,
			float blue) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(posX, posY, posZ);
		GL11.glNormal3f(0.0F, 0.0F, 0.0F);
		GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);

		GlStateManager.scale(-0.1D, -0.1D, 0.1D);
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GlStateManager.func_179098_w();
		GlStateManager.depthMask(true);
		if (Rainbow.enabled) {
			SpecialCircle.circleOutline(0F, -8.2F, 10, ColorUtils.getRainbow(0L, 1F).getRGB());
		}
		if (!Manager.getManager().getFriendManager().isFriend(e.getName())) {
			SpecialCircle.circleOutline(0F, -8.2F, 10, 0xFFFFFFFF);
		} else {
			SpecialCircle.circleOutline(0F, -8.2F, 10, 0xFF2EFFEA);
		}
		GL11.glDisable(3042);
		GL11.glEnable(2929);
		GL11.glEnable(2896);
		GlStateManager.popMatrix();
	}

}
