package me.swezedcode.client.module.modules.Fight;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;

public class BowAimbot extends Module {

	public BowAimbot() {
		super("BowAimbot", Keyboard.KEY_NONE, 0xFF3BFF6C, ModCategory.Fight);
	}

	private ArrayList<EntityLivingBase> loaded = new ArrayList<EntityLivingBase>();

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
	public void onMotion(EventMotion event) {
		for (Object o : mc.theWorld.loadedEntityList) {
			EntityLivingBase entity = (EntityLivingBase) o;
			if (mc.thePlayer.getDistanceToEntity(entity) <= 30 && entity instanceof EntityLivingBase && (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow)) {
				if (mc.thePlayer.isUsingItem()) {
					float[] rotations = AimUtils.getRotations(entity);
					setAngles(entity);
					event.getLocation().setYaw(rotations[0]);
					event.getLocation().setPitch(rotations[1]);
				}
			}
		}

	}
	
	@Override
	public void onEnable() {
		msg("Broken, please toggle off.");
	}

}
