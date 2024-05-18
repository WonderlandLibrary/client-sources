package me.protocol_client.modules;

import java.util.Iterator;

import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketRecieve;

public class NoRender extends Module{

	public NoRender() {
		super("NoRender", "norender", 0, Category.RENDER, new String[]{"dsdfsdfsdfsdghgh"});
	}
	@EventTarget
	  public void onTick(EventPacketRecieve event)
	  {
		if(event.getPacket() instanceof S0EPacketSpawnObject){
			event.setCancelled(true);
		}
		 for (Object o : mc.theWorld.loadedEntityList)
	      {
	        Entity entity = (Entity)o;
	        if ((entity instanceof EntityItem)) {
	          mc.theWorld.removeEntity(entity);
	        }
	    }
	   Iterator var3 = this.mc.theWorld.loadedEntityList.iterator();
	    while (var3.hasNext())
	    {
	      Object o = var3.next();
	      Entity entity = (Entity)o;
	      if ((entity instanceof EntityItem))
	      {
	        EntityItem item = (EntityItem)entity;
	        item.renderDistanceWeight = 0.0D;
	      }
	    }
	  }
	  
	  public void onDisable()
	  {
		  EventManager.unregister(this);
	    super.onDisable();
	    Iterator var3 = this.mc.theWorld.loadedEntityList.iterator();
	    while (var3.hasNext())
	    {
	      Object o = var3.next();
	      Entity entity = (Entity)o;
	      if ((entity instanceof EntityItem))
	      {
	        EntityItem item = (EntityItem)entity;
	        item.renderDistanceWeight = 1.0D;
	      }
	    }
	  }
	  public void onEnable(){
		  EventManager.register(this);
	  }
	}
