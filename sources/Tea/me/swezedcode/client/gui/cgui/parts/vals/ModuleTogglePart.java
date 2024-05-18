package me.swezedcode.client.gui.cgui.parts.vals;

import java.awt.Color;
import java.awt.Point;

import org.lwjgl.opengl.GL11;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.cgui.component.Component;
import me.swezedcode.client.gui.cgui.parts.Parts;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ModuleTogglePart extends Parts {
	public Module module;
	public boolean enabled;
	int extendedHeight;

	public ModuleTogglePart(final String title, final Component parent) {
		this.enabled = false;
		this.title = title;
		this.parent = parent;
		this.width = 170;
		this.height = 28;
	}

	@Override
	public void draw(final int mouseX, final int mouseY, final float partialTicks) {
		final Point mouse = new Point(mouseX, mouseY);
		final boolean hover = this.MouseIsInside().contains(mouse);
		Gui.drawRect(this.x, this.y - 2, this.x + this.width + 20, this.y + this.height - 2,
				new Color(0, 0, 0, 150).getRGB());
		GL11.glPushMatrix();
		GL11.glScalef(2.0f, 2.0f, 2.0f);

		if (this.open) {
			Tea.fontRenderer.drawString(this.title, this.x / 2 + 3, this.y / 2 + 4,
					this.isEnabled() ? new Color(255, 128, 0, 255).getRGB() : -1);
			Tea.fontRenderer.drawString("...",
					this.x / 2 + 81, this.y / 2 + 3, this.isEnabled() ? -1 : -1);
		}else{
			Tea.fontRenderer.drawString(this.title, this.x / 2 + 3, this.y / 2 + 4,
					this.isEnabled() ? new Color(255, 128, 0, 255).getRGB() : -1);
		}
		
		GL11.glPopMatrix();

		if (this.open) {
			Gui.drawRect(this.x + this.width + 21, this.y + this.height - 32,
					this.x + this.width + this.width + 40, this.y + this.height + 29 * this.components.size() - 28 + 2, new Color(0, 0, 0, 150).getRGB());
		

			for (final Component comp : this.getComponents()) {
				comp.onUpdate(mouseX, mouseY, partialTicks);
				comp.draw(mouseX, mouseY, partialTicks);
			}
		}

	}

	@Override
	public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
		Point mouse = new Point(mouseX, mouseY);
		if (this.MouseIsInside().contains(mouse)) {
			if (mouseButton == 0) {
				
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

	@Override
	public void onUpdate(int mouseX, int mouseY, float partialTicks) {
		final int compX = this.x + this.width + 2;
		int compY = this.y;
		for (final Component comp : this.getComponents()) {
			comp.setHeight(28);
			comp.setWidth(this.width);
			comp.setX(compX);
			comp.setY(compY);
			compY += comp.getHeight();
		}
		this.extendedHeight = compY;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public void onToggle() {
		module.toggle();
	}
}
