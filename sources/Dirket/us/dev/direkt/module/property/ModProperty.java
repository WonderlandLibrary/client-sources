package us.dev.direkt.module.property;

import us.dev.api.interfaces.Labeled;
import us.dev.api.property.Property;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.property.annotations.Dependency;
import us.dev.direkt.module.property.annotations.Exposed;

/**
 * @author Foundry
 */
public final class ModProperty<T> implements Labeled {
    private final Propertied backingModule;
    private final Property<T> backingProperty;
    private final String propertyDescription;
    private final Dependency[] useDependencies;

    public <M extends Module & Propertied> ModProperty(M backingModule, Property<T> property, Exposed propertyMetadata) {
        this.backingProperty = property;
        this.backingModule = backingModule;
        this.propertyDescription = propertyMetadata.description();
        this.useDependencies = propertyMetadata.depends();
    }

    public <M extends Module & Propertied> ModProperty(M backingModule, Property<T> property) {
        this.backingProperty = property;
        this.backingModule = backingModule;
        this.propertyDescription = "";
        this.useDependencies = new Dependency[0];
    }

    public boolean isUsable() {
        if (useDependencies.length == 0) return true;
        else {
            for (Dependency dependency : useDependencies) {
                for (ModProperty<?> property : backingModule.getProperties()) {
                    if (property.getLabel().equals(dependency.label())
                            && property.getType().equals(dependency.type())
                            && !property.getValue().toString().equals(dependency.value())) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    @Override
    public String getLabel() {
        return backingProperty.getLabel();
    }

    public T getValue() {
        return backingProperty.getValue();
    }

    public void setValue(T value) {
        backingProperty.setValue(value);
    }

    public Property<T> getProperty() {
        return backingProperty;
    }

    public Class<? extends T> getType() {
        return backingProperty.getType();
    }

    public String getDescription() {
        return propertyDescription;
    }
}
