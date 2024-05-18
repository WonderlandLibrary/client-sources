package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import me.protocol_client.thanks_slicky.properties.Value;
import me.protocol_client.utils.RenderUtils;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.Render3DEvent;

public class ESP extends Module {

	public final Value<Boolean>			player		= new Value<>("esp_player", true);
	public final Value<Boolean>			mob			= new Value<>("esp_mob", false);
	public final Value<Boolean>			chams		= new Value<>("esp_filled", true);
	public final Value<Boolean>			outline		= new Value<>("esp_outline", false);
	public final Value<Boolean>			box			= new Value<>("esp_box", false);
	public final Value<Boolean>			wallhack	= new Value<>("esp_wallhack", false);

	public final ClampedValue<Float>	linewidth	= new ClampedValue<>("esp_line_width", 3f, 0f, 5f);

	RendererLivingEntity				renderlivingbase;
	public int							selection	= 0;

	public ESP() {
		super("ESP", "esp", Keyboard.KEY_LBRACKET, Category.RENDER, new String[] { "esp" });
		setTag(outline.getName());
	}

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	@EventTarget
	public void onRenderEvent(Render3DEvent event) {
		setDisplayName("ESP");
		if (outline.getValue()) {
			setDisplayName(getDisplayName() + " [Outline]");
		}
		if (box.getValue()) {
			setDisplayName(getDisplayName() + " [Box]");
		}
		if(chams.getValue()){
			setDisplayName(getDisplayName() + " [Filled]");
		}
		for (Object theObject : mc.theWorld.loadedEntityList) {
			if (!(theObject instanceof EntityLivingBase))
				continue;
			EntityLivingBase entity = (EntityLivingBase) theObject;
			if (isCorrect(entity)) {
				if (isCorrect(entity)) {
					if (box.getValue()) {
						player(entity);
					}
				}
			}
		}
	}

	public void player(EntityLivingBase entity) {
		float red = 1;
		float green = 1f;
		float blue = 1F;
		float red2 = 0F;
		float blue2 = 1F;
		float green2 = 1F;
		float ired = 1F;
		float igreen = 0.5f;
		float iblue = 0f;

		double xPos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
		double yPos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY;
		double zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;

		if (isCorrect(entity)) {
			if (entity.isInvisible() && !Protocol.getFriendManager().isFriend(entity.getName())) {
				render(ired, igreen, iblue, xPos, yPos, zPos, entity.width, entity.height, entity);
				return;
			}

			if (Protocol.getFriendManager().isFriend(entity.getName())) {
				render(red2, green2, blue2, xPos, yPos, zPos, entity.width, entity.height, entity);
				return;
			} else {
				if (!entity.isPlayerSleeping() && !entity.isSneaking() && !entity.isInvisible()) {
					render(red, green, blue, xPos, yPos, zPos, entity.width, entity.height, entity);
					return;
				}
				if (entity.isSneaking() && !entity.isInvisible()) {
					render(1F, 0.4F, 0.4F, xPos, yPos, zPos, entity.width, entity.height, entity);
					return;
				}
			}
		}
	}

	private int		amount	= 0;
	private float	spin;
	private float	cumSize;

	public void render(float red, float green, float blue, double x, double y, double z, float width, float height, EntityLivingBase entity) {
		RenderUtils.drawEntityESP(x, y, z, width, height, red, green, blue, 0.25F, red, green, blue, 1F, 0.45F, entity);
	}

	public boolean isCorrect(EntityLivingBase entity) {
		if (player.getValue()) {
			if (entity instanceof EntityPlayer && entity != Wrapper.getPlayer()) {
				return true;
			}
		}
		if (mob.getValue()) {
			if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer)) {
				return true;
			}
		}
		return false;
	}

	public void runCmd(String s) {
		try {
			if (s.startsWith("mode")) {
				String line2 = s.split(" ")[1];
				if (line2.equalsIgnoreCase("player") || line2.equalsIgnoreCase("players")) {
					this.player.setValue(!this.player.getValue());
					Wrapper.tellPlayer("\2477ESP mode " + Protocol.primColor + "players \2477is " + (this.player.getValue() ? "now" : "no longer") + " active");
					return;
				}
				if (line2.equalsIgnoreCase("mob") || line2.equalsIgnoreCase("mobs")) {
					this.mob.setValue(!this.mob.getValue());
					Wrapper.tellPlayer("\2477ESP mode " + Protocol.primColor + "mobs \2477is " + (this.mob.getValue() ? "now" : "no longer") + " active");
					return;
				}
				if (line2.equalsIgnoreCase("outline")) {
					this.outline.setValue(!this.outline.getValue());
					Wrapper.tellPlayer("\2477ESP mode " + Protocol.primColor + "outline \2477is " + (this.outline.getValue() ? "now" : "no longer") + " active");
					return;
				}
				if (line2.equalsIgnoreCase("box")) {
					this.box.setValue(!this.box.getValue());
					Wrapper.tellPlayer("\2477ESP mode " + Protocol.primColor + "box \2477is " + (this.box.getValue() ? "now" : "no longer") + " active");
					return;
				}
				Wrapper.tellPlayer("Invalid arguments. -" + Protocol.primColor + "esp \2477mode<players/mobs>");
				return;
			}
			if (s.startsWith("width")) {
				float width = Float.parseFloat(s.split(" ")[1]);
				this.linewidth.setValue(width);
				Wrapper.tellPlayer("\2477ESP " + Protocol.primColor + "line width \2477is now " + width);
				return;
			}
			Wrapper.tellPlayer("\2477Invalid arguments. -" + Protocol.primColor + "esp \2477<mode/width> <players/mobs/width>");
			return;
		} catch (Exception e) {
			Wrapper.invalidCommand("ESP");
			Wrapper.tellPlayer("\2477-" + Protocol.primColor + "ESP \2477<mode> <players/mobs>");
			Wrapper.tellPlayer("\2477-" + Protocol.primColor + "ESP \2477<Width> <Line Width>");
		}
	}
}
