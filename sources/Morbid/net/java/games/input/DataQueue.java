package net.java.games.input;

final class DataQueue
{
    private final Object[] elements;
    private int position;
    private int limit;
    static /* synthetic */ Class class$net$java$games$input$DataQueue;
    
    public DataQueue(final int size, final Class element_type) {
        this.elements = new Object[size];
        for (int i = 0; i < this.elements.length; ++i) {
            try {
                this.elements[i] = element_type.newInstance();
            }
            catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
            catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
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
    
    public final Object get(final int index) {
        assert index < this.limit;
        return this.elements[index];
    }
    
    public final Object get() {
        if (!this.hasRemaining()) {
            return null;
        }
        return this.get(this.position++);
    }
    
    public final void compact() {
        int index = 0;
        while (this.hasRemaining()) {
            this.swap(this.position, index);
            ++this.position;
            ++index;
        }
        this.position = index;
        this.limit = this.elements.length;
    }
    
    private final void swap(final int index1, final int index2) {
        final Object temp = this.elements[index1];
        this.elements[index1] = this.elements[index2];
        this.elements[index2] = temp;
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
    
    public final void position(final int position) {
        this.position = position;
    }
    
    public final Object[] getElements() {
        return this.elements;
    }
    
    static /* synthetic */ Class class$(final String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x) {
            throw new NoClassDefFoundError().initCause(x);
        }
    }
    
    static {
        $assertionsDisabled = !((DataQueue.class$net$java$games$input$DataQueue == null) ? (DataQueue.class$net$java$games$input$DataQueue = class$("net.java.games.input.DataQueue")) : DataQueue.class$net$java$games$input$DataQueue).desiredAssertionStatus();
    }
}
