package none.module.modules.player;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.utils.MoveUtils;
import none.utils.Utils;
import none.valuesystem.ModeValue;

public class NoWeb extends Module{
	
	private String[] modes = {"AAC"};
	private ModeValue nowebmode = new ModeValue("NoWeb-Mode", "AAC", modes);
	
	public NoWeb() {
		super("NoWeb", "NoWeb", Category.PLAYER, Keyboard.KEY_NONE);
	}
	
	@Override
	protected void onEnable() {
		super.onEnable();
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
	}
	
	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(this.getName() + ChatFormatting.WHITE + " " + nowebmode.getSelected());
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (nowebmode.getSelected().equalsIgnoreCase("AAC")) {
				//AAC
				if (mc.thePlayer.isInWeb) {
					if (mc.thePlayer.onGround) {
						mc.thePlayer.motionY = 0.2;
					}else {
						mc.thePlayer.motionY = 0;
					}
					MoveUtils.setMotion(0.4);
				}
			}
		}
	}
}
