package me.swezedcode.client.gui.clickGui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.ClientOverlay;
import me.swezedcode.client.gui.other.ColorPickerGui;
import me.swezedcode.client.module.modules.Options.Rainbow;
import me.swezedcode.client.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

public class SpecialButton extends Component {

	GuiMainMenu gmu;

	public ArrayList<Component> elements = new ArrayList<Component>();

	public String title;
	public String desc;
	public boolean held;
	public boolean enabled;
	public long hoverTime = 0;
	public long lastTime = 0;
	public static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
	float p_73968_1_;

	public static int size = 100;

	public boolean open;

	public SpecialButton(String title, String desc, Component parent) {
		this.title = title;
		this.desc = desc;
		this.parent = parent;
		this.width = parent.width - 4;
		this.height = 12;
		this.renderWidth = width;
		this.renderHeight = height;
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks, int parX, int parY, GuiScreen screen) {
		this.onUpdate();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.parent.x, this.parent.y, 0);
		this.absx = parX + this.x;
		this.absy = parY + this.y;

		int rHeight = this.height;
		//screen.drawRect(x + 118, y - 1, x + width / 8 + 102, y + height, 0xFF457A8C);
		//screen.drawRect(x - 2, y - 1, x + width / 8 - 14, y + height, 0xFF31484D);
		screen.drawRect(x - 1, y, x + width + 1, y + height, 0x99000000);
		
		if (enabled) {
			if(Rainbow.enabled) {
				ClientOverlay.fontRenderer.drawStringWithShadow(title, x + width / 2 - 55, y + 3, ColorUtils.getRainbow(1, 1).getRGB());
			}else{
				ClientOverlay.fontRenderer.drawStringWithShadow(title, x + width / 2 - 55, y + 3, ColorPickerGui.color);
			}
		} else if (!enabled) {
			ClientOverlay.fontRenderer.drawStringWithShadow(title, x + width / 2 - 55, y + 3, 0xFF919B9E);
		}

		int compY = this.height;
		int compX = 0;
		int spacer = 2;
		for (Component c : this.elements) {
			c.y = compY + spacer;
			c.x = spacer;
			c.width = this.width - spacer * 2;
			compY += c.renderHeight + spacer - 1;
		}
		if (this.open) {
			this.renderHeight = compY + spacer + 10;
		}
		if (this.elements.size() > 0) {
			if (this.open) {
				for (Component c : this.elements) {
					c.draw(mouseX, mouseY, partialTicks, absx, absy, screen);
					c.isVisible = true;
				}
				this.renderHeight -= 11;
			} else {
				this.renderHeight = this.height;
				for (Component c : this.elements) {
					c.isVisible = false;
				}
			}
		} else {
			this.renderHeight = this.height;
		}
		GL11.glPopMatrix();
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!this.isVisible) {
			return;
		}

		if (mouseX >= this.absx && mouseX <= this.absx + this.width) {
			if (mouseY >= this.absy && mouseY <= this.absy + this.height) {

			}
		}
		if (mouseX >= this.absx && mouseX <= this.absx + this.width) {
			if (mouseY >= this.absy && mouseY <= this.absy + this.height) {
				if (mouseButton == 0) {
					this.held = true;
				} else {
					if (this.elements.size() > 0) {
						this.open = !this.open;
					}
				}
			}

		}
		if (open) {
			for (Component c : elements) {
				c.mouseClicked(mouseX, mouseY, mouseButton);
			}
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		if (mouseX >= this.absx && mouseX <= this.absx + this.width) {
			if (mouseY >= this.absy && mouseY <= this.absy + this.height) {
				if (this.held) {
					this.held = false;
					this.onPressed();
				}
			}
		}
		if (open) {
			for (Component c : elements) {
				c.mouseReleased(mouseX, mouseY, mouseButton);
			}
		}
	}

	public void onPressed() {
	}

	public void onUpdate() {
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		for (Component c : elements) {
			c.keyTyped(typedChar, keyCode);
		}

	}

	@Override
	public void keyTypedNum(int typedChar, int keyCode) throws IOException {
		// TODO Auto-generated method stub

	}
}
