package pw.stamina.causam.publish.listen.decorate;

public abstract class AbstractInstanceAssociatedListenerDecorator<T, R> implements InstanceAssociatedListenerDecorator<T, R>
{
    private final Class<R> decorationType;
    protected final R decoration;
    
    protected AbstractInstanceAssociatedListenerDecorator(final Class<R> decorationType, final R decoration) {
        this.decorationType = decorationType;
        this.decoration = decoration;
    }
    
    @Override
    public final Class<R> getDecorationType() {
        return this.decorationType;
    }
    
    @Override
    public final R getDecoration() {
        return this.decoration;
    }
}
