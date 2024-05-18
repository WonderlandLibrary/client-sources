package de.labystudio.utils;

import net.minecraft.entity.player.EntityPlayer;

public class TiltSupport
{
    public static TiltSupport.RenderCallback tiltCamera = null;

    public interface RenderCallback
    {
        void render(EntityPlayer var1);
    }
}
