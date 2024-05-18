package me.swezedcode.client.gui.cgui.parts.keybinder;

import java.awt.Point;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.swezedcode.client.gui.cgui.component.Component;
import me.swezedcode.client.module.Module;
import net.minecraft.client.Minecraft;

public class KeybindPart extends Component {
	private Module mod;
	private boolean binding;

	public KeybindPart( Module mod,  Component parent) {
		this.mod = mod;
		this.parent = parent;
	}

	@Override
	public void draw(final int mouseX, final int mouseY, final float partialTicks) {

		final Point mouse = new Point(mouseX, mouseY);
		final boolean hover = this.MouseIsInside().contains(mouse);
		final String s = this.binding ? "Press a key ..." : Keyboard.getKeyName(this.mod.getKeycode());
		GL11.glPushMatrix();
		GL11.glScalef(2.0f, 2.0f, 2.0f);
		final int posX = this.x / 2 + this.width / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(s) + 6;
		if(!this.binding) {
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("bind: ", this.x / 2 + 15 + 1, this.y / 2 + 3.5f,
				-1);
		}
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(s, posX , this.y / 2 + 3.5f, -1);
		GL11.glPopMatrix();
	}

	@Override
	public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
		final Point mouse = new Point(mouseX, mouseY);
		if (this.MouseIsInside().contains(mouse) && mouseButton == 0) {
			this.binding = true;
		}
	}

	@Override
	public void mouseReleased(final int mouseX, final int mouseY, final int state) {
	}

	@Override
	public void keyTyped(final char typedChar, final int keyCode) {
		if (this.binding) {
			if (keyCode == Keyboard.KEY_DELETE) {
				mod.setKeycode(0);
			} else {
				mod.setKeycode(keyCode);

			}
			this.binding = false;
		}
	}

	@Override
	public void onUpdate(final int mouseX, final int mouseY, final float partialTicks) {
	}
}
