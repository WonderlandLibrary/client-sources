package net.minecraft.client.audio;

public interface ISoundEventAccessor<T>
{
    T cloneEntry();
    
    int getWeight();
}
