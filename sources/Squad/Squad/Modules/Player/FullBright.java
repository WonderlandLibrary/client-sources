package Squad.Modules.Player;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Category;
import Squad.base.Module;

public class FullBright
extends Module
{
public FullBright()
{
  super("FullBright", 0, 2184, Category.Movement);
}

@EventTarget
public void onUpdate(EventUpdate e)
{
  mc.gameSettings.gammaSetting = 100.0F;
}

public void onDisable()
{
  mc.gameSettings.gammaSetting = 0.0F;
}
}
