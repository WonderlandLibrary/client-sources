package pw.stamina.causam.publish.listen.decorate;

public interface InstanceAssociatedListenerDecorator<T, R> extends ListenerDecorator<T>
{
    Class<R> getDecorationType();
    
    R getDecoration();
}
