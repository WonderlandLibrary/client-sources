/**
 * 
 */
package cafe.kagu.kagu.eventBus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cafe.kagu.kagu.eventBus.Event.EventPosition;
import cafe.kagu.kagu.mods.Toggleable;
import net.minecraft.client.Minecraft;


/**
 * @author DistastefulBannock
 *
 */
public class EventBus {
	
	private Map<Class<? extends Event>, ArrayList<EventSubscriber>> subscribers = new LinkedHashMap<>();
	
	/**
	 * Scans an object for handlers and subscribes them to the event bus
	 * @param obj The object to scan
	 */
	public void subscribe(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			
			// Get event handler annotations
			Annotation[] annotations = field.getAnnotationsByType(EventHandler.class);
			if (annotations.length == 0)
				continue;
			
			// Add toggleable if the field is in a class for one
			Toggleable linkedToggleable = null;
			if (obj instanceof Toggleable)
				linkedToggleable = (Toggleable)obj;
			
			// Check if the field is of a handler, then get the handler
			Handler handler = null;
			try {
				if (field.get(obj) instanceof Handler)
					handler = (Handler)field.get(obj);
				else continue;
			} catch (Exception e) {
				e.printStackTrace();
				continue; // This shouldn't happen
			}
			
			Class<?> maybeListenedEvent = null;
			try {
				// Still class.forname, but shouldn't initialize the class
				maybeListenedEvent = Class.forName(((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0].getTypeName(), false, getClass().getClassLoader());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				continue; // Should not happen
			}
			Class<? extends Event> listenedEvent = (Class<? extends Event>)maybeListenedEvent;
			
			// Make sure the listened event is actually an event and not some other shit
			if (!Event.class.isAssignableFrom(listenedEvent)) {
				continue; // This shouldn't happen, but should check
			}
			
			// Go through each event handler annotation and subscribe them to the bus
			for (Annotation annotation : annotations) {
				EventHandler eventHandler = (EventHandler)annotation;
				ArrayList<EventSubscriber> subList = subscribers.get(listenedEvent);
				if (subList == null)
					subList = new ArrayList<>();
				subList.add(new EventSubscriber(handler, linkedToggleable));
				subscribers.put(listenedEvent, subList);
			}
			
		}
	}
	
	/**
	 * Posts an event to all the handlers
	 * @param evt The event to post
	 */
	public void postEvent(Event evt) {
		
		// Don't post the event if the player or the world is null
		if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null)
			return;
		
		// Start checks then post event the handlers
		Class<? extends Event> eventClass = evt.getClass();
		if (subscribers.get(eventClass) == null)
			return; // Prevent crash
		subscribers.get(eventClass).stream().filter(sub -> sub != null && (sub.getLinkedToggleable() == null || sub.getLinkedToggleable().isEnabled())).forEach(sub -> {
			// Post the event
			try {
				sub.getHandler().onEventNoGeneric(evt);
			} catch (Exception e) {
				// If a handler throws something then it will be caught here
				System.err.println("Something went wrong while posting an event, view logs below:");
				e.printStackTrace();
			}
		});
	}
	
	private class EventSubscriber {
		
		/**
		 * @param handler
		 * @param linkToggleable
		 * @param listenedEvent
		 * @param subscribedLocation
		 */
		public EventSubscriber(Handler handler, Toggleable linkedToggleable) {
			this.handler = handler;
			this.linkedToggleable = linkedToggleable;
		}
		
		/**
		 * The handler to post the event to
		 */
		private Handler handler;

		/**
		 * If null then it's not linked to a toggleable
		 */
		private Toggleable linkedToggleable;
		
		/**
		 * @return the handler
		 */
		public Handler getHandler() {
			return handler;
		}

		/**
		 * @return the linkToggleable
		 */
		public Toggleable getLinkedToggleable() {
			return linkedToggleable;
		}
		
	}
	
}
