package me.rich.module.render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ChinaHat extends Feature {

	public ChinaHat() {
		super("ChinaHat", 0, Category.RENDER);
		ArrayList<String> hat = new ArrayList<String>();
		hat.add("StrawCircle");
		hat.add("StrawHexagon");
		hat.add("StrawPolygon");
		hat.add("Circle");
		hat.add("Hexagon");
		hat.add("Polygon");
		Main.instance.settingsManager.rSetting(new Setting("ChinaHat Mode", this, "StrawCircle", hat));
		Main.instance.settingsManager.rSetting(new Setting("UnRenderFirstPerson", this, true));
	}

	@EventTarget
	public void asf(Event3D event) {
		ItemStack stack = mc.player.getEquipmentInSlot(4);
		final double height = stack.getItem() instanceof ItemArmor ? mc.player.isSneaking() ? -0.18 : 0.04 : mc.player.isSneaking() ? -0.30 : -0.08;
		String mode = Main.instance.settingsManager.getSettingByName("ChinaHat Mode").getValString();
		this.setModuleName("ChinaHat §7[" + mode + "]");
		if ((mc.gameSettings.thirdPersonView == 1 || mc.gameSettings.thirdPersonView == 2) && Main.settingsManager
				.getSettingByName(Main.moduleManager.getModule(ChinaHat.class), "UnRenderFirstPerson").getValBoolean()) {
			GL11.glPushMatrix();
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			GL11.glColor4f(Main.getClientColor().getRed() / 255.0f, Main.getClientColor().getGreen() / 255.0f,
					Main.getClientColor().getBlue() / 255.0f, 125.0f / 255.0f);
			GL11.glTranslatef(0f, (float) ((float) (mc.player.height + 0.36) + height), 0f); 
			GL11.glRotatef(90f, 1f, 0f, 0f);
			Cylinder c = new Cylinder();
			c.setDrawStyle(GLU.GLU_SMOOTH);
			c.setDrawStyle(GLU.GLU_LINE);
			
			if (mode.equalsIgnoreCase("StrawCircle")) {
				c.draw(0f, 0.55f, 0.3f, 30, 5);
			}

			if (mode.equalsIgnoreCase("StrawHexagon")) {
				c.draw(0f, 0.55f, 0.3f, 5, 10);
			}

			if (mode.equalsIgnoreCase("Circle")) {
				c.draw(0f, 0.55f, 0.3f, 150, 100);
			}

			if (mode.equalsIgnoreCase("Hexagon")) {
				c.draw(0f, 0.55f, 0.3f, 6, 100);
			}

			if (mode.equalsIgnoreCase("StrawPolygon")) {
				c.draw(0f, 0.55f, 0.3f, 9, 10);
			}

			if (mode.equalsIgnoreCase("Polygon")) {
				c.draw(0f, 0.55f, 0.3f, 9, 100);
			}

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		} else if(!Main.settingsManager
				.getSettingByName(Main.moduleManager.getModule(ChinaHat.class), "UnRenderFirstPerson").getValBoolean()){
			GL11.glPushMatrix();
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			GL11.glColor4f(Main.getClientColor().getRed() / 255.0f, Main.getClientColor().getGreen() / 255.0f,
					Main.getClientColor().getBlue() / 255.0f, 125.0f / 255.0f);
			GL11.glTranslatef(0f, (float) ((float) (mc.player.height + 0.36) + height), 0f); 
			GL11.glRotatef(90f, 1f, 0f, 0f);
			Cylinder c = new Cylinder();
			c.setDrawStyle(GLU.GLU_SMOOTH);
			c.setDrawStyle(GLU.GLU_LINE);
			if (mode.equalsIgnoreCase("StrawCircle")) {
				c.draw(0f, 0.55f, 0.3f, 30, 5);
			}

			if (mode.equalsIgnoreCase("StrawHexagon")) {
				c.draw(0f, 0.55f, 0.3f, 5, 10);
			}

			if (mode.equalsIgnoreCase("Circle")) {
				c.draw(0f, 0.55f, 0.3f, 150, 100);
			}

			if (mode.equalsIgnoreCase("Hexagon")) {
				c.draw(0f, 0.55f, 0.3f, 6, 100);
			}

			if (mode.equalsIgnoreCase("StrawPolygon")) {
				c.draw(0f, 0.55f, 0.3f, 9, 10);
			}

			if (mode.equalsIgnoreCase("Polygon")) {
				c.draw(0f, 0.55f, 0.3f, 9, 100);
			}

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void onEnable() {
		super.onEnable();
		NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
	}

	public void onDisable() {
		NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}
}