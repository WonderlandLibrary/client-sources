package lunadevs.luna.module.combat;

import java.util.List;

import lunadevs.luna.category.Category;
import lunadevs.luna.friend.FriendManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Aimbot extends Module {
	public Aimbot() {
		super("Aimbot", 0, Category.COMBAT, false);
	}

	public void onEnable() {
	}

	public void onDisable() {
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) {
			return;
		}
		List list = mc.theWorld.playerEntities;
		for (int k = 0; k < list.size(); k++) {
			if (((EntityPlayer) list.get(k)).getName() != mc.thePlayer.getName()) {
				EntityPlayer entityplayer = (EntityPlayer) list.get(1);
				if (mc.thePlayer.getDistanceToEntity(entityplayer) > mc.thePlayer
						.getDistanceToEntity((Entity) list.get(k))) {
					entityplayer = (EntityPlayer) list.get(k);
				}
				if (FriendManager.isFriend(entityplayer.getName())) {
					continue;
				}
				float f = mc.thePlayer.getDistanceToEntity(entityplayer);
				if ((f < 6.0F) && (mc.thePlayer.canEntityBeSeen(entityplayer)) && (!entityplayer.isInvisible())) {
					faceEntity(entityplayer);
				}
			}
		}
	}

	@Override
	public void onRender() {

	}

	public static void faceEntity(EntityLivingBase entity) {
		float[] rotations = RotationUtils.getRotations(entity);
		if (rotations != null) {
			Minecraft.getMinecraft().thePlayer.rotationYaw = rotations[0];
			Minecraft.getMinecraft().thePlayer.rotationPitch = rotations[1] - 8.0F;
		}
	}

	@Override
	public String getValue() {
		return null;
	}
}
