package com.client.glowclient.modules.player;

import net.minecraftforge.client.event.*;
import net.minecraft.client.entity.*;
import com.client.glowclient.utils.*;
import java.util.*;
import net.minecraft.entity.passive.*;
import com.google.common.util.concurrent.*;
import com.client.glowclient.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class MCF extends ModuleContainer
{
    @SubscribeEvent
    public void M(final MouseEvent mouseEvent) {
        final RayTraceResult objectMouseOver;
        if (mouseEvent.getButton() == 2 && mouseEvent.isButtonstate() && (objectMouseOver = Wrapper.mc.objectMouseOver) != null && objectMouseOver.typeOfHit == RayTraceResult$Type.ENTITY) {
            final Entity entityHit;
            Label_0129: {
                if ((entityHit = objectMouseOver.entityHit) instanceof EntityOtherPlayerMP) {
                    final String name = entityHit.getName();
                    if (!Va.M().M(name)) {
                        if (wa.M().M(name)) {
                            qd.D(String.format("§c%s is already an enemy", name));
                            final Entity entity = entityHit;
                            break Label_0129;
                        }
                        Va.M().D(name);
                        ConfigUtils.M().e();
                        final Entity entity = entityHit;
                        break Label_0129;
                    }
                    else {
                        Va.M().M(name);
                        ConfigUtils.M().e();
                    }
                }
                final Entity entity = entityHit;
            }
            Entity entity;
            if (entity instanceof EntityHorse || entityHit instanceof EntityDonkey || entityHit instanceof EntityMule || entityHit instanceof EntityLlama || entityHit instanceof EntityOcelot || entityHit instanceof EntityParrot || entityHit instanceof EntityWolf) {
                final long n = 1L;
                UUID uuid = new UUID(n, n);
                if ((entityHit instanceof EntityHorse || entityHit instanceof EntityDonkey || entityHit instanceof EntityMule || entityHit instanceof EntityLlama) && ((AbstractHorse)entityHit).getOwnerUniqueId() != null) {
                    uuid = ((AbstractHorse)entityHit).getOwnerUniqueId();
                }
                if (entityHit instanceof EntityOcelot && ((EntityOcelot)entityHit).getOwnerId() != null) {
                    uuid = ((EntityOcelot)entityHit).getOwnerId();
                }
                if (entityHit instanceof EntityParrot && ((EntityParrot)entityHit).getOwnerId() != null) {
                    uuid = ((EntityParrot)entityHit).getOwnerId();
                }
                if (entityHit instanceof EntityWolf && ((EntityWolf)entityHit).getOwnerId() != null) {
                    uuid = ((EntityWolf)entityHit).getOwnerId();
                }
                r.M(uuid, (FutureCallback<R>)new aD(this));
            }
        }
    }
    
    @Override
    public boolean A() {
        return false;
    }
    
    public MCF() {
        super(Category.PLAYER, "MCF", false, -1, "Middle Click Friends");
    }
}
