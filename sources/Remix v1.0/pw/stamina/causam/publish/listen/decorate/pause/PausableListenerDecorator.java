package pw.stamina.causam.publish.listen.decorate.pause;

import pw.stamina.causam.publish.listen.decorate.*;
import pw.stamina.causam.publish.listen.*;
import pw.stamina.causam.publish.exception.*;

public final class PausableListenerDecorator<T> extends AbstractInstanceAssociatedListenerDecorator<T, Pausable>
{
    private PausableListenerDecorator(final Pausable pausable) {
        super(Pausable.class, pausable);
    }
    
    @Override
    public Listener<T> decorate(final Listener<T> decorating) {
        return event -> {
            if (!((Pausable)this.decoration).isPaused()) {
                decorating.publish(event);
            }
        };
    }
    
    public static <T> PausableListenerDecorator<T> simple() {
        return new PausableListenerDecorator<T>(Pausable.simple());
    }
    
    public static <T> PausableListenerDecorator<T> atomic() {
        return new PausableListenerDecorator<T>(Pausable.atomic());
    }
}
