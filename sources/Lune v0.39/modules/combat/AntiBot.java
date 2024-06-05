package me.superskidder.lune.modules.combat;

import me.superskidder.lune.Lune;
import me.superskidder.lune.events.EventPacketReceive;
import me.superskidder.lune.events.EventPacketSend;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.guis.notification.Notification;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.utils.client.ClientUtils;
import me.superskidder.lune.utils.math.MathUtil;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.type.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.util.Timer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AntiBot extends Mod {
	public static Mode option =  new Mode("Mode", AntiMode.values(), AntiMode.Hypixel);
	public static ArrayList Bots = new ArrayList();
	private TimerUtil timer;
	int bots;
	private static List invalid = new ArrayList();
	private List<Packet> packetList = new CopyOnWriteArrayList();
	public static ArrayList bot = new ArrayList();
	private static List removed = new ArrayList();
	public TimerUtil lastRemoved = new TimerUtil();

	public static List onAirInvalid = new ArrayList();

	public AntiBot() {
		super("AntiBot", ModCategory.Combat,"Make killAura not to target bots");
		this.addValues(option);
		this.bots = 0;
		this.timer = new TimerUtil();

	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		this.setDisplayName(this.option.getValue().toString());
		if (this.option.getValue() == AntiMode.Hypixel2) {
			Iterator var3 = mc.theWorld.playerEntities.iterator();
			while (var3.hasNext()) {
				EntityPlayer entity = (EntityPlayer) var3.next();
				if (entity != mc.thePlayer && entity != null && entity instanceof EntityLivingBase) {
					if (entity != mc.thePlayer && entity instanceof EntityPlayer && !isInTablist(entity)
							&& !entity.getDisplayName().getFormattedText().toLowerCase().contains("[npc")
							&& entity.getDisplayName().getFormattedText().startsWith("\247")) {
						mc.theWorld.removeEntity(entity);
					}
				}
			}

			if (!removed.isEmpty() && this.lastRemoved.isDelayComplete(1000L)) {
				this.lastRemoved.reset();
				removed.clear();
			}
			var3 = mc.theWorld.getLoadedEntityList().iterator();

			while (var3.hasNext()) {
				Object o = var3.next();
				if (o instanceof EntityPlayer) {
					EntityPlayer ent = (EntityPlayer) o;
					if (ent != mc.thePlayer && !invalid.contains(ent)) {
						String formated = ent.getDisplayName().getFormattedText();
						String custom = ent.getCustomNameTag();
						String name = ent.getName();
						if (ent.isInvisible() && !formated.startsWith("\u00a7c") && formated.endsWith("\u00a7r")
								&& custom.equals(name)) {
							double diffX = Math.abs(ent.posX - mc.thePlayer.posX);
							double diffY = Math.abs(ent.posY - mc.thePlayer.posY);
							double diffZ = Math.abs(ent.posZ - mc.thePlayer.posZ);
							double diffH = Math.sqrt(diffX * diffX + diffZ * diffZ);
							if (diffY < 13.0D && diffY > 10.0D && diffH < 3.0D) {
								List list = getPlayerList();
								if (!list.contains(ent)) {
									this.lastRemoved.reset();
									removed.add(ent);
									mc.theWorld.removeEntity(ent);

									invalid.add(ent);
								}
							}
						}

						if (!formated.startsWith("\247") && formated.endsWith("\u00a7r")) {
							invalid.add(ent);
						}

						if (!isInTablist(ent)) {
							invalid.add(ent);
						}

						if (ent.isInvisible() && !custom.equalsIgnoreCase("") && custom.toLowerCase().contains("\u00a7c\u00a7c")
								&& name.contains("\u00a7c")) {
							this.lastRemoved.reset();
							removed.add(ent);
							mc.theWorld.removeEntity(ent);

							invalid.add(ent);
						}

						if (!custom.equalsIgnoreCase("") && custom.toLowerCase().contains("\u00a7c")
								&& custom.toLowerCase().contains("\u00a7r")) {
							this.lastRemoved.reset();
							removed.add(ent);
							mc.theWorld.removeEntity(ent);

							invalid.add(ent);
						}

						if (formated.contains("\u00a78[NPC]")) {
							invalid.add(ent);
						}

						if (!formated.contains("\u00a7c") && !custom.equalsIgnoreCase("")) {
							invalid.add(ent);
						}
					}
				}
			}

		}
		if (this.option.getValue() == AntiMode.Hypixel) {
			Iterator var4 = mc.theWorld.loadedEntityList.iterator();

			label63: while (true) {
				Object entity2;
				EntityPlayer entityPlayer;
				EntityPlayer ent;
				do {
					if (!var4.hasNext()) {
						this.bots = 0;
						var4 = mc.theWorld.getLoadedEntityList().iterator();

						while (var4.hasNext()) {
							Entity entity21 = (Entity) var4.next();
							if (entity21 instanceof EntityPlayer) {
								ent = entityPlayer = (EntityPlayer) entity21;
								if (entityPlayer != mc.thePlayer && ent.isInvisible() && ent.ticksExisted > 105) {
									if (getPlayerList().contains(ent)) {
										if (ent.isInvisible()) {
											ent.setInvisible(false);
										}
									} else {
										ent.setInvisible(false);
										++this.bots;
										mc.theWorld.removeEntity(ent);
									}
								}
							}
						}
						break label63;
					}

					entity2 = var4.next();
				} while (!(entity2 instanceof EntityPlayer));

				ent = entityPlayer = (EntityPlayer) entity2;
				if (entityPlayer != mc.thePlayer) {
					Minecraft mc4 = AntiBot.mc;
					if (mc.thePlayer.getDistanceToEntity(ent) < 10.0F
							&& (!ent.getDisplayName().getFormattedText().contains("ยง") || ent.isInvisible()
									|| ent.getDisplayName().getFormattedText().toLowerCase().contains("npc")
									|| ent.getDisplayName().getFormattedText().toLowerCase().contains("§r"))) {
						Bots.add(ent);
					}
				}

				if (Bots.contains(ent)) {
					Bots.remove(ent);
				}
			}
		}
		if (option.getValue() == AntiMode.MC233) {
			for (Entity ent : mc.theWorld.loadedEntityList) {
				if (ent instanceof EntityPlayer && ent != mc.thePlayer && !ent.onGround && ent.getDistanceToEntity(mc.thePlayer) < 8) {
					boolean isBot = false;
					ItemStack[] armor = ((EntityPlayer) ent).inventory.armorInventory;
					int index = 0;
					for (ItemStack itemStack : armor) {
						if (itemStack != null) {
							index++;
						}
					}
					if (index == 1 || index == 2 && ((EntityPlayer) ent).getTeam() != null) {
						isBot = true;
					}
					if (isBot) {
						ent.setInvisible(false);
						mc.theWorld.removeEntity(ent);
						ClientUtils.sendClientMessage("Removed one bot：" + ent.getName(), Notification.Type.SUCCESS);
						System.out.println("发现假人" + ent.getName());
					}
				}
			}
		}
	}

	@EventTarget
	public void onReceivePacket(EventPacketReceive event) {
		if (mc.theWorld == null) {
			return;
		}
		if (event.getPacket() instanceof S14PacketEntity) {
			S14PacketEntity packet = (S14PacketEntity) event.getPacket();
			Entity entity;
			if ((entity = packet.getEntity(mc.theWorld)) instanceof EntityPlayer && !packet.getOnGround()
					&& !onAirInvalid.contains(entity.getEntityId())) {
				onAirInvalid.add(entity.getEntityId());
			}
		}

	}

	public boolean isInGodMode(Entity entity) {
		return this.getState() && this.option.getValue() == AntiMode.Hypixel && entity.ticksExisted <= 25;
	}

	public static boolean isBot(Entity entity) {
		if (Lune.moduleManager.getModByClass(AntiBot.class).getState()) {
			if (option.getValue() == AntiMode.Hypixel2) {
				return invalid.contains(entity) || !onAirInvalid.contains(entity.getEntityId());
			}
			if (option.getValue() == AntiMode.Hypixel) {
				if (ModuleManager.getModByClass(AntiBot.class).getState() && option.getValue() == AntiMode.Hypixel) {
					if (entity.getDisplayName().getFormattedText().startsWith("\u00a7")
							&& !entity.getDisplayName().getFormattedText().toLowerCase().contains("[npc")) {
						return false;
					}
					return true;
				}
				return false;
			}

			if (option.getValue() == AntiMode.Mineplex) {
				Minecraft mc = AntiBot.mc;
				Iterator var4 = mc.theWorld.playerEntities.iterator();

				while (var4.hasNext()) {
					Object object = var4.next();
					EntityPlayer entityPlayer = (EntityPlayer) object;
					if (entityPlayer != null) {
						Minecraft mc2 = AntiBot.mc;
						if (entityPlayer != mc.thePlayer && (entityPlayer.getName().startsWith("Body #")
								|| entityPlayer.getMaxHealth() == 20.0F)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public void onEnable() {
		Bots.clear();
	}

	private void onPacketSend(EventPacketSend e) {
		if (e.getPacket() instanceof C00PacketKeepAlive) {
			if (mc.thePlayer.isEntityAlive()) {
				this.packetList.add(e.getPacket());
				e.setCancelled(true);
			}
		}
		double d = Timer.timerSpeed > 1.0f ? 750.0 : (double) (750.0f * Timer.timerSpeed);
		if (this.timer.hasReached(d)) {
			if (mc.theWorld != null && Minecraft.getMinecraft().getNetHandler() != null) {
				if (!this.packetList.isEmpty()) {
					int i = 0;
					double totalPackets = MathUtil.getIncremental((double) (Math.random() * 10.0), (double) 1.0);
					for (Packet packet : this.packetList) {
						if ((double) i >= totalPackets)
							continue;
						++i;
						mc.getNetHandler().getNetworkManager().sendPacket(packet);
						this.packetList.remove((Object) packet);
					}
				}
				mc.getNetHandler().getNetworkManager().sendPacket((Packet) new C00PacketKeepAlive(10000));
				this.timer.reset();
			}
		}
	}

	public void onDisable() {
		Bots.clear();
	}

	public static boolean isInTablist(EntityPlayer player) {
		if (Minecraft.getMinecraft().isSingleplayer()) {
			return true;
		}
		for (NetworkPlayerInfo o : Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()) {
			NetworkPlayerInfo playerInfo = o;
			if (!playerInfo.getGameProfile().getName().equalsIgnoreCase(player.getName()))
				continue;
			return true;
		}
		return false;
	}

	public List<EntityPlayer> getPlayerList() {
		Minecraft.getMinecraft();
		Collection<NetworkPlayerInfo> playerInfoMap = mc.thePlayer.sendQueue.getPlayerInfoMap();
		ArrayList<EntityPlayer> list = new ArrayList<EntityPlayer>();
		for (NetworkPlayerInfo networkPlayerInfo : playerInfoMap) {
			list.add(Minecraft.getMinecraft().theWorld
					.getPlayerEntityByName(networkPlayerInfo.getGameProfile().getName()));
		}
		return list;
	}

	enum AntiMode {
		Hypixel, Mineplex, Hypixel2, MC233;
	}
}
