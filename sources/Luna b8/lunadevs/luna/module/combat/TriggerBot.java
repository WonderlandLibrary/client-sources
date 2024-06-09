package lunadevs.luna.module.combat;

import java.util.List;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class TriggerBot extends Module{

	int delay;
	int delay2;
	
	public TriggerBot() {
		super("TriggerBot", 0, Category.COMBAT, false);
	}
	@Override
	public void onUpdate() {
		if (!this.isEnabled) {
		      return;
		    }
		    List list = this.mc.theWorld.playerEntities;
		    this.delay += 1;
		    this.delay2 += 1;
		    for (int k = 0; k < list.size(); k++) {
		      if (((EntityPlayer)list.get(k)).getName() != this.mc.thePlayer.getName())
		      {
		        EntityPlayer entityplayer = (EntityPlayer)list.get(1);
		        if (this.mc.thePlayer.getDistanceToEntity(entityplayer) > this.mc.thePlayer.getDistanceToEntity((Entity)list.get(k))) {
		          entityplayer = (EntityPlayer)list.get(k);
		        }
		        float f = this.mc.thePlayer.getDistanceToEntity(entityplayer);
		        if ((f <= 4.3) && (EntityUtils.getDistanceFromMouseTrig(entityplayer) == 999999.0F) && (EntityUtils.getDistanceFromMouseTrig(entityplayer) > -8.0F) && (entityplayer.isEntityAlive()) && (this.delay > 3) && (!entityplayer.isInvisible()) && (this.mc.thePlayer.canEntityBeSeen(entityplayer)))
		        {
		        this.mc.thePlayer.swingItem();
		        this.mc.playerController.attackEntity(mc.thePlayer, entityplayer);
		        this.delay = 0;
		        }
		      }
		    }
		super.onUpdate();
	}
	@Override
	public void onEnable() {
		 this.delay = 0;
		super.onEnable();
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
