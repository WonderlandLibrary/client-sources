package me.swezedcode.client.module.modules.Fight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;
import com.darkmagician6.eventapi.types.EventType;

import me.swezedcode.client.command.commands.CommandAura;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.MemeNames;
import me.swezedcode.client.module.modules.World.AntiBot;
import me.swezedcode.client.utils.AimUtils;
import me.swezedcode.client.utils.Angles;
import me.swezedcode.client.utils.AnglesUtils;
import me.swezedcode.client.utils.ModuleUtils;
import me.swezedcode.client.utils.Vector3D;
import me.swezedcode.client.utils.events.EventMotion;
import me.swezedcode.client.utils.timer.TimeUtil;
import me.swezedcode.client.utils.timer.Timer;
import me.swezedcode.client.utils.timer.TimerUtils;
import me.swezedcode.client.utils.values.BooleanValue;
import me.swezedcode.client.utils.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class KillAura extends Module {

	public static KillAura INSTANCE = new KillAura();

	private List<EntityLivingBase> entities;
	private EntityLivingBase target;
	private TimerUtils time = new TimerUtils();
	private TimerUtils time2 = new TimerUtils();
	private TimeUtil timer = new TimeUtil();
	private List<EntityLivingBase> loaded = new ArrayList<>();

	public KillAura() {
		super("KillAura", Keyboard.KEY_R, 0xFFFA3232, ModCategory.Fight);
		entities = new ArrayList<EntityLivingBase>();
	}

	public static int delay = 17;
	public static float reach = 4.25F;
	public static float rndDelay = 2F;
	public BooleanValue randomDelay = new BooleanValue(this, "Random Delay", "RandomDelay", Boolean.valueOf(false));
	public BooleanValue noSwing = new BooleanValue(this, "NoSwing", "noswing", Boolean.valueOf(false));
	public BooleanValue autoblock = new BooleanValue(this, "AutoBlock", "autoblock", Boolean.valueOf(true));
	public BooleanValue teams = new BooleanValue(this, "Teams", "teams", Boolean.valueOf(true));
	public BooleanValue ereach = new BooleanValue(this, "Reach Exploit", "reach_exploit", Boolean.valueOf(false));
	public BooleanValue jumpcrit = new BooleanValue(this, "HvH Crits", "criticals", Boolean.valueOf(false));

	public static String mode = "Switch";

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
	public void onUpdate(EventMotion event) {
		AntiBot antiBot = new AntiBot();
		if (mode.equalsIgnoreCase("Switch")) {
			if (!MemeNames.enabled) {
				setDisplayName(getName() + " §7Switch, Range: " + reach + ", APS: " + delay);
			} else {
				setDisplayName(
						"KillNiggersFasterThanKCFDeliversChicken" + " §7Switch Range: " + reach + " APS: " + delay);
			}
			boolean shouldBlock = autoblock.getValue() && mc.thePlayer.inventory.getCurrentItem() != null
					&& mc.thePlayer.inventory.getCurrentItem().getItem() != null
					&& mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
			boolean randomdelay = randomDelay.getValue() ? TimerUtils.hD(randomDelay() / rndDelay)
					: TimerUtils.hD(delay);
			if (event.getType() == EventType.PRE) {
				if (entities.isEmpty()) {
					for (Object object : mc.theWorld.loadedEntityList) {
						if (object instanceof EntityLivingBase) {
							EntityLivingBase entity = (EntityLivingBase) object;
							if (ereach.getValue() && (entity != Minecraft.thePlayer)
									&& ((entity instanceof EntityLivingBase))
									&& (Minecraft.thePlayer.getDistanceToEntity(entity) <= 20) && (!entity.isDead)
									&& (entity.isEntityAlive())) {
								mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld,
										mc.thePlayer.inventory.getCurrentItem());
								entities.add(entity);
							}
							if ((entity != Minecraft.thePlayer) && ((entity instanceof EntityLivingBase))
									&& (Minecraft.thePlayer.getDistanceToEntity(entity) <= reach) && (!entity.isDead)
									&& (entity.isEntityAlive())) {
								if (Manager.getManager().getFriendManager().isFriend(entity.getName())) {
									continue;
								}
								if (entity.getName().equals("Awago") || entity.getName().equals("ModuleManager")) {
									continue;
								}
								for (final EntityPlayer p : GuiPlayerTabOverlay.getPlayerList()) {
									if (p == null) {
										continue;
									}
								}
								if (entity.isPlayerSleeping()) {
									continue;
								}
								if (this.teams.getValue() && entity != null) {
									final String name = entity.getDisplayName().getFormattedText();
									final StringBuilder append = new StringBuilder().append("§");
									if (name.startsWith(
											append.append(mc.thePlayer.getDisplayName().getFormattedText().charAt(1))
													.toString())) {
										continue;
									}
								}
								entities.add(entity);
							}
						}
						entities.sort((o1, o2) -> {
							float[] rot1 = AimUtils.getRotations(o1);
							float[] rot2 = AimUtils.getRotations(o2);
							return (int) ((mc.thePlayer.rotationYaw - rot1[0]) % 360
									- (mc.thePlayer.rotationYaw - rot2[0]) % 360);
						});
					}
				}
				if (!entities.isEmpty()) {
					target = entities.get(0);
				}
				if (target != null) {
					setAngles(target);
					event.getLocation().setYaw(serverAngles.getYaw());
					event.getLocation()
							.setPitch(serverAngles.getPitch() + (mc.thePlayer.getDistanceToEntity(target)) * -0.15F);
				}
			} else {
				if (target != null && shouldBlock) {
					mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
				}
				if (target != null && randomdelay) {
					boolean wasBlocking = mc.thePlayer.isBlocking();
					if (wasBlocking) {
						mc.thePlayer.sendQueue.addToSendQueue(
								new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
										new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0)));
					}
					if (noSwing.getValue()) {
						if (mc.thePlayer.swingProgress <= 0 && !mc.thePlayer.isEating()) {
							mc.thePlayer.swingProgressInt = 5;
						}
					} else {
						mc.thePlayer.swingItem();
					}
					if (jumpcrit.getValue()) {
						if (mc.thePlayer.onGround) {
							mc.thePlayer.jump();
							mc.thePlayer.motionY -= 0.30000001192092896D;
						}
					}
					
					if (ModuleUtils.getMod(Criticals.class).isToggled()) {
						mc.thePlayer.onCriticalHit(target);
					}
					
					attack(target, true);

					float sharpLevel = EnchantmentHelper.func_152377_a(mc.thePlayer.getHeldItem(),
							mc.thePlayer.getCreatureAttribute());
					if (sharpLevel > 0) {
						mc.thePlayer.onEnchantmentCritical(target);
					}
					if (wasBlocking) {
						mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(0, 0, 0),
								255, mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
					}
					entities.remove(target);
					target = null;
					time.rt();
				}
			}
		} else if (mode.equalsIgnoreCase("Cosmic")) {
			setDisplayName(getName() + " §7Cosmic");
			boolean shouldBlock = autoblock.getValue() && mc.thePlayer.inventory.getCurrentItem() != null
					&& mc.thePlayer.inventory.getCurrentItem().getItem() != null
					&& mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
			if (event.getType() == EventType.PRE) {
				if (entities.isEmpty()) {
					for (Object object : mc.theWorld.loadedEntityList) {
						if (object instanceof EntityLivingBase) {
							EntityLivingBase entity = (EntityLivingBase) object;
							boolean teams = this.teams.getValue() && mc.thePlayer.isOnSameTeam(entity);
							if ((entity != Minecraft.thePlayer) && ((entity instanceof EntityLivingBase))
									&& (Minecraft.thePlayer.getDistanceToEntity(entity) <= 3) && (!entity.isDead)
									&& (entity.isEntityAlive()) && (mc.inGameHasFocus)) {
								if (Manager.getManager().getFriendManager().isFriend(entity.getName())) {
									continue;
								}
								if (entity.isPlayerSleeping()) {
									continue;
								}
								for (final EntityPlayer p : GuiPlayerTabOverlay.getPlayerList()) {
									if (p == null) {
										continue;
									}
								}
								entities.add(entity);
							}
						}
						entities.sort((o1, o2) -> {
							float[] rot1 = AimUtils.getRotations(o1);
							float[] rot2 = AimUtils.getRotations(o2);
							return (int) ((mc.thePlayer.rotationYaw - rot1[0]) % 360
									- (mc.thePlayer.rotationYaw - rot2[0]) % 360);
						});
					}
				}
				if (!entities.isEmpty()) {
					target = entities.get(0);
				}
				if (target != null) {
					setAngles(target);
					event.getLocation().setYaw(serverAngles.getYaw());
					event.getLocation()
							.setPitch(serverAngles.getPitch() + (mc.thePlayer.getDistanceToEntity(target) * -0.15F));
				}
			} else {
				if (target != null && time.hD(3)) {
					boolean wasBlocking = mc.thePlayer.isBlocking();
					if (wasBlocking) {
						mc.thePlayer.sendQueue.addToSendQueue(
								new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
										new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0)));
					}
					if (noSwing.getValue()) {
						if (mc.thePlayer.swingProgress <= 0 && !mc.thePlayer.isEating()) {
							mc.thePlayer.swingProgressInt = 5;
						}
					} else {
						mc.thePlayer.swingItem();
					}
					if (jumpcrit.getValue()) {
						if (mc.thePlayer.onGround) {
							mc.thePlayer.jump();
							mc.thePlayer.motionY -= 0.30000001192092896D;
						}
					}
					mc.thePlayer.sendQueue
							.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
					float sharpLevel = EnchantmentHelper.func_152377_a(mc.thePlayer.getHeldItem(),
							mc.thePlayer.getCreatureAttribute());
					if (sharpLevel > 0) {
						mc.thePlayer.onEnchantmentCritical(target);
					}
					if (wasBlocking) {
						mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(0, 0, 0),
								255, mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
					}
					entities.remove(target);
					target = null;
					time.rt();
				}
			}
		} else if (mode.equalsIgnoreCase("Tick")) {
			if (!MemeNames.enabled) {
				setDisplayName(getName() + " §7Tick, Range: " + "Auto" + ", APS: " + "Auto");
			} else {
				setDisplayName(
						"KillNiggersFasterThanKCFDeliversChicken" + " §7Tick Range: " + "Auto" + " APS: " + "Auto");
			}
			boolean shouldBlock = mc.thePlayer.inventory.getCurrentItem() != null
					&& mc.thePlayer.inventory.getCurrentItem().getItem() != null
					&& mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
			boolean randomdelay = randomDelay.getValue() ? TimerUtils.hD(randomDelay() / rndDelay)
					: TimerUtils.hD(delay);
			if (event.getType() == EventType.PRE) {
				if (entities.isEmpty()) {
					for (Object object : mc.theWorld.loadedEntityList) {
						if (object instanceof EntityLivingBase) {
							EntityLivingBase entity = (EntityLivingBase) object;
							if ((entity != Minecraft.thePlayer) && ((entity instanceof EntityLivingBase))
									&& (Minecraft.thePlayer.getDistanceToEntity(entity) <= reach) && (!entity.isDead)
									&& (entity.isEntityAlive())) {
								if (Manager.getManager().getFriendManager().isFriend(entity.getName())) {
									continue;
								}
								for (final EntityPlayer p : GuiPlayerTabOverlay.getPlayerList()) {
									if (p == null) {
										continue;
									}
								}
								if (entity.isPlayerSleeping()) {
									continue;
								}
								if (this.teams.getValue() && entity != null) {
									final String name = entity.getDisplayName().getFormattedText();
									final StringBuilder append = new StringBuilder().append("§");
									if (name.startsWith(
											append.append(mc.thePlayer.getDisplayName().getFormattedText().charAt(1))
													.toString())) {
										continue;
									}
								}
								entities.add(entity);
							}
						}
						entities.sort((o1, o2) -> {
							float[] rot1 = AimUtils.getRotations(o1);
							float[] rot2 = AimUtils.getRotations(o2);
							return (int) ((mc.thePlayer.rotationYaw - rot1[0]) % 360
									- (mc.thePlayer.rotationYaw - rot2[0]) % 360);
						});
					}
				}
				if (!entities.isEmpty()) {
					target = entities.get(0);
				}
				if (target != null) {
					if (AutoHeal.ticks < 0)
						return;
					setAngles(target);
					event.getLocation().setYaw(serverAngles.getYaw());
					event.getLocation()
							.setPitch(serverAngles.getPitch() + (mc.thePlayer.getDistanceToEntity(target) * -0.15F));
				}
			} else {
				int delay = 4;
				if (target != null && shouldBlock) {
					mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
				}
				if (target != null && TimerUtils.hD(4D)) {
					boolean wasBlocking = mc.thePlayer.isBlocking();
					if (wasBlocking) {
						mc.thePlayer.sendQueue.addToSendQueue(
								new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
										new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0)));
					}

					swap(9, mc.thePlayer.inventory.currentItem);
					attack(target, true);
					swap(9, mc.thePlayer.inventory.currentItem);
					attack(target, true);

					if (jumpcrit.getValue()) {
						if (mc.thePlayer.onGround) {
							mc.thePlayer.jump();
							mc.thePlayer.motionY -= 0.30000001192092896D;
						}
					}
					
					if (ModuleUtils.getMod(Criticals.class).isToggled()) {
						mc.thePlayer.onCriticalHit(target);
					}

					float sharpLevel = EnchantmentHelper.func_152377_a(mc.thePlayer.getHeldItem(),
							mc.thePlayer.getCreatureAttribute());
					if (sharpLevel > 0) {
						mc.thePlayer.onEnchantmentCritical(target);
					}
					if (mc.thePlayer.isBlocking() || mc.gameSettings.keyBindUseItem.getIsKeyPressed()) {
						mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld,
								mc.thePlayer.getCurrentEquippedItem());
					}
					if (wasBlocking) {
						mc.thePlayer.sendQueue.addToSendQueue(
								new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
										new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0)));
					}

					entities.remove(target);
					target = null;
					time.rt();
				}
			}
		}
	}

	private void attack(EntityLivingBase ent, boolean critical) {
		mc.thePlayer.swingItem();
		if (critical) {
			// mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
		} else {
			// mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
		}
		mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(ent, C02PacketUseEntity.Action.ATTACK));
	}

	private void swap(int slot, int hotbarNum) {
		mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
	}

	private int randomDelay() {
		Random randy = new Random();
		int randyInt = randy.nextInt(15);
		return (int) randyInt;
	}

}
