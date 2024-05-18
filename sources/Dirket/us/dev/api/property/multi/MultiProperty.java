package us.dev.api.property.multi;

import com.google.common.collect.ImmutableMap;
import us.dev.api.property.Property;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author Foundry
 */
public class MultiProperty<T> extends Property<Map<String, Property<T>>> {
    private MultiProperty(String label, Map<String, Property<T>> value) {
        super(label, value);
    }

    @SuppressWarnings("unchecked")
    public Property<T> getValue(String label) {
        return (Property<T>) getValue(Object.class, label);
    }

    @SuppressWarnings("unchecked")
    public <P> Property<P> getValue(Class<P> propertyType, String label) {
        final Property lookupValue = value.get(label);
        if (lookupValue == null) {
            throw new RuntimeException("Sub-property with label \"" + label
                    + "\" does not exist in MultiProperty " + getLabel());
        } else if (!propertyType.isAssignableFrom(lookupValue.getType())) {
            throw new RuntimeException("Tried to access sub-property with label \"" + label
                    + "\" and type " + lookupValue.getType().getName()
                    + " as inconvertible type " + propertyType.getName());
        } else {
            return (Property<P>) lookupValue;
        }
    }

    public Collection<Property<T>> getProperties() {
        return Collections.unmodifiableCollection(value.values());
    }

    public static class Builder<T> {
        private final ImmutableMap.Builder<String, Property<T>> map = new ImmutableMap.Builder<>();
        private final String label;

        public Builder(String label) {
            this.label = label;
        }

        public Builder<T> put(Property<T> property) {
            map.put(property.getLabel(), property);
            return this;
        }

        public MultiProperty<T> build() {
            return new MultiProperty<>(label, map.build());
        }
    }
}
