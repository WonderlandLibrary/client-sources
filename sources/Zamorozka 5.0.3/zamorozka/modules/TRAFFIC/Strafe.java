package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventMotionUp;
import zamorozka.event.events.EventMove;
import zamorozka.event.events.EventMoveFlying;
import zamorozka.event.events.EventPostMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.UpdateEvent;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.MoveUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.MovementUtils;
import zamorozka.ui.PlayerUtil;
import zamorozka.ui.RotationsNew;
import zamorozka.ui.TimerHelper;
import zamorozka.ui.Wrapper;

public class Strafe extends Module {
	
	public Strafe() {
		super("Strafe", Keyboard.KEY_NONE, Category.TRAFFIC);
	}

	@EventTarget
	public void onUpdate(EventPostMotionUpdates event) {
		MovementUtils.strafe();
	}
}