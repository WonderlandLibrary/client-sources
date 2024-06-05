package pw.stamina.causam.publish.listen.decorate;

import pw.stamina.causam.publish.listen.*;
import java.util.function.*;
import java.util.*;
import java.util.stream.*;

public final class ListenerDecoratorContainer<T>
{
    private final List<ListenerDecorator<T>> decorators;
    
    public ListenerDecoratorContainer() {
        this.decorators = new ArrayList<ListenerDecorator<T>>();
    }
    
    public void add(final ListenerDecorator<T> decorator) {
        this.decorators.add(decorator);
    }
    
    public Listener<T> applyDecorationsToListener(Listener<T> listener) {
        for (final ListenerDecorator<T> decorator : this.decorators) {
            listener = decorator.decorate(listener);
        }
        return listener;
    }
    
    public boolean shouldCreateProxy() {
        return this.decorators.stream().anyMatch(InstanceAssociatedListenerDecorator.class::isInstance);
    }
    
    public Stream<Class<?>> extractInterfacesFromDecorators() {
        return this.findInstanceAssociatedDecorators().map((Function<? super InstanceAssociatedListenerDecorator<T, Object>, ? extends Class<?>>)InstanceAssociatedListenerDecorator::getDecorationType);
    }
    
    public Map<Class<?>, Object> extractDecorationsInterfaceToInstanceAsMap() {
        return this.findInstanceAssociatedDecorators().collect(Collectors.toMap((Function<? super InstanceAssociatedListenerDecorator<T, Object>, ? extends Class<?>>)InstanceAssociatedListenerDecorator::getDecorationType, (Function<? super InstanceAssociatedListenerDecorator<T, Object>, ?>)InstanceAssociatedListenerDecorator::getDecoration));
    }
    
    private Stream<InstanceAssociatedListenerDecorator<T, Object>> findInstanceAssociatedDecorators() {
        return this.decorators.stream().filter(InstanceAssociatedListenerDecorator.class::isInstance).map(decorator -> decorator);
    }
}
