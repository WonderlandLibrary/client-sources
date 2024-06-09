package Squad.Modules.Player;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.ItemSword;

public class TntBlock
extends Module
{
	 public static boolean isntblocking = true;

public TntBlock()
{
  super("TntBlocker", 0, 4919, Category.Player);
}

public void onEnable()
{
  EventManager.register(this);
  super.onEnable();
}

public void onDisable()
{
  EventManager.unregister(this);
  super.onDisable();
}

@EventTarget
public void onUpdate(EventUpdate e)
{
  if (checkForExplodingTNTinRange())
  {
    if ((mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword))
    {
      isntblocking = false;
      mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);
    }
  }
  else if (!checkForExplodingTNTinRange()) {
    isntblocking = true;
  }
}

public boolean checkForExplodingTNTinRange()
{
  for (Object e : mc.theWorld.loadedEntityList) {
    if ((e instanceof EntityTNTPrimed))
    {
      EntityTNTPrimed tnt = (EntityTNTPrimed)e;
      if (mc.thePlayer.getDistanceToEntity(tnt) <= 6.0D) {
        return true;
      }
    }
  }
  return false;
}
}
