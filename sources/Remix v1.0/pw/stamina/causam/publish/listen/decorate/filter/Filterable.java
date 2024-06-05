package pw.stamina.causam.publish.listen.decorate.filter;

import java.util.*;
import java.util.function.*;

public interface Filterable<T>
{
    boolean passesFilters(final T p0);
    
    List<Predicate<T>> getFilters();
    
    default <T> Filterable<T> array(final Collection<Predicate<T>> filters) {
        Objects.requireNonNull(filters, "filters");
        if (filters.isEmpty()) {
            throw new IllegalArgumentException("filters must not be empty");
        }
        return new ArrayFilterable<T>(filters.stream().peek(Objects::requireNonNull).toArray(Predicate[]::new));
    }
}
