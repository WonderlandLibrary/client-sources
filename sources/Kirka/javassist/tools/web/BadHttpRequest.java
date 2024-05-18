/*
 * Decompiled with CFR 0.143.
 */
package javassist.tools.web;

public class BadHttpRequest
extends Exception {
    private Exception e;

    public BadHttpRequest() {
        this.e = null;
    }

    public BadHttpRequest(Exception _e) {
        this.e = _e;
    }

    @Override
    public String toString() {
        if (this.e == null) {
            return super.toString();
        }
        return this.e.toString();
    }
}

