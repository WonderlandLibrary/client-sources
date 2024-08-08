package me.xatzdevelopments.xatz.client.darkmagician6.eventapi;

import me.xatzdevelopments.xatz.client.darkmagician6.eventapi.types.Priority;

import java.lang.annotation.*;

/**
 * Marks a method so that the EventManager knows that it should be registered.
 * The priority of the method is also set with this.
 *
 * @author DarkMagician6
 * @see com.darkmagician6.eventapi.types.Priority
 * @since July 30, 2013
 */

/**
 * sorry i had to make this look like forge's event system ~ kix
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubscribeEvent {

    byte value() default Priority.MEDIUM;
}
