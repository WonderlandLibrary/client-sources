/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui;

import net.minecraft.realms.RealmsButton;

public interface GuiCallback {
    public void tick();

    public void buttonClicked(RealmsButton var1);
}

