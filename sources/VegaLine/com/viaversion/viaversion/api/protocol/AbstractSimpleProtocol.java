/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.SimpleProtocol;

public abstract class AbstractSimpleProtocol
extends AbstractProtocol<SimpleProtocol.DummyPacketTypes, SimpleProtocol.DummyPacketTypes, SimpleProtocol.DummyPacketTypes, SimpleProtocol.DummyPacketTypes>
implements SimpleProtocol {
    protected AbstractSimpleProtocol() {
        super(null, null, null, null);
    }
}

