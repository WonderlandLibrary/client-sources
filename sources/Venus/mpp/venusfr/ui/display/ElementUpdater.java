/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.display;

import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.utils.client.IMinecraft;

public interface ElementUpdater
extends IMinecraft {
    public void update(EventUpdate var1);
}

