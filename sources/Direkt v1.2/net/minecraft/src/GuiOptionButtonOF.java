package net.minecraft.src;

import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionButtonOF extends GuiOptionButton implements IOptionControl {
	private GameSettings.Options option = null;

	public GuiOptionButtonOF(int p_i31_1_, int p_i31_2_, int p_i31_3_, GameSettings.Options p_i31_4_, String p_i31_5_) {
		super(p_i31_1_, p_i31_2_, p_i31_3_, p_i31_4_, p_i31_5_);
		this.option = p_i31_4_;
	}

	@Override
	public GameSettings.Options getOption() {
		return this.option;
	}
}
