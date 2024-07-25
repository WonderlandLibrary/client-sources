package club.bluezenith.scripting.bindings.data.impl;

import club.bluezenith.scripting.bindings.data.MappedProperty;

import java.lang.reflect.Field;

public class MappedField extends MappedProperty<Field> {

    public MappedField(String name, Field field, Object propertyOwnerInstance) {
        super(name, field.getType(), field, propertyOwnerInstance);
    }

    @Override
    public boolean isFunction() {
        return false;
    }

    @Override
    public Object getMember(String name) {
        if(name.equals(this.getPropertyName()))
             return runCatching(() -> this.getProperty().get(propertyOwnerInstance));
        else
             return null;
    }

    @Override
    public boolean hasMember(String name) {
        return name.equals(getPropertyName());
    }

    @Override
    public Object call(Object thiz, Object... args) {
        return runCatching(() -> this.getProperty().get(propertyOwnerInstance));
    }
}
