package net.silentclient.client.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.event.impl.EventClickMouse;

public class CPSTracker {
	private List<Long> lclicks = new ArrayList<Long>();
	private List<Long> rclicks = new ArrayList<Long>();
	
	
	public int getLCPS() {
		return this.lclicks.size();
	}
	
	public int getRCPS() {
		return this.rclicks.size();
	}
	
	@EventTarget
	public void onTick(ClientTickEvent event) {
		if(Minecraft.getMinecraft().currentScreen != null) {
			return;
		}
		List<Long> list = new ArrayList<Long>();
        Iterator<Long> mY = this.lclicks.iterator();

        while(mY.hasNext()) {
           long i = ((Long)mY.next()).longValue();
           if(System.currentTimeMillis() - i < 1000L) {
              list.add(Long.valueOf(i));
           }
        }

        this.lclicks = list;
        
        List<Long> list2 = new ArrayList<Long>();
        Iterator<Long> mY2 = this.rclicks.iterator();

        while(mY2.hasNext()) {
           long i = ((Long)mY2.next()).longValue();
           if(System.currentTimeMillis() - i < 1000L) {
              list2.add(Long.valueOf(i));
           }
        }

        this.rclicks = list2;
	}
	
	@EventTarget
	public void onMouseClick(EventClickMouse event) {
		if(event.getButton() == 0) {
			this.lclicks.add(Long.valueOf(System.currentTimeMillis()));
		} else {
			this.rclicks.add(Long.valueOf(System.currentTimeMillis()));
		}
	}
}
