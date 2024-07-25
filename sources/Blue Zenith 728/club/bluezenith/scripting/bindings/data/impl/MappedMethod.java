package club.bluezenith.scripting.bindings.data.impl;

import club.bluezenith.scripting.bindings.data.MappedProperty;

import java.lang.reflect.Method;

public class MappedMethod extends MappedProperty<Method> {

    public MappedMethod(String methodName, Method method, Object propertyOwnerInstance) {
        super(methodName, method.getReturnType(), method, propertyOwnerInstance);
    }

    @Override
    public boolean isFunction() {
        return false;
    }

    @Override
    public Object getMember(String name) {
        if(name.equals(this.getPropertyName()))
            return this;
        else return null;
    }

    @Override
    public boolean hasMember(String name) {
        return name.equals(getPropertyName());
    }

    @Override
    public Object call(Object thiz, Object... args) {
        return runCatching(() -> {
            final Class<?>[] parameterTypes = this.getProperty().getParameterTypes();
            if(parameterTypes.length != args.length) return null; //check if enough (not less/not more than the mapped method has) arguments were provided

            for (int index = 0; index < parameterTypes.length; index++) {
                if(args[index].getClass() != parameterTypes[index]) return null; //check if arguments are in correct order and of correct types
            }

            return this.getProperty().invoke(propertyOwnerInstance, args); //if all checks passed, its safe to invoke the method.
        });
    }

}
