package pw.stamina.causam.scan.method.filter;

import java.lang.reflect.*;
import java.lang.annotation.*;
import java.util.function.*;
import java.util.*;
import java.util.stream.*;

class SimpleFilterCreationService implements FilterCreationService
{
    @Override
    public <T> List<Predicate<T>> createFilters(final Method method) {
        final Annotation[] annotations = method.getDeclaredAnnotations();
        final List<Predicate<T>> castedFilters;
        final List<?> filters = castedFilters = (List<Predicate<T>>)Arrays.stream(annotations).filter(this::isAnnotationAnEventFilter).map((Function<? super Annotation, ?>)this::getFilterAnnotationFromAnnotation).map((Function<? super Object, ?>)this::createFilterFactoryFromClass).filter(Objects::nonNull).map(factory -> factory.createFilter(method)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        return castedFilters;
    }
    
    private boolean isAnnotationAnEventFilter(final Annotation annotation) {
        return annotation.annotationType().isAnnotationPresent(Filter.class);
    }
    
    private Filter getFilterAnnotationFromAnnotation(final Annotation annotation) {
        return annotation.annotationType().getAnnotation(Filter.class);
    }
    
    private FilterFactory<?> createFilterFactoryFromClass(final Filter filter) {
        final Class<? extends FilterFactory<?>> factoryClass = filter.value();
        try {
            return (FilterFactory<?>)factoryClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            throw new AssertionError((Object)e);
        }
    }
}
