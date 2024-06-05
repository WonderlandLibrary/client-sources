package pw.stamina.causam.publish;

import pw.stamina.causam.subscribe.*;
import pw.stamina.causam.event.*;

public final class ImmediatePublisher implements Publisher
{
    @Override
    public <T> void publish(final T event, final Iterable<Subscription<T>> subscriptions) {
        subscriptions.forEach(subscription -> subscription.publish(event));
    }
    
    @Override
    public <T extends Cancellable> void publishCancellable(final T event, final Iterable<Subscription<T>> subscriptions) {
        subscriptions.forEach(subscription -> {
            if (!subscription.ignoreCancelled() || !event.isCancelled()) {
                subscription.publish(event);
            }
        });
    }
}
