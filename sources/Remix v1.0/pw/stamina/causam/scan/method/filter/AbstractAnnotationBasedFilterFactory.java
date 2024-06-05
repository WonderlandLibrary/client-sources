package pw.stamina.causam.scan.method.filter;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.function.*;

public abstract class AbstractAnnotationBasedFilterFactory<T, R extends Annotation> extends AbstractFilterFactory<T>
{
    private final Class<R> requiredAnnotation;
    
    public AbstractAnnotationBasedFilterFactory(final Class<T> supportedEventType, final Class<R> requiredAnnotation) {
        super(supportedEventType);
        this.requiredAnnotation = requiredAnnotation;
    }
    
    @Override
    public Predicate<T> createFilter(final Method method) {
        final R annotation = method.getAnnotation(this.requiredAnnotation);
        return this.createFilter(annotation);
    }
    
    protected abstract Predicate<T> createFilter(final R p0);
    
    @Override
    public boolean accepts(final Method method) {
        return method.isAnnotationPresent(this.requiredAnnotation);
    }
}
