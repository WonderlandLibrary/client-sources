package Hydro.module.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.event.Event;
import Hydro.event.events.*;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.module.ModuleManager;
import Hydro.notification.NotificationManager;
import Hydro.notification.Type;
import Hydro.util.*;
import Hydro.util.font.FontUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Aura extends Module {

	public Timer timer = new Timer();
		
	public boolean blocking;

    public static EntityLivingBase target;

	public double ticksExisted;
	private boolean setupTick;
	private boolean switchingTargets;
	public List<EntityLivingBase> targets;
	public int index;
	public static boolean attacking = false;
	private int test;
	private float animtest;
	private boolean anim;
	public boolean canSendNotification;
    
	public Aura() {
		super("Aura", Keyboard.KEY_R, true, Category.COMBAT, "Attacks players around you");
		ArrayList<String> options = new ArrayList<>();
		options.add("No rotations");
		options.add("Watchdog");
		Client.instance.settingsManager.rSetting(new Setting("AuraMode", "Mode", this, "Watchdog", options));
		Client.instance.settingsManager.rSetting(new Setting("auraaps", "APS", this, 20, 1, 20, true));
		Client.instance.settingsManager.rSetting(new Setting("AuraRange", "Range", this, 4.4, 1, 7, true));
		Client.instance.settingsManager.rSetting(new Setting("auraswing", "Swing", this, true));
		Client.instance.settingsManager.rSetting(new Setting("aurablock", "AutoBlock", this, false));
		//Client.instance.settingsManager.rSetting(new Setting("AuraCircle", "Target ESP", this, false));
		Client.instance.settingsManager.rSetting(new Setting("AuraCriticals", "Criticals", this, false));
		Client.instance.settingsManager.rSetting(new Setting("AuraRangeCircle", "Range Circle", this, false));
		Client.instance.settingsManager.rSetting(new Setting("AuraRangeCircleHeight", "Range Circle Height", this, 1, 0.1, 2, false));

		this.ticksExisted = 10.0;
		this.timer = new Timer();
		this.targets = new ArrayList<EntityLivingBase>();
	}
    	
	@Override
	public void onEvent(Event e) {
		if(Client.instance.settingsManager.getSettingByName("AuraMode").getValString().equals("No rotations")){
			this.displayName = "Aura " + EnumChatFormatting.GRAY + "No Rotations";
			if(e instanceof EventMotion) {
				List<Entity> targets = (List<Entity>) Minecraft.theWorld.loadedEntityList.stream().filter(Entity.class::isInstance).collect(Collectors.toList());

				targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < Client.settingsManager.getSettingByName("AuraRange").getValDouble() && entity != mc.thePlayer).collect(Collectors.toList());

				targets.sort(Comparator.comparingDouble(entity -> ((Entity)entity).getDistanceToEntity(mc.thePlayer)));

				targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());

				if(!targets.isEmpty()) {
					Entity target = targets.get(0);

					if(!Client.instance.friendManager.isFriend(target.getName())) {
						if(timer.hasTimeElapsed((long) (1000 / Client.instance.settingsManager.getSettingByName("auraaps").getValDouble()), true)) {

							if(Client.instance.settingsManager.getSettingByName("auraswing").getValBoolean()) {
								mc.thePlayer.swingItem();
							}else {
								mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
							}

							mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, Action.ATTACK));
						}

						if(!(mc.thePlayer.inventory.getCurrentItem() == null) && Client.settingsManager.getSettingByName("aurablock").getValBoolean()) {
							if(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
								mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), 20);
							}
						}
					}
				}

			}

		}

		if(e instanceof EventUpdate) {
			if(mc.thePlayer.ticksExisted <= 1) {
				NotificationManager.notificationManager.createNotification("Warning", "Aura disabled due to respawn", true, (long) 3.5f, Type.WARNING, Hydro.notification.Color.YELLOW);
				Client.moduleManager.getModuleByName("Aura").toggle();
			}
		}

		if(e instanceof EventRenderGUI){
			if(Client.moduleManager.getModuleByName("TargetHUD").isEnabled()){
				List<Entity> targets = (List<Entity>) Minecraft.theWorld.loadedEntityList.stream().filter(Objects::nonNull).collect(Collectors.toList());
				targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < Client.settingsManager.getSettingByName("AuraRange").getValDouble() && entity != mc.thePlayer).collect(Collectors.toList());
				targets.sort(Comparator.comparingDouble(entity -> ((Entity)entity).getDistanceToEntity(mc.thePlayer)));
				targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());

				//Dont do anything if there is no targets
				if(targets.isEmpty())
					return;

				//the target that is displayed
				target = (EntityLivingBase) targets.get(0);

				//Max Health
				double maxHealth = 130.0f / this.target.getMaxHealth();

				//Render code
				if(!target.isInvisibleToPlayer(mc.thePlayer)){
					Gui.drawRect(Client.settingsManager.getSettingByName("TargetHUDX").getValDouble(), Client.settingsManager.getSettingByName("TargetHUDY").getValDouble(), Client.settingsManager.getSettingByName("TargetHUDX").getValDouble() + (int)(target.getHealth() * maxHealth) + 3, Client.settingsManager.getSettingByName("TargetHUDY").getValDouble() + 2, getColorByHealth(target.getHealth()));
					Gui.drawRect(Client.settingsManager.getSettingByName("TargetHUDX").getValDouble(), Client.settingsManager.getSettingByName("TargetHUDY").getValDouble(), Client.settingsManager.getSettingByName("TargetHUDX").getValDouble() + 133, Client.settingsManager.getSettingByName("TargetHUDY").getValDouble() - 40, -1879048192);
					FontUtil.arrayList.drawString(target.getName(), Client.settingsManager.getSettingByName("TargetHUDX").getValDouble() + 4, (float) (Client.settingsManager.getSettingByName("TargetHUDY").getValDouble() - 38), -1);
					FontUtil.regular.drawString(String.valueOf((int)target.getHealth()) + EnumChatFormatting.WHITE + " HP", ((int) Client.settingsManager.getSettingByName("TargetHUDX").getValDouble() + 2), ((int) Client.settingsManager.getSettingByName("TargetHUDY").getValDouble() - 15), getColorByHealth(target.getHealth()));
					//FontUtil.arrayList.drawString(getItemName(target.getHeldItem()), Client.settingsManager.getSettingByName("TargetHUDX").getValDouble() + 35, (float) (Client.settingsManager.getSettingByName("TargetHUDY").getValDouble() - 25), -1);
				}
			}
		}



		if(Client.instance.settingsManager.getSettingByName("AuraMode").getValString().equals("Watchdog")){
			this.displayName = "Aura " + EnumChatFormatting.GRAY + "Watchdog";

			if(e instanceof EventPacket && ((EventPacket) e).isSending()){
				EventPacket event = (EventPacket) e;
				Packet<?> packet = event.getPacket();
				if (packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) {

					final float[] rotations = RotationUtils.getRotations(target);

					C03PacketPlayer.C06PacketPlayerPosLook C03 = new C03PacketPlayer.C06PacketPlayerPosLook();

					C03.setYaw(rotations[0]);
					C03.setPitch(rotations[1]);
				}
			}

			if(e instanceof EventMotion){
				if(e.isPre()){
					StateManager.setOffsetLastPacketAura(false);
					if (this.index >= this.targets.size()) {
						this.index = 0;
					}

					List<Entity> targets = (List<Entity>) Minecraft.theWorld.loadedEntityList.stream().filter(Entity.class::isInstance).collect(Collectors.toList());

					targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < Client.settingsManager.getSettingByName("AuraRange").getValDouble() && entity != mc.thePlayer).collect(Collectors.toList());

					targets.sort(Comparator.comparingDouble(entity -> ((Entity)entity).getDistanceToEntity(mc.thePlayer)));

					targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());

					if (!targets.isEmpty()) {
						target = (EntityLivingBase) targets.get(0);
						if (target != null) {
							if (Client.settingsManager.getSettingByName("aurablock").getValBoolean() && Wrapper.player().getCurrentEquippedItem() != null && Wrapper.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
								Wrapper.playerController().sendUseItem(Wrapper.player(), Minecraft.theWorld, Wrapper.player().getCurrentEquippedItem());
							}
							final float[] rotations = RotationUtils.getRotations(target);
							//mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, rotations[0], mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
							//mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, rotations[1], mc.thePlayer.onGround));
							mc.thePlayer.rotationYawHead = rotations[0];
							mc.thePlayer.renderYawOffset = rotations[0];
							mc.thePlayer.rotationPitchHead = rotations[1];
						}

						if (this.setupTick) {
							EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
							if (this.targets.size() > 0 && Client.settingsManager.getSettingByName("AuraCriticals").getValBoolean() && Wrapper.player().isCollidedVertically) {
								StateManager.setOffsetLastPacketAura(true);
								((EventMotion) e).setY(((EventMotion) e).getY() + 0.07);
								((EventMotion) e).setOnGround(false);
							}
							if (this.timer.delay((float) Math.random())) {
								this.incrementIndex();
								this.switchingTargets = true;
								this.timer.reset();
							}
						}
						else {
							if (this.targets.size() > 0 && Client.settingsManager.getSettingByName("criticals").getValBoolean() && Wrapper.player().isCollidedVertically && this.bhopCheck()) {
								((EventMotion) e).setOnGround(false);
							}
							if (Wrapper.player().fallDistance > 0.0f && Wrapper.player().fallDistance < 0.66) {
								((EventMotion) e).setOnGround(true);
							}
						}
					} else {
						canSendNotification = false;
					}
					this.setupTick = !this.setupTick;
				}
				if(e.isPost()){
					List<Entity> targets = (List<Entity>) Minecraft.theWorld.loadedEntityList.stream().filter(Entity.class::isInstance).collect(Collectors.toList());

					targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < Client.settingsManager.getSettingByName("AuraRange").getValDouble() && entity != mc.thePlayer).collect(Collectors.toList());

					targets.sort(Comparator.comparingDouble(entity -> ((Entity)entity).getDistanceToEntity(mc.thePlayer)));

					targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());
					if (!this.setupTick) {
						return;
					}
					if (Wrapper.player().isBlocking()) {
						mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
					}
					if(!targets.isEmpty()){
						Entity target = targets.get(0);
						attack((EntityLivingBase) target);
					}
					if (Wrapper.player().isBlocking()) {
						mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Wrapper.player().inventory.getCurrentItem()));
					}
				}
			}
		}

		if(Client.settingsManager.getSettingByName("AuraRangeCircle").getValBoolean()){
			if(e instanceof EventRender3D){
				this.drawCircle(mc.thePlayer, ((EventRender3D) e).getTicks(), Client.settingsManager.getSettingByName("AuraRange").getValDouble(), Client.settingsManager.getSettingByName("AuraRangeCircleHeight").getValDouble());
			}
		}

	}

	public String getItemName(ItemStack item){
		if(item != null){
			return item.getDisplayName();
		}
		return "No item";
	}

	public int getColorByHealth(double health){
		if(health > 15){
			return 0xff33ff00; //Green
		}else if(health >= 10){
			return 0xffccff00; //Yellow
		}else{
			return 0xffff2600; //Red
		}
	}

	public void attack(final EntityLivingBase entity) {
		this.attack(entity, Client.settingsManager.getSettingByName("AuraCriticals").getValBoolean());
	}

	public void attack(final EntityLivingBase entity, final boolean crit) {
		attacking = true;
		mc.thePlayer.swingItem();
		final float sharpLevel = EnchantmentHelper.getModifierForCreature(Wrapper.player().getHeldItem(), entity.getCreatureAttribute());
		final boolean vanillaCrit = Wrapper.player().fallDistance > 0.0f && !Wrapper.player().onGround && !Wrapper.player().isOnLadder() && !Wrapper.player().isInWater() && !Wrapper.player().isPotionActive(Potion.blindness) && Wrapper.player().ridingEntity == null;
		mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
		if (crit || vanillaCrit) {
			Wrapper.player().onCriticalHit(entity);
		}
		if (sharpLevel > 0.0f) {
			Wrapper.player().onEnchantmentCritical(entity);
		}
	}

	private void incrementIndex() {
		++this.index;
		if (this.index >= this.targets.size()) {
			this.index = 0;
		}
	}

	private boolean bhopCheck() {
		if (Client.moduleManager.getModuleByName("Speed").isEnabled()) {
			return Wrapper.player().moveForward == 0.0f && Wrapper.player().moveStrafing == 0.0f;
		}
		return true;
	}
	
	private void unBlock() {
	  mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
	          C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
	          new BlockPos(-1, -1, -1),
	          EnumFacing.DOWN));
	}

	  private void block() {
	      double value = -1;
	      mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(value, value, value), 255, mc.thePlayer.inventory.getCurrentItem(), 0, 0, 0));
	  }

    private boolean isHoldingSword() {
    	if(mc.thePlayer == null)
    		return false;
        return mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }

	private void drawCircleTranslating(final Entity entity, final float partialTicks, final double rad) {
		GL11.glPushMatrix();
		GL11.glDisable(3553);
		GLUtils.startSmooth();
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glLineWidth(2f);
		GL11.glBegin(3);
		final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
		final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
		final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;
		final float r = 0.003921569f * Color.WHITE.getRed();
		final float g = 0.003921569f * Color.WHITE.getGreen();
		final float b = 0.003921569f * Color.WHITE.getBlue();

		if (this.test <= 10) {
			if (this.anim) {
				this.animtest += 0.01F;
			} else {
				this.animtest -= 0.01F;
			}
			this.test = 10;
		}
		this.test--;
		if (this.animtest <= y) {
			this.anim = true;
		} else if (this.animtest >= y + entity.getEyeHeight() + 0.25) {
			this.anim = false;
		}
		for (int i = 0; i <= 90; ++i) {
			GL11.glColor4f(r, g, b,i);
			GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586 / 45.0), animtest, z + rad * Math.sin(i * 6.283185307179586 / 45.0));
		}
		GL11.glEnd();
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GLUtils.endSmooth();
		GL11.glEnable(3553);
		GL11.glPopMatrix();
	}

	private void drawCircle(final Entity entity, final float partialTicks, final double rad, double height) {
		GL11.glPushMatrix();
		GL11.glDisable(3553);
		GLUtils.startSmooth();
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glLineWidth(2f);
		GL11.glBegin(3);
		final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
		final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
		final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;
		final float r = 0.003921569f * Color.WHITE.getRed();
		final float g = 0.003921569f * Color.WHITE.getGreen();
		final float b = 0.003921569f * Color.WHITE.getBlue();

		this.animtest = (float) height;

		for (int i = 0; i <= 90; ++i) {
			GL11.glColor4f(r, g, b,i);
			GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586 / 45.0), animtest, z + rad * Math.sin(i * 6.283185307179586 / 45.0));
		}

		GL11.glEnd();
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GLUtils.endSmooth();
		GL11.glEnable(3553);
		GL11.glPopMatrix();
	}
    
}
