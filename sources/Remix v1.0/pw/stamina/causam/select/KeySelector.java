package pw.stamina.causam.select;

import java.util.*;

public interface KeySelector
{
    boolean canSelect(final Class<?> p0);
    
    default KeySelector exact(final Class<?> expectedKey) {
        Objects.requireNonNull(expectedKey, "expectedKey");
        return new ExactMatchingKeySelector(expectedKey);
    }
    
    default SubtypesAcceptingKeySelector acceptsSubtypes(final Class<?> superClass) {
        Objects.requireNonNull(superClass, "superClass");
        return new SubtypesAcceptingKeySelector(superClass);
    }
}
