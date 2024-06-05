package zamorozka.modules.VISUALLY;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import zamorozka.event.EventTarget;
import zamorozka.event.events.RenderEvent3D;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.EXPLOITS.GhostHand;
import zamorozka.ui.RenderUtils2;
import zamorozka.ui.Wrapper;

public class Trajectories extends Module {
	private double x, y, z;
	private double motionX, motionY, motionZ;
	private boolean hitEntity = false;
	private double r, g, b;
	public double pX = -9000, pY = -9000, pZ = -9000;
	private EntityLivingBase entity;
	private RayTraceResult blockCollision, entityCollision;
	private static AxisAlignedBB aim;

	public Trajectories() {
		super("Trajectories", Keyboard.KEY_NONE, Category.VISUALLY);
	}

	@EventTarget
	public void onRender3D(RenderEvent3D event) {
		try {
			if (ModuleManager.getModule(GhostHand.class).getState()
					&& mc.player.getHeldItemMainhand().getItem() instanceof ItemEnderPearl)
				return;
			if (mc.player.inventory.getCurrentItem() != null) {
				EntityPlayerSP player = mc.player;
				ItemStack stack = player.inventory.getCurrentItem();
				int itemMain = Item.getIdFromItem(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem());
				int itemOff = Item.getIdFromItem(mc.player.getHeldItem(EnumHand.OFF_HAND).getItem());

				if (((itemMain == 261 || itemOff == 261) || (itemMain == 368 || itemOff == 368)
						|| (itemMain == 332 || itemOff == 332) || (itemMain == 344 || itemOff == 344))) {
					double posX = player.lastTickPosX
							+ (player.posX - player.lastTickPosX) * mc.timer.renderPartialTicks
							- Math.cos(Math.toRadians(player.rotationYaw)) * 0.16F;
					double posY = player.lastTickPosY
							+ (player.posY - player.lastTickPosY) * mc.timer.renderPartialTicks + player.getEyeHeight()
							- 0.1D;
					double posZ = player.lastTickPosZ
							+ (player.posZ - player.lastTickPosZ) * mc.timer.renderPartialTicks
							- Math.sin(Math.toRadians(player.rotationYaw)) * 0.16F;
					double itemBow = stack.getItem() instanceof ItemBow ? 1.0F : 0.4F;
					double yaw = Math.toRadians(player.rotationYaw);
					double pitch = Math.toRadians(player.rotationPitch);
					double trajectoryX = -Math.sin(yaw) * Math.cos(pitch) * itemBow;
					double trajectoryY = -Math.sin(pitch) * itemBow;
					double trajectoryZ = Math.cos(yaw) * Math.cos(pitch) * itemBow;
					double trajectory = Math
							.sqrt(trajectoryX * trajectoryX + trajectoryY * trajectoryY + trajectoryZ * trajectoryZ);

					trajectoryX /= trajectory;
					trajectoryY /= trajectory;
					trajectoryZ /= trajectory;

					if (stack.getItem() instanceof ItemBow) {
						float bowPower = (72000 - player.getItemInUseCount()) / 20.0F;
						bowPower = (bowPower * bowPower + bowPower * 2.0F) / 3.0F;
						if (bowPower > 1.0F) {
							bowPower = 1.0F;
						}
						bowPower *= 3.0F;
						trajectoryX *= bowPower;
						trajectoryY *= bowPower;
						trajectoryZ *= bowPower;
					} else {
						trajectoryX *= 1.5D;
						trajectoryY *= 1.5D;
						trajectoryZ *= 1.5D;
					}

					GL11.glPushMatrix();
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glDisable(GL11.GL_DEPTH_TEST);
					GL11.glDepthMask(false);
					GL11.glEnable(GL11.GL_LINE_SMOOTH);
					GL11.glLineWidth(2.0F);
					double gravity = stack.getItem() instanceof ItemBow ? 0.05D : 0.03D;
					GL11.glColor4f(255, 255, 255, 0.5F);
					GL11.glBegin(GL11.GL_LINE_STRIP);

					for (int i = 0; i < 2000; i++) {
						GL11.glVertex3d(posX - mc.getRenderManager().renderPosX,
								posY - mc.getRenderManager().renderPosY, posZ - mc.getRenderManager().renderPosZ);

						posX += trajectoryX * 0.1D;
						posY += trajectoryY * 0.1D;
						posZ += trajectoryZ * 0.1D;

						trajectoryX *= 0.999D;
						trajectoryY *= 0.999D;
						trajectoryZ *= 0.999D;

						trajectoryY = (trajectoryY - gravity * 0.1D);
						Vec3d vec = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
						blockCollision = mc.world.rayTraceBlocks(vec, new Vec3d(posX, posY, posZ));

						for (Entity o : mc.world.getLoadedEntityList()) {
							if (o instanceof EntityLivingBase && !(o instanceof EntityPlayerSP)) {
								entity = (EntityLivingBase) o;
								AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox().expand(0.3D, 0.3D,
										0.3D);
								entityCollision = entityBoundingBox.calculateIntercept(vec,
										new Vec3d(posX, posY, posZ));

								if (entityCollision != null) {
									blockCollision = entityCollision;
								}

								if (entityCollision != null) {
									GL11.glColor4f(1.0F, 0.0F, 0.2F, 0.5F);
								}

								if (entityCollision != null) {
									blockCollision = entityCollision;
								}
							}
						}
						if (blockCollision != null) {
							break;
						}
					}
					GL11.glEnd();
					double renderX = posX - mc.getRenderManager().renderPosX;
					double renderY = posY - mc.getRenderManager().renderPosY;
					double renderZ = posZ - mc.getRenderManager().renderPosZ;
					GL11.glPushMatrix();
					GL11.glTranslated(renderX - 0.5D, renderY - 0.5D, renderZ - 0.5D);
					switch (blockCollision.sideHit.getIndex()) {
					case 2:
					case 3:
						GlStateManager.rotate(90, 1, 0, 0);
						aim = new AxisAlignedBB(0.0D, 0.5D, -1.0D, 1.0D, 0.45D, 0.0D);
						break;

					case 4:
					case 5:
						GlStateManager.rotate(90, 0, 0, 1);
						aim = new AxisAlignedBB(0.0D, -0.5D, 0.0D, 1.0D, -0.45D, 1.0D);
						break;

					default:
						aim = new AxisAlignedBB(0.0D, 0.5, 0.0D, 1.0D, 0.45D, 1.0D);
						break;
					}

					drawBox(aim);
					func_181561_a(aim);
					GL11.glPopMatrix();
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glEnable(GL11.GL_DEPTH_TEST);
					GL11.glDepthMask(true);
					GL11.glDisable(GL11.GL_LINE_SMOOTH);
					GL11.glPopMatrix();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void func_181561_a(AxisAlignedBB p_181561_0_) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(3, DefaultVertexFormats.POSITION);
		worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
		worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
		worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
		worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
		worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
		tessellator.draw();
		worldrenderer.begin(3, DefaultVertexFormats.POSITION);
		worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
		worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
		worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
		worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
		worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
		tessellator.draw();
		worldrenderer.begin(1, DefaultVertexFormats.POSITION);
		worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
		worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
		worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
		worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
		worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
		worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
		worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
		worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
		tessellator.draw();
	}

	public static void drawBox(AxisAlignedBB bb) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glEnd();
	}
}