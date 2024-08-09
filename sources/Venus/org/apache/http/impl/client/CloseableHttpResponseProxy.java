/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

@Deprecated
class CloseableHttpResponseProxy
implements InvocationHandler {
    private static final Constructor<?> CONSTRUCTOR;
    private final HttpResponse original;

    CloseableHttpResponseProxy(HttpResponse httpResponse) {
        this.original = httpResponse;
    }

    public void close() throws IOException {
        HttpEntity httpEntity = this.original.getEntity();
        EntityUtils.consume(httpEntity);
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objectArray) throws Throwable {
        String string = method.getName();
        if (string.equals("close")) {
            this.close();
            return null;
        }
        try {
            return method.invoke(this.original, objectArray);
        } catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getCause();
            if (throwable != null) {
                throw throwable;
            }
            throw invocationTargetException;
        }
    }

    public static CloseableHttpResponse newProxy(HttpResponse httpResponse) {
        try {
            return (CloseableHttpResponse)CONSTRUCTOR.newInstance(new CloseableHttpResponseProxy(httpResponse));
        } catch (InstantiationException instantiationException) {
            throw new IllegalStateException(instantiationException);
        } catch (InvocationTargetException invocationTargetException) {
            throw new IllegalStateException(invocationTargetException);
        } catch (IllegalAccessException illegalAccessException) {
            throw new IllegalStateException(illegalAccessException);
        }
    }

    static {
        try {
            CONSTRUCTOR = Proxy.getProxyClass(CloseableHttpResponseProxy.class.getClassLoader(), CloseableHttpResponse.class).getConstructor(InvocationHandler.class);
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new IllegalStateException(noSuchMethodException);
        }
    }
}

