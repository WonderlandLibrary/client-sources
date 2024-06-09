package com.client.glowclient;

import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.monster.*;
import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class he extends rF
{
    private bA b;
    
    @SubscribeEvent
    public void M(final mg mg) {
        if (this.b != null) {
            this.b.M();
        }
    }
    
    @SubscribeEvent
    public void M(final LivingEvent$LivingUpdateEvent livingEvent$LivingUpdateEvent) {
        try {
            if (livingEvent$LivingUpdateEvent.getEntityLiving() instanceof EntityPigZombie) {
                final EntityPigZombie entityPigZombie;
                if ((entityPigZombie = (EntityPigZombie)livingEvent$LivingUpdateEvent.getEntityLiving()).isArmsRaised()) {
                    entityPigZombie.angerLevel = 400;
                    return;
                }
                if (entityPigZombie.isAngry()) {
                    final EntityPigZombie entityPigZombie2 = entityPigZombie;
                    --entityPigZombie2.angerLevel;
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void M(final ee ee) {
        this.b = cB.M(eC.B);
        if (this.b != null) {
            this.b.D();
        }
    }
    
    @SubscribeEvent
    public void A(final EventUpdate eventUpdate) {
        try {
            Wrapper.mc.player.inPortal = false;
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void M(final ItemTooltipEvent itemTooltipEvent) {
        if (!false || itemTooltipEvent.getFlags().isAdvanced()) {
            final NBTTagCompound tagCompound = itemTooltipEvent.getItemStack().getTagCompound();
            if (itemTooltipEvent.getItemStack().isItemStackDamageable()) {
                final int maxDamage = itemTooltipEvent.getItemStack().getMaxDamage();
                final int itemDamage = itemTooltipEvent.getItemStack().itemDamage;
                itemTooltipEvent.getToolTip().add(new StringBuilder().insert(0, "Damage: ").append(maxDamage - itemDamage).append("/").append(maxDamage).append(" (").append(itemDamage).append(")").toString());
            }
            if (tagCompound != null && itemTooltipEvent.getItemStack().getTagCompound().hasKey("RepairCost")) {
                itemTooltipEvent.getToolTip().add(new StringBuilder().insert(0, "Repair: ").append(itemTooltipEvent.getItemStack().getRepairCost()).toString());
            }
        }
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        eventUpdate.getEntityLiving().extinguish();
    }
    
    public he() {
        final bA b = null;
        super("BackgroundFixMod", "Repairs Minecraft inconsistencies and errors");
        this.b = b;
    }
}
