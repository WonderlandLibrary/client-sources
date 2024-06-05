package pw.stamina.causam.scan.method.filter;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

public interface FilterCreationService
{
     <T> List<Predicate<T>> createFilters(final Method p0);
}
