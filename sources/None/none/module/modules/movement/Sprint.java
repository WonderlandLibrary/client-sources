package none.module.modules.movement;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.module.modules.combat.BowAimbot;
import none.module.modules.combat.Killaura;
import none.module.modules.world.Scaffold;
import none.utils.BlockUtil;
import none.utils.Block.BlockUtils;
import none.valuesystem.BooleanValue;

public class Sprint extends Module{
	
	private BooleanValue mulitsprint = new BooleanValue("MulitSprint", false);

	public Sprint() {
		super("Sprint", "Sprint", Category.MOVEMENT, Keyboard.KEY_P);
	}
	
	@Override
	protected void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();
	}
	
	@Override
	protected void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (e.isPre() && mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
				if (mulitsprint.getObject()) {
					//Mulit
					setDisplayName(this.getName() + ChatFormatting.WHITE + " Multi");
					if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
						mc.thePlayer.setSprinting(true);
						if (BlockUtils.isOnLiquid(0.01)) {
							e.setSprinting(false);
						}else {
							e.setSprinting(true);
						}
					}
				}else if(!mulitsprint.getObject()) {
					setDisplayName(this.getName());
					if (mc.gameSettings.keyBindForward.isKeyDown()) {
						mc.thePlayer.setSprinting(true);
						if (BlockUtils.isOnLiquid(0.01)) {
							e.setSprinting(false);
						}else {
							e.setSprinting(true);
						}
					}
				}
			}
		}
	}
}
