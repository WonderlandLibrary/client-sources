package de.tired.module.impl.list.visual;
import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.Render3DEvent2;
import de.tired.event.events.Render3DEventPRE;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
@ModuleAnnotation(name = "Breadcrumbs", category = ModuleCategory.RENDER, clickG = "Render a line from your movement")
public class Breadcrumbs extends Module {

	@EventTarget
	public void onRender(Render3DEvent2 e) {

	}

	public void render(Render3DEventPRE e) {

	}

	public final double interpolate(final double old, final double now, final float partialTicks) {
		return old + (now - old) * partialTicks;

	}

	@Override
	public void onState() {

	}
	@Override
	public void onUndo() {

	}
	

}
