package me.swezedcode.client.gui.other;

import me.swezedcode.client.Tea;
import me.swezedcode.client.utils.render.RenderUtils;
import me.swezedcode.client.utils.render.SpecialCircle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class BorderButton extends GuiButton {
	private int x;
	private int y;
	private int x1;
	private int y1;
	private String text;
	int alphaInc = 100;
	int alpha = 0;
	int size = 0;
	int size2 = 0;

	public BorderButton(int par1, int par2, int par3, int par4, int par5, String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
		this.x = par2;
		this.y = par3;
		this.x1 = par4;
		this.y1 = par5;
		this.text = par6Str;
	}

	public BorderButton(int i, int j, int k, String stringParams) {
		this(i, j, k, 200, 20, stringParams);
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		boolean isOverButton = (mouseX >= this.x) && (mouseX <= this.x + this.x1) && (mouseY >= this.y)
				&& (mouseY <= this.y + this.y1);
		if ((isOverButton) && (this.alphaInc <= 150)) {
			this.alphaInc += 5;
			this.alpha = (this.alphaInc << 24);
		} else if ((!isOverButton) && (this.alphaInc >= 100)) {
			this.alphaInc -= 6;
			this.alpha = (this.alphaInc << 24);
		}
		if (this.alphaInc > 150) {
			this.alphaInc = 150;
		} else if (this.alphaInc < 100) {
			this.alphaInc = 100;
		}
		if ((isOverButton) && (this.size <= 1)) {
			this.size += 1;
		} else if ((!isOverButton) && (this.size >= 0)) {
			this.size -= 1;
		}
		if ((isOverButton) && (this.size2 <= 5)) {
			this.size2 += 1;
		} else if ((!isOverButton) && (this.size2 >= 0)) {
			this.size2 -= 1;
		}
		RenderUtils.drawBorderedRect(this.x - this.size, this.y, this.x + this.x1 + this.size,
				this.y + this.y1, 2, 0x00000000, 0x60000000);
		Tea.fontRenderer.drawCenteredString(Tea.fontRenderer, this.text, this.x + this.x1 / 2, this.y + this.y1 / 2 - 4, -1);
	}
}
