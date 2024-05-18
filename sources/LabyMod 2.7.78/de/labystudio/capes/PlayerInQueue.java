package de.labystudio.capes;

import net.minecraft.client.entity.AbstractClientPlayer;

public class PlayerInQueue
{
    private AbstractClientPlayer player;
    private boolean refresh;

    public PlayerInQueue(AbstractClientPlayer player, boolean refresh)
    {
        this.player = player;
        this.refresh = refresh;
    }

    public AbstractClientPlayer getPlayer()
    {
        return this.player;
    }

    public boolean isRefresh()
    {
        return this.refresh;
    }
}
