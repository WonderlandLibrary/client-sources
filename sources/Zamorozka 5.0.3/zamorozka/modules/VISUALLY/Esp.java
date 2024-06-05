package zamorozka.modules.VISUALLY;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import zamorozka.event.EventTarget;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.AttackEvent;

public class Esp extends Module {

	public Esp() {
		super("ESP", 0, Category.VISUALLY);
	}

	public static int playerBox;
	private final ArrayList<EntityPlayer> players = new ArrayList();

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("LiteBoxesLine", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("PlayerLine", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("2DBoxesLine", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("FullStroke", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("PlayerBox", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Outline", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Mobs", this, false));
	}

	public void onEnable() {
		if (Zamorozka.settingsManager.getSettingByName("PlayerBox").getValBoolean()) {
			this.playerBox = GL11.glGenLists(1);
			GL11.glNewList(this.playerBox, 4864);
			AxisAlignedBB bb = new AxisAlignedBB(-0.5D, 0.0D, -0.5D, 0.5D, 1.0D, 0.5D);
			drawOutlinedBox(bb);
			GL11.glEndList();
		}

	}

	public void onDisable() {
		GL11.glDeleteLists(this.playerBox, 1);
		this.playerBox = 0;
	}

	public void onUpdate() {

		if (Zamorozka.settingsManager.getSettingByName("PlayerBox").getValBoolean()) {
			EntityPlayer player = mc.player;
			World world = mc.world;

			this.players.clear();
			List<EntityPlayer> list = world.playerEntities;
			for (EntityPlayer ep : list) {
				if (ep != player) {

					this.players.add(ep);

				}
			}
		}
	}

	@EventTarget
	public void onRender() {
		if (Zamorozka.settingsManager.getSettingByName("LiteBoxesLine").getValBoolean()) {
			for (Object o : this.mc.world.loadedEntityList) {
				if (((o instanceof EntityPlayer)) && (o != this.mc.player)) {
					EntityPlayer e = (EntityPlayer) o;
					double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * this.mc.timer.elapsedTicks
							- this.mc.getRenderManager().renderPosX;
					double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * this.mc.timer.elapsedTicks
							- this.mc.getRenderManager().renderPosY;
					double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * this.mc.timer.elapsedTicks
							- this.mc.getRenderManager().renderPosZ;

					// if (e.hurtTime != 0) {
					// color = Color.RED;
					// }
					GL11.glPushMatrix();
					GL11.glTranslated(x, y - 0.2D, z);
					GL11.glScalef(0.03F, 0.03F, 0.03F);

					GL11.glRotated(-this.mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
					GlStateManager.disableDepth();
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glLineWidth(1F);
					GL11.glBlendFunc(770, 771);
					GL11.glEnable(3042);

					// Box Line
					Gui.drawRect(-21, 0, -20.5, 74, 0xFF31AFFD);

					Gui.drawRect(21, 0, 20.5, 74, 0xFF31AFFD);
					// Up+down
					Gui.drawRect(-21, 0, -8, .5, 0xFF31AFFD);
					Gui.drawRect(21, 0, 8, .5, 0xFF31AFFD);

					Gui.drawRect(-21, 73.5, 21, 74, 0xFF31AFFD);

					GL11.glDisable(3042);
					GlStateManager.enableDepth();
					GL11.glPopMatrix();
				}
			}
		}
		if (Zamorozka.settingsManager.getSettingByName("2DBoxesLine").getValBoolean()) {
			for (Object o : this.mc.world.loadedEntityList) {
				if (((o instanceof EntityPlayer)) && (o != this.mc.player)) {
					EntityPlayer e = (EntityPlayer) o;
					double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * this.mc.timer.elapsedTicks
							- this.mc.getRenderManager().renderPosX;
					double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * this.mc.timer.elapsedTicks
							- this.mc.getRenderManager().renderPosY;
					double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * this.mc.timer.elapsedTicks
							- this.mc.getRenderManager().renderPosZ;

					// if (e.hurtTime != 0) {
					// color = Color.RED;
					// }
					GL11.glPushMatrix();
					GL11.glTranslated(x, y - 0.2D, z);
					GL11.glScalef(0.03F, 0.03F, 0.03F);

					GL11.glRotated(-this.mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
					GlStateManager.disableDepth();
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glLineWidth(1F);
					GL11.glBlendFunc(770, 771);
					GL11.glEnable(3042);

					// Box Line
					Gui.drawRect(-21, 0, -20, 15, 0xFFFFFFFF);
					Gui.drawRect(21, 0, 20, 15, 0xFFFFFFFF);
					Gui.drawRect(-21, 74, -20, 59, 0xFFFFFFFF);
					Gui.drawRect(21, 74, 20, 59, 0xFFFFFFFF);
					// Up+down
					AttackEvent event = new AttackEvent(e);
					if (event.getTargetEntity() == e) {
						Gui.drawRect(-21, 0, 20, 1, 0xFFFF4D4D);
						Gui.drawRect(-21, 73, 20, 74, 0xFFFF4D4D);
					} else {
						Gui.drawRect(-21, 0, 20, 1, 0xFFFFFFFF);
						Gui.drawRect(-21, 73, 20, 74, 0xFFFFFFFF);
					}
					GL11.glDisable(3042);
					GlStateManager.enableDepth();
					GL11.glPopMatrix();
				}
			}
		}
		if (Zamorozka.settingsManager.getSettingByName("PlayerBox").getValBoolean()) {
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glEnable(2848);
			GL11.glLineWidth(2.0F);
			GL11.glDisable(3553);
			GL11.glDisable(2929);

			GL11.glPushMatrix();
			GL11.glTranslated(-mc.getRenderManager().renderPosX, -mc.getRenderManager().renderPosY,
					-mc.getRenderManager().renderPosZ);

			renderBoxes();

			GL11.glPopMatrix();

			GL11.glEnable(2929);
			GL11.glEnable(3553);
			GL11.glDisable(3042);
			GL11.glDisable(2848);
		}
	}

	private void renderBoxes() {
		for (EntityPlayer e : this.players) {

			GL11.glPushMatrix();

			GL11.glTranslated(e.prevPosX + (e.posX - e.prevPosX) * 1.0D, e.prevPosY + (e.posY - e.prevPosY) * 1.0D,
					e.prevPosZ + (e.posZ - e.prevPosZ) * 1.0D);
			GL11.glScaled(e.width + 0.1D, e.height + 0.1D, e.width + 0.1D);

			if (e.getHealth() > 15.0F) {
				GL11.glColor3f(0, 0, 255);
			} else {
				GL11.glColor3f(255, 0, 0);
			}

			float x = System.currentTimeMillis() % 2000 / 1000F;
			float red = 0.5F + 0.5F * MathHelper.sin(x * (float) Math.PI);
			float green = 0.5F + 0.5F * MathHelper.sin((x + 4F / 3F) * (float) Math.PI);
			float blue = 0.5F + 0.5F * MathHelper.sin((x + 8F / 3F) * (float) Math.PI);

			if (indexer.getFriends().isFriend(e.getName())) {
				if (e.getHealth() > 15.0F) {
					GL11.glColor4d(0, 1, 0, 0.9F);
				} else {
					GL11.glColor3f(255, 0, 0);
				}
			} else {
				if (e.getHealth() > 15.0F) {
					GL11.glColor3d(83, 146, 244);

				} else {
					GL11.glColor3f(255, 0, 0);
				}
			}

			GL11.glCallList(this.playerBox);
			GL11.glPopMatrix();

		}
	}

	public static void drawOutlinedBox(AxisAlignedBB bb) {

		GL11.glBegin(1);
		GL11.glColor4d(1, 1, 1, 1);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

		GL11.glEnd();
	}

	public static int rainbow(int delay) {
		double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
		rainbowState %= 360;
		return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
	}
}