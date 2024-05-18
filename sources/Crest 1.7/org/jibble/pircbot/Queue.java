// 
// Decompiled by Procyon v0.5.30
// 

package org.jibble.pircbot;

import java.util.Vector;

public class Queue
{
    private Vector _queue;
    
    public Queue() {
        this._queue = new Vector();
    }
    
    public void add(final Object o) {
        synchronized (this._queue) {
            this._queue.addElement(o);
            this._queue.notify();
        }
    }
    
    public void addFront(final Object o) {
        synchronized (this._queue) {
            this._queue.insertElementAt(o, 0);
            this._queue.notify();
        }
    }
    
    public Object next() {
        Object firstElement = null;
        synchronized (this._queue) {
            if (this._queue.size() == 0) {
                try {
                    this._queue.wait();
                }
                catch (InterruptedException ex) {
                    return null;
                }
            }
            try {
                firstElement = this._queue.firstElement();
                this._queue.removeElementAt(0);
            }
            catch (ArrayIndexOutOfBoundsException ex2) {
                throw new InternalError("Race hazard in Queue object.");
            }
        }
        return firstElement;
    }
    
    public boolean hasNext() {
        return this.size() != 0;
    }
    
    public void clear() {
        synchronized (this._queue) {
            this._queue.removeAllElements();
        }
    }
    
    public int size() {
        return this._queue.size();
    }
}
