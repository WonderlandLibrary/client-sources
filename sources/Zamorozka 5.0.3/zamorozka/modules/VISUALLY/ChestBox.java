package zamorozka.modules.VISUALLY;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;
import optifine.BlockDir;
import zamorozka.event.EventTarget;
import zamorozka.event.events.RenderEvent3D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.PLAYER.ChestStealer;
import zamorozka.ui.RenderUtils;
import zamorozka.ui.RenderUtils2;
import zamorozka.ui.RenderingUtils;

public class ChestBox extends Module {
	public ChestBox() {
		super("ChestESP", 0, Category.VISUALLY);
	}

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("BOX", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("LineBox", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("AutoEsp", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("WallHack", this, false));
	}

	TileEntity ent;

	public void onRender() {
		if (this.getState()) {
			for (Object o : mc.world.loadedTileEntityList) {
				if (o instanceof TileEntityChest) {
					float[] color = getColor(ent);

					blockESPBox2(((TileEntityChest) o).getPos(), null,
							(new Color(color[0], color[1], color[2])).getRGB());
					// blockESPBox3(((TileEntityChest)o).getPos(), null, (new Color(color[0],
					// color[1], color[2])).getRGB());
				}
			}
		}
	}

	public static void blockESPBox2(BlockPos blockPos, AxisAlignedBB bb, int color) {

		double var10000 = (double) blockPos.getX();
		Minecraft.getMinecraft().getRenderManager();
		double x = var10000 - RenderManager.renderPosX;
		var10000 = (double) blockPos.getY();
		Minecraft.getMinecraft().getRenderManager();
		double y = var10000 - RenderManager.renderPosY;
		var10000 = (double) blockPos.getZ();
		Minecraft.getMinecraft().getRenderManager();
		double z = var10000 - RenderManager.renderPosZ;

		GL11.glPushMatrix();

		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glLineWidth(1.5F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(1.0F);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);

		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

		// RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0F, y
		// + 1.0, z + 1.0F), 0.4f, 0.6f, 1.0f, 1.0f);
		// RenderGlobal.drawBoundingBox(x, y, z, x + 1.0F, y + 1.0, z + 1.0F, 0.4f,
		// 0.6f, 1.0f, 1.0f);

		if (Zamorozka.settingsManager.getSettingByName("LineBox").getValBoolean()) {
			RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0F, y + 1.0, z + 1.0F), 0.4f, 0.6f,
					1.0f, 1.0f);

		}
		if (Zamorozka.settingsManager.getSettingByName("AutoEsp").getValBoolean()) {
			float f = (float) mc.player.getDistanceSq(blockPos);
			GL11.glColor4f(0.4f, 0.6f, 1.0f, 1.0f);
			RenderUtils.drawOutlinedBox(new AxisAlignedBB(x + 0.06, y, z + 0.06, x + 0.94, y + .88, z + 0.95));
			// RenderUtils.drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0F, y + 1.0, z +
			// 1.0F), 0.4f, 0.6f, 1.0f, 1.0f);
		}
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	public static void blockESPBox3(BlockPos blockPos, AxisAlignedBB bb, int color) {

		double var10000 = (double) blockPos.getX();
		Minecraft.getMinecraft().getRenderManager();
		double x = var10000 - RenderManager.renderPosX - 0.5;
		var10000 = (double) blockPos.getY();
		Minecraft.getMinecraft().getRenderManager();
		double y = var10000 - RenderManager.renderPosY - 0.5;
		var10000 = (double) blockPos.getZ();
		Minecraft.getMinecraft().getRenderManager();
		double z = var10000 - RenderManager.renderPosZ - 0.5;

		GL11.glPushMatrix();

		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glLineWidth(1.5F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(1.0F);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);

		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0F, y + 1.0, z + 1.0F), 0.4f, 0.6f, 1.0f,
				1.0f);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	private float[] getColor(TileEntity ent) {
		if (ent instanceof TileEntityChest) {
			return new float[] { 0.0F, 0.5F, 0.5F };
		}
		if (ent instanceof net.minecraft.tileentity.TileEntityDispenser) {
			return new float[] { 0.5F, 0.5F, 0.5F };
		}
		if (ent instanceof net.minecraft.tileentity.TileEntityEnderChest) {
			return new float[] { 0.3F, 0.0F, 0.3F };
		}
		return new float[] { 1.0F, 1.0F, 1.0F };
	}

	@EventTarget
	public void onRender3D(RenderEvent3D render) {
		if (Zamorozka.settingsManager.getSettingByName("BOX").getValBoolean()) {
			for (final Object o : mc.world.loadedTileEntityList) {
				if (o instanceof TileEntityChest) {
					final TileEntityLockable storage = (TileEntityLockable) o;
					this.drawESPOnStorage(storage, storage.getPos().getX(), storage.getPos().getY(),
							storage.getPos().getZ());

				}
			}
		}
	}

	public void drawESPOnStorage(TileEntityLockable storage, double x, double y, double z) {
		assert !storage.isLocked();
		TileEntityChest chest = (TileEntityChest) storage;
		Vec3d vec;
		Vec3d vec2;
		if (chest.adjacentChestZNeg != null) {
			vec = new Vec3d(x + 0.0625, y, z - 0.9375);
			vec2 = new Vec3d(x + 0.9375, y + 0.875, z + 0.9375);
		} else if (chest.adjacentChestXNeg != null) {
			vec = new Vec3d(x + 0.9375, y, z + 0.0625);
			vec2 = new Vec3d(x - 0.9375, y + 0.875, z + 0.9375);
		} else if (chest.adjacentChestXPos == null && chest.adjacentChestZPos == null) {
			vec = new Vec3d(x + 0.0625, y, z + 0.0625);
			vec2 = new Vec3d(x + 0.9375, y + 0.875, z + 0.9375);
		} else {
			return;
		}
		GL11.glPushMatrix();
		RenderingUtils.pre3D();
		mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
		if (chest.getChestType() == BlockChest.Type.TRAP) {
			GL11.glColor4d(0.7, 0.1, 0.1, 0.3);
		} else if (chest.isEmpty && ModuleManager.getModule(ChestStealer.class).getState()) {
			GL11.glColor4d(0.4, 0.2, 0.2, 0.3);
		} else {
			Color c = RenderUtils2.effect(1, 1, 1);
			GL11.glColor4d(1f, 1, 1.0f, .4f);
		}
		RenderingUtils.drawBoundingBox(
				new AxisAlignedBB(vec.xCoord - RenderManager.renderPosX, vec.yCoord - RenderManager.renderPosY,
						vec.zCoord - RenderManager.renderPosZ, vec2.xCoord - RenderManager.renderPosX,
						vec2.yCoord - RenderManager.renderPosY, vec2.zCoord - RenderManager.renderPosZ));
		GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		RenderingUtils.post3D();
		GL11.glPopMatrix();
	}

	public void draw(Block paramBlock, double paramDouble1, double paramDouble2, double paramDouble3,
			double paramDouble4, double paramDouble5, double paramDouble6) {
		GL11.glDisable(2896);
		GL11.glDisable(3553);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(2929);
		GL11.glEnable(2848);
		GL11.glDepthMask(false);
		GL11.glLineWidth(0.3F);
		if (paramBlock == Blocks.ENDER_CHEST) {
			GL11.glColor4f(0.4F, 0.2F, 1.0F, 1.0F);
			paramDouble1 += 0.05000000074505806D;
			paramDouble2 += 0.0D;
			paramDouble3 += 0.05000000074505806D;
			paramDouble4 -= 0.10000000149011612D;
			paramDouble5 -= 0.10000000149011612D;
			paramDouble6 -= 0.10000000149011612D;
		} else if (paramBlock == Blocks.CHEST) {
			GL11.glColor4f(1.0F, 1.0F, 0.0F, 1.0F);
			paramDouble1 += 0.05000000074505806D;
			paramDouble2 += 0.0D;
			paramDouble3 += 0.05000000074505806D;
			paramDouble4 -= 0.10000000149011612D;
			paramDouble5 -= 0.10000000149011612D;
			paramDouble6 -= 0.10000000149011612D;
		} else if (paramBlock == Blocks.TRAPPED_CHEST) {
			GL11.glColor4f(1.0F, 0.6F, 0.0F, 1.0F);
			paramDouble1 += 0.05000000074505806D;
			paramDouble2 += 0.0D;
			paramDouble3 += 0.05000000074505806D;
			paramDouble4 -= 0.10000000149011612D;
			paramDouble5 -= 0.10000000149011612D;
			paramDouble6 -= 0.10000000149011612D;
		} else if (paramBlock == Blocks.FURNACE) {
			GL11.glColor4f(0.6F, 0.6F, 0.6F, 1.0F);
		} else if (paramBlock == Blocks.LIT_FURNACE) {
			GL11.glColor4f(1.0F, 0.4F, 0.0F, 1.0F);
		}

		RenderingUtils.drawOutlinedBoundingBox(new AxisAlignedBB(paramDouble1, paramDouble2, paramDouble3,
				paramDouble1 + paramDouble4, paramDouble2 + paramDouble5, paramDouble3 + paramDouble6));
		if (paramBlock == Blocks.ENDER_CHEST) {
			GL11.glColor4f(0.4F, 0.2F, 1.0F, 0.11F);
		} else if (paramBlock == Blocks.CHEST) {
			GL11.glColor4f(1.0F, 1.0F, 0.0F, 0.11F);
		} else if (paramBlock == Blocks.TRAPPED_CHEST) {
			GL11.glColor4f(1.0F, 0.6F, 0.0F, 0.11F);
		} else if (paramBlock == Blocks.FURNACE) {
			GL11.glColor4f(0.6F, 0.6F, 0.6F, 0.11F);
		} else if (paramBlock == Blocks.LIT_FURNACE) {
			GL11.glColor4f(1.0F, 0.4F, 0.0F, 0.11F);
		}

		RenderingUtils.drawFilledBox(new AxisAlignedBB(paramDouble1, paramDouble2, paramDouble3,
				paramDouble1 + paramDouble4, paramDouble2 + paramDouble5, paramDouble3 + paramDouble6));
		GL11.glDepthMask(true);
		GL11.glDisable(2848);
		GL11.glEnable(2929);
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glEnable(3553);
	}
}