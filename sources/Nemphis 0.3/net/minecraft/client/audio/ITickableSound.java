/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.ISound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public interface ITickableSound
extends ISound,
IUpdatePlayerListBox {
    public boolean isDonePlaying();
}

