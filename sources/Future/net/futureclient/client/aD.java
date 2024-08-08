package net.futureclient.client;

import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.futureclient.client.events.Event;

public class aD extends Event
{
    private EntityLivingBase a;
    private ItemStack D;
    private BlockPos k;
    
    public aD(final EntityLivingBase a, final BlockPos k, final ItemStack d) {
        super();
        this.a = a;
        this.k = k;
        this.D = d;
    }
    
    public BlockPos M() {
        return this.k;
    }
    
    public ItemStack M() {
        return this.D;
    }
    
    public EntityLivingBase M() {
        return this.a;
    }
}
