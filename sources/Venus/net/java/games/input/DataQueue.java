/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

final class DataQueue {
    private final Object[] elements;
    private int position;
    private int limit;
    static final boolean $assertionsDisabled = !(class$net$java$games$input$DataQueue == null ? (class$net$java$games$input$DataQueue = DataQueue.class$("net.java.games.input.DataQueue")) : class$net$java$games$input$DataQueue).desiredAssertionStatus();
    static Class class$net$java$games$input$DataQueue;

    public DataQueue(int n, Class clazz) {
        this.elements = new Object[n];
        for (int i = 0; i < this.elements.length; ++i) {
            try {
                this.elements[i] = clazz.newInstance();
                continue;
            } catch (InstantiationException instantiationException) {
                throw new RuntimeException(instantiationException);
            } catch (IllegalAccessException illegalAccessException) {
                throw new RuntimeException(illegalAccessException);
            }
        }
        this.clear();
    }

    public final void clear() {
        this.position = 0;
        this.limit = this.elements.length;
    }

    public final int position() {
        return this.position;
    }

    public final int limit() {
        return this.limit;
    }

    public final Object get(int n) {
        if (!$assertionsDisabled && n >= this.limit) {
            throw new AssertionError();
        }
        return this.elements[n];
    }

    public final Object get() {
        if (!this.hasRemaining()) {
            return null;
        }
        return this.get(this.position++);
    }

    public final void compact() {
        int n = 0;
        while (this.hasRemaining()) {
            this.swap(this.position, n);
            ++this.position;
            ++n;
        }
        this.position = n;
        this.limit = this.elements.length;
    }

    private final void swap(int n, int n2) {
        Object object = this.elements[n];
        this.elements[n] = this.elements[n2];
        this.elements[n2] = object;
    }

    public final void flip() {
        this.limit = this.position;
        this.position = 0;
    }

    public final boolean hasRemaining() {
        return this.remaining() > 0;
    }

    public final int remaining() {
        return this.limit - this.position;
    }

    public final void position(int n) {
        this.position = n;
    }

    public final Object[] getElements() {
        return this.elements;
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError().initCause(classNotFoundException);
        }
    }
}

