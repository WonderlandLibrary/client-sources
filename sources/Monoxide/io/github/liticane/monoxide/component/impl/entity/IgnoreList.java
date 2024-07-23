package io.github.liticane.monoxide.component.impl.entity;

import net.minecraft.entity.Entity;

public interface IgnoreList {

    boolean shouldSkipEntity(Entity entity);

}
