/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.ISound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public interface ITickableSound
extends ISound,
IUpdatePlayerListBox {
    public boolean isDonePlaying();
}

