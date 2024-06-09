package com.client.glowclient.modules.render;

import com.client.glowclient.modules.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class EntityHunger extends ModuleContainer
{
    public EntityHunger() {
        super(Category.RENDER, "EntityHunger", false, -1, "Renders hunger while riding entities");
    }
    
    @Override
    public boolean A() {
        return false;
    }
    
    @SubscribeEvent
    public void M(final RenderGameOverlayEvent renderGameOverlayEvent) {
        GuiIngameForge.renderFood = true;
    }
}
