package none.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import none.Client;
import none.event.Event;
import none.event.EventSystem;
import none.event.RegisterEvent;
import none.event.events.Event2D;
import none.event.events.Event3D;
import none.event.events.EventAttack;
import none.event.events.EventChat;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventStep;
import none.event.events.EventTick;
import none.event.events.StrafeEvent;
import none.fontRenderer.xdolf.Fonts;
import none.friend.FriendManager;
import none.module.Category;
import none.module.Module;
import none.module.modules.movement.Fly;
import none.module.modules.movement.LiquidWalk;
import none.module.modules.player.Regen;
import none.module.modules.render.ClientColor;
import none.module.modules.render.Esp;
import none.module.modules.world.Cheststealer;
import none.module.modules.world.Scaffold;
import none.notifications.Notification;
import none.notifications.NotificationType;
import none.utils.MoveUtils;
import none.utils.RayCastUtil;
import none.utils.RenderingUtil;
import none.utils.RotationUtils;
import none.utils.Targeter;
import none.utils.TeamUtils;
import none.utils.TimeHelper;
import none.utils.Utils;
import none.utils.Block.BlockUtils;
import none.utils.render.Colors;
import none.utils.render.TTFFontRenderer;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class Killaura extends Module {

	public Killaura() {
		super("Killaura", "Killaura", Category.COMBAT, Keyboard.KEY_R);
	}

	public static EntityLivingBase targeter;
	public EntityLivingBase target;

	public String[] targetmodes = { "Single", "Switch", "Multi" };
	public ModeValue targetmode = new ModeValue("Target-Mode", "Single", targetmodes);
	public String[] sortmode = { "Angle", "Range", "FOV", "Armor", "Health", "FightTick" };
	public ModeValue sortmodes = new ModeValue("Sort-Mode", "Angle", sortmode);
	public String[] ABSpammodes = { "Ground", "Air" };
	public ModeValue ABSpammode = new ModeValue("ABSpam-Mode", "Ground", ABSpammodes);
	public String[] ABmodes = { "Hold", "Spam" };
	public ModeValue ABmode = new ModeValue("AutoBlock-Mode", "Spam", ABmodes);
	public static String[] autoblocktype = { "NCP", "AAC" };
	public static ModeValue autoblocktypes = new ModeValue("BlockModes", "NCP", autoblocktype);

	public NumberValue<Double> rangeValue = new NumberValue<>("Range", 3.5, 0.1, 7.0);
	public NumberValue<Double> blockrange = new NumberValue<>("Block-Range", 3.5, 0.1, 7.0);

	public NumberValue<Integer> minCPSValue = new NumberValue<>("MinCPS", 6, 1, 20);
	public NumberValue<Integer> maxCPSValue = new NumberValue<>("MaxCPS", 8, 1, 20);

	public static BooleanValue autoblock = new BooleanValue("AutoBlock", true);
	public BooleanValue INTERACT = new BooleanValue("Interact", false);
	public BooleanValue randomcenter = new BooleanValue("RandomCenter", false);
	public BooleanValue raycast = new BooleanValue("RayCast", true);
	public BooleanValue prelook = new BooleanValue("PreLook", true);
	public NumberValue<Float> prelookRange = new NumberValue<>("PreLookRange", 0.75F, 0.0F, 1.0F);
	public BooleanValue esp = new BooleanValue("ESP", true);
	public ModeValue espmode = new ModeValue("ESP-Mode", "1", new String[] { "1", "2" });
	public BooleanValue sprint = new BooleanValue("KeepSprint", false);
	public String[] SprintType = { "Always", "InFov" };
	public ModeValue sprintMode = new ModeValue("Sprint-Type", "Always", SprintType);
	public BooleanValue walls = new BooleanValue("Wall", false);
	public BooleanValue hurttime = new BooleanValue("HurtTime", true);
	public BooleanValue noscaffold = new BooleanValue("NoScaffold", false);
	public BooleanValue OneAttack = new BooleanValue("OneAttack", true);
	public BooleanValue teams = new BooleanValue("Teams", false);
	public static BooleanValue fakeblock = new BooleanValue("FakeBlock", false);
	public ModeValue rotationsmodes = new ModeValue("Rotation", "Basic",
			new String[] { "Basic", "AAC", "Smooth", "Test" });
	public NumberValue<Integer> minspeed = new NumberValue<>("Min-Speed", 70, 1, 180);
	public NumberValue<Integer> maxspeed = new NumberValue<>("Max-Speed", 90, 1, 180);
	public static BooleanValue oldsprintattack = new BooleanValue("OldSprintAttack", false);
	public BooleanValue noswing = new BooleanValue("No-Swing", false);
	public BooleanValue superswing = new BooleanValue("Super-Swing", false);
	public static BooleanValue rotationStrafeValue = new BooleanValue("RotationStrafe", true);
	public BooleanValue noFly = new BooleanValue("NoFly", true);
	public BooleanValue aim = new BooleanValue("Aim", true);
	public NumberValue<Integer> percent = new NumberValue<>("Hit-Rate", 70, 1, 100);
//    private TimeHelper cpsTimer = new TimeHelper();
	private TimeHelper targetChangeTimer = new TimeHelper();
	private TimeHelper lastStep = new TimeHelper();
	public static float[] rotations = new float[2];
	private int timer;
	private float aacB;
	private boolean isleft = false;
	private static double Range;
	private List<EntityLivingBase> loaded = new CopyOnWriteArrayList<>();
	private int index, crits, groundTicks;
	private double fall, speed = 0;
	int[] randoms = { 0, 1, 0 };

	public static boolean isBlocking, isblocking;
	public static boolean isSetup = false;
	public float[] prev = new float[2];
	public float[] rrot = new float[2];
	public float[] lastrot = new float[2];

	public static boolean isSetupTick() {
		return isSetup;
	}

	private final ResourceLocation NoneLogo3 = new ResourceLocation("textures/NONELOGO3.png");

	@Override
	protected void onEnable() {
		super.onEnable();
		if (mc.thePlayer == null)
			return;
		isblocking = mc.thePlayer.isUsingItem() ? true : false;
		loaded.clear();
		isBlocking = false;
		rotations[0] = mc.thePlayer.rotationYaw;
		rotations[1] = mc.thePlayer.rotationPitch;
		aacB = 0;
		timer = 20;
		speed = 0;
		groundTicks = MoveUtils.isOnGround(0.01) ? 1 : 0;
		isSetup = false;
		targetChangeTimer.setLastMS();
		prev[0] = mc.thePlayer.rotationYaw;
		prev[1] = mc.thePlayer.rotationPitch;
		lastrot[0] = mc.thePlayer.rotationYaw;
		lastrot[1] = mc.thePlayer.rotationPitch;
	}

	@Override
	protected void onDisable() {
		super.onDisable();
		target = null;
		targeter = null;
		loaded.clear();
		isBlocking = false;
		isSetup = false;
		if (mc.thePlayer == null)
			return;
		rotations[0] = mc.thePlayer.rotationYaw;
		rotations[1] = mc.thePlayer.rotationPitch;
		if (isblocking && hassword() && mc.thePlayer.getItemInUseCount() == 999)
			unBlock();
		if (mc.thePlayer.getItemInUseCount() == 999)
			mc.thePlayer.itemInUseCount = 0;
	}

	public static boolean getBlocking() {
		return isBlocking;
	}

	private boolean isValid(Entity entity) {
		double range = rangeValue.getObject();
		if (prelook.getObject() && !(targetmode.getObject() == 1 || targetmode.getObject() == 2)) {
			range = range + prelookRange.getObject();
			Range = range;
		} else {
			range = rangeValue.getObject();
			Range = range;
		}
		if (Targeter.isTarget(entity) && !entity.isDead) {
			boolean team = false;
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				if (Client.instance.moduleManager.auraTeams.isEnabled()) {
					if (AuraTeams.player.contains(player)) {
						return false;
					}
				} else {
					if (!teams.getObject()
							|| (teams.getObject() && !TeamUtils.isTeam(mc.thePlayer, (EntityPlayer) entity))) {

					} else {
						return false;
					}
				}

				if (!Client.instance.moduleManager.noFriends.isEnabled()
						&& FriendManager.isFriend(player.getName())) {
					return false;
				}
			}
			return mc.thePlayer.getDistanceToEntity(entity) <= range
					&& (Utils.canEntityBeSeen((EntityLivingBase) entity) || walls.getObject());
		}
//    	if (Targeter.isTarget(entity) && !entity.isDead) {
//    		return entity.getDistance(-35, 72, 38) <= range && (Utils.canEntityBeSeen((EntityLivingBase)entity) || walls.getObject());
//    	}
		return false;
	}

	private boolean hassword() {
		return mc.thePlayer.inventory.getCurrentItem() != null
				&& mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
	}

	public static void drawESP(EntityLivingBase entity, int color) {
		double x = entity.lastTickPosX
				+ (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks;

		double y = entity.lastTickPosY
				+ (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks
				+ entity.getEyeHeight() * 1.2;

		double z = entity.lastTickPosZ
				+ (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks;
		double width = Math.abs(entity.boundingBox.maxX - entity.boundingBox.minX) + 0.2;
		double height = 0.1;
		Vec3 vec = new Vec3(x - width / 2, y, z - width / 2);
		Vec3 vec2 = new Vec3(x + width / 2, y + height, z + width / 2);
		final float rot = Utils.getNeededRotationsForEntity(entity)[0];
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		RenderingUtil.enableGL2D();
		RenderingUtil.pre3D();
		Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks,
				2);
		RenderingUtil.glColor(color);
		RenderingUtil.drawBoundingBox(
				new AxisAlignedBB(vec.xCoord - renderManager.renderPosX, vec.yCoord - renderManager.renderPosY,
						vec.zCoord - renderManager.renderPosZ, vec2.xCoord - renderManager.renderPosX,
						vec2.yCoord - renderManager.renderPosY, vec2.zCoord - renderManager.renderPosZ));
		GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		RenderingUtil.post3D();
		RenderingUtil.disableGL2D();
	}

	public static void drawAura(EntityLivingBase entity) {
		Minecraft mc = Minecraft.getMinecraft();
		double x = entity.lastTickPosX
				+ (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks;

		double y = entity.lastTickPosY
				+ (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks
				+ entity.getEyeHeight() * 1.2;

		double z = entity.lastTickPosZ
				+ (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks;
		double dx = x - mc.thePlayer.posX;
		double dy = y - mc.thePlayer.posY;
		double dz = z - mc.thePlayer.posZ;
		double width = Math.abs(entity.boundingBox.maxX - entity.boundingBox.minX) + 0.2;
		GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
		RenderingUtil.drawCircle((float) dx, (float) dz, (float) width, 10, Color.WHITE.getRGB());
		GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
	}

	public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY,
			EntityLivingBase ent) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, 50.0F);
		GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		float f = ent.renderYawOffset;
		float f1 = ent.rotationYaw;
		float f2 = ent.rotationPitch;
		float f3 = ent.prevRotationYawHead;
		float f4 = ent.rotationYawHead;
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		ent.renderYawOffset = (float) Math.atan((double) (mouseX / 40.0F)) * 20.0F;
		ent.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 40.0F;
		ent.rotationPitch = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;
		ent.rotationYawHead = ent.rotationYaw;
		ent.prevRotationYawHead = ent.rotationYaw;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		ent.renderYawOffset = f;
		ent.rotationYaw = f1;
		ent.rotationPitch = f2;
		ent.prevRotationYawHead = f3;
		ent.rotationYawHead = f4;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	public void drawEsp2(EntityPlayer entity, int x, int y) {
		NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.connection;
		List<NetworkPlayerInfo> list = GuiPlayerTabOverlay.ENTRY_ORDERING
				.<NetworkPlayerInfo>sortedCopy(nethandlerplayclient.getPlayerInfoMap());
		NetworkPlayerInfo info = null;
		TTFFontRenderer fontRenderer = Client.fm.getFont("BebasNeue");
		for (NetworkPlayerInfo thisinfo : list) {
			if (thisinfo.getGameProfile().getId() == entity.getGameProfile().getId()) {
				info = thisinfo;
			}
		}
		Gui.drawOutlineRGB(x, y, x + 180, y + 70, 2, Colors.getColor(Color.BLACK.getRGB(), 170));
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		// Head
		if (info != null) {
			EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(entity.getGameProfile().getId());
			boolean flag1 = false;
			this.mc.getTextureManager().bindTexture(info.getLocationSkin());
			int l2 = 8 + (flag1 ? 8 : 0);
			int i3 = 8 * (flag1 ? -1 : 1);
			Gui.drawScaledCustomSizeModalRect(x + 10, y + 20, 8.0F, (float) l2, 8, i3, 40, 40, 64.0F, 64.0F);
		}
		// Name
		fontRenderer.drawString(entity.getName(), x + 10, y + 7, Color.WHITE.getRGB());
		float health = target.getHealth();
		float a = (health / 20F) * 100F;
		if (a > 100F) {
			a = 100F;
		}
		// Health Bar
		Gui.drawRect(x + 60, y + 27, x + 160, y + 43, Colors.getColor(Color.RED, 200));
		Gui.drawRect(x + 60, y + 27, x + 60 + (a), y + 43, Colors.getColor(Color.GREEN, 180));
		// Precent String
		fontRenderer.drawString((int) a + "%", x + 105, y + 30, Color.WHITE.getRGB());
		return;
	}

	private List<EntityLivingBase> getTargets() {
		List<EntityLivingBase> targets = new ArrayList<>();
		// List entities =
		// mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.thePlayer,
		// mc.thePlayer.boundingBox.expand(range, range, range));
		// for (Object o : entities) {
		for (Object o : mc.theWorld.loadedEntityList) {
			if (o instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase) o;
				if (isValid(entity)) {
					targets.add(entity);
				}
				if (mc.thePlayer.getDistanceToEntity(entity) > Range) {
					targets.remove(entity);
				}
			}
		}
		return targets;
	}

	private EntityLivingBase getNearTargets() {
		List<EntityLivingBase> targets = new ArrayList<>();
		targets = getTargets();
		if (targets == null) {
			return null;
		}
		if (targets.isEmpty()) {
			return null;
		}
		sortList(targets);
		return targets.get(0);
	}

	private void sortList(List<EntityLivingBase> weed) {
		String current = sortmodes.getSelected();
		switch (current) {
		case "Range":
			weed.sort((o1, o2) -> (int) (o1.getDistanceToEntity(mc.thePlayer) * 1000
					- o2.getDistanceToEntity(mc.thePlayer) * 1000));

			break;
		case "FOV":
			weed.sort(Comparator.comparingDouble(o -> (RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationYaw,
					RotationUtils.getRotations(o)[0]))));
			break;
		case "Angle":
			weed.sort((o1, o2) -> {
				float[] rot1 = RotationUtils.getRotations(o1);
				float[] rot2 = RotationUtils.getRotations(o2);
				return (int) ((mc.thePlayer.rotationYaw - rot1[0]) - (mc.thePlayer.rotationYaw - rot2[0]));
			});
			break;
		case "Health":
			weed.sort((o1, o2) -> (int) (o1.getHealth() - o2.getHealth()));
			break;
		case "Armor":
			weed.sort(Comparator.comparingInt(
					o -> (o instanceof EntityPlayer ? ((EntityPlayer) o).getTotalArmorValue() : (int) o.getHealth())));
			break;

		case "FightTick":
//			weed.sort((o1, o2) -> (int) (o1.fighttick - o2.fighttick));
			weed.sort(new Comparator<EntityLivingBase>() {

				@Override
				public int compare(EntityLivingBase o1, EntityLivingBase o2) {
					if (o1.fighttick > o2.fighttick) {
						return -1;
					} else if (o1.fighttick < o2.fighttick) {
						return 1;
					}
					return 0;
				}
			});
			break;
		}
	}

	@Override
	@RegisterEvent(events = { EventPreMotionUpdate.class, Event2D.class, Event3D.class, EventStep.class,
			EventTick.class, StrafeEvent.class, EventPacket.class })
	public void onEvent(Event event) {
		if (!isEnabled()) {
			return;
		}
		FontRenderer fontRenderer = Fonts.roboto18;

		ScaledResolution res = new ScaledResolution(mc);
		Module scaffold = Client.instance.moduleManager.scaffold;
		Module fly = Client.instance.moduleManager.fly;
		Regen aacHeal = Client.instance.moduleManager.regen; 
		
		if (noscaffold.getObject() && scaffold.isEnabled()) {
			return;
		}

		if (noFly.getObject() && fly.isEnabled()) {
			return;
		}
		
		if (aacHeal.isEnabled() && aacHeal.healing) {
			return;
		}
		
		BowAimbot bowaimbot = (BowAimbot) Client.instance.moduleManager.bowAimbot;
		if (bowaimbot != null) {
			if (bowaimbot.isEnabled() && bowaimbot.shouldAim()) {
				return;
			}
		}

		if (Cheststealer.shouldChest == true) {
			return;
		}

		setDisplayName(getName() + ChatFormatting.WHITE + " " + targetmode.getSelected());

		if ((!mc.thePlayer.isEntityAlive() || (mc.currentScreen != null && mc.currentScreen instanceof GuiGameOver))) {
			Client.instance.notification
					.show(new Notification(NotificationType.INFO, getName(), " disabled by death.", 3));
			EventChat.addchatmessage("KillAura disabled by death.");
			this.toggle();
			return;
		}
		if (mc.thePlayer.ticksExisted <= 1) {
			Client.instance.notification
					.show(new Notification(NotificationType.INFO, getName(), " disabled by respawn.", 3));
			EventChat.addchatmessage("KillAura disabled by respawn.");
			this.toggle();

			rotations[0] = mc.thePlayer.rotationYaw;
			rotations[1] = mc.thePlayer.rotationPitch;
			return;
		}
		Module critsMod = Client.instance.moduleManager.criticals;
		String critsMode = Criticals.modes.getSelected();
		boolean minicrit = critsMod.isEnabled() && (critsMode.equalsIgnoreCase("Minis"));
		int cps = randomNumber(minCPSValue.getInteger(), maxCPSValue.getInteger());

		if (event instanceof EventPacket) {
			EventPacket ep = (EventPacket) event;
			Packet p = ep.getPacket();
			if (p instanceof S08PacketPlayerPosLook) {
				S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) p;
				rotations[0] = packet.getYaw();
				rotations[1] = packet.getPitch();
			}
		}

		if (event instanceof EventStep) {
			EventStep es = (EventStep) event;
			if (!es.isPre()) {
				lastStep.setLastMS();
			}
		}

		if (event instanceof EventTick) {
			if (target == null) {

			} else {
				float yawtarget = rotationsmodes.getSelected().equalsIgnoreCase("Basic")
						? Utils.getNeededRotationsForEntity(target)[0]
						: Utils.getNeededRotationsForAAC(target)[0];
				float pitchtarget = rotationsmodes.getSelected().equalsIgnoreCase("Basic")
						? Utils.getNeededRotationsForEntity(target)[1]
						: Utils.getNeededRotationsForAAC(target)[1];

				boolean walls = Utils.canEntityBeSeen(target);
				int currentCPS = Utils.random(minCPSValue.getObject(), maxCPSValue.getObject());

				EntityLivingBase rayCastEntity = raycast.getObject()
						? RayCastUtil.rayCast(Range, rotations[0], rotations[1])
						: null;

				if (!lastStep.hasTimeReached(100)) {
					return;
				}

				if (target != null && mc.thePlayer.getDistanceToEntity(target) <= blockrange.getObject()) {
					if (autoblock.getObject() && ABmode.getSelected().equalsIgnoreCase("Hold")) {
						if (hassword()) {
							if (!isblocking) {
								if (autoblocktypes.getSelected().equalsIgnoreCase("NCP")) {
									block(target);
								}
							}
							mc.thePlayer.itemInUseCount = 999;
						}
					}
				}

				if (fakeblock.getObject() && mc.thePlayer.getDistanceToEntity(target) <= blockrange.getObject()) {
					isBlocking = true;
				}

				float[] yawpitch = { yawtarget, pitchtarget };
				if ((!targetChangeTimer.hasTimeReached(100) && !targetmode.getSelected().equalsIgnoreCase("Multi"))
						|| (Client.instance.moduleManager.autoPot.isEnabled()
								&& AutoPot.isPotting())) {
					// Miss Click :D
					if (noswing.getObject()) {
						mc.thePlayer.connection.sendPacket(new C0APacketAnimation());
					} else {
						mc.thePlayer.swingItem();
					}
				} else {
					boolean openInventory = mc.currentScreen instanceof GuiInventory;

					if (openInventory)
						mc.getConnection().sendPacket(new C0DPacketCloseWindow());
					if (((hurttime.getObject() && target.hurtTime <= 4) || !hurttime.getObject()) && timer >= 20 / cps
							&& mc.thePlayer.getDistanceToEntity(target) <= rangeValue.getObject()
							&& ((!this.walls.getObject() && walls) || (this.walls.getObject() && (!walls || walls)))
							&& ((raycast.getObject() && rayCast(yawpitch, rotations, Range)) || !raycast.getObject())) {
						timer = 0;
						if (Utils.randomPercent(percent.getObject())) {
							if (OneAttack.getObject()) {
								hitEntity(rayCastEntity == null ? target : (EntityLivingBase) rayCastEntity);
							} else {
								hitEntity(rayCastEntity == null ? target : (EntityLivingBase) rayCastEntity);
							}
						} else {
							// Miss Click :D
							if (noswing.getObject()) {
								mc.thePlayer.connection.sendPacket(new C0APacketAnimation());
							} else {
								mc.thePlayer.swingItem();
							}
						}

						// AutoBlock-Spam
						// if (mc.getCurrentServerData() != null && mc.getIntegratedServer() == null) {
						// if (hassword() &&
						// !(mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel"))) {
						// if (autoblock.getObject()) {
						// if (ABmode.getSelected() == "Spam") {
						// //Spam-Mode
						// if (ABSpammode.getObject() == 0 && (mc.thePlayer.onGround ||
						// !mc.thePlayer.onGround)) {
						// //Ground
						// mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld,
						// mc.thePlayer.inventory.getCurrentItem());
						// }else if (ABSpammode.getObject() == 1 && !mc.thePlayer.onGround) {
						// //Air
						// mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld,
						// mc.thePlayer.inventory.getCurrentItem());
						// }
						// }
						// }
						// }
						// }

						// cpsTimer.setLastMS();

						int XR = randomNumber(1, -1);
						int YR = randomNumber(1, -1);
						int ZR = randomNumber(1, -1);

						randoms[0] = XR;
						randoms[1] = YR;
						randoms[2] = ZR;
					}
					if (openInventory)
						mc.getConnection().sendPacket(
								new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
				}

				if (target != null && mc.thePlayer.getDistanceToEntity(target) <= blockrange.getObject()) {
					if (autoblock.getObject() && ABmode.getSelected().equalsIgnoreCase("Hold")) {
						if (hassword()) {
							if (!isblocking) {
								if (autoblocktypes.getSelected().equalsIgnoreCase("NCP"))
									block(target);
							}
							mc.thePlayer.itemInUseCount = 999;
						}
					}
				}
			}
		}

		if (event instanceof StrafeEvent) {
			StrafeEvent strafeevent = (StrafeEvent) event;
			if (!rotationStrafeValue.getObject())
				return;

			if (target == null)
				return;

			if (!MoveUtils.isMoveKeyPressed())
				return;

//			if (mc.thePlayer.getDistanceToEntity(target) <= 0.5F) {
				float yaw = rotations[0];
				float strafe = strafeevent.getStrafe();
				float forward = strafeevent.getForward();
				float friction = strafeevent.getFriction();
				float f = strafe * strafe + forward * forward;

				if (f >= 1.0E-4F) {
					f = MathHelper.sqrt_float(f);

					if (f < 1.0F) {
						f = 1.0F;
					}

					f = friction / f;
					strafe = strafe * f;
					forward = forward * f;
					float f1 = MathHelper.sin(yaw * (float) Math.PI / 180.0F);
					float f2 = MathHelper.cos(yaw * (float) Math.PI / 180.0F);
					mc.thePlayer.motionX += (double) (strafe * f2 - forward * f1);
					mc.thePlayer.motionZ += (double) (forward * f2 + strafe * f1);
				}
				strafeevent.setCancelled(true);
//			}
		}

		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;

			if (e.isPre()) {
				rrot[0] = mc.thePlayer.rotationYaw;
				rrot[1] = mc.thePlayer.rotationPitch;
				timer++;
				loaded = getTargets();
				target = getNearTargets();
				targeter = target;
				if (target == null) {
					speed = 0;
					targetChangeTimer.setLastMS();
					isBlocking = false;
					if (isblocking)
						unBlock();
					if (mc.thePlayer.itemInUseCount == 999)
						mc.thePlayer.itemInUseCount = 0;
					if (!aim.getObject()) {
						if (rotationsmodes.getSelected().equalsIgnoreCase("Smooth")) {
							rotations = returnAim();
							
							e.setYaw(rotations[0]);
							e.setPitch(rotations[1]);
						} else if (rotationsmodes.getSelected().equalsIgnoreCase("Test")) {
//							rotations = returnAim();
//							
//							e.setYaw(rotations[0]);
//							e.setPitch(rotations[1]);
						}
					}
				} else {
					target.fighttick++;
//                    if (sprint.getObject() && MoveUtils.isMoveKeyPressed()) {
//                		mc.thePlayer.setSprinting(true);
//                    }else {
//                    	mc.thePlayer.setSprinting(false);
//                    }
					if (sprint.getObject()) {
						if (sprintMode.getSelected().equalsIgnoreCase("InFov") && mc.thePlayer.moveForward > 0
								&& RotationUtils.getDistanceBetweenAngles(rotations[0],
										mc.thePlayer.rotationYaw) < 40) {
							if (BlockUtils.isOnLiquid(0.1)) {
								if ((Client.instance.moduleManager.liquidWalk.isEnabled()
										&& !LiquidWalk.walkmodes.getSelected().equalsIgnoreCase("Seksin"))
										|| !Client.instance.moduleManager.liquidWalk
												.isEnabled()) {
									mc.thePlayer.setSprinting(true);
								} else {
									mc.thePlayer.setSprinting(false);
								}
							} else {
								mc.thePlayer.setSprinting(true);
							}
						} else if (sprintMode.getSelected().equalsIgnoreCase("Always")) {
							if (BlockUtils.isOnLiquid(0.1)) {
								if (Client.instance.moduleManager.liquidWalk.isEnabled()
										&& !LiquidWalk.walkmodes.getSelected().equalsIgnoreCase("Seksin")) {
									mc.thePlayer.setSprinting(true);
								} else {
									mc.thePlayer.setSprinting(false);
								}
							} else {
								if (MoveUtils.isMoveKeyPressed()) {
									mc.thePlayer.setSprinting(true);
								} else {
									mc.thePlayer.setSprinting(false);
								}
							}
						}
					} else {
						mc.thePlayer.setSprinting(false);
					}

					if (targetmode.getSelected().equalsIgnoreCase("Switch")) {
						float cooldown = 300;
						if (targetChangeTimer.hasTimeReached((long) cooldown)) {
							loaded = getTargets();
						}
						if (index >= loaded.size()) {
							index = 0;
						}
						if (targetChangeTimer.hasTimeReached((long) cooldown) && loaded.size() > 0) {
							index += 1;
							if (index >= loaded.size()) {
								index = 0;
							}
							targetChangeTimer.setLastMS();
						}

						if (loaded.size() > 0)
							target = loaded.get(index);
						else
							target = null;
					}

					if (targetmode.getSelected().equalsIgnoreCase("Multi")) {
						float cooldown = 10;
						if (targetChangeTimer.hasTimeReached((long) cooldown)) {
							loaded = getTargets();
						}
						if (index >= loaded.size()) {
							index = 0;
						}
						if (targetChangeTimer.hasTimeReached((long) cooldown) && loaded.size() > 0) {
							index += 1;
							if (index >= loaded.size()) {
								index = 0;
							}
							targetChangeTimer.setLastMS();
						}

						if (loaded.size() > 0)
							target = loaded.get(index);
						else
							target = null;
					}

					if (minicrit) {
						miniCrit(e, critsMode);
					}
					if (randomcenter.getObject() && (!rotationsmodes.getSelected().equalsIgnoreCase("Smooth")
							|| !rotationsmodes.getSelected().equalsIgnoreCase("Test"))) {
						// rot =
						// Utils.getNeededRotations(Utils.getRandomCenter(target.getEntityBoundingBox()));
						rotations = Utils.getNeededRotationsForEntity(target);
						float randomYaw = randomNumber(-2, 2) * 2F;
						float randomPitch = randomNumber(-2, 2) * 2F;
						rotations[0] += randomYaw;
						rotations[1] += randomPitch;
					} else {
						if (rotationsmodes.getSelected().equalsIgnoreCase("AAC")) {
							rotations = Utils.getNeededRotationsForAAC(target);
						} else if (rotationsmodes.getSelected().equalsIgnoreCase("Basic")) {
							rotations = Utils.getNeededRotationsForEntity(target);
						} else if (rotationsmodes.getSelected().equalsIgnoreCase("Smooth")) {
							rotations = smoothAim(target);
						} else if (rotationsmodes.getSelected().equalsIgnoreCase("Test")) {
							testAim(target);
						}
					}
					if (aim.getObject()) {
						mc.thePlayer.rotationYaw = rotations[0];
						mc.thePlayer.rotationPitch = rotations[1];
					}else {
						e.setYaw(rotations[0]);
						e.setPitch(rotations[1]);
					}
				}
			} else if (e.isPost()) {
				prev[0] = mc.thePlayer.rotationYaw;
				prev[1] = mc.thePlayer.rotationPitch;
				if (autoblocktypes.getSelected().equalsIgnoreCase("AAC")) {
					if (isblocking && autoblock.getObject() && ABmode.getSelected() == "Hold") {
						if (hassword())
							unBlock();
						mc.thePlayer.itemInUseCount = 0;
						isBlocking = false;
					}
				}
			}
		}

		if (event instanceof Event2D) {
			int renderColor = ClientColor.getColor();
			if (esp.getObject()) {
				if (target != null) {
					if (espmode.getSelected().equalsIgnoreCase("1")) {
						if (mc.thePlayer.getDistanceToEntity(target) <= Range) {
							float health = target.getHealth();

							if (health > 20) {
								health = 20;
							}

							float[] fractions = new float[] { 0f, 0.5f, 1f };
							Color[] colors = new Color[] { Color.RED, Color.YELLOW, Color.GREEN };
							float progress = (health * 5) * 0.01f;

							int resX = (res.getScaledWidth() / 2) - 165;
							int resY = (res.getScaledHeight() / 2) + 100;
							String facing = "" + target.getHorizontalFacing();
							facing = Character.toUpperCase(facing.toLowerCase().charAt(0))
									+ facing.toLowerCase().substring(1);
							drawEntityOnScreen(resX + 37, resY - 15, 25, 0, 0, target);
							Gui.drawRect((resX) + 23, (resY) - 77, (resX) + 152, (resY) + 2,
									!ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(1000));
							Gui.drawRect((resX) + 25, (resY) - 75, (resX) + 150, (resY), Color.BLACK.getRGB());
							Gui.drawRect(resX + 54, ((resY) - 38), (resX + 56) + ((int) health * 4.5F),
									((resY) - 36) + fontRenderer.FONT_HEIGHT + 2,
									!ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100));
							Gui.drawRect(resX + 55, ((resY) - 37), (resX + 55) + ((int) health * 4.5F),
									((resY) - 37) + fontRenderer.FONT_HEIGHT + 2,
									Esp.blendColors(fractions, colors, progress).brighter().getRGB());
							fontRenderer.drawString("Name:" + target.getName(), (resX) + 50, (resY) - 75,
									!ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100));
							fontRenderer.drawString(
									"Position:" + "X=" + (int) target.posX + " Y=" + (int) target.posY + " Z="
											+ (int) target.posZ,
									(resX) + 30, (resY) - 10,
									!ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100));
							fontRenderer.drawString("Health:" + (int) health, (resX) + 50, (resY) - 67,
									!ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100));
							fontRenderer.drawString("Direction:" + facing, (resX) + 50, (resY) - 59,
									!ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100));
						}
					} else if (espmode.getSelected().equalsIgnoreCase("2")) {
						if (mc.thePlayer.getDistanceToEntity(target) <= Range) {
							if (target instanceof EntityPlayer) {
								int resX = (res.getScaledWidth() / 2) - 165;
								int resY = (res.getScaledHeight() / 2) + 30;
								drawEsp2((EntityPlayer) target, resX, resY);
							}
						}
					}
				}
			}
		}

		if (event instanceof Event3D) {
			int renderColor = ClientColor.getColor();
			int renderColor2 = ClientColor.getColor();
			if (ClientColor.rainbow.getObject()) {
				renderColor2 = ClientColor.rainbow(1000 * 100);
			}
			if (esp.getObject()) {
				if (target != null) {
					if (mc.thePlayer.getDistanceToEntity(target) <= Range) {
						if (target.hurtTime > 0) {
							drawESP(target, Color.RED.getRGB());
						} else {
							drawESP(target, !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(1000));
						}
						drawAura(target);
						drawLogo3(target);
					}
				}
			}
		}
	}

	public void miniCrit(EventPreMotionUpdate em, String mode) {
		double offset = 0;
		boolean ground = false;
		final int min = minCPSValue.getInteger();
		final int max = maxCPSValue.getInteger();
//		final double hitchance = ((Number) settings.get(HITCHANCE).getValue()).doubleValue();
		final int cps = randomNumber(min, max);
		final int delay = 20 / cps;
		// TODO
		if (mode.equalsIgnoreCase("Minis")) {
			if (crits == 0) {
				double x = mc.thePlayer.posX;
				double y = mc.thePlayer.posY;
				double z = mc.thePlayer.posZ;
//				C04PacketPlayerPosition p = new C04PacketPlayerPosition(x, y+0.0626001, z, false);
//				//mc.thePlayer.sendQueue.addToSendQueue(p);
				offset = 0;
				crits = 1;

			} else if (timer == delay - 2 || (delay - 2 <= 0 && timer <= delay - 2) || timer >= delay) {
				if (crits >= 1 && lastStep.hasTimeReached(20)) {
					crits = 0;
					offset = 0.0628;
					fall += offset;
				}
			}
		}

		boolean aa = BlockUtils.isOnGround(0.001);
		if (!aa) {
			groundTicks = 0;
			crits = 0;
			fall = 0;
		} else {
			groundTicks++;
		}
		if (mc.thePlayer.isCollidedVertically && aa && !mc.thePlayer.isJumping && !mc.thePlayer.isInWater()
				&& !mc.gameSettings.keyBindJump.isKeyDown()) {
			if (mc.thePlayer.motionY != -0.1552320045166016) {
				if (offset != 0) {
					isSetup = true;
				} else {
					isSetup = false;
				}
				mc.thePlayer.lastReportedPosY = 0;
				em.setY(mc.thePlayer.posY + offset);
				em.setOnGround(ground);
			}
		}
	}

	public static int randomNumber(int min, int max) {
		return Math.round(min + (float) Math.random() * ((max - min)));
	}

	private void block(EntityLivingBase ent) {
		isblocking = true;
		mc.playerController.syncCurrentPlayItem();
		if (INTERACT.getObject()) {
			mc.thePlayer.connection
					.sendPacket(new C02PacketUseEntity(ent, new Vec3((double) randomNumber(-50, 50) / 100,
							(double) randomNumber(0, 200) / 100, (double) randomNumber(-50, 50) / 100)));
			mc.thePlayer.connection.sendPacket(new C02PacketUseEntity(ent, C02PacketUseEntity.Action.INTERACT));
		}
		mc.thePlayer.connection.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
	}

	private void unBlock() {
		isblocking = false;
		mc.playerController.syncCurrentPlayItem();
		mc.thePlayer.connection.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
				BlockPos.ORIGIN, EnumFacing.DOWN));
	}

	private void hitEntity(EntityLivingBase target) {
		if (!autoblocktypes.getSelected().equalsIgnoreCase("AAC") && isblocking && autoblock.getObject()
				&& ABmode.getSelected() == "Hold") {
			if (hassword())
				if (autoblocktypes.getSelected().equalsIgnoreCase("NCP")) {
					unBlock();
				}
			mc.thePlayer.itemInUseCount = 0;
		}
//		mc.thePlayer.movementInput.moveForward *= 0.2f;
//		mc.thePlayer.movementInput.moveStrafe *= 0.2f;
//		mc.thePlayer.motionX *= 0.37;
//		mc.thePlayer.motionZ *= 0.37;

		final float sharpLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId,
				mc.thePlayer.getHeldItem());
		EventAttack ej = (EventAttack) EventSystem.getInstance(EventAttack.class);
		ej.fire(target, true);
		if (noswing.getObject()) {
			mc.thePlayer.connection.sendPacket(new C0APacketAnimation());
		} else {
			if (superswing.getObject()) {
				mc.thePlayer.swingItem();
				mc.thePlayer.prevSwingProgress = 10F;
				mc.thePlayer.swingProgress = 10F;
			} else {
				mc.thePlayer.swingItem();
			}
		}
//		if (target.hurtResistantTime <= 12) {
		mc.playerController.attackEntity(mc.thePlayer, target);
//		}
//		mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C02PacketUseEntity(target));
		if (!OneAttack.getObject()) {
			mc.getConnection().getNetworkManager()
					.sendPacketNoEvent(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
		}
		ej.fire(target, false);
		mc.thePlayer.onCriticalHit(target);
		if (sharpLevel > 0.0F)
			mc.thePlayer.onEnchantmentCritical(target);

		if (autoblock.getObject() && ABmode.getSelected().equalsIgnoreCase("Hold")) {
			if (hassword()) {
				if (!isblocking) {
					if (autoblocktypes.getSelected().equalsIgnoreCase("AAC"))
						block(target);
				}
				mc.thePlayer.itemInUseCount = 999;
			}
		}
	}

	private float[] smoothAim(EntityLivingBase target) {
		double currSpeed = Math
				.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
		int addX = 0;
		int addY = 0;
		int addZ = 0;
		double newX = 0;
		double newY = 0;
		double newZ = 0;
		if (randomcenter.getObject()) {
			addX = randoms[0];
			addY = randoms[1];
			addZ = randoms[2];
			newX = addX * 0.12;
			newY = addY * 0.12;
			newZ = addZ * 0.12;
		}
//		float targetYaw = RotationUtils.getYawChange(rotations[0], target.posX + newX, target.posZ + newZ);
//		float yawFactor = targetYaw;
//		if (Math.abs(targetYaw) < 5) {
//			yawFactor = targetYaw / (float)(smoothspeed.getObject() / 10F);
//		}else if (Math.abs(targetYaw) > 5 && Math.abs(targetYaw) < 10) {
//			yawFactor = targetYaw / 4.2F;
//		}else if (Math.abs(targetYaw) > 10 && Math.abs(targetYaw) < 14) {
//			yawFactor = targetYaw / 3.7F;
//		}else if (Math.abs(targetYaw) > 14) {
//			yawFactor = targetYaw / 2F;
//		}
//		
//		rotations[0] += yawFactor;
		float[] rot = getCustomRotsChange(rotations[0], rotations[1], target.posX + newX, target.posY + newY,
				target.posZ + newZ);

		float targetYaw = rot[0];
		float targetPitch = rot[1];

		float turnspeed = (float) randomNumber(minspeed.getInteger(), maxspeed.getInteger());

		if (Math.abs(targetYaw) > turnspeed) {
			if (targetYaw > 0) {
				targetYaw = turnspeed;
			} else {
				targetYaw = -turnspeed;
			}
			targetYaw = Math.max(targetYaw, -turnspeed);
			rotations[0] += targetYaw;
		} else {
			float yawFactor = targetYaw;
			if (Math.abs(targetYaw) < 5) {
				yawFactor = targetYaw / 5.5F;
			} else if (Math.abs(targetYaw) > 5 && Math.abs(targetYaw) < 10) {
				yawFactor = targetYaw / 4.2F;
			} else if (Math.abs(targetYaw) > 10 && Math.abs(targetYaw) < 14) {
				yawFactor = targetYaw / 3.7F;
			} else if (Math.abs(targetYaw) > 14) {
				yawFactor = targetYaw / 2F;
			}
			targetYaw = yawFactor;
			rotations[0] += targetYaw;
		}

		float pitchFactor = targetPitch;
//		boolean flag = false;
//		if (target.posY + target.getEyeHeight() > mc.thePlayer.posY + mc.thePlayer.getEyeHeight() + 0.5 || target.posY + target.getEyeHeight() < mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - 0.5) {
//			flag = true;
//		}
//		if (mc.thePlayer.rotationPitch < -10 || mc.thePlayer.rotationPitch > 35) {
//			flag = true;
//		}
//		if (!flag) {
//			rotations[1] = mc.thePlayer.rotationPitch;
//		}else {
//			pitchFactor = targetPitch / 2F;
		rotations[1] += pitchFactor;
//		}
//		if (Math.abs(targetPitch) < 2.5) {
//			pitchFactor = targetPitch / (float)(smoothspeed.getObject() / 10F);
//		}else if (Math.abs(targetPitch) > 2.5 && Math.abs(targetPitch) < 5) {
//			pitchFactor = targetYaw / 4.2F;
//		}else if (Math.abs(targetPitch) > 5 && Math.abs(targetPitch) < 7) {
//			pitchFactor = targetPitch / 3.7F;
//		}else if (Math.abs(targetPitch) > 7) {
//			pitchFactor = targetPitch / 2F;
//		}
		return new float[] { rotations[0], rotations[1] };
	}

	public void testAim(EntityLivingBase target) {
		float[] oldRotation = rotations;
		float[] targetRotation = RotationUtils.getRotations(target);
		if (rayCast(rotations, targetRotation, rangeValue.getObject())) {
			rotations = oldRotation;
		}else {
			float yaw = rotations[0];
			float pitch = rotations[1];
			AxisAlignedBB bb = target.getEntityBoundingBox();
			double x = (bb.minX + (bb.maxX - bb.minX) * 0.5);
			double y = (bb.minY + (bb.maxY - bb.minY) * 0.5);
			double z = (bb.minZ + (bb.maxZ - bb.minZ) * 0.5);
			Vec3 vec = new Vec3(x, y, z);
			vec.addVector(target.motionX, target.motionY, target.motionZ);
			float[] rot = getCustomRotsChange(yaw, pitch, vec.xCoord, vec.yCoord, vec.zCoord);
			float turnspeed = (float) randomNumber(minspeed.getInteger(), maxspeed.getInteger());
			yaw = rot[0];
			if (Math.abs(yaw) > turnspeed) {
				if (yaw > 0) {
					yaw = turnspeed;
				} else {
					yaw = -turnspeed;
				}
				yaw = Math.max(yaw, -turnspeed);
			}else {
				yaw = rot[0] / 2F;
			}
			pitch = rot[1];
			rotations[0] += yaw;
			rotations[1] += pitch;
		}
	}

	private float[] returnAim() {
		float targetYaw = RotationUtils.getReturnYawChange(rotations[0]);
		float targetPitch = RotationUtils.getReturnPitchChange(rotations[1]);
		float yawFactor = targetYaw;
		float turnspeed = (float) randomNumber(minspeed.getInteger(), maxspeed.getInteger());

		if (Math.abs(targetYaw) > turnspeed) {
			if (targetYaw > 0) {
				targetYaw = turnspeed;
			} else {
				targetYaw = -turnspeed;
			}
			targetYaw = Math.max(targetYaw, -turnspeed);
			rotations[0] += targetYaw;
		} else {
			if (Math.abs(targetYaw) < 5) {
				yawFactor = targetYaw / 5.5F;
			} else if (Math.abs(targetYaw) > 5 && Math.abs(targetYaw) < 10) {
				yawFactor = targetYaw / 4.2F;
			} else if (Math.abs(targetYaw) > 10 && Math.abs(targetYaw) < 14) {
				yawFactor = targetYaw / 3.7F;
			} else if (Math.abs(targetYaw) > 14) {
				yawFactor = targetYaw / 2F;
			}
			targetYaw = yawFactor;
			rotations[0] += targetYaw;
		}
		float pitchFactor = targetPitch;
		if (Math.abs(targetPitch) < 2.5) {
			pitchFactor = targetPitch / 5.5F;
		} else if (Math.abs(targetPitch) > 2.5 && Math.abs(targetPitch) < 5) {
			pitchFactor = targetYaw / 4.2F;
		} else if (Math.abs(targetPitch) > 5 && Math.abs(targetPitch) < 7) {
			pitchFactor = targetPitch / 3.7F;
		} else if (Math.abs(targetPitch) > 7) {
			pitchFactor = targetPitch / 2F;
		}
		rotations[1] += pitchFactor;
		return new float[] { RotationUtils.getNewAngle(rotations[0]), RotationUtils.getNewAngle(rotations[1]) };
	}

	public float[] getCustomRotsChange(float yaw, float pitch, double x, double y, double z) {
		Vec3 vec = new Vec3(mc.thePlayer.posX, (mc.thePlayer.posY + mc.thePlayer.getEyeHeight()), mc.thePlayer.posZ);
		vec.addVector(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
		double xDiff = x - vec.xCoord;
		double yDiff = y - vec.yCoord;
		double zDiff = z - vec.zCoord;

		double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
		float yawToEntity = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
		float pitchToEntity = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
		return new float[] { MathHelper.wrapAngleTo180_float(-(yaw - (float) yawToEntity)),
				-MathHelper.wrapAngleTo180_float(pitch - (float) pitchToEntity) - 2.5F };
	}

	public boolean rayCast(float[] currentRotation, float[] targetRotation, double range) {
		Entity currEntity = RayCastUtil.rayCast(range, currentRotation[0], currentRotation[1]);
		Entity rotEntity = RayCastUtil.rayCast(range, targetRotation[0], targetRotation[1]);
		if (currEntity == null || rotEntity == null)
			return false;
		return currEntity == rotEntity;
	}
	
	public void drawLogo3(EntityLivingBase entity) {
		Minecraft mc = Minecraft.getMinecraft();
		double dx = entity.lastTickPosX
				+ (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks;

		double dy = entity.lastTickPosY
				+ (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks
				+ entity.getEyeHeight() * 1.2;

		double dz = entity.lastTickPosZ
				+ (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks;
		double x = dx - mc.thePlayer.posX;
		double y = dy - mc.thePlayer.posY;
		double z = dz - mc.thePlayer.posZ;
		RenderManager renderManager = mc.getRenderManager();
		float f = renderManager.playerViewY;
		float f1 = renderManager.playerViewX;
		boolean flag1 = renderManager.options.thirdPersonView == 2;
		if (!entity.upward) {
			if (entity.renderCount > 0) {
				entity.renderCount -= 0.25F;
			}else {
				entity.upward = true;
			}
		}else {
			if (entity.renderCount < 2) {
				entity.renderCount += 0.25F;
			}else {
				entity.upward = false;
			}
		}
		GL11.glColor4d(1, 1, 1, 1);
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		GlStateManager.translate(x, y + entity.renderCount, z);
		GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(-0.1, -0.1, 0.1);
		mc.getTextureManager().bindTexture(NoneLogo3);
		drawModalRectWithCustomSizedTexture(-6, 0, 0, 0, 12, 12, 12, 12);
		GlStateManager.scale(0.9, 0.9, -0.9);
		GlStateManager.translate(x, y - entity.renderCount, z);
		GlStateManager.disableBlend();
	}
	
	public void drawModalRectWithCustomSizedTexture(double x, double z, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        GlStateManager.pushMatrix();
        float var8 = 1.0F / textureWidth;
        float var9 = 1.0F / textureHeight;
        Tessellator var10 = Tessellator.getInstance();
        WorldRenderer var11 = var10.getWorldRenderer();
        Gui.drawModalRectWithCustomSizedTexture((float)x, (float)z, u, var9, width, height, textureWidth, textureHeight);
        GlStateManager.popMatrix();
    }
}
