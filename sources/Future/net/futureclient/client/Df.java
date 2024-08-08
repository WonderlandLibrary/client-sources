package net.futureclient.client;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.futureclient.client.events.Event;

public class Df extends Event
{
    private World a;
    private ItemStack D;
    private EntityLivingBase k;
    
    public Df(final ItemStack d, final World a, final EntityLivingBase k) {
        super();
        this.D = d;
        this.a = a;
        this.k = k;
    }
    
    public EntityLivingBase M() {
        return this.k;
    }
    
    public World M() {
        return this.a;
    }
    
    public ItemStack M() {
        return this.D;
    }
}
