package pw.stamina.causam;

import pw.stamina.causam.registry.*;
import pw.stamina.causam.scan.method.*;
import pw.stamina.causam.scan.*;
import pw.stamina.causam.event.*;

public final class StandardEventBus implements EventBus
{
    private final SubscriptionRegistry registry;
    private final EventEmitter emitter;
    
    StandardEventBus(final SubscriptionRegistry registry, final EventEmitter emitter) {
        this.registry = registry;
        this.emitter = emitter;
    }
    
    @Override
    public boolean register(final Object subscriber) {
        return this.registry.registerWith(subscriber, MethodSubscriberScanningStrategy.standard());
    }
    
    @Override
    public boolean unregister(final Object subscriber) {
        return this.registry.unregisterAll(subscriber);
    }
    
    @Override
    public <T> boolean emit(final T event) {
        if (event instanceof Cancellable) {
            return this.emitter.emitCancellable((Cancellable)event);
        }
        return this.emitter.emit(event);
    }
    
    @Override
    public EventEmitter getEmitter() {
        return this.emitter;
    }
    
    @Override
    public SubscriptionRegistry getRegistry() {
        return this.registry;
    }
}
