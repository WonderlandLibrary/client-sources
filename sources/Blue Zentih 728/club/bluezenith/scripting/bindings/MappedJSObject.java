package club.bluezenith.scripting.bindings;

import club.bluezenith.scripting.bindings.data.impl.MappedField;
import club.bluezenith.scripting.bindings.data.impl.MappedMethod;
import jdk.nashorn.api.scripting.AbstractJSObject;

import java.util.ArrayList;
import java.util.List;

public class MappedJSObject extends AbstractJSObject {
    private final String objectName;
    private final List<MappedField> mappedFields = new ArrayList<>();
    private final List<MappedMethod> mappedMethods = new ArrayList<>();

    public MappedJSObject(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public boolean hasMember(String name) {
        return  mappedFields.stream().anyMatch(field -> field.getPropertyName().equals(name))
                || mappedMethods.stream().anyMatch(method -> method.getPropertyName().equals(name));
    }

    @Override
    public Object getMember(String name) {
        final MappedField mappedField = mappedFields.stream()
                .filter(field -> field.getPropertyName().equals(name))
                .findFirst()
                .orElse(null);

        if(mappedField != null) return mappedField.call(null);

        return mappedMethods.stream()
                .filter(method -> method.getPropertyName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isFunction() {
        return false;
    }

    public void addMappedField(MappedField mappedField) {
        this.mappedFields.add(mappedField);
    }

    public void addMappedMethod(MappedMethod mappedMethod) {
        this.mappedMethods.add(mappedMethod);
    }
}
