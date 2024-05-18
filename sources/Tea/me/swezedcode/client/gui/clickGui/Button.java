package me.swezedcode.client.gui.clickGui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.ClientOverlay;
import me.swezedcode.client.gui.other.ColorPickerGui;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.ValueManager;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.Rainbow;
import me.swezedcode.client.utils.render.ColorUtils;
import me.swezedcode.client.utils.render.SpecialCircle;
import me.swezedcode.client.utils.values.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

public class Button extends Component {

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

	public Button(String title, String desc, Component parent) {
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
		
		screen.drawRect(x, y - 1, x + width, y + height, 0x99000000);
		
		if (mouseX >= this.absx && mouseX <= this.absx + this.width) {
			if (mouseY >= this.absy && mouseY <= this.absy + this.height) {
				screen.drawRect(x, y - 1, x + width, y + height, 0x60000000);
			}
		}
		
		if (enabled) {
			if(Rainbow.enabled) {
				ClientOverlay.fontRenderer.drawStringWithShadow(title, x + width / 2 - 55, y + 2, ColorUtils.getRainbow(1, 1).getRGB());
			}else{
				ClientOverlay.fontRenderer.drawStringWithShadow(title, x + width / 2 - 55, y + 2, ColorPickerGui.color);
			}
		} else if (!enabled) {
			ClientOverlay.fontRenderer.drawStringWithShadow(title, x + width / 2 - 57, y + 2, 0xFF919B9E);
		}

		

		int compY = this.height;
		int compX = 0;
		int spacer = 2;
		for (Component c : this.elements) {
			c.y = compY + spacer - 2;
			c.x = spacer;
			c.width = this.width - spacer * 2;
			compY += c.renderHeight + spacer - 2;
		}
		if (this.open) {
			this.renderHeight = compY + spacer + 10;
			//GL11.glRotated(5, 0, Integer.MAX_VALUE, 0);
		}
		if (this.elements.size() > 0) {
			if (this.open) {
				for (Component c : this.elements) {
					// screen.drawRect(x, y + 12, x + width, y + height,
					// 0xFF000000);
				}
				for (Component c : this.elements) {
					c.draw(mouseX, mouseY, partialTicks, absx, absy, screen);
					c.isVisible = true;
				}
				if (!Rainbow.enabled) {
					Gui.drawRect(x + 118, y + 2, x + width / 8 + 95, y + height - 2, ColorPickerGui.color);
				} else {
					Gui.drawRect(x + 118, y + 2, x + width / 8 + 95, y + height - 2, ColorUtils.getRainbow(1, 1).getRGB());
				}
				this.renderHeight -= 13;
			} else {
				this.renderHeight = this.height;
				for (Component c : this.elements) {
					c.isVisible = false;
				}
				//Gui.drawRect(x + 118, y - 1, x + width / 8 + 105, y + height, 0xFFFFFFFF);
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
						if(open) {
							Minecraft.thePlayer.playSound("tile.piston.in", 0.5F, 2.0F);
						}else{
							Minecraft.thePlayer.playSound("tile.piston.out", 0.5F, 2.0F);
						}
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
