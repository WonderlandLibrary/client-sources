package club.bluezenith.scripting.bindings.data;

import club.bluezenith.scripting.bindings.MappedJSObject;
import club.bluezenith.util.client.UnsafeSupplier;
import jdk.nashorn.api.scripting.AbstractJSObject;

import java.util.ArrayList;
import java.util.List;

public class MappedProperty<T> extends AbstractJSObject {

    protected final List<MappedJSObject> innerMembers = new ArrayList<>();

    protected final Class<?> propertyReturnType;

    private final String propertyName;
    private final T property;

    protected final Object propertyOwnerInstance;

    public MappedProperty(String propertyName, Class<?> propertyReturnType, T property, Object propertyOwnerInstance) {
        this.propertyName = propertyName;
        this.propertyReturnType = propertyReturnType;
        this.property = property;
        this.propertyOwnerInstance = propertyOwnerInstance;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public Class<?> getPropertyReturnType() {
        return this.propertyReturnType;
    }

    public T getProperty() {
        return this.property;
    }

    public void addInnerMember(MappedJSObject mappedJSObject) {
        this.innerMembers.add(mappedJSObject);
    }

    protected <Z> Z runCatching(UnsafeSupplier<Z> unsafeSupplier) {
        try {
            return unsafeSupplier.get();
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
