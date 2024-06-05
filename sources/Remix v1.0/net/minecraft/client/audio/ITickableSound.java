package net.minecraft.client.audio;

import net.minecraft.server.gui.*;

public interface ITickableSound extends ISound, IUpdatePlayerListBox
{
    boolean isDonePlaying();
}
