// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.render;

import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option;
import net.minecraft.client.triton.ui.click.ClickGui;
import net.minecraft.client.triton.utils.ClientUtils;

@Mod(displayName = "Click Gui", keybind = 54, shown = false)
public class Gui extends Module {
	@Option.Op(name = "Dark Theme")
	private boolean darkTheme;

	public Gui() {
		this.setProperties("Gui", "Gui", Module.Category.Render, 0, "", false);
	}

	@Override
	public void enable() {
		ClientUtils.mc().displayGuiScreen(ClickGui.getInstance());
	}

	public boolean isDarkTheme() {
		return this.darkTheme;
	}
}
