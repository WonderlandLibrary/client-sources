package pw.stamina.causam.scan.method.filter;

import java.lang.reflect.*;
import java.util.function.*;

public interface FilterFactory<T>
{
    Predicate<T> createFilter(final Method p0);
    
    boolean accepts(final Method p0);
    
    Class<T> getSupportedEventType();
}
