package Squad.Modules.Other;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Category;
import Squad.base.Module;

public class Timer
extends Module
{
public Timer()
{
  super("Timer", 0, 629145, Category.Other);
}

@EventTarget
public void onUpdate(EventUpdate e)
{
  mc.timer.timerSpeed = 3.0F;
}

public void onDisable()
{
  mc.timer.timerSpeed = 1.0F;
  EventManager.unregister(this);
}

public void onEnable()
{
  EventManager.register(this);
}
}
