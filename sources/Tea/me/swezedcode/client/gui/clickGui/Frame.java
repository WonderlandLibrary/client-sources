package me.swezedcode.client.gui.clickGui;

import java.awt.font.FontRenderContext;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.ClientOverlay;
import me.swezedcode.client.gui.other.ColorPickerGui;
import me.swezedcode.client.module.modules.Options.Rainbow;
import me.swezedcode.client.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderGlobal.ContainerLocalRenderInformation;

public class Frame extends Component {

	GuiMainMenu gmu;

	public String title;
	private boolean isOpen;
	private boolean isDrag;
	private int xOffset, yOffset;
	public ArrayList<Component> Items = new ArrayList<Component>();
	public SoundHandler soundHandlerIn;
	public static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

	public int max, min;
	private int size = 0;

	public Frame(String title, int size) {
		this.title = title;
		this.size = size;
		this.x = 0;
		this.y = 0;
		this.width = 120;
		this.height = 15;
	}

	public void addItem(Component item) {
		item.parent = this;
		Items.add(item);
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks, int parX, int parY, GuiScreen screen) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
		if (isDrag) {
			x = mouseX - xOffset;
			y = mouseY - yOffset;
		}
		
		this.absx = parX + this.x;
		this.absy = parY + this.y;
		if (isOpen) {
			int rHeight = this.height;
			for (Component item : Items) {
				rHeight += item.renderHeight + 2;
			}
			rHeight -= Items.size() + 1;
		}
		
		screen.drawRect(x, y, x + width, y + height, 0xFF404040);
		
		if (!Rainbow.enabled) {
			ClientOverlay.fontRenderer.drawStringWithShadow(" " + title + " §f(" + size + ")", x, y + 3, ColorPickerGui.color);
		} else {
			ClientOverlay.fontRenderer.drawStringWithShadow(" " + title + " §f(" + size + ")", x, y + 3, ColorUtils.getRainbow(1, 1).getRGB());
		}
		
		if (isOpen) {
			for (Component item : Items) {
				item.draw(mouseX, mouseY, partialTicks, this.absx, this.absy, screen);
				item.isVisible = true;
			}
			if (!Rainbow.enabled) {
				Gui.drawRect(x + 120, y + 13, x + width / 8 - 15, y + height - 1, ColorPickerGui.color);
			} else {
				Gui.drawRect(x + 120, y + 13, x + width / 8 - 15, y + height - 1, ColorUtils.getRainbow(1, 1).getRGB());
			}
		} else {
			for (Component item : Items) {
				item.isVisible = false;
			}
		}
		resizeComponenets();
	}

	private void resizeComponenets() {
		int compY = height;
		for (Component comp : Items) {
			comp.x = 0;
			comp.y = compY;
			comp.width = this.width;
			compY += comp.renderHeight + 1;
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseX >= x && mouseX <= x + width) {
			if (mouseY >= y && mouseY <= y + height) {
				if (mouseButton == 0) {
					isDrag = true;
					xOffset = mouseX - x;
					yOffset = mouseY - y;
				} else {
					isOpen = !isOpen;
				}
			}
		}
		if (isOpen) {
			for (Component child : Items) {
				child.mouseClicked(mouseX, mouseY, mouseButton);
			}
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		isDrag = false;
		for (Component child : Items) {
			child.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		for (Component child : Items) {
			child.keyTyped(typedChar, keyCode);
		}

	}

	@Override
	public void keyTypedNum(int typedChar, int keyCode) throws IOException {
		// TODO Auto-generated method stub

	}

	public void setExpanded(boolean open) {
		isOpen = open;

	}

	public boolean isExpanded() {
		return isOpen;
	}

	public String getTitle() {
		return title;
	}

}
