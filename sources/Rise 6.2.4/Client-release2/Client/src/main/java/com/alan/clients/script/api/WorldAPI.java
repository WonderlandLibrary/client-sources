package com.alan.clients.script.api;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.script.api.wrapper.impl.ScriptBlockPos;
import com.alan.clients.script.api.wrapper.impl.ScriptEntityLiving;
import com.alan.clients.script.api.wrapper.impl.ScriptWorld;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;

public class WorldAPI extends ScriptWorld {

    public WorldAPI() {
        super(MC.theWorld);

        Client.INSTANCE.getEventBus().register(this);
    }

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        if (this.wrapped == null) {
            this.wrapped = MC.theWorld;
        }
    };

    public ScriptEntityLiving[] getEntities() {
        final Object[] entityLivingBases = MC.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityLivingBase).toArray();
        final ScriptEntityLiving[] scriptEntities = new ScriptEntityLiving[entityLivingBases.length];

        for (int index = 0; index < entityLivingBases.length; index++) {
            scriptEntities[index] = new ScriptEntityLiving((EntityLivingBase) entityLivingBases[index]);
        }

        return scriptEntities;
    }

    public ScriptEntityLiving getTargetEntity(int range) {
        EntityLivingBase entityLivingBase = TargetComponent.getTarget(range);
        return entityLivingBase != null ? new ScriptEntityLiving(entityLivingBase) : null;
    }

    public void removeEntity(int id) {
        MC.theWorld.removeEntityFromWorld(id);
    }

    public void removeEntity(ScriptEntityLiving entity) {
        removeEntity(entity.getEntityId());
    }

    public ScriptBlockPos newBlockPos(int x, int y, int z) {
        return new ScriptBlockPos(new BlockPos(x, y, z));
    }

    public String getBlockName(ScriptBlockPos blockPos) {
        return blockPos.getBlock().getName();
    }

}
