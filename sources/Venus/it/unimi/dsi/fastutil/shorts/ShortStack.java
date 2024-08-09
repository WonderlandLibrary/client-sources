/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Stack;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ShortStack
extends Stack<Short> {
    @Override
    public void push(short var1);

    public short popShort();

    public short topShort();

    public short peekShort(int var1);

    @Override
    @Deprecated
    default public void push(Short s) {
        this.push((short)s);
    }

    @Override
    @Deprecated
    default public Short pop() {
        return this.popShort();
    }

    @Override
    @Deprecated
    default public Short top() {
        return this.topShort();
    }

    @Override
    @Deprecated
    default public Short peek(int n) {
        return this.peekShort(n);
    }

    @Override
    @Deprecated
    default public Object peek(int n) {
        return this.peek(n);
    }

    @Override
    @Deprecated
    default public Object top() {
        return this.top();
    }

    @Override
    @Deprecated
    default public Object pop() {
        return this.pop();
    }

    @Override
    @Deprecated
    default public void push(Object object) {
        this.push((Short)object);
    }
}

