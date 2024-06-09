/**
 * 
 */
package cafe.kagu.kagu.eventBus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 
 * @author lavaflowglow
 * @param <E> The event that you want to listen for, also includes events that
 *            extend off of that event
 */
@SuppressWarnings("hiding")
public interface Handler<E extends Event> {

	/**
	 * Called when the subscriber receives an event
	 * @param e The event being received
	 */
	public void onEvent(E e);
	
	/**
	 * @param e The event being received, it will be casted to the generic type and sent to the onEvent method
	 */
	@SuppressWarnings("unchecked")
	public default void onEventNoGeneric(Event e) {
		onEvent((E)e);
	}
}
