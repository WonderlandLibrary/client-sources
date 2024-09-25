package none.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCarpet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.HWID;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event2D;
import none.event.events.Event3D;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventTick;
import none.friend.FriendManager;
import none.module.Category;
import none.module.Module;
import none.module.modules.render.ClientColor;
import none.path.PathFinder;
import none.path.PathFinder.Node;
import none.utils.RenderingUtil;
import none.utils.RotationUtils;
import none.utils.Targeter;
import none.utils.TeamUtils;
import none.utils.Utils;
import none.utils.render.Colors;
import none.utils.render.TTFFontRenderer;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class InfiniteAura extends Module {

	public InfiniteAura() {
		super("InfiniteAura", "InfiniteAura", Category.COMBAT, Keyboard.KEY_NONE);
	}

	public String[] mode = { "AStar" };
	public ModeValue modes = new ModeValue("Path-Mode", "AStar", mode);
	public BooleanValue testpath = new BooleanValue("Test-Path", true);
	public String[] sortmode = { "Angle", "Range", "FOV", "Armor", "Health", "FightTick" };
	public ModeValue sortmodes = new ModeValue("Sort-Mode", "Angle", sortmode);
	public NumberValue<Double> rangeValue = new NumberValue<>("Range", 20.0, 10.0, 256.0);
	public NumberValue<Integer> mincps = new NumberValue<>("Min-CPS", 2, 1, 20);
	public NumberValue<Integer> maxcps = new NumberValue<>("Max-CPS", 4, 1, 20);
	public BooleanValue teams = new BooleanValue("Teams", false);
	public static BooleanValue noy = new BooleanValue("No-Y", true);
	public EntityLivingBase target = null;
	public PathFinder pathfinder = new PathFinder();
	public ArrayList<Vec3> path = new ArrayList<>();
	public ArrayList<Node> nodes = new ArrayList<>();
	public ArrayList<Node> nodetowork = new ArrayList<>();
	public int timer = 0;

	@Override
	protected void onEnable() {
		if (!HWID.isHWID()) {
			evc("Premium Only");
			Client.instance.notification.show(Client.notification("Premium", "You are not Premium", 3));
			setState(false);
			return;
		}
		if (pathfinder.cantfind) {
			pathfinder.cantfind = false;
		}
		pathfinder.nodetowork.clear();
		pathfinder.nodes.clear();
		target = null;
		path = null;
		nodes.clear();
		nodetowork.clear();
		timer = 0;
		super.onEnable();
	}

	@Override
	protected void onDisable() {
		target = null;
		super.onDisable();
	}

	@Override
	@RegisterEvent(events = { EventTick.class, EventPreMotionUpdate.class, Event2D.class, Event3D.class })
	public void onEvent(Event event) {
		if (!isEnabled())
			return;
		if (!HWID.isHWID()) {
			return;
		}
		int cps = Utils.random(mincps.getObject(), maxcps.getObject());
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (e.isPre()) {
				timer++;
				target = getNearTargets();
				if (target == null) {
					path = null;
				} else if (target != null) {
					if (modes.getSelected().equalsIgnoreCase("AStar")) {
						if (testpath.getObject()) {
							if (mc.thePlayer.ticksExisted % 5 == 0)
								path = pathfinder.doPath(mc.thePlayer.getPositionVector2(),
										target.getPositionVector2());
							nodes = pathfinder.nodes;
							nodetowork = pathfinder.nodetowork;
						} else {
							if (!nodes.isEmpty()) {
								nodes.clear();
							}
							if (!nodetowork.isEmpty()) {
								nodetowork.clear();
							}
							if (timer >= 20 / cps) {
								pathfinder.cantfind = false;
								
							    path = pathfinder.doPath2(200, mc.thePlayer.getPositionVector2(),
							    		target.getPositionVector2());
								timer = 0;
								if (path != null) {
									for (Vec3 vec : path) {
										boolean onGround = !(mc.theWorld
												.getBlock(new BlockPos(vec)) instanceof BlockAir);
										mc.thePlayer.connection.sendPacket(new C04PacketPlayerPosition(vec.xCoord,
												(mc.theWorld.getBlock(new BlockPos(vec)) instanceof BlockCarpet)
														? vec.yCoord + 0.1
														: vec.yCoord,
												vec.zCoord, true));
									}

									mc.thePlayer.connection.sendPacket(
											new C04PacketPlayerPosition(target.posX, target.posY, target.posZ, true));

									mc.thePlayer.swingItem();
									mc.thePlayer.connection.sendPacket(
											new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
									Collections.reverse(path);

									for (Vec3 vec : path) {
										boolean onGround = !(mc.theWorld
												.getBlock(new BlockPos(vec)) instanceof BlockAir);
										mc.thePlayer.connection.sendPacket(new C04PacketPlayerPosition(vec.xCoord,
												(mc.theWorld.getBlock(new BlockPos(vec)) instanceof BlockCarpet)
														? vec.yCoord + 0.1
														: vec.yCoord,
												vec.zCoord, true));
									}
								}
							}
						}

					}
				}
			}
		}

		if (event instanceof Event3D) {
			Event3D event3d = (Event3D) event;
			if (testpath.getObject()) {
				if (nodes != null && !nodes.isEmpty())
					for (Node node : nodes) {
						drawPathNode(node);
					}
				if (nodetowork != null && !nodetowork.isEmpty())
					for (Node node : nodetowork) {
						drawPathNodeToWork(node);
					}
			}
			if (path != null) {
				for (int i = 0; i < path.size(); i++) {
					Vec3 vec = path.get(i);
					Vec3 last = null;
					if (i > 0) {
						last = path.get(i - 1);
					}
					drawPath(last, vec);
				}
			}
		}
	}

	public void drawPath(Vec3 last, Vec3 vec) {
		int renderColor = ClientColor.getColor();
		if (ClientColor.rainbow.getObject()) {
			renderColor = ClientColor.rainbow(10000);
		}
		if (last == null) {
			last = vec;
		}
		float beginX = (float) ((float) last.xCoord - RenderManager.renderPosX);
		float beginY = (float) ((float) last.yCoord - RenderManager.renderPosY);
		float beginZ = (float) ((float) last.zCoord - RenderManager.renderPosZ);
		float endX = (float) ((float) vec.xCoord - RenderManager.renderPosX);
		float endY = (float) ((float) vec.yCoord - RenderManager.renderPosY);
		float endZ = (float) ((float) vec.zCoord - RenderManager.renderPosZ);
		final boolean bobbing = mc.gameSettings.viewBobbing;
		mc.gameSettings.viewBobbing = false;
		RenderingUtil.drawLine3D(beginX, beginY, beginZ, endX, endY, endZ, renderColor);
		mc.gameSettings.viewBobbing = bobbing;
	}

	public void drawPathNode(Node vec1) {
		Vec3 vec = vec1.getLoc();
		double x = vec.xCoord - RenderManager.renderPosX;
		double y = vec.yCoord - RenderManager.renderPosY;
		double z = vec.zCoord - RenderManager.renderPosZ;
		double width = 0.3;
		double height = mc.thePlayer.getEyeHeight();
		RenderingUtil.pre3D();
		GL11.glLoadIdentity();
		mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
		int colors[] = { Colors.getColor(Color.black), Colors.getColor(Color.red) };
		for (int i = 0; i < 2; i++) {
			RenderingUtil.glColor(colors[i]);
			GL11.glLineWidth(3 - i * 2);
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x - width, y, z - width);
			GL11.glVertex3d(x - width, y, z - width);
			GL11.glVertex3d(x - width, y + height, z - width);
			GL11.glVertex3d(x + width, y + height, z - width);
			GL11.glVertex3d(x + width, y, z - width);
			GL11.glVertex3d(x - width, y, z - width);
			GL11.glVertex3d(x - width, y, z + width);
			GL11.glEnd();
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x + width, y, z + width);
			GL11.glVertex3d(x + width, y + height, z + width);
			GL11.glVertex3d(x - width, y + height, z + width);
			GL11.glVertex3d(x - width, y, z + width);
			GL11.glVertex3d(x + width, y, z + width);
			GL11.glVertex3d(x + width, y, z - width);
			GL11.glEnd();
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x + width, y + height, z + width);
			GL11.glVertex3d(x + width, y + height, z - width);
			GL11.glEnd();
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x - width, y + height, z + width);
			GL11.glVertex3d(x - width, y + height, z - width);
			GL11.glEnd();
		}

		RenderingUtil.post3D();
	}

	public void drawPathNodeToWork(Node vec1) {
		Vec3 vec = vec1.getLoc();
		double x = vec.xCoord - RenderManager.renderPosX;
		double y = vec.yCoord - RenderManager.renderPosY;
		double z = vec.zCoord - RenderManager.renderPosZ;
		double width = 0.3;
		double height = mc.thePlayer.getEyeHeight();
		RenderingUtil.pre3D();
		GL11.glLoadIdentity();
		mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
		int colors[] = { Colors.getColor(Color.black), Colors.getColor(Color.green) };
		for (int i = 0; i < 2; i++) {
			RenderingUtil.glColor(colors[i]);
			GL11.glLineWidth(3 - i * 2);
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x - width, y, z - width);
			GL11.glVertex3d(x - width, y, z - width);
			GL11.glVertex3d(x - width, y + height, z - width);
			GL11.glVertex3d(x + width, y + height, z - width);
			GL11.glVertex3d(x + width, y, z - width);
			GL11.glVertex3d(x - width, y, z - width);
			GL11.glVertex3d(x - width, y, z + width);
			GL11.glEnd();
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x + width, y, z + width);
			GL11.glVertex3d(x + width, y + height, z + width);
			GL11.glVertex3d(x - width, y + height, z + width);
			GL11.glVertex3d(x - width, y, z + width);
			GL11.glVertex3d(x + width, y, z + width);
			GL11.glVertex3d(x + width, y, z - width);
			GL11.glEnd();
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x + width, y + height, z + width);
			GL11.glVertex3d(x + width, y + height, z - width);
			GL11.glEnd();
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x - width, y + height, z + width);
			GL11.glVertex3d(x - width, y + height, z - width);
			GL11.glEnd();
		}

		float[] xyz = { (float) (vec.xCoord - mc.thePlayer.posX), (float) (vec.yCoord - mc.thePlayer.posY),
				(float) (vec.zCoord - mc.thePlayer.posZ) };
		float f = RenderManager.playerViewY;
		float f1 = RenderManager.playerViewX;
		boolean flag1 = RenderManager.options.thirdPersonView == 2;
		TTFFontRenderer fr = Client.fm.getFont("BebasNeue");
		drawNameplate(fr, "G:" + vec1.gcost, xyz[0], xyz[1] + 1, xyz[2], 0, f, f1, flag1, false);
		drawNameplate(fr, "H:" + vec1.hcost, xyz[0], xyz[1] + 2, xyz[2], 0, f, f1, flag1, false);
		drawNameplate(fr, "F:" + vec1.getFcost(), xyz[0], xyz[1] + 3, xyz[2], 0, f, f1, flag1, false);
		RenderingUtil.post3D();
	}

	public void drawNameplate(TTFFontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift,
			float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking) {
		Minecraft mc = Minecraft.getMinecraft();
		RenderManager renderManager = mc.getRenderManager();
		float f = 1.6F;
		float f1 = 0.016666668F * f;
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(-f1, -f1, f1);
		GlStateManager.disableLighting();
		GlStateManager.depthMask(false);
		GlStateManager.disableDepth();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		byte b0 = 0;
		int renderColor = ClientColor.rainbow.getObject() ? ClientColor.rainbow(100) : ClientColor.getColor();

		int i = (int) (fontRendererIn.getStringWidth(str) / 2);
		GlStateManager.disableTexture2D();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos((double) (-i - 1), (double) (-1 + b0), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		worldrenderer.pos((double) (-i - 1), (double) (8 + b0), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		worldrenderer.pos((double) (i + 1), (double) (8 + b0), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		worldrenderer.pos((double) (i + 1), (double) (-1 + b0), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, b0, renderColor);
		GlStateManager.enableDepth();
		GlStateManager.depthMask(true);
		fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, b0, renderColor);
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}

	private boolean isValid(Entity entity) {
		double range = rangeValue.getObject();
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
			return mc.thePlayer.getDistanceToEntity(entity) <= range;
		}
//    	if (Targeter.isTarget(entity) && !entity.isDead) {
//    		return entity.getDistance(-35, 72, 38) <= range && (Utils.canEntityBeSeen((EntityLivingBase)entity) || walls.getObject());
//    	}
		return false;
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
				if (mc.thePlayer.getDistanceToEntity(entity) > rangeValue.getObject()) {
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
//			weed.sort(Comparator.comparingDouble(o -> (RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationYaw,
//					RotationUtils.getRotations(o)[0]))));
			weed.sort(new Comparator<EntityLivingBase>() {
				@Override
				public int compare(EntityLivingBase o1, EntityLivingBase o2) {
					float yaw = mc.thePlayer.rotationYaw;
					float[] rot1 = RotationUtils.getRotations(o1);
					float[] rot2 = RotationUtils.getRotations(o2);
					if (RotationUtils.getDistanceBetweenAngles(yaw, rot1[0]) < RotationUtils.getDistanceBetweenAngles(yaw, rot2[0])) {
						return -1;
					}else if (RotationUtils.getDistanceBetweenAngles(yaw, rot1[0]) > RotationUtils.getDistanceBetweenAngles(yaw, rot2[0])) {
						return 1;
					}
					return 0;
				};
			});
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
}
