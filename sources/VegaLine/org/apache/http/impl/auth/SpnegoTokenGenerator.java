/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

import java.io.IOException;

@Deprecated
public interface SpnegoTokenGenerator {
    public byte[] generateSpnegoDERObject(byte[] var1) throws IOException;
}

