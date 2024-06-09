package com.client.glowclient.modules.jewishtricks;

import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class RemoveEntities extends ModuleContainer
{
    public RemoveEntities() {
        super(Category.JEWISH TRICKS, "RemoveEntities", false, -1, "Removes all entities in your render distance - client sided");
    }
    
    @Override
    public void D() {
        try {
            final Iterator<Entity> iterator = Wrapper.mc.world.loadedEntityList.iterator();
            while (iterator.hasNext()) {
                final Entity entity;
                if ((entity = iterator.next()) != null && !(entity instanceof EntityPlayer)) {
                    Wrapper.mc.world.removeEntityFromWorld(entity.entityId);
                }
            }
        }
        catch (Exception ex) {}
    }
}
