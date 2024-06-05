package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.Render2DEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;

@ModuleAnnotation(name = "Notifications", category = ModuleCategory.RENDER)
public class Notifications extends Module {



	@EventTarget
	public void onRender(Render2DEvent e) {
	}

	@Override
	public void onState() {

	}

	@Override
	public void onUndo() {

	}
}
