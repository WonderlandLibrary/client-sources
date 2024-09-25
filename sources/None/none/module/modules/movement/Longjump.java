package none.module.modules.movement;

import net.minecraft.world.HWID;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventJump;
import none.event.events.EventMove;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.utils.MoveUtils;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class Longjump extends Module{

	public Longjump() {
		super("Longjump", "Longjump", Category.MOVEMENT);
	}
	
	public ModeValue modes = new ModeValue("Mode", "NCP", new String[] {"NCP", "Seksin"});
	public NumberValue<Float> ncpboost = new NumberValue<>("NCPBoost", 4.25F, 1F, 10F);
	public BooleanValue autojump = new BooleanValue("Auto-Jump", true);
	public boolean jumped = false, boosted = false;
	
	@Override
	protected void onEnable() {
		String mode = modes.getSelected();
		if (mode.equalsIgnoreCase("Seksin") && !HWID.isHWID()) {
			evc("Premium Only");
			Client.instance.notification.show(Client.notification("Premium", "You are not Premium", 3));
			setState(false);
			return;
		}
		super.onEnable();
	}
	
	@Override
	@RegisterEvent(events = {EventTick.class, EventJump.class, EventMove.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		String mode = modes.getSelected();
		
		if (mode.equalsIgnoreCase("Seksin") && !HWID.isHWID()) {
			setState(false);
			return;
		}
		
		if (event instanceof EventTick) {
			if (jumped && mc.thePlayer.onGround) {
				jumped = false;
				if (mode.equalsIgnoreCase("NCP")) {
                    mc.thePlayer.motionX = 0;
                    mc.thePlayer.motionZ = 0;
                }
				return;
			}
			if (jumped && !mc.thePlayer.onGround) {
				if (mode.equalsIgnoreCase("NCP")) {
					MoveUtils.strafe(boosted ? ncpboost.getObject() : 1F);
					boosted = false;
				}else if (mode.equalsIgnoreCase("Seksin")) {
					mc.thePlayer.motionY += 0.042D;
//                    MoveUtils.strafe(0.42F);
                    MoveUtils.strafe(0.42F);
				}
			}
			
			if (autojump.getObject() && mc.thePlayer.onGround && MoveUtils.isMoveKeyPressed()) {
				boosted = true;
				jumped = true;
				mc.thePlayer.jump();
			}
		}
		
		if (event instanceof EventJump) {
			EventJump ej = (EventJump) event;
			if (ej.isPre()) {
				boosted = true;
				jumped = true;
			}
		}
		
		if (event instanceof EventMove) {
			EventMove em = (EventMove) event;
			if (!boosted && !jumped && mc.thePlayer.onGround) {
				em.setX(0);
				em.setZ(0);
			}else if (boosted && jumped && !mc.thePlayer.onGround && !MoveUtils.isMoveKeyPressed()) {
				em.setX(0);
				em.setZ(0);
			}
		}
	}

}
