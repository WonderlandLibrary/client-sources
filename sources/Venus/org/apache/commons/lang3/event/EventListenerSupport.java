/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.event;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.lang3.Validate;

public class EventListenerSupport<L>
implements Serializable {
    private static final long serialVersionUID = 3593265990380473632L;
    private List<L> listeners = new CopyOnWriteArrayList<L>();
    private transient L proxy;
    private transient L[] prototypeArray;

    public static <T> EventListenerSupport<T> create(Class<T> clazz) {
        return new EventListenerSupport<T>(clazz);
    }

    public EventListenerSupport(Class<L> clazz) {
        this(clazz, Thread.currentThread().getContextClassLoader());
    }

    public EventListenerSupport(Class<L> clazz, ClassLoader classLoader) {
        this();
        Validate.notNull(clazz, "Listener interface cannot be null.", new Object[0]);
        Validate.notNull(classLoader, "ClassLoader cannot be null.", new Object[0]);
        Validate.isTrue(clazz.isInterface(), "Class {0} is not an interface", clazz.getName());
        super.initializeTransientFields(clazz, classLoader);
    }

    private EventListenerSupport() {
    }

    public L fire() {
        return this.proxy;
    }

    public void addListener(L l) {
        this.addListener(l, false);
    }

    public void addListener(L l, boolean bl) {
        Validate.notNull(l, "Listener object cannot be null.", new Object[0]);
        if (bl) {
            this.listeners.add(l);
        } else if (!this.listeners.contains(l)) {
            this.listeners.add(l);
        }
    }

    int getListenerCount() {
        return this.listeners.size();
    }

    public void removeListener(L l) {
        Validate.notNull(l, "Listener object cannot be null.", new Object[0]);
        this.listeners.remove(l);
    }

    public L[] getListeners() {
        return this.listeners.toArray(this.prototypeArray);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ArrayList<L> arrayList = new ArrayList<L>();
        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new ByteArrayOutputStream());
        for (L l : this.listeners) {
            try {
                objectOutputStream2.writeObject(l);
                arrayList.add(l);
            } catch (IOException iOException) {
                objectOutputStream2 = new ObjectOutputStream(new ByteArrayOutputStream());
            }
        }
        objectOutputStream.writeObject(arrayList.toArray(this.prototypeArray));
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Object[] objectArray = (Object[])objectInputStream.readObject();
        this.listeners = new CopyOnWriteArrayList<Object>(objectArray);
        Class<?> clazz = objectArray.getClass().getComponentType();
        this.initializeTransientFields(clazz, Thread.currentThread().getContextClassLoader());
    }

    private void initializeTransientFields(Class<L> clazz, ClassLoader classLoader) {
        Object[] objectArray = (Object[])Array.newInstance(clazz, 0);
        this.prototypeArray = objectArray;
        this.createProxy(clazz, classLoader);
    }

    private void createProxy(Class<L> clazz, ClassLoader classLoader) {
        this.proxy = clazz.cast(Proxy.newProxyInstance(classLoader, new Class[]{clazz}, this.createInvocationHandler()));
    }

    protected InvocationHandler createInvocationHandler() {
        return new ProxyInvocationHandler(this);
    }

    static List access$000(EventListenerSupport eventListenerSupport) {
        return eventListenerSupport.listeners;
    }

    protected class ProxyInvocationHandler
    implements InvocationHandler {
        final EventListenerSupport this$0;

        protected ProxyInvocationHandler(EventListenerSupport eventListenerSupport) {
            this.this$0 = eventListenerSupport;
        }

        @Override
        public Object invoke(Object object, Method method, Object[] objectArray) throws Throwable {
            for (Object e : EventListenerSupport.access$000(this.this$0)) {
                method.invoke(e, objectArray);
            }
            return null;
        }
    }
}

