package us.dev.dvent.internal.util;

/**
 * @author Foundry
 */

import us.dev.dvent.LinkBody;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Foundry
 */
public final class LinkTargetResolver {
    private LinkTargetResolver() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> resolveLinkTarget(LinkBody<T> link) {
        final Class<? extends LinkBody> subType = link.getClass();
        if (subType.isSynthetic()) {
            return (Class<T>) getLambdaMethod(getSerializedLambda(link)).getParameterTypes()[0];
        } else {
            ParameterizedType lookupType;
            for (Type type : subType.getGenericInterfaces()) {
                if (type instanceof ParameterizedType && (lookupType = ((ParameterizedType) type)).getRawType().equals(LinkBody.class)) {
                    return (Class<T>) lookupType.getActualTypeArguments()[0];
                }
            }
        }
        throw new RuntimeException("Unable to resolve the target of the supplied link");
    }

    private static SerializedLambda getSerializedLambda(LinkBody<?> link) {
        for (Class<?> clazz = link.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                final Method replaceMethod = clazz.getDeclaredMethod("writeReplace");
                replaceMethod.setAccessible(true);
                return (SerializedLambda) replaceMethod.invoke(link);
            } catch (NoSuchMethodException e) {
                /* fall through the loop and try the next class */
            } catch (Throwable t) {
                throw new RuntimeException("Error while extracting serialized lambda", t);
            }
        }
        throw new RuntimeException("writeReplace method not found");
    }

    private static Method getLambdaMethod(SerializedLambda lambda) {
        Class<?> implClass; String implClassName = lambda.getImplClass().replace('/', '.');
        try {
            implClass = Class.forName(implClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to instantiate lambda class");
        }
        final String lambdaName = lambda.getImplMethodName();
        for (Method m : implClass.getDeclaredMethods()) {
            if (m.getName().equals(lambdaName)) {
                return m;
            }
        }
        throw new RuntimeException("Lambda Method not found");
    }
}

