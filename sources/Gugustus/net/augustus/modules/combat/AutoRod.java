package net.augustus.modules.combat;

import java.awt.Color;

import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.skid.tenacity.TimerUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemFishingRod;

public class AutoRod extends Module{

	public TimerUtil timer = new TimerUtil();
	public TimerUtil timer2 = new TimerUtil();

   public DoubleValue rodRangeMin;
   public DoubleValue rodRangeMax;
   public DoubleValue rodDelayMin;
   public DoubleValue rodDelayMax;
   public DoubleValue nextRodDelay;
   public BooleanValue resetSlot;
	
	public AutoRod() {
		super("AutoRod", Color.CYAN, Categorys.COMBAT);
		rodRangeMin = new DoubleValue(696969, "MinRodRange", this, 4.0, 0.0, 15.0, 1);
		rodRangeMax = new DoubleValue(66969, "MaxRodRange", this, 4.0, 0.0, 15.0, 1);
		rodDelayMin = new DoubleValue(63, "MinDelay", this, 40.0, 0.0, 1000.0, 0);
		rodDelayMax = new DoubleValue(466445, "MaxDelay", this, 40.0, 0.0, 1000.0, 0);
		nextRodDelay = new DoubleValue(416445, "NextRod", this, 40.0, 0.0, 5000.0, 0);
		resetSlot = new BooleanValue(290, "ResetSlot", this, true);
	}
	
	@EventTarget
	public void onEventTick(EventTick e) {
		if(this.isToggled()) {
	      if (mc.currentScreen == null && mm.killAura.target != null) {
	    	  EntityLivingBase target = mm.killAura.target;
	    	  int originalSlot = mc.thePlayer.inventory.currentItem;
	    	  boolean foundRod = false;
	    	  int rodSlot = -1;
	    	  for(int i = 0; i < 9; i++) {
	    		  if(mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemFishingRod) {
	    			  foundRod = true;
	    			  rodSlot = i;
	    		  }
	    	  }
	    	  if(foundRod && rodSlot != -1) {
	    		  if(target.getDistanceToEntity(mc.thePlayer) < rodRangeMin.getValue() || target.getDistanceToEntity(mc.thePlayer) > rodRangeMax.getValue())
	    			  return;
	    		  if(timer.hasTimeElapsed((int)(RandomUtil.nextFloat(rodDelayMin.getValue(), rodDelayMax.getValue())))) {
	    			  mc.thePlayer.inventory.currentItem = rodSlot;
	    			  mc.rightClickMouse();
	    			  mc.thePlayer.inventory.currentItem = originalSlot;
	    			  if(timer2.hasTimeElapsed((int)nextRodDelay.getValue(), true)) {
	    				  timer.reset();
	    			  }
	    		  }
	    	  }
	      }
		}
	}

}
