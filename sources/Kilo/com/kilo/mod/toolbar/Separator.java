package com.kilo.mod.toolbar;

import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.util.Util;

public class Separator extends Component {

	public Separator(int id, Texture texture, float x, float y, float width, float height) {
		super(id, texture, x, y, width, height);
	}

	@Override
	public void update(int mouseX, int mouseY) {}

	@Override
	public void mouseClick(int mouseX, int mouseY, int button) {}

	@Override
	public void mouseRelease(int mouseX, int mouseY, int button) {}

	@Override
	public void render(float transparency) {
		GlStateManager.color(0, 0, 0, 0);
		GL11.glColor4f(0, 0, 0, 0);
		
		float w = 1;
		float h = 32;

		Color c = new Color(Util.reAlpha(Colors.WHITE.c, 0.4f*transparency));
		
		GL11.glColor4f(c.r, c.g, c.b, c.a);
		Draw.rectTexture(x+32-(w/2), y+32-(h/2), w, h, texture);
	}
}
