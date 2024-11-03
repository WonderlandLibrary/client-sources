package net.silentclient.client.event;

import java.util.ArrayList;

public class Event {
	public Event call() {
		final ArrayList<EventData> dataList = EventManager.get(this.getClass());
		
		if(dataList != null) {
			for(EventData data : dataList) {
				try {
					data.target.invoke(data.source, this);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return this;
	}
}
