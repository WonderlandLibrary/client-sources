package me.tojatta.api.utilities.value;

import java.lang.annotation.*;
import me.tojatta.api.utilities.value.impl.annotations.*;
import me.tojatta.api.utilities.value.impl.*;
import java.lang.reflect.*;
import java.util.*;

public class ValueManager
{
    public static final String AUTHOR = "Tojatta";
    private HashMap<Value, Class> values;
    
    public ValueManager() {
        this.values = new HashMap<Value, Class>();
    }
    
    public HashMap<Value, Class> getValues() {
        return this.values;
    }
    
    public Optional<Value> getOptionalValueName(final String name) {
        return (Optional<Value>)this.values.keySet().stream().filter(value -> name.equalsIgnoreCase(value.getLabel())).findAny();
    }
    
    public List<Value> getValuesFromClass(final Object object) {
        final ArrayList<Value> valueList = new ArrayList<Value>();
        this.values.entrySet().stream().filter(valueClassEntry -> valueClassEntry.getValue().equals(object.getClass())).forEach(valueClassEntry -> valueList.add(valueClassEntry.getKey()));
        return valueList;
    }
    
    public final void register(final Object o) {
        if (o == null) {
            return;
        }
        final Class<?> objectClass = o.getClass();
        for (final Field field : objectClass.getDeclaredFields()) {
            final boolean accessible = field.isAccessible();
            field.setAccessible(true);
            Label_0622: {
                if (field.isAnnotationPresent(BooleanValue.class)) {
                    final BooleanValue annotation = field.getAnnotation(BooleanValue.class);
                    final TypeBoolean booleanValue = new TypeBoolean(annotation.label(), o, field);
                    this.values.put(booleanValue, objectClass);
                    System.out.println(booleanValue.getLabel());
                }
                else if (field.isAnnotationPresent(StringValue.class)) {
                    final StringValue annotation2 = field.getAnnotation(StringValue.class);
                    final TypeString stringValue = new TypeString(annotation2.label(), o, field);
                    this.values.put(stringValue, objectClass);
                    System.out.println(stringValue.getLabel());
                }
                else if (field.isAnnotationPresent(NumberValue.class)) {
                    if (!field.getType().isPrimitive()) {
                        break Label_0622;
                    }
                    final NumberValue annotation3 = field.getAnnotation(NumberValue.class);
                    TypeNumber numberValue = null;
                    if (field.getType().isAssignableFrom(Byte.TYPE)) {
                        numberValue = new TypeNumber(annotation3.label(), o, field, (T)Byte.parseByte(annotation3.minimum()), (T)Byte.parseByte(annotation3.maximum()));
                    }
                    else if (field.getType().isAssignableFrom(Short.TYPE)) {
                        numberValue = new TypeNumber(annotation3.label(), o, field, (T)Short.parseShort(annotation3.minimum()), (T)Short.parseShort(annotation3.maximum()));
                    }
                    else if (field.getType().isAssignableFrom(Integer.TYPE)) {
                        numberValue = new TypeNumber(annotation3.label(), o, field, (T)Integer.parseInt(annotation3.minimum()), (T)Integer.parseInt(annotation3.maximum()));
                    }
                    else if (field.getType().isAssignableFrom(Long.TYPE)) {
                        numberValue = new TypeNumber(annotation3.label(), o, field, (T)Long.parseLong(annotation3.minimum()), (T)Long.parseLong(annotation3.maximum()));
                    }
                    else if (field.getType().isAssignableFrom(Double.TYPE)) {
                        numberValue = new TypeNumber(annotation3.label(), o, field, (T)Double.parseDouble(annotation3.minimum()), (T)Double.parseDouble(annotation3.maximum()));
                    }
                    else if (field.getType().isAssignableFrom(Float.TYPE)) {
                        numberValue = new TypeNumber(annotation3.label(), o, field, (T)Float.parseFloat(annotation3.minimum()), (T)Float.parseFloat(annotation3.maximum()));
                    }
                    if (numberValue != null) {
                        System.out.println(numberValue.getLabel());
                        this.values.put(numberValue, objectClass);
                    }
                }
                field.setAccessible(accessible);
            }
        }
    }
}
