package club.bluezenith.scripting.bindings;

import club.bluezenith.scripting.bindings.annotations.GenerateMapping;
import club.bluezenith.scripting.bindings.annotations.MappedPropertyName;
import club.bluezenith.scripting.bindings.data.impl.MappedField;
import club.bluezenith.scripting.bindings.data.impl.MappedMethod;
import jdk.nashorn.api.scripting.AbstractJSObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BindingsFactory {

    public AbstractJSObject createObjectFromClass(String objectName, Class<?> clazz, Object classInstance) {
        final MappedJSObject mappedJSObject = new MappedJSObject(objectName);

        for (Field field : clazz.getFields()) {
            if(!field.isAnnotationPresent(GenerateMapping.class)) continue;
            if(!field.isAnnotationPresent(MappedPropertyName.class)) throw new RuntimeException("Missing MappedPropertyName annotation on a field " + field + " in class " + clazz.getName());

            mappedJSObject.addMappedField(new MappedField(field.getAnnotation(MappedPropertyName.class).value(), field, classInstance));
        }

        for (Method method : clazz.getMethods()) {
            if(!method.isAnnotationPresent(GenerateMapping.class)) continue;
            if(!method.isAnnotationPresent(MappedPropertyName.class)) throw new RuntimeException("Missing MappedPropertyName annotation on a method " + method + " in class " + clazz.getName());

            mappedJSObject.addMappedMethod(new MappedMethod(method.getAnnotation(MappedPropertyName.class).value(), method, classInstance));
        }
        return mappedJSObject;
    }

}
