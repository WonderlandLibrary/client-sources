package net.minecraft.entity.boss;

import net.minecraft.util.IChatComponent;

public interface IBossDisplayData {
   float getHealth();

   IChatComponent getDisplayName();

   float getMaxHealth();
}
