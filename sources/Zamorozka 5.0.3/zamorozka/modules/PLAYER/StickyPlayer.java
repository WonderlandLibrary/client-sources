package zamorozka.modules.PLAYER;

import java.util.List;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class StickyPlayer extends Module {

	public StickyPlayer() {
		super("StickyPlayer", Keyboard.KEY_NONE, Category.PLAYER);
	}
	 @Override
	    public void setup() {
	        Zamorozka.settingsManager.rSetting(new Setting("StickyDist", this, 3.70D, 0, 20.5D, true));
	        Zamorozka.settingsManager.rSetting(new Setting("Packet", this, true));
	    }
		public void onUpdate() {
			if (!this.getState()) {
				return;
			}
			
			List list = mc.world.playerEntities;

			for (int k = 0; k < list.size(); k++) {
				if (((EntityPlayer) list.get(k)).getName() == mc.player.getName()) {
					continue;
				}

				EntityPlayer entityplayer = (EntityPlayer) list.get(1);

				if (mc.player.getDistanceToEntity(entityplayer) > mc.player.getDistanceToEntity((Entity) list.get(k))) {
					entityplayer = (EntityPlayer) list.get(k);
				}

				float f = mc.player.getDistanceToEntity(entityplayer);

				if (f < Zamorozka.settingsManager.getSettingByName("StickyDist").getValDouble() && mc.player.canEntityBeSeen(entityplayer)) {
					this.faceEntity(entityplayer);
					mc.gameSettings.keyBindForward.pressed = true;
				}else {
					
			}
		}
		}

		public static synchronized void faceEntity(EntityLivingBase entity) {
			final float[] rotations = getRotationsNeeded(entity);

			if (rotations != null) {
				 if(Zamorozka.settingsManager.getSettingByName("Packet").getValBoolean()){	
					 mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], rotations[1] +1 , false)); 
					 mc.gameSettings.keyBindForward.pressed = true;
				 }else{
						mc.player.rotationYaw = rotations[0];
						mc.player.rotationPitch = rotations[1] + 1.0F;// 14
						
			}
		}
		}

		public static float[] getRotationsNeeded(Entity entity) {
			if (entity == null) {
				return null;
			}

			final double diffX = entity.posX - mc.player.posX;
			final double diffZ = entity.posZ - mc.player.posZ;
			double diffY;

			if (entity instanceof EntityLivingBase) {
				final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
				diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight());
			} else {
				diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (mc.player.posY + mc.player.getEyeHeight());
			}

			final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
			final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
			final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
			return new float[] { mc.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.player.rotationPitch) };
		}
	}