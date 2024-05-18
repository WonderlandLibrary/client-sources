package Squad.Modules.Movement;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Category;
import Squad.base.Module;

public class AutoWalk
extends Module
{
public static int mode = 1;

public AutoWalk()
{
  super("AutoWalk", 0, 136, Category.Movement);
}

@EventTarget
public void onUpdate(EventUpdate e)
{
  if ((getState()) && 
    (mode == 1)) {
    mc.gameSettings.keyBindForward.pressed = true;
  }
}

public void onDisable()
{
  mc.gameSettings.keyBindForward.pressed = false;
}
}
