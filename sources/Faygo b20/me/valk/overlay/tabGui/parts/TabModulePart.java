package me.valk.overlay.tabGui.parts;

import org.lwjgl.input.Keyboard;

import me.valk.module.Module;
import me.valk.overlay.tabGui.TabPart;
import me.valk.utils.value.Value;

public class TabModulePart extends TabPart {

	private Module module;

	public TabModulePart(Module module, TabPanel parent) {
		super(module.getName(), parent);

		this.module = module;
	}

	@Override
	public void onKeyPress(int key) {
		if (key == Keyboard.KEY_RETURN)
			module.toggle();
		if (key == Keyboard.KEY_RIGHT) {
			TabPanel panel = new TabPanel(this.getParent().getTabGui());
			panel.setVisible(true);

			// for(ModMode mode : module.getModes()){
			// panel.addElement(new TabModePart(this.getModule(), mode, panel));
			// }

			for (Value value : module.getData().getValues()) {
				panel.addElement(new TabValuePart(
						Character.toString(value.getName().toString().toLowerCase().charAt(0)).toUpperCase()
								+ value.getName().toString().toLowerCase().substring(1),
						panel, value, this.getModule()));
			}

			if (panel.getElements().isEmpty())
				return;

			panel.setIndex(this.getParent().getIndex() + 1);

			this.getParent().getTabGui().addPanel(panel);
		}
	}

	public Module getModule() {
		return module;
	}

}
