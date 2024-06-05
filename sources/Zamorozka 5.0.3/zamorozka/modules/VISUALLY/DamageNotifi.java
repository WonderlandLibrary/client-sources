package zamorozka.modules.VISUALLY;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.util.math.MathHelper;
import zamorozka.event.EventTarget;
import zamorozka.event.events.AttackEvent;
import zamorozka.event.events.EventEntityDamage;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.RenderEvent3D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.AngleUtil;
import zamorozka.ui.AnimationUtil;
import zamorozka.ui.DeltaUtil;
import zamorozka.ui.EntityUtil;
import zamorozka.ui.TimerHelper;
import zamorozka.ui.font.Fonts;

public class DamageNotifi extends Module {

	TimerHelper timer;

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("RemoveTicks", this, (int) 120, (int) 50, (int) 500, true));
	}

	public DamageNotifi() {
		super("DamageParticles", 0, Category.VISUALLY);
		this.damageTexts = new ArrayList<DamageText>();
	}

	List<DamageText> damageTexts;

	@EventTarget
	public void onRender(RenderEvent3D event) {
		for (final DamageText text : new ArrayList<DamageText>(damageTexts)) {
			final Entity entity = text.getEntity();
			text.updateAlpha();
			GL11.glPushMatrix();
			final RenderManager renderManager = mc.getRenderManager();
			final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - renderManager.renderPosX;
			final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - renderManager.renderPosY + entity.height / 2;
			final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - renderManager.renderPosZ;
			GL11.glTranslated(x, y, z);
			GL11.glRotated(-mc.getRenderManager().playerViewY, 0, 1, 0);
			GL11.glRotated(mc.getRenderManager().playerViewX, 1, 0, 0);
			GL11.glScaled(-.03, -.03, 1);
			GlStateManager.disableDepth();
			RenderHelper.disableStandardItemLighting();
			Fonts.default25.drawStringWithShadow(text.getText(), -mc.fontRendererObj.getStringWidth(text.getText()) / 2 + (float) (text.isNegative() ? -text.getrX() : text.getrX()), (float) (text.isNegative() ? -text.getrY() : text.getrY()),
					Zamorozka.getClientColor());
			GlStateManager.disableBlend();
			GL11.glPopMatrix();
			long ticks = (long) Zamorozka.settingsManager.getSettingByName("RemoveTicks").getValDouble();
			if (mc.player.ticksExisted % (int) ticks == 0 || entity == null || (((EntityPlayer) entity).getHealth() <= 0)) {
				damageTexts.remove(text);
			}
		}
	}

	private boolean contains(DamageText text) {
		for (final DamageText t : damageTexts) {
			if (t.getId() == text.getId())
				return true;
		}
		return false;
	}

	@EventTarget
	public void onDamage(AttackEvent event) {
		final Entity entity = event.getTargetEntity();
		if (entity == null || entity == mc.player || !(entity instanceof EntityPlayer))
			return;
		final RenderManager renderManager = mc.getRenderManager();
		if (entity instanceof EntityLivingBase) {
			final EntityLivingBase ent = ((EntityLivingBase) entity);
			final DamageText text = new DamageText("DMG!", entity);
			if (!contains(text) && entity != null || (((EntityPlayer) entity).getHealth() > 0) && mc.player != entity) {
				GL11.glPushMatrix();
				damageTexts.add(text);
				GlStateManager.popMatrix();
			}
		}
	}

	class DamageText {

		private final String text;

		private int alpha, id;

		private final Entity entity;

		private double rX, startX, rMaxX, rY, startY, rMaxY;

		private boolean negative;

		public DamageText(String text, Entity entity) {
			this.text = text;
			this.alpha = 255;
			this.entity = entity;
			this.id = (int) (1000 * Math.random());
			this.negative = ThreadLocalRandom.current().nextBoolean();
			this.rMaxX = AngleUtil.randomFloat(0f, 30f);
			this.rMaxY = AngleUtil.randomFloat(0f, 50f);
			this.rX = this.startX = ThreadLocalRandom.current().nextDouble(0, rMaxX);
			this.rY = this.startY = ThreadLocalRandom.current().nextDouble(0, rMaxY);
		}

		public String getText() {
			return text;
		}

		public Entity getEntity() {
			return entity;
		}

		public int getAlpha() {
			return alpha;
		}

		public int getId() {
			return id;
		}

		public double getrX() {
			return rX;
		}

		public double getrY() {
			return rY;
		}

		public boolean isNegative() {
			return negative;
		}

		public void updateAlpha() {
			alpha -= DeltaUtil.deltaTime * .25;
			alpha = MathHelper.clamp(alpha, 0, 255);
			rX = AnimationUtil.calculateCompensation((float) rX, (float) startX, (long) rMaxX, (float) 0.1);
			rY = AnimationUtil.calculateCompensation((float) rY, (float) startY, (long) rMaxY, 0.1);
		}
	}

	public static int novoline(int delay) {
		double novolineState = Math.ceil((System.currentTimeMillis() + delay) / 50.0);
		novolineState %= 360;
		return Color.getHSBColor((float) (novolineState / 180.0f), 0.3f, 1.0f).getRGB();
	}

}