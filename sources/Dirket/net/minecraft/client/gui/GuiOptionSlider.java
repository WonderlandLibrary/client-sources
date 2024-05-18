package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.math.MathHelper;
import us.dev.direkt.util.render.OGLRender;

public class GuiOptionSlider extends GuiButton {
	private float sliderValue;
	public boolean dragging;
	private final GameSettings.Options options;
	private final float minValue;
	private final float maxValue;

	public GuiOptionSlider(int buttonId, int x, int y, GameSettings.Options optionIn) {
		this(buttonId, x, y, optionIn, 0.0F, 1.0F);
	}

	public GuiOptionSlider(int buttonId, int x, int y, GameSettings.Options optionIn, float minValueIn, float maxValue) {
		super(buttonId, x, y, 150, 20, "");
		this.sliderValue = 1.0F;
		this.options = optionIn;
		this.minValue = minValueIn;
		this.maxValue = maxValue;
		Minecraft minecraft = Minecraft.getMinecraft();
		this.sliderValue = optionIn.normalizeValue(minecraft.gameSettings.getOptionFloatValue(optionIn));
		this.displayString = minecraft.gameSettings.getKeyBinding(optionIn);
	}

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over this button.
	 */
	@Override
	protected int getHoverState(boolean mouseOver) {
		return 0;
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
	 */
	@Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);
                this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
                float f = this.options.denormalizeValue(this.sliderValue);
                mc.gameSettings.setOptionFloatValue(this.options, f);
                this.sliderValue = this.options.normalizeValue(f);
                this.displayString = mc.gameSettings.getKeyBinding(this.options);
                OGLRender.drawBorderedRectReliant(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 2, this.yPosition, this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 6, this.yPosition + 20, 0.5f, 0x406FF358, -1);
            } else {
                OGLRender.drawGradientBorderedRectReliant(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 2, this.yPosition, this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 6, this.yPosition + 20, 0.5f, -1, 0x40000000, 0x50000000);
            }
        }
    }

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent e).
	 */
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if (super.mousePressed(mc, mouseX, mouseY)) {
			this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
			this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
			mc.gameSettings.setOptionFloatValue(this.options, this.options.denormalizeValue(this.sliderValue));
			this.displayString = mc.gameSettings.getKeyBinding(this.options);
			this.dragging = true;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
	 */
	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		this.dragging = false;
	}
}
