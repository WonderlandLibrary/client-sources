package none.module.modules.render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.entity.player.EntityPlayer;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventRenderEntity;
import none.module.Category;
import none.module.Module;

public class Chams extends Module{

	public Chams() {
		super("Chams", "Chams", Category.RENDER, Keyboard.KEY_NONE);
	}

	@Override
	@RegisterEvent(events = {EventRenderEntity.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventRenderEntity) {
            EventRenderEntity er = (EventRenderEntity) event;
            if (er.isPre() && er.getEntity() instanceof EntityPlayer) {
                GL11.glEnable(32823);
                GL11.glPolygonOffset(1.0f, -1100000.0f);
            } else if (er.isPost() && er.getEntity() instanceof EntityPlayer) {
                GL11.glDisable(32823);
                GL11.glPolygonOffset(1.0f, 1100000.0f);
            }
        }
	}

}
