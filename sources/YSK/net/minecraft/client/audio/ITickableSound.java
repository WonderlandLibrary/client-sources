package net.minecraft.client.audio;

import net.minecraft.util.*;

public interface ITickableSound extends ITickable, ISound
{
    boolean isDonePlaying();
}
