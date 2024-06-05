package net.minecraft.client.gui;

import java.awt.Color;

import me.rich.Main;
import me.rich.font.Fonts;
import me.rich.helpers.render.ColorHelper;
import me.rich.helpers.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui {
	protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");

	/** Button width in pixels */
	protected int width;
	// Fade.
	float speed = 0;
	private static int fade = 20;

	/** Button height in pixels */
	protected int height;

	/** The x position of this control. */
	public int xPosition;

	/** The y position of this control. */
	public int yPosition;

	/** The string displayed on this control. */
	public String displayString;
	public int id;

	/** True if this control is enabled, false to disable. */
	public boolean enabled;

	/** Hides the button completely if false. */
	public boolean visible;
	protected boolean hovered;

	public GuiButton(int buttonId, int x, int y, String buttonText) {
		this(buttonId, x, y, 200, 20, buttonText);
	}

	public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		this.width = 200;
		this.height = 20;
		this.enabled = true;
		this.visible = true;
		this.id = buttonId;
		this.xPosition = x;
		this.yPosition = y;
		this.width = widthIn;
		this.height = heightIn;
		this.displayString = buttonText;
	}

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this
	 * button and 2 if it IS hovering over this button.
	 */
	protected int getHoverState(boolean mouseOver) {
		int i = 1;

		if (!this.enabled) {
			i = 0;
		} else if (mouseOver) {
			i = 2;
		}

		return i;
	}

	public void func_191745_a(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width
					&& mouseY < this.yPosition + this.height);
			int color = ColorHelper.astolfoColors((int) 0, 0);
			Color text = new Color(255, 255, 255, 255);
			boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width
					&& mouseY < this.yPosition + this.height; // Flag, tells if your mouse is hovering the button
			int var5 = this.getHoverState(this.hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(770, 771);
			if (flag) {
				RenderHelper.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height + 1f, 2, Main.getClientColor());
				RenderHelper.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, (int) 2F, new Color(26, 26, 26, 255));
				//drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1,
					//	this.yPosition + this.height - 1, new Color(10, 10, 5, 105).getRGB());
				Fonts.neverlose500_14.drawCenteredString(displayString, this.xPosition + this.width / 2 ,this.yPosition + (this.height - (int) 2) / 2, new Color(255, 255, 255).getRGB());
			} else {
				int height = 20;
				int posX = 2;
				int posY = 2;
				RenderHelper.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height + 1f, 2, Main.getClientColor());
				RenderHelper.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, (int) 2F, new Color(21, 21, 21, 255));
				//drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height,
					//	(new Color(10, 10, 5, 85)).getRGB());
				//drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1,
						//this.yPosition + this.height - 1, (new Color(250, 250, 250, 10)).getRGB());
				// RenderUtils.drawBorderedRect(this.xPosition, this.yPosition, this.xPosition +
				// this.width, this.yPosition + this.height, 5, 0x33101010, 0x33101010);
				Fonts.neverlose500_14.drawCenteredString(displayString, this.xPosition + this.width / 2 ,this.yPosition + (this.height - (int) 2) / 2, new Color(255, 255, 255).getRGB());
			}
			this.mouseDragged(mc, mouseX, mouseY);
		}
	}

	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
	}

	/**
	 * Fired when the mouse button is released. Equivalent of
	 * MouseListener.mouseReleased(MouseEvent e).
	 */
	public void mouseReleased(int mouseX, int mouseY) {
	}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of
	 * MouseListener.mousePressed(MouseEvent e).
	 */
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition
				&& mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
	}

	/**
	 * Whether the mouse cursor is currently over the button.
	 */
	public boolean isMouseOver() {
		return this.hovered;
	}

	public void drawButtonForegroundLayer(int mouseX, int mouseY) {
	}

	public void playPressSound(SoundHandler soundHandlerIn) {
		soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}

	public int getButtonWidth() {
		return this.width;
	}

	public static void drawRect(double left, double top, double right, double bottom, int color) {
		if (left < right) {
			double i = left;
			left = right;
			right = i;
		}
		if (top < bottom) {
			double j = top;
			top = bottom;
			bottom = j;
		}
		float f3 = (float) (color >> 24 & 0xFF) / 255.0f;
		float f = (float) (color >> 16 & 0xFF) / 255.0f;
		float f1 = (float) (color >> 8 & 0xFF) / 255.0f;
		float f2 = (float) (color & 0xFF) / 255.0f;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f, f1, f2, f3);
		bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
		bufferBuilder.pos(left, bottom, 0.0).endVertex();
		bufferBuilder.pos(right, bottom, 0.0).endVertex();
		bufferBuilder.pos(right, top, 0.0).endVertex();
		bufferBuilder.pos(left, top, 0.0).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public void setWidth(int width) {
		this.width = width;
	}
}