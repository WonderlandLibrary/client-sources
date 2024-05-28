package arsenic.module.property;

import arsenic.utils.interfaces.ISerializable;

public abstract class SerializableProperty<T> extends Property<T> implements ISerializable {

    protected String name;

    protected SerializableProperty(String name, T value) {
        super(value);
        this.name = name;
    }

    @Override
    public final String getJsonKey() { return name; }

    @Override
    public final String getName() { return name; }

}
