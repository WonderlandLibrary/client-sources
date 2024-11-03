package net.augustus.modules.misc.flagdetector;

import net.minecraft.entity.player.EntityPlayer;

public class FlagDetectorPlayerData {
    public EntityPlayer player;
    public int ticksElapsed = 0;
    public float predictedDamage = 1f;

    public FlagDetectorPlayerData(EntityPlayer player, float damage) {
        this.player = player;
        this.predictedDamage = damage;
    }
}
