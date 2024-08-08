package me.xatzdevelopments.modules.combat;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventRenderGUI;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.ui.TargetHUDMaker;
import me.xatzdevelopments.util.AnimationUtils;
import me.xatzdevelopments.util.ColorUtils;
import me.xatzdevelopments.util.KillauraUtil;
import me.xatzdevelopments.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemSword;

public class KillAura extends Module {

	float rotationYaw = 0;
    float rotationPitch = 0;
	public Timer timer = new Timer();
	public NumberSetting range = new NumberSetting("Range", 4, 1, 6, 0.1);
	public NumberSetting aps = new NumberSetting("APS", 10, 1, 20, 1);
	public NumberSetting yawspeed = new NumberSetting("YawSpeed", 90, 1, 180, 1);
    public NumberSetting pitchspeed = new NumberSetting("PitchSpeed", 90, 1, 180, 1);
    public NumberSetting fov = new NumberSetting("FOV", 180, 1, 180, 1);
	public BooleanSetting noSwing = new BooleanSetting("NoSwing", false);
	public BooleanSetting autoblockbool = new BooleanSetting("Autoblock", false);
	public ModeSetting test = new ModeSetting("Test", "One", "One", "Two", "Three");
	public ModeSetting rotations = new ModeSetting("Rotations", "Vanilla", "Vanilla", "Smooth", "Test");
	public ModeSetting autoblock = new ModeSetting("AutoBlock", "Normal", "Normal", "NCP", "Legit");
	public static String health = "";
	public static double maxHealth = 0;
	public static String name = "";
	public static String distance = "";
	public static double health2 = 0;
	public static EntityLivingBase target;
	public static boolean isattacking = false;
	public static boolean isAttacking = false;
	public int healthcolor = -1;
	EntityLivingBase idk = null;

	public KillAura() {
		super("KillAura", Keyboard.KEY_G, Category.COMBAT, "Attacks Entities For You");
		this.addSettings(range, aps, noSwing, test, rotations, autoblock, autoblockbool, yawspeed, pitchspeed, fov);
	}

	public void onEnable() {

	}

	public void onDisable() {
		Xatz.overridePitch = false;
	}


	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(this.isattacking) {
				Xatz.overridePitch = true;
			} else {
				Xatz.overridePitch = false;
			}
		}
		if(e instanceof EventMotion) {
			if(e.isPre()) {

				EventMotion event = (EventMotion)e;

				List<EntityLivingBase> targets = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());

				targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getValue() && entity != mc.thePlayer && !entity.isDead && entity.getHealth() > 0).collect(Collectors.toList());

				targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));

List<EntityLivingBase> targets2 = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());

				targets2 = targets2.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < 8 && entity != mc.thePlayer && !entity.isDead && entity.getHealth() > 0).collect(Collectors.toList());

				targets2.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));

				if(!targets2.isEmpty()) {
					this.isattacking = true;
					EntityLivingBase target2 = targets2.get(0);
					this.target = target2;
					idk = target2;
					if(autoblockbool.isEnabled()) {
					if(this.autoblock.getMode().equals("NCP")) {
						if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
							mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), 71626);
						}
					}else if(this.autoblock.getMode().equals("Normal")) {
						if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
							mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
							mc.thePlayer.setItemInUse(this.mc.thePlayer.getHeldItem(),
									this.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
						}
					 }else if(this.autoblock.getMode().equals("Legit")) {
						 if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
							 mc.playerController.syncCurrentPlayItem();
				             mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
				               
						 }
					 }
					}
					if(this.isattacking) {
				        if (this.target.getHealth() > 8 && this.target.getHealth() < 10) {
				            healthcolor = 0xff0ac713;
				        }
				        else if (this.target.getHealth() > 5 && this.target.getHealth() < 8) {
				            healthcolor = 0xff85c70a;
				        }
				        else if (this.target.getHealth() > 3 && this.target.getHealth() < 5) {
				            healthcolor = 0xffc7980a;
				        }
				        else {
				            healthcolor = 0xffc7590a;
				        }
			        }else if(!this.isattacking) {
			        	healthcolor = -1;



			    }
					String healthstr = String.valueOf((float)((int)target2.getHealth()) / 2.0F);
					this.health = "" + healthstr;
					this.name = "" + target2.getName();
					this.health2 = target2.getHealth();
					this.distance = "Distance: " + (double)Math.round(mc.thePlayer.getDistanceToEntity(target2) * 100) / 100;
				} else {
					this.target = null;
					this.health = "0.0";
					this.name = "Unknown";
					this.distance = "Distance: 0.0";
					this.target = null;
					this.maxHealth =0;
					this.health2 = 0.0;
					this.isattacking = false;
					healthcolor = -1;
					idk = null;
				}
				//Players filter
				//targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());
				//Animals filter
				//targets = targets.stream().filter(EntityAnimal.class::isInstance).collect(Collectors.toList());
				//Hostile filter
				//targets = targets.stream().filter(EntityMob.class::isInstance).collect(Collectors.toList());

				if(!targets.isEmpty()) {
					this.isAttacking = true;
					TargetHUDMaker thud = new TargetHUDMaker();
					thud.animationStopwatch.reset();
					EntityLivingBase target = targets.get(0);
					/*String healthstr = String.valueOf((float)((int)target.getHealth()) / 2.0F);
					this.health = "" + healthstr;
					this.name = "" + target.getName();
					this.health2 = target.getHealth();
					this.distance = "Distance: " + (double)Math.round(mc.thePlayer.getDistanceToEntity(target) * 100) / 100;*/
					if(rotations.getMode().equals("Vanilla")) {
						event.setYaw(this.getRotationsNeeded(target)[0]);
						event.setPitch(this.getRotationsNeeded(target)[1]);
					}else if(rotations.getMode().equals("Smooth")) {
						event.setRotation(rotationYaw, rotationPitch);
					}else if(rotations.getMode().equals("Test")) {
						event.setYaw(this.testRots(target)[0]);
						event.setPitch(this.testRots(target)[1]);
					}
					mc.thePlayer.renderYawOffset = event.getYaw();
					mc.thePlayer.rotationYawHead = event.getYaw();
					Xatz.overridePitch = true;
					//Xatz.overrideOverridePitch = true;
					mc.thePlayer.rotationPitchHead = event.getPitch();


				        /*if(this.isattacking) {
					        if (target.getHealth() > 8 && target.getHealth() < 10) {
					            healthcolor = 0x0ac713;
					        }
					        else if (target.getHealth() > 5 && target.getHealth() < 8) {
					            healthcolor = 0x85c70a;
					        }
					        else if (target.getHealth() > 3 && target.getHealth() < 5) {
					            healthcolor = 0xc7980a;
					        }
					        else {
					            healthcolor = 0xc7590a;
					        }
				        }else if(!this.isattacking) {
				        	healthcolor = -1;



				    }*/
					if(timer.hasTimeElapsed((long) (1000/aps.getValue()), true)) {
					 if(noSwing.isEnabled()) {
						mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
					 }else {
					    mc.thePlayer.swingItem();
					 }
					mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, Action.ATTACK));
					/*this.isattacking = true;
					this.target = target;
					this.maxHealth = target.getMaxHealth();

					//mc.fontRendererObj.drawString(KillAura.health, thud.x2 + 40.0F + 46.0F - (float)mc.fontRendererObj.getStringWidth(KillAura.health) / 2.0F, mc.displayHeight / 4 + 15 + 16.0F, -1);
			        mc.fontRendererObj.drawStringwithShadow(this.target.getName(), thud.x2 + 40.0F, mc.displayHeight / 4 + 15 + 2.0F, -1);*/
					}
				} else {
					this.isAttacking = false;
					//Xatz.overridePitch = false;
					//Xatz.overrideOverridePitch = false;
					if(Xatz.getModuleByName("Annoy").isEnabled())
					mc.thePlayer.rotationPitchHead = (float)Xatz.getModuleByName("Annoy").getNumberSetting("Pitch").getValue();
					TargetHUDMaker thud = new TargetHUDMaker();
					thud.animationStopwatch.reset();
					//mc.fontRendererObj.drawString(KillAura.health, thud.x + 40.0F + 46.0F - (float)mc.fontRendererObj.getStringWidth(KillAura.health) / 2.0F, mc.displayHeight / 4 + 15 + 16.0F, -1);
			        mc.fontRendererObj.drawString(KillAura.name, thud.x2 + 40.0F, mc.displayHeight / 4 + 15 + 2.0F, -1);

					/*this.health = "0.0";
					this.name = "Unknown";
					this.distance = "Distance: 0.0";
					this.target = null;
					this.maxHealth = 0.1;
					this.health2 = 0.1;*/
				}


			}


		}
		
		if(e instanceof EventRenderGUI) {
			if(Xatz.getModuleByName("TargetHUD").isEnabled()) {
				TargetHUDMaker thud = new TargetHUDMaker();
				double healthBarWidth = health2 * 3;
				float width = 140.0F;
	            float height = 40.0F;
	            float xOffset = 40.0F;
	            float x = mc.displayWidth / 2.0F - 70.0F;
	            float y = mc.displayHeight / 2.0F + 80.0F;
	            double hpPercentage = (health2 / maxHealth);
	            double hpPercentage2;
	            hpPercentage2 = MathHelper.clamp_double(hpPercentage, 0.0D, 1.0D);
	            double hpWidth = 92.0D * hpPercentage2;
	            //int healthColor = ColorUtils.getHealthColor((float)this.health2, (float)this.maxHealth).getRGB();
	            String healthStr = String.valueOf((float)((int)this.maxHealth) / 2.0F);
			/*TargetHUDMaker thud = new TargetHUDMaker();
			final Color COLOR = new Color(0, 0, 0, 180);
			Gui.drawRect((double)thud.x, (double)mc.displayHeight / 4 + 15, (double)(thud.x + 140.0F), (double)(mc.displayHeight / 4 + 15 + 40.0F), COLOR.getRGB());
	        Gui.drawRect((double)(thud.x + 40.0F), (double)(mc.displayHeight / 4 + 15 + 45.0F), (double)(thud.x + 40.0F) + thud.healthBarWidth, (double)(mc.displayHeight / 4 + 15 + 25.0F), thud.healthColor);
			//mc.fontRendererObj.drawString(KillAura.health, thud.x2 - 1f + 40.0F + 46.0F - (float)mc.fontRendererObj.getStringWidth(KillAura.health) / 2.0F, mc.displayHeight / 4 + 15 + 16.0F, -1);
			//Gui.drawRect((double)thud.x, (double)mc.displayHeight / 4 + 15, (double)(thud.x + 140.0F), (double)(mc.displayHeight / 4 + 15 + 40.0F), COLOR.getRGB());
	        Gui.drawRect((double)(thud.x + 40.0F), (double)(mc.displayHeight / 4 + 15 + 45.0F), (double)(thud.x + 40.0F + KillAura.health2) + thud.healthBarWidth, (double)(mc.displayHeight / 4 + 15 - 40), -1);
	        mc.fontRendererObj.drawString(KillAura.name, thud.x2 + 40.0F, mc.displayHeight / 4 + 15 + 2.0F, this.healthcolor);
	        mc.fontRendererObj.drawString(KillAura.health, thud.x2 + 40.0F, mc.displayHeight / 4 + 15 + 16.0F, this.healthcolor);
	        mc.fontRendererObj.drawString(KillAura.distance, thud.x2 + 40.0F, mc.displayHeight / 4 + 15 + 30.0F, this.healthcolor);*/

	            	//GuiInventory.drawEntityOnScreen((int)(thud.x + 13.333333F), (int)(thud.y + 30.0F), 20, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer);
	            //}
	            if(this.isattacking) {
	            	Gui.drawRect((double)mc.displayWidth / 4 - 95 - 5, (double)mc.displayHeight / 4 + 15, (double)(mc.displayWidth / 4 - 55 + 125.0F), (double)(mc.displayHeight / 4 + 15 + 40.0F + 10), 0xEE111111);
		            Gui.drawRect((double)(mc.displayWidth / 4 - 55 - 5), (double)(mc.displayHeight / 4 + 15 + 15.0F), (double)(mc.displayWidth / 4 - 55 - 5.0F) + healthBarWidth, (double)(mc.displayHeight / 4 + 15 + 27.0F), healthcolor);
		            mc.fontRendererObj.drawStringWithShadow(this.health, /*thud.x + 40.0F + 46.0F - (float)mc.fontRendererObj.getStringWidth(this.health) / 2.0F*/ mc.displayWidth / 4 - 55 + 50, mc.displayHeight / 4 + 15 + 16.0F, -1);
		            mc.fontRendererObj.drawStringWithShadow(this.name, /*thud.x + 40.0F*/ mc.displayWidth / 4 - 55, mc.displayHeight / 4 + 15 + 2.0F, -1);
		            if(idk != null) {
		            GuiInventory.drawEntityOnScreen((int)(mc.displayWidth / 4 - 76), (int)(mc.displayHeight / 4 + 60.0F), 20, idk.rotationYaw, idk.rotationPitch, idk);
		            }
	            //GuiInventory.drawEntityOnScreen((int)(x + 13.333333F), (int)(y + 40.0F), 20, this.target.rotationYaw, this.target.rotationPitch, this.target);
	            }
				//mc.fontRendererObj.drawString(this.name, 165, 50, -1);
				//mc.fontRendererObj.drawString(this.health, 165, 40, -1);
				//TargetHUDMaker hud = new TargetHUDMaker();
				//hud.makeTargetHUD();
			}
			if(Xatz.getModuleByName("SuperHeroFX").isEnabled()) {
				if(this.isattacking && idk.hurtTime > 0) {
					Xatz.fxManager.addTextFx(idk, "test");
				}
				if(!this.isattacking) {
					Xatz.fxManager.resetFxList();
				}
			}
			if(this.test.getMode().equals("Two")) {
				//mc.fontRendererObj.drawString(this.health, 50, 50, -1);
			}
		}
	}

	public float[] getRotations(Entity e) {
		double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
			   deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
			   deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
			   distance = Math.sqrt(Math.pow(deltaX, 2) + (Math.pow(deltaZ, 2)));

		float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
			  pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

		if(deltaX < 0 && deltaZ < 0) {
			yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
		}else if(deltaX > 0 && deltaZ > 0) {
			yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
		}

		return new float[] {yaw, pitch};
	}

	private int getHealthColor() {
        int color = -1;
        if(this.isattacking) {
	        if (target.getHealth() > 8) {
	            color = 0x23c70a;
	        }
	        else if (target.getHealth() > 5) {
	            color = 0xc7be0a;
	        }
	        else if (target.getHealth() > 3) {
	            color = 0xc76f0a;
	        }
	        else {
	            color = 0xc7360a;
	        }
        }else if(!this.isattacking) {
        	color = -1;
        }
		return color;

    }

	
	
	public float[] testRots(Entity entity) {
		float[] rots = getRotationsNeeded(entity);
		return new float[] {rots[0] + randomNumber(3, -3), rots[1] + randomNumber(3, -3)};
	}

	public void setRotations() {
        float[] smoothRotations = KillauraUtil.faceEntitySmooth(rotationYaw, rotationPitch, KillauraUtil.rotations(target)[0], KillauraUtil.rotations(target)[1], yawspeed.getValue(), pitchspeed.getValue());
        rotationYaw = KillauraUtil.updateRotation(mc.thePlayer.rotationYaw, smoothRotations[0], (float) fov.getValue());
        rotationPitch = smoothRotations[1];
        if(rotationPitch > 90) {
            rotationPitch = 90;
        } else if (rotationPitch < -90) {
            rotationPitch = -90;
        }
    }
	
	public static int randomNumber(int max, int min) {
        return Math.round(min + (float) Math.random() * ((max - min)));
    }
	
	public float[] getRotationsNeeded(Entity entity) {
		if (entity == null) {
			return null;
		}

		final double diffX = entity.posX - mc.thePlayer.posX;
		final double diffZ = entity.posZ - mc.thePlayer.posZ;
		double diffY;

		if (entity instanceof EntityLivingBase) {
			final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
		} else {
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
		}

		final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
		return new float[] { mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch) };
	}
}
