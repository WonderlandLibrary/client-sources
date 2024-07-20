/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import net.minecraft.realms.RealmsScreen;

public abstract class RealmsScreenWithCallback<T>
extends RealmsScreen {
    abstract void callback(T var1);
}

