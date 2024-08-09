/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;

final class Java9SslUtils {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Java9SslUtils.class);
    private static final Method SET_APPLICATION_PROTOCOLS;
    private static final Method GET_APPLICATION_PROTOCOL;
    private static final Method GET_HANDSHAKE_APPLICATION_PROTOCOL;
    private static final Method SET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR;
    private static final Method GET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR;

    private Java9SslUtils() {
    }

    static boolean supportsAlpn() {
        return GET_APPLICATION_PROTOCOL != null;
    }

    static String getApplicationProtocol(SSLEngine sSLEngine) {
        try {
            return (String)GET_APPLICATION_PROTOCOL.invoke(sSLEngine, new Object[0]);
        } catch (UnsupportedOperationException unsupportedOperationException) {
            throw unsupportedOperationException;
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    static String getHandshakeApplicationProtocol(SSLEngine sSLEngine) {
        try {
            return (String)GET_HANDSHAKE_APPLICATION_PROTOCOL.invoke(sSLEngine, new Object[0]);
        } catch (UnsupportedOperationException unsupportedOperationException) {
            throw unsupportedOperationException;
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    static void setApplicationProtocols(SSLEngine sSLEngine, List<String> list) {
        SSLParameters sSLParameters = sSLEngine.getSSLParameters();
        String[] stringArray = list.toArray(EmptyArrays.EMPTY_STRINGS);
        try {
            SET_APPLICATION_PROTOCOLS.invoke(sSLParameters, new Object[]{stringArray});
        } catch (UnsupportedOperationException unsupportedOperationException) {
            throw unsupportedOperationException;
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
        sSLEngine.setSSLParameters(sSLParameters);
    }

    static void setHandshakeApplicationProtocolSelector(SSLEngine sSLEngine, BiFunction<SSLEngine, List<String>, String> biFunction) {
        try {
            SET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR.invoke(sSLEngine, biFunction);
        } catch (UnsupportedOperationException unsupportedOperationException) {
            throw unsupportedOperationException;
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    static BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector(SSLEngine sSLEngine) {
        try {
            return (BiFunction)GET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR.invoke(sSLEngine, new Object[0]);
        } catch (UnsupportedOperationException unsupportedOperationException) {
            throw unsupportedOperationException;
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    static {
        Method method = null;
        Method method2 = null;
        Method method3 = null;
        Method method4 = null;
        Method method5 = null;
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, null, null);
            SSLEngine sSLEngine = sSLContext.createSSLEngine();
            method = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                @Override
                public Method run() throws Exception {
                    return SSLEngine.class.getMethod("getHandshakeApplicationProtocol", new Class[0]);
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
            method.invoke(sSLEngine, new Object[0]);
            method2 = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                @Override
                public Method run() throws Exception {
                    return SSLEngine.class.getMethod("getApplicationProtocol", new Class[0]);
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
            method2.invoke(sSLEngine, new Object[0]);
            method3 = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                @Override
                public Method run() throws Exception {
                    return SSLParameters.class.getMethod("setApplicationProtocols", String[].class);
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
            method3.invoke(sSLEngine.getSSLParameters(), new Object[]{EmptyArrays.EMPTY_STRINGS});
            method4 = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                @Override
                public Method run() throws Exception {
                    return SSLEngine.class.getMethod("setHandshakeApplicationProtocolSelector", BiFunction.class);
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
            method4.invoke(sSLEngine, new BiFunction<SSLEngine, List<String>, String>(){

                @Override
                public String apply(SSLEngine sSLEngine, List<String> list) {
                    return null;
                }

                @Override
                public Object apply(Object object, Object object2) {
                    return this.apply((SSLEngine)object, (List)object2);
                }
            });
            method5 = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                @Override
                public Method run() throws Exception {
                    return SSLEngine.class.getMethod("getHandshakeApplicationProtocolSelector", new Class[0]);
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
            method5.invoke(sSLEngine, new Object[0]);
        } catch (Throwable throwable) {
            logger.error("Unable to initialize Java9SslUtils, but the detected javaVersion was: {}", (Object)PlatformDependent.javaVersion(), (Object)throwable);
            method = null;
            method2 = null;
            method3 = null;
            method4 = null;
            method5 = null;
        }
        GET_HANDSHAKE_APPLICATION_PROTOCOL = method;
        GET_APPLICATION_PROTOCOL = method2;
        SET_APPLICATION_PROTOCOLS = method3;
        SET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR = method4;
        GET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR = method5;
    }
}

