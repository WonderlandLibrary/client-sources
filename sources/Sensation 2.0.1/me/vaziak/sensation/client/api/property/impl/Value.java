package me.vaziak.sensation.client.api.property.impl;

/**
 * @author antja03
 */
public abstract class Value<T> {

    private String id;
    private String description;
    public T value;
    public T defaultValue;
    private me.vaziak.sensation.utils.anthony.Dependency dependency;

    protected Value(String id, String description, me.vaziak.sensation.utils.anthony.Dependency dependency) {
        this.id = id;
        this.description = description;
        this.dependency = dependency;
    }
    
    protected Value(String id, String description) {
        this.id = id;
        this.description = description;//What if we dont want a dependency?
        this.dependency = null;
    }

    public abstract void setValue(String input);

    public void setDefault() {
        value = defaultValue;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return this.description;
    }

    public T getValue() {
        return value;
    }

    public String getValueAsString() {
        return String.valueOf(value);
    }

    public void setValue(T input) {
        value = input;
    }

    public boolean checkDependency() {
        if (dependency == null) {
            return true;
        } else {
            return dependency.check();
        }
    }

    public interface Dependency {
        boolean check();
    }
}
