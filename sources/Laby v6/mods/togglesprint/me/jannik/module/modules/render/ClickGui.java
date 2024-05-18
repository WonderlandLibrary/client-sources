package mods.togglesprint.me.jannik.module.modules.render;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.imfr0zen.guiapi.example.ExampleGuiScreen;
import mods.togglesprint.me.jannik.Jannik;
import mods.togglesprint.me.jannik.events.EventUpdate;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class ClickGui extends Module {

	public ClickGui() {
		super("ClickGui", Category.RENDER, 54);
	}
		
	public void onEnabled() {
        if (mc.theWorld != null) {
        	mc.displayGuiScreen(new ExampleGuiScreen());
        }                  
    }
	
	@EventTarget
	private void onUpdate(EventUpdate event) {
		toggleModule();
	}
}
