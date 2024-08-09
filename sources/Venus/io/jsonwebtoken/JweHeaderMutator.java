/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.ProtectedHeaderMutator;

public interface JweHeaderMutator<T extends JweHeaderMutator<T>>
extends ProtectedHeaderMutator<T> {
    public T agreementPartyUInfo(byte[] var1);

    public T agreementPartyUInfo(String var1);

    public T agreementPartyVInfo(byte[] var1);

    public T agreementPartyVInfo(String var1);

    public T pbes2Count(int var1);
}

