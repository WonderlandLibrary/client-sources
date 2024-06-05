package pw.stamina.causam.scan.method.model;

import java.util.function.*;
import pw.stamina.causam.publish.listen.decorate.pause.*;

public enum PausableType
{
    NONE(() -> null), 
    SIMPLE(PausableListenerDecorator::simple), 
    ATOMIC(PausableListenerDecorator::atomic);
    
    private final Supplier<PausableListenerDecorator> factory;
    
    private PausableType(final Supplier<PausableListenerDecorator> factory) {
        this.factory = factory;
    }
    
    public final PausableListenerDecorator create() {
        return this.factory.get();
    }
}
