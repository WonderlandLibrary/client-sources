package us.dev.direkt.module.internal.render;

import org.lwjgl.opengl.Display;

import us.dev.api.property.Property;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.direkt.util.client.MovementUtils;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

@ModData(label = "No Render", aliases = {"norender", "render"}, category = ModCategory.RENDER)
public class NoRender extends ToggleableModule {

	int lastFramerate;
	
    @Exposed(description = "Should only distant tiles be made not to render")
    private Property<Boolean> smart = new Property<>("Smart", true);

    @Exposed(description = "Should visibility graphing be used")
    private Property<Boolean> visGraphing = new Property<>("Vis Graph", true);

	@Override
    public void onEnable() {
    	Wrapper.getMinecraft().renderGlobal.loadRenderers();
    }
    
    @Override
    public void onDisable() {
    	Wrapper.getMinecraft().renderGlobal.loadRenderers();
    }
    
    public boolean shouldDoSmartRendering() {
        return smart.getValue();
    }

    public boolean shouldDoSmartVisGraphing() {
		return visGraphing.getValue();
    }
}
