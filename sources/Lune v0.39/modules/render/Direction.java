package me.superskidder.lune.modules.render;

import me.superskidder.lune.events.EventPreUpdate;
import me.superskidder.lune.events.EventRender3D;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.utils.render.Colors;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.utils.timer.TimerUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Iterator;

public class Direction extends Mod {
	public EntityPlayer player;
	private ArrayList openList = new ArrayList();
	private ArrayList closedList = new ArrayList();
	private ArrayList path = new ArrayList();
	private TimerUtil timer = new TimerUtil();
	private Thread thread = new Thread();
	private boolean startNextThread = true;

	public Direction() {
		super("Direction", ModCategory.Render,"Draw a line to nearest player");
	}

	@Override
	public void onEnabled() {
		this.setEntity();
		if (this.player != null) {
			this.astar();
		}

	}

	public void onDisable() {
		super.onDisable();
	}

	@EventTarget
	public void onUpdate(EventPreUpdate event) {
		this.setEntity();
		if (this.player != null && this.startNextThread) {
			this.setEntity();
			this.startNextThread = false;
			this.openList.clear();
			this.closedList.clear();
			this.path.clear();
			Runnable run = () -> {
				this.astar();
			};
			(new Thread(run)).start();
		}

	}

	@EventTarget
	public void onRender(EventRender3D event) {
		if (this.path.size() > 2) {
			double x = this.player.lastTickPosX
					+ (this.player.posX - this.player.lastTickPosX) * (double) this.mc.timer.renderPartialTicks
					- this.mc.getRenderManager().renderPosX;
			double y = this.player.lastTickPosY
					+ (this.player.posY - this.player.lastTickPosY) * (double) this.mc.timer.renderPartialTicks
					- this.mc.getRenderManager().renderPosY;
			double z = this.player.lastTickPosZ
					+ (this.player.posZ - this.player.lastTickPosZ) * (double) this.mc.timer.renderPartialTicks
					- this.mc.getRenderManager().renderPosZ;
			double playerX = this.mc.thePlayer.lastTickPosX + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX)
					* (double) this.mc.timer.renderPartialTicks - this.mc.getRenderManager().renderPosX;
			double playerY = this.mc.thePlayer.lastTickPosY + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY)
					* (double) this.mc.timer.renderPartialTicks - this.mc.getRenderManager().renderPosY;
			double playerZ = this.mc.thePlayer.lastTickPosZ + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ)
					* (double) this.mc.timer.renderPartialTicks - this.mc.getRenderManager().renderPosZ;
			GL11.glPushMatrix();
			GL11.glEnable(3042);
			GL11.glEnable(2848);
			GL11.glDisable(2929);
			GL11.glDisable(3553);
			GL11.glBlendFunc(770, 771);
			GL11.glLineWidth(2.85F);
			RenderUtil.color(Colors.YELLOW.c);
			GL11.glLoadIdentity();
			boolean bobbing = this.mc.gameSettings.viewBobbing;
			this.mc.gameSettings.viewBobbing = false;
			this.mc.entityRenderer.orientCamera(this.mc.timer.renderPartialTicks);
			GL11.glBegin(3);
			GL11.glVertex3d(x, y + (double) this.player.getEyeHeight(), z);
			GL11.glVertex3d(x, y, z);

			for (int i = 0; i < this.path.size(); ++i) {
				AStarNode node = (AStarNode) this.path.get(i);
				GL11.glVertex3d(node.getX() - this.mc.getRenderManager().renderPosX,
						this.player.posY - this.mc.getRenderManager().renderPosY,
						node.getZ() - this.mc.getRenderManager().renderPosZ);
			}

			GL11.glVertex3d(playerX, playerY, playerZ);
			GL11.glEnd();
			this.mc.gameSettings.viewBobbing = bobbing;
			GL11.glEnable(3553);
			GL11.glEnable(2929);
			GL11.glDisable(2848);
			GL11.glDisable(3042);
			GL11.glPopMatrix();
		}

	}

	private void astar() {
		this.openList.clear();
		this.closedList.clear();
		this.path.clear();
		double pX = (double) ((int) this.mc.thePlayer.posX);
		double pZ = (double) ((int) this.mc.thePlayer.posZ);
		double eX = (double) ((int) this.player.posX);
		double eZ = (double) ((int) this.player.posZ);
		AStarNode startNode = new AStarNode(pX, pZ);
		this.openList.add(startNode);
		int steps = 0;
		if (pX == eX && pZ == eZ) {
			this.startNextThread = true;
		} else {
			System.out.println("Astar..");
			long start = System.currentTimeMillis();

			label72: while (!this.openList.isEmpty()) {
				++steps;
				int nextNode = -1;
				int distance = Integer.MAX_VALUE;

				AStarNode newNode;
				for (int node = 0; node < this.openList.size(); ++node) {
					newNode = (AStarNode) this.openList.get(node);
					newNode.setHeuristic(this.getHeuristic(eX, eZ, newNode.getX(), newNode.getZ()));
					if (nextNode == -1 || newNode.getHeuristic() < (double) distance) {
						nextNode = node;
						distance = (int) newNode.getHeuristic();
					}
				}

				AStarNode var23 = (AStarNode) this.openList.get(nextNode);
				this.closedList.add(var23);
				this.openList.remove(nextNode);
				newNode = (AStarNode) this.closedList.get(this.closedList.size() - 1);
				AStarNode lastNode = null;

				label60: for (double pathPoint = newNode.getX() - 1.0D; pathPoint <= newNode.getX()
						+ 1.0D; ++pathPoint) {
					for (double z = newNode.getZ() - 1.0D; z <= newNode.getZ() + 1.0D; ++z) {
						if (!this.isObsctacle(pathPoint, z) && !this.isInClosedList(pathPoint, z)) {
							AStarNode neighbourNode = new AStarNode(pathPoint, z);
							neighbourNode.setParent(newNode);
							this.openList.add(neighbourNode);
							if (pathPoint == eX && z == eZ) {
								lastNode = neighbourNode;
								break label60;
							}
						}
					}
				}

				if (lastNode != null) {
					AStarNode var24 = lastNode;
					this.path.add(lastNode);

					while (true) {
						if ((var24 = var24.getParent()) == null) {
							break label72;
						}

						this.path.add(var24);
					}
				}

				if (System.currentTimeMillis() - 1000L > start) {
					break;
				}
			}

			this.startNextThread = true;
			System.out.println(steps);
		}
	}

	private boolean isInClosedList(double x, double z) {
		Iterator var6 = this.closedList.iterator();

		AStarNode node;
		do {
			if (!var6.hasNext()) {
				return false;
			}

			node = (AStarNode) var6.next();
		} while (node.getX() != x || node.getZ() != z);

		return true;
	}

	private int getHeuristic(double x1, double z1, double x2, double z2) {
		return (int) (Math.abs(x2 - x1) + Math.abs(z2 - z1));
	}

	private boolean isObsctacle(double x, double z) {
		BlockPos pos = new BlockPos(x, (double) ((int) this.player.posY), z);
		return !(this.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)
				|| this.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof BlockAir
						&& this.mc.theWorld.getBlockState(pos.add(0, -2, 0)).getBlock() instanceof BlockAir;
	}

	public void setEntity() {
		EntityPlayer newPlayer = null ;
		Iterator var3 = this.mc.theWorld.playerEntities.iterator();

		while (var3.hasNext()) {
			EntityPlayer player = (EntityPlayer) var3.next();
			if (this.mc.thePlayer != player && !player.isInvisible() && !player.isDead) {
				if (newPlayer == null) {
					newPlayer = player;
				} else if (this.mc.thePlayer.getDistanceToEntity(player) < this.mc.thePlayer
						.getDistanceToEntity(newPlayer)) {
					newPlayer = player;
				}
			}
		}

		this.player = newPlayer;
	}
}
