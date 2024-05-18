package us.dev.api.factory;

import us.dev.api.factory.exceptions.FactoryException;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * @author Foundry
 */
public final class LambdaFactory {
    private static final MethodHandles.Lookup LOOKUP;

    private static final boolean DO_PRIVILEGED_LOOKUPS;

    @SuppressWarnings("unchecked")
    public static <T, K> T create(Class<T> lambdaClass, Class<? extends K> parentClass, K parentInstance, Method backingMethod, Object... capturedState) {
        validateLambdaTarget(lambdaClass, backingMethod, capturedState);    //before we go any further, check certain invariants
        try {
            final MethodHandles.Lookup caller = LOOKUP.in(parentClass);   //create a new lookup in our parent class
            MethodHandle implMethod; Method lambdaMethod = null;

            for (Method method : lambdaClass.getMethods()) {    //guaranteed that only a single method exists
                if (!method.isDefault() && !Modifier.isStatic(method.getModifiers())) {
                    lambdaMethod = method;
                    break;
                }
            }

            final int methodModifiers = backingMethod.getModifiers();
            if (Modifier.isPrivate(methodModifiers)) {  //begin resolving the MethodHandle based on its protection level
                if (DO_PRIVILEGED_LOOKUPS) {    //if we have access to a privileged lookup object
                    //private static methods don't use invokespecial bytecode, so account for that possibility
                    implMethod = Modifier.isStatic(methodModifiers) ? caller.unreflect(backingMethod) : caller.unreflectSpecial(backingMethod, parentClass);
                } else {
                    throw new FactoryException("Tried to do lookup on private method " + backingMethod.getName() + ", but an access error occurred");
                }
            } else if (Modifier.isProtected(methodModifiers)) {
                if (DO_PRIVILEGED_LOOKUPS) {
                    implMethod = caller.unreflect(backingMethod);
                } else {
                    throw new FactoryException("Tried to do lookup on protected method " + backingMethod.getName() + ", but an access error occurred");
                }
            } else if (Modifier.isPublic(methodModifiers)) {
                implMethod = caller.unreflect(backingMethod);
            } else {    //no explicit access modifier; must be package-local
                if (DO_PRIVILEGED_LOOKUPS) {
                    implMethod = caller.unreflect(backingMethod);
                } else {
                    throw new FactoryException("Tried to do lookup on package-local method " + backingMethod.getName() + ", but an access error occurred");
                }
            }

            if (implMethod != null && lambdaMethod != null) {
                final Class<?>[] erasedBackingParameters = new Class[lambdaMethod.getParameterCount()], capturedStateClasses = flattenObjectsToClasses(capturedState);
                for (int i = 0; i < lambdaMethod.getParameterTypes().length; i++) { //ensure that if the interface method uses any generic types, the created method type is made compatible with them
                    erasedBackingParameters[i] = !backingMethod.getParameterTypes()[i+capturedState.length].isAssignableFrom(lambdaMethod.getParameterTypes()[i])
                            ? Object.class : backingMethod.getParameterTypes()[i+capturedState.length];
                }
                final Class<?> erasedReturnType = backingMethod.getReturnType().isAssignableFrom(lambdaMethod.getReturnType())
                        ? backingMethod.getReturnType() : Object.class; //if the method has a generic return type, erase it

                final MethodType instantiatedMethodType = MethodType.methodType(backingMethod.getReturnType(), stripCapturedTypes(backingMethod.getParameterTypes(), capturedStateClasses));  //generate a type to match the backing method
                final MethodType lambdaMethodType = MethodType.methodType(erasedReturnType, erasedBackingParameters);
                if (Modifier.isStatic(methodModifiers)) {
                    return (T) LambdaMetafactory.metafactory(caller, lambdaMethod.getName(), MethodType.methodType(lambdaClass, stripParameterTypes(backingMethod.getParameterTypes(), capturedStateClasses)),   //the type used for static invocations
                            lambdaMethodType, implMethod, instantiatedMethodType).dynamicInvoker().invokeWithArguments(Arrays.asList(capturedState));   //varargs seems to resolve a passed array as a single object otherwise
                } else {
                    if (parentInstance == null) {   //assert that the backing instance for a non-static method is not null
                        throw new FactoryException("Instance of parent class for non-static method " + backingMethod.getName() + " cannot be null");
                    }
                    return (T) LambdaMetafactory.metafactory(caller, lambdaMethod.getName(), MethodType.methodType(lambdaClass, parentClass, (Class<?>[]) stripParameterTypes(backingMethod.getParameterTypes(), capturedStateClasses)),    //the type used for instance invocations
                            lambdaMethodType, implMethod, instantiatedMethodType).dynamicInvoker().invokeWithArguments(Arrays.asList(arrayConcat(new Object[] {parentInstance}, capturedState)));
                }
            } else {
                throw new FactoryException("Unable to do lookup on method \"" + backingMethod.getName() + "\", insufficient permissions from security manager?");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new FactoryException("Exception creating dynamic invoker for method \"" + backingMethod.getName() + "\":" + System.lineSeparator() + e);
        }
    }

    public static <T, K> T create(Class<T> lambdaClass, Class<? extends K> parentClass, K parentInstance, String methodEquivalent, Class<?>[] methodParameters, Object... capturedState) {
        for (Class<?> clazz = parentClass; clazz.getSuperclass() != null; clazz = clazz.getSuperclass()) {  //loop through inheritance tree
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(methodEquivalent) && Arrays.equals(method.getParameterTypes(), methodParameters))   //validate that method signature matches intention
                    return create(lambdaClass, parentClass, parentInstance, method, capturedState);
            }
        }
        throw new FactoryException("Method " + methodEquivalent
                + " with parameters " + Arrays.toString(methodParameters)
                + " does not exist in class " + parentClass.getSimpleName());
    }

    public static <T, K> T create(Class<T> lambdaClass, Class<? extends K> parentClass, K parentInstance, String methodEquivalent, Class<?>... methodParameters) {
        return create(lambdaClass, parentClass, parentInstance, methodEquivalent, methodParameters, new Object[0]);
    }

    private static void validateLambdaTarget(Class<?> lambdaClass, Method methodEquivalent, Object... capturedState) throws FactoryException {
        if (!lambdaClass.isInterface()) {   //basic check to ensure that you are dealing with an interface
            throw new FactoryException("Class " + lambdaClass.getSimpleName()
                    + " is not an interface and cannot be used as a lambda target");
        }
        Method functionalMethod = null;
        for (Method method : lambdaClass.getMethods()) {
            if (!method.isDefault() && !Modifier.isStatic(method.getModifiers())) {  //check to make sure that we aren't dealing with a default method
                if (functionalMethod == null) { //first non-default method found
                    functionalMethod = method;
                } else {   //more than one non-default method exists in interface
                    throw new FactoryException("Interface " + lambdaClass.getSimpleName()
                            + "has more than one non-default method and cannot be used as a lambda target");
                }
            }
        }

        if (functionalMethod == null) {   //interface has no non-default methods
            throw new FactoryException("Interface " + lambdaClass.getSimpleName() + " does not have any non-default methods");
        } else if (!functionalMethod.getReturnType().isAssignableFrom(methodEquivalent.getReturnType())) { //sanity check for incompatible return types
            throw new FactoryException("Incompatible methods: backing method " + methodEquivalent.getName()
                    + " returns " + methodEquivalent.getReturnType()
                    + " while interface method " + functionalMethod.getName()
                    + " returns " + functionalMethod.getReturnType());
        } else if (functionalMethod.getParameterCount() != methodEquivalent.getParameterCount() - capturedState.length) {  //sanity check for incompatible parameter counts
            throw new FactoryException("Incompatible methods: backing method " + methodEquivalent.getName()
                    + " takes " + methodEquivalent.getParameterCount() + " arguments while interface method " + functionalMethod.getName()
                    + " takes " + functionalMethod.getParameterCount() + " arguments");
        }

        Class<?>[] lambdaParams = functionalMethod.getParameterTypes(), methodParams = methodEquivalent.getParameterTypes();
        for (int i = 0; i < capturedState.length; i++) {    //check for compatible method signatures inside capture
            if (!methodParams[i].isAssignableFrom(capturedState[i].getClass())) {   //ensure the two parameters are interoperable
                throw new FactoryException("Incompatible capture: parameter " + i + " of backing method " + methodEquivalent.getName()
                        + " is of type " + methodParams[i].getSimpleName()
                        + " but is supplied captured argument of type " + capturedState[i].getClass().getSimpleName());
            }
        }
        for (int i = 0; i < lambdaParams.length; i++) {    //check for compatible method signatures past capture
            if (!lambdaParams[i].isAssignableFrom(methodParams[i+capturedState.length])) {   //ensure the two parameters are interoperable
                throw new FactoryException("Incompatible methods: parameter " + i+capturedState.length + " of backing method " + methodEquivalent.getName()
                        + " is of type " + methodParams[i+capturedState.length].getSimpleName()
                        + " while parameter " + i + " of interface method " + functionalMethod.getName()
                        + " is of type " + lambdaParams[i].getSimpleName());
            }
        }
    }

    private static Class<?>[] flattenObjectsToClasses(Object[] objects) {
        Class<?>[] classes = new Class[objects.length];
        for (int i = 0; i < objects.length; i++) {
            classes[i] = objects[i].getClass();
        }
        return classes;
    }

    private static Class<?>[] stripCapturedTypes(Class<?>[] parameterTypes, Class<?>... captureTypes) {
        return Arrays.copyOfRange(parameterTypes, captureTypes.length, parameterTypes.length);
    }

    private static Class<?>[] stripParameterTypes(Class<?>[] parameterTypes, Class<?>... captureTypes) {
        return Arrays.copyOfRange(parameterTypes, 0, captureTypes.length);
    }

    private static Object[] arrayConcat(Object[] first, Object[] second) {
        Object[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    static {
        MethodHandles.Lookup lookupObject;
        boolean hasPrivilegedLookup;
        try {
            Field lookupImplField = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP"); //try to get the trusted Lookup object
            lookupImplField.setAccessible(true);    //attempt to crack the field
            lookupObject = (MethodHandles.Lookup) lookupImplField.get(null);    //success!
            hasPrivilegedLookup = true;
        } catch (ReflectiveOperationException e) {  //something prevented us from accessing the trusted Lookup object
            System.err.println("(LambdaFactory) (Warning) Could not acquire privileged lookup object; lambda factory usage will be limited to public methods");
            lookupObject = MethodHandles.lookup();  //failure, unprivileged lookups only
            hasPrivilegedLookup = false;
        }

        LOOKUP = lookupObject;
        DO_PRIVILEGED_LOOKUPS = hasPrivilegedLookup;
    }
}