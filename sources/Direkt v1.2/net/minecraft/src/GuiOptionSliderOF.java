package net.minecraft.src;

import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionSliderOF extends GuiOptionSlider implements IOptionControl {
	private GameSettings.Options option = null;

	public GuiOptionSliderOF(int p_i32_1_, int p_i32_2_, int p_i32_3_, GameSettings.Options p_i32_4_) {
		super(p_i32_1_, p_i32_2_, p_i32_3_, p_i32_4_);
		this.option = p_i32_4_;
	}

	@Override
	public GameSettings.Options getOption() {
		return this.option;
	}
}
