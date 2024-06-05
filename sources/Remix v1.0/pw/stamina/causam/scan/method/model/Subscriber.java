package pw.stamina.causam.scan.method.model;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscriber {
    Pausable pausable() default @Pausable(Pausable.PausableType.NONE);
    
    boolean synchronize() default false;
    
    boolean rejectSubtypes() default false;
    
    boolean ignoreCancelled() default false;
}
