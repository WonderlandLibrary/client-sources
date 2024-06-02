/**
 * 
 */
package cafe.kagu.kagu.eventBus;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * @author lavaflowglow
 * 
 */
public @interface EventHandler {

}
