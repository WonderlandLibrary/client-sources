package me.swezedcode.client.gui.cgui.component;

import java.awt.Color;
import java.awt.Point;

import org.lwjgl.opengl.GL11;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.cgui.parts.Parts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Frames extends Parts {
	private int dragX;
	private int dragY;
	private boolean dragging;

	public Frames(final String title) {
		this.title = title;
	}

	@Override
	public void draw(final int mouseX, final int mouseY, final float partialTicks) {
		open = true;
		Gui.drawRect(this.x - 2, this.y - 2, this.x + this.width + 20, this.y + 25, 0xFF345345);
		//boolean blendEnabled = GL11.glIsEnabled(3042);
		//GL11.glDisable(3042);
		GL11.glPushMatrix();
		GL11.glScalef(2.0f, 2.0f, 2.0f);
		Tea.mainMenuFont.drawString(this.title, this.x / 2 + 1, this.y / 2 - 1, -1);

		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.open ? "" : "",
				this.x / 2 + this.width / 2 + 2, this.y / 2 + 8 - 6 + (this.open ? 1 : 0), -1);
		GL11.glPopMatrix();
		//if (blendEnabled) {
		//	GL11.glEnable(3042);
		//}
		if (this.open) {
			Gui.drawRect(this.x - 2, this.y + this.height, this.x + this.width + 20, this.y + this.height, -1);
			for (final Component comp : this.components) {
				comp.onUpdate(mouseX, mouseY, partialTicks);
				comp.draw(mouseX, mouseY, partialTicks);
			}
		}
	}

	@Override
	public void onUpdate(final int mouseX, final int mouseY, final float partialTicks) {
		if (this.dragging) {
			this.x = mouseX - this.dragX;
			this.y = mouseY - this.dragY;
			final double increment = 5.0;
			this.x = (int) (Math.round(this.x * (1.0 / increment)) / (1.0 / increment));
			this.y = (int) (Math.round(this.y * (1.0 / increment)) / (1.0 / increment));
		}
		this.width = 160;
		this.height = 27;
		final int compX = this.x;
		int compY = this.y + this.height;
		for (Component comp : this.getComponents()) {
			comp.setX(compX);
			comp.setY(compY);
			comp.setWidth(this.width);
			compY += comp.getHeight();
		}
		if (this.open) {
			for (Component comp : this.getComponents()) {
				comp.onUpdate(mouseX, mouseY, partialTicks);
			}
		}
	}

	@Override
	public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
		final Point mouse = new Point(mouseX, mouseY);
		if (this.MouseIsInside().contains(mouse)) {
			if (mouseButton == 0) {
				this.dragX = mouseX - this.x;
				this.dragY = mouseY - this.y;
				this.dragging = true;
			} else {
				this.open = (!this.open);
			}
		}
		if (this.open) {
			for (final Component comp : this.getComponents()) {
				comp.mouseClicked(mouseX, mouseY, mouseButton);
			}
		}
	}

	@Override
	public void mouseReleased(final int mouseX, final int mouseY, final int state) {
		this.dragging = false;
		if (this.open) {
			for (final Component comp : this.getComponents()) {
				comp.mouseReleased(mouseX, mouseY, state);
			}
		}
	}

	@Override
	public void keyTyped(final char typedChar, final int keyCode) {
		if (this.open) {
			for (final Component comp : this.getComponents()) {
				comp.keyTyped(typedChar, keyCode);
			}
		}
	}

	public boolean didCollide(final int mouseX, final int mouseY) {
		return false;
	}
}
