package pw.stamina.causam.publish.listen.decorate.filter;

import pw.stamina.causam.publish.listen.decorate.*;
import pw.stamina.causam.publish.listen.*;
import pw.stamina.causam.publish.exception.*;

public final class FilterableListenerDecorator<T> implements InstanceAssociatedListenerDecorator<T, Filterable>
{
    private final Filterable<T> filterable;
    
    private FilterableListenerDecorator(final Filterable<T> filterable) {
        this.filterable = filterable;
    }
    
    @Override
    public Listener<T> decorate(final Listener<T> decorating) {
        return event -> {
            if (!(!this.filterable.passesFilters(event))) {
                decorating.publish(event);
            }
        };
    }
    
    @Override
    public Class<Filterable> getDecorationType() {
        return Filterable.class;
    }
    
    @Override
    public Filterable getDecoration() {
        return this.filterable;
    }
    
    public static <T> FilterableListenerDecorator<T> from(final Filterable<T> filterable) {
        return new FilterableListenerDecorator<T>(filterable);
    }
}
