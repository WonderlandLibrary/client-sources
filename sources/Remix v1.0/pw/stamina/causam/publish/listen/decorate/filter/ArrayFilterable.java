package pw.stamina.causam.publish.listen.decorate.filter;

import java.util.function.*;
import java.util.*;

final class ArrayFilterable<T> implements Filterable<T>
{
    private final Predicate<T>[] filters;
    
    ArrayFilterable(final Predicate<T>[] filters) {
        this.filters = filters;
    }
    
    @Override
    public boolean passesFilters(final T event) {
        for (final Predicate<T> filter : this.filters) {
            if (!filter.test(event)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public List<Predicate<T>> getFilters() {
        return Arrays.asList(this.filters);
    }
}
