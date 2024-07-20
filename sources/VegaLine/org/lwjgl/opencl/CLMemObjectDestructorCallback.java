/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opencl;

import org.lwjgl.PointerWrapperAbstract;
import org.lwjgl.opencl.CallbackUtil;

public abstract class CLMemObjectDestructorCallback
extends PointerWrapperAbstract {
    protected CLMemObjectDestructorCallback() {
        super(CallbackUtil.getMemObjectDestructorCallback());
    }

    protected abstract void handleMessage(long var1);
}

