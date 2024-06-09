package me.swezedcode.client.module.modules.World;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.AimUtils;
import me.swezedcode.client.utils.Angles;
import me.swezedcode.client.utils.AnglesUtils;
import me.swezedcode.client.utils.Vector3D;
import me.swezedcode.client.utils.events.EventMotion;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;

public class MineStrike extends Module {

	public MineStrike() {
		super("MineStrike", Keyboard.KEY_NONE, 0xFFE0264F, ModCategory.World);
	}

	public static Angles serverAngles = new Angles(0f, 0f);
	public static AnglesUtils aimUtil = new AnglesUtils(30, 40, 30, 40);

	public static void setAngles(EntityLivingBase targ) {
		if (targ != null) {
			Vector3D<Double> enemy = new Vector3D<>(targ.posX, targ.posY, targ.posZ);
			Vector3D<Double> me = new Vector3D<>(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
			Angles dstAngle = aimUtil.calculateAngle(enemy, me);
			Angles srcAngle = new Angles(serverAngles.getYaw(), serverAngles.getPitch());
			serverAngles = aimUtil.smoothAngle(srcAngle, dstAngle);
		}
	}

	@EventListener
	public void onPre(EventMotion event) {
		for (Object object : mc.theWorld.loadedEntityList) {
			if (object instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase) object;
				if ((entity != Minecraft.thePlayer) && ((entity instanceof EntityLivingBase) && !(entity instanceof EntitySquid))
						&& (Minecraft.thePlayer.getDistanceToEntity(entity) <= 30) && (!entity.isDead)
						&& (entity.isEntityAlive())) {
					if (Manager.getManager().getFriendManager().isFriend(entity.getName())) {
						continue;
					}
					if(mc.thePlayer.isOnSameTeam(entity)) {
						continue;
					}
					float[] rotations = AimUtils.getRotations(entity);
					event.getLocation().setYaw(rotations[0]);
					event.getLocation().setPitch(rotations[1]);
				}
			}
		}
	}

}
