/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.api.entities.meta;

import nl.matsv.viabackwards.api.entities.meta.MetaHandlerEvent;
import nl.matsv.viabackwards.api.exceptions.RemovedValueException;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;

public interface MetaHandler {
    public Metadata handle(MetaHandlerEvent var1) throws RemovedValueException;
}

