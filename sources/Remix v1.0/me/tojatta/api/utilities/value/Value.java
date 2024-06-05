package me.tojatta.api.utilities.value;

import me.tojatta.api.utilities.value.interfaces.*;
import java.lang.reflect.*;

public class Value<T> implements Labelable
{
    protected String label;
    protected Object object;
    protected Field field;
    
    public Value(final String label, final Object object, final Field field) {
        this.label = label;
        this.object = object;
        this.field = field;
    }
    
    public T getValue() {
        try {
            final boolean accessible = this.field.isAccessible();
            this.field.setAccessible(true);
            final T val = (T)this.field.get(this.object);
            this.field.setAccessible(accessible);
            return val;
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void setValue(final T value) {
        try {
            final boolean accessible = this.field.isAccessible();
            this.field.setAccessible(true);
            this.field.set(this.object, value);
            this.field.setAccessible(accessible);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String getLabel() {
        return this.label;
    }
    
    public void setLabel(final String label) {
        this.label = label;
    }
    
    public Object getObject() {
        return this.object;
    }
}
