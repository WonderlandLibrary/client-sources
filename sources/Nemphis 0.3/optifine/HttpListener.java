/*
 * Decompiled with CFR 0_118.
 */
package optifine;

import optifine.HttpRequest;
import optifine.HttpResponse;

public interface HttpListener {
    public void finished(HttpRequest var1, HttpResponse var2);

    public void failed(HttpRequest var1, Exception var2);
}

