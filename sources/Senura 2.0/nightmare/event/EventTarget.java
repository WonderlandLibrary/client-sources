package nightmare.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTarget {
  byte value() default 2;
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\event\EventTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */