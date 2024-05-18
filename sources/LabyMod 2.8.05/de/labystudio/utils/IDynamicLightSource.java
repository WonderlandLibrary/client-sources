package de.labystudio.utils;

import net.minecraft.entity.Entity;

public interface IDynamicLightSource
{
    Entity getAttachmentEntity();

    int getLightLevel();
}
