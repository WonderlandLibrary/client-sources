/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opencl;

import org.lwjgl.PointerWrapperAbstract;
import org.lwjgl.opencl.CallbackUtil;

public abstract class CLPrintfCallback
extends PointerWrapperAbstract {
    protected CLPrintfCallback() {
        super(CallbackUtil.getPrintfCallback());
    }

    protected abstract void handleMessage(String var1);
}

